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

data class ChessPiece(val type: PieceType, val color: PieceColor)

data class ChessBoard(
    val squares: List<List<ChessPiece?>> = List(8) { row ->
        List(8) { col ->
            initialPiece(row, col)
        }
    }
)

data class ChessState(
    val board: ChessBoard = ChessBoard(),
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
    val correctMoveFrom: String = "",
    val correctMoveTo: String = "",
    val message: String = "Find the checkmate move"
)

data class PregeneratedChessPuzzle(
    override val id: String,
    override val difficulty: String,
    val fen: String,
    val solutionFrom: String,
    val solutionTo: String,
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
            0, 7 -> ChessPiece(PieceType.ROOK, black)
            1, 6 -> ChessPiece(PieceType.KNIGHT, black)
            2, 5 -> ChessPiece(PieceType.BISHOP, black)
            3 -> ChessPiece(PieceType.QUEEN, black)
            4 -> ChessPiece(PieceType.KING, black)
            else -> ChessPiece(PieceType.PAWN, black)
        }
        row == 7 -> when (col) {
            0, 7 -> ChessPiece(PieceType.ROOK, white)
            1, 6 -> ChessPiece(PieceType.KNIGHT, white)
            2, 5 -> ChessPiece(PieceType.BISHOP, white)
            3 -> ChessPiece(PieceType.QUEEN, white)
            4 -> ChessPiece(PieceType.KING, white)
            else -> ChessPiece(PieceType.PAWN, white)
        }
        row == 1 -> ChessPiece(PieceType.PAWN, black)
        row == 6 -> ChessPiece(PieceType.PAWN, white)
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