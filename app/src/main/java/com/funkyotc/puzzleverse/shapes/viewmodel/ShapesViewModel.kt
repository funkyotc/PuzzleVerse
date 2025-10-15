package com.funkyotc.puzzleverse.shapes.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShapesViewModel : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle

    init {
        loadNewPuzzle()
    }

    private fun loadNewPuzzle() {
        // For demonstration, we'll create a simple square puzzle
        val pieces = listOf(
            PuzzlePiece(1, listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(100f, 100f), Offset(0f, 100f)), Offset(50f, 50f), 0f),
            PuzzlePiece(2, listOf(Offset(0f, 0f), Offset(100f, 0f), Offset(0f, 100f)), Offset(200f, 50f), 0f)
        )
        val target = TargetShape(listOf(Offset(0f, 0f), Offset(200f, 0f), Offset(200f, 200f), Offset(0f, 200f)))
        _puzzle.value = ShapesPuzzle(pieces, target)
    }

    fun movePiece(pieceId: Int, newPosition: Offset) {
        _puzzle.value?.let { currentPuzzle ->
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId) {
                    it.copy(position = newPosition)
                } else {
                    it
                }
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkCompletion()
        }
    }

    fun rotatePiece(pieceId: Int, angle: Float) {
        _puzzle.value?.let { currentPuzzle ->
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId) {
                    it.copy(rotation = it.rotation + angle)
                } else {
                    it
                }
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkCompletion()
        }
    }

    private fun checkCompletion() {
        // TODO: Implement actual completion logic. For now, we'll just log.
        println("Checking for puzzle completion...")
    }
}