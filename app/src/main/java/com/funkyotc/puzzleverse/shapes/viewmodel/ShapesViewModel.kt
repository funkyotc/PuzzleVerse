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
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private var currentLevel = 0

    init {
        loadNewPuzzle()
    }

    fun loadNewPuzzle() {

        if (mode == "puzzle" && puzzleId != null) {
            val cx = 200f
            val cy = 200f
            val scale = 1.5f
            val pregen = com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.getPuzzleById(puzzleId)
            if (pregen != null) {
                val originalLevel = pregen.toShapesPuzzle()
                val colors = listOf(
                    Color(0xFF6B8DD6), // Light Blue
                    Color(0xFF8E37D7), // Purple
                    Color(0xFFFFB75E), // Light Orange
                    Color(0xFFED8F03), // Dark Orange
                    Color(0xFFFF5252), // Coral/Red
                    Color(0xFF4CAF50), // Green
                    Color(0xFF00BCD4)  // Cyan
                )
                
                val scaledPieces = originalLevel.pieces.map { piece ->
                    piece.copy(
                        initialVertices = piece.initialVertices.map { Offset(it.x * scale * 100f, it.y * scale * 100f) },
                        position = Offset(
                            cx + piece.solutionPosition.x * scale * 100f,
                            cy + piece.solutionPosition.y * scale * 100f
                        ),
                        solutionPosition = Offset(
                            cx + piece.solutionPosition.x * scale * 100f,
                            cy + piece.solutionPosition.y * scale * 100f
                        )
                    )
                }
                
                // Shuffle tangram pieces into a neat 2-row grid at the bottom of the screen
                val shuffledPieces = scaledPieces.mapIndexed { index, piece ->
                    val row = index / 4
                    val col = index % 4
                    val startX = if (row == 0) 50f else 80f
                    val x = startX + col * 80f + Random.nextFloat() * 15f
                    val y = 460f + row * 85f + Random.nextFloat() * 15f
                    piece.copy(
                        position = Offset(x, y),
                        color = colors.getOrElse(index) { Color.Gray }
                    )
                }
                
                val scaledTarget = TargetShape(originalLevel.target.vertices.map { 
                    Offset(cx + (it.x) * scale * 100f, cy + (it.y) * scale * 100f)
                })
                
                _puzzle.value = originalLevel.copy(pieces = shuffledPieces, target = scaledTarget)
                _isGameWon.value = false
                return
            }
        }

        val seed = if (mode == "daily") {
            LocalDate.now(java.time.ZoneOffset.UTC).toEpochDay()
        } else {
            Random.nextLong()
        }
        val random = Random(seed)

        val originalLevel = com.funkyotc.puzzleverse.shapes.util.ShapesLevels.generateLevel(currentLevel, random)
        
        val scale = 1.5f
        val cx = 200f
        val cy = 200f
        
        val scaledPieces = originalLevel.pieces.map { piece ->
            piece.copy(
                initialVertices = piece.initialVertices.map { Offset(it.x * scale, it.y * scale) },
                position = Offset(
                    150f + random.nextFloat() * 100f, 
                    450f + random.nextFloat() * 150f
                ),
                solutionPosition = Offset(
                    cx + (piece.solutionPosition.x - cx) * scale, 
                    cy + (piece.solutionPosition.y - cy) * scale
                )
            )
        }
        val scaledTarget = TargetShape(originalLevel.target.vertices.map { 
            Offset(cx + (it.x - cx) * scale, cy + (it.y - cy) * scale) 
        })

        _puzzle.value = originalLevel.copy(pieces = scaledPieces, target = scaledTarget)
        _isGameWon.value = false
        currentLevel++
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
        }
    }

    fun movePieceDelta(pieceId: Int, delta: Offset) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

            val tentativePiece = currentPuzzle.pieces.find { it.id == pieceId && !it.isLocked } ?: return
            val newPosition = Offset(tentativePiece.position.x + delta.x, tentativePiece.position.y + delta.y)
            movePiece(pieceId, newPosition)
        }
    }

    fun snapPiece(pieceId: Int) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return

            val tentativePiece = currentPuzzle.pieces.find { it.id == pieceId && !it.isLocked } ?: return

            // Vertex snapping logic
            var bestSnapDelta = Offset.Zero
            var minDistance = 30f // Threshold for snapping

            val targetVertices = currentPuzzle.target.vertices
            val otherPiecesVertices = currentPuzzle.pieces.filter { it.id != pieceId }.flatMap { it.currentVertices }
            val allSnapPoints = targetVertices + otherPiecesVertices

            for (vertex in tentativePiece.currentVertices) {
                for (snapPoint in allSnapPoints) {
                    val dist = kotlin.math.hypot(vertex.x - snapPoint.x, vertex.y - snapPoint.y)
                    if (dist < minDistance) {
                        minDistance = dist
                        bestSnapDelta = Offset(snapPoint.x - vertex.x, snapPoint.y - vertex.y)
                    }
                }
            }

            val finalPosition = Offset(tentativePiece.position.x + bestSnapDelta.x, tentativePiece.position.y + bestSnapDelta.y)

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

    fun hint() {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            
            // Find a piece that is not yet locked and not exactly at its solution position
            val pieceToHint = currentPuzzle.pieces.find { !it.isLocked && (it.position != it.solutionPosition || it.rotation != it.solutionRotation) }
            
            if (pieceToHint != null) {
                val updatedPieces = currentPuzzle.pieces.map {
                    if (it.id == pieceToHint.id) {
                        it.copy(position = it.solutionPosition, rotation = it.solutionRotation, isLocked = true)
                    } else {
                        it
                    }
                }
                _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
                checkCompletion()
            }
        }
    }

    private fun checkCompletion() {
        val currentPuzzle = _puzzle.value ?: return
        
        // Check 1: Solution position proximity check
        // Highly robust and reliable check based on solution design coordinates
        val allAtSolution = currentPuzzle.pieces.all { piece ->
            val dist = kotlin.math.hypot(piece.position.x - piece.solutionPosition.x, piece.position.y - piece.solutionPosition.y)
            val normRot = (piece.rotation % 360f + 360f) % 360f
            val normSolRot = (piece.solutionRotation % 360f + 360f) % 360f
            val rotDiff = kotlin.math.abs(normRot - normSolRot) % 360f
            val isRotCorrect = rotDiff < 10f || rotDiff > 350f
            
            dist < 10f && isRotCorrect
        }
        
        if (allAtSolution) {
            _isGameWon.value = true
            _puzzle.value = currentPuzzle.copy(isComplete = true)
            return
        }

        // Check 2: Strict polygon inclusion check (for manual placement / edge cases)
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

class ShapesViewModelFactory(private val mode: String?, private val puzzleId: String? = null) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShapesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShapesViewModel(mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}