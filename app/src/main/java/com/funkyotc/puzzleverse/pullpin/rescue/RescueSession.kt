package com.funkyotc.puzzleverse.pullpin.rescue

import com.funkyotc.puzzleverse.pullpin.data.WORLD_H
import com.funkyotc.puzzleverse.pullpin.data.WORLD_W

/** A supported, slow local pile around BOTH head flanks and the crown, not impact damage. */
internal object BurialProbe {
    fun measure(king: KingGeometry, stones: List<StoneState>): BurialCoverage {
        val r = king.headRadius
        val local = stones.filter { it.speed <= 18.0 &&
            kotlin.math.abs(it.x - king.x) < r + 26 &&
            it.y > king.headY - r - 22 && it.y < king.headY + r + 28 }
        fun occupancy(x0: Double, y0: Double, x1: Double, y1: Double): Double {
            var occupied = 0
            val n = 6
            for (row in 0 until n) for (col in 0 until n) {
                val x = x0 + (col + 0.5) * (x1 - x0) / n
                val y = y0 + (row + 0.5) * (y1 - y0) / n
                if (local.any { (it.x - x) * (it.x - x) + (it.y - y) * (it.y - y) <= it.radius * it.radius }) occupied++
            }
            return occupied.toDouble() / (n * n)
        }
        return BurialCoverage(
            occupancy(king.x - r - 12, king.headY - r / 2, king.x - r, king.headY + r),
            occupancy(king.x + r, king.headY - r / 2, king.x + r + 12, king.headY + r),
            occupancy(king.x - r, king.headY - r - 12, king.x + r, king.headY - r),
            minOf(
                occupancy(king.x - r - 12, king.headY + r, king.x - r, king.headY + r + 18),
                occupancy(king.x + r, king.headY + r, king.x + r + 12, king.headY + r + 18)))
    }
}

/** Evaluates the observed world; separate from stepping so boundary cases can be tested directly. */
internal object RescueRules {
    fun evaluate(level: RescueLevel, previous: RescueState, tick: Int,
        pins: List<PinState>, stones: List<StoneState>): RescueState {
        val burial = BurialProbe.measure(level.king, stones)
        val burialTicks = if (burial.covered) previous.burialTicks + 1 else 0
        // A sustained low-motion interval rejects bounce apexes without waiting forever on
        // dyn4j sleep flags for tiny stone/contact jitter. Check displacement as well as speed.
        val settled = stones.zip(previous.stones).all { (now, before) ->
            now.speed <= 1.5 && kotlin.math.hypot(now.x - before.x, now.y - before.y) <= 0.03
        } && pins.none { it.acceptedTick != null && !it.removed }
        val settledTicks = if (settled) previous.settledTicks + 1 else 0
        val escaped = stones.any { !it.x.isFinite() || !it.y.isFinite() ||
            it.x - it.radius < -2 || it.x + it.radius > WORLD_W + 2 ||
            it.y - it.radius < -2 || it.y + it.radius > WORLD_H + 2 }
        val loss = when {
            escaped -> "A stone escaped the bounded board. Retry this layout."
            burialTicks >= RescueSession.BURIAL_GRACE_TICKS -> "The king's head is buried."
            else -> null
        }
        return RescueState(tick, when {
            loss != null -> RescueStatus.LOST
            settledTicks >= RescueSession.SETTLE_TICKS && !burial.covered -> RescueStatus.WON
            else -> RescueStatus.RUNNING
        }, stones, pins, burial, burialTicks, settledTicks, loss)
    }
}

/** Pure tick-driven rules. The caller starts only after the board is visible and input is ready. */
class RescueSession(val level: RescueLevel) {
    private val physics = RescuePhysics(level)
    private val acceptedActions = mutableListOf<TimedPull>()
    val actions: List<TimedPull> get() = acceptedActions.toList()
    var paused: Boolean = false
        private set
    internal var clockEpoch = 0
        private set
    var state = RescueState(0, RescueStatus.READY, physics.snapshot(), level.pins.map { PinState(it) })
        private set

    fun start() {
        if (state.status == RescueStatus.READY) {
            state = state.copy(status = RescueStatus.RUNNING)
            clockEpoch++
        }
    }
    fun setPaused(value: Boolean) {
        if (paused != value) { paused = value; clockEpoch++ }
    }
    fun retry(): RescueSession = RescueSession(level)
    fun pull(pinId: String): Boolean {
        if (paused || state.status != RescueStatus.RUNNING) return false
        val pin = state.pins.find { it.pin.id == pinId } ?: return false
        if (pin.acceptedTick != null) return false
        acceptedActions += TimedPull(state.tick, pinId)
        state = state.copy(pins = state.pins.map { if (it.pin.id == pinId) it.copy(acceptedTick = state.tick) else it },
            settledTicks = 0)
        return true
    }

    fun step() {
        if (paused || state.status != RescueStatus.RUNNING) return
        val tick = state.tick + 1
        val pins = state.pins.map { pin ->
            if (!pin.removed && pin.acceptedTick != null && tick - pin.acceptedTick >= PULL_TICKS) {
                physics.removePin(pin.pin.id)
                pin.copy(removed = true)
            } else pin
        }
        physics.step()
        val stones = physics.snapshot()
        state = RescueRules.evaluate(level, state, tick, pins, stones)
    }

    companion object {
        const val TICKS_PER_SECOND = 60
        const val PULL_TICKS = 14
        const val BURIAL_GRACE_TICKS = 60
        const val SETTLE_TICKS = 90
    }
}

/** Render-independent stepping. Paused time is discarded, active slow frames retain their ticks. */
class RescueClock(private val session: RescueSession) {
    private var lastNanos: Long? = null
    private var tickFraction = 0L
    private var epoch = session.clockEpoch
    fun reset() { lastNanos = null; tickFraction = 0 }
    fun setPaused(paused: Boolean) { session.setPaused(paused); reset() }
    fun frame(nowNanos: Long) {
        val previous = lastNanos
        lastNanos = nowNanos
        val changed = epoch != session.clockEpoch
        epoch = session.clockEpoch
        if (changed || session.paused || session.state.status != RescueStatus.RUNNING || previous == null) {
            tickFraction = 0; return
        }
        require(nowNanos >= previous) { "Use a monotonic clock" }
        // Divide before multiplying so an unexpectedly large timestamp gap cannot overflow.
        val elapsed = nowNanos - previous
        var steps = (elapsed / 1_000_000_000L) * RescueSession.TICKS_PER_SECOND
        tickFraction += (elapsed % 1_000_000_000L) * RescueSession.TICKS_PER_SECOND
        steps += tickFraction / 1_000_000_000L
        tickFraction %= 1_000_000_000L
        while (steps-- > 0 && session.state.status == RescueStatus.RUNNING) session.step()
    }
}
