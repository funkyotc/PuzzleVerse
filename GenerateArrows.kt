import java.io.File
import kotlin.random.Random

data class Coordinate(val x: Int, val y: Int)
data class Dir(val dx: Int, val dy: Int, val name: String)

val UP = Dir(0, -1, "UP")
val DOWN = Dir(0, 1, "DOWN")
val LEFT = Dir(-1, 0, "LEFT")
val RIGHT = Dir(1, 0, "RIGHT")

fun opposite(d: Dir): Dir = when (d) {
    UP -> DOWN
    DOWN -> UP
    LEFT -> RIGHT
    RIGHT -> LEFT
}

data class Arrow(val id: Int, val segments: List<Coordinate>, val direction: Dir, val color: Int)

/**
 * Generates a single Arrow Escape puzzle.
 *
 * Convention (matching the runtime model in GridState.kt):
 *  - segments.first() is the HEAD, placed on a grid edge.
 *  - The body extends in the direction OPPOSITE to the arrow's travel direction,
 *    so that when the arrow flies off in `direction`, the head leads and the body
 *    trails correctly behind it (segments are collinear with travel axis).
 *  - Arrows are placed by pushing the head backwards from the edge so that the
 *    final resting shape leaves each arrow free to slide straight out without
 *    blocking another arrow's only exit.
 */
fun generatePuzzle(
    width: Int,
    height: Int,
    density: Float,
    random: Random
): List<Arrow> {
    val grid = Array(height) { IntArray(width) { 0 } }
    val arrows = mutableListOf<Arrow>()
    var nextId = 1

    val colors = listOf(1, 2, 3, 4, 5, 6, 7)
    val targetCells = (width * height * density).toInt()
    var filledCells = 0

    var attempts = 0
    val maxAttempts = targetCells * 20

    while (filledCells < targetCells && attempts < maxAttempts) {
        attempts++

        val spawnDir = when (random.nextInt(4)) {
            0 -> UP    // head on bottom edge, flies UP
            1 -> DOWN  // head on top edge, flies DOWN
            2 -> LEFT  // head on right edge, flies LEFT
            else -> RIGHT // head on left edge, flies RIGHT
        }

        val headX: Int
        val headY: Int
        when (spawnDir) {
            UP -> { headX = random.nextInt(width); headY = height - 1 }
            DOWN -> { headX = random.nextInt(width); headY = 0 }
            LEFT -> { headX = width - 1; headY = random.nextInt(height) }
            RIGHT -> { headX = 0; headY = random.nextInt(height) }
        }

        if (grid[headY][headX] != 0) continue

        val length = random.nextInt(1, 6)

        // Body grows in the direction opposite to travel, from the head inward.
        val bodyDir = opposite(spawnDir)
        val segments = mutableListOf(Coordinate(headX, headY))
        var cx = headX
        var cy = headY
        var curDir = bodyDir

        for (i in 1 until length) {
            if (random.nextFloat() < 0.25f && segments.size >= 1) {
                // Bend, but keep the new body direction consistent with travel:
                // travel axis stays the same, so we only allow 90-degree turns
                // that keep the arrow aligned to a single row/column OR add an
                // L-bend that still ends up collinear-friendly.
                val turnOptions = listOf(UP, DOWN, LEFT, RIGHT)
                    .filter { it != curDir && it != opposite(curDir) }
                curDir = turnOptions.random(random)
            }

            val nx = cx + curDir.dx
            val ny = cy + curDir.dy
            if (nx !in 0 until width || ny !in 0 until height) break
            if (grid[ny][nx] != 0) break
            if (segments.any { it.x == nx && it.y == ny }) break
            cx = nx; cy = ny
            segments.add(Coordinate(cx, cy))
        }

        val arrowId = nextId++
        for (seg in segments) {
            grid[seg.y][seg.x] = arrowId
            filledCells++
        }
        arrows.add(Arrow(arrowId, segments, spawnDir, colors.random(random)))
    }

    return arrows
}

fun main() {
    val random = Random(20240716)
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.arrowescape.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.arrowescape.model.Arrow")
    sb.appendLine("import com.funkyotc.puzzleverse.arrowescape.model.Coordinate")
    sb.appendLine("import com.funkyotc.puzzleverse.arrowescape.model.Direction")
    sb.appendLine()
    sb.appendLine("object ArrowEscapePregenerated {")
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY = mapOf(")

    // (difficulty, size, puzzlesPerDifficulty)
    val configs = listOf(
        Triple("Easy", 10, 8),
        Triple("Medium", 20, 8),
        Triple("Hard", 30, 8)
    )

    for ((diff, size, count) in configs) {
        sb.appendLine("        \"$diff\" to listOf(")
        for (p in 1..count) {
            val arrows = generatePuzzle(size, size, 0.4f, random)
            sb.appendLine("            listOf(")
            for (a in arrows) {
                val segStr = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.appendLine("                Arrow(${a.id}, listOf($segStr), Direction.${a.direction.name}, ${a.color}),")
            }
            sb.appendLine("            ),")
        }
        sb.appendLine("        ),")
    }

    sb.appendLine("    )")
    sb.appendLine("}")

    File("app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt")
        .apply { parentFile.mkdirs() }
        .writeText(sb.toString())

    println("Generated Arrow Escape puzzles.")
}
