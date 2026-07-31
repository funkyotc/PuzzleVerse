package generators.nonogram

import java.io.File
import kotlin.random.Random

/**
 * Nonogram Solver for checking unique logical solvability without guessing.
 */
class NonogramSolver {
    companion object {
        fun isSolvableWithoutGuessing(solution: List<List<Boolean>>): Boolean {
            val rows = solution.size
            val cols = if (rows > 0) solution[0].size else 0
            if (rows == 0 || cols == 0) return true

            val grid: List<MutableList<Boolean?>> = solution.map { row ->
                row.map { null as Boolean? }.toMutableList()
            }

            val rowClues = solution.map { calculateClues(it) }
            val colClues = (0 until cols).map { c ->
                calculateClues(solution.map { it[c] })
            }

            var changed = true
            var passes = 0
            while (changed && passes < 100) {
                changed = false
                passes++

                for (r in 0 until rows) {
                    if (applyLineConstraints(grid[r], rowClues[r], cols)) changed = true
                }

                for (c in 0 until cols) {
                    val col = (0 until rows).map { grid[it][c] }.toMutableList()
                    if (applyLineConstraints(col, colClues[c], rows)) {
                        changed = true
                        for (r in 0 until rows) {
                            grid[r][c] = col[r]
                        }
                    }
                }
            }

            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (grid[r][c] == null) return false
                }
            }

            return true
        }

        private fun calculateClues(line: List<Boolean>): List<Int> {
            val clues = mutableListOf<Int>()
            var current = 0
            for (cell in line) {
                if (cell) {
                    current++
                } else {
                    if (current > 0) {
                        clues.add(current)
                        current = 0
                    }
                }
            }
            if (current > 0) clues.add(current)
            return clues
        }

        private fun applyLineConstraints(line: MutableList<Boolean?>, clues: List<Int>, length: Int): Boolean {
            val placements = getValidPlacements(clues, length)
            if (placements.isEmpty()) return false

            val filteredPlacements = placements.filter { placement ->
                var matches = true
                for (i in 0 until length) {
                    if (line[i] != null && line[i] != placement[i]) {
                        matches = false
                        break
                    }
                }
                matches
            }

            if (filteredPlacements.isEmpty()) return false

            var changed = false
            for (i in 0 until length) {
                val allFilled = filteredPlacements.all { it[i] }
                val allEmpty = filteredPlacements.all { !it[i] }

                if (allFilled && line[i] == null) {
                    line[i] = true
                    changed = true
                } else if (allEmpty && line[i] == null) {
                    line[i] = false
                    changed = true
                }
            }

            return changed
        }

        private fun getValidPlacements(clues: List<Int>, length: Int): List<List<Boolean>> {
            if (clues.isEmpty() || clues.all { it == 0 }) {
                return listOf(List(length) { false })
            }

            val result = mutableListOf<List<Boolean>>()
            generatePlacements(clues, 0, 0, length, BooleanArray(length), result)
            return result
        }

        private fun generatePlacements(
            clues: List<Int>,
            clueIdx: Int,
            startPos: Int,
            length: Int,
            current: BooleanArray,
            result: MutableList<List<Boolean>>
        ) {
            if (clueIdx == clues.size) {
                val placement = current.toList()
                result.add(placement)
                return
            }

            val clueLen = clues[clueIdx]
            val minRemaining = clues.subList(clueIdx + 1, clues.size).sum() + (clues.size - clueIdx - 1)
            val maxStart = length - minRemaining - clueLen

            for (pos in startPos..maxStart) {
                val nextArray = current.clone()
                for (i in 0 until clueLen) {
                    nextArray[pos + i] = true
                }
                val nextStart = pos + clueLen + 1
                generatePlacements(clues, clueIdx + 1, nextStart, length, nextArray, result)
            }
        }
    }
}

/**
 * NonogramGeneratorRunner constructs pixel art solution matrices and row/column hints
 * into NonogramPregenerated.kt.
 */
