package com.funkyotc.puzzleverse.shapes.data

import androidx.compose.ui.geometry.Offset
import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetSilhouette
import com.funkyotc.puzzleverse.shapes.model.TangramPieceType
import com.funkyotc.puzzleverse.shapes.model.TangramPieces
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import kotlin.random.Random

data class PiecePlacement(
    val x: Float,        // Centroid X in grid coordinates
    val y: Float,        // Centroid Y in grid coordinates
    val rotation: Float, // Degrees (multiples of 45: 0, 45, 90, 135, 180, 225, 270, 315)
    val flipped: Boolean = false
)

data class PregeneratedShape(
    override val id: String,
    override val difficulty: String,
    val name: String,
    val category: String,
    // Order of placements: LT1, LT2, MT, ST1, ST2, SQ, PARA
    val placements: List<PiecePlacement>
) : BrowseablePuzzle {
    override val label: String get() = name
    override val subtitle: String get() = category

    private val cachedSilhouette: List<Offset> by lazy {
        computeSilhouette()
    }

    fun computeSilhouette(): List<Offset> {
        val types = listOf(
            TangramPieceType.LARGE_TRIANGLE_1,
            TangramPieceType.LARGE_TRIANGLE_2,
            TangramPieceType.MEDIUM_TRIANGLE,
            TangramPieceType.SMALL_TRIANGLE_1,
            TangramPieceType.SMALL_TRIANGLE_2,
            TangramPieceType.SQUARE,
            TangramPieceType.PARALLELOGRAM
        )

        val polygons = types.zip(placements).map { (type, placement) ->
            val localVerts = TangramPieces.verticesForType(type)
            GeometryUtils.transformPolygon(
                localVerts,
                translation = Offset(placement.x, placement.y),
                rotationDegrees = placement.rotation,
                isFlipped = placement.flipped
            )
        }

        val boundary = GeometryUtils.computeOuterBoundary(polygons)
        return boundary.ifEmpty {
            // Fallback to convex hull if boundary extraction fails
            polygons.flatten().distinct()
        }
    }

    fun toShapesPuzzle(): ShapesPuzzle {
        val types = listOf(
            TangramPieceType.LARGE_TRIANGLE_1,
            TangramPieceType.LARGE_TRIANGLE_2,
            TangramPieceType.MEDIUM_TRIANGLE,
            TangramPieceType.SMALL_TRIANGLE_1,
            TangramPieceType.SMALL_TRIANGLE_2,
            TangramPieceType.SQUARE,
            TangramPieceType.PARALLELOGRAM
        )

        val pieces = types.zip(placements).mapIndexed { index, (type, placement) ->
            val localVerts = TangramPieces.verticesForType(type)
            val solutionPos = Offset(placement.x, placement.y)
            val solutionRot = placement.rotation
            val color = TangramPieces.COLORS[type] ?: androidx.compose.ui.graphics.Color.Blue

            PuzzlePiece(
                id = index + 1,
                type = type,
                localVertices = localVerts,
                position = Offset.Zero, // initialized below in shuffled layout
                rotation = 0f,
                isFlipped = false,
                color = color,
                isLocked = false,
                solutionPosition = solutionPos,
                solutionRotation = solutionRot,
                solutionFlipped = placement.flipped
            )
        }

        val target = TargetSilhouette(cachedSilhouette)
        return ShapesPuzzle(id, name, pieces, target, false)
    }
}

object ShapesPregenerated {

    // Helper constructor for 7 piece placements
    private fun puzzle(
        id: String, difficulty: String, name: String, category: String,
        lt1: PiecePlacement, lt2: PiecePlacement, mt: PiecePlacement,
        st1: PiecePlacement, st2: PiecePlacement, sq: PiecePlacement, para: PiecePlacement
    ): PregeneratedShape {
        return PregeneratedShape(
            id = id,
            difficulty = difficulty,
            name = name,
            category = category,
            placements = listOf(lt1, lt2, mt, st1, st2, sq, para)
        )
    }

