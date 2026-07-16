package com.funkyotc.puzzleverse.arrowescape.model

data class Coordinate(val x: Int, val y: Int) {
    fun move(direction: Direction): Coordinate {
        return Coordinate(x + direction.dx, y + direction.dy)
    }
}

enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    val opposite: Direction
        get() = when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
}

/**
 * An Arrow in the grid.
 * @param id Unique identifier for the arrow
 * @param segments Ordered list of coordinates making up the arrow. The first segment (index 0) is the HEAD.
 * @param color Color index or hex string for rendering.
 */
data class Arrow(
    val id: Int,
    val segments: List<Coordinate>,
    val direction: Direction,
    val color: Int
) {
    val head: Coordinate
        get() = segments.first()

    init {
        require(segments.isNotEmpty()) { "Arrow must have at least one segment" }
    }
}
