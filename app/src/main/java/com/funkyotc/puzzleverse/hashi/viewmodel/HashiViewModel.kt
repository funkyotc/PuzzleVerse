package com.funkyotc.puzzleverse.hashi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.hashi.data.HashiPregenerated
import com.funkyotc.puzzleverse.hashi.data.HashiPuzzle
import com.funkyotc.puzzleverse.hashi.data.Island
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

data class Bridge(
    val island1: Island,
    val island2: Island,
    val count: Int
)

class HashiViewModel(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModel() {

    private val _puzzle = MutableStateFlow<HashiPuzzle?>(null)
    val puzzle: StateFlow<HashiPuzzle?> = _puzzle.asStateFlow()

    private val _bridges = MutableStateFlow<List<Bridge>>(emptyList())
    val bridges: StateFlow<List<Bridge>> = _bridges.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    init {
        loadPuzzle()
    }

    private fun loadPuzzle() {
        val p = if (mode == "puzzle" && puzzleId != null) {
            HashiPregenerated.getPuzzle(puzzleId)
        } else if (mode == "daily") {
            HashiPregenerated.MEDIUM_PUZZLES.firstOrNull() // Simplified for demo
        } else {
            HashiPregenerated.EASY_PUZZLES.randomOrNull()
        }
        _puzzle.value = p
        _bridges.value = emptyList()
        _isGameWon.value = false
    }

    fun toggleBridge(i1: Island, i2: Island) {
        if (_isGameWon.value) return
        
        // Ensure standard ordering so (i1, i2) is same as (i2, i1)
        val sorted = listOf(i1, i2).sortedWith(compareBy({ it.x }, { it.y }))
        val islandA = sorted[0]
        val islandB = sorted[1]

        // Validate bridge is horizontal or vertical
        if (islandA.x != islandB.x && islandA.y != islandB.y) return

        // Check if there's any island in between
        if (hasIslandBetween(islandA, islandB)) return

        // Check for crossing bridges
        if (isCrossingOtherBridges(islandA, islandB)) return

        val currentBridges = _bridges.value.toMutableList()
        val existingBridgeIndex = currentBridges.indexOfFirst { it.island1 == islandA && it.island2 == islandB }

        if (existingBridgeIndex != -1) {
            val existing = currentBridges[existingBridgeIndex]
            if (existing.count == 1) {
                currentBridges[existingBridgeIndex] = existing.copy(count = 2)
            } else {
                currentBridges.removeAt(existingBridgeIndex)
            }
        } else {
            currentBridges.add(Bridge(islandA, islandB, 1))
        }

        _bridges.value = currentBridges
        checkWinCondition()
    }

    private fun hasIslandBetween(i1: Island, i2: Island): Boolean {
        val p = _puzzle.value ?: return false
        if (i1.x == i2.x) {
            val minY = min(i1.y, i2.y)
            val maxY = max(i1.y, i2.y)
            return p.islands.any { it.x == i1.x && it.y > minY && it.y < maxY }
        } else {
            val minX = min(i1.x, i2.x)
            val maxX = max(i1.x, i2.x)
            return p.islands.any { it.y == i1.y && it.x > minX && it.x < maxX }
        }
    }

    private fun isCrossingOtherBridges(i1: Island, i2: Island): Boolean {
        val isHorizontal = i1.y == i2.y
        for (b in _bridges.value) {
            val bIsHorizontal = b.island1.y == b.island2.y
            if (isHorizontal && !bIsHorizontal) {
                val minX = min(i1.x, i2.x)
                val maxX = max(i1.x, i2.x)
                val bMinY = min(b.island1.y, b.island2.y)
                val bMaxY = max(b.island1.y, b.island2.y)
                
                if (b.island1.x in (minX + 1) until maxX && i1.y in (bMinY + 1) until bMaxY) {
                    return true
                }
            } else if (!isHorizontal && bIsHorizontal) {
                val minY = min(i1.y, i2.y)
                val maxY = max(i1.y, i2.y)
                val bMinX = min(b.island1.x, b.island2.x)
                val bMaxX = max(b.island1.x, b.island2.x)
                
                if (b.island1.y in (minY + 1) until maxY && i1.x in (bMinX + 1) until bMaxX) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkWinCondition() {
        val p = _puzzle.value ?: return
        val currentBridges = _bridges.value

        // 1. Check all island counts
        for (island in p.islands) {
            val count = currentBridges.filter { it.island1 == island || it.island2 == island }.sumOf { it.count }
            if (count != island.requiredBridges) return
        }

        // 2. Check connectedness
        if (p.islands.isNotEmpty()) {
            val visited = mutableSetOf<Island>()
            val queue = mutableListOf(p.islands.first())
            visited.add(p.islands.first())

            while (queue.isNotEmpty()) {
                val current = queue.removeAt(0)
                val neighbors = currentBridges.filter { it.island1 == current || it.island2 == current }
                    .map { if (it.island1 == current) it.island2 else it.island1 }
                
                for (n in neighbors) {
                    if (n !in visited) {
                        visited.add(n)
                        queue.add(n)
                    }
                }
            }

            if (visited.size != p.islands.size) return
        }

        _isGameWon.value = true
        if (mode == "daily") {
            viewModelScope.launch {
                val currentStreak = streakRepository.getStreak("hashi")
                val today = todayEpochDay()
                if (currentStreak.lastCompletedEpochDay != today) {
                    val newCount = if (currentStreak.lastCompletedEpochDay == today - 1) currentStreak.count + 1 else 1
                    streakRepository.saveStreak(currentStreak.copy(count = newCount, lastCompletedEpochDay = today))
                }
            }
        }
    }
}

class HashiViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HashiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HashiViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
