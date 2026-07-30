package com.funkyotc.puzzleverse.arrowescape.model

import kotlin.random.Random

class ArrowEscapeGenerator {

    /**
     * Generates a guaranteed solvable and challenging puzzle using Reverse Engineering.
     * Arrows fill >= 95% of valid grid cells within the specified LevelShape.
     * Gap filling is performed efficiently by extending existing arrow tails.
     */
    fun generate(
        width: Int,
        height: Int,
        density: Float = 0.95f,
        shape: LevelShape = LevelShape.SQUARE,
        random: Random = Random
    ): List<Arrow> {
        val minGridDim = minOf(width, height)
        val minLen = 3
        val maxLen = when {
            minGridDim <= 10 -> 18
            minGridDim <= 20 -> 45
            minGridDim <= 30 -> 80
            minGridDim <= 40 -> 120
            else -> 160
        }
        val maxStarterArrows = when {
            minGridDim <= 10 -> 4
            minGridDim <= 20 -> 6
            minGridDim <= 30 -> 8
            minGridDim <= 40 -> 10
            else -> 12
        }

        var candidateAttempts = 0
        val maxAttemptsCount = 30

        while (candidateAttempts < maxAttemptsCount) {
            candidateAttempts++
            val arrows = generateCandidate(width, height, density, shape, minLen, maxLen, random)
            
            if (arrows.isNotEmpty()) {
                val starters = countStarterArrows(arrows, width, height, shape)
                val starterCap = maxStarterArrows + 15
                if (starters in 1..starterCap && isPuzzleSolvable(arrows, width, height, shape)) {
                    return arrows
                }
            }
        }

        // Fallback: return best solvable candidate
        for (fallbackSeed in 1..20) {
            val r = Random(random.nextInt() + fallbackSeed)
            val arrows = generateCandidate(width, height, 0.85f, shape, minLen, maxLen, r)
            if (arrows.isNotEmpty() && isPuzzleSolvable(arrows, width, height, shape)) {
                return arrows
            }
        }

        return emptyList()
    }

