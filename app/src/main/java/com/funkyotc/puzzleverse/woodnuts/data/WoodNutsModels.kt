package com.funkyotc.puzzleverse.woodnuts.data

data class Bolt(
    val id: String,
    val row: Int,
    val col: Int,
    val removed: Boolean = false,
    val isUnscrewing: Boolean = false
)

data class Plank(
    val id: String,
    val startRow: Int,
    val startCol: Int,
    val endRow: Int,
    val endCol: Int,
    val boltIds: List<String>,
    val removed: Boolean = false,
    val angle: Float = 0f
)

data class WoodNutsLevel(
    override val id: String,
    override val difficulty: String,
    val rows: Int,
    val cols: Int,
    val bolts: List<Bolt>,
    val planks: List<Plank>
) : com.funkyotc.puzzleverse.core.data.BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String get() = "${rows}x${cols}"
}

data class WoodNutsState(
    val level: WoodNutsLevel,
    val bolts: List<Bolt>,
    val planks: List<Plank>,
    val moves: Int = 0,
    val isWon: Boolean = false,
    val lastRemovedPlankId: String? = null,
    val lastRemovedBoltId: String? = null
)
