package com.funkyotc.puzzleverse.ui.screens.bonza

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModelFactory
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BonzaScreen(
    navController: NavController,
    mode: String?,
    forceNewGame: Boolean = false,
    context: Context = LocalContext.current,
    streakRepository: StreakRepository,
    bonzaViewModel: BonzaViewModel = viewModel(factory = BonzaViewModelFactory(context, mode, forceNewGame, streakRepository))
) {
    val isGameWon by bonzaViewModel.isGameWon.collectAsState()
    val puzzle by bonzaViewModel.puzzle.collectAsState()
    var showNewGameDialog by remember { mutableStateOf(false) }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle!") },
            confirmButton = {
                Button(onClick = { bonzaViewModel.newGame() }) {
                    Text("New Game")
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
                    bonzaViewModel.newGame()
                    showNewGameDialog = false
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
                title = { Text("Bonza - ${puzzle.theme}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showNewGameDialog = true }) {
                        Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            BonzaBoard(
                puzzle = puzzle,
                viewModel = bonzaViewModel
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BonzaBoard(puzzle: BonzaPuzzle, viewModel: BonzaViewModel) {
    val textMeasurer = rememberTextMeasurer()
    val primaryColor = MaterialTheme.colorScheme.primary
    
    // Scale configuration
    val density = LocalDensity.current
    val letterBoxSizeDp = 48.dp
    val letterBoxSizePx = with(density) { letterBoxSizeDp.toPx() }
    val letterBoxCornerRadius = 16f
    
    // Transformable state for Pan and Zoom
    var scale by remember { mutableStateOf(1f) } 
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { position ->
                        // Convert screen position to puzzle space (Grid Units)
                        val puzzlePos = (position - offset) / scale / letterBoxSizePx
                        viewModel.onDragStart(puzzlePos)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        // Drag amount in puzzle space (Grid Units)
                        viewModel.onDrag(dragAmount / scale / letterBoxSizePx)
                    },
                    onDragEnd = { viewModel.onDragEnd() }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(offset.x, offset.y) {
                scale(scale, pivot = Offset.Zero) {
                    puzzle.fragments.forEach { fragment ->
                        val shadowOffset = Offset(5f, 5f)
                        fragment.text.forEachIndexed { index, char ->
                            // Current position is in Grid Units. Convert to Pixels for drawing.
                            val gridPos = if (fragment.direction == ConnectionDirection.HORIZONTAL) {
                               Offset(fragment.currentPosition.x + index, fragment.currentPosition.y)
                            } else {
                               Offset(fragment.currentPosition.x, fragment.currentPosition.y + index)
                            }
                            
                            val letterOffset = gridPos * letterBoxSizePx

                            drawRoundRect(
                                color = Color.Gray,
                                topLeft = letterOffset + shadowOffset,
                                size = Size(letterBoxSizePx, letterBoxSizePx),
                                cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius)
                            )

                            drawRoundRect(
                                color = primaryColor,
                                topLeft = letterOffset,
                                size = Size(letterBoxSizePx, letterBoxSizePx),
                                cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius)
                            )

                            drawRoundRect(
                                color = Color.Black,
                                topLeft = letterOffset,
                                size = Size(letterBoxSizePx, letterBoxSizePx),
                                cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius),
                                style = Stroke(width = 2f)
                            )

                            val textLayoutResult = textMeasurer.measure(
                                text = char.toString(),
                                style = TextStyle(color = Color.White, fontSize = 24.sp)
                            )

                            drawText(
                                textLayoutResult,
                                topLeft = letterOffset + Offset(
                                    (letterBoxSizePx - textLayoutResult.size.width) / 2,
                                    (letterBoxSizePx - textLayoutResult.size.height) / 2
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
