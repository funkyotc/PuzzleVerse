package com.funkyotc.puzzleverse.watersort.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import kotlin.random.Random

data class PregeneratedWaterSortLevel(
    override val id: String,
    override val difficulty: String,
    val bottles: List<List<Int>>,
    val numColors: Int,
    val height: Int
) : BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String get() = "$numColors colors, ${bottles.size} bottles"
}

object WaterSortPregenerated {

    val ALL_LEVELS: List<PregeneratedWaterSortLevel> by lazy { generateLevels() }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedWaterSortLevel>> by lazy {
        ALL_LEVELS.groupBy { it.difficulty }
    }

    fun getPuzzleById(id: String): PregeneratedWaterSortLevel? = ALL_LEVELS.find { it.id == id }

    fun getRandomPuzzle(difficulty: String? = null): PregeneratedWaterSortLevel {
        val pool = if (difficulty != null) PUZZLES_BY_DIFFICULTY[difficulty] else ALL_LEVELS
        return pool?.random() ?: ALL_LEVELS.first()
    }

    /**
     * Generates levels by construction: each color gets a complete stack of [height]
     * layers, which are then shuffled across the filled bottles. Because the reverse
     * (un-pour) is always possible, every generated puzzle is guaranteed solvable.
     */
    private fun generateLevels(): List<PregeneratedWaterSortLevel> {
        val levels = mutableListOf<PregeneratedWaterSortLevel>()
        var seed = 1

        fun addBatch(difficulty: String, specs: List<Triple<Int, Int, Int>>) {
            for ((numColors, height, emptyBottles) in specs) {
                // Generate a few varied shuffles per spec to add variety.
                val variants = if (difficulty == "Easy") 4 else 3
                repeat(variants) {
                    levels.add(
                        makeLevel(difficulty, numColors, height, emptyBottles, seed++)
                    )
                }
            }
        }

        addBatch("Easy", listOf(
            Triple(3, 4, 1),
            Triple(3, 4, 2),
            Triple(4, 4, 1),
            Triple(4, 4, 2),
        ))
        addBatch("Medium", listOf(
            Triple(5, 4, 2),
            Triple(5, 5, 2),
            Triple(6, 4, 2),
            Triple(6, 5, 2),
        ))
        addBatch("Hard", listOf(
            Triple(7, 5, 2),
            Triple(7, 5, 3),
            Triple(8, 5, 2),
            Triple(8, 5, 3),
        ))
        addBatch("Expert", listOf(
            Triple(9, 5, 2),
            Triple(9, 6, 2),
            Triple(10, 5, 3),
            Triple(10, 6, 3),
        ))
        addBatch("Nightmare", listOf(
            Triple(11, 6, 3),
            Triple(11, 6, 4),
            Triple(12, 6, 3),
            Triple(12, 6, 4),
        ))

        return levels
    }

    private fun makeLevel(
        difficulty: String,
        numColors: Int,
        height: Int,
        emptyBottles: Int,
        seed: Int
    ): PregeneratedWaterSortLevel {
        val id = "watersort_${difficulty.lowercase()}_${seed.toString().padStart(3, '0')}"
        val rand = Random(seed * 2654435761L)

        // Build the pool of all layers: each color appears exactly `height` times.
        val pool = mutableListOf<Int>()
        repeat(numColors) { color ->
            repeat(height) { pool.add(color) }
        }
        // A Fisher-Yates-ish shuffle via Kotlin's shuffled for determinism.
        val shuffled = pool.shuffled(rand)

        // Distribute the shuffled layers into the filled bottles.
        val filledCount = numColors
        val bottles = mutableListOf<List<Int>>()
        var cursor = 0
        repeat(filledCount) {
            val slice = shuffled.subList(cursor, cursor + height)
            cursor += height
            bottles.add(slice.toList())
        }
        repeat(emptyBottles) { bottles.add(emptyList()) }

        return PregeneratedWaterSortLevel(id, difficulty, bottles, numColors, height)
    }
}
