package com.funkyotc.puzzleverse.hexasort

import com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

/** Necessary all-level conditions; two-shuffle completion still needs a winning replay. */
class HexaSortWinPreconditionsTest {
    @Test fun everyLevelHasNoSingletonColorAndAnOpeningPop() {
        val failures = mutableListOf<String>()
        assertEquals(24, HexaSortPregenerated.ALL_PUZZLES.size)
        for (level in HexaSortPregenerated.ALL_PUZZLES) {
            val grid = level.grid
            val singletons = grid.flatten().filterNotNull().groupingBy { it }.eachCount()
                .filterValues { it == 1 }.keys
            val opening = grid.indices.any { r -> grid[r].indices.any { c ->
                val color = grid[r][c]
                color != null && neighbors(r, c, grid.size, grid[r].size)
                    .any { (nr, nc) -> grid[nr][nc] == color }
            } }
            if (singletons.isNotEmpty() || !opening) {
                failures += "${level.id}: singleton=$singletons opening=$opening"
            }
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }

    private fun neighbors(r: Int, c: Int, rows: Int, cols: Int): List<Pair<Int, Int>> {
        val candidates = if (r % 2 == 1) listOf(
            r - 1 to c, r - 1 to c + 1, r to c - 1, r to c + 1, r + 1 to c, r + 1 to c + 1
        ) else listOf(
            r - 1 to c - 1, r - 1 to c, r to c - 1, r to c + 1, r + 1 to c - 1, r + 1 to c
        )
        return candidates.filter { (nr, nc) -> nr in 0 until rows && nc in 0 until cols }
    }
}
