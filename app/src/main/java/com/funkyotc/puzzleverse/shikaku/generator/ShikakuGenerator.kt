package com.funkyotc.puzzleverse.shikaku.generator

import com.funkyotc.puzzleverse.shikaku.data.ShikakuBoard
import com.funkyotc.puzzleverse.shikaku.data.ShikakuCell
import com.funkyotc.puzzleverse.shikaku.data.ShikakuRectangle
import java.util.Random
import java.util.UUID

class ShikakuGenerator(private val seed: Long) {
    private val random = Random(seed)

    fun generate(difficulty: String): ShikakuBoard {
        val gridSize = when (difficulty) {
            "easy" -> 8
            "medium" -> 10
            "hard" -> 12
            else -> 8
        }

        val puzzleId = if (seed > 0 && seed < 100000) "daily" else "std_${seed}"

        val (rectangles, valid) = generateRectangles(gridSize, gridSize)
        if (!valid) {
            return generate(difficulty)
        }

        val cells = createCells(gridSize, gridSize, rectangles)

        return ShikakuBoard(
            cells = cells,
            gridSize = gridSize,
            seed = seed,
            puzzleId = puzzleId,
            isDaily = seed > 0 && seed < 100000
        )
    }

    private fun generateRectangles(rows: Int, cols: Int): Pair<List<ShikakuRectangle>, Boolean> {
        val grid = Array(rows) { IntArray(cols) { -1 } }
        val rectangles = mutableListOf<ShikakuRectangle>()

        val maxArea = when (rows) {
            in 12..14 -> 12
            in 10..11 -> 9
            else -> 6
        }
        val minRectangles = (rows * cols) / maxArea
        val maxRectangles = (rows * cols) / 2

        val targetRectangles = random.nextInt(minRectangles, maxRectangles + 1)

        if (backtrack(grid, rectangles, 0, 0, rows, cols, targetRectangles, maxArea)) {
            return rectangles to true
        }
        return emptyList<ShikakuRectangle>() to false
    }

    private fun backtrack(
        grid: Array<IntArray>,
        rectangles: MutableList<ShikakuRectangle>,
        startRow: Int,
        startCol: Int,
        rows: Int,
        cols: Int,
        targetRectangles: Int,
        maxArea: Int
    ): Boolean {
        var currentRow = startRow
        var currentCol = startCol

        while (currentRow < rows && grid[currentRow][currentCol] != -1) {
            currentCol++
            if (currentCol >= cols) {
                currentRow++
                currentCol = 0
            }
        }

        if (currentRow >= rows) {
            return rectangles.size >= targetRectangles - 2
        }

        if (rectangles.size >= targetRectangles + 2) {
            fillRemaining(grid, rows, cols)
            return true
        }

        val remainingCells = countRemaining(grid, rows, cols)
        val remainingTargets = targetRectangles - rectangles.size
        if (remainingCells < remainingTargets) {
            return false
        }

        val maxW = minOf(cols - currentCol, if (remainingCells <= maxArea) remainingCells else maxArea)
        val maxH = minOf(rows - currentRow, if (remainingCells <= maxArea) remainingCells else maxArea)

        val minW = if (remainingCells <= remainingTargets * maxArea) 1 else remainingCells / (remainingTargets * maxArea)
        val minH = if (remainingCells <= remainingTargets * maxArea) 1 else 1

        val widths = generateRange(minW.coerceAtMost(maxW), maxW)
        val heights = generateRange(minH.coerceAtMost(maxH), maxH)
        val shuffledWidths = shuffleList(widths)
        val shuffledHeights = shuffleList(heights)

        for (h in shuffledHeights) {
            for (w in shuffledWidths) {
                if (canPlace(grid, currentRow, currentCol, h, w, rows, cols)) {
                    val rectId = rectangles.size
                    place(grid, currentRow, currentCol, h, w, rectId, rows, cols)
                    val rect = ShikakuRectangle(
                        id = UUID.randomUUID().toString(),
                        row = currentRow,
                        col = currentCol,
                        width = w,
                        height = h
                    )
                    rectangles.add(rect)

                    if (backtrack(grid, rectangles, currentRow, currentCol, rows, cols, targetRectangles, maxArea)) {
                        return true
                    }

                    rectangles.removeAt(rectangles.size - 1)
                    remove(grid, currentRow, currentCol, h, w, rows, cols)
                }
            }
        }

        return false
    }

    private fun generateRange(min: Int, max: Int): List<Int> {
        val range = mutableListOf<Int>()
        for (i in min..max) {
            range.add(i)
        }
        return range
    }

    private fun shuffleList(list: List<Int>): List<Int> {
        val shuffled = list.toMutableList()
        for (i in shuffled.lastIndex downTo 1) {
            val j = random.nextInt(i + 1)
            val temp = shuffled[i]
            shuffled[i] = shuffled[j]
            shuffled[j] = temp
        }
        return shuffled
    }

    private fun canPlace(grid: Array<IntArray>, row: Int, col: Int, height: Int, width: Int, rows: Int, cols: Int): Boolean {
        for (r in row until row + height) {
            if (r >= rows) return false
            for (c in col until col + width) {
                if (c >= cols || grid[r][c] != -1) return false
            }
        }
        return true
    }

    private fun place(grid: Array<IntArray>, row: Int, col: Int, height: Int, width: Int, rectId: Int, rows: Int, cols: Int) {
        for (r in row until row + height) {
            for (c in col until col + width) {
                if (r < rows && c < cols) {
                    grid[r][c] = rectId
                }
            }
        }
    }

    private fun remove(grid: Array<IntArray>, row: Int, col: Int, height: Int, width: Int, rows: Int, cols: Int) {
        for (r in row until row + height) {
            for (c in col until col + width) {
                if (r < rows && c < cols) {
                    grid[r][c] = -1
                }
            }
        }
    }

    private fun countRemaining(grid: Array<IntArray>, rows: Int, cols: Int): Int {
        var count = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == -1) count++
            }
        }
        return count
    }

    private fun fillRemaining(grid: Array<IntArray>, rows: Int, cols: Int) {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == -1) {
                    grid[r][c] = -2
                }
            }
        }
    }

    private fun createCells(gridSize: Int, cols: Int, rectangles: List<ShikakuRectangle>): List<ShikakuCell> {
        val cells = mutableListOf<ShikakuCell>()

        for (rect in rectangles) {
            val area = rect.width * rect.height
            val clueRow = rect.row + random.nextInt(rect.height)
            val clueCol = rect.col + random.nextInt(rect.width)

            for (r in rect.row until rect.row + rect.height) {
                for (c in rect.col until rect.col + rect.width) {
                    val clue = if (r == clueRow && c == clueCol) area else null
                    cells.add(ShikakuCell(
                        row = r,
                        col = c,
                        clue = clue,
                        rectangleId = rect.id
                    ))
                }
            }
        }

        return cells
    }
}
