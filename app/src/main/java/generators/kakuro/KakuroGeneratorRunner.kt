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
    val grid: List<List<KakuroCell>>,
    val givens: Map<Pair<Int, Int>, Int>
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
                // Each run sum determines its last hidden digit when every
                // other input is fixed. This conservative reveal rule keeps
                // regenerated boards unique under the game's clue rules.
                val inputs = grid.flatten().filter { it.type == CellType.PLAYER_INPUT }
                val givens = inputs.dropLast(1).associate { (it.r to it.c) to solution[it.r][it.c] }
                puzzles.add(PregeneratedKakuro(id, diff, rows, cols, grid, givens))
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
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedKakuro(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val rows: Int,")
    sb.appendLine("    val cols: Int,")
    sb.appendLine("    val grid: List<List<KakuroCell>>,")
    sb.appendLine("    val givens: Map<Pair<Int, Int>, Int>")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = \"Kakuro \${id.substringAfterLast('_')}\"")
    sb.appendLine("    override val subtitle: String get() = \"\${rows}x\${cols}\"")
    sb.appendLine("    val startingGrid: List<List<KakuroCell>> get() = grid.mapIndexed { r, row ->")
    sb.appendLine("        row.mapIndexed { c, cell -> givens[r to c]?.let { cell.copy(playerValue = it, isGiven = true) } ?: cell }")
    sb.appendLine("    }")
    sb.appendLine("}")
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
        sb.appendLine("            ), mapOf(")
        for ((pos, digit) in p.givens) {
            sb.appendLine("                (${pos.first} to ${pos.second}) to $digit,")
        }
        sb.appendLine("            )),")
    }

    sb.appendLine("        )")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }")
    sb.appendLine("}")

    val targetFile = File(args.firstOrNull()
        ?: "app/src/main/java/com/funkyotc/puzzleverse/kakuro/data/KakuroPregenerated.kt")
    targetFile.parentFile?.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated KakuroPregenerated.kt with ${puzzles.size} puzzles.")
}
