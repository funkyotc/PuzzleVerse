package com.funkyotc.puzzleverse.kakuro.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.kakuro.data.CellType
import com.funkyotc.puzzleverse.kakuro.data.KakuroCell
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModel
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import androidx.compose.ui.platform.LocalContext
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.core.ui.animateEntrance
import com.funkyotc.puzzleverse.core.ui.animatePiecePlacement
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakuroScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: KakuroViewModel = viewModel(factory = KakuroViewModelFactory(streakRepository, mode, puzzleId))
) {
    val soundManager = LocalSoundManager.current
    val state by viewModel.state.collectAsState()
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Kakuro") }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Fill white cells with numbers 1-9. Numbers in a run cannot repeat, and must sum up to the clue numbers. The top-right number is the horizontal sum, bottom-left is vertical.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val diffPuzzles = com.funkyotc.puzzleverse.kakuro.data.KakuroPregenerated.PUZZLES_BY_DIFFICULTY.values.flatten()
            val currentIndex = diffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < diffPuzzles.size) diffPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    navController.popBackStack()
                    navController.navigate("game/kakuro/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "You Win!",
            message = "You solved the Kakuro puzzle!",
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
                    navController.navigate("game/kakuro/standard/new") { popUpTo("home") }
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
        title = "Kakuro",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            // Grid
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(state.cols.toFloat() / state.rows.toFloat())
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        for (r in 0 until state.rows) {
                            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                for (c in 0 until state.cols) {
                                    val cell = state.grid[r][c]
                                    val cellInteractionSource = remember { MutableInteractionSource() }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .border(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                                            .animateEntrance(delayMillis = (r * state.cols + c) * 15)
                                            .then(
                                                if (cell.type == CellType.PLAYER_INPUT) {
                                                    Modifier.clickable(
                                                        interactionSource = cellInteractionSource,
                                                        indication = null
                                                    ) {
                                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                        selectedCell = Pair(r, c)
                                                    }.animateTapFeedback(cellInteractionSource)
                                                } else Modifier
                                            )
                                    ) {
                                        KakuroCellView(cell, isSelected = selectedCell == Pair(r, c))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Numpad
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (col in 1..3) {
                            val num = row * 3 + col
                            val interactionSource = remember { MutableInteractionSource() }
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_TILE_PLACE)
                                    selectedCell?.let { (r, c) ->
                                        viewModel.setCellValue(r, c, num)
                                    }
                                },
                                interactionSource = interactionSource,
                                modifier = Modifier
                                    .size(64.dp)
                                    .animateTapFeedback(interactionSource)
                            ) {
                                Text(num.toString(), fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KakuroCellView(cell: KakuroCell, isSelected: Boolean) {
    if (cell.type == CellType.BLACK) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant))
    } else if (cell.type == CellType.CLUE) {
        val lineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        val hasHorizontal = cell.clue?.horizontalSum != null
        val hasVertical = cell.clue?.verticalSum != null
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f))
        ) {
            if (hasHorizontal && hasVertical) {
                // Both clues present: Draw diagonal split line
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    drawLine(
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(canvasWidth, canvasHeight),
                        color = lineColor,
                        strokeWidth = 2f
                    )
                }
                Text(
                    text = cell.clue?.horizontalSum.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd).padding(end = 4.dp, top = 2.dp)
                )
                Text(
                    text = cell.clue?.verticalSum.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 4.dp, bottom = 2.dp)
                )
            } else if (hasHorizontal) {
                // Only horizontal sum: Single number on the right side with arrow
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 6.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${cell.clue?.horizontalSum} ▶",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            } else if (hasVertical) {
                // Only vertical sum: Single number at the bottom side with arrow
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 4.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${cell.clue?.verticalSum}\n▼",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        lineHeight = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    else MaterialTheme.colorScheme.surface
                ),
            contentAlignment = Alignment.Center
        ) {
            if (cell.playerValue != null) {
                Text(
                    text = cell.playerValue.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.animatePiecePlacement(trigger = cell.playerValue)
                )
            }
        }
    }
}
