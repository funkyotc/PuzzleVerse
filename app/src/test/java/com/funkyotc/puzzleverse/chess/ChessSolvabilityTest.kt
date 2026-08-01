package com.funkyotc.puzzleverse.chess

import com.funkyotc.puzzleverse.chess.data.ChessPregenerated
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import org.junit.Assert.*
import org.junit.Test

class ChessSolvabilityTest {

    @Test
    fun testAllPregeneratedChessPuzzlesAreSolvable() {
        val puzzles = ChessPregenerated.ALL_PUZZLES
        assertTrue("ChessPregenerated should contain puzzles", puzzles.isNotEmpty())
        assertEquals(15, puzzles.size)

        for (puzzle in puzzles) {
            val board = Board()
            board.loadFromFen(puzzle.fen)

            assertTrue("Puzzle ${puzzle.id} solutionMoves should not be empty", puzzle.solutionMoves.isNotEmpty())

            for (moveNotation in puzzle.solutionMoves) {
                assertTrue("Move notation $moveNotation in ${puzzle.id} should be >= 4 chars", moveNotation.length >= 4)
                val fromStr = moveNotation.substring(0, 2).uppercase()
                val toStr = moveNotation.substring(2, 4).uppercase()
                val fromSq = try {
                    Square.fromValue(fromStr)
                } catch (e: Exception) {
                    fail("Invalid from square '$fromStr' in puzzle ${puzzle.id} move '$moveNotation': ${e}")
                    return
                }
                val toSq = try {
                    Square.fromValue(toStr)
                } catch (e: Exception) {
                    fail("Invalid to square '$toStr' in puzzle ${puzzle.id} move '$moveNotation': ${e}")
                    return
                }
                val move = Move(fromSq, toSq)

                val legal = try {
                    board.isMoveLegal(move, true)
                } catch (e: Exception) {
                    false
                }
                assertTrue(
                    "Move $moveNotation ($fromSq -> $toSq) in puzzle ${puzzle.id} must be a legal move",
                    legal
                )
                board.doMove(move)
            }
        }
    }
}
