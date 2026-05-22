package com.funkyotc.puzzleverse.shikaku.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.shikaku.data.ShikakuPregenerated
import com.funkyotc.puzzleverse.shikaku.data.ShikakuRectangle
import com.funkyotc.puzzleverse.shikaku.viewmodel.ShikakuViewModel
import com.funkyotc.puzzleverse.shikaku.viewmodel.ShikakuViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import androidx.compose.ui.platform.LocalContext
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShikakuScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    forceNewGame: Boolean = false,
    puzzleId: String? = null,
    viewModel: ShikakuViewModel = viewModel(factory = ShikakuViewModelFactory(LocalContext.current, mode, forceNewGame, streakRepository, settingsRepository, puzzleId))
) {
    val board by viewModel.board.collectAsState()
    val selectedCell by viewModel.selectedCell.collectAsState()
    val isGameWon by viewModel.isGameWon.collectAsState()
    val showHowToDialog by viewModel.showHowToDialog.collectAsState()
    val showNewGameDialog by viewModel.showNewGameDialog.collectAsState()

    var drawStartCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var currentDragRect by remember { mutableStateOf<ShikakuRectangle?>(null) }

    val context = LocalContext.current
    val soundManager = LocalSoundManager.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Shikaku") }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    val gridSize = board.gridSize

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setShowHowToDialog(false) },
            title = { Text("How To Play Shikaku") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Divide the grid into rectangular or square cells.")
                    Text("Each rectangle must contain exactly one number, which equals the area (width x height) of that rectangle.")
                    Text("All cells in the grid must be filled with exactly one rectangle - no overlaps or gaps allowed.")
                    Text("Draw rectangles by dragging across cells. Clear individual cells by tapping them.")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.setShowHowToDialog(false) }) { Text("Got it!") }
            }
        )
    }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("You Win!") },
            text = { Text("You solved the Shikaku puzzle!") },
            confirmButton = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (mode == "daily") {
                        Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            Text("Main Menu")
                        }
                        Button(onClick = { navController.navigate("game/shikaku/standard") { popUpTo("home") } }) {
                            Text("Play Again")
                        }
                    } else if (mode == "puzzle") {
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Back to List")
                        }
                        val allPuzzles = ShikakuPregenerated.PUZZLES_BY_DIFFICULTY.values.flatten()
                        val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
                        if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) {
                            val nextId = allPuzzles[currentIndex + 1].id
                            Button(onClick = {
                                navController.popBackStack()
                                navController.navigate("game/shikaku/puzzle/$nextId")
                            }) {
                                Text("Next Puzzle")
                            }
                        }
                    } else {
                        Button(onClick = { viewModel.newGame() }) {
                            Text("Play Again")
                        }
                    }
                }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setShowNewGameDialog(false) },
            title = { Text("New Game") },
            text = { Text("Are you sure you want to start a new game? Your current progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.newGame()
                    viewModel.setShowNewGameDialog(false)
                }) { Text("Start New Game") }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setShowNewGameDialog(false) }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shikaku") },
                navigationIcon = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.setShowHowToDialog(true)
                    }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To Play")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            viewModel.setShowNewGameDialog(true)
                        }) {
                            Icon(Icons.Filled.RestartAlt, contentDescription = "New Game")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Grid
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val safeGridSize = maxOf(1, gridSize)
                val actualGridSizeDp = minOf(maxWidth, maxHeight)
                val cellSizeDp = actualGridSizeDp / safeGridSize
                val cellSizePx = with(LocalDensity.current) { actualGridSizeDp.toPx() } / safeGridSize

                // Inner perfectly square grid board container
                Box(
                    modifier = Modifier
                        .size(actualGridSizeDp)
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Draw cells
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        for (r in 0 until safeGridSize) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                for (c in 0 until safeGridSize) {
                                    val cell = board.getCell(r, c)
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .border(0.5.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        cell?.let { shikakuCell ->
                                            if (shikakuCell.clue != null) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(maxOf(24.dp, cellSizeDp * 0.6f))
                                                        .background(
                                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                                                            RoundedCornerShape(8.dp)
                                                        ),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = shikakuCell.clue.toString(),
                                                        fontSize = when {
                                                            safeGridSize <= 8 -> 18.sp
                                                            safeGridSize <= 10 -> 15.sp
                                                            else -> 12.sp
                                                        },
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Render placed player rectangles
                    board.playerRectangles.forEach { rect ->
                        val cluesInRect = board.getClueCells().filter { clueCell ->
                            clueCell.row >= rect.row && clueCell.row < rect.row + rect.height &&
                            clueCell.col >= rect.col && clueCell.col < rect.col + rect.width
                        }
                        val rectArea = rect.width * rect.height
                        val clueCell = cluesInRect.firstOrNull()
                        val isValid = cluesInRect.size == 1 && clueCell != null && rectArea == clueCell.clue
                        val isInvalid = cluesInRect.size > 1 || (cluesInRect.size == 1 && clueCell != null && rectArea > (clueCell.clue ?: 0))
                        
                        // Generate premium dynamic HSV pastel color unique to this rectangle
                        val hash = rect.id.hashCode()
                        val hue = (hash % 360).let { if (it < 0) it + 360 else it }.toFloat()
                        val pastelFill = Color(
                            android.graphics.Color.HSVToColor(floatArrayOf(
                                hue,
                                0.08f, // Soft pastel saturation (reduced from 0.18f)
                                0.96f  // High brightness
                            ))
                        ).copy(alpha = 0.20f) // Softer transparent fill (reduced from 0.35f)
                        
                        val borderStroke = when {
                            isValid -> BorderStroke(2.dp, Color(0xFF81C784)) // Soft Sage/Emerald Green
                            isInvalid -> BorderStroke(2.dp, Color(0xFFE57373)) // Soft Crimson/Rose Red
                            else -> BorderStroke(2.dp, Color(0xFFB0BEC5)) // Soft Slate Gray/Silver
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart) // Align to TopStart for absolute offset positioning
                                .offset(
                                    x = cellSizeDp * rect.col,
                                    y = cellSizeDp * rect.row
                                )
                                .width(cellSizeDp * rect.width)
                                .height(cellSizeDp * rect.height)
                                .padding(2.dp)
                                .background(pastelFill, RoundedCornerShape(6.dp))
                                .border(borderStroke, RoundedCornerShape(6.dp))
                        )
                    }

                    // Draw drag preview
                    val previewRect = currentDragRect
                    val targetX = if (previewRect != null) cellSizeDp * previewRect.col else 0.dp
                    val targetY = if (previewRect != null) cellSizeDp * previewRect.row else 0.dp
                    val targetW = if (previewRect != null) cellSizeDp * previewRect.width else 0.dp
                    val targetH = if (previewRect != null) cellSizeDp * previewRect.height else 0.dp

                    val animatedX by animateDpAsState(
                        targetValue = targetX,
                        label = "dragX"
                    )
                    val animatedY by animateDpAsState(
                        targetValue = targetY,
                        label = "dragY"
                    )
                    val animatedW by animateDpAsState(
                        targetValue = targetW,
                        label = "dragW"
                    )
                    val animatedH by animateDpAsState(
                        targetValue = targetH,
                        label = "dragH"
                    )
                    val animatedAlpha by animateFloatAsState(
                        targetValue = if (currentDragRect != null) 1f else 0f,
                        label = "dragAlpha"
                    )

                    if (animatedAlpha > 0.01f) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(x = animatedX, y = animatedY)
                                .width(animatedW)
                                .height(animatedH)
                                .padding(2.dp)
                                .border(
                                    BorderStroke(2.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.8f * animatedAlpha)),
                                    RoundedCornerShape(6.dp)
                                )
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f * animatedAlpha), RoundedCornerShape(6.dp))
                        )
                    }

                    // Transparent gesture touch overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(gridSize, cellSizePx) {
                                detectTapGestures(
                                    onTap = { offset ->
                                        val col = (offset.x / cellSizePx).toInt().coerceIn(0, safeGridSize - 1)
                                        val row = (offset.y / cellSizePx).toInt().coerceIn(0, safeGridSize - 1)
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        viewModel.onCellClear(row, col)
                                    }
                                )
                            }
                            .pointerInput(gridSize, cellSizePx) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        val col = (offset.x / cellSizePx).toInt().coerceIn(0, safeGridSize - 1)
                                        val row = (offset.y / cellSizePx).toInt().coerceIn(0, safeGridSize - 1)
                                        drawStartCell = Pair(row, col)
                                    },
                                    onDragEnd = {
                                        drawStartCell?.let { start ->
                                            currentDragRect?.let { rect ->
                                                viewModel.onPlayerRectangleDraw(rect)
                                            }
                                        }
                                        drawStartCell = null
                                        currentDragRect = null
                                    },
                                    onDragCancel = {
                                        drawStartCell = null
                                        currentDragRect = null
                                    }
                                ) { change, _ ->
                                    change.consume()
                                    val offset = change.position
                                    val col = (offset.x / cellSizePx).toInt().coerceIn(0, safeGridSize - 1)
                                    val row = (offset.y / cellSizePx).toInt().coerceIn(0, safeGridSize - 1)
                                    drawStartCell?.let { start ->
                                        val minRow = minOf(start.first, row)
                                        val maxRow = maxOf(start.first, row)
                                        val minCol = minOf(start.second, col)
                                        val maxCol = maxOf(start.second, col)
                                        currentDragRect = ShikakuRectangle(
                                            id = "drag-preview",
                                            row = minRow,
                                            col = minCol,
                                            width = maxCol - minCol + 1,
                                            height = maxRow - minRow + 1
                                        )
                                    }
                                }
                            }
                    )
                }
            }

            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.undo()
                }) {
                    Icon(Icons.Filled.Undo, contentDescription = "Undo", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.hint()
                }) {
                    Icon(Icons.Filled.HourglassEmpty, contentDescription = "Hint", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.clearAllPlayerMarks()
                }) {
                    Icon(Icons.Filled.RestartAlt, contentDescription = "Clear All", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

private fun calculateCellSize(gridSize: Int): Int {
    val maxCellWidth = 360 / gridSize
    return maxOf(20, minOf(40, maxCellWidth))
}
