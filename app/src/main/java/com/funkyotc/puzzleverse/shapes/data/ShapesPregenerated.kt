package com.funkyotc.puzzleverse.shapes.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
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

    // Standard piece colors for vibrant visual presentation
    private val COLOR_LARGE_1 = Color(0xFF3B82F6) // Blue
    private val COLOR_LARGE_2 = Color(0xFF8B5CF6) // Purple / Indigo
    private val COLOR_MEDIUM  = Color(0xFF14B8A6) // Teal
    private val COLOR_SMALL_1 = Color(0xFFF59E0B) // Amber
    private val COLOR_SQUARE  = Color(0xFFF43F5E) // Rose
    private val COLOR_PARA    = Color(0xFF10B981) // Emerald
    private val COLOR_SMALL_2 = Color(0xFFF97316) // Orange

    /**
     * Creates the standard 7 Tangram pieces with local geometry centered at origin (0,0).
     */
    fun createStandardTangramPieces(
        sol1: Pair<Offset, Float>, // (solutionPosition, solutionRotation) for Large Triangle 1
        sol2: Pair<Offset, Float>, // Large Triangle 2
        sol3: Pair<Offset, Float>, // Medium Triangle
        sol4: Pair<Offset, Float>, // Small Triangle 1
        sol5: Pair<Offset, Float>, // Square
        sol6: Pair<Offset, Float>, // Parallelogram
        sol7: Pair<Offset, Float>  // Small Triangle 2
    ): List<PuzzlePiece> {
        return listOf(
            // 1. Large Triangle 1 (Legs = 100, Hypotenuse = 100*sqrt(2))
            PuzzlePiece(
                id = 1,
                initialVertices = listOf(Offset(-50f, -25f), Offset(50f, -25f), Offset(0f, 25f)),
                position = Offset.Zero,
                color = COLOR_LARGE_1,
                solutionPosition = sol1.first,
                solutionRotation = sol1.second
            ),
            // 2. Large Triangle 2 (Same size as Large Triangle 1)
            PuzzlePiece(
                id = 2,
                initialVertices = listOf(Offset(-25f, -50f), Offset(-25f, 50f), Offset(25f, 0f)),
                position = Offset.Zero,
                color = COLOR_LARGE_2,
                solutionPosition = sol2.first,
                solutionRotation = sol2.second
            ),
            // 3. Medium Triangle (Legs = 50*sqrt(2), Hypotenuse = 100)
            PuzzlePiece(
                id = 3,
                initialVertices = listOf(Offset(25f, -25f), Offset(25f, 25f), Offset(-25f, 25f)),
                position = Offset.Zero,
                color = COLOR_MEDIUM,
                solutionPosition = sol3.first,
                solutionRotation = sol3.second
            ),
            // 4. Small Triangle 1 (Legs = 50, Hypotenuse = 50*sqrt(2))
            PuzzlePiece(
                id = 4,
                initialVertices = listOf(Offset(-25f, 12.5f), Offset(0f, -12.5f), Offset(25f, 12.5f)),
                position = Offset.Zero,
                color = COLOR_SMALL_1,
                solutionPosition = sol4.first,
                solutionRotation = sol4.second
            ),
            // 5. Square (Side = 50)
            PuzzlePiece(
                id = 5,
                initialVertices = listOf(Offset(0f, -25f), Offset(25f, 0f), Offset(0f, 25f), Offset(-25f, 0f)),
                position = Offset.Zero,
                color = COLOR_SQUARE,
                solutionPosition = sol5.first,
                solutionRotation = sol5.second
            ),
            // 6. Parallelogram (Base = 50, Height = 25)
            PuzzlePiece(
                id = 6,
                initialVertices = listOf(Offset(-25f, -12.5f), Offset(0f, -12.5f), Offset(25f, 12.5f), Offset(0f, 12.5f)),
                position = Offset.Zero,
                color = COLOR_PARA,
                solutionPosition = sol6.first,
                solutionRotation = sol6.second
            ),
            // 7. Small Triangle 2 (Same size as Small Triangle 1)
            PuzzlePiece(
                id = 7,
                initialVertices = listOf(Offset(12.5f, -25f), Offset(12.5f, 25f), Offset(-12.5f, 0f)),
                position = Offset.Zero,
                color = COLOR_SMALL_2,
                solutionPosition = sol7.first,
                solutionRotation = sol7.second
            )
        )
    }

    val ALL_PUZZLES: List<PregeneratedShape> by lazy {
        listOf(
            // 1. Classic Square
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_000",
                difficulty = "Easy",
                name = "Classic Square",
                target = TargetShape(vertices = listOf(
                    Offset(-50f, -50f), Offset(50f, -50f), Offset(50f, 50f), Offset(-50f, 50f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, -25f), 0f),
                    sol2 = Pair(Offset(-25f, 0f), 0f),
                    sol3 = Pair(Offset(25f, -25f), 0f),
                    sol4 = Pair(Offset(-25f, 37.5f), 0f),
                    sol5 = Pair(Offset(0f, 25f), 0f),
                    sol6 = Pair(Offset(25f, 12.5f), 0f),
                    sol7 = Pair(Offset(37.5f, 25f), 0f)
                )
            ),

            // 2. Grand Triangle
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_001",
                difficulty = "Easy",
                name = "Grand Triangle",
                target = TargetShape(vertices = listOf(
                    Offset(-100f, 50f), Offset(0f, -50f), Offset(100f, 50f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(-50f, 0f), 0f),
                    sol2 = Pair(Offset(50f, 0f), 90f),
                    sol3 = Pair(Offset(0f, -25f), 180f),
                    sol4 = Pair(Offset(-25f, 25f), 0f),
                    sol5 = Pair(Offset(0f, 25f), 0f),
                    sol6 = Pair(Offset(25f, 25f), 0f),
                    sol7 = Pair(Offset(25f, -25f), 90f)
                )
            ),

            // 3. House
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_002",
                difficulty = "Easy",
                name = "Cozy Cottage",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -75f), Offset(50f, -25f), Offset(50f, 50f), Offset(-50f, 50f), Offset(-50f, -25f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, -50f), 0f),
                    sol2 = Pair(Offset(-25f, 12.5f), 0f),
                    sol3 = Pair(Offset(25f, -12.5f), 0f),
                    sol4 = Pair(Offset(-25f, 37.5f), 0f),
                    sol5 = Pair(Offset(0f, 25f), 0f),
                    sol6 = Pair(Offset(25f, 12.5f), 0f),
                    sol7 = Pair(Offset(37.5f, 25f), 0f)
                )
            ),

            // 4. Sailboat
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_003",
                difficulty = "Easy",
                name = "Sailboat",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -75f), Offset(40f, 0f), Offset(75f, 50f), Offset(-75f, 50f), Offset(-40f, 0f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(-25f, -37.5f), 0f),
                    sol2 = Pair(Offset(25f, -37.5f), -90f),
                    sol3 = Pair(Offset(0f, 25f), 180f),
                    sol4 = Pair(Offset(-50f, 25f), 90f),
                    sol5 = Pair(Offset(-25f, 25f), 0f),
                    sol6 = Pair(Offset(50f, 25f), 0f),
                    sol7 = Pair(Offset(25f, 25f), -90f)
                )
            ),

            // 5. Graceful Swan
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_004",
                difficulty = "Easy",
                name = "Graceful Swan",
                target = TargetShape(vertices = listOf(
                    Offset(-60f, -75f), Offset(-40f, -75f), Offset(-20f, -35f), Offset(60f, -35f),
                    Offset(80f, 15f), Offset(20f, 55f), Offset(-60f, 55f), Offset(-80f, 15f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, 10f), 0f),
                    sol2 = Pair(Offset(-40f, 10f), -90f),
                    sol3 = Pair(Offset(40f, -10f), 90f),
                    sol4 = Pair(Offset(-50f, -55f), 0f),
                    sol5 = Pair(Offset(-30f, -35f), 0f),
                    sol6 = Pair(Offset(50f, 35f), 0f),
                    sol7 = Pair(Offset(-60f, 35f), 180f)
                )
            ),

            // 6. Clever Cat
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_005",
                difficulty = "Easy",
                name = "Clever Cat",
                target = TargetShape(vertices = listOf(
                    Offset(-35f, -65f), Offset(-20f, -85f), Offset(0f, -65f), Offset(20f, -85f),
                    Offset(35f, -65f), Offset(35f, -25f), Offset(65f, 15f), Offset(85f, 45f),
                    Offset(65f, 65f), Offset(-35f, 65f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, 20f), 0f),
                    sol2 = Pair(Offset(-15f, -10f), -90f),
                    sol3 = Pair(Offset(45f, 35f), 45f),
                    sol4 = Pair(Offset(-20f, -75f), 0f),
                    sol5 = Pair(Offset(0f, -45f), 0f),
                    sol6 = Pair(Offset(60f, 45f), 45f),
                    sol7 = Pair(Offset(20f, -75f), 90f)
                )
            ),

            // 7. Space Rocket
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_006",
                difficulty = "Easy",
                name = "Space Rocket",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -85f), Offset(35f, -35f), Offset(35f, 45f), Offset(65f, 75f),
                    Offset(-65f, 75f), Offset(-35f, 45f), Offset(-35f, -35f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, -60f), 0f),
                    sol2 = Pair(Offset(-15f, 5f), -90f),
                    sol3 = Pair(Offset(15f, 5f), 90f),
                    sol4 = Pair(Offset(-45f, 60f), 0f),
                    sol5 = Pair(Offset(0f, -15f), 0f),
                    sol6 = Pair(Offset(45f, 60f), 0f),
                    sol7 = Pair(Offset(0f, 45f), 180f)
                )
            ),

            // 8. Bright Candle
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_007",
                difficulty = "Hard",
                name = "Bright Candle",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -75f), Offset(15f, -50f), Offset(25f, -50f), Offset(25f, 40f),
                    Offset(55f, 40f), Offset(55f, 65f), Offset(-55f, 65f), Offset(-55f, 40f),
                    Offset(-25f, 40f), Offset(-25f, -50f), Offset(-15f, -50f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, 0f), 0f),
                    sol2 = Pair(Offset(0f, -25f), 180f),
                    sol3 = Pair(Offset(0f, 52.5f), 0f),
                    sol4 = Pair(Offset(0f, -62.5f), 0f),
                    sol5 = Pair(Offset(0f, 25f), 0f),
                    sol6 = Pair(Offset(35f, 52.5f), 0f),
                    sol7 = Pair(Offset(-35f, 52.5f), 180f)
                )
            ),

            // 9. Twin Square Rectangle
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_008",
                difficulty = "Hard",
                name = "Twin Rectangle",
                target = TargetShape(vertices = listOf(
                    Offset(-100f, -25f), Offset(100f, -25f), Offset(100f, 25f), Offset(-100f, 25f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(-50f, 0f), 90f),
                    sol2 = Pair(Offset(50f, 0f), -90f),
                    sol3 = Pair(Offset(-75f, 0f), 0f),
                    sol4 = Pair(Offset(-25f, -12.5f), 0f),
                    sol5 = Pair(Offset(0f, 0f), 0f),
                    sol6 = Pair(Offset(25f, 12.5f), 0f),
                    sol7 = Pair(Offset(75f, 0f), 180f)
                )
            ),

            // 10. Wide Parallelogram
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_009",
                difficulty = "Hard",
                name = "Wide Parallelogram",
                target = TargetShape(vertices = listOf(
                    Offset(-75f, -37.5f), Offset(75f, -37.5f), Offset(25f, 37.5f), Offset(-125f, 37.5f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(-25f, -12.5f), 0f),
                    sol2 = Pair(Offset(25f, 12.5f), 180f),
                    sol3 = Pair(Offset(-62.5f, 12.5f), 0f),
                    sol4 = Pair(Offset(-75f, -25f), 0f),
                    sol5 = Pair(Offset(0f, 0f), 0f),
                    sol6 = Pair(Offset(50f, -25f), 0f),
                    sol7 = Pair(Offset(12.5f, -25f), 90f)
                )
            ),

            // 11. Fir Tree
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_010",
                difficulty = "Hard",
                name = "Evergreen Fir",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -80f), Offset(35f, -40f), Offset(20f, -40f), Offset(55f, 10f),
                    Offset(35f, 10f), Offset(75f, 60f), Offset(15f, 60f), Offset(15f, 85f),
                    Offset(-15f, 85f), Offset(-15f, 60f), Offset(-75f, 60f), Offset(-35f, 10f),
                    Offset(-55f, 10f), Offset(-20f, -40f), Offset(-35f, -40f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, -55f), 0f),
                    sol2 = Pair(Offset(0f, -15f), 0f),
                    sol3 = Pair(Offset(0f, 35f), 0f),
                    sol4 = Pair(Offset(-35f, 35f), 0f),
                    sol5 = Pair(Offset(0f, 72.5f), 0f),
                    sol6 = Pair(Offset(35f, 35f), 0f),
                    sol7 = Pair(Offset(0f, 10f), 180f)
                )
            ),

            // 12. Pointing Arrow
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_011",
                difficulty = "Hard",
                name = "Pointing Arrow",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -80f), Offset(60f, -20f), Offset(25f, -20f), Offset(25f, 80f),
                    Offset(-25f, 80f), Offset(-25f, -20f), Offset(-60f, -20f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, -50f), 0f),
                    sol2 = Pair(Offset(0f, 0f), 180f),
                    sol3 = Pair(Offset(0f, 40f), 0f),
                    sol4 = Pair(Offset(-35f, -35f), 0f),
                    sol5 = Pair(Offset(0f, 65f), 0f),
                    sol6 = Pair(Offset(35f, -35f), 0f),
                    sol7 = Pair(Offset(0f, 20f), 180f)
                )
            ),

            // 13. Meditating Monk
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_012",
                difficulty = "Hard",
                name = "Meditating Monk",
                target = TargetShape(vertices = listOf(
                    Offset(0f, -75f), Offset(20f, -55f), Offset(40f, -35f), Offset(60f, 25f),
                    Offset(40f, 75f), Offset(-40f, 75f), Offset(-60f, 25f), Offset(-40f, -35f),
                    Offset(-20f, -55f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(0f, 0f), 0f),
                    sol2 = Pair(Offset(0f, 40f), 180f),
                    sol3 = Pair(Offset(-30f, -10f), 90f),
                    sol4 = Pair(Offset(0f, -65f), 0f),
                    sol5 = Pair(Offset(0f, -40f), 0f),
                    sol6 = Pair(Offset(30f, -10f), 0f),
                    sol7 = Pair(Offset(0f, 60f), 180f)
                )
            ),

            // 14. Swimming Fish
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_013",
                difficulty = "Hard",
                name = "Swimming Fish",
                target = TargetShape(vertices = listOf(
                    Offset(-75f, 0f), Offset(-25f, -50f), Offset(35f, -30f), Offset(75f, -60f),
                    Offset(60f, 0f), Offset(75f, 60f), Offset(35f, 30f), Offset(-25f, 50f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(-25f, 0f), -90f),
                    sol2 = Pair(Offset(25f, 0f), 90f),
                    sol3 = Pair(Offset(-45f, 20f), 180f),
                    sol4 = Pair(Offset(60f, -35f), 0f),
                    sol5 = Pair(Offset(0f, 0f), 0f),
                    sol6 = Pair(Offset(60f, 35f), 0f),
                    sol7 = Pair(Offset(-45f, -20f), 0f)
                )
            ),

            // 15. Arch Bridge
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_014",
                difficulty = "Hard",
                name = "Arch Bridge",
                target = TargetShape(vertices = listOf(
                    Offset(-80f, -30f), Offset(80f, -30f), Offset(80f, 50f), Offset(40f, 50f),
                    Offset(40f, 10f), Offset(-40f, 10f), Offset(-40f, 50f), Offset(-80f, 50f)
                )),
                pieces = createStandardTangramPieces(
                    sol1 = Pair(Offset(-35f, -10f), 0f),
                    sol2 = Pair(Offset(35f, -10f), 180f),
                    sol3 = Pair(Offset(0f, -10f), 90f),
                    sol4 = Pair(Offset(-60f, 30f), 0f),
                    sol5 = Pair(Offset(-60f, 5f), 0f),
                    sol6 = Pair(Offset(60f, 30f), 0f),
                    sol7 = Pair(Offset(60f, 5f), 180f)
                )
            )
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}
