package com.funkyotc.puzzleverse.shapes.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class PregeneratedShape(
    override val id: String,
    override val difficulty: String,
    val name: String,
    val target: TargetShape,
    val pieces: List<PuzzlePiece>
) : BrowseablePuzzle {
    override val label: String get() = name
    override val subtitle: String get() = "${pieces.size} pieces"

    fun toShapesPuzzle(): ShapesPuzzle {
        return ShapesPuzzle(pieces, target, false)
    }
}

object ShapesPregenerated {

    private val COLOR_LARGE_1 = Color(0xFF3B82F6) // Blue
    private val COLOR_LARGE_2 = Color(0xFF8B5CF6) // Purple
    private val COLOR_MEDIUM  = Color(0xFF14B8A6) // Teal
    private val COLOR_SMALL_1 = Color(0xFFF59E0B) // Amber
    private val COLOR_SQUARE  = Color(0xFFF43F5E) // Rose
    private val COLOR_PARA    = Color(0xFF10B981) // Emerald
    private val COLOR_SMALL_2 = Color(0xFFF97316) // Orange

    data class PieceConfig(
        val pos: Offset,
        val rot: Float,
        val flipped: Boolean = false
    )

    fun createStandardTangramPieces(
        sol1: PieceConfig,
        sol2: PieceConfig,
        sol3: PieceConfig,
        sol4: PieceConfig,
        sol5: PieceConfig,
        sol6: PieceConfig,
        sol7: PieceConfig
    ): List<PuzzlePiece> {
        return listOf(
            // 1. Large Triangle 1
            PuzzlePiece(
                id = 1,
                initialVertices = listOf(Offset(-50f, -25f), Offset(50f, -25f), Offset(0f, 25f)),
                position = Offset.Zero,
                color = COLOR_LARGE_1,
                solutionPosition = sol1.pos,
                solutionRotation = sol1.rot,
                isFlipped = sol1.flipped
            ),
            // 2. Large Triangle 2
            PuzzlePiece(
                id = 2,
                initialVertices = listOf(Offset(-50f, -25f), Offset(50f, -25f), Offset(0f, 25f)),
                position = Offset.Zero,
                color = COLOR_LARGE_2,
                solutionPosition = sol2.pos,
                solutionRotation = sol2.rot,
                isFlipped = sol2.flipped
            ),
            // 3. Medium Triangle
            PuzzlePiece(
                id = 3,
                initialVertices = listOf(Offset(-25f, -25f), Offset(25f, -25f), Offset(-25f, 25f)),
                position = Offset.Zero,
                color = COLOR_MEDIUM,
                solutionPosition = sol3.pos,
                solutionRotation = sol3.rot,
                isFlipped = sol3.flipped
            ),
            // 4. Small Triangle 1
            PuzzlePiece(
                id = 4,
                initialVertices = listOf(Offset(-25f, 12.5f), Offset(25f, 12.5f), Offset(0f, -12.5f)),
                position = Offset.Zero,
                color = COLOR_SMALL_1,
                solutionPosition = sol4.pos,
                solutionRotation = sol4.rot,
                isFlipped = sol4.flipped
            ),
            // 5. Square
            PuzzlePiece(
                id = 5,
                initialVertices = listOf(Offset(0f, -25f), Offset(25f, 0f), Offset(0f, 25f), Offset(-25f, 0f)),
                position = Offset.Zero,
                color = COLOR_SQUARE,
                solutionPosition = sol5.pos,
                solutionRotation = sol5.rot,
                isFlipped = sol5.flipped
            ),
            // 6. Parallelogram
            PuzzlePiece(
                id = 6,
                initialVertices = listOf(Offset(-25f, -12.5f), Offset(0f, -12.5f), Offset(25f, 12.5f), Offset(0f, 12.5f)),
                position = Offset.Zero,
                color = COLOR_PARA,
                solutionPosition = sol6.pos,
                solutionRotation = sol6.rot,
                isFlipped = sol6.flipped
            ),
            // 7. Small Triangle 2
            PuzzlePiece(
                id = 7,
                initialVertices = listOf(Offset(-25f, 12.5f), Offset(25f, 12.5f), Offset(0f, -12.5f)),
                position = Offset.Zero,
                color = COLOR_SMALL_2,
                solutionPosition = sol7.pos,
                solutionRotation = sol7.rot,
                isFlipped = sol7.flipped
            )
        )
    }

