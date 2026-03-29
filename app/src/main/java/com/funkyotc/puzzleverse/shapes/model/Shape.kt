package com.funkyotc.puzzleverse.shapes.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Defines a tangram piece in local space, with vertices defined around its centroid.
 */
data class PieceDefinition(
    val id: Int,
    val localVertices: List<Offset>, // Vertices relative to piece centroid
    val color: Color
) {
    val centroid: Offset get() {
        if (localVertices.isEmpty()) return Offset.Zero
        return Offset(
            localVertices.sumOf { it.x.toDouble() }.toFloat() / localVertices.size,
            localVertices.sumOf { it.y.toDouble() }.toFloat() / localVertices.size
        )
    }

    /**
     * Vertices in local space recentered so centroid is at origin.
     * (localVertices are already centroid-relative, so this is just a copy.)
     */
    val centeredVertices: List<Offset> get() {
        val c = centroid
        return localVertices.map { Offset(it.x - c.x, it.y - c.y) }
    }
}

/**
 * A piece instance in the puzzle — has a current position, rotation, flip state,
 * and optionally the correct transform for snapping.
 */
data class PuzzlePiece(
    val definitionId: Int,
    val position: Offset,
    val rotation: Float = 0f,
    val isFlipped: Boolean = false,
    val isLocked: Boolean = false,
    val correctPosition: Offset? = null,
    val correctRotation: Float? = null,
    val correctFlip: Boolean = false
) {
    companion object {
        fun fromDefinition(
            definition: PieceDefinition,
            position: Offset,
            rotation: Float = 0f,
            isFlipped: Boolean = false,
            correctPosition: Offset? = null,
            correctRotation: Float? = null
        ) = PuzzlePiece(
            definitionId = definition.id,
            position = position,
            rotation = rotation,
            isFlipped = isFlipped,
            correctPosition = correctPosition,
            correctRotation = correctRotation
        )
    }
}

/**
 * Represents the target silhouette for the puzzle.
 */
data class TargetShape(
    val vertices: List<Offset>,
    val centroid: Offset = if (vertices.isEmpty()) Offset.Zero else Offset(
        vertices.sumOf { it.x.toDouble() }.toFloat() / vertices.size,
        vertices.sumOf { it.y.toDouble() }.toFloat() / vertices.size
    )
)

/**
 * Complete puzzle state: target + piece instances.
 */
data class ShapesPuzzle(
    val target: TargetShape,
    val pieces: List<PuzzlePiece>,
    val isComplete: Boolean = false
)

/**
 * Pre-defined solvable puzzle templates.
 * Each template specifies a target silhouette and the correct placement of each piece.
 */
data class PuzzleTemplate(
    val name: String,
    val targetVertices: List<Offset>,
    val pieceCorrectPlacements: List<PieceCorrectPlacement>
)

/**
 * Correct placement for one piece within a template.
 */
data class PieceCorrectPlacement(
    val definitionId: Int,
    val position: Offset, // world-space centroid position
    val rotation: Float,
    val isFlipped: Boolean = false
)
