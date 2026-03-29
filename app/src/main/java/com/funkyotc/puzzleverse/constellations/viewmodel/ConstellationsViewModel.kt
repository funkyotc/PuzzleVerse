package com.funkyotc.puzzleverse.constellations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.constellations.data.Cell
import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import com.funkyotc.puzzleverse.constellations.generator.ConstellationsPuzzleGenerator
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.random.Random

class ConstellationsViewModel(
    private val mode: String?,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ConstellationsPuzzle?>(null)
    val puzzle: StateFlow<ConstellationsPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()
    
    private val _moveCount = MutableStateFlow(0)
    val moveCount: StateFlow<Int> = _moveCount.asStateFlow()
    
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime.asStateFlow()
    
    private val _showErrorHighlight = MutableStateFlow(false)
    val showErrorHighlight: StateFlow<Boolean> = _showErrorHighlight.asStateFlow()
    
    private val _hintPosition = MutableStateFlow<Pair<Int, Int>?>(null)
    val hintPosition: StateFlow<Pair<Int, Int>?> = _hintPosition.asStateFlow()
    
    private var timerJob: Job? = null

    private val generator = ConstellationsPuzzleGenerator()

    init {
        loadNewPuzzle()
        startTimer()
    }

    private fun startTimer() {
        // Cancel any existing timer
        timerJob?.cancel()
        
        // Reset elapsed time
        _elapsedTime.value = 0L
        
        // Start new timer that updates every second
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000) // 1 second
                _elapsedTime.value += 1
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun loadNewPuzzle() {
        _moveCount.value = 0
        _showErrorHighlight.value = false
        _isGameWon.value = false
        
        val seed = if (mode == "daily") {
            LocalDate.now().toEpochDay()
        } else {
            Random.nextLong()
        }
        _puzzle.value = generator.generate(size = 8, seed = seed)
        startTimer()
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
        
        _moveCount.value += 1
        updateCellState(row, col, newState, isAuto = false)
    }

    fun onDragStart(row: Int, col: Int) {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return

        val currentCell = currentPuzzle.cells[row][col]
        val newState = when (currentCell.state) {
            CellState.EMPTY -> CellState.CROSS
            CellState.CROSS -> CellState.STAR
            CellState.STAR -> CellState.EMPTY
        }
        _moveCount.value += 1
        updateCellState(row, col, newState, isAuto = false)
    }

    fun onDragOver(row: Int, col: Int) {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return
        
        val cell = currentPuzzle.cells[row][col]
        if (cell.state == CellState.EMPTY || (cell.state == CellState.CROSS && cell.isAuto)) {
            updateCellState(row, col, CellState.CROSS, isAuto = false)
        }
    }

    fun checkForErrors() {
        val currentPuzzle = _puzzle.value ?: return
        if (_isGameWon.value) return
        
        _showErrorHighlight.value = true
        
        // Reset any previous error highlights
        val currentCells = currentPuzzle.cells.map { it.map { cell -> cell.copy() }.toMutableList() }.toMutableList()
        
        // Clear all error highlights first
        for (r in currentCells.indices) {
            for (c in currentCells[r].indices) {
                currentCells[r][c].isAuto = false
            }
        }
        
        // Check for violations and highlight them
        val size = currentPuzzle.size
        
        // Check rows
        for (r in 0 until size) {
            val starCount = currentCells[r].count { it.state == CellState.STAR }
            if (starCount != 1) {
                // Highlight all cells in this row
                for (c in 0 until size) {
                    currentCells[r][c].isAuto = true
                }
            }
        }
        
        // Check columns
        for (c in 0 until size) {
            var starCount = 0
            for (r in 0 until size) {
                if (currentCells[r][c].state == CellState.STAR) starCount++
            }
            if (starCount != 1) {
                // Highlight all cells in this column
                for (r in 0 until size) {
                    currentCells[r][c].isAuto = true
                }
            }
        }
        
        // Check regions
        for (regionCells in currentPuzzle.regions.values) {
            var starCount = 0
            for ((r, c) in regionCells) {
                if (currentCells[r][c].state == CellState.STAR) starCount++
            }
            if (starCount != 1) {
                // Highlight all cells in this region
                for ((r, c) in regionCells) {
                    currentCells[r][c].isAuto = true
                }
            }
        }
        
        // Check adjacency (no touching stars)
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (currentCells[r][c].state == CellState.STAR) {
                    // Check all 8 surrounding cells
                    for (dr in -1..1) {
                        for (dc in -1..1) {
                            if (dr == 0 && dc == 0) continue
                            val nr = r + dr
                            val nc = c + dc
                            if (nr in 0 until size && nc in 0 until size) {
                                if (currentCells[nr][nc].state == CellState.STAR) {
                                    // Highlight both stars
                                    currentCells[r][c].isAuto = true
                                    currentCells[nr][nc].isAuto = true
                                }
                            }
                        }
                    }
                }
            }
        }
        
        _puzzle.value = currentPuzzle.copy(cells = currentCells)
        
        // Auto-reset error highlight after 2 seconds
        viewModelScope.launch {
            delay(2000)
            _showErrorHighlight.value = false
            // Reset the cells to remove highlighting
            val resetCells = currentPuzzle.cells.map { it.map { cell -> cell.copy(isAuto = false) }.toMutableList() }.toMutableList()
            _puzzle.value = currentPuzzle.copy(cells = resetCells)
        }
    }

    fun getHint(): Pair<Int, Int>? {
        val currentPuzzle = _puzzle.value ?: return null
        if (_isGameWon.value) return null
        
        val size = currentPuzzle.size
        val cells = currentPuzzle.cells
        
        // Find a cell that's empty and could potentially be a star
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (cells[r][c].state == CellState.EMPTY) {
                    // Check if placing a star here would violate any constraints
                    if (isValidStarPlacement(r, c, cells)) {
                        emptyCells.add(r to c)
                    }
                }
            }
        }
        
        val hint = if (emptyCells.isNotEmpty()) {
            emptyCells.random()
        } else {
            null
        }
        
        _hintPosition.value = hint
        return hint
    }

    private fun isValidStarPlacement(row: Int, col: Int, cells: List<List<Cell>>): Boolean {
        val size = cells.size
        
        // Check row constraint
        val rowStarCount = cells[row].count { it.state == CellState.STAR }
        if (rowStarCount >= 1) return false
        
        // Check column constraint
        var colStarCount = 0
        for (r in 0 until size) {
            if (cells[r][col].state == CellState.STAR) colStarCount++
        }
        if (colStarCount >= 1) return false
        
        // Check adjacency constraint
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val nr = row + dr
                val nc = col + dc
                if (nr in 0 until size && nc in 0 until size) {
                    if (cells[nr][nc].state == CellState.STAR) return false
                }
            }
        }
        
        return true
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
        stopTimer()

        if (mode == "daily") {
            val today = LocalDate.now().toEpochDay()
            val streak = streakRepository.getStreak("constellations")
            if (streak.lastCompletedEpochDay != today) {
                val newStreak = streak.copy(
                    count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                    lastCompletedEpochDay = today
                )
                streakRepository.saveStreak(newStreak)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}

class ConstellationsViewModelFactory(
    private val mode: String?,
    private val streakRepository: StreakRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConstellationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConstellationsViewModel(mode, streakRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
