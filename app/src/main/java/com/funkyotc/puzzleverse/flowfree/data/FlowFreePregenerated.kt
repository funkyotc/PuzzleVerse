package com.funkyotc.puzzleverse.flowfree.data


data class PregeneratedPuzzle(
    val id: String,
    val size: Int,
    val difficulty: String,
    val dots: List<ColorDot>
)

object FlowFreePregenerated {

    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(
        // Easy puzzles (5x5)
        PregeneratedPuzzle("flow_easy_001", 5, "Easy", listOf(
            ColorDot(1, Point(0, 0), Point(4, 4)),
            ColorDot(2, Point(0, 4), Point(4, 0)),
            ColorDot(3, Point(0, 2), Point(4, 2)),
            ColorDot(4, Point(2, 0), Point(2, 4))
        )),
        PregeneratedPuzzle("flow_easy_002", 5, "Easy", listOf(
            ColorDot(1, Point(0, 0), Point(4, 0)),
            ColorDot(2, Point(0, 1), Point(4, 1)),
            ColorDot(3, Point(0, 3), Point(4, 3)),
            ColorDot(4, Point(0, 4), Point(4, 4))
        )),
        
        // Medium puzzles (6x6)
        PregeneratedPuzzle("flow_medium_001", 6, "Medium", listOf(
            ColorDot(1, Point(0, 0), Point(5, 5)),
            ColorDot(2, Point(0, 5), Point(5, 0)),
            ColorDot(3, Point(0, 2), Point(5, 2)),
            ColorDot(4, Point(0, 3), Point(5, 3)),
            ColorDot(5, Point(2, 0), Point(3, 5))
        )),
        PregeneratedPuzzle("flow_medium_002", 6, "Medium", listOf(
            ColorDot(1, Point(0, 0), Point(5, 0)),
            ColorDot(2, Point(0, 1), Point(5, 1)),
            ColorDot(3, Point(0, 3), Point(5, 3)),
            ColorDot(4, Point(0, 4), Point(5, 4)),
            ColorDot(5, Point(0, 2), Point(5, 5))
        )),
        
        // Hard puzzles (7x7)
        PregeneratedPuzzle("flow_hard_001", 7, "Hard", listOf(
            ColorDot(1, Point(0, 0), Point(6, 6)),
            ColorDot(2, Point(0, 6), Point(6, 0)),
            ColorDot(3, Point(0, 3), Point(6, 3)),
            ColorDot(4, Point(3, 0), Point(3, 6)),
            ColorDot(5, Point(0, 1), Point(6, 1)),
            ColorDot(6, Point(0, 2), Point(6, 5))
        )),
        PregeneratedPuzzle("flow_hard_002", 7, "Hard", listOf(
            ColorDot(1, Point(0, 0), Point(6, 0)),
            ColorDot(2, Point(0, 1), Point(6, 1)),
            ColorDot(3, Point(0, 3), Point(6, 3)),
            ColorDot(4, Point(0, 4), Point(6, 4)),
            ColorDot(5, Point(0, 2), Point(6, 5)),
            ColorDot(6, Point(0, 5), Point(6, 6))
        )),
        
        // Expert puzzles (8x8)
        PregeneratedPuzzle("flow_expert_001", 8, "Expert", listOf(
            ColorDot(1, Point(0, 0), Point(7, 7)),
            ColorDot(2, Point(0, 7), Point(7, 0)),
            ColorDot(3, Point(0, 3), Point(7, 3)),
            ColorDot(4, Point(3, 0), Point(3, 7)),
            ColorDot(5, Point(0, 1), Point(7, 1)),
            ColorDot(6, Point(0, 2), Point(7, 6)),
            ColorDot(7, Point(0, 4), Point(7, 4))
        ))
    )

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedPuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }

    fun getPuzzleById(id: String): PregeneratedPuzzle? = ALL_PUZZLES.find { it.id == id }

    fun getRandomPuzzle(difficulty: FlowDifficulty): PregeneratedPuzzle? {
        val diffName = when(difficulty) {
            FlowDifficulty.EASY -> "Easy"
            FlowDifficulty.MEDIUM -> "Medium"
            FlowDifficulty.HARD -> "Hard"
            FlowDifficulty.EXPERT -> "Expert"
        }
        return PUZZLES_BY_DIFFICULTY[diffName]?.randomOrNull()
    }
}
