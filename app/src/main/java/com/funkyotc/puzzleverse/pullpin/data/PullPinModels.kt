package com.funkyotc.puzzleverse.pullpin.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

/**
 * Continuous-coordinate model for the reworked "Pull the Pin" game.
 *
 * There is no grid. Everything lives in a virtual world of [WORLD_W] x [WORLD_H]
 * units (portrait). The UI scales this world to fit the screen. Physics is
 * handled by a dyn4j world; these types are the pure data description of a
 * level plus the live UI state.
 */

const val WORLD_W = 400f
const val WORLD_H = 700f

/** A static wall segment (rendered + simulated as a thin rectangle body). */
data class WallSegment(
    val x: Float,
    val y: Float,
    val w: Float,
    val h: Float
)

/** A colored cup on the floor. A matching-color ball resting inside is captured. */
data class CupData(
    val id: String,
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Int // 1..8, required color
)

/**
 * A removable pin. While present it blocks balls (static body). Tapping it
 * slides it out (animated) then it is removed from the physics world.
 */
data class PinData(
    val id: String,
    val x: Float,
    val y: Float,
    val w: Float,
    val h: Float,
    val pullDx: Float = 0f,
    val pullDy: Float = 0f,
    val removed: Boolean = false,
    val isPulling: Boolean = false
)

/** Initial ball placement. Color 0 = grey (must be colored by contact). */
data class BallSpawn(
    val id: String,
    val x: Float,
    val y: Float,
    val color: Int,
    val radius: Float = 14f
)

data class PullPinLevel(
    override val id: String,
    override val difficulty: String,
    val walls: List<WallSegment>,
    val cups: List<CupData>,
    val pins: List<PinData>,
    val balls: List<BallSpawn>
) : BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String
        get() = "${cups.size} cups - ${balls.size} balls"
}

enum class GameStatus {
    IDLE,      // waiting for the player to pull a pin
    RUNNING,   // physics simulating after a pin was pulled
    WON,
    LOST
}

data class BallRuntime(
    val id: String,
    val x: Float,
    val y: Float,
    val color: Int,
    val inCup: Boolean = false,
    val captured: Boolean = false,
    val outOfBounds: Boolean = false
)

data class PullPinState(
    val level: PullPinLevel,
    val balls: List<BallRuntime>,
    val pins: List<PinData>,
    val moves: Int = 0,
    val status: GameStatus = GameStatus.IDLE,
    val lostReason: String? = null
)
