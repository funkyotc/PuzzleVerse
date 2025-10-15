package com.funkyotc.puzzleverse.shapes.model

import androidx.compose.ui.geometry.Offset

// Represents a single geometric piece in the puzzle
data class PuzzlePiece(
    val id: Int,
    val vertices: List<Offset>,
    var position: Offset, // Top-left corner of the piece's bounding box
    var rotation: Float // Angle in degrees
)

// Represents the target silhouette for the puzzle
data class TargetShape(
    val vertices: List<Offset>
)

// Represents the entire state of the Shapes puzzle
data class ShapesPuzzle(
    val pieces: List<PuzzlePiece>,
    val target: TargetShape
)