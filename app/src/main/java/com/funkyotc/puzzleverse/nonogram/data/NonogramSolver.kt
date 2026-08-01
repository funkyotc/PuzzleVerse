package com.funkyotc.puzzleverse.nonogram.data

class NonogramSolver {
    companion object {
        fun isSolvableWithoutGuessing(solution: List<List<Boolean>>): Boolean {
            val rows = solution.size
            val cols = if (rows > 0) solution[0].size else 0
            if (rows == 0 || cols == 0) return true

            val grid: List<MutableList<Boolean?>> = List(rows) { MutableList(cols) { null as Boolean? } }

            val rowClues = solution.map { calculateClues(it) }
            val colClues = (0 until cols).map { c ->
                calculateClues(solution.map { it[c] })
            }

            var changed = true
            var passes = 0
            while (changed && passes < 100) {
                changed = false
                passes++

                for (r in 0 until rows) {
                    if (applyLineConstraints(grid[r], rowClues[r], cols)) changed = true
                }

                for (c in 0 until cols) {
                    val col = (0 until rows).map { grid[it][c] }.toMutableList()
                    if (applyLineConstraints(col, colClues[c], rows)) {
                        changed = true
                        for (r in 0 until rows) {
                            grid[r][c] = col[r]
                        }
                    }
                }
            }

            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (grid[r][c] == null) return false
                }
            }

            return true
        }

        private fun calculateClues(line: List<Boolean>): List<Int> {
            val clues = mutableListOf<Int>()
            var current = 0
            for (cell in line) {
                if (cell) {
                    current++
                } else {
                    if (current > 0) {
                        clues.add(current)
                        current = 0
                    }
                }
            }
            if (current > 0) clues.add(current)
            return if (clues.isEmpty()) listOf(0) else clues
        }

        private fun applyLineConstraints(line: MutableList<Boolean?>, clues: List<Int>, length: Int): Boolean {
            val placements = getValidPlacements(clues, length)
            if (placements.isEmpty()) return false

            val filteredPlacements = placements.filter { placement ->
                var matches = true
                for (i in 0 until length) {
                    if (line[i] != null && line[i] != placement[i]) {
                        matches = false
                        break
                    }
                }
                matches
            }

            if (filteredPlacements.isEmpty()) return false

            var changed = false
            for (i in 0 until length) {
                val allFilled = filteredPlacements.all { it[i] }
                val allEmpty = filteredPlacements.all { !it[i] }

                if (allFilled && line[i] == null) {
                    line[i] = true
                    changed = true
                } else if (allEmpty && line[i] == null) {
                    line[i] = false
                    changed = true
                }
            }

            return changed
        }

        private fun getValidPlacements(clues: List<Int>, length: Int): List<List<Boolean>> {
            if (clues.isEmpty() || clues.all { it == 0 }) {
                return listOf(List(length) { false })
            }

            val result = mutableListOf<List<Boolean>>()
            generatePlacements(clues, 0, 0, length, BooleanArray(length), result)
            return result
        }

        private fun generatePlacements(
            clues: List<Int>,
            clueIdx: Int,
            startPos: Int,
            length: Int,
            current: BooleanArray,
            result: MutableList<List<Boolean>>
        ) {
            if (clueIdx == clues.size) {
                val placement = current.toList()
                result.add(placement)
                return
            }

            val clueLen = clues[clueIdx]
            val minRemaining = clues.subList(clueIdx + 1, clues.size).sum() + (clues.size - clueIdx - 1)
            val maxStart = length - minRemaining - clueLen

            for (pos in startPos..maxStart) {
                val nextArray = current.clone()
                for (i in 0 until clueLen) {
                    nextArray[pos + i] = true
                }
                val nextStart = pos + clueLen + 1
                generatePlacements(clues, clueIdx + 1, nextStart, length, nextArray, result)
            }
        }
    }
}