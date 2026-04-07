package com.funkyotc.puzzleverse.nonogram.data

object NonogramPuzzleLibrary {
    fun getRandomPuzzle(): List<List<Boolean>> {
        val puzzles = listOf(
            // Heart (10x10)
            listOf(
                listOf(false, false, false, false, false, false, false, false, false, false),
                listOf(false, true, true, false, false, false, true, true, false, false),
                listOf(true, true, true, true, false, true, true, true, true, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, false, true, true, true, true, true, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, false, false, true, false, false, false, false, false),
                listOf(false, false, false, false, false, false, false, false, false, false)
            ),
            // Smiley (10x10)
            listOf(
                listOf(false, false, true, true, true, true, true, true, false, false),
                listOf(false, true, false, false, false, false, false, false, true, false),
                listOf(true, false, true, false, false, false, false, true, false, true),
                listOf(true, false, true, false, false, false, false, true, false, true),
                listOf(true, false, false, false, false, false, false, false, false, true),
                listOf(true, false, true, false, false, false, false, true, false, true),
                listOf(true, false, false, true, true, true, true, false, false, true),
                listOf(false, true, false, false, false, false, false, false, true, false),
                listOf(false, false, true, true, true, true, true, true, false, false),
                listOf(false, false, false, false, false, false, false, false, false, false)
            ),
            // House (10x10)
            listOf(
                listOf(false, false, false, false, true, false, false, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, true, true, true, true, true, false, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, true, true, false, false, true, true, true, false, false),
                listOf(false, true, true, false, false, true, true, true, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, false, false, false, false, false, false, false, false, false)
            ),
            // Cat Silhouette (10x10)
            listOf(
                listOf(false, false, false, false, false, false, false, false, false, false),
                listOf(false, true, false, false, false, false, false, true, false, false),
                listOf(false, true, true, false, false, false, true, true, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(false, false, false, false, false, false, false, false, false, false)
            ),
            // Tree (10x10)
            listOf(
                listOf(false, false, false, false, true, false, false, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, true, true, true, true, true, false, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, false, true, true, true, true, true, false, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false)
            ),
            // Skull (10x10)
            listOf(
                listOf(false, false, true, true, true, true, true, true, false, false),
                listOf(false, true, true, true, true, true, true, true, true, false),
                listOf(true, true, true, true, true, true, true, true, true, true),
                listOf(true, true, false, true, true, true, true, false, true, true),
                listOf(true, true, false, true, true, true, true, false, true, true),
                listOf(true, true, true, true, true, true, true, true, true, true),
                listOf(false, true, true, true, true, true, true, true, true, false),
                listOf(false, false, true, false, true, false, true, false, true, false),
                listOf(false, false, true, true, true, true, true, true, false, false),
                listOf(false, false, false, false, false, false, false, false, false, false)
            ),
             // Sword (10x10)
            listOf(
                listOf(false, false, false, false, false, false, false, false, true, false),
                listOf(false, false, false, false, false, false, false, true, true, false),
                listOf(false, false, false, false, false, false, true, true, false, false),
                listOf(false, false, false, false, false, true, true, false, false, false),
                listOf(false, false, false, false, true, true, false, false, false, false),
                listOf(false, false, false, true, true, false, false, false, false, false),
                listOf(false, false, true, true, false, false, false, false, false, false),
                listOf(false, true, true, true, true, false, false, false, false, false),
                listOf(true, true, false, false, false, false, false, false, false, false),
                listOf(true, false, false, false, false, false, false, false, false, false)
            ),
            // Diamond (10x10)
            listOf(
                listOf(false, false, false, false, true, false, false, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, true, true, true, true, true, false, false, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(true, true, true, true, true, true, true, true, true, false),
                listOf(false, true, true, true, true, true, true, true, false, false),
                listOf(false, false, true, true, true, true, true, false, false, false),
                listOf(false, false, false, true, true, true, false, false, false, false),
                listOf(false, false, false, false, true, false, false, false, false, false),
                listOf(false, false, false, false, false, false, false, false, false, false)
            )
        )
        return puzzles.random()
    }
}
