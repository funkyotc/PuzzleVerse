package com.funkyotc.puzzleverse.flowfree.data

import kotlin.random.Random

enum class FlowDifficulty(val gridSize: Int, val numColors: Int, val label: String) {
    EASY(5, 4, "Easy"),
    MEDIUM(6, 5, "Medium"),
    HARD(7, 6, "Hard"),
    EXPERT(8, 7, "Expert")
}

/**
 * Generates valid Flow Free puzzles using solution-first approach:
 * 1. Fill grid with random non-overlapping paths (= valid solution)
 * 2. Extract path endpoints as puzzle dots
 * 3. Verify puzzle has exactly one solution via backtracking solver
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

    fun generate(difficulty: FlowDifficulty, random: Random = Random): GeneratedPuzzle {
        val size = difficulty.gridSize
        val numColors = difficulty.numColors
        
        while (true) {
            val dots = generateRandomFilledGrid(size, numColors, random) ?: continue
            val solutions = countSolutions(size, dots)
            if (solutions == 1) {
                // We shuffle the colors to be purely random
                val shuffled = shuffleColors(dots, numColors, random)
                // We also apply random reflections/rotations
                val finalDots = applyTransformations(shuffled, size, random)
                return GeneratedPuzzle(size, finalDots, difficulty)
            }
        }
    }

    private fun generateRandomFilledGrid(size: Int, numColors: Int, random: Random): List<ColorDot>? {
        val grid = Array(size) { IntArray(size) { 0 } }
        val paths = Array(numColors + 1) { mutableListOf<Point>() }
        
        val startPoints = mutableListOf<Point>()
        while (startPoints.size < numColors) {
            val p = Point(random.nextInt(size), random.nextInt(size))
            if (!startPoints.contains(p)) startPoints.add(p)
        }
        
        for (i in 1..numColors) {
            val start = startPoints[i - 1]
            paths[i].add(start)
            grid[start.r][start.c] = i
        }
        
        fun fill(colorIdx: Int, emptyCount: Int): Boolean {
            if (emptyCount == 0) {
                // Ensure all paths have length >= 2
                for (i in 1..numColors) {
                    if (paths[i].size < 2) return false
                }
                return true
            }
            if (colorIdx > numColors) return false
            
            val head = paths[colorIdx].last()
            
            val dirs = DIRECTIONS.toList().shuffled(random)
            for (dir in dirs) {
                val nr = head.r + dir[0]
                val nc = head.c + dir[1]
                if (nr in 0 until size && nc in 0 until size && grid[nr][nc] == 0) {
                    grid[nr][nc] = colorIdx
                    paths[colorIdx].add(Point(nr, nc))
                    if (fill(colorIdx, emptyCount - 1)) return true
                    grid[nr][nc] = 0
                    paths[colorIdx].removeAt(paths[colorIdx].size - 1)
                }
            }
            
            if (paths[colorIdx].size >= 2) {
                if (fill(colorIdx + 1, emptyCount)) return true
            }
            
            return false
        }
        
        if (fill(1, size * size - numColors)) {
            val dots = mutableListOf<ColorDot>()
            for (i in 1..numColors) {
                dots.add(ColorDot(i, paths[i].first(), paths[i].last()))
            }
            return dots
        }
        return null
    }

    private fun countSolutions(size: Int, dots: List<ColorDot>): Int {
        val grid = Array(size) { IntArray(size) { 0 } }
        val startPoints = Array(dots.size + 1) { Point(0, 0) }
        val endPoints = Array(dots.size + 1) { Point(0, 0) }

        for (dot in dots) {
            grid[dot.start.r][dot.start.c] = dot.colorId
            grid[dot.end.r][dot.end.c] = dot.colorId
            startPoints[dot.colorId] = dot.start
            endPoints[dot.colorId] = dot.end
        }

        var solutions = 0
        fun backtrack(colorId: Int, r: Int, c: Int, emptyCount: Int) {
            if (solutions > 1) return

            if (r == endPoints[colorId].r && c == endPoints[colorId].c) {
                if (colorId == dots.size) {
                    if (emptyCount == 0) solutions++
                    return
                } else {
                    val nextStart = startPoints[colorId + 1]
                    backtrack(colorId + 1, nextStart.r, nextStart.c, emptyCount)
                    return
                }
            }

            for (dir in DIRECTIONS) {
                val nr = r + dir[0]
                val nc = c + dir[1]
                if (nr in 0 until size && nc in 0 until size) {
                    val isEnd = (nr == endPoints[colorId].r && nc == endPoints[colorId].c)
                    if (grid[nr][nc] == 0 || isEnd) {
                        if (!isEnd) {
                            grid[nr][nc] = colorId
                            backtrack(colorId, nr, nc, emptyCount - 1)
                            grid[nr][nc] = 0
                        } else {
                            backtrack(colorId, nr, nc, emptyCount)
                        }
                    }
                }
            }
        }

        val emptyCount = size * size - dots.size * 2
        val firstStart = startPoints[1]
        backtrack(1, firstStart.r, firstStart.c, emptyCount)
        return solutions
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
