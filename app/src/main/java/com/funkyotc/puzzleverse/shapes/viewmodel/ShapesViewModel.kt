package com.funkyotc.puzzleverse.shapes.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShapesViewModel : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        // Create a simple puzzle: A square target made of two triangles.
        // Target: Square (0,0) to (200,200) at position (300, 300)
        // Pieces: Two triangles that form the square.
        
        // Define pieces in local coordinates around their approximate center
        val triangle1 = listOf(Offset(-50f, -50f), Offset(50f, -50f), Offset(-50f, 50f)) // Top-Left Triangle
        val triangle2 = listOf(Offset(50f, 50f), Offset(-50f, 50f), Offset(50f, -50f)) // Bottom-Right Triangle
        
        // Let's make it simpler for initial verif: Split 100x100 square
        // Triangle 1: (0,0), (100,0), (0,100) -> Center approx (33, 33)
        // Local: (-33, -33), (67, -33), (-33, 67)
        
        val p1Local = listOf(Offset(-33f, -33f), Offset(67f, -33f), Offset(-33f, 67f))
        val p2Local = listOf(Offset(33f, 33f), Offset(-67f, 33f), Offset(33f, -67f))

        val pieces = listOf(
            PuzzlePiece(1, p1Local, Offset(100f, 400f), 0f, color = Color.Red),
            PuzzlePiece(2, p2Local, Offset(250f, 400f), 90f, color = Color.Green) // Rotated initially
        )

        // Target: a 100x100 square at (100, 100)
        val targetVertices = listOf(
             Offset(100f, 100f),
             Offset(200f, 100f),
             Offset(200f, 200f),
             Offset(100f, 200f)
        )
        val target = TargetShape(targetVertices)

        _puzzle.value = ShapesPuzzle(pieces, target)
        _isGameWon.value = false
    }

    fun movePiece(pieceId: Int, newPosition: Offset) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) {
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
            if (currentPuzzle.isComplete) return

           val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) {
                    // Snap to 45 degree increments? Or 90? Plan said 90.
                    val newRotation = (it.rotation + angle) % 360f
                    it.copy(rotation = newRotation)
                } else {
                    it
                }
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkCompletion()
        }
    }
    
    fun flipPiece(pieceId: Int) {
          _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

           val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) {
                    it.copy(isFlipped = !it.isFlipped)
                } else {
                    it
                }
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            checkCompletion()
        }
    }

    private fun checkCompletion() {
        val currentPuzzle = _puzzle.value ?: return
        
        // 1. Check if ANY piece overlaps with ANY OTHER piece (strictTangram rules)
        // Usually tangram rules are: pieces must not overlap.
        for (i in currentPuzzle.pieces.indices) {
             for (j in i + 1 until currentPuzzle.pieces.size) {
                 if (GeometryUtils.doPolygonsIntersect(
                         currentPuzzle.pieces[i].currentVertices, 
                         currentPuzzle.pieces[j].currentVertices
                     )) {
                     // Overlap detected, invalid state (not won)
                     return
                 }
             }
        }

        // 2. Check if ALL pieces are INSIDE the target
        val allInside = currentPuzzle.pieces.all { piece ->
            GeometryUtils.isPolygonInside(piece.currentVertices, currentPuzzle.target.vertices)
        }

        if (allInside) {
             // 3. (Optional) Check total area covered matches target area? 
             // If they don't overlap and are all inside, and the sum of their areas == target area, then it's filled.
             // Or simpler: Just check if they non-overlap and inside. If the puzzle set is constructed to perfectly fill, this triggers win.
             
             _isGameWon.value = true
             _puzzle.value = currentPuzzle.copy(isComplete = true)
        }
    }
}