package com.funkyotc.puzzleverse.flowfree.data

object FlowFreePregenerated {
    val PUZZLES = mapOf(
        FlowDifficulty.EASY to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(4,4)),
                ColorDot(2, Point(0,3), Point(3,4)),
                ColorDot(3, Point(1,1), Point(3,1)),
                ColorDot(4, Point(1,0), Point(4,1))
            ),
            listOf(
                ColorDot(1, Point(0,1), Point(2,3)),
                ColorDot(2, Point(0,4), Point(4,4)),
                ColorDot(3, Point(1,0), Point(4,2)),
                ColorDot(4, Point(3,0), Point(4,0))
            ),
            listOf(
                ColorDot(1, Point(0,0), Point(1,3)),
                ColorDot(2, Point(0,2), Point(2,4)),
                ColorDot(3, Point(2,0), Point(4,4)),
                ColorDot(4, Point(0,4), Point(2,1))
            )
        ),
        FlowDifficulty.MEDIUM to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(5,5)),
                ColorDot(2, Point(0,3), Point(4,5)),
                ColorDot(3, Point(1,1), Point(4,1)),
                ColorDot(4, Point(2,2), Point(4,2)),
                ColorDot(5, Point(3,3), Point(5,4))
            ),
            listOf(
                ColorDot(1, Point(0,2), Point(3,2)),
                ColorDot(2, Point(1,0), Point(5,3)),
                ColorDot(3, Point(1,4), Point(5,5)),
                ColorDot(4, Point(2,3), Point(5,4)),
                ColorDot(5, Point(3,0), Point(4,1))
            ),
            listOf(
                ColorDot(1, Point(0,0), Point(2,2)),
                ColorDot(2, Point(0,1), Point(2,3)),
                ColorDot(3, Point(0,4), Point(4,3)),
                ColorDot(4, Point(3,0), Point(5,5)),
                ColorDot(5, Point(4,0), Point(5,2))
            )
        ),
        FlowDifficulty.HARD to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(6,6)),
                ColorDot(2, Point(0,3), Point(5,6)),
                ColorDot(3, Point(1,1), Point(5,1)),
                ColorDot(4, Point(2,2), Point(5,2)),
                ColorDot(5, Point(3,3), Point(4,4)),
                ColorDot(6, Point(4,3), Point(6,4))
            ),
            listOf(
                ColorDot(1, Point(0,2), Point(4,1)),
                ColorDot(2, Point(1,0), Point(6,0)),
                ColorDot(3, Point(1,4), Point(4,5)),
                ColorDot(4, Point(2,3), Point(5,4)),
                ColorDot(5, Point(3,1), Point(6,5)),
                ColorDot(6, Point(0,6), Point(6,6))
            )
        ),
        FlowDifficulty.EXPERT to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(7,7)),
                ColorDot(2, Point(0,4), Point(6,7)),
                ColorDot(3, Point(1,1), Point(6,1)),
                ColorDot(4, Point(2,2), Point(6,2)),
                ColorDot(5, Point(2,4), Point(4,6)),
                ColorDot(6, Point(3,3), Point(5,4)),
                ColorDot(7, Point(4,3), Point(7,4))
            ),
            listOf(
                ColorDot(1, Point(0,3), Point(3,0)),
                ColorDot(2, Point(1,1), Point(7,1)),
                ColorDot(3, Point(1,5), Point(5,6)),
                ColorDot(4, Point(2,6), Point(6,6)),
                ColorDot(5, Point(3,2), Point(7,3)),
                ColorDot(6, Point(4,4), Point(6,5)),
                ColorDot(7, Point(0,7), Point(7,7))
            )
        )
    )
}
