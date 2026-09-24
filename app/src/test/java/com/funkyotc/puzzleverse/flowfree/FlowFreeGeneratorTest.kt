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

        val failures = mutableListOf<String>()
        for (puzzle in allPuzzles) {
            val size = puzzle.size
            val dots = puzzle.dots

            // Verify dot endpoints are distinct and within bounds
            val points = mutableSetOf<Pair<Int, Int>>()
            val structuralIssues = mutableListOf<String>()
            for (dot in dots) {
                val start = Pair(dot.start.r, dot.start.c)
                val end = Pair(dot.end.r, dot.end.c)
                if (dot.start.r !in 0 until size || dot.start.c !in 0 until size ||
                    dot.end.r !in 0 until size || dot.end.c !in 0 until size) structuralIssues += "endpoint out of bounds"
                if (start == end) structuralIssues += "identical endpoints"
                if (!points.add(start) || !points.add(end)) structuralIssues += "duplicate endpoint"
            }
            if (structuralIssues.isNotEmpty()) {
                failures += "${puzzle.id}: ${structuralIssues.distinct().joinToString()}"
                continue
            }

            // Verify singular 100% coverage solution using FlowSolver
            val solverDots = dots.map { 
                generators.flowfree.ColorDot(
                    it.colorId, 
                    generators.flowfree.Point(it.start.r, it.start.c), 
                    generators.flowfree.Point(it.end.r, it.end.c)
                ) 
            }
            val solver = generators.flowfree.FlowSolver(size, solverDots)
            val solutions = solver.countFullCoverageSolutions(maxSolutions = 2, maxSteps = 1_000_000)
            if (solutions != 1 || solver.wasTruncated) {
                failures += "${puzzle.id}: $solutions solution(s)${if (solver.wasTruncated) " (search limit reached)" else ""}"
            }
        }
        assertTrue("Invalid or unproved Flow Free boards: ${failures.joinToString()}", failures.isEmpty())
    }
}
