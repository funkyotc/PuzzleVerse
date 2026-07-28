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
        for (seed in 1..10) {
            val random = Random(seed)
            val arrows = generator.generate(10, 10, 0.68f, random)
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

        var puzzleIndex = 1

        // Easy Puzzles (1-20, 10x10) - ~68% density (~12-16 arrows per level)
        for (i in 1..20) {
            val random = Random(100 + i)
            val arrows = generator.generate(10, 10, 0.68f, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Medium Puzzles (21-40, 20x20) - ~68% density (~35-45 arrows per level)
        for (i in 1..20) {
            val random = Random(200 + i)
            val arrows = generator.generate(20, 20, 0.68f, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Hard Puzzles (41-60, 30x30) - ~65% density (~70-90 arrows per level)
        for (i in 1..20) {
            val random = Random(300 + i)
            val arrows = generator.generate(30, 30, 0.65f, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Expert Puzzles (61-80, 40x40) - ~62% density (~120-150 arrows per level)
        for (i in 1..20) {
            val random = Random(400 + i)
            val arrows = generator.generate(40, 40, 0.62f, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Master Puzzles (81-100, 50x50) - ~60% density (~180-220 arrows per level)
        for (i in 1..20) {
            val random = Random(500 + i)
            val arrows = generator.generate(50, 50, 0.60f, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        sb.append("object ArrowEscapePregenerated {\n")
        sb.append("    val ALL_PUZZLES: List<ArrowEscapePuzzle> by lazy {\n")
        sb.append("        listOf(\n")
        
        var pIdx = 1
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_easy_$formattedIndex\", \"Easy\", ArrowEscapePuzzle$pIdx.arrows),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_medium_$formattedIndex\", \"Medium\", ArrowEscapePuzzle$pIdx.arrows),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_hard_$formattedIndex\", \"Hard\", ArrowEscapePuzzle$pIdx.arrows),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_expert_$formattedIndex\", \"Expert\", ArrowEscapePuzzle$pIdx.arrows),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_master_$formattedIndex\", \"Master\", ArrowEscapePuzzle$pIdx.arrows),\n")
            pIdx++
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
        println("Successfully updated ArrowEscapePregenerated.kt with 100 high-density puzzles (${file.length()} bytes)!")
    }
}
