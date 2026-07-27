package com.funkyotc.puzzleverse.flowfree.data

/**
 * Provides puzzle retrieval from pregenerated Flow Free puzzles.
 */
object FlowFreePuzzleLibrary {

    fun getRandomPuzzle(difficulty: FlowDifficulty = FlowDifficulty.EASY): PregeneratedPuzzle {
        return FlowFreePregenerated.getRandomPuzzle(difficulty)
            ?: FlowFreePregenerated.ALL_PUZZLES.first()
    }

    fun getRandomPuzzlePair(): Pair<Int, List<ColorDot>> {
        val puzzle = getRandomPuzzle(FlowDifficulty.EASY)
        return Pair(puzzle.size, puzzle.dots)
    }

    fun getDailyPuzzle(seed: Long): PregeneratedPuzzle {
        val puzzles = FlowFreePregenerated.ALL_PUZZLES
        val index = (seed % puzzles.size).let {
            if (it < 0) (it + puzzles.size).toInt() else it.toInt()
        }
        return puzzles[index]
    }
}
