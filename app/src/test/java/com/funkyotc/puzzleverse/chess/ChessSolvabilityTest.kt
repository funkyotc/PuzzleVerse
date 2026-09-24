package com.funkyotc.puzzleverse.chess

import com.funkyotc.puzzleverse.chess.data.ChessPregenerated
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ChessSolvabilityTest {
    @Test fun everyScriptIsLegalAndEndsInActualMate() {
        val puzzles = ChessPregenerated.ALL_PUZZLES
        assertEquals(15, puzzles.size)
        val failures = mutableListOf<String>()
        for (puzzle in puzzles) {
            val board = Board()
            try {
                board.loadFromFen(puzzle.fen)
            } catch (error: Exception) {
                failures += "${puzzle.id}: invalid FEN ($error)"
                continue
            }
            if (puzzle.solutionMoves.isEmpty()) {
                failures += "${puzzle.id}: empty script"
                continue
            }
            var invalid: String? = null
            var beforeFinal = puzzle.fen
            for ((index, notation) in puzzle.solutionMoves.withIndex()) {
                if (index == puzzle.solutionMoves.lastIndex) beforeFinal = board.fen
                val move = try {
                    require(notation.length >= 4)
                    Move(Square.fromValue(notation.substring(0, 2).uppercase()),
                        Square.fromValue(notation.substring(2, 4).uppercase()))
                } catch (error: Exception) {
                    invalid = "bad move notation $notation"
                    break
                }
                if (!runCatching { board.isMoveLegal(move, true) }.getOrDefault(false)) {
                    invalid = "illegal move $notation at step ${index + 1}"
                    break
                }
                board.doMove(move)
            }
            if (invalid != null) {
                failures += "${puzzle.id}: $invalid"
                continue
            }
            if (!board.isMated) {
                val alternatives = Board().apply { loadFromFen(beforeFinal) }.legalMoves()
                    .filter { move -> Board().apply { loadFromFen(beforeFinal); doMove(move) }.isMated }
                failures += "${puzzle.id}: script ends without mate (mate moves: ${alternatives.joinToString()})"
            }
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }
}
