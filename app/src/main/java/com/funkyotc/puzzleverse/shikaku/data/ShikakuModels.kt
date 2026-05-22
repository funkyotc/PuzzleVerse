package com.funkyotc.puzzleverse.shikaku.data

import java.util.UUID

data class ShikakuCell(
    val row: Int,
    val col: Int,
    val clue: Int? = null,
    var rectangleId: String? = null
)

data class ShikakuRectangle(
    val id: String = UUID.randomUUID().toString(),
    val row: Int,
    val col: Int,
    val width: Int,
    val height: Int
) {
    fun overlaps(other: ShikakuRectangle): Boolean {
        return this.row < other.row + other.height &&
               other.row < this.row + this.height &&
               this.col < other.col + other.width &&
               other.col < this.col + this.width
    }
}

data class ShikakuBoard(
    val cells: List<ShikakuCell>,
    val gridSize: Int,
    val seed: Long,
    val puzzleId: String,
    val isDaily: Boolean,
    val solutionRectangles: List<ShikakuRectangle> = emptyList(),
    val playerRectangles: List<ShikakuRectangle> = emptyList()
) {
    fun getCell(row: Int, col: Int): ShikakuCell? {
        return cells.find { it.row == row && it.col == col }
    }

    fun getClueCells(): List<ShikakuCell> {
        return cells.filter { it.clue != null }
    }

    fun reconstructRectanglesFromCells(): List<ShikakuRectangle> {
        val idToRect = mutableMapOf<String, ShikakuRectangle>()
        cells.forEach { cell ->
            cell.rectangleId?.let { rectId ->
                if (!idToRect.containsKey(rectId)) {
                    var minRow = cell.row
                    var minCol = cell.col
                    var maxRow = cell.row
                    var maxCol = cell.col
                    cells.filter { it.rectangleId == rectId }.forEach { c ->
                        minRow = minOf(minRow, c.row)
                        minCol = minOf(minCol, c.col)
                        maxRow = maxOf(maxRow, c.row)
                        maxCol = maxOf(maxCol, c.col)
                    }
                    idToRect[rectId] = ShikakuRectangle(
                        id = rectId,
                        row = minRow,
                        col = minCol,
                        width = maxCol - minCol + 1,
                        height = maxRow - minRow + 1
                    )
                }
            }
        }
        return idToRect.values.toList()
    }
}
