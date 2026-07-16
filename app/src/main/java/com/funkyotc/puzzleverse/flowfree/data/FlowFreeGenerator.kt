package com.funkyotc.puzzleverse.flowfree.data

import kotlin.random.Random

enum class FlowDifficulty(val gridSize: Int, val numColors: Int, val label: String) {
    EASY(5, 4, "Easy"),
    MEDIUM(6, 5, "Medium"),
    HARD(7, 6, "Hard"),
    EXPERT(8, 7, "Expert")
}

/**
 * Generates valid Flow Free puzzles using a Hamiltonian-cut approach:
 * 1. Build a Hamiltonian path that visits every cell exactly once.
 * 2. Cut that path into `numColors` disjoint segments (each length >= 2).
 * 3. The first and last cell of each segment become the color's start/end dots.
 * 4. Verify the puzzle has exactly one solution via a threaded backtracking solver.
 *
 * This guarantees full-grid coverage and minimal (detour-free) paths by construction,
 * matching the behavior of the Flow Free app.
 */
object FlowFreeGenerator {

    data class GeneratedPuzzle(
        val size: Int,
        val dots: List<ColorDot>,
        val difficulty: FlowDifficulty
    )

    private val DIRECTIONS = arrayOf(
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(0, -1), intArrayOf(0, 1)
    )

    private const val SOLVER_TIMEOUT_MS = 10_000

    fun generate(difficulty: FlowDifficulty, random: Random = Random): GeneratedPuzzle {
        val size = difficulty.gridSize
        val numColors = difficulty.numColors

        while (true) {
            val dots = generateRandomFilledGrid(size, numColors, random) ?: continue
            val solutions = countSolutions(size, dots)
            if (solutions == 1) {
                val shuffled = shuffleColors(dots, numColors, random)
                val finalDots = applyTransformations(shuffled, size, random)
                return GeneratedPuzzle(size, finalDots, difficulty)
            }
        }
    }

    /**
     * Builds a Hamiltonian path and cuts it into `numColors` segments, returning the
     * resulting dots. Returns null on any failure (no Hamiltonian path, invalid cut,
     * or a segment shorter than 2) so the caller can retry from scratch.
     */
    private fun generateRandomFilledGrid(size: Int, numColors: Int, random: Random): List<ColorDot>? {
        val path = buildHamiltonianPath(size, random) ?: return null
        val cuts = cutIndices(path.size, numColors, random) ?: return null

        val dots = mutableListOf<ColorDot>()
        for (i in 0 until numColors) {
            val startCell = path[cuts[i]]
            val endCell = path[cuts[i + 1] - 1]
            dots.add(ColorDot(i + 1, startCell, endCell))
        }
        return dots
    }

    /**
     * Builds a single Hamiltonian path over the size x size grid using randomized
     * Warnsdorff-ordered DFS backtracking. Returns the ordered list of cells, or null
     * if no Hamiltonian path is found within the attempt/backtrack budget.
     */
    private fun buildHamiltonianPath(size: Int, random: Random): List<Point>? {
        val total = size * size
        val visited = Array(size) { BooleanArray(size) }
        val path = mutableListOf<Point>()

        // Fixed start point keeps searches deterministic per (size, seed) while still
        // allowing randomized neighbor ordering to explore distinct tours.
        val start = Point(0, 0)
        path.add(start)
        visited[start.r][start.c] = true

        if (hamiltonianDfs(start, total, visited, path, size, random)) {
            return path.toList()
        }
        return null
    }

