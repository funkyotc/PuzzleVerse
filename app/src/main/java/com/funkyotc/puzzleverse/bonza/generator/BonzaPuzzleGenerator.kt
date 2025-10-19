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
        ),
        BonzaPuzzle(
            clue = "Weather",
            words = listOf(
                BonzaWord("RAIN", "RAIN".toList(), 0, 0, isHorizontal = true),
                BonzaWord("WIND", "WIND".toList(), 2, -1, isHorizontal = false),
                BonzaWord("SUN", "SUN".toList(), 0, 1, isHorizontal = true),
                BonzaWord("SNOW", "SNOW".toList(), 0, 1, isHorizontal = false)
            )
        ),
        BonzaPuzzle(
            clue = "Fruits",
            words = listOf(
                BonzaWord("APPLE", "APPLE".toList(), 0, 0, isHorizontal = true),
                BonzaWord("LEMON", "LEMON".toList(), 3, 0, isHorizontal = false),
                BonzaWord("GRAPE", "GRAPE".toList(), -1, 1, isHorizontal = true),
                BonzaWord("PEAR", "PEAR".toList(), 2, 1, isHorizontal = false)
            )
        )
    )

    fun generate(seed: Long = Random.nextLong()): BonzaPuzzle {
        val puzzleIndex = (seed.mod(puzzles.size)).toInt()
        return puzzles[puzzleIndex]
    }
}