    private fun createPregeneratedShape(
        id: String,
        difficulty: String,
        name: String,
        customTargetVertices: List<Offset>?,
        sol1: PieceConfig,
        sol2: PieceConfig,
        sol3: PieceConfig,
        sol4: PieceConfig,
        sol5: PieceConfig,
        sol6: PieceConfig,
        sol7: PieceConfig
    ): PregeneratedShape {
        val pieces = createStandardTangramPieces(sol1, sol2, sol3, sol4, sol5, sol6, sol7)
        val targetVerts = customTargetVertices ?: run {
            val allWorldVerts = pieces.flatMap { piece ->
                GeometryUtils.transformPolygon(
                    piece.initialVertices,
                    piece.solutionPosition,
                    piece.solutionRotation,
                    isFlipped = piece.isFlipped
                )
            }
            computeConvexHull(allWorldVerts)
        }
        return PregeneratedShape(id, difficulty, name, TargetShape(targetVerts), pieces)
    }

    private fun computeConvexHull(points: List<Offset>): List<Offset> {
        val sorted = points.distinctBy { Pair((it.x * 10f).toInt(), (it.y * 10f).toInt()) }
            .sortedWith(compareBy({ it.x }, { it.y }))
        if (sorted.size <= 3) return sorted

        val lower = mutableListOf<Offset>()
        for (p in sorted) {
            while (lower.size >= 2 && crossProduct(lower[lower.size - 2], lower.last(), p) <= 0) {
                lower.removeAt(lower.size - 1)
            }
            lower.add(p)
        }

        val upper = mutableListOf<Offset>()
        for (p in sorted.reversed()) {
            while (upper.size >= 2 && crossProduct(upper[upper.size - 2], upper.last(), p) <= 0) {
                upper.removeAt(upper.size - 1)
            }
            upper.add(p)
        }

        lower.removeAt(lower.size - 1)
        upper.removeAt(upper.size - 1)
        return lower + upper
    }

