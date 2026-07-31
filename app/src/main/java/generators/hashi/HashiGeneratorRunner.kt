package generators.hashi

import com.funkyotc.puzzleverse.hashi.data.HashiPuzzle
import com.funkyotc.puzzleverse.hashi.generator.HashiGenerator
import java.io.File

fun main(args: Array<String>) {
    println("Generating Hashi island bridge topologies...")

    val difficulties = listOf("Easy", "Medium", "Hard", "Expert")
    val puzzlesByDiff = mutableMapOf<String, List<HashiPuzzle>>()

    for ((diffIdx, diff) in difficulties.withIndex()) {
        val list = mutableListOf<HashiPuzzle>()
        val baseSeed = (diffIdx + 1) * 1000L
        for (i in 1..10) {
            val gen = HashiGenerator(seed = baseSeed + i)
            list.add(gen.generate(diff))
        }
        puzzlesByDiff[diff] = list
    }

    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.hashi.data")
    sb.appendLine()
    sb.appendLine("object HashiPregenerated {")
    sb.appendLine()

    for (diff in difficulties) {
        val uppercaseName = "${diff.uppercase()}_PUZZLES"
        val puzzles = puzzlesByDiff[diff] ?: emptyList()
        sb.appendLine("    val $uppercaseName: List<HashiPuzzle> = listOf(")
        for ((idx, p) in puzzles.withIndex()) {
            val comma = if (idx < puzzles.size - 1) "," else ""
            sb.appendLine("        HashiPuzzle(")
            sb.appendLine("            id = \"${p.id}\",")
            sb.appendLine("            width = ${p.width},")
            sb.appendLine("            height = ${p.height},")
            sb.appendLine("            difficulty = \"${p.difficulty}\",")
            sb.appendLine("            islands = listOf(")
            for ((iIdx, island) in p.islands.withIndex()) {
                val iComma = if (iIdx < p.islands.size - 1) "," else ""
                sb.appendLine("                Island(${island.x}, ${island.y}, ${island.requiredBridges})$iComma")
            }
            sb.appendLine("            )")
            sb.appendLine("        )$comma")
        }
        sb.appendLine("    )")
        sb.appendLine()
    }

    sb.appendLine("    val ALL_PUZZLES: List<HashiPuzzle> = EASY_PUZZLES + MEDIUM_PUZZLES + HARD_PUZZLES + EXPERT_PUZZLES")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<HashiPuzzle>> = mapOf(")
    sb.appendLine("        \"Easy\" to EASY_PUZZLES,")
    sb.appendLine("        \"Medium\" to MEDIUM_PUZZLES,")
    sb.appendLine("        \"Hard\" to HARD_PUZZLES,")
    sb.appendLine("        \"Expert\" to EXPERT_PUZZLES")
    sb.appendLine("    )")
    sb.appendLine()
    sb.appendLine("    fun getPuzzle(id: String): HashiPuzzle? {")
    sb.appendLine("        return ALL_PUZZLES.find { it.id == id }")
    sb.appendLine("    }")
    sb.appendLine("}")

    val targetFileCandidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/hashi/data/HashiPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/hashi/data/HashiPregenerated.kt")
    )
    val targetFile = targetFileCandidates.find { it.parentFile.exists() } ?: targetFileCandidates.first()
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())
    println("Successfully generated Hashi puzzles into ${targetFile.absolutePath}")
}
