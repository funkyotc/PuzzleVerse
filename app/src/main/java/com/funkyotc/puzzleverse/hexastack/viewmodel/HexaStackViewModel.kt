package com.funkyotc.puzzleverse.hexastack.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.hexastack.data.AxialCoord
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLevel
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLogic
import com.funkyotc.puzzleverse.hexastack.data.HexaStackPregenerated
import com.funkyotc.puzzleverse.hexastack.data.HexaStackRepository
import com.funkyotc.puzzleverse.hexastack.data.HexaStackState
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HexaStackViewModel(
    context: Context? = null,
    private val mode: String? = null,
    private val forceNewGame: Boolean = false,
    private val streakRepository: StreakRepository,
    private val settingsRepository: SettingsRepository? = null,
    private val puzzleId: String? = null
) : ViewModel() {

    private val repository = HexaStackRepository(context)
    private val completionRepo = PuzzleCompletionRepository(context, "Hexa Stack")
    private val gridKey = when {
        mode == "daily" -> "daily_hexastack_grid"
        puzzleId != null -> "puzzle_${puzzleId}"
        else -> "standard_hexastack_grid"
    }

    private val _state = MutableStateFlow<HexaStackState?>(null)
    val state: StateFlow<HexaStackState?> = _state.asStateFlow()

    private val _elapsedSeconds = MutableStateFlow(0)
    val elapsedSeconds: StateFlow<Int> = _elapsedSeconds.asStateFlow()

    private var timerJob: Job? = null

    private val _events = MutableSharedFlow<HexaStackEvent>(extraBufferCapacity = 8)
    val events: SharedFlow<HexaStackEvent> = _events.asSharedFlow()

    init {
        val initial = if (forceNewGame) generateFreshState() else tryRestore()
        _state.value = initial
        if (initial != null) startTimer()
    }

    private fun tryRestore(): HexaStackState? {
        val save = repository.loadGame(gridKey) ?: return generateFreshState()
        val level = when {
            mode == "puzzle" && puzzleId != null -> null // puzzle mode never saves
            mode == "daily" -> pickLevelForDaily()
            else -> HexaStackPregenerated.getPuzzleById(save.levelId)?.toLevel()
        } ?: return generateFreshState()
        if (level.id != save.levelId) return generateFreshState()
        val cells = save.cells.map { AxialCoord(it.q, it.r) to it.tiles }.toMap()
        val state = HexaStackState(
            level = level,
            cells = cells,
            tray = save.tray,
            deckIndex = save.deckIndex,
            score = save.score,
            moves = save.moves
        ).let { HexaStackLogic.finish(it) }
        return state
    }

    private fun generateFreshState(): HexaStackState? {
        val level = pickLevel() ?: return null
        val state = HexaStackLogic.initialState(level)
        saveState(state)
        return state
    }

    private fun pickLevel(): HexaStackLevel? {
        return when {
            mode == "puzzle" && puzzleId != null ->
                HexaStackPregenerated.getPuzzleById(puzzleId)?.toLevel()
            mode == "daily" -> pickLevelForDaily()
            else -> HexaStackPregenerated.ALL_PUZZLES.randomOrNull()?.toLevel()
        }
    }

    private fun pickLevelForDaily(): HexaStackLevel? {
        val rng = Random(todayEpochDay())
        val puzzles = HexaStackPregenerated.ALL_PUZZLES
        return puzzles[rng.nextInt(puzzles.size)].toLevel()
    }

    private fun saveState(state: HexaStackState) {
        if (mode == "puzzle") return // don't save puzzle mode state
        val save = HexaStackRepository.HexaStackSave(
            levelId = state.level.id,
            cells = state.cells.map { (c, tiles) ->
                HexaStackRepository.SavedCell(c.q, c.r, tiles)
            },
            tray = state.tray,
            deckIndex = state.deckIndex,
            score = state.score,
            moves = state.moves
        )
        repository.saveGame(gridKey, save)
    }

    private fun clearSavedState() {
        repository.removeKey(gridKey)
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _elapsedSeconds.value++
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun startNewGame() {
        clearSavedState()
        _elapsedSeconds.value = 0
        stopTimer()
        val state = generateFreshState()
        if (state != null) startTimer()
    }

    /**
     * Place the tray group in [traySlot] at [coord]. Returns false if the move is illegal.
     * Emits Placed/Popped events for sounds, then re-evaluates win/loss after a short
     * pop-animation window so the UI can flash popping stacks before finishing.
     * When merges occur, emits a Moves event with a recorded sequence of transfers so
     * the UI can animate them before the final resolved state is applied.
     */
    fun place(traySlot: Int, coord: AxialCoord) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return
        if (currentState.poppingCoords.isNotEmpty()) return // ignore input mid-animation

        // Use the new logic variant that returns recorded moves
        val placeWithMoves = HexaStackLogic.placeAndResolveWithMoves(currentState, traySlot, coord) ?: return
        val (resolvedState, moves) = placeWithMoves

        // Estimate animation duration so ViewModel delays applying the resolved state until UI finishes
        val perTileMs = 160   // translation + flip overlap
        val tileStaggerMs = 40
        val stackStaggerMs = 120
        val durationMs = moves.fold(0) { acc, m -> acc + perTileMs + (m.tiles.size - 1) * tileStaggerMs } + ((moves.size - 1) * stackStaggerMs)

        // Emit placed sound then emit moves for UI animation
        viewModelScope.launch {
            _events.emit(HexaStackEvent.Placed)
            if (moves.isNotEmpty()) {
                _events.emit(HexaStackEvent.Moves(moves))
                // wait for UI to finish animations before applying resolved state
                delay(durationMs.toLong())
            }
            // After animation completes, update state to resolved result and emit pop sound if any
            _state.value = resolvedState
            if (resolvedState.lastPoppedTiles > 0) {
                _events.emit(HexaStackEvent.Popped(resolvedState.lastPoppedTiles, resolvedState.score))
            }

            if (resolvedState.poppingCoords.isEmpty()) {
                afterResolve(resolvedState)
            } else {
                // keep the popping coords visible for the POP_ANIMATION_MS then settle
                delay(POP_ANIMATION_MS)
                val settled = _state.value?.copy(poppingCoords = emptySet()) ?: return@launch
                _state.value = settled
                afterResolve(settled)
            }
        }
    }

    private fun afterResolve(state: HexaStackState) {
        val finished = HexaStackLogic.finish(state)
        _state.value = finished
        saveState(finished)
        if (finished.isWon) onWin(finished)
        if (finished.isGameOver) onGameOver()
    }

    private fun onWin(state: HexaStackState) {
        stopTimer()
        clearSavedState()
        settingsRepository?.addWin()
        if (mode == "puzzle" && puzzleId != null) {
            completionRepo.markCompleted(puzzleId)
        }
        if (mode == "daily") {
            val today = todayEpochDay()
            val streak = streakRepository.getStreak("hexastack")
            if (streak.lastCompletedEpochDay != today) {
                val newStreak = streak.copy(
                    count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                    lastCompletedEpochDay = today
                )
                streakRepository.saveStreak(newStreak)
            }
        }
        viewModelScope.launch {
            _events.emit(HexaStackEvent.Won(state.score))
        }
    }

    private fun onGameOver() {
        stopTimer()
        viewModelScope.launch {
            _events.emit(HexaStackEvent.GameOver)
        }
    }

    companion object {
        const val POP_ANIMATION_MS = 350L
    }
}

sealed class HexaStackEvent {
    data class Won(val score: Int) : HexaStackEvent()
    data object GameOver : HexaStackEvent()
    data object Placed : HexaStackEvent()
    data class Popped(val tiles: Int, val totalScore: Int) : HexaStackEvent()
    data class Moves(val moves: List<com.funkyotc.puzzleverse.hexastack.data.HexaStackLogic.Move>) : HexaStackEvent()
}

class HexaStackViewModelFactory(
    private val context: Context,
    private val mode: String?,
    private val forceNewGame: Boolean = false,
    private val streakRepository: StreakRepository,
    private val settingsRepository: SettingsRepository? = null,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HexaStackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HexaStackViewModel(context, mode, forceNewGame, streakRepository, settingsRepository, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
