package com.funkyotc.puzzleverse.constellations.generator

import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import com.funkyotc.puzzleverse.constellations.data.PregeneratedConstellation
import java.io.File
import kotlin.random.Random

/**
 * Standalone runner to generate starry node graph levels using ConstellationsPuzzleGenerator
 * and update ConstellationsPregenerated.kt.
 */
fun main(args: Array<String>) {
    val countPerDiff = args.getOrNull(0)?.toIntOrNull() ?: 10
    println("================================================================")
    println("CONSTELLATION GENERATOR RUNNER")
    println("Target levels per difficulty: $countPerDiff")
    println("================================================================")

    val generator = ConstellationsPuzzleGenerator()
    val configs = listOf(
        Triple("Easy", 5, countPerDiff),
        Triple("Medium", 6, countPerDiff),
        Triple("Hard", 8, countPerDiff / 2)
    )

    val generatedPuzzles = mutableListOf<PregeneratedConstellation>()
    var levelCounter = 1

    for ((diff, size, count) in configs) {
        println("Generating $count '$diff' ($size x $size) levels...")
        var generated = 0
        var attempts = 0

        while (generated < count && attempts < count * 50) {
            attempts++
            val seed = System.nanoTime() + levelCounter * 777L
            val puzzle = generator.generate(size, seed)

            val idStr = "Constellations_${diff}_puzzle_${String.format("%03d", generated + 1)}"
            val pregenerated = PregeneratedConstellation(
                id = idStr,
                difficulty = diff,
                size = size,
                regions = puzzle.regions,
                solution = puzzle.starPositions
            )

            generatedPuzzles.add(pregenerated)
            generated++
            levelCounter++
            println("  [$diff #${generated}/$count] ID: $idStr | Size: ${size}x${size} | Regions: ${puzzle.regions.size}")
        }
    }

    println("\nUpdating ConstellationsPregenerated.kt with ${generatedPuzzles.size} levels...")
    updateConstellationsPregeneratedFile(generatedPuzzles)
    println("Constellations pregeneration complete!")
}

private fun updateConstellationsPregeneratedFile(puzzles: List<PregeneratedConstellation>) {
    val candidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/constellations/data/ConstellationsPregenerated.kt"),
        File("src/main/java/com/funkyotc/puzzleverse/constellations/data/ConstellationsPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/constellations/data/ConstellationsPregenerated.kt")
    )
    val file = candidates.find { it.exists() } ?: candidates.first()

    val sb = StringBuilder()
    sb.append("package com.funkyotc.puzzleverse.constellations.data\n\n")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle\n\n")
    sb.append("data class PregeneratedConstellation(\n")
    sb.append("    override val id: String,\n")
    sb.append("    override val difficulty: String,\n")
    sb.append("    val size: Int,\n")
    sb.append("    val regions: Map<Int, List<Pair<Int, Int>>>,\n")
    sb.append("    val solution: List<Pair<Int, Int>>\n")
    sb.append(") : BrowseablePuzzle {\n")
    sb.append("    override val label: String get() = \"Level \${id.takeLast(3)}\"\n")
    sb.append("    override val subtitle: String get() = \"\${size}x\${size}\"\n")
    sb.append("    \n")
    sb.append("    fun toConstellationsPuzzle(): ConstellationsPuzzle {\n")
    sb.append("        val grid = List(size) { r -> List(size) { c ->\n")
    sb.append("            val regionId = regions.entries.find { it.value.contains(r to c) }?.key ?: 0\n")
    sb.append("            Cell(row = r, col = c, regionId = regionId)\n")
    sb.append("        }}\n")
    sb.append("        return ConstellationsPuzzle(size, grid, regions, solution)\n")
    sb.append("    }\n")
    sb.append("}\n\n")
    sb.append("object ConstellationsPregenerated {\n")
    sb.append("    val ALL_PUZZLES: List<PregeneratedConstellation> by lazy {\n")
    sb.append("        listOf(\n")

    for (p in puzzles) {
        sb.append("            PregeneratedConstellation(\n")
        sb.append("                id = \"${p.id}\",\n")
        sb.append("                difficulty = \"${p.difficulty}\",\n")
        sb.append("                size = ${p.size},\n")
        sb.append("                regions = mapOf(\n")
        for ((regionId, cells) in p.regions) {
            val cellPairs = cells.joinToString(", ") { "${it.first} to ${it.second}" }
            sb.append("                    $regionId to listOf($cellPairs),\n")
        }
        sb.append("                ),\n")
        val solPairs = p.solution.joinToString(", ") { "${it.first} to ${it.second}" }
        sb.append("                solution = listOf($solPairs)\n")
        sb.append("            ),\n")
    }

    sb.append("        )\n")
    sb.append("    }\n\n")
    sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedConstellation>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }\n\n")
    sb.append("    fun getPuzzleById(id: String): PregeneratedConstellation? = ALL_PUZZLES.find { it.id == id }\n")
    sb.append("}\n")

    file.writeText(sb.toString())
    println("Successfully wrote ${puzzles.size} constellation levels to ${file.absolutePath}")
}
