package com.funkyotc.puzzleverse.arrowescape.model

class GridState(
    val width: Int,
    val height: Int,
    initialArrows: List<Arrow>
) {
    private val grid = Array(height) { IntArray(width) }
    val arrows = initialArrows.associateBy { it.id }.toMutableMap()

    init {
        for (arrow in initialArrows) {
            for (segment in arrow.segments) {
                grid[segment.y][segment.x] = arrow.id
            }
        }
    }

    fun canMove(arrowId: Int): Boolean {
        val arrow = arrows[arrowId] ?: return false
        val nextPos = arrow.head.move(arrow.direction)

        // Can move if it goes off screen
        if (nextPos.x !in 0 until width || nextPos.y !in 0 until height) {
            return true
        }

        // Can move if the next cell is empty, or occupied by itself (which shouldn't happen right in front of the head but just in case)
        val occupant = grid[nextPos.y][nextPos.x]
        return occupant == 0 || occupant == arrowId
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
        grid[tail.y][tail.x] = 0

        // Create new segments list: add nextPos as new head, remove old tail
        val newSegments = mutableListOf(nextPos)
        newSegments.addAll(arrow.segments.dropLast(1))

        // Update grid for new head (if on screen)
        if (nextPos.x in 0 until width && nextPos.y in 0 until height) {
            grid[nextPos.y][nextPos.x] = arrowId
        }

        // Check if the entire arrow is now off screen
        val isOffScreen = newSegments.all { it.x !in 0 until width || it.y !in 0 until height }

        if (isOffScreen) {
            arrows.remove(arrowId)
        } else {
            arrows[arrowId] = arrow.copy(segments = newSegments)
        }

        return true
    }

    /**
     * Attempts to move an arrow until it's completely off screen.
     * In a real implementation, this might happen tick by tick in the UI.
     * This function is mostly for logical validation or quick solving.
     */
    fun moveArrowFully(arrowId: Int): Boolean {
        if (!canMove(arrowId)) return false
        while (arrows.containsKey(arrowId)) {
            if (!moveArrow(arrowId)) {
                // Should not happen if paths are guaranteed, but good safeguard
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
