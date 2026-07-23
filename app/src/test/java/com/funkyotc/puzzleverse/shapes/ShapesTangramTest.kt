package com.funkyotc.puzzleverse.shapes

import com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class ShapesTangramTest {

    @Test
    fun testAllPregeneratedPuzzlesHaveSevenPieces() {
        val puzzles = ShapesPregenerated.ALL_PUZZLES
        assertTrue("Must have pregenerated puzzles", puzzles.isNotEmpty())
        for (puzzle in puzzles) {
            assertEquals("Puzzle ${puzzle.name} (${puzzle.id}) must have 7 piece placements", 7, puzzle.placements.size)
            assertEquals("Puzzle ${puzzle.name} (${puzzle.id}) must generate 7 puzzle pieces", 7, puzzle.toShapesPuzzle().pieces.size)
        }
    }

    @Test
    fun testNoOverlapsInSolutionPositions() {
        val puzzles = ShapesPregenerated.ALL_PUZZLES
        val failures = mutableListOf<String>()

        for (puzzle in puzzles) {
            val shapesPuzzle = puzzle.toShapesPuzzle()
            val pieces = shapesPuzzle.pieces
            val solvedVertices = pieces.map { piece ->
                GeometryUtils.transformPolygon(
                    piece.localVertices,
                    piece.solutionPosition,
                    piece.solutionRotation,
                    isFlipped = piece.solutionFlipped
                )
            }

            for (i in solvedVertices.indices) {
                for (j in i + 1 until solvedVertices.size) {
                    val intersects = GeometryUtils.doPolygonsIntersect(solvedVertices[i], solvedVertices[j])
                    if (intersects) {
                        failures.add("Puzzle [${puzzle.name}] (${puzzle.id}) - Pieces #${pieces[i].id} (${pieces[i].type}) and #${pieces[j].id} (${pieces[j].type}) overlap!")
                    }
                }
            }
        }

        if (failures.isNotEmpty()) {
            fail("Overlap failures found:\n" + failures.joinToString("\n"))
        }
    }
}
