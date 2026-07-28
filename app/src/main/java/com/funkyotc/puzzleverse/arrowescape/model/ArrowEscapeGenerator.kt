package com.funkyotc.puzzleverse.arrowescape.model

import kotlin.random.Random

class ArrowEscapeGenerator {

    /**
     * Generates a guaranteed solvable and challenging puzzle using Reverse Engineering.
     * Arrows block each other's exit paths, creating an interlocking puzzle structure
     * where only a small number of starter arrows can be freed initially.
     */
    fun generate(width: Int, height: Int, density: Float, random: Random = Random): List<Arrow> {
        val minGridDim = minOf(width, height)
        val minLen = if (minGridDim <= 10) 4 else (minGridDim * 0.35).toInt().coerceAtLeast(5)
        val maxLen = if (minGridDim <= 10) 12 else (minGridDim * 0.8).toInt().coerceAtLeast(8)
        val maxStarterArrows = if (minGridDim <= 10) 3 else 5

        var candidateAttempts = 0
        while (candidateAttempts < 100) {
            candidateAttempts++
            val arrows = generateCandidate(width, height, density, minLen, maxLen, random)
            
            if (arrows.isNotEmpty()) {
                val starters = countStarterArrows(arrows, width, height)
                if (starters in 1..maxStarterArrows && isPuzzleSolvable(arrows, width, height)) {
                    return arrows
                }
            }
        }

        // Fallback: return best generated candidate that is solvable
        for (fallbackSeed in 1..50) {
            val r = Random(random.nextInt() + fallbackSeed)
            val arrows = generateCandidate(width, height, density * 0.9f, minLen, maxLen, r)
            if (arrows.isNotEmpty() && isPuzzleSolvable(arrows, width, height)) {
                return arrows
            }
        }

        return emptyList()
    }

    private fun generateCandidate(
        width: Int,
        height: Int,
        density: Float,
        minLen: Int,
        maxLen: Int,
        random: Random
    ): List<Arrow> {
        val grid = Array(height) { IntArray(width) }
        val arrows = mutableListOf<Arrow>()
        var nextId = 1

        val targetCells = (width * height * density).toInt()
        var filledCells = 0
        val colors = listOf(1, 2, 3, 4, 5, 6, 7)

        var attempts = 0
        val maxAttempts = targetCells * 30

        while (filledCells < targetCells && attempts < maxAttempts) {
            attempts++

            val spawnDir = Direction.entries.random(random)

            // Spawn head on grid edge or interior
            val headX: Int
            val headY: Int
            when (spawnDir) {
                Direction.UP -> {
                    headX = random.nextInt(width)
                    headY = random.nextInt(height / 2 + 1)
                }
                Direction.DOWN -> {
                    headX = random.nextInt(width)
                    headY = random.nextInt(height / 2, height)
                }
                Direction.LEFT -> {
                    headX = random.nextInt(width / 2 + 1)
                    headY = random.nextInt(height)
                }
                Direction.RIGHT -> {
                    headX = random.nextInt(width / 2, width)
                    headY = random.nextInt(height)
                }
            }

            if (grid[headY][headX] != 0) continue

            val targetLength = random.nextInt(minLen, maxLen + 1)
            val segments = mutableListOf<Coordinate>()
            segments.add(Coordinate(headX, headY))

            // Segment 1 MUST be placed directly behind head along spawnDir.opposite so neck line aligns straight
            val neckDir = spawnDir.opposite
            val neckX = headX + neckDir.dx
            val neckY = headY + neckDir.dy

            if (neckX !in 0 until width || neckY !in 0 until height || grid[neckY][neckX] != 0) continue
            segments.add(Coordinate(neckX, neckY))

            var currentX = neckX
            var currentY = neckY
            var currentDir = neckDir

            // Extend subsequent tail segments (segment 2 onwards) with serpentine turns
            for (i in 2 until targetLength) {
                val preferredDirs = mutableListOf<Direction>()
                if (random.nextFloat() < 0.55f) {
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
                        !isOnFrontHeadRay(nextX, nextY, headX, headY, spawnDir, width, height)
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

            val arrowId = nextId++
            for (seg in segments) {
                grid[seg.y][seg.x] = arrowId
                filledCells++
            }

            arrows.add(Arrow(id = arrowId, segments = segments, direction = spawnDir, color = colors.random(random)))
        }

        return arrows
    }

    private fun isOnFrontHeadRay(x: Int, y: Int, headX: Int, headY: Int, dir: Direction, width: Int, height: Int): Boolean {
        var cx = headX + dir.dx
        var cy = headY + dir.dy
        while (cx in 0 until width && cy in 0 until height) {
            if (cx == x && cy == y) return true
            cx += dir.dx
            cy += dir.dy
        }
        return false
    }

    private fun countStarterArrows(arrows: List<Arrow>, width: Int, height: Int): Int {
        val state = GridState(width, height, arrows)
        return state.arrows.keys.count { state.canMove(it) }
    }

    fun isPuzzleSolvable(arrows: List<Arrow>, width: Int, height: Int): Boolean {
        val state = GridState(width, height, arrows)
        var progress = true
        while (progress && !state.isComplete()) {
            progress = false
            for (arrowId in state.arrows.keys.toList()) {
                if (state.canMove(arrowId)) {
                    state.moveArrowFully(arrowId)
                    progress = true
                    break
                }
            }
        }
        return state.isComplete()
    }
}
