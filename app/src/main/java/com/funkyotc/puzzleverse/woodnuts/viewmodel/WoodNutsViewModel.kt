package com.funkyotc.puzzleverse.woodnuts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.woodnuts.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.funkyotc.puzzleverse.core.todayEpochDay

class WoodNutsViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow<WoodNutsState?>(null)
    val state: StateFlow<WoodNutsState?> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val selectedLevel = when {
            puzzleId != null -> {
                WoodNutsPregenerated.ALL_LEVELS.find { it.id == puzzleId }
            }
            mode == "daily" -> {
                val today = todayEpochDay()
                val index = (today % WoodNutsPregenerated.ALL_LEVELS.size).toInt()
                WoodNutsPregenerated.ALL_LEVELS[index]
            }
            else -> {
                val diffName = when (mode?.lowercase()) {
                    "easy" -> "Easy"
                    "medium" -> "Medium"
                    "hard" -> "Hard"
                    else -> null
                }
                if (diffName != null) {
                    WoodNutsPregenerated.PUZZLES_BY_DIFFICULTY[diffName]?.random()
                } else {
                    WoodNutsPregenerated.ALL_LEVELS.random()
                }
            }
        } ?: WoodNutsPregenerated.ALL_LEVELS.first()

        val level = WoodNutsLevel(
            id = selectedLevel.id,
            difficulty = selectedLevel.difficulty,
            rows = selectedLevel.rows,
            cols = selectedLevel.cols,
            bolts = selectedLevel.bolts,
            planks = selectedLevel.planks
        )

        _state.value = WoodNutsState(
            level = level,
            bolts = selectedLevel.bolts.map { it.copy() },
            planks = selectedLevel.planks.map { it.copy() }
        )
    }

    fun removeBolt(boltId: String) {
        val currentState = _state.value ?: return
        if (currentState.isWon) return

        val boltIndex = currentState.bolts.indexOfFirst { it.id == boltId && !it.removed }
        if (boltIndex == -1) return

        val updatedBolts = currentState.bolts.toMutableList()
        updatedBolts[boltIndex] = updatedBolts[boltIndex].copy(removed = true)

        var updatedPlanks = currentState.planks.toMutableList()
        var lastRemoved: String? = null

        var changed = true
        while (changed) {
            changed = false
            val newPlanks = updatedPlanks.toMutableList()
            for (i in updatedPlanks.indices) {
                val plank = updatedPlanks[i]
                if (plank.removed) continue
                val hasSupport = plank.boltIds.any { id ->
                    updatedBolts.find { it.id == id }?.removed != true
                }
                if (!hasSupport) {
                    newPlanks[i] = plank.copy(removed = true)
                    lastRemoved = plank.id
                    changed = true
                }
            }
            updatedPlanks = newPlanks
        }

        val isWon = updatedPlanks.all { it.removed }

        if (isWon && mode == "daily") {
            val today = todayEpochDay()
            streakRepository?.let { repo ->
                val streak = repo.getStreak("woodnuts")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    repo.saveStreak(newStreak)
                }
            }
        }

        _state.value = WoodNutsState(
            level = currentState.level,
            bolts = updatedBolts,
            planks = updatedPlanks,
            moves = currentState.moves + 1,
            isWon = isWon,
            lastRemovedPlankId = lastRemoved
        )
    }
}

class WoodNutsViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WoodNutsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WoodNutsViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
