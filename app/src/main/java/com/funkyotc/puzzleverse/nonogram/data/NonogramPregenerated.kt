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
    val grid: List<List<Boolean>> get() = gridStr.map { it == '1' }.chunked(size)
    override val label: String get() = "Nonogram ${id.substringAfterLast('_')}"
    override val subtitle: String get() = "${size}x${size}"
}

object NonogramPregenerated {

    val ALL_PUZZLES: List<PregeneratedNonogram> by lazy {
        listOf(
            PregeneratedNonogram(
                id = "nonogram_easy_1",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(0), listOf(2, 2), listOf(4, 4), listOf(9), listOf(9), listOf(7), listOf(5), listOf(3), listOf(1), listOf(0)),
                colClues = listOf(listOf(3), listOf(5), listOf(6), listOf(6), listOf(6), listOf(6), listOf(6), listOf(5), listOf(3), listOf(0)),
                gridStr = "0000000000011000110011110111101111111110111111111001111111000011111000000111000000001000000000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_2",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(6), listOf(1, 1), listOf(1, 1, 1, 1), listOf(1, 1, 1, 1), listOf(1, 1), listOf(1, 1, 1, 1), listOf(1, 4, 1), listOf(1, 1), listOf(6), listOf(0)),
                colClues = listOf(listOf(5), listOf(1, 1), listOf(1, 2, 1, 1), listOf(1, 1, 1), listOf(1, 1, 1), listOf(1, 1, 1), listOf(1, 1, 1), listOf(1, 2, 1, 1), listOf(1, 1), listOf(5)),
                gridStr = "0011111100010000001010100001011010000101100000000110100001011001111001010000001000111111000000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_3",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1), listOf(3), listOf(5), listOf(7), listOf(9), listOf(7), listOf(2, 3), listOf(2, 3), listOf(7), listOf(0)),
                colClues = listOf(listOf(1), listOf(6), listOf(7), listOf(5, 1), listOf(6, 1), listOf(8), listOf(7), listOf(6), listOf(1), listOf(0)),
                gridStr = "0000100000000111000000111110000111111100111111111001111111000110011100011001110001111111000000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_4",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(0), listOf(1, 1), listOf(2, 2), listOf(7), listOf(7), listOf(7), listOf(7), listOf(9), listOf(9), listOf(0)),
                colClues = listOf(listOf(2), listOf(8), listOf(7), listOf(6), listOf(6), listOf(6), listOf(7), listOf(8), listOf(2), listOf(0)),
                gridStr = "0000000000010000010001100011000111111100011111110001111111000111111100111111111011111111100000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_5",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1), listOf(3), listOf(5), listOf(7), listOf(5), listOf(7), listOf(9), listOf(3), listOf(3), listOf(3)),
                colClues = listOf(listOf(1), listOf(1, 2), listOf(5), listOf(9), listOf(10), listOf(9), listOf(5), listOf(1, 2), listOf(1), listOf(0)),
                gridStr = "0000100000000111000000111110000111111100001111100001111111001111111110000111000000011100000001110000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_6",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(6), listOf(8), listOf(10), listOf(2, 4, 2), listOf(2, 4, 2), listOf(10), listOf(8), listOf(1, 1, 1, 1), listOf(6), listOf(0)),
                colClues = listOf(listOf(4), listOf(6), listOf(3, 4), listOf(7, 1), listOf(9), listOf(7, 1), listOf(9), listOf(3, 2, 1), listOf(7), listOf(4)),
                gridStr = "0011111100011111111011111111111101111011110111101111111111110111111110001010101000111111000000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_7",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1), listOf(3), listOf(5), listOf(7), listOf(9), listOf(7), listOf(5), listOf(3), listOf(1), listOf(0)),
                colClues = listOf(listOf(1), listOf(3), listOf(5), listOf(7), listOf(9), listOf(7), listOf(5), listOf(3), listOf(1), listOf(0)),
                gridStr = "0000100000000111000000111110000111111100111111111001111111000011111000000111000000001000000000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_8",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1), listOf(2), listOf(2), listOf(2), listOf(2), listOf(2), listOf(2), listOf(4), listOf(2), listOf(1)),
                colClues = listOf(listOf(2), listOf(2), listOf(2), listOf(3), listOf(2, 1), listOf(2), listOf(2), listOf(2), listOf(2), listOf(0)),
                gridStr = "0000000010000000011000000011000000011000000011000000011000000011000000011110000011000000001000000000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_9",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(4), listOf(8), listOf(10), listOf(2, 4, 2), listOf(10), listOf(6), listOf(4), listOf(4), listOf(4), listOf(4)),
                colClues = listOf(listOf(3), listOf(4), listOf(2, 2), listOf(10), listOf(10), listOf(10), listOf(10), listOf(2, 2), listOf(4), listOf(3)),
                gridStr = "0001111000011111111011111111111101111011111111111100111111000001111000000111100000011110000001111000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_10",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(4), listOf(6), listOf(8), listOf(1, 2, 3), listOf(8), listOf(8), listOf(8), listOf(8), listOf(1, 1, 1, 2), listOf(1, 1, 1, 1)),
                colClues = listOf(listOf(0), listOf(8), listOf(2, 4), listOf(10), listOf(8), listOf(3, 6), listOf(8), listOf(9), listOf(7), listOf(0)),
                gridStr = "0001111000001111110001111111100101101110011111111001111111100111111110011111111001010101100101010100"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_11",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 1, 1), listOf(2, 1, 1), listOf(2, 6), listOf(2, 2, 3), listOf(2, 1, 2, 1), listOf(1, 2, 1, 2), listOf(1, 1, 3, 1), listOf(1, 3, 1, 1), listOf(1, 1, 1, 1), listOf(3, 1, 3)),
                colClues = listOf(listOf(1, 5), listOf(3, 1), listOf(3, 4, 1), listOf(1, 1, 1, 1), listOf(1, 3, 4), listOf(2, 2), listOf(3, 2), listOf(4, 2), listOf(4, 1, 1), listOf(1, 1, 4)),
                gridStr = "0010100010001101001001101111111101101110011010110110110101101010111001101110100110001001011110100111"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_12",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(6, 1), listOf(1, 1, 2), listOf(3, 1, 2), listOf(4, 1), listOf(2, 2, 1), listOf(1, 1, 2, 1), listOf(2, 1, 1, 1), listOf(2, 1), listOf(6, 2), listOf(2, 4)),
                colClues = listOf(listOf(1, 1, 3, 1), listOf(3, 1, 1, 2), listOf(1, 1, 1, 1), listOf(1, 1, 1, 1), listOf(2, 1, 2), listOf(1, 3, 4), listOf(3, 3), listOf(2, 1, 1), listOf(4, 1), listOf(5)),
                gridStr = "1111110010010010011011100101100001111010110001100110100011011101010001000001100101111110111100111100"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_13",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 1, 1), listOf(1, 7), listOf(2, 4), listOf(1, 7), listOf(2), listOf(2, 1, 1), listOf(1, 1, 1, 1, 1), listOf(2, 3), listOf(4, 2), listOf(1, 4)),
                colClues = listOf(listOf(2, 1), listOf(1, 1), listOf(1, 2, 2), listOf(4, 2), listOf(4, 3), listOf(1, 1, 2), listOf(4, 3, 1), listOf(3, 3), listOf(3, 1, 3), listOf(1, 2, 1)),
                gridStr = "1000001001101111111000011011110101111111000110000001100010101010101001000110111000111101100010011110"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_14",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 2, 1), listOf(2, 2), listOf(4, 1, 1), listOf(2, 1, 1, 2), listOf(6), listOf(1, 2), listOf(1, 3), listOf(1, 2, 2), listOf(3, 1, 1), listOf(1, 1, 2)),
                colClues = listOf(listOf(2), listOf(2, 2, 1), listOf(3, 1, 2), listOf(2, 1, 2), listOf(1, 2, 3), listOf(3, 4, 1), listOf(1, 4, 2), listOf(1), listOf(1, 2, 1), listOf(1, 2)),
                gridStr = "0010110010001101100011110100101100101011001111110001000110000100111000001011001100111010010101011000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_15",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 3), listOf(2, 1, 1), listOf(1, 4, 1), listOf(5, 1, 1), listOf(2, 2, 2, 1), listOf(1, 1, 1, 1), listOf(4, 1), listOf(2, 7), listOf(2, 1), listOf(2, 1, 4)),
                colClues = listOf(listOf(2, 3), listOf(3, 3), listOf(1, 1, 2), listOf(5, 2, 1), listOf(6), listOf(1, 2), listOf(1, 2, 3), listOf(2, 2, 1, 1), listOf(1, 1, 1, 1, 1), listOf(2, 2, 2, 1)),
                gridStr = "0001000111001100010101011110101111100101110110110100101010100011110001110111111111000010001101001111"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_1",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 4, 1, 3, 1), listOf(1, 1, 5, 4), listOf(1, 1, 4, 1, 1), listOf(1, 1, 1, 3, 3), listOf(2, 2, 2), listOf(1, 2, 1, 3, 2), listOf(1, 1, 1, 2, 2), listOf(1, 3, 3, 1, 1), listOf(2, 6, 2, 1), listOf(2, 1, 1, 3), listOf(3, 4, 1, 1), listOf(1, 2, 3, 1, 1), listOf(2, 1, 1, 1, 1), listOf(1, 3, 1), listOf(4, 5, 1, 2)),
                colClues = listOf(listOf(1, 4, 2, 2, 1), listOf(1, 1, 1, 1, 1, 1), listOf(1, 2, 6), listOf(2, 2, 3, 1, 1), listOf(1, 6, 1, 1), listOf(3, 4, 2), listOf(4, 1, 1, 2, 2), listOf(2, 3, 2, 2), listOf(5, 3, 1, 1), listOf(1, 3, 1, 1), listOf(1, 2, 2, 1, 1), listOf(2, 2, 1, 1, 1), listOf(2, 1, 1, 3), listOf(1, 1, 2, 1, 2), listOf(4, 6, 1, 1)),
                gridStr = "100111101011101010101111101111101001111010001100010101110111110110001100000100110100111011001010010011011101110011100101110111111011001001101001000111111011110000101101101110010100011010001001001001001110000010111101111101011"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_2",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 2, 1, 2, 1), listOf(4, 7), listOf(3, 3), listOf(4, 2, 6), listOf(1, 1, 2, 1), listOf(3, 1, 1, 4, 1), listOf(4, 3, 1), listOf(1, 1, 3, 1, 2, 1), listOf(2, 2, 1, 1, 1), listOf(1, 3), listOf(1, 3, 1, 2, 1), listOf(1, 2, 1, 1, 1, 1), listOf(3, 7), listOf(7), listOf(2, 2, 1)),
                colClues = listOf(listOf(1, 1, 2, 1), listOf(1, 1, 1, 3, 1), listOf(1, 1, 3, 2, 1), listOf(2, 2, 1, 2, 1), listOf(2, 3, 1), listOf(1, 1, 3, 1), listOf(1, 3, 2, 2), listOf(1, 1, 1, 1), listOf(4, 1, 1, 3), listOf(2, 1, 1, 1, 3), listOf(6, 1, 1, 2), listOf(7, 4), listOf(4, 3, 1, 3), listOf(1, 2, 3, 2), listOf(1, 3, 1, 1, 1)),
                gridStr = "010110101100100001111001111111000000111011100111101101111110000100100011010111010010111101001111000011101101011100101101110001101010010010000000000111010011101011010101100100101001011100011111110000000001111111001100011000100"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_3",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(3, 3, 3), listOf(2, 7), listOf(1, 1, 1, 1, 2), listOf(2, 3, 3), listOf(1, 9), listOf(1, 4, 2, 3), listOf(1, 1, 1, 1, 1, 1), listOf(1, 2, 1, 1, 3, 1), listOf(4, 2, 5), listOf(2, 2, 4), listOf(4, 3, 3), listOf(2, 10), listOf(2, 2, 1, 1), listOf(4, 7), listOf(2, 1, 1, 1, 2, 1)),
                colClues = listOf(listOf(1, 9, 1), listOf(2, 7), listOf(1, 1, 1, 2, 1, 1), listOf(1, 1, 4, 1, 2), listOf(2, 1, 1, 1, 1), listOf(1, 2, 3, 1), listOf(1, 2, 1, 1, 3, 1), listOf(6, 1, 3), listOf(1, 4, 3, 1), listOf(3, 1, 1, 1, 1, 2), listOf(2, 3, 7), listOf(2, 9, 2), listOf(5, 1, 2, 2), listOf(2, 1, 3, 3), listOf(1, 2)),
                gridStr = "011101110111000110000011111110001010010100110000110111011100100001111111110101111011011100100100101001010101101010111010111101100011111110011001111000111100111011100110011111111110110000110010010011110001111111110100100101101"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_4",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(2, 2, 2, 1, 1), listOf(1, 6, 2), listOf(2, 2, 3), listOf(1, 1, 3, 2), listOf(1, 1, 2, 4), listOf(6, 1, 3, 1), listOf(3, 3, 2, 3), listOf(3, 3, 3), listOf(6, 3, 1), listOf(1, 2, 2), listOf(1, 2, 4, 1), listOf(3, 1, 2, 2), listOf(2, 2, 2), listOf(3, 2, 2, 1, 2), listOf(2, 3, 1)),
                colClues = listOf(listOf(1, 1, 2, 3), listOf(2, 1, 4, 1, 3), listOf(5, 1, 1), listOf(1, 2, 1, 3, 1), listOf(3, 1, 7), listOf(3, 3, 1, 2), listOf(1, 1, 2, 1), listOf(1, 1, 2, 1, 1), listOf(2, 2, 1, 1, 1, 1), listOf(1, 2, 1, 1, 3), listOf(2, 1, 2, 1, 1, 2), listOf(1, 3, 5, 1), listOf(1, 1, 4, 3, 1), listOf(3, 2, 1), listOf(1, 5, 1, 1)),
                gridStr = "110011000110101010111111011000000011001100111010000100111011001001011001111111111001011101011101110110111111000111001110111111000111001000010011001100010110000111101001110101101100110110000110000111011011010110110111000001000"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_5",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(5, 1, 1, 2), listOf(1, 3, 2, 1, 2), listOf(1, 1, 1, 1, 1, 1, 1), listOf(2, 2, 2, 1, 1), listOf(3, 3, 1, 1), listOf(4, 1, 2, 1, 1), listOf(1, 1, 1, 2), listOf(1, 1, 1, 1, 2, 1), listOf(1, 1, 2, 2), listOf(3, 3, 3, 2), listOf(1, 7, 3, 1), listOf(1, 3, 4, 3), listOf(3, 4, 1, 1), listOf(2, 4, 1, 1), listOf(4, 2, 6)),
                colClues = listOf(listOf(3, 2, 1, 5), listOf(1, 4, 2, 3), listOf(1, 3, 1, 4, 1), listOf(3, 1, 3, 1), listOf(2, 1, 1, 1, 2), listOf(5, 2, 3), listOf(1, 1, 6), listOf(5, 5), listOf(2, 1, 1, 1, 4), listOf(2, 1, 2, 1, 1), listOf(1, 8, 1), listOf(2, 1, 2, 1), listOf(1, 1, 5), listOf(3, 3, 1, 1), listOf(1, 1, 1, 7)),
                gridStr = "111110001010110100111001101011100101010101010011011011010001111001110010100111101011010001010010010110000101000101011010010010000110011011101110111011101111111011101101110111100111111001111000101110001111000101111101100111111"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_6",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 4, 1), listOf(5, 1, 7), listOf(1, 2, 1, 3, 1), listOf(1, 4, 2, 3, 1), listOf(1, 2, 2, 2, 2), listOf(1, 2, 2, 2), listOf(4, 1, 3), listOf(2, 1, 1, 1, 1, 2), listOf(1, 1, 2, 2, 3), listOf(1, 1, 5, 1), listOf(2, 1, 2, 1), listOf(1, 3, 2), listOf(1, 3, 2, 2), listOf(1, 3, 3, 2), listOf(2, 1, 2, 2, 1)),
                colClues = listOf(listOf(1, 3, 1, 1, 2), listOf(2, 2, 3, 1), listOf(1, 1, 4), listOf(7, 1), listOf(7, 2, 1), listOf(1, 1, 2), listOf(2, 6, 3), listOf(1, 3, 4, 1), listOf(2, 1, 5), listOf(2, 1, 2, 3), listOf(5, 2, 2, 3), listOf(3, 2, 1, 2), listOf(3, 5, 2), listOf(2, 1, 2, 3), listOf(4, 1, 1)),
                gridStr = "000100011110010111110101111111010110100011101101111011011101100110110110011100110110001100011110100011100011010101010110101001101100111001010111110100110010011010000010000011101100010001110110110100011100111011110100110011010"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_7",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(2, 2, 2, 1, 1), listOf(1, 1, 1, 2), listOf(3, 3, 5, 1), listOf(3, 1, 1, 2), listOf(4, 1, 2, 1, 1), listOf(3, 3, 3), listOf(2, 2, 1, 1, 2), listOf(4, 1, 1, 1, 1), listOf(2, 2, 1, 2, 1), listOf(1, 1, 1, 3, 2, 1), listOf(3, 1, 1, 3), listOf(1, 4, 1, 1, 2), listOf(3, 1, 2, 2), listOf(1, 2, 1, 3), listOf(1, 3, 5, 1, 1)),
                colClues = listOf(listOf(3, 1, 1, 2, 2, 1), listOf(1, 1, 5, 1, 2), listOf(4, 1, 4, 1), listOf(1, 6, 2, 1), listOf(4, 4, 1, 1), listOf(1, 1), listOf(3, 1, 4, 1, 1), listOf(1, 1, 2, 1, 1, 2), listOf(1, 2, 2, 2), listOf(7, 1, 1), listOf(1, 6), listOf(3, 1, 1, 1), listOf(3, 1, 1, 3), listOf(1, 9), listOf(1, 1, 3, 2, 2)),
                gridStr = "110110110001001100010100001100111011101111101001110010100110111100101101001011100011100111110110010100011011110100101010110110101100010101010111011010011100100010111101111010010011111000100110110010000011010111101110111110101"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_8",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(2, 5, 2), listOf(3, 2, 1, 4), listOf(1, 3, 5, 1), listOf(1, 1, 2, 1, 1, 3), listOf(1, 3, 1, 1, 1), listOf(1, 3, 2, 4), listOf(1, 1, 2, 2, 1, 1), listOf(1, 1, 2, 4), listOf(2, 5, 2, 2), listOf(1, 3, 1, 3, 2), listOf(1, 1, 1, 1), listOf(1, 1, 1, 2, 1, 1), listOf(1, 2, 1, 2, 1), listOf(6, 1, 3), listOf(2, 3, 2, 1)),
                colClues = listOf(listOf(1, 2, 1, 5, 1), listOf(3, 1, 2, 1), listOf(2, 1, 1, 1, 2), listOf(1, 1, 2, 3), listOf(2, 6, 1), listOf(6, 1, 3), listOf(2, 1, 4, 2), listOf(1, 2, 2, 2), listOf(3, 2, 1, 1), listOf(1, 2, 2, 1, 1, 1), listOf(1, 1, 1, 5, 1), listOf(3, 4, 3), listOf(5, 1, 1, 1, 1), listOf(2, 1, 1, 3, 3), listOf(3, 5, 1)),
                gridStr = "011000111110011111001101001111010111001111101101011010101110100001110010101010111001101111101011001101001010010110001111110111110011011101110101110110100010100010000100101000110101101101000011010001111110101110110000111011010"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_9",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(2, 3, 2, 1), listOf(1, 3, 4, 1), listOf(1, 4, 6), listOf(1, 5), listOf(4, 1, 1), listOf(2, 4, 2, 2), listOf(3, 5, 1, 1), listOf(5, 2, 6), listOf(1, 2, 2), listOf(1, 4, 2), listOf(2, 2, 4, 2), listOf(1, 2, 1, 1), listOf(11, 1, 1), listOf(1, 4, 2), listOf(3, 6, 1)),
                colClues = listOf(listOf(3, 2, 6), listOf(1, 3, 1, 1, 1), listOf(2, 3, 1, 1, 1), listOf(2, 1, 3, 2), listOf(3, 2, 1, 1, 4), listOf(1, 1, 3, 7), listOf(1, 5, 1, 3), listOf(2, 3, 2, 1), listOf(3, 1, 1, 1, 1), listOf(3, 4, 1, 1, 1), listOf(4, 1, 4, 2), listOf(3, 2, 2, 1), listOf(1, 2, 1, 1), listOf(5, 1), listOf(1, 1, 1, 1, 3, 1)),
                gridStr = "110011100110100101110011110001101111011111100000000001011111000111100001010011011110110011111001111101010111110110111111000101100110000101111000011000110001101111011100011010000001111111111110101100111100011000111011111100001"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_10",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(3, 2, 1, 1), listOf(2, 2, 1, 3, 1), listOf(4, 1, 2, 4), listOf(1, 2, 1, 1, 3), listOf(2, 7, 1), listOf(1, 4, 1, 4), listOf(1, 4, 5, 1), listOf(4, 2, 4, 1), listOf(2, 5, 1, 1), listOf(1, 1, 1, 2), listOf(1, 1, 5, 1, 2), listOf(1, 2, 4, 3), listOf(6, 3, 2), listOf(2, 1, 1, 4), listOf(1, 2, 2, 2)),
                colClues = listOf(listOf(2, 3, 3, 1), listOf(3, 1, 2, 2), listOf(1, 1, 1, 1, 2), listOf(1, 7, 1, 1), listOf(2, 1, 2, 1, 2, 1), listOf(2, 5, 3, 1), listOf(1, 7, 1), listOf(2, 2, 1, 2), listOf(1, 1, 1, 2, 4, 1), listOf(2, 5, 5), listOf(1, 2, 2, 1, 2), listOf(2, 7, 2), listOf(1, 2, 2, 1, 2), listOf(2, 1, 4), listOf(8, 4)),
                gridStr = "000111011000101110011010111001111100101101111010110010010111001101111111001010111100101111100111101111101111101101111001110111110101000001000101011000100101111101011100011011110111111111001110011011000100101111100011001101100"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_1",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 2, 2, 2, 1, 1, 1), listOf(1, 1, 4, 2, 1, 1, 1), listOf(1, 1, 1, 2, 2, 1), listOf(2, 2, 1, 2, 1, 2), listOf(1, 1, 1, 1, 2, 1), listOf(2, 3, 1, 1, 1, 5), listOf(1, 1, 2, 5, 1, 2), listOf(1, 2, 5, 1, 2), listOf(3, 1, 1, 1, 6), listOf(2, 6, 2, 1), listOf(1, 1, 2, 3, 1, 1, 2), listOf(1, 4, 2, 1, 1, 1, 1), listOf(1, 1, 1, 1, 1, 1, 2), listOf(2, 2, 1, 1, 1), listOf(2, 1, 2, 1, 1, 2, 1), listOf(3, 3, 2, 1, 1), listOf(4, 3, 1, 1, 4), listOf(3, 1, 1, 1, 2), listOf(1, 6, 6), listOf(3, 1, 2, 1)),
                colClues = listOf(listOf(3, 2, 1, 1, 1), listOf(1, 3, 2, 2, 4, 1), listOf(1, 2, 6), listOf(2, 2, 1, 3, 2), listOf(2, 4, 3, 3, 1), listOf(2, 2, 2, 3, 1), listOf(2, 1, 1, 4, 5), listOf(1, 1, 1, 1, 1), listOf(3, 3, 3, 1, 1, 1, 1), listOf(1, 2, 3, 2, 1), listOf(1, 2, 4, 2, 2), listOf(2, 3, 1, 2, 2), listOf(2, 2, 2, 2, 1, 1), listOf(1, 2, 1, 2, 2, 1), listOf(1, 1, 1, 1, 1), listOf(2, 8, 1, 1, 1), listOf(2, 2, 1, 3, 1), listOf(2, 1, 2, 1, 2, 1, 1), listOf(5, 2, 1, 5), listOf(4, 2, 2, 1, 2)),
                gridStr = "0101011011001101010110010111100110010101100010001011011000011101101000001101001101010000100010011010110011101001010111111010110111110001001101011000011111010110111010101000111111000011001111110001101001010110111010010110010111100110101010011010100010100001001101101100010010000100011001001101001011011110111000110000101001111011101001011110001110100001010000110100001111110011111100001110001011000010"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_2",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 2, 2, 2, 5), listOf(6, 2, 1), listOf(1, 4, 3, 1, 1, 1), listOf(1, 1, 1, 2, 1), listOf(2, 1, 3, 3), listOf(1, 1, 3, 1, 3, 1), listOf(3, 5, 3), listOf(2, 2, 2, 1, 1, 1), listOf(3, 2, 2, 1, 1), listOf(2, 1, 5, 3, 1), listOf(4, 1, 4, 5), listOf(1, 1, 2, 2, 1, 2, 1), listOf(1, 1, 1, 2, 2, 3), listOf(1, 1, 5, 1, 1, 2), listOf(2, 4, 2, 5, 1), listOf(4, 1, 1, 2), listOf(3, 1, 2, 1, 1), listOf(10, 2, 1, 1, 1), listOf(3, 1, 4, 2, 2, 2), listOf(2, 2, 1, 2)),
                colClues = listOf(listOf(3, 2, 3, 3), listOf(1, 2, 4, 1, 2), listOf(2, 1, 1, 1, 1, 2), listOf(3, 1, 4, 1, 2), listOf(10, 4, 1), listOf(3, 2, 1, 2, 6), listOf(1, 1, 2, 1, 4), listOf(1, 2, 2), listOf(1, 1, 1, 2, 5), listOf(1, 1, 4, 3, 3), listOf(1, 1, 2, 4, 3, 2), listOf(1, 1, 3, 1, 1), listOf(2, 1, 2, 2, 1, 1, 3), listOf(2, 1, 7, 3), listOf(3, 2, 3), listOf(2, 1, 4, 1, 3), listOf(1, 5, 6, 2), listOf(1, 1, 2, 2, 1, 2), listOf(1, 1, 3, 1, 1, 1, 1), listOf(1, 3, 2, 2)),
                gridStr = "1011011001100001111111111100000110010000100111101110100010100100100100000110100011001000000011101110101011100010001110100000111001111100111011011000110010100100111011000110001000101100100001111101110101111000101111011111100101100011010110011001010011001101110010010001111101001011011011110110111110010001111010100110000000001110100110100100011111111110110101010111010111101101101100001100110001011000"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_3",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 1, 1, 1, 1, 1, 1), listOf(1, 1, 4, 4, 1, 1), listOf(1, 1, 1, 4), listOf(1, 3, 3, 1, 1), listOf(4, 1, 2, 1, 1, 2), listOf(3, 3, 2, 1), listOf(1, 7, 1, 1), listOf(1, 1, 4, 1, 1, 3), listOf(2, 3, 1, 4, 1), listOf(2, 1, 1, 3, 2), listOf(5, 1, 1, 2, 4), listOf(2, 2, 1, 2), listOf(2, 2, 1, 1, 1, 1, 2), listOf(1, 1, 1, 2, 2, 1, 1, 2), listOf(1, 1, 4, 4), listOf(2, 3, 1, 4, 1), listOf(3, 3, 1, 1, 1), listOf(4, 3, 1), listOf(1, 3, 5, 4, 1), listOf(1, 3, 1, 4, 1)),
                colClues = listOf(listOf(3, 1, 1, 1, 2, 2), listOf(2, 1, 1, 1, 1, 1, 1), listOf(9, 2, 1, 3), listOf(1, 3, 5), listOf(2, 1, 3, 2, 2, 2), listOf(1, 1, 2, 2, 1, 1), listOf(3, 4, 1, 3, 1), listOf(2, 5, 1, 1, 2, 2), listOf(4, 1, 1, 2, 1), listOf(2, 3, 1, 1, 1, 2), listOf(1, 3, 1, 2, 3), listOf(1, 1, 1, 1, 2, 2, 1), listOf(1, 1, 4, 2, 1, 1), listOf(2, 2, 1, 1), listOf(2, 1, 4, 4, 1), listOf(3, 1, 1, 3, 2), listOf(1, 1, 3, 1, 4, 1), listOf(2, 1, 1, 1, 2, 2), listOf(2, 1, 2, 1, 2), listOf(1, 2, 2, 2, 1, 1)),
                gridStr = "1010100101000010100110101111011110100010101000100000000111100010011100000111010101111001001101010011111001110110000001000010011111110000101010100011110010101110011011101000011110010000110010101110001111111001010110111100001101101000110000001101100001001010101110101001101100101011010000100111101111000001101110001011110100111011100101010000111100000011100001001011101111100011110101011101011110010000"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_4",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(5, 3, 3), listOf(1, 1, 5, 2, 2), listOf(2, 5, 5, 2, 2), listOf(2, 1, 2, 1, 1, 3), listOf(1, 1, 3, 2, 4), listOf(1, 2, 3, 2, 1, 3), listOf(3, 2, 2, 1, 3, 1), listOf(1, 1, 5, 1, 1, 1), listOf(2, 2, 1, 3, 3, 2), listOf(5, 1, 2, 1, 2, 1), listOf(1, 4, 1, 1, 6, 1), listOf(1, 2, 2, 6, 2), listOf(1, 6, 1, 1), listOf(4, 2, 3, 3, 2), listOf(1, 1, 1, 3, 1, 1, 1), listOf(1, 2, 3, 1, 1, 3), listOf(1, 1, 1, 2, 2, 1, 1), listOf(1, 4, 2, 2, 1, 1, 1), listOf(2, 6, 3, 6), listOf(5, 3, 5, 1)),
                colClues = listOf(listOf(3, 2, 4, 5), listOf(2, 1, 2, 1, 2), listOf(1, 3, 3, 1, 3, 1), listOf(4, 1, 4, 1, 1, 3), listOf(1, 1, 5, 1, 3), listOf(1, 1, 3, 1, 3, 2), listOf(1, 1, 1, 2, 1, 1, 1), listOf(3, 3, 2, 1, 1, 3), listOf(1, 1, 1, 1, 8), listOf(3, 1, 2, 1, 1, 2, 1), listOf(5, 4, 4, 2), listOf(3, 2, 1, 7, 3), listOf(2, 4, 5, 1, 2), listOf(2, 1, 7, 2, 1), listOf(1, 3, 1, 2, 2, 2), listOf(1, 1, 2, 1, 4), listOf(3, 2, 3, 1), listOf(1, 3, 3, 1, 2), listOf(7, 1, 5, 2), listOf(3, 2, 1, 3, 1, 4)),
                gridStr = "0011111001110000011110010001111101100011110111110111110110111101000110101000111000100100011101101111101101110001101001111110110110101110001000001001011111010101110110101110111001101111101000110100110110111101010111111001101100110011111100110000010011111100101000111101101110111011010001001011101010101011000111010100011110100010110011010001101111011011000101011101111110111011111111111001110111110010"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_5",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 3, 1, 1, 1, 3), listOf(1, 4, 1, 1, 2, 2), listOf(1, 7, 1, 2, 1, 1), listOf(4, 1, 1, 1, 4, 2), listOf(1, 2, 2, 1, 3, 2), listOf(1, 2, 2, 1, 1, 4), listOf(1, 1, 1, 1, 1, 6), listOf(1, 1, 1, 2, 2, 3), listOf(4, 1, 3, 3, 2, 1), listOf(2, 1, 5, 3, 3), listOf(4, 1, 6, 1, 1), listOf(2, 3, 2, 1, 1, 3), listOf(2, 4, 1, 1, 2), listOf(1, 1, 2, 2, 1, 4), listOf(2, 1, 2, 1, 1, 1), listOf(4, 1, 4, 2), listOf(1, 1, 6, 4), listOf(3, 2, 1, 3, 1, 2), listOf(6, 1, 1, 8), listOf(2, 4, 1, 2, 1, 1, 2)),
                colClues = listOf(listOf(6, 1, 1, 2, 2), listOf(1, 5, 3, 3), listOf(2, 1, 2, 4, 2, 2), listOf(5, 2, 1, 1, 5), listOf(3, 1, 3, 2), listOf(4, 2, 3, 1, 3), listOf(1, 1, 2, 1, 3, 1, 1), listOf(5, 1, 1, 2, 1, 1), listOf(1, 1, 3, 2, 1), listOf(3, 1, 2, 4, 2), listOf(2, 1, 3, 4, 2), listOf(2, 1, 4, 2, 1), listOf(1, 6, 1, 6), listOf(4, 3, 1, 4), listOf(12, 1, 4), listOf(1, 3, 2, 1, 1), listOf(3, 4, 3, 4), listOf(1, 4, 4, 1, 1, 1), listOf(3, 1, 1, 1, 7), listOf(1, 3, 1, 5)),
                gridStr = "0010111010101011100010111101001001101100100111111101011010101111010101011110011010011011010011100110101100110010101111001010010001001011111101010101100110111000111100101110111011010110010111110111011111110100001111110101011011101101001011100001101111010101100001001011011010001111011001000110101000101111000100111100001110010000011111101111011101100100111010111111110100101111111111011110101100101011"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_6",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 1, 7, 1, 2, 1), listOf(2, 1, 3, 1, 5, 1), listOf(1, 1, 3, 6), listOf(3, 1, 1, 1), listOf(2, 1, 1, 3, 1, 1, 1), listOf(2, 1, 1, 1, 1), listOf(2, 1, 5, 2, 2, 1), listOf(6, 1, 2, 2, 2), listOf(3, 2, 1, 4, 2), listOf(1, 4, 7, 1), listOf(1, 1, 1, 1, 2, 2, 1), listOf(4, 1, 1, 5), listOf(3, 4, 2, 1, 2), listOf(1, 1, 1, 1, 1, 1, 1, 1), listOf(1, 1, 1, 3, 3, 1, 2), listOf(1, 1, 2, 1, 1, 2, 2, 1), listOf(1, 1, 2, 1, 4, 1), listOf(2, 1, 1, 2, 2), listOf(4, 9), listOf(1, 5, 6)),
                colClues = listOf(listOf(2, 1, 3, 3, 1), listOf(1, 9, 1, 1), listOf(1, 1, 1, 2, 4, 2), listOf(4, 1, 2, 1), listOf(1, 1, 4, 1, 2), listOf(1, 3, 1, 2, 1), listOf(4, 1, 2, 2, 1), listOf(2, 1, 4, 2, 1, 4), listOf(1, 1, 1, 2, 4, 2), listOf(3, 3, 1, 1, 1), listOf(1, 1, 1, 2, 1, 1, 1, 1), listOf(1, 1, 2, 6, 2), listOf(2, 1, 2, 2), listOf(1, 1, 5, 5), listOf(4, 6, 1, 2), listOf(2, 1, 2, 1, 1, 2, 2), listOf(3, 1, 1, 5, 3, 2), listOf(1, 1, 1, 2, 1, 3), listOf(1, 1, 2, 2, 1, 3), listOf(4, 6, 5, 2)),
                gridStr = "1010101111111010110111010111010011111001000100101110001111110011101000000010000111010001011101001010011010000100000100011100100111110110110111111101001101100011111011010000011110110100011110111111100101010000100101101101011110010001001111101110011110110000101110100010100100010101101001011101110010110101011010101101100100101011010001111001011010010000110001100000011110011111111110000001111100111111"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_7",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(3, 3, 3, 1, 2), listOf(2, 1, 2, 1, 1), listOf(1, 2, 3, 5, 2, 1), listOf(4, 2, 3, 5), listOf(1, 2, 5, 2, 1), listOf(3, 3, 2, 1, 1), listOf(2, 3, 1, 1, 2, 1), listOf(7, 7, 1), listOf(1, 1, 2, 2, 3, 2, 1), listOf(5, 2, 6), listOf(1, 1, 4, 7), listOf(5, 1, 1, 1, 2), listOf(5, 3, 1, 2, 1, 1), listOf(1, 2, 1, 2, 5), listOf(2, 2, 1, 3, 3), listOf(1, 1, 2, 1, 3), listOf(3, 1, 6, 1, 1, 1), listOf(4, 1, 5, 3), listOf(3, 1, 3, 3, 3), listOf(4, 1, 3, 1, 4)),
                colClues = listOf(listOf(5, 3, 1, 1, 1, 2), listOf(2, 1, 3, 2, 1, 2), listOf(1, 4, 2, 2, 6), listOf(5, 1, 3, 1, 1, 1), listOf(1, 4, 3, 3), listOf(1, 1, 4, 1, 1, 1), listOf(3, 4, 1, 2, 1, 1, 2), listOf(5, 2, 1, 5), listOf(3, 4, 2, 1, 2), listOf(1, 3, 1, 4, 1), listOf(3, 1, 3, 1, 3, 1), listOf(1, 2, 5, 2, 3), listOf(1, 4, 2, 1, 2, 3), listOf(1, 1, 1, 8, 3), listOf(2, 1, 2, 3), listOf(1, 1, 3, 1, 1), listOf(4, 1, 3, 5, 1), listOf(5, 4, 3, 3), listOf(1, 1, 1, 3, 6), listOf(3, 1, 1, 1, 2)),
                gridStr = "1110111000011100101111010011000000001001101101110011111011011111000110111011111010110011111011000100011100111001100101001100111001010000110111111110111111100010101011001101110011010000111110110111111010010001111011111110001111100010010101101111101110010110100101001100101101111100001100110100111011100010000101101000111011101011111101001010001111010111110001111110101110011100011111110010111010011110"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_8",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(2, 4, 4, 2, 1), listOf(5, 2, 3, 4), listOf(2, 4, 1, 2, 1, 1), listOf(1, 1, 1, 1, 4), listOf(1, 1, 1, 1, 2), listOf(2, 1, 1, 1, 3, 1, 1), listOf(4, 1, 3, 1, 2, 1), listOf(1, 6, 2, 3, 1), listOf(1, 3, 1, 2, 4, 2), listOf(10, 1, 6), listOf(3, 5, 1, 1), listOf(1, 9, 2, 1, 3), listOf(3, 6, 1, 1), listOf(1, 1, 3, 3, 1, 1), listOf(1, 1, 1, 1, 3, 4), listOf(2, 2, 1, 3, 4), listOf(1, 6, 1, 1, 4, 1), listOf(1, 1, 2, 4, 6), listOf(2, 1, 1, 9, 1), listOf(3, 2, 3, 2, 1, 1)),
                colClues = listOf(listOf(1, 7, 1, 5), listOf(3, 2, 1, 3, 2), listOf(4, 2, 1, 1, 1, 1), listOf(2, 4, 2, 2, 1), listOf(2, 1, 3, 2, 4, 1), listOf(2, 4, 3, 1, 1), listOf(1, 1, 2, 1, 3, 5), listOf(3, 8, 2), listOf(3, 2, 5, 1, 1), listOf(1, 1, 1, 4, 9), listOf(1, 2, 3, 1, 3), listOf(3, 2, 3, 3), listOf(1, 1, 1, 6, 3), listOf(3, 2, 5, 3, 2), listOf(1, 4, 4, 1, 4), listOf(5, 1, 1, 5), listOf(3, 1, 2, 1, 1, 3), listOf(1, 1, 1, 3, 1, 1, 3), listOf(4, 4, 2, 1), listOf(1, 1, 2, 1, 1, 4)),
                gridStr = "1101111011110110001001111101100111001111011000111101011010101010000100000010111110100010010001100000110010101000011101011111010111001001100110111111011001110100100111010110111101101111111111010111111000000011101111101010101111111110110101110001110111111010100001000101110111010010010010100101110011111101101011101111000010111111010100111101100010110111101111111101001001111111110111101100111011010001"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_9",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 1, 3, 1, 1, 1), listOf(3, 2, 8, 1), listOf(2, 2, 4, 1, 2, 2), listOf(4, 1, 1, 1, 2, 2), listOf(3, 2, 1, 2, 1, 1), listOf(3, 5, 2, 1, 1), listOf(2, 2, 1, 6, 2), listOf(3, 1, 2, 3, 1), listOf(10, 1, 1, 1), listOf(1, 1, 1, 1, 1, 2, 1, 1), listOf(1, 2, 2, 1, 3), listOf(1, 8, 3, 1), listOf(2, 1, 4, 2, 4), listOf(3, 4, 3, 2, 1), listOf(6, 1, 1, 1), listOf(5, 3, 2, 3, 1), listOf(1, 3, 1, 1, 1, 2), listOf(5, 1, 3, 2, 3), listOf(2, 5, 1), listOf(3, 2, 6, 1, 2)),
                colClues = listOf(listOf(3, 3, 1, 3, 1), listOf(8, 1, 6, 1), listOf(2, 3, 3, 3, 1, 1), listOf(1, 2, 1, 1, 2, 3), listOf(1, 2, 1, 3, 2, 2), listOf(2, 1, 3, 4, 2, 1), listOf(2, 1, 2, 4, 1, 1), listOf(1, 4, 1, 3, 2), listOf(1, 1, 1, 2, 3, 1, 2), listOf(1, 2, 7, 1, 3), listOf(2, 2, 2, 1, 5), listOf(4, 1, 1, 1, 1, 3), listOf(1, 3, 5, 2), listOf(8, 1, 1, 3, 1), listOf(1, 2, 5, 2, 1), listOf(2, 1, 3, 1, 1), listOf(2, 1, 5, 3), listOf(1, 3, 1, 1), listOf(1, 2, 1, 1, 1, 4), listOf(9, 1, 4, 1)),
                gridStr = "1010100111010100001011100110001111111101110001101111010110110111100101010110001111101101000001101001111000111110110100011100110101111110001101110100110011100001111111111100001010010010101001010110100101011000011000101110100001111111100111011101011110001101111011100111101110110001011111100000101000011111100011101101110101000111001001001011011111010111011011100001100011111000001001110110111111010011"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_10",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(2, 2, 1, 1, 1, 1), listOf(1, 5, 9), listOf(2, 3, 1, 2, 3), listOf(13, 1, 1), listOf(1, 4, 1, 1, 5), listOf(2, 3, 2, 1, 1, 2), listOf(2, 2, 1, 3, 2, 1, 2), listOf(3, 8, 4), listOf(1, 1, 1, 1, 3, 1), listOf(1, 1, 1, 2, 1, 1, 3), listOf(2, 1, 2, 6, 1, 1), listOf(2, 1, 11, 1), listOf(1, 1, 2, 4, 6), listOf(5, 5, 2, 1), listOf(2, 1, 4, 2, 2, 1), listOf(1, 2, 1, 4, 1), listOf(2, 1, 1, 4, 4), listOf(1, 3, 4, 3, 3), listOf(1, 3, 2, 3, 1, 1, 1), listOf(1, 3, 5)),
                colClues = listOf(listOf(3, 7, 2, 1, 1), listOf(1, 2, 3, 2, 1, 3), listOf(1, 2, 1, 1, 4, 1), listOf(1, 4, 2, 2), listOf(13, 1, 2), listOf(6, 1, 1, 4), listOf(4, 2, 1, 1), listOf(1, 1, 1, 10), listOf(1, 2, 3, 6, 1), listOf(2, 4, 8), listOf(1, 1, 3, 10), listOf(2, 2, 2, 2, 2, 2), listOf(3, 4, 3, 1), listOf(4, 1, 1, 5, 2), listOf(1, 1, 8, 1, 1), listOf(1, 3, 2, 3, 4), listOf(2, 1, 2, 1, 2, 3, 1), listOf(2, 2, 1, 6, 2, 1), listOf(7, 2, 2, 1), listOf(1, 2, 1, 2)),
                gridStr = "1100011010010100001010111110001111111110110011101000110011100111111111111101001010111100010100111110110111011010000101101101101011100110101111101111111100111100100010000101011100101010100011001010111011001001101111110101011010011111111111011010110111101111110011111011111001100100001101011110110011010100110101111000100011000101011110011110010111011110011101111011101101110101000100000001001110111110"
            ),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedNonogram>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
