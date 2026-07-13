package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.data.BallState
import com.funkyotc.puzzleverse.pullpin.data.Cell
import com.funkyotc.puzzleverse.pullpin.data.CellType
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PullPinPhysicsTest {

    private fun createViewModel(): PullPinViewModel {
        return PullPinViewModel(
            streakRepository = null,
            mode = "easy",
            puzzleId = "pullpin_easy_001"
        )
    }

    @Test
    fun testVerticalFalling() {
        val vm = createViewModel()

        // 3x3 grid:
        // . 1 .
        // . . .
        // . a .
        val grid = listOf(
            listOf(Cell(CellType.EMPTY), Cell(CellType.BALL, 1), Cell(CellType.EMPTY)),
            listOf(Cell(CellType.EMPTY), Cell(CellType.EMPTY), Cell(CellType.EMPTY)),
            listOf(Cell(CellType.EMPTY), Cell(CellType.CUP, 1), Cell(CellType.EMPTY))
        )
        val balls = listOf(BallState(row = 0, col = 1, color = 1))

        // Step 1: Ball should fall down to row 1
        val result1 = vm.stepPhysics(grid, balls)
        assertTrue(result1.moved)
        assertEquals(1, result1.balls[0].row)
        assertEquals(1, result1.balls[0].col)
        assertFalse(result1.balls[0].inCup)

        // Step 2: Ball should fall from row 1 to row 2 (enters cup)
        val result2 = vm.stepPhysics(result1.grid, result1.balls)
        assertTrue(result2.moved)
        assertEquals(2, result2.balls[0].row)
        assertTrue(result2.balls[0].inCup)
    }

    @Test
    fun testDiagonalSliding() {
        val vm = createViewModel()

        // 3x3 grid:
        // . 1 .
        // . W .
        // . . .
        // Wall at (1, 1) should force ball to slide left or right.
        val grid = listOf(
            listOf(Cell(CellType.EMPTY), Cell(CellType.BALL, 1), Cell(CellType.EMPTY)),
            listOf(Cell(CellType.EMPTY), Cell(CellType.WALL), Cell(CellType.EMPTY)),
            listOf(Cell(CellType.EMPTY), Cell(CellType.EMPTY), Cell(CellType.EMPTY))
        )
        val balls = listOf(BallState(row = 0, col = 1, color = 1))

        val result = vm.stepPhysics(grid, balls)
        assertTrue(result.moved)
        assertEquals(1, result.balls[0].row)
        // Col should have slid to either 0 or 2
        val finalCol = result.balls[0].col
        assertTrue(finalCol == 0 || finalCol == 2)
    }

    @Test
    fun testColorPropagation() {
        val vm = createViewModel()

        // Grid:
        // . 1 .
        // . 0 .
        // . . .
        // Ball 1 (red) is next to Ball 2 (grey).
        val grid = listOf(
            listOf(Cell(CellType.EMPTY), Cell(CellType.BALL, 1), Cell(CellType.EMPTY)),
            listOf(Cell(CellType.EMPTY), Cell(CellType.BALL, 0), Cell(CellType.EMPTY)),
            listOf(Cell(CellType.EMPTY), Cell(CellType.EMPTY), Cell(CellType.EMPTY))
        )
        val balls = listOf(
            BallState(row = 0, col = 1, color = 1),
            BallState(row = 1, col = 1, color = 0)
        )

        val result = vm.propagateColors(grid, balls)
        assertTrue(result.changed)
        assertEquals(1, result.balls[0].color) // Red
        assertEquals(1, result.balls[1].color) // Grey ball becomes colored (red)
    }
}
