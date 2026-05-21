package com.funkyotc.puzzleverse.shikaku.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.shikaku.generator.ShikakuGenerator

data class PregeneratedShikaku(
    override val id: String,
    override val difficulty: String,
    val clueValue: Int,
    val clueRow: Int,
    val clueCol: Int,
    val grid: List<List<Int>>,
    val seed: Long
) : BrowseablePuzzle {
    override val label: String get() = "Puzzle"
    override val subtitle: String get() = "${grid.size}x${grid.size}"
}

object ShikakuPregenerated {

    private val ALL_PUZZLES: List<PregeneratedShikaku> by lazy {
        val puzzles = mutableListOf<PregeneratedShikaku>()

        // Generate puzzles using deterministic seeds
        val puzzleConfigs = listOf(
            // Easy 8x8 puzzles
            "easy_001" to 1000001L,
            "easy_002" to 1000002L,
            "easy_003" to 1000003L,
            "easy_004" to 1000004L,
            "easy_005" to 1000005L,
            "easy_006" to 1000006L,
            "easy_007" to 1000007L,
            "easy_008" to 1000008L,
            "easy_009" to 1000009L,
            "easy_010" to 1000010L,
            // Medium 10x10 puzzles
            "medium_001" to 2000001L,
            "medium_002" to 2000002L,
            "medium_003" to 2000003L,
            "medium_004" to 2000004L,
            "medium_005" to 2000005L,
            "medium_006" to 2000006L,
            "medium_007" to 2000007L,
            "medium_008" to 2000008L,
            "medium_009" to 2000009L,
            "medium_010" to 2000010L,
            // Hard 12x12 puzzles
            "hard_001" to 3000001L,
            "hard_002" to 3000002L,
            "hard_003" to 3000003L,
            "hard_004" to 3000004L,
            "hard_005" to 3000005L,
            "hard_006" to 3000006L,
            "hard_007" to 3000007L,
            "hard_008" to 3000008L,
            "hard_009" to 3000009L,
            "hard_010" to 3000010L,
        )

        for ((id, seed) in puzzleConfigs) {
            val generator = ShikakuGenerator(seed)
            val difficulty = when {
                seed in 1000001L..1000010L -> "easy"
                seed in 2000001L..2000010L -> "medium"
                seed in 3000001L..3000010L -> "hard"
                else -> "easy"
            }
            val board = generator.generate(difficulty)
            val solutionRects = board.getSolutionRectangles()

            if (solutionRects.isNotEmpty()) {
                // Use the first rectangle's clue info
                val solutionCell = board.cells.find { it.rectangleId == solutionRects.first().id && it.clue != null }
                solutionCell?.let { cell ->
                    // Build grid representation
                    val gridSize = board.gridSize
                    val grid = mutableListOf<MutableList<Int>>()
                    for (r in 0 until gridSize) {
                        grid.add(mutableListOf())
                        for (c in 0 until gridSize) {
                            val boardCell = board.getCell(r, c)
                            grid[r].add(boardCell?.rectangleId?.hashCode() ?: 0)
                        }
                    }

                    puzzles.add(PregeneratedShikaku(
                        id = id,
                        difficulty = difficulty.replaceFirstChar { it.uppercaseChar() },
                        clueValue = cell.clue ?: 0,
                        clueRow = cell.row,
                        clueCol = cell.col,
                        grid = grid.map { it.toList() },
                        seed = seed
                    ))
                }
            }
        }

        puzzles
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShikaku>> by lazy {
        ALL_PUZZLES.groupBy { it.difficulty.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }
    }

    fun getPuzzleById(id: String): PregeneratedShikaku? = ALL_PUZZLES.find { it.id == id }

    fun getRandomPuzzle(difficulty: String): PregeneratedShikaku? {
        return PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()
    }
}
