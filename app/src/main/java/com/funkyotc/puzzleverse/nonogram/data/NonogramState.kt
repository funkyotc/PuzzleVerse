package com.funkyotc.puzzleverse.nonogram.data

enum class CellState { EMPTY, FILLED, CROSSED }

data class NonogramState(
    val rows: Int = 5,
    val cols: Int = 5,
    val solutionGrid: List<List<Boolean>> = emptyList(),
    val playerGrid: List<List<CellState>> = emptyList(),
    val rowClues: List<List<Int>> = emptyList(),
    val colClues: List<List<Int>> = emptyList(),
    val isGameOver: Boolean = false,
    val isWon: Boolean = false,
    val mistakes: Int = 0
)
