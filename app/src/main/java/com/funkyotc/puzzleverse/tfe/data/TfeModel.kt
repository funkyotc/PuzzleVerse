package com.funkyotc.puzzleverse.tfe.data

data class TfeState(
    val grid: List<List<Int>> = List(4) { List(4) { 0 } },
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false
) {
    fun hasEmptyCell(): Boolean = grid.any { row -> row.any { it == 0 } }
}

enum class Direction { UP, DOWN, LEFT, RIGHT }
