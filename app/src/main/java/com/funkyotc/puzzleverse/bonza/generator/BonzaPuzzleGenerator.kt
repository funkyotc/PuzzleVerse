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
            fragments = listOf(), // Will be filled in generate()
            connections = listOf(
                BonzaConnection(1, 2, ConnectionDirection.HORIZONTAL), // APP-LE
                BonzaConnection(3, 4, ConnectionDirection.HORIZONTAL), // GR-APE
                BonzaConnection(4, 5, ConnectionDirection.VERTICAL) // APE intersects PEAR
            ),
            solvedFragments = solvedFragments
        )
    }

    fun generate(seed: Long = Random.nextLong()): BonzaPuzzle {
        val random = Random(seed)
        val basePuzzle = puzzles[0]
        
        // Generate random starting positions for fragments
        // Use a larger area to scatter them around
        val minX = 50f
        val maxX = 800f
        val minY = 100f
        val maxY = 1200f
        
        val shuffledFragments = basePuzzle.solvedFragments.map { solvedFragment ->
            val randomX = random.nextFloat() * (maxX - minX) + minX
            val randomY = random.nextFloat() * (maxY - minY) + minY
            
            WordFragment(
                id = solvedFragment.id,
                text = solvedFragment.text,
                initialPosition = Offset(randomX, randomY),
                currentPosition = Offset(randomX, randomY),
                direction = solvedFragment.direction
            )
        }
        
        return basePuzzle.copy(fragments = shuffledFragments)
    }
}
