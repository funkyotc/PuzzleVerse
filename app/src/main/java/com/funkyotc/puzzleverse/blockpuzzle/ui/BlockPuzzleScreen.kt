package com.funkyotc.puzzleverse.blockpuzzle.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.blockpuzzle.data.BoxShape
import com.funkyotc.puzzleverse.blockpuzzle.data.BoxType
import com.funkyotc.puzzleverse.blockpuzzle.viewmodel.BlockPuzzleViewModel
import com.funkyotc.puzzleverse.blockpuzzle.viewmodel.BlockPuzzleViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockPuzzleScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    viewModel: BlockPuzzleViewModel = viewModel(factory = BlockPuzzleViewModelFactory(streakRepository, mode))
) {
    val state by viewModel.state.collectAsState()
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Drag shapes from the bottom tray onto the board. Make full rows or columns to clear them and score points!") },
            confirmButton = { TextButton(onClick = { showHowToDialog = false }) { Text("OK") } }
        )
    }

    if (state.isGameOver) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Game Over") },
            text = { Text("No more space for the blocks. Final Score: ${state.score}") },
            confirmButton = {
                Button(onClick = { viewModel.startNewGame() }) { Text("Try Again") }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Game") },
            text = { Text("Are you sure you want to start over?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.startNewGame()
                    showNewGameDialog = false
                }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Block Puzzle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToDialog = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Game")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Score: ${state.score}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                var gridOffset by remember { mutableStateOf(Offset.Zero) }
                var gridWidth by remember { mutableStateOf(0f) }

                // Flash animation
                var flashRunning by remember { mutableStateOf(false) }
                LaunchedEffect(state.flashId) {
                    if (state.flashId > 0) {
                        flashRunning = true
                        kotlinx.coroutines.delay(150)
                        flashRunning = false
                    }
                }
                
                val flashAlpha by animateFloatAsState(
                    targetValue = if (flashRunning) 0.8f else 0f,
                    animationSpec = tween(durationMillis = 150),
                    label = "flashAlpha"
                )

                // The 10x10 Grid
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.LightGray)
                        .onGloballyPositioned { coordinates ->
                            gridOffset = coordinates.positionInRoot()
                            gridWidth = coordinates.size.width.toFloat()
                        }
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        for (r in 0 until 10) {
                            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                for (c in 0 until 10) {
                                    val cell = state.grid[r][c]
                                    val isFlashing = state.recentlyClearedRows.contains(r) || state.recentlyClearedCols.contains(c)
                                    val cellBgColor = if (cell == BoxType.FILLED) Color(0xFF4CAF50) 
                                                      else if (isFlashing && flashAlpha > 0f) Color.White.copy(alpha = flashAlpha)
                                                      else Color.Transparent

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .border(1.dp, Color.Gray)
                                            .background(cellBgColor)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // The Tray
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() // Allow blocks to overflow naturally
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.tray.forEachIndexed { index, shape ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (shape != null) {
                                DraggableShape(
                                    shape = shape,
                                    shapeIndex = index,
                                    gridOffset = gridOffset,
                                    gridWidth = gridWidth,
                                    onDrop = { r, c ->
                                        viewModel.placeShape(index, r, c)
                                    }
                                )
                            } else {
                                Spacer(modifier = Modifier.height(60.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun DraggableShape(
    shape: BoxShape,
    shapeIndex: Int,
    gridOffset: Offset,
    gridWidth: Float,
    onDrop: (Int, Int) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var initialPosition by remember { mutableStateOf(Offset.Zero) }

    val density = LocalDensity.current
    // gridWidth is in pixels, divide by 10 for cell width
    val blockPx = if (gridWidth > 0f) gridWidth / 10f else 0f
    val cellSizeDp = if (blockPx > 0f) with(density) { blockPx.toDp() } else 20.dp

    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                if (!isDragging) {
                    initialPosition = coordinates.positionInRoot()
                }
            }
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(gridWidth) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = {
                        isDragging = false
                        if (blockPx > 0f) {
                            val currentPosition = initialPosition + Offset(offsetX, offsetY)
                            val localDropX = currentPosition.x - gridOffset.x
                            val localDropY = currentPosition.y - gridOffset.y
                            
                            // Adjust to target nearest cell center
                            val dropRow = ((localDropY + (blockPx / 2f)) / blockPx).toInt()
                            val dropCol = ((localDropX + (blockPx / 2f)) / blockPx).toInt()
                            
                            if (dropRow in 0..9 && dropCol in 0..9) {
                                onDrop(dropRow, dropCol)
                            }
                        }
                        
                        offsetX = 0f
                        offsetY = 0f
                    },
                    onDragCancel = {
                        isDragging = false
                        offsetX = 0f
                        offsetY = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        // Draw the shape 
        val maxR = shape.blocks.maxOfOrNull { it.r } ?: 0
        val maxC = shape.blocks.maxOfOrNull { it.c } ?: 0
        
        Box(modifier = Modifier.size(cellSizeDp * (maxC + 1), cellSizeDp * (maxR + 1))) {
            shape.blocks.forEach { p ->
                Box(
                    modifier = Modifier
                        .offset(x = cellSizeDp * p.c, y = cellSizeDp * p.r)
                        .size(cellSizeDp)
                        .background(Color(0xFF2196F3))
                        .border(1.dp, Color.White)
                )
            }
        }
    }
}
