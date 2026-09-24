package com.funkyotc.puzzleverse.pullpin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.pullpin.data.*
import com.funkyotc.puzzleverse.pullpin.physics.PullPinSession
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PullPinViewModel(
    private val streakRepository: StreakRepository?,
    private val mode: String?,
    private val puzzleId: String?
) : ViewModel() {
    private var campaignIndex = 0
    private var challengeDay = todayEpochDay()
    private var session = PullPinSession(selectLevel())
    private val _state = MutableStateFlow<PullPinState?>(session.state)
    val state: StateFlow<PullPinState?> = _state.asStateFlow()
    private var completionRepo: PuzzleCompletionRepository? = null
    val canUndo get() = session.canUndo
    var paused = false
    var backgroundPaused = false

    init {
        viewModelScope.launch {
            while (isActive) {
                if (!paused && !backgroundPaused) {
                    if (mode == "daily" && challengeDay != todayEpochDay()) startNewGame()
                    val before = session.state.status
                    session.step()
                    _state.value = session.state
                    if (before != GameStatus.WON && session.state.status == GameStatus.WON) onWin()
                }
                delay(16)
            }
        }
    }

    fun setCompletionRepo(repo: PuzzleCompletionRepository) {
        val firstConnection = completionRepo == null
        completionRepo = repo
        if (firstConnection && mode != "daily" && puzzleId == null && session.state.moves == 0) {
            campaignIndex = levelPool().indexOfFirst { !repo.isCompleted(it.id) }.coerceAtLeast(0)
            session = PullPinSession(selectLevel())
            _state.value = session.state
        }
    }

    /** New game advances the campaign; retry always keeps the current layout. */
    fun startNewGame() {
        if (mode != "daily" && puzzleId == null) campaignIndex++
        challengeDay = todayEpochDay()
        session = PullPinSession(selectLevel())
        _state.value = session.state
    }

    fun retry() {
        session = PullPinSession(session.level)
        _state.value = session.state
    }

    fun undo() {
        if (session.state.status == GameStatus.WON) return
        session = session.undo()
        _state.value = session.state
    }

    fun removePin(id: String): Boolean {
        val accepted = session.pull(id)
        _state.value = session.state
        return accepted
    }

    fun hint(): String {
        val s = session.state
        if (s.status == GameStatus.LOST) return "Undo your last pull, or retry this board."
        val next = s.level.solution.firstOrNull { id -> s.pins.any { it.id == id && !it.removed } }
            ?: return "Wait for the remaining balls to reach their cups."
        val pin = s.pins.first { it.id == next }
        if (pin.isPulling) return "Let the balls settle before the next pull."
        if (pin.unlockAfter > session.rescued) return "Save ${pin.unlockAfter - session.rescued} more balls to unlock this exit."
        val lane = next.substringAfterLast('_').toInt() + 1
        return if (next.startsWith("mix")) "Pull MIX in chamber $lane, then wait for all grey balls to gain color."
        else if (next.startsWith("drain")) "Wait for the lower reservoir in chamber $lane to gain color, then pull DRAIN."
        else "When chamber $lane has no grey balls left, pull its EXIT pin."
    }

    private fun onWin() {
        if (mode == "daily" && challengeDay == todayEpochDay()) {
            streakRepository?.recordDailyCompletion("pullpin", challengeDay)
        }
        completionRepo?.markCompleted(session.level.id)
    }

    private fun levelPool(): List<PullPinLevel> =
        PullPinPregenerated.PUZZLES_BY_DIFFICULTY.entries.firstOrNull { it.key.equals(mode, true) }?.value
            ?: PullPinPregenerated.ALL_LEVELS

    private fun selectLevel(): PullPinLevel {
        val all = PullPinPregenerated.ALL_LEVELS
        if (puzzleId != null) return all.firstOrNull { it.id == puzzleId }
            ?: error("Unknown Pull the Pin puzzle: $puzzleId")
        if (mode == "daily") return all[Math.floorMod(challengeDay, all.size.toLong()).toInt()]
        val pool = levelPool()
        return pool[campaignIndex % pool.size]
    }
}

class PullPinViewModelFactory(
    private val streakRepository: StreakRepository?, private val mode: String?, private val puzzleId: String?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PullPinViewModel(streakRepository, mode, puzzleId) as T
}
