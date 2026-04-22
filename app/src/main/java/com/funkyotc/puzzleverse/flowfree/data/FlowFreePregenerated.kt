package com.funkyotc.puzzleverse.flowfree.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedPuzzle(
    override val id: String,
    val size: Int,
    override val difficulty: String,
    val dots: List<ColorDot>
) : BrowseablePuzzle {
    override val label: String get() = "Puzzle"
    override val subtitle: String get() = "${size}x${size}"
}

object FlowFreePregenerated {

    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(
        PregeneratedPuzzle("Easy_5x5_puzzle_001", 5, "Easy", listOf(ColorDot(1, Point(0, 3), Point(0, 4)), ColorDot(2, Point(3, 1), Point(4, 0)), ColorDot(3, Point(1, 3), Point(3, 2)), ColorDot(4, Point(3, 0), Point(3, 3)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_002", 5, "Easy", listOf(ColorDot(1, Point(1, 3), Point(3, 1)), ColorDot(2, Point(1, 1), Point(4, 0)), ColorDot(3, Point(0, 3), Point(1, 4)), ColorDot(4, Point(1, 0), Point(3, 3)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_003", 5, "Easy", listOf(ColorDot(1, Point(0, 1), Point(1, 3)), ColorDot(2, Point(0, 2), Point(4, 3)), ColorDot(3, Point(0, 0), Point(2, 2)), ColorDot(4, Point(2, 3), Point(4, 0)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_004", 5, "Easy", listOf(ColorDot(1, Point(0, 1), Point(4, 1)), ColorDot(2, Point(1, 3), Point(4, 4)), ColorDot(3, Point(1, 1), Point(4, 3)), ColorDot(4, Point(0, 4), Point(3, 3)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_005", 5, "Easy", listOf(ColorDot(1, Point(0, 0), Point(4, 1)), ColorDot(2, Point(1, 2), Point(4, 4)), ColorDot(3, Point(0, 2), Point(3, 2)), ColorDot(4, Point(2, 2), Point(4, 2)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_006", 5, "Easy", listOf(ColorDot(1, Point(0, 0), Point(4, 0)), ColorDot(2, Point(0, 4), Point(2, 3)), ColorDot(3, Point(0, 3), Point(3, 4)), ColorDot(4, Point(0, 2), Point(4, 4)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_007", 5, "Easy", listOf(ColorDot(1, Point(2, 2), Point(4, 3)), ColorDot(2, Point(1, 1), Point(2, 0)), ColorDot(3, Point(1, 0), Point(4, 4)), ColorDot(4, Point(0, 3), Point(2, 4)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_008", 5, "Easy", listOf(ColorDot(1, Point(0, 0), Point(4, 1)), ColorDot(2, Point(1, 2), Point(4, 4)), ColorDot(3, Point(3, 1), Point(3, 3)), ColorDot(4, Point(0, 4), Point(3, 4)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_009", 5, "Easy", listOf(ColorDot(1, Point(4, 2), Point(4, 4)), ColorDot(2, Point(0, 3), Point(3, 3)), ColorDot(3, Point(0, 1), Point(4, 0)), ColorDot(4, Point(0, 0), Point(3, 0)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_010", 5, "Easy", listOf(ColorDot(1, Point(3, 0), Point(3, 1)), ColorDot(2, Point(1, 3), Point(4, 0)), ColorDot(3, Point(0, 0), Point(2, 1)), ColorDot(4, Point(2, 4), Point(3, 2)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_011", 5, "Easy", listOf(ColorDot(1, Point(0, 1), Point(1, 3)), ColorDot(2, Point(3, 0), Point(3, 3)), ColorDot(3, Point(0, 4), Point(4, 4)), ColorDot(4, Point(0, 3), Point(1, 1)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_012", 5, "Easy", listOf(ColorDot(1, Point(1, 0), Point(3, 3)), ColorDot(2, Point(0, 2), Point(4, 3)), ColorDot(3, Point(0, 0), Point(3, 1)), ColorDot(4, Point(1, 2), Point(2, 2)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_013", 5, "Easy", listOf(ColorDot(1, Point(2, 4), Point(4, 4)), ColorDot(2, Point(0, 3), Point(1, 1)), ColorDot(3, Point(0, 4), Point(4, 1)), ColorDot(4, Point(0, 1), Point(4, 0)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_014", 5, "Easy", listOf(ColorDot(1, Point(1, 1), Point(3, 3)), ColorDot(2, Point(1, 2), Point(3, 2)), ColorDot(3, Point(0, 0), Point(4, 0)), ColorDot(4, Point(0, 4), Point(4, 1)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_015", 5, "Easy", listOf(ColorDot(1, Point(3, 0), Point(4, 2)), ColorDot(2, Point(1, 1), Point(3, 2)), ColorDot(3, Point(0, 4), Point(2, 0)), ColorDot(4, Point(1, 4), Point(2, 2)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_016", 5, "Easy", listOf(ColorDot(1, Point(2, 3), Point(3, 1)), ColorDot(2, Point(1, 4), Point(4, 3)), ColorDot(3, Point(2, 0), Point(3, 3)), ColorDot(4, Point(0, 4), Point(1, 3)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_017", 5, "Easy", listOf(ColorDot(1, Point(1, 2), Point(4, 2)), ColorDot(2, Point(2, 2), Point(2, 4)), ColorDot(3, Point(1, 4), Point(2, 3)), ColorDot(4, Point(0, 2), Point(0, 4)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_018", 5, "Easy", listOf(ColorDot(1, Point(1, 4), Point(3, 4)), ColorDot(2, Point(3, 0), Point(4, 4)), ColorDot(3, Point(0, 4), Point(2, 0)), ColorDot(4, Point(1, 1), Point(3, 1)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_019", 5, "Easy", listOf(ColorDot(1, Point(2, 2), Point(2, 4)), ColorDot(2, Point(2, 3), Point(3, 0)), ColorDot(3, Point(4, 0), Point(4, 2)), ColorDot(4, Point(1, 4), Point(2, 0)))),
        PregeneratedPuzzle("Easy_5x5_puzzle_020", 5, "Easy", listOf(ColorDot(1, Point(0, 0), Point(3, 0)), ColorDot(2, Point(2, 2), Point(4, 0)), ColorDot(3, Point(3, 1), Point(4, 4)), ColorDot(4, Point(2, 3), Point(4, 3)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_001", 6, "Medium", listOf(ColorDot(1, Point(4, 1), Point(5, 2)), ColorDot(2, Point(1, 0), Point(2, 0)), ColorDot(3, Point(0, 0), Point(4, 4)), ColorDot(4, Point(1, 4), Point(3, 5)), ColorDot(5, Point(1, 2), Point(5, 1)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_002", 6, "Medium", listOf(ColorDot(1, Point(2, 3), Point(5, 5)), ColorDot(2, Point(0, 2), Point(4, 5)), ColorDot(3, Point(0, 1), Point(5, 1)), ColorDot(4, Point(1, 1), Point(5, 3)), ColorDot(5, Point(2, 1), Point(5, 2)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_003", 6, "Medium", listOf(ColorDot(1, Point(0, 3), Point(3, 3)), ColorDot(2, Point(0, 5), Point(4, 4)), ColorDot(3, Point(0, 1), Point(5, 2)), ColorDot(4, Point(1, 5), Point(5, 5)), ColorDot(5, Point(1, 1), Point(5, 4)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_004", 6, "Medium", listOf(ColorDot(1, Point(0, 2), Point(3, 3)), ColorDot(2, Point(3, 4), Point(5, 5)), ColorDot(3, Point(2, 2), Point(4, 5)), ColorDot(4, Point(1, 4), Point(2, 3)), ColorDot(5, Point(0, 0), Point(4, 3)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_005", 6, "Medium", listOf(ColorDot(1, Point(3, 4), Point(5, 3)), ColorDot(2, Point(3, 0), Point(5, 0)), ColorDot(3, Point(0, 4), Point(4, 3)), ColorDot(4, Point(1, 0), Point(3, 1)), ColorDot(5, Point(0, 0), Point(2, 4)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_006", 6, "Medium", listOf(ColorDot(1, Point(0, 4), Point(3, 4)), ColorDot(2, Point(0, 0), Point(1, 4)), ColorDot(3, Point(3, 5), Point(5, 3)), ColorDot(4, Point(2, 3), Point(3, 0)), ColorDot(5, Point(2, 1), Point(4, 4)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_007", 6, "Medium", listOf(ColorDot(1, Point(0, 0), Point(1, 3)), ColorDot(2, Point(2, 1), Point(4, 0)), ColorDot(3, Point(4, 4), Point(5, 0)), ColorDot(4, Point(2, 0), Point(3, 4)), ColorDot(5, Point(2, 3), Point(5, 4)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_008", 6, "Medium", listOf(ColorDot(1, Point(2, 5), Point(4, 5)), ColorDot(2, Point(0, 5), Point(1, 5)), ColorDot(3, Point(4, 0), Point(5, 2)), ColorDot(4, Point(1, 1), Point(5, 5)), ColorDot(5, Point(1, 2), Point(5, 3)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_009", 6, "Medium", listOf(ColorDot(1, Point(1, 2), Point(5, 0)), ColorDot(2, Point(0, 3), Point(5, 5)), ColorDot(3, Point(1, 3), Point(4, 4)), ColorDot(4, Point(2, 2), Point(5, 4)), ColorDot(5, Point(0, 2), Point(4, 1)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_010", 6, "Medium", listOf(ColorDot(1, Point(2, 4), Point(3, 2)), ColorDot(2, Point(1, 2), Point(5, 0)), ColorDot(3, Point(0, 0), Point(0, 1)), ColorDot(4, Point(4, 3), Point(5, 1)), ColorDot(5, Point(0, 2), Point(3, 5)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_011", 6, "Medium", listOf(ColorDot(1, Point(3, 0), Point(5, 0)), ColorDot(2, Point(3, 1), Point(4, 3)), ColorDot(3, Point(3, 2), Point(5, 3)), ColorDot(4, Point(0, 1), Point(2, 2)), ColorDot(5, Point(1, 1), Point(5, 5)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_012", 6, "Medium", listOf(ColorDot(1, Point(0, 1), Point(4, 0)), ColorDot(2, Point(1, 1), Point(2, 3)), ColorDot(3, Point(5, 0), Point(5, 5)), ColorDot(4, Point(0, 5), Point(2, 2)), ColorDot(5, Point(1, 5), Point(4, 3)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_013", 6, "Medium", listOf(ColorDot(1, Point(3, 0), Point(5, 2)), ColorDot(2, Point(3, 4), Point(5, 3)), ColorDot(3, Point(1, 0), Point(4, 4)), ColorDot(4, Point(0, 2), Point(1, 5)), ColorDot(5, Point(0, 0), Point(2, 5)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_014", 6, "Medium", listOf(ColorDot(1, Point(0, 0), Point(4, 3)), ColorDot(2, Point(0, 4), Point(3, 0)), ColorDot(3, Point(0, 2), Point(2, 4)), ColorDot(4, Point(4, 1), Point(4, 2)), ColorDot(5, Point(1, 0), Point(3, 2)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_015", 6, "Medium", listOf(ColorDot(1, Point(0, 3), Point(1, 1)), ColorDot(2, Point(3, 0), Point(4, 2)), ColorDot(3, Point(0, 5), Point(4, 4)), ColorDot(4, Point(0, 4), Point(3, 5)), ColorDot(5, Point(0, 1), Point(4, 1)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_016", 6, "Medium", listOf(ColorDot(1, Point(1, 1), Point(4, 5)), ColorDot(2, Point(4, 1), Point(4, 4)), ColorDot(3, Point(1, 4), Point(3, 1)), ColorDot(4, Point(1, 3), Point(2, 1)), ColorDot(5, Point(0, 0), Point(3, 4)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_017", 6, "Medium", listOf(ColorDot(1, Point(0, 5), Point(1, 5)), ColorDot(2, Point(2, 1), Point(4, 1)), ColorDot(3, Point(0, 4), Point(2, 0)), ColorDot(4, Point(2, 2), Point(5, 0)), ColorDot(5, Point(1, 4), Point(5, 1)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_018", 6, "Medium", listOf(ColorDot(1, Point(1, 1), Point(2, 1)), ColorDot(2, Point(0, 5), Point(3, 3)), ColorDot(3, Point(3, 1), Point(5, 2)), ColorDot(4, Point(2, 3), Point(5, 5)), ColorDot(5, Point(0, 4), Point(3, 0)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_019", 6, "Medium", listOf(ColorDot(1, Point(0, 3), Point(1, 4)), ColorDot(2, Point(2, 4), Point(3, 1)), ColorDot(3, Point(3, 2), Point(5, 1)), ColorDot(4, Point(4, 5), Point(5, 4)), ColorDot(5, Point(0, 4), Point(4, 4)))),
        PregeneratedPuzzle("Medium_6x6_puzzle_020", 6, "Medium", listOf(ColorDot(1, Point(2, 0), Point(5, 5)), ColorDot(2, Point(1, 4), Point(4, 5)), ColorDot(3, Point(0, 4), Point(2, 1)), ColorDot(4, Point(0, 5), Point(1, 5)), ColorDot(5, Point(2, 5), Point(3, 5)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_001", 7, "Hard", listOf(ColorDot(1, Point(0, 2), Point(4, 2)), ColorDot(2, Point(2, 1), Point(6, 2)), ColorDot(3, Point(4, 3), Point(5, 5)), ColorDot(4, Point(2, 5), Point(4, 6)), ColorDot(5, Point(0, 0), Point(1, 1)), ColorDot(6, Point(1, 0), Point(6, 1)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_002", 7, "Hard", listOf(ColorDot(1, Point(1, 1), Point(4, 4)), ColorDot(2, Point(1, 2), Point(1, 5)), ColorDot(3, Point(3, 0), Point(6, 2)), ColorDot(4, Point(0, 5), Point(6, 5)), ColorDot(5, Point(2, 2), Point(5, 2)), ColorDot(6, Point(0, 0), Point(5, 1)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_003", 7, "Hard", listOf(ColorDot(1, Point(3, 4), Point(4, 4)), ColorDot(2, Point(3, 6), Point(6, 6)), ColorDot(3, Point(2, 5), Point(5, 4)), ColorDot(4, Point(2, 6), Point(3, 1)), ColorDot(5, Point(0, 0), Point(2, 2)), ColorDot(6, Point(3, 5), Point(5, 2)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_004", 7, "Hard", listOf(ColorDot(1, Point(0, 6), Point(3, 6)), ColorDot(2, Point(4, 4), Point(4, 6)), ColorDot(3, Point(3, 2), Point(4, 5)), ColorDot(4, Point(0, 1), Point(6, 0)), ColorDot(5, Point(1, 4), Point(4, 2)), ColorDot(6, Point(0, 2), Point(6, 4)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_005", 7, "Hard", listOf(ColorDot(1, Point(1, 1), Point(6, 1)), ColorDot(2, Point(1, 0), Point(3, 5)), ColorDot(3, Point(3, 1), Point(5, 2)), ColorDot(4, Point(2, 1), Point(5, 1)), ColorDot(5, Point(5, 0), Point(6, 0)), ColorDot(6, Point(2, 4), Point(2, 6)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_006", 7, "Hard", listOf(ColorDot(1, Point(3, 3), Point(4, 1)), ColorDot(2, Point(4, 2), Point(6, 1)), ColorDot(3, Point(0, 6), Point(5, 5)), ColorDot(4, Point(1, 5), Point(6, 4)), ColorDot(5, Point(1, 6), Point(3, 6)), ColorDot(6, Point(0, 3), Point(2, 0)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_007", 7, "Hard", listOf(ColorDot(1, Point(1, 5), Point(4, 5)), ColorDot(2, Point(2, 2), Point(6, 0)), ColorDot(3, Point(1, 1), Point(5, 1)), ColorDot(4, Point(1, 2), Point(5, 0)), ColorDot(5, Point(2, 6), Point(5, 3)), ColorDot(6, Point(2, 5), Point(6, 3)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_008", 7, "Hard", listOf(ColorDot(1, Point(3, 2), Point(4, 1)), ColorDot(2, Point(3, 6), Point(4, 4)), ColorDot(3, Point(2, 0), Point(2, 2)), ColorDot(4, Point(4, 3), Point(6, 1)), ColorDot(5, Point(3, 5), Point(6, 2)), ColorDot(6, Point(1, 1), Point(6, 0)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_009", 7, "Hard", listOf(ColorDot(1, Point(4, 0), Point(6, 2)), ColorDot(2, Point(1, 0), Point(3, 2)), ColorDot(3, Point(3, 0), Point(5, 1)), ColorDot(4, Point(0, 0), Point(5, 4)), ColorDot(5, Point(3, 5), Point(4, 2)), ColorDot(6, Point(3, 4), Point(6, 6)))),
        PregeneratedPuzzle("Hard_7x7_puzzle_010", 7, "Hard", listOf(ColorDot(1, Point(1, 5), Point(6, 6)), ColorDot(2, Point(2, 5), Point(6, 5)), ColorDot(3, Point(2, 2), Point(5, 3)), ColorDot(4, Point(0, 0), Point(3, 2)), ColorDot(5, Point(0, 6), Point(5, 2)), ColorDot(6, Point(1, 2), Point(2, 0)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_001", 8, "Expert", listOf(ColorDot(1, Point(1, 2), Point(4, 1)), ColorDot(2, Point(5, 7), Point(6, 1)), ColorDot(3, Point(1, 1), Point(3, 6)), ColorDot(4, Point(2, 7), Point(5, 1)), ColorDot(5, Point(1, 3), Point(4, 3)), ColorDot(6, Point(2, 1), Point(3, 1)), ColorDot(7, Point(0, 0), Point(7, 1)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_002", 8, "Expert", listOf(ColorDot(1, Point(0, 3), Point(6, 5)), ColorDot(2, Point(0, 5), Point(7, 6)), ColorDot(3, Point(3, 1), Point(7, 2)), ColorDot(4, Point(0, 6), Point(0, 7)), ColorDot(5, Point(2, 0), Point(4, 3)), ColorDot(6, Point(1, 1), Point(7, 5)), ColorDot(7, Point(1, 7), Point(7, 7)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_003", 8, "Expert", listOf(ColorDot(1, Point(0, 1), Point(3, 2)), ColorDot(2, Point(4, 4), Point(6, 7)), ColorDot(3, Point(4, 7), Point(5, 6)), ColorDot(4, Point(4, 1), Point(7, 7)), ColorDot(5, Point(1, 1), Point(3, 7)), ColorDot(6, Point(1, 5), Point(5, 5)), ColorDot(7, Point(1, 6), Point(4, 6)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_004", 8, "Expert", listOf(ColorDot(1, Point(2, 2), Point(5, 1)), ColorDot(2, Point(0, 1), Point(7, 5)), ColorDot(3, Point(1, 7), Point(4, 5)), ColorDot(4, Point(1, 4), Point(3, 6)), ColorDot(5, Point(1, 6), Point(6, 5)), ColorDot(6, Point(1, 3), Point(5, 2)), ColorDot(7, Point(0, 4), Point(1, 1)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_005", 8, "Expert", listOf(ColorDot(1, Point(2, 5), Point(5, 0)), ColorDot(2, Point(0, 7), Point(5, 5)), ColorDot(3, Point(3, 0), Point(5, 2)), ColorDot(4, Point(6, 0), Point(7, 0)), ColorDot(5, Point(1, 5), Point(5, 3)), ColorDot(6, Point(0, 0), Point(2, 0)), ColorDot(7, Point(0, 1), Point(6, 6)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_006", 8, "Expert", listOf(ColorDot(1, Point(2, 4), Point(4, 0)), ColorDot(2, Point(3, 2), Point(7, 2)), ColorDot(3, Point(7, 4), Point(7, 5)), ColorDot(4, Point(5, 0), Point(7, 0)), ColorDot(5, Point(3, 3), Point(7, 7)), ColorDot(6, Point(5, 6), Point(7, 6)), ColorDot(7, Point(2, 6), Point(7, 1)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_007", 8, "Expert", listOf(ColorDot(1, Point(4, 7), Point(7, 4)), ColorDot(2, Point(0, 7), Point(6, 4)), ColorDot(3, Point(0, 1), Point(0, 4)), ColorDot(4, Point(1, 1), Point(5, 1)), ColorDot(5, Point(0, 0), Point(1, 0)), ColorDot(6, Point(1, 4), Point(3, 4)), ColorDot(7, Point(2, 0), Point(2, 4)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_008", 8, "Expert", listOf(ColorDot(1, Point(5, 3), Point(7, 1)), ColorDot(2, Point(1, 2), Point(7, 7)), ColorDot(3, Point(2, 3), Point(4, 0)), ColorDot(4, Point(6, 1), Point(7, 3)), ColorDot(5, Point(5, 4), Point(6, 3)), ColorDot(6, Point(4, 2), Point(5, 2)), ColorDot(7, Point(1, 3), Point(7, 4)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_009", 8, "Expert", listOf(ColorDot(1, Point(2, 3), Point(6, 3)), ColorDot(2, Point(0, 3), Point(2, 0)), ColorDot(3, Point(1, 7), Point(6, 6)), ColorDot(4, Point(1, 6), Point(5, 7)), ColorDot(5, Point(0, 5), Point(5, 0)), ColorDot(6, Point(0, 6), Point(0, 7)), ColorDot(7, Point(0, 2), Point(3, 1)))),
        PregeneratedPuzzle("Expert_8x8_puzzle_010", 8, "Expert", listOf(ColorDot(1, Point(4, 6), Point(5, 7)), ColorDot(2, Point(0, 4), Point(2, 5)), ColorDot(3, Point(5, 6), Point(6, 1)), ColorDot(4, Point(0, 3), Point(5, 5)), ColorDot(5, Point(1, 6), Point(3, 7)), ColorDot(6, Point(0, 6), Point(2, 7)), ColorDot(7, Point(0, 2), Point(5, 2)))),
    )

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedPuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }

    fun getPuzzleById(id: String): PregeneratedPuzzle? = ALL_PUZZLES.find { it.id == id }

    fun getRandomPuzzle(difficulty: FlowDifficulty): PregeneratedPuzzle? {
        val diffName = when(difficulty) {
            FlowDifficulty.EASY -> "Easy"
            FlowDifficulty.MEDIUM -> "Medium"
            FlowDifficulty.HARD -> "Hard"
            FlowDifficulty.EXPERT -> "Expert"
        }
        return PUZZLES_BY_DIFFICULTY[diffName]?.randomOrNull()
    }
}
