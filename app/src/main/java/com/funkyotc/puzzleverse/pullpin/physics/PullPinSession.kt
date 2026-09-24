package com.funkyotc.puzzleverse.pullpin.physics

import com.funkyotc.puzzleverse.pullpin.data.*
import kotlin.math.hypot

/** Fixed-step rules shared by gameplay, undo replay and the campaign proof. */
class PullPinSession(val level: PullPinLevel) {
    private val engine = PullPinPhysicsEngine().also { it.initWorld(level) }
    private val spawns = level.balls.associateBy { it.id }
    private val pulling = mutableMapOf<String, Int>()
    private val actions = mutableListOf<Pair<Int, String>>()
    var ticks = 0
        private set
    var state = PullPinState(level, level.balls.map { BallRuntime(it.id, it.x, it.y, it.color) }, level.pins)
        private set
    val canUndo get() = actions.isNotEmpty()
    val rescued get() = state.balls.count { it.captured }

    fun pull(id: String): Boolean {
        if (state.status == GameStatus.WON || state.status == GameStatus.LOST) return false
        val pin = state.pins.find { it.id == id } ?: return false
        if (pin.removed || pin.isPulling || rescued < pin.unlockAfter) return false
        actions += ticks to id
        pulling[id] = ticks + 14
        state = state.copy(pins = state.pins.map { if (it.id == id) it.copy(isPulling = true) else it },
            moves = state.moves + 1, status = GameStatus.RUNNING)
        return true
    }

    fun undo(): PullPinSession {
        if (!canUndo) return this
        val replay = PullPinSession(level)
        val target = actions.last().first
        for ((tick, pin) in actions.dropLast(1)) {
            while (replay.ticks < tick) replay.step()
            replay.pull(pin)
        }
        while (replay.ticks < target) replay.step()
        return replay
    }

    fun step() {
        if (state.status == GameStatus.WON || state.status == GameStatus.LOST) return
        // Idle thinking time must not inflate the cost of undo replay.
        if (pulling.isEmpty() && engine.isSettled()) return
        ticks++
        val finished = pulling.filterValues { it <= ticks }.keys.toSet()
        finished.forEach { engine.removePin(it); pulling.remove(it) }
        val pins = state.pins.map { if (it.id in finished) it.copy(removed = true, isPulling = false) else it }
        engine.step(1.0 / 60.0)
        val positions = engine.getBallTransforms()
        val balls = state.balls.map { b -> positions[b.id]?.let { b.copy(x = it.first, y = it.second) } ?: b }.toMutableList()
        var loss: String? = null
        // A one-unit contact tolerance accommodates the physics contact slop;
        // the old 30-unit aura could paint balls through closed pins.
        for (i in balls.indices) {
            val b = balls[i]
            if (b.captured || spawns.getValue(b.id).isBomb) continue
            val touching = balls.filter { other -> other.id != b.id && !other.captured &&
                hypot(b.x - other.x, b.y - other.y) <= spawns.getValue(b.id).radius + spawns.getValue(other.id).radius + 1f }
            if (touching.any { spawns.getValue(it.id).isBomb }) loss = "A bomb touched a ball. Keep the hazard pin closed!"
            if (b.color == 0) touching.firstOrNull { it.color > 0 && !spawns.getValue(it.id).isBomb }?.let {
                balls[i] = b.copy(color = it.color)
            }
        }
        for (i in balls.indices) {
            val b = balls[i]
            if (b.captured) continue
            val spawn = spawns.getValue(b.id)
            val cup = level.cups.firstOrNull { kotlin.math.abs(b.x - it.x) <= it.radius && kotlin.math.abs(b.y - it.y) <= 24f }
            if (cup != null) {
                when {
                    spawn.isBomb -> loss = "A bomb reached a cup!"
                    b.color == 0 -> loss = "A grey ball reached a cup. Mix before opening the exit!"
                    b.color != cup.color -> loss = "A ball reached the wrong color cup!"
                    else -> {
                        balls[i] = b.copy(captured = true, inCup = true)
                        engine.removeBall(b.id)
                    }
                }
            }
            if (!spawn.isBomb && engine.isBallOutOfBounds(b.id)) {
                balls[i] = b.copy(outOfBounds = true)
                loss = "A ball escaped the board!"
            }
        }
        val allSaved = balls.filter { !spawns.getValue(it.id).isBomb }.let { it.isNotEmpty() && it.all { b -> b.captured } }
        state = state.copy(balls = balls, pins = pins, lostReason = loss, status = when {
            loss != null -> GameStatus.LOST
            allSaved -> GameStatus.WON
            else -> state.status
        })
    }
}
