package com.funkyotc.puzzleverse.flowfree.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.flowfree.data.FlowDifficulty
import com.funkyotc.puzzleverse.flowfree.data.Point
import com.funkyotc.puzzleverse.flowfree.viewmodel.FlowFreeViewModel
import com.funkyotc.puzzleverse.flowfree.viewmodel.FlowFreeViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowFreeScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: FlowFreeViewModel = viewModel(factory = FlowFreeViewModelFactory(streakRepository, mode, puzzleId))
) {
    val soundManager = LocalSoundManager.current
    val state by viewModel.state.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val currentDifficulty by viewModel.difficulty.collectAsState()
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Connect matching colors with pipes to create a flow. Pair all colors, and cover the entire board to solve each puzzle. Pipes cannot branch or cross each other.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val currentPuzzle = com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated.getPuzzleById(puzzleId)
            val sameSizePuzzles = if (currentPuzzle != null) {
                com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated.ALL_PUZZLES
                    .filter { it.size == currentPuzzle.size && it.difficulty == currentPuzzle.difficulty }
            } else emptyList()
            val currentIndex = sameSizePuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex < sameSizePuzzles.size - 1) {
                sameSizePuzzles[currentIndex + 1]
            } else null

            if (nextPuzzle != null) {
                {
                    navController.navigate("game/flowfree/puzzle/${nextPuzzle.id}") {
                        popUpTo("flowfree/puzzles")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "You Win!",
            message = "You connected all flows and filled the grid!",
            mode = mode,
            onMainMenuClick = {
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = {
                if (mode == "daily") {
                    navController.navigate("game/flowfree/standard/new") { popUpTo("home") }
                } else {
                    viewModel.startNewGame()
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Game",
            message = "Are you sure you want to start over?",
            onConfirm = {
                viewModel.startNewGame()
                showNewGameDialog = false
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Flow Free",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily" && mode != "puzzle") { { showNewGameDialog = true } } else null,
        actions = {
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                viewModel.undo()
            }) {
                Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo")
            }
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                viewModel.restartLevel()
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Difficulty indicator chip
            if (mode == "daily" || puzzleId != null) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    val labelPrefix = if (mode == "daily") "Daily" else "Puzzle"
                    Text(
                        text = "$labelPrefix • ${currentDifficulty.label} • ${state.rows}×${state.cols}",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Grid size label
            Text(
                text = "${state.rows}×${state.cols} • ${state.dots.size} flows",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (isGenerating) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Generating puzzle...", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                var gridWidth by remember { mutableStateOf(0f) }
                var gridHeight by remember { mutableStateOf(0f) }
                var activeColorId by remember { mutableStateOf<Int?>(null) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(state.cols.toFloat() / state.rows.toFloat())
                        .background(Color.Black)
                        .onGloballyPositioned { coordinates ->
                            gridWidth = coordinates.size.width.toFloat()
                            gridHeight = coordinates.size.height.toFloat()
                        }
                        .pointerInput(gridWidth) { // Re-init if layout changes
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val cellPxW = gridWidth / state.cols.toFloat()
                                    val cellPxH = gridHeight / state.rows.toFloat()
                                    val r = (offset.y / cellPxH).toInt()
                                    val c = (offset.x / cellPxW).toInt()

                                    val dot = state.dots.find { it.start == Point(r, c) || it.end == Point(r, c) }
                                    if (dot != null) {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        activeColorId = dot.colorId
                                        viewModel.startPath(dot.colorId, r, c)
                                    } else {
                                        // Resume path from middle
                                        val path = state.paths.find { it.path.contains(Point(r, c)) }
                                        if (path != null) {
                                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                            activeColorId = path.colorId
                                        }
                                    }
                                },
                                onDragEnd = { activeColorId = null },
                                onDragCancel = { activeColorId = null }
                            ) { change, _ ->
                                change.consume()
                                val offset = change.position
                                val cellPxW = gridWidth / state.cols.toFloat()
                                val cellPxH = gridHeight / state.rows.toFloat()
                                val r = (offset.y / cellPxH).toInt()
                                val c = (offset.x / cellPxW).toInt()

                                if (r in 0 until state.rows && c in 0 until state.cols && activeColorId != null) {
                                    viewModel.extendPath(activeColorId!!, r, c)
                                }
                            }
                        }
                ) {
                    // Background grid lines
                    Column(modifier = Modifier.fillMaxSize()) {
                        for (r in 0 until state.rows) {
                            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                for (c in 0 until state.cols) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .border(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                    )
                                }
                            }
                        }
                    }

                    // Draw Dots and Paths
                    if (gridWidth > 0f && gridHeight > 0f) {
                        val cellW = gridWidth / state.cols
                        val cellH = gridHeight / state.rows

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Draw paths
                            for (pathObj in state.paths) {
                                val color = getColor(pathObj.colorId)
                                val pathPts = pathObj.path

                                for (i in 0 until pathPts.size - 1) {
                                    val p1 = pathPts[i]
                                    val p2 = pathPts[i + 1]

                                    val x1 = (p1.c * cellW) + (cellW / 2)
                                    val y1 = (p1.r * cellH) + (cellH / 2)
                                    val x2 = (p2.c * cellW) + (cellW / 2)
                                    val y2 = (p2.r * cellH) + (cellH / 2)

                                    drawLine(
                                        color = color,
                                        start = Offset(x1, y1),
                                        end = Offset(x2, y2),
                                        strokeWidth = cellW * 0.4f,
                                        cap = StrokeCap.Round
                                    )
                                }
                            }

                            // Draw dots
                            for (dot in state.dots) {
                                val color = getColor(dot.colorId)
                                val r1 = dot.start.r
                                val c1 = dot.start.c
                                drawCircle(
                                    color = color,
                                    radius = cellW * 0.35f,
                                    center = Offset(c1 * cellW + cellW / 2, r1 * cellH + cellH / 2)
                                )

                                val r2 = dot.end.r
                                val c2 = dot.end.c
                                drawCircle(
                                    color = color,
                                    radius = cellW * 0.35f,
                                    center = Offset(c2 * cellW + cellW / 2, r2 * cellH + cellH / 2)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getColor(id: Int): Color {
    return when (id) {
        1 -> Color.Red
        2 -> Color.Blue
        3 -> Color.Green
        4 -> Color.Yellow
        5 -> Color(0xFFFFA500) // Orange
        6 -> Color.Cyan
        7 -> Color.Magenta
        8 -> Color(0xFF800000) // Maroon
        9 -> Color(0xFF008080) // Teal
        else -> Color.White
    }
}
