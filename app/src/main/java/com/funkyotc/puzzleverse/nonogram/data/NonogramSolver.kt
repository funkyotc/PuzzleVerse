package com.funkyotc.puzzleverse.nonogram.data

import kotlin.math.min

class NonogramSolver {
    companion object {
        fun isSolvableWithoutGuessing(solution: List<List<Boolean>>): Boolean {
            val rows = solution.size
            val cols = if (rows > 0) solution[0].size else 0
            if (rows == 0 || cols == 0) return true

            // Create a copy of the solution grid
            val grid: List<MutableList<Boolean?>> = solution.map { row ->
                row.map { it as Boolean? }.toMutableList()
            }

            // Apply constraint propagation
            val changed = solveLineByLine(grid, rows, cols)

            // If any cell is still unknown, it requires guessing
            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (grid[r][c] == null) return false
                }
            }

            return true
        }

        private fun solveLineByLine(grid: List<MutableList<Boolean?>>, rows: Int, cols: Int): Boolean {
            var changed = false
            val rowClues = grid.map { calculateClues(it) }
            val colClues = mutableListOf<List<Int>>()
            for (c in 0 until cols) {
                val colList = grid.map { it[c] }
                colClues.add(calculateClues(colList))
            }

            // Try to solve rows
            for (r in 0 until rows) {
                val row = grid[r]
                val clues = rowClues[r]
                if (applyLineConstraints(row, clues, cols)) changed = true
            }

            // Try to solve columns
            for (c in 0 until cols) {
                val col = (0 until rows).map { grid[it][c] }.toMutableList()
                val clues = colClues[c]
                if (applyLineConstraints(col, clues, rows)) {
                    changed = true
                    // Update grid with new values
                    for (r in 0 until rows) {
                        grid[r][c] = col[r]
                    }
                }
            }

            return changed
        }

        private fun applyLineConstraints(line: MutableList<Boolean?>, clues: List<Int>, length: Int): Boolean {
            if (clues.isEmpty()) return false
            if (clues.all { it == 0 }) return false

            var changed = false
            val lineArray = line.toMutableList()

            // Get possible placements for this line
            val placements = getValidPlacements(clues, length)

            // If no valid placements, this is invalid
            if (placements.isEmpty()) return false

            // Find cells that are always filled in all valid placements
            for (i in 0 until length) {
                val allFilled = placements.all { it[i] }
                val allEmpty = placements.all { !it[i] }

                if (allFilled && lineArray[i] == null) {
                    lineArray[i] = true
                    changed = true
                } else if (allEmpty && lineArray[i] == null) {
                    lineArray[i] = false
                    changed = true
                }
            }

            // Apply changes back to the line
            for (i in 0 until length) {
                if (lineArray[i] != null && line[i] != lineArray[i]) {
                    line[i] = lineArray[i]
                    changed = true
                }
            }

            return changed
        }

        private fun getValidPlacements(clues: List<Int>, length: Int): List<List<Boolean>> {
            if (clues.isEmpty() || clues.all { it == 0 }) {
                return listOf(List(length) { false })
            }

            val totalFilled = clues.sum()
            val totalGaps = clues.size - 1
            val minLength = totalFilled + totalGaps

            if (minLength > length) return emptyList()

            val result = mutableListOf<List<Boolean>>()
            val placements = mutableListOf<List<Boolean>>()

            // Generate all possible placements
            generatePlacements(clues, 0, 0, length, mutableListOf(), placements)

            // Filter for valid placements
            for (placement in placements) {
                if (placement.size == length) {
                    result.add(placement)
                }
            }

            return result
        }

        private fun generatePlacements(
            clues: List<Int>,
            clueIndex: Int,
            position: Int,
            length: Int,
            current: MutableList<Boolean>,
            result: MutableList<List<Boolean>>
        ) {
            if (clueIndex >= clues.size) {
                // Fill remaining positions with empty
                while (current.size < length) {
                    current.add(false)
                }
                result.add(current.toList())
                return
            }

            val clue = clues[clueIndex]
            val remainingClues = clues.size - clueIndex - 1
            val minSpaceForRemaining = remainingClues
            val maxPosition = length - clue - minSpaceForRemaining

            if (position > maxPosition) return

            // Try placing the current clue at this position
            if (position + clue <= length) {
                // Add the filled cells
                val originalSize = current.size
                while (current.size < position) {
                    current.add(false)
                }
                repeat(clue) {
                    current.add(true)
                }

                // Try next clue
                generatePlacements(clues, clueIndex + 1, position + clue + 1, length, current, result)

                // Backtrack
                current.subList(originalSize, current.size).clear()
            }

            // Try skipping this position
            if (current.size < length) {
                current.add(false)
                generatePlacements(clues, clueIndex, position + 1, length, current, result)
                current.removeAt(current.size - 1)
            }
        }

        private fun calculateClues(line: List<Boolean?>): List<Int> {
            val clues = mutableListOf<Int>()
            var count = 0
            for (v in line) {
                if (v == true) count++
                else if (count > 0) {
                    clues.add(count)
                    count = 0
                }
            }
            if (count > 0) clues.add(count)
            return clues
        }

        fun solvePuzzle(rowClues: List<List<Int>>, colClues: List<List<Int>>, size: Int): List<List<Boolean>> {
            val grid = List(size) { MutableList(size) { null as Boolean? } }
            val rowCluesCopy = rowClues.map { it.toList() }
            val colCluesCopy = colClues.map { it.toList() }

            var changed = true
            while (changed) {
                changed = false

                // Solve rows
                for (r in 0 until size) {
                    val row = grid[r]
                    val clues = rowCluesCopy[r]
                    val line = row.toMutableList()
                    val placements = getValidPlacements(clues, size)

                    if (placements.isEmpty()) {
                        // Invalid puzzle
                        return List(size) { List(size) { false } }
                    }

                    // Find cells that are always filled in all valid placements
                    for (c in 0 until size) {
                        val allFilled = placements.all { it[c] }
                        val allEmpty = placements.all { !it[c] }

                        if (allFilled && line[c] == null) {
                            line[c] = true
                            changed = true
                        } else if (allEmpty && line[c] == null) {
                            line[c] = false
                            changed = true
                        }
                    }

                    // Update the grid
                    for (c in 0 until size) {
                        if (line[c] != null && grid[r][c] != line[c]) {
                            grid[r][c] = line[c]
                            changed = true
                        }
                    }
                }

                // Solve columns
                for (c in 0 until size) {
                    val col = (0 until size).map { grid[it][c] }
                    val clues = colCluesCopy[c]
                    val line = col.toMutableList()
                    val placements = getValidPlacements(clues, size)

                    if (placements.isEmpty()) {
                        // Invalid puzzle
                        return List(size) { List(size) { false } }
                    }

                    // Find cells that are always filled in all valid placements
                    for (r in 0 until size) {
                        val allFilled = placements.all { it[r] }
                        val allEmpty = placements.all { !it[r] }

                        if (allFilled && line[r] == null) {
                            line[r] = true
                            changed = true
                        } else if (allEmpty && line[r] == null) {
                            line[r] = false
                            changed = true
                        }
                    }

                    // Update the grid
                    for (r in 0 until size) {
                        if (line[r] != null && grid[r][c] != line[r]) {
                            grid[r][c] = line[r]
                            changed = true
                        }
                    }
                }
            }

            // Check if solved
            for (r in 0 until size) {
                for (c in 0 until size) {
                    if (grid[r][c] == null) {
                        // Use a simple backtracking solver for remaining cells
                        return backtrackSolve(grid, rowCluesCopy, colCluesCopy, size)
                    }
                }
            }

            return grid.map { it.map { it ?: false } }
        }

        private fun backtrackSolve(
            grid: List<MutableList<Boolean?>>,
            rowClues: List<List<Int>>,
            colClues: List<List<Int>>,
            size: Int
        ): List<List<Boolean>> {
            val result = grid.map { it.map { it ?: false } }
            val emptyCells = mutableListOf<Pair<Int, Int>>()

            // Find empty cells
            for (r in 0 until size) {
                for (c in 0 until size) {
                    if (grid[r][c] == null) {
                        emptyCells.add(Pair(r, c))
                    }
                }
            }

            if (emptyCells.isEmpty()) return result

            // Try filling empty cells
            val solution = solveWithBacktracking(result, rowClues, colClues, emptyCells, 0)
            return solution ?: result
        }

        private fun solveWithBacktracking(
            grid: List<List<Boolean>>, 
            rowClues: List<List<Int>>, 
            colClues: List<List<Int>>, 
            emptyCells: List<Pair<Int, Int>>, 
            index: Int
        ): List<List<Boolean>>? {
            if (index == emptyCells.size) {
                // Check if solution is valid
                if (isValidSolution(grid, rowClues, colClues)) {
                    return grid
                }
                return null
            }

            val (r, c) = emptyCells[index]

            // Try filling with true
            val gridWithTrue = grid.map { it.toMutableList() }
            gridWithTrue[r][c] = true
            val result = solveWithBacktracking(gridWithTrue, rowClues, colClues, emptyCells, index + 1)
            if (result != null) return result

            // Try filling with false
            val gridWithFalse = grid.map { it.toMutableList() }
            gridWithFalse[r][c] = false
            return solveWithBacktracking(gridWithFalse, rowClues, colClues, emptyCells, index + 1)
        }

        private fun isValidSolution(
            grid: List<List<Boolean>>, 
            rowClues: List<List<Int>>, 
            colClues: List<List<Int>>
        ): Boolean {
            val rows = grid.size
            val cols = if (rows > 0) grid[0].size else 0

            // Check rows
            for (r in 0 until rows) {
                val rowClue = rowClues[r]
                val row = grid[r]
                val calculatedClues = mutableListOf<Int>()
                var count = 0
                for (cell in row) {
                    if (cell) count++
                    else if (count > 0) {
                        calculatedClues.add(count)
                        count = 0
                    }
                }
                if (count > 0) calculatedClues.add(count)
                if (calculatedClues != rowClue) return false
            }

            // Check columns
            for (c in 0 until cols) {
                val colClue = colClues[c]
                val col = (0 until rows).map { grid[it][c] }
                val calculatedClues = mutableListOf<Int>()
                var count = 0
                for (cell in col) {
                    if (cell) count++
                    else if (count > 0) {
                        calculatedClues.add(count)
                        count = 0
                    }
                }
                if (count > 0) calculatedClues.add(count)
                if (calculatedClues != colClue) return false
            }

            return true
        }
    }
}