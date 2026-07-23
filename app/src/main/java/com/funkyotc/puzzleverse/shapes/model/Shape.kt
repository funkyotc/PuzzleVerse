package com.funkyotc.puzzleverse.shapes.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils

enum class TangramPieceType(val displayName: String) {
    LARGE_TRIANGLE_1("Large Triangle"),
    LARGE_TRIANGLE_2("Large Triangle"),
    MEDIUM_TRIANGLE("Medium Triangle"),
    SMALL_TRIANGLE_1("Small Triangle"),
    SMALL_TRIANGLE_2("Small Triangle"),
    SQUARE("Square"),
    PARALLELOGRAM("Parallelogram");

    /** Pieces that are interchangeable with this one (same shape) */
    val interchangeableWith: TangramPieceType? get() = when (this) {
        LARGE_TRIANGLE_1 -> LARGE_TRIANGLE_2
        LARGE_TRIANGLE_2 -> LARGE_TRIANGLE_1
        SMALL_TRIANGLE_1 -> SMALL_TRIANGLE_2
        SMALL_TRIANGLE_2 -> SMALL_TRIANGLE_1
        else -> null
    }

    /** Rotational symmetry period in degrees */
    val symmetryPeriod: Float get() = when (this) {
        SQUARE -> 90f
        PARALLELOGRAM -> 180f
        else -> 360f  // No rotational symmetry for triangles
    }

    /** Whether flip state matters for solution matching */
    val isFlipSignificant: Boolean get() = this == PARALLELOGRAM
}

data class PuzzlePiece(
    val id: Int,
    val type: TangramPieceType,
    val localVertices: List<Offset>,  // Canonical vertices centered at (0,0)
    val position: Offset,             // Current center in grid coordinates
    val rotation: Float = 0f,         // Degrees (multiples of 45)
    val isFlipped: Boolean = false,
    val color: Color,
    val isLocked: Boolean = false,
    val solutionPosition: Offset,
    val solutionRotation: Float,
    val solutionFlipped: Boolean = false
) {
    // Computed property for current world vertices in grid space
    val currentVertices: List<Offset> get() = GeometryUtils.transformPolygon(
        localVertices,
        position,
        rotation,
        isFlipped = isFlipped
    )
}

data class TargetSilhouette(
    val vertices: List<Offset>,  // The actual concave outline polygon in grid coordinates
    val color: Color = Color(0xFF1E293B)
)

data class ShapesPuzzle(
    val id: String,
    val name: String,
    val pieces: List<PuzzlePiece>,
    val target: TargetSilhouette,
    val isComplete: Boolean = false
)