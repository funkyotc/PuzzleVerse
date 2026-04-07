package com.funkyotc.puzzleverse.kakuro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.kakuro.data.CellType
import com.funkyotc.puzzleverse.kakuro.data.Clue
import com.funkyotc.puzzleverse.kakuro.data.KakuroCell
import com.funkyotc.puzzleverse.kakuro.data.KakuroState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KakuroViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard"
) : ViewModel() {
    private val _state = MutableStateFlow(KakuroState())
    val state: StateFlow<KakuroState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val grid = com.funkyotc.puzzleverse.kakuro.data.KakuroPuzzleLibrary.getRandomPuzzle()
        val rows = grid.size
        val cols = if (rows > 0) grid[0].size else 0
        
        _state.value = KakuroState(
            grid = grid,
            rows = rows,
            cols = cols,
            isWon = false
        )
    }

    fun setCellValue(r: Int, c: Int, value: Int) {
        val st = _state.value
        if (st.isWon) return
        if (st.grid[r][c].type != CellType.PLAYER_INPUT) return
        
        val newGrid = st.grid.map { it.toMutableList() }.toMutableList()
        val currentVal = newGrid[r][c].playerValue
        
        // toggle if same value clicked
        if (currentVal == value) {
            newGrid[r][c] = newGrid[r][c].copy(playerValue = null)
        } else {
            newGrid[r][c] = newGrid[r][c].copy(playerValue = value)
        }
        
        val won = checkWin(newGrid, st.rows, st.cols)
        _state.update { it.copy(grid = newGrid, isWon = won) }
    }
    
    private fun checkWin(grid: List<List<KakuroCell>>, rows: Int, cols: Int): Boolean {
        // Find player inputs and clues dynamically
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val cell = grid[r][c]
                if (cell.type == CellType.CLUE) {
                    val hSum = cell.clue?.horizontalSum
                    val vSum = cell.clue?.verticalSum
                    
                    if (hSum != null) {
                        var sum = 0
                        val inputs = mutableSetOf<Int>()
                        var valid = true
                        for (i in c + 1 until cols) {
                            val nextCell = grid[r][i]
                            if (nextCell.type != CellType.PLAYER_INPUT) break
                            val v = nextCell.playerValue
                            if (v == null) { valid = false; break }
                            if (inputs.contains(v)) { valid = false; break }
                            inputs.add(v)
                            sum += v
                        }
                        if (!valid || sum != hSum) return false
                    }
                    
                    if (vSum != null) {
                        var sum = 0
                        val inputs = mutableSetOf<Int>()
                        var valid = true
                        for (i in r + 1 until rows) {
                            val nextCell = grid[i][c]
                            if (nextCell.type != CellType.PLAYER_INPUT) break
                            val v = nextCell.playerValue
                            if (v == null) { valid = false; break }
                            if (inputs.contains(v)) { valid = false; break }
                            inputs.add(v)
                            sum += v
                        }
                        if (!valid || sum != vSum) return false
                    }
                }
            }
        }
        return true
    }
}

class KakuroViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KakuroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return KakuroViewModel(streakRepository, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
