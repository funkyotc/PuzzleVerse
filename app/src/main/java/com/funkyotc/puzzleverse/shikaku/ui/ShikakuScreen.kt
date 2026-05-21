package com.funkyotc.puzzleverse.shikaku.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
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
    val cellSize = calculateCellSize(gridSize)

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
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                val gridPixelSize = gridSize * cellSize
                Box(
                    modifier = Modifier
                        .width(gridPixelSize.dp)
                        .height(gridPixelSize.dp)
                        .pointerInput(gridSize, cellSize) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val col = (offset.x / cellSize).toInt().coerceIn(0, gridSize - 1)
                                    val row = (offset.y / cellSize).toInt().coerceIn(0, gridSize - 1)
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
                                val col = (offset.x / cellSize).toInt().coerceIn(0, gridSize - 1)
                                val row = (offset.y / cellSize).toInt().coerceIn(0, gridSize - 1)
                                drawStartCell?.let { start ->
                                    val minRow = minOf(start.first, row)
                                    val maxRow = maxOf(start.first, row)
                                    val minCol = minOf(start.second, col)
                                    val maxCol = maxOf(start.second, col)
                                    currentDragRect = ShikakuRectangle(
                                        id = UUID.randomUUID().toString(),
                                        row = minRow,
                                        col = minCol,
                                        width = maxCol - minCol + 1,
                                        height = maxRow - minRow + 1
                                    )
                                }
                            }
                        }
                ) {
                    // Draw grid cells
                    for (r in 0 until gridSize) {
                        for (c in 0 until gridSize) {
                            val cell = board.getCell(r, c)
                            val cellColor = when {
                                cell != null && cell.clue != null -> Color(0xFFF5F5F5)
                                cell != null && cell.rectangleId != null -> {
                                    val hash = cell.rectangleId.hashCode()
                                    Color(
                                        android.graphics.Color.HSVToColor(floatArrayOf(
                                            (hash % 360).toFloat(),
                                            0.15f,
                                            0.95f
                                        ))
                                    )
                                }
                                else -> Color.White
                            }

                            Box(
                                modifier = Modifier
                                    .width(cellSize.dp)
                                    .height(cellSize.dp)
                                    .border(0.5.dp, Color.Gray)
                                    .background(cellColor)
                                    .pointerInput(r, c) {
                                        detectDragGestures(
                                            onDragStart = {
                                                drawStartCell = Pair(r, c)
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
                                            val col = (offset.x / cellSize).toInt().coerceIn(0, gridSize - 1)
                                            val row = (offset.y / cellSize).toInt().coerceIn(0, gridSize - 1)
                                            drawStartCell?.let { start ->
                                                val minRow = minOf(start.first, row)
                                                val maxRow = maxOf(start.first, row)
                                                val minCol = minOf(start.second, col)
                                                val maxCol = maxOf(start.second, col)
                                                currentDragRect = ShikakuRectangle(
                                                    id = UUID.randomUUID().toString(),
                                                    row = minRow,
                                                    col = minCol,
                                                    width = maxCol - minCol + 1,
                                                    height = maxRow - minRow + 1
                                                )
                                            }
                                        }
                                    }
                            ) {
                                cell?.let { shikakuCell ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        if (shikakuCell.clue != null) {
                                            Text(
                                                text = shikakuCell.clue.toString(),
                                                fontSize = when {
                                                    gridSize <= 8 -> 18.sp
                                                    gridSize <= 10 -> 15.sp
                                                    else -> 13.sp
                                                },
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                        }
                                        if (shikakuCell.clue == null && shikakuCell.rectangleId != null) {
                                            Box(
                                                modifier = Modifier
                                                    .width(8.dp)
                                                    .height(8.dp)
                                                    .background(
                                                        Color(0xFF2196F3),
                                                        RoundedCornerShape(4.dp)
                                                    )
                                                    .align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Draw current drag rectangle preview
                    currentDragRect?.let { rect ->
                        Box(
                            modifier = Modifier
                                .width((rect.width * cellSize).dp)
                                .height((rect.height * cellSize).dp)
                                .offset(
                                    x = (rect.col * cellSize).dp,
                                    y = (rect.row * cellSize).dp
                                )
                                .border(2.dp, Color.Blue.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                .background(Color.Blue.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                        )
                    }
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
