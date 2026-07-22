package com.funkyotc.puzzleverse.shapes.viewmodel

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.shapes.data.ShapesRepository
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.funkyotc.puzzleverse.shapes.model.TargetShape
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.funkyotc.puzzleverse.core.todayEpochDay
import kotlin.random.Random

class ShapesViewModel(
    private val context: Context,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModel() {

    private val _puzzle = MutableStateFlow<ShapesPuzzle?>(null)
    val puzzle: StateFlow<ShapesPuzzle?> = _puzzle.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    private var currentLevel = 0
    private val repository = ShapesRepository(context)

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
        val cx = 200f
        val cy = 200f
        val sc = 2.0f

        val pregen = if (mode == "puzzle" && puzzleId != null) {
            com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.getPuzzleById(puzzleId)
        } else if (mode == "daily") {
            val idx = (todayEpochDay() % com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.ALL_PUZZLES.size).toInt()
            com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.ALL_PUZZLES[idx]
        } else {
            val idx = kotlin.math.abs(level) % com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.ALL_PUZZLES.size
            com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.ALL_PUZZLES[idx]
        }

        if (pregen != null) {
            val originalLevel = pregen.toShapesPuzzle()

            val scaledPieces = originalLevel.pieces.map { piece ->
                piece.copy(
                    initialVertices = piece.initialVertices.map { Offset(it.x * sc, it.y * sc) },
                    position = Offset(
                        cx + piece.solutionPosition.x * sc,
                        cy + piece.solutionPosition.y * sc
                    ),
                    solutionPosition = Offset(
                        cx + piece.solutionPosition.x * sc,
                        cy + piece.solutionPosition.y * sc
                    )
                )
            }

            // Layout unplaced pieces cleanly in 2 rows in the bottom tray area (Y = 475..640)
            val shuffledPieces = scaledPieces.mapIndexed { index, piece ->
                val row = index / 4
                val col = index % 4
                val x = 65f + col * 85f + Random.nextFloat() * 10f
                val y = 475f + row * 90f + Random.nextFloat() * 10f
                val randomRot = (Random.nextInt(8) * 45f)
                piece.copy(
                    position = Offset(x, y),
                    rotation = randomRot
                )
            }

            val scaledTarget = TargetShape(originalLevel.target.vertices.map {
                Offset(cx + it.x * sc, cy + it.y * sc)
            })

            _puzzle.value = originalLevel.copy(pieces = shuffledPieces, target = scaledTarget)
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

            var bestSnapDelta = Offset.Zero
            var minDistance = 25f

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

            // Direct snap to solution position if close and correctly rotated
            val distToSolution = kotlin.math.hypot(tentativePiece.position.x - tentativePiece.solutionPosition.x, tentativePiece.position.y - tentativePiece.solutionPosition.y)
            val normRot = (tentativePiece.rotation % 360f + 360f) % 360f
            val normSolRot = (tentativePiece.solutionRotation % 360f + 360f) % 360f
            val rotDiff = kotlin.math.abs(normRot - normSolRot) % 360f
            if (distToSolution < 25f && (rotDiff < 15f || rotDiff > 345f)) {
                bestSnapDelta = Offset(tentativePiece.solutionPosition.x - tentativePiece.position.x, tentativePiece.solutionPosition.y - tentativePiece.position.y)
            }

            val finalPosition = Offset(tentativePiece.position.x + bestSnapDelta.x, tentativePiece.position.y + bestSnapDelta.y)
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) it.copy(position = finalPosition) else it
            }
            _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
            persist()
            checkCompletion()
        }
    }

    fun rotatePiece(pieceId: Int, angle: Float) {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val updatedPieces = currentPuzzle.pieces.map {
                if (it.id == pieceId && !it.isLocked) {
                    it.copy(rotation = (it.rotation + angle) % 360f)
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

    fun hint() {
        _puzzle.value?.let { currentPuzzle ->
            if (currentPuzzle.isComplete) return
            val pieceToHint = currentPuzzle.pieces.find { !it.isLocked && (it.position != it.solutionPosition || it.rotation != it.solutionRotation) }
            if (pieceToHint != null) {
                val updatedPieces = currentPuzzle.pieces.map {
                    if (it.id == pieceToHint.id) {
                        it.copy(position = it.solutionPosition, rotation = it.solutionRotation, isLocked = true)
                    } else it
                }
                _puzzle.value = currentPuzzle.copy(pieces = updatedPieces)
                persist()
                checkCompletion()
            }
        }
    }

    private fun checkCompletion() {
        val currentPuzzle = _puzzle.value ?: return

        val allAtSolution = currentPuzzle.pieces.all { piece ->
            val dist = kotlin.math.hypot(piece.position.x - piece.solutionPosition.x, piece.position.y - piece.solutionPosition.y)
            val normRot = (piece.rotation % 360f + 360f) % 360f
            val normSolRot = (piece.solutionRotation % 360f + 360f) % 360f
            val rotDiff = kotlin.math.abs(normRot - normSolRot) % 360f
            dist < 15f && (rotDiff < 15f || rotDiff > 345f)
        }

        if (allAtSolution) {
            _isGameWon.value = true
            _puzzle.value = currentPuzzle.copy(isComplete = true)
            repository.clear(mode, puzzleId)
            return
        }

        for (i in currentPuzzle.pieces.indices) {
            for (j in i + 1 until currentPuzzle.pieces.size) {
                if (GeometryUtils.doPolygonsIntersect(
                        currentPuzzle.pieces[i].currentVertices,
                        currentPuzzle.pieces[j].currentVertices
                    )) {
                    return
                }
            }
        }

        val allInside = currentPuzzle.pieces.all { piece ->
            GeometryUtils.isPolygonInside(piece.currentVertices, currentPuzzle.target.vertices)
        }

        if (allInside) {
            _isGameWon.value = true
            _puzzle.value = currentPuzzle.copy(isComplete = true)
            repository.clear(mode, puzzleId)
        }
    }
}
