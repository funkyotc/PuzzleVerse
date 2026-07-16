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
        ),
        HashiPuzzle(
            id = "easy_2",
            width = 7,
            height = 7,
            islands = listOf(
                Island(1, 1, 2), Island(5, 1, 2),
                Island(1, 5, 2), Island(5, 5, 2)
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
        ),
        HashiPuzzle(
            id = "medium_2",
            width = 10,
            height = 10,
            islands = listOf(
                Island(1, 1, 2), Island(1, 8, 3), Island(8, 1, 3), Island(8, 8, 2),
                Island(4, 4, 4), Island(4, 8, 3), Island(8, 4, 3)
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
        ),
        HashiPuzzle(
            id = "hard_2",
            width = 15,
            height = 15,
            islands = listOf(
                Island(1, 1, 3), Island(1, 13, 3),
                Island(13, 1, 3), Island(13, 13, 3),
                Island(7, 7, 4)
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
