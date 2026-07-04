package com.funkyotc.puzzleverse.watersort.data

data class Bottle(val colors: List<Int>) {
    fun isEmpty(): Boolean = colors.isEmpty()
    fun isFull(height: Int): Boolean = colors.size >= height
    fun topColor(): Int? = colors.lastOrNull()
    fun countTopColor(): Int = colors.takeLastWhile { it == topColor() }.size
    fun withoutTop(count: Int): Bottle = Bottle(colors.dropLast(count))
    fun withColor(color: Int, count: Int): Bottle = Bottle(colors + List(count) { color })
}

data class Level(
    val id: String,
    val difficulty: String,
    val bottles: List<Bottle>,
    val numColors: Int,
    val height: Int
)

data class WaterSortState(
    val level: Level,
    val bottles: List<Bottle>,
    val selectedIndex: Int = -1,
    val moves: Int = 0,
    val isWon: Boolean = false
)
