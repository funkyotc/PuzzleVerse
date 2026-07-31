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
 * into FlowFreePregenerated.kt.
 */
fun main(args: Array<String>) {
    val random = Random(9999)

    val DIRECTIONS = arrayOf(
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(0, -1), intArrayOf(0, 1)
    )

    fun generateRandomFilledGrid(size: Int, numColors: Int, rnd: Random): List<ColorDot>? {
        val grid = Array(size) { IntArray(size) { 0 } }
        val paths = Array(numColors + 1) { mutableListOf<Point>() }

        val emptyCells = mutableListOf<Point>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                emptyCells.add(Point(r, c))
            }
        }
        emptyCells.shuffle(rnd)

        for (i in 1..numColors) {
            if (emptyCells.isEmpty()) return null
            val start = emptyCells.removeAt(emptyCells.size - 1)
            paths[i].add(start)
            grid[start.r][start.c] = i
        }

        var changed = true
        while (changed) {
            changed = false
            val colorOrder = (1..numColors).shuffled(rnd)
            for (i in colorOrder) {
                val path = paths[i]
                val head = path.last()
                val validDirs = DIRECTIONS.filter { dir ->
                    val nr = head.r + dir[0]
                    val nc = head.c + dir[1]
                    nr in 0 until size && nc in 0 until size && grid[nr][nc] == 0
                }
                if (validDirs.isNotEmpty()) {
                    val dir = validDirs[rnd.nextInt(validDirs.size)]
                    val next = Point(head.r + dir[0], head.c + dir[1])
                    grid[next.r][next.c] = i
                    path.add(next)
                    emptyCells.remove(next)
                    changed = true
                }
            }
        }

        if (emptyCells.isNotEmpty()) return null

        val dots = mutableListOf<ColorDot>()
        for (i in 1..numColors) {
            val p = paths[i]
            if (p.size < 2) return null
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
        while (added < 5 && attempts < 20000) {
            attempts++
            val dots = generateRandomFilledGrid(size, numColors, random)
            if (dots != null) {
                added++
                val id = "${diff}_${size}x${size}_puzzle_${String.format("%03d", added)}"
                puzzles.add(PregeneratedPuzzle(id, size, diff, dots))
            }
        }
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
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated FlowFreePregenerated.kt with ${puzzles.size} puzzles.")
}
