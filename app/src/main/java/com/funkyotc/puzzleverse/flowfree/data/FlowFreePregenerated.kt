package com.funkyotc.puzzleverse.flowfree.data


data class PregeneratedPuzzle(
    val id: String,
    val size: Int,
    val difficulty: String,
    val dots: List<ColorDot>
)

object FlowFreePregenerated {

    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(
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
