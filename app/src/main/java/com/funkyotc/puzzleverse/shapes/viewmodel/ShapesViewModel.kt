package com.funkyotc.puzzleverse.shapes.viewmodel

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.shapes.generator.ShapesPuzzleGenerator
import com.funkyotc.puzzleverse.shapes.model.PieceDefinition
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.math.abs
import kotlin.random.Random

class ShapesViewModel(
    private val mode: String?,
    private val context: Context,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private val generator = ShapesPuzzleGenerator()
    val pieceDefinitions: Map<Int, PieceDefinition> = ShapesPuzzleGenerator.TANS.associateBy { it.id }

    // Snap threshold in pixels
    private val snapThreshold = 40f

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        val seed = if (mode == "daily") {
            LocalDate.now().toEpochDay()
        } else {
            Random.nextLong()
        }

        _puzzle.value = generator.generate(seed)
        _isGameWon.value = false
    }

    /** Get current world-space vertices for a piece */
    fun getPieceVertices(piece: PuzzlePiece): List<Offset> {
        val def = pieceDefinitions[piece.definitionId] ?: return emptyList()
        return GeometryUtils.transformPolygon(
            def.localVertices,
            piece.position,
            piece.rotation,
            isFlipped = piece.isFlipped
        )
    }

    fun movePiece(pieceId: Int, newPosition: Offset) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

            val updatedPieces = currentPuzzle.pieces.map { piece ->
                if (piece.definitionId == pieceId && !piece.isLocked) {
                    piece.copy(position = newPosition)
                } else piece
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkAndSnap()
        }
    }

    fun rotatePiece(pieceId: Int, angle: Float) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

            val updatedPieces = currentPuzzle.pieces.map { piece ->
                if (piece.definitionId == pieceId && !piece.isLocked) {
                    piece.copy(rotation = (piece.rotation + angle) % 360f)
                } else piece
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkAndSnap()
        }
    }

    fun flipPiece(pieceId: Int) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

            val updatedPieces = currentPuzzle.pieces.map { piece ->
                if (piece.definitionId == pieceId && !piece.isLocked) {
                    piece.copy(isFlipped = !piece.isFlipped)
                } else piece
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkAndSnap()
        }
    }

    private fun checkAndSnap() {
        val currentPuzzle = _puzzle.value ?: return

        val updatedPieces = currentPuzzle.pieces.map { piece ->
            if (piece.isLocked) return@map piece

            val cp = piece.correctPosition ?: return@map piece
            val cr = piece.correctRotation ?: return@map piece
            val cf = piece.correctFlip

            val dist = abs(piece.position.x - cp.x) + abs(piece.position.y - cp.y)
            val rotMatch = (piece.rotation - cr).let { d ->
                ((d % 360f + 360f) % 360f)
            }.let { normalized ->
                normalized < snapThreshold || normalized > 360f - snapThreshold
            }
            val flipMatch = piece.isFlipped == cf

            if (dist < snapThreshold && rotMatch && flipMatch) {
                piece.copy(isLocked = true)
            } else {
                piece
            }
        }

        _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
        checkCompletion(updatedPieces)
    }

    private fun checkCompletion(pieces: List<PuzzlePiece>) {
        // All pieces must be locked
        if (!pieces.all { it.isLocked }) return

        // Check no overlaps
        for (i in pieces.indices) {
            for (j in i + 1 until pieces.size) {
                if (GeometryUtils.doPolygonsIntersect(
                        getPieceVertices(pieces[i]),
                        getPieceVertices(pieces[j])
                    )) {
                    return
                }
            }
        }

        // Check all inside target
        val target = _puzzle.value?.target ?: return
        val allInside = pieces.all { piece ->
            GeometryUtils.isPolygonInside(getPieceVertices(piece), target.vertices)
        }

        if (allInside) {
            _isGameWon.value = true
            _puzzle.value = _puzzle.value?.copy(isComplete = true)

            if (mode == "daily") {
                val today = LocalDate.now().toEpochDay()
                val streak = streakRepository.getStreak("shapes")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    streakRepository.saveStreak(newStreak)
                }
            }
        }
    }
}

class ShapesViewModelFactory(
    private val mode: String?,
    private val context: Context,
    private val streakRepository: StreakRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShapesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShapesViewModel(mode, context, streakRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
