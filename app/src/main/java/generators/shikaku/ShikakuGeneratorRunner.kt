package generators.shikaku

import java.io.File
import java.util.Random
import java.util.UUID

data class ShikakuCell(
    val row: Int,
    val col: Int,
    val clue: Int? = null,
    var rectangleId: String? = null
)

data class ShikakuRectangle(
    val id: String = UUID.randomUUID().toString(),
    val row: Int,
    val col: Int,
    val width: Int,
    val height: Int
)

data class ShikakuBoard(
    val cells: List<ShikakuCell>,
    val gridSize: Int,
    val seed: Long,
    val puzzleId: String,
    val isDaily: Boolean
)

class ShikakuGenerator(private val seed: Long) {
    private val random = Random(seed)

    fun generate(difficulty: String): ShikakuBoard {
        val gridSize = when (difficulty.lowercase()) {
            "easy" -> 8
            "medium" -> 10
            "hard" -> 12
            else -> 8
        }

        val maxArea = when (gridSize) {
            12 -> 14
            10 -> 12
            else -> 10
        }

        val minRectangles = (gridSize * gridSize) / maxArea
        val maxRectangles = (gridSize * gridSize) / 4.5f
        val bound = maxRectangles.toInt() + 1 - minRectangles
        val targetRectangles = if (bound > 0) random.nextInt(bound) + minRectangles else minRectangles

        var cells: List<ShikakuCell>
        var attempts = 0
        while (true) {
            val rectangles = generateRectangles(gridSize, gridSize, maxArea, targetRectangles)
            cells = createCells(gridSize, gridSize, rectangles)
            if (hasUniqueSolution(gridSize, cells)) {
                break
            }
            attempts++
            if (attempts > 500) {
                break
            }
        }

        return ShikakuBoard(
            cells = cells,
            gridSize = gridSize,
            seed = seed,
            puzzleId = "std_$seed",
            isDaily = false
        )
    }

    private fun hasValidSplit(rect: ShikakuRectangle): Boolean {
        val canSplitVertically = rect.width > 1
        val canSplitHorizontally = rect.height > 1
        if (canSplitVertically) {
            for (splitCol in 1 until rect.width) {
                if (splitCol * rect.height >= 2 && (rect.width - splitCol) * rect.height >= 2) return true
            }
        }
        if (canSplitHorizontally) {
            for (splitRow in 1 until rect.height) {
                if (rect.width * splitRow >= 2 && rect.width * (rect.height - splitRow) >= 2) return true
            }
        }
        return false
    }

    private fun generateRectangles(rows: Int, cols: Int, maxArea: Int, targetRectangles: Int): List<ShikakuRectangle> {
        val rectangles = mutableListOf(ShikakuRectangle(UUID.randomUUID().toString(), 0, 0, cols, rows))
        var attempts = 0
        val maxAttempts = 1000
        while (attempts++ < maxAttempts) {
            val oversized = rectangles.filter { it.width * it.height > maxArea }
            if (oversized.isNotEmpty()) {
                val splittableOversized = oversized.filter { hasValidSplit(it) }
                if (splittableOversized.isEmpty()) break
                val toSplit = splittableOversized[random.nextInt(splittableOversized.size)]
                if (!splitRectangle(toSplit, rectangles)) break
            } else if (rectangles.size < targetRectangles) {
                val splittable = rectangles.filter { hasValidSplit(it) }
                if (splittable.isEmpty()) break
                val toSplit = splittable[random.nextInt(splittable.size)]
                splitRectangle(toSplit, rectangles)
            } else {
                break
            }
        }
        return rectangles
    }

