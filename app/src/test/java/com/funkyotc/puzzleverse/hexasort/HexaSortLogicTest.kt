package com.funkyotc.puzzleverse.hexasort

import com.funkyotc.puzzleverse.hexasort.data.HexaSortLevel
import com.funkyotc.puzzleverse.hexasort.data.HexaSortState
import org.junit.Assert.*
import org.junit.Test

class HexaSortLogicTest {

    @Test
    fun testHexaSortLevelPalette() {
        val grid = listOf(
            listOf(1, 2, null),
            listOf(null, 2, 3),
            listOf(1, 3, 4)
        )
        val level = HexaSortLevel(
            id = "test_1",
            difficulty = "Easy",
            rows = 3,
            cols = 3,
            grid = grid
        )
        val palette = level.colorPalette
        assertEquals(setOf(1, 2, 3, 4), palette)
    }

    @Test
    fun testHexaSortInitialState() {
        val level = HexaSortLevel("test_1", "Easy", 3, 3, listOf(listOf(1, 2), listOf(3, 4)))
        val state = HexaSortState(level = level, grid = level.grid)
        assertEquals(0, state.score)
        assertEquals(0, state.moves)
        assertEquals(2, state.shufflesRemaining)
        assertFalse(state.isWon)
        assertFalse(state.isGameOver)
    }
}
