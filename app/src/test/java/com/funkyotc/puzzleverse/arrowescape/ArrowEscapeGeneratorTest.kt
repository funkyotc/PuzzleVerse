package com.funkyotc.puzzleverse.arrowescape

import com.funkyotc.puzzleverse.arrowescape.model.ArrowEscapeGenerator
import com.funkyotc.puzzleverse.arrowescape.model.GridState
import com.funkyotc.puzzleverse.arrowescape.model.LevelShape
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import kotlin.random.Random

class ArrowEscapeGeneratorTest {

    @Test
    fun testGeneratedPuzzlesAre100PercentSolvable() {
        val generator = ArrowEscapeGenerator()
        for (seed in 1..5) {
            val random = Random(seed)
            val shape = LevelShape.entries[seed % LevelShape.entries.size]
            val arrows = generator.generate(10, 10, 1.00f, shape, random)
            assertTrue("Generated puzzle should contain arrows", arrows.isNotEmpty())

            val state = GridState(10, 10, arrows, shape)
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
    fun testTestingGeneratedPuzzlesAreSolvableAndChallenging() {
        val generator = ArrowEscapeGenerator()
        for (seed in 1..5) {
            val random = Random(seed)
            val shape = LevelShape.SQUARE
            val arrows = generator.generateTesting(12, 12, 0.90f, shape, random)
            assertTrue("Generated testing puzzle should contain arrows", arrows.isNotEmpty())
            assertTrue("Testing puzzle generated with seed $seed should be solvable", generator.isPuzzleSolvable(arrows, 12, 12, shape))

            val depth = generator.calculateDependencyDepth(arrows, 12, 12, shape)
            assertTrue("Testing puzzle should have dependency depth >= 3, got $depth", depth >= 3)
        }
    }

    fun updatePregeneratedFile() {
        val generator = ArrowEscapeGenerator()
        val sb = StringBuilder()

        sb.append("package com.funkyotc.puzzleverse.arrowescape.data\n\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Arrow\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Coordinate\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Direction\n")
        sb.append("import com.funkyotc.puzzleverse.arrowescape.model.LevelShape\n")
        sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle\n\n")
        sb.append("data class ArrowEscapePuzzle(\n")
        sb.append("    override val id: String,\n")
        sb.append("    override val difficulty: String,\n")
        sb.append("    val arrows: List<Arrow>,\n")
        sb.append("    val shapeName: String = \"SQUARE\"\n")
        sb.append(") : BrowseablePuzzle {\n")
        sb.append("    val shape: LevelShape get() = try { LevelShape.valueOf(shapeName) } catch(e: Exception) { LevelShape.SQUARE }\n")
        sb.append("    override val label: String get() = \"Puzzle \${id.takeLast(3)}\"\n")
        sb.append("    override val subtitle: String get() = \"\${arrows.size} arrows\"\n")
        sb.append("}\n\n")

        val shapes = LevelShape.entries
        var puzzleIndex = 1

        // Easy Puzzles (1-20, 10x10) - 100% density with shapes
        for (i in 1..20) {
            val random = Random(100 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generate(10, 10, 1.00f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Medium Puzzles (21-40, 20x20) - 100% density with shapes
        for (i in 1..20) {
            val random = Random(200 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generate(20, 20, 1.00f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Hard Puzzles (41-60, 30x30) - 100% density with shapes
        for (i in 1..20) {
            val random = Random(300 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generate(30, 30, 1.00f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Expert Puzzles (61-80, 40x40) - 100% density with shapes
        for (i in 1..20) {
            val random = Random(400 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generate(40, 40, 1.00f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Master Puzzles (81-100, 50x50) - 100% density with shapes
        for (i in 1..20) {
            val random = Random(500 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generate(50, 50, 1.00f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Testing Puzzles (101-120, 15x15 to 40x40) - Reverse Dependency Generation
        val testingSizes = listOf(15, 15, 15, 15, 20, 20, 20, 20, 25, 25, 25, 25, 30, 30, 30, 30, 40, 40, 40, 40)
        for (i in 1..20) {
            val size = testingSizes[i - 1]
            val random = Random(600 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generateTesting(size, size, 0.90f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
            sb.append("    val arrows = listOf<Arrow>(\n")
            arrows.forEach { a ->
                val segs = a.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
                sb.append("        Arrow(${a.id}, listOf($segs), Direction.${a.direction.name}, ${a.color}),\n")
            }
            sb.append("    )\n}\n\n")
            puzzleIndex++
        }

        // Extreme / God Tier Puzzles (121-140, 30x30 to 50x50) - Concentric Reverse Dependency Generation
        val extremeSizes = listOf(30, 30, 30, 30, 40, 40, 40, 40, 40, 40, 40, 40, 50, 50, 50, 50, 50, 50, 50, 50)
        for (i in 1..20) {
            val size = extremeSizes[i - 1]
            val random = Random(700 + i)
            val shape = shapes[(i - 1) % shapes.size]
            val arrows = generator.generateExtreme(size, size, 0.92f, shape, random)
            sb.append("object ArrowEscapePuzzle$puzzleIndex {\n")
            sb.append("    val shape = LevelShape.${shape.name}\n")
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
            sb.append("            ArrowEscapePuzzle(\"arrowescape_easy_$formattedIndex\", \"Easy\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_medium_$formattedIndex\", \"Medium\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_hard_$formattedIndex\", \"Hard\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_expert_$formattedIndex\", \"Expert\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_master_$formattedIndex\", \"Master\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_testing_$formattedIndex\", \"Testing\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
            pIdx++
        }
        for (i in 1..20) {
            val formattedIndex = String.format("%03d", i)
            sb.append("            ArrowEscapePuzzle(\"arrowescape_extreme_$formattedIndex\", \"Extreme\", ArrowEscapePuzzle$pIdx.arrows, ArrowEscapePuzzle$pIdx.shape.name),\n")
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
        println("Successfully updated ArrowEscapePregenerated.kt with 140 shape-masked puzzles including Extreme difficulty (${file.length()} bytes)!")
    }
}
