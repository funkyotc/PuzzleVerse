package com.funkyotc.puzzleverse.arrowescape.model

import kotlin.random.Random

class ArrowEscapeGenerator {

    /**
     * Generates a guaranteed 100% solvable puzzle using Reverse Engineering.
     * Starts with an empty grid, spawns arrows backward one by one.
     */
    fun generate(width: Int, height: Int, density: Float, random: Random = Random): List<Arrow> {
        val grid = Array(height) { IntArray(width) }
        val arrows = mutableListOf<Arrow>()
        var nextId = 1
        
        val targetCells = (width * height * density).toInt()
        var filledCells = 0

        val colors = listOf(1, 2, 3, 4, 5, 6, 7)

        var attempts = 0
        val maxAttempts = targetCells * 30

        val minGridDim = minOf(width, height)
        val minLen = if (minGridDim <= 10) 4 else (minGridDim * 0.35).toInt().coerceAtLeast(5)
        val maxLen = if (minGridDim <= 10) 12 else (minGridDim * 0.8).toInt().coerceAtLeast(8)

        while (filledCells < targetCells && attempts < maxAttempts) {
            attempts++
            
            // Pick a random exit direction
            val spawnDir = Direction.entries.random(random)

            // Spawn head on grid edge corresponding to exit direction
            val headX: Int
            val headY: Int
            when (spawnDir) {
                Direction.UP -> {
                    headX = random.nextInt(width)
                    headY = 0
                }
                Direction.DOWN -> {
                    headX = random.nextInt(width)
                    headY = height - 1
                }
                Direction.LEFT -> {
                    headX = 0
                    headY = random.nextInt(height)
                }
                Direction.RIGHT -> {
                    headX = width - 1
                    headY = random.nextInt(height)
                }
            }

            // Head must be empty and exit ray from head in spawnDir must be clear
            if (grid[headY][headX] != 0) continue
            if (!isExitRayClear(headX, headY, spawnDir, grid, width, height)) continue

            val targetLength = random.nextInt(minLen, maxLen + 1)
            val segments = mutableListOf<Coordinate>()
            
            // Add Head (segment 0)
            segments.add(Coordinate(headX, headY))

            // Segment 1 MUST be placed directly behind head along spawnDir.opposite
            val neckDir = spawnDir.opposite
            val neckX = headX + neckDir.dx
            val neckY = headY + neckDir.dy

            if (neckX !in 0 until width || neckY !in 0 until height || grid[neckY][neckX] != 0) continue
            if (!isExitRayClear(neckX, neckY, spawnDir, grid, width, height)) continue

            segments.add(Coordinate(neckX, neckY))

            var currentX = neckX
            var currentY = neckY
            var currentDir = neckDir

            // Extend subsequent tail segments (segment 2 onwards)
            for (i in 2 until targetLength) {
                val preferredDirs = mutableListOf<Direction>()
                if (random.nextFloat() < 0.50f) {
                    val turnOptions = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
                        .filter { it != currentDir && it != currentDir.opposite }
                    preferredDirs.addAll(turnOptions.shuffled(random))
                }
                preferredDirs.add(currentDir)

                var added = false
                for (tryDir in preferredDirs) {
                    val nextX = currentX + tryDir.dx
                    val nextY = currentY + tryDir.dy

                    if (nextX in 0 until width && nextY in 0 until height &&
                        grid[nextY][nextX] == 0 &&
                        !segments.contains(Coordinate(nextX, nextY)) &&
                        isExitRayClear(nextX, nextY, spawnDir, grid, width, height)
                    ) {
                        currentX = nextX
                        currentY = nextY
                        currentDir = tryDir
                        segments.add(Coordinate(currentX, currentY))
                        added = true
                        break
                    }
                }

                if (!added) break
            }

            if (segments.size < minLen) continue

            // Assign arrow to grid
            val arrowId = nextId++
            for (seg in segments) {
                grid[seg.y][seg.x] = arrowId
                filledCells++
            }

            arrows.add(Arrow(id = arrowId, segments = segments, direction = spawnDir, color = colors.random(random)))
        }

        return arrows
    }

    /**
     * Checks if the exit path from (x, y) along `dir` to the grid boundary is clear of other arrows.
     */
    private fun isExitRayClear(x: Int, y: Int, dir: Direction, grid: Array<IntArray>, width: Int, height: Int): Boolean {
        var cx = x + dir.dx
        var cy = y + dir.dy
        while (cx in 0 until width && cy in 0 until height) {
            if (grid[cy][cx] != 0) return false
            cx += dir.dx
            cy += dir.dy
        }
        return true
    }
}
