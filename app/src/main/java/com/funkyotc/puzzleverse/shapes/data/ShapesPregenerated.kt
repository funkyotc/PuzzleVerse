package com.funkyotc.puzzleverse.shapes.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import androidx.compose.ui.geometry.Offset

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
    val ALL_PUZZLES: List<PregeneratedShape> by lazy {
        listOf(
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_000",
                difficulty = "Easy",
                name = "Square #1",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_001",
                difficulty = "Easy",
                name = "Square #2",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_002",
                difficulty = "Easy",
                name = "Square #3",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_003",
                difficulty = "Easy",
                name = "Square #4",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_004",
                difficulty = "Easy",
                name = "Square #5",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_005",
                difficulty = "Easy",
                name = "Square #6",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Easy_puzzle_006",
                difficulty = "Easy",
                name = "Square #7",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_007",
                difficulty = "Hard",
                name = "Square #8",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_008",
                difficulty = "Hard",
                name = "Square #9",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_009",
                difficulty = "Hard",
                name = "Square #10",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_010",
                difficulty = "Hard",
                name = "Square #11",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_011",
                difficulty = "Hard",
                name = "Square #12",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_012",
                difficulty = "Hard",
                name = "Square #13",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_013",
                difficulty = "Hard",
                name = "Square #14",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_014",
                difficulty = "Hard",
                name = "Square #15",
                target = TargetShape(vertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(Offset(0f, 0f), Offset(0f, 100f), Offset(50f, 50f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 100f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(Offset(50f, 50f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(Offset(0f, 100f), Offset(50f, 100f), Offset(25f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(Offset(50f, 50f), Offset(75f, 25f), Offset(100f, 50f), Offset(75f, 75f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(Offset(0f, 100f), Offset(25f, 75f), Offset(75f, 75f), Offset(50f, 100f)), position = Offset(0f, 0f), solutionPosition = Offset(0f, 0f), solutionRotation = 0.0f)
                )
            ),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}
