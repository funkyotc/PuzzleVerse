package com.funkyotc.puzzleverse.shapes.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Canonical tangram piece definitions on a 4-unit integer grid.
 * 
 * Derived from the standard dissection of a 4×4 square:
 *   (0,0)→(4,0)→(4,4)→(0,4)
 * 
 * All vertices are centered at each piece's centroid (0,0).
 * Multiply by GRID_SCALE at render time.
 */
object TangramPieces {

    /** Scale factor: grid units → virtual pixels */
    const val GRID_SCALE = 40f
    
    /** Offset to center the puzzle in the virtual canvas */
    val CANVAS_CENTER = Offset(200f, 200f)

    // Centroid-centered vertices (grid coordinates)
    val LARGE_TRIANGLE = listOf(
        Offset(-2.000000f, -0.666667f),
        Offset( 2.000000f, -0.666667f),
        Offset( 0.000000f,  1.333333f)
    )

    val MEDIUM_TRIANGLE = listOf(
        Offset(-0.666667f, -1.333333f),
        Offset( 1.333333f,  0.666667f),
        Offset(-0.666667f,  0.666667f)
    )

    val SMALL_TRIANGLE = listOf(
        Offset(-0.666667f,  0.000000f),
        Offset( 0.333333f, -1.000000f),
        Offset( 0.333333f,  1.000000f)
    )

    val SQUARE = listOf(
        Offset(-1.000000f,  0.000000f),
        Offset( 0.000000f, -1.000000f),
        Offset( 1.000000f,  0.000000f),
        Offset( 0.000000f,  1.000000f)
    )

    val PARALLELOGRAM = listOf(
        Offset(-1.500000f,  0.500000f),
        Offset( 0.500000f,  0.500000f),
        Offset( 1.500000f, -0.500000f),
        Offset(-0.500000f, -0.500000f)
    )

    // Piece colors (vibrant, distinguishable palette)
    val COLORS = mapOf(
        TangramPieceType.LARGE_TRIANGLE_1 to Color(0xFF3B82F6), // Blue
        TangramPieceType.LARGE_TRIANGLE_2 to Color(0xFF8B5CF6), // Purple
        TangramPieceType.MEDIUM_TRIANGLE  to Color(0xFF14B8A6), // Teal
        TangramPieceType.SMALL_TRIANGLE_1 to Color(0xFFF59E0B), // Amber
        TangramPieceType.SMALL_TRIANGLE_2 to Color(0xFFF97316), // Orange
        TangramPieceType.SQUARE           to Color(0xFFF43F5E), // Rose
        TangramPieceType.PARALLELOGRAM    to Color(0xFF10B981)  // Emerald
    )

    fun verticesForType(type: TangramPieceType): List<Offset> = when (type) {
        TangramPieceType.LARGE_TRIANGLE_1,
        TangramPieceType.LARGE_TRIANGLE_2 -> LARGE_TRIANGLE
        TangramPieceType.MEDIUM_TRIANGLE  -> MEDIUM_TRIANGLE
        TangramPieceType.SMALL_TRIANGLE_1,
        TangramPieceType.SMALL_TRIANGLE_2 -> SMALL_TRIANGLE
        TangramPieceType.SQUARE           -> SQUARE
        TangramPieceType.PARALLELOGRAM    -> PARALLELOGRAM
    }
}
