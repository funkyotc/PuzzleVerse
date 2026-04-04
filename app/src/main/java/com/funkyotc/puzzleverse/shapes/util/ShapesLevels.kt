package com.funkyotc.puzzleverse.shapes.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import kotlin.random.Random

object ShapesLevels {

    private val colorsLevel1 = listOf(Color(0xFF6B8DD6), Color(0xFF8E37D7))
    private val colorsLevel2 = listOf(Color(0xFFFFB75E), Color(0xFFED8F03), Color(0xFFFF5252))
    private val colorsLevel3 = listOf(Color(0xFF11998E), Color(0xFF38EF7D), Color(0xFF00C9FF), Color(0xFF92FE9D))

    fun generateLevel(levelIndex: Int, random: Random): ShapesPuzzle {
        return when (levelIndex % 3) {
            0 -> createSquareLevel(random)
            1 -> createHouseLevel(random)
            else -> createBoatLevel(random)
        }
    }

    private fun createSquareLevel(random: Random): ShapesPuzzle {
        val p1Local = listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(-50f, 50f))
        val p2Local = listOf(Offset(50f, 50f), Offset(50f, -50f), Offset(-50f, 50f))
        
        val pieces = listOf(
            PuzzlePiece(1, p1Local, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel1[0]),
            PuzzlePiece(2, p2Local, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel1[1])
        )

        val targetVertices = listOf(
             Offset(150f, 150f),
             Offset(250f, 150f),
             Offset(250f, 250f),
             Offset(150f, 250f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createHouseLevel(random: Random): ShapesPuzzle {
        // Roof: Triangle
        val roof = listOf(Offset(0f, -50f), Offset(50f, 0f), Offset(-50f, 0f))
        // Base: Square
        val base = listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(50f, 50f), Offset(-50f, 50f))
        // Door: Rectangle (small)
        val door = listOf(Offset(-20f, -30f), Offset(20f, -30f), Offset(20f, 30f), Offset(-20f, 30f))

        val pieces = listOf(
            PuzzlePiece(1, roof, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[0]),
            PuzzlePiece(2, base, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[1]),
            PuzzlePiece(3, door, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[2])
        )

        val targetVertices = listOf(
            Offset(200f, 100f), // top of roof
            Offset(250f, 150f), // right roof edge
            Offset(250f, 250f), // bottom right base
            Offset(150f, 250f), // bottom left base
            Offset(150f, 150f)  // left roof edge
        )
        // Wait, door is inside base? Tangram pieces must not overlap. 
        // House should be composed of pieces that form exactly the target.
        // Base square piece + roof triangle piece. That's 2 pieces. If 3 pieces:
        // Left base rect, Right base rect, Roof triangle.
        
        val leftBase = listOf(Offset(-25f, -50f), Offset(25f, -50f), Offset(25f, 50f), Offset(-25f, 50f))
        val rightBase = listOf(Offset(-25f, -50f), Offset(25f, -50f), Offset(25f, 50f), Offset(-25f, 50f))
        
        val fixedPieces = listOf(
            PuzzlePiece(1, roof, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[0]),
            PuzzlePiece(2, leftBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[1]),
            PuzzlePiece(3, rightBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[2])
        )
        return ShapesPuzzle(fixedPieces, TargetShape(targetVertices))
    }

    private fun createBoatLevel(random: Random): ShapesPuzzle {
        // Sail: Triangle
        val sail = listOf(Offset(-50f, -50f), Offset(50f, 50f), Offset(-50f, 50f))
        // Hull Left: Triangle
        val hullLeft = listOf(Offset(-50f, 0f), Offset(50f, 50f), Offset(-50f, 50f)) // Wait, need simple shapes
        // Let's use simple blocks for Boat
        // Mast: narrow rect
        val mast = listOf(Offset(-10f, -50f), Offset(10f, -50f), Offset(10f, 50f), Offset(-10f, 50f))
        // Hull center: wide rect
        val hull = listOf(Offset(-60f, -20f), Offset(60f, -20f), Offset(60f, 20f), Offset(-60f, 20f))
        // Sail right: triangle
        val sailR = listOf(Offset(0f, -40f), Offset(40f, 40f), Offset(0f, 40f))
        
        val pieces = listOf(
            PuzzlePiece(1, mast, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel3[0]),
            PuzzlePiece(2, hull, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel3[1]),
            PuzzlePiece(3, sailR, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel3[2])
        )
        
        // Target shape roughly resembling the combined pieces
        val targetVertices = listOf(
            Offset(200f, 100f), // Mast top
            Offset(220f, 100f), // Mast right edge top
            Offset(220f, 120f), // Sail top
            Offset(260f, 200f), // Sail right bottom
            Offset(220f, 200f), // mast right edge bottom area
            Offset(260f, 200f), // hull right top
            Offset(260f, 240f), // hull right bottom
            Offset(140f, 240f), // hull left bottom
            Offset(140f, 200f), // hull left top
            Offset(200f, 200f)  // mast left bottom
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun randomPosition(random: Random): Offset {
        return Offset(
            random.nextInt(15, 30) * 10f, 
            random.nextInt(40, 60) * 10f
        )
    }
}
