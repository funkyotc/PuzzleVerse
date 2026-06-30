package com.funkyotc.puzzleverse.shapes.generator

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.funkyotc.puzzleverse.shapes.model.*
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Generates Shapes (tangram-style) puzzles.
 * Uses pre-defined solvable templates and randomizes piece starting positions/rotations.
 */
class ShapesPuzzleGenerator {

    companion object {
        // Unit size = 50f so the 8x8 board (400x400) fits nicely
        private const val U = 50f

        /** The 7 classic tangram pieces (defined around centroid, with unit size U) */
        val TANS: List<PieceDefinition> = listOf(
            // Small triangle — vertices centered at origin (centroid is at its center)
            PieceDefinition(
                id = 0,
                localVertices = listOf(
                    Offset(-0.5f * U, -0.25f * U),
                    Offset(0.5f * U, -0.25f * U),
                    Offset(0f, 0.5f * U)
                ),
                color = Color(0xFFE57373) // Red
            ),
            // Small triangle (mirror)
            PieceDefinition(
                id = 1,
                localVertices = listOf(
                    Offset(-0.5f * U, -0.25f * U),
                    Offset(0.5f * U, -0.25f * U),
                    Offset(0f, 0.5f * U)
                ),
                color = Color(0xFF81C784) // Green
            ),
            // Medium triangle
            PieceDefinition(
                id = 2,
                localVertices = listOf(
                    Offset(-1f * U, -0.5f * U),
                    Offset(1f * U, -0.5f * U),
                    Offset(0f, 1f * U)
                ),
                color = Color(0xFF64B5F6) // Blue
            ),
            // Large triangle 1
            PieceDefinition(
                id = 3,
                localVertices = listOf(
                    Offset(-1.5f * U, -0.75f * U),
                    Offset(1.5f * U, -0.75f * U),
                    Offset(0f, 1.5f * U)
                ),
                color = Color(0xFFFFD54F) // Yellow
            ),
            // Large triangle 2
            PieceDefinition(
                id = 4,
                localVertices = listOf(
                    Offset(-1.5f * U, -0.75f * U),
                    Offset(1.5f * U, -0.75f * U),
                    Offset(0f, 1.5f * U)
                ),
                color = Color(0xFFBA68C8) // Purple
            ),
            // Square
            PieceDefinition(
                id = 5,
                localVertices = listOf(
                    Offset(-0.5f * U, -0.5f * U),
                    Offset(0.5f * U, -0.5f * U),
                    Offset(0.5f * U, 0.5f * U),
                    Offset(-0.5f * U, 0.5f * U)
                ),
                color = Color(0xFFFF8A65) // Orange
            ),
            // Parallelogram
            PieceDefinition(
                id = 6,
                localVertices = listOf(
                    Offset(-1f * U, -0.5f * U),
                    Offset(0f, -0.5f * U),
                    Offset(1f * U, 0.5f * U),
                    Offset(0f, 0.5f * U)
                ),
                color = Color(0xFF4DB6AC) // Teal
            )
        )

        /** Centroid of each tan (for use in correct placements) */
        private val TAN_CENTROIDS: Map<Int, Offset> = TANS.associate { it.id to it.centroid }
    }

