package com.funkyotc.puzzleverse

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Square
import com.funkyotc.puzzleverse.chess.data.ChessPregenerated

fun main() {
    println("Test starting")
    try {
        val p = ChessPregenerated.ALL_PUZZLES.firstOrNull()
        println("First puzzle: $p")
        if (p != null) {
            val board = Board()
            board.loadFromFen(p.fen)
            println("Board loaded")
            val piece = board.getPiece(Square.A8)
            println("Piece at A8: $piece")
            
            // Test squareFromRowCol logic
            for (row in 0..7) {
                for (col in 0..7) {
                    val file = 'a' + col
                    val rank = '8' - row
                    val str = "$file$rank"
                    Square.fromValue(str)
                }
            }
            println("squareFromRowCol logic OK")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
