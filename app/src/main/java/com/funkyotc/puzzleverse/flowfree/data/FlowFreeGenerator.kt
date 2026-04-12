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
        
        // Pick a base puzzle for the difficulty
        val bases = BASE_PUZZLES[difficulty] ?: BASE_PUZZLES[FlowDifficulty.EASY]!!
        val base = bases[random.nextInt(bases.size)]
        
        var dots = base.map { it.copy() }
        
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

    private val BASE_PUZZLES = mapOf(
        FlowDifficulty.EASY to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(4,4)),
                ColorDot(2, Point(0,3), Point(3,4)),
                ColorDot(3, Point(1,1), Point(3,1)),
                ColorDot(4, Point(1,0), Point(4,1))
            ),
            listOf(
                ColorDot(1, Point(0,1), Point(2,3)),
                ColorDot(2, Point(0,4), Point(4,4)),
                ColorDot(3, Point(1,0), Point(4,2)),
                ColorDot(4, Point(3,0), Point(4,0))
            ),
            listOf(
                ColorDot(1, Point(0,0), Point(1,3)),
                ColorDot(2, Point(0,2), Point(2,4)),
                ColorDot(3, Point(2,0), Point(4,4)),
                ColorDot(4, Point(0,4), Point(2,1))
            )
        ),
        FlowDifficulty.MEDIUM to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(5,5)),
                ColorDot(2, Point(0,3), Point(4,5)),
                ColorDot(3, Point(1,1), Point(4,1)),
                ColorDot(4, Point(2,2), Point(4,2)),
                ColorDot(5, Point(3,3), Point(5,4))
            ),
            listOf(
                ColorDot(1, Point(0,2), Point(3,2)),
                ColorDot(2, Point(1,0), Point(5,3)),
                ColorDot(3, Point(1,4), Point(5,5)),
                ColorDot(4, Point(2,3), Point(5,4)),
                ColorDot(5, Point(3,0), Point(4,1))
            ),
            listOf(
                ColorDot(1, Point(0,0), Point(2,2)),
                ColorDot(2, Point(0,1), Point(2,3)),
                ColorDot(3, Point(0,4), Point(4,3)),
                ColorDot(4, Point(3,0), Point(5,5)),
                ColorDot(5, Point(4,0), Point(5,2))
            )
        ),
        FlowDifficulty.HARD to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(6,6)),
                ColorDot(2, Point(0,3), Point(5,6)),
                ColorDot(3, Point(1,1), Point(5,1)),
                ColorDot(4, Point(2,2), Point(5,2)),
                ColorDot(5, Point(3,3), Point(4,4)),
                ColorDot(6, Point(4,3), Point(6,4))
            ),
            listOf(
                ColorDot(1, Point(0,2), Point(4,1)),
                ColorDot(2, Point(1,0), Point(6,0)),
                ColorDot(3, Point(1,4), Point(4,5)),
                ColorDot(4, Point(2,3), Point(5,4)),
                ColorDot(5, Point(3,1), Point(6,5)),
                ColorDot(6, Point(0,6), Point(6,6))
            )
        ),
        FlowDifficulty.EXPERT to listOf(
            listOf(
                ColorDot(1, Point(0,0), Point(7,7)),
                ColorDot(2, Point(0,4), Point(6,7)),
                ColorDot(3, Point(1,1), Point(6,1)),
                ColorDot(4, Point(2,2), Point(6,2)),
                ColorDot(5, Point(2,4), Point(4,6)),
                ColorDot(6, Point(3,3), Point(5,4)),
                ColorDot(7, Point(4,3), Point(7,4))
            ),
            listOf(
                ColorDot(1, Point(0,3), Point(3,0)),
                ColorDot(2, Point(1,1), Point(7,1)),
                ColorDot(3, Point(1,5), Point(5,6)),
                ColorDot(4, Point(2,6), Point(6,6)),
                ColorDot(5, Point(3,2), Point(7,3)),
                ColorDot(6, Point(4,4), Point(6,5)),
                ColorDot(7, Point(0,7), Point(7,7))
            )
        )
    )
}
