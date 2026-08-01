package generators.flowfree

import java.io.File
import kotlin.random.Random

data class Point(val r: Int, val c: Int)
data class ColorDot(val colorId: Int, val start: Point, val end: Point)

data class PregeneratedPuzzle(
    val id: String,
    val size: Int,
    val difficulty: String,
    val dots: List<ColorDot>
)

/**
 * FlowFreeGeneratorRunner generates non-crossing color endpoint grid puzzles
 * with 100% grid coverage and paths >= 3 cells into FlowFreePregenerated.kt.
 */
fun main(args: Array<String>) {
    val random = Random(9999)

    val DIRECTIONS = arrayOf(
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(0, -1), intArrayOf(0, 1)
    )

    fun generateGridPartition(size: Int, numColors: Int, rnd: Random): List<ColorDot>? {
        val totalCells = size * size
        val grid = Array(size) { IntArray(size) { 0 } }
        val paths = Array(numColors + 1) { mutableListOf<Point>() }

        // Pick distinct random start points for each color
        val allPoints = mutableListOf<Point>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                allPoints.add(Point(r, c))
            }
        }
        allPoints.shuffle(rnd)

        for (i in 1..numColors) {
            val start = allPoints[i - 1]
            paths[i].add(start)
            grid[start.r][start.c] = i
        }

        val unvisitedCount = totalCells - numColors

        // Helper recursive grow with backtracking
        fun growPaths(remaining: Int): Boolean {
            if (remaining == 0) {
                // Verify all paths have length >= 3
                return (1..numColors).all { paths[it].size >= 3 }
            }

            // Pick a color to grow (prioritize colors with fewest valid extension options to avoid dead ends)
            val candidates = mutableListOf<Pair<Int, List<Point>>>()
            for (i in 1..numColors) {
                val head = paths[i].last()
                val validNext = DIRECTIONS.mapNotNull { dir ->
                    val nr = head.r + dir[0]
                    val nc = head.c + dir[1]
                    if (nr in 0 until size && nc in 0 until size && grid[nr][nc] == 0) {
                        Point(nr, nc)
                    } else null
                }
                if (validNext.isNotEmpty()) {
                    candidates.add(i to validNext)
                }
            }

            if (candidates.isEmpty()) return false

            // Sort colors by number of options (ascending) so constrained paths grow first
            candidates.sortBy { it.second.size }

            // Try extending the most constrained color
            val (colorToGrow, options) = candidates.first()
            val shuffledOptions = options.shuffled(rnd)

            for (nextPt in shuffledOptions) {
                grid[nextPt.r][nextPt.c] = colorToGrow
                paths[colorToGrow].add(nextPt)

                if (growPaths(remaining - 1)) {
                    return true
                }

                // Backtrack
                paths[colorToGrow].removeAt(paths[colorToGrow].lastIndex)
                grid[nextPt.r][nextPt.c] = 0
            }

            return false
        }

        if (!growPaths(unvisitedCount)) {
            return null
        }

        val dots = mutableListOf<ColorDot>()
        for (i in 1..numColors) {
            val p = paths[i]
            dots.add(ColorDot(i, p.first(), p.last()))
        }
        return dots
    }

    val configs = listOf(
        Triple("Easy", 5, 4),
        Triple("Medium", 6, 5),
        Triple("Hard", 7, 6),
        Triple("Expert", 8, 7)
    )

    val puzzles = mutableListOf<PregeneratedPuzzle>()

    for ((diff, size, numColors) in configs) {
        var added = 0
        var attempts = 0
        while (added < 10 && attempts < 50000) {
            attempts++
            val dots = generateGridPartition(size, numColors, random)
            if (dots != null) {
                added++
                val id = "${diff}_${size}x${size}_puzzle_${String.format("%03d", added)}"
                puzzles.add(PregeneratedPuzzle(id, size, diff, dots))
            }
        }
        println("Generated $added puzzles for $diff (${size}x${size}) in $attempts attempts.")
    }

    // Write FlowFreePregenerated.kt
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.flowfree.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedPuzzle(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    val size: Int,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val dots: List<ColorDot>")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = \"Puzzle\"")
    sb.appendLine("    override val subtitle: String get() = \"\${size}x\${size}\"")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object FlowFreePregenerated {")
    sb.appendLine()
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(")

    for (p in puzzles) {
        val dotsStr = p.dots.joinToString(", ") { d ->
            "ColorDot(${d.colorId}, Point(${d.start.r}, ${d.start.c}), Point(${d.end.r}, ${d.end.c}))"
        }
        sb.appendLine("        PregeneratedPuzzle(\"${p.id}\", ${p.size}, \"${p.difficulty}\", listOf($dotsStr)),")
    }

    sb.appendLine("    )")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<BrowseablePuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): PregeneratedPuzzle? = ALL_PUZZLES.find { it.id == id }")
    sb.appendLine()
    sb.appendLine("    fun getRandomPuzzle(difficulty: FlowDifficulty): PregeneratedPuzzle? {")
    sb.appendLine("        val diffName = when(difficulty) {")
    sb.appendLine("            FlowDifficulty.EASY -> \"Easy\"")
    sb.appendLine("            FlowDifficulty.MEDIUM -> \"Medium\"")
    sb.appendLine("            FlowDifficulty.HARD -> \"Hard\"")
    sb.appendLine("            FlowDifficulty.EXPERT -> \"Expert\"")
    sb.appendLine("        }")
    sb.appendLine("        return ALL_PUZZLES.filter { it.difficulty == diffName }.randomOrNull()")
    sb.appendLine("    }")
    sb.appendLine("}")

    val targetFile = File("app/src/main/java/com/funkyotc/puzzleverse/flowfree/data/FlowFreePregenerated.kt")
    targetFile.parentFile?.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated FlowFreePregenerated.kt with ${puzzles.size} puzzles.")
}
