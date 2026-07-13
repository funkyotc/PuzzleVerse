package com.funkyotc.puzzleverse.pullpin.data

import kotlin.random.Random

private fun generateLevel(
    id: String,
    difficulty: String,
    rows: Int,
    cols: Int,
    seed: Long
): PullPinLevel {
    val random = Random(seed)
    
    // Initialize empty grid
    val grid = List(rows) { MutableList(cols) { Cell(CellType.EMPTY) } }

    // 1. Bottom floor
    for (c in 0 until cols) {
        grid[rows - 1][c] = Cell(CellType.WALL)
    }

    // 2. Left and right boundary walls
    for (r in 0 until rows - 1) {
        grid[r][0] = Cell(CellType.WALL)
        grid[r][cols - 1] = Cell(CellType.WALL)
    }

    // 3. Channel splitting
    val channelWidth = 3
    val dividerCols = mutableListOf<Int>()
    var col = 0
    while (col < cols) {
        dividerCols.add(col)
        col += channelWidth
    }
    if (dividerCols.isEmpty() || dividerCols.last() != cols - 1) {
        dividerCols.add(cols - 1)
    }

    // Draw vertical divider walls (from top to row rows-3)
    for (divCol in dividerCols) {
        if (divCol == 0 || divCol == cols - 1) continue
        for (r in 0 until rows - 2) {
            grid[r][divCol] = Cell(CellType.WALL)
        }
    }

    // Identify channels (ranges of columns)
    val channels = mutableListOf<IntRange>()
    for (i in 0 until dividerCols.size - 1) {
        val start = dividerCols[i] + 1
        val end = dividerCols[i + 1] - 1
        if (start <= end) {
            channels.add(start..end)
        }
    }

    // 4. Place Cups at rows-2 in the center of each channel
    for (i in channels.indices) {
        val range = channels[i]
        val cupCol = range.first + (range.last - range.first) / 2
        val color = (i % 8) + 1 // Cycle color indexes 1 to 8
        grid[rows - 2][cupCol] = Cell(CellType.CUP, color)
    }

    // 5. Populate compartments inside each channel
    for (i in channels.indices) {
        val range = channels[i]
        val cupCol = range.first + (range.last - range.first) / 2
        val color = (i % 8) + 1

        // Place a pin right above the cup to block it completely
        val cupPinRow = rows - 3
        for (c in range) {
            grid[cupPinRow][c] = Cell(CellType.PIN)
        }

        // Decide if this channel should have grey balls
        val hasGreyBalls = when (difficulty) {
            "Easy" -> false // Keep easy levels straightforward
            else -> true
        }

        if (hasGreyBalls) {
            // Place grey balls above the cup pin
            val numGrey = random.nextInt(3) + 2
            val greyStartRow = cupPinRow - 1
            for (offset in 0 until numGrey) {
                val r = greyStartRow - offset
                if (r > 3) {
                    for (c in range) {
                        grid[r][c] = Cell(CellType.BALL, color = 0)
                    }
                }
            }

            // Place a separating pin above the grey balls
            val sepPinRow = greyStartRow - numGrey
            if (sepPinRow > 2) {
                for (c in range) {
                    grid[sepPinRow][c] = Cell(CellType.PIN)
                }

                // Place colored balls above the separating pin
                val numCol = random.nextInt(3) + 2
                val colStartRow = sepPinRow - 1
                for (offset in 0 until numCol) {
                    val r = colStartRow - offset
                    if (r > 1) {
                        for (c in range) {
                            grid[r][c] = Cell(CellType.BALL, color = color)
                        }
                    }
                }
            }
        } else {
            // Place colored balls directly above the cup pin
            val numCol = random.nextInt(4) + 2
            val colStartRow = cupPinRow - 1
            for (offset in 0 until numCol) {
                val r = colStartRow - offset
                if (r > 1) {
                    for (c in range) {
                        grid[r][c] = Cell(CellType.BALL, color = color)
                    }
                }
            }
        }
    }

    // 6. Connect channels using side gates/pins inside divider walls
    for (divCol in dividerCols) {
        if (divCol == 0 || divCol == cols - 1) continue

        val possibleRows = (2 until rows - 4).toList()
        if (possibleRows.isNotEmpty()) {
            val numGatedPins = when (difficulty) {
                "Easy" -> 1
                "Medium" -> random.nextInt(2) + 1
                "Hard" -> random.nextInt(3) + 1
                else -> random.nextInt(4) + 1
            }
            
            val selectedRows = possibleRows.shuffled(random).take(numGatedPins)
            for (r in selectedRows) {
                grid[r][divCol] = Cell(CellType.PIN)
            }
        }
    }

    return PullPinLevel(
        id = id,
        difficulty = difficulty,
        rows = rows,
        cols = cols,
        grid = grid.map { it.toList() }
    )
}

object PullPinPregenerated {
    private fun generateLevels(difficulty: String, count: Int, rows: Int, cols: Int): List<PullPinLevel> {
        return List(count) { index ->
            val id = "pullpin_${difficulty.lowercase()}_${(index + 1).toString().padStart(3, '0')}"
            val seed = difficulty.hashCode().toLong() * 31 + index.toLong() * 997
            generateLevel(id, difficulty, rows, cols, seed)
        }
    }

    val ALL_LEVELS: List<PullPinLevel> by lazy {
        generateLevels("Easy", 10, 18, 10) +
        generateLevels("Medium", 10, 27, 15) +
        generateLevels("Hard", 10, 36, 20) +
        generateLevels("Expert", 10, 44, 25)
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PullPinLevel>> by lazy {
        ALL_LEVELS.groupBy { it.difficulty }
    }
}
