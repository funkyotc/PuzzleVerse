package com.funkyotc.puzzleverse.watersort.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import kotlin.random.Random

data class PregeneratedWaterSortLevel(
    override val id: String,
    override val difficulty: String,
    val bottles: List<List<Int>>,
    val numColors: Int,
    val height: Int,
    /** Pours that solve this level; each pair is a source and destination bottle index. */
    val winningMoves: List<Pair<Int, Int>>
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

    /** Scramble solved bottles only with moves whose inverse is a legal one-layer pour. */
    private fun generateLevels(): List<PregeneratedWaterSortLevel> {
        val levels = mutableListOf<PregeneratedWaterSortLevel>()
        var seed = 1

        fun addBatch(difficulty: String, specs: List<Triple<Int, Int, Int>>) {
            for ((numColors, height, emptyBottles) in specs) {
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

        val bottles = MutableList(numColors + emptyBottles) { index ->
            if (index < numColors) MutableList(height) { index } else mutableListOf()
        }
        val reverseMoves = mutableListOf<Pair<Int, Int>>()
        val targetMoves = numColors * height * 4
        repeat(targetMoves) {
            val choices = buildList {
                for (from in bottles.indices) for (to in bottles.indices) {
                    if (from == to || bottles[from].isEmpty() || bottles[to].size == height) continue
                    val color = bottles[from].last()
                    // The inverse will pour back onto this bottle. It must be
                    // empty or still have the same top color after removing one.
                    if (bottles[from].size > 1 && bottles[from][bottles[from].lastIndex - 1] != color) continue
                    // A distinct destination top keeps the moved group at size one.
                    if (bottles[to].lastOrNull() == color) continue
                    add(from to to)
                }
            }
            if (choices.isEmpty()) return@repeat
            val (from, to) = choices.random(rand)
            bottles[to].add(bottles[from].removeAt(bottles[from].lastIndex))
            reverseMoves.add(to to from)
        }
        if (reverseMoves.isEmpty()) error("Could not scramble $id")
        return PregeneratedWaterSortLevel(id, difficulty, bottles.map { it.toList() },
            numColors, height, reverseMoves.asReversed())
    }
}
