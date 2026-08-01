package com.funkyotc.puzzleverse.nonogram

import com.funkyotc.puzzleverse.nonogram.data.NonogramPregenerated
import com.funkyotc.puzzleverse.nonogram.data.NonogramPuzzleLibrary
import com.funkyotc.puzzleverse.nonogram.data.NonogramSolver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NonogramSolvabilityTest {

    @Test
    fun testPregeneratedPuzzleCountAndDistribution() {
        val puzzles = NonogramPregenerated.ALL_PUZZLES
        assertEquals("Total pregenerated puzzles must be exactly 40", 40, puzzles.size)

        val byDiff = NonogramPregenerated.PUZZLES_BY_DIFFICULTY
        assertEquals("Easy puzzles count must be 15", 15, byDiff["Easy"]?.size ?: 0)
        assertEquals("Medium puzzles count must be 15", 15, byDiff["Medium"]?.size ?: 0)
        assertEquals("Hard puzzles count must be 10", 10, byDiff["Hard"]?.size ?: 0)
    }

    @Test
    fun testAllPregeneratedPuzzlesAreSolvableWithoutGuessing() {
        val puzzles = NonogramPregenerated.ALL_PUZZLES
        val failedPuzzles = mutableListOf<String>()

        for (puzzle in puzzles) {
            val solvable = NonogramSolver.isSolvableWithoutGuessing(puzzle.grid)
            assertTrue("Puzzle ${puzzle.id} (${puzzle.difficulty}, size ${puzzle.size}x${puzzle.size}) must be solvable without guessing", solvable)
            if (!solvable) {
                failedPuzzles.add("${puzzle.id} (${puzzle.difficulty})")
            }
        }

        assertTrue("Failing puzzles: $failedPuzzles", failedPuzzles.isEmpty())
    }

    @Test
    fun testEasyPregeneratedPuzzlesSolvability() {
        val easyPuzzles = NonogramPregenerated.PUZZLES_BY_DIFFICULTY["Easy"] ?: emptyList()
        assertEquals(15, easyPuzzles.size)
        for (puzzle in easyPuzzles) {
            assertTrue("Easy puzzle ${puzzle.id} must be solvable without guessing", NonogramSolver.isSolvableWithoutGuessing(puzzle.grid))
        }
    }

    @Test
    fun testMediumPregeneratedPuzzlesSolvability() {
        val mediumPuzzles = NonogramPregenerated.PUZZLES_BY_DIFFICULTY["Medium"] ?: emptyList()
        assertEquals(15, mediumPuzzles.size)
        for (puzzle in mediumPuzzles) {
            assertTrue("Medium puzzle ${puzzle.id} must be solvable without guessing", NonogramSolver.isSolvableWithoutGuessing(puzzle.grid))
        }
    }

    @Test
    fun testHardPregeneratedPuzzlesSolvability() {
        val hardPuzzles = NonogramPregenerated.PUZZLES_BY_DIFFICULTY["Hard"] ?: emptyList()
        assertEquals(10, hardPuzzles.size)
        for (puzzle in hardPuzzles) {
            assertTrue("Hard puzzle ${puzzle.id} must be solvable without guessing", NonogramSolver.isSolvableWithoutGuessing(puzzle.grid))
        }
    }

    @Test
    fun testRandomPuzzleGeneratorSolvability() {
        for (size in listOf(10, 15)) {
            for (i in 1..5) {
                val grid = NonogramPuzzleLibrary.getRandomPuzzle(size)
                assertTrue("getRandomPuzzle($size) attempt $i must be solvable without guessing", NonogramSolver.isSolvableWithoutGuessing(grid))
            }
        }
    }

    @Test
    fun testEdgeCase1x1Grids() {
        val filled1x1 = listOf(listOf(true))
        assertTrue("1x1 filled grid must be solvable", NonogramSolver.isSolvableWithoutGuessing(filled1x1))

        val empty1x1 = listOf(listOf(false))
        assertTrue("1x1 empty grid must be solvable", NonogramSolver.isSolvableWithoutGuessing(empty1x1))
    }

    @Test
    fun testEdgeCaseFullyFilledGrids() {
        val sizes = listOf(2, 5, 10)
        for (size in sizes) {
            val grid = List(size) { List(size) { true } }
            assertTrue("${size}x${size} fully filled grid must be solvable without guessing", NonogramSolver.isSolvableWithoutGuessing(grid))
        }
    }

    @Test
    fun testEdgeCaseCompletelyEmptyGrids() {
        val sizes = listOf(2, 5, 10)
        for (size in sizes) {
            val grid = List(size) { List(size) { false } }
            assertTrue("${size}x${size} completely empty grid must be solvable without guessing", NonogramSolver.isSolvableWithoutGuessing(grid))
        }
    }

    @Test
    fun testEdgeCaseEmptyDimensions() {
        val emptyGrid = emptyList<List<Boolean>>()
        assertTrue("Empty grid list must return true", NonogramSolver.isSolvableWithoutGuessing(emptyGrid))

        val emptyRowGrid = listOf(emptyList<Boolean>())
        assertTrue("Grid with empty row must return true", NonogramSolver.isSolvableWithoutGuessing(emptyRowGrid))
    }

    @Test
    fun testEdgeCaseSingleLineLineConstraintSolver() {
        // Horizontal 1xN grids
        val line1x5 = listOf(listOf(true, false, true, true, false))
        assertTrue("1x5 single row nonogram must be solvable", NonogramSolver.isSolvableWithoutGuessing(line1x5))

        val line1x10Pattern = listOf(listOf(true, false, true, false, true, false, true, false, true, false))
        assertTrue("1x10 alternating row nonogram must be solvable", NonogramSolver.isSolvableWithoutGuessing(line1x10Pattern))

        val line1x10Block = listOf(listOf(false, false, true, true, true, true, true, false, false, false))
        assertTrue("1x10 block row nonogram must be solvable", NonogramSolver.isSolvableWithoutGuessing(line1x10Block))

        // Vertical Nx1 grids
        val col5x1 = listOf(
            listOf(true),
            listOf(true),
            listOf(false),
            listOf(true),
            listOf(false)
        )
        assertTrue("5x1 single column nonogram must be solvable", NonogramSolver.isSolvableWithoutGuessing(col5x1))

        val col10x1Pattern = List(10) { i -> listOf(i % 2 == 0) }
        assertTrue("10x1 alternating column nonogram must be solvable", NonogramSolver.isSolvableWithoutGuessing(col10x1Pattern))
    }

    @Test
    fun testAmbiguousUnsolvableGridReturnsFalse() {
        val ambiguous2x2 = listOf(
            listOf(true, false),
            listOf(false, true)
        )
        assertFalse("2x2 ambiguous grid requiring guessing must return false", NonogramSolver.isSolvableWithoutGuessing(ambiguous2x2))
    }
}
