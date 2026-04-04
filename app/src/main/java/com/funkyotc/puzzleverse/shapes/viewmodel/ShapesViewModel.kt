package com.funkyotc.puzzleverse.shapes.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.random.Random

class ShapesViewModel(
    private val mode: String?
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        val seed = if (mode == "daily") {
            LocalDate.now().toEpochDay()
        } else {
            Random.nextLong()
        }
        val random = Random(seed)

        // Create the puzzle
        // To provide variety with just one hardcoded puzzle, we will:
        // 1. Randomize the initial positions of pieces more effectively
        // 2. Randomize the initial rotation of pieces
        // 3. (Optional) Flip the whole puzzle geometry randomly?
        
        // Define Local Shapes
        val p1Local = listOf(Offset(-33f, -33f), Offset(67f, -33f), Offset(-33f, 67f))
        val p2Local = listOf(Offset(33f, 33f), Offset(-67f, 33f), Offset(33f, -67f))

        val pieces = listOf(
            PuzzlePiece(
                id = 1, 
                initialVertices = p1Local, 
                position = Offset(
                    random.nextInt(61) * 10f + 50f, 
                    random.nextInt(41) * 10f + 400f
                ), 
                rotation = random.nextInt(4) * 90f, 
                color = Color.Red
            ),
            PuzzlePiece(
                id = 2, 
                initialVertices = p2Local, 
                position = Offset(
                    random.nextInt(61) * 10f + 50f, 
                    random.nextInt(41) * 10f + 400f
                ), 
                rotation = random.nextInt(4) * 90f,
                color = Color.Green
            ) 
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

            val tentativePiece = currentPuzzle.pieces.find { it.id == pieceId && !it.isLocked } ?: return
            val pieceWithNewPos = tentativePiece.copy(position = newPosition)

            // Vertex snapping logic
            var bestSnapDelta = Offset.Zero
            var minDistance = 30f // Threshold for snapping

            val targetVertices = currentPuzzle.target.vertices
            val otherPiecesVertices = currentPuzzle.pieces.filter { it.id != pieceId }.flatMap { it.currentVertices }
            val allSnapPoints = targetVertices + otherPiecesVertices

            for (vertex in pieceWithNewPos.currentVertices) {
                for (snapPoint in allSnapPoints) {
                    val dist = kotlin.math.hypot(vertex.x - snapPoint.x, vertex.y - snapPoint.y)
                    if (dist < minDistance) {
                        minDistance = dist
                        bestSnapDelta = Offset(snapPoint.x - vertex.x, snapPoint.y - vertex.y)
                    }
                }
            }

            val finalPosition = Offset(newPosition.x + bestSnapDelta.x, newPosition.y + bestSnapDelta.y)

            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) {
                    it.copy(position = finalPosition)
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
             _isGameWon.value = true
             _puzzle.value = currentPuzzle.copy(isComplete = true)
        }
    }
}

class ShapesViewModelFactory(private val mode: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShapesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShapesViewModel(mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}