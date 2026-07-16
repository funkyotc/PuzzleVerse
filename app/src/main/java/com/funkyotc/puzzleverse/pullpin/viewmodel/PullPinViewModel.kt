package com.funkyotc.puzzleverse.pullpin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.pullpin.data.BallRuntime
import com.funkyotc.puzzleverse.pullpin.data.GameStatus
import com.funkyotc.puzzleverse.pullpin.data.PinData
import com.funkyotc.puzzleverse.pullpin.data.PullPinLevel
import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.pullpin.physics.PullPinPhysicsEngine
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.hypot

class PullPinViewModel(
    private val streakRepository: StreakRepository?,
    private val mode: String?,
    private val puzzleId: String?
) : ViewModel() {

    private val _state = MutableStateFlow<PullPinState?>(null)
    val state: StateFlow<PullPinState?> = _state.asStateFlow()

    private val physicsEngine = PullPinPhysicsEngine()

    private var physicsJob: Job? = null

    private var completionRepo: PuzzleCompletionRepository? = null

    init {
        startNewGame()
    }

    fun setCompletionRepo(repo: PuzzleCompletionRepository) {
        completionRepo = repo
    }

    fun startNewGame() {
        val level = selectLevel()

        val balls = level.balls.map { BallRuntime(it.id, it.x, it.y, it.color) }
        val pins = level.pins.map { it.copy() }

        _state.value = PullPinState(
            level = level,
            balls = balls,
            pins = pins,
            moves = 0,
            status = GameStatus.IDLE,
            lostReason = null
        )

        physicsEngine.initWorld(level)

        startPhysicsLoop()
    }

    private fun startPhysicsLoop() {
        physicsJob?.cancel()
        physicsJob = viewModelScope.launch {
            var prevX = emptyMap<String, Float>()
            var prevY = emptyMap<String, Float>()
            while (isActive) {
                delay(16)
                val s = _state.value ?: continue
                if (s.status == GameStatus.WON || s.status == GameStatus.LOST) continue

                physicsEngine.step(1.0 / 60.0)
                val transforms = physicsEngine.getBallTransforms()

                val radiusById = s.level.balls.associate { it.id to it.radius }

                var updated = false
                val newBalls = s.balls.map { b ->
                    val t = transforms[b.id]
                    if (t != null) {
                        if (t.first != b.x || t.second != b.y) updated = true
                        b.copy(x = t.first, y = t.second)
                    } else b
                }.toMutableList()

                // Color merge: grey balls take the color of a nearby colored ball.
                for (i in newBalls.indices) {
                    val b = newBalls[i]
                    if (b.color == 0 && !b.captured) {
                        val rA = radiusById[b.id] ?: 14f
                        for (j in newBalls.indices) {
                            if (i == j) continue
                            val o = newBalls[j]
                            if (o.color == 0 || o.captured) continue
                            val rB = radiusById[o.id] ?: 14f
                            val d = hypot(b.x - o.x, b.y - o.y)
                            if (d <= rA + rB + 30f) {
                                newBalls[i] = b.copy(color = o.color)
                                updated = true
                                break
                            }
                        }
                    }
                }

                // Cup capture / wrong-cup / grey-in-cup detection.
                var lostReason: String? = null
                var lost = false
                for (i in newBalls.indices) {
                    val b = newBalls[i]
                    if (b.captured) continue
                    for (cup in s.level.cups) {
                        val d = hypot(b.x - cup.x, b.y - cup.y)
                        if (d <= cup.radius) {
                            if (b.color == 0) {
                                lostReason = "A grey ball fell into a cup!"
                                lost = true
                                break
                            } else if (b.color != cup.color) {
                                lostReason = "A ball landed in the wrong cup!"
                                lost = true
                                break
                            } else {
                                newBalls[i] = b.copy(captured = true, inCup = true)
                                updated = true
                            }
                        }
                    }
                    if (lost) break
                }

                // Out of bounds.
                if (!lost) {
                    for (i in newBalls.indices) {
                        val b = newBalls[i]
                        if (!b.outOfBounds && physicsEngine.isBallOutOfBounds(b.id)) {
                            newBalls[i] = b.copy(outOfBounds = true)
                            lostReason = "A ball fell out of the world!"
                            lost = true
                            updated = true
                            break
                        }
                    }
                }

                // Win check: every cup has a matching captured ball.
                val cupColors = s.level.cups.map { it.color }
                val won = cupColors.all { color ->
                    newBalls.any { it.captured && it.color == color }
                }

                // Settle detection.
                val moved = newBalls.any { b ->
                    (prevX[b.id] != null && hypot(
                        (b.x - (prevX[b.id] ?: b.x)),
                        (b.y - (prevY[b.id] ?: b.y))
                    ) > 0.5f)
                }

                var status = s.status
                when {
                    lost -> {
                        status = GameStatus.LOST
                        updated = true
                    }
                    won -> {
                        status = GameStatus.WON
                        updated = true
                    }
                    s.status == GameStatus.RUNNING && !moved -> {
                        status = GameStatus.IDLE
                        updated = true
                    }
                }

                if (updated || status != s.status) {
                    val finalState = s.copy(
                        balls = newBalls,
                        status = status,
                        lostReason = if (status == GameStatus.LOST) lostReason else null
                    )
                    _state.value = finalState
                    prevX = newBalls.associate { it.id to it.x }
                    prevY = newBalls.associate { it.id to it.y }
                    if (status == GameStatus.WON) onWin()
                } else {
                    prevX = newBalls.associate { it.id to it.x }
                    prevY = newBalls.associate { it.id to it.y }
                }
            }
        }
    }

    fun removePin(pinId: String) {
        val current = _state.value ?: return
        if (current.status == GameStatus.WON || current.status == GameStatus.LOST) return

        val idx = current.pins.indexOfFirst { it.id == pinId && !it.removed && !it.isPulling }
        if (idx == -1) return

        val newPins = current.pins.toMutableList()
        newPins[idx] = newPins[idx].copy(isPulling = true)

        _state.value = current.copy(
            pins = newPins,
            moves = current.moves + 1,
            status = GameStatus.RUNNING
        )

        viewModelScope.launch {
            delay(220)
            finishRemovePin(pinId)
        }
    }

    private fun finishRemovePin(pinId: String) {
        val current = _state.value ?: return
        if (current.status == GameStatus.WON || current.status == GameStatus.LOST) return

        val idx = current.pins.indexOfFirst { it.id == pinId }
        if (idx == -1) return

        val newPins = current.pins.toMutableList()
        newPins[idx] = newPins[idx].copy(removed = true, isPulling = false)

        physicsEngine.removePin(pinId)

        _state.value = current.copy(pins = newPins)
    }

    private fun onWin() {
        val current = _state.value ?: return
        if (mode == "daily" && streakRepository != null) {
            val streak = streakRepository.getStreak("pullpin")
            val today = todayEpochDay()
            val newCount = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1
            streakRepository.saveStreak(
                streak.copy(count = newCount, lastCompletedEpochDay = today)
            )
        }
        if (mode == "puzzle" && puzzleId != null && completionRepo != null) {
            completionRepo!!.markCompleted(puzzleId)
        }
    }

    private fun selectLevel(): PullPinLevel {
        val allLevels = PullPinPregenerated.ALL_LEVELS
        if (allLevels.isEmpty()) error("No pullpin levels available")

        if (puzzleId != null) {
            return allLevels.find { it.id == puzzleId } ?: allLevels.first()
        }

        return when {
            mode == "daily" -> {
                val today = todayEpochDay()
                allLevels[(today % allLevels.size).toInt()]
            }
            mode == "easy" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Easy"]?.random() ?: allLevels.first()
            mode == "medium" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Medium"]?.random() ?: allLevels.first()
            mode == "hard" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Hard"]?.random() ?: allLevels.first()
            mode == "expert" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Expert"]?.random() ?: allLevels.first()
            else -> allLevels.random()
        }
    }
}

class PullPinViewModelFactory(
    private val streakRepository: StreakRepository?,
    private val mode: String?,
    private val puzzleId: String?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PullPinViewModel(streakRepository, mode, puzzleId) as T
    }
}
