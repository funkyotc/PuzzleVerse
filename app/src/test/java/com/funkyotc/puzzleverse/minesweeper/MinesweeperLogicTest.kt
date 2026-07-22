package com.funkyotc.puzzleverse.minesweeper

import com.funkyotc.puzzleverse.minesweeper.data.MineCell
import com.funkyotc.puzzleverse.minesweeper.data.MinesweeperState
import org.junit.Assert.*
import org.junit.Test

class MinesweeperLogicTest {

    @Test
    fun testInitialMinesweeperState() {
        val state = MinesweeperState(rows = 10, cols = 10, totalMines = 15)
        assertEquals(10, state.rows)
        assertEquals(10, state.cols)
        assertEquals(15, state.totalMines)
        assertFalse(state.isGameOver)
        assertFalse(state.isWon)
    }

    @Test
    fun testMineCellNeighbors() {
        val cell = MineCell(row = 1, col = 2, isMine = false, neighboringMines = 3, isRevealed = true)
        assertEquals(1, cell.row)
        assertEquals(2, cell.col)
        assertFalse(cell.isMine)
        assertEquals(3, cell.neighboringMines)
        assertTrue(cell.isRevealed)
        assertFalse(cell.isFlagged)
    }
}
