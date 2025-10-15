package com.funkyotc.puzzleverse.bonza.generator

import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.BonzaWord
import kotlin.random.Random

class BonzaPuzzleGenerator {

    private val puzzles = listOf(
        BonzaPuzzle(
            clue = "Android Dev",
            words = listOf(
                BonzaWord("KOTLIN", "KOTLIN".toList(), 0, 0, isHorizontal = true),
                BonzaWord("COMPOSE", "COMPOSE".toList(), 3, -2, isHorizontal = false),
                BonzaWord("ANDROID", "ANDROID".toList(), -2, 2, isHorizontal = true),
                BonzaWord("JETPACK", "JETPACK".toList(), 5, 0, isHorizontal = false)
            )
        )
        // TODO: Add more puzzles
    )

    fun generate(seed: Long = Random.nextLong()): BonzaPuzzle {
        val puzzleIndex = (seed % puzzles.size).toInt()
        return puzzles[puzzleIndex]
    }
}
