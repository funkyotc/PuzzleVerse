package com.funkyotc.puzzleverse.hexasort.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.hexasort.data.HexaSortLevel
import com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated
import com.funkyotc.puzzleverse.hexasort.data.HexaSortState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HexaSortViewModel(
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow<HexaSortState?>(null)
    val state: StateFlow<HexaSortState?> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val level = when {
            mode == "puzzle" && puzzleId != null -> {
                HexaSortPregenerated.getPuzzleById(puzzleId)?.toLevel()
            }
            mode == "daily" -> {
                val seed = todayEpochDay()
                generateDailyLevel(seed)
            }
            else -> {
                val puzzles = HexaSortPregenerated.ALL_PUZZLES
                puzzles.randomOrNull()?.toLevel()
            }
        }
        if (level != null) {
            _state.value = HexaSortState(
                level = level,
                grid = level.grid.map { row -> row.map { it } }
            )
        }
    }

    private fun generateDailyLevel(seed: Long): HexaSortLevel? {
        val rng = Random(seed)
        val puzzles = HexaSortPregenerated.ALL_PUZZLES
        return puzzles[rng.nextInt(puzzles.size)]?.toLevel()
    }

    fun tapCell(row: Int, col: Int) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return

        val grid = currentState.grid
        if (row !in grid.indices || col !in grid[row].indices) return
        if (grid[row][col] == null) return

        val color = grid[row][col] ?: return
        val group = floodFill(grid, row, col, color)

        if (group.size < 2) return

        val newGrid = grid.map { it.toMutableList() }.toMutableList()

        group.forEach { (r, c) -> newGrid[r][c] = null }

        flashGroup(group)

        applyGravity(newGrid)
        fillEmptyCells(newGrid, currentState.level.colorPalette)

        val allEmpty = newGrid.all { row -> row.all { it == null } }
        val noMoves = !allEmpty && !hasValidMoves(newGrid, currentState.level.colorPalette)

        _state.value = currentState.copy(
            grid = newGrid,
            score = currentState.score + group.size * 10,
            moves = currentState.moves + 1,
            isWon = allEmpty,
            isGameOver = noMoves
        )
    }

    private fun floodFill(
        grid: List<List<Int?>>,
        startRow: Int,
        startCol: Int,
        color: Int
    ): Set<Pair<Int, Int>> {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(startRow to startCol)
        visited.add(startRow to startCol)

        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            for ((nr, nc) in getNeighbors(r, c, grid.size, grid[0].size)) {
                if (nr to nc !in visited && grid[nr][nc] == color) {
                    visited.add(nr to nc)
                    queue.add(nr to nc)
                }
            }
        }
        return visited
    }

    private fun getNeighbors(
        row: Int, col: Int, rows: Int, cols: Int
    ): List<Pair<Int, Int>> {
        val isOddRow = row % 2 == 1
        val candidates = if (isOddRow) {
            listOf(
                row - 1 to col, row - 1 to col + 1,
                row to col - 1, row to col + 1,
                row + 1 to col, row + 1 to col + 1
            )
        } else {
            listOf(
                row - 1 to col - 1, row - 1 to col,
                row to col - 1, row to col + 1,
                row + 1 to col - 1, row + 1 to col
            )
        }
        return candidates.filter { (r, c) ->
            r in 0 until rows && c in 0 until cols
        }
    }

    private fun applyGravity(grid: MutableList<MutableList<Int?>>) {
        val rows = grid.size
        val cols = grid[0].size
        for (c in 0 until cols) {
            var writeRow = rows - 1
            for (r in rows - 1 downTo 0) {
                if (grid[r][c] != null) {
                    grid[writeRow][c] = grid[r][c]
                    if (writeRow != r) grid[r][c] = null
                    writeRow--
                }
            }
        }
    }

    private fun fillEmptyCells(
        grid: MutableList<MutableList<Int?>>,
        palette: Set<Int>
    ) {
        val colors = palette.toList()
        if (colors.isEmpty()) return
        val rows = grid.size
        val cols = grid[0].size
        for (c in 0 until cols) {
            for (r in 0 until rows) {
                if (grid[r][c] == null) {
                    grid[r][c] = colors[Random.nextInt(colors.size)]
                }
            }
        }
    }

    private fun flashGroup(group: Set<Pair<Int, Int>>) {
        val currentState = _state.value ?: return
        _state.value = currentState.copy(flashingCells = group)
        viewModelScope.launch {
            delay(200)
            _state.value = _state.value?.copy(flashingCells = emptySet())
        }
    }

    private fun hasValidMoves(
        grid: List<List<Int?>>,
        palette: Set<Int>
    ): Boolean {
        val rows = grid.size
        val cols = grid[0].size
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val color = grid[r][c] ?: continue
                for ((nr, nc) in getNeighbors(r, c, rows, cols)) {
                    if (grid[nr][nc] == color) return true
                }
            }
        }
        return false
    }
}

class HexaSortViewModelFactory(
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HexaSortViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HexaSortViewModel(mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
