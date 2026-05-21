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
)

data class ShikakuBoard(
    val cells: List<ShikakuCell>,
    val gridSize: Int,
    val seed: Long,
    val puzzleId: String,
    val isDaily: Boolean
) {
    fun getCell(row: Int, col: Int): ShikakuCell? {
        return cells.find { it.row == row && it.col == col }
    }

    fun getClueCells(): List<ShikakuCell> {
        return cells.filter { it.clue != null }
    }

    fun getSolutionRectangles(): List<ShikakuRectangle> {
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

    fun copy(): ShikakuBoard {
        return ShikakuBoard(
            cells = cells.map { ShikakuCell(it.row, it.col, it.clue, null) },
            gridSize = gridSize,
            seed = seed,
            puzzleId = puzzleId,
            isDaily = isDaily
        )
    }
}
