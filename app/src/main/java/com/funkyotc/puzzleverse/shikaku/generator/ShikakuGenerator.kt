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
            in 10..11 -> 12 // Increased to 12!
            else -> 10      // Increased to 10!
        }
        val minRectangles = (gridSize * gridSize) / maxArea
        // Lower the maximum split count to get larger boxes and more variety!
        val maxRectangles = (gridSize * gridSize) / 4.5f

        val targetRectangles = random.nextInt(minRectangles, maxRectangles.toInt() + 1)

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

    private fun hasValidSplit(rect: ShikakuRectangle): Boolean {
        val canSplitVertically = rect.width > 1
        val canSplitHorizontally = rect.height > 1
        
        if (canSplitVertically) {
            for (splitCol in 1 until rect.width) {
                if (splitCol * rect.height >= 2 && (rect.width - splitCol) * rect.height >= 2) {
                    return true
                }
            }
        }
        if (canSplitHorizontally) {
            for (splitRow in 1 until rect.height) {
                if (rect.width * splitRow >= 2 && rect.width * (rect.height - splitRow) >= 2) {
                    return true
                }
            }
        }
        return false
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
                val splittableOversized = oversized.filter { hasValidSplit(it) }
                if (splittableOversized.isEmpty()) {
                    break
                }
                val toSplit = splittableOversized[random.nextInt(splittableOversized.size)]
                if (!splitRectangle(toSplit, rectangles)) {
                    break
                }
            } else if (rectangles.size < targetRectangles) {
                val splittable = rectangles.filter { hasValidSplit(it) }
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

        val validVerticalSplits = mutableListOf<Int>()
        val preferredVerticalSplits = mutableListOf<Int>()
        if (canSplitVertically) {
            for (splitCol in 1 until rect.width) {
                val r1Area = splitCol * rect.height
                val r2Area = (rect.width - splitCol) * rect.height
                if (r1Area >= 2 && r2Area >= 2) {
                    validVerticalSplits.add(splitCol)
                    if (r1Area >= 4 && r2Area >= 4) {
                        preferredVerticalSplits.add(splitCol)
                    }
                }
            }
        }

        val validHorizontalSplits = mutableListOf<Int>()
        val preferredHorizontalSplits = mutableListOf<Int>()
        if (canSplitHorizontally) {
            for (splitRow in 1 until rect.height) {
                val r1Area = rect.width * splitRow
                val r2Area = rect.width * (rect.height - splitRow)
                if (r1Area >= 2 && r2Area >= 2) {
                    validHorizontalSplits.add(splitRow)
                    if (r1Area >= 4 && r2Area >= 4) {
                        preferredHorizontalSplits.add(splitRow)
                    }
                }
            }
        }

        // Use preferred splits if available to reduce 2s and 3s
        val usePreferred = preferredVerticalSplits.isNotEmpty() || preferredHorizontalSplits.isNotEmpty()
        val verticalSplits = if (usePreferred) preferredVerticalSplits else validVerticalSplits
        val horizontalSplits = if (usePreferred) preferredHorizontalSplits else validHorizontalSplits

        val canSplitV = verticalSplits.isNotEmpty()
        val canSplitH = horizontalSplits.isNotEmpty()
        if (!canSplitV && !canSplitH) return false

        val splitVertical = when {
            canSplitV && canSplitH -> random.nextBoolean()
            canSplitV -> true
            else -> false
        }

        val r1: ShikakuRectangle
        val r2: ShikakuRectangle

        if (splitVertical) {
            val splitCol = verticalSplits[random.nextInt(verticalSplits.size)]
            r1 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col, splitCol, rect.height)
            r2 = ShikakuRectangle(UUID.randomUUID().toString(), rect.row, rect.col + splitCol, rect.width - splitCol, rect.height)
        } else {
            val splitRow = horizontalSplits[random.nextInt(horizontalSplits.size)]
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
