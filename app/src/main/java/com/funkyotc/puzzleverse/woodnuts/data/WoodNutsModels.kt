package com.funkyotc.puzzleverse.woodnuts.data

data class PhysicsTransform(
    val x: Float,
    val y: Float,
    val angle: Float
)

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
    val angle: Float = 0f,
    val depthLayer: Int = 0,
    val transform: PhysicsTransform? = null
)

/**
 * Bolts are authored at grid-line coordinates (0..cols / 0..rows), but they are
 * rendered at cell centres (col+0.5, row+0.5). For bolts sitting on the right or
 * bottom edge of a plank (e.g. col == cols) the authored coordinate lands in the
 * empty neighbouring cell, so the bolt appears off the plank edge. Clamp the
 * authored coordinate into the cell span of the plank(s) the bolt attaches to so
 * edge bolts render at the adjacent inner cell centre. Interior bolts are already
 * within the span and pass through unchanged.
 */
fun clampBoltCellToPlanks(bolt: Bolt, planks: List<Plank>): Pair<Float, Float> {
    val related = planks.filter { it.boltIds.contains(bolt.id) }
    val minC = related.minOfOrNull { it.startCol }?.toFloat() ?: bolt.col.toFloat()
    val maxC = related.maxOfOrNull { it.endCol }?.toFloat() ?: bolt.col.toFloat()
    val minR = related.minOfOrNull { it.startRow }?.toFloat() ?: bolt.row.toFloat()
    val maxR = related.maxOfOrNull { it.endRow }?.toFloat() ?: bolt.row.toFloat()
    val clampedCol = bolt.col.toFloat().coerceIn(minC, maxC)
    val clampedRow = bolt.row.toFloat().coerceIn(minR, maxR)
    return clampedCol to clampedRow
}

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
