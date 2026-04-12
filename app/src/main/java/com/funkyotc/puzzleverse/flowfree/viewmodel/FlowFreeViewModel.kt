package com.funkyotc.puzzleverse.flowfree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.flowfree.data.ColorDot
import com.funkyotc.puzzleverse.flowfree.data.FlowDifficulty
import com.funkyotc.puzzleverse.flowfree.data.FlowPath
import com.funkyotc.puzzleverse.flowfree.data.FlowFreeState
import com.funkyotc.puzzleverse.flowfree.data.FlowFreePuzzleLibrary
import com.funkyotc.puzzleverse.flowfree.data.Point
import com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlowFreeViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {
    private val _state = MutableStateFlow(FlowFreeState())
    val state: StateFlow<FlowFreeState> = _state.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _difficulty = MutableStateFlow(FlowDifficulty.EASY)
    val difficulty: StateFlow<FlowDifficulty> = _difficulty.asStateFlow()

    init {
        startNewGame()
    }

    fun setDifficulty(diff: FlowDifficulty) {
        _difficulty.value = diff
        startNewGame()
    }

    fun startNewGame() {
        viewModelScope.launch(Dispatchers.Default) {
            _isGenerating.value = true

            if (puzzleId != null) {
                // Load specific puzzle by ID
                val pregen = FlowFreePregenerated.getPuzzleById(puzzleId)
                if (pregen != null) {
                    _state.value = FlowFreeState(
                        rows = pregen.size,
                        cols = pregen.size,
                        dots = pregen.dots,
                        paths = emptyList(),
                        isWon = false
                    )
                    _isGenerating.value = false
                    return@launch
                }
            }

            val puzzle = if (mode == "daily") {
                val today = java.time.LocalDate.now()
                val seed = today.toEpochDay()
                FlowFreePuzzleLibrary.getDailyPuzzle(seed)
            } else {
                FlowFreePuzzleLibrary.getRandomPuzzle(_difficulty.value)
            }

            _state.value = FlowFreeState(
                rows = puzzle.size,
                cols = puzzle.size,
                dots = puzzle.dots,
                paths = emptyList(),
                isWon = false
            )

            _isGenerating.value = false
        }
    }

    fun startPath(colorId: Int, r: Int, c: Int) {
        if (_state.value.isWon) return

        // Check if we started on a dot of this color
        val dot = _state.value.dots.find { it.colorId == colorId } ?: return
        if (dot.start != Point(r, c) && dot.end != Point(r, c)) return

        // Create new path or reset
        val newPaths = _state.value.paths.filter { it.colorId != colorId }.toMutableList()
        newPaths.add(FlowPath(colorId, listOf(Point(r, c))))

        _state.update { it.copy(paths = newPaths) }
    }

    fun extendPath(colorId: Int, r: Int, c: Int) {
        val st = _state.value
        if (st.isWon) return

        val activePath = st.paths.find { it.colorId == colorId } ?: return
        val currentPoints = activePath.path.toMutableList()
        val lastPoint = currentPoints.last()
        val newPoint = Point(r, c)

        if (lastPoint == newPoint) return

        // allow moving only to adjacent orthogonal cells
        if (Math.abs(lastPoint.r - r) + Math.abs(lastPoint.c - c) != 1) return

        // Path retracting
        if (currentPoints.size > 1 && currentPoints[currentPoints.size - 2] == newPoint) {
            currentPoints.removeAt(currentPoints.size - 1)
            updatePaths(st, colorId, currentPoints)
            return
        }

        // Cannot intersect itself
        if (currentPoints.contains(newPoint)) return

        // Cannot intersect other paths
        val intersectsOtherPath = st.paths.any { p -> p.colorId != colorId && p.path.contains(newPoint) }
        if (intersectsOtherPath) return

        // Cannot go through a dot of a DIFFERENT color
        val isDifferentColorDot = st.dots.any { it.colorId != colorId && (it.start == newPoint || it.end == newPoint) }
        if (isDifferentColorDot) return

        // Add to path
        val dot = st.dots.find { it.colorId == colorId }!!
        // If we already reached the OTHER dot, stop extending further
        if (currentPoints.contains(dot.start) && currentPoints.contains(dot.end)) {
            return
        }

        currentPoints.add(newPoint)
        updatePaths(st, colorId, currentPoints)
    }

    private fun updatePaths(st: FlowFreeState, colorId: Int, newPathList: List<Point>) {
        val newPaths = st.paths.map { if (it.colorId == colorId) it.copy(path = newPathList) else it }
        val won = checkWin(newPaths, st.dots, st.rows, st.cols)
        _state.update { it.copy(paths = newPaths, isWon = won) }
    }

    private fun checkWin(paths: List<FlowPath>, dots: List<ColorDot>, rows: Int, cols: Int): Boolean {
        // 1. All paths must touch BOTH start and end
        for (d in dots) {
            val p = paths.find { it.colorId == d.colorId } ?: return false
            if (!p.path.contains(d.start) || !p.path.contains(d.end)) return false
        }

        // 2. All grid cells must be covered by SOME path
        var coveredCount = 0
        for (p in paths) {
            coveredCount += p.path.size
        }
        if (coveredCount != rows * cols) return false

        return true
    }
}

class FlowFreeViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowFreeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlowFreeViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
