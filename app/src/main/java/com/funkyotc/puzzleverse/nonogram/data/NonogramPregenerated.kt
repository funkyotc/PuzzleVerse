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
                rowClues = listOf(listOf(1, 2, 1, 1), listOf(4, 1), listOf(3, 1, 2), listOf(3, 3, 1), listOf(1, 1, 3), listOf(1, 2, 1, 1), listOf(2, 1, 2), listOf(4, 1, 1, 1), listOf(3, 1, 1), listOf(3, 2, 1)),
                colClues = listOf(listOf(4, 1, 1), listOf(3, 2, 1), listOf(10), listOf(2, 1, 3), listOf(2, 1), listOf(4, 1), listOf(1, 2, 1), listOf(1, 1, 3), listOf(1, 3, 1), listOf(6)),
                gridStr = "1011010010111101000011100101101110111010001010011110110010010110001011111101010100111001010111001101"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_2",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 6), listOf(2, 2, 3), listOf(4, 4), listOf(3, 1, 1), listOf(2, 6), listOf(1, 1, 2, 1), listOf(1, 1, 1), listOf(1, 1, 2, 1), listOf(1, 1, 1), listOf(2, 1, 3)),
                colClues = listOf(listOf(2, 1), listOf(5, 2), listOf(1, 3, 1), listOf(3, 2), listOf(2, 2, 2), listOf(1, 1, 1), listOf(1, 1, 2, 1, 1), listOf(10), listOf(3, 1, 1), listOf(6, 1)),
                gridStr = "0010111111110110011111110011110111000101011011111101001011010001010100100100110101001001000110101110"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_3",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 2), listOf(1, 3, 1, 1), listOf(1, 2, 1, 2), listOf(1, 1, 1, 1, 1), listOf(2, 1, 3), listOf(4, 3), listOf(1, 2, 2), listOf(1, 2), listOf(1, 1, 2), listOf(2, 2)),
                colClues = listOf(listOf(1, 1, 1), listOf(1, 2), listOf(2, 7), listOf(2, 2, 1), listOf(3, 1), listOf(1, 1), listOf(4, 1, 2), listOf(3, 1), listOf(1, 2, 2), listOf(5, 2)),
                gridStr = "0010011000101110100101011010111010101001011001011101111001111011001100001000001100100010110011001100"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_4",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 6), listOf(1, 2), listOf(1, 1, 4), listOf(2, 2, 1), listOf(1, 1, 1, 2), listOf(1, 1), listOf(1, 2, 3), listOf(1, 1), listOf(4, 2), listOf(2, 4)),
                colClues = listOf(listOf(1, 2, 1), listOf(3, 2), listOf(2), listOf(3, 1, 1), listOf(2, 1, 3), listOf(1, 1, 1), listOf(1, 1, 1, 1, 1), listOf(3, 1, 1, 1), listOf(5, 1, 2), listOf(1, 1, 2)),
                gridStr = "1000111111000010011010010111101101100010010101011001000010001001100111000010100001111000110110001111"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_5",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 1, 3), listOf(1, 5), listOf(1, 2, 1, 1), listOf(2, 2, 1), listOf(1, 4, 3), listOf(1, 3, 2, 1), listOf(1, 3, 2), listOf(1, 1, 1, 1), listOf(1, 1, 1), listOf(1, 1)),
                colClues = listOf(listOf(1, 1, 4), listOf(1, 2), listOf(5), listOf(1, 7), listOf(1, 3), listOf(1, 1), listOf(3, 1, 3), listOf(2, 4), listOf(3, 1, 1), listOf(1, 3, 1)),
                gridStr = "1001000111010011111010110010100011001101101111011110111011011011100110100100100101010010000100001000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_6",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 3), listOf(1, 3, 2), listOf(3, 1, 2), listOf(1, 1, 1, 2), listOf(2, 2), listOf(1, 1, 2), listOf(7, 2), listOf(1, 3), listOf(1, 3), listOf(2, 2, 3)),
                colClues = listOf(listOf(1, 1, 1), listOf(1, 1, 2, 2), listOf(3, 1), listOf(3, 3, 2), listOf(2, 1, 4), listOf(3, 1, 1), listOf(1, 1, 1), listOf(1, 2, 1, 1), listOf(7, 1), listOf(2, 2)),
                gridStr = "0101110000100111011001110100110010101011001100011001010001101111111011000010011101011100001101101110"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_7",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(9), listOf(1, 1, 1, 1), listOf(2, 3, 2), listOf(2, 1, 2), listOf(3, 3, 2), listOf(2, 1), listOf(2, 1, 2), listOf(1, 2, 3), listOf(1, 1, 2, 2), listOf(5, 1)),
                colClues = listOf(listOf(2, 1, 1, 1), listOf(1, 3, 2), listOf(1, 2, 1), listOf(3, 3, 1), listOf(1, 1, 2, 1, 1), listOf(1, 3, 2), listOf(2, 1, 3), listOf(1, 3, 1), listOf(1, 3, 3), listOf(5, 2)),
                gridStr = "0111111111100100100111011100110110010011111011101100011001001101000110010110111010100110110001111101"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_8",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(3, 4), listOf(2, 1), listOf(1, 2, 1, 1), listOf(2, 1, 1, 1), listOf(1, 1, 1), listOf(4, 2), listOf(1, 2, 3), listOf(1, 2, 1), listOf(1, 1, 1, 1), listOf(2, 3, 2)),
                colClues = listOf(listOf(3, 1, 1), listOf(1, 1, 1), listOf(1, 1, 2), listOf(1, 1, 1), listOf(1, 5), listOf(5, 1), listOf(2, 1, 2), listOf(5, 2), listOf(1, 2, 1), listOf(4, 2, 1)),
                gridStr = "0011101111000000110110110001011100010101100001010000011110111000110111010011010010101010000110111011"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_9",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 3, 4), listOf(2, 3), listOf(1, 3), listOf(1, 1, 1), listOf(1, 1, 2), listOf(1, 4), listOf(2, 2, 1, 2), listOf(3, 1, 2), listOf(1, 1, 3), listOf(1, 2)),
                colClues = listOf(listOf(1, 5), listOf(2, 1), listOf(3, 2), listOf(2, 1, 1), listOf(1, 1, 1), listOf(1, 1), listOf(1, 7), listOf(2, 1, 1), listOf(2, 6), listOf(2, 2, 2, 1)),
                gridStr = "1011101111001100011100101110000001001001100000101110000111101101101011111000101110100011100100000011"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_10",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(4, 1, 1), listOf(1, 4, 1), listOf(1, 3, 1), listOf(2, 1), listOf(1, 1, 2, 1), listOf(1, 1, 1), listOf(1, 1, 1, 1), listOf(4, 3, 1), listOf(1, 4), listOf(1, 4)),
                colClues = listOf(listOf(1, 1, 1, 1, 1), listOf(2, 1, 1), listOf(1, 1, 1, 3), listOf(1, 2, 1, 1), listOf(4, 1), listOf(1, 2, 3), listOf(2, 4), listOf(1, 1, 3), listOf(1, 2, 2), listOf(1, 2)),
                gridStr = "1111001010010011110110111000100001100010101011010001010100000010101001111101110100100111101000011110"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_11",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(2, 1, 2, 1), listOf(2, 3, 1), listOf(1, 1, 1, 1), listOf(1, 3, 3), listOf(1, 4), listOf(3, 1, 1), listOf(1, 1, 3), listOf(2, 5), listOf(2, 1), listOf(1, 5)),
                colClues = listOf(listOf(4, 2, 1), listOf(2, 1), listOf(1, 3, 1), listOf(1, 3, 1, 1), listOf(1, 1, 2), listOf(2, 6), listOf(5, 2, 1), listOf(1, 3, 2), listOf(1, 3, 1), listOf(1, 1)),
                gridStr = "1101011010110001110110010010101011101110000101111011100101001010111000001101111100001101001011111000"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_12",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 6), listOf(3, 2, 1), listOf(1, 1, 2), listOf(1, 2), listOf(1, 1, 5), listOf(3, 2, 1), listOf(3, 1, 1), listOf(1, 2, 3), listOf(1, 2), listOf(1, 1, 2, 1)),
                colClues = listOf(listOf(2, 2, 1, 1), listOf(1, 1, 2), listOf(2, 2, 2), listOf(1, 1, 1), listOf(1, 1, 1), listOf(2, 5, 1), listOf(2, 3), listOf(1, 1, 1, 2), listOf(1, 1, 6), listOf(2, 1, 1)),
                gridStr = "0010111111111001100110010001100100011000100101111111100110100111010010100011011100100001101010110010"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_13",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 1, 1, 2), listOf(1, 2, 3), listOf(1, 2, 1), listOf(5, 1, 1), listOf(1, 1, 1, 1), listOf(2, 1), listOf(1, 1, 5), listOf(2, 1, 3), listOf(1, 1, 1, 1), listOf(1, 2, 4)),
                colClues = listOf(listOf(1, 1, 4), listOf(1, 3, 1), listOf(1, 2, 2, 1), listOf(1, 2, 1), listOf(4, 2), listOf(1, 2), listOf(1, 2, 1), listOf(1, 1, 4), listOf(2, 1, 2, 1), listOf(3, 1, 2, 1)),
                gridStr = "1010100011010110011100101100011111101010010100010101100010001010011111110001011110101001001001101111"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_14",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 2, 2), listOf(4, 1, 1), listOf(7), listOf(1, 2, 2), listOf(2, 1, 1), listOf(1, 2, 2), listOf(1, 1, 4), listOf(2, 1, 3), listOf(2, 2, 2), listOf(1, 2, 1, 1)),
                colClues = listOf(listOf(1, 3, 2), listOf(1, 1, 1, 1), listOf(3, 1, 1), listOf(3, 5), listOf(3, 1, 1), listOf(3, 2), listOf(2, 1, 1), listOf(1, 1, 4), listOf(4, 3), listOf(3, 1, 1)),
                gridStr = "1011000110011110101000111111101000110011110001000110011000110101011110001101011111011011001011000101"
            ),
            PregeneratedNonogram(
                id = "nonogram_easy_15",
                difficulty = "Easy",
                size = 10,
                rowClues = listOf(listOf(1, 1, 2, 1), listOf(1, 2, 2), listOf(2, 3), listOf(1, 1), listOf(1, 1, 1, 1, 1), listOf(1, 1, 1, 1), listOf(1, 4), listOf(2, 2, 1), listOf(6), listOf(1, 3, 1)),
                colClues = listOf(listOf(1, 1), listOf(1, 1, 1, 1), listOf(1, 2), listOf(1, 3, 1), listOf(1, 3), listOf(1, 2, 2), listOf(2, 1, 3), listOf(1, 1, 4), listOf(3, 1, 1), listOf(3, 2, 2)),
                gridStr = "0101001101001001101111000001110010000010101010100101010100010001011110000110110100001111110101110100"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_1",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(3, 2, 2), listOf(3, 3, 2, 1), listOf(2, 1, 1, 1, 3), listOf(1, 1, 3, 3), listOf(4, 2, 2), listOf(4, 1, 2), listOf(2, 3, 1, 3), listOf(2, 1, 3, 1, 1, 1), listOf(1, 1, 3, 1), listOf(1, 2, 4, 1, 1), listOf(1, 1, 2, 2, 2), listOf(2, 8, 2), listOf(8, 3), listOf(1, 2, 7), listOf(2, 3, 1, 3)),
                colClues = listOf(listOf(5, 4, 1), listOf(1, 1, 5, 2, 1), listOf(1, 4, 2, 2), listOf(2, 2, 3, 1), listOf(3, 4), listOf(1, 2, 1, 5), listOf(2, 2, 4, 1), listOf(1, 5, 3), listOf(3, 1, 1, 4, 2), listOf(2, 1, 6), listOf(1, 3, 3), listOf(1, 1, 4), listOf(3, 5, 2), listOf(1, 2, 1, 2, 2), listOf(4, 2, 1, 1)),
                gridStr = "011100001100011000111011101101011010001010111101011100000111111101101100000111100010011000110000111010111110101110010101010100011100100101100111100101101001101100110110011111111011111111110111000001011011111110110011101001110"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_2",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(4, 2, 3), listOf(7, 2), listOf(2, 4, 2, 2), listOf(5, 1, 1), listOf(3, 5, 4), listOf(1, 1, 3), listOf(1, 6, 1), listOf(3, 1, 1, 2), listOf(2, 4, 1, 1), listOf(3, 1, 2), listOf(2, 1, 1, 2, 3), listOf(1, 4, 6), listOf(1, 5), listOf(2, 1, 2, 5), listOf(3, 1, 3, 1)),
                colClues = listOf(listOf(1, 1, 2, 2, 2), listOf(3, 1, 2, 1, 1), listOf(2, 1, 1, 1, 1), listOf(2, 3, 1), listOf(2, 2, 1, 4), listOf(6, 3, 1), listOf(5, 1, 1), listOf(4, 4, 2), listOf(2, 1, 1, 2), listOf(2, 1, 1, 3, 1), listOf(2, 3, 4), listOf(3, 1, 2, 4), listOf(1, 3, 1, 4), listOf(1, 1, 2, 5), listOf(5, 3, 2)),
                gridStr = "111101100001110011111110011000110011110011011000001111100101111001111101111100001000000111000010111111001111010010011000110000011110101000111010000011110101001100111101111001111110000010000111110011010110011111000111010111001"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_3",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(2, 1, 2, 2, 3), listOf(2, 2, 2), listOf(4, 1, 1, 2), listOf(4, 6, 1), listOf(1, 2, 4), listOf(2, 1, 1, 1, 1), listOf(2, 2, 1, 2, 1), listOf(4, 4, 4), listOf(4, 3, 1), listOf(1, 2, 3, 2), listOf(2, 2, 2), listOf(2, 1, 2, 2, 2, 1), listOf(3, 3, 1, 3), listOf(1, 1, 2, 4, 1), listOf(3, 2, 1)),
                colClues = listOf(listOf(1, 1, 2, 2, 1), listOf(9, 4), listOf(3, 1, 3, 1, 1), listOf(1, 3, 3, 2, 1), listOf(2, 1, 1, 1, 1, 1), listOf(1, 1, 1, 1, 1, 3), listOf(1, 2, 2, 6), listOf(1, 1, 1, 1, 1), listOf(4, 1, 1, 1), listOf(2, 2, 2, 3), listOf(2, 2, 2, 1), listOf(1, 5, 2, 1), listOf(1, 1, 2, 4), listOf(1, 1, 1, 1, 1, 1), listOf(1, 4, 4)),
                gridStr = "110100110110111011011000110000011110101001100111101111110010010110001111000011001001001010110110100001101111101111001111011110000111001001001101110011000110110001100110101101101101111011100100111010101100111101111000110000001"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_4",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 2, 2, 2, 1), listOf(1, 2, 3, 5), listOf(1, 2, 1, 1, 3), listOf(2, 4, 1), listOf(1, 3, 1, 1, 2), listOf(1, 1, 1, 2, 1, 2), listOf(1, 1, 2, 3), listOf(10, 2, 1), listOf(3, 2, 1, 1, 2), listOf(1, 1, 1, 2, 3), listOf(4, 5), listOf(1, 2, 4), listOf(1, 1, 1, 4), listOf(1, 3, 1, 2, 2), listOf(2, 2, 3)),
                colClues = listOf(listOf(3, 3, 1, 1), listOf(2, 3, 1, 1), listOf(1, 1, 2, 2), listOf(8, 2, 1), listOf(2, 1, 2, 4), listOf(2, 5, 1), listOf(3, 2, 1, 2), listOf(2, 6, 2), listOf(3, 1, 1, 1, 1, 1), listOf(1, 1, 1, 3), listOf(2, 1, 1, 3), listOf(1, 2, 5, 1), listOf(2, 2, 2, 1, 1), listOf(3, 3, 3, 2), listOf(5, 2, 1, 2)),
                gridStr = "101100110110010100110111011111100110101000111001100001111001010111010001011010101011010011100100110001110111111111101101111011010001011010101011001110000111100011111100011011110000010010010111100101110101100011011001100000111"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_5",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 1, 3, 3), listOf(3, 4, 1), listOf(1, 1, 1, 1, 4, 1), listOf(1, 1, 1, 4), listOf(1, 1, 2, 1, 1, 1), listOf(1, 6, 3, 2), listOf(3, 1, 4, 1, 1), listOf(1, 2, 1, 4), listOf(3, 5), listOf(4, 1, 1), listOf(1, 1, 2, 3, 1), listOf(1, 1, 1, 1, 5), listOf(1, 1, 1), listOf(1, 2, 3), listOf(1, 6, 2)),
                colClues = listOf(listOf(1, 4, 1), listOf(3, 1, 1, 3), listOf(1, 1, 2, 1, 1), listOf(1, 2, 1, 1, 1), listOf(1, 5, 1, 1), listOf(1, 2, 1, 2, 1), listOf(1, 4, 4, 2), listOf(1, 1, 5, 2), listOf(2, 2, 1, 1, 1, 1), listOf(2, 2, 1, 2), listOf(6, 1, 2), listOf(4, 1, 1, 1, 1, 2), listOf(1, 2, 2, 4), listOf(1, 1, 1, 1, 2, 1), listOf(1, 4, 1, 1)),
                gridStr = "010010111011100111000001111010010101010111101001000101011110100101101010001101111110111011111010111100101100011010001111001110111110000000011110001001010101101110010101010100111110010000000000101010000110001110010111111001100"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_6",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(3, 1, 1, 1), listOf(2, 2, 6, 1), listOf(1, 1, 2, 1), listOf(1, 5, 4, 1), listOf(1, 1, 1, 1), listOf(3, 2, 1, 2, 1), listOf(7, 1), listOf(1, 1), listOf(3, 2, 1, 1), listOf(1, 1, 2, 2, 3), listOf(2, 4, 2), listOf(4, 1, 7), listOf(1, 4, 3, 1), listOf(1, 1, 4), listOf(2, 5, 1, 1)),
                colClues = listOf(listOf(1, 1, 1, 1, 1), listOf(2, 5, 2, 1), listOf(1, 1, 2, 1, 1, 1), listOf(1, 2, 1, 4), listOf(1, 1, 1, 2, 1, 1), listOf(2, 2, 1, 1, 1), listOf(2, 2, 1, 2, 1), listOf(1, 1, 2, 3, 1), listOf(1, 1, 2, 2), listOf(3, 1, 3), listOf(4, 1, 4, 1), listOf(1, 3, 1, 3), listOf(2, 1, 2, 5), listOf(1, 4, 2), listOf(1, 3, 1, 3)),
                gridStr = "011101000010100110011011111101001000100110010100111110111101000101000001001111000110101101011111110000100010000000010000111000011001010010010110110111000110011110110111100101111111010111100011101000100001001111011011111010010"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_7",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(4, 1, 1), listOf(1, 2, 2, 1, 2), listOf(1, 2, 2), listOf(1, 2, 2, 1), listOf(2, 1, 4, 3, 1), listOf(1, 3), listOf(2, 1, 2, 1), listOf(4, 1), listOf(1, 4, 2, 1), listOf(1, 1, 1, 3, 3), listOf(1, 2, 1, 1), listOf(1, 2, 1, 1, 1), listOf(1, 1, 3, 1), listOf(2, 1, 8), listOf(1, 1, 1)),
                colClues = listOf(listOf(5, 2, 1, 1), listOf(1, 1, 4, 1), listOf(2, 1, 1, 2), listOf(11, 3), listOf(1, 1, 1), listOf(1, 1, 3), listOf(1, 1, 2), listOf(2, 2, 1), listOf(2, 1, 1), listOf(1, 1, 2, 4), listOf(2, 1, 1, 2, 2), listOf(2, 2, 4), listOf(1, 2, 2, 1), listOf(1, 2, 1, 3), listOf(2, 1, 2)),
                gridStr = "111100010100000101100110010110100110000110000101100011001000110101111011101000100000000111110100000011010111100000001000010111100110100010100101110111101101000001000001011000101010000101000111010110100011111111000100000100001"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_8",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(3, 2, 1, 1), listOf(2, 4, 4), listOf(1, 4, 2, 2), listOf(1, 2, 1), listOf(3, 1, 2), listOf(1, 1, 1), listOf(1, 1, 1, 2), listOf(5, 1, 2, 3), listOf(3, 2, 1, 5), listOf(1, 2, 2, 1, 4), listOf(3, 1, 1, 3), listOf(3, 2, 3), listOf(2, 1, 2, 2, 3), listOf(1, 1, 3, 1, 3), listOf(3, 1, 1, 1, 1)),
                colClues = listOf(listOf(1, 4, 2), listOf(3, 1, 2, 3, 1), listOf(1, 10, 1), listOf(1, 1, 1, 1, 1, 1), listOf(1, 3, 1), listOf(6, 2, 1), listOf(3, 1, 2, 2), listOf(2, 3), listOf(5, 2), listOf(2, 1, 6), listOf(2, 1, 1, 2), listOf(2, 4, 1), listOf(2, 8), listOf(2, 9), listOf(1, 2, 3)),
                gridStr = "011101101000100110001111011110010001111011011001011001000000011101001100000001001000100000001010000010110111110101101110111011001011111101101100101111111000100101110011100000110111011010110110111100101110100111111000010101010"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_9",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 1, 3, 1, 1), listOf(1, 2, 1, 2), listOf(1, 2, 7), listOf(3, 3, 6), listOf(2, 1, 1), listOf(1, 2, 3), listOf(1, 2, 1, 1, 5), listOf(3, 2, 2, 1), listOf(3, 4, 3), listOf(1, 4, 1, 1, 1, 1), listOf(3, 1, 5), listOf(3, 1, 1, 2, 3), listOf(5, 3, 2), listOf(4, 1, 1, 3), listOf(3, 3)),
                colClues = listOf(listOf(1, 1, 1, 1, 1), listOf(4, 3, 2, 1), listOf(1, 3, 7), listOf(1, 3, 2), listOf(1, 3, 1, 1, 2), listOf(2, 1, 1, 1), listOf(1, 1, 4, 3), listOf(3, 1, 4, 1), listOf(5, 1, 1, 1, 1), listOf(2, 3, 2), listOf(3, 2, 4), listOf(1, 2, 1, 3), listOf(2, 2, 1, 5), listOf(3, 2, 1, 5), listOf(2, 3, 3, 2)),
                gridStr = "001010111001001010000011010011010011011111110111011101111110011010001000000001000110000111010110101011111111001100110001011100111101110101111010101001011100010011111111010010110111001111101110110111100100010111000000111000111"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_10",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 1, 2, 1, 2), listOf(1, 2, 2, 1, 1), listOf(5, 1, 1), listOf(3, 2, 1, 3), listOf(4, 4, 1), listOf(1, 2, 2), listOf(6, 2, 1), listOf(1, 2, 5, 1, 1), listOf(1, 3, 1, 1, 1), listOf(6, 1, 1, 1, 1), listOf(2, 4, 3), listOf(1, 1, 3, 1, 3), listOf(1, 2, 3, 2), listOf(1, 2, 1, 2, 4), listOf(2, 4, 1, 1)),
                colClues = listOf(listOf(1, 1, 1, 1, 1, 2), listOf(3, 1, 2, 2, 1), listOf(3, 2, 2, 1), listOf(3, 1, 6, 1), listOf(6, 2, 1), listOf(1, 8, 3), listOf(1, 2, 2, 1), listOf(1, 1, 1, 3, 1), listOf(4, 5, 3), listOf(1, 1, 2, 1, 3), listOf(1, 3, 2, 1), listOf(1, 1, 3, 1, 1), listOf(1, 2, 3, 2), listOf(1, 1, 1, 4), listOf(2, 4)),
                gridStr = "100101100101100010110011010010011111001000100111011001011100001111011110010100011000011000011111101101000101101111101010010111001010001111111001010101001100111101110010101110010111010011011100011101101001101111110000111100101"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_11",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(2, 1, 2, 1, 1), listOf(2, 1, 2, 1), listOf(1, 1, 2, 1), listOf(1, 2, 1, 1), listOf(5, 1, 1, 1, 1), listOf(2, 1, 1, 1, 2), listOf(1, 3, 5), listOf(5, 1, 1, 1), listOf(1, 2, 1, 3, 2), listOf(2, 3, 5), listOf(6, 2), listOf(1, 1, 3, 3), listOf(1, 2, 4, 5), listOf(2, 1, 2, 2), listOf(1, 4, 5)),
                colClues = listOf(listOf(1, 1, 1, 1, 1), listOf(1, 2, 1, 1, 1), listOf(1, 1, 7), listOf(1, 2, 2, 1, 2), listOf(1, 4, 2), listOf(1, 2, 2, 3), listOf(8, 4, 1), listOf(1, 1, 1, 1, 1), listOf(4), listOf(1, 1, 2, 2, 1, 1), listOf(1, 4, 2, 1), listOf(2, 2, 2, 1, 1), listOf(2, 1, 2, 1, 4), listOf(1, 1, 2, 7), listOf(2, 2, 2, 3, 1)),
                gridStr = "110010110100010001100100001101100000100001101010001100100010111110100010101000110100101011010011100111110001111100010101101100010111011011011100111110001111110000011001000101110111101101111011111001101001100110010001111011111"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_12",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 4, 2), listOf(1, 1, 2, 3), listOf(3, 1, 1, 4), listOf(3, 3, 1, 1), listOf(1, 4), listOf(1, 3, 2, 2), listOf(1, 1, 4, 1, 1), listOf(3, 1, 3, 1, 1), listOf(2, 2, 1, 5), listOf(1, 1, 10), listOf(2, 1, 2, 1), listOf(4, 1, 1, 1), listOf(1, 2, 1, 2, 3), listOf(5, 1), listOf(1, 4, 3)),
                colClues = listOf(listOf(4, 2, 1), listOf(1, 2, 4, 1, 1), listOf(2, 1, 2, 1), listOf(3, 1), listOf(3, 5, 1), listOf(1, 3, 1, 1, 2, 1), listOf(7, 1, 1), listOf(2, 7, 2, 1), listOf(1, 1, 2, 1, 2), listOf(2, 5, 4), listOf(2, 1, 3, 2), listOf(3, 1, 4, 1), listOf(1, 1, 3, 2), listOf(2, 2, 2), listOf(1, 1, 1, 1, 2)),
                gridStr = "010011110001100100010110111000111010101111000111001110000101100001111000000010001110110011010100111101010011101011100101110110010111110101011111111110011010000011001000011110101010010011010110111000000001111101101111011100000"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_13",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(5, 3, 1, 1), listOf(1, 1, 1, 1, 1), listOf(10, 2, 1), listOf(1, 1, 3), listOf(2, 1, 5, 1), listOf(3, 4, 2, 1), listOf(5, 4, 2), listOf(1, 5, 1, 4), listOf(2, 2, 4, 1), listOf(3, 1, 3, 1), listOf(1, 3, 7), listOf(1, 1, 4, 2, 1), listOf(2, 4, 1, 3), listOf(1, 1, 1, 2, 1), listOf(4, 1, 5)),
                colClues = listOf(listOf(1, 4, 3, 1), listOf(7, 2, 3), listOf(1, 1, 5, 2, 1), listOf(3, 2, 1, 2), listOf(1, 1, 3, 2), listOf(1, 3, 2, 3), listOf(1, 1, 1, 2, 1), listOf(2, 3, 3), listOf(1, 5, 2, 3), listOf(9, 2, 1), listOf(1, 2, 1, 7), listOf(1, 2, 4, 2), listOf(3, 6, 1, 1), listOf(2, 1, 2), listOf(1, 1, 2, 3, 2)),
                gridStr = "011111001110101010100010100100111111111101101010001001110000110001011111001111000111101101111110011110110101111100101111011011000111101111000001011101100111011111110101011110110001011001111010111010100001011010111100101111100"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_14",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 1, 4), listOf(4, 1, 4), listOf(2, 2, 4), listOf(2, 1, 1, 1), listOf(1, 2, 1, 1, 1), listOf(4, 2, 2, 1, 1), listOf(1, 3, 2, 1, 3), listOf(1, 1, 9), listOf(1, 4, 2, 2), listOf(1, 1, 1, 1), listOf(1, 2, 1), listOf(1, 1, 3, 1, 1), listOf(1, 3, 3, 1, 3), listOf(3, 1, 2, 2, 2), listOf(2, 4, 2)),
                colClues = listOf(listOf(2, 1, 2, 4), listOf(3, 1, 1, 1), listOf(1, 1, 3, 5), listOf(3, 2, 1, 1, 1), listOf(1, 1, 3, 2), listOf(1, 1, 2, 1), listOf(6, 4), listOf(1, 5, 4), listOf(1, 2, 3, 1, 1), listOf(3, 2, 1, 2), listOf(3, 4, 2), listOf(2, 1, 2, 1), listOf(1, 4, 1, 2, 1), listOf(1, 1, 2), listOf(3, 6)),
                gridStr = "010010001111000111101000111100110110011110000001101001001000100000110100101011110110110101101110110010111101011111111100010001111011011000000101000101101100000000001101001110000101101110111010111111010110110011001100111101100"
            ),
            PregeneratedNonogram(
                id = "nonogram_medium_15",
                difficulty = "Medium",
                size = 15,
                rowClues = listOf(listOf(1, 2, 1, 2, 1), listOf(4, 1, 5), listOf(1, 3, 2, 1, 1, 1), listOf(1, 1, 1, 2, 4), listOf(2, 1, 2, 5), listOf(1, 2, 2), listOf(1, 2), listOf(2, 2, 2), listOf(4, 3, 2, 1, 1), listOf(1, 2, 1, 2), listOf(2, 2, 1, 1), listOf(2, 1, 1, 2), listOf(6, 5), listOf(5, 1, 1, 1), listOf(3, 1, 1, 2)),
                colClues = listOf(listOf(1, 2, 2), listOf(2, 3, 6), listOf(2, 1, 1, 1, 1), listOf(4, 1, 2, 2, 1), listOf(3, 2, 1, 3), listOf(1, 3, 4), listOf(2, 4, 1), listOf(6, 1, 1, 2), listOf(2, 2, 2), listOf(1, 2, 1, 1, 1), listOf(2, 1, 1, 2), listOf(2, 2, 1, 1), listOf(5, 1, 3), listOf(1, 2, 1, 1, 1), listOf(5, 2, 1, 1)),
                gridStr = "010110000101101011110010011111101110011010101010101011001111011010110011111010110110000000000001011000000110001101100000111101110110101010110100000011011000110101000110101000000011111111001111100000011111010101000111010100110"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_1",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(4, 1, 2, 1, 1), listOf(2, 1, 2, 1, 2), listOf(1, 1, 3, 2, 2), listOf(1, 1, 1, 1, 2, 3), listOf(1, 2, 3, 1, 1, 2), listOf(1, 2, 2, 1, 5, 2), listOf(1, 1, 1, 1, 2, 1), listOf(1, 1, 2, 1, 1), listOf(4, 7, 3), listOf(1, 1, 1, 1, 4, 2, 2), listOf(2, 1, 4, 2, 1, 1), listOf(3, 1, 1, 2, 2, 2), listOf(6, 5), listOf(1, 3, 1, 1, 1, 1), listOf(1, 2, 7, 2, 1), listOf(1, 1, 1, 1, 3, 1, 1), listOf(3, 2, 2, 3, 1), listOf(1, 1, 1, 4, 2, 2), listOf(1, 3, 1, 4, 1), listOf(6, 6, 3)),
                colClues = listOf(listOf(1, 2, 4), listOf(2, 1, 2, 3, 3), listOf(1, 1, 1, 1), listOf(2, 1, 1, 5, 1), listOf(1, 4, 1, 1, 2, 1, 2), listOf(1, 1, 1, 1, 1, 3), listOf(1, 4, 5, 1, 2), listOf(1, 3, 1, 1, 3), listOf(1, 2, 3, 1, 1, 4), listOf(1, 1, 2, 1, 1, 1, 2, 1), listOf(2, 3, 1, 2, 1, 1), listOf(1, 1, 5, 1, 3), listOf(2, 4, 1, 6, 2), listOf(3, 1, 1, 2, 2), listOf(2, 4, 2, 2, 2), listOf(2, 9, 1, 2), listOf(2, 1, 2, 2, 2), listOf(3, 1, 1, 1, 2), listOf(2, 4, 5, 1, 1), listOf(2, 1, 1, 1, 4, 1)),
                gridStr = "0000111101011001001011000000100011010110010000100111011011000000101010100110111010011011100010010011100110110100111110110000100101001011001000000010000010110101111101111111001110001000101010111101101111010111101100010010111010100001101100110000000111111011111001011101000010001010010110111111101100010101010000101110100100111000110011011101010101001111001100110100111010011110010001111110111111000111"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_2",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 8), listOf(3, 5, 1, 2), listOf(1, 2, 3, 3, 1), listOf(2, 3, 3, 3), listOf(2, 2, 3, 2, 1), listOf(3, 1, 1, 1, 2), listOf(1, 2, 2, 2), listOf(4, 1, 1, 1), listOf(1, 4, 1, 1, 2, 2), listOf(5, 6, 1), listOf(9, 2, 3), listOf(3, 3, 1, 1, 1, 1), listOf(3, 2, 2, 1, 1, 2), listOf(1, 1, 1, 2, 3, 1, 1, 1), listOf(3, 2, 3, 5), listOf(1, 1, 3, 3, 1, 3), listOf(2, 1, 1, 2, 1, 1, 1, 1), listOf(3, 2, 1, 2, 1), listOf(2, 1, 1, 4, 4, 1), listOf(2, 4, 6, 1, 1)),
                colClues = listOf(listOf(1, 3, 2, 1, 1, 2), listOf(1, 3, 1, 2, 4), listOf(1, 1, 7, 2), listOf(1, 1, 1, 6, 2, 3), listOf(1, 4, 2, 1, 1), listOf(1, 1, 1, 2, 3, 2), listOf(1, 2, 6, 1), listOf(2, 1, 3, 4, 1), listOf(1, 1, 3, 1, 3), listOf(1, 1, 3, 2, 2, 4), listOf(3, 9, 2), listOf(4, 1, 1, 4, 1), listOf(3, 1, 1, 2, 3, 1), listOf(2, 2, 3, 1, 3, 2), listOf(1, 3, 2, 3, 1), listOf(4, 1, 3, 1, 3), listOf(1, 1, 1, 1, 6), listOf(1, 1, 1, 2, 2), listOf(2, 1, 1, 1, 3, 1), listOf(2, 1, 1, 1, 3, 1)),
                gridStr = "0001010000011111111011100000011111010011000100011011100111011100011101110111000011011000000011101101111001010100001100000000001011001100001100011110010101000000101111001010011000111111100111111010000000111111111011011100011101110010000101010111011000110101001110101011011101001001001110110111011111001001001110111010111001101001011010101010011100001100100110101101010111100111100111011110111111010010"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_3",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(6, 1, 1, 3, 1), listOf(4, 1, 4, 3), listOf(3, 1, 2, 1, 3, 1), listOf(3, 3, 1, 3), listOf(1, 4, 2, 3, 4), listOf(2, 1, 2, 1, 1), listOf(4, 5, 2, 3), listOf(2, 3, 3, 4), listOf(1, 5, 2, 3, 1, 1), listOf(4, 1, 1, 2, 1, 1), listOf(5, 1, 1, 4, 1, 1), listOf(2, 2, 1, 3, 1), listOf(2, 1, 2, 2, 2, 4), listOf(1, 1, 2, 5, 2), listOf(3, 1, 5, 1, 3), listOf(1, 4, 2, 1, 4), listOf(3, 2, 1, 6), listOf(1, 4, 2, 1, 1, 1), listOf(2, 2, 2, 2, 2), listOf(4, 2, 2, 1)),
                colClues = listOf(listOf(4, 1, 5, 2), listOf(3, 3, 4, 3, 2), listOf(11, 1, 1, 2), listOf(2, 1, 1, 6, 1), listOf(1, 1, 1, 4, 4), listOf(1, 2, 1, 1, 4), listOf(1, 1, 3, 3, 2, 1), listOf(4, 2, 1, 1, 1, 3), listOf(1, 3, 1, 3, 2), listOf(1, 1, 1, 1, 1, 1, 1, 1), listOf(3, 1, 4, 2, 2), listOf(2, 1, 1, 3, 3, 1), listOf(2, 2, 2, 1, 2, 1), listOf(2, 1, 4, 2, 1, 1), listOf(2, 1, 2, 1, 2, 2, 2), listOf(1, 1, 2, 1, 2, 2, 1), listOf(2, 1, 3, 2, 2), listOf(4, 2, 1, 1, 3, 1), listOf(1, 5, 1, 6), listOf(1, 3, 3, 1, 4)),
                gridStr = "0011111101010111000111110001000111101110111000010110100111011110001110100000011110111100011011101111011001000001100100100111101111100011011101100011100011101111101111101101110010010111100100100110101011111010101111000101110110100111000100001101011000110110111110010001100011111011111000101111100101110100011110110010111111101100000101111110100011110110100100100110110110110110000001111001100000110100"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_4",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 3, 1, 2, 2), listOf(5, 4, 2, 1), listOf(1, 5, 1, 1, 1, 1), listOf(3, 1, 6, 1, 2), listOf(1, 1, 3, 2, 1, 1), listOf(1, 1, 2, 1), listOf(1, 4, 2, 3, 1, 1), listOf(3, 5, 4, 1), listOf(5, 1, 1, 2), listOf(1, 7, 1, 1, 2), listOf(1, 1, 1, 1, 1, 3), listOf(2, 1, 2, 2), listOf(1, 1, 1, 4, 3, 5), listOf(4, 1, 1, 1, 3, 2), listOf(1, 1, 1, 4, 1), listOf(1, 2, 3, 1, 1, 2), listOf(6, 3, 1, 3), listOf(2, 2, 1, 4, 1), listOf(3, 1, 5, 4), listOf(1, 7, 1, 5, 1)),
                colClues = listOf(listOf(2, 2, 5), listOf(1, 1, 1, 3, 1, 1, 3), listOf(1, 1, 2, 3, 1, 2), listOf(4, 2, 2, 1, 2, 1), listOf(3, 1, 2, 1, 5), listOf(2, 1, 1, 1, 3, 1), listOf(8, 1, 1, 3), listOf(1, 1, 2, 1, 2, 1, 2), listOf(1, 1, 1, 2, 1, 6), listOf(4, 4, 2, 4), listOf(1, 2, 2, 1, 2, 3), listOf(6, 2, 2), listOf(1, 1, 4, 1, 1, 1, 1), listOf(1, 3, 2, 1, 3, 2), listOf(2, 2, 2, 2, 3, 2), listOf(1, 1, 1, 3, 1, 2), listOf(4, 1, 1, 1, 1, 2, 1), listOf(1, 2, 1, 1, 1, 1, 1), listOf(5, 1, 5, 1), listOf(1, 1, 5, 1)),
                gridStr = "0101001110010110011000111110011110110010010111110101010000100011101001111110101101000010011101101010000000101001100010000100111101101110101001110011111011110100111110000100100011001000111111100101001101010000100010101110001101000000011000111010101111011101111111110001010101110011000001001010001111011000110011101010001111111101110100011100110110101111000010001110101111101111000010111111101001111101"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_5",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(3, 1, 1, 6, 1), listOf(2, 2, 2, 1, 1, 4), listOf(1, 3, 1, 1, 1, 3, 1, 1), listOf(2, 1, 1, 7, 2), listOf(6, 3, 1, 5), listOf(1, 1, 4, 2), listOf(1, 1, 4, 2, 2, 1), listOf(1, 1, 2, 2, 1), listOf(1, 1, 1, 1, 1, 1, 2), listOf(6, 1, 2, 2, 1), listOf(4, 1, 3, 6), listOf(5, 1, 6), listOf(4, 2, 1, 2), listOf(1, 1, 2, 4, 1, 2), listOf(3, 1, 3, 4), listOf(1, 3, 1, 1, 2, 1, 1), listOf(1, 3, 5, 1, 5), listOf(3, 4, 1, 2, 2), listOf(4, 1, 1, 2, 1, 1), listOf(1, 1, 3, 3, 1)),
                colClues = listOf(listOf(3, 2, 1, 2, 1), listOf(1, 1, 2, 4, 1), listOf(1, 1, 4, 2, 1, 1), listOf(3, 9, 1, 1), listOf(3, 1, 4, 3, 2), listOf(2, 4, 2, 1, 1, 5), listOf(1, 1, 1, 1, 1, 1, 3), listOf(1, 2, 5, 1, 1, 2), listOf(4, 2, 2, 3), listOf(3, 1, 1, 2, 1), listOf(1, 3, 1, 1, 3, 1, 1), listOf(1, 2, 1, 3, 4, 1), listOf(1, 1, 1, 4, 2), listOf(1, 4, 1, 2, 2), listOf(3, 3, 3, 1, 2), listOf(1, 1, 8, 1, 3), listOf(2, 3, 1, 2, 4), listOf(5, 2, 1, 1, 1), listOf(1, 2, 2, 2, 2), listOf(3, 4, 1, 1, 5)),
                gridStr = "0000111010101111110111001101100100101111101110101010011101011101010111111100110000111111011101011111000001000100011110110101011110110011000101010001100000110001100101010001010110001111110100011011001001111001011100111111011111001000111111000111100110101100000010010110011110001011001110010011100111100010111001010110100110011101111101011111111001111000101100110001111010001011010110101110011100000001"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_6",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(2, 2, 1, 3, 1, 3), listOf(1, 2, 2, 3, 1), listOf(2, 1, 2, 4, 2), listOf(4, 1, 2, 7), listOf(2, 3, 3, 1, 1, 1), listOf(2, 1, 1, 2, 1, 1, 2), listOf(1, 1, 2, 1, 2, 2, 3), listOf(3, 3, 1, 1, 3, 2), listOf(1, 3, 2, 1, 1, 1, 1), listOf(3, 1, 1, 3, 1, 1), listOf(1, 5, 1, 3, 1), listOf(3, 1, 4, 1, 1, 1), listOf(5, 1, 4, 3, 2), listOf(1, 2, 3, 3), listOf(1, 1, 2, 1, 1, 2, 1), listOf(1, 1, 3, 1, 1, 2, 1), listOf(1, 1, 2, 1, 1, 3, 2), listOf(5, 1, 3, 5), listOf(4, 1, 1, 1), listOf(3, 3, 5, 1)),
                colClues = listOf(listOf(1, 1, 2, 1, 2, 2), listOf(1, 2, 1, 1, 2, 1), listOf(1, 2, 8, 3), listOf(2, 1, 6, 2), listOf(2, 1, 2, 1, 2, 1, 3), listOf(3, 5, 2, 2, 3), listOf(1, 4, 6), listOf(1, 4, 1, 2, 1), listOf(1, 1, 1, 1, 2, 1), listOf(2, 1, 1, 2, 2, 1), listOf(2, 4, 1, 2, 1, 1), listOf(1, 2, 2, 1, 2, 2), listOf(1, 1, 2, 2, 2, 3, 1), listOf(2, 1, 2, 1, 3, 1, 1), listOf(7, 1, 1, 1, 1), listOf(4, 2, 1, 1, 1, 4), listOf(1, 2, 2, 6, 2, 1), listOf(1, 1, 2, 1, 1, 2), listOf(1, 2, 3, 1, 3, 2), listOf(5, 2, 1, 1, 4, 1)),
                gridStr = "0110110100111001011110001100011000111001001101001100111100110111100100110111111111000111001110101001001101010110101001101010110100110110111011101110100100111011001001110110010101010111001010011100101010111110000010111001011101011110010010101111100101111011101110011000000111001110010101101000010110011010111010101100000110100011010010111011001111100101110111110001111000010001010000001110111011111001"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_7",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(3, 2, 2, 4), listOf(4, 2, 2, 2, 2), listOf(2, 1, 1, 1, 1, 2, 3), listOf(1, 1, 3, 1, 4, 2), listOf(2, 1, 1, 1, 1, 3), listOf(1, 2, 1, 1, 4, 1), listOf(2, 2, 1, 2, 1, 1), listOf(2, 2, 2, 2, 1), listOf(2, 2, 5, 2, 1), listOf(1, 2, 1, 2, 2), listOf(2, 1, 1, 2, 1, 1), listOf(3, 1, 7, 3), listOf(1, 2, 3, 1, 3, 2), listOf(1, 2, 1, 2, 1, 2, 2), listOf(2, 1, 2, 5), listOf(2, 2, 1, 2, 1, 1), listOf(1, 2, 2, 1, 3, 1), listOf(1, 1, 2, 1, 2, 1), listOf(4, 1, 3, 1, 2, 3), listOf(4, 1, 1, 2, 2, 1)),
                colClues = listOf(listOf(2, 1, 2, 2, 1, 3), listOf(4, 3, 3, 2, 2), listOf(2, 2, 1, 1, 1, 2), listOf(5, 1, 3, 3), listOf(1, 1, 2, 2, 1, 1), listOf(1, 2, 1, 2, 1), listOf(2, 2, 1, 2), listOf(2, 1, 2, 2, 2), listOf(1, 2, 1, 5, 3), listOf(1, 1, 5, 1, 2, 3), listOf(2, 1, 2, 1), listOf(1, 1, 1, 4, 1, 2), listOf(3, 3, 1, 3, 1), listOf(1, 1, 1, 2, 1, 1, 1, 2), listOf(2, 1, 1, 8), listOf(1, 4, 1, 5), listOf(1, 1, 3, 2, 1, 1, 1), listOf(3, 1, 2, 2, 2, 3), listOf(6, 1, 3, 1), listOf(3, 4, 2, 5)),
                gridStr = "0011100110011001111011110011011011000110110100100010101101110101000111010111101111010000100010010111001011010100111100100110001101011000100111000110110000001101110011000111110011010000100011010110001111010000101100010100111010011111110011100101101110001011101110110100110010110011000110100110011111000110011000001011010101001100110101110001100100001100001011011111010111010110011111110001000101101101"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_8",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(2, 1, 2, 2, 2), listOf(2, 3, 1, 2, 2), listOf(3, 1, 3, 2, 2), listOf(7, 1, 3, 1, 1), listOf(2, 4, 1, 1, 1), listOf(1, 2, 5, 2, 2), listOf(3, 1, 4, 1, 1, 2), listOf(2, 1, 3, 2, 2, 1), listOf(2, 4, 1, 1, 5), listOf(3, 2, 3, 1, 5), listOf(4, 6, 3), listOf(8, 5, 1, 1, 1), listOf(1, 1, 1, 4), listOf(1, 8, 3, 3), listOf(1, 1, 1, 3, 1, 5), listOf(2, 1, 1, 1, 5, 1), listOf(2, 1, 6, 1, 2), listOf(4, 6, 2, 3), listOf(2, 1, 1, 2, 2, 2), listOf(1, 1, 7, 2, 3)),
                colClues = listOf(listOf(4, 2, 4, 1), listOf(5, 6, 1, 2), listOf(3, 1, 4, 1, 3), listOf(1, 1, 1, 4, 5), listOf(4, 1, 2, 1, 1, 2, 1), listOf(1, 2, 1, 4, 3, 2), listOf(4, 5, 1, 1, 1), listOf(1, 1, 4, 2, 1, 1, 1, 1), listOf(1, 1, 1, 2, 2, 2, 2, 1), listOf(1, 1, 2, 3, 2, 4), listOf(1, 11, 2, 1), listOf(3, 1, 1, 1, 4), listOf(2, 1, 1, 1, 1, 1, 3), listOf(1, 1, 4, 4, 2, 1), listOf(1, 2, 4, 3), listOf(1, 5, 11), listOf(1, 1, 1, 2, 1, 2), listOf(1, 1, 1, 2, 1, 7), listOf(1, 6, 2, 4), listOf(1, 1, 1, 1, 1, 1, 1)),
                gridStr = "1100100110001101100011011100010110000110111010111011000110001111111001011101010001100111101000010010000010110111110110110111010111100101011011001011101101100010110111100010010111110111011011101011111011110111111001110000111111110111110101011010000000100111100010011111111001110111010101001110101111100011010100010111110100011010111111010110011110011111101101110110010001011011011010101111111001100111"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_9",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 1, 4, 2, 4), listOf(3, 1, 1, 2, 1, 1, 1), listOf(1, 2, 2, 1, 1, 1, 1, 2), listOf(2, 2, 1, 4, 1, 1), listOf(5, 2, 1, 2, 1), listOf(2, 2, 1, 1, 1, 1), listOf(5, 2, 1, 1, 1), listOf(1, 3, 4, 2, 1), listOf(1, 4, 4, 1, 1, 2), listOf(3, 2, 3, 2, 2), listOf(4, 2, 1, 4, 2), listOf(3, 5, 1, 1, 2), listOf(2, 1, 2, 1, 1, 1), listOf(1, 3, 3), listOf(5, 2, 2, 1), listOf(2, 6, 1), listOf(2, 2, 5, 1, 1, 1), listOf(1, 2, 3, 2), listOf(1, 1, 1, 1, 1, 1, 4), listOf(1, 5, 1, 1, 3, 3)),
                colClues = listOf(listOf(2, 2, 4, 1, 1), listOf(5, 2, 5, 1), listOf(2, 3, 5, 3, 1), listOf(3, 1, 1, 2, 1, 2), listOf(3, 3, 1, 4, 1), listOf(2, 4, 5, 2), listOf(1, 3, 2, 3, 1, 1, 1), listOf(3, 1, 1, 2, 2), listOf(1, 1, 2, 1, 2, 2, 2), listOf(3, 2, 5, 4), listOf(1, 1, 3, 1, 1, 2, 1), listOf(4, 1, 1, 2, 1), listOf(2, 2, 3, 1), listOf(1, 1, 1, 1, 1, 1), listOf(5, 3, 4), listOf(2, 5, 1, 1), listOf(1, 1, 3, 1, 3), listOf(1, 3, 2, 2), listOf(3, 1, 5, 3), listOf(2, 3, 2, 4)),
                gridStr = "1010011110001101111011100101011010010010010110110101001010111101101011110010000111111011000101100010011000001101001010010011111011000010100110001110001111001101101111011110101001101110011001110011011011110011010011110011001110111110100100110110010011001001001001001110000000011100011111000110110001000110111111010000000001101101111100101001100000000110001110110101010010010010111110111110101001110111"
            ),
            PregeneratedNonogram(
                id = "nonogram_hard_10",
                difficulty = "Hard",
                size = 20,
                rowClues = listOf(listOf(1, 2, 1, 6, 3), listOf(3, 3, 1, 2, 2), listOf(5, 2, 2, 3, 2), listOf(3, 3, 2, 1, 2, 1), listOf(1, 1, 4, 2, 2, 2), listOf(1, 1, 2, 7), listOf(1, 7, 1, 1, 1, 2), listOf(1, 1, 2, 2, 1, 3, 1), listOf(4, 5, 1), listOf(3, 1, 1, 1, 2), listOf(2, 1, 1, 3, 4), listOf(1, 6, 1, 4), listOf(1, 1, 2, 2, 2, 4, 1), listOf(3, 2, 2, 1, 2, 1), listOf(1, 8, 2, 1, 1, 1), listOf(1, 1, 1, 6, 3), listOf(2, 1, 1, 1, 1, 1), listOf(2, 1, 2, 1, 1), listOf(2, 2, 4, 1), listOf(1, 4, 1, 1, 2)),
                colClues = listOf(listOf(3, 3, 2, 3), listOf(5, 3, 2, 1), listOf(3, 1, 2, 2, 1), listOf(1, 1, 3, 3, 2, 1, 1), listOf(1, 1, 1, 1, 1, 2), listOf(1, 5, 5, 1), listOf(4, 2, 1, 4, 1, 1), listOf(4, 1, 1, 6), listOf(2, 1, 1, 4, 2), listOf(1, 1, 1, 1, 5, 2), listOf(8, 1, 1, 2), listOf(1, 2, 2, 1, 4, 1), listOf(1, 2, 1, 1, 4), listOf(6, 2, 2, 3, 1), listOf(3, 3, 1, 4), listOf(1, 2, 1, 2, 4, 1), listOf(1, 3, 2, 3, 1), listOf(3, 2, 1, 7, 2), listOf(1, 2, 2, 3, 1, 1), listOf(1, 4, 1, 1, 2)),
                gridStr = "0100110010111111011111100011101001101100111110110110011101101110011100110101101001010111101101101100100001000110111111101011111110101010001110010110011001011101011110000000111110011110001001010000001111010100000111011110000101111110010111100101011011011011110111100110110010110100101111111101101001011001000101111110111000000011000101010101000110010011010000010110110111100000010000010011110101000110"
            ),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedNonogram>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
