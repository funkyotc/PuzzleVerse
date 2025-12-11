package com.funkyotc.puzzleverse.shapes.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapesScreen(
    navController: NavController, 
    mode: String? = "standard",
    viewModel: ShapesViewModel = viewModel(factory = ShapesViewModelFactory(mode))
) {
    val puzzle by viewModel.puzzle.collectAsState()
    val isGameWon by viewModel.isGameWon.collectAsState()
    var selectedPieceId by remember { mutableStateOf<Int?>(null) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { viewModel.loadNewPuzzle() },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle! All pieces fit perfectly.") },
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
            text = { Text("Are you sure you want to start a new puzzle? Your current progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.loadNewPuzzle()
                    showNewGameDialog = false
                    selectedPieceId = null // Deselect on new game
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
             // Controls for the selected piece
             if (selectedPieceId != null) {
                 Row(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(16.dp)
                         .background(MaterialTheme.colorScheme.surface),
                     horizontalArrangement = Arrangement.Center,
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     Button(onClick = { selectedPieceId?.let { viewModel.rotatePiece(it, -90f) } }) {
                         Text("Rotate L")
                     }
                     Spacer(modifier = Modifier.width(16.dp))
                     Button(onClick = { selectedPieceId?.let { viewModel.flipPiece(it) } }) {
                         Text("Flip")
                     }
                     Spacer(modifier = Modifier.width(16.dp))
                     Button(onClick = { selectedPieceId?.let { viewModel.rotatePiece(it, 90f) } }) {
                         Text("Rotate R")
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
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                // Hit test against CURRENT rotated/flipped vertices
                                val clickedPiece = puzzleState.pieces.findLast { piece ->
                                     GeometryUtils.isPointInPolygon(offset, piece.currentVertices)
                                }
                                selectedPieceId = clickedPiece?.id
                            },
                            onDoubleTap = { offset ->
                                // Double tap shortcut to Rotate
                                val clickedPiece = puzzleState.pieces.findLast { piece ->
                                     GeometryUtils.isPointInPolygon(offset, piece.currentVertices)
                                }
                                clickedPiece?.let {
                                    viewModel.rotatePiece(it.id, 90f)
                                }
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val clickedPiece = puzzleState.pieces.findLast { piece ->
                                     GeometryUtils.isPointInPolygon(offset, piece.currentVertices)
                                }
                                selectedPieceId = clickedPiece?.id // Select on drag start
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                selectedPieceId?.let { id ->
                                    val piece = puzzleState.pieces.find { it.id == id }
                                    piece?.let {
                                        viewModel.movePiece(it.id, it.position + dragAmount)
                                    }
                                }
                            }
                        )
                    }
                ) {
                    // 1. Draw Target Silhouette
                    val targetPath = Path().apply {
                        if (puzzleState.target.vertices.isNotEmpty()) {
                            val first = puzzleState.target.vertices.first()
                            moveTo(first.x, first.y)
                            puzzleState.target.vertices.drop(1).forEach {
                                lineTo(it.x, it.y)
                            }
                            close()
                        }
                    }
                    
                    drawPath(targetPath, Color.LightGray.copy(alpha = 0.5f), style = Fill)
                    drawPath(targetPath, Color.Black, style = Stroke(width = 3f))

                    // 2. Draw Pieces
                    puzzleState.pieces.forEach { piece ->
                        val isSelected = piece.id == selectedPieceId
                        val piecePath = Path().apply {
                            val vertices = piece.currentVertices
                            if (vertices.isNotEmpty()) {
                                val first = vertices.first()
                                moveTo(first.x, first.y)
                                vertices.drop(1).forEach {
                                    lineTo(it.x, it.y)
                                }
                                close()
                            }
                        }
                        
                        drawPath(piecePath, piece.color, style = Fill)
                        drawPath(
                            piecePath, 
                            if (isSelected) Color.White else Color.Black, 
                            style = Stroke(width = if (isSelected) 5f else 2f)
                        )
                    }
                }
            }
        }
    }
}