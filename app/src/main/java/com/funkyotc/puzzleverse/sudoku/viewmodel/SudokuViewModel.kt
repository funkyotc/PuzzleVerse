package com.funkyotc.puzzleverse.sudoku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.data.SudokuRepository
import com.funkyotc.puzzleverse.sudoku.generator.SudokuGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class SudokuViewModel(
    context: Context, 
    private val mode: String?, 
    private val forceNewGame: Boolean, 
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val generator = SudokuGenerator()
    private val repository = SudokuRepository(context)
    private val dailyChallengeSeed = LocalDate.now().toEpochDay()
    private val boardKey = if (mode == "daily") "daily_sudoku_board" else "standard_sudoku_board"

    private val _board: MutableStateFlow<SudokuBoard>
    val board: StateFlow<SudokuBoard>

    private val _selectedCell = MutableStateFlow<SudokuCell?>(null)
    val selectedCell: StateFlow<SudokuCell?> = _selectedCell

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val _isPencilOn = MutableStateFlow(false)
    val isPencilOn: StateFlow<Boolean> = _isPencilOn

    private var boardHistory = mutableListOf<SudokuBoard>()

    init {
        _board = MutableStateFlow(generateInitialBoard())
        board = _board
        boardHistory.add(_board.value)
    }

    private fun generateInitialBoard(): SudokuBoard {
        if (forceNewGame) {
            val newBoard = generateNewBoard()
            repository.saveBoard(newBoard, boardKey)
            return newBoard
        }

        val loadedBoard = repository.loadBoard(boardKey)
        if (loadedBoard != null && loadedBoard.cells.isNotEmpty()) {
            return loadedBoard
        }

        val newBoard = generateNewBoard()
        repository.saveBoard(newBoard, boardKey)
        return newBoard
    }

    private fun generateNewBoard(): SudokuBoard {
        return if (mode == "daily") {
            generator.generate(dailyChallengeSeed)
        } else {
            generator.generate()
        }
    }

    fun onCellSelected(row: Int, col: Int) {
        _selectedCell.value = _board.value.getCell(row, col)
    }

    fun onNumberInput(number: Int) {
        if (_isGameWon.value) return

        _selectedCell.value?.let { cell ->
            if (!cell.isHint) {
                val newCells = _board.value.cells.map {
                    if (it.row == cell.row && it.col == cell.col) {
                        if (_isPencilOn.value) {
                            val newPencilMarks = it.pencilMarks.toMutableSet()
                            if (newPencilMarks.contains(number)) {
                                newPencilMarks.remove(number)
                            } else {
                                newPencilMarks.add(number)
                            }
                            it.copy(pencilMarks = newPencilMarks, number = 0)
                        } else {
                            val newNumber = if (it.number == number) 0 else number
                            it.copy(number = newNumber, pencilMarks = emptySet())
                        }
                    } else {
                        it
                    }
                }
                val newBoard = validateBoard(SudokuBoard(newCells))
                _board.value = newBoard
                _selectedCell.value = newBoard.getCell(cell.row, cell.col)
                boardHistory.add(newBoard)
                repository.saveBoard(newBoard, boardKey)
                checkWinCondition(newBoard)
            }
        }
    }

    fun onErase() {
        _selectedCell.value?.let { cell ->
            if (!cell.isHint) {
                val newCells = _board.value.cells.map {
                    if (it.row == cell.row && it.col == cell.col) {
                        it.copy(number = 0, pencilMarks = emptySet())
                    } else {
                        it
                    }
                }
                val newBoard = validateBoard(SudokuBoard(newCells))
                _board.value = newBoard
                _selectedCell.value = newBoard.getCell(cell.row, cell.col)
                boardHistory.add(newBoard)
                repository.saveBoard(newBoard, boardKey)
            }
        }
    }

    fun togglePencil() {
        _isPencilOn.value = !_isPencilOn.value
    }

    fun undo() {
        if (boardHistory.size > 1) {
            boardHistory.removeLast()
            val previousBoard = boardHistory.last()
            _board.value = previousBoard
            repository.saveBoard(previousBoard, boardKey)
        }
    }

    fun newGame() {
        val newBoard = generateNewBoard()
        _board.value = newBoard
        boardHistory.clear()
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard, boardKey)
        _isGameWon.value = false
        _selectedCell.value = null
    }

    private fun validateBoard(board: SudokuBoard): SudokuBoard {
        val cells = board.cells
        val errorPositions = mutableSetOf<Pair<Int, Int>>()

        // Reset all errors before re-validating
        val cellsWithoutErrors = cells.map { it.copy(isError = false) }

        for (i in 0 until 9) {
            // Check rows
            val rowCounts = cellsWithoutErrors.filter { it.row == i && it.number != 0 }.groupBy { it.number }
            rowCounts.values.filter { it.size > 1 }.flatten().forEach { cell ->
                errorPositions.add(cell.row to cell.col)
            }

            // Check columns
            val colCounts = cellsWithoutErrors.filter { it.col == i && it.number != 0 }.groupBy { it.number }
            colCounts.values.filter { it.size > 1 }.flatten().forEach { cell ->
                errorPositions.add(cell.row to cell.col)
            }
        }

        // Check 3x3 sub-grids
        for (i in 0 until 9 step 3) {
            for (j in 0 until 9 step 3) {
                val subgrid = cellsWithoutErrors.filter { it.row in i until i + 3 && it.col in j until j + 3 && it.number != 0 }
                val subgridCounts = subgrid.groupBy { it.number }
                subgridCounts.values.filter { it.size > 1 }.flatten().forEach { cell ->
                    errorPositions.add(cell.row to cell.col)
                }
            }
        }

        val validatedCells = cellsWithoutErrors.map {
            it.copy(isError = errorPositions.contains(it.row to it.col))
        }

        return SudokuBoard(validatedCells)
    }

    private fun checkWinCondition(board: SudokuBoard) {
        val isComplete = board.cells.none { it.number == 0 }
        val hasNoErrors = board.cells.none { it.isError }
        if (isComplete && hasNoErrors) {
            _isGameWon.value = true

            if (mode == "daily") {
                val today = LocalDate.now().toEpochDay()
                val streak = streakRepository.getStreak("sudoku")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    streakRepository.saveStreak(newStreak)
                }
                repository.saveBoard(SudokuBoard(emptyList()), boardKey) // Clear daily challenge board on win
            } else {
                // For standard mode, clear the saved board so a new one is generated next time
                repository.saveBoard(SudokuBoard(emptyList()), boardKey)
            }
        }
    }
}
