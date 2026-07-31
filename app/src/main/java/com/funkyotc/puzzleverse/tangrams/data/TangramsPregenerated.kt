package com.funkyotc.puzzleverse.tangrams.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

enum class TangramPieceType {
    LARGE_TRIANGLE,
    MEDIUM_TRIANGLE,
    SQUARE,
    PARALLELOGRAM,
    SMALL_TRIANGLE
}

data class TangramPiecePlacement(
    val pieceType: TangramPieceType,
    val centerX: Float,
    val centerY: Float,
    val vertices: List<Pair<Float, Float>>
)

data class TangramPuzzleInfo(
    override val id: String,
    override val difficulty: String,
    val name: String,
    val assetFileName: String,
    val emoji: String,
    val piecePlacements: List<TangramPiecePlacement> = emptyList()
) : BrowseablePuzzle {
    override val label: String get() = name
    override val subtitle: String get() = emoji
}

object TangramsPregenerated {
    val ALL_PUZZLES = listOf(
        TangramPuzzleInfo(
            id = "triangle",
            difficulty = "Easy",
            name = "Triangle",
            assetFileName = "tangrams/triangle.svg",
            emoji = "🔺",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 5.0f, 0.99999994f, listOf(Pair(6.0f, 1.9999998f), Pair(4.0000005f, 1.65E-7f), Pair(4.0000005f, 1.999999f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 3.0f, 2.0f, listOf(Pair(2.9999995f, 0.9999997f), Pair(4.0f, 2.0f), Pair(3.0f, 3.0000002f), Pair(2.0f, 2.0000005f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.4999995f, 1.0000007f, listOf(Pair(3.9999988f, 1.07E-6f), Pair(3.0f, 1.0f), Pair(3.9999993f, 2.0000005f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.999999f, 3.4999995f, listOf(Pair(4.9999986f, 3.0f), Pair(2.9999993f, 3.0f), Pair(3.9999998f, 3.9999993f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.5f, 2.5f, listOf(Pair(5.999999f, 1.9999998f), Pair(5.0f, 3.0f), Pair(3.0000005f, 3.0000002f), Pair(4.000001f, 1.9999996f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.0f, 3.0f, listOf(Pair(0.0f, 3.9999998f), Pair(2.0000002f, 2.0000002f), Pair(4.0f, 3.9999998f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 6.0f, 3.0f, listOf(Pair(8.000001f, 3.9999995f), Pair(3.999999f, 4.0f), Pair(6.0f, 2.0f))),
            )
        ),
        TangramPuzzleInfo(
            id = "square",
            difficulty = "Easy",
            name = "Square",
            assetFileName = "tangrams/square.svg",
            emoji = "🟩",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 0.9999999f, 1.0f, listOf(Pair(0.0f, 2.0f), Pair(1.9999998f, 0.0f), Pair(0.0f, 0.0f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 1.9999999f, 1.0f, listOf(Pair(1.9999996f, 0.0f), Pair(2.9999998f, 1.0f), Pair(1.9999996f, 2.0f), Pair(0.99999994f, 1.0f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 2.9999998f, 0.5f, listOf(Pair(1.9999996f, 0.0f), Pair(2.9999998f, 1.0f), Pair(3.9999998f, 0.0f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 1.4999998f, 2.0f, listOf(Pair(0.9999999f, 1.0f), Pair(0.9999999f, 3.0f), Pair(1.9999996f, 2.0f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 0.5f, 2.5f, listOf(Pair(1.0f, 1.0f), Pair(0.0f, 2.0f), Pair(0.0f, 4.0f), Pair(1.0f, 3.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.0f, 3.0f, listOf(Pair(6.5E-8f, 4.0f), Pair(1.9999999f, 2.0f), Pair(4.0f, 4.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.9999998f, 2.0f, listOf(Pair(3.9999998f, 2.0E-8f), Pair(3.9999998f, 4.0f), Pair(1.9999998f, 2.0f))),
            )
        ),
        TangramPuzzleInfo(
            id = "rhombus",
            difficulty = "Easy",
            name = "Rhombus",
            assetFileName = "tangrams/rhombus.svg",
            emoji = "🔷",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 3.000001f, 3.0000005f, listOf(Pair(2.0000007f, 2.0000007f), Pair(4.0000005f, 4.0f), Pair(4.000001f, 2.000001f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 4.000001f, 1.0000002f, listOf(Pair(3.0000005f, 1.0000008f), Pair(4.0000005f, 2.3E-7f), Pair(5.000001f, 1.0000005f), Pair(4.000001f, 2.0000002f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.0000002f, 1.5000007f, listOf(Pair(4.0000005f, 2.0f), Pair(3.0000012f, 1.0000013f), Pair(2.0f, 2.0000002f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 5.5f, 2.0000002f, listOf(Pair(5.0000005f, 1.0000005f), Pair(5.0000005f, 3.0f), Pair(5.9999995f, 1.9999994f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.500001f, 2.5000007f, listOf(Pair(5.0000005f, 1.0000014f), Pair(4.000001f, 2.0000005f), Pair(4.0000005f, 4.0f), Pair(5.000001f, 3.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.0000007f, 3.0f, listOf(Pair(8.15E-7f, 4.0f), Pair(2.000001f, 2.0000002f), Pair(4.0000005f, 4.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 6.000001f, 1.0f, listOf(Pair(4.0f, 4.5E-7f), Pair(8.000002f, -5.0E-8f), Pair(6.000001f, 2.0f))),
            )
        ),
        TangramPuzzleInfo(
            id = "gem",
            difficulty = "Easy",
            name = "Gem",
            assetFileName = "tangrams/gem.svg",
            emoji = "💎",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 3.0000002f, 1.0f, listOf(Pair(2.0f, 3.05E-7f), Pair(4.0f, 1.9999996f), Pair(4.0000005f, 5.95E-7f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 4.0000014f, 3.0000002f, listOf(Pair(4.0000014f, 4.0000005f), Pair(3.0000012f, 3.0f), Pair(4.0000014f, 1.9999999f), Pair(5.0000014f, 2.9999993f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 5.5f, 1.9999996f, listOf(Pair(5.0000005f, 2.9999998f), Pair(5.9999995f, 2.0000005f), Pair(5.0000005f, 0.9999995f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.0000012f, 3.500001f, listOf(Pair(2.0000014f, 4.0000005f), Pair(4.000001f, 4.0000005f), Pair(3.0000005f, 3.0000014f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.5f, 1.4999995f, listOf(Pair(5.0000005f, 2.999999f), Pair(4.0000005f, 1.9999999f), Pair(3.9999995f, 5.5E-8f), Pair(5.0000005f, 1.0000004f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.0000014f, 3.0f, listOf(Pair(4.0000014f, 2.0f), Pair(2.000001f, 4.0f), Pair(1.4305115E-6f, 2.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.0000012f, 1.0f, listOf(Pair(4.0000024f, 1.9999996f), Pair(0.0f, 2.0f), Pair(2.000001f, 0.0f))),
            )
        ),
        TangramPuzzleInfo(
            id = "candle",
            difficulty = "Easy",
            name = "Candle",
            assetFileName = "tangrams/candle.svg",
            emoji = "🕯️",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 1.4779025f, 9.707108f, listOf(Pair(0.06368907f, 10.414214f), Pair(2.892116f, 10.414213f), Pair(1.4779027f, 9.0f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 1.4779027f, 4.0f, listOf(Pair(2.4779027f, 4.0f), Pair(1.4779027f, 5.0f), Pair(0.47790265f, 4.0f), Pair(1.4779027f, 3.0000002f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 0.97790253f, 5.0f, listOf(Pair(0.47790247f, 6.0f), Pair(1.4779027f, 5.0f), Pair(0.47790244f, 4.0f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 1.9779025f, 9.0f, listOf(Pair(2.4779024f, 10.0f), Pair(2.4779024f, 8.0f), Pair(1.4779027f, 9.0f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 0.9779027f, 1.5f, listOf(Pair(1.4779024f, 3.0f), Pair(0.47790286f, 2.0000005f), Pair(0.47790256f, 1.0E-7f), Pair(1.4779029f, 1.0000004f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 1.4779027f, 6.0f, listOf(Pair(2.477903f, 8.0f), Pair(0.47790253f, 6.0f), Pair(2.4779027f, 4.0000005f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 1.4779025f, 8.0f, listOf(Pair(0.4779027f, 10.0f), Pair(0.4779025f, 6.0f), Pair(2.4779027f, 8.0f))),
            )
        ),
        TangramPuzzleInfo(
            id = "house",
            difficulty = "Easy",
            name = "House",
            assetFileName = "tangrams/house.svg",
            emoji = "🏠",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 1.8284266f, 3.8284276f, listOf(Pair(2.8284266f, 4.8284273f), Pair(0.82842666f, 2.8284278f), Pair(0.82842666f, 4.828427f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 2.1213193f, 0.707107f, listOf(Pair(1.4142125f, 1.414214f), Pair(1.4142123f, 0.0f), Pair(2.8284264f, 0.0f), Pair(2.8284264f, 1.4142132f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 4.3284264f, 3.828427f, listOf(Pair(4.828426f, 2.8284268f), Pair(3.8284268f, 3.8284264f), Pair(4.828426f, 4.8284273f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.8284268f, 4.328427f, listOf(Pair(2.828427f, 4.8284264f), Pair(4.8284264f, 4.8284264f), Pair(3.828426f, 3.8284273f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 1.4142132f, 2.1213207f, listOf(Pair(0.0f, 2.8284268f), Pair(1.4142128f, 2.8284276f), Pair(2.8284264f, 1.4142146f), Pair(1.4142123f, 1.4142141f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.8284266f, 3.8284273f, listOf(Pair(4.8284264f, 2.8284273f), Pair(2.8284261f, 4.8284273f), Pair(0.8284267f, 2.8284273f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 3.4142141f, 1.8284276f, listOf(Pair(5.414215f, 2.828427f), Pair(1.4142132f, 2.8284276f), Pair(3.4142141f, 0.82842755f))),
            )
        ),
        TangramPuzzleInfo(
            id = "koi",
            difficulty = "Easy",
            name = "Koi",
            assetFileName = "tangrams/koi.svg",
            emoji = "🎏",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 0.7071068f, 4.999999f, listOf(Pair(0.0f, 3.5857856f), Pair(6.0E-7f, 6.4142127f), Pair(1.4142137f, 5.0f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 6.94975f, 5.1213207f, listOf(Pair(7.656857f, 5.828428f), Pair(6.2426434f, 5.828428f), Pair(6.2426434f, 4.414214f), Pair(7.6568565f, 4.4142137f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 6.94975f, 3.707107f, listOf(Pair(6.242643f, 3.0000002f), Pair(6.242643f, 4.414214f), Pair(7.656857f, 4.414214f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 4.7071066f, 5.7071066f, listOf(Pair(5.414213f, 5.0f), Pair(4.0f, 6.414213f), Pair(5.414213f, 6.414212f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 5.7426424f, 1.5000001f, listOf(Pair(5.242642f, 4.5E-7f), Pair(6.242642f, 0.99999976f), Pair(6.242643f, 2.9999998f), Pair(5.242642f, 1.9999995f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 3.4142134f, 4.0f, listOf(Pair(1.4142137f, 5.0f), Pair(3.4142137f, 3.0f), Pair(5.414213f, 5.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 4.8284283f, 4.414214f, listOf(Pair(6.242643f, 5.8284283f), Pair(3.414214f, 2.9999998f), Pair(6.242642f, 3.000001f))),
            )
        ),
        TangramPuzzleInfo(
            id = "snake",
            difficulty = "Easy",
            name = "Snake",
            assetFileName = "tangrams/snake.svg",
            emoji = "🐍",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.2928934f, 4.000002f, listOf(Pair(5.0f, 5.4142156f), Pair(5.0f, 2.5857887f), Pair(3.5857868f, 4.000002f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 3.9999998f, 5.4142156f, listOf(Pair(2.9999995f, 5.414216f), Pair(3.9999998f, 4.4142156f), Pair(5.0f, 5.4142156f), Pair(4.0f, 6.4142156f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 1.0000001f, 5.914216f, listOf(Pair(2.0000005f, 6.414215f), Pair(1.0000008f, 5.4142165f), Pair(-2.0E-7f, 6.4142156f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 0.50000036f, 5.414216f, listOf(Pair(8.0E-7f, 4.4142165f), Pair(8.0E-7f, 6.414216f), Pair(0.9999999f, 5.4142156f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 2.4999995f, 5.914216f, listOf(Pair(3.9999988f, 6.414216f), Pair(2.9999995f, 5.414216f), Pair(1.0f, 5.4142156f), Pair(2.0f, 6.414216f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 4.585787f, 2.000001f, listOf(Pair(3.585787f, 1.0E-6f), Pair(5.585787f, 2.0000012f), Pair(3.585787f, 4.000001f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.5857868f, 2.000001f, listOf(Pair(3.5857863f, 0.0f), Pair(3.5857868f, 4.000002f), Pair(1.5857868f, 2.0000012f))),
            )
        ),
        TangramPuzzleInfo(
            id = "swan",
            difficulty = "Medium",
            name = "Swan",
            assetFileName = "tangrams/swan.svg",
            emoji = "🦢",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 5.585787f, 0.70710635f, listOf(Pair(4.171573f, 1.4142127f), Pair(7.0f, 1.4142127f), Pair(5.585787f, 0.0f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 6.000002f, 4.242639f, listOf(Pair(5.0000014f, 4.2426395f), Pair(6.0000014f, 3.242639f), Pair(7.000002f, 4.242639f), Pair(6.000002f, 5.242639f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 4.707107f, 5.9497457f, listOf(Pair(4.0f, 5.242639f), Pair(4.000001f, 6.656852f), Pair(5.414214f, 6.6568522f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 6.2928934f, 3.5355325f, listOf(Pair(5.585787f, 2.828426f), Pair(7.0f, 4.242639f), Pair(6.999999f, 2.828426f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 5.585787f, 2.1213193f, listOf(Pair(4.1715736f, 1.4142133f), Pair(5.585787f, 1.4142127f), Pair(7.0000005f, 2.8284256f), Pair(5.5857863f, 2.828426f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.0f, 4.242639f, listOf(Pair(4.0f, 3.2426393f), Pair(1.9999998f, 5.242639f), Pair(1.5E-7f, 3.2426393f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 4.0000005f, 4.242639f, listOf(Pair(6.0000014f, 5.2426386f), Pair(1.9999995f, 5.242639f), Pair(4.0000005f, 3.242639f))),
            )
        ),
        TangramPuzzleInfo(
            id = "cat",
            difficulty = "Medium",
            name = "Cat",
            assetFileName = "tangrams/cat.svg",
            emoji = "🐱",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.12132f, 3.4142134f, listOf(Pair(3.414213f, 1.9999999f), Pair(3.4142137f, 4.828427f), Pair(4.828427f, 3.414214f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 4.414213f, 2.0f, listOf(Pair(5.414213f, 2.0f), Pair(4.414213f, 3.0f), Pair(3.4142132f, 2.0f), Pair(4.414212f, 1.0000002f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.9142127f, 1.0f, listOf(Pair(3.4142127f, 2.0f), Pair(4.4142127f, 1.0f), Pair(3.4142127f, 0.0f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 4.9142127f, 1.0f, listOf(Pair(5.414212f, 2.0f), Pair(5.414212f, 0.0f), Pair(4.414213f, 1.0f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 0.7071069f, 5.414213f, listOf(Pair(0.0f, 3.9999998f), Pair(1.0E-7f, 5.4142127f), Pair(1.4142138f, 6.828427f), Pair(1.4142135f, 5.4142127f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.4142137f, 3.999999f, listOf(Pair(3.414214f, 5.999999f), Pair(1.4142137f, 3.9999988f), Pair(3.414214f, 1.9999992f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.8284283f, 5.414213f, listOf(Pair(1.4142138f, 3.999999f), Pair(4.242643f, 6.828428f), Pair(1.414215f, 6.828427f))),
            )
        ),
        TangramPuzzleInfo(
            id = "dog",
            difficulty = "Medium",
            name = "Dog",
            assetFileName = "tangrams/dog.svg",
            emoji = "🐕",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 3.8284268f, 3.242641f, listOf(Pair(2.4142134f, 3.9497478f), Pair(5.2426405f, 3.949747f), Pair(3.8284278f, 2.535534f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 0.7071069f, 1.8284273f, listOf(Pair(1.4142138f, 2.5355344f), Pair(1.8E-7f, 2.5355344f), Pair(0.0f, 1.1213205f), Pair(1.414213f, 1.1213201f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 2.1213202f, 1.8284271f, listOf(Pair(1.4142133f, 1.1213204f), Pair(1.4142134f, 2.535534f), Pair(2.828427f, 2.5355337f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 4.535535f, 4.6568537f, listOf(Pair(3.8284278f, 3.949747f), Pair(5.2426414f, 5.3639603f), Pair(5.2426405f, 3.9497476f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 7.3639617f, 1.4142135f, listOf(Pair(6.6568546f, 2.828427f), Pair(6.6568546f, 1.414214f), Pair(8.071069f, 0.0f), Pair(8.071069f, 1.414214f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.4142134f, 3.9497476f, listOf(Pair(1.0000001f, 5.363961f), Pair(0.99999994f, 2.535534f), Pair(3.828427f, 2.5355341f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 5.2426405f, 3.949748f, listOf(Pair(6.6568546f, 5.363962f), Pair(3.8284261f, 2.5355337f), Pair(6.6568537f, 2.5355349f))),
            )
        ),
        TangramPuzzleInfo(
            id = "fox",
            difficulty = "Medium",
            name = "Fox",
            assetFileName = "tangrams/fox.svg",
            emoji = "🦊",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 5.24264f, 2.8786798f, listOf(Pair(3.8284266f, 3.5857866f), Pair(6.6568537f, 3.585786f), Pair(5.242641f, 2.171573f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 1.0f, 4.0f, listOf(Pair(1.0000004f, 5.0f), Pair(3.0E-8f, 4.0f), Pair(1.0000002f, 2.9999998f), Pair(2.0f, 3.9999993f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 9.707107f, 1.0859367f, listOf(Pair(10.414213f, 0.37882978f), Pair(9.000001f, 0.37882984f), Pair(9.0f, 1.7930435f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 4.5355334f, 4.2928934f, listOf(Pair(3.828427f, 5.0f), Pair(5.24264f, 3.5857868f), Pair(3.828427f, 3.5857878f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 2.4142137f, 4.2928934f, listOf(Pair(1.0f, 4.9999995f), Pair(2.414213f, 5.0f), Pair(3.828427f, 3.5857873f), Pair(2.4142127f, 3.5857866f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 6.6568537f, 3.5857866f, listOf(Pair(5.24264f, 2.1715732f), Pair(8.071068f, 2.1715734f), Pair(8.071068f, 5.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 8.0f, 1.0859365f, listOf(Pair(9.0f, -0.9140646f), Pair(9.0f, 3.0859377f), Pair(7.0f, 1.0859368f))),
            )
        ),
        TangramPuzzleInfo(
            id = "tree",
            difficulty = "Medium",
            name = "Tree",
            assetFileName = "tangrams/tree.svg",
            emoji = "🌲",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 2.8284261f, 0.70710635f, listOf(Pair(1.4142127f, 1.4142127f), Pair(4.2426395f, 1.4142127f), Pair(2.8284264f, 0.0f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 2.8284268f, 6.949746f, listOf(Pair(2.1213195f, 6.2426395f), Pair(3.535534f, 6.2426395f), Pair(3.535534f, 7.656853f), Pair(2.1213202f, 7.656853f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 2.1213198f, 5.535533f, listOf(Pair(1.4142134f, 6.2426395f), Pair(2.8284252f, 6.2426395f), Pair(2.8284261f, 4.8284264f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 0.70710665f, 5.535534f, listOf(Pair(1.4142131f, 4.8284273f), Pair(0.0f, 6.2426405f), Pair(1.4142133f, 6.2426395f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 2.1213198f, 4.8284264f, listOf(Pair(2.8284256f, 3.4142132f), Pair(2.8284264f, 4.8284264f), Pair(1.4142134f, 6.24264f), Pair(1.4142132f, 4.828426f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 4.24264f, 4.8284264f, listOf(Pair(5.6568537f, 6.24264f), Pair(2.8284268f, 6.2426395f), Pair(2.8284266f, 3.414213f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.8284264f, 2.4142127f, listOf(Pair(4.8284273f, 3.4142122f), Pair(0.8284254f, 3.4142127f), Pair(2.8284264f, 1.4142127f))),
            )
        ),
        TangramPuzzleInfo(
            id = "bridge",
            difficulty = "Medium",
            name = "Bridge",
            assetFileName = "tangrams/bridge.svg",
            emoji = "🌉",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.242641f, 0.70710695f, listOf(Pair(2.8284273f, 1.4142138f), Pair(5.6568546f, 1.4142134f), Pair(4.242641f, 8.5E-8f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 4.949748f, 2.1213207f, listOf(Pair(5.6568546f, 1.4142139f), Pair(5.6568546f, 2.8284276f), Pair(4.242641f, 2.8284276f), Pair(4.242641f, 1.4142141f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.535534f, 0.70710695f, listOf(Pair(4.242641f, 5.0E-8f), Pair(2.828427f, 0.0f), Pair(2.828427f, 1.4142139f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 6.363961f, 0.7071069f, listOf(Pair(5.656854f, 6.625E-8f), Pair(7.071068f, 1.4142137f), Pair(7.071068f, 3.6625E-7f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 5.656854f, 0.70710677f, listOf(Pair(7.071068f, 1.4142137f), Pair(5.6568546f, 1.4142134f), Pair(4.2426405f, 0.0f), Pair(5.6568546f, -6.75E-8f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 8.485281f, 1.4142137f, listOf(Pair(9.899495f, 2.8284273f), Pair(7.0710673f, 2.8284273f), Pair(7.0710673f, 0.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 1.4142135f, 1.4142137f, listOf(Pair(0.0f, 2.828427f), Pair(2.8284268f, 0.0f), Pair(2.828427f, 2.8284273f))),
            )
        ),
        TangramPuzzleInfo(
            id = "person",
            difficulty = "Medium",
            name = "Person",
            assetFileName = "tangrams/person.svg",
            emoji = "🧍",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 2.999999f, 3.2928934f, listOf(Pair(4.414212f, 2.5857868f), Pair(1.5857856f, 2.585787f), Pair(2.9999986f, 4.0f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 5.0f, 1.0f, listOf(Pair(3.9999998f, 1.0000005f), Pair(5.0f, -3.5E-8f), Pair(6.0000005f, 1.0000004f), Pair(5.0000005f, 2.0f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 3.7071052f, 4.7071066f, listOf(Pair(4.414212f, 5.4142137f), Pair(4.4142118f, 4.0000014f), Pair(2.999998f, 4.0f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 0.9999992f, 6.4999995f, listOf(Pair(1.999999f, 6.0f), Pair(-5.9604645E-7f, 6.0f), Pair(0.9999999f, 6.999999f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.999999f, 6.7071066f, listOf(Pair(6.414212f, 7.4142127f), Pair(4.9999995f, 7.414213f), Pair(3.5857859f, 6.0f), Pair(5.0f, 5.9999995f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 2.999999f, 5.0f, listOf(Pair(0.9999992f, 6.0f), Pair(2.9999993f, 4.0f), Pair(4.999999f, 6.0f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 5.0f, 3.0f, listOf(Pair(7.000001f, 3.9999998f), Pair(2.999999f, 4.0f), Pair(5.0f, 2.0f))),
            )
        ),
        TangramPuzzleInfo(
            id = "boat",
            difficulty = "Hard",
            name = "Boat",
            assetFileName = "tangrams/boat.svg",
            emoji = "⛵",
            piecePlacements = listOf(
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 3.1715727f, 4.949747f, listOf(Pair(4.5857863f, 4.2426405f), Pair(1.7573593f, 4.242641f), Pair(3.1715727f, 5.656854f))),
                TangramPiecePlacement(TangramPieceType.SQUARE, 4.7071066f, 3.535534f, listOf(Pair(5.414213f, 2.828427f), Pair(5.414213f, 4.2426405f), Pair(3.9999998f, 4.2426405f), Pair(3.9999998f, 2.828427f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 6.828427f, 3.328427f, listOf(Pair(7.828427f, 3.828427f), Pair(6.828427f, 2.828427f), Pair(5.828427f, 3.828427f))),
                TangramPiecePlacement(TangramPieceType.SMALL_TRIANGLE, 6.12132f, 3.535534f, listOf(Pair(5.414213f, 4.2426405f), Pair(6.828427f, 2.828427f), Pair(5.4142137f, 2.8284273f))),
                TangramPiecePlacement(TangramPieceType.PARALLELOGRAM, 4.5857863f, 4.949747f, listOf(Pair(6.0f, 4.2426405f), Pair(4.5857863f, 4.2426405f), Pair(3.1715727f, 5.656854f), Pair(4.5857863f, 5.656854f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 1.9999999f, 3.2426405f, listOf(Pair(3.9999998f, 2.2426407f), Pair(2.0f, 4.2426405f), Pair(0.0f, 2.2426405f))),
                TangramPiecePlacement(TangramPieceType.LARGE_TRIANGLE, 5.4142137f, 1.4142135f, listOf(Pair(4.0f, 0.0f), Pair(6.8284273f, 2.8284268f), Pair(4.0f, 2.828427f))),
            )
        ),
    )

    val PUZZLES_BY_DIFFICULTY: Map<String, List<TangramPuzzleInfo>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): TangramPuzzleInfo? = ALL_PUZZLES.find { it.id == id }
}
