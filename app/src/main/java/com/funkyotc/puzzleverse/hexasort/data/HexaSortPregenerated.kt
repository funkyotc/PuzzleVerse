package com.funkyotc.puzzleverse.hexasort.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedHexaSort(
    override val id: String,
    override val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<Int?>>
) : BrowseablePuzzle {
    override val label: String get() = "Puzzle ${id.takeLast(3)}"
    override val subtitle: String get() = "${rows}x$cols"

    fun toLevel(): HexaSortLevel = HexaSortLevel(id, difficulty, rows, cols, grid)
}

object HexaSortPregenerated {
    val ALL_PUZZLES: List<PregeneratedHexaSort> by lazy { listOf(
        PregeneratedHexaSort("HexaSort_Easy_puzzle_001", "Easy", 5, 5, listOf(
            listOf(0, 1, 2, 0, 1),
            listOf(1, 2, 0, 1, 2),
            listOf(2, 0, 1, 2, 0),
            listOf(0, 1, 2, 0, 1),
            listOf(1, 2, 0, 1, 2)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_002", "Easy", 5, 5, listOf(
            listOf(1, 1, 0, 2, 2),
            listOf(0, 1, 0, 2, 1),
            listOf(0, 0, 1, 1, 0),
            listOf(2, 0, 1, 0, 2),
            listOf(2, 2, 0, 1, 1)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_003", "Easy", 5, 5, listOf(
            listOf(0, 2, 0, 2, 0),
            listOf(1, 0, 1, 0, 1),
            listOf(2, 1, 2, 1, 2),
            listOf(0, 2, 0, 2, 0),
            listOf(1, 0, 1, 0, 1)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_004", "Easy", 5, 5, listOf(
            listOf(2, 0, 0, 1, 1),
            listOf(0, 2, 0, 1, 2),
            listOf(0, 0, 2, 2, 1),
            listOf(1, 1, 0, 2, 0),
            listOf(2, 1, 1, 0, 2)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_005", "Easy", 5, 5, listOf(
            listOf(1, 0, 1, 0, 1),
            listOf(2, 1, 2, 1, 2),
            listOf(0, 2, 0, 2, 0),
            listOf(1, 0, 1, 0, 1),
            listOf(2, 1, 2, 1, 2)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_006", "Easy", 5, 5, listOf(
            listOf(0, 0, 1, 2, 2),
            listOf(1, 0, 1, 0, 2),
            listOf(2, 1, 2, 0, 1),
            listOf(2, 2, 1, 0, 0),
            listOf(1, 2, 0, 1, 2)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_007", "Easy", 5, 5, listOf(
            listOf(0, 1, 0, 1, 0),
            listOf(2, 0, 2, 0, 2),
            listOf(1, 2, 1, 2, 1),
            listOf(0, 1, 0, 1, 0),
            listOf(2, 0, 2, 0, 2)
        )),
        PregeneratedHexaSort("HexaSort_Easy_puzzle_008", "Easy", 5, 5, listOf(
            listOf(2, 2, 1, 1, 0),
            listOf(1, 2, 2, 0, 1),
            listOf(0, 0, 1, 2, 2),
            listOf(1, 2, 0, 0, 1),
            listOf(2, 1, 0, 2, 0)
        )),
        PregeneratedHexaSort("HexaSort_Medium_puzzle_001", "Medium", 6, 6, listOf(
            listOf(0, 1, 2, 3, 0, 1),
            listOf(1, 2, 3, 0, 1, 2),
            listOf(2, 3, 0, 1, 2, 3),
            listOf(3, 0, 1, 2, 3, 0),
            listOf(0, 1, 2, 3, 0, 1),
            listOf(1, 2, 3, 0, 1, 2)
        )),
        PregeneratedHexaSort("HexaSort_Medium_puzzle_002", "Medium", 6, 6, listOf(
            listOf(0, 0, 1, 2, 3, 3),
            listOf(1, 0, 1, 2, 3, 0),
            listOf(2, 1, 2, 3, 0, 1),
            listOf(3, 2, 3, 0, 1, 2),
            listOf(0, 3, 0, 1, 2, 3),
            listOf(1, 0, 1, 2, 3, 0)
        )),
        PregeneratedHexaSort("HexaSort_Medium_puzzle_003", "Medium", 6, 6, listOf(
            listOf(3, 3, 2, 2, 1, 1),
            listOf(0, 3, 2, 1, 1, 0),
            listOf(0, 0, 3, 2, 1, 0),
            listOf(1, 0, 3, 2, 0, 0),
            listOf(1, 1, 2, 3, 0, 0),
            listOf(2, 1, 1, 3, 3, 2)
        )),
        PregeneratedHexaSort("HexaSort_Medium_puzzle_004", "Medium", 6, 6, listOf(
            listOf(0, 1, 2, 3, 2, 1),
            listOf(1, 2, 3, 0, 3, 2),
            listOf(2, 3, 0, 1, 0, 3),
            listOf(3, 0, 1, 2, 1, 0),
            listOf(2, 3, 0, 1, 0, 3),
            listOf(1, 2, 3, 0, 3, 2)
        )),
        PregeneratedHexaSort("HexaSort_Hard_puzzle_001", "Hard", 7, 7, listOf(
            listOf(0, 1, 2, 3, 4, 0, 1),
            listOf(1, 2, 3, 4, 0, 1, 2),
            listOf(2, 3, 4, 0, 1, 2, 3),
            listOf(3, 4, 0, 1, 2, 3, 4),
            listOf(4, 0, 1, 2, 3, 4, 0),
            listOf(0, 1, 2, 3, 4, 0, 1),
            listOf(1, 2, 3, 4, 0, 1, 2)
        )),
        PregeneratedHexaSort("HexaSort_Hard_puzzle_002", "Hard", 7, 7, listOf(
            listOf(4, 4, 3, 3, 2, 2, 1),
            listOf(0, 4, 3, 2, 1, 1, 0),
            listOf(0, 0, 4, 3, 2, 1, 0),
            listOf(1, 0, 4, 3, 2, 0, 0),
            listOf(1, 1, 3, 4, 2, 0, 0),
            listOf(2, 1, 3, 4, 0, 0, 0),
            listOf(2, 2, 3, 4, 4, 1, 1)
        )),
        PregeneratedHexaSort("HexaSort_Hard_puzzle_003", "Hard", 7, 7, listOf(
            listOf(3, 2, 1, 0, 4, 3, 2),
            listOf(2, 1, 0, 4, 3, 2, 1),
            listOf(1, 0, 4, 3, 2, 1, 0),
            listOf(0, 4, 3, 2, 1, 0, 4),
            listOf(4, 3, 2, 1, 0, 4, 3),
            listOf(3, 2, 1, 0, 4, 3, 2),
            listOf(2, 1, 0, 4, 3, 2, 1)
        ))
    ) }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedHexaSort>> by lazy {
        ALL_PUZZLES.groupBy { it.difficulty }
    }

    fun getPuzzleById(id: String): PregeneratedHexaSort? = ALL_PUZZLES.find { it.id == id }
}
