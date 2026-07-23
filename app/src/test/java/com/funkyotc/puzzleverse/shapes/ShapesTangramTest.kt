package com.funkyotc.puzzleverse.shapes

import com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ShapesTangramTest {

    @Test
    fun testAllPregeneratedPuzzlesHaveSevenPieces() {
        val puzzles = ShapesPregenerated.ALL_PUZZLES
        assertEquals(100, puzzles.size)
        for (puzzle in puzzles) {
            assertEquals("Puzzle ${puzzle.name} (${puzzle.id}) must have 7 pieces", 7, puzzle.pieces.size)
        }
    }

    @Test
    fun testNoOverlapsInSolutionPositions() {
        val puzzles = ShapesPregenerated.ALL_PUZZLES
        val failures = mutableListOf<String>()

        for (puzzle in puzzles) {
            val pieces = puzzle.pieces
            val solvedVertices = pieces.map { piece ->
                GeometryUtils.transformPolygon(
                    piece.initialVertices,
                    piece.solutionPosition,
                    piece.solutionRotation,
                    isFlipped = piece.isFlipped
                )
            }

            for (i in solvedVertices.indices) {
                for (j in i + 1 until solvedVertices.size) {
                    val intersects = GeometryUtils.doPolygonsIntersect(solvedVertices[i], solvedVertices[j])
                    if (intersects) {
                        failures.add("Puzzle [${puzzle.name}] (${puzzle.id}) - Pieces #${pieces[i].id} and #${pieces[j].id} overlap!")
                    }
                }
            }
        }

        if (failures.isNotEmpty()) {
            fail("Overlap failures found:\n" + failures.joinToString("\n"))
        }
    }
}
