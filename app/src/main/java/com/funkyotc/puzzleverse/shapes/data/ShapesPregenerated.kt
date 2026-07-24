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
            puzzle("square", "easy", "Classic Square", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("grand_triangle", "easy", "Grand Triangle", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("rectangle", "easy", "Twin Rectangle", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("parallelogram_geo", "medium", "Wide Parallelogram", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("trapezoid", "easy", "Large Trapezoid", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("pentagon", "medium", "Convex Pentagon", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("hexagon", "medium", "Symmetric Hexagon", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("diamond", "easy", "Sparkling Diamond", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("arrow_geo", "easy", "North Arrow", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("cross_geo", "hard", "Iron Cross", "Geometric", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("swan", "easy", "Graceful Swan", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("cat", "easy", "Curious Cat", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("dog", "medium", "Running Dog", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("rabbit", "medium", "Hopping Rabbit", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("duck", "easy", "Floating Duck", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("horse", "hard", "Galloping Horse", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("fish", "easy", "Golden Fish", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("bird", "medium", "Soaring Eagle", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("fox", "medium", "Clever Fox", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("turtle", "hard", "Sea Turtle", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("butterfly", "medium", "Monarch Butterfly", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("crab", "hard", "Beach Crab", "Animals", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("runner", "medium", "Fast Runner", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("dancer", "medium", "Graceful Dancer", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("monk", "easy", "Meditating Monk", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("sitting_man", "easy", "Sitting Thinker", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("walking_man", "easy", "Walking Man", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("archer", "hard", "Bow Archer", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("reader", "easy", "Book Reader", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("yogi", "hard", "Yoga Master", "People", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("house", "easy", "Cosy Cottage", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("sailboat", "easy", "Ocean Sailboat", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("candle", "easy", "Birthday Candle", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("cup", "easy", "Coffee Cup", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("rocket", "medium", "Space Rocket", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("hammer", "easy", "Steel Hammer", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("bridge", "medium", "Arch Bridge", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("key", "medium", "Golden Key", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("heart", "medium", "Red Heart", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
            puzzle("boat", "hard", "Speed Boat", "Objects", PiecePlacement(4.0000f, 2.6667f, 0.0f), PiecePlacement(2.6667f, 4.0000f, 270.0f), PiecePlacement(5.3333f, 3.3333f, 0.0f, flipped = true), PiecePlacement(3.0000f, 5.6667f, 90.0f), PiecePlacement(5.0000f, 5.6667f, 90.0f), PiecePlacement(4.0000f, 5.0000f, 0.0f), PiecePlacement(5.5000f, 4.5000f, 0.0f, flipped = true)),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}