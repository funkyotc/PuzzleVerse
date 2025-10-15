package com.funkyotc.puzzleverse.constellations.viewmodel

import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import com.funkyotc.puzzleverse.constellations.data.Star
import com.funkyotc.puzzleverse.constellations.generator.ConstellationsPuzzleGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConstellationsViewModel : ViewModel() {

    private val _puzzle = MutableStateFlow<ConstellationsPuzzle?>(null)
    val puzzle: StateFlow<ConstellationsPuzzle?> = _puzzle

    private val _drawnConnections = MutableStateFlow<Set<Pair<Int, Int>>>(emptySet())
    val drawnConnections: StateFlow<Set<Pair<Int, Int>>> = _drawnConnections

    private var selectedStar: Star? = null

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val generator = ConstellationsPuzzleGenerator()

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        val newPuzzle = generator.generate()
        _puzzle.value = newPuzzle
        _drawnConnections.value = emptySet()
        _isGameWon.value = false
    }

    fun onStarClicked(starId: Int) {
        val clickedStar = _puzzle.value?.stars?.find { it.id == starId } ?: return

        if (selectedStar == null) {
            selectedStar = clickedStar
        } else {
            val currentSelectedStar = selectedStar!!
            if (currentSelectedStar.id != clickedStar.id) {
                val newConnection = Pair(currentSelectedStar.id, clickedStar.id)
                if (!isConnectionCrossing(newConnection)) {
                    _drawnConnections.value = _drawnConnections.value + newConnection
                    checkWinCondition()
                }
            }
            selectedStar = null
        }
    }

    private fun isConnectionCrossing(newConnection: Pair<Int, Int>): Boolean {
        val stars = _puzzle.value?.stars ?: return false
        val fromStar = stars.find { it.id == newConnection.first }!!
        val toStar = stars.find { it.id == newConnection.second }!!

        for (connection in _drawnConnections.value) {
            val existingFromStar = stars.find { it.id == connection.first }!!
            val existingToStar = stars.find { it.id == connection.second }!!

            if (doLinesIntersect(fromStar, toStar, existingFromStar, existingToStar)) {
                return true
            }
        }
        return false
    }

    private fun doLinesIntersect(p1: Star, q1: Star, p2: Star, q2: Star): Boolean {
        val o1 = orientation(p1, q1, p2)
        val o2 = orientation(p1, q1, q2)
        val o3 = orientation(p2, q2, p1)
        val o4 = orientation(p2, q2, q1)

        return o1 != o2 && o3 != o4

        // Special cases for co-linear points can be added here if needed
    }

    private fun orientation(p: Star, q: Star, r: Star): Int {
        val value = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
        if (value == 0f) return 0 // Co-linear
        return if (value > 0) 1 else 2 // Clockwise or counter-clockwise
    }


    private fun checkWinCondition() {
        val puzzleValue = _puzzle.value ?: return
        val requiredConnections = puzzleValue.connections.map { Pair(it.from, it.to) }.toSet()
        _isGameWon.value = _drawnConnections.value.size == requiredConnections.size &&
                _drawnConnections.value.all { requiredConnections.contains(it) || requiredConnections.contains(it.second to it.first) }
    }
}
