package com.funkyotc.puzzleverse.hexasort.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortEvent
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
    context: Context = LocalContext.current,
    viewModel: HexaSortViewModel = viewModel(
        factory = HexaSortViewModelFactory(context, mode, forceNewGame, streakRepository, puzzleId)
    )
) {
    val state by viewModel.state.collectAsState()
    val elapsedSeconds by viewModel.elapsedSeconds.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showVictoryDialog by remember { mutableStateOf(false) }
    var showGameOverDialog by remember { mutableStateOf(false) }
    var victoryScore by remember { mutableStateOf(0) }
    var streakUpdated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HexaSortEvent.Won -> {
                    victoryScore = event.score
                    showVictoryDialog = true
                    settingsRepository.addWin()
                    soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
                    if (mode == "puzzle" && puzzleId != null) {
                        com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository(context, "HexaSort").markCompleted(puzzleId)
                    } else if (mode == "daily" && !streakUpdated) {
                        streakUpdated = true
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
                is HexaSortEvent.GameOver -> {
                    showGameOverDialog = true
                    soundManager.playSound(SoundManager.SOUND_ID_FAILURE)
                }
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Tap groups of 2+ same-colored hexes to clear them. Clear all hexes to win!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (showVictoryDialog) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated.ALL_PUZZLES
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) allPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    showVictoryDialog = false
                    navController.popBackStack()
                    navController.navigate("game/hexasort/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "All hexes cleared! Score: $victoryScore",
            mode = mode,
            onMainMenuClick = {
                showVictoryDialog = false
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = {
                showVictoryDialog = false
                viewModel.startNewGame()
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showGameOverDialog) {
        GameEndDialog(
            isWon = false,
            title = "Game Over",
            message = "No more moves available!",
            mode = mode,
            onMainMenuClick = {
                showGameOverDialog = false
                navController.navigate("home") { popUpTo(0) }
            },
            onPlayAgainClick = {
                showGameOverDialog = false
                viewModel.startNewGame()
            }
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Game",
            message = "Are you sure you want to start a new game?",
            onConfirm = {
                showNewGameDialog = false
                viewModel.startNewGame()
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Hexa Sort",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state?.let { gs ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Score: ${gs.score}", fontSize = 16.sp)
                    Text(text = "Moves: ${gs.moves}", fontSize = 16.sp)
                    val timeFormatted = String.format(java.util.Locale.ROOT, "%02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60)
                    Text(text = timeFormatted, fontSize = 16.sp)
                }

                val level = gs.level

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    var canvasSize by remember { mutableStateOf(androidx.compose.ui.unit.IntSize.Zero) }

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned { canvasSize = it.size }
                            .pointerInput(level.rows, level.cols, canvasSize) {
                                if (canvasSize.width == 0 || canvasSize.height == 0) return@pointerInput
                                val cW = canvasSize.width.toFloat()
                                val cH = canvasSize.height.toFloat()
                                val (hexW, _) = computeHexSize(level.rows, level.cols, cW, cH)
                                detectTapGestures { offset ->
                                    val cellCoords = hitTestHex(
                                        offset, level.rows, level.cols, hexW, cW, cH
                                    )
                                    if (cellCoords != null) {
                                        val (r, c) = cellCoords
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        viewModel.tapCell(r, c)
                                    }
                                }
                            }
                    ) {
                        val (hexW, hexH) = computeHexSize(level.rows, level.cols, size.width, size.height)
                        drawHexGrid(gs.grid, level.rows, level.cols, hexW, hexH, gs.flashingCells)
                    }
                }
            }
        }
    }
}

private fun computeHexSize(rows: Int, cols: Int, canvasW: Float, canvasH: Float): Pair<Float, Float> {
    if (rows == 0 || cols == 0 || canvasW <= 0 || canvasH <= 0) return 40f to 34.64f
    val hexWByWidth = (canvasW - 10f) / (cols * 0.75f + 0.5f)
    val hexHByHeight = (canvasH - 10f) / (rows * sqrt(3.0) / 2 + sqrt(3.0) / 4).toFloat()
    val hexW = minOf(hexWByWidth, hexHByHeight * 2f / sqrt(3.0).toFloat(), 80f)
    return hexW to (sqrt(3.0) * hexW / 2).toFloat()
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
            val cx = offsetX + c * hexWidth * 0.75f + hexWidth / 2
            val cy = offsetY + r * hexHeight + (if (r % 2 == 1) hexHeight / 2 else 0f) + hexHeight / 2

            val colorIdx = cellValue.coerceIn(0, paletteColors.lastIndex)
            val fillColor = paletteColors[colorIdx]
            val strokeColor = fillColor.copy(alpha = 0.7f)

            val path = createHexPath(cx, cy, hexWidth / 2)
            if (isFlashing) {
                drawPath(path, fillColor.copy(alpha = 0.35f), style = Fill)
            } else {
                drawPath(path, fillColor, style = Fill)
            }
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
    canvasWidth: Float,
    canvasHeight: Float
): Pair<Int, Int>? {
    val hexHeight = (sqrt(3.0) * hexWidth / 2).toFloat()
    val offsetX = (canvasWidth - cols * hexWidth * 0.75f - hexWidth * 0.5f) / 2
    val offsetY = (canvasHeight - rows * hexHeight - hexHeight / 2) / 2
    val inRadius = hexWidth / 2 * cos(PI / 6).toFloat()
    val inRadiusSq = inRadius * inRadius

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val cx = offsetX + c * hexWidth * 0.75f + hexWidth / 2
            val cy = offsetY + r * hexHeight + (if (r % 2 == 1) hexHeight / 2 else 0f) + hexHeight / 2
            val dx = tap.x - cx
            val dy = tap.y - cy
            if (dx * dx + dy * dy <= inRadiusSq) {
                return r to c
            }
        }
    }
    return null
}
