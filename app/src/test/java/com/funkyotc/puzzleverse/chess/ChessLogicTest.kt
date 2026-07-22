package com.funkyotc.puzzleverse.chess

import com.funkyotc.puzzleverse.chess.data.ChessPiece
import com.funkyotc.puzzleverse.chess.data.ChessState
import com.funkyotc.puzzleverse.chess.data.PieceColor
import com.funkyotc.puzzleverse.chess.data.PieceType
import com.funkyotc.puzzleverse.chess.data.chessPieceToUnicode
import org.junit.Assert.*
import org.junit.Test

class ChessLogicTest {

    @Test
    fun testChessPieceUnicodeMapping() {
        val whiteKing = ChessPiece("k1", PieceType.KING, PieceColor.WHITE)
        assertEquals("\u2654", chessPieceToUnicode(whiteKing))

        val blackQueen = ChessPiece("q1", PieceType.QUEEN, PieceColor.BLACK)
        assertEquals("\u265B", chessPieceToUnicode(blackQueen))
    }

    @Test
    fun testInitialChessState() {
        val state = ChessState()
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", state.fen)
        assertEquals(-1, state.selectedRow)
        assertEquals(-1, state.selectedCol)
        assertFalse(state.isGameOver)
    }
}
