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
            // Level 2: 5x5
            Pair(5, listOf(
                ColorDot(1, Point(1, 0), Point(2, 2)), // Red
                ColorDot(2, Point(0, 2), Point(4, 2)), // Blue
                ColorDot(3, Point(1, 4), Point(4, 3)), // Green
                ColorDot(4, Point(3, 1), Point(4, 0))  // Yellow
            )),
            // Level 3: 5x5
            Pair(5, listOf(
                ColorDot(1, Point(0, 2), Point(4, 4)), // Red
                ColorDot(2, Point(0, 3), Point(2, 3)), // Blue
                ColorDot(3, Point(0, 4), Point(1, 3)), // Green
                ColorDot(4, Point(1, 0), Point(4, 0)), // Yellow
                ColorDot(5, Point(2, 2), Point(3, 2))  // Orange
            )),
            // Level 4: 5x5
            Pair(5, listOf(
                ColorDot(1, Point(4, 0), Point(2, 2)),
                ColorDot(2, Point(3, 0), Point(1, 2)),
                ColorDot(3, Point(0, 1), Point(3, 2)),
                ColorDot(4, Point(4, 1), Point(0, 4)),
                ColorDot(5, Point(4, 3), Point(2, 4))
            ))
        )
        return puzzles.random()
    }
}
