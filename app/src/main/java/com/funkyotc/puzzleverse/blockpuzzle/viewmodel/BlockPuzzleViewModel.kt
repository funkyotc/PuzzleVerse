package com.funkyotc.puzzleverse.blockpuzzle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.blockpuzzle.data.BlockPuzzleState
import com.funkyotc.puzzleverse.blockpuzzle.data.BoxShape
import com.funkyotc.puzzleverse.blockpuzzle.data.BoxType
import com.funkyotc.puzzleverse.blockpuzzle.data.ShapeLibrary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BlockPuzzleViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard"
) : ViewModel() {
    private val _state = MutableStateFlow(BlockPuzzleState())
    val state: StateFlow<BlockPuzzleState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val cleanGrid = List(10) { List(10) { BoxType.EMPTY } }
        _state.value = BlockPuzzleState(
            grid = cleanGrid,
            tray = listOf(ShapeLibrary.getRandomShape(), ShapeLibrary.getRandomShape(), ShapeLibrary.getRandomShape())
        )
    }

    fun placeShape(shapeIndex: Int, targetRow: Int, targetCol: Int) {
        val st = _state.value
        if (st.isGameOver) return
        
        val shape = st.tray[shapeIndex] ?: return
        
        // Validate placement
        if (!canPlace(st.grid, shape, targetRow, targetCol)) return
        
        // Place it
        val newGrid = st.grid.map { it.toMutableList() }.toMutableList()
        for (p in shape.blocks) {
            newGrid[targetRow + p.r][targetCol + p.c] = BoxType.FILLED
        }
        
        // Check for line clears
        var scoreAdd = shape.blocks.size * 10
        val rowsToClear = mutableListOf<Int>()
        val colsToClear = mutableListOf<Int>()
        
        for (r in 0 until 10) {
            if (newGrid[r].all { it == BoxType.FILLED }) rowsToClear.add(r)
        }
        for (c in 0 until 10) {
            var colFull = true
            for (r in 0 until 10) {
                if (newGrid[r][c] == BoxType.EMPTY) colFull = false
            }
            if (colFull) colsToClear.add(c)
        }
        
        for (r in rowsToClear) {
            for (c in 0 until 10) {
                newGrid[r][c] = BoxType.EMPTY
            }
        }
        for (c in colsToClear) {
            for (r in 0 until 10) {
                newGrid[r][c] = BoxType.EMPTY
            }
        }
        
        val linesCleared = rowsToClear.size + colsToClear.size
        if (linesCleared > 0) {
            scoreAdd += linesCleared * 100
        }
        
        // Update tray
        val newTray = st.tray.toMutableList()
        newTray[shapeIndex] = null
        
        // If tray empty, generate 3 more
        if (newTray.all { it == null }) {
            newTray[0] = ShapeLibrary.getRandomShape()
            newTray[1] = ShapeLibrary.getRandomShape()
            newTray[2] = ShapeLibrary.getRandomShape()
        }
        
        _state.update { it.copy(
            grid = newGrid, 
            tray = newTray, 
            score = it.score + scoreAdd,
            recentlyClearedRows = rowsToClear,
            recentlyClearedCols = colsToClear,
            flashId = if (linesCleared > 0) System.currentTimeMillis() else it.flashId
        ) }
        
        // Check check game over
        checkGameOver()
    }

    private fun canPlace(grid: List<List<BoxType>>, shape: BoxShape, r: Int, c: Int): Boolean {
        for (p in shape.blocks) {
            val nr = r + p.r
            val nc = c + p.c
            if (nr !in 0 until 10 || nc !in 0 until 10) return false
            if (grid[nr][nc] == BoxType.FILLED) return false
        }
        return true
    }

    private fun checkGameOver() {
        val st = _state.value
        var canPlay = false
        
        for (shape in st.tray) {
            if (shape == null) continue
            var shapeFitsAnywhere = false
            for (r in 0 until 10) {
                for (c in 0 until 10) {
                    if (canPlace(st.grid, shape, r, c)) {
                        shapeFitsAnywhere = true
                        break
                    }
                }
                if (shapeFitsAnywhere) break
            }
            if (shapeFitsAnywhere) {
                canPlay = true
                break
            }
        }
        
        if (!canPlay) {
            _state.update { it.copy(isGameOver = true) }
        }
    }
}

class BlockPuzzleViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlockPuzzleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlockPuzzleViewModel(streakRepository, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
