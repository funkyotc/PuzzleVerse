package com.funkyotc.puzzleverse.constellations.data

enum class CellState {
    EMPTY,
    STAR,
    CROSS // Used to mark cells where a star definitely cannot be
}

data class Cell(
    val row: Int,
    val col: Int,
    val regionId: Int,
    var state: CellState = CellState.EMPTY,
    var isAuto: Boolean = false
)

data class ConstellationsPuzzle(
    val size: Int,
    val cells: List<List<Cell>>, // 2D grid
    val regions: Map<Int, List<Pair<Int, Int>>> // regionId -> list of (row, col) coordinates
)
