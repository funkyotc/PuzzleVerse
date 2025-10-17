package com.funkyotc.puzzleverse.sudoku.generator

import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import kotlin.random.Random

class SudokuGenerator {
    private var grid = Array(9) { IntArray(9) }
    private val numbers = (1..9).toList()

    fun generate(seed: Long = Random.nextLong(), difficulty: Int = 45): SudokuBoard {
        val random = Random(seed)
        grid = Array(9) { IntArray(9) } // Clear grid

        // Generate a fully solved Sudoku board
        solve(random)

        // Remove numbers to create the puzzle, based on difficulty
        var removed = 0
        val cellsToRemove = (0..80).shuffled(random)

        for (cellIndex in cellsToRemove) {
            if (removed >= difficulty) break

            val row = cellIndex / 9
            val col = cellIndex % 9

            if (grid[row][col] != 0) {
                grid[row][col] = 0
                removed++
            }
        }

        val cells = mutableListOf<SudokuCell>()
        for (row in 0..8) {
            for (col in 0..8) {
                val number = grid[row][col]
                cells.add(SudokuCell(row, col, number, isHint = number != 0))
            }
        }

        return SudokuBoard(cells)
    }

    private fun solve(random: Random): Boolean {
        for (row in 0..8) {
            for (col in 0..8) {
                if (grid[row][col] == 0) {
                    val shuffledNumbers = numbers.shuffled(random)
                    for (num in shuffledNumbers) {
                        if (isSafe(row, col, num)) {
                            grid[row][col] = num
                            if (solve(random)) {
                                return true
                            }
                            grid[row][col] = 0 // backtrack
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        // Check row
        for (c in 0..8) {
            if (grid[row][c] == num) return false
        }
        // Check column
        for (r in 0..8) {
            if (grid[r][col] == num) return false
        }
        // Check 3x3 box
        val boxRowStart = row - row % 3
        val boxColStart = col - col % 3
        for (r in boxRowStart until boxRowStart + 3) {
            for (c in boxColStart until boxColStart + 3) {
                if (grid[r][c] == num) return false
            }
        }
        return true
    }
}