    val ALL_PUZZLES: List<PregeneratedShape> by lazy {
        listOf(
            // ==================== GEOMETRIC SHAPES (10) ====================
            puzzle("square", "easy", "Classic Square", "Geometric",
                PiecePlacement(2.0000f, 0.6667f, 0f),
                PiecePlacement(0.6667f, 2.0000f, 90f),
                PiecePlacement(2.6667f, 3.3333f, 0f),
                PiecePlacement(3.6667f, 1.0000f, 0f),
                PiecePlacement(3.6667f, 3.0000f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(1.5000f, 3.5000f, 0f, flipped = false)
            ),
            puzzle("grand_triangle", "easy", "Grand Triangle", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 180f),
                PiecePlacement(4.0000f, 2.6667f, 90f),
                PiecePlacement(3.0000f, 3.6667f, 0f),
                PiecePlacement(5.0000f, 3.6667f, 90f),
                PiecePlacement(4.0000f, 3.3333f, 0f),
                PiecePlacement(4.0000f, 4.5000f, 90f)
            ),
            puzzle("rectangle", "easy", "Twin Rectangle", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 180f),
                PiecePlacement(4.0000f, 0.6667f, 270f),
                PiecePlacement(0.6667f, 2.3333f, 90f),
                PiecePlacement(7.3333f, 0.3333f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 0f),
                PiecePlacement(5.5000f, 2.5000f, 0f)
            ),
            puzzle("parallelogram_geo", "medium", "Wide Parallelogram", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(4.0000f, 2.6667f, 180f),
                PiecePlacement(6.6667f, 1.3333f, 0f),
                PiecePlacement(0.6667f, 0.6667f, 90f),
                PiecePlacement(5.3333f, 3.3333f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(7.5000f, 2.5000f, 0f, flipped = false)
            ),
            puzzle("trapezoid", "easy", "Large Trapezoid", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 0f),
                PiecePlacement(4.0000f, 2.6667f, 180f),
                PiecePlacement(0.6667f, 0.6667f, 90f),
                PiecePlacement(7.3333f, 2.3333f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 0f),
                PiecePlacement(5.5000f, 2.5000f, 0f)
            ),
            puzzle("pentagon", "medium", "Convex Pentagon", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(0.6667f, 3.3333f, 90f),
                PiecePlacement(3.3333f, 3.3333f, 180f),
                PiecePlacement(4.3333f, 1.0000f, 0f),
                PiecePlacement(2.3333f, 4.3333f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(1.5000f, 4.5000f, 0f)
            ),
            puzzle("hexagon", "medium", "Symmetric Hexagon", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 180f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(0.6667f, 2.3333f, 90f),
                PiecePlacement(7.3333f, 0.3333f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 0f),
                PiecePlacement(5.5000f, 2.5000f, 0f)
            ),
            puzzle("diamond", "easy", "Sparkling Diamond", "Geometric",
                PiecePlacement(2.0000f, 1.3333f, 45f),
                PiecePlacement(4.0000f, 3.3333f, 225f),
                PiecePlacement(1.3333f, 3.3333f, 135f),
                PiecePlacement(4.6667f, 1.3333f, 45f),
                PiecePlacement(2.6667f, 4.3333f, 315f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(3.5000f, 3.5000f, 45f)
            ),
            puzzle("arrow_geo", "easy", "North Arrow", "Geometric",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 0.6667f, 90f),
                PiecePlacement(3.0000f, 4.0000f, 0f),
                PiecePlacement(5.0000f, 4.0000f, 90f),
                PiecePlacement(4.0000f, 3.0000f, 0f),
                PiecePlacement(4.0000f, 5.0000f, 90f)
            ),
            puzzle("cross_geo", "hard", "Iron Cross", "Geometric",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(4.0000f, 4.0000f, 180f),
                PiecePlacement(2.0000f, 4.6667f, 90f),
                PiecePlacement(0.6667f, 2.0000f, 0f),
                PiecePlacement(5.3333f, 4.0000f, 180f),
                PiecePlacement(3.0000f, 3.0000f, 45f),
                PiecePlacement(4.0000f, 1.5000f, 90f)
            ),

            // ==================== ANIMALS (12) ====================
            puzzle("swan", "easy", "Graceful Swan", "Animals",
                PiecePlacement(2.0000f, 2.6667f, 0f),
                PiecePlacement(4.0000f, 4.0000f, 180f),
                PiecePlacement(1.3333f, 0.6667f, 45f),
                PiecePlacement(2.6667f, 0.3333f, 0f),
                PiecePlacement(5.3333f, 4.6667f, 270f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(5.5000f, 2.5000f, 0f, flipped = false)
            ),
            puzzle("cat", "easy", "Curious Cat", "Animals",
                PiecePlacement(2.0000f, 3.0000f, 0f),
                PiecePlacement(4.0000f, 4.3333f, 180f),
                PiecePlacement(3.0000f, 1.6667f, 90f),
                PiecePlacement(2.0000f, 0.3333f, 0f),
                PiecePlacement(4.0000f, 0.3333f, 90f),
                PiecePlacement(3.0000f, 0.6667f, 45f),
                PiecePlacement(5.5000f, 3.0000f, 45f, flipped = false)
            ),
            puzzle("dog", "medium", "Running Dog", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 90f),
                PiecePlacement(4.6667f, 2.0000f, 270f),
                PiecePlacement(1.3333f, 0.6667f, 45f),
                PiecePlacement(0.3333f, 2.3333f, 0f),
                PiecePlacement(6.0000f, 3.3333f, 180f),
                PiecePlacement(3.0000f, 0.6667f, 0f),
                PiecePlacement(4.5000f, 3.5000f, 90f)
            ),
            puzzle("rabbit", "medium", "Hopping Rabbit", "Animals",
                PiecePlacement(2.0000f, 3.0000f, 0f),
                PiecePlacement(4.0000f, 3.0000f, 180f),
                PiecePlacement(1.3333f, 1.3333f, 45f),
                PiecePlacement(2.0000f, 0.3333f, 315f),
                PiecePlacement(5.3333f, 4.3333f, 90f),
                PiecePlacement(3.0000f, 1.6667f, 45f),
                PiecePlacement(0.5000f, 4.5000f, 0f)
            ),
            puzzle("duck", "easy", "Floating Duck", "Animals",
                PiecePlacement(3.0000f, 2.6667f, 0f),
                PiecePlacement(5.0000f, 4.0000f, 180f),
                PiecePlacement(1.6667f, 1.3333f, 45f),
                PiecePlacement(2.3333f, 0.3333f, 0f),
                PiecePlacement(6.3333f, 4.6667f, 270f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(0.5000f, 3.5000f, 0f)
            ),
            puzzle("horse", "hard", "Galloping Horse", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 90f),
                PiecePlacement(4.6667f, 2.0000f, 270f),
                PiecePlacement(1.3333f, 0.6667f, 45f),
                PiecePlacement(0.3333f, 4.3333f, 0f),
                PiecePlacement(6.0000f, 4.3333f, 180f),
                PiecePlacement(3.0000f, 0.6667f, 0f),
                PiecePlacement(3.5000f, 4.5000f, 90f)
            ),
            puzzle("fish", "easy", "Golden Fish", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 45f),
                PiecePlacement(4.0000f, 2.0000f, 225f),
                PiecePlacement(6.0000f, 2.0000f, 135f),
                PiecePlacement(5.3333f, 0.6667f, 45f),
                PiecePlacement(5.3333f, 3.3333f, 315f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(0.5000f, 2.0000f, 45f)
            ),
            puzzle("bird", "medium", "Soaring Eagle", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 0.6667f, 90f),
                PiecePlacement(1.0000f, 3.6667f, 0f),
                PiecePlacement(7.0000f, 3.6667f, 90f),
                PiecePlacement(4.0000f, 2.0000f, 0f),
                PiecePlacement(4.0000f, 3.5000f, 90f)
            ),
            puzzle("fox", "medium", "Clever Fox", "Animals",
                PiecePlacement(2.0000f, 3.0000f, 0f),
                PiecePlacement(4.0000f, 4.3333f, 180f),
                PiecePlacement(1.3333f, 1.3333f, 45f),
                PiecePlacement(2.0000f, 0.3333f, 0f),
                PiecePlacement(4.0000f, 0.3333f, 90f),
                PiecePlacement(3.0000f, 1.6667f, 45f),
                PiecePlacement(5.5000f, 3.0000f, 45f)
            ),
            puzzle("turtle", "hard", "Sea Turtle", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 45f),
                PiecePlacement(4.0000f, 2.0000f, 225f),
                PiecePlacement(3.0000f, 0.3333f, 180f),
                PiecePlacement(0.6667f, 2.0000f, 90f),
                PiecePlacement(5.3333f, 2.0000f, 270f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(3.0000f, 4.0000f, 0f)
            ),
            puzzle("butterfly", "medium", "Monarch Butterfly", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 45f),
                PiecePlacement(4.0000f, 2.0000f, 225f),
                PiecePlacement(3.0000f, 4.0000f, 0f),
                PiecePlacement(0.6667f, 0.6667f, 45f),
                PiecePlacement(5.3333f, 0.6667f, 315f),
                PiecePlacement(3.0000f, 2.0000f, 45f),
                PiecePlacement(3.0000f, 0.5000f, 90f)
            ),
            puzzle("crab", "hard", "Beach Crab", "Animals",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(0.6667f, 0.6667f, 45f),
                PiecePlacement(7.3333f, 0.6667f, 315f),
                PiecePlacement(4.0000f, 1.0000f, 0f),
                PiecePlacement(4.0000f, 4.5000f, 90f)
            ),

