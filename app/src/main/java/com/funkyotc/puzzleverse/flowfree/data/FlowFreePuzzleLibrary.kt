package com.funkyotc.puzzleverse.flowfree.data

import kotlin.random.Random

/**
 * Thin wrapper around FlowFreeGenerator.
 * Provides puzzle retrieval with difficulty selection.
 */
object FlowFreePuzzleLibrary {

    fun getRandomPuzzle(difficulty: FlowDifficulty = FlowDifficulty.EASY): FlowFreeGenerator.GeneratedPuzzle {
        return FlowFreeGenerator.generate(difficulty)
    }

    fun getRandomPuzzle(): Pair<Int, List<ColorDot>> {
        val puzzle = FlowFreeGenerator.generate(FlowDifficulty.EASY)
        return Pair(puzzle.size, puzzle.dots)
    }

    fun getDailyPuzzle(seed: Long): FlowFreeGenerator.GeneratedPuzzle {
        return FlowFreeGenerator.generate(FlowDifficulty.MEDIUM, Random(seed))
    }
}
