package com.funkyotc.puzzleverse.hashi.data

import com.funkyotc.puzzleverse.hashi.generator.HashiGenerator

object HashiPregenerated {

    val EASY_PUZZLES: List<HashiPuzzle> by lazy {
        (1..10).map { idx ->
            HashiGenerator(seed = 1000L + idx).generate("Easy")
        }
    }

    val MEDIUM_PUZZLES: List<HashiPuzzle> by lazy {
        (1..10).map { idx ->
            HashiGenerator(seed = 2000L + idx).generate("Medium")
        }
    }

    val HARD_PUZZLES: List<HashiPuzzle> by lazy {
        (1..10).map { idx ->
            HashiGenerator(seed = 3000L + idx).generate("Hard")
        }
    }

    val EXPERT_PUZZLES: List<HashiPuzzle> by lazy {
        (1..10).map { idx ->
            HashiGenerator(seed = 4000L + idx).generate("Expert")
        }
    }

    val ALL_PUZZLES: List<HashiPuzzle> by lazy {
        EASY_PUZZLES + MEDIUM_PUZZLES + HARD_PUZZLES + EXPERT_PUZZLES
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<HashiPuzzle>> by lazy {
        mapOf(
            "Easy" to EASY_PUZZLES,
            "Medium" to MEDIUM_PUZZLES,
            "Hard" to HARD_PUZZLES,
            "Expert" to EXPERT_PUZZLES
        )
    }

    fun getPuzzle(id: String): HashiPuzzle? {
        return ALL_PUZZLES.find { it.id == id }
    }
}
