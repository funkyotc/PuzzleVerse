package com.funkyotc.puzzleverse.kakuro.data

enum class CellType { BLACK, CLUE, PLAYER_INPUT }

data class Clue(val horizontalSum: Int?, val verticalSum: Int?)

data class KakuroCell(
    val type: CellType,
    val clue: Clue? = null, // Only for CellType.CLUE
    val playerValue: Int? = null, // Only for CellType.PLAYER_INPUT
    val r: Int,
    val c: Int
)

data class KakuroState(
    val grid: List<List<KakuroCell>> = emptyList(),
    val rows: Int = 4,
    val cols: Int = 4,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false
)
