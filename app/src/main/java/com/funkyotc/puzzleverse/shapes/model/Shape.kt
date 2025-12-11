package com.funkyotc.puzzleverse.shapes.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils

// Represents a single geometric piece in the puzzle
data class PuzzlePiece(
    val id: Int,
    val initialVertices: List<Offset>, // Vertices in local space (centered or relative to 0,0)
    val position: Offset, // Center position in world space
    val rotation: Float = 0f, // Angle in degrees
    val isFlipped: Boolean = false,
    val color: Color = Color.Blue,
    val isLocked: Boolean = false // If true, piece is correctly placed and shouldn't move
) {
    // Computed property for current world vertices
    val currentVertices: List<Offset> get() = GeometryUtils.transformPolygon(
        initialVertices,
        position,
        rotation,
        isFlipped = isFlipped
    )
}

// Represents the target silhouette for the puzzle
data class TargetShape(
    val vertices: List<Offset>,
    val color: Color = Color.DarkGray
)

// Represents the entire state of the Shapes puzzle
data class ShapesPuzzle(
    val pieces: List<PuzzlePiece>,
    val target: TargetShape,
    val isComplete: Boolean = false
)