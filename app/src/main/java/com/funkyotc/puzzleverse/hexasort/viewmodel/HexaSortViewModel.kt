package com.funkyotc.puzzleverse.hexasort.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.hexasort.data.HexaSortLevel
import com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated
import com.funkyotc.puzzleverse.hexasort.data.HexaSortRepository
import com.funkyotc.puzzleverse.hexasort.data.HexaSortState
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HexaSortViewModel(
    context: Context,
    private val mode: String?,
    private val forceNewGame: Boolean,
    private val streakRepository: StreakRepository,
    private val puzzleId: String? = null
) : ViewModel() {

    private val repository = HexaSortRepository(context)
    private val completionRepo = PuzzleCompletionRepository(context, "HexaSort")
    private val gridKey = when {
        mode == "daily" -> "daily_hexasort_grid"
        puzzleId != null -> "puzzle_${puzzleId}"
        else -> "standard_hexasort_grid"
    }

    private val _state = MutableStateFlow<HexaSortState?>(null)
    val state: StateFlow<HexaSortState?> = _state.asStateFlow()

    private val _elapsedSeconds = MutableStateFlow(0)
    val elapsedSeconds: StateFlow<Int> = _elapsedSeconds.asStateFlow()

    private var timerJob: Job? = null

    private val _events = MutableSharedFlow<HexaSortEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<HexaSortEvent> = _events.asSharedFlow()

    init {
        val initial = if (forceNewGame) generateFreshState() else tryRestore()
        _state.value = initial
        if (initial != null) startTimer()
    }

    private fun tryRestore(): HexaSortState? {
        val level = pickLevel() ?: return null
        val savedGrid = repository.loadGrid(gridKey)
        val savedScore = repository.loadInt("${gridKey}_score")
        val savedMoves = repository.loadInt("${gridKey}_moves")
        if (savedGrid != null && savedGrid.size == level.rows && savedGrid[0].size == level.cols) {
            val allEmpty = savedGrid.all { row -> row.all { it == null } }
            val noMoves = !allEmpty && !hasValidMoves(savedGrid)
            return HexaSortState(
                level = level, grid = savedGrid,
                score = savedScore, moves = savedMoves,
                isWon = allEmpty, isGameOver = noMoves
            )
        }
        return generateFreshState()
    }

    private fun generateFreshState(): HexaSortState? {
        val level = pickLevel() ?: return null
        val grid = level.grid.map { row -> row.map { it } }
        val state = HexaSortState(level = level, grid = grid)
        saveState(state)
        return state
    }

    private fun pickLevel(): HexaSortLevel? {
        return when {
            mode == "puzzle" && puzzleId != null -> {
                HexaSortPregenerated.getPuzzleById(puzzleId)?.toLevel()
            }
            mode == "daily" -> {
                val rng = Random(todayEpochDay())
                val puzzles = HexaSortPregenerated.ALL_PUZZLES
                puzzles[rng.nextInt(puzzles.size)]?.toLevel()
            }
            else -> {
                HexaSortPregenerated.ALL_PUZZLES.randomOrNull()?.toLevel()
            }
        }
    }

    private fun saveState(state: HexaSortState) {
        if (mode == "puzzle") return // don't save puzzle mode state
        repository.saveGrid(state.grid, gridKey)
        repository.saveInt("${gridKey}_score", state.score)
        repository.saveInt("${gridKey}_moves", state.moves)
    }

    private fun clearSavedState() {
        repository.removeKey(gridKey)
        repository.removeKey("${gridKey}_score")
        repository.removeKey("${gridKey}_moves")
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _elapsedSeconds.value++
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun startNewGame() {
        clearSavedState()
        _elapsedSeconds.value = 0
        stopTimer()
        val state = generateFreshState()
        if (state != null) startTimer()
    }

    fun tapCell(row: Int, col: Int) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return

        val grid = currentState.grid
        if (grid.isEmpty() || row !in grid.indices || col !in grid[0].indices) return
        if (grid[row][col] == null) return

        val color = grid[row][col] ?: return
        val group = floodFill(grid, row, col, color)

        if (group.size < 2) return

        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        group.forEach { (r, c) -> newGrid[r][c] = null }
        flashGroup(group)
        applyGravity(newGrid)


        val allEmpty = newGrid.all { row -> row.all { it == null } }
        val noMoves = !allEmpty && !hasValidMoves(newGrid)

        val newState = currentState.copy(
            grid = newGrid,
            score = currentState.score + group.size * 10,
            moves = currentState.moves + 1,
            isWon = allEmpty,
            isGameOver = noMoves
        )
        _state.value = newState
        saveState(newState)

        if (allEmpty) onWin(newState)
        if (noMoves && !allEmpty) onGameOver()
    }

    private fun onWin(state: HexaSortState) {
        stopTimer()
        clearSavedState()
        viewModelScope.launch {
            _events.emit(HexaSortEvent.Won(state.score))
        }
    }

    private fun onGameOver() {
        stopTimer()
        viewModelScope.launch {
            _events.emit(HexaSortEvent.GameOver)
        }
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
        if (grid.isEmpty() || grid[0].isEmpty()) return
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

    private fun flashGroup(group: Set<Pair<Int, Int>>) {
        val currentState = _state.value ?: return
        _state.value = currentState.copy(flashingCells = group)
        viewModelScope.launch {
            delay(250)
            _state.value = _state.value?.copy(flashingCells = emptySet())
        }
    }

    private fun hasValidMoves(grid: List<List<Int?>>): Boolean {
        if (grid.isEmpty()) return false
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

sealed class HexaSortEvent {
    data class Won(val score: Int) : HexaSortEvent()
    data object GameOver : HexaSortEvent()
}

class HexaSortViewModelFactory(
    private val context: Context,
    private val mode: String?,
    private val forceNewGame: Boolean = false,
    private val streakRepository: StreakRepository,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HexaSortViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HexaSortViewModel(context, mode, forceNewGame, streakRepository, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
