package com.funkyotc.puzzleverse.minesweeper.data

data class MineCell(
    val row: Int,
    val col: Int,
    val isMine: Boolean = false,
    val neighboringMines: Int = 0,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false
)

data class MinesweeperState(
    val grid: List<List<MineCell>> = emptyList(),
    val rows: Int = 10,
    val cols: Int = 10,
    val totalMines: Int = 15,
    val flagsPlaced: Int = 0,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false,
    val firstClickDone: Boolean = false
)
