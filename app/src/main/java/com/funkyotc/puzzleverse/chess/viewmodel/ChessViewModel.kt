package com.funkyotc.puzzleverse.chess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.chess.data.ChessPregenerated
import com.funkyotc.puzzleverse.chess.data.ChessPieceUiModel
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
import kotlinx.coroutines.delay
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

        puzzle = puzzle ?: ChessPregenerated.ALL_PUZZLES.firstOrNull()

        puzzle?.let { p ->
            chessBoard = Board()
            chessBoard.loadFromFen(p.fen)
            playerColor = if (chessBoard.getSideToMove() == Side.WHITE) {
                PieceColor.WHITE
            } else {
                PieceColor.BLACK
            }

            _state.value = ChessState(
                pieces = createInitialPieces(chessBoard),
                fen = p.fen,
                puzzleId = p.id,
                difficulty = p.difficulty,
                mateIn = p.mateIn,
                correctMoveIndex = 0,
                message = "Find the checkmate in ${p.mateIn}"
            )
        }
    }

    private fun createInitialPieces(board: Board): List<ChessPieceUiModel> {
        val pieces = mutableListOf<ChessPieceUiModel>()
        val pieceCounts = mutableMapOf<String, Int>()

        for (row in 0..7) {
            for (col in 0..7) {
                val sq = squareFromRowCol(row, col)
                val piece = board.getPiece(sq)
                if (piece != com.github.bhlangonijr.chesslib.Piece.NONE) {
                    val color = if (piece.getPieceSide() == Side.WHITE) PieceColor.WHITE else PieceColor.BLACK
                    val type = when (piece.pieceType) {
                        com.github.bhlangonijr.chesslib.PieceType.KING -> PieceType.KING
                        com.github.bhlangonijr.chesslib.PieceType.QUEEN -> PieceType.QUEEN
                        com.github.bhlangonijr.chesslib.PieceType.ROOK -> PieceType.ROOK
                        com.github.bhlangonijr.chesslib.PieceType.BISHOP -> PieceType.BISHOP
                        com.github.bhlangonijr.chesslib.PieceType.KNIGHT -> PieceType.KNIGHT
                        com.github.bhlangonijr.chesslib.PieceType.PAWN -> PieceType.PAWN
                        else -> continue
                    }
                    val typePrefix = type.name.lowercase() + "_" + color.name.lowercase()
                    val count = pieceCounts.getOrDefault(typePrefix, 0) + 1
                    pieceCounts[typePrefix] = count
                    val id = "${typePrefix}_$count"
                    pieces.add(ChessPieceUiModel(id, type, color, row, col))
                }
            }
        }
        return pieces
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
        val moveNotation = fromSq.value() + toSq.value()
        val st = _state.value
        val p = puzzle ?: return

        val move = Move(fromSq, toSq)
        if (!chessBoard.isMoveLegal(move, true)) {
            _state.update {
                it.copy(
                    selectedRow = -1, selectedCol = -1,
                    moveAttempts = it.moveAttempts + 1,
                    message = "Illegal move. Try again!"
                )
            }
            return
        }

        // Check against the puzzle's expected move sequence
        if (st.correctMoveIndex < p.solutionMoves.size && moveNotation == p.solutionMoves[st.correctMoveIndex]) {
            // Correct move
            applyMoveToUi(fromRow, fromCol, toRow, toCol)
            chessBoard.doMove(move)

            val nextIndex = st.correctMoveIndex + 1
            if (nextIndex >= p.solutionMoves.size) {
                _state.update {
                    it.copy(
                        selectedRow = -1, selectedCol = -1,
                        isGameOver = true, isWon = true,
                        correctMoveIndex = nextIndex,
                        message = "Checkmate! You found the solution!"
                    )
                }
                handleWin()
            } else {
                _state.update {
                    it.copy(
                        selectedRow = -1, selectedCol = -1,
                        correctMoveIndex = nextIndex,
                        message = "Good move! Keep going."
                    )
                }
                // Opponent's turn to reply automatically
                playOpponentReply(p.solutionMoves[nextIndex])
            }
        } else {
            // Wrong move, but legal
            _state.update {
                it.copy(
                    selectedRow = -1, selectedCol = -1,
                    moveAttempts = it.moveAttempts + 1,
                    message = "Not the right move. Try again!"
                )
            }
        }
    }

    private fun playOpponentReply(moveNotation: String) {
        viewModelScope.launch {
            delay(500) // small delay for animation / human feel
            
            val fromSq = Square.fromValue(moveNotation.substring(0, 2))
            val toSq = Square.fromValue(moveNotation.substring(2, 4))
            val move = Move(fromSq, toSq)
            
            val fromPos = rowColFromSquare(fromSq)
            val toPos = rowColFromSquare(toSq)
            
            if (fromPos != null && toPos != null) {
                applyMoveToUi(fromPos.first, fromPos.second, toPos.first, toPos.second)
                chessBoard.doMove(move)
                
                _state.update {
                    it.copy(
                        correctMoveIndex = it.correctMoveIndex + 1,
                        message = "Opponent moved. Your turn!"
                    )
                }
            }
        }
    }

    private fun applyMoveToUi(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int) {
        _state.update { st ->
            val updatedPieces = st.pieces.map { piece ->
                if (piece.row == toRow && piece.col == toCol && !piece.isCaptured) {
                    // This piece was captured
                    piece.copy(isCaptured = true)
                } else if (piece.row == fromRow && piece.col == fromCol && !piece.isCaptured) {
                    // This is the piece that moved
                    piece.copy(row = toRow, col = toCol)
                } else {
                    piece
                }
            }
            st.copy(pieces = updatedPieces)
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