    /**
     * All puzzle templates, each using a subset of the 7 tans.
     * Vertices are in world space (centroid at origin).
     */
    private val templates: List<PuzzleTemplate> = listOf(

        // ─── Template 0: Square ───
        // Fills the 2U × 2U square (centroid at origin → corners at ±U)
        PuzzleTemplate(
            name = "Square",
            targetVertices = listOf(
                Offset(-2f * U, -2f * U),
                Offset(2f * U, -2f * U),
                Offset(2f * U, 2f * U),
                Offset(-2f * U, 2f * U)
            ),
            pieceCorrectPlacements = listOf(
                PieceCorrectPlacement(0, Offset(-0.5f * U, 0.5f * U), 180f),
                PieceCorrectPlacement(1, Offset(0.5f * U, 0.5f * U), 0f),
                PieceCorrectPlacement(2, Offset(-1f * U, -1f * U), 90f),
                PieceCorrectPlacement(3, Offset(0f, -1f * U), 180f),
                PieceCorrectPlacement(4, Offset(0f, 0.5f * U), 0f),
                PieceCorrectPlacement(5, Offset(0.5f * U, -0.5f * U), 0f),
                PieceCorrectPlacement(6, Offset(-0.5f * U, -0.5f * U), 0f)
            )
        ),

        // ─── Template 1: Cat / House silhouette ───
        // An L-shaped or house-like silhouette
        PuzzleTemplate(
            name = "House",
            targetVertices = listOf(
                Offset(-2f * U, 2f * U),
                Offset(-2f * U, -0.5f * U),
                Offset(0f, -0.5f * U),
                Offset(0f, -2f * U),
                Offset(2f * U, -2f * U),
                Offset(2f * U, 2f * U)
            ),
            pieceCorrectPlacements = listOf(
                // Small triangles make up the house body + roof
                PieceCorrectPlacement(0, Offset(-0.5f * U, -0.25f * U), 0f),
                PieceCorrectPlacement(1, Offset(0.5f * U, -1.5f * U), 180f),
                PieceCorrectPlacement(2, Offset(-1f * U, 0.5f * U), 270f),
                PieceCorrectPlacement(3, Offset(0.5f * U, 0.5f * U), 90f),
                PieceCorrectPlacement(4, Offset(-0.5f * U, -1f * U), 0f),
                PieceCorrectPlacement(5, Offset(0.5f * U, -0.5f * U), 0f),
                PieceCorrectPlacement(6, Offset(1f * U, 0f), 180f)
            )
        ),

        // ─── Template 2: Parallelogram / Rectangle ───
        PuzzleTemplate(
            name = "Rectangle",
            targetVertices = listOf(
                Offset(-2f * U, -U),
                Offset(2f * U, -U),
                Offset(2f * U, U),
                Offset(-2f * U, U)
            ),
            pieceCorrectPlacements = listOf(
                PieceCorrectPlacement(0, Offset(0f, 0.5f * U), 180f),
                PieceCorrectPlacement(1, Offset(0f, -0.5f * U), 0f),
                PieceCorrectPlacement(2, Offset(-0.5f * U, 0f), 270f),
                PieceCorrectPlacement(3, Offset(-0.5f * U, -U), 0f),
                PieceCorrectPlacement(4, Offset(0.5f * U, 0f), 0f),
                PieceCorrectPlacement(5, Offset(1f * U, 0f), 0f),
                PieceCorrectPlacement(6, Offset(-1.5f * U, 0f), 90f)
            )
        ),

        // ─── Template 3: Diamond ───
        PuzzleTemplate(
            name = "Diamond",
            targetVertices = listOf(
                Offset(0f, -2f * U),
                Offset(2f * U, 0f),
                Offset(0f, 2f * U),
                Offset(-2f * U, 0f)
            ),
            pieceCorrectPlacements = listOf(
                PieceCorrectPlacement(0, Offset(-0.5f * U, 0f), 90f),
                PieceCorrectPlacement(1, Offset(0.5f * U, 0f), 270f),
                PieceCorrectPlacement(2, Offset(0f, 0.5f * U), 180f),
                PieceCorrectPlacement(3, Offset(0f, -0.5f * U), 0f),
                PieceCorrectPlacement(4, Offset(0f, 0f), 0f),
                PieceCorrectPlacement(5, Offset(0.5f * U, -0.5f * U), 0f),
                PieceCorrectPlacement(6, Offset(-0.5f * U, -0.5f * U), 0f)
            )
        ),

        // ─── Template 4: Arrow ───
        PuzzleTemplate(
            name = "Arrow",
            targetVertices = listOf(
                Offset(0f, -2f * U),
                Offset(1f * U, -1f * U),
                Offset(1f * U, 1f * U),
                Offset(2f * U, 1f * U),
                Offset(0f, 2f * U),
                Offset(-2f * U, 1f * U),
                Offset(-1f * U, 1f * U),
                Offset(-1f * U, -1f * U)
            ),
            pieceCorrectPlacements = listOf(
                PieceCorrectPlacement(0, Offset(-1.5f * U, 0f), 180f),
                PieceCorrectPlacement(1, Offset(1.5f * U, 0f), 0f),
                PieceCorrectPlacement(2, Offset(-0.5f * U, 0f), 90f),
                PieceCorrectPlacement(3, Offset(0f, -0.5f * U), 180f),
                PieceCorrectPlacement(4, Offset(0f, 0.5f * U), 0f),
                PieceCorrectPlacement(5, Offset(0f, 0f), 0f),
                PieceCorrectPlacement(6, Offset(-0.5f * U, -0.5f * U), 0f)
            )
        )
    )

