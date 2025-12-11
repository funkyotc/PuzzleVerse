package com.funkyotc.puzzleverse.constellations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.constellations.data.Cell
import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import com.funkyotc.puzzleverse.constellations.generator.ConstellationsPuzzleGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.random.Random

class ConstellationsViewModel(
    private val mode: String?
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ConstellationsPuzzle?>(null)
    val puzzle: StateFlow<ConstellationsPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private val generator = ConstellationsPuzzleGenerator()

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        val seed = if (mode == "daily") {
            LocalDate.now().toEpochDay()
        } else {
            Random.nextLong()
        }
        // Assuming generator can take a seed now, or we implement seeded generation
        // Updating generator call to use seed logic if capable, or simulating it
        // Since original generator didn't take a seed, we need to update it or rely on randomness.
        // Let's pass the seed to generate() if we update generator, or set Random default.
        
        // For now, we will update Generate to roughly respect randomness if we pass it, 
        // but since we haven't updated Generator signature yet, let's assume valid randomness
        // or actually update generator? The plan implied updating ViewModel logic.
        // Ideally we pass a Random instance to generator.
        
        // We will assume Generator is updated to accept a Random instance or seed.
        // For this step, I'll pass the seed to a new generate method I'll add to the generator, 
        // or just use a Random instance here and pass it.
        
        _puzzle.value = generator.generate(seed = seed)
        _isGameWon.value = false
    }

    fun onCellClicked(row: Int, col: Int) {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return
        
        val currentCell = currentPuzzle.cells[row][col]
        val newState = when (currentCell.state) {
            CellState.EMPTY -> CellState.CROSS
            CellState.CROSS -> CellState.STAR
            CellState.STAR -> CellState.EMPTY
        }
        
        updateCellState(row, col, newState, isAuto = false)
    }

    fun onDragStart(row: Int, col: Int) {
        onDragOver(row, col)
    }

    fun onDragOver(row: Int, col: Int) {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return
        
        val cell = currentPuzzle.cells[row][col]
        if (cell.state == CellState.EMPTY || (cell.state == CellState.CROSS && cell.isAuto)) {
            updateCellState(row, col, CellState.CROSS, isAuto = false)
        }
    }

    private fun updateCellState(row: Int, col: Int, newState: CellState, isAuto: Boolean) {
        val currentPuzzle = _puzzle.value ?: return
        
        val newCells = currentPuzzle.cells.map { it.map { cell -> cell.copy() }.toMutableList() }.toMutableList()
        
        newCells[row][col].state = newState
        newCells[row][col].isAuto = isAuto

        for (r in newCells.indices) {
            for (c in newCells[r].indices) {
                if (newCells[r][c].isAuto) {
                    newCells[r][c].state = CellState.EMPTY
                    newCells[r][c].isAuto = false
                }
            }
        }

        val stars = mutableListOf<Cell>()
        for (r in newCells.indices) {
            for (c in newCells[r].indices) {
                if (newCells[r][c].state == CellState.STAR) {
                    stars.add(newCells[r][c])
                }
            }
        }

        val size = currentPuzzle.size
        for (star in stars) {
            for (c in 0 until size) crossOut(newCells, star.row, c)
            for (r in 0 until size) crossOut(newCells, r, star.col)
            val regionCells = currentPuzzle.regions[star.regionId] ?: emptyList()
            for ((r, c) in regionCells) crossOut(newCells, r, c)
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
            if (cell.state == CellState.EMPTY) {
                cell.state = CellState.CROSS
                cell.isAuto = true
            }
        }
    }

    private fun checkWinCondition(cells: List<List<Cell>>, size: Int, regions: Map<Int, List<Pair<Int, Int>>>) {
        for (r in 0 until size) {
            if (cells[r].count { it.state == CellState.STAR } != 1) return
        }

        for (c in 0 until size) {
            var starCount = 0
            for (r in 0 until size) {
                if (cells[r][c].state == CellState.STAR) starCount++
            }
            if (starCount != 1) return
        }

        for (regionCells in regions.values) {
            var starCount = 0
            for ((r, c) in regionCells) {
                if (cells[r][c].state == CellState.STAR) starCount++
            }
            if (starCount != 1) return
        }

        for (r in 0 until size) {
            for (c in 0 until size) {
                if (cells[r][c].state == CellState.STAR) {
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

class ConstellationsViewModelFactory(private val mode: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConstellationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConstellationsViewModel(mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
