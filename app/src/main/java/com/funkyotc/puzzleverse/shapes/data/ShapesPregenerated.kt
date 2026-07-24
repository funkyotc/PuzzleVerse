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
            puzzle("square", "easy", "Classic Square", "Geometric", PiecePlacement(2.2000f, 4.0000f, 45.0f), PiecePlacement(6.8000f, 4.0000f, 225.0f), PiecePlacement(5.0000f, 6.5000f, 135.0f), PiecePlacement(2.2000f, 7.5000f, 45.0f), PiecePlacement(7.8000f, 7.5000f, 315.0f), PiecePlacement(5.0000f, 4.0000f, 45.0f), PiecePlacement(5.0000f, 8.8000f, 45.0f)),
            puzzle("grand_triangle", "easy", "Grand Triangle", "Geometric", PiecePlacement(2.5000f, 4.0000f, 90.0f), PiecePlacement(7.5000f, 4.0000f, 270.0f), PiecePlacement(5.0000f, 1.5000f, 0.0f), PiecePlacement(1.5000f, 7.0000f, 0.0f), PiecePlacement(8.5000f, 7.0000f, 180.0f), PiecePlacement(5.0000f, 4.0000f, 45.0f), PiecePlacement(5.0000f, 9.5000f, 90.0f)),
            puzzle("rectangle", "easy", "Twin Rectangle", "Geometric", PiecePlacement(2.5000f, 4.0000f, 135.0f), PiecePlacement(7.5000f, 4.0000f, 315.0f), PiecePlacement(5.0000f, 2.0000f, 225.0f), PiecePlacement(1.5000f, 1.0000f, 135.0f), PiecePlacement(8.5000f, 1.0000f, 45.0f), PiecePlacement(5.0000f, 4.0000f, 45.0f), PiecePlacement(5.0000f, 7.0000f, 135.0f)),
            puzzle("parallelogram_geo", "medium", "Wide Parallelogram", "Geometric", PiecePlacement(5.0000f, 2.5000f, 0.0f), PiecePlacement(5.0000f, 6.5000f, 180.0f), PiecePlacement(5.0000f, 9.5000f, 90.0f), PiecePlacement(2.5000f, 4.5000f, 90.0f), PiecePlacement(7.5000f, 4.5000f, 270.0f), PiecePlacement(5.0000f, 4.5000f, 0.0f), PiecePlacement(5.0000f, 12.0000f, 0.0f)),
            puzzle("trapezoid", "easy", "Large Trapezoid", "Geometric", PiecePlacement(2.5000f, 4.5000f, 0.0f), PiecePlacement(7.5000f, 4.5000f, 180.0f), PiecePlacement(5.0000f, 7.5000f, 90.0f), PiecePlacement(2.0000f, 7.5000f, 0.0f), PiecePlacement(8.0000f, 7.5000f, 90.0f), PiecePlacement(5.0000f, 4.5000f, 45.0f), PiecePlacement(5.0000f, 9.8000f, 90.0f)),
            puzzle("pentagon", "medium", "Convex Pentagon", "Geometric", PiecePlacement(3.7272f, 2.3130f, 90.0f), PiecePlacement(6.9799f, 5.5657f, 270.0f), PiecePlacement(3.9393f, 6.0607f, 180.0f), PiecePlacement(1.2523f, 4.7879f, 90.0f), PiecePlacement(5.2121f, 8.7477f, 0.0f), PiecePlacement(5.7071f, 4.2929f, 90.0f), PiecePlacement(2.3130f, 7.6870f, 90.0f)),
            puzzle("hexagon", "medium", "Symmetric Hexagon", "Geometric", PiecePlacement(3.9393f, 2.5251f, 135.0f), PiecePlacement(7.4749f, 6.0607f, 315.0f), PiecePlacement(7.4749f, 2.5251f, 45.0f), PiecePlacement(1.1109f, 3.9393f, 45.0f), PiecePlacement(6.0607f, 8.8891f, 225.0f), PiecePlacement(5.7071f, 4.2929f, 90.0f), PiecePlacement(1.8180f, 8.1820f, 135.0f)),
            puzzle("diamond", "easy", "Sparkling Diamond", "Geometric", PiecePlacement(3.9393f, 2.5251f, 180.0f), PiecePlacement(7.4749f, 6.0607f, 0.0f), PiecePlacement(7.1213f, 2.8787f, 270.0f), PiecePlacement(5.3536f, -0.3033f, 180.0f), PiecePlacement(10.3033f, 4.6464f, 90.0f), PiecePlacement(5.7071f, 4.2929f, 90.0f), PiecePlacement(3.5858f, 6.4142f, 180.0f)),
            puzzle("arrow_geo", "easy", "North Arrow", "Geometric", PiecePlacement(6.7678f, 3.2322f, 45.0f), PiecePlacement(3.9393f, 6.0607f, 225.0f), PiecePlacement(1.8180f, 8.1820f, 135.0f), PiecePlacement(3.5858f, 2.8787f, 135.0f), PiecePlacement(7.1213f, 6.4142f, 315.0f), PiecePlacement(5.3536f, 4.6464f, 45.0f), PiecePlacement(0.0503f, 9.9497f, 45.0f)),
            puzzle("cross_geo", "hard", "Iron Cross", "Geometric", PiecePlacement(3.5858f, 2.8787f, 45.0f), PiecePlacement(7.1213f, 6.4142f, 225.0f), PiecePlacement(3.2322f, 6.7678f, 135.0f), PiecePlacement(1.1109f, 4.6464f, 45.0f), PiecePlacement(5.3536f, 8.8891f, 135.0f), PiecePlacement(5.3536f, 4.6464f, 90.0f), PiecePlacement(1.6059f, 8.3941f, 135.0f)),
            puzzle("swan", "easy", "Graceful Swan", "Animals", PiecePlacement(6.0000f, 2.2000f, 135.0f), PiecePlacement(6.0000f, 6.8000f, 315.0f), PiecePlacement(3.5000f, 5.0000f, 225.0f), PiecePlacement(2.5000f, 2.2000f, 135.0f), PiecePlacement(2.5000f, 7.8000f, 45.0f), PiecePlacement(6.0000f, 5.0000f, 135.0f), PiecePlacement(1.2000f, 5.0000f, 135.0f)),
            puzzle("cat", "easy", "Curious Cat", "Animals", PiecePlacement(6.0000f, 2.5000f, 180.0f), PiecePlacement(6.0000f, 7.5000f, 0.0f), PiecePlacement(8.5000f, 5.0000f, 90.0f), PiecePlacement(3.0000f, 1.5000f, 90.0f), PiecePlacement(3.0000f, 8.5000f, 270.0f), PiecePlacement(6.0000f, 5.0000f, 135.0f), PiecePlacement(0.5000f, 5.0000f, 180.0f)),
            puzzle("dog", "medium", "Running Dog", "Animals", PiecePlacement(6.0000f, 2.5000f, 225.0f), PiecePlacement(6.0000f, 7.5000f, 45.0f), PiecePlacement(8.0000f, 5.0000f, 315.0f), PiecePlacement(9.0000f, 1.5000f, 225.0f), PiecePlacement(9.0000f, 8.5000f, 135.0f), PiecePlacement(6.0000f, 5.0000f, 135.0f), PiecePlacement(3.0000f, 5.0000f, 225.0f)),
            puzzle("rabbit", "medium", "Hopping Rabbit", "Animals", PiecePlacement(7.5000f, 5.0000f, 90.0f), PiecePlacement(3.5000f, 5.0000f, 270.0f), PiecePlacement(0.5000f, 5.0000f, 180.0f), PiecePlacement(5.5000f, 2.5000f, 180.0f), PiecePlacement(5.5000f, 7.5000f, 0.0f), PiecePlacement(5.5000f, 5.0000f, 90.0f), PiecePlacement(-2.0000f, 5.0000f, 90.0f)),
            puzzle("duck", "easy", "Floating Duck", "Animals", PiecePlacement(5.5000f, 2.5000f, 90.0f), PiecePlacement(5.5000f, 7.5000f, 270.0f), PiecePlacement(2.5000f, 5.0000f, 180.0f), PiecePlacement(2.5000f, 2.0000f, 90.0f), PiecePlacement(2.5000f, 8.0000f, 180.0f), PiecePlacement(5.5000f, 5.0000f, 135.0f), PiecePlacement(0.2000f, 5.0000f, 180.0f)),
            puzzle("horse", "hard", "Galloping Horse", "Animals", PiecePlacement(7.6870f, 3.7272f, 180.0f), PiecePlacement(4.4343f, 6.9799f, 0.0f), PiecePlacement(3.9393f, 3.9393f, 270.0f), PiecePlacement(5.2121f, 1.2523f, 180.0f), PiecePlacement(1.2523f, 5.2121f, 90.0f), PiecePlacement(5.7071f, 5.7071f, 180.0f), PiecePlacement(2.3130f, 2.3130f, 180.0f)),
            puzzle("fish", "easy", "Golden Fish", "Animals", PiecePlacement(7.4749f, 3.9393f, 225.0f), PiecePlacement(3.9393f, 7.4749f, 45.0f), PiecePlacement(7.4749f, 7.4749f, 135.0f), PiecePlacement(6.0607f, 1.1109f, 135.0f), PiecePlacement(1.1109f, 6.0607f, 315.0f), PiecePlacement(5.7071f, 5.7071f, 180.0f), PiecePlacement(1.8180f, 1.8180f, 225.0f)),
            puzzle("bird", "medium", "Soaring Eagle", "Animals", PiecePlacement(7.4749f, 3.9393f, 270.0f), PiecePlacement(3.9393f, 7.4749f, 90.0f), PiecePlacement(7.1213f, 7.1213f, 0.0f), PiecePlacement(10.3033f, 5.3536f, 270.0f), PiecePlacement(5.3536f, 10.3033f, 180.0f), PiecePlacement(5.7071f, 5.7071f, 180.0f), PiecePlacement(3.5858f, 3.5858f, 270.0f)),
            puzzle("fox", "medium", "Clever Fox", "Animals", PiecePlacement(6.7678f, 6.7678f, 135.0f), PiecePlacement(3.9393f, 3.9393f, 315.0f), PiecePlacement(1.8180f, 1.8180f, 225.0f), PiecePlacement(7.1213f, 3.5858f, 225.0f), PiecePlacement(3.5858f, 7.1213f, 45.0f), PiecePlacement(5.3536f, 5.3536f, 135.0f), PiecePlacement(0.0503f, 0.0503f, 135.0f)),
            puzzle("turtle", "hard", "Sea Turtle", "Animals", PiecePlacement(7.1213f, 3.5858f, 135.0f), PiecePlacement(3.5858f, 7.1213f, 315.0f), PiecePlacement(3.2322f, 3.2322f, 225.0f), PiecePlacement(5.3536f, 1.1109f, 135.0f), PiecePlacement(1.1109f, 5.3536f, 225.0f), PiecePlacement(5.3536f, 5.3536f, 180.0f), PiecePlacement(1.6059f, 1.6059f, 225.0f)),
            puzzle("butterfly", "medium", "Monarch Butterfly", "Animals", PiecePlacement(7.8000f, 6.0000f, 225.0f), PiecePlacement(3.2000f, 6.0000f, 45.0f), PiecePlacement(5.0000f, 3.5000f, 315.0f), PiecePlacement(7.8000f, 2.5000f, 225.0f), PiecePlacement(2.2000f, 2.5000f, 135.0f), PiecePlacement(5.0000f, 6.0000f, 225.0f), PiecePlacement(5.0000f, 1.2000f, 225.0f)),
            puzzle("crab", "hard", "Beach Crab", "Animals", PiecePlacement(7.5000f, 6.0000f, 270.0f), PiecePlacement(2.5000f, 6.0000f, 90.0f), PiecePlacement(5.0000f, 8.5000f, 180.0f), PiecePlacement(8.5000f, 3.0000f, 180.0f), PiecePlacement(1.5000f, 3.0000f, 0.0f), PiecePlacement(5.0000f, 6.0000f, 225.0f), PiecePlacement(5.0000f, 0.5000f, 270.0f)),
            puzzle("runner", "medium", "Fast Runner", "People", PiecePlacement(7.5000f, 6.0000f, 315.0f), PiecePlacement(2.5000f, 6.0000f, 135.0f), PiecePlacement(5.0000f, 8.0000f, 45.0f), PiecePlacement(8.5000f, 9.0000f, 315.0f), PiecePlacement(1.5000f, 9.0000f, 225.0f), PiecePlacement(5.0000f, 6.0000f, 225.0f), PiecePlacement(5.0000f, 3.0000f, 315.0f)),
            puzzle("dancer", "medium", "Graceful Dancer", "People", PiecePlacement(5.0000f, 7.5000f, 180.0f), PiecePlacement(5.0000f, 3.5000f, 0.0f), PiecePlacement(5.0000f, 0.5000f, 270.0f), PiecePlacement(7.5000f, 5.5000f, 270.0f), PiecePlacement(2.5000f, 5.5000f, 90.0f), PiecePlacement(5.0000f, 5.5000f, 180.0f), PiecePlacement(5.0000f, -2.0000f, 180.0f)),
            puzzle("monk", "easy", "Meditating Monk", "People", PiecePlacement(7.5000f, 5.5000f, 180.0f), PiecePlacement(2.5000f, 5.5000f, 0.0f), PiecePlacement(5.0000f, 2.5000f, 270.0f), PiecePlacement(8.0000f, 2.5000f, 180.0f), PiecePlacement(2.0000f, 2.5000f, 270.0f), PiecePlacement(5.0000f, 5.5000f, 225.0f), PiecePlacement(5.0000f, 0.2000f, 270.0f)),
            puzzle("sitting_man", "easy", "Sitting Thinker", "People", PiecePlacement(6.2728f, 7.6870f, 270.0f), PiecePlacement(3.0201f, 4.4343f, 90.0f), PiecePlacement(6.0607f, 3.9393f, 0.0f), PiecePlacement(8.7477f, 5.2121f, 270.0f), PiecePlacement(4.7879f, 1.2523f, 180.0f), PiecePlacement(4.2929f, 5.7071f, 270.0f), PiecePlacement(7.6870f, 2.3130f, 270.0f)),
            puzzle("walking_man", "easy", "Walking Man", "People", PiecePlacement(6.0607f, 7.4749f, 315.0f), PiecePlacement(2.5251f, 3.9393f, 135.0f), PiecePlacement(2.5251f, 7.4749f, 225.0f), PiecePlacement(8.8891f, 6.0607f, 225.0f), PiecePlacement(3.9393f, 1.1109f, 45.0f), PiecePlacement(4.2929f, 5.7071f, 270.0f), PiecePlacement(8.1820f, 1.8180f, 315.0f)),
            puzzle("archer", "hard", "Bow Archer", "People", PiecePlacement(6.0607f, 7.4749f, 0.0f), PiecePlacement(2.5251f, 3.9393f, 180.0f), PiecePlacement(2.8787f, 7.1213f, 90.0f), PiecePlacement(4.6464f, 10.3033f, 0.0f), PiecePlacement(-0.3033f, 5.3536f, 270.0f), PiecePlacement(4.2929f, 5.7071f, 270.0f), PiecePlacement(6.4142f, 3.5858f, 0.0f)),
            puzzle("reader", "easy", "Book Reader", "People", PiecePlacement(3.2322f, 6.7678f, 225.0f), PiecePlacement(6.0607f, 3.9393f, 45.0f), PiecePlacement(8.1820f, 1.8180f, 315.0f), PiecePlacement(6.4142f, 7.1213f, 315.0f), PiecePlacement(2.8787f, 3.5858f, 135.0f), PiecePlacement(4.6464f, 5.3536f, 225.0f), PiecePlacement(9.9497f, 0.0503f, 225.0f)),
            puzzle("yogi", "hard", "Yoga Master", "People", PiecePlacement(6.4142f, 7.1213f, 225.0f), PiecePlacement(2.8787f, 3.5858f, 45.0f), PiecePlacement(6.7678f, 3.2322f, 315.0f), PiecePlacement(8.8891f, 5.3536f, 225.0f), PiecePlacement(4.6464f, 1.1109f, 315.0f), PiecePlacement(4.6464f, 5.3536f, 270.0f), PiecePlacement(8.3941f, 1.6059f, 315.0f)),
            puzzle("house", "easy", "Cosy Cottage", "Objects", PiecePlacement(4.0000f, 7.8000f, 315.0f), PiecePlacement(4.0000f, 3.2000f, 135.0f), PiecePlacement(6.5000f, 5.0000f, 45.0f), PiecePlacement(7.5000f, 7.8000f, 315.0f), PiecePlacement(7.5000f, 2.2000f, 225.0f), PiecePlacement(4.0000f, 5.0000f, 315.0f), PiecePlacement(8.8000f, 5.0000f, 315.0f)),
            puzzle("sailboat", "easy", "Ocean Sailboat", "Objects", PiecePlacement(4.0000f, 7.5000f, 0.0f), PiecePlacement(4.0000f, 2.5000f, 180.0f), PiecePlacement(1.5000f, 5.0000f, 270.0f), PiecePlacement(7.0000f, 8.5000f, 270.0f), PiecePlacement(7.0000f, 1.5000f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 315.0f), PiecePlacement(9.5000f, 5.0000f, 0.0f)),
            puzzle("candle", "easy", "Birthday Candle", "Objects", PiecePlacement(4.0000f, 7.5000f, 45.0f), PiecePlacement(4.0000f, 2.5000f, 225.0f), PiecePlacement(2.0000f, 5.0000f, 135.0f), PiecePlacement(1.0000f, 8.5000f, 45.0f), PiecePlacement(1.0000f, 1.5000f, 315.0f), PiecePlacement(4.0000f, 5.0000f, 315.0f), PiecePlacement(7.0000f, 5.0000f, 45.0f)),
            puzzle("cup", "easy", "Coffee Cup", "Objects", PiecePlacement(2.5000f, 5.0000f, 270.0f), PiecePlacement(6.5000f, 5.0000f, 90.0f), PiecePlacement(9.5000f, 5.0000f, 0.0f), PiecePlacement(4.5000f, 7.5000f, 0.0f), PiecePlacement(4.5000f, 2.5000f, 180.0f), PiecePlacement(4.5000f, 5.0000f, 270.0f), PiecePlacement(12.0000f, 5.0000f, 270.0f)),
            puzzle("rocket", "medium", "Space Rocket", "Objects", PiecePlacement(4.5000f, 7.5000f, 270.0f), PiecePlacement(4.5000f, 2.5000f, 90.0f), PiecePlacement(7.5000f, 5.0000f, 0.0f), PiecePlacement(7.5000f, 8.0000f, 270.0f), PiecePlacement(7.5000f, 2.0000f, 0.0f), PiecePlacement(4.5000f, 5.0000f, 315.0f), PiecePlacement(9.8000f, 5.0000f, 0.0f)),
            puzzle("hammer", "easy", "Steel Hammer", "Objects", PiecePlacement(2.3130f, 6.2728f, 0.0f), PiecePlacement(5.5657f, 3.0201f, 180.0f), PiecePlacement(6.0607f, 6.0607f, 90.0f), PiecePlacement(4.7879f, 8.7477f, 0.0f), PiecePlacement(8.7477f, 4.7879f, 270.0f), PiecePlacement(4.2929f, 4.2929f, 0.0f), PiecePlacement(7.6870f, 7.6870f, 0.0f)),
            puzzle("bridge", "medium", "Arch Bridge", "Objects", PiecePlacement(2.5251f, 6.0607f, 45.0f), PiecePlacement(6.0607f, 2.5251f, 225.0f), PiecePlacement(2.5251f, 2.5251f, 315.0f), PiecePlacement(3.9393f, 8.8891f, 315.0f), PiecePlacement(8.8891f, 3.9393f, 135.0f), PiecePlacement(4.2929f, 4.2929f, 0.0f), PiecePlacement(8.1820f, 8.1820f, 45.0f)),
            puzzle("key", "medium", "Golden Key", "Objects", PiecePlacement(2.5251f, 6.0607f, 90.0f), PiecePlacement(6.0607f, 2.5251f, 270.0f), PiecePlacement(2.8787f, 2.8787f, 180.0f), PiecePlacement(-0.3033f, 4.6464f, 90.0f), PiecePlacement(4.6464f, -0.3033f, 0.0f), PiecePlacement(4.2929f, 4.2929f, 0.0f), PiecePlacement(6.4142f, 6.4142f, 90.0f)),
            puzzle("heart", "medium", "Red Heart", "Objects", PiecePlacement(3.2322f, 3.2322f, 315.0f), PiecePlacement(6.0607f, 6.0607f, 135.0f), PiecePlacement(8.1820f, 8.1820f, 45.0f), PiecePlacement(2.8787f, 6.4142f, 45.0f), PiecePlacement(6.4142f, 2.8787f, 225.0f), PiecePlacement(4.6464f, 4.6464f, 315.0f), PiecePlacement(9.9497f, 9.9497f, 315.0f)),
            puzzle("boat", "hard", "Speed Boat", "Objects", PiecePlacement(2.8787f, 6.4142f, 315.0f), PiecePlacement(6.4142f, 2.8787f, 135.0f), PiecePlacement(6.7678f, 6.7678f, 45.0f), PiecePlacement(4.6464f, 8.8891f, 315.0f), PiecePlacement(8.8891f, 4.6464f, 45.0f), PiecePlacement(4.6464f, 4.6464f, 0.0f), PiecePlacement(8.3941f, 8.3941f, 45.0f)),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}