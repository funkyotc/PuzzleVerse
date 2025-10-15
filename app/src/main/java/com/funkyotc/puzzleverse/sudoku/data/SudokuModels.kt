package com.funkyotc.puzzleverse.sudoku.data

data class SudokuCell(
    val row: Int,
    val col: Int,
    val number: Int,
    val isHint: Boolean = false,
    val isError: Boolean = false,
    val pencilMarks: Set<Int> = emptySet()
)

class SudokuBoard(val cells: List<SudokuCell>) {
    private val cellMap: Map<Pair<Int, Int>, SudokuCell> = cells.associateBy { it.row to it.col }

    fun getCell(row: Int, col: Int): SudokuCell {
        return cellMap[row to col]
            ?: throw IllegalStateException("Cell not found at row $row, col $col")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuBoard

        if (cells != other.cells) return false

        return true
    }

    override fun hashCode(): Int {
        return cells.hashCode()
    }
}