    private fun crossProduct(o: Offset, a: Offset, b: Offset): Float {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x)
    }

    // Standard 0-overlap layout A (Classic Square layout)
    private val LAYOUT_SQUARE = listOf(
        PieceConfig(Offset(0f, -25f), 0f),
        PieceConfig(Offset(25f, 0f), 90f),
        PieceConfig(Offset(-25f, 25f), 0f),
        PieceConfig(Offset(-25f, 37.5f), 0f),
        PieceConfig(Offset(0f, 25f), 0f),
        PieceConfig(Offset(-25f, -12.5f), 0f),
        PieceConfig(Offset(25f, 37.5f), 0f)
    )

    // Standard 0-overlap layout B (Grand Triangle layout)
    private val LAYOUT_TRIANGLE = listOf(
        PieceConfig(Offset(0f, 25f), 0f),
        PieceConfig(Offset(-50f, 25f), 180f),
        PieceConfig(Offset(-12.5f, 0f), 45f),
        PieceConfig(Offset(25f, 37.5f), 0f),
        PieceConfig(Offset(50f, 25f), 0f),
        PieceConfig(Offset(12.5f, -25f), 0f),
        PieceConfig(Offset(75f, 37.5f), 0f)
    )

    // Standard 0-overlap layout C (Cozy Cottage layout)
    private val LAYOUT_COTTAGE = listOf(
        PieceConfig(Offset(0f, 0f), 0f),
        PieceConfig(Offset(0f, -50f), 180f),
        PieceConfig(Offset(-12.5f, 50f), 45f),
        PieceConfig(Offset(-37.5f, 0f), 90f),
        PieceConfig(Offset(25f, 25f), 0f),
        PieceConfig(Offset(-37.5f, 25f), 90f),
        PieceConfig(Offset(37.5f, 0f), 270f)
    )

    // Standard 0-overlap layout D (Twin Rectangle layout)
    private val LAYOUT_RECTANGLE = listOf(
        PieceConfig(Offset(-50f, 0f), 0f),
        PieceConfig(Offset(50f, 0f), 0f),
        PieceConfig(Offset(75f, 0f), 180f),
        PieceConfig(Offset(-25f, 12.5f), 0f),
        PieceConfig(Offset(0f, 0f), 0f),
        PieceConfig(Offset(-75f, 12.5f), 0f),
        PieceConfig(Offset(25f, 12.5f), 0f)
    )

    // Standard 0-overlap layout E (Wide Parallelogram layout)
    private val LAYOUT_PARALLELOGRAM = listOf(
        PieceConfig(Offset(-25f, -12.5f), 0f),
        PieceConfig(Offset(-50f, 12.5f), 180f),
        PieceConfig(Offset(50f, -12.5f), 0f),
        PieceConfig(Offset(-62.5f, -12.5f), 90f),
        PieceConfig(Offset(0f, 12.5f), 0f),
        PieceConfig(Offset(-87.5f, 0f), 90f),
        PieceConfig(Offset(12.5f, -12.5f), 270f)
    )

    // Standard 0-overlap layout F (Meditating Monk layout)
    private val LAYOUT_MONK = listOf(
        PieceConfig(Offset(0f, 25f), 0f),
        PieceConfig(Offset(0f, -25f), 180f),
        PieceConfig(Offset(0f, 50f), 180f),
        PieceConfig(Offset(0f, -62.5f), 0f),
        PieceConfig(Offset(-25f, 50f), 0f),
        PieceConfig(Offset(37.5f, 25f), 90f),
        PieceConfig(Offset(-37.5f, 25f), 90f)
    )

    val ALL_PUZZLES: List<PregeneratedShape> by lazy {
        listOf(
            // 1. Classic Square
            createPregeneratedShape("Shapes_Easy_puzzle_000", "Easy", "Classic Square",
                listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(50f, 50f), Offset(-50f, 50f)),
                LAYOUT_SQUARE[0], LAYOUT_SQUARE[1], LAYOUT_SQUARE[2], LAYOUT_SQUARE[3], LAYOUT_SQUARE[4], LAYOUT_SQUARE[5], LAYOUT_SQUARE[6]
            ),
            // 2. Grand Triangle
            createPregeneratedShape("Shapes_Easy_puzzle_001", "Easy", "Grand Triangle",
                listOf(Offset(-100f, 50f), Offset(0f, -50f), Offset(100f, 50f)),
                LAYOUT_TRIANGLE[0], LAYOUT_TRIANGLE[1], LAYOUT_TRIANGLE[2], LAYOUT_TRIANGLE[3], LAYOUT_TRIANGLE[4], LAYOUT_TRIANGLE[5], LAYOUT_TRIANGLE[6]
            ),
            // 3. Cozy Cottage
            createPregeneratedShape("Shapes_Easy_puzzle_002", "Easy", "Cozy Cottage", null,
                LAYOUT_COTTAGE[0], LAYOUT_COTTAGE[1], LAYOUT_COTTAGE[2], LAYOUT_COTTAGE[3], LAYOUT_COTTAGE[4], LAYOUT_COTTAGE[5], LAYOUT_COTTAGE[6]
            ),
            // 4. Sailboat
            createPregeneratedShape("Shapes_Easy_puzzle_003", "Easy", "Sailboat", null,
                LAYOUT_SQUARE[0], LAYOUT_SQUARE[1], LAYOUT_SQUARE[2], LAYOUT_SQUARE[3], LAYOUT_SQUARE[4], LAYOUT_SQUARE[5], LAYOUT_SQUARE[6]
            ),
            // 5. Graceful Swan
            createPregeneratedShape("Shapes_Easy_puzzle_004", "Easy", "Graceful Swan", null,
                LAYOUT_MONK[0], LAYOUT_MONK[1], LAYOUT_MONK[2], LAYOUT_MONK[3], LAYOUT_MONK[4], LAYOUT_MONK[5], LAYOUT_MONK[6]
            ),
            // 6. Clever Cat
            createPregeneratedShape("Shapes_Easy_puzzle_005", "Easy", "Clever Cat", null,
                LAYOUT_COTTAGE[0], LAYOUT_COTTAGE[1], LAYOUT_COTTAGE[2], LAYOUT_COTTAGE[3], LAYOUT_COTTAGE[4], LAYOUT_COTTAGE[5], LAYOUT_COTTAGE[6]
            ),
            // 7. Space Rocket
            createPregeneratedShape("Shapes_Easy_puzzle_006", "Easy", "Space Rocket", null,
                LAYOUT_TRIANGLE[0], LAYOUT_TRIANGLE[1], LAYOUT_TRIANGLE[2], LAYOUT_TRIANGLE[3], LAYOUT_TRIANGLE[4], LAYOUT_TRIANGLE[5], LAYOUT_TRIANGLE[6]
            ),
            // 8. Bright Candle
            createPregeneratedShape("Shapes_Hard_puzzle_007", "Hard", "Bright Candle", null,
                LAYOUT_MONK[0], LAYOUT_MONK[1], LAYOUT_MONK[2], LAYOUT_MONK[3], LAYOUT_MONK[4], LAYOUT_MONK[5], LAYOUT_MONK[6]
            ),
            // 9. Twin Rectangle
            createPregeneratedShape("Shapes_Hard_puzzle_008", "Hard", "Twin Rectangle",
                listOf(Offset(-100f, -25f), Offset(100f, -25f), Offset(100f, 25f), Offset(-100f, 25f)),
                LAYOUT_RECTANGLE[0], LAYOUT_RECTANGLE[1], LAYOUT_RECTANGLE[2], LAYOUT_RECTANGLE[3], LAYOUT_RECTANGLE[4], LAYOUT_RECTANGLE[5], LAYOUT_RECTANGLE[6]
            ),
            // 10. Wide Parallelogram
            createPregeneratedShape("Shapes_Hard_puzzle_009", "Hard", "Wide Parallelogram", null,
                LAYOUT_PARALLELOGRAM[0], LAYOUT_PARALLELOGRAM[1], LAYOUT_PARALLELOGRAM[2], LAYOUT_PARALLELOGRAM[3], LAYOUT_PARALLELOGRAM[4], LAYOUT_PARALLELOGRAM[5], LAYOUT_PARALLELOGRAM[6]
            ),
            // 11. Evergreen Fir
            createPregeneratedShape("Shapes_Hard_puzzle_010", "Hard", "Evergreen Fir", null,
                LAYOUT_TRIANGLE[0], LAYOUT_TRIANGLE[1], LAYOUT_TRIANGLE[2], LAYOUT_TRIANGLE[3], LAYOUT_TRIANGLE[4], LAYOUT_TRIANGLE[5], LAYOUT_TRIANGLE[6]
            ),
            // 12. Pointing Arrow
            createPregeneratedShape("Shapes_Hard_puzzle_011", "Hard", "Pointing Arrow", null,
                LAYOUT_COTTAGE[0], LAYOUT_COTTAGE[1], LAYOUT_COTTAGE[2], LAYOUT_COTTAGE[3], LAYOUT_COTTAGE[4], LAYOUT_COTTAGE[5], LAYOUT_COTTAGE[6]
            ),
            // 13. Meditating Monk
            createPregeneratedShape("Shapes_Hard_puzzle_012", "Hard", "Meditating Monk", null,
                LAYOUT_MONK[0], LAYOUT_MONK[1], LAYOUT_MONK[2], LAYOUT_MONK[3], LAYOUT_MONK[4], LAYOUT_MONK[5], LAYOUT_MONK[6]
            ),
            // 14. Swimming Fish
            createPregeneratedShape("Shapes_Hard_puzzle_013", "Hard", "Swimming Fish", null,
                LAYOUT_PARALLELOGRAM[0], LAYOUT_PARALLELOGRAM[1], LAYOUT_PARALLELOGRAM[2], LAYOUT_PARALLELOGRAM[3], LAYOUT_PARALLELOGRAM[4], LAYOUT_PARALLELOGRAM[5], LAYOUT_PARALLELOGRAM[6]
            ),
            // 15. Arch Bridge
            createPregeneratedShape("Shapes_Hard_puzzle_014", "Hard", "Arch Bridge", null,
                LAYOUT_RECTANGLE[0], LAYOUT_RECTANGLE[1], LAYOUT_RECTANGLE[2], LAYOUT_RECTANGLE[3], LAYOUT_RECTANGLE[4], LAYOUT_RECTANGLE[5], LAYOUT_RECTANGLE[6]
            )
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}
