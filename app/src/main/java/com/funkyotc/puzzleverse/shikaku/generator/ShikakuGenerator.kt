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

        val maxArea = when (gridSize) {
            in 12..14 -> 12
            in 10..11 -> 9
            else -> 6
        }
        val minRectangles = (gridSize * gridSize) / maxArea
        val maxRectangles = (gridSize * gridSize) / 2

        val targetRectangles = random.nextInt(minRectangles, maxRectangles + 1)

        val rectangles = generateRectangles(gridSize, gridSize, maxArea, targetRectangles)
        val cells = createCells(gridSize, gridSize, rectangles)

        return ShikakuBoard(
            cells = cells,
            gridSize = gridSize,
            seed = seed,
            puzzleId = puzzleId,
            isDaily = seed > 0 && seed < 100000
        )
    }

    private fun generateRectangles(rows: Int, cols: Int, maxArea: Int, targetRectangles: Int): List<ShikakuRectangle> {
        val rectangles = mutableListOf<ShikakuRectangle>(
            ShikakuRectangle(UUID.randomUUID().toString(), 0, 0, cols, rows)
        )

        var attempts = 0
        val maxAttempts = 1000
        while (attempts++ < maxAttempts) {
            val oversized = rectangles.filter { it.width * it.height > maxArea }
            if (oversized.isNotEmpty()) {
                val toSplit = oversized[random.nextInt(oversized.size)]
                if (!splitRectangle(toSplit, rectangles)) {
                    break
                }
            } else if (rectangles.size < targetRectangles) {
                val splittable = rectangles.filter { it.width > 1 || it.height > 1 }
                if (splittable.isEmpty()) break
                val toSplit = splittable[random.nextInt(splittable.size)]
                splitRectangle(toSplit, rectangles)
            } else {
                break
            }
        }
        return rectangles
    }

    private fun splitRectangle(rect: ShikakuRectangle, list: MutableList<ShikakuRectangle>): Boolean {
        val canSplitVertically = rect.width > 1
        val canSplitHorizontally = rect.height > 1
        if (!canSplitVertically && !canSplitHorizontally) return false

        val splitVertical = when {
            canSplitVertically && canSplitHorizontally -> random.nextBoolean()
            canSplitVertically -> true
            else -> false
        }

        val r1: ShikakuRectangle
        val r2: ShikakuRectangle

        if (splitVertical) {
            val splitCol = 1 + random.nextInt(rect.width - 1)
            r1 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col, splitCol, rect.height)
            r2 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col + splitCol, rect.width - splitCol, rect.height)
        } else {
            val splitRow = 1 + random.nextInt(rect.height - 1)
            r1 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col, rect.width, splitRow)
            r2 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row + splitRow, rect.col, rect.width, rect.height - splitRow)
        }

        list.remove(rect)
        list.add(r1)
        list.add(r2)
        return true
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