    private fun generateCandidate(
        width: Int,
        height: Int,
        density: Float,
        shape: LevelShape,
        minLen: Int,
        maxLen: Int,
        random: Random
    ): List<Arrow> {
        val grid = Array(height) { IntArray(width) }
        val arrows = mutableListOf<Arrow>()
        var nextId = 1

        val validCells = mutableListOf<Coordinate>()
        for (r in 0 until height) {
            for (c in 0 until width) {
                if (shape.isCellInside(c, r, width, height)) {
                    validCells.add(Coordinate(c, r))
                }
            }
        }

        if (validCells.isEmpty()) return emptyList()

        val minTargetCells = (validCells.size * density).toInt()
        val primaryTargetCells = (validCells.size * 0.60f).toInt()
        var filledCells = 0
        val colors = listOf(1, 2, 3, 4, 5, 6, 7)

        var attempts = 0
        val maxAttempts = validCells.size * 4
        val emptyCoords = mutableListOf<Coordinate>()

        // Pass 1: Primary Arrow Placement (place long serpentine arrows up to ~60% cell coverage)
        while (filledCells < primaryTargetCells && attempts < maxAttempts) {
            attempts++

            val headX: Int
            val headY: Int
            val spawnDir = Direction.entries.random(random)

            if (attempts > validCells.size || filledCells > primaryTargetCells * 0.5) {
                emptyCoords.clear()
                for (coord in validCells) {
                    if (grid[coord.y][coord.x] == 0) emptyCoords.add(coord)
                }
                if (emptyCoords.isEmpty()) break
                val selected = emptyCoords.random(random)
                headX = selected.x
                headY = selected.y
            } else {
                val candidateHead = validCells.random(random)
                headX = candidateHead.x
                headY = candidateHead.y
                if (grid[headY][headX] != 0) continue
            }

            // Head exit ray must be clear of previously placed arrows inside shape
            if (!isExitRayClearOfPlacedArrows(headX, headY, spawnDir, grid, width, height, shape)) continue

            val targetLength = random.nextInt(minLen, maxLen + 1)
            val segments = mutableListOf<Coordinate>()
            segments.add(Coordinate(headX, headY))

            // Segment 1 MUST be placed directly behind head along spawnDir.opposite so neck line aligns straight
            val neckDir = spawnDir.opposite
            val neckX = headX + neckDir.dx
            val neckY = headY + neckDir.dy

            if (!shape.isCellInside(neckX, neckY, width, height) || grid[neckY][neckX] != 0) continue
            segments.add(Coordinate(neckX, neckY))

            var currentX = neckX
            var currentY = neckY
            var currentDir = neckDir

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

                    if (shape.isCellInside(nextX, nextY, width, height) &&
                        grid[nextY][nextX] == 0 &&
                        !segments.contains(Coordinate(nextX, nextY)) &&
                        !isOnFrontHeadRay(nextX, nextY, headX, headY, spawnDir, width, height, shape)
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

        // Pass 2: Fast Tail Extension (extend existing placed arrow ends by 2-5 segments into adjacent empty cells)
        if (arrows.isNotEmpty()) {
            val maxPasses = 40
            var pass = 0

            while (filledCells < minTargetCells && pass < maxPasses) {
                pass++
                var extendedAny = false

                for (i in arrows.indices) {
                    if (filledCells >= minTargetCells) break
                    val arrow = arrows[i]

                    val mutableSegments = arrow.segments.toMutableList()
                    val headX = mutableSegments.first().x
                    val headY = mutableSegments.first().y
                    val spawnDir = arrow.direction

                    var extCount = 0
                    val maxExt = random.nextInt(2, 6) // Extend tail by 2-5 segments

                    while (extCount < maxExt) {
                        val tail = mutableSegments.last()
                        val validNextTailDirs = Direction.entries.shuffled(random).filter { d ->
                            val nx = tail.x + d.dx
                            val ny = tail.y + d.dy
                            shape.isCellInside(nx, ny, width, height) &&
                            grid[ny][nx] == 0 &&
                            !mutableSegments.contains(Coordinate(nx, ny)) &&
                            !isOnFrontHeadRay(nx, ny, headX, headY, spawnDir, width, height, shape)
                        }

                        if (validNextTailDirs.isEmpty()) break

                        val chosenDir = validNextTailDirs.first()
                        val newTailX = tail.x + chosenDir.dx
                        val newTailY = tail.y + chosenDir.dy

                        mutableSegments.add(Coordinate(newTailX, newTailY))
                        grid[newTailY][newTailX] = arrow.id
                        filledCells++
                        extCount++
                        extendedAny = true
                    }

                    if (extCount > 0) {
                        arrows[i] = arrow.copy(segments = mutableSegments)
                    }
                }

                if (!extendedAny) break
            }
        }

        if (filledCells < (validCells.size * 0.70f).toInt()) {
            return emptyList()
        }

        return arrows
    }

    private fun isExitRayClearOfPlacedArrows(
        x: Int, y: Int, dir: Direction, grid: Array<IntArray>, width: Int, height: Int, shape: LevelShape
    ): Boolean {
        var cx = x + dir.dx
        var cy = y + dir.dy
        while (shape.isCellInside(cx, cy, width, height)) {
            if (grid[cy][cx] != 0) return false
            cx += dir.dx
            cy += dir.dy
        }
        return true
    }

    private fun isOnFrontHeadRay(
        x: Int, y: Int, headX: Int, headY: Int, dir: Direction, width: Int, height: Int, shape: LevelShape
    ): Boolean {
        var cx = headX + dir.dx
        var cy = headY + dir.dy
        while (shape.isCellInside(cx, cy, width, height)) {
            if (cx == x && cy == y) return true
            cx += dir.dx
            cy += dir.dy
        }
        return false
    }

    private fun countStarterArrows(arrows: List<Arrow>, width: Int, height: Int, shape: LevelShape): Int {
        val state = GridState(width, height, arrows, shape)
        return state.arrows.keys.count { state.canMove(it) }
    }

    fun isPuzzleSolvable(arrows: List<Arrow>, width: Int, height: Int, shape: LevelShape = LevelShape.SQUARE): Boolean {
        val state = GridState(width, height, arrows, shape)
        var steps = 0
        val maxSteps = arrows.size * 2
        while (!state.isComplete() && steps < maxSteps) {
            steps++
            var moved = false
            for (arrowId in state.arrows.keys.toList()) {
                if (state.canMove(arrowId)) {
                    state.moveArrowFully(arrowId)
                    moved = true
                    break
                }
            }
            if (!moved) break
        }
        return state.isComplete()
    }
}
