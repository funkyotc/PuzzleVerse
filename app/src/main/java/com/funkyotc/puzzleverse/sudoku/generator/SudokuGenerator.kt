package com.funkyotc.puzzleverse.sudoku.generator

import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import kotlin.random.Random

class SudokuGenerator {
    private val puzzles = listOf(
        listOf(
            listOf(5, 3, 0, 0, 7, 0, 0, 0, 0),
            listOf(6, 0, 0, 1, 9, 5, 0, 0, 0),
            listOf(0, 9, 8, 0, 0, 0, 0, 6, 0),
            listOf(8, 0, 0, 0, 6, 0, 0, 0, 3),
            listOf(4, 0, 0, 8, 0, 3, 0, 0, 1),
            listOf(7, 0, 0, 0, 2, 0, 0, 0, 6),
            listOf(0, 6, 0, 0, 0, 0, 2, 8, 0),
            listOf(0, 0, 0, 4, 1, 9, 0, 0, 5),
            listOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
        ),
        listOf(
            listOf(0, 0, 0, 2, 6, 0, 7, 0, 1),
            listOf(6, 8, 0, 0, 7, 0, 0, 9, 0),
            listOf(1, 9, 0, 0, 0, 4, 5, 0, 0),
            listOf(8, 2, 0, 1, 0, 0, 0, 4, 0),
            listOf(0, 0, 4, 6, 0, 2, 9, 0, 0),
            listOf(0, 5, 0, 0, 0, 3, 0, 2, 8),
            listOf(0, 0, 9, 3, 0, 0, 0, 7, 4),
            listOf(0, 4, 0, 0, 5, 0, 0, 3, 6),
            listOf(7, 0, 3, 0, 1, 8, 0, 0, 0)
        )
        // TODO: Add more puzzles
    )

    fun generate(seed: Long = Random.nextLong()): SudokuBoard {
        val puzzleIndex = (seed % puzzles.size).toInt()
        val puzzle = puzzles[puzzleIndex]

        val cells = mutableListOf<SudokuCell>()
        for (row in 0..8) {
            for (col in 0..8) {
                val number = puzzle[row][col]
                cells.add(SudokuCell(row, col, number, isHint = number != 0))
            }
        }

        return SudokuBoard(cells)
    }
}