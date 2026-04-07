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
    private val colorsLevel4 = listOf(Color(0xFFFF416C), Color(0xFFFF4B2B), Color(0xFFF9D423), Color(0xFFFF4E50), Color(0xFF9D50BB))
    private val colorsLevel5 = listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2))
    private val colorsLevel6 = listOf(Color(0xFF00B4DB), Color(0xFF0083B0), Color(0xFF1CB5E0), Color(0xFF000046))
    private val colorsLevel7 = listOf(Color(0xFF1D976C), Color(0xFF93F9B9), Color(0xFF1D2B64), Color(0xFFF8CDDA))
    private val colorsLevel8 = listOf(Color(0xFFFF5F6D), Color(0xFFFFC371), Color(0xFFE55D87))

    fun generateLevel(levelIndex: Int, random: Random): ShapesPuzzle {
        return when (levelIndex % 10) {
            0 -> createSquareLevel(random)
            1 -> createHouseLevel(random)
            2 -> createBoatLevel(random)
            3 -> createCrossLevel(random)
            4 -> createArrowLevel(random)
            5 -> createDiamondLevel(random)
            6 -> createHourglassLevel(random)
            7 -> createTrapezoidLevel(random)
            8 -> createRectangleLevel(random)
            9 -> createTallHouseLevel(random)
            else -> createSquareLevel(random)
        }
    }

    private fun createSquareLevel(random: Random): ShapesPuzzle {
        val p1Local = listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(-50f, 50f))
        val p2Local = listOf(Offset(50f, 50f), Offset(50f, -50f), Offset(-50f, 50f))
        
        val pieces = listOf(
            PuzzlePiece(1, p1Local, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel1[0], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, p2Local, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel1[1], solutionPosition = Offset(200f, 200f), solutionRotation = 0f)
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
            PuzzlePiece(1, roof, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[0], solutionPosition = Offset(200f, 150f), solutionRotation = 0f),
            PuzzlePiece(2, leftBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[1], solutionPosition = Offset(175f, 200f), solutionRotation = 0f),
            PuzzlePiece(3, rightBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[2], solutionPosition = Offset(225f, 200f), solutionRotation = 0f)
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
            PuzzlePiece(1, mast, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel3[0], solutionPosition = Offset(210f, 150f), solutionRotation = 0f),
            PuzzlePiece(2, hull, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel3[1], solutionPosition = Offset(200f, 220f), solutionRotation = 0f),
            PuzzlePiece(3, sailR, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel3[2], solutionPosition = Offset(220f, 160f), solutionRotation = 0f)
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

    private fun createCrossLevel(random: Random): ShapesPuzzle {
        val square = listOf(Offset(-25f, -25f), Offset(25f, -25f), Offset(25f, 25f), Offset(-25f, 25f))
        
        val pieces = listOf(
            PuzzlePiece(1, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel4[0], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel4[1], solutionPosition = Offset(200f, 150f), solutionRotation = 0f),
            PuzzlePiece(3, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel4[2], solutionPosition = Offset(200f, 250f), solutionRotation = 0f),
            PuzzlePiece(4, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel4[3], solutionPosition = Offset(150f, 200f), solutionRotation = 0f),
            PuzzlePiece(5, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel4[4], solutionPosition = Offset(250f, 200f), solutionRotation = 0f)
        )

        // Center at (200, 200)
        val targetVertices = listOf(
            Offset(175f, 125f), Offset(225f, 125f), Offset(225f, 175f), Offset(275f, 175f),
            Offset(275f, 225f), Offset(225f, 225f), Offset(225f, 275f), Offset(175f, 275f),
            Offset(175f, 225f), Offset(125f, 225f), Offset(125f, 175f), Offset(175f, 175f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createArrowLevel(random: Random): ShapesPuzzle {
        val base = listOf(Offset(-20f, -40f), Offset(20f, -40f), Offset(20f, 40f), Offset(-20f, 40f))
        val head = listOf(Offset(-40f, 0f), Offset(40f, 0f), Offset(0f, -60f))
        
        val pieces = listOf(
            PuzzlePiece(1, base, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel5[0], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, head, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel5[1], solutionPosition = Offset(200f, 160f), solutionRotation = 0f)
        )

        // Target: point up at (200, 100), handle from (180, 160) to (220, 240)
        val targetVertices = listOf(
            Offset(200f, 100f), Offset(240f, 160f), Offset(220f, 160f), Offset(220f, 240f),
            Offset(180f, 240f), Offset(180f, 160f), Offset(160f, 160f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createDiamondLevel(random: Random): ShapesPuzzle {
        val tri = listOf(Offset(0f, 0f), Offset(50f, 0f), Offset(0f, -50f))
        
        val pieces = listOf(
            PuzzlePiece(1, tri, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel6[0], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, tri, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel6[1], solutionPosition = Offset(200f, 200f), solutionRotation = 90f),
            PuzzlePiece(3, tri, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel6[2], solutionPosition = Offset(200f, 200f), solutionRotation = 180f),
            PuzzlePiece(4, tri, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel6[3], solutionPosition = Offset(200f, 200f), solutionRotation = 270f)
        )

        // Diamond center (200, 200). Size 100x100
        val targetVertices = listOf(
            Offset(200f, 100f), Offset(300f, 200f), Offset(200f, 300f), Offset(100f, 200f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createHourglassLevel(random: Random): ShapesPuzzle {
        val top = listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(0f, 0f))
        val bottom = listOf(Offset(-50f, 50f), Offset(50f, 50f), Offset(0f, 0f))
        val support = listOf(Offset(-5f, -20f), Offset(5f, -20f), Offset(5f, 20f), Offset(-5f, 20f))

        val pieces = listOf(
            PuzzlePiece(1, top, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel7[0], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, bottom, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel7[1], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(3, support, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel7[2], solutionPosition = Offset(200f, 200f), solutionRotation = 0f)
        )

        // Hourglass shape Target is slightly more complex
        val targetVertices = listOf(
            Offset(150f, 150f), Offset(250f, 150f), Offset(205f, 195f), Offset(205f, 205f), 
            Offset(250f, 250f), Offset(150f, 250f), Offset(195f, 205f), Offset(195f, 195f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createTrapezoidLevel(random: Random): ShapesPuzzle {
        val square = listOf(Offset(-40f, -40f), Offset(40f, -40f), Offset(40f, 40f), Offset(-40f, 40f))
        val triL = listOf(Offset(0f, -40f), Offset(0f, 40f), Offset(-40f, 40f))
        val triR = listOf(Offset(0f, -40f), Offset(40f, 40f), Offset(0f, 40f))

        val pieces = listOf(
            PuzzlePiece(1, triL, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel8[0], solutionPosition = Offset(160f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel8[1], solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(3, triR, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel8[2], solutionPosition = Offset(240f, 200f), solutionRotation = 0f)
        )

        // Target: (120, 160) to (280, 160), bottom is wider
        val targetVertices = listOf(
            Offset(160f, 160f), Offset(240f, 160f), Offset(280f, 240f), Offset(120f, 240f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createRectangleLevel(random: Random): ShapesPuzzle {
        val square = listOf(Offset(-25f, -25f), Offset(25f, -25f), Offset(25f, 25f), Offset(-25f, 25f))
        val pieces = listOf(
            PuzzlePiece(1, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel1[0], solutionPosition = Offset(175f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, square, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel1[1], solutionPosition = Offset(225f, 200f), solutionRotation = 0f)
        )
        val targetVertices = listOf(
            Offset(150f, 175f), Offset(250f, 175f), Offset(250f, 225f), Offset(150f, 225f)
        )
        return ShapesPuzzle(pieces, TargetShape(targetVertices))
    }

    private fun createTallHouseLevel(random: Random): ShapesPuzzle {
        val roof = listOf(Offset(0f, -50f), Offset(50f, 0f), Offset(-50f, 0f))
        val base = listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(50f, 50f), Offset(-50f, 50f))
        
        val leftBase = listOf(Offset(-25f, -50f), Offset(25f, -50f), Offset(25f, 50f), Offset(-25f, 50f))
        val rightBase = listOf(Offset(-25f, -50f), Offset(25f, -50f), Offset(25f, 50f), Offset(-25f, 50f))
        val topBase = listOf(Offset(-50f, -25f), Offset(50f, -25f), Offset(50f, 25f), Offset(-50f, 25f))

        val pieces = listOf(
            PuzzlePiece(1, roof, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[0], solutionPosition = Offset(200f, 100f), solutionRotation = 0f),
            PuzzlePiece(2, topBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[1], solutionPosition = Offset(200f, 175f), solutionRotation = 0f),
            PuzzlePiece(3, leftBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[2], solutionPosition = Offset(175f, 250f), solutionRotation = 0f),
            PuzzlePiece(4, rightBase, randomPosition(random), random.nextInt(4) * 90f, color = colorsLevel2[0], solutionPosition = Offset(225f, 250f), solutionRotation = 0f)
        )

        val targetVertices = listOf(
            Offset(200f, 50f),
            Offset(250f, 100f),
            Offset(250f, 150f),
            Offset(250f, 300f),
            Offset(150f, 300f),
            Offset(150f, 150f)
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
