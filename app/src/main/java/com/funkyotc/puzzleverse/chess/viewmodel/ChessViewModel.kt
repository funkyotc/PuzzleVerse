package com.funkyotc.puzzleverse.chess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.chess.data.ChessPregenerated
import com.funkyotc.puzzleverse.chess.data.ChessPiece
import com.funkyotc.puzzleverse.chess.data.ChessState
import com.funkyotc.puzzleverse.chess.data.PieceColor
import com.funkyotc.puzzleverse.chess.data.PieceType
import com.funkyotc.puzzleverse.chess.data.PregeneratedChessPuzzle
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChessViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow(ChessState())
    val state: StateFlow<ChessState> = _state.asStateFlow()

    private var puzzle: PregeneratedChessPuzzle? = null
    private var chessBoard: Board = Board()
    private var playerColor: PieceColor = PieceColor.WHITE

    init {
        viewModelScope.launch(Dispatchers.Default) {
            startNewGame()
        }
    }

    fun startNewGame() {
        puzzle = when {
            puzzleId != null -> ChessPregenerated.getPuzzleById(puzzleId)
            mode == "daily" -> ChessPregenerated.getDailyPuzzle(todayEpochDay())
            else -> ChessPregenerated.getRandomPuzzle()
        }

        puzzle = puzzle?.takeIf(::isSolvablePuzzle)
            ?: ChessPregenerated.ALL_PUZZLES.firstOrNull(::isSolvablePuzzle)

        puzzle?.let { p ->
            chessBoard = Board()
            chessBoard.loadFromFen(p.fen)
            playerColor = if (chessBoard.getSideToMove() == Side.WHITE) {
                PieceColor.WHITE
            } else {
                PieceColor.BLACK
            }

            _state.value = ChessState(
                fen = p.fen,
                puzzleId = p.id,
                difficulty = p.difficulty,
                mateIn = p.mateIn,
                correctMoveFrom = p.solutionFrom,
                correctMoveTo = p.solutionTo,
                message = "Find the checkmate in ${p.mateIn}"
            )
        }
    }

    private fun isSolvablePuzzle(candidate: PregeneratedChessPuzzle): Boolean {
        return try {
            val board = Board()
            board.loadFromFen(candidate.fen)
            val move = Move(
                Square.fromValue(candidate.solutionFrom),
                Square.fromValue(candidate.solutionTo)
            )
            if (!board.isMoveLegal(move, true)) return false
            board.doMove(move)
            board.isMated()
        } catch (_: Exception) {
            false
        }
    }
    fun onSquareClicked(row: Int, col: Int) {
        val st = _state.value
        if (st.isGameOver) return

        val square = squareFromRowCol(row, col)
        val piece = chessBoard.getPiece(square)

        val isPlayer = isPlayerPiece(piece)

        if (st.selectedRow == -1) {
            if (isPlayer) {
                _state.update { it.copy(selectedRow = row, selectedCol = col) }
            }
        } else {
            if (row == st.selectedRow && col == st.selectedCol) {
                _state.update { it.copy(selectedRow = -1, selectedCol = -1) }
            } else if (isPlayer) {
                _state.update { it.copy(selectedRow = row, selectedCol = col) }
            } else {
                attemptMove(st.selectedRow, st.selectedCol, row, col)
            }
        }
    }

    private fun attemptMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int) {
        val fromSq = squareFromRowCol(fromRow, fromCol)
        val toSq = squareFromRowCol(toRow, toCol)
        val st = _state.value
        val p = puzzle ?: return
        val move = Move(fromSq, toSq)

        if (fromSq.value() == p.solutionFrom && toSq.value() == p.solutionTo && chessBoard.isMoveLegal(move, true)) {
            chessBoard.doMove(move)

            if (chessBoard.isMated()) {
                _state.update {
                    it.copy(
                        selectedRow = -1, selectedCol = -1,
                        isGameOver = true, isWon = true,
                        message = "Checkmate! You found the solution!"
                    )
                }
                handleWin()
            } else {
                _state.update {
                    it.copy(
                        selectedRow = -1, selectedCol = -1,
                        moveAttempts = it.moveAttempts + 1,
                        message = "Good move, but not the fastest mate. Try again!"
                    )
                }
                chessBoard.undoMove()
            }
        } else {
            if (chessBoard.isMoveLegal(move, true)) {
                chessBoard.doMove(move)
                if (chessBoard.isMated()) {
                    _state.update {
                        it.copy(
                            selectedRow = -1, selectedCol = -1,
                            moveAttempts = it.moveAttempts + 1,
                            message = "That is checkmate, but find the puzzle's intended move."
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            selectedRow = -1, selectedCol = -1,
                            moveAttempts = it.moveAttempts + 1,
                            message = "Not the right move. Try again!"
                        )
                    }
                }
                chessBoard.undoMove()
            } else {
                _state.update {
                    it.copy(
                        selectedRow = -1, selectedCol = -1,
                        moveAttempts = it.moveAttempts + 1,
                        message = "Illegal move. Try again!"
                    )
                }
            }
        }
    }

    private fun handleWin() {
        if (mode == "daily") {
            val today = todayEpochDay()
            streakRepository?.let { repo ->
                val streak = repo.getStreak("chess")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    repo.saveStreak(newStreak)
                }
            }
        }
    }

    fun getLegalMoveSquares(row: Int, col: Int): List<Pair<Int, Int>> {
        if (row < 0 || col < 0) return emptyList()
        val square = squareFromRowCol(row, col)
        val piece = chessBoard.getPiece(square)
        if (!isPlayerPiece(piece)) return emptyList()

        val legalMoves = chessBoard.legalMoves().filter { it.from == square }
        return legalMoves.mapNotNull { m ->
            val toSq = m.to
            if (toSq != Square.NONE) {
                rowColFromSquare(toSq)
            } else {
                null
            }
        }
    }

    private fun isPlayerPiece(piece: com.github.bhlangonijr.chesslib.Piece): Boolean {
        if (piece == com.github.bhlangonijr.chesslib.Piece.NONE) return false
        return piece.getPieceSide() == when (playerColor) {
            PieceColor.WHITE -> Side.WHITE
            PieceColor.BLACK -> Side.BLACK
        }
    }

    private fun squareFromRowCol(row: Int, col: Int): Square {
        val file = 'a' + col
        val rank = '8' - row
        return Square.fromValue("$file$rank")
    }

    private fun rowColFromSquare(square: Square): Pair<Int, Int>? {
        val notation = square.value() ?: return null
        if (notation.length < 2) return null
        val col = notation[0] - 'a'
        val row = '8' - notation[1]
        return if (row in 0..7 && col in 0..7) Pair(row, col) else null
    }

    fun getPieceAt(row: Int, col: Int): ChessPiece? {
        val square = squareFromRowCol(row, col)
        return chessLibPieceToModel(chessBoard.getPiece(square))
    }

    private fun chessLibPieceToModel(libPiece: com.github.bhlangonijr.chesslib.Piece): ChessPiece? {
        if (libPiece == com.github.bhlangonijr.chesslib.Piece.NONE) return null
        val color = if (libPiece.getPieceSide() == Side.WHITE) PieceColor.WHITE else PieceColor.BLACK
        val type = when (libPiece) {
            com.github.bhlangonijr.chesslib.Piece.WHITE_KING, com.github.bhlangonijr.chesslib.Piece.BLACK_KING -> PieceType.KING
            com.github.bhlangonijr.chesslib.Piece.WHITE_QUEEN, com.github.bhlangonijr.chesslib.Piece.BLACK_QUEEN -> PieceType.QUEEN
            com.github.bhlangonijr.chesslib.Piece.WHITE_ROOK, com.github.bhlangonijr.chesslib.Piece.BLACK_ROOK -> PieceType.ROOK
            com.github.bhlangonijr.chesslib.Piece.WHITE_BISHOP, com.github.bhlangonijr.chesslib.Piece.BLACK_BISHOP -> PieceType.BISHOP
            com.github.bhlangonijr.chesslib.Piece.WHITE_KNIGHT, com.github.bhlangonijr.chesslib.Piece.BLACK_KNIGHT -> PieceType.KNIGHT
            com.github.bhlangonijr.chesslib.Piece.WHITE_PAWN, com.github.bhlangonijr.chesslib.Piece.BLACK_PAWN -> PieceType.PAWN
            else -> return null
        }
        return ChessPiece(type, color)
    }
}

class ChessViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChessViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}