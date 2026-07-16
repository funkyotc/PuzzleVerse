package com.funkyotc.puzzleverse.hashi.data

object HashiPregenerated {

    val EASY_PUZZLES = listOf(
        HashiPuzzle(
            id = "easy_1",
            width = 7,
            height = 7,
            islands = listOf(
                Island(0, 0, 2), Island(6, 0, 2),
                Island(0, 6, 2), Island(6, 6, 2)
            )
        )
    )

    val MEDIUM_PUZZLES = listOf(
        HashiPuzzle(
            id = "medium_1",
            width = 10,
            height = 10,
            islands = listOf(
                Island(0, 0, 3), Island(0, 4, 3), Island(0, 9, 2),
                Island(4, 0, 4), Island(4, 4, 6), Island(4, 9, 3),
                Island(9, 0, 3), Island(9, 4, 4), Island(9, 9, 2)
            )
        )
    )

    val HARD_PUZZLES = listOf(
        HashiPuzzle(
            id = "hard_1",
            width = 15,
            height = 15,
            islands = listOf(
                Island(0, 0, 2), Island(14, 0, 2),
                Island(0, 14, 2), Island(14, 14, 2)
            )
        )
    )

    val PUZZLES_BY_DIFFICULTY = mapOf(
        "Easy" to EASY_PUZZLES,
        "Medium" to MEDIUM_PUZZLES,
        "Hard" to HARD_PUZZLES
    )

    fun getPuzzle(id: String): HashiPuzzle? {
        return EASY_PUZZLES.find { it.id == id }
            ?: MEDIUM_PUZZLES.find { it.id == id }
            ?: HARD_PUZZLES.find { it.id == id }
    }
}
