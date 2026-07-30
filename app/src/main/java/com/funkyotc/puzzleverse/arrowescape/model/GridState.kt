package com.funkyotc.puzzleverse.arrowescape.model

class GridState(
    val width: Int,
    val height: Int,
    initialArrows: List<Arrow>,
    val shape: LevelShape = LevelShape.SQUARE
) {
    private val grid = Array(height) { IntArray(width) }
    val arrows = initialArrows.associateBy { it.id }.toMutableMap()

    init {
        for (arrow in initialArrows) {
            for (segment in arrow.segments) {
                if (isCellOnBoard(segment.x, segment.y)) {
                    grid[segment.y][segment.x] = arrow.id
                }
            }
        }
    }

    fun isCellOnBoard(x: Int, y: Int): Boolean {
        if (x !in 0 until width || y !in 0 until height) return false
        return shape.isCellInside(x, y, width, height)
    }

    fun canMove(arrowId: Int): Boolean {
        val arrow = arrows[arrowId] ?: return false
        var checkPos = arrow.head.move(arrow.direction)
        while (isCellOnBoard(checkPos.x, checkPos.y)) {
            val occupant = grid[checkPos.y][checkPos.x]
            if (occupant != 0) {
                return false
            }
            checkPos = checkPos.move(arrow.direction)
        }
        return true
    }

    /**
     * Moves the arrow one step. 
     * Returns true if moved, false if blocked.
     */
    fun moveArrow(arrowId: Int): Boolean {
        if (!canMove(arrowId)) return false

        val arrow = arrows[arrowId] ?: return false
        val nextPos = arrow.head.move(arrow.direction)

        // Clear the tail from the grid
        val tail = arrow.segments.last()
        if (isCellOnBoard(tail.x, tail.y)) {
            grid[tail.y][tail.x] = 0
        }

        // Create new segments list: add nextPos as new head, remove old tail
        val newSegments = mutableListOf(nextPos)
        newSegments.addAll(arrow.segments.dropLast(1))

        // Update grid for new head (if on screen)
        if (isCellOnBoard(nextPos.x, nextPos.y)) {
            grid[nextPos.y][nextPos.x] = arrowId
        }

        // Check if the entire arrow is now off screen / off board
        val isOffScreen = newSegments.all { !isCellOnBoard(it.x, it.y) }

        if (isOffScreen) {
            arrows.remove(arrowId)
        } else {
            arrows[arrowId] = arrow.copy(segments = newSegments)
        }

        return true
    }

    /**
     * Attempts to move an arrow until it's completely off screen.
     */
    fun moveArrowFully(arrowId: Int): Boolean {
        if (!canMove(arrowId)) return false
        while (arrows.containsKey(arrowId)) {
            if (!moveArrow(arrowId)) {
                break
            }
        }
        return true
    }

    fun isComplete(): Boolean {
        return arrows.isEmpty()
    }
    
    fun getGridArray(): Array<IntArray> = grid
}
