package com.funkyotc.puzzleverse.hexasort.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortViewModel
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private val paletteColors = listOf(
    Color(0xFFE53935),
    Color(0xFF43A047),
    Color(0xFF1E88E5),
    Color(0xFFFDD835),
    Color(0xFF8E24AA),
    Color(0xFFFF6F00)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HexaSortScreen(
    navController: NavController,
    mode: String? = "standard",
    forceNewGame: Boolean = false,
    puzzleId: String? = null,
    settingsRepository: SettingsRepository,
    streakRepository: StreakRepository,
    viewModel: HexaSortViewModel = viewModel(
        factory = HexaSortViewModelFactory(mode, puzzleId)
    )
) {
    val state by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "HexaSort") }

    val gameState = state

    LaunchedEffect(gameState?.isWon) {
        if (gameState?.isWon == true) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            } else if (mode == "daily") {
                val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                val streak = streakRepository.getStreak("hexasort")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    streakRepository.saveStreak(newStreak)
                }
            }
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Tap groups of 2+ same-colored hexes to clear them. Clear all hexes to win!") },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHowToDialog = false
                }) { Text("OK") }
            }
        )
    }

    if (gameState?.isWon == true) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Victory!") },
            text = { Text("All hexes cleared! Score: ${gameState.score}") },
            confirmButton = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("home") { popUpTo(0) }
                    }) { Text("Main Menu") }
                    Button(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.startNewGame()
                    }) { Text("Play Again") }
                }
            }
        )
    }

    if (gameState?.isGameOver == true && !gameState.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Game Over") },
            text = { Text("No more moves available! Score: ${gameState.score}") },
            confirmButton = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("home") { popUpTo(0) }
                    }) { Text("Main Menu") }
                    Button(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.startNewGame()
                    }) { Text("Try Again") }
                }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Game") },
            text = { Text("Are you sure you want to start a new game?") },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.startNewGame()
                    showNewGameDialog = false
                }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showNewGameDialog = false
                }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hexa Sort") },
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
                        showHowToDialog = true
                    }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            showNewGameDialog = true
                        }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Game")
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            gameState?.let { gs ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Score: ${gs.score}")
                    Text(text = "Moves: ${gs.moves}")
                }

                val level = gs.level
                val hexWidth = 50f
                val hexHeight = (sqrt(3.0) * hexWidth / 2).toFloat()

                val gridWidth = level.cols * hexWidth * 0.75f + hexWidth * 0.5f
                val gridHeight = level.rows * hexHeight + hexHeight / 2

                var canvasSize by remember { mutableStateOf(androidx.compose.ui.unit.IntSize.Zero) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned { canvasSize = it.size }
                            .pointerInput(level.rows, level.cols, canvasSize) {
                                detectTapGestures { offset ->
                                    val cellCoords = hitTestHex(
                                        offset, level.rows, level.cols,
                                        hexWidth, hexHeight,
                                        canvasSize.width.toFloat(), canvasSize.height.toFloat()
                                    )
                                    if (cellCoords != null) {
                                        val (r, c) = cellCoords
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        viewModel.tapCell(r, c)
                                    }
                                }
                            }
                    ) {
                        drawHexGrid(
                            gs.grid, level.rows, level.cols,
                            hexWidth, hexHeight,
                            gs.flashingCells
                        )
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawHexGrid(
    grid: List<List<Int?>>,
    rows: Int,
    cols: Int,
    hexWidth: Float,
    hexHeight: Float,
    flashingCells: Set<Pair<Int, Int>>
) {
    val offsetX = (size.width - cols * hexWidth * 0.75f - hexWidth * 0.5f) / 2
    val offsetY = (size.height - rows * hexHeight - hexHeight / 2) / 2

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val cellValue = grid[r][c] ?: continue
            val isFlashing = (r to c) in flashingCells
            val x = offsetX + c * hexWidth * 0.75f + hexWidth / 2
            val y = offsetY + r * hexHeight + (if (r % 2 == 1) hexHeight / 2 else 0f) + hexHeight / 2

            val colorIdx = if (cellValue in paletteColors.indices) cellValue else 0
            var fillColor = paletteColors[colorIdx]
            if (isFlashing) {
                fillColor = fillColor.copy(alpha = 0.4f)
            }
            val strokeColor = fillColor.copy(alpha = 0.6f)

            val path = createHexPath(x, y, hexWidth / 2)
            drawPath(path, fillColor, style = Fill)
            drawPath(path, strokeColor, style = Stroke(width = 2f))
        }
    }
}

private fun createHexPath(centerX: Float, centerY: Float, radius: Float): Path {
    val path = Path()
    for (i in 0..5) {
        val angle = PI / 3 * i - PI / 6
        val px = centerX + (radius * cos(angle)).toFloat()
        val py = centerY + (radius * sin(angle)).toFloat()
        if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
    }
    path.close()
    return path
}

private fun hitTestHex(
    tap: Offset,
    rows: Int,
    cols: Int,
    hexWidth: Float,
    hexHeight: Float,
    canvasWidth: Float,
    canvasHeight: Float
): Pair<Int, Int>? {
    val offsetX = (canvasWidth - cols * hexWidth * 0.75f - hexWidth * 0.5f) / 2
    val offsetY = (canvasHeight - rows * hexHeight - hexHeight / 2) / 2
    val radius = hexWidth / 2
    val radiusSq = radius * radius

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val cx = offsetX + c * hexWidth * 0.75f + hexWidth / 2
            val cy = offsetY + r * hexHeight + (if (r % 2 == 1) hexHeight / 2 else 0f) + hexHeight / 2
            val dx = tap.x - cx
            val dy = tap.y - cy
            if (dx * dx + dy * dy <= radiusSq) {
                return r to c
            }
        }
    }
    return null
}
