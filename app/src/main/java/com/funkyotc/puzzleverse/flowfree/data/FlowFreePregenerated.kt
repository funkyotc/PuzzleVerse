package com.funkyotc.puzzleverse.flowfree.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedPuzzle(
    override val id: String,
    val size: Int,
    override val difficulty: String,
    val dots: List<ColorDot>
) : BrowseablePuzzle {
    override val label: String get() = "Puzzle"
    override val subtitle: String get() = "${size}x${size}"
}

object FlowFreePregenerated {

    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(
        // Easy puzzles (5x5) - Valid non-crossing paths
        PregeneratedPuzzle("flow_easy_001", 5, "Easy", listOf(
            ColorDot(1, Point(0, 0), Point(4, 0)),
            ColorDot(2, Point(0, 1), Point(4, 1)),
            ColorDot(3, Point(0, 2), Point(4, 2)),
            ColorDot(4, Point(0, 3), Point(4, 3)),
            ColorDot(5, Point(0, 4), Point(4, 4))
        )),
        PregeneratedPuzzle("flow_easy_002", 5, "Easy", listOf(
            ColorDot(1, Point(0, 0), Point(2, 2)),
            ColorDot(2, Point(1, 0), Point(1, 1)),
            ColorDot(3, Point(2, 0), Point(2, 1)),
            ColorDot(4, Point(3, 0), Point(3, 4)),
            ColorDot(5, Point(4, 0), Point(4, 4)),
            ColorDot(6, Point(0, 3), Point(2, 4))
        )),
        PregeneratedPuzzle("flow_easy_003", 5, "Easy", listOf(
            ColorDot(1, Point(0, 0), Point(0, 4)),
            ColorDot(2, Point(1, 0), Point(1, 4)),
            ColorDot(3, Point(2, 0), Point(2, 4)),
            ColorDot(4, Point(3, 0), Point(3, 4)),
            ColorDot(5, Point(4, 0), Point(4, 4))
        )),
        
        // Medium puzzles (6x6)
        PregeneratedPuzzle("flow_medium_001", 6, "Medium", listOf(
            ColorDot(1, Point(0, 0), Point(5, 0)),
            ColorDot(2, Point(0, 1), Point(5, 1)),
            ColorDot(3, Point(0, 2), Point(5, 2)),
            ColorDot(4, Point(0, 3), Point(5, 3)),
            ColorDot(5, Point(0, 4), Point(5, 4)),
            ColorDot(6, Point(0, 5), Point(5, 5))
        )),
        PregeneratedPuzzle("flow_medium_002", 6, "Medium", listOf(
            ColorDot(1, Point(0, 0), Point(2, 3)),
            ColorDot(2, Point(1, 0), Point(1, 2)),
            ColorDot(3, Point(2, 0), Point(2, 2)),
            ColorDot(4, Point(3, 0), Point(5, 5)),
            ColorDot(5, Point(0, 4), Point(2, 5)),
            ColorDot(6, Point(3, 4), Point(4, 5))
        )),
        
        // Hard puzzles (7x7)
        PregeneratedPuzzle("flow_hard_001", 7, "Hard", listOf(
            ColorDot(1, Point(0, 0), Point(6, 0)),
            ColorDot(2, Point(0, 1), Point(6, 1)),
            ColorDot(3, Point(0, 2), Point(6, 2)),
            ColorDot(4, Point(0, 3), Point(6, 3)),
            ColorDot(5, Point(0, 4), Point(6, 4)),
            ColorDot(6, Point(0, 5), Point(6, 5)),
            ColorDot(7, Point(0, 6), Point(6, 6))
        )),
        PregeneratedPuzzle("flow_hard_002", 7, "Hard", listOf(
            ColorDot(1, Point(0, 0), Point(3, 3)),
            ColorDot(2, Point(1, 0), Point(2, 2)),
            ColorDot(3, Point(4, 0), Point(6, 6)),
            ColorDot(4, Point(0, 4), Point(3, 6)),
            ColorDot(5, Point(4, 3), Point(5, 6)),
            ColorDot(6, Point(0, 1), Point(0, 3)),
            ColorDot(7, Point(6, 0), Point(6, 3))
        )),
        
        // Expert puzzles (8x8)
        PregeneratedPuzzle("flow_expert_001", 8, "Expert", listOf(
            ColorDot(1, Point(0, 0), Point(7, 0)),
            ColorDot(2, Point(0, 1), Point(7, 1)),
            ColorDot(3, Point(0, 2), Point(7, 2)),
            ColorDot(4, Point(0, 3), Point(7, 3)),
            ColorDot(5, Point(0, 4), Point(7, 4)),
            ColorDot(6, Point(0, 5), Point(7, 5)),
            ColorDot(7, Point(0, 6), Point(7, 6)),
            ColorDot(8, Point(0, 7), Point(7, 7))
        )),
        PregeneratedPuzzle("flow_expert_002", 8, "Expert", listOf(
            ColorDot(1, Point(0, 0), Point(4, 4)),
            ColorDot(2, Point(1, 0), Point(3, 3)),
            ColorDot(3, Point(5, 0), Point(7, 7)),
            ColorDot(4, Point(0, 5), Point(4, 7)),
            ColorDot(5, Point(5, 4), Point(6, 7)),
            ColorDot(6, Point(0, 1), Point(0, 4)),
            ColorDot(7, Point(7, 0), Point(7, 4)),
            ColorDot(8, Point(2, 0), Point(2, 3))
        ))
    )

    val PUZZLES_BY_DIFFICULTY: Map<String, List<BrowseablePuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }

    fun getPuzzleById(id: String): PregeneratedPuzzle? = ALL_PUZZLES.find { it.id == id }

    fun getRandomPuzzle(difficulty: FlowDifficulty): PregeneratedPuzzle? {
        val diffName = when(difficulty) {
            FlowDifficulty.EASY -> "Easy"
            FlowDifficulty.MEDIUM -> "Medium"
            FlowDifficulty.HARD -> "Hard"
            FlowDifficulty.EXPERT -> "Expert"
        }
        return ALL_PUZZLES.filter { it.difficulty == diffName }.randomOrNull()
    }
}
