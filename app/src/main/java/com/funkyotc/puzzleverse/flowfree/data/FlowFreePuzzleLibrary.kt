package com.funkyotc.puzzleverse.flowfree.data

object FlowFreePuzzleLibrary {
    fun getRandomPuzzle(): Pair<Int, List<ColorDot>> {
        val puzzles = listOf(
            // Level 1: 5x5
            Pair(5, listOf(
                ColorDot(1, Point(0, 0), Point(2, 2)), // Red
                ColorDot(2, Point(0, 1), Point(4, 4)), // Blue
                ColorDot(3, Point(0, 4), Point(3, 4)), // Green
                ColorDot(4, Point(3, 0), Point(3, 1))  // Yellow
            )),
            // Level 2: 7x7
            Pair(7, listOf(
                ColorDot(1, Point(0, 0), Point(6, 6)), // Red
                ColorDot(2, Point(0, 1), Point(5, 5)), // Blue
                ColorDot(3, Point(0, 2), Point(4, 4)), // Green
                ColorDot(4, Point(1, 0), Point(6, 5)), // Yellow
                ColorDot(5, Point(2, 0), Point(6, 4))  // Orange
            )),
            // Level 3: 8x8
            Pair(8, listOf(
                ColorDot(1, Point(0, 0), Point(7, 7)), // Red
                ColorDot(2, Point(0, 1), Point(6, 6)), // Blue
                ColorDot(3, Point(0, 2), Point(5, 5)), // Green
                ColorDot(4, Point(1, 0), Point(7, 6)), // Yellow
                ColorDot(5, Point(2, 0), Point(7, 5)), // Orange
                ColorDot(6, Point(3, 0), Point(7, 4))  // Cyan
            )),
            // Level 4: 9x9
            Pair(9, listOf(
                ColorDot(1, Point(0, 0), Point(8, 8)),
                ColorDot(2, Point(0, 1), Point(8, 7)),
                ColorDot(3, Point(0, 2), Point(8, 6)),
                ColorDot(4, Point(0, 3), Point(8, 5)),
                ColorDot(5, Point(0, 4), Point(8, 4)),
                ColorDot(6, Point(4, 0), Point(4, 8)),
                ColorDot(7, Point(3, 0), Point(3, 8))
            )),
            // Level 5: 6x6
            Pair(6, listOf(
                ColorDot(1, Point(0, 0), Point(5, 0)),
                ColorDot(2, Point(0, 1), Point(5, 1)),
                ColorDot(3, Point(0, 2), Point(5, 2)),
                ColorDot(4, Point(0, 3), Point(5, 3)),
                ColorDot(5, Point(0, 4), Point(5, 4)),
                ColorDot(6, Point(0, 5), Point(5, 5))
            ))
        )
        return puzzles.random()
    }
}
