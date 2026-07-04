package com.funkyotc.puzzleverse.hexasort.data

data class HexCell(
    val color: Int,
    val removed: Boolean = false
)

data class HexaSortLevel(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<Int?>>
) {
    val colorPalette: Set<Int>
        get() = grid.flatten().filterNotNull().toSet()
}

data class HexaSortState(
    val level: HexaSortLevel,
    val grid: List<List<Int?>>,
    val score: Int = 0,
    val moves: Int = 0,
    val isWon: Boolean = false,
    val isGameOver: Boolean = false,
    val flashingCells: Set<Pair<Int, Int>> = emptySet()
)
