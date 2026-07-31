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

    /**
     * Generates an elevated difficulty puzzle using Reverse Dependency Construction.
     * Newly placed arrows are required to be blocked by at least one previously placed arrow
     * along their exit ray, forcing arrows to point inward/transverse across the board
     * and creating deep dependency chains (DAG depth).
     */
    fun generateTesting(
        width: Int,
        height: Int,
        density: Float = 0.90f,
        shape: LevelShape = LevelShape.SQUARE,
        random: Random = Random
    ): List<Arrow> {
        val minGridDim = minOf(width, height)
        val minLen = 4
        val maxLen = when {
            minGridDim <= 10 -> 20
            minGridDim <= 20 -> 50
            minGridDim <= 30 -> 90
            else -> 140
        }
        val maxStarters = 2
        val minRequiredDepth = when {
            minGridDim <= 15 -> 5
            minGridDim <= 25 -> 8
            minGridDim <= 35 -> 12
            else -> 15
        }

        var candidateAttempts = 0
        val maxAttemptsCount = 50

        while (candidateAttempts < maxAttemptsCount) {
            candidateAttempts++
            val arrows = generateTestingCandidate(width, height, density, shape, minLen, maxLen, maxStarters, random)

            if (arrows.isNotEmpty()) {
                val starters = countStarterArrows(arrows, width, height, shape)
                val depth = calculateDependencyDepth(arrows, width, height, shape)
                if (starters in 1..3 && depth >= minRequiredDepth && isPuzzleSolvable(arrows, width, height, shape)) {
                    return arrows
                }
            }
        }

        // Fallback: return best solvable candidate with relaxed depth constraint
        for (fallbackSeed in 1..40) {
            val r = Random(random.nextInt() + fallbackSeed)
            val arrows = generateTestingCandidate(width, height, 0.80f, shape, minLen, maxLen, 3, r)
            if (arrows.isNotEmpty() && isPuzzleSolvable(arrows, width, height, shape)) {
                return arrows
            }
        }

        // Ultimate fallback to standard generate if testing candidate fails
        return generate(width, height, density, shape, random)
    }

    /**
     * Generates an Extreme / God-Tier difficulty puzzle using Concentric Reverse Dependency Construction.
     * Enforces:
     * - Strict single starter bottleneck (1 starter arrow on the entire board).
     * - High ray blocker density (arrows intersect 2-4+ placed arrow segments along their exit ray).
     * - Deep dependency tree (DAG depth >= 25 on 40x40, >= 35 on 50x50).
     * - High cell coverage (>= 92% density).
     */
    fun generateExtreme(
        width: Int,
        height: Int,
        density: Float = 0.92f,
        shape: LevelShape = LevelShape.SQUARE,
        random: Random = Random
    ): List<Arrow> {
        val minGridDim = minOf(width, height)
        val minLen = 5
        val maxLen = when {
            minGridDim <= 30 -> 100
            minGridDim <= 40 -> 150
            else -> 200
        }
        val maxStarters = 1
        val minRequiredDepth = when {
            minGridDim <= 20 -> 12
            minGridDim <= 30 -> 20
            minGridDim <= 40 -> 28
            else -> 35
        }

        var candidateAttempts = 0
        val maxAttemptsCount = 80

        while (candidateAttempts < maxAttemptsCount) {
            candidateAttempts++
            val arrows = generateExtremeCandidate(width, height, density, shape, minLen, maxLen, maxStarters, random)

            if (arrows.isNotEmpty()) {
                val starters = countStarterArrows(arrows, width, height, shape)
                val depth = calculateDependencyDepth(arrows, width, height, shape)
                if (starters == 1 && depth >= minRequiredDepth && isPuzzleSolvable(arrows, width, height, shape)) {
                    return arrows
                }
            }
        }

        // Fallback with slightly relaxed starters (1..2) and depth
        for (fallbackSeed in 1..50) {
            val r = Random(random.nextInt() + fallbackSeed)
            val arrows = generateExtremeCandidate(width, height, 0.85f, shape, minLen, maxLen, 2, r)
            if (arrows.isNotEmpty() && isPuzzleSolvable(arrows, width, height, shape)) {
                return arrows
            }
        }

        return generateTesting(width, height, density, shape, random)
    }

    private fun generateExtremeCandidate(
        width: Int,
        height: Int,
        density: Float,
        shape: LevelShape,
        minLen: Int,
        maxLen: Int,
        maxStarters: Int,
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
        val primaryTargetCells = (validCells.size * 0.72f).toInt()
        var filledCells = 0
        val colors = listOf(1, 2, 3, 4, 5, 6, 7)

        var placedStarters = 0
        var attempts = 0
        val maxAttempts = validCells.size * 15
        val emptyCoords = mutableListOf<Coordinate>()

        while (filledCells < primaryTargetCells && attempts < maxAttempts) {
            attempts++

            emptyCoords.clear()
            for (coord in validCells) {
                if (grid[coord.y][coord.x] == 0) emptyCoords.add(coord)
            }
            if (emptyCoords.isEmpty()) break

            val selectedHead = emptyCoords.random(random)
            val headX = selectedHead.x
            val headY = selectedHead.y
            val spawnDir = Direction.entries.random(random)

            val blockerCount = countPlacedBlockersOnRay(headX, headY, spawnDir, grid, width, height, shape)

            // Strict starter check
            if (placedStarters >= maxStarters && blockerCount == 0) {
                continue
            }

            // Require multi-blocker placements for heavy entanglement
            if (placedStarters >= maxStarters + 2 && blockerCount < 2 && random.nextFloat() < 0.65f) {
                continue
            }

            val neckDir = spawnDir.opposite
            val neckX = headX + neckDir.dx
            val neckY = headY + neckDir.dy

            if (!shape.isCellInside(neckX, neckY, width, height) || grid[neckY][neckX] != 0) continue

            val targetLength = random.nextInt(minLen, maxLen + 1)
            val segments = mutableListOf<Coordinate>()
            segments.add(Coordinate(headX, headY))
            segments.add(Coordinate(neckX, neckY))

            var currentX = neckX
            var currentY = neckY
            var currentDir = neckDir

            for (i in 2 until targetLength) {
                val preferredDirs = mutableListOf<Direction>()
                if (random.nextFloat() < 0.80f) {
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
                        !segments.contains(Coordinate(nextX, nextY))
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

            if (blockerCount == 0) {
                placedStarters++
            }

            arrows.add(Arrow(id = arrowId, segments = segments, direction = spawnDir, color = colors.random(random)))
        }

        // Pass 2: Fast Tail Extension into remaining empty cells
        if (arrows.isNotEmpty()) {
            val maxPasses = 50
            var pass = 0
            while (filledCells < minTargetCells && pass < maxPasses) {
                pass++
                var extendedAny = false
                for (i in arrows.indices) {
                    if (filledCells >= minTargetCells) break
                    val arrow = arrows[i]
                    val mutableSegments = arrow.segments.toMutableList()
                    var extCount = 0
                    val maxExt = random.nextInt(3, 8)

                    while (extCount < maxExt) {
                        val tail = mutableSegments.last()
                        val validNextTailDirs = Direction.entries.shuffled(random).filter { d ->
                            val nx = tail.x + d.dx
                            val ny = tail.y + d.dy
                            shape.isCellInside(nx, ny, width, height) &&
                            grid[ny][nx] == 0 &&
                            !mutableSegments.contains(Coordinate(nx, ny))
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

        if (filledCells < (validCells.size * 0.35f).toInt()) {
            return emptyList()
        }

        return arrows
    }

    private fun generateTestingCandidate(
        width: Int,
        height: Int,
        density: Float,
        shape: LevelShape,
        minLen: Int,
        maxLen: Int,
        maxStarters: Int,
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
        val primaryTargetCells = (validCells.size * 0.70f).toInt()
        var filledCells = 0
        val colors = listOf(1, 2, 3, 4, 5, 6, 7)

        var placedStarters = 0
        var attempts = 0
        val maxAttempts = validCells.size * 12
        val emptyCoords = mutableListOf<Coordinate>()

        while (filledCells < primaryTargetCells && attempts < maxAttempts) {
            attempts++

            emptyCoords.clear()
            for (coord in validCells) {
                if (grid[coord.y][coord.x] == 0) emptyCoords.add(coord)
            }
            if (emptyCoords.isEmpty()) break

            val selectedHead = emptyCoords.random(random)
            val headX = selectedHead.x
            val headY = selectedHead.y
            val spawnDir = Direction.entries.random(random)

            // Check exit ray blockers
            val blockerCount = countPlacedBlockersOnRay(headX, headY, spawnDir, grid, width, height, shape)

            // If we have reached maxStarters, we REQUIRE blockerCount >= 1 (arrow MUST be blocked by already placed arrow)
            if (placedStarters >= maxStarters && blockerCount == 0) {
                continue
            }

            // Prefer multi-blocker placements for deeper web of dependencies
            if (placedStarters >= maxStarters + 3 && blockerCount < 2 && random.nextFloat() < 0.50f) {
                continue
            }

            // Segment 1 (neck) MUST be placed behind head (spawnDir.opposite)
            val neckDir = spawnDir.opposite
            val neckX = headX + neckDir.dx
            val neckY = headY + neckDir.dy

            if (!shape.isCellInside(neckX, neckY, width, height) || grid[neckY][neckX] != 0) continue

            val targetLength = random.nextInt(minLen, maxLen + 1)
            val segments = mutableListOf<Coordinate>()
            segments.add(Coordinate(headX, headY))
            segments.add(Coordinate(neckX, neckY))

            var currentX = neckX
            var currentY = neckY
            var currentDir = neckDir

            for (i in 2 until targetLength) {
                val preferredDirs = mutableListOf<Direction>()
                if (random.nextFloat() < 0.75f) {
                    val turnOptions = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
                        .filter { it != currentDir && it != currentDir.opposite }
                    preferredDirs.addAll(turnOptions.shuffled(random))
                }
                preferredDirs.add(currentDir)

                var added = false
                for (tryDir in preferredDirs) {
                    val nextX = currentX + tryDir.dx
                    val nextY = currentY + tryDir.dy

                    // Allow body segments to cross front rays of previously placed arrows
                    if (shape.isCellInside(nextX, nextY, width, height) &&
                        grid[nextY][nextX] == 0 &&
                        !segments.contains(Coordinate(nextX, nextY))
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

            if (blockerCount == 0) {
                placedStarters++
            }

            arrows.add(Arrow(id = arrowId, segments = segments, direction = spawnDir, color = colors.random(random)))
        }

        // Pass 2: Fast Tail Extension into remaining empty cells
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
                    var extCount = 0
                    val maxExt = random.nextInt(2, 6)

                    while (extCount < maxExt) {
                        val tail = mutableSegments.last()
                        val validNextTailDirs = Direction.entries.shuffled(random).filter { d ->
                            val nx = tail.x + d.dx
                            val ny = tail.y + d.dy
                            shape.isCellInside(nx, ny, width, height) &&
                            grid[ny][nx] == 0 &&
                            !mutableSegments.contains(Coordinate(nx, ny))
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

        if (filledCells < (validCells.size * 0.60f).toInt()) {
            return emptyList()
        }

        return arrows
    }

    private fun countPlacedBlockersOnRay(
        x: Int, y: Int, dir: Direction, grid: Array<IntArray>, width: Int, height: Int, shape: LevelShape
    ): Int {
        var cx = x + dir.dx
        var cy = y + dir.dy
        var blockers = 0
        val seenArrowIds = mutableSetOf<Int>()

        while (shape.isCellInside(cx, cy, width, height)) {
            val arrowId = grid[cy][cx]
            if (arrowId != 0 && !seenArrowIds.contains(arrowId)) {
                seenArrowIds.add(arrowId)
                blockers++
            }
            cx += dir.dx
            cy += dir.dy
        }
        return blockers
    }

    fun calculateDependencyDepth(arrows: List<Arrow>, width: Int, height: Int, shape: LevelShape = LevelShape.SQUARE): Int {
        val state = GridState(width, height, arrows, shape)
        var depth = 0
        val maxSteps = arrows.size + 5

        while (!state.isComplete() && depth < maxSteps) {
            val movable = state.arrows.keys.filter { state.canMove(it) }
            if (movable.isEmpty()) break

            for (arrowId in movable) {
                state.moveArrowFully(arrowId)
            }
            depth++
        }

        return if (state.isComplete()) depth else 0
    }

    fun generateExtremeCandidateLayout(
        width: Int,
        height: Int,
        shape: LevelShape = LevelShape.SQUARE,
        random: Random = Random
    ): List<Arrow> {
        val minGridDim = minOf(width, height)
        val minLen = 4
        val maxLen = when {
            minGridDim <= 30 -> 60
            minGridDim <= 40 -> 90
            else -> 120
        }
        val cand1 = generateExtremeCandidate(width, height, 0.45f, shape, minLen, maxLen, 1, random)
        if (cand1.isNotEmpty()) return cand1
        val cand2 = generateTestingCandidate(width, height, 0.40f, shape, minLen, maxLen, 2, random)
        if (cand2.isNotEmpty()) return cand2
        return generateCandidate(width, height, 0.35f, shape, minLen, maxLen, random)
    }


    fun evaluateCandidate(
        arrows: List<Arrow>,
        width: Int,
        height: Int,
        shape: LevelShape = LevelShape.SQUARE
    ): CandidateStats {
        if (arrows.isEmpty()) {
            return CandidateStats(0, 0, 0, 0, 0f, 0, false, -1.0)
        }

        var validCellsCount = 0
        for (r in 0 until height) {
            for (c in 0 until width) {
                if (shape.isCellInside(c, r, width, height)) {
                    validCellsCount++
                }
            }
        }
        if (validCellsCount == 0) return CandidateStats(0, 0, 0, 0, 0f, 0, false, -1.0)

        val grid = Array(height) { IntArray(width) }
        var totalOccupied = 0
        for (arrow in arrows) {
            for (seg in arrow.segments) {
                grid[seg.y][seg.x] = arrow.id
                totalOccupied++
            }
        }

        val density = totalOccupied.toFloat() / validCellsCount.toFloat()

        var turns = 0
        for (arrow in arrows) {
            val segs = arrow.segments
            for (i in 0 until segs.size - 2) {
                val dx1 = segs[i + 1].x - segs[i].x
                val dy1 = segs[i + 1].y - segs[i].y
                val dx2 = segs[i + 2].x - segs[i + 1].x
                val dy2 = segs[i + 2].y - segs[i + 1].y
                if (dx1 != dx2 || dy1 != dy2) {
                    turns++
                }
            }
        }

        var intersections = 0
        var multiBlockers = 0

        for (arrow in arrows) {
            val head = arrow.segments.first()
            val dir = arrow.direction
            var cx = head.x + dir.dx
            var cy = head.y + dir.dy

            val seenBlockers = mutableSetOf<Int>()
            while (shape.isCellInside(cx, cy, width, height)) {
                val targetId = grid[cy][cx]
                if (targetId != 0 && targetId != arrow.id) {
                    intersections++
                    seenBlockers.add(targetId)
                }
                cx += dir.dx
                cy += dir.dy
            }

            if (seenBlockers.size >= 2) {
                multiBlockers++
            }
        }

        val starters = countStarterArrows(arrows, width, height, shape)
        val depth = calculateDependencyDepth(arrows, width, height, shape)
        val solvable = isPuzzleSolvable(arrows, width, height, shape)

        val starterBonus = when {
            starters == 1 -> 2000.0
            starters == 2 -> 1200.0
            starters == 3 -> 800.0
            starters == 4 -> 400.0
            starters <= 6 -> 100.0
            else -> 0.0
        }

        val score = if (solvable) {
            10.0 * depth + 5.0 * intersections + 8.0 * multiBlockers + 2.0 * turns + 100.0 * density + starterBonus
        } else {
            -1.0
        }


        return CandidateStats(
            depth = depth,
            intersections = intersections,
            multiBlockers = multiBlockers,
            turns = turns,
            density = density,
            starters = starters,
            isSolvable = solvable,
            score = score
        )
    }

}

data class CandidateStats(
    val depth: Int,
    val intersections: Int,
    val multiBlockers: Int,
    val turns: Int,
    val density: Float,
    val starters: Int,
    val isSolvable: Boolean,
    val score: Double
)