    private fun splitRectangle(rect: ShikakuRectangle, list: MutableList<ShikakuRectangle>): Boolean {
        val canSplitVertically = rect.width > 1
        val canSplitHorizontally = rect.height > 1
        if (!canSplitVertically && !canSplitHorizontally) return false

        val validVerticalSplits = mutableListOf<Int>()
        if (canSplitVertically) {
            for (splitCol in 1 until rect.width) {
                if (splitCol * rect.height >= 2 && (rect.width - splitCol) * rect.height >= 2) {
                    validVerticalSplits.add(splitCol)
                }
            }
        }

        val validHorizontalSplits = mutableListOf<Int>()
        if (canSplitHorizontally) {
            for (splitRow in 1 until rect.height) {
                if (rect.width * splitRow >= 2 && rect.width * (rect.height - splitRow) >= 2) {
                    validHorizontalSplits.add(splitRow)
                }
            }
        }

        val canSplitV = validVerticalSplits.isNotEmpty()
        val canSplitH = validHorizontalSplits.isNotEmpty()
        if (!canSplitV && !canSplitH) return false

        val splitVertical = when {
            canSplitV && canSplitH -> random.nextBoolean()
            canSplitV -> true
            else -> false
        }

        val r1: ShikakuRectangle
        val r2: ShikakuRectangle

        if (splitVertical) {
            val splitCol = validVerticalSplits[random.nextInt(validVerticalSplits.size)]
            r1 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col, splitCol, rect.height)
            r2 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col + splitCol, rect.width - splitCol, rect.height)
        } else {
            val splitRow = validHorizontalSplits[random.nextInt(validHorizontalSplits.size)]
            r1 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col, rect.width, splitRow)
            r2 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row + splitRow, rect.col, rect.width, rect.height - splitRow)
        }

        list.remove(rect)
        list.add(r1)
        list.add(r2)
        return true
    }

    private fun createCells(gridSize: Int, cols: Int, rectangles: List<ShikakuRectangle>): List<ShikakuCell> {
        val cells = mutableListOf<ShikakuCell>()
        for (rect in rectangles) {
            val area = rect.width * rect.height
            val clueRow = rect.row + random.nextInt(rect.height)
            val clueCol = rect.col + random.nextInt(rect.width)

            for (r in rect.row until rect.row + rect.height) {
                for (c in rect.col until rect.col + rect.width) {
                    val clue = if (r == clueRow && c == clueCol) area else null
                    cells.add(ShikakuCell(
                        row = r,
                        col = c,
                        clue = clue,
                        rectangleId = rect.id
                    ))
                }
            }
        }
        return cells
    }

    private data class ClueInfo(val r: Int, val c: Int, val area: Int)
    private data class Rect(val r: Int, val c: Int, val w: Int, val h: Int) {
        fun contains(r2: Int, c2: Int) = r2 in r until r + h && c2 in c until c + w
    }

    private fun hasUniqueSolution(gridSize: Int, cells: List<ShikakuCell>): Boolean {
        val clues = cells.filter { it.clue != null }.map { ClueInfo(it.row, it.col, it.clue!!) }
        val candidateMap = mutableListOf<List<Rect>>()
        for (clue in clues) {
            val candidates = mutableListOf<Rect>()
            for (w in 1..clue.area) {
                if (clue.area % w == 0) {
                    val h = clue.area / w
                    val minR = maxOf(0, clue.r - h + 1)
                    val maxR = minOf(gridSize - h, clue.r)
                    val minC = maxOf(0, clue.c - w + 1)
                    val maxC = minOf(gridSize - w, clue.c)

                    for (tr in minR..maxR) {
                        for (tc in minC..maxC) {
                            val rect = Rect(tr, tc, w, h)
                            var valid = true
                            for (other in clues) {
                                if (other != clue && rect.contains(other.r, other.c)) {
                                    valid = false
                                    break
                                }
                            }
                            if (valid) candidates.add(rect)
                        }
                    }
                }
            }
            if (candidates.isEmpty()) return false
            candidateMap.add(candidates)
        }

        val sortedIndices = clues.indices.sortedBy { candidateMap[it].size }
        val sortedCandidates = sortedIndices.map { candidateMap[it] }
        val covered = BooleanArray(gridSize * gridSize)

        fun solve(clueIndex: Int): Int {
            if (clueIndex == clues.size) return 1
            var solutions = 0
            val candidates = sortedCandidates[clueIndex]

            for (rect in candidates) {
                var canPlace = true
                for (r in rect.r until rect.r + rect.h) {
                    for (c in rect.c until rect.c + rect.w) {
                        if (covered[r * gridSize + c]) {
                            canPlace = false
                            break
                        }
                    }
                    if (!canPlace) break
                }

                if (canPlace) {
                    for (r in rect.r until rect.r + rect.h) {
                        for (c in rect.c until rect.c + rect.w) {
                            covered[r * gridSize + c] = true
                        }
                    }

                    solutions += solve(clueIndex + 1)

                    for (r in rect.r until rect.r + rect.h) {
                        for (c in rect.c until rect.c + rect.w) {
                            covered[r * gridSize + c] = false
                        }
                    }

                    if (solutions > 1) return 2
                }
            }
            return solutions
        }

        return solve(0) == 1
    }
}

data class PregeneratedShikakuCompact(
    val id: String,
    val difficulty: String,
    val cluesStr: String,
    val gridStr: String,
    val seed: Long
)

/**
 * ShikakuGeneratorRunner calls ShikakuGenerator to partition grids into rectangles
 * and output ShikakuPregenerated.kt.
 */
