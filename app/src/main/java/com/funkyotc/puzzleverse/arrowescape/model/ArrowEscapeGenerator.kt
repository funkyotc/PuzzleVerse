package com.funkyotc.puzzleverse.arrowescape.model

import kotlin.random.Random

class ArrowEscapeGenerator {

    /**
     * Generates a puzzle using the Reverse Engineering Method.
     * Starts with an empty grid, spawns arrows, and pushes them backward.
     */
    fun generate(width: Int, height: Int, density: Float, random: Random = Random): List<Arrow> {
        val grid = Array(height) { IntArray(width) { 0 } }
        val arrows = mutableListOf<Arrow>()
        var nextId = 1
        
        // Target filled cells based on density
        val targetCells = (width * height * density).toInt()
        var filledCells = 0

        val colors = listOf(1, 2, 3, 4, 5, 6, 7) // Simple color indices

        // To prevent infinite loops, we set a max attempts counter
        var attempts = 0
        val maxAttempts = targetCells * 10

        while (filledCells < targetCells && attempts < maxAttempts) {
            attempts++
            
            // Pick a random edge to spawn an arrow
            val edge = random.nextInt(4)
            val spawnDir = when (edge) {
                0 -> Direction.UP    // Spawns at bottom edge, looking UP, will be pushed DOWN
                1 -> Direction.DOWN  // Spawns at top edge, looking DOWN, will be pushed UP
                2 -> Direction.LEFT  // Spawns at right edge, looking LEFT, will be pushed RIGHT
                else -> Direction.RIGHT // Spawns at left edge, looking RIGHT, will be pushed LEFT
            }

            // The exit direction of the arrow is spawnDir.
            // When we push it BACKWARD, it moves in spawnDir.opposite.
            
            val headX: Int
            val headY: Int
            when (spawnDir) {
                Direction.UP -> {
                    headX = random.nextInt(width)
                    headY = height - 1
                }
                Direction.DOWN -> {
                    headX = random.nextInt(width)
                    headY = 0
                }
                Direction.LEFT -> {
                    headX = width - 1
                    headY = random.nextInt(height)
                }
                Direction.RIGHT -> {
                    headX = 0
                    headY = random.nextInt(height)
                }
            }

            // Check if spawn point is empty
            if (grid[headY][headX] != 0) continue

            // Determine length of the arrow (3 to 12 segments for longer curling tails)
            val length = random.nextInt(3, 13)
            
            val segments = mutableListOf<Coordinate>()
            var currentX = headX
            var currentY = headY
            var currentDir = spawnDir.opposite // The direction we push the tail
            
            var validArrow = true
            segments.add(Coordinate(currentX, currentY))

            for (i in 1 until length) {
                // Higher turn probability for more curling (40% chance)
                if (random.nextFloat() < 0.4f) {
                    val turnOptions = listOf(
                        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
                    ).filter { it != currentDir && it != currentDir.opposite }
                    currentDir = turnOptions.random(random)
                }

                val nextX = currentX + currentDir.dx
                val nextY = currentY + currentDir.dy

                // Check bounds
                if (nextX !in 0 until width || nextY !in 0 until height) {
                    break // Stop growing if we hit a wall
                }

                // Check collision
                if (grid[nextY][nextX] != 0) {
                    break // Stop growing if we hit another arrow
                }
                
                // Also check if we cross ourselves (shouldn't happen with simple logic, but just in case)
                if (segments.contains(Coordinate(nextX, nextY))) {
                    break
                }

                currentX = nextX
                currentY = nextY
                segments.add(Coordinate(currentX, currentY))
            }

            // Assign to grid
            val arrowId = nextId++
            for (seg in segments) {
                grid[seg.y][seg.x] = arrowId
                filledCells++
            }
            
            arrows.add(Arrow(id = arrowId, segments = segments, direction = spawnDir, color = colors.random(random)))
        }

        return arrows
    }
}
