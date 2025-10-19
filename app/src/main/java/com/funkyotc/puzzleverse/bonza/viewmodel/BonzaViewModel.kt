package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.bonza.data.BonzaConnection
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.data.WordFragment
import com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs

class BonzaViewModel(
    context: Context,
    private val mode: String?,
    private val forceNewGame: Boolean,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val _puzzle = MutableStateFlow(BonzaPuzzleGenerator().generate())
    val puzzle: StateFlow<BonzaPuzzle> = _puzzle

    private var draggedFragment: WordFragment? = null
    private val letterBoxSize = 80f

    init {
        // Initialize Bonza game state here
    }

    fun onDragStart(position: Offset) {
        draggedFragment = getFragmentAt(position)
    }

    fun onDrag(dragAmount: Offset) {
        draggedFragment?.let { fragment ->
            val newPosition = fragment.currentPosition + dragAmount
            updateFragmentPosition(fragment.id, newPosition)
        }
    }

    fun onDragEnd() {
        draggedFragment?.let { fragment ->
            checkForSnapping(fragment)
            checkWinCondition()
        }
        draggedFragment = null
    }

    private fun checkWinCondition() {
        val solved = _puzzle.value.fragments.all { fragment ->
            val solvedFragment = _puzzle.value.solvedFragments.find { it.id == fragment.id }
            solvedFragment?.currentPosition == fragment.currentPosition
        }

        if (solved) {
            _isGameWon.value = true
        }
    }

    private fun checkForSnapping(draggedFragment: WordFragment) {
        _puzzle.value.connections.forEach { connection ->
            val (fragment1, fragment2) = getFragmentsForConnection(connection)

            if (fragment1 != null && fragment2 != null) {
                val (snapped, newPos) = areFragmentsSnappable(fragment1, fragment2, connection.direction)
                if (snapped) {
                    if (draggedFragment.id == fragment1.id) {
                        updateFragmentPosition(fragment1.id, newPos)
                    } else if (draggedFragment.id == fragment2.id) {
                        updateFragmentPosition(fragment2.id, newPos)
                    }
                }
            }
        }
    }

    private fun areFragmentsSnappable(
        fragment1: WordFragment,
        fragment2: WordFragment,
        direction: ConnectionDirection
    ): Pair<Boolean, Offset> {
        val snapThreshold = 50f
        if (direction == ConnectionDirection.HORIZONTAL) {
            val fragment1Width = fragment1.text.length * letterBoxSize
            val newX = fragment2.currentPosition.x - fragment1Width
            val newPosForF1 = Offset(newX, fragment2.currentPosition.y)
            val distance = (fragment1.currentPosition - newPosForF1).getDistance()

            if (distance < snapThreshold) {
                return Pair(true, newPosForF1)
            }
        } else { // VERTICAL
            val fragment1Height = letterBoxSize
            val newY = fragment2.currentPosition.y - fragment1Height
            val newPosForF1 = Offset(fragment2.currentPosition.x, newY)

            val distance = (fragment1.currentPosition - newPosForF1).getDistance()
            if (distance < snapThreshold) {
                return Pair(true, newPosForF1)
            }
        }
        return Pair(false, Offset.Zero)
    }

    private fun getFragmentsForConnection(connection: BonzaConnection): Pair<WordFragment?, WordFragment?> {
        val fragment1 = _puzzle.value.fragments.find { it.id == connection.fragment1Id }
        val fragment2 = _puzzle.value.fragments.find { it.id == connection.fragment2Id }
        return Pair(fragment1, fragment2)
    }

    private fun updateFragmentPosition(fragmentId: Int, newPosition: Offset) {
        val updatedFragments = _puzzle.value.fragments.map {
            if (it.id == fragmentId) {
                it.copy(currentPosition = newPosition)
            } else {
                it
            }
        }
        _puzzle.value = _puzzle.value.copy(fragments = updatedFragments)
    }

    private fun getFragmentAt(position: Offset): WordFragment? {
        return _puzzle.value.fragments.asReversed().find { fragment ->
            val fragmentSize = if (fragment.direction == ConnectionDirection.HORIZONTAL) {
                Size(fragment.text.length * letterBoxSize, letterBoxSize)
            } else {
                Size(letterBoxSize, fragment.text.length * letterBoxSize)
            }
            val fragmentRect = Rect(offset = fragment.currentPosition, size = fragmentSize)
            fragmentRect.contains(position)
        }
    }

    fun newGame() {
        _isGameWon.value = false
        _puzzle.value = BonzaPuzzleGenerator().generate()
    }
}
