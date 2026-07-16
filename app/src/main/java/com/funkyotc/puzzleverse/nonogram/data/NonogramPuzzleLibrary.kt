package com.funkyotc.puzzleverse.nonogram.data

object NonogramPuzzleLibrary {

    val PRESET_PUZZLES = listOf(
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

    fun getRandomPuzzle(size: Int = 10): List<List<Boolean>> {
        val random = kotlin.random.Random.Default
        
        // 1. Try choosing a solvable preset that matches the requested size
        val matchingPresets = PRESET_PUZZLES.filter { preset -> 
            preset.size == size && (preset.isEmpty() || preset[0].size == size)
        }
        
        if (matchingPresets.isNotEmpty()) {
            val shuffledPresets = matchingPresets.shuffled()
            for (preset in shuffledPresets) {
                try {
                    if (NonogramSolver.isSolvableWithoutGuessing(preset)) {
                        return preset
                    }
                } catch (e: Exception) {
                    // Ignore exceptions during validation and continue
                }
            }
        }
        
        // 2. Procedurally generate a guaranteed logically solvable grid
        // Use a time limit to prevent ANRs or infinite loops
        val startTime = System.currentTimeMillis()
        var attempts = 0
        while (attempts < 2000 && (System.currentTimeMillis() - startTime) < 500) {
            val density = random.nextFloat() * 0.1f + 0.45f // Target 45% - 55% cell density
            val grid = List(size) {
                List(size) {
                    random.nextFloat() < density
                }
            }
            try {
                if (NonogramSolver.isSolvableWithoutGuessing(grid)) {
                    return grid
                }
            } catch (e: Exception) {
                // Ignore exceptions and try another grid
            }
            attempts++
        }
        
        // Final ultimate fallback: return the first matching preset, or a completely empty valid grid
        return if (matchingPresets.isNotEmpty()) {
            matchingPresets.first()
        } else if (PRESET_PUZZLES.isNotEmpty() && size == 10) {
            PRESET_PUZZLES.first()
        } else {
            List(size) { List(size) { false } } // Guaranteed valid empty grid
        }
    }
}
