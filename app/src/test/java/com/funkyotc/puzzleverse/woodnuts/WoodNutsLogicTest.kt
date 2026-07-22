package com.funkyotc.puzzleverse.woodnuts

import com.funkyotc.puzzleverse.woodnuts.data.Bolt
import com.funkyotc.puzzleverse.woodnuts.data.Plank
import com.funkyotc.puzzleverse.woodnuts.data.clampBoltCellToPlanks
import org.junit.Assert.*
import org.junit.Test

class WoodNutsLogicTest {

    @Test
    fun testClampBoltCellToPlanks() {
        val bolt = Bolt(id = "b1", row = 0, col = 3)
        val plank = Plank(
            id = "p1",
            startRow = 0,
            startCol = 0,
            endRow = 0,
            endCol = 2,
            boltIds = listOf("b1")
        )
        val (clampedCol, clampedRow) = clampBoltCellToPlanks(bolt, listOf(plank))
        assertEquals(2.0f, clampedCol, 0.001f)
        assertEquals(0.0f, clampedRow, 0.001f)
    }

    @Test
    fun testBoltRemoval() {
        val bolt = Bolt(id = "b1", row = 1, col = 1, removed = false)
        val removedBolt = bolt.copy(removed = true)
        assertTrue(removedBolt.removed)
    }
}
