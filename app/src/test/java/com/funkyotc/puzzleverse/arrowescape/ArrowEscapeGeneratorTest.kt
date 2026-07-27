package com.funkyotc.puzzleverse.arrowescape

import com.funkyotc.puzzleverse.arrowescape.model.ArrowEscapeGenerator
import com.funkyotc.puzzleverse.arrowescape.model.GridState
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import kotlin.random.Random

class ArrowEscapeGeneratorTest {

    @Test
    fun testGeneratedPuzzlesAre100PercentSolvable() {
        val generator = ArrowEscapeGenerator()
        for (seed in 1..20) {
            val random = Random(seed)
            val arrows = generator.generate(10, 10, 0.40f, random)
            assertTrue("Generated puzzle should contain arrows", arrows.isNotEmpty())

            val state = GridState(10, 10, arrows)
            var progress = true
            while (progress && !state.isComplete()) {
                progress = false
                val availableArrows = state.arrows.keys.toList()
                for (arrowId in availableArrows) {
                    if (state.canMove(arrowId)) {
                        state.moveArrowFully(arrowId)
                        progress = true
                        break
                    }
                }
            }

            assertTrue("Puzzle generated with seed $seed should be 100% solvable", state.isComplete())
        }
    }

    @Test
    fun updatePregeneratedFile() {
        val generator = ArrowEscapeGenerator()
        val sb = StringBuilder()

        sb.append("package com.funkyotc.puzzleverse.arrowescape.data\n\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Arrow\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Coordinate\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Direction\n")
        sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle\n\n")
        sb.append("data class ArrowEscapePuzzle(\n")
        sb.append("    override val id: String,\n")
        sb.append("    override val difficulty: String,\n")
        sb.append("    val arrows: List<Arrow>\n")
        sb.append(") : BrowseablePuzzle {\n")
        sb.append("    override val label: String get() = \"Puzzle \${id.takeLast(3)}\"\n")
        sb.append("    override val subtitle: String get() = \"\${arrows.size} arrows\"\n")
        sb.append("}\n\n")

        // Easy Puzzles (1-8, 10x10)
        for (i in 1..8) {
            val random = Random(100 + i)
            val arrows = generator.generate(10, 10, 0.40f, random)
            sb.append("object ArrowEscapePuzzle$i {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
        }

        // Medium Puzzles (9-16, 20x20)
        for (i in 9..16) {
            val random = Random(200 + i)
            val arrows = generator.generate(20, 20, 0.40f, random)
            sb.append("object ArrowEscapePuzzle$i {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
        }

        // Hard Puzzles (17-24, 30x30)
        for (i in 17..24) {
            val random = Random(300 + i)
            val arrows = generator.generate(30, 30, 0.40f, random)
            sb.append("object ArrowEscapePuzzle$i {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
        }

        sb.append("object ArrowEscapePregenerated {\n")
        sb.append("    val ALL_PUZZLES: List<ArrowEscapePuzzle> by lazy {\n")
        sb.append("        listOf(\n")
        for (i in 1..8) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_easy_$formattedIndex\", \"Easy\", ArrowEscapePuzzle$i.arrows),\n")
        }
        for (i in 9..16) {
            val formattedIndex = String.format("%03d", i - 8)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_medium_$formattedIndex\", \"Medium\", ArrowEscapePuzzle$i.arrows),\n")
        }
        for (i in 17..24) {
            val formattedIndex = String.format("%03d", i - 16)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_hard_$formattedIndex\", \"Hard\", ArrowEscapePuzzle$i.arrows),\n")
        }
        sb.append("        )\n")
        sb.append("    }\n\n")
        sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<ArrowEscapePuzzle>> by lazy {\n")
        sb.append("        ALL_PUZZLES.groupBy { it.difficulty }\n")
        sb.append("    }\n\n")
        sb.append("    fun getPuzzleById(id: String): ArrowEscapePuzzle? = ALL_PUZZLES.find { it.id == id }\n")
        sb.append("}\n")

        val targetPath = "src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt"
        val file = File(targetPath)
        file.writeText(sb.toString())
        println("Successfully updated ArrowEscapePregenerated.kt with ${file.length()} bytes!")
    }
}
