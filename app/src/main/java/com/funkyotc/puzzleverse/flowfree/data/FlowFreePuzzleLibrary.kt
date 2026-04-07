package com.funkyotc.puzzleverse.flowfree.data

object FlowFreePuzzleLibrary {
    fun getRandomPuzzle(): Pair<Int, List<ColorDot>> {
        val puzzles = listOf(
            // Level 1: 5x5 (Verified)
            Pair(5, listOf(
                ColorDot(1, Point(0, 0), Point(4, 0)), // Red
                ColorDot(2, Point(0, 1), Point(4, 1)), // Blue
                ColorDot(3, Point(0, 2), Point(4, 2)), // Green
                ColorDot(4, Point(0, 3), Point(4, 3)), // Yellow
                ColorDot(5, Point(0, 4), Point(4, 4))  // Orange
            )),
            // Level 2: 5x5 (Winding)
            Pair(5, listOf(
                ColorDot(1, Point(0, 0), Point(2, 2)), 
                ColorDot(2, Point(0, 1), Point(0, 3)),
                ColorDot(3, Point(1, 1), Point(3, 1)),
                ColorDot(4, Point(1, 4), Point(4, 4)),
                ColorDot(5, Point(4, 0), Point(4, 2))
            )),
            // Level 3: 6x6
            Pair(6, listOf(
                ColorDot(1, Point(0, 0), Point(5, 0)),
                ColorDot(2, Point(0, 1), Point(5, 1)),
                ColorDot(3, Point(0, 2), Point(5, 2)),
                ColorDot(4, Point(0, 3), Point(5, 3)),
                ColorDot(5, Point(0, 4), Point(5, 4)),
                ColorDot(6, Point(0, 5), Point(5, 5))
            )),
            // Level 4: 7x7
            Pair(7, listOf(
                ColorDot(1, Point(0, 0), Point(6, 0)),
                ColorDot(2, Point(0, 1), Point(6, 1)),
                ColorDot(3, Point(0, 2), Point(6, 2)),
                ColorDot(4, Point(0, 3), Point(6, 3)),
                ColorDot(5, Point(0, 4), Point(6, 4)),
                ColorDot(6, Point(0, 5), Point(6, 5)),
                ColorDot(7, Point(0, 6), Point(6, 6))
            )),
            // Level 5: 8x8
            Pair(8, listOf(
                ColorDot(1, Point(0, 0), Point(7, 0)),
                ColorDot(2, Point(0, 1), Point(7, 1)),
                ColorDot(3, Point(0, 2), Point(7, 2)),
                ColorDot(4, Point(0, 3), Point(7, 3)),
                ColorDot(5, Point(0, 4), Point(7, 4)),
                ColorDot(6, Point(0, 5), Point(7, 5)),
                ColorDot(7, Point(0, 6), Point(7, 6)),
                ColorDot(8, Point(0, 7), Point(7, 7))
            ))
        )
        return puzzles.random()
    }
}
