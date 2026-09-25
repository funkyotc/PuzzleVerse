package com.funkyotc.puzzleverse.pullpin.rescue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.core.SystemUtcDaySource
import com.funkyotc.puzzleverse.core.UtcDaySource
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RescueCampaignUiState(val levelIndex: Int, val state: RescueState)

class RescueCampaignViewModel(
    private val mode: String?, private val puzzleId: String?, private val forceNewGame: Boolean,
    private val streakRepository: StreakRepository?,
    private val daySource: UtcDaySource = SystemUtcDaySource
) : ViewModel() {
    private val challengeDay = daySource.epochDay()
    private var levelIndex = when {
        puzzleId != null -> RescueCampaign.levels.indexOfFirst { it.id == puzzleId }.coerceAtLeast(0)
        mode == "daily" -> RescueCampaign.levels.indexOf(RescueCampaign.dailyLevel(challengeDay))
        else -> 0
    }
    private var session = RescueSession(RescueCampaign.levels[levelIndex])
    private var clock = RescueClock(session)
    private val _ui = MutableStateFlow(RescueCampaignUiState(levelIndex, session.state))
    val ui = _ui.asStateFlow()
    private var completionRepo: PuzzleCompletionRepository? = null
    private var backgroundPaused = false
    private var overlayPaused = true

    val canAdvance get() = mode != "daily" && puzzleId == null && levelIndex < RescueCampaign.levels.lastIndex

    fun setCompletionRepo(repo: PuzzleCompletionRepository) {
        if (completionRepo != null) return
        completionRepo = repo
        if (mode != "daily" && puzzleId == null && !forceNewGame && session.state.status == RescueStatus.READY) {
            val first = RescueCampaign.levels.indexOfFirst { !repo.isCompleted(it.id) }
            selectIndex(if (first < 0) 0 else first)
        }
    }

    fun frame(nanos: Long) {
        if (mode == "daily" && challengeDay != daySource.epochDay()) return
        val before = session.state.status
        clock.frame(nanos)
        if (before != RescueStatus.WON && session.state.status == RescueStatus.WON) {
            completionRepo?.markCompleted(session.level.id)
            if (mode == "daily" && challengeDay == daySource.epochDay()) {
                streakRepository?.recordDailyCompletion("pullpin", challengeDay)
            }
        }
        if (_ui.value.state != session.state) publish()
    }

    fun start() {
        session.start()
        setOverlayPaused(false)
        publish()
    }
    fun setBackgroundPaused(value: Boolean) { backgroundPaused = value; applyPause() }
    fun setOverlayPaused(value: Boolean) { overlayPaused = value; applyPause() }
    private fun applyPause() = clock.setPaused(backgroundPaused || overlayPaused)

    fun pull(id: String): Boolean {
        val accepted = session.pull(id)
        if (accepted) publish()
        return accepted
    }
    fun retry() {
        session = session.retry()
        clock = RescueClock(session)
        applyPause()
        session.start()
        publish()
    }
    fun advance() {
        if (canAdvance && session.state.status == RescueStatus.WON) selectIndex(levelIndex + 1)
    }
    private fun selectIndex(index: Int) {
        levelIndex = index
        session = RescueSession(RescueCampaign.levels[index])
        clock = RescueClock(session)
        overlayPaused = true
        applyPause()
        publish()
    }
    fun hint(): String {
        val next = session.level.solution.firstOrNull { action ->
            session.state.pins.any { it.pin.id == action.pinId && it.acceptedTick == null }
        } ?: return when (session.level.objective) {
            RescueObjective.SURVIVE -> "The route is open. Keep the king's head clear while the stones settle."
            RescueObjective.CLEAR -> "The route is open. Clear the stones around the king's body."
            RescueObjective.ESCAPE -> "The route is open. The king must reach the blue exit."
        }
        return session.level.pins.first { it.id == next.pinId }.label + "."
    }
    private fun publish() { _ui.value = RescueCampaignUiState(levelIndex, session.state) }
}

class RescueCampaignViewModelFactory(
    private val mode: String?, private val puzzleId: String?, private val forceNewGame: Boolean,
    private val streakRepository: StreakRepository?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        RescueCampaignViewModel(mode, puzzleId, forceNewGame, streakRepository) as T
}
