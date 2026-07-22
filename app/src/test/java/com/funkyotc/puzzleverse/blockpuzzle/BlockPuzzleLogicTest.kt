package com.funkyotc.puzzleverse.blockpuzzle

import com.funkyotc.puzzleverse.blockpuzzle.data.BlockPuzzleState
import com.funkyotc.puzzleverse.blockpuzzle.data.BoxType
import com.funkyotc.puzzleverse.blockpuzzle.data.ShapeLibrary
import org.junit.Assert.*
import org.junit.Test

class BlockPuzzleLogicTest {

    @Test
    fun testInitialGridEmpty() {
        val state = BlockPuzzleState()
        assertEquals(10, state.grid.size)
        assertEquals(10, state.grid.first().size)
        assertTrue(state.grid.flatten().all { it == BoxType.EMPTY })
        assertFalse(state.isGameOver)
        assertEquals(0, state.score)
    }

    @Test
    fun testShapeLibraryGeneratesValidShapes() {
        val shape = ShapeLibrary.getRandomShape()
        assertNotNull(shape)
        assertTrue(shape.blocks.isNotEmpty())
        assertTrue(ShapeLibrary.allShapes.isNotEmpty())
    }
}
