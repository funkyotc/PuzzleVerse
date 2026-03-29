package com.funkyotc.puzzleverse.shapes.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import com.funkyotc.puzzleverse.shapes.viewmodel.ShapesViewModel
import com.funkyotc.puzzleverse.shapes.viewmodel.ShapesViewModelFactory
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapesScreen(
    navController: NavController,
    mode: String? = "standard",
    context: Context = LocalContext.current,
    streakRepository: StreakRepository,
    viewModel: ShapesViewModel = viewModel(factory = ShapesViewModelFactory(mode, context, streakRepository))
) {
    val puzzle by viewModel.puzzle.collectAsState()
    val isGameWon by viewModel.isGameWon.collectAsState()
    var selectedPieceId by remember { mutableStateOf<Int?>(null) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { /* wait for button */ },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle! All 7 pieces fit perfectly.") },
            confirmButton = {
                Button(onClick = { viewModel.loadNewPuzzle() }) {
                    Text("New Puzzle")
                }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Puzzle") },
            text = { Text("Start a new puzzle? Your current progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.loadNewPuzzle()
                    showNewGameDialog = false
                    selectedPieceId = null
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shapes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (mode != "daily") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (selectedPieceId != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { selectedPieceId?.let { viewModel.rotatePiece(it, -90f) } }
                    ) {
                        Text("↺ Rotate")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { selectedPieceId?.let { viewModel.flipPiece(it) } }
                    ) {
                        Text("↔ Flip")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { selectedPieceId?.let { viewModel.rotatePiece(it, 90f) } }
                    ) {
                        Text("↻ Rotate")
                    }
                }
            }
        }
    ) { paddingValues ->
        puzzle?.let { puzzleState ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { offset ->
                                    val clicked = puzzleState.pieces
                                        .filter { !it.isLocked }
                                        .findLast { piece ->
                                            GeometryUtils.isPointInPolygon(
                                                offset,
                                                viewModel.getPieceVertices(piece)
                                            )
                                        }
                                    selectedPieceId = clicked?.definitionId
                                },
                                onDoubleTap = { offset ->
                                    val clicked = puzzleState.pieces
                                        .filter { !it.isLocked }
                                        .findLast { piece ->
                                            GeometryUtils.isPointInPolygon(
                                                offset,
                                                viewModel.getPieceVertices(piece)
                                            )
                                        }
                                    clicked?.let { viewModel.rotatePiece(it.definitionId, 90f) }
                                }
                            )
                        }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val clicked = puzzleState.pieces
                                        .filter { !it.isLocked }
                                        .findLast { piece ->
                                            GeometryUtils.isPointInPolygon(
                                                offset,
                                                viewModel.getPieceVertices(piece)
                                            )
                                        }
                                    selectedPieceId = clicked?.definitionId
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    selectedPieceId?.let { id ->
                                        val piece = puzzleState.pieces.find { it.definitionId == id }
                                        piece?.let {
                                            viewModel.movePiece(it.definitionId, it.position + dragAmount)
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    // Draw target silhouette
                    val targetPath = buildPolyPath(puzzleState.target.vertices)
                    drawPath(targetPath, Color.LightGray.copy(alpha = 0.3f), style = Fill)
                    drawPath(targetPath, Color.Gray, style = Stroke(width = 2f))

                    // Draw pieces
                    puzzleState.pieces.forEach { piece ->
                        val vertices = viewModel.getPieceVertices(piece)
                        if (vertices.isEmpty()) return@forEach

                        val def = viewModel.pieceDefinitions[piece.definitionId]
                        val pieceColor = def?.color ?: Color.Gray
                        val isSelected = piece.definitionId == selectedPieceId

                        val piecePath = buildPolyPath(vertices)

                        // Locked pieces get a subtle overlay to indicate placement
                        drawPath(
                            piecePath,
                            if (piece.isLocked) pieceColor.copy(alpha = 0.85f) else pieceColor,
                            style = Fill
                        )

                        // Border
                        drawPath(
                            piecePath,
                            when {
                                piece.isLocked -> Color(0xFF4CAF50) // Green border for locked
                                isSelected -> Color.White
                                else -> Color.Black
                            },
                            style = Stroke(width = if (isSelected) 4f else 2f)
                        )
                    }
                }

                // Lock indicator overlay (shows how many pieces placed)
                val lockedCount = puzzleState.pieces.count { it.isLocked }
                if (lockedCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "$lockedCount / ${puzzleState.pieces.size}",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun buildPolyPath(vertices: List<androidx.compose.ui.geometry.Offset>): androidx.compose.ui.graphics.Path {
    return androidx.compose.ui.graphics.Path().apply {
        if (vertices.isEmpty()) return@apply
        moveTo(vertices.first().x, vertices.first().y)
        vertices.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }
}
