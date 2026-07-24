package com.funkyotc.puzzleverse.tangrams.ui

import android.content.Context
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.tangrams.data.TangramsPregenerated
import com.funkyotc.puzzleverse.tangrams.engine.PieceType
import com.funkyotc.puzzleverse.tangrams.engine.SvgPuzzleParser
import com.funkyotc.puzzleverse.tangrams.engine.TargetSlot
import com.funkyotc.puzzleverse.tangrams.engine.TangramValidator
import com.funkyotc.puzzleverse.tangrams.model.TangramPiece
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.hypot

class TangramsViewModel(
    private val context: Context,
    private val mode: String?,
    private val initialPuzzleId: String?
) : ViewModel() {

    private val _pieces = MutableStateFlow<List<TangramPiece>>(emptyList())
    val pieces: StateFlow<List<TangramPiece>> = _pieces.asStateFlow()

    private val _targetSilhouette = MutableStateFlow<Path?>(null)
    val targetSilhouette: StateFlow<Path?> = _targetSilhouette.asStateFlow()

    private val _isPuzzleSolved = MutableStateFlow(false)
    val isPuzzleSolved: StateFlow<Boolean> = _isPuzzleSolved.asStateFlow()

    private val _selectedPieceId = MutableStateFlow<Int?>(null)
    val selectedPieceId: StateFlow<Int?> = _selectedPieceId.asStateFlow()
    
    private val _currentPuzzleName = MutableStateFlow("")
    val currentPuzzleName: StateFlow<String> = _currentPuzzleName.asStateFlow()
    
    private val _currentPuzzleId = MutableStateFlow("")
    val currentPuzzleId: StateFlow<String> = _currentPuzzleId.asStateFlow()

    private var screenWidth: Float = 1080f
    private var screenHeight: Float = 2400f
    private var calculatedPieceScale: Float = 100f
    private var targetSolutionVertices: List<PointF> = emptyList()
    private var targetSlots: List<TargetSlot> = emptyList()
    private var targetPiecePaths: List<Pair<Path, PieceType>> = emptyList()
    private var isInitialized = false

    fun initialize(width: Float, height: Float) {
        if (isInitialized && screenWidth == width && screenHeight == height) return
        screenWidth = width
        screenHeight = height
        isInitialized = true

        loadNewPuzzle()
    }

    fun loadNewPuzzle() {
        val puzzleInfo = if (mode == "puzzle" && initialPuzzleId != null) {
            TangramsPregenerated.getPuzzleById(initialPuzzleId) ?: TangramsPregenerated.ALL_PUZZLES.first()
        } else if (mode == "daily") {
            val dailyIndex = (com.funkyotc.puzzleverse.core.todayEpochDay() % TangramsPregenerated.ALL_PUZZLES.size).toInt()
            TangramsPregenerated.ALL_PUZZLES[dailyIndex]
        } else {
            TangramsPregenerated.ALL_PUZZLES.random()
        }
        
        _currentPuzzleName.value = puzzleInfo.name
        _currentPuzzleId.value = puzzleInfo.id

        viewModelScope.launch {
            try {
                val svgText = context.assets.open(puzzleInfo.assetFileName).bufferedReader().use { it.readText() }
                val result = SvgPuzzleParser.createSolidSilhouette(svgText, screenWidth, screenHeight)
                _targetSilhouette.value = result.path
                calculatedPieceScale = result.pieceScaleFactor
                targetSolutionVertices = result.targetVertices
                targetSlots = result.targetSlots
                targetPiecePaths = result.targetPiecePaths
                loadDefaultPieces()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadDefaultPieces() {
        val scale = calculatedPieceScale
        // Tray starts at 64% screen height
        val startY = screenHeight * 0.64f

        val defaultPieces = listOf(
            // 1. Large Triangle 1
            TangramPiece(
                id = 1,
                unitPoints = listOf(PointF(0f, 0f), PointF(4f, 0f), PointF(2f, 2f)),
                x = screenWidth * 0.05f,
                y = startY,
                rotationAngle = 0f,
                color = Color(0xFFFF5252), // Coral Red
                scaleFactor = scale
            ),
            // 2. Large Triangle 2
            TangramPiece(
                id = 2,
                unitPoints = listOf(PointF(0f, 0f), PointF(4f, 0f), PointF(2f, 2f)),
                x = screenWidth * 0.50f,
                y = startY,
                rotationAngle = 90f,
                color = Color(0xFF7C4DFF), // Purple
                scaleFactor = scale
            ),
            // 3. Medium Triangle
            TangramPiece(
                id = 3,
                unitPoints = listOf(PointF(0f, 0f), PointF(2.828427f, 0f), PointF(1.414214f, 1.414214f)),
                x = screenWidth * 0.05f,
                y = startY + scale * 1.6f,
                rotationAngle = 45f,
                color = Color(0xFF00E676), // Emerald Green
                scaleFactor = scale
            ),
            // 4. Square
            TangramPiece(
                id = 4,
                unitPoints = listOf(PointF(0f, 1f), PointF(1f, 0f), PointF(2f, 1f), PointF(1f, 2f)),
                x = screenWidth * 0.42f,
                y = startY + scale * 1.6f,
                rotationAngle = 0f,
                color = Color(0xFFFFD700), // Amber Gold
                scaleFactor = scale
            ),
            // 5. Parallelogram (Can be flipped horizontally)
            TangramPiece(
                id = 7,
                unitPoints = listOf(PointF(0f, 0f), PointF(2f, 0f), PointF(3f, 1f), PointF(1f, 1f)),
                x = screenWidth * 0.68f,
                y = startY + scale * 1.6f,
                rotationAngle = 0f,
                isFlipped = false,
                color = Color(0xFF00B0FF), // Electric Blue
                scaleFactor = scale
            ),
            // 6. Small Triangle 1
            TangramPiece(
                id = 5,
                unitPoints = listOf(PointF(0f, 0f), PointF(2f, 0f), PointF(1f, 1f)),
                x = screenWidth * 0.15f,
                y = startY + scale * 2.9f,
                rotationAngle = 180f,
                color = Color(0xFF1DE9B6), // Cyan
                scaleFactor = scale
            ),
            // 7. Small Triangle 2
            TangramPiece(
                id = 6,
                unitPoints = listOf(PointF(0f, 0f), PointF(2f, 0f), PointF(1f, 1f)),
                x = screenWidth * 0.55f,
                y = startY + scale * 2.9f,
                rotationAngle = 270f,
                color = Color(0xFFFF9100), // Bright Orange
                scaleFactor = scale
            )
        )
        _pieces.value = defaultPieces
        _isPuzzleSolved.value = false
        _selectedPieceId.value = null
    }

    fun selectPiece(id: Int?) {
        _selectedPieceId.value = id
        if (id != null) {
            bringToFront(id)
        }
    }

    fun updatePiecePosition(id: Int, dx: Float, dy: Float) {
        _pieces.value = _pieces.value.map { piece ->
            if (piece.id == id) {
                piece.copy(x = piece.x + dx, y = piece.y + dy)
            } else piece
        }
    }

    fun rotatePiece(id: Int) {
        _pieces.value = _pieces.value.map { piece ->
            if (piece.id == id) {
                val newAngle = (piece.rotationAngle + 45f) % 360f
                piece.copy(rotationAngle = newAngle)
            } else piece
        }
        checkVictory()
    }

    fun flipPiece(id: Int) {
        _pieces.value = _pieces.value.map { piece ->
            if (piece.id == id) {
                piece.copy(isFlipped = !piece.isFlipped)
            } else piece
        }
        checkVictory()
    }

    private fun getPieceType(piece: TangramPiece): PieceType {
        return when (piece.id) {
            1, 2 -> PieceType.LARGE_TRIANGLE
            3 -> PieceType.MEDIUM_TRIANGLE
            4 -> PieceType.SQUARE
            5, 6 -> PieceType.SMALL_TRIANGLE
            7 -> PieceType.PARALLELOGRAM
            else -> PieceType.SMALL_TRIANGLE
        }
    }

    /**
     * Magnetically snaps piece vertices and centers to target solution slots & neighboring piece corners.
     */
    fun snapPiece(id: Int) {
        val currentPieces = _pieces.value
        val activePiece = currentPieces.firstOrNull { it.id == id } ?: return

        // 1. Check center snapping to matching target solution slots first
        val pBounds = RectF()
        activePiece.getTransformedPath().computeBounds(pBounds, true)
        val pieceCenter = PointF(pBounds.centerX(), pBounds.centerY())
        val pType = getPieceType(activePiece)

        val matchingSlot = targetSlots.firstOrNull { slot ->
            slot.pieceType == pType && hypot(slot.center.x - pieceCenter.x, slot.center.y - pieceCenter.y) <= 60f
        }

        if (matchingSlot != null) {
            val dx = matchingSlot.center.x - pieceCenter.x
            val dy = matchingSlot.center.y - pieceCenter.y
            _pieces.value = _pieces.value.map { piece ->
                if (piece.id == id) {
                    piece.copy(x = piece.x + dx, y = piece.y + dy)
                } else piece
            }
            checkVictory()
            return
        }

        // 2. Vertex snapping to other placed pieces and target vertices
        val activeVertices = getTransformedVertices(activePiece)
        val targetVertices = mutableListOf<PointF>()

        targetVertices.addAll(targetSolutionVertices)
        currentPieces.filter { it.id != id }.forEach { piece ->
            targetVertices.addAll(getTransformedVertices(piece))
        }

        val snapThreshold = 40f
        var bestDx = 0f
        var bestDy = 0f
        var minDistance = Float.MAX_VALUE

        for (v in activeVertices) {
            for (t in targetVertices) {
                val dist = hypot(t.x - v.x, t.y - v.y)
                if (dist < minDistance && dist <= snapThreshold) {
                    minDistance = dist
                    bestDx = t.x - v.x
                    bestDy = t.y - v.y
                }
            }
        }

        if (minDistance <= snapThreshold) {
            _pieces.value = _pieces.value.map { piece ->
                if (piece.id == id) {
                    piece.copy(x = piece.x + bestDx, y = piece.y + bestDy)
                } else piece
            }
        }
        checkVictory()
    }

    private fun getTransformedVertices(piece: TangramPiece): List<PointF> {
        val matrix = Matrix()
        val path = Path()
        if (piece.unitPoints.isEmpty()) return emptyList()

        path.moveTo(piece.unitPoints[0].x * piece.scaleFactor, piece.unitPoints[0].y * piece.scaleFactor)
        for (i in 1 until piece.unitPoints.size) {
            path.lineTo(piece.unitPoints[i].x * piece.scaleFactor, piece.unitPoints[i].y * piece.scaleFactor)
        }
        path.close()

        val bounds = RectF()
        path.computeBounds(bounds, true)

        if (piece.isFlipped) {
            matrix.postScale(-1f, 1f, bounds.centerX(), bounds.centerY())
        }
        matrix.postRotate(piece.rotationAngle, bounds.centerX(), bounds.centerY())
        matrix.postTranslate(piece.x, piece.y)

        val srcPoints = FloatArray(piece.unitPoints.size * 2)
        for (i in piece.unitPoints.indices) {
            srcPoints[i * 2] = piece.unitPoints[i].x * piece.scaleFactor
            srcPoints[i * 2 + 1] = piece.unitPoints[i].y * piece.scaleFactor
        }

        val dstPoints = FloatArray(srcPoints.size)
        matrix.mapPoints(dstPoints, srcPoints)

        val result = mutableListOf<PointF>()
        for (i in 0 until dstPoints.size step 2) {
            result.add(PointF(dstPoints[i], dstPoints[i + 1]))
        }
        return result
    }

    fun bringToFront(id: Int) {
        val currentList = _pieces.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1 && index != currentList.lastIndex) {
            val item = currentList.removeAt(index)
            currentList.add(item)
            _pieces.value = currentList
        }
    }

    fun checkVictory() {
        val solved = TangramValidator.isPuzzleSolved(_pieces.value, targetPiecePaths)
        _isPuzzleSolved.value = solved
    }

    /**
     * Debug function: log all target path bounds and areas,
     * then log all piece bounds and areas, to diagnose scale mismatches.
     */
    fun debugLogState() {
        android.util.Log.d("TangramDebug", "=== DEBUG STATE ===")
        android.util.Log.d("TangramDebug", "Screen: ${screenWidth}x${screenHeight}")
        android.util.Log.d("TangramDebug", "PieceScale: $calculatedPieceScale")
        android.util.Log.d("TangramDebug", "TargetPiecePaths count: ${targetPiecePaths.size}")
        
        for ((i, pair) in targetPiecePaths.withIndex()) {
            val (tPath, tType) = pair
            val b = RectF()
            tPath.computeBounds(b, true)
            android.util.Log.d("TangramDebug", "  Target[$i] type=$tType bounds=[${b.left.toInt()},${b.top.toInt()},${b.right.toInt()},${b.bottom.toInt()}] size=${b.width().toInt()}x${b.height().toInt()}")
        }
        
        for (piece in _pieces.value) {
            val pPath = piece.getTransformedPath()
            val b = RectF()
            pPath.computeBounds(b, true)
            val pType = getPieceType(piece)
            android.util.Log.d("TangramDebug", "  Piece[${piece.id}] type=$pType bounds=[${b.left.toInt()},${b.top.toInt()},${b.right.toInt()},${b.bottom.toInt()}] size=${b.width().toInt()}x${b.height().toInt()} pos=(${piece.x.toInt()},${piece.y.toInt()}) rot=${piece.rotationAngle} flip=${piece.isFlipped}")
        }
        android.util.Log.d("TangramDebug", "=== END DEBUG ===")
        
        // Also trigger a victory check to see the overlap logs
        checkVictory()
    }
}

class TangramsViewModelFactory(
    private val context: Context,
    private val mode: String?,
    private val initialPuzzleId: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TangramsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TangramsViewModel(context, mode, initialPuzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
