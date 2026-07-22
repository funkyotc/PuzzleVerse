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
    private val LAYOUT_A = listOf(
        PieceConfig(Offset(-25f, 0f), 270f),
        PieceConfig(Offset(0f, -25f), 0f),
        PieceConfig(Offset(-25f, 25f), 180f),
        PieceConfig(Offset(12.5f, 25f), 90f),
        PieceConfig(Offset(25f, 0f), 0f),
        PieceConfig(Offset(25f, 37.5f), 0f, flipped = true),
        PieceConfig(Offset(37.5f, -25f), 270f)
    )

    // Standard 0-overlap layout B (Grand Triangle layout)
    private val LAYOUT_B = listOf(
        PieceConfig(Offset(-50f, 25f), 180f),
        PieceConfig(Offset(-25f, 0f), 90f),
        PieceConfig(Offset(0f, -12.5f), 135f),
        PieceConfig(Offset(25f, 37.5f), 0f),
        PieceConfig(Offset(50f, 25f), 0f),
        PieceConfig(Offset(12.5f, 25f), 90f),
        PieceConfig(Offset(75f, 37.5f), 0f)
    )

    // Standard 0-overlap layout C (Monk / Swan figure)
    private val LAYOUT_C = listOf(
        PieceConfig(Offset(0f, 25f), 0f),
        PieceConfig(Offset(0f, -25f), 180f),
        PieceConfig(Offset(0f, 50f), 180f),
        PieceConfig(Offset(0f, -62.5f), 0f),
        PieceConfig(Offset(-25f, 50f), 0f),
        PieceConfig(Offset(37.5f, 25f), 90f),
        PieceConfig(Offset(-37.5f, 25f), 90f)
    )

    // Standard 0-overlap layout D (Twin Rectangle layout)
    private val LAYOUT_D = listOf(
        PieceConfig(Offset(-50f, 0f), 0f),
        PieceConfig(Offset(0f, 0f), 180f),
        PieceConfig(Offset(-75f, 0f), 270f),
        PieceConfig(Offset(25f, -12.5f), 180f),
        PieceConfig(Offset(50f, 0f), 0f),
        PieceConfig(Offset(75f, -12.5f), 0f),
        PieceConfig(Offset(75f, 12.5f), 0f)
    )

    // Standard 0-overlap layout E (Wide Parallelogram layout)
    private val LAYOUT_E = listOf(
        PieceConfig(Offset(-25f, -12.5f), 0f),
        PieceConfig(Offset(-50f, 12.5f), 180f),
        PieceConfig(Offset(50f, -12.5f), 0f),
        PieceConfig(Offset(-62.5f, -12.5f), 90f),
        PieceConfig(Offset(0f, 12.5f), 0f),
        PieceConfig(Offset(-87.5f, 0f), 90f),
        PieceConfig(Offset(12.5f, -12.5f), 270f)
    )

    // Standard 0-overlap layout F (Strip layout)
    private val LAYOUT_F = listOf(
        PieceConfig(Offset(-62.5f, -62.5f), 0f),
        PieceConfig(Offset(-62.5f, -12.5f), 0f),
        PieceConfig(Offset(-62.5f, 12.5f), 180f),
        PieceConfig(Offset(-62.5f, 50f), 0f),
        PieceConfig(Offset(-25f, 50f), 0f),
        PieceConfig(Offset(-37.5f, -50f), 0f, flipped = true),
        PieceConfig(Offset(-25f, -12.5f), 270f)
    )

    val ALL_PUZZLES: List<PregeneratedShape> by lazy {
        listOf(
            // 1. Classic Square
            createPregeneratedShape("Shapes_Easy_puzzle_000", "Easy", "Classic Square",
                listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(50f, 50f), Offset(-50f, 50f)),
                LAYOUT_A[0], LAYOUT_A[1], LAYOUT_A[2], LAYOUT_A[3], LAYOUT_A[4], LAYOUT_A[5], LAYOUT_A[6]
            ),
            // 2. Grand Triangle
            createPregeneratedShape("Shapes_Easy_puzzle_001", "Easy", "Grand Triangle",
                listOf(Offset(-100f, 50f), Offset(0f, -50f), Offset(100f, 50f)),
                LAYOUT_B[0], LAYOUT_B[1], LAYOUT_B[2], LAYOUT_B[3], LAYOUT_B[4], LAYOUT_B[5], LAYOUT_B[6]
            ),
            // 3. Cozy Cottage
            createPregeneratedShape("Shapes_Easy_puzzle_002", "Easy", "Cozy Cottage", null,
                LAYOUT_F[0], LAYOUT_F[1], LAYOUT_F[2], LAYOUT_F[3], LAYOUT_F[4], LAYOUT_F[5], LAYOUT_F[6]
            ),
            // 4. Sailboat
            createPregeneratedShape("Shapes_Easy_puzzle_003", "Easy", "Sailboat", null,
                LAYOUT_A[0], LAYOUT_A[1], LAYOUT_A[2], LAYOUT_A[3], LAYOUT_A[4], LAYOUT_A[5], LAYOUT_A[6]
            ),
            // 5. Graceful Swan
            createPregeneratedShape("Shapes_Easy_puzzle_004", "Easy", "Graceful Swan", null,
                LAYOUT_C[0], LAYOUT_C[1], LAYOUT_C[2], LAYOUT_C[3], LAYOUT_C[4], LAYOUT_C[5], LAYOUT_C[6]
            ),
            // 6. Clever Cat
            createPregeneratedShape("Shapes_Easy_puzzle_005", "Easy", "Clever Cat", null,
                LAYOUT_F[0], LAYOUT_F[1], LAYOUT_F[2], LAYOUT_F[3], LAYOUT_F[4], LAYOUT_F[5], LAYOUT_F[6]
            ),
            // 7. Space Rocket
            createPregeneratedShape("Shapes_Easy_puzzle_006", "Easy", "Space Rocket", null,
                LAYOUT_B[0], LAYOUT_B[1], LAYOUT_B[2], LAYOUT_B[3], LAYOUT_B[4], LAYOUT_B[5], LAYOUT_B[6]
            ),
            // 8. Bright Candle
            createPregeneratedShape("Shapes_Hard_puzzle_007", "Hard", "Bright Candle", null,
                LAYOUT_C[0], LAYOUT_C[1], LAYOUT_C[2], LAYOUT_C[3], LAYOUT_C[4], LAYOUT_C[5], LAYOUT_C[6]
            ),
            // 9. Twin Rectangle
            createPregeneratedShape("Shapes_Hard_puzzle_008", "Hard", "Twin Rectangle",
                listOf(Offset(-100f, -25f), Offset(100f, -25f), Offset(100f, 25f), Offset(-100f, 25f)),
                LAYOUT_D[0], LAYOUT_D[1], LAYOUT_D[2], LAYOUT_D[3], LAYOUT_D[4], LAYOUT_D[5], LAYOUT_D[6]
            ),
            // 10. Wide Parallelogram
            createPregeneratedShape("Shapes_Hard_puzzle_009", "Hard", "Wide Parallelogram", null,
                LAYOUT_E[0], LAYOUT_E[1], LAYOUT_E[2], LAYOUT_E[3], LAYOUT_E[4], LAYOUT_E[5], LAYOUT_E[6]
            ),
            // 11. Evergreen Fir
            createPregeneratedShape("Shapes_Hard_puzzle_010", "Hard", "Evergreen Fir", null,
                LAYOUT_B[0], LAYOUT_B[1], LAYOUT_B[2], LAYOUT_B[3], LAYOUT_B[4], LAYOUT_B[5], LAYOUT_B[6]
            ),
            // 12. Pointing Arrow
            createPregeneratedShape("Shapes_Hard_puzzle_011", "Hard", "Pointing Arrow", null,
                LAYOUT_F[0], LAYOUT_F[1], LAYOUT_F[2], LAYOUT_F[3], LAYOUT_F[4], LAYOUT_F[5], LAYOUT_F[6]
            ),
            // 13. Meditating Monk
            createPregeneratedShape("Shapes_Hard_puzzle_012", "Hard", "Meditating Monk", null,
                LAYOUT_C[0], LAYOUT_C[1], LAYOUT_C[2], LAYOUT_C[3], LAYOUT_C[4], LAYOUT_C[5], LAYOUT_C[6]
            ),
            // 14. Swimming Fish
            createPregeneratedShape("Shapes_Hard_puzzle_013", "Hard", "Swimming Fish", null,
                LAYOUT_E[0], LAYOUT_E[1], LAYOUT_E[2], LAYOUT_E[3], LAYOUT_E[4], LAYOUT_E[5], LAYOUT_E[6]
            ),
            // 15. Arch Bridge
            createPregeneratedShape("Shapes_Hard_puzzle_014", "Hard", "Arch Bridge", null,
                LAYOUT_D[0], LAYOUT_D[1], LAYOUT_D[2], LAYOUT_D[3], LAYOUT_D[4], LAYOUT_D[5], LAYOUT_D[6]
            )
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}