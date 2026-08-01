package com.funkyotc.puzzleverse.flowfree

import com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated
import org.junit.Assert.*
import org.junit.Test

class FlowFreeGeneratorTest {

    @Test
    fun testVerifyFlowFreePuzzles() {
        val allPuzzles = FlowFreePregenerated.ALL_PUZZLES
        assertTrue("FlowFreePregenerated should contain puzzles", allPuzzles.isNotEmpty())
        assertEquals(20, allPuzzles.size)

        for (puzzle in allPuzzles) {
            val size = puzzle.size
            val dots = puzzle.dots

            // Verify dot endpoints are distinct and within bounds
            val points = mutableSetOf<Pair<Int, Int>>()
            for (dot in dots) {
                val start = Pair(dot.start.r, dot.start.c)
                val end = Pair(dot.end.r, dot.end.c)
                assertTrue("Start point within bounds", dot.start.r in 0 until size && dot.start.c in 0 until size)
                assertTrue("End point within bounds", dot.end.r in 0 until size && dot.end.c in 0 until size)
                assertNotEquals("Start and end should not be identical", start, end)

                assertFalse("Duplicate endpoint detected in puzzle ${puzzle.id}", points.contains(start))
                points.add(start)
                assertFalse("Duplicate endpoint detected in puzzle ${puzzle.id}", points.contains(end))
                points.add(end)
            }
        }
    }
}
