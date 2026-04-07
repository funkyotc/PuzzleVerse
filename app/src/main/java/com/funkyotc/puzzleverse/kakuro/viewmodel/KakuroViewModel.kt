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
        val rows = 3
        val cols = 3
        
        // Simple 3x3 predefined puzzle
        // (0,0) Black   (0,1) V=4      (0,2) V=6
        // (1,0) H=3     (1,1) Input(1) (1,2) Input(2)
        // (2,0) H=7     (2,1) Input(3) (2,2) Input(4)
        
        val grid = listOf(
            listOf(
                KakuroCell(CellType.BLACK, null, null, 0, 0),
                KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1),
                KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 2)
            ),
            listOf(
                KakuroCell(CellType.CLUE, Clue(3, null), null, 1, 0),
                KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1),
                KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2)
            ),
            listOf(
                KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 0),
                KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1),
                KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2)
            )
        )
        
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
        // Simple check for our hardcoded 3x3 puzzle
        // To be generic, we would scan all clues and sum player inputs until next black/clue.
        // For grid 3x3:
        val r1c1 = grid[1][1].playerValue ?: return false
        val r1c2 = grid[1][2].playerValue ?: return false
        val r2c1 = grid[2][1].playerValue ?: return false
        val r2c2 = grid[2][2].playerValue ?: return false
        
        // Check uniqueness in runs
        if (r1c1 == r1c2) return false
        if (r2c1 == r2c2) return false
        if (r1c1 == r2c1) return false
        if (r1c2 == r2c2) return false
        
        // Check sums
        if (r1c1 + r1c2 != 3) return false
        if (r2c1 + r2c2 != 7) return false
        if (r1c1 + r2c1 != 4) return false
        if (r1c2 + r2c2 != 6) return false

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
