package com.funkyotc.puzzleverse.minesweeper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.minesweeper.data.MineCell
import com.funkyotc.puzzleverse.minesweeper.data.MinesweeperState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MinesweeperViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard"
) : ViewModel() {
    private val _state = MutableStateFlow(MinesweeperState())
    val state: StateFlow<MinesweeperState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val rows = 12
        val cols = 10
        val totalMines = 20
        
        val emptyGrid = List(rows) { r ->
            List(cols) { c ->
                MineCell(row = r, col = c)
            }
        }
        
        _state.value = MinesweeperState(
            grid = emptyGrid,
            rows = rows,
            cols = cols,
            totalMines = totalMines
        )
    }

    private fun placeMines(firstClickRow: Int, firstClickCol: Int) {
        val st = _state.value
        val allPositions = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until st.rows) {
            for (c in 0 until st.cols) {
                // Ensure first click and immediate neighbors are safe
                if (Math.abs(r - firstClickRow) <= 1 && Math.abs(c - firstClickCol) <= 1) continue
                allPositions.add(Pair(r, c))
            }
        }
        
        allPositions.shuffle()
        val minePositions = allPositions.take(st.totalMines).toSet()
        
        val newGrid = st.grid.map { it.toMutableList() }.toMutableList()
        for (pos in minePositions) {
            newGrid[pos.first][pos.second] = newGrid[pos.first][pos.second].copy(isMine = true)
        }
        
        // Calculate neighboring mines
        for (r in 0 until st.rows) {
            for (c in 0 until st.cols) {
                if (!newGrid[r][c].isMine) {
                    var count = 0
                    for (dr in -1..1) {
                        for (dc in -1..1) {
                            val nr = r + dr
                            val nc = c + dc
                            if (nr in 0 until st.rows && nc in 0 until st.cols && newGrid[nr][nc].isMine) {
                                count++
                            }
                        }
                    }
                    newGrid[r][c] = newGrid[r][c].copy(neighboringMines = count)
                }
            }
        }
        
        _state.update { it.copy(grid = newGrid, firstClickDone = true) }
    }

    fun revealCell(row: Int, col: Int) {
        val st = _state.value
        if (st.isGameOver || st.isWon) return
        if (st.grid[row][col].isRevealed || st.grid[row][col].isFlagged) return

        if (!st.firstClickDone) {
            placeMines(row, col)
        }

        val grid = _state.value.grid.map { it.toMutableList() }.toMutableList()
        if (grid[row][col].isMine) {
            // Game Over
            for (r in 0 until st.rows) {
                for (c in 0 until st.cols) {
                    if (grid[r][c].isMine) {
                        grid[r][c] = grid[r][c].copy(isRevealed = true)
                    }
                }
            }
            _state.update { it.copy(grid = grid, isGameOver = true) }
            return
        }

        floodFillReveal(grid, row, col, st.rows, st.cols)
        
        val isWon = checkWin(grid, st.rows, st.cols, st.totalMines)
        _state.update { it.copy(grid = grid, isWon = isWon) }
    }
    
    private fun floodFillReveal(grid: MutableList<MutableList<MineCell>>, row: Int, col: Int, rows: Int, cols: Int) {
        if (row !in 0 until rows || col !in 0 until cols) return
        if (grid[row][col].isRevealed || grid[row][col].isFlagged || grid[row][col].isMine) return

        grid[row][col] = grid[row][col].copy(isRevealed = true)
        if (grid[row][col].neighboringMines == 0) {
            for (dr in -1..1) {
                for (dc in -1..1) {
                    floodFillReveal(grid, row + dr, col + dc, rows, cols)
                }
            }
        }
    }

    fun toggleFlag(row: Int, col: Int) {
        val st = _state.value
        if (st.isGameOver || st.isWon || !st.firstClickDone) return
        if (st.grid[row][col].isRevealed) return
        
        val grid = st.grid.map { it.toMutableList() }.toMutableList()
        val currentlyFlagged = grid[row][col].isFlagged
        
        val newFlags = if (currentlyFlagged) st.flagsPlaced - 1 else st.flagsPlaced + 1
        grid[row][col] = grid[row][col].copy(isFlagged = !currentlyFlagged)
        
        _state.update { it.copy(grid = grid, flagsPlaced = newFlags) }
    }

    private fun checkWin(grid: List<List<MineCell>>, rows: Int, cols: Int, totalMines: Int): Boolean {
        var unrevealed = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (!grid[r][c].isRevealed) unrevealed++
            }
        }
        return unrevealed == totalMines
    }
}

class MinesweeperViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MinesweeperViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MinesweeperViewModel(streakRepository, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
