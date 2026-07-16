import java.io.File
import kotlin.random.Random

data class Coordinate(val x: Int, val y: Int)

enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    val opposite: Direction
        get() = when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
}

data class Arrow(
    val id: Int,
    val segments: List<Coordinate>,
    val direction: Direction,
    val color: Int
)

fun generate(width: Int, height: Int, density: Float, random: Random = Random): List<Arrow> {
    val grid = Array(height) { IntArray(width) { 0 } }
    val arrows = mutableListOf<Arrow>()
    var nextId = 1
    
    val targetCells = (width * height * density).toInt()
    var filledCells = 0
    val colors = listOf(1, 2, 3, 4, 5, 6, 7)

    var attempts = 0
    val maxAttempts = targetCells * 20

    while (filledCells < targetCells && attempts < maxAttempts) {
        attempts++
        
        val edge = random.nextInt(4)
        val spawnDir = when (edge) {
            0 -> Direction.UP    // Spawns at bottom edge, looking UP, pushing backwards means moving DOWN
            1 -> Direction.DOWN  // Spawns at top edge, looking DOWN, pushing backwards means moving UP
            2 -> Direction.LEFT  // Spawns at right edge, looking LEFT, pushing backwards means moving RIGHT
            else -> Direction.RIGHT // Spawns at left edge, looking RIGHT, pushing backwards means moving LEFT
        }

        val headX: Int
        val headY: Int
        when (spawnDir) {
            Direction.UP -> {
                headX = random.nextInt(width)
                headY = height - 1
            }
            Direction.DOWN -> {
                headX = random.nextInt(width)
                headY = 0
            }
            Direction.LEFT -> {
                headX = width - 1
                headY = random.nextInt(height)
            }
            Direction.RIGHT -> {
                headX = 0
                headY = random.nextInt(height)
            }
        }

        if (grid[headY][headX] != 0) continue

        val length = random.nextInt(1, 6)
        
        val segments = mutableListOf<Coordinate>()
        var currentX = headX
        var currentY = headY
        var currentDir = spawnDir.opposite
        
        segments.add(Coordinate(currentX, currentY))

        // We push the head backwards. If the head moves, it traces the body.
        // Actually, the simplest way is to trace the head backward for 'length' steps.
        // Wait, if an arrow has length N, when we push it backward, it traces a path.
        // Let's just generate its final resting shape on the grid.
        
        for (i in 1 until length) {
            if (random.nextFloat() < 0.3f) {
                val turnOptions = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
                    .filter { it != currentDir && it != currentDir.opposite }
                currentDir = turnOptions.random(random)
            }

            val nextX = currentX + currentDir.dx
            val nextY = currentY + currentDir.dy

            if (nextX !in 0 until width || nextY !in 0 until height) break
            if (grid[nextY][nextX] != 0) break
            if (segments.contains(Coordinate(nextX, nextY))) break

            currentX = nextX
            currentY = nextY
            segments.add(Coordinate(currentX, currentY))
        }

        val arrowId = nextId++
        for (seg in segments) {
            grid[seg.y][seg.x] = arrowId
            filledCells++
        }
        
        arrows.add(Arrow(id = arrowId, segments = segments, direction = spawnDir, color = colors.random(random)))
    }

    return arrows
}

fun main() {
    val random = Random(42)
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.arrowescape.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.arrowescape.model.Arrow")
    sb.appendLine("import com.funkyotc.puzzleverse.arrowescape.model.Coordinate")
    sb.appendLine("import com.funkyotc.puzzleverse.arrowescape.model.Direction")
    sb.appendLine()
    sb.appendLine("object ArrowEscapePregenerated {")
    
    val sizes = listOf(10, 20, 30)
    val difficulties = listOf("Easy", "Medium", "Hard")
    
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY = mapOf(")
    
    for (i in sizes.indices) {
        val size = sizes[i]
        val diff = difficulties[i]
        
        sb.appendLine("        \"$diff\" to listOf(")
        for (j in 1..5) {
            val arrows = generate(size, size, 0.4f, random)
            sb.appendLine("            listOf(")
            for (arrow in arrows) {
                val segStr = arrow.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.appendLine("                Arrow(${arrow.id}, listOf($segStr), Direction.${arrow.direction}, ${arrow.color}),")
            }
            sb.appendLine("            ),")
        }
        sb.appendLine("        ),")
    }
    
    sb.appendLine("    )")
    sb.appendLine("}")
    
    File("app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt").apply {
        parentFile.mkdirs()
        writeText(sb.toString())
    }
    println("Generated puzzles.")
}
