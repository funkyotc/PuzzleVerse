package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.bonza.data.BonzaConnection
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.data.WordFragment
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

    private val _puzzle = MutableStateFlow(generateSamplePuzzle())
    val puzzle: StateFlow<BonzaPuzzle> = _puzzle

    private var draggedFragment: WordFragment? = null

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
                if(snapped) {
                    if(draggedFragment.id == fragment1.id) {
                        updateFragmentPosition(fragment1.id, newPos)
                    } else if(draggedFragment.id == fragment2.id) {
                        updateFragmentPosition(fragment2.id, newPos)
                    }
                }
            }
        }
    }

    private fun areFragmentsSnappable(fragment1: WordFragment, fragment2: WordFragment, direction: ConnectionDirection): Pair<Boolean, Offset> {
        val snapThreshold = 50f
        if (direction == ConnectionDirection.HORIZONTAL) {
            val distance = abs(fragment1.currentPosition.x + fragment1.text.length * 20f - fragment2.currentPosition.x) // Approximate width
            if (distance < snapThreshold) {
                return Pair(true, Offset(fragment2.currentPosition.x - fragment1.text.length * 20f, fragment2.currentPosition.y))
            }
        } else { // VERTICAL
            val distance = abs(fragment1.currentPosition.y - (fragment2.currentPosition.y - 40f))
            if (distance < snapThreshold) {
                return Pair(true, Offset(fragment1.currentPosition.x, fragment2.currentPosition.y - 40f))
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
        return _puzzle.value.fragments.find { fragment ->
            val fragmentRect = Rect(fragment.currentPosition, fragment.currentPosition + Offset(fragment.text.length * 20f, 40f)) // Approximate bounding box
            fragmentRect.contains(position)
        }
    }

    fun newGame() {
        _isGameWon.value = false
        _puzzle.value = generateSamplePuzzle()
    }

    private fun generateSamplePuzzle(): BonzaPuzzle {
        val fragments = listOf(
            WordFragment(1, "APP", Offset(100f, 100f)),
            WordFragment(2, "LE", Offset(200f, 100f)),
            WordFragment(3, "BAN", Offset(100f, 200f)),
            WordFragment(4, "ANA", Offset(200f, 200f)),
            WordFragment(5, "OR", Offset(100f, 300f)),
            WordFragment(6, "ANGE", Offset(200f, 300f))
        )

        val solvedFragments = listOf(
            WordFragment(1, "APP", Offset(100f, 100f)),
            WordFragment(2, "LE", Offset(160f, 100f)),
            WordFragment(3, "BAN", Offset(100f, 140f)),
            WordFragment(4, "ANA", Offset(160f, 140f)),
            WordFragment(5, "OR", Offset(100f, 180f)),
            WordFragment(6, "ANGE", Offset(140f, 180f))
        )

        return BonzaPuzzle(
            theme = "Fruits",
            words = listOf("APPLE", "BANANA", "ORANGE"),
            fragments = fragments,
            connections = listOf(
                BonzaConnection(1, 2, ConnectionDirection.HORIZONTAL),
                BonzaConnection(3, 4, ConnectionDirection.HORIZONTAL),
                BonzaConnection(5, 6, ConnectionDirection.HORIZONTAL)
            ),
            solvedFragments = solvedFragments
        )
    }
}