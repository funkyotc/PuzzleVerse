package generators.kakuro

import java.io.File
import kotlin.random.Random

enum class CellType { BLACK, CLUE, PLAYER_INPUT }

data class Clue(val horizontalSum: Int?, val verticalSum: Int?)

data class KakuroCell(
    val type: CellType,
    val clue: Clue? = null,
    val playerValue: Int? = null,
    val r: Int,
    val c: Int
)

data class PregeneratedKakuro(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<KakuroCell>>
)

/**
 * KakuroGeneratorRunner constructs Kakuro cross-sum numerical boards
 * into KakuroPregenerated.kt.
 */
fun main(args: Array<String>) {
    val random = Random(12345)

    // Layout templates: 'B' = BLACK, 'C' = CLUE, 'I' = INPUT
    val LAYOUTS_5X5 = listOf(
        listOf(
            "B C C B B",
            "C I I C B",
            "C I I I C",
            "B C I I I",
            "B B C I I"
        ),
        listOf(
            "B B C C B",
            "B C I I B",
            "C I I I B",
            "C I I I B",
            "B C I I B"
        ),
        listOf(
            "B C C B B",
            "C I I C B",
            "C I I I C",
            "B C I I I",
            "B B C I I"
        )
    )

    val LAYOUTS_6X6 = listOf(
        listOf(
            "B B C C B B",
            "B C I I C B",
            "C I I I I B",
            "C I I I I B",
            "B C I I B B",
            "B B B B B B"
        ),
        listOf(
            "B C C B C C",
            "C I I C I I",
            "C I I I I I",
            "B C I I I I",
            "B B C I I B",
            "B B B B B B"
        )
    )

    val LAYOUTS_7X7 = listOf(
        listOf(
            "B B C C B B B",
            "B C I I C C B",
            "C I I I I I B",
            "C I I C I I C",
            "B I I I I I B",
            "B C C I I C B",
            "B B B B B B B"
        )
    )

    fun fillLayout(layout: List<String>, random: Random): List<List<Int>>? {
        val rows = layout.size
        val cols = layout[0].split(" ").size
        val charGrid = layout.map { it.split(" ") }
        val solution = Array(rows) { IntArray(cols) { 0 } }

        fun solve(r: Int, c: Int): Boolean {
            if (r >= rows) return true
            val nextR = if (c + 1 >= cols) r + 1 else r
            val nextC = if (c + 1 >= cols) 0 else c + 1

            if (charGrid[r][c] != "I") {
                return solve(nextR, nextC)
            }

            val digits = (1..9).shuffled(random)
            for (d in digits) {
                // Check horizontal uniqueness
                var valid = true
                var cc = c - 1
                while (cc >= 0 && charGrid[r][cc] == "I") {
                    if (solution[r][cc] == d) {
                        valid = false
                        break
                    }
                    cc--
                }
                if (!valid) continue

                // Check vertical uniqueness
                var rr = r - 1
                while (rr >= 0 && charGrid[rr][c] == "I") {
                    if (solution[rr][c] == d) {
                        valid = false
                        break
                    }
                    rr--
                }
                if (!valid) continue

                solution[r][c] = d
                if (solve(nextR, nextC)) return true
                solution[r][c] = 0
            }
            return false
        }

        return if (solve(0, 0)) solution.map { it.toList() } else null
    }

    fun buildBoard(layout: List<String>, solution: List<List<Int>>): List<List<KakuroCell>> {
        val rows = layout.size
        val cols = layout[0].split(" ").size
        val charGrid = layout.map { it.split(" ") }

        val grid = mutableListOf<MutableList<KakuroCell>>()

        for (r in 0 until rows) {
            val rowCells = mutableListOf<KakuroCell>()
            for (c in 0 until cols) {
                when (charGrid[r][c]) {
                    "I" -> {
                        rowCells.add(KakuroCell(CellType.PLAYER_INPUT, null, null, r, c))
                    }
                    "C" -> {
                        // Calculate horizontal sum (run to the right)
                        var hSum: Int? = null
                        var cc = c + 1
                        var sum = 0
                        while (cc < cols && charGrid[r][cc] == "I") {
                            sum += solution[r][cc]
                            cc++
                        }
                        if (cc > c + 1) hSum = sum

                        // Calculate vertical sum (run downwards)
                        var vSum: Int? = null
                        var rr = r + 1
                        sum = 0
                        while (rr < rows && charGrid[rr][c] == "I") {
                            sum += solution[rr][c]
                            rr++
                        }
                        if (rr > r + 1) vSum = sum

                        if (hSum != null || vSum != null) {
                            rowCells.add(KakuroCell(CellType.CLUE, Clue(hSum, vSum), null, r, c))
                        } else {
                            rowCells.add(KakuroCell(CellType.BLACK, null, null, r, c))
                        }
                    }
                    else -> {
                        rowCells.add(KakuroCell(CellType.BLACK, null, null, r, c))
                    }
                }
            }
            grid.add(rowCells)
        }

        return grid
    }

    val puzzles = mutableListOf<PregeneratedKakuro>()

    fun generateKakuroSet(diff: String, layouts: List<List<String>>, count: Int) {
        var added = 0
        var attempts = 0
        while (added < count && attempts < 1000) {
            attempts++
            val layout = layouts[attempts % layouts.size]
            val solution = fillLayout(layout, random)
            if (solution != null) {
                added++
                val grid = buildBoard(layout, solution)
                val rows = layout.size
                val cols = layout[0].split(" ").size
                val id = "kakuro_${diff.lowercase()}_$added"
                puzzles.add(PregeneratedKakuro(id, diff, rows, cols, grid))
            }
        }
    }

    generateKakuroSet("Easy", LAYOUTS_5X5, 10)
    generateKakuroSet("Medium", LAYOUTS_6X6, 8)
    generateKakuroSet("Hard", LAYOUTS_7X7, 6)

    // Write KakuroPregenerated.kt
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.kakuro.data")
    sb.appendLine()
    sb.appendLine("data class PregeneratedKakuro(")
    sb.appendLine("    val id: String,")
    sb.appendLine("    val difficulty: String,")
    sb.appendLine("    val rows: Int,")
    sb.appendLine("    val cols: Int,")
    sb.appendLine("    val grid: List<List<KakuroCell>>")
    sb.appendLine(")")
    sb.appendLine()
    sb.appendLine("object KakuroPregenerated {")
    sb.appendLine()
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedKakuro> by lazy {")
    sb.appendLine("        listOf(")

    for (p in puzzles) {
        sb.appendLine("            PregeneratedKakuro(\"${p.id}\", \"${p.difficulty}\", ${p.rows}, ${p.cols}, listOf(")
        for (r in p.grid) {
            sb.append("                listOf(")
            val cellStrs = r.map { cell ->
                when (cell.type) {
                    CellType.BLACK -> "KakuroCell(CellType.BLACK, null, null, ${cell.r}, ${cell.c})"
                    CellType.PLAYER_INPUT -> "KakuroCell(CellType.PLAYER_INPUT, null, null, ${cell.r}, ${cell.c})"
                    CellType.CLUE -> {
                        val h = cell.clue?.horizontalSum?.toString() ?: "null"
                        val v = cell.clue?.verticalSum?.toString() ?: "null"
                        "KakuroCell(CellType.CLUE, Clue($h, $v), null, ${cell.r}, ${cell.c})"
                    }
                }
            }
            sb.append(cellStrs.joinToString(", "))
            sb.appendLine("),")
        }
        sb.appendLine("            )),")
    }

    sb.appendLine("        )")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }")
    sb.appendLine("}")

    val targetFile = File("app/src/main/java/com/funkyotc/puzzleverse/kakuro/data/KakuroPregenerated.kt")
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated KakuroPregenerated.kt with ${puzzles.size} puzzles.")
}
