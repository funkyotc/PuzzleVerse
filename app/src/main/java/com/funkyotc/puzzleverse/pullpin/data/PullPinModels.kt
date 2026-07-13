package com.funkyotc.puzzleverse.pullpin.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

enum class CellType { EMPTY, WALL, PIN, BALL, CUP }

data class Cell(val type: CellType, val color: Int? = null)

data class PullPinLevel(
    override val id: String,
    override val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<Cell>>
) : BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String get() = "${cols}x${rows}"
}

data class BallState(
    val row: Int,
    val col: Int,
    val color: Int, // 0 indicates grey/uncolored
    val inCup: Boolean = false
)

enum class GameStatus {
    IDLE,
    SIMULATING,
    WON,
    LOST
}

data class PullPinState(
    val level: PullPinLevel,
    val grid: List<List<Cell>>,
    val balls: List<BallState>,
    val removedPins: Set<String>,
    val moves: Int = 0,
    val status: GameStatus = GameStatus.IDLE,
    val lostReason: String? = null
)

