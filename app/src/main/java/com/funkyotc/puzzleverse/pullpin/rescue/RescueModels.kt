package com.funkyotc.puzzleverse.pullpin.rescue

import com.funkyotc.puzzleverse.pullpin.data.WallSegment

/** Drawing units, with the same 100 units/metre convention as the original engine. */
data class StoneSpawn(val id: String, val x: Double, val y: Double, val radius: Double = 4.5)
data class KingGeometry(val x: Double, val floorY: Double, val bodyWidth: Double = 18.0,
    val bodyHeight: Double = 34.0, val headRadius: Double = 9.0) {
    val headY get() = floorY - bodyHeight - headRadius
}
data class RescuePin(val id: String, val shape: WallSegment, val label: String)
data class TimedPull(val tick: Int, val pinId: String)
enum class RescueObjective { SURVIVE }
data class RescueLevel(val id: String, val walls: List<WallSegment>, val pins: List<RescuePin>,
    val stones: List<StoneSpawn>, val king: KingGeometry,
    val objective: RescueObjective = RescueObjective.SURVIVE,
    val solution: List<TimedPull> = emptyList()) {
    init {
        require(stones.isNotEmpty() && stones.map { it.id }.distinct().size == stones.size)
        require(pins.map { it.id }.distinct().size == pins.size)
        require(stones.all { it.radius > 0 && it.x.isFinite() && it.y.isFinite() })
        require(king.bodyWidth > 0 && king.bodyHeight > 0 && king.headRadius > 0)
    }
}
data class StoneState(val id: String, val x: Double, val y: Double, val radius: Double,
    val speed: Double = 0.0, val resting: Boolean = false)
enum class RescueStatus { READY, RUNNING, WON, LOST }
data class PinState(val pin: RescuePin, val acceptedTick: Int? = null, val removed: Boolean = false) {
    fun progress(tick: Int): Float = acceptedTick?.let {
        ((tick - it).toFloat() / RescueSession.PULL_TICKS).coerceIn(0f, 1f)
    } ?: 0f
}
data class BurialCoverage(val left: Double = 0.0, val right: Double = 0.0,
    val above: Double = 0.0, val support: Double = 0.0) {
    val covered get() = left >= 0.42 && right >= 0.42 && above >= 0.30 && support >= 0.42
}
data class RescueState(val tick: Int, val status: RescueStatus, val stones: List<StoneState>,
    val pins: List<PinState>, val burial: BurialCoverage = BurialCoverage(),
    val burialTicks: Int = 0, val settledTicks: Int = 0, val lossReason: String? = null)
