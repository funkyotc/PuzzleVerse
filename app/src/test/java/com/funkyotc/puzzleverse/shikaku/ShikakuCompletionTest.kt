package com.funkyotc.puzzleverse.shikaku

import com.funkyotc.puzzleverse.shikaku.data.ShikakuPregenerated
import com.funkyotc.puzzleverse.shikaku.data.ShikakuRectangle
import com.funkyotc.puzzleverse.shikaku.viewmodel.ShikakuViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShikakuCompletionTest {
    @Test fun everyCheckedInGridWinsThroughRectangleActions() {
        val puzzles = ShikakuPregenerated.PUZZLES_BY_DIFFICULTY.values.flatten()
        assertEquals(30, puzzles.size)
        val failures = mutableListOf<String>()
        for (puzzle in puzzles) {
            val groups = puzzle.grid.indices.flatMap { r -> puzzle.grid[r].indices.map { c ->
                puzzle.grid[r][c] to (r to c)
            } }.groupBy({ it.first }, { it.second })
            val rectangles = groups.map { (id, positions) ->
                val minRow = positions.minOf { it.first }
                val maxRow = positions.maxOf { it.first }
                val minCol = positions.minOf { it.second }
                val maxCol = positions.maxOf { it.second }
                val rectangle = ShikakuRectangle("solution_$id", minRow, minCol,
                    maxCol - minCol + 1, maxRow - minRow + 1)
                if (positions.size != rectangle.width * rectangle.height) {
                    failures += "${puzzle.id}: region $id is not rectangular"
                }
                rectangle
            }
            if (failures.any { it.startsWith("${puzzle.id}:") }) continue
            val vm = ShikakuViewModel(mode = "puzzle", puzzleId = puzzle.id)
            rectangles.forEach(vm::onPlayerRectangleDraw)
            if (!vm.isGameWon.value) failures += "${puzzle.id}: ViewModel rejected checked-in solution"
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }
}
