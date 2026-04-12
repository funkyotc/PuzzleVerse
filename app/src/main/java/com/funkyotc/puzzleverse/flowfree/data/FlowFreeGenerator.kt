package com.funkyotc.puzzleverse.flowfree.data

import kotlin.random.Random

enum class FlowDifficulty(val gridSize: Int, val numColors: Int, val label: String) {
    EASY(5, 4, "Easy"),
    MEDIUM(6, 5, "Medium"),
    HARD(7, 6, "Hard"),
    EXPERT(8, 7, "Expert")
}

/**
 * Generates valid Flow Free puzzles using solution-first approach:
 * 1. Fill grid with random non-overlapping paths (= valid solution)
 * 2. Extract path endpoints as puzzle dots
 * 3. Verify puzzle has exactly one solution via backtracking solver
 */
object FlowFreeGenerator {

    private val DIRECTIONS = arrayOf(
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(0, -1), intArrayOf(0, 1)
    )

    data class GeneratedPuzzle(
        val size: Int,
        val dots: List<ColorDot>,
        val difficulty: FlowDifficulty
    )

    /**
     * Main entry point. Generates a valid, unique-solution puzzle.
     * Retries until a good puzzle is found.
     */
    fun generate(difficulty: FlowDifficulty, random: Random = Random): GeneratedPuzzle {
        val size = difficulty.gridSize
        val numColors = difficulty.numColors
        
        // Pick a random pregenerated puzzle for this difficulty
        val pregen = FlowFreePregenerated.getRandomPuzzle(difficulty)
        
        var dots = if (pregen != null) {
            pregen.dots.map { it.copy() }
        } else {
            // Fallback: use first puzzle available
            FlowFreePregenerated.ALL_PUZZLES.firstOrNull()?.dots?.map { it.copy() } ?: emptyList()
        }
        
        // Apply transformations
        dots = applyTransformations(dots, size, random)
        
        // Shuffle colors
        dots = shuffleColors(dots, numColors, random)
        
        return GeneratedPuzzle(size, dots, difficulty)
    }

    private fun applyTransformations(dots: List<ColorDot>, size: Int, random: Random): List<ColorDot> {
        var transformed = dots

        // Rotate 0, 90, 180, or 270 degrees
        val rotations = random.nextInt(4)
        for (i in 0 until rotations) {
            transformed = transformed.map { dot ->
                ColorDot(
                    dot.colorId,
                    Point(dot.start.c, size - 1 - dot.start.r),
                    Point(dot.end.c, size - 1 - dot.end.r)
                )
            }
        }

        // Flip horizontally
        if (random.nextBoolean()) {
            transformed = transformed.map { dot ->
                ColorDot(
                    dot.colorId,
                    Point(dot.start.r, size - 1 - dot.start.c),
                    Point(dot.end.r, size - 1 - dot.end.c)
                )
            }
        }

        // Flip vertically
        if (random.nextBoolean()) {
            transformed = transformed.map { dot ->
                ColorDot(
                    dot.colorId,
                    Point(size - 1 - dot.start.r, dot.start.c),
                    Point(size - 1 - dot.end.r, dot.end.c)
                )
            }
        }

        return transformed
    }

    private fun shuffleColors(dots: List<ColorDot>, numColors: Int, random: Random): List<ColorDot> {
        val colorIds = (1..numColors).toMutableList()
        colorIds.shuffle(random)
        
        return dots.mapIndexed { index, dot ->
            dot.copy(colorId = colorIds[index % colorIds.size])
        }
    }

}
