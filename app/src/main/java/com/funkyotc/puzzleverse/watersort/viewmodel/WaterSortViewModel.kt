package com.funkyotc.puzzleverse.watersort.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.watersort.data.Bottle
import com.funkyotc.puzzleverse.watersort.data.Level
import com.funkyotc.puzzleverse.watersort.data.WaterSortPregenerated
import com.funkyotc.puzzleverse.watersort.data.WaterSortState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class WaterSortViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow<WaterSortState?>(null)
    val state: StateFlow<WaterSortState?> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val selected = when {
            puzzleId != null -> WaterSortPregenerated.getPuzzleById(puzzleId)
            mode == "daily" -> {
                val today = todayEpochDay()
                val idx = (today % WaterSortPregenerated.ALL_LEVELS.size).toInt()
                WaterSortPregenerated.ALL_LEVELS[idx]
            }
            else -> {
                val diffName = when (mode?.lowercase()) {
                    "easy" -> "Easy"
                    "medium" -> "Medium"
                    "hard" -> "Hard"
                    else -> null
                }
                if (diffName != null) {
                    val pool = WaterSortPregenerated.PUZZLES_BY_DIFFICULTY[diffName]
                    if (pool != null && pool.isNotEmpty()) pool.random()
                    else WaterSortPregenerated.ALL_LEVELS.random()
                } else {
                    WaterSortPregenerated.ALL_LEVELS.random()
                }
            }
        } ?: WaterSortPregenerated.ALL_LEVELS.first()

        val level = Level(
            id = selected.id,
            difficulty = selected.difficulty,
            bottles = selected.bottles.map { Bottle(it) },
            numColors = selected.numColors,
            height = selected.height
        )

        _state.value = WaterSortState(
            level = level,
            bottles = level.bottles,
            selectedIndex = -1,
            moves = 0,
            isWon = false
        )
    }

    fun selectBottle(index: Int) {
        val current = _state.value ?: return
        if (current.isWon) return
        if (index < 0 || index >= current.bottles.size) return

        val bottle = current.bottles[index]
        if (bottle.isEmpty()) return

        if (current.selectedIndex == index) {
            _state.value = current.copy(selectedIndex = -1)
        } else if (current.selectedIndex == -1) {
            _state.value = current.copy(selectedIndex = index)
        } else {
            pour(current.selectedIndex, index)
        }
    }

    private fun pour(fromIndex: Int, toIndex: Int) {
        val current = _state.value ?: return
        val from = current.bottles[fromIndex]
        val to = current.bottles[toIndex]

        if (from.isEmpty()) return
        if (to.isFull(current.level.height)) return

        val topColor = from.topColor() ?: return
        if (!to.isEmpty() && to.topColor() != topColor) return

        val count = from.countTopColor()
        val space = current.level.height - to.colors.size
        val pourCount = minOf(count, space)

        val newFrom = from.withoutTop(pourCount)
        val newTo = to.withColor(topColor, pourCount)

        val newBottles = current.bottles.toMutableList()
        newBottles[fromIndex] = newFrom
        newBottles[toIndex] = newTo

        val isWon = checkWin(newBottles, current.level.numColors, current.level.height)

        if (isWon && mode == "daily") {
            val today = todayEpochDay()
            streakRepository?.let { repo ->
                val streak = repo.getStreak("watersort")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    repo.saveStreak(newStreak)
                }
            }
        }

        _state.value = current.copy(
            bottles = newBottles,
            selectedIndex = -1,
            moves = current.moves + 1,
            isWon = isWon
        )
    }

    private fun checkWin(bottles: List<Bottle>, numColors: Int, height: Int): Boolean {
        val filledBottles = bottles.filter { it.colors.size == height }
        if (filledBottles.size < numColors) return false
        return filledBottles.all { it.colors.distinct().size == 1 }
    }
}

class WaterSortViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterSortViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterSortViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