    private fun hamiltonianDfs(
        current: Point,
        remaining: Int,
        visited: Array<BooleanArray>,
        path: MutableList<Point>,
        size: Int,
        random: Random
    ): Boolean {
        if (remaining == 1) return true

        // Warnsdorff heuristic: try neighbors with the fewest onward moves first,
        // randomized within equal-degree ties to vary the produced tour.
        val neighbors = DIRECTIONS.mapNotNull { dir ->
            val nr = current.r + dir[0]
            val nc = current.c + dir[1]
            if (nr in 0 until size && nc in 0 until size && !visited[nr][nc]) {
                Point(nr, nc)
            } else null
        }

        val ordered = neighbors
            .map { n -> n to countFreeNeighbors(n, visited, size) }
            .sortedWith(compareBy({ it.second }, { random.nextInt() }))
            .map { it.first }

        for (next in ordered) {
            visited[next.r][next.c] = true
            path.add(next)
            if (hamiltonianDfs(next, remaining - 1, visited, path, size, random)) return true
            path.removeAt(path.lastIndex)
            visited[next.r][next.c] = false
        }
        return false
    }

    private fun countFreeNeighbors(p: Point, visited: Array<BooleanArray>, size: Int): Int {
        var count = 0
        for (dir in DIRECTIONS) {
            val nr = p.r + dir[0]
            val nc = p.c + dir[1]
            if (nr in 0 until size && nc in 0 until size && !visited[nr][nc]) count++
        }
        return count
    }

    /**
     * Chooses `numColors - 1` internal cut points on a path of length `n` such that
     * every resulting segment has length >= 2. Returns the `numColors + 1` cumulative
     * indices [0, c1, c2, ..., n] inclusive, or null if no valid split exists.
     */
    private fun cutIndices(n: Int, numColors: Int, random: Random): IntArray? {
        if (n < numColors * 2) return null

        // Segment lengths (each >= 2) summing to n. Build greedily then shuffle.
        val lengths = IntArray(numColors) { 2 }
        var remaining = n - numColors * 2
        while (remaining > 0) {
            lengths[random.nextInt(numColors)]++
            remaining--
        }
        lengths.shuffle(random)

        val cuts = IntArray(numColors + 1)
        cuts[0] = 0
        for (i in 0 until numColors) {
            cuts[i + 1] = cuts[i] + lengths[i]
        }
        return cuts
    }

    /**
     * Counts the number of solutions via a threaded backtracking solver with a
     * [SOLVER_TIMEOUT_MS] budget. Returns -1 on timeout so the caller treats the
     * attempt as failed and retries.
     */
    private fun countSolutions(size: Int, dots: List<ColorDot>): Int {
        val solver = Solver(dots, size)
        var result: Int? = null
        val thread = Thread {
            result = solver.countSolutions(maxCount = 2)
        }
        thread.start()
        thread.join(SOLVER_TIMEOUT_MS.toLong())
        if (thread.isAlive) {
            thread.interrupt()
            return -1
        }
        return result ?: -1
    }

    /**
     * Backtracking solver that counts solutions up to [maxCount], with move ordering
     * and isolated-empty-cell pruning for speed.
     */
    private class Solver(private val dots: List<ColorDot>, private val size: Int) {
        private val grid = Array(size) { IntArray(size) }
        private var solutionCount = 0
        private var maxSolutions = 2

        fun countSolutions(maxCount: Int): Int {
            solutionCount = 0
            maxSolutions = maxCount

            for (dot in dots) {
                grid[dot.start.r][dot.start.c] = dot.colorId
                grid[dot.end.r][dot.end.c] = dot.colorId
            }

            val paths = dots.associate { it.colorId to mutableListOf(it.start) }.toMutableMap()
            solve(paths)
            return solutionCount
        }

        private fun solve(paths: MutableMap<Int, MutableList<Point>>) {
            if (Thread.currentThread().isInterrupted) return
            if (solutionCount >= maxSolutions) return

            var bestColor = -1
            var bestOptions = Int.MAX_VALUE

            for (dot in dots) {
                val path = paths[dot.colorId] ?: continue
                val head = path.last()
                if (head == dot.end && path.size > 1) continue

                val options = countValidMoves(head, dot.colorId, dot.end)
                if (options == 0) return
                if (options < bestOptions) {
                    bestOptions = options
                    bestColor = dot.colorId
                }
            }

            if (bestColor == -1) {
                if (isFullyFilled()) solutionCount++
                return
            }

            val dot = dots.find { it.colorId == bestColor }!!
            val path = paths[bestColor]!!
            val head = path.last()

            for (move in getValidMoves(head, bestColor, dot.end)) {
                grid[move.r][move.c] = bestColor
                path.add(move)
                if (hasNoIsolatedEmptyCells()) {
                    solve(paths)
                }
                path.removeAt(path.lastIndex)
                if (move != dot.start && move != dot.end) {
                    grid[move.r][move.c] = 0
                }
                if (solutionCount >= maxSolutions) return
            }
        }

