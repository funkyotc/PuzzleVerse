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
    private val dailyChallengeSeed = LocalDate.now(java.time.ZoneOffset.UTC).toEpochDay()
    private val boardKey = if (mode == "daily") "daily_shikaku_board" else if (puzzleId != null) "puzzle_${puzzleId}" else "standard_shikaku_board"

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
            val newBoard = generateNewBoard()
            repository.saveBoard(newBoard, boardKey)
            return newBoard
        }

        val loadedBoard = repository.loadBoard(boardKey)
        if (loadedBoard != null && loadedBoard.cells.isNotEmpty()) {
            var restoredBoard = loadedBoard
            // Reconstruct solutionRectangles if missing
            if (restoredBoard.solutionRectangles.isEmpty()) {
                val solutionRects: List<ShikakuRectangle> = if (loadedBoard.puzzleId.startsWith("easy_") ||
                    loadedBoard.puzzleId.startsWith("medium_") ||
                    loadedBoard.puzzleId.startsWith("hard_")
                ) {
                    val pregen = com.funkyotc.puzzleverse.shikaku.data.ShikakuPregenerated.getPuzzleById(loadedBoard.puzzleId)
                    if (pregen != null) {
                        createBoardFromPuzzle(pregen).reconstructRectanglesFromCells()
                    } else {
                        emptyList()
                    }
                } else {
                    val difficulty = when (loadedBoard.gridSize) {
                        8 -> "easy"
                        10 -> "medium"
                        12 -> "hard"
                        else -> "easy"
                    }
                    ShikakuGenerator(loadedBoard.seed).generate(difficulty).reconstructRectanglesFromCells()
                }
                restoredBoard = restoredBoard.copy(solutionRectangles = solutionRects)
            }

            // Reconstruct playerRectangles if missing (from non-null cell rectangleId)
            if (restoredBoard.playerRectangles.isEmpty()) {
                val reconstructed = restoredBoard.reconstructRectanglesFromCells()
                val targetSolRects = restoredBoard.solutionRectangles
                // If it exactly matches the solution and the game is not won yet, it means the board was brand new
                // and should be cleared for the player to start empty.
                val isBrandNew = reconstructed.size == targetSolRects.size &&
                        reconstructed.all { rec ->
                            targetSolRects.any { sol ->
                                sol.row == rec.row && sol.col == rec.col &&
                                sol.width == rec.width && sol.height == rec.height
                            }
                        }

                restoredBoard = if (isBrandNew) {
                    restoredBoard.copy(
                        playerRectangles = emptyList(),
                        cells = restoredBoard.cells.map { it.copy(rectangleId = null) }
                    )
                } else {
                    restoredBoard.copy(playerRectangles = reconstructed)
                }
            }

            // Proactive Self-Healing: If there are 1x1 player rectangles that do not contain a clue of value 1,
            // the saved board is corrupt (e.g. from a previous buggy run). Clean the board so the user can play.
            val clues = restoredBoard.getClueCells()
            val hasCorruptRectangles = restoredBoard.playerRectangles.any { rect ->
                val isOneByOne = rect.width == 1 && rect.height == 1
                if (isOneByOne) {
                    val clueInRect = clues.find { c -> c.row == rect.row && c.col == rect.col }
                    clueInRect == null || clueInRect.clue != 1
                } else {
                    false
                }
            }
            if (hasCorruptRectangles) {
                restoredBoard = restoredBoard.copy(
                    playerRectangles = emptyList(),
                    cells = restoredBoard.cells.map { it.copy(rectangleId = null) }
                )
            }

            repository.saveBoard(restoredBoard, boardKey)
            return restoredBoard
        }

        val newBoard = generateNewBoard()
        repository.saveBoard(newBoard, boardKey)
        return newBoard
    }

    private fun generateNewBoard(): ShikakuBoard {
        val rawBoard = if (puzzleId != null) {
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

        // Extract solution rectangles, then reset cell rectangle IDs and initialize empty player rectangles
        val solutionRects = rawBoard.reconstructRectanglesFromCells()
        val cleanCells = rawBoard.cells.map { it.copy(rectangleId = null) }
        val cleanBoard = rawBoard.copy(
            cells = cleanCells,
            solutionRectangles = solutionRects,
            playerRectangles = emptyList()
        )

        repository.saveBoard(cleanBoard, boardKey)
        return cleanBoard
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
        // Remove overlapping player rectangles
        val filteredRects = currentBoard.playerRectangles.filter { !it.overlaps(rectangle) }
        val newRects = filteredRects + rectangle

        // Keep cells' rectangleId synchronized
        val newCells = currentBoard.cells.map { cell ->
            val covering = newRects.find { rect ->
                cell.row >= rect.row && cell.row < rect.row + rect.height &&
                cell.col >= rect.col && cell.col < rect.col + rect.width
            }
            cell.copy(rectangleId = covering?.id)
        }

        val newBoard = currentBoard.copy(
            cells = newCells,
            playerRectangles = newRects
        )

        _board.value = newBoard
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard, boardKey)
        checkWinCondition(newBoard)
    }

    fun onCellClear(row: Int, col: Int) {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        // Find player rectangle that covers this cell
        val rectToRemove = currentBoard.playerRectangles.find { rect ->
            row >= rect.row && row < rect.row + rect.height &&
            col >= rect.col && col < rect.col + rect.width
        }

        if (rectToRemove != null) {
            val newRects = currentBoard.playerRectangles - rectToRemove
            val newCells = currentBoard.cells.map { cell ->
                val covering = newRects.find { rect ->
                    cell.row >= rect.row && cell.row < rect.row + rect.height &&
                    cell.col >= rect.col && cell.col < rect.col + rect.width
                }
                cell.copy(rectangleId = covering?.id)
            }

            val newBoard = currentBoard.copy(
                cells = newCells,
                playerRectangles = newRects
            )

            _board.value = newBoard
            boardHistory.add(newBoard)
            repository.saveBoard(newBoard, boardKey)
        }
    }

    fun clearAllPlayerMarks() {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        val newCells = currentBoard.cells.map { it.copy(rectangleId = null) }
        val newBoard = currentBoard.copy(
            cells = newCells,
            playerRectangles = emptyList()
        )

        _board.value = newBoard
        boardHistory.add(newBoard)
        repository.saveBoard(newBoard, boardKey)
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
        _isGameWon.value = false
        _isGameOver.value = false
        _selectedCell.value = null
    }

    fun hint() {
        if (_isGameWon.value) return

        val currentBoard = _board.value
        val solutionRects = currentBoard.solutionRectangles
        if (solutionRects.isEmpty()) return

        // Find the first solution rectangle that isn't already drawn correctly by the player
        val unsolvedRect = solutionRects.find { solRect ->
            currentBoard.playerRectangles.none { playRect ->
                playRect.row == solRect.row &&
                playRect.col == solRect.col &&
                playRect.width == solRect.width &&
                playRect.height == solRect.height
            }
        } ?: return

        // Place the correct solution rectangle
        onPlayerRectangleDraw(unsolvedRect)
    }

    private fun checkWinCondition(board: ShikakuBoard) {
        val gridSize = board.gridSize
        val playerRectangles = board.playerRectangles

        // 1. Check if all cells are covered and do not overlap
        val covered = Array(gridSize) { BooleanArray(gridSize) }
        var hasOverlapOrOutOfBounds = false
        for (rect in playerRectangles) {
            for (r in rect.row until (rect.row + rect.height)) {
                for (c in rect.col until (rect.col + rect.width)) {
                    if (r in 0 until gridSize && c in 0 until gridSize) {
                        if (covered[r][c]) {
                            hasOverlapOrOutOfBounds = true
                        }
                        covered[r][c] = true
                    } else {
                        hasOverlapOrOutOfBounds = true
                    }
                }
            }
        }
        if (hasOverlapOrOutOfBounds) return

        val allCovered = covered.all { row -> row.all { it } }
        if (!allCovered) return

        // 2. Check clues and rectangle sizes
        val clues = board.getClueCells()
        for (rect in playerRectangles) {
            val cluesInRect = clues.filter { clueCell ->
                clueCell.row >= rect.row && clueCell.row < rect.row + rect.height &&
                clueCell.col >= rect.col && clueCell.col < rect.col + rect.width
            }
            if (cluesInRect.size != 1) return
            val targetClue = cluesInRect.first()
            if (rect.width * rect.height != targetClue.clue) return
        }

        // If we satisfy all rule checks, player has won!
        _isGameWon.value = true

        if (puzzleId != null) {
            completionRepo.markCompleted(puzzleId)
        }

        if (mode == "daily") {
            val today = LocalDate.now(java.time.ZoneOffset.UTC).toEpochDay()
            streakRepository?.getStreak("shikaku")?.let { streak ->
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    streakRepository.saveStreak(newStreak)
                }
            }
            repository.clearBoard(boardKey)
        } else {
            repository.saveBoard(ShikakuBoard(emptyList(), board.gridSize, board.seed, board.puzzleId, board.isDaily), boardKey)
        }

        settingsRepository?.addWin()
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
