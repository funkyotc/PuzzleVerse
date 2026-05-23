package com.funkyotc.puzzleverse.shapes.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle

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
                id = "Shapes_Easy_puzzle_001",
                difficulty = "Easy",
                name = "Square",
                target = TargetShape(vertices = listOf(androidx.compose.ui.geometry.Offset(-1.0f, -1.0f), androidx.compose.ui.geometry.Offset(1.0f, -1.0f), androidx.compose.ui.geometry.Offset(1.0f, 1.0f), androidx.compose.ui.geometry.Offset(-1.0f, 1.0f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.3333f, -1.0f), androidx.compose.ui.geometry.Offset(-0.3333f, 1.0f), androidx.compose.ui.geometry.Offset(0.6667f, 0.0f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(-0.6667f, 0.0f), solutionRotation = 0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-1.0f, 0.3333f), androidx.compose.ui.geometry.Offset(1.0f, 0.3333f), androidx.compose.ui.geometry.Offset(0.0f, -0.6667f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.0f, 0.6667f), solutionRotation = 0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.6667f, -0.3333f), androidx.compose.ui.geometry.Offset(0.3333f, -0.3333f), androidx.compose.ui.geometry.Offset(0.3333f, 0.6667f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.6667f, -0.6667f), solutionRotation = 0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.5f, -0.1667f), androidx.compose.ui.geometry.Offset(0.5f, -0.1667f), androidx.compose.ui.geometry.Offset(0.0f, 0.3333f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.0f, -0.3333f), solutionRotation = 0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(androidx.compose.ui.geometry.Offset(0.1667f, -0.5f), androidx.compose.ui.geometry.Offset(0.1667f, 0.5f), androidx.compose.ui.geometry.Offset(-0.3333f, 0.0f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.8333f, 0.5f), solutionRotation = 0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.5f, 0.0f), androidx.compose.ui.geometry.Offset(0.0f, -0.5f), androidx.compose.ui.geometry.Offset(0.5f, 0.0f), androidx.compose.ui.geometry.Offset(0.0f, 0.5f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.5f, 0.0f), solutionRotation = 0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.75f, -0.25f), androidx.compose.ui.geometry.Offset(0.25f, -0.25f), androidx.compose.ui.geometry.Offset(0.75f, 0.25f), androidx.compose.ui.geometry.Offset(-0.25f, 0.25f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(-0.25f, -0.75f), solutionRotation = 0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Hard_puzzle_001",
                difficulty = "Hard",
                name = "Parallelogram",
                target = TargetShape(vertices = listOf(androidx.compose.ui.geometry.Offset(-1.0f, -1.0f), androidx.compose.ui.geometry.Offset(1.0f, -1.0f), androidx.compose.ui.geometry.Offset(3.0f, 1.0f), androidx.compose.ui.geometry.Offset(1.0f, 1.0f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.3333f, -1.0f), androidx.compose.ui.geometry.Offset(-0.3333f, 1.0f), androidx.compose.ui.geometry.Offset(0.6667f, 0.0f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(1.3333f, 0.0f), solutionRotation = 0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-1.0f, 0.3333f), androidx.compose.ui.geometry.Offset(1.0f, 0.3333f), androidx.compose.ui.geometry.Offset(0.0f, -0.6667f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(2.0f, 0.6667f), solutionRotation = 0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.6667f, -0.3333f), androidx.compose.ui.geometry.Offset(0.3333f, -0.3333f), androidx.compose.ui.geometry.Offset(0.3333f, 0.6667f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.6667f, -0.6667f), solutionRotation = 0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.5f, -0.1667f), androidx.compose.ui.geometry.Offset(0.5f, -0.1667f), androidx.compose.ui.geometry.Offset(0.0f, 0.3333f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.0f, -0.3333f), solutionRotation = 0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(androidx.compose.ui.geometry.Offset(0.1667f, -0.5f), androidx.compose.ui.geometry.Offset(0.1667f, 0.5f), androidx.compose.ui.geometry.Offset(-0.3333f, 0.0f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.8333f, 0.5f), solutionRotation = 0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.5f, 0.0f), androidx.compose.ui.geometry.Offset(0.0f, -0.5f), androidx.compose.ui.geometry.Offset(0.5f, 0.0f), androidx.compose.ui.geometry.Offset(0.0f, 0.5f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.5f, 0.0f), solutionRotation = 0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.75f, -0.25f), androidx.compose.ui.geometry.Offset(0.25f, -0.25f), androidx.compose.ui.geometry.Offset(0.75f, 0.25f), androidx.compose.ui.geometry.Offset(-0.25f, 0.25f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(-0.25f, -0.75f), solutionRotation = 0f)
                )
            ),
            PregeneratedShape(
                id = "Shapes_Medium_puzzle_001",
                difficulty = "Medium",
                name = "Chevron",
                target = TargetShape(vertices = listOf(androidx.compose.ui.geometry.Offset(-1.0f, -1.0f), androidx.compose.ui.geometry.Offset(1.0f, -1.0f), androidx.compose.ui.geometry.Offset(1.0f, 1.0f), androidx.compose.ui.geometry.Offset(2.0f, 2.0f), androidx.compose.ui.geometry.Offset(0.0f, 2.0f), androidx.compose.ui.geometry.Offset(0.0f, 0.0f))),
                pieces = listOf(
                    PuzzlePiece(id = 1, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.3333f, -1.0f), androidx.compose.ui.geometry.Offset(-0.3333f, 1.0f), androidx.compose.ui.geometry.Offset(0.6667f, 0.0f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.3333f, 1.0f), solutionRotation = 0f),
                    PuzzlePiece(id = 2, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-1.0f, 0.3333f), androidx.compose.ui.geometry.Offset(1.0f, 0.3333f), androidx.compose.ui.geometry.Offset(0.0f, -0.6667f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(1.0f, 1.6667f), solutionRotation = 0f),
                    PuzzlePiece(id = 3, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.6667f, -0.3333f), androidx.compose.ui.geometry.Offset(0.3333f, -0.3333f), androidx.compose.ui.geometry.Offset(0.3333f, 0.6667f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.6667f, -0.6667f), solutionRotation = 0f),
                    PuzzlePiece(id = 4, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.5f, -0.1667f), androidx.compose.ui.geometry.Offset(0.5f, -0.1667f), androidx.compose.ui.geometry.Offset(0.0f, 0.3333f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.0f, -0.3333f), solutionRotation = 0f),
                    PuzzlePiece(id = 5, initialVertices = listOf(androidx.compose.ui.geometry.Offset(0.1667f, -0.5f), androidx.compose.ui.geometry.Offset(0.1667f, 0.5f), androidx.compose.ui.geometry.Offset(-0.3333f, 0.0f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.8333f, 0.5f), solutionRotation = 0f),
                    PuzzlePiece(id = 6, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.5f, 0.0f), androidx.compose.ui.geometry.Offset(0.0f, -0.5f), androidx.compose.ui.geometry.Offset(0.5f, 0.0f), androidx.compose.ui.geometry.Offset(0.0f, 0.5f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(0.5f, 0.0f), solutionRotation = 0f),
                    PuzzlePiece(id = 7, initialVertices = listOf(androidx.compose.ui.geometry.Offset(-0.75f, -0.25f), androidx.compose.ui.geometry.Offset(0.25f, -0.25f), androidx.compose.ui.geometry.Offset(0.75f, 0.25f), androidx.compose.ui.geometry.Offset(-0.25f, 0.25f)), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset(-0.25f, -0.75f), solutionRotation = 0f)
                )
            ),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}
