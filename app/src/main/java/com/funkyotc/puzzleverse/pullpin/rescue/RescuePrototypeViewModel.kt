package com.funkyotc.puzzleverse.pullpin.rescue

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RescuePrototypeUiState(val levelIndex: Int, val state: RescueState)

/** Prototype progress is deliberately in memory until the rescue campaign migration in Phase 3. */
class RescuePrototypeViewModel : ViewModel() {
    private var session = RescueSession(RescuePrototype.levels.first())
    private var clock = RescueClock(session)
    private val _ui = MutableStateFlow(RescuePrototypeUiState(0, session.state))
    val ui = _ui.asStateFlow()
    private var backgroundPaused = false
    private var overlayPaused = true

    fun frame(nanos: Long) {
        clock.frame(nanos)
        if (_ui.value.state != session.state) publish()
    }

    fun start() {
        session.start()
        setOverlayPaused(false)
        publish()
    }

    fun setBackgroundPaused(value: Boolean) {
        backgroundPaused = value
        applyPause()
    }

    fun setOverlayPaused(value: Boolean) {
        overlayPaused = value
        applyPause()
    }

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

    fun selectLevel(index: Int) {
        require(index in RescuePrototype.levels.indices)
        session = RescueSession(RescuePrototype.levels[index])
        clock = RescueClock(session)
        overlayPaused = true
        applyPause()
        _ui.value = RescuePrototypeUiState(index, session.state)
    }

    private fun publish() { _ui.value = RescuePrototypeUiState(_ui.value.levelIndex, session.state) }
}
