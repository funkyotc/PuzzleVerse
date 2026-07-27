package com.funkyotc.puzzleverse.flowfree.data

enum class FlowDifficulty(val gridSize: Int, val numColors: Int, val label: String) {
    EASY(5, 4, "Easy"),
    MEDIUM(6, 5, "Medium"),
    HARD(7, 6, "Hard"),
    EXPERT(8, 7, "Expert")
}

data class Point(val r: Int, val c: Int)

data class ColorDot(val colorId: Int, val start: Point, val end: Point)

data class FlowPath(val colorId: Int, val path: List<Point>)

data class FlowFreeState(
    val grid: List<List<Int>> = emptyList(), // Store colorId. 0 means empty
    val rows: Int = 5,
    val cols: Int = 5,
    val dots: List<ColorDot> = emptyList(),
    val paths: List<FlowPath> = emptyList(),
    val isGameOver: Boolean = false,
    val isWon: Boolean = false
)
