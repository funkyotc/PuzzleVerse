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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Info
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
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapesScreen(
    navController: NavController, 
    mode: String? = "standard",
    settingsRepository: SettingsRepository,
    viewModel: ShapesViewModel = viewModel(factory = ShapesViewModelFactory(mode))
) {
    val puzzle by viewModel.puzzle.collectAsState()
    val isGameWon by viewModel.isGameWon.collectAsState()
    var selectedPieceId by remember { mutableStateOf<Int?>(null) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHintDialog by remember { mutableStateOf(false) }

    if (showHintDialog) {
        AlertDialog(
            onDismissRequest = { showHintDialog = false },
            title = { Text("Use a Hint?") },
            text = { Text("Are you sure you want to use a hint to reveal part of the puzzle?") },
            confirmButton = {
                TextButton(onClick = {
                    showHintDialog = false
                    viewModel.hint() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showHintDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            settingsRepository.addWin()
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Drag, rotate, and flip the pieces so they fit perfectly into the target shape.") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = { navController.navigate("game/shapes/standard/new") { popUpTo("home") } }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else {

                TextButton(onClick = { showHowToDialog = false }) {
                    Text("OK")

                }
                }
            }
        )
    }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { viewModel.loadNewPuzzle() },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle! All pieces fit perfectly.") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = { navController.navigate("game/shapes/standard/new") { popUpTo("home") } }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else {

                Button(onClick = { viewModel.loadNewPuzzle() }) {
                    Text("New Puzzle")

                }
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
                    IconButton(onClick = { showHowToDialog = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    IconButton(onClick = { showHintDialog = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Hint")
                    }
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
                                val clickedPiece = puzzleState.pieces.findLast { piece ->
                                     GeometryUtils.isPointInPolygon(offset, piece.currentVertices)
                                }
                                selectedPieceId = clickedPiece?.id
                            },
                            onDoubleTap = { offset ->
                                val clickedPiece = puzzleState.pieces.findLast { piece ->
                                     GeometryUtils.isPointInPolygon(offset, piece.currentVertices)
                                }
                                if (clickedPiece != null) {
                                    selectedPieceId = clickedPiece.id
                                    viewModel.rotatePiece(clickedPiece.id, 90f)
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
                                if (clickedPiece != null) {
                                    selectedPieceId = clickedPiece.id
                                }
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
                        
                        if (isSelected) {
                            // Draw a shadow for selected piece to give depth
                            drawPath(
                                piecePath,
                                Color.Black.copy(alpha = 0.3f),
                                style = Stroke(width = 12f)
                            )
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