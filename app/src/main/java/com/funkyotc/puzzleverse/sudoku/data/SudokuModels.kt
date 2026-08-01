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

    fun getCell(row: Int, col: Int): SudokuCell {
        return cells.firstOrNull { it.row == row && it.col == col }
            ?: throw IllegalStateException("Cell not found at row $row, col $col")
    }

    fun isNumberCompleted(number: Int): Boolean {
        return cells.count { it.number == number } >= 9
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