fun main(args: Array<String>) {
    val random = Random(42)

    val PRESET_PATTERNS_10 = listOf(
        // Heart
        listOf(
            "0000000000",
            "0110001100",
            "1111011110",
            "1111111110",
            "1111111110",
            "0111111100",
            "0011111000",
            "0001110000",
            "0000100000",
            "0000000000"
        ),
        // Smiley
        listOf(
            "0011111100",
            "0100000010",
            "1010000101",
            "1010000101",
            "1000000001",
            "1010000101",
            "1001111001",
            "0100000010",
            "0011111100",
            "0000000000"
        ),
        // House
        listOf(
            "0000100000",
            "0001110000",
            "0011111000",
            "0111111100",
            "1111111110",
            "0111111100",
            "0110011100",
            "0110011100",
            "0111111100",
            "0000000000"
        ),
        // Cat
        listOf(
            "0000000000",
            "0100000100",
            "0110001100",
            "0111111100",
            "0111111100",
            "0111111100",
            "0111111100",
            "1111111110",
            "1111111110",
            "0000000000"
        ),
        // Tree
        listOf(
            "0000100000",
            "0001110000",
            "0011111000",
            "0111111100",
            "0011111000",
            "0111111100",
            "1111111110",
            "0001110000",
            "0001110000",
            "0001110000"
        ),
        // Skull
        listOf(
            "0011111100",
            "0111111110",
            "1111111111",
            "1101111011",
            "1101111011",
            "1111111111",
            "0111111110",
            "0010101010",
            "0011111100",
            "0000000000"
        ),
        // Diamond
        listOf(
            "0000100000",
            "0001110000",
            "0011111000",
            "0111111100",
            "1111111110",
            "0111111100",
            "0011111000",
            "0001110000",
            "0000100000",
            "0000000000"
        ),
        // Sword
        listOf(
            "0000000010",
            "0000000110",
            "0000001100",
            "0000011000",
            "0000110000",
            "0001100000",
            "0011000000",
            "0111100000",
            "1100000000",
            "1000000000"
        ),
        // Mushroom
        listOf(
            "0001111000",
            "0111111110",
            "1111111111",
            "1101111011",
            "1111111111",
            "0011111100",
            "0001111000",
            "0001111000",
            "0001111000",
            "0001111000"
        ),
        // Ghost
        listOf(
            "0001111000",
            "0011111100",
            "0111111110",
            "0101101110",
            "0111111110",
            "0111111110",
            "0111111110",
            "0111111110",
            "0101010110",
            "0101010100"
        )
    )

    fun calculateClues(line: List<Boolean>): List<Int> {
        val clues = mutableListOf<Int>()
        var count = 0
        for (cell in line) {
            if (cell) {
                count++
            } else if (count > 0) {
                clues.add(count)
                count = 0
            }
        }
        if (count > 0) clues.add(count)
        return if (clues.isEmpty()) listOf(0) else clues
    }

    data class NonogramData(
        val id: String,
        val difficulty: String,
        val size: Int,
        val rowClues: List<List<Int>>,
        val colClues: List<List<Int>>,
        val gridStr: String
    )

    val puzzles = mutableListOf<NonogramData>()

    // Generate Easy Puzzles (10x10)
    var easyCount = 0
    for ((idx, pattern) in PRESET_PATTERNS_10.withIndex()) {
        val grid = pattern.map { row -> row.map { it == '1' } }
        val size = 10
        val rowClues = grid.map { calculateClues(it) }
        val colClues = (0 until size).map { c -> calculateClues(grid.map { it[c] }) }
        val gridStr = pattern.joinToString("")

        easyCount++
        puzzles.add(
            NonogramData(
                id = "nonogram_easy_$easyCount",
                difficulty = "Easy",
                size = size,
                rowClues = rowClues,
                colClues = colClues,
                gridStr = gridStr
            )
        )
    }

    // Procedural generation for remaining Easy (10x10), Medium (15x15), Hard (20x20)
    fun generateProceduralPuzzles(count: Int, size: Int, difficulty: String, startIdx: Int) {
        var added = 0
        var attempts = 0
        while (added < count && attempts < 5000) {
            attempts++
            val density = random.nextFloat() * 0.15f + 0.45f
            val grid = List(size) {
                List(size) {
                    random.nextFloat() < density
                }
            }

            if (NonogramSolver.isSolvableWithoutGuessing(grid)) {
                val rowClues = grid.map { calculateClues(it) }
                val colClues = (0 until size).map { c -> calculateClues(grid.map { it[c] }) }
                val gridStr = grid.flatten().joinToString("") { if (it) "1" else "0" }

                added++
                puzzles.add(
                    NonogramData(
                        id = "nonogram_${difficulty.lowercase()}_${startIdx + added - 1}",
                        difficulty = difficulty,
                        size = size,
                        rowClues = rowClues,
                        colClues = colClues,
                        gridStr = gridStr
                    )
                )
            }
        }
    }

    generateProceduralPuzzles(5, 10, "Easy", easyCount + 1)
    generateProceduralPuzzles(10, 15, "Medium", 1)
    generateProceduralPuzzles(10, 20, "Hard", 1)

    // Format output file
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.nonogram.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedNonogram(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val size: Int,")
    sb.appendLine("    val rowClues: List<List<Int>>,")
    sb.appendLine("    val colClues: List<List<Int>>,")
    sb.appendLine("    val gridStr: String")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    val grid: List<List<Boolean>> get() = gridStr.map { it == '1' }.chunked(size)")
    sb.appendLine("    override val label: String get() = \"Nonogram \${id.substringAfterLast('_')}\"")
    sb.appendLine("    override val subtitle: String get() = \"\${size}x\${size}\"")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object NonogramPregenerated {")
    sb.appendLine()
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedNonogram> by lazy {")
    sb.appendLine("        listOf(")

    for (p in puzzles) {
        val rowCluesStr = p.rowClues.joinToString(", ") { r -> "listOf(" + r.joinToString(", ") + ")" }
        val colCluesStr = p.colClues.joinToString(", ") { c -> "listOf(" + c.joinToString(", ") + ")" }

        sb.appendLine("            PregeneratedNonogram(")
        sb.appendLine("                id = \"${p.id}\",")
        sb.appendLine("                difficulty = \"${p.difficulty}\",")
        sb.appendLine("                size = ${p.size},")
        sb.appendLine("                rowClues = listOf($rowCluesStr),")
        sb.appendLine("                colClues = listOf($colCluesStr),")
        sb.appendLine("                gridStr = \"${p.gridStr}\"")
        sb.appendLine("            ),")
    }

    sb.appendLine("        )")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedNonogram>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }")
    sb.appendLine("}")

    val targetFile = File("app/src/main/java/com/funkyotc/puzzleverse/nonogram/data/NonogramPregenerated.kt")
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated NonogramPregenerated.kt with ${puzzles.size} puzzles.")
}
