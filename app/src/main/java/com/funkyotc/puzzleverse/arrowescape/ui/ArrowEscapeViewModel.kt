package com.funkyotc.puzzleverse.arrowescape.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.arrowescape.data.ArrowEscapePregenerated
import com.funkyotc.puzzleverse.arrowescape.model.Arrow
import com.funkyotc.puzzleverse.arrowescape.model.GridState
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ArrowEscapeViewModel(
    private val streakRepository: StreakRepository,
    private val settingsRepository: SettingsRepository,
    private val mode: String,
    private val puzzleId: String? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArrowEscapeUiState())
    val uiState: StateFlow<ArrowEscapeUiState> = _uiState.asStateFlow()

    private var gridState: GridState? = null
    private val history = mutableListOf<List<Arrow>>()

    init {
        loadPuzzle()
    }

    private fun loadPuzzle() {
        val difficulty = when (mode) {
            "daily" -> "Medium"
            else -> "Easy" // For now just default to Easy if not specified or standard
        }
        
        // Pick a random puzzle from the generated list for now
        val puzzles = ArrowEscapePregenerated.PUZZLES_BY_DIFFICULTY[difficulty] ?: emptyList()
        val arrows = if (puzzles.isNotEmpty()) puzzles.random() else emptyList()
        
        // Determine grid size based on puzzle
        val width = if (difficulty == "Easy") 10 else if (difficulty == "Medium") 20 else 30
        val height = width

        gridState = GridState(width, height, arrows)
        _uiState.value = ArrowEscapeUiState(
            gridWidth = width,
            gridHeight = height,
            arrows = arrows,
            isComplete = false
        )
        history.clear()
        saveStateToHistory()
    }

    private fun saveStateToHistory() {
        gridState?.let {
            history.add(it.arrows.values.map { arrow -> 
                arrow.copy(segments = arrow.segments.toList()) 
            })
        }
    }

    fun undo() {
        if (history.size > 1) {
            history.removeAt(history.lastIndex) // current state
            val previousArrows = history.last()
            
            gridState = GridState(_uiState.value.gridWidth, _uiState.value.gridHeight, previousArrows)
            _uiState.value = _uiState.value.copy(
                arrows = previousArrows,
                isComplete = false
            )
        }
    }

    fun onArrowTapped(arrowId: Int, onBump: () -> Unit, onMove: () -> Unit) {
        val state = gridState ?: return
        if (state.isComplete()) return

        val arrow = state.arrows[arrowId] ?: return

        // Check if path is clear to the edge
        var isBlocked = false
        var checkPos = arrow.head.move(arrow.direction)
        
        while (checkPos.x in 0 until state.width && checkPos.y in 0 until state.height) {
            val gridOccupant = state.getGridArray()[checkPos.y][checkPos.x]
            if (gridOccupant != 0 && gridOccupant != arrowId) {
                isBlocked = true
                break
            }
            checkPos = checkPos.move(arrow.direction)
        }

        if (isBlocked) {
            onBump()
        } else {
            // Move it out
            viewModelScope.launch {
                onMove()
                
                // Animate stepping out (frame-synced at display refresh rate)
                var moving = true
                var lastFrame = System.currentTimeMillis()
                while (isActive && moving) {
                    val nowFrame = System.currentTimeMillis()
                    // dt unused here but kept for parity
                    val dt = if (lastFrame == 0L) 1.0 / 60.0 else ((nowFrame - lastFrame) / 1000.0).coerceAtMost(1.0 / 60.0)
                    lastFrame = nowFrame

                    moving = state.moveArrow(arrowId)
                    _uiState.value = _uiState.value.copy(
                        arrows = state.arrows.values.toList()
                    )

                    kotlinx.coroutines.delay(16)
                }
                
                saveStateToHistory()
                
                if (state.isComplete()) {
                    _uiState.value = _uiState.value.copy(isComplete = true)
                    if (mode == "daily") {
                        val streak = streakRepository.getStreak("arrowescape")
                        val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                        if (streak.lastCompletedEpochDay != today) {
                            val newStreakCount = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1
                            streakRepository.saveStreak(streak.copy(count = newStreakCount, lastCompletedEpochDay = today))
                        }
                    }
                }
            }
        }
    }
}

data class ArrowEscapeUiState(
    val gridWidth: Int = 10,
    val gridHeight: Int = 10,
    val arrows: List<Arrow> = emptyList(),
    val isComplete: Boolean = false
)
