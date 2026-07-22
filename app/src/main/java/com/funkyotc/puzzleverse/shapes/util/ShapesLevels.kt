package com.funkyotc.puzzleverse.shapes.util

import com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import kotlin.random.Random

object ShapesLevels {

    /**
     * Returns a standard 7-piece Tangram puzzle based on level index.
     */
    fun generateLevel(levelIndex: Int, random: Random): ShapesPuzzle {
        val puzzles = ShapesPregenerated.ALL_PUZZLES
        val index = kotlin.math.abs(levelIndex) % puzzles.size
        return puzzles[index].toShapesPuzzle()
    }
}
