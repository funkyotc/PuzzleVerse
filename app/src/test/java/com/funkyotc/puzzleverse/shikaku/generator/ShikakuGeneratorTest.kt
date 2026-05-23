package com.funkyotc.puzzleverse.shikaku.generator

import com.funkyotc.puzzleverse.shikaku.data.ShikakuBoard
import org.junit.Assert.*
import org.junit.Test

class ShikakuGeneratorTest {

    @Test
    fun testGenerateEasy() {
        val generator = ShikakuGenerator(12345L)
        val board = generator.generate("easy")
        assertNotNull(board)
        assertEquals(8, board.gridSize)
        validateBoard(board)
    }

    @Test
    fun testGenerateMedium() {
        val generator = ShikakuGenerator(12345L)
        val board = generator.generate("medium")
        assertNotNull(board)
        assertEquals(10, board.gridSize)
        validateBoard(board)
    }

    @Test
    fun testGenerateHard() {
        val generator = ShikakuGenerator(12345L)
        val board = generator.generate("hard")
        assertNotNull(board)
        assertEquals(12, board.gridSize)
        validateBoard(board)
    }

    @Test
    fun testPrintCompactPuzzles() {
        val puzzleConfigs = listOf(
            // Easy 8x8 puzzles
            "easy_001" to 1000001L,
            "easy_002" to 1000002L,
            "easy_003" to 1000003L,
            "easy_004" to 1000004L,
            "easy_005" to 1000005L,
            "easy_006" to 1000006L,
            "easy_007" to 1000007L,
            "easy_008" to 1000008L,
            "easy_009" to 1000009L,
            "easy_010" to 1000010L,
            // Medium 10x10 puzzles
            "medium_001" to 2000001L,
            "medium_002" to 2000002L,
            "medium_003" to 2000003L,
            "medium_004" to 2000004L,
            "medium_005" to 2000005L,
            "medium_006" to 2000006L,
            "medium_007" to 2000007L,
            "medium_008" to 2000008L,
            "medium_009" to 2000009L,
            "medium_010" to 2000010L,
            // Hard 12x12 puzzles
            "hard_001" to 3000001L,
            "hard_002" to 3000002L,
            "hard_003" to 3000003L,
            "hard_004" to 3000004L,
            "hard_005" to 3000005L,
            "hard_006" to 3000006L,
            "hard_007" to 3000007L,
            "hard_008" to 3000008L,
            "hard_009" to 3000009L,
            "hard_010" to 3000010L
        )

        val sb = StringBuilder()
        sb.append("package com.funkyotc.puzzleverse.shikaku.data\n\n")
        sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle\n\n")
        sb.append("data class PregeneratedShikakuClue(\n")
        sb.append("    val row: Int,\n")
        sb.append("    val col: Int,\n")
        sb.append("    val value: Int\n")
        sb.append(")\n\n")
        sb.append("data class PregeneratedShikaku(\n")
        sb.append("    override val id: String,\n")
        sb.append("    override val difficulty: String,\n")
        sb.append("    val clues: List<PregeneratedShikakuClue>,\n")
        sb.append("    val grid: List<List<Int>>,\n")
        sb.append("    val seed: Long\n")
        sb.append(") : BrowseablePuzzle {\n")
        sb.append("    override val label: String get() = \"Puzzle\"\n")
        sb.append("    override val subtitle: String get() = \"\${grid.size}x\${grid.size}\"\n")
        sb.append("}\n\n")
        sb.append("data class PregeneratedShikakuCompact(\n")
        sb.append("    val id: String,\n")
        sb.append("    val difficulty: String,\n")
        sb.append("    val cluesStr: String,\n")
        sb.append("    val gridStr: String,\n")
        sb.append("    val seed: Long\n")
        sb.append(")\n\n")
        sb.append("object ShikakuPregenerated {\n\n")
        sb.append("    private val STATIC_PUZZLES = listOf(\n")

        for ((id, seed) in puzzleConfigs) {
            val difficulty = when {
                seed in 1000001L..1000010L -> "easy"
                seed in 2000001L..2000010L -> "medium"
                seed in 3000001L..3000010L -> "hard"
                else -> "easy"
            }
            val generator = ShikakuGenerator(seed)
            val board = generator.generate(difficulty)
            val gridSize = board.gridSize

            // Format clues: row,col,value;...
            val clues = board.cells.filter { it.clue != null }
            val cluesStr = clues.joinToString(";") { "${it.row},${it.col},${it.clue}" }

            // Map rectangleIds to sequential integers starting from 1
            val idMap = mutableMapOf<String, Int>()
            var nextId = 1

            // Format grid rows: semicolon separated, numbers comma separated
            val gridRows = mutableListOf<String>()
            for (r in 0 until gridSize) {
                val rowVals = mutableListOf<Int>()
                for (c in 0 until gridSize) {
                    val boardCell = board.getCell(r, c)
                    val rectId = boardCell?.rectangleId ?: ""
                    val mappedId = idMap.getOrPut(rectId) { nextId++ }
                    rowVals.add(mappedId)
                }
                gridRows.add(rowVals.joinToString(","))
            }
            val gridStr = gridRows.joinToString(";")

            sb.append("        PregeneratedShikakuCompact(\n")
            sb.append("            id = \"$id\",\n")
            sb.append("            difficulty = \"${difficulty.replaceFirstChar { it.uppercaseChar() }}\",\n")
            sb.append("            cluesStr = \"$cluesStr\",\n")
            sb.append("            gridStr = \"$gridStr\",\n")
            sb.append("            seed = ${seed}L\n")
            sb.append("        ),\n")
        }
        sb.append("    )\n\n")
        
        sb.append("    private val ALL_PUZZLES: List<PregeneratedShikaku> by lazy {\n")
        sb.append("        STATIC_PUZZLES.map { compact ->\n")
        sb.append("            val clues = compact.cluesStr.split(\";\").map { parts ->\n")
        sb.append("                val clueParts = parts.split(\",\")\n")
        sb.append("                PregeneratedShikakuClue(\n")
        sb.append("                    row = clueParts[0].toInt(),\n")
        sb.append("                    col = clueParts[1].toInt(),\n")
        sb.append("                    value = clueParts[2].toInt()\n")
        sb.append("                )\n")
        sb.append("            }\n\n")
        sb.append("            val grid = compact.gridStr.split(\";\").map { rowStr ->\n")
        sb.append("                rowStr.split(\",\").map { it.toInt() }\n")
        sb.append("            }\n\n")
        sb.append("            PregeneratedShikaku(\n")
        sb.append("                id = compact.id,\n")
        sb.append("                difficulty = compact.difficulty,\n")
        sb.append("                clues = clues,\n")
        sb.append("                grid = grid,\n")
        sb.append("                seed = compact.seed\n")
        sb.append("            )\n")
        sb.append("        }\n")
        sb.append("    }\n\n")
        sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShikaku>> by lazy {\n")
        sb.append("        ALL_PUZZLES.groupBy { it.difficulty.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }\n")
        sb.append("    }\n\n")
        sb.append("    fun getPuzzleById(id: String): PregeneratedShikaku? = ALL_PUZZLES.find { it.id == id }\n\n")
        sb.append("    fun getRandomPuzzle(difficulty: String): PregeneratedShikaku? {\n")
        sb.append("        return PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()\n")
        sb.append("    }\n")
        sb.append("}\n")

        val targetPath1 = "src/main/java/com/funkyotc/puzzleverse/shikaku/data/ShikakuPregenerated.kt"
        val targetPath2 = "app/src/main/java/com/funkyotc/puzzleverse/shikaku/data/ShikakuPregenerated.kt"
        val targetFile = if (java.io.File(targetPath1).exists()) java.io.File(targetPath1) else java.io.File(targetPath2)
        
        targetFile.parentFile.mkdirs()
        targetFile.writeText(sb.toString())
        println("Successfully wrote generated puzzles to ${targetFile.absolutePath}")
    }

    private fun validateBoard(board: ShikakuBoard) {
        val size = board.gridSize
        // 1. Verify grid size matches cells count
        assertEquals(size * size, board.cells.size)

        // 2. Verify every cell is covered by exactly one rectangle
        val cellsMap = board.cells.associateBy { Pair(it.row, it.col) }
        for (r in 0 until size) {
            for (c in 0 until size) {
                val cell = cellsMap[Pair(r, c)]
                assertNotNull("Missing cell at $r, $c", cell)
                assertNotNull("Cell at $r, $c has no rectangleId", cell?.rectangleId)
            }
        }

        // 3. Verify rectangles are valid
        val solutionRects = board.reconstructRectanglesFromCells()
        assertTrue(solutionRects.isNotEmpty())

        // Check each rectangle
        for (rect in solutionRects) {
            val rectCells = board.cells.filter { it.rectangleId == rect.id }
            val area = rect.width * rect.height
            assertEquals("Rectangle ${rect.id} size mismatch", area, rectCells.size)

            // Check that it contains exactly one clue cell with value = area
            val clueCells = rectCells.filter { it.clue != null }
            assertEquals("Rectangle ${rect.id} should have exactly 1 clue", 1, clueCells.size)
            assertEquals("Rectangle ${rect.id} clue value mismatch", area, clueCells.first().clue)
        }
    }
}
