package com.funkyotc.puzzleverse.minesweeper.data

enum class MinesweeperDifficulty(val rows: Int, val cols: Int, val mines: Int) {
    EASY(9, 9, 10),
    MEDIUM(16, 16, 40),
    HARD(16, 30, 99)
}