            // ==================== PEOPLE (8) ====================
            puzzle("runner", "medium", "Fast Runner", "People",
                PiecePlacement(2.0000f, 2.0000f, 45f),
                PiecePlacement(4.6667f, 3.3333f, 180f),
                PiecePlacement(1.3333f, 0.6667f, 45f),
                PiecePlacement(0.3333f, 3.6667f, 0f),
                PiecePlacement(6.0000f, 4.6667f, 180f),
                PiecePlacement(2.5000f, 0.3333f, 0f),
                PiecePlacement(4.0000f, 1.5000f, 90f)
            ),
            puzzle("dancer", "medium", "Graceful Dancer", "People",
                PiecePlacement(2.0000f, 3.0000f, 0f),
                PiecePlacement(4.0000f, 4.3333f, 180f),
                PiecePlacement(3.0000f, 1.6667f, 90f),
                PiecePlacement(3.0000f, 0.3333f, 0f),
                PiecePlacement(5.3333f, 5.3333f, 270f),
                PiecePlacement(3.0000f, 0.6667f, 45f),
                PiecePlacement(1.0000f, 5.0000f, 45f)
            ),
            puzzle("monk", "easy", "Meditating Monk", "People",
                PiecePlacement(2.0000f, 2.6667f, 0f),
                PiecePlacement(4.0000f, 2.6667f, 180f),
                PiecePlacement(3.0000f, 4.0000f, 180f),
                PiecePlacement(1.6667f, 0.6667f, 45f),
                PiecePlacement(4.3333f, 0.6667f, 315f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(3.0000f, 5.0000f, 90f)
            ),
            puzzle("sitting_man", "easy", "Sitting Thinker", "People",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(1.3333f, 0.6667f, 45f),
                PiecePlacement(2.3333f, 0.3333f, 0f),
                PiecePlacement(5.3333f, 4.3333f, 270f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(3.5000f, 4.5000f, 90f)
            ),
            puzzle("walking_man", "easy", "Walking Man", "People",
                PiecePlacement(2.0000f, 2.6667f, 0f),
                PiecePlacement(4.0000f, 4.0000f, 180f),
                PiecePlacement(3.0000f, 1.3333f, 90f),
                PiecePlacement(3.0000f, 0.3333f, 0f),
                PiecePlacement(5.3333f, 5.3333f, 270f),
                PiecePlacement(3.0000f, 0.6667f, 45f),
                PiecePlacement(1.0000f, 5.0000f, 0f)
            ),
            puzzle("archer", "hard", "Bow Archer", "People",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 0.6667f, 90f),
                PiecePlacement(0.6667f, 3.6667f, 0f),
                PiecePlacement(7.3333f, 3.6667f, 90f),
                PiecePlacement(4.0000f, 2.0000f, 0f),
                PiecePlacement(4.0000f, 3.5000f, 90f)
            ),
            puzzle("reader", "easy", "Book Reader", "People",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(1.3333f, 0.6667f, 45f),
                PiecePlacement(2.3333f, 0.3333f, 0f),
                PiecePlacement(5.3333f, 4.3333f, 270f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(1.5000f, 3.5000f, 0f)
            ),
            puzzle("yogi", "hard", "Yoga Master", "People",
                PiecePlacement(2.0000f, 2.6667f, 0f),
                PiecePlacement(4.0000f, 2.6667f, 180f),
                PiecePlacement(3.0000f, 4.0000f, 180f),
                PiecePlacement(1.0000f, 0.6667f, 45f),
                PiecePlacement(5.0000f, 0.6667f, 315f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(3.0000f, 5.0000f, 0f)
            ),

            // ==================== OBJECTS (10) ====================
            puzzle("house", "easy", "Cosy Cottage", "Objects",
                PiecePlacement(2.0000f, 2.6667f, 0f),
                PiecePlacement(6.0000f, 2.6667f, 180f),
                PiecePlacement(4.0000f, 1.3333f, 90f),
                PiecePlacement(2.0000f, 4.3333f, 0f),
                PiecePlacement(6.0000f, 4.3333f, 90f),
                PiecePlacement(4.0000f, 4.0000f, 0f),
                PiecePlacement(4.0000f, 5.5000f, 90f)
            ),
            puzzle("sailboat", "easy", "Ocean Sailboat", "Objects",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(4.0000f, 2.0000f, 180f),
                PiecePlacement(6.0000f, 4.0000f, 180f),
                PiecePlacement(1.0000f, 4.0000f, 0f),
                PiecePlacement(3.0000f, 4.0000f, 90f),
                PiecePlacement(3.0000f, 0.6667f, 45f),
                PiecePlacement(4.5000f, 4.5000f, 0f)
            ),
            puzzle("candle", "easy", "Birthday Candle", "Objects",
                PiecePlacement(2.0000f, 3.3333f, 0f),
                PiecePlacement(6.0000f, 3.3333f, 180f),
                PiecePlacement(4.0000f, 1.3333f, 90f),
                PiecePlacement(4.0000f, 0.3333f, 0f),
                PiecePlacement(7.3333f, 5.0000f, 270f),
                PiecePlacement(4.0000f, 2.3333f, 0f),
                PiecePlacement(1.5000f, 5.0000f, 0f)
            ),
            puzzle("cup", "easy", "Coffee Cup", "Objects",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(0.6667f, 0.6667f, 45f),
                PiecePlacement(7.3333f, 0.6667f, 315f),
                PiecePlacement(4.0000f, 1.0000f, 0f),
                PiecePlacement(7.5000f, 2.5000f, 90f)
            ),
            puzzle("rocket", "medium", "Space Rocket", "Objects",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 0.6667f, 90f),
                PiecePlacement(0.6667f, 4.0000f, 90f),
                PiecePlacement(7.3333f, 4.0000f, 270f),
                PiecePlacement(4.0000f, 3.0000f, 0f),
                PiecePlacement(4.0000f, 4.5000f, 90f)
            ),
            puzzle("hammer", "easy", "Steel Hammer", "Objects",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 180f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(4.0000f, 4.6667f, 0f),
                PiecePlacement(4.0000f, 6.0000f, 90f),
                PiecePlacement(4.0000f, 2.0000f, 0f),
                PiecePlacement(1.5000f, 1.5000f, 90f)
            ),
            puzzle("bridge", "medium", "Arch Bridge", "Objects",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 180f),
                PiecePlacement(4.0000f, 0.0000f, 90f),
                PiecePlacement(0.6667f, 2.3333f, 90f),
                PiecePlacement(7.3333f, 2.3333f, 270f),
                PiecePlacement(4.0000f, 1.6667f, 0f),
                PiecePlacement(4.0000f, 3.0000f, 90f)
            ),
            puzzle("key", "medium", "Golden Key", "Objects",
                PiecePlacement(2.0000f, 1.3333f, 0f),
                PiecePlacement(6.0000f, 1.3333f, 180f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(8.0000f, 1.3333f, 0f),
                PiecePlacement(8.0000f, 3.3333f, 90f),
                PiecePlacement(4.0000f, 2.0000f, 0f),
                PiecePlacement(0.5000f, 1.5000f, 0f)
            ),
            puzzle("heart", "medium", "Red Heart", "Objects",
                PiecePlacement(2.0000f, 2.0000f, 45f),
                PiecePlacement(4.0000f, 2.0000f, 225f),
                PiecePlacement(3.0000f, 4.0000f, 180f),
                PiecePlacement(0.6667f, 0.6667f, 45f),
                PiecePlacement(5.3333f, 0.6667f, 315f),
                PiecePlacement(3.0000f, 1.0000f, 45f),
                PiecePlacement(3.0000f, 5.0000f, 90f)
            ),
            puzzle("boat", "hard", "Speed Boat", "Objects",
                PiecePlacement(2.0000f, 2.0000f, 0f),
                PiecePlacement(6.0000f, 2.0000f, 180f),
                PiecePlacement(4.0000f, 3.3333f, 180f),
                PiecePlacement(0.6667f, 3.3333f, 90f),
                PiecePlacement(7.3333f, 3.3333f, 270f),
                PiecePlacement(4.0000f, 1.0000f, 0f),
                PiecePlacement(4.0000f, 4.5000f, 90f)
            )
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }
}