package com.funkyotc.puzzleverse.sudoku

import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuPregenerated
import org.junit.Assert.*
import org.junit.Test

class SudokuLogicTest {

    @Test
    fun testPregeneratedPuzzlesLoaded() {
        val puzzles = SudokuPregenerated.ALL_PUZZLES
        assertTrue("Pregenerated Sudoku puzzles should not be empty", puzzles.isNotEmpty())
        val first = puzzles.first()
        assertEquals(9, first.puzzle.size)
        assertEquals(9, first.puzzle.first().size)
        assertEquals(9, first.solution.size)
    }

    @Test
    fun testSudokuBoardCompletionCheck() {
        val cells = mutableListOf<SudokuCell>()
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                // Set digit 5 for column 0..8
                cells.add(SudokuCell(row = r, col = c, number = if (r == 0) 5 else 0))
            }
        }
        val board = SudokuBoard(cells)
        // Only one row has number 5 (9 cells)
        assertTrue("Number 5 should be completed", board.isNumberCompleted(5))
        assertFalse("Number 1 should not be completed", board.isNumberCompleted(1))
    }
}
