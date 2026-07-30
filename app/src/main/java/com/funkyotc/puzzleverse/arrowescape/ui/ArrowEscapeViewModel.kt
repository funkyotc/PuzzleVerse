package com.funkyotc.puzzleverse.arrowescape.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.arrowescape.data.ArrowEscapePregenerated
import com.funkyotc.puzzleverse.arrowescape.model.Arrow
import com.funkyotc.puzzleverse.arrowescape.model.GridState
import com.funkyotc.puzzleverse.arrowescape.model.LevelShape
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
        val specificPuzzle = if (puzzleId != null) ArrowEscapePregenerated.getPuzzleById(puzzleId) else null
        val difficulty = specificPuzzle?.difficulty ?: when (mode) {
            "daily" -> "Medium"
            else -> "Easy"
        }
        
        val arrows = if (specificPuzzle != null) {
            specificPuzzle.arrows
        } else {
            val puzzles = ArrowEscapePregenerated.PUZZLES_BY_DIFFICULTY[difficulty] ?: emptyList()
            if (puzzles.isNotEmpty()) puzzles.random().arrows else emptyList()
        }

        val shape = specificPuzzle?.shape ?: LevelShape.SQUARE
        
        // Determine grid size based on puzzle
        val maxCoord = arrows.flatMap { it.segments }.let { segs ->
            if (segs.isEmpty()) 10 else maxOf(segs.maxOf { it.x }, segs.maxOf { it.y }) + 1
        }
        val width = when (difficulty) {
            "Easy" -> 10
            "Medium" -> 20
            "Hard" -> 30
            "Expert" -> 40
            "Master" -> 50
            else -> maxOf(10, maxCoord)
        }
        val height = width

        gridState = GridState(width, height, arrows, shape)
        _uiState.value = ArrowEscapeUiState(
            gridWidth = width,
            gridHeight = height,
            shape = shape,
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
            
            gridState = GridState(_uiState.value.gridWidth, _uiState.value.gridHeight, previousArrows, _uiState.value.shape)
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

        if (!state.canMove(arrowId)) {
            onBump()
        } else {
            // Move it out
            viewModelScope.launch {
                onMove()
                
                var moving = true
                var lastFrame = System.currentTimeMillis()
                while (isActive && moving) {
                    val nowFrame = System.currentTimeMillis()
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

    fun resetPuzzle() {
        loadPuzzle()
    }
}

data class ArrowEscapeUiState(
    val gridWidth: Int = 10,
    val gridHeight: Int = 10,
    val shape: LevelShape = LevelShape.SQUARE,
    val arrows: List<Arrow> = emptyList(),
    val isComplete: Boolean = false
)
