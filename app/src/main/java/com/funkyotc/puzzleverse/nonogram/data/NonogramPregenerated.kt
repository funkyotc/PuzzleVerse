package com.funkyotc.puzzleverse.nonogram.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedNonogram(
    override val id: String,
    override val difficulty: String,
    val size: Int,
    val rowClues: List<List<Int>>,
    val colClues: List<List<Int>>,
    val gridStr: String
) : BrowseablePuzzle {
    override val label: String get() = "Puzzle"
    override val subtitle: String get() = "${size}x${size}"
    val grid: List<List<Boolean>> get() = gridStr.map { it == '1' }.chunked(size)
}

object NonogramPregenerated {

    val ALL_PUZZLES: List<PregeneratedNonogram> by lazy {
        listOf(
            PregeneratedNonogram("nonogram_easy_1", "Easy", 5, listOf(listOf(1, 1), listOf(1), listOf(1, 2), listOf(1), listOf(1)), listOf(listOf(1), listOf(1, 1), listOf(), listOf(1), listOf(4)), "0100100001100110000101000"),
            PregeneratedNonogram("nonogram_easy_10", "Easy", 5, listOf(listOf(5), listOf(1), listOf(), listOf(2), listOf(3)), listOf(listOf(1, 1), listOf(1, 2), listOf(2, 1), listOf(1, 1), listOf(1)), "1111100100000001100001110"),
            PregeneratedNonogram("nonogram_easy_11", "Easy", 5, listOf(listOf(1), listOf(2, 1), listOf(3), listOf(1), listOf(1)), listOf(listOf(2, 1), listOf(3), listOf(1), listOf(1), listOf(1)), "1000011001011100100010000"),
            PregeneratedNonogram("nonogram_easy_12", "Easy", 5, listOf(listOf(1), listOf(1), listOf(), listOf(1), listOf()), listOf(listOf(), listOf(), listOf(1, 1), listOf(), listOf(1)), "0010000001000000010000000"),
            PregeneratedNonogram("nonogram_easy_13", "Easy", 5, listOf(listOf(1, 1), listOf(3), listOf(1, 1), listOf(1), listOf()), listOf(listOf(3), listOf(1), listOf(1), listOf(1), listOf(2)), "1001011100100010000100000"),
            PregeneratedNonogram("nonogram_easy_14", "Easy", 5, listOf(listOf(2, 1), listOf(1), listOf(1), listOf(2), listOf(1)), listOf(listOf(1, 2), listOf(1, 1), listOf(), listOf(2), listOf(1)), "1100100010000101100010000"),
            PregeneratedNonogram("nonogram_easy_15", "Easy", 5, listOf(listOf(), listOf(1, 1), listOf(1), listOf(), listOf(1)), listOf(listOf(2), listOf(), listOf(1), listOf(1), listOf()), "0000010100100000000000010"),
            PregeneratedNonogram("nonogram_easy_2", "Easy", 5, listOf(listOf(2), listOf(2), listOf(2), listOf(1), listOf(1)), listOf(listOf(5), listOf(3), listOf(), listOf(), listOf()), "1100011000110001000010000"),
            PregeneratedNonogram("nonogram_easy_3", "Easy", 5, listOf(listOf(1), listOf(), listOf(1, 2), listOf(2), listOf(2)), listOf(listOf(2), listOf(2), listOf(1, 1), listOf(1), listOf(1)), "0010000000100111100001100"),
            PregeneratedNonogram("nonogram_easy_4", "Easy", 5, listOf(listOf(1), listOf(1), listOf(1), listOf(4), listOf(2)), listOf(listOf(), listOf(1, 1), listOf(2), listOf(4), listOf(1)), "0100000010000100111100110"),
            PregeneratedNonogram("nonogram_easy_5", "Easy", 5, listOf(listOf(), listOf(5), listOf(5), listOf(1, 1), listOf(1, 1)), listOf(listOf(4), listOf(2), listOf(2), listOf(2), listOf(4)), "0000011111111111000110001"),
            PregeneratedNonogram("nonogram_easy_6", "Easy", 5, listOf(listOf(3), listOf(1), listOf(1), listOf(2), listOf(1)), listOf(listOf(1), listOf(1, 1), listOf(2), listOf(1), listOf(2)), "1110000100000010001101000"),
            PregeneratedNonogram("nonogram_easy_7", "Easy", 5, listOf(listOf(4), listOf(3), listOf(3), listOf(4), listOf(1, 1)), listOf(listOf(1, 1), listOf(1, 1), listOf(4), listOf(4), listOf(4)), "1111000111001110111110001"),
            PregeneratedNonogram("nonogram_easy_8", "Easy", 5, listOf(listOf(1), listOf(1, 1), listOf(1, 1), listOf(), listOf(1)), listOf(listOf(2), listOf(), listOf(1, 1), listOf(), listOf(2)), "1000010001001010000000100"),
            PregeneratedNonogram("nonogram_easy_9", "Easy", 5, listOf(listOf(5), listOf(5), listOf(5), listOf(5), listOf(5)), listOf(listOf(5), listOf(5), listOf(5), listOf(5), listOf(5)), "1111111111111111111111111"),
            PregeneratedNonogram("nonogram_medium_1", "Medium", 10, listOf(listOf(5), listOf(2, 2), listOf(2, 1, 2), listOf(2, 4), listOf(1, 2), listOf(1, 1, 1), listOf(1, 2), listOf(1, 1), listOf(), listOf(1, 1)), listOf(listOf(3), listOf(2, 1, 1), listOf(1), listOf(1), listOf(3, 1), listOf(2, 2, 1), listOf(1, 1, 1), listOf(1, 1), listOf(4, 1), listOf(5)), "0001111100000011001111001000111100001111100000001101000010010010110000000001001000000000000100010000"),
            PregeneratedNonogram("nonogram_medium_10", "Medium", 10, listOf(listOf(1, 2), listOf(1), listOf(), listOf(1, 2, 1), listOf(2, 1), listOf(1, 1), listOf(1), listOf(1, 1, 2), listOf(1, 1), listOf(1, 1, 1)), listOf(listOf(1, 1, 1, 1), listOf(1, 1), listOf(1, 1, 1), listOf(1, 1), listOf(1, 1, 1), listOf(1), listOf(1, 2), listOf(1, 1), listOf(1), listOf(2)), "0100001100000010000000000000001001100010011001000010001000000000001000101000110000010000011010000001"),
            PregeneratedNonogram("nonogram_medium_11", "Medium", 10, listOf(listOf(4, 4), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), listOf(listOf(10), listOf(10), listOf(10), listOf(10), listOf(9), listOf(9), listOf(10), listOf(10), listOf(10), listOf(10)), "1111001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"),
            PregeneratedNonogram("nonogram_medium_12", "Medium", 10, listOf(listOf(2, 1), listOf(1, 1, 1), listOf(1, 1), listOf(1, 1, 1), listOf(2, 2, 1), listOf(1), listOf(1, 2, 2), listOf(1, 1, 1), listOf(1, 1), listOf(1, 1)), listOf(listOf(1, 1, 1), listOf(2, 2), listOf(1, 1, 1), listOf(2, 1, 1), listOf(1, 1), listOf(1, 2), listOf(1), listOf(1, 1), listOf(1, 1, 1), listOf(1, 1, 2)), "0110000010010000010110010000000001001001110011010001000000000010110011000101000100100000101001000000"),
            PregeneratedNonogram("nonogram_medium_13", "Medium", 10, listOf(listOf(2, 3), listOf(1, 1, 1), listOf(), listOf(2, 1), listOf(1, 4), listOf(1, 1, 1), listOf(2, 1, 1), listOf(1, 1), listOf(1, 1), listOf(2)), listOf(listOf(1, 1), listOf(2, 1), listOf(3, 1), listOf(1), listOf(1, 1), listOf(1, 1), listOf(1, 3, 1), listOf(1, 2, 1), listOf(1, 1, 2), listOf(2)), "1100011100010010001000000000000001100100001001111000100010010110001001100000001000100000100000001100"),
            PregeneratedNonogram("nonogram_medium_14", "Medium", 10, listOf(listOf(1, 2, 1), listOf(8), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), listOf(listOf(8), listOf(10), listOf(9), listOf(9), listOf(10), listOf(10), listOf(9), listOf(9), listOf(10), listOf(8)), "0100110010011111111011111111111111111111111111111111111111111111111111111111111111111111111111111111"),
            PregeneratedNonogram("nonogram_medium_15", "Medium", 10, listOf(listOf(2, 2), listOf(4, 4), listOf(4, 4), listOf(4, 4), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(8)), listOf(listOf(8), listOf(9), listOf(10), listOf(10), listOf(6), listOf(6), listOf(10), listOf(10), listOf(9), listOf(8)), "0011001100111100111111110011111111001111111111111111111111111111111111111111111111111111110111111110"),
            PregeneratedNonogram("nonogram_medium_2", "Medium", 10, listOf(listOf(8), listOf(8), listOf(10), listOf(4, 4), listOf(4, 4), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), listOf(listOf(8), listOf(10), listOf(10), listOf(10), listOf(3, 5), listOf(3, 5), listOf(10), listOf(10), listOf(10), listOf(8)), "0111111110011111111011111111111111001111111100111111111111111111111111111111111111111111111111111111"),
            PregeneratedNonogram("nonogram_medium_3", "Medium", 10, listOf(listOf(1, 1), listOf(2, 1, 1), listOf(1, 1, 1), listOf(1), listOf(1), listOf(2), listOf(1, 1), listOf(1, 2, 1), listOf(1, 2, 1), listOf(1, 1)), listOf(listOf(1, 1), listOf(1, 1), listOf(2, 1), listOf(1, 1), listOf(2), listOf(2, 1, 2), listOf(3), listOf(1, 1), listOf(1, 3), listOf()), "0000010100011001001010100001000000010000000010000000011000000000001010010001101000010110101010000000"),
            PregeneratedNonogram("nonogram_medium_4", "Medium", 10, listOf(listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(8), listOf(3, 3)), listOf(listOf(8), listOf(10), listOf(10), listOf(10), listOf(9), listOf(9), listOf(10), listOf(10), listOf(10), listOf(8)), "1111111111111111111111111111111111111111111111111111111111111111111111111111111101111111100111001110"),
            PregeneratedNonogram("nonogram_medium_5", "Medium", 10, listOf(listOf(), listOf(6), listOf(8), listOf(10), listOf(3, 3), listOf(2, 2), listOf(8), listOf(10), listOf(1, 1, 1, 1), listOf(1, 1)), listOf(listOf(2, 3), listOf(6), listOf(7), listOf(3, 3), listOf(3, 2), listOf(3, 2), listOf(3, 3), listOf(7), listOf(6), listOf(2, 3)), "0000000000001111110001111111101111111111111000011101100001100111111110111111111110010010011000000001"),
            PregeneratedNonogram("nonogram_medium_6", "Medium", 10, listOf(listOf(1, 1), listOf(1), listOf(1, 1, 1), listOf(2), listOf(1, 1), listOf(1, 1), listOf(1, 2, 2), listOf(2), listOf(1, 1), listOf(1, 1)), listOf(listOf(2, 1, 2), listOf(2), listOf(2, 1), listOf(1, 1), listOf(1, 1), listOf(1, 1), listOf(1), listOf(1, 1), listOf(1), listOf(3)), "1000000100100000000001001010000110000000001000000100000001011011000011000011000010010000001000010000"),
            PregeneratedNonogram("nonogram_medium_7", "Medium", 10, listOf(listOf(2), listOf(), listOf(1, 1), listOf(1, 1), listOf(1, 1), listOf(1, 1, 1), listOf(1, 1, 1), listOf(1, 1, 1), listOf(), listOf(1, 1, 1)), listOf(listOf(1, 1, 1), listOf(1, 1, 1, 1), listOf(1, 1), listOf(1, 1), listOf(1, 1, 1), listOf(1, 1), listOf(1), listOf(1), listOf(), listOf(1, 1)), "1100000000000000000001001000000000010001010010000010100001000101001000001010000100000000001001010000"),
            PregeneratedNonogram("nonogram_medium_8", "Medium", 10, listOf(listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), listOf(listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"),
            PregeneratedNonogram("nonogram_medium_9", "Medium", 10, listOf(listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), listOf(listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10), listOf(10)), "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedNonogram>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
