package com.funkyotc.puzzleverse.shikaku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.shikaku.data.ShikakuBoard
import com.funkyotc.puzzleverse.shikaku.data.ShikakuCell
import com.funkyotc.puzzleverse.shikaku.data.ShikakuRectangle
import com.funkyotc.puzzleverse.shikaku.data.ShikakuRepository
import com.funkyotc.puzzleverse.shikaku.generator.ShikakuGenerator
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class ShikakuViewModel(
    context: Context,
    private val mode: String? = "standard",
    private val forceNewGame: Boolean = false,
    private val streakRepository: StreakRepository? = null,
    private val settingsRepository: SettingsRepository? = null,
    private val puzzleId: String? = null
) : ViewModel() {

    private val generator = ShikakuGenerator(System.currentTimeMillis())
    private val repository = ShikakuRepository(context)
    private val completionRepo = PuzzleCompletionRepository(context, "Shikaku")
    private val dailyChallengeSeed = LocalDate.now().toEpochDay()

    private val _board = MutableStateFlow(generateInitialBoard())
    val board: StateFlow<ShikakuBoard> = _board.asStateFlow()

    private val _selectedCell = MutableStateFlow<Pair<Int, Int>?>(null)
    val selectedCell: StateFlow<Pair<Int, Int>?> = _selectedCell.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    private val _showHowToDialog = MutableStateFlow(false)
    val showHowToDialog: StateFlow<Boolean> = _showHowToDialog.asStateFlow()

    private val _showNewGameDialog = MutableStateFlow(false)
    val showNewGameDialog: StateFlow<Boolean> = _showNewGameDialog.asStateFlow()

    private var boardHistory = mutableListOf<ShikakuBoard>()

    init {
        boardHistory.add(_board.value)
    }

    private fun generateInitialBoard(): ShikakuBoard {
        if (forceNewGame) {
            return generateNewBoard()
        }

        val loadedBoard = repository.loadBoard(currentPuzzleId())
        if (loadedBoard != null) {
            return loadedBoard
        }

        return generateNewBoard()
    }

    private fun currentPuzzleId(): String {
        return puzzleId ?: _board.value.puzzleId
    }

    private fun generateNewBoard(): ShikakuBoard {
        val newBoard = if (puzzleId != null) {
            val pregen = com.funkyotc.puzzleverse.shikaku.data.ShikakuPregenerated.getPuzzleById(puzzleId)
            if (pregen != null) {
                createBoardFromPuzzle(pregen)
            } else {
                ShikakuGenerator(0).generate("easy")
            }
        } else if (mode == "daily") {
            ShikakuGenerator(dailyChallengeSeed).generate("easy")
        } else {
            ShikakuGenerator(System.currentTimeMillis()).generate("easy")
        }

        repository.saveBoard(newBoard)
        return newBoard
    }

    private fun createBoardFromPuzzle(pregen: com.funkyotc.puzzleverse.shikaku.data.PregeneratedShikaku): ShikakuBoard {
        val cells = mutableListOf<ShikakuCell>()
        val gridSize = pregen.grid.size
        val cluesMap = pregen.clues.associateBy { Pair(it.row, it.col) }

        for (r in 0 until gridSize) {
            for (c in 0 until gridSize) {
                val rectId = pregen.grid[r][c].toString()
                val clue = cluesMap[Pair(r, c)]?.value
                cells.add(ShikakuCell(r, c, clue, rectId))
            }
        }

        return ShikakuBoard(
            cells = cells,
            gridSize = gridSize,
            seed = pregen.seed,
            puzzleId = pregen.id,
            isDaily = false
        )
    }

    fun onCellSelected(row: Int, col: Int) {
        _selectedCell.value = if (_selectedCell.value == Pair(row, col)) null else Pair(row, col)
    }

    fun onPlayerRectangleDraw(rectangle: ShikakuRectangle) {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        val newCells = currentBoard.cells.map { cell ->
            if (rectangle.row <= cell.row && cell.row < rectangle.row + rectangle.height &&
                rectangle.col <= cell.col && cell.col < rectangle.col + rectangle.width) {
                cell.copy(rectangleId = rectangle.id)
            } else {
                cell
            }
        }

        val newBoard = currentBoard.copy(cells = newCells)
        _board.value = newBoard
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard)
        checkWinCondition(newBoard)
    }

    fun onCellClear(row: Int, col: Int) {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        val newCells = currentBoard.cells.map { cell ->
            if (cell.row == row && cell.col == col && cell.clue == null) {
                cell.copy(rectangleId = null)
            } else {
                cell
            }
        }

        val newBoard = currentBoard.copy(cells = newCells)
        _board.value = newBoard
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard)
    }

    fun clearAllPlayerMarks() {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        val newCells = currentBoard.cells.map { cell ->
            if (cell.clue == null) {
                cell.copy(rectangleId = null)
            } else {
                cell
            }
        }

        val newBoard = currentBoard.copy(cells = newCells)
        _board.value = newBoard
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard)
    }

    fun undo() {
        if (boardHistory.size > 1) {
            boardHistory.removeLast()
            val previousBoard = boardHistory.last()
            _board.value = previousBoard
            repository.saveBoard(previousBoard)
        }
    }

    fun newGame() {
        val newBoard = generateNewBoard()
        _board.value = newBoard
        boardHistory.clear()
        boardHistory.add(newBoard)
        _isGameWon.value = false
        _isGameOver.value = false
        _selectedCell.value = null
    }

    fun hint() {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        val solutionRects = currentBoard.getSolutionRectangles()

        // Find a cell that doesn't have the correct rectangleId assigned
        val unsolvedCells = currentBoard.cells.filter { cell ->
            cell.clue == null && cell.rectangleId != null
        }

        if (unsolvedCells.isEmpty()) return

        // Find the first solution rectangle that isn't fully solved
        val unsolvedRect = solutionRects.find { solutionRect ->
            !currentBoard.cells.filter { it.rectangleId == solutionRect.id }.all { cell ->
                cell.rectangleId == solutionRect.id
            }
        }

        if (unsolvedRect == null) return

        // Reveal one cell in the unsolved rectangle
        val targetCell = currentBoard.cells.find { cell ->
            cell.rectangleId == null &&
            unsolvedRect.row <= cell.row && cell.row < unsolvedRect.row + unsolvedRect.height &&
            unsolvedRect.col <= cell.col && cell.col < unsolvedRect.col + unsolvedRect.width
        } ?: return

        val newCells = currentBoard.cells.map { cell ->
            if (cell.row == targetCell.row && cell.col == targetCell.col) {
                cell.copy(rectangleId = unsolvedRect.id)
            } else {
                cell
            }
        }

        val newBoard = currentBoard.copy(cells = newCells)
        _board.value = newBoard
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard)
        checkWinCondition(newBoard)
    }

    private fun checkWinCondition(board: ShikakuBoard) {
        val solutionRects = board.getSolutionRectangles()

        // Check if all solution rectangles are fully identified
        val allSolved = solutionRects.all { solutionRect ->
            board.cells.all { cell ->
                val inSolutionRect = solutionRect.row <= cell.row && cell.row < solutionRect.row + solutionRect.height &&
                        solutionRect.col <= cell.col && cell.col < solutionRect.col + solutionRect.width
                if (inSolutionRect) {
                    cell.rectangleId == solutionRect.id
                } else {
                    cell.rectangleId != solutionRect.id
                }
            }
        }

        if (allSolved && solutionRects.isNotEmpty()) {
            _isGameWon.value = true

            if (puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }

            if (mode == "daily") {
                val today = LocalDate.now().toEpochDay()
                streakRepository?.getStreak("shikaku")?.let { streak ->
                    if (streak.lastCompletedEpochDay != today) {
                        val newStreak = streak.copy(
                            count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                            lastCompletedEpochDay = today
                        )
                        streakRepository.saveStreak(newStreak)
                    }
                }
                repository.clearBoard(board.puzzleId)
            } else {
                repository.saveBoard(ShikakuBoard(emptyList(), board.gridSize, board.seed, board.puzzleId, board.isDaily))
            }

            settingsRepository?.addWin()
        }
    }

    fun setShowHowToDialog(show: Boolean) {
        _showHowToDialog.value = show
    }

    fun setShowNewGameDialog(show: Boolean) {
        _showNewGameDialog.value = show
    }
}

class ShikakuViewModelFactory(
    private val context: Context,
    private val mode: String?,
    private val forceNewGame: Boolean = false,
    private val streakRepository: StreakRepository,
    private val settingsRepository: SettingsRepository,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShikakuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShikakuViewModel(context, mode, forceNewGame, streakRepository, settingsRepository, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
