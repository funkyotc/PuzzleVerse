package com.funkyotc.puzzleverse.tfe

import com.funkyotc.puzzleverse.tfe.data.Direction
import com.funkyotc.puzzleverse.tfe.data.TfeState
import com.funkyotc.puzzleverse.tfe.data.Tile
import org.junit.Assert.*
import org.junit.Test

class TfeLogicTest {

    @Test
    fun testTfeStateEmptyCell() {
        val state = TfeState(
            tiles = listOf(
                Tile(value = 2, row = 0, col = 0),
                Tile(value = 4, row = 0, col = 1)
            )
        )
        assertTrue(state.hasEmptyCell())
        assertEquals(2, state.tiles.size)
        assertEquals(2, state.tileAt(0, 0)?.value)
        assertEquals(4, state.tileAt(0, 1)?.value)
        assertNull(state.tileAt(1, 1))
    }

    @Test
    fun testTfeStateFullGrid() {
        val tiles = mutableListOf<Tile>()
        for (r in 0 until 4) {
            for (c in 0 until 4) {
                tiles.add(Tile(value = 2, row = r, col = c))
            }
        }
        val fullState = TfeState(tiles = tiles)
        assertFalse(fullState.hasEmptyCell())
    }
}
