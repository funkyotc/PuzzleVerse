package com.funkyotc.puzzleverse.woodnuts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.woodnuts.data.*
import com.funkyotc.puzzleverse.woodnuts.physics.WoodNutsPhysicsEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.funkyotc.puzzleverse.core.todayEpochDay
import kotlinx.coroutines.isActive
import androidx.compose.runtime.withFrameMillis

class WoodNutsViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow<WoodNutsState?>(null)
    val state: StateFlow<WoodNutsState?> = _state.asStateFlow()

    private val physicsEngine = WoodNutsPhysicsEngine()

    init {
        startNewGame()
        startPhysicsLoop()
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

        // Assign random depth layers to planks so they collide in layers
        val assignedPlanks = selectedLevel.planks.mapIndexed { index, p -> 
            p.copy(depthLayer = index % 14)
        }

        _state.value = WoodNutsState(
            level = level,
            bolts = selectedLevel.bolts.map { it.copy() },
            planks = assignedPlanks
        )

        physicsEngine.initWorld(level, assignedPlanks, selectedLevel.bolts)
    }

    private fun startPhysicsLoop() {
        viewModelScope.launch {
            var lastFrame = System.currentTimeMillis()
            while (isActive) {
                withFrameMillis { now ->
                    val dt = if (lastFrame == 0L) 1.0 / 60.0
                             else ((now - lastFrame) / 1000.0).coerceAtMost(1.0 / 60.0)
                    lastFrame = now

                    val currentState = _state.value ?: return@withFrameMillis
                    if (currentState.isWon) return@withFrameMillis

                    val transforms = physicsEngine.step(dt)
                    var updated = false

                    val newPlanks = currentState.planks.map { plank ->
                        val trans = transforms[plank.id]
                        if (trans != null && trans != plank.transform) {
                            updated = true
                            plank.copy(transform = trans)
                        } else plank
                    }.toMutableList()

                    var newlyWon = false
                    for (i in newPlanks.indices) {
                        val p = newPlanks[i]
                        if (!p.removed && physicsEngine.isPlankOutOfBounds(p.id, currentState.level.rows + 3f)) {
                            newPlanks[i] = p.copy(removed = true)
                            physicsEngine.removePlank(p.id)
                            updated = true
                        }
                    }

                    if (updated) {
                        val isWon = newPlanks.all { it.removed }
                        if (isWon && !currentState.isWon) {
                            newlyWon = true
                            if (mode == "daily") {
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
                        }
                        _state.value = currentState.copy(planks = newPlanks, isWon = isWon)
                    }
                }
            }
        }
    }

    fun removeBolt(boltId: String) {
        val currentState = _state.value ?: return
        if (currentState.isWon) return

        val boltIndex = currentState.bolts.indexOfFirst { it.id == boltId && !it.removed && !it.isUnscrewing }
        if (boltIndex == -1) return

        val unscrewingBolts = currentState.bolts.toMutableList()
        unscrewingBolts[boltIndex] = unscrewingBolts[boltIndex].copy(isUnscrewing = true)

        _state.value = currentState.copy(
            bolts = unscrewingBolts,
            moves = currentState.moves + 1
        )

        viewModelScope.launch {
            delay(350)
            finishRemoveBolt(boltId)
        }
    }

    private fun finishRemoveBolt(boltId: String) {
        val currentState = _state.value ?: return
        if (currentState.isWon) return

        val boltIndex = currentState.bolts.indexOfFirst { it.id == boltId }
        if (boltIndex == -1) return

        val updatedBolts = currentState.bolts.toMutableList()
        updatedBolts[boltIndex] = updatedBolts[boltIndex].copy(removed = true, isUnscrewing = false)

        physicsEngine.removeBolt(boltId)

        _state.value = currentState.copy(
            bolts = updatedBolts,
            lastRemovedBoltId = boltId
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
