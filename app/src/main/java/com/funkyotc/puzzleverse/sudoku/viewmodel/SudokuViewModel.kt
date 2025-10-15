package com.funkyotc.puzzleverse.sudoku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.data.SudokuRepository
import com.funkyotc.puzzleverse.sudoku.generator.SudokuGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class SudokuViewModel(context: Context, private val mode: String?) : ViewModel() {

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
        val loadedBoard = repository.loadBoard(boardKey)
        if (loadedBoard != null && loadedBoard.cells.isNotEmpty()) {
            return loadedBoard
        }

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
        val newBoard = if (mode == "daily") generator.generate(dailyChallengeSeed) else generator.generate()
        _board.value = newBoard
        boardHistory.clear()
        boardHistory.add(newBoard)
        repository.saveBoard(_board.value, boardKey)
        _isGameWon.value = false
        _selectedCell.value = null
    }

    private fun validateBoard(board: SudokuBoard): SudokuBoard {
        val errorPositions = mutableSetOf<Pair<Int, Int>>()
        val cells = board.cells

        // Check rows and columns
        for (i in 0 until 9) {
            val rowCounts = mutableMapOf<Int, MutableList<Int>>()
            val colCounts = mutableMapOf<Int, MutableList<Int>>()
            for (j in 0 until 9) {
                val rowCell = cells[i * 9 + j]
                if (rowCell.number != 0) {
                    rowCounts.getOrPut(rowCell.number) { mutableListOf() }.add(j)
                }
                val colCell = cells[j * 9 + i]
                if (colCell.number != 0) {
                    colCounts.getOrPut(colCell.number) { mutableListOf() }.add(j)
                }
            }
            rowCounts.values.filter { it.size > 1 }.flatten().forEach { j -> errorPositions.add(i to j) }
            colCounts.values.filter { it.size > 1 }.flatten().forEach { j -> errorPositions.add(j to i) }
        }

        // Check 3x3 sub-grids
        for (i in 0 until 9 step 3) {
            for (j in 0 until 9 step 3) {
                val subgridCounts = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
                for (row in i until i + 3) {
                    for (col in j until j + 3) {
                        val cell = cells[row * 9 + col]
                        if (cell.number != 0) {
                            subgridCounts.getOrPut(cell.number) { mutableListOf() }.add(row to col)
                        }
                    }
                }
                subgridCounts.values.filter { it.size > 1 }.flatten().forEach { errorPositions.add(it) }
            }
        }

        val validatedCells = cells.map {
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
                // For daily challenges, you might want to prevent starting a new game until the next day
                // Or, you could allow it but with a clear indication that it's the same puzzle
            } else {
                // For standard mode, clear the saved board so a new one is generated next time
                repository.saveBoard(SudokuBoard(emptyList()), boardKey)
            }
        }
    }
}
