package com.funkyotc.puzzleverse.kakuro

import com.funkyotc.puzzleverse.kakuro.data.CellType
import com.funkyotc.puzzleverse.kakuro.data.PregeneratedKakuro
import com.funkyotc.puzzleverse.kakuro.data.KakuroPregenerated
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModel
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class KakuroCompletionTest {
    private data class Run(val indices: List<Int>, val target: Int)

    private fun countSolutions(puzzle: PregeneratedKakuro): Int {
        val grid = puzzle.grid
        val inputs = grid.indices.flatMap { r -> grid[r].indices.mapNotNull { c ->
            if (grid[r][c].type == CellType.PLAYER_INPUT) r to c else null
        } }
        val inputIndex = inputs.withIndex().associate { it.value to it.index }
        val runs = mutableListOf<Run>()
        for (r in grid.indices) for (c in grid[r].indices) {
            val cell = grid[r][c]
            if (cell.type != CellType.CLUE) continue
            cell.clue?.horizontalSum?.let { target ->
                val cells = (c + 1 until grid[r].size).takeWhile { grid[r][it].type == CellType.PLAYER_INPUT }
                    .map { inputIndex.getValue(r to it) }
                runs += Run(cells, target)
            }
            cell.clue?.verticalSum?.let { target ->
                val cells = (r + 1 until grid.size).takeWhile { grid[it][c].type == CellType.PLAYER_INPUT }
                    .map { inputIndex.getValue(it to c) }
                runs += Run(cells, target)
            }
        }
        require(inputs.isNotEmpty() && inputs.indices.all { i -> runs.any { i in it.indices } }) {
            "${puzzle.id} has an unconstrained input"
        }
        val values = IntArray(inputs.size) { puzzle.givens[inputs[it]] ?: 0 }
        val touching = inputs.indices.map { i -> runs.filter { i in it.indices } }

        fun valid(run: Run): Boolean {
            val assigned = run.indices.map { values[it] }.filter { it != 0 }
            if (assigned.size != assigned.distinct().size) return false
            val sum = assigned.sum()
            val remaining = run.indices.size - assigned.size
            if (remaining == 0) return sum == run.target
            val available = (1..9).filter { it !in assigned }
            if (available.size < remaining) return false
            return sum + available.take(remaining).sum() <= run.target &&
                sum + available.takeLast(remaining).sum() >= run.target
        }

        var solutions = 0
        fun search() {
            if (solutions >= 2) return
            var bestIndex = -1
            var bestCandidates = emptyList<Int>()
            for (i in inputs.indices) {
                if (values[i] != 0) continue
                val candidates = (1..9).filter { digit ->
                    values[i] = digit
                    val possible = touching[i].all(::valid)
                    values[i] = 0
                    possible
                }
                if (candidates.isEmpty()) return
                if (bestIndex == -1 || candidates.size < bestCandidates.size) {
                    bestIndex = i
                    bestCandidates = candidates
                }
            }
            if (bestIndex == -1) {
                solutions++
                return
            }
            for (digit in bestCandidates) {
                values[bestIndex] = digit
                search()
                values[bestIndex] = 0
                if (solutions >= 2) return
            }
        }
        if (runs.all(::valid)) search()
        return solutions
    }

    @Test
    fun everyCheckedInKakuroHasOneWinningAssignment() {
        assertEquals(24, KakuroPregenerated.ALL_PUZZLES.size)
        val failures = KakuroPregenerated.ALL_PUZZLES.mapNotNull { puzzle ->
            val count = countSolutions(puzzle)
            if (count == 1) null else "${puzzle.id}: $count solution(s)"
        }
        assertTrue(failures.joinToString(), failures.isEmpty())
    }

    @Test
    fun fixedStartingDigitsCannotBeChanged() {
        val puzzle = KakuroPregenerated.ALL_PUZZLES.first()
        val (position, value) = puzzle.givens.entries.first().let { it.key to it.value }
        val viewModel = KakuroViewModel(puzzleId = puzzle.id)
        assertTrue(viewModel.state.value.grid[position.first][position.second].isGiven)
        viewModel.setCellValue(position.first, position.second, if (value == 9) 1 else value + 1)
        assertTrue(viewModel.state.value.grid[position.first][position.second].playerValue == value)
    }
}
