package com.funkyotc.puzzleverse.nonogram

import com.funkyotc.puzzleverse.nonogram.data.CellState
import com.funkyotc.puzzleverse.nonogram.viewmodel.NonogramViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NonogramViewModelTest {

    private lateinit var viewModel: NonogramViewModel

    @Before
    fun setUp() {
        viewModel = NonogramViewModel()
        var attempts = 0
        while (viewModel.state.value.rows == 0 && attempts < 50) {
            Thread.sleep(20)
            attempts++
        }
    }

    @Test
    fun testAutoFillRowCrossesWhenRowIsSatisfied() {
        val state = viewModel.state.value
        assertTrue("Rows should be initialized", state.rows > 0)
        val row = 0

        // Set filled cells matching the row solution for row 0
        for (c in 0 until state.cols) {
            if (state.solutionGrid[row][c]) {
                viewModel.setCellState(row, c, CellState.FILLED)
            }
        }

        // Row 0 should now be satisfied. Tapping hint header fills remaining empty spaces with CROSSED (X)
        val autoFilled = viewModel.autoFillRowCrosses(row)
        assertTrue(autoFilled || state.solutionGrid[row].all { it })

        val updatedState = viewModel.state.value
        for (c in 0 until state.cols) {
            if (!state.solutionGrid[row][c]) {
                assertEquals(CellState.CROSSED, updatedState.playerGrid[row][c])
            }
        }
    }

    @Test
    fun testAutoFillColCrossesWhenColIsSatisfied() {
        val state = viewModel.state.value
        assertTrue("Cols should be initialized", state.cols > 0)
        val col = 0

        // Set filled cells matching the column solution for col 0
        for (r in 0 until state.rows) {
            if (state.solutionGrid[r][col]) {
                viewModel.setCellState(r, col, CellState.FILLED)
            }
        }

        // Col 0 should now be satisfied. Tapping hint header fills remaining empty spaces with CROSSED (X)
        val autoFilled = viewModel.autoFillColCrosses(col)
        assertTrue(autoFilled || (0 until state.rows).all { state.solutionGrid[it][col] })

        val updatedState = viewModel.state.value
        for (r in 0 until state.rows) {
            if (!state.solutionGrid[r][col]) {
                assertEquals(CellState.CROSSED, updatedState.playerGrid[r][col])
            }
        }
    }

    @Test
    fun testAutoFillDoesNotTriggerWhenRowUnsatisfied() {
        val state = viewModel.state.value
        assertTrue("Rows should be initialized", state.rows > 0)
        val row = 0

        // Row is empty (and solution has filled cells), so autoFill should return false
        if (state.solutionGrid[row].any { it }) {
            val autoFilled = viewModel.autoFillRowCrosses(row)
            assertFalse(autoFilled)
        }
    }
}
