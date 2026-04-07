package com.funkyotc.puzzleverse.tfe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.tfe.data.Direction
import com.funkyotc.puzzleverse.tfe.data.TfeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TfeViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard"
) : ViewModel() {
    private val _state = MutableStateFlow(TfeState())
    val state: StateFlow<TfeState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val emptyGrid = List(4) { List(4) { 0 } }
        _state.value = TfeState(grid = emptyGrid)
        addRandomTile()
        addRandomTile()
    }

    private fun addRandomTile() {
        val current = _state.value
        if (!current.hasEmptyCell()) return
        
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (r in 0..3) {
            for (c in 0..3) {
                if (current.grid[r][c] == 0) {
                    emptyCells.add(Pair(r, c))
                }
            }
        }
        
        val (r, c) = emptyCells.random()
        val newValue = if (Math.random() < 0.9) 2 else 4
        
        val newGrid = current.grid.map { it.toMutableList() }.toMutableList()
        newGrid[r][c] = newValue
        
        _state.update { it.copy(grid = newGrid) }
    }

    fun move(direction: Direction) {
        val current = _state.value
        if (current.isGameOver) return
        
        var moved = false
        val newGrid = current.grid.map { it.toMutableList() }.toMutableList()
        var newScore = current.score
        
        when (direction) {
            Direction.LEFT -> {
                for (r in 0..3) {
                    val (line, addScore) = slideAndMerge(newGrid[r])
                    newGrid[r] = line.toMutableList()
                    newScore += addScore
                    if (newGrid[r] != current.grid[r]) moved = true
                }
            }
            Direction.RIGHT -> {
                for (r in 0..3) {
                    val (line, addScore) = slideAndMerge(newGrid[r].reversed())
                    newGrid[r] = line.reversed().toMutableList()
                    newScore += addScore
                    if (newGrid[r] != current.grid[r]) moved = true
                }
            }
            Direction.UP -> {
                for (c in 0..3) {
                    val col = listOf(newGrid[0][c], newGrid[1][c], newGrid[2][c], newGrid[3][c])
                    val (line, addScore) = slideAndMerge(col)
                    newScore += addScore
                    for (r in 0..3) {
                        if (newGrid[r][c] != line[r]) {
                            newGrid[r][c] = line[r]
                            moved = true
                        }
                    }
                }
            }
            Direction.DOWN -> {
                for (c in 0..3) {
                    val col = listOf(newGrid[3][c], newGrid[2][c], newGrid[1][c], newGrid[0][c])
                    val (line, addScore) = slideAndMerge(col)
                    val rev = line.reversed()
                    newScore += addScore
                    for (r in 0..3) {
                        if (newGrid[r][c] != rev[r]) {
                            newGrid[r][c] = rev[r]
                            moved = true
                        }
                    }
                }
            }
        }
        
        if (moved) {
            _state.update { it.copy(grid = newGrid, score = newScore) }
            addRandomTile()
            checkGameOver()
        }
    }

    private fun slideAndMerge(line: List<Int>): Pair<List<Int>, Int> {
        val nonZero = line.filter { it != 0 }.toMutableList()
        var scoreAdd = 0
        var i = 0
        while (i < nonZero.size - 1) {
            if (nonZero[i] == nonZero[i+1]) {
                nonZero[i] *= 2
                scoreAdd += nonZero[i]
                nonZero.removeAt(i+1)
            }
            i++
        }
        while (nonZero.size < 4) {
            nonZero.add(0)
        }
        return Pair(nonZero, scoreAdd)
    }

    private fun checkGameOver() {
        val grid = _state.value.grid
        var canMove = false
        for (r in 0..3) {
            for (c in 0..3) {
                if (grid[r][c] == 0) canMove = true
                else {
                    if (c < 3 && grid[r][c] == grid[r][c+1]) canMove = true
                    if (r < 3 && grid[r][c] == grid[r+1][c]) canMove = true
                }
            }
        }
        val isWon = grid.any { row -> row.any { it >= 2048 } }
        _state.update { it.copy(isGameOver = !canMove, isWon = isWon) }
    }
}

class TfeViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TfeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TfeViewModel(streakRepository, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
