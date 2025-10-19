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

    private var draggedFragmentId: Int? = null
    private val letterBoxSize = 80f

    init {
        // Initialize Bonza game state here
    }

    fun onDragStart(position: Offset) {
        val fragment = getFragmentAt(position)
        draggedFragmentId = fragment?.id
    }

    fun onDrag(dragAmount: Offset) {
        draggedFragmentId?.let { fragmentId ->
            val currentFragment = _puzzle.value.fragments.find { it.id == fragmentId }
            currentFragment?.let { fragment ->
                val newPosition = fragment.currentPosition + dragAmount
                updateFragmentPosition(fragmentId, newPosition)
            }
        }
    }

    fun onDragEnd() {
        draggedFragmentId?.let { fragmentId ->
            val fragment = _puzzle.value.fragments.find { it.id == fragmentId }
            fragment?.let {
                checkForSnapping(it)
                checkWinCondition()
            }
        }
        draggedFragmentId = null
    }

    private fun checkWinCondition() {
        val threshold = 20f // Allow some tolerance for "close enough"
        
        val solved = _puzzle.value.fragments.all { fragment ->
            val solvedFragment = _puzzle.value.solvedFragments.find { it.id == fragment.id }
            if (solvedFragment != null) {
                val distance = (fragment.currentPosition - solvedFragment.currentPosition).getDistance()
                distance < threshold
            } else {
                false
            }
        }

        if (solved) {
            _isGameWon.value = true
        }
    }

    private fun checkForSnapping(draggedFragment: WordFragment) {
        val snapThreshold = 50f
        
        _puzzle.value.connections.forEach { connection ->
            val (fragment1, fragment2) = getFragmentsForConnection(connection)

            if (fragment1 != null && fragment2 != null) {
                if (draggedFragment.id == fragment1.id) {
                    // Check if fragment1 should snap to fragment2
                    val (shouldSnap, snapPosition) = calculateSnapPosition(
                        fragment1, 
                        fragment2, 
                        connection.direction,
                        isFragment1Primary = true
                    )
                    
                    if (shouldSnap) {
                        val distance = (fragment1.currentPosition - snapPosition).getDistance()
                        if (distance < snapThreshold) {
                            updateFragmentPosition(fragment1.id, snapPosition)
                        }
                    }
                } else if (draggedFragment.id == fragment2.id) {
                    // Check if fragment2 should snap to fragment1
                    val (shouldSnap, snapPosition) = calculateSnapPosition(
                        fragment2, 
                        fragment1, 
                        connection.direction,
                        isFragment1Primary = false
                    )
                    
                    if (shouldSnap) {
                        val distance = (fragment2.currentPosition - snapPosition).getDistance()
                        if (distance < snapThreshold) {
                            updateFragmentPosition(fragment2.id, snapPosition)
                        }
                    }
                }
            }
        }
    }

    private fun calculateSnapPosition(
        movingFragment: WordFragment,
        anchorFragment: WordFragment,
        direction: ConnectionDirection,
        isFragment1Primary: Boolean
    ): Pair<Boolean, Offset> {
        val snapThreshold = 50f
        
        return when (direction) {
            ConnectionDirection.HORIZONTAL -> {
                if (isFragment1Primary) {
                    // fragment1 should be to the left of fragment2
                    val fragment1Width = movingFragment.text.length * letterBoxSize
                    val targetX = anchorFragment.currentPosition.x - fragment1Width
                    val targetPosition = Offset(targetX, anchorFragment.currentPosition.y)
                    val distance = (movingFragment.currentPosition - targetPosition).getDistance()
                    Pair(distance < snapThreshold, targetPosition)
                } else {
                    // fragment2 should be to the right of fragment1
                    val fragment1Width = anchorFragment.text.length * letterBoxSize
                    val targetX = anchorFragment.currentPosition.x + fragment1Width
                    val targetPosition = Offset(targetX, anchorFragment.currentPosition.y)
                    val distance = (movingFragment.currentPosition - targetPosition).getDistance()
                    Pair(distance < snapThreshold, targetPosition)
                }
            }
            ConnectionDirection.VERTICAL -> {
                if (isFragment1Primary) {
                    // fragment1 should be above fragment2
                    val targetY = anchorFragment.currentPosition.y - letterBoxSize
                    val targetPosition = Offset(anchorFragment.currentPosition.x, targetY)
                    val distance = (movingFragment.currentPosition - targetPosition).getDistance()
                    Pair(distance < snapThreshold, targetPosition)
                } else {
                    // fragment2 should be below fragment1
                    val targetY = anchorFragment.currentPosition.y + letterBoxSize
                    val targetPosition = Offset(anchorFragment.currentPosition.x, targetY)
                    val distance = (movingFragment.currentPosition - targetPosition).getDistance()
                    Pair(distance < snapThreshold, targetPosition)
                }
            }
        }
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
        // Check fragments in reverse order so top fragments are selected first
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
