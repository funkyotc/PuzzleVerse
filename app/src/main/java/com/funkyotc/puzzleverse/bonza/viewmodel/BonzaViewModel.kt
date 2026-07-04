package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.bonza.data.BonzaConnection
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.data.WordFragment
import com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

class BonzaViewModel(
    context: Context,
    private val mode: String?,
    private val puzzleId: String?,
    private val forceNewGame: Boolean,
    private val streakRepository: StreakRepository,
    private val puzzleGenerator: BonzaPuzzleGenerator
) : ViewModel() {

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val _puzzle = MutableStateFlow(BonzaPuzzle("", emptyList(), emptyList(), emptyList(), emptyList()))
    val puzzle: StateFlow<BonzaPuzzle> = _puzzle

    private val _draggedGroupId = MutableStateFlow<Int?>(null)
    val draggedGroupId = _draggedGroupId.asStateFlow()

    private var draggedFragmentGroupId: Int? = null
    private var dragOffset = Offset.Zero
    // Logical size of a letter box in grid units is always 1.0
    private val letterBoxSize = 1.0f

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _puzzle.value = generatePuzzle()
        }
    }

    private fun generatePuzzle(): BonzaPuzzle {
        val basePuzzle = if (mode == "puzzle" && puzzleId != null) {
            val pregenerated = com.funkyotc.puzzleverse.bonza.data.BonzaPregenerated.getPuzzleById(puzzleId)
            if (pregenerated != null) {
                pregenerated.toBonzaPuzzle()
            } else {
                val seed = kotlin.random.Random.nextLong()
                puzzleGenerator.generate(seed)
            }
        } else {
            val seed = if (mode == "daily") {
                com.funkyotc.puzzleverse.core.todayEpochDay()
            } else {
                kotlin.random.Random.nextLong()
            }
            puzzleGenerator.generate(seed)
        }
        return initializePuzzle(basePuzzle)
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
        
        // Store the offset from the touch point to the fragment's current position
        fragment?.let {
            dragOffset = it.currentPosition - position
        }
    }

    fun onDrag(touchPosition: Offset) {
        draggedFragmentGroupId?.let { groupId ->
            val targetPosition = touchPosition + dragOffset
            val currentGroupPos = _puzzle.value.fragments.find { it.groupId == groupId }?.currentPosition ?: return@let
            val moveDelta = targetPosition - currentGroupPos
            
            val updatedFragments = _puzzle.value.fragments.map { fragment ->
                if (fragment.groupId == groupId) {
                    fragment.copy(currentPosition = fragment.currentPosition + moveDelta)
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
                // Snap the entire group to the nearest integer grid coordinates!
                val leadFragment = draggedGroupFragments.firstOrNull()
                if (leadFragment != null) {
                    val currentPos = leadFragment.currentPosition
                    val snappedX = Math.round(currentPos.x).toFloat()
                    val snappedY = Math.round(currentPos.y).toFloat()
                    val snapDelta = Offset(snappedX - currentPos.x, snappedY - currentPos.y)
                    
                    val updatedFragments = _puzzle.value.fragments.map { fragment ->
                        if (fragment.groupId == groupId) {
                            fragment.copy(currentPosition = fragment.currentPosition + snapDelta)
                        } else {
                            fragment
                        }
                    }
                    _puzzle.value = _puzzle.value.copy(fragments = updatedFragments)
                }
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
                val today = com.funkyotc.puzzleverse.core.todayEpochDay()
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
        viewModelScope.launch(Dispatchers.Default) {
            _puzzle.value = generatePuzzle()
        }
    }

    private fun initializePuzzle(puzzle: BonzaPuzzle): BonzaPuzzle {
        val random = kotlin.random.Random(puzzle.theme.hashCode().toLong())
        
        // 1. Calculate connections if empty
        val connections = if (puzzle.connections.isEmpty()) {
            calculateConnections(puzzle.fragments)
        } else {
            puzzle.connections
        }
        
        // 2. Perform non-overlapping layout for fragments snapped to grid!
        val laidOutFragments = layoutFragmentsWithoutOverlaps(puzzle.fragments, random)
        
        return puzzle.copy(
            fragments = laidOutFragments,
            connections = connections
        )
    }

    private fun layoutFragmentsWithoutOverlaps(
        fragments: List<WordFragment>,
        random: kotlin.random.Random
    ): List<WordFragment> {
        val laidOut = mutableListOf<WordFragment>()
        val sorted = fragments.sortedByDescending { it.text.length }
        val placedBoxes = mutableListOf<Rect>()
        
        for (fragment in sorted) {
            val w = if (fragment.direction == ConnectionDirection.HORIZONTAL) fragment.text.length else 1
            val h = if (fragment.direction == ConnectionDirection.VERTICAL) fragment.text.length else 1
            
            var found = false
            var attempts = 0
            var targetX = 0f
            var targetY = 0f
            
            while (!found && attempts < 1000) {
                val rx = random.nextInt(0, 12).toFloat()
                val ry = random.nextInt(0, 16).toFloat()
                
                val newRect = Rect(rx, ry, rx + w, ry + h)
                val overlaps = placedBoxes.any { placed ->
                    !(newRect.right <= placed.left || placed.right <= newRect.left ||
                      newRect.bottom <= placed.top || placed.bottom <= newRect.top)
                }
                
                if (!overlaps) {
                    targetX = rx
                    targetY = ry
                    placedBoxes.add(newRect)
                    found = true
                }
                attempts++
            }
            
            if (!found) {
                for (ry in 0..50) {
                    for (rx in 0..50) {
                        val fx = rx.toFloat()
                        val fy = ry.toFloat()
                        val newRect = Rect(fx, fy, fx + w, fy + h)
                        val overlaps = placedBoxes.any { placed ->
                            !(newRect.right <= placed.left || placed.right <= newRect.left ||
                              newRect.bottom <= placed.top || placed.bottom <= newRect.top)
                        }
                        if (!overlaps) {
                            targetX = fx
                            targetY = fy
                            placedBoxes.add(newRect)
                            found = true
                            break
                        }
                    }
                    if (found) break
                }
            }
            
            laidOut.add(fragment.copy(
                initialPosition = Offset(targetX, targetY),
                currentPosition = Offset(targetX, targetY),
                groupId = fragment.id
            ))
        }
        
        return laidOut
    }

    private fun calculateConnections(fragments: List<WordFragment>): List<BonzaConnection> {
        val connections = mutableListOf<BonzaConnection>()
        val gridOwners = mutableMapOf<Pair<Int, Int>, Int>()
        
        for (frag in fragments) {
            val solvedX = frag.solvedPosition?.x?.toInt() ?: 0
            val solvedY = frag.solvedPosition?.y?.toInt() ?: 0
            for (i in frag.text.indices) {
                val cx = solvedX + if (frag.direction == ConnectionDirection.HORIZONTAL) i else 0
                val cy = solvedY + if (frag.direction == ConnectionDirection.VERTICAL) i else 0
                gridOwners[Pair(cx, cy)] = frag.id
            }
        }
        
        for (frag in fragments) {
            val solvedX = frag.solvedPosition?.x?.toInt() ?: 0
            val solvedY = frag.solvedPosition?.y?.toInt() ?: 0
            for (i in frag.text.indices) {
                val cx = solvedX + if (frag.direction == ConnectionDirection.HORIZONTAL) i else 0
                val cy = solvedY + if (frag.direction == ConnectionDirection.VERTICAL) i else 0
                
                val neighbors = listOf(
                    Pair(cx + 1, cy) to ConnectionDirection.HORIZONTAL,
                    Pair(cx - 1, cy) to ConnectionDirection.HORIZONTAL,
                    Pair(cx, cy + 1) to ConnectionDirection.VERTICAL,
                    Pair(cx, cy - 1) to ConnectionDirection.VERTICAL
                )
                
                for ((nPos, dir) in neighbors) {
                    val neighborId = gridOwners[nPos]
                    if (neighborId != null && neighborId != frag.id) {
                        if (frag.id < neighborId) {
                            if (connections.none { it.fragment1Id == frag.id && it.fragment2Id == neighborId }) {
                                connections.add(BonzaConnection(frag.id, neighborId, dir))
                            }
                        }
                    }
                }
            }
        }
        return connections
    }
}
