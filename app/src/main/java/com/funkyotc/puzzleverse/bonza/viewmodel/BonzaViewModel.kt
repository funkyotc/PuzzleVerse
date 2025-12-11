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
    private val streakRepository: StreakRepository,
    private val puzzleGenerator: BonzaPuzzleGenerator
) : ViewModel() {

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val _puzzle = MutableStateFlow(puzzleGenerator.generate())
    val puzzle: StateFlow<BonzaPuzzle> = _puzzle

    private var draggedFragmentGroupId: Int? = null
    // Logical size of a letter box in grid units is always 1.0
    private val letterBoxSize = 1.0f

    init {
        // Initialize Bonza game state here
    }

    fun onDragStart(position: Offset) {
        val fragment = getFragmentAt(position)
        draggedFragmentGroupId = fragment?.groupId
    }

    fun onDrag(dragAmount: Offset) {
        draggedFragmentGroupId?.let { groupId ->
            val updatedFragments = _puzzle.value.fragments.map { fragment ->
                if (fragment.groupId == groupId) {
                    fragment.copy(currentPosition = fragment.currentPosition + dragAmount)
                } else {
                    fragment
                }
            }
            _puzzle.value = _puzzle.value.copy(fragments = updatedFragments)
        }
    }

    fun onDragEnd() {
        draggedFragmentGroupId?.let { groupId ->
            // Check for snapping for *any* fragment in the dragged group against *any* fragment NOT in the group
            var groupSnapped = false
            val currentPuzzle = _puzzle.value
            val draggedGroupFragments = currentPuzzle.fragments.filter { it.groupId == groupId }
            val otherFragments = currentPuzzle.fragments.filter { it.groupId != groupId }

            // Find valid connection between a dragged fragment and an other fragment
            for (draggedFrag in draggedGroupFragments) {
                if (groupSnapped) break

                for (otherFrag in otherFragments) {
                     // Check if these two fragments have a defined connection
                     val connection = currentPuzzle.connections.find { 
                         (it.fragment1Id == draggedFrag.id && it.fragment2Id == otherFrag.id) ||
                         (it.fragment1Id == otherFrag.id && it.fragment2Id == draggedFrag.id)
                     }
                     
                     if (connection != null) {
                         // Check snap
                         val (shouldSnap, moveDelta) = calculateSnapDelta(draggedFrag, otherFrag, connection)
                         if (shouldSnap) {
                             // Snap the ENTIRE dragged group by the calculated delta
                             snapGroup(groupId, moveDelta, otherFrag.groupId)
                             groupSnapped = true
                             break
                         }
                     }
                }
            }
            
            if (!groupSnapped) {
               // Optional: Visual bounce back or just leave it
            }
            
            checkWinCondition()
        }
        draggedFragmentGroupId = null
    }
    
    // Returns Pair(ShouldSnap, DeltaToMoveDraggedFragment)
    private fun calculateSnapDelta(
        movingFrag: WordFragment,
        anchorFrag: WordFragment,
        connection: BonzaConnection
    ): Pair<Boolean, Offset> {
        val snapThreshold = 0.4f // Snap if within 0.4 grid units
        
        // Target position for movingFrag based on anchorFrag's current position and solved relative position
        val movingSolved = movingFrag.solvedPosition ?: return Pair(false, Offset.Zero)
        val anchorSolved = anchorFrag.solvedPosition ?: return Pair(false, Offset.Zero)
        
        // Relative vector from anchor to moving in solved state
        val relativeVector = movingSolved - anchorSolved
        
        // Target position in current state
        val targetPosition = anchorFrag.currentPosition + relativeVector
        
        val distance = (movingFrag.currentPosition - targetPosition).getDistance()
        
        return if (distance < snapThreshold) {
            Pair(true, targetPosition - movingFrag.currentPosition)
        } else {
            Pair(false, Offset.Zero)
        }
    }

    private fun snapGroup(movingGroupId: Int, moveDelta: Offset, targetGroupId: Int) {
        val updatedFragments = _puzzle.value.fragments.map { fragment ->
            if (fragment.groupId == movingGroupId) {
                // Move and update Group ID to merge
                fragment.copy(
                    currentPosition = fragment.currentPosition + moveDelta,
                    groupId = targetGroupId
                )
            } else {
                fragment
            }
        }
        _puzzle.value = _puzzle.value.copy(fragments = updatedFragments)
    }

    private fun checkWinCondition() {
        val fragments = _puzzle.value.fragments
        if (fragments.isEmpty()) return

        // 1. Check if all fragments belong to the same group
        val firstGroupId = fragments.first().groupId
        if (fragments.any { it.groupId != firstGroupId }) return

        // 2. Check relative positions
        // Pick the first fragment as the anchor
        val anchor = fragments.first()
        val threshold = 0.1f // Tight tolerance for win check (0.1 grid units)

        val isSolved = fragments.all { fragment ->
            if (fragment.id == anchor.id) true
            else {
                val expectedRelative = (fragment.solvedPosition ?: Offset.Zero) - (anchor.solvedPosition ?: Offset.Zero)
                val actualRelative = fragment.currentPosition - anchor.currentPosition
                (expectedRelative - actualRelative).getDistance() < threshold
            }
        }

        if (isSolved) {
            _isGameWon.value = true
        }
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
        _puzzle.value = puzzleGenerator.generate()
    }
}
