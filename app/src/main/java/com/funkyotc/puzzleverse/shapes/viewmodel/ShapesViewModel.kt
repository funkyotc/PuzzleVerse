package com.funkyotc.puzzleverse.shapes.viewmodel

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated
import com.funkyotc.puzzleverse.shapes.data.ShapesRepository
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TangramPieceType
import com.funkyotc.puzzleverse.shapes.model.TangramPieces
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.random.Random

data class SolutionSlot(
    val type: TangramPieceType,
    val position: Offset,
    val rotation: Float,
    val flipped: Boolean
)

class ShapesViewModel(
    context: Context,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private var currentLevel = 0
    private val repository = ShapesRepository(context.applicationContext)

    init {
        val saved = repository.loadPieces(mode, puzzleId)
        if (saved != null && mode != "daily") {
            currentLevel = saved.currentLevel
            generatePuzzle(currentLevel)
            saved.pieces.forEach { ps ->
                _puzzle.value = _puzzle.value?.copy(
                    pieces = _puzzle.value!!.pieces.map { p ->
                        if (p.id == ps.id) p.copy(
                            position = Offset(ps.posX, ps.posY),
                            rotation = ps.rotation,
                            isFlipped = ps.isFlipped,
                            isLocked = ps.isLocked
                        ) else p
                    }
                )
            }
        } else {
            loadNewPuzzle()
        }
    }

    fun loadNewPuzzle() {
        repository.clear(mode, puzzleId)
        generatePuzzle(currentLevel)
        currentLevel++
    }

    private fun generatePuzzle(level: Int) {
        val pregen = if (mode == "puzzle" && puzzleId != null) {
            ShapesPregenerated.getPuzzleById(puzzleId)
        } else if (mode == "daily") {
            val idx = (todayEpochDay() % ShapesPregenerated.ALL_PUZZLES.size).toInt()
            ShapesPregenerated.ALL_PUZZLES[idx]
        } else {
            val idx = abs(level) % ShapesPregenerated.ALL_PUZZLES.size
            ShapesPregenerated.ALL_PUZZLES[idx]
        }

        if (pregen != null) {
            val basePuzzle = pregen.toShapesPuzzle()

            // Tray area bounds in grid units (bottom area below Y=10)
            // Pieces spawn shuffled in 2 rows in bottom tray
            val trayPieces = basePuzzle.pieces.mapIndexed { index, piece ->
                val row = index / 4
                val col = index % 4
                // Tray area X: 1.0..8.5, Y: 11.5..15.5
                val tx = 1.2f + col * 2.0f + Random.nextFloat() * 0.4f
                val ty = 12.0f + row * 2.2f + Random.nextFloat() * 0.4f
                val randomRot = (Random.nextInt(8) * 45f)

                piece.copy(
                    position = Offset(tx, ty),
                    rotation = randomRot,
                    isFlipped = if (piece.type.isFlipSignificant) Random.nextBoolean() else false
                )
            }

            _puzzle.value = basePuzzle.copy(pieces = trayPieces)
            _isGameWon.value = false
        }
    }

    private fun persist() {
        _puzzle.value?.let { p ->
            repository.save(p, currentLevel, mode, puzzleId)
        }
    }

    fun movePiece(pieceId: Int, newPosition: Offset) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) it.copy(position = newPosition) else it
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            persist()
        }
    }

    fun movePieceDelta(pieceId: Int, deltaGrid: Offset) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val piece = currentPuzzle.pieces.find { it.id == pieceId && !it.isLocked } ?: return
            val newPosition = Offset(piece.position.x + deltaGrid.x, piece.position.y + deltaGrid.y)
            movePiece(pieceId, newPosition)
        }
    }

    fun rotatePiece(pieceId: Int, angle: Float) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) {
                    val newRot = (it.rotation + angle) % 360f
                    val normRot = (newRot + 360f) % 360f
                    it.copy(rotation = normRot)
                } else it
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            persist()
            checkCompletion()
        }
    }

    fun flipPiece(pieceId: Int) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) it.copy(isFlipped = !it.isFlipped) else it
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            persist()
            checkCompletion()
        }
    }

    fun snapPiece(pieceId: Int) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val piece = currentPuzzle.pieces.find { it.id == pieceId && !it.isLocked } ?: return

            // 1. Priority check: Solution snap & auto-lock
            val matchingSlot = findMatchingSolutionSlot(piece, currentPuzzle)
            if (matchingSlot != null) {
                val lockedPiece = piece.copy(
                    position = matchingSlot.position,
                    rotation = matchingSlot.rotation,
                    isFlipped = if (piece.type.isFlipSignificant) matchingSlot.flipped else piece.isFlipped,
                    isLocked = true
                )
                val updatedPieces = currentPuzzle.pieces.map {
                    if (it.id == pieceId) lockedPiece else it
                }
                _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
                persist()
                checkCompletion()
                return
            }

            // 2. Vertex-to-vertex snapping (SNAP_DISTANCE in grid units)
            val snapDistance = 0.8f
            var bestDelta = Offset.Zero
            var minDistance = snapDistance

            val snapTargets = buildList {
                addAll(currentPuzzle.target.vertices)
                currentPuzzle.pieces.filter { it.isLocked && it.id != pieceId }
                    .forEach { addAll(it.currentVertices) }
            }

            for (vertex in piece.currentVertices) {
                for (target in snapTargets) {
                    val dist = hypot(vertex.x - target.x, vertex.y - target.y)
                    if (dist < minDistance) {
                        minDistance = dist
                        bestDelta = Offset(target.x - vertex.x, target.y - vertex.y)
                    }
                }
            }

            if (bestDelta != Offset.Zero) {
                val finalPosition = Offset(piece.position.x + bestDelta.x, piece.position.y + bestDelta.y)
                val updatedPieces = currentPuzzle.pieces.map {
                    if (it.id == pieceId) it.copy(position = finalPosition) else it
                }
                _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
                persist()
                checkCompletion()
            }
        }
    }

    private fun findMatchingSolutionSlot(piece: PuzzlePiece, puzzle: ShapesPuzzle): SolutionSlot? {
        val positionTolerance = 0.6f // grid units
        val rotationTolerance = 12f  // degrees

        val compatibleTypes = if (piece.type.interchangeableWith != null) {
            setOf(piece.type, piece.type.interchangeableWith!!)
        } else {
            setOf(piece.type)
        }

        // Slots currently occupied by ALREADY locked pieces
        val occupiedPositions = puzzle.pieces.filter { it.isLocked }.map { it.solutionPosition }

        for (targetPiece in puzzle.pieces) {
            if (targetPiece.type in compatibleTypes && targetPiece.solutionPosition !in occupiedPositions) {
                val slot = SolutionSlot(
                    type = targetPiece.type,
                    position = targetPiece.solutionPosition,
                    rotation = targetPiece.solutionRotation,
                    flipped = targetPiece.solutionFlipped
                )

                if (isPieceAtSlot(piece, slot, positionTolerance, rotationTolerance)) {
                    return slot
                }
            }
        }
        return null
    }

    private fun isPieceAtSlot(
        piece: PuzzlePiece,
        slot: SolutionSlot,
        posTolerance: Float,
        rotTolerance: Float
    ): Boolean {
        // Position check
        val dist = hypot(piece.position.x - slot.position.x, piece.position.y - slot.position.y)
        if (dist > posTolerance) return false

        // Rotation check with symmetry period
        val period = piece.type.symmetryPeriod
        val pieceRot = ((piece.rotation % period) + period) % period
        val slotRot = ((slot.rotation % period) + period) % period
        val rotDiff = abs(pieceRot - slotRot)
        val effectiveRotDiff = minOf(rotDiff, period - rotDiff)
        if (effectiveRotDiff > rotTolerance) return false

        // Flip check for chiral pieces (Parallelogram)
        if (piece.type.isFlipSignificant && piece.isFlipped != slot.flipped) return false

        return true
    }

    fun hint() {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val pieceToHint = currentPuzzle.pieces.find { !it.isLocked }
            if (pieceToHint != null) {
                val updatedPieces = currentPuzzle.pieces.map { p ->
                    if (p.id == pieceToHint.id) {
                        p.copy(
                            position = p.solutionPosition,
                            rotation = p.solutionRotation,
                            isFlipped = p.solutionFlipped,
                            isLocked = true
                        )
                    } else p
                }
                _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
                persist()
                checkCompletion()
            }
        }
    }

    private fun checkCompletion() {
        val puzzle = _puzzle.value ?: return

        // Solution slots from puzzle definition
        val slots = puzzle.pieces.map {
            SolutionSlot(it.type, it.solutionPosition, it.solutionRotation, it.solutionFlipped)
        }

        val allMatched = matchPiecesToSlots(puzzle.pieces, slots)

        if (allMatched) {
            _isGameWon.value = true
            _puzzle.value = puzzle.copy(
                isComplete = true,
                pieces = puzzle.pieces.map { it.copy(isLocked = true) }
            )
            repository.clear(mode, puzzleId)
        }
    }

    private fun matchPiecesToSlots(
        pieces: List<PuzzlePiece>,
        slots: List<SolutionSlot>
    ): Boolean {
        val posTolerance = 0.6f
        val rotTolerance = 12f

        val usedSlots = mutableSetOf<Int>()

        for (piece in pieces) {
            val compatibleTypes = if (piece.type.interchangeableWith != null) {
                setOf(piece.type, piece.type.interchangeableWith!!)
            } else {
                setOf(piece.type)
            }

            var matched = false
            for ((idx, slot) in slots.withIndex()) {
                if (idx in usedSlots) continue
                if (slot.type in compatibleTypes && isPieceAtSlot(piece, slot, posTolerance, rotTolerance)) {
                    usedSlots.add(idx)
                    matched = true
                    break
                }
            }
            if (!matched) return false
        }
        return true
    }
}
