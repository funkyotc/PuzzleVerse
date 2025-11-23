package com.funkyotc.puzzleverse.constellations.viewmodel

import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.constellations.data.Cell
import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import com.funkyotc.puzzleverse.constellations.generator.ConstellationsPuzzleGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

class ConstellationsViewModel : ViewModel() {

    private val _puzzle = MutableStateFlow<ConstellationsPuzzle?>(null)
    val puzzle: StateFlow<ConstellationsPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private val generator = ConstellationsPuzzleGenerator()

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        _puzzle.value = generator.generate()
        _isGameWon.value = false
    }

    fun onCellClicked(row: Int, col: Int) {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return

        // Cycle: EMPTY -> CROSS -> STAR -> EMPTY
        // Note: If it's an AUTO_CROSS, it behaves like CROSS, so next is STAR.
        
        val currentCell = currentPuzzle.cells[row][col]
        val newState = when (currentCell.state) {
            CellState.EMPTY -> CellState.CROSS
            CellState.CROSS -> CellState.STAR
            CellState.STAR -> CellState.EMPTY
        }

        // If we are changing state, we need to update the cell.
        // If we are setting to CROSS manually, isAuto should be false.
        // If we are setting to STAR, isAuto is false.
        // If we are setting to EMPTY, isAuto is false.
        
        updateCellState(row, col, newState, isAuto = false)
    }

    fun onDragStart(row: Int, col: Int) {
        // For now, just treat as drag over
        onDragOver(row, col)
    }

    fun onDragOver(row: Int, col: Int) {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return
        
        val cell = currentPuzzle.cells[row][col]
        // Only change if it's EMPTY or AUTO_CROSS. Don't overwrite existing manual CROSS or STAR.
        // User said "add lots of crosses easily".
        if (cell.state == CellState.EMPTY || (cell.state == CellState.CROSS && cell.isAuto)) {
            updateCellState(row, col, CellState.CROSS, isAuto = false)
        }
    }

    private fun updateCellState(row: Int, col: Int, newState: CellState, isAuto: Boolean) {
        val currentPuzzle = _puzzle.value ?: return
        
        // 1. Update the specific cell
        // We need a mutable copy of the grid to work with
        val newCells = currentPuzzle.cells.map { it.map { cell -> cell.copy() }.toMutableList() }.toMutableList()
        
        newCells[row][col].state = newState
        newCells[row][col].isAuto = isAuto

        // 2. Recalculate Auto Crosses
        // First, clear all existing AUTO crosses to EMPTY
        for (r in newCells.indices) {
            for (c in newCells[r].indices) {
                if (newCells[r][c].isAuto) {
                    newCells[r][c].state = CellState.EMPTY
                    newCells[r][c].isAuto = false
                }
            }
        }

        // Find all stars
        val stars = mutableListOf<Cell>()
        for (r in newCells.indices) {
            for (c in newCells[r].indices) {
                if (newCells[r][c].state == CellState.STAR) {
                    stars.add(newCells[r][c])
                }
            }
        }

        // Apply auto crosses based on stars
        val size = currentPuzzle.size
        for (star in stars) {
            // Row
            for (c in 0 until size) crossOut(newCells, star.row, c)
            // Col
            for (r in 0 until size) crossOut(newCells, r, star.col)
            // Region
            val regionCells = currentPuzzle.regions[star.regionId] ?: emptyList()
            for ((r, c) in regionCells) crossOut(newCells, r, c)
            // Neighbors
            for (dr in -1..1) {
                for (dc in -1..1) {
                    crossOut(newCells, star.row + dr, star.col + dc)
                }
            }
        }

        _puzzle.value = currentPuzzle.copy(cells = newCells)
        checkWinCondition(newCells, size, currentPuzzle.regions)
    }

    private fun crossOut(cells: MutableList<MutableList<Cell>>, r: Int, c: Int) {
        val size = cells.size
        if (r in 0 until size && c in 0 until size) {
            val cell = cells[r][c]
            // Only overwrite if EMPTY. 
            // Do NOT overwrite STAR (that would be a conflict, we show it as is).
            // Do NOT overwrite manual CROSS (user decision).
            if (cell.state == CellState.EMPTY) {
                cell.state = CellState.CROSS
                cell.isAuto = true
            }
        }
    }

    private fun checkWinCondition(cells: List<List<Cell>>, size: Int, regions: Map<Int, List<Pair<Int, Int>>>) {
        // 1. Check Rows: Exactly one star per row
        for (r in 0 until size) {
            if (cells[r].count { it.state == CellState.STAR } != 1) return
        }

        // 2. Check Cols: Exactly one star per col
        for (c in 0 until size) {
            var starCount = 0
            for (r in 0 until size) {
                if (cells[r][c].state == CellState.STAR) starCount++
            }
            if (starCount != 1) return
        }

        // 3. Check Regions: Exactly one star per region
        for (regionCells in regions.values) {
            var starCount = 0
            for ((r, c) in regionCells) {
                if (cells[r][c].state == CellState.STAR) starCount++
            }
            if (starCount != 1) return
        }

        // 4. Check Adjacency: No two stars touch (including diagonals)
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (cells[r][c].state == CellState.STAR) {
                    // Check neighbors
                    for (dr in -1..1) {
                        for (dc in -1..1) {
                            if (dr == 0 && dc == 0) continue
                            val nr = r + dr
                            val nc = c + dc
                            if (nr in 0 until size && nc in 0 until size) {
                                if (cells[nr][nc].state == CellState.STAR) return
                            }
                        }
                    }
                }
            }
        }

        _isGameWon.value = true
    }
}
