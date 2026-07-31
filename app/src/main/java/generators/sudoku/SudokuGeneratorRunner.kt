package com.funkyotc.puzzleverse.sudoku.generator

import com.funkyotc.puzzleverse.sudoku.data.PregeneratedSudoku
import java.io.File
import kotlin.random.Random

/**
 * Standalone runner to pregenerate 9x9 Sudoku puzzles into SudokuPregenerated.kt.
 */
fun main(args: Array<String>) {
    val puzzlesPerDifficulty = args.getOrNull(0)?.toIntOrNull() ?: 20
    println("================================================================")
    println("SUDOKU GENERATOR RUNNER")
    println("Target per difficulty: $puzzlesPerDifficulty")
    println("================================================================")

    val generator = SudokuGenerator()
    val difficulties = listOf(
        Triple("Easy", 40, 20),    // 40 removed cells -> ~41 clues
        Triple("Medium", 49, 20),  // 49 removed cells -> ~32 clues
        Triple("Hard", 55, 20)     // 55 removed cells -> ~26 clues
    )

    val generatedPuzzles = mutableListOf<PregeneratedSudoku>()
    var puzzleCounter = 1

    for ((diffLabel, removedCount, count) in difficulties) {
        val targetCount = if (args.isNotEmpty()) puzzlesPerDifficulty else count
        println("Generating $targetCount '$diffLabel' puzzles...")
        
        var generatedForDiff = 0
        var attempts = 0

        while (generatedForDiff < targetCount && attempts < targetCount * 20) {
            attempts++
            val seed = System.nanoTime() + puzzleCounter * 31337L
            val board = generator.generate(seed, removedCount)
            val solutionGrid = generator.getSolutionGrid(board) ?: continue

            val puzzleSb = StringBuilder()
            val solutionSb = StringBuilder()
            var cluesCount = 0

            for (r in 0..8) {
                for (c in 0..8) {
                    val cell = board.cells.find { it.row == r && it.col == c }
                    val num = cell?.number ?: 0
                    puzzleSb.append(num)
                    if (num != 0) cluesCount++

                    solutionSb.append(solutionGrid[r][c])
                }
            }

            val puzzleStr = puzzleSb.toString()
            val solutionStr = solutionSb.toString()
            val puzzleId = "Sudoku_${diffLabel}_puzzle_${String.format("%03d", generatedForDiff + 1)}"

            generatedPuzzles.add(
                PregeneratedSudoku(
                    id = puzzleId,
                    difficulty = diffLabel,
                    clues = cluesCount,
                    puzzleStr = puzzleStr,
                    solutionStr = solutionStr
                )
            )

            generatedForDiff++
            puzzleCounter++
            println("  [$diffLabel #${generatedForDiff}/$targetCount] ID: $puzzleId | Clues: $cluesCount")
        }
    }

    println("\nUpdating SudokuPregenerated.kt with ${generatedPuzzles.size} puzzles...")
    updateSudokuPregeneratedFile(generatedPuzzles)
    println("Sudoku pregeneration complete!")
}

private fun updateSudokuPregeneratedFile(puzzles: List<PregeneratedSudoku>) {
    val candidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/sudoku/data/SudokuPregenerated.kt"),
        File("src/main/java/com/funkyotc/puzzleverse/sudoku/data/SudokuPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/sudoku/data/SudokuPregenerated.kt")
    )
    val file = candidates.find { it.exists() }
    if (file == null) {
        println("Error: SudokuPregenerated.kt not found!")
        return
    }

    val sb = StringBuilder()
    sb.append("package com.funkyotc.puzzleverse.sudoku.data\n\n")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle\n\n")
    sb.append("data class PregeneratedSudoku(\n")
    sb.append("    override val id: String,\n")
    sb.append("    override val difficulty: String,\n")
    sb.append("    val clues: Int,\n")
    sb.append("    val puzzleStr: String,\n")
    sb.append("    val solutionStr: String\n")
    sb.append(") : BrowseablePuzzle {\n")
    sb.append("    override val label: String get() = \"Puzzle\"\n")
    sb.append("    override val subtitle: String get() = \"\$clues clues\"\n")
    sb.append("    val puzzle: List<List<Int>> get() = puzzleStr.map { it.digitToInt() }.chunked(9)\n")
    sb.append("    val solution: List<List<Int>> get() = solutionStr.map { it.digitToInt() }.chunked(9)\n")
    sb.append("}\n\n")
    sb.append("object SudokuPregenerated {\n\n")
    sb.append("    val ALL_PUZZLES: List<PregeneratedSudoku> by lazy {\n")
    sb.append("        listOf(\n")

    for (p in puzzles) {
        sb.append("            PregeneratedSudoku(\"${p.id}\", \"${p.difficulty}\", ${p.clues}, \"${p.puzzleStr}\", \"${p.solutionStr}\"),\n")
    }

    sb.append("        )\n")
    sb.append("    }\n\n")
    sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedSudoku>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }\n\n")
    sb.append("    fun getPuzzleById(id: String): PregeneratedSudoku? = ALL_PUZZLES.find { it.id == id }\n\n")
    sb.append("    fun getRandomPuzzle(difficulty: String): PregeneratedSudoku? {\n")
    sb.append("        return PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()\n")
    sb.append("    }\n")
    sb.append("}\n")

    file.writeText(sb.toString())
    println("Successfully wrote ${puzzles.size} puzzles to ${file.absolutePath}")
}
