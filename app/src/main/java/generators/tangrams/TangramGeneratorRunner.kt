package generators.tangrams

import java.io.File
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.sqrt

data class Point2D(val x: Float, val y: Float)

enum class TangramPieceType {
    LARGE_TRIANGLE,
    MEDIUM_TRIANGLE,
    SQUARE,
    PARALLELOGRAM,
    SMALL_TRIANGLE
}

data class ParsedPiecePlacement(
    val pieceType: TangramPieceType,
    val centerX: Float,
    val centerY: Float,
    val vertices: List<Point2D>
)

data class SvgPuzzleData(
    val id: String,
    val difficulty: String,
    val name: String,
    val assetFileName: String,
    val emoji: String,
    val placements: List<ParsedPiecePlacement>
)

/**
 * Standalone Kotlin runner for parsing SVG silhouette vector assets into piece placement definitions
 * for Tangrams, updating TangramsPregenerated.kt.
 */
fun main(args: Array<String>) {
    println("==========================================================================")
    println("TANGRAM SVG SILHOUETTE VECTOR ASSET GENERATOR RUNNER")
    println("==========================================================================")

    val assetDirectories = listOf(
        File("app/src/main/assets/tangrams"),
        File("tangram-svg"),
        File("manual-tangrams"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/assets/tangrams")
    )

    val assetDir = assetDirectories.find { it.exists() && it.isDirectory }
    if (assetDir == null) {
        println("Error: Could not find tangrams asset directory!")
        return
    }

    println("Reading SVG assets from: ${assetDir.absolutePath}")

    val puzzleMetaList = listOf(
        Triple("triangle", "Easy", Pair("Triangle", "🔺")),
        Triple("square", "Easy", Pair("Square", "🟩")),
        Triple("rhombus", "Easy", Pair("Rhombus", "🔷")),
        Triple("gem", "Easy", Pair("Gem", "💎")),
        Triple("candle", "Easy", Pair("Candle", "🕯️")),
        Triple("house", "Easy", Pair("House", "🏠")),
        Triple("koi", "Easy", Pair("Koi", "🎏")),
        Triple("snake", "Easy", Pair("Snake", "🐍")),
        Triple("swan", "Medium", Pair("Swan", "🦢")),
        Triple("cat", "Medium", Pair("Cat", "🐱")),
        Triple("dog", "Medium", Pair("Dog", "🐕")),
        Triple("fox", "Medium", Pair("Fox", "🦊")),
        Triple("tree", "Medium", Pair("Tree", "🌲")),
        Triple("bridge", "Medium", Pair("Bridge", "🌉")),
        Triple("person", "Medium", Pair("Person", "🧍")),
        Triple("boat", "Hard", Pair("Boat", "⛵"))
    )

    val parsedPuzzles = mutableListOf<SvgPuzzleData>()

    for ((id, difficulty, nameAndEmoji) in puzzleMetaList) {
        val (name, emoji) = nameAndEmoji
        val svgFile = File(assetDir, "$id.svg")
        if (!svgFile.exists()) {
            println("Warning: SVG file not found for puzzle '$id' at ${svgFile.absolutePath}")
            continue
        }

        val svgText = svgFile.readText()
        val placements = parseSvgPieces(svgText)
        println("Parsed puzzle '$id' ($name): ${placements.size} piece placements extracted.")
        parsedPuzzles.add(
            SvgPuzzleData(
                id = id,
                difficulty = difficulty,
                name = name,
                assetFileName = "tangrams/$id.svg",
                emoji = emoji,
                placements = placements
            )
        )
    }

    println("\nUpdating TangramsPregenerated.kt...")
    updateTangramsPregeneratedFile(parsedPuzzles)
}

fun parseSvgPieces(svgRawText: String): List<ParsedPiecePlacement> {
    val pathRegex = Regex("""\bd="([^"]+)"""", RegexOption.IGNORE_CASE)
    val matches = pathRegex.findAll(svgRawText).toList()

    val rawPiecePaths = mutableListOf<List<Point2D>>()
    for (match in matches) {
        val pathStr = match.groupValues[1]
        val vertices = parseSvgPathData(pathStr)
        if (vertices.size >= 3) {
            rawPiecePaths.add(vertices)
        }
    }

    if (rawPiecePaths.isEmpty()) return emptyList()

    val pieceAreas = rawPiecePaths.map { calculateShoelaceArea(it) }
    val totalArea = pieceAreas.sum()
    val expectedUnitArea = if (totalArea > 0f) totalArea / 16.0f else 1.0f

    val results = mutableListOf<ParsedPiecePlacement>()

    for (i in rawPiecePaths.indices) {
        val vertices = rawPiecePaths[i]
        val area = pieceAreas[i]
        val perimeter = calculatePerimeter(vertices)
        val pieceType = classifyTangramPiece(area, perimeter, expectedUnitArea)

        val minX = vertices.minOf { it.x }
        val maxX = vertices.maxOf { it.x }
        val minY = vertices.minOf { it.y }
        val maxY = vertices.maxOf { it.y }
        val centerX = (minX + maxX) / 2f
        val centerY = (minY + maxY) / 2f

        results.add(
            ParsedPiecePlacement(
                pieceType = pieceType,
                centerX = centerX,
                centerY = centerY,
                vertices = vertices
            )
        )
    }

    return results
}

fun parseSvgPathData(pathStr: String): List<Point2D> {
    val sciRegex = Regex("""([+-]?\d+(?:\.\d+)?)[eE]([+-]?\d+)""")
    val sanitized = pathStr.replace(sciRegex) { match ->
        try {
            match.value.toDouble().toBigDecimal().toPlainString()
        } catch (e: Exception) {
            "0"
        }
    }

    val tokenRegex = Regex("""([a-zA-Z])|([-+]?\d*\.?\d+(?:[eE][-+]?\d+)?)""")
    val tokens = tokenRegex.findAll(sanitized).map { it.value }.toList()

    val vertices = mutableListOf<Point2D>()
    var currentX = 0f
    var currentY = 0f
    var startX = 0f
    var startY = 0f
    var currentCommand = 'M'
    var i = 0

    while (i < tokens.size) {
        val token = tokens[i]
        if (token.length == 1 && token[0].isLetter()) {
            currentCommand = token[0]
            i++
            if (currentCommand == 'Z' || currentCommand == 'z') {
                currentX = startX
                currentY = startY
            }
            continue
        }

        when (currentCommand) {
            'M' -> {
                val x = token.toFloatOrNull() ?: 0f
                val y = tokens.getOrNull(i + 1)?.toFloatOrNull() ?: 0f
                currentX = x
                currentY = y
                startX = x
                startY = y
                vertices.add(Point2D(currentX, currentY))
                i += 2
                currentCommand = 'L'
            }
            'm' -> {
                val dx = token.toFloatOrNull() ?: 0f
                val dy = tokens.getOrNull(i + 1)?.toFloatOrNull() ?: 0f
                currentX += dx
                currentY += dy
                startX = currentX
                startY = currentY
                vertices.add(Point2D(currentX, currentY))
                i += 2
                currentCommand = 'l'
            }
            'L' -> {
                val x = token.toFloatOrNull() ?: 0f
                val y = tokens.getOrNull(i + 1)?.toFloatOrNull() ?: 0f
                currentX = x
                currentY = y
                vertices.add(Point2D(currentX, currentY))
                i += 2
            }
            'l' -> {
                val dx = token.toFloatOrNull() ?: 0f
                val dy = tokens.getOrNull(i + 1)?.toFloatOrNull() ?: 0f
                currentX += dx
                currentY += dy
                vertices.add(Point2D(currentX, currentY))
                i += 2
            }
            'H' -> {
                val x = token.toFloatOrNull() ?: 0f
                currentX = x
                vertices.add(Point2D(currentX, currentY))
                i++
            }
            'h' -> {
                val dx = token.toFloatOrNull() ?: 0f
                currentX += dx
                vertices.add(Point2D(currentX, currentY))
                i++
            }
            'V' -> {
                val y = token.toFloatOrNull() ?: 0f
                currentY = y
                vertices.add(Point2D(currentX, currentY))
                i++
            }
            'v' -> {
                val dy = token.toFloatOrNull() ?: 0f
                currentY += dy
                vertices.add(Point2D(currentX, currentY))
                i++
            }
            else -> i++
        }
    }
    return vertices
}

fun calculateShoelaceArea(vertices: List<Point2D>): Float {
    if (vertices.size < 3) return 0f
    var areaSum = 0f
    val n = vertices.size
    for (i in 0 until n) {
        val j = (i + 1) % n
        areaSum += (vertices[i].x * vertices[j].y - vertices[j].x * vertices[i].y)
    }
    return abs(areaSum) / 2f
}

fun calculatePerimeter(vertices: List<Point2D>): Float {
    if (vertices.size < 2) return 0f
    var peri = 0f
    val n = vertices.size
    for (i in 0 until n) {
        val j = (i + 1) % n
        val dx = vertices[j].x - vertices[i].x
        val dy = vertices[j].y - vertices[i].y
        peri += hypot(dx.toDouble(), dy.toDouble()).toFloat()
    }
    return peri
}

fun classifyTangramPiece(area: Float, perimeter: Float, expectedUnitArea: Float): TangramPieceType {
    val relArea = area / expectedUnitArea
    if (relArea > 3.0f) return TangramPieceType.LARGE_TRIANGLE
    if (relArea < 1.5f) return TangramPieceType.SMALL_TRIANGLE

    val perimeterRatio = if (area > 0f) perimeter / sqrt(area.toDouble()).toFloat() else 0f
    return when {
        perimeterRatio < 4.2f -> TangramPieceType.SQUARE
        perimeterRatio < 4.75f -> TangramPieceType.MEDIUM_TRIANGLE
        else -> TangramPieceType.PARALLELOGRAM
    }
}

private fun updateTangramsPregeneratedFile(puzzles: List<SvgPuzzleData>) {
    val targetFileCandidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/tangrams/data/TangramsPregenerated.kt"),
        File("src/main/java/com/funkyotc/puzzleverse/tangrams/data/TangramsPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/tangrams/data/TangramsPregenerated.kt")
    )
    val file = targetFileCandidates.find { it.exists() }
    if (file == null) {
        println("Error: TangramsPregenerated.kt not found at target locations.")
        return
    }

    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.tangrams.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("enum class TangramPieceType {")
    sb.appendLine("    LARGE_TRIANGLE,")
    sb.appendLine("    MEDIUM_TRIANGLE,")
    sb.appendLine("    SQUARE,")
    sb.appendLine("    PARALLELOGRAM,")
    sb.appendLine("    SMALL_TRIANGLE")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("data class TangramPiecePlacement(")
    sb.appendLine("    val pieceType: TangramPieceType,")
    sb.appendLine("    val centerX: Float,")
    sb.appendLine("    val centerY: Float,")
    sb.appendLine("    val vertices: List<Pair<Float, Float>>")
    sb.appendLine(")")
    sb.appendLine()
    sb.appendLine("data class TangramPuzzleInfo(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val name: String,")
    sb.appendLine("    val assetFileName: String,")
    sb.appendLine("    val emoji: String,")
    sb.appendLine("    val piecePlacements: List<TangramPiecePlacement> = emptyList()")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = name")
    sb.appendLine("    override val subtitle: String get() = emoji")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object TangramsPregenerated {")
    sb.appendLine("    val ALL_PUZZLES = listOf(")

    for (p in puzzles) {
        sb.appendLine("        TangramPuzzleInfo(")
        sb.appendLine("            id = \"${p.id}\",")
        sb.appendLine("            difficulty = \"${p.difficulty}\",")
        sb.appendLine("            name = \"${p.name}\",")
        sb.appendLine("            assetFileName = \"${p.assetFileName}\",")
        sb.appendLine("            emoji = \"${p.emoji}\",")
        sb.appendLine("            piecePlacements = listOf(")
        for (place in p.placements) {
            val vStr = place.vertices.joinToString(", ") { "Pair(${it.x}f, ${it.y}f)" }
            sb.appendLine("                TangramPiecePlacement(TangramPieceType.${place.pieceType.name}, ${place.centerX}f, ${place.centerY}f, listOf($vStr)),")
        }
        sb.appendLine("            )")
        sb.appendLine("        ),")
    }

    sb.appendLine("    )")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<TangramPuzzleInfo>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): TangramPuzzleInfo? = ALL_PUZZLES.find { it.id == id }")
    sb.appendLine("}")

    file.writeText(sb.toString())
    println("Successfully updated ${file.absolutePath} with pregenerated SVG piece placement definitions!")
}