    /**
     * Generate a puzzle.
     * @param seed  Random seed for reproducibility.
     * @param templateIndex  Which template to use (-1 = pick randomly).
     */
    fun generate(seed: Long, templateIndex: Int = -1): ShapesPuzzle {
        val random = Random(seed)

        // Pick template
        val template = if (templateIndex in templates.indices) {
            templates[templateIndex]
        } else {
            templates[random.nextInt(templates.size)]
        }

        // Board dimensions (canvas is fillMaxSize, assume ~400dp × 800dp available)
        val boardWidth = 400f
        val boardHeight = 700f

        // Place each piece in the piece bank (below the board) with random rotation
        val tanDefs = TANS.associateBy { it.id }
        val correctPlacements = template.pieceCorrectPlacements.associateBy { it.definitionId }

        // Build piece instances: scramble initial rotation, place in bank area
        val bankTop = 100f  // y where piece bank starts (pieces start visible below)
        val bankHeight = boardHeight - bankTop

        val pieces = template.pieceCorrectPlacements.mapIndexed { index, placement ->
            val def = tanDefs[placement.definitionId]!!

            // Scatter pieces across the bank area
            val col = index % 3
            val row = index / 3
            val cellW = boardWidth / 3f
            val cellH = bankHeight / 3f
            val baseX = col * cellW + cellW / 2 + random.nextFloat() * 40f - 20f
            val baseY = bankTop + row * cellH + cellH / 2 + random.nextFloat() * 40f - 20f

            PuzzlePiece(
                definitionId = placement.definitionId,
                position = Offset(baseX, baseY),
                rotation = random.nextInt(4) * 90f + listOf(0f, 90f, 180f, 270f)[random.nextInt(4)],
                isFlipped = random.nextBoolean(),
                isLocked = false,
                correctPosition = placement.position,
                correctRotation = placement.rotation,
                correctFlip = placement.isFlipped
            )
        }

        // Center the target in the upper portion of the screen
        val targetCentroid = template.targetVertices.let { vs ->
            Offset(
                vs.sumOf { it.x.toDouble() }.toFloat() / vs.size,
                vs.sumOf { it.y.toDouble() }.toFloat() / vs.size
            )
        }
        // Offset target so it sits in the top half
        val targetOffset = Offset(boardWidth / 2 - targetCentroid.x, 120f - targetCentroid.y)
        val shiftedTarget = TargetShape(
            vertices = template.targetVertices.map { it + targetOffset },
            centroid = targetCentroid + targetOffset
        )

        // Shift correct placements to match
        val shiftedPieces = pieces.map { piece ->
            val cp = correctPlacements[piece.definitionId]!!
            piece.copy(
                correctPosition = cp.position + targetOffset,
                correctRotation = cp.rotation,
                correctFlip = cp.isFlipped
            )
        }

        return ShapesPuzzle(target = shiftedTarget, pieces = shiftedPieces)
    }
}
