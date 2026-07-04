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

    private fun generateLevels(): List<PregeneratedWaterSortLevel> {
        val levels = mutableListOf<PregeneratedWaterSortLevel>()

        levels.addAll(easyLevels())
        levels.addAll(mediumLevels())
        levels.addAll(hardLevels())

        return levels
    }

    private fun easyLevels(): List<PregeneratedWaterSortLevel> {
        return listOf(
            PregeneratedWaterSortLevel("watersort_easy_001", "Easy", listOf(
                listOf(0, 0, 1, 1),
                listOf(1, 1, 0, 0),
                listOf()
            ), 2, 4),
            PregeneratedWaterSortLevel("watersort_easy_002", "Easy", listOf(
                listOf(0, 0, 0, 1),
                listOf(1, 1, 1, 0),
                listOf()
            ), 2, 4),
            PregeneratedWaterSortLevel("watersort_easy_003", "Easy", listOf(
                listOf(0, 1, 1, 0),
                listOf(1, 0, 0, 1),
                listOf()
            ), 2, 4),
            PregeneratedWaterSortLevel("watersort_easy_004", "Easy", listOf(
                listOf(0, 1, 0, 1),
                listOf(1, 0, 1, 0),
                listOf()
            ), 2, 4),
            PregeneratedWaterSortLevel("watersort_easy_005", "Easy", listOf(
                listOf(0, 0, 1, 0),
                listOf(1, 1, 0, 1),
                listOf()
            ), 2, 4),
        )
    }

    private fun mediumLevels(): List<PregeneratedWaterSortLevel> {
        return listOf(
            PregeneratedWaterSortLevel("watersort_medium_001", "Medium", listOf(
                listOf(0, 0, 1, 2),
                listOf(1, 1, 2, 0),
                listOf(2, 2, 0, 1),
                listOf(),
                listOf()
            ), 3, 4),
            PregeneratedWaterSortLevel("watersort_medium_002", "Medium", listOf(
                listOf(0, 1, 2, 0),
                listOf(1, 2, 0, 1),
                listOf(2, 0, 1, 2),
                listOf(),
                listOf()
            ), 3, 4),
            PregeneratedWaterSortLevel("watersort_medium_003", "Medium", listOf(
                listOf(0, 0, 2, 1),
                listOf(1, 1, 0, 2),
                listOf(2, 2, 1, 0),
                listOf(),
                listOf()
            ), 3, 4),
            PregeneratedWaterSortLevel("watersort_medium_004", "Medium", listOf(
                listOf(0, 2, 1, 0),
                listOf(1, 0, 2, 1),
                listOf(2, 1, 0, 2),
                listOf(),
                listOf()
            ), 3, 4),
            PregeneratedWaterSortLevel("watersort_medium_005", "Medium", listOf(
                listOf(0, 1, 1, 0),
                listOf(1, 2, 2, 1),
                listOf(2, 0, 0, 2),
                listOf(),
                listOf()
            ), 3, 4),
        )
    }

    private fun hardLevels(): List<PregeneratedWaterSortLevel> {
        return listOf(
            PregeneratedWaterSortLevel("watersort_hard_001", "Hard", listOf(
                listOf(0, 0, 1, 2, 3),
                listOf(1, 1, 2, 3, 0),
                listOf(2, 2, 3, 0, 1),
                listOf(3, 3, 0, 1, 2),
                listOf(),
                listOf()
            ), 4, 5),
            PregeneratedWaterSortLevel("watersort_hard_002", "Hard", listOf(
                listOf(0, 1, 2, 3, 0),
                listOf(1, 2, 3, 0, 1),
                listOf(2, 3, 0, 1, 2),
                listOf(3, 0, 1, 2, 3),
                listOf(),
                listOf()
            ), 4, 5),
            PregeneratedWaterSortLevel("watersort_hard_003", "Hard", listOf(
                listOf(0, 3, 2, 1, 0),
                listOf(1, 0, 3, 2, 1),
                listOf(2, 1, 0, 3, 2),
                listOf(3, 2, 1, 0, 3),
                listOf(),
                listOf()
            ), 4, 5),
            PregeneratedWaterSortLevel("watersort_hard_004", "Hard", listOf(
                listOf(0, 1, 2, 3, 4),
                listOf(1, 2, 3, 4, 0),
                listOf(2, 3, 4, 0, 1),
                listOf(3, 4, 0, 1, 2),
                listOf(4, 0, 1, 2, 3),
                listOf(),
                listOf()
            ), 5, 5),
            PregeneratedWaterSortLevel("watersort_hard_005", "Hard", listOf(
                listOf(0, 4, 3, 2, 1),
                listOf(1, 0, 4, 3, 2),
                listOf(2, 1, 0, 4, 3),
                listOf(3, 2, 1, 0, 4),
                listOf(4, 3, 2, 1, 0),
                listOf(),
                listOf()
            ), 5, 5),
        )
    }
}