fun main(args: Array<String>) {
    val difficulties = listOf(
        Triple("Easy", 1000000L, 10),
        Triple("Medium", 2000000L, 10),
        Triple("Hard", 3000000L, 10)
    )

    val compactPuzzles = mutableListOf<PregeneratedShikakuCompact>()

    for ((diff, baseSeed, count) in difficulties) {
        for (i in 1..count) {
            val seed = baseSeed + i
            val generator = ShikakuGenerator(seed)
            val board = generator.generate(diff)

            // Format cluesStr: "r,c,value;r,c,value;..."
            val clues = board.cells.filter { it.clue != null }
                .map { "${it.row},${it.col},${it.clue}" }
                .joinToString(";")

            // Format gridStr: map rectangle IDs to sequential 1..N indices
            val rectIdMap = mutableMapOf<String, Int>()
            var nextRectId = 1
            board.cells.forEach { cell ->
                cell.rectangleId?.let { id ->
                    if (!rectIdMap.containsKey(id)) {
                        rectIdMap[id] = nextRectId++
                    }
                }
            }

            val gridRows = (0 until board.gridSize).map { r ->
                (0 until board.gridSize).map { c ->
                    val cell = board.cells.find { it.row == r && it.col == c }
                    rectIdMap[cell?.rectangleId] ?: 0
                }.joinToString(",")
            }.joinToString(";")

            val puzzleId = "${diff.lowercase()}_${String.format("%03d", i)}"
            compactPuzzles.add(
                PregeneratedShikakuCompact(
                    id = puzzleId,
                    difficulty = diff,
                    cluesStr = clues,
                    gridStr = gridRows,
                    seed = seed
                )
            )
        }
    }

    // Write ShikakuPregenerated.kt
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.shikaku.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedShikakuClue(")
    sb.appendLine("    val row: Int,")
    sb.appendLine("    val col: Int,")
    sb.appendLine("    val value: Int")
    sb.appendLine(")")
    sb.appendLine()
    sb.appendLine("data class PregeneratedShikaku(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val clues: List<PregeneratedShikakuClue>,")
    sb.appendLine("    val grid: List<List<Int>>,")
    sb.appendLine("    val seed: Long")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = \"Puzzle\"")
    sb.appendLine("    override val subtitle: String get() = \"\${grid.size}x\${grid.size}\"")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("data class PregeneratedShikakuCompact(")
    sb.appendLine("    val id: String,")
    sb.appendLine("    val difficulty: String,")
    sb.appendLine("    val cluesStr: String,")
    sb.appendLine("    val gridStr: String,")
    sb.appendLine("    val seed: Long")
    sb.appendLine(")")
    sb.appendLine()
    sb.appendLine("object ShikakuPregenerated {")
    sb.appendLine()
    sb.appendLine("    private val STATIC_PUZZLES = listOf(")

    for (p in compactPuzzles) {
        sb.appendLine("        PregeneratedShikakuCompact(")
        sb.appendLine("            id = \"${p.id}\",")
        sb.appendLine("            difficulty = \"${p.difficulty}\",")
        sb.appendLine("            cluesStr = \"${p.cluesStr}\",")
        sb.appendLine("            gridStr = \"${p.gridStr}\",")
        sb.appendLine("            seed = ${p.seed}L")
        sb.appendLine("        ),")
    }

    sb.appendLine("    )")
    sb.appendLine()
    sb.appendLine("    private val ALL_PUZZLES: List<PregeneratedShikaku> by lazy {")
    sb.appendLine("        STATIC_PUZZLES.map { compact ->")
    sb.appendLine("            val clues = compact.cluesStr.split(\";\").map { parts ->")
    sb.appendLine("                val clueParts = parts.split(\",\")")
    sb.appendLine("                PregeneratedShikakuClue(")
    sb.appendLine("                    row = clueParts[0].toInt(),")
    sb.appendLine("                    col = clueParts[1].toInt(),")
    sb.appendLine("                    value = clueParts[2].toInt()")
    sb.appendLine("                )")
    sb.appendLine("            }")
    sb.appendLine()
    sb.appendLine("            val grid = compact.gridStr.split(\";\").map { rowStr ->")
    sb.appendLine("                rowStr.split(\",\").map { it.toInt() }")
    sb.appendLine("            }")
    sb.appendLine()
    sb.appendLine("            PregeneratedShikaku(")
    sb.appendLine("                id = compact.id,")
    sb.appendLine("                difficulty = compact.difficulty,")
    sb.appendLine("                clues = clues,")
    sb.appendLine("                grid = grid,")
    sb.appendLine("                seed = compact.seed")
    sb.appendLine("            )")
    sb.appendLine("        }")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShikaku>> by lazy {")
    sb.appendLine("        ALL_PUZZLES.groupBy { it.difficulty.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): PregeneratedShikaku? = ALL_PUZZLES.find { it.id == id }")
    sb.appendLine()
    sb.appendLine("    fun getRandomPuzzle(difficulty: String): PregeneratedShikaku? {")
    sb.appendLine("        return PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()")
    sb.appendLine("    }")
    sb.appendLine("}")

    val targetFile = File("app/src/main/java/com/funkyotc/puzzleverse/shikaku/data/ShikakuPregenerated.kt")
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated ShikakuPregenerated.kt with ${compactPuzzles.size} puzzles.")
}
