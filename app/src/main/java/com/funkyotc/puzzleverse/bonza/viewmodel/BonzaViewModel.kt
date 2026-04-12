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
import kotlinx.coroutines.flow.asStateFlow
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

    private val _puzzle = MutableStateFlow(generatePuzzle())
    val puzzle: StateFlow<BonzaPuzzle> = _puzzle

    private val _draggedGroupId = MutableStateFlow<Int?>(null)
    val draggedGroupId = _draggedGroupId.asStateFlow()

    private var draggedFragmentGroupId: Int? = null
    // Logical size of a letter box in grid units is always 1.0
    private val letterBoxSize = 1.0f

    init {
        // Initialize Bonza game state here
    }

    private fun generatePuzzle(): BonzaPuzzle {
        val seed = if (mode == "daily") {
            java.time.LocalDate.now().toEpochDay()
        } else {
            kotlin.random.Random.nextLong()
        }
        return puzzleGenerator.generate(seed)
    }

    fun hint() {
        if (_isGameWon.value) return
        val currentPuzzle = _puzzle.value
        val fragments = currentPuzzle.fragments
        if (fragments.isEmpty()) return

        // Find an anchor group and a moving group that are disconnected
        val anchorGroupId = fragments.first().groupId
        
        // Find a fragment that is NOT in the anchor group
        val movingFragment = fragments.find { it.groupId != anchorGroupId } ?: return
        
        // Calculate the vector needed to move movingFragment to its solved relative position against an anchor fragment.
        val anchorFragment = fragments.first { it.groupId == anchorGroupId }
        
        val movingSolved = movingFragment.solvedPosition ?: return
        val anchorSolved = anchorFragment.solvedPosition ?: return
        
        val relativeVector = movingSolved - anchorSolved
        val targetPosition = anchorFragment.currentPosition + relativeVector
        val moveDelta = targetPosition - movingFragment.currentPosition
        
        snapGroup(movingFragment.groupId, moveDelta, anchorGroupId)
        checkWinCondition()
    }

    fun onDragStart(position: Offset) {
        val fragment = getFragmentAt(position)
        draggedFragmentGroupId = fragment?.groupId
        _draggedGroupId.value = fragment?.groupId
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
        _draggedGroupId.value = null
    }
    
    // Returns Pair(ShouldSnap, DeltaToMoveDraggedFragment)
    private fun calculateSnapDelta(
        movingFrag: WordFragment,
        anchorFrag: WordFragment,
        connection: BonzaConnection
    ): Pair<Boolean, Offset> {
        val snapThreshold = 0.85f // Snap if within 0.85 grid units
        
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
            if (!_isGameWon.value && mode == "daily") {
                val today = java.time.LocalDate.now().toEpochDay()
                val streak = streakRepository.getStreak("bonza")
                if (streak.lastCompletedEpochDay != today) {
                    val newCount = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1
                    streakRepository.saveStreak(streak.copy(count = newCount, lastCompletedEpochDay = today))
                }
            }
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

    fun getPuzzleBounds(): Rect {
        val fragments = _puzzle.value.fragments
        if (fragments.isEmpty()) return Rect.Zero

        var minX = Float.MAX_VALUE
        var minY = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var maxY = Float.MIN_VALUE

        fragments.forEach { fragment ->
            val x = fragment.currentPosition.x
            val y = fragment.currentPosition.y
            val width = if (fragment.direction == ConnectionDirection.HORIZONTAL) fragment.text.length.toFloat() else 1f
            val height = if (fragment.direction == ConnectionDirection.VERTICAL) fragment.text.length.toFloat() else 1f

            if (x < minX) minX = x
            if (y < minY) minY = y
            if (x + width > maxX) maxX = x + width
            if (y + height > maxY) maxY = y + height
        }
        
        // Add a small padding in grid units (e.g., 1 unit)
        return Rect(minX - 1, minY - 1, maxX + 1, maxY + 1)
    }

    fun newGame() {
        if (mode == "daily") return // usually new game is disabled manually, but as fallback
        _isGameWon.value = false
        _puzzle.value = generatePuzzle()
    }
}
