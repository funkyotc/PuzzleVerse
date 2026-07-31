package generators.hexasort

import java.io.File
import kotlin.random.Random

data class GeneratedHexaSortPuzzle(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<Int?>>
)

private fun getNeighbors(row: Int, col: Int, rows: Int, cols: Int): List<Pair<Int, Int>> {
    val isOddRow = row % 2 == 1
    val candidates = if (isOddRow) {
        listOf(
            row - 1 to col, row - 1 to col + 1,
            row to col - 1, row to col + 1,
            row + 1 to col, row + 1 to col + 1
        )
    } else {
        listOf(
            row - 1 to col - 1, row - 1 to col,
            row to col - 1, row to col + 1,
            row + 1 to col - 1, row + 1 to col
        )
    }
    return candidates.filter { (r, c) -> r in 0 until rows && c in 0 until cols }
}

private fun hasValidMoves(grid: List<List<Int?>>): Boolean {
    val rows = grid.size
    val cols = grid[0].size
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val color = grid[r][c] ?: continue
            for ((nr, nc) in getNeighbors(r, c, rows, cols)) {
                if (grid[nr][nc] == color) return true
            }
        }
    }
    return false
}

private fun generateGrid(rows: Int, cols: Int, numColors: Int, random: Random): List<List<Int?>> {
    var grid: List<List<Int?>>
    var attempts = 0
    do {
        grid = List(rows) {
            List(cols) {
                random.nextInt(numColors)
            }
        }
        attempts++
    } while (!hasValidMoves(grid) && attempts < 100)
    return grid
}

fun main(args: Array<String>) {
    println("Generating Hexa Sort pregenerated puzzles...")
    val random = Random(42L)
    val puzzles = mutableListOf<GeneratedHexaSortPuzzle>()

    val configs = listOf(
        Triple("Easy", 5, 3),
        Triple("Medium", 6, 4),
        Triple("Hard", 7, 5)
    )

    for ((difficulty, size, colors) in configs) {
        for (i in 1..8) {
            val id = "HexaSort_${difficulty}_puzzle_${String.format("%03d", i)}"
            val grid = generateGrid(size, size, colors, random)
            puzzles.add(GeneratedHexaSortPuzzle(id, difficulty, size, size, grid))
        }
    }

    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.hexasort.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedHexaSort(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val rows: Int,")
    sb.appendLine("    val cols: Int,")
    sb.appendLine("    val grid: List<List<Int?>>")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = \"Puzzle \${id.takeLast(3)}\"")
    sb.appendLine("    override val subtitle: String get() = \"\${rows}x\$cols\"")
    sb.appendLine()
    sb.appendLine("    fun toLevel(): HexaSortLevel = HexaSortLevel(id, difficulty, rows, cols, grid)")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object HexaSortPregenerated {")
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedHexaSort> by lazy { listOf(")

    for ((index, p) in puzzles.withIndex()) {
        sb.appendLine("        PregeneratedHexaSort(\"${p.id}\", \"${p.difficulty}\", ${p.rows}, ${p.cols}, listOf(")
        for ((rIdx, row) in p.grid.withIndex()) {
            val rowStr = row.joinToString(", ") { it?.toString() ?: "null" }
            val comma = if (rIdx < p.grid.size - 1) "," else ""
            sb.appendLine("            listOf($rowStr)$comma")
        }
        val pComma = if (index < puzzles.size - 1) "," else ""
        sb.appendLine("        ))$pComma")
    }

    sb.appendLine("    ) }")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedHexaSort>> by lazy {")
    sb.appendLine("        ALL_PUZZLES.groupBy { it.difficulty }")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): PregeneratedHexaSort? = ALL_PUZZLES.find { it.id == id }")
    sb.appendLine("}")

    val targetFileCandidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/hexasort/data/HexaSortPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/hexasort/data/HexaSortPregenerated.kt")
    )
    val targetFile = targetFileCandidates.find { it.parentFile.exists() } ?: targetFileCandidates.first()
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())
    println("Successfully generated ${puzzles.size} Hexa Sort puzzles into ${targetFile.absolutePath}")
}
