package com.funkyotc.puzzleverse.watersort

import com.funkyotc.puzzleverse.watersort.data.Bottle
import org.junit.Assert.*
import org.junit.Test

class WaterSortLogicTest {

    @Test
    fun testBottleOperations() {
        val emptyBottle = Bottle(emptyList())
        assertTrue(emptyBottle.isEmpty())
        assertFalse(emptyBottle.isFull(4))
        assertNull(emptyBottle.topColor())

        val partialBottle = Bottle(listOf(1, 1, 2))
        assertFalse(partialBottle.isEmpty())
        assertEquals(2, partialBottle.topColor())
        assertEquals(1, partialBottle.countTopColor())

        val bottleSame = Bottle(listOf(1, 2, 2, 2))
        assertEquals(2, bottleSame.topColor())
        assertEquals(3, bottleSame.countTopColor())

        val poured = bottleSame.withoutTop(3)
        assertEquals(listOf(1), poured.colors)

        val filled = emptyBottle.withColor(3, 4)
        assertEquals(listOf(3, 3, 3, 3), filled.colors)
        assertTrue(filled.isFull(4))
    }
}
