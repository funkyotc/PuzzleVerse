package com.funkyotc.puzzleverse.bonza.generator

import androidx.compose.ui.geometry.Offset
import com.funkyotc.puzzleverse.bonza.data.BonzaConnection
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.data.WordFragment
import kotlin.random.Random

class BonzaPuzzleGenerator {

    private val puzzles = listOf(
        generateFruitsPuzzle()
    )

    private fun generateFruitsPuzzle(): BonzaPuzzle {
        val fragments = listOf(
            WordFragment(1, "APP", Offset(100f, 200f)),
            WordFragment(2, "LE", Offset(340f, 200f)),
            WordFragment(3, "GR", Offset(100f, 400f)),
            WordFragment(4, "APE", Offset(260f, 400f)),
            WordFragment(5, "PEAR", Offset(260f, 280f), direction = ConnectionDirection.VERTICAL) // Intersects with APE
        )

        val solvedFragments = listOf(
            WordFragment(1, "APP", Offset(100f, 200f), currentPosition = Offset(100f, 200f)),
            WordFragment(2, "LE", Offset(340f, 200f), currentPosition = Offset(340f, 200f)),
            WordFragment(3, "GR", Offset(100f, 400f), currentPosition = Offset(100f, 400f)),
            WordFragment(4, "APE", Offset(260f, 400f), currentPosition = Offset(260f, 400f)),
            WordFragment(5, "PEAR", Offset(260f, 280f), currentPosition = Offset(260f, 280f), direction = ConnectionDirection.VERTICAL)
        )

        return BonzaPuzzle(
            theme = "Fruits",
            words = listOf("APPLE", "GRAPE", "PEAR"),
            fragments = fragments,
            connections = listOf(
                BonzaConnection(1, 2, ConnectionDirection.HORIZONTAL), // APP-LE
                BonzaConnection(3, 4, ConnectionDirection.HORIZONTAL), // GR-APE
                BonzaConnection(4, 5, ConnectionDirection.VERTICAL) // APE intersects PEAR
            ),
            solvedFragments = solvedFragments
        )
    }

    fun generate(seed: Long = Random.nextLong()): BonzaPuzzle {
        return puzzles[0]
    }
}
