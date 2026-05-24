package com.funkyotc.puzzleverse.blockpuzzle.data

import java.util.UUID

data class Point(val r: Int, val c: Int)

data class BoxShape(
    val id: String = UUID.randomUUID().toString(),
    val blocks: List<Point> // Relative coordinates where (0,0) is top-left
)

enum class BoxType { EMPTY, FILLED }

data class BlockPuzzleState(
    val grid: List<List<BoxType>> = List(10) { List(10) { BoxType.EMPTY } },
    val tray: List<BoxShape?> = listOf(null, null, null),
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val recentlyClearedRows: List<Int> = emptyList(),
    val recentlyClearedCols: List<Int> = emptyList(),
    val flashId: Long = 0L
)

object ShapeLibrary {
    val allShapes = listOf(
        // Single block
        BoxShape(blocks = listOf(Point(0,0))),
        // 2x1 horizontal
        BoxShape(blocks = listOf(Point(0,0), Point(0,1))),
        // 1x2 vertical
        BoxShape(blocks = listOf(Point(0,0), Point(1,0))),
        // 3x1 horizontal
        BoxShape(blocks = listOf(Point(0,0), Point(0,1), Point(0,2))),
        // 1x3 vertical
        BoxShape(blocks = listOf(Point(0,0), Point(1,0), Point(2,0))),
        // 2x2 square
        BoxShape(blocks = listOf(Point(0,0), Point(0,1), Point(1,0), Point(1,1))),
        // L shape (3x3 corner)
        BoxShape(blocks = listOf(Point(0,0), Point(1,0), Point(2,0), Point(2,1), Point(2,2))),
        // Tetris T-shape
        BoxShape(blocks = listOf(Point(0,1), Point(1,0), Point(1,1), Point(1,2))),
        // Tetris L-shape
        BoxShape(blocks = listOf(Point(0,0), Point(1,0), Point(2,0), Point(2,1))),
        // Tetris J-shape
        BoxShape(blocks = listOf(Point(0,1), Point(1,1), Point(2,0), Point(2,1))),
        // Tetris S-shape
        BoxShape(blocks = listOf(Point(0,1), Point(0,2), Point(1,0), Point(1,1))),
        // Tetris Z-shape
        BoxShape(blocks = listOf(Point(0,0), Point(0,1), Point(1,1), Point(1,2))),
        // Tetris I-shape horizontal
        BoxShape(blocks = listOf(Point(0,0), Point(0,1), Point(0,2), Point(0,3))),
        // Tetris I-shape vertical
        BoxShape(blocks = listOf(Point(0,0), Point(1,0), Point(2,0), Point(3,0)))
    )

    fun getRandomShape(): BoxShape {
        val s = allShapes.random()
        return s.copy(id = UUID.randomUUID().toString())
    }
}
