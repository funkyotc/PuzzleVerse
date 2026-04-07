package com.funkyotc.puzzleverse.tfe.data

import java.util.UUID

data class Tile(
    val id: String = UUID.randomWindow().toString(), // Helper function or just UUID.randomUUID().toString()
    val value: Int,
    val row: Int,
    val col: Int,
    val isNew: Boolean = true,
    val isMerged: Boolean = false
) {
    companion object {
        fun createId() = UUID.randomUUID().toString()
    }
}

data class TfeState(
    val tiles: List<Tile> = emptyList(),
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false
) {
    fun tileAt(r: Int, c: Int): Tile? = tiles.find { it.row == r && it.col == c }
    fun hasEmptyCell(): Boolean = tiles.size < 16
}

enum class Direction { UP, DOWN, LEFT, RIGHT }
