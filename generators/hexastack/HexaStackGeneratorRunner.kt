package generators.hexastack

import com.funkyotc.puzzleverse.hexastack.data.HexaStackGenerator
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLevel
import java.io.File

fun main(args: Array<String>) {
    println("Generating Hexa Stack pregenerated stages...")
    
    val diffConfigs = listOf(
        Triple("Easy", HexaStackGenerator.Config(radius = 2, numColors = 3, targetFraction = 0.65), 20),
        Triple("Medium", HexaStackGenerator.Config(radius = 2, numColors = 4, targetFraction = 0.65), 20),
        Triple("Hard", HexaStackGenerator.Config(radius = 3, numColors = 4, targetFraction = 0.65), 20),
        Triple("Expert", HexaStackGenerator.Config(radius = 3, numColors = 5, targetFraction = 0.65), 20)
    )

    val generatedByDiff = mutableMapOf<String, MutableList<HexaStackLevel>>()

    for ((difficulty, config, count) in diffConfigs) {
        val list = mutableListOf<HexaStackLevel>()
        println("Generating $count $difficulty levels...")
        var seed = 1000L + difficulty.hashCode()
        var generatedCount = 0
        while (generatedCount < count) {
            val id = "${difficulty.lowercase()}-$generatedCount"
            val level = HexaStackGenerator.generate(id, difficulty, config, seed)
            if (level != null) {
                list.add(level)
                generatedCount++
            }
            seed += 137L
        }
        generatedByDiff[difficulty] = list
    }

    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.hexastack.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedHexaStack(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val radius: Int,")
    sb.appendLine("    val scoreTarget: Int,")
    sb.appendLine("    val initialStacks: Map<AxialCoord, List<Int>> = emptyMap(),")
    sb.appendLine("    val spawnDeck: List<List<Int>>")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = \"Puzzle \${id.takeLast(3)}\"")
    sb.appendLine("    override val subtitle: String get() = \"Target \$scoreTarget\"")
    sb.appendLine()
    sb.appendLine("    fun toLevel(): HexaStackLevel = HexaStackLevel(id, difficulty, radius, scoreTarget, initialStacks, spawnDeck)")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object HexaStackPregenerated {")
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedHexaStack> = easyLevels() + mediumLevels() + hardLevels() + expertLevels()")
    sb.appendLine("    val LEVELS_BY_DIFFICULTY: Map<String, List<PregeneratedHexaStack>> = ALL_PUZZLES.groupBy { it.difficulty }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): PregeneratedHexaStack? = ALL_PUZZLES.firstOrNull { it.id == id }")
    sb.appendLine()

    for (difficulty in listOf("Easy", "Medium", "Hard", "Expert")) {
        val funcName = "${difficulty.lowercase()}Levels"
        val levels = generatedByDiff[difficulty] ?: emptyList()
        sb.appendLine("    private fun $funcName() = listOf(")
        for (lvl in levels) {
            sb.appendLine(HexaStackGenerator.renderKotlin(lvl))
        }
        sb.appendLine("    )")
        sb.appendLine()
    }

    sb.appendLine("}")

    val targetFileCandidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/hexastack/data/HexaStackPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/hexastack/data/HexaStackPregenerated.kt")
    )
    val targetFile = targetFileCandidates.find { it.parentFile.exists() } ?: targetFileCandidates.first()
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())
    println("Successfully generated Hexa Stack levels into ${targetFile.absolutePath}")
}
