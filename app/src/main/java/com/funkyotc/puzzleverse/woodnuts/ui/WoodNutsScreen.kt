package com.funkyotc.puzzleverse.woodnuts.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModel
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModelFactory

private val PLANK_PALETTE = listOf(
    Color(0xFF8D6E63), Color(0xFFA1887F), Color(0xFFBCAAA4),
    Color(0xFF6D4C41), Color(0xFF5D4037), Color(0xFF4E342E),
    Color(0xFFD7CCC8), Color(0xFFEFEBE9), Color(0xFF795548),
    Color(0xFF8B6F5E), Color(0xFFA0897C), Color(0xFFB8A69B)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoodNutsScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: WoodNutsViewModel = viewModel(
        factory = WoodNutsViewModelFactory(streakRepository, mode, puzzleId)
    )
) {
    val stateOpt by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showVictoryDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Wood Nuts") }

    val state = stateOpt ?: return

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
            showVictoryDialog = true
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = {
                Text(
                    "Wooden planks are held together by bolts. " +
                    "Tap a bolt to unscrew it. " +
                    "When the last bolt holding a plank is removed, the plank falls off. " +
                    "Planks falling may expose other bolts and trigger chain reactions. " +
                    "Remove all planks to win!"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHowToDialog = false
                }) {
                    Text("Got it")
                }
            }
        )
    }

    if (showVictoryDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Victory!") },
            text = { Text("All planks removed in ${state.moves} moves!") },
            confirmButton = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (mode == "puzzle") {
                        Button(
                            onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                showVictoryDialog = false
                                navController.popBackStack()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Back to Browser")
                        }

                        val nextId = puzzleId?.let { id ->
                            val allPuzzles = WoodNutsPregenerated.ALL_LEVELS
                            val currentIndex = allPuzzles.indexOfFirst { it.id == id }
                            if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) {
                                allPuzzles[currentIndex + 1].id
                            } else {
                                null
                            }
                        }

                        if (nextId != null) {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    showVictoryDialog = false
                                    navController.popBackStack()
                                    navController.navigate("game/woodnuts/puzzle/$nextId")
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Next Puzzle")
                            }
                        }
                    } else {
                        Button(
                            onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                showVictoryDialog = false
                                viewModel.startNewGame()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Play Again")
                        }

                        Button(
                            onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                showVictoryDialog = false
                                viewModel.startNewGame()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("New Game")
                        }
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wood Nuts & Bolts") },
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
                        Icon(Icons.Filled.Info, contentDescription = "How to Play")
                    }
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.startNewGame()
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Restart")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Difficulty: ${state.level.difficulty}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )

                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val density = LocalDensity.current
                    val rows = state.level.rows
                    val cols = state.level.cols
                    val intersectionsCols = cols + 1
                    val intersectionsRows = rows + 1

                    val cellDp = minOf(
                        maxWidth / intersectionsCols,
                        maxHeight / intersectionsRows
                    )

                    val cellPx = with(density) { cellDp.toPx() }
                    val gridWidthDp = cellDp * intersectionsCols
                    val gridHeightDp = cellDp * intersectionsRows

                    val boltRadius = cellPx * 0.18f
                    val plankCorner = cellPx * 0.12f
                    val plankPaddingPx = cellPx * 0.08f
                    val strokePx = with(density) { 2.dp.toPx() }

                    val removedPlankIds = remember { mutableStateMapOf<String, Float>() }

                    LaunchedEffect(state.lastRemovedPlankId) {
                        state.lastRemovedPlankId?.let { id ->
                            if (id !in removedPlankIds) {
                                val anim = Animatable(1f)
                                removedPlankIds[id] = 1f
                                anim.animateTo(0f, animationSpec = tween(400))
                                removedPlankIds[id] = 0f
                            }
                        }
                    }

                    Canvas(
                        modifier = Modifier
                            .size(gridWidthDp, gridHeightDp)
                            .pointerInput(state.bolts) {
                                detectTapGestures { offset ->
                                    val col = (offset.x / cellPx).toInt()
                                    val row = (offset.y / cellPx).toInt()
                                    val bolt = state.bolts.find {
                                        it.row == row && it.col == col && !it.removed
                                    }
                                    if (bolt != null) {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK, 0.8f)
                                        viewModel.removeBolt(bolt.id)
                                    }
                                }
                            }
                    ) {
                        for (plank in state.planks) {
                            val alpha = if (plank.removed) {
                                removedPlankIds[plank.id] ?: 0f
                            } else {
                                1f
                            }
                            if (alpha <= 0f) continue

                            val colorIndex = state.planks.indexOf(plank) % PLANK_PALETTE.size
                            val left = (plank.startCol + plankPaddingPx * 0f) * cellPx
                            val top = (plank.startRow + plankPaddingPx * 0f) * cellPx
                            val w = (plank.endCol - plank.startCol + 1) * cellPx - plankPaddingPx * 2f
                            val h = (plank.endRow - plank.startRow + 1) * cellPx - plankPaddingPx * 2f

                            drawRoundRect(
                                color = PLANK_PALETTE[colorIndex].copy(alpha = alpha),
                                topLeft = Offset(left + plankPaddingPx, top + plankPaddingPx),
                                size = Size(w, h),
                                cornerRadius = CornerRadius(plankCorner, plankCorner)
                            )

                            if (!plank.removed) {
                                drawRoundRect(
                                    color = PLANK_PALETTE[colorIndex].copy(alpha = 0.3f),
                                    topLeft = Offset(left + plankPaddingPx, top + plankPaddingPx),
                                    size = Size(w, h),
                                    cornerRadius = CornerRadius(plankCorner, plankCorner),
                                    style = Stroke(width = strokePx)
                                )
                            }
                        }

                        for (bolt in state.bolts) {
                            if (bolt.removed) continue

                            val cx = bolt.col * cellPx
                            val cy = bolt.row * cellPx

                            drawCircle(
                                color = Color(0xFF37474F),
                                radius = boltRadius,
                                center = Offset(cx, cy)
                            )
                            drawCircle(
                                color = Color(0xFF78909C),
                                radius = boltRadius * 0.7f,
                                center = Offset(cx, cy)
                            )
                            drawCircle(
                                color = Color(0xFFB0BEC5),
                                radius = boltRadius * 0.4f,
                                center = Offset(cx, cy)
                            )
                        }
                    }
                }

                Text(
                    text = "Moves: ${state.moves}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}
