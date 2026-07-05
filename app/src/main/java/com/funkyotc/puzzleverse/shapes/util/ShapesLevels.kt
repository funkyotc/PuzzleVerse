package com.funkyotc.puzzleverse.shapes.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import kotlin.random.Random

object ShapesLevels {

    private val colorSets = listOf(
        listOf(Color(0xFF6B8DD6), Color(0xFF8E37D7)),
        listOf(Color(0xFFFFB75E), Color(0xFFED8F03), Color(0xFFFF5252)),
        listOf(Color(0xFF11998E), Color(0xFF38EF7D), Color(0xFF00C9FF)),
        listOf(Color(0xFFFF416C), Color(0xFFFF4B2B), Color(0xFFF9D423), Color(0xFFFF4E50), Color(0xFF9D50BB)),
        listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2), Color(0xFF00B4DB)),
        listOf(Color(0xFF00B4DB), Color(0xFF0083B0), Color(0xFF1CB5E0), Color(0xFF000046)),
        listOf(Color(0xFF1D976C), Color(0xFF93F9B9), Color(0xFF1D2B64)),
        listOf(Color(0xFFFF5F6D), Color(0xFFFFC371), Color(0xFFE55D87)),
        listOf(Color(0xFF6B8DD6), Color(0xFF8E37D7), Color(0xFFFFB75E)),
        listOf(Color(0xFF4CAF50), Color(0xFF8BC34A), Color(0xFFCDDC39), Color(0xFFFFC107))
    )

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
        val colors = colorSets[0]
        val pieces = listOf(
            PuzzlePiece(
                1, listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(-50f, 50f)),
                randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f
            ),
            PuzzlePiece(
                2, listOf(Offset(-50f, 50f), Offset(50f, 50f), Offset(50f, -50f)),
                randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f
            )
        )
        val target = listOf(
            Offset(150f, 150f), Offset(250f, 150f), Offset(250f, 250f), Offset(150f, 250f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createHouseLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[1]
        val roof = listOf(Offset(0f, -50f), Offset(50f, 0f), Offset(-50f, 0f))
        val halfBase = listOf(Offset(-25f, -50f), Offset(25f, -50f), Offset(25f, 50f), Offset(-25f, 50f))
        val pieces = listOf(
            PuzzlePiece(1, roof, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 130f), solutionRotation = 0f),
            PuzzlePiece(2, halfBase, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(175f, 200f), solutionRotation = 0f),
            PuzzlePiece(3, halfBase, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(225f, 200f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(200f, 80f), Offset(250f, 130f), Offset(250f, 250f),
            Offset(150f, 250f), Offset(150f, 130f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createBoatLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[2]
        val hull = listOf(Offset(-60f, -20f), Offset(60f, -20f), Offset(60f, 20f), Offset(-60f, 20f))
        val mast = listOf(Offset(-10f, -50f), Offset(10f, -50f), Offset(10f, 50f), Offset(-10f, 50f))
        val sail = listOf(Offset(0f, -40f), Offset(40f, 40f), Offset(0f, 40f))
        val pieces = listOf(
            PuzzlePiece(1, mast, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(210f, 150f), solutionRotation = 0f),
            PuzzlePiece(2, hull, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 220f), solutionRotation = 0f),
            PuzzlePiece(3, sail, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(220f, 160f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(200f, 100f), Offset(220f, 100f), Offset(220f, 120f),
            Offset(260f, 200f), Offset(260f, 240f), Offset(140f, 240f),
            Offset(140f, 200f), Offset(200f, 200f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createCrossLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[3]
        val square = listOf(Offset(-25f, -25f), Offset(25f, -25f), Offset(25f, 25f), Offset(-25f, 25f))
        val pieces = listOf(
            PuzzlePiece(1, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 150f), solutionRotation = 0f),
            PuzzlePiece(3, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(200f, 250f), solutionRotation = 0f),
            PuzzlePiece(4, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[3],
                solutionPosition = Offset(150f, 200f), solutionRotation = 0f),
            PuzzlePiece(5, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[4],
                solutionPosition = Offset(250f, 200f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(175f, 125f), Offset(225f, 125f), Offset(225f, 175f), Offset(275f, 175f),
            Offset(275f, 225f), Offset(225f, 225f), Offset(225f, 275f), Offset(175f, 275f),
            Offset(175f, 225f), Offset(125f, 225f), Offset(125f, 175f), Offset(175f, 175f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createArrowLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[4]
        val shaft = listOf(Offset(-20f, -40f), Offset(20f, -40f), Offset(20f, 40f), Offset(-20f, 40f))
        val head = listOf(Offset(-40f, 0f), Offset(40f, 0f), Offset(0f, -60f))
        val pieces = listOf(
            PuzzlePiece(1, head, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 160f), solutionRotation = 0f),
            PuzzlePiece(2, shaft, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(200f, 100f), Offset(240f, 160f), Offset(220f, 160f),
            Offset(220f, 240f), Offset(180f, 240f), Offset(180f, 160f), Offset(160f, 160f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createDiamondLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[5]
        val tri = listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(0f, -100f))
        val pieces = listOf(
            PuzzlePiece(1, tri, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, tri, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 200f), solutionRotation = 90f),
            PuzzlePiece(3, tri, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(200f, 200f), solutionRotation = 180f),
            PuzzlePiece(4, tri, randomPosition(random), random.nextInt(4) * 90f, color = colors[3],
                solutionPosition = Offset(200f, 200f), solutionRotation = 270f)
        )
        val target = listOf(
            Offset(200f, 100f), Offset(300f, 200f), Offset(200f, 300f), Offset(100f, 200f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createHourglassLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[6]
        val topTri = listOf(Offset(-50f, -13.33f), Offset(50f, -13.33f), Offset(0f, 26.67f))
        val botTri = listOf(Offset(-50f, 13.33f), Offset(50f, 13.33f), Offset(0f, -26.67f))
        val connector = listOf(Offset(-10f, -10f), Offset(10f, -10f), Offset(10f, 10f), Offset(-10f, 10f))

        val pieces = listOf(
            PuzzlePiece(1, topTri, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 163.33f), solutionRotation = 0f),
            PuzzlePiece(2, botTri, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 236.67f), solutionRotation = 0f),
            PuzzlePiece(3, connector, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(150f, 150f), Offset(250f, 150f), Offset(200f, 190f),
            Offset(210f, 190f), Offset(210f, 210f), Offset(200f, 210f),
            Offset(250f, 250f), Offset(150f, 250f), Offset(190f, 210f),
            Offset(190f, 190f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createTrapezoidLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[7]
        val square = listOf(Offset(-40f, -40f), Offset(40f, -40f), Offset(40f, 40f), Offset(-40f, 40f))
        val triL = listOf(Offset(0f, -40f), Offset(0f, 40f), Offset(-40f, 40f))
        val triR = listOf(Offset(0f, -40f), Offset(40f, 40f), Offset(0f, 40f))
        val pieces = listOf(
            PuzzlePiece(1, triL, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(160f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 200f), solutionRotation = 0f),
            PuzzlePiece(3, triR, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(240f, 200f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(160f, 160f), Offset(240f, 160f), Offset(280f, 240f), Offset(120f, 240f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createRectangleLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[8]
        val square = listOf(Offset(-25f, -25f), Offset(25f, -25f), Offset(25f, 25f), Offset(-25f, 25f))
        val pieces = listOf(
            PuzzlePiece(1, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(175f, 200f), solutionRotation = 0f),
            PuzzlePiece(2, square, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(225f, 200f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(150f, 175f), Offset(250f, 175f), Offset(250f, 225f), Offset(150f, 225f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun createTallHouseLevel(random: Random): ShapesPuzzle {
        val colors = colorSets[9]
        val roof = listOf(Offset(0f, -50f), Offset(50f, 0f), Offset(-50f, 0f))
        val midBase = listOf(Offset(-50f, -25f), Offset(50f, -25f), Offset(50f, 25f), Offset(-50f, 25f))
        val halfBase = listOf(Offset(-25f, -50f), Offset(25f, -50f), Offset(25f, 50f), Offset(-25f, 50f))
        val pieces = listOf(
            PuzzlePiece(1, roof, randomPosition(random), random.nextInt(4) * 90f, color = colors[0],
                solutionPosition = Offset(200f, 100f), solutionRotation = 0f),
            PuzzlePiece(2, midBase, randomPosition(random), random.nextInt(4) * 90f, color = colors[1],
                solutionPosition = Offset(200f, 175f), solutionRotation = 0f),
            PuzzlePiece(3, halfBase, randomPosition(random), random.nextInt(4) * 90f, color = colors[2],
                solutionPosition = Offset(175f, 250f), solutionRotation = 0f),
            PuzzlePiece(4, halfBase, randomPosition(random), random.nextInt(4) * 90f, color = colors[3],
                solutionPosition = Offset(225f, 250f), solutionRotation = 0f)
        )
        val target = listOf(
            Offset(200f, 50f), Offset(250f, 100f), Offset(250f, 150f),
            Offset(250f, 300f), Offset(150f, 300f), Offset(150f, 150f)
        )
        return ShapesPuzzle(pieces, TargetShape(target))
    }

    private fun randomPosition(random: Random): Offset {
        return Offset(
            random.nextInt(15, 30) * 10f,
            random.nextInt(40, 60) * 10f
        )
    }
}
