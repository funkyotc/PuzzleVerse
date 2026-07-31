package com.funkyotc.puzzleverse.generators.cubeshooter

import java.io.File
import kotlin.random.Random

/**
 * Standalone Kotlin runner for generating Cube Shooter levels into CubeShooterPregenerated.kt.
 */
fun main(args: Array<String>) {
    val targetFile = File("app/src/main/java/com/funkyotc/puzzleverse/cubeshooter/data/CubeShooterPregenerated.kt")
    println("Generating Cube Shooter levels into ${targetFile.absolutePath}...")

    val random = Random(20240731)
    val sb = StringBuilder()

    sb.appendLine("package com.funkyotc.puzzleverse.cubeshooter.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedLevel(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val cols: Int,")
    sb.appendLine("    val rows: Int,")
    sb.appendLine("    val grid: List<List<Int?>>,")
    sb.appendLine("    val tray: List<Tank>")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = id.substringAfterLast('_')")
    sb.appendLine("    override val subtitle: String get() = \"\${cols}x\${rows}\"")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object CubeShooterPregenerated {")
    sb.appendLine()
    sb.appendLine("    private fun parseLevel(")
    sb.appendLine("        id: String, difficulty: String, cols: Int, rows: Int,")
    sb.appendLine("        grid: String, tray: String")
    sb.appendLine("    ): PregeneratedLevel {")
    sb.appendLine("        val parsedGrid = grid.split('|').map { row ->")
    sb.appendLine("            row.split(',').map { it.toIntOrNull() }")
    sb.appendLine("        }")
    sb.appendLine("        val parsedTray = tray.split(';').map { part ->")
    sb.appendLine("            val (c, a) = part.split(',').map { it.toInt() }")
    sb.appendLine("            Tank(c, a)")
    sb.appendLine("        }")
    sb.appendLine("        return PregeneratedLevel(id, difficulty, cols, rows, parsedGrid, parsedTray)")
    sb.appendLine("    }")
    sb.appendLine()

    val chunkNames = mutableListOf<String>()
    var levelIndex = 0

    val difficulties = listOf(
        Triple("Easy", 20, 15 to 20),
        Triple("Medium", 20, 20 to 30),
        Triple("Hard", 20, 30 to 30),
        Triple("Pictures", 10, 20 to 20),
        Triple("Hard Pictures", 10, 30 to 30)
    )

    for ((diff, count, sizePair) in difficulties) {
        val (cols, rows) = sizePair
        val diffTag = diff.lowercase().replace(" ", "_")

        for (i in 1..count) {
            val chunkName = "CHUNK_${diffTag.uppercase()}_$i"
            chunkNames.add(chunkName)

            val levelId = "cubeshooter_${diffTag}_${i.toString().padStart(3, '0')}"
            val numColors = when (diff) {
                "Easy" -> 4
                "Medium" -> 6
                "Hard" -> 8
                "Pictures" -> 5
                else -> 7
            }

            // Generate grid with color bands / blocks
            val colorCounts = IntArray(numColors) { 0 }
            val gridLines = mutableListOf<String>()

            for (r in 0 until rows) {
                val rowCells = mutableListOf<String>()
                val bandColor = (r * numColors / rows) % numColors
                for (c in 0 until cols) {
                    val color = if ((r + c) % 11 == 0 && random.nextFloat() < 0.15f) {
                        (bandColor + 1) % numColors
                    } else {
                        bandColor
                    }
                    rowCells.add(color.toString())
                    colorCounts[color]++
                }
                gridLines.add(rowCells.joinToString(","))
            }
            val gridStr = gridLines.joinToString("|")

            // Build tanks matching exact color counts
            val trayTanks = mutableListOf<Pair<Int, Int>>()
            for (color in 0 until numColors) {
                var remaining = colorCounts[color]
                while (remaining > 0) {
                    val ammo = minOf(remaining, if (remaining >= 20) 20 else if (remaining >= 15) 15 else if (remaining >= 10) 10 else remaining)
                    trayTanks.add(color to ammo)
                    remaining -= ammo
                }
            }
            trayTanks.shuffle(random)
            val trayStr = trayTanks.joinToString(";") { "${it.first},${it.second}" }

            sb.appendLine("    private val $chunkName by lazy {")
            sb.appendLine("        parseLevel(")
            sb.appendLine("            \"$levelId\", \"$diff\", $cols, $rows,")
            sb.appendLine("            grid = \"$gridStr\",")
            sb.appendLine("            tray = \"$trayStr\"")
            sb.appendLine("        )")
            sb.appendLine("    }")
            sb.appendLine()
            levelIndex++
        }
    }

    sb.appendLine("    val ALL_LEVELS: List<PregeneratedLevel> by lazy {")
    sb.appendLine("        listOf(${chunkNames.joinToString(", ")})")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    val LEVELS_BY_DIFFICULTY: Map<String, List<PregeneratedLevel>> by lazy { ALL_LEVELS.groupBy { it.difficulty } }")
    sb.appendLine("}")

    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())
    println("Successfully generated $levelIndex levels into ${targetFile.path}")
}

fun main() {
    main(emptyArray())
}
