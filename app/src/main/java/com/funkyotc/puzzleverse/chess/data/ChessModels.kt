package com.funkyotc.puzzleverse.chess.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

enum class PieceType(val symbol: String) {
    KING("K"),
    QUEEN("Q"),
    ROOK("R"),
    BISHOP("B"),
    KNIGHT("N"),
    PAWN("P")
}

enum class PieceColor { WHITE, BLACK }

data class ChessPiece(val id: String, val type: PieceType, val color: PieceColor)

data class ChessPieceUiModel(
    val id: String,
    val type: PieceType,
    val color: PieceColor,
    val row: Int,
    val col: Int,
    val isCaptured: Boolean = false
)

data class ChessState(
    val pieces: List<ChessPieceUiModel> = emptyList(),
    val fen: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
    val puzzleId: String = "",
    val difficulty: String = "Easy",
    val mateIn: Int = 1,
    val selectedRow: Int = -1,
    val selectedCol: Int = -1,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false,
    val isLost: Boolean = false,
    val moveAttempts: Int = 0,
    val correctMoveIndex: Int = 0,
    val message: String = "Find the checkmate move"
)

data class PregeneratedChessPuzzle(
    override val id: String,
    override val difficulty: String,
    val fen: String,
    val solutionMoves: List<String>,
    val mateIn: Int
) : BrowseablePuzzle {
    override val label: String get() = "Mate in $mateIn"
    override val subtitle: String get() = when (difficulty) {
        "Easy" -> "Checkmate in one move"
        "Medium" -> "Checkmate in two moves"
        "Hard" -> "Checkmate in three moves"
        else -> difficulty
    }
}

private fun initialPiece(row: Int, col: Int): ChessPiece? {
    val white = PieceColor.WHITE
    val black = PieceColor.BLACK
    return when {
        row == 0 -> when (col) {
            0, 7 -> ChessPiece("r0_$col", PieceType.ROOK, black)
            1, 6 -> ChessPiece("n0_$col", PieceType.KNIGHT, black)
            2, 5 -> ChessPiece("b0_$col", PieceType.BISHOP, black)
            3 -> ChessPiece("q0_$col", PieceType.QUEEN, black)
            4 -> ChessPiece("k0_$col", PieceType.KING, black)
            else -> ChessPiece("p0_$col", PieceType.PAWN, black)
        }
        row == 7 -> when (col) {
            0, 7 -> ChessPiece("r7_$col", PieceType.ROOK, white)
            1, 6 -> ChessPiece("n7_$col", PieceType.KNIGHT, white)
            2, 5 -> ChessPiece("b7_$col", PieceType.BISHOP, white)
            3 -> ChessPiece("q7_$col", PieceType.QUEEN, white)
            4 -> ChessPiece("k7_$col", PieceType.KING, white)
            else -> ChessPiece("p7_$col", PieceType.PAWN, white)
        }
        row == 1 -> ChessPiece("p1_$col", PieceType.PAWN, black)
        row == 6 -> ChessPiece("p6_$col", PieceType.PAWN, white)
        else -> null
    }
}

fun chessPieceToUnicode(piece: ChessPiece): String = when {
    piece.color == PieceColor.WHITE && piece.type == PieceType.KING -> "\u2654"
    piece.color == PieceColor.WHITE && piece.type == PieceType.QUEEN -> "\u2655"
    piece.color == PieceColor.WHITE && piece.type == PieceType.ROOK -> "\u2656"
    piece.color == PieceColor.WHITE && piece.type == PieceType.BISHOP -> "\u2657"
    piece.color == PieceColor.WHITE && piece.type == PieceType.KNIGHT -> "\u2658"
    piece.color == PieceColor.WHITE && piece.type == PieceType.PAWN -> "\u2659"
    piece.color == PieceColor.BLACK && piece.type == PieceType.KING -> "\u265A"
    piece.color == PieceColor.BLACK && piece.type == PieceType.QUEEN -> "\u265B"
    piece.color == PieceColor.BLACK && piece.type == PieceType.ROOK -> "\u265C"
    piece.color == PieceColor.BLACK && piece.type == PieceType.BISHOP -> "\u265D"
    piece.color == PieceColor.BLACK && piece.type == PieceType.KNIGHT -> "\u265E"
    piece.color == PieceColor.BLACK && piece.type == PieceType.PAWN -> "\u265F"
    else -> "?"
}