package com.funkyotc.puzzleverse.shapes.data

import androidx.compose.ui.geometry.Offset
import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetSilhouette
import com.funkyotc.puzzleverse.shapes.model.TangramPieceType
import com.funkyotc.puzzleverse.shapes.model.TangramPieces
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils

data class PiecePlacement(
    val x: Float,
    val y: Float,
    val rotation: Float,
    val flipped: Boolean = false
)

data class PregeneratedShape(
    override val id: String,
    override val difficulty: String,
    val name: String,
    val category: String,
    val placements: List<PiecePlacement>
) : BrowseablePuzzle {
    override val label: String get() = name
    override val subtitle: String get() = category

    fun toShapesPuzzle(): ShapesPuzzle {
        val types = listOf(
            TangramPieceType.LARGE_TRIANGLE_1, TangramPieceType.LARGE_TRIANGLE_2,
            TangramPieceType.MEDIUM_TRIANGLE, TangramPieceType.SMALL_TRIANGLE_1,
            TangramPieceType.SMALL_TRIANGLE_2, TangramPieceType.SQUARE,
            TangramPieceType.PARALLELOGRAM
        )
        val targetPolygons = types.zip(placements).map { (type, placement) ->
            val localVerts = TangramPieces.verticesForType(type)
            GeometryUtils.transformPolygon(
                localVerts,
                translation = Offset(placement.x, placement.y),
                rotationDegrees = placement.rotation,
                isFlipped = placement.flipped
            )
        }
        val pieces = types.zip(placements).mapIndexed { index, (type, placement) ->
            val localVerts = TangramPieces.verticesForType(type)
            val solutionPos = Offset(placement.x, placement.y)
            val solutionRot = placement.rotation
            val color = TangramPieces.COLORS[type] ?: androidx.compose.ui.graphics.Color.Blue
            PuzzlePiece(
                id = index + 1, type = type, localVertices = localVerts,
                position = Offset.Zero, rotation = 0f, isFlipped = false,
                color = color, isLocked = false,
                solutionPosition = solutionPos, solutionRotation = solutionRot,
                solutionFlipped = placement.flipped
            )
        }
        return ShapesPuzzle(id, name, pieces, TargetSilhouette(targetPolygons), false)
    }
}

object ShapesPregenerated {
    private fun puzzle(
        id: String, difficulty: String, name: String, category: String,
        lt1: PiecePlacement, lt2: PiecePlacement, mt: PiecePlacement,
        st1: PiecePlacement, st2: PiecePlacement, sq: PiecePlacement, para: PiecePlacement
    ): PregeneratedShape = PregeneratedShape(id, difficulty, name, category, listOf(lt1, lt2, mt, st1, st2, sq, para))

    val ALL_PUZZLES: List<PregeneratedShape> by lazy {
        listOf(
            puzzle("square", "easy", "Classic Square", "Geometric", PiecePlacement(2.5000f, 3.0000f, 45.0f), PiecePlacement(6.5000f, 3.0000f, 225.0f), PiecePlacement(4.5000f, 6.0000f, 135.0f), PiecePlacement(1.5000f, 8.0000f, 45.0f), PiecePlacement(7.5000f, 8.0000f, 315.0f), PiecePlacement(4.5000f, 3.0000f, 45.0f), PiecePlacement(4.5000f, 10.0000f, 45.0f)),
            puzzle("grand_triangle", "easy", "Grand Triangle", "Geometric", PiecePlacement(2.0000f, 3.0000f, 0.0f), PiecePlacement(6.0000f, 3.0000f, 180.0f), PiecePlacement(4.0000f, 6.0000f, 270.0f), PiecePlacement(1.0000f, 8.0000f, 90.0f), PiecePlacement(7.0000f, 8.0000f, 270.0f), PiecePlacement(4.0000f, 3.0000f, 0.0f), PiecePlacement(4.0000f, 10.0000f, 0.0f)),
            puzzle("rectangle", "easy", "Twin Rectangle", "Geometric", PiecePlacement(2.0000f, 4.0000f, 90.0f), PiecePlacement(6.0000f, 4.0000f, 270.0f), PiecePlacement(4.0000f, 1.5000f, 0.0f), PiecePlacement(1.0000f, 7.0000f, 0.0f), PiecePlacement(7.0000f, 7.0000f, 180.0f), PiecePlacement(4.0000f, 4.0000f, 45.0f), PiecePlacement(4.0000f, 9.5000f, 90.0f)),
            puzzle("parallelogram_geo", "medium", "Wide Parallelogram", "Geometric", PiecePlacement(2.5000f, 4.0000f, 135.0f), PiecePlacement(6.5000f, 4.0000f, 315.0f), PiecePlacement(4.5000f, 2.0000f, 225.0f), PiecePlacement(1.5000f, 1.0000f, 135.0f), PiecePlacement(7.5000f, 1.0000f, 45.0f), PiecePlacement(4.5000f, 4.0000f, 45.0f), PiecePlacement(4.5000f, 7.0000f, 135.0f)),
            puzzle("trapezoid", "easy", "Large Trapezoid", "Geometric", PiecePlacement(4.0000f, 2.0000f, 0.0f), PiecePlacement(4.0000f, 6.0000f, 180.0f), PiecePlacement(4.0000f, 9.5000f, 90.0f), PiecePlacement(1.5000f, 4.0000f, 90.0f), PiecePlacement(6.5000f, 4.0000f, 270.0f), PiecePlacement(4.0000f, 4.0000f, 0.0f), PiecePlacement(4.0000f, 12.0000f, 0.0f)),
            puzzle("pentagon", "medium", "Convex Pentagon", "Geometric", PiecePlacement(3.6464f, 2.2322f, 90.0f), PiecePlacement(6.4749f, 5.0607f, 270.0f), PiecePlacement(2.9393f, 5.7678f, 180.0f), PiecePlacement(-0.5962f, 5.0607f, 90.0f), PiecePlacement(3.6464f, 9.3033f, 0.0f), PiecePlacement(5.0607f, 3.6464f, 90.0f), PiecePlacement(0.1109f, 8.5962f, 90.0f)),
            puzzle("hexagon", "medium", "Symmetric Hexagon", "Geometric", PiecePlacement(3.2929f, 1.8787f, 45.0f), PiecePlacement(6.1213f, 4.7071f, 225.0f), PiecePlacement(2.5858f, 5.4142f, 315.0f), PiecePlacement(-0.9497f, 4.7071f, 135.0f), PiecePlacement(3.2929f, 8.9497f, 315.0f), PiecePlacement(4.7071f, 3.2929f, 45.0f), PiecePlacement(-0.2426f, 8.2426f, 45.0f)),
            puzzle("diamond", "easy", "Sparkling Diamond", "Geometric", PiecePlacement(2.5858f, 2.5858f, 135.0f), PiecePlacement(5.4142f, 5.4142f, 315.0f), PiecePlacement(5.7678f, 2.2322f, 45.0f), PiecePlacement(-0.2426f, 4.0000f, 45.0f), PiecePlacement(4.0000f, 8.2426f, 225.0f), PiecePlacement(4.0000f, 4.0000f, 90.0f), PiecePlacement(0.1109f, 7.8891f, 135.0f)),
            puzzle("arrow_geo", "easy", "North Arrow", "Geometric", PiecePlacement(2.9393f, 2.9393f, 180.0f), PiecePlacement(5.7678f, 5.7678f, 0.0f), PiecePlacement(5.7678f, 2.9393f, 270.0f), PiecePlacement(4.3536f, 0.1109f, 180.0f), PiecePlacement(8.5962f, 4.3536f, 90.0f), PiecePlacement(4.3536f, 4.3536f, 90.0f), PiecePlacement(2.2322f, 6.4749f, 180.0f)),
            puzzle("cross_geo", "hard", "Iron Cross", "Geometric", PiecePlacement(5.4142f, 2.5858f, 45.0f), PiecePlacement(2.5858f, 5.4142f, 225.0f), PiecePlacement(0.1109f, 7.8891f, 135.0f), PiecePlacement(2.2322f, 2.2322f, 135.0f), PiecePlacement(5.7678f, 5.7678f, 315.0f), PiecePlacement(4.0000f, 4.0000f, 45.0f), PiecePlacement(-1.6569f, 9.6569f, 45.0f)),
            puzzle("swan", "easy", "Graceful Swan", "Animals", PiecePlacement(5.0000f, 2.5000f, 135.0f), PiecePlacement(5.0000f, 6.5000f, 315.0f), PiecePlacement(2.0000f, 4.5000f, 225.0f), PiecePlacement(0.0000f, 1.5000f, 135.0f), PiecePlacement(0.0000f, 7.5000f, 45.0f), PiecePlacement(5.0000f, 4.5000f, 135.0f), PiecePlacement(-2.0000f, 4.5000f, 135.0f)),
            puzzle("cat", "easy", "Curious Cat", "Animals", PiecePlacement(5.0000f, 2.0000f, 90.0f), PiecePlacement(5.0000f, 6.0000f, 270.0f), PiecePlacement(2.0000f, 4.0000f, 0.0f), PiecePlacement(0.0000f, 1.0000f, 180.0f), PiecePlacement(0.0000f, 7.0000f, 0.0f), PiecePlacement(5.0000f, 4.0000f, 90.0f), PiecePlacement(-2.0000f, 4.0000f, 90.0f)),
            puzzle("dog", "medium", "Running Dog", "Animals", PiecePlacement(4.0000f, 2.0000f, 180.0f), PiecePlacement(4.0000f, 6.0000f, 0.0f), PiecePlacement(6.5000f, 4.0000f, 90.0f), PiecePlacement(1.0000f, 1.0000f, 90.0f), PiecePlacement(1.0000f, 7.0000f, 270.0f), PiecePlacement(4.0000f, 4.0000f, 135.0f), PiecePlacement(-1.5000f, 4.0000f, 180.0f)),
            puzzle("rabbit", "medium", "Hopping Rabbit", "Animals", PiecePlacement(4.0000f, 2.5000f, 225.0f), PiecePlacement(4.0000f, 6.5000f, 45.0f), PiecePlacement(6.0000f, 4.5000f, 315.0f), PiecePlacement(7.0000f, 1.5000f, 225.0f), PiecePlacement(7.0000f, 7.5000f, 135.0f), PiecePlacement(4.0000f, 4.5000f, 135.0f), PiecePlacement(1.0000f, 4.5000f, 225.0f)),
            puzzle("duck", "easy", "Floating Duck", "Animals", PiecePlacement(6.0000f, 4.0000f, 90.0f), PiecePlacement(2.0000f, 4.0000f, 270.0f), PiecePlacement(-1.5000f, 4.0000f, 180.0f), PiecePlacement(4.0000f, 1.5000f, 180.0f), PiecePlacement(4.0000f, 6.5000f, 0.0f), PiecePlacement(4.0000f, 4.0000f, 90.0f), PiecePlacement(-4.0000f, 4.0000f, 90.0f)),
            puzzle("horse", "hard", "Galloping Horse", "Animals", PiecePlacement(5.7678f, 3.6464f, 180.0f), PiecePlacement(2.9393f, 6.4749f, 0.0f), PiecePlacement(2.2322f, 2.9393f, 270.0f), PiecePlacement(2.9393f, -0.5962f, 180.0f), PiecePlacement(-1.3033f, 3.6464f, 90.0f), PiecePlacement(4.3536f, 5.0607f, 180.0f), PiecePlacement(-0.5962f, 0.1109f, 180.0f)),
            puzzle("fish", "easy", "Golden Fish", "Animals", PiecePlacement(6.1213f, 3.2929f, 135.0f), PiecePlacement(3.2929f, 6.1213f, 315.0f), PiecePlacement(2.5858f, 2.5858f, 45.0f), PiecePlacement(3.2929f, -0.9497f, 225.0f), PiecePlacement(-0.9497f, 3.2929f, 45.0f), PiecePlacement(4.7071f, 4.7071f, 135.0f), PiecePlacement(-0.2426f, -0.2426f, 135.0f)),
            puzzle("bird", "medium", "Soaring Eagle", "Animals", PiecePlacement(5.4142f, 2.5858f, 225.0f), PiecePlacement(2.5858f, 5.4142f, 45.0f), PiecePlacement(5.7678f, 5.7678f, 135.0f), PiecePlacement(4.0000f, -0.2426f, 135.0f), PiecePlacement(-0.2426f, 4.0000f, 315.0f), PiecePlacement(4.0000f, 4.0000f, 180.0f), PiecePlacement(0.1109f, 0.1109f, 225.0f)),
            puzzle("fox", "medium", "Clever Fox", "Animals", PiecePlacement(5.0607f, 2.9393f, 270.0f), PiecePlacement(2.2322f, 5.7678f, 90.0f), PiecePlacement(5.0607f, 5.7678f, 0.0f), PiecePlacement(7.8891f, 4.3536f, 270.0f), PiecePlacement(3.6464f, 8.5962f, 180.0f), PiecePlacement(3.6464f, 4.3536f, 180.0f), PiecePlacement(1.5251f, 2.2322f, 270.0f)),
            puzzle("turtle", "hard", "Sea Turtle", "Animals", PiecePlacement(5.4142f, 5.4142f, 135.0f), PiecePlacement(2.5858f, 2.5858f, 315.0f), PiecePlacement(0.1109f, 0.1109f, 225.0f), PiecePlacement(5.7678f, 2.2322f, 225.0f), PiecePlacement(2.2322f, 5.7678f, 45.0f), PiecePlacement(4.0000f, 4.0000f, 135.0f), PiecePlacement(-1.6569f, -1.6569f, 135.0f)),
            puzzle("butterfly", "medium", "Monarch Butterfly", "Animals", PiecePlacement(5.5000f, 5.0000f, 225.0f), PiecePlacement(1.5000f, 5.0000f, 45.0f), PiecePlacement(3.5000f, 2.0000f, 315.0f), PiecePlacement(6.5000f, 0.0000f, 225.0f), PiecePlacement(0.5000f, 0.0000f, 135.0f), PiecePlacement(3.5000f, 5.0000f, 225.0f), PiecePlacement(3.5000f, -2.0000f, 225.0f)),
            puzzle("crab", "hard", "Beach Crab", "Animals", PiecePlacement(6.0000f, 5.0000f, 180.0f), PiecePlacement(2.0000f, 5.0000f, 0.0f), PiecePlacement(4.0000f, 2.0000f, 90.0f), PiecePlacement(7.0000f, 0.0000f, 270.0f), PiecePlacement(1.0000f, 0.0000f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 180.0f), PiecePlacement(4.0000f, -2.0000f, 180.0f)),
            puzzle("runner", "medium", "Fast Runner", "People", PiecePlacement(6.0000f, 4.0000f, 270.0f), PiecePlacement(2.0000f, 4.0000f, 90.0f), PiecePlacement(4.0000f, 6.5000f, 180.0f), PiecePlacement(7.0000f, 1.0000f, 180.0f), PiecePlacement(1.0000f, 1.0000f, 0.0f), PiecePlacement(4.0000f, 4.0000f, 225.0f), PiecePlacement(4.0000f, -1.5000f, 270.0f)),
            puzzle("dancer", "medium", "Graceful Dancer", "People", PiecePlacement(5.5000f, 4.0000f, 315.0f), PiecePlacement(1.5000f, 4.0000f, 135.0f), PiecePlacement(3.5000f, 6.0000f, 45.0f), PiecePlacement(6.5000f, 7.0000f, 315.0f), PiecePlacement(0.5000f, 7.0000f, 225.0f), PiecePlacement(3.5000f, 4.0000f, 225.0f), PiecePlacement(3.5000f, 1.0000f, 315.0f)),
            puzzle("monk", "easy", "Meditating Monk", "People", PiecePlacement(4.0000f, 6.0000f, 180.0f), PiecePlacement(4.0000f, 2.0000f, 0.0f), PiecePlacement(4.0000f, -1.5000f, 270.0f), PiecePlacement(6.5000f, 4.0000f, 270.0f), PiecePlacement(1.5000f, 4.0000f, 90.0f), PiecePlacement(4.0000f, 4.0000f, 180.0f), PiecePlacement(4.0000f, -4.0000f, 180.0f)),
            puzzle("sitting_man", "easy", "Sitting Thinker", "People", PiecePlacement(4.3536f, 5.7678f, 270.0f), PiecePlacement(1.5251f, 2.9393f, 90.0f), PiecePlacement(5.0607f, 2.2322f, 0.0f), PiecePlacement(8.5962f, 2.9393f, 270.0f), PiecePlacement(4.3536f, -1.3033f, 180.0f), PiecePlacement(2.9393f, 4.3536f, 270.0f), PiecePlacement(7.8891f, -0.5962f, 270.0f)),
            puzzle("walking_man", "easy", "Walking Man", "People", PiecePlacement(4.7071f, 6.1213f, 225.0f), PiecePlacement(1.8787f, 3.2929f, 45.0f), PiecePlacement(5.4142f, 2.5858f, 135.0f), PiecePlacement(8.9497f, 3.2929f, 315.0f), PiecePlacement(4.7071f, -0.9497f, 135.0f), PiecePlacement(3.2929f, 4.7071f, 225.0f), PiecePlacement(8.2426f, -0.2426f, 225.0f)),
            puzzle("archer", "hard", "Bow Archer", "People", PiecePlacement(5.4142f, 5.4142f, 315.0f), PiecePlacement(2.5858f, 2.5858f, 135.0f), PiecePlacement(2.2322f, 5.7678f, 225.0f), PiecePlacement(8.2426f, 4.0000f, 225.0f), PiecePlacement(4.0000f, -0.2426f, 45.0f), PiecePlacement(4.0000f, 4.0000f, 270.0f), PiecePlacement(7.8891f, 0.1109f, 315.0f)),
            puzzle("reader", "easy", "Book Reader", "People", PiecePlacement(5.0607f, 5.0607f, 0.0f), PiecePlacement(2.2322f, 2.2322f, 180.0f), PiecePlacement(2.2322f, 5.0607f, 90.0f), PiecePlacement(3.6464f, 7.8891f, 0.0f), PiecePlacement(-0.5962f, 3.6464f, 270.0f), PiecePlacement(3.6464f, 3.6464f, 270.0f), PiecePlacement(5.7678f, 1.5251f, 0.0f)),
            puzzle("yogi", "hard", "Yoga Master", "People", PiecePlacement(2.5858f, 5.4142f, 225.0f), PiecePlacement(5.4142f, 2.5858f, 45.0f), PiecePlacement(7.8891f, 0.1109f, 315.0f), PiecePlacement(5.7678f, 5.7678f, 315.0f), PiecePlacement(2.2322f, 2.2322f, 135.0f), PiecePlacement(4.0000f, 4.0000f, 225.0f), PiecePlacement(9.6569f, -1.6569f, 225.0f)),
            puzzle("house", "easy", "Cosy Cottage", "Objects", PiecePlacement(3.0000f, 5.5000f, 315.0f), PiecePlacement(3.0000f, 1.5000f, 135.0f), PiecePlacement(6.0000f, 3.5000f, 45.0f), PiecePlacement(8.0000f, 6.5000f, 315.0f), PiecePlacement(8.0000f, 0.5000f, 225.0f), PiecePlacement(3.0000f, 3.5000f, 315.0f), PiecePlacement(10.0000f, 3.5000f, 315.0f)),
            puzzle("sailboat", "easy", "Ocean Sailboat", "Objects", PiecePlacement(3.0000f, 6.0000f, 270.0f), PiecePlacement(3.0000f, 2.0000f, 90.0f), PiecePlacement(6.0000f, 4.0000f, 180.0f), PiecePlacement(8.0000f, 7.0000f, 0.0f), PiecePlacement(8.0000f, 1.0000f, 180.0f), PiecePlacement(3.0000f, 4.0000f, 270.0f), PiecePlacement(10.0000f, 4.0000f, 270.0f)),
            puzzle("candle", "easy", "Birthday Candle", "Objects", PiecePlacement(4.0000f, 6.0000f, 0.0f), PiecePlacement(4.0000f, 2.0000f, 180.0f), PiecePlacement(1.5000f, 4.0000f, 270.0f), PiecePlacement(7.0000f, 7.0000f, 270.0f), PiecePlacement(7.0000f, 1.0000f, 90.0f), PiecePlacement(4.0000f, 4.0000f, 315.0f), PiecePlacement(9.5000f, 4.0000f, 0.0f)),
            puzzle("cup", "easy", "Coffee Cup", "Objects", PiecePlacement(4.0000f, 5.5000f, 45.0f), PiecePlacement(4.0000f, 1.5000f, 225.0f), PiecePlacement(2.0000f, 3.5000f, 135.0f), PiecePlacement(1.0000f, 6.5000f, 45.0f), PiecePlacement(1.0000f, 0.5000f, 315.0f), PiecePlacement(4.0000f, 3.5000f, 315.0f), PiecePlacement(7.0000f, 3.5000f, 45.0f)),
            puzzle("rocket", "medium", "Space Rocket", "Objects", PiecePlacement(2.0000f, 4.0000f, 270.0f), PiecePlacement(6.0000f, 4.0000f, 90.0f), PiecePlacement(9.5000f, 4.0000f, 0.0f), PiecePlacement(4.0000f, 6.5000f, 0.0f), PiecePlacement(4.0000f, 1.5000f, 180.0f), PiecePlacement(4.0000f, 4.0000f, 270.0f), PiecePlacement(12.0000f, 4.0000f, 270.0f)),
            puzzle("hammer", "easy", "Steel Hammer", "Objects", PiecePlacement(2.2322f, 4.3536f, 0.0f), PiecePlacement(5.0607f, 1.5251f, 180.0f), PiecePlacement(5.7678f, 5.0607f, 90.0f), PiecePlacement(5.0607f, 8.5962f, 0.0f), PiecePlacement(9.3033f, 4.3536f, 270.0f), PiecePlacement(3.6464f, 2.9393f, 0.0f), PiecePlacement(8.5962f, 7.8891f, 0.0f)),
            puzzle("bridge", "medium", "Arch Bridge", "Objects", PiecePlacement(1.8787f, 4.7071f, 315.0f), PiecePlacement(4.7071f, 1.8787f, 135.0f), PiecePlacement(5.4142f, 5.4142f, 225.0f), PiecePlacement(4.7071f, 8.9497f, 45.0f), PiecePlacement(8.9497f, 4.7071f, 225.0f), PiecePlacement(3.2929f, 3.2929f, 315.0f), PiecePlacement(8.2426f, 8.2426f, 315.0f)),
            puzzle("key", "medium", "Golden Key", "Objects", PiecePlacement(2.5858f, 5.4142f, 45.0f), PiecePlacement(5.4142f, 2.5858f, 225.0f), PiecePlacement(2.2322f, 2.2322f, 315.0f), PiecePlacement(4.0000f, 8.2426f, 315.0f), PiecePlacement(8.2426f, 4.0000f, 135.0f), PiecePlacement(4.0000f, 4.0000f, 0.0f), PiecePlacement(7.8891f, 7.8891f, 45.0f)),
            puzzle("heart", "medium", "Red Heart", "Objects", PiecePlacement(2.9393f, 5.0607f, 90.0f), PiecePlacement(5.7678f, 2.2322f, 270.0f), PiecePlacement(2.9393f, 2.2322f, 180.0f), PiecePlacement(0.1109f, 3.6464f, 90.0f), PiecePlacement(4.3536f, -0.5962f, 0.0f), PiecePlacement(4.3536f, 3.6464f, 0.0f), PiecePlacement(6.4749f, 5.7678f, 90.0f)),
            puzzle("boat", "hard", "Speed Boat", "Objects", PiecePlacement(2.5858f, 2.5858f, 315.0f), PiecePlacement(5.4142f, 5.4142f, 135.0f), PiecePlacement(7.8891f, 7.8891f, 45.0f), PiecePlacement(2.2322f, 5.7678f, 45.0f), PiecePlacement(5.7678f, 2.2322f, 225.0f), PiecePlacement(4.0000f, 4.0000f, 315.0f), PiecePlacement(9.6569f, 9.6569f, 315.0f)),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}