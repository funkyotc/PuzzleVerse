package com.funkyotc.puzzleverse.constellations.generator

import com.funkyotc.puzzleverse.constellations.data.Connection
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import com.funkyotc.puzzleverse.constellations.data.Star

class ConstellationsPuzzleGenerator {
    fun generate(): ConstellationsPuzzle {
        // A simple house shape
        val stars = listOf(
            Star(0, 0.5f, 0.1f), // Peak of the roof
            Star(1, 0.2f, 0.4f), // Left corner of roof
            Star(2, 0.8f, 0.4f), // Right corner of roof
            Star(3, 0.2f, 0.9f), // Bottom-left corner of house
            Star(4, 0.8f, 0.9f)  // Bottom-right corner of house
        )

        val connections = listOf(
            Connection(0, 1),
            Connection(0, 2),
            Connection(1, 2),
            Connection(1, 3),
            Connection(2, 4),
            Connection(3, 4)
        )

        return ConstellationsPuzzle(stars, connections)
    }
}
