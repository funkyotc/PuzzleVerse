package com.funkyotc.puzzleverse.generators.watersort

import com.funkyotc.puzzleverse.watersort.data.WaterSortPregenerated
import java.io.File

/** Materializes the deterministic, solver-gated Water Sort levels as source data. */
fun main(args: Array<String>) {
    val levels = WaterSortPregenerated.ALL_LEVELS
    require(levels.all { it.winningMoves.isNotEmpty() }) { "Every generated level needs a winning replay" }
    val target = File(args.firstOrNull()
        ?: "app/src/main/java/com/funkyotc/puzzleverse/watersort/data/WaterSortPregenerated.kt")
    val code = buildString {
        appendLine("package com.funkyotc.puzzleverse.watersort.data")
        appendLine()
        appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
        appendLine()
        appendLine("data class PregeneratedWaterSortLevel(")
        appendLine("    override val id: String, override val difficulty: String,")
        appendLine("    val bottles: List<List<Int>>, val numColors: Int, val height: Int,")
        appendLine("    val winningMoves: List<Pair<Int, Int>>")
        appendLine(") : BrowseablePuzzle {")
        appendLine("    override val label: String get() = id.substringAfterLast('_')")
        appendLine("    override val subtitle: String get() = \"\$numColors colors, \${bottles.size} bottles\"")
        appendLine("}")
        appendLine()
        appendLine("object WaterSortPregenerated {")
        appendLine("    val ALL_LEVELS: List<PregeneratedWaterSortLevel> by lazy { listOf(")
        for (index in levels.chunked(8).indices) appendLine("        batch$index(),")
        appendLine("    ).flatten() }")
        for ((index, batch) in levels.chunked(8).withIndex()) {
            appendLine("    private fun batch$index(): List<PregeneratedWaterSortLevel> = listOf(")
            for (level in batch) {
                val bottleSource = level.bottles.joinToString(", ") { bottle ->
                    "listOf(${bottle.joinToString(", ")})"
                }
                val moveSource = level.winningMoves.joinToString(", ") { (from, to) -> "$from to $to" }
                appendLine("        PregeneratedWaterSortLevel(\"${level.id}\", \"${level.difficulty}\", " +
                    "listOf($bottleSource), ${level.numColors}, ${level.height}, listOf($moveSource)),")
            }
            appendLine("    )")
        }
        appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedWaterSortLevel>> = ALL_LEVELS.groupBy { it.difficulty }")
        appendLine("    fun getPuzzleById(id: String): PregeneratedWaterSortLevel? = ALL_LEVELS.find { it.id == id }")
        appendLine("    fun getRandomPuzzle(difficulty: String? = null): PregeneratedWaterSortLevel {")
        appendLine("        val pool = if (difficulty != null) PUZZLES_BY_DIFFICULTY[difficulty] else ALL_LEVELS")
        appendLine("        return pool?.random() ?: ALL_LEVELS.first()")
        appendLine("    }")
        appendLine("}")
    }
    target.parentFile?.mkdirs()
    target.writeText(code)
    println("Wrote ${levels.size} Water Sort levels to ${target.absolutePath}")
}

fun main() = main(emptyArray())
