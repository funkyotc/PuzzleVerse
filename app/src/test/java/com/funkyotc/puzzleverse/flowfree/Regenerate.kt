package com.funkyotc.puzzleverse.flowfree

import com.funkyotc.puzzleverse.flowfree.data.*
import java.io.File
import kotlin.random.Random

import org.junit.Test

class RegenerateTest {
    @Test
    fun regenerateAll() {
    val random = Random(42)
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

    val difficulties = listOf(
        Pair(FlowDifficulty.EASY, 20),
        Pair(FlowDifficulty.MEDIUM, 20),
        Pair(FlowDifficulty.HARD, 10),
        Pair(FlowDifficulty.EXPERT, 10)
    )

    for ((diff, count) in difficulties) {
        val diffName = when(diff) {
            FlowDifficulty.EASY -> "Easy"
            FlowDifficulty.MEDIUM -> "Medium"
            FlowDifficulty.HARD -> "Hard"
            FlowDifficulty.EXPERT -> "Expert"
        }
        val size = diff.gridSize
        for (i in 1..count) {
            print("Generating $diffName ${size}x${size} puzzle $i... ")
            val p = FlowFreeGenerator.generate(diff, random)
            val id = "${diffName}_${size}x${size}_puzzle_${String.format("%03d", i)}"
            val dotsStr = p.dots.joinToString(", ") { dot ->
                "ColorDot(${dot.colorId}, Point(${dot.start.r}, ${dot.start.c}), Point(${dot.end.r}, ${dot.end.c}))"
            }
            sb.appendLine("        PregeneratedPuzzle(\"$id\", $size, \"$diffName\", listOf($dotsStr)),")
            println("Done")
        }
    }

    sb.appendLine("    )")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedPuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }")
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
    sb.appendLine("        return PUZZLES_BY_DIFFICULTY[diffName]?.randomOrNull()")
    sb.appendLine("    }")
    sb.appendLine("}")
    
    File("c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/flowfree/data/FlowFreePregenerated.kt").writeText(sb.toString())
    }
}