        private fun countValidMoves(from: Point, colorId: Int, target: Point): Int {
            var count = 0
            for (dir in DIRECTIONS) {
                val nr = from.r + dir[0]
                val nc = from.c + dir[1]
                if (nr in 0 until size && nc in 0 until size) {
                    val p = Point(nr, nc)
                    if (p == target && grid[nr][nc] == colorId) count++
                    else if (grid[nr][nc] == 0) count++
                }
            }
            return count
        }

        private fun getValidMoves(from: Point, colorId: Int, target: Point): List<Point> {
            val moves = mutableListOf<Point>()
            for (dir in DIRECTIONS) {
                val nr = from.r + dir[0]
                val nc = from.c + dir[1]
                if (nr in 0 until size && nc in 0 until size) {
                    val p = Point(nr, nc)
                    if (p == target && grid[nr][nc] == colorId) moves.add(p)
                    else if (grid[nr][nc] == 0) moves.add(p)
                }
            }
            return moves
        }

        private fun isFullyFilled(): Boolean {
            for (r in 0 until size) {
                for (c in 0 until size) {
                    if (grid[r][c] == 0) return false
                }
            }
            return true
        }

        private fun hasNoIsolatedEmptyCells(): Boolean {
            val empty = mutableSetOf<Point>()
            for (r in 0 until size) {
                for (c in 0 until size) {
                    if (grid[r][c] == 0) empty.add(Point(r, c))
                }
            }
            if (empty.isEmpty()) return true

            val start = empty.first()
            val visited = mutableSetOf(start)
            val queue = ArrayDeque<Point>().apply { add(start) }
            while (queue.isNotEmpty()) {
                val curr = queue.removeFirst()
                for (dir in DIRECTIONS) {
                    val nr = curr.r + dir[0]
                    val nc = curr.c + dir[1]
                    val p = Point(nr, nc)
                    if (p in empty && p !in visited) {
                        visited.add(p)
                        queue.add(p)
                    }
                }
            }
            return visited.size == empty.size
        }
    }

    private fun applyTransformations(dots: List<ColorDot>, size: Int, random: Random): List<ColorDot> {
        var transformed = dots

        // Rotate 0, 90, 180, or 270 degrees
        val rotations = random.nextInt(4)
        for (i in 0 until rotations) {
            transformed = transformed.map { dot ->
                ColorDot(
                    dot.colorId,
                    Point(dot.start.c, size - 1 - dot.start.r),
                    Point(dot.end.c, size - 1 - dot.end.r)
                )
            }
        }

        // Flip horizontally
        if (random.nextBoolean()) {
            transformed = transformed.map { dot ->
                ColorDot(
                    dot.colorId,
                    Point(dot.start.r, size - 1 - dot.start.c),
                    Point(dot.end.r, size - 1 - dot.end.c)
                )
            }
        }

        // Flip vertically
        if (random.nextBoolean()) {
            transformed = transformed.map { dot ->
                ColorDot(
                    dot.colorId,
                    Point(size - 1 - dot.start.r, dot.start.c),
                    Point(size - 1 - dot.end.r, dot.end.c)
                )
            }
        }

        return transformed
    }

    private fun shuffleColors(dots: List<ColorDot>, numColors: Int, random: Random): List<ColorDot> {
        val colorIds = (1..numColors).toMutableList()
        colorIds.shuffle(random)

        return dots.mapIndexed { index, dot ->
            dot.copy(colorId = colorIds[index % colorIds.size])
        }
    }
}
