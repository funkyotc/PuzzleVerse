package com.funkyotc.puzzleverse.nonogram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.nonogram.data.CellState
import com.funkyotc.puzzleverse.nonogram.data.NonogramState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NonogramViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard"
) : ViewModel() {
    private val _state = MutableStateFlow(NonogramState())
    val state: StateFlow<NonogramState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        // Use library for handcrafted levels
        val finalSolution = com.funkyotc.puzzleverse.nonogram.data.NonogramPuzzleLibrary.getRandomPuzzle()
        val rows = finalSolution.size
        val cols = if (rows > 0) finalSolution[0].size else 0
        
        val player = List(rows) { List(cols) { CellState.EMPTY } }
        
        val rowClues = finalSolution.map { calculateClues(it) }
        
        val colClues = mutableListOf<List<Int>>()
        for (c in 0 until cols) {
            val colList = finalSolution.map { it[c] }
            colClues.add(calculateClues(colList))
        }

        _state.value = NonogramState(
            rows = rows,
            cols = cols,
            solutionGrid = finalSolution,
            playerGrid = player,
            rowClues = rowClues,
            colClues = colClues
        )
    }
    
    private fun calculateClues(line: List<Boolean>): List<Int> {
        val clues = mutableListOf<Int>()
        var count = 0
        for (v in line) {
            if (v) count++
            else if (count > 0) {
                clues.add(count)
                count = 0
            }
        }
        if (count > 0) clues.add(count)
        if (clues.isEmpty()) clues.add(0)
        return clues
    }

    fun toggleCell(r: Int, c: Int, isFillAction: Boolean) {
        val st = _state.value
        if (st.isWon || st.isGameOver) return
        
        val currentCell = st.playerGrid[r][c]
        
        val newCellState = if (isFillAction) {
            if (currentCell == CellState.FILLED) CellState.EMPTY else CellState.FILLED
        } else {
            if (currentCell == CellState.CROSSED) CellState.EMPTY else CellState.CROSSED
        }
        
        val newPlayerGrid = st.playerGrid.map { it.toMutableList() }.toMutableList()
        newPlayerGrid[r][c] = newCellState
        
        // Instant feedback mode: (optional, simple logic: if filled wrong, show mistake)
        // Here we just let player fill whatever and check win condition.
        
        val isWon = checkWin(newPlayerGrid, st.solutionGrid, st.rows, st.cols)
        
        _state.update { it.copy(playerGrid = newPlayerGrid, isWon = isWon) }
    }
    
    private fun checkWin(player: List<List<CellState>>, solution: List<List<Boolean>>, rows: Int, cols: Int): Boolean {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val isFilled = player[r][c] == CellState.FILLED
                val shouldBeFilled = solution[r][c]
                if (isFilled != shouldBeFilled) return false
            }
        }
        return true
    }
}

class NonogramViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NonogramViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NonogramViewModel(streakRepository, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
