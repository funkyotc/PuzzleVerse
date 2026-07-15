package com.funkyotc.puzzleverse.minesweeper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.minesweeper.viewmodel.MinesweeperViewModel
import com.funkyotc.puzzleverse.minesweeper.viewmodel.MinesweeperViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.funkyotc.puzzleverse.core.ui.animateEntrance
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.core.ui.animatePiecePlacement
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MinesweeperScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    viewModel: MinesweeperViewModel = viewModel(factory = MinesweeperViewModelFactory(streakRepository, mode))
) {
    val state by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
        }
    }

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            soundManager.playSound(SoundManager.SOUND_ID_FAILURE)
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Tap to reveal a cell. Long press to place a flag on suspected mines. If you hit a mine, game over!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isGameOver) {
        GameEndDialog(
            isWon = false,
            title = "Game Over",
            message = "You hit a mine!",
            mode = mode,
            onMainMenuClick = { navController.navigate("home") { popUpTo(0) } },
            onPlayAgainClick = { viewModel.startNewGame() }
        )
    }

    if (state.isWon) {
        GameEndDialog(
            isWon = true,
            title = "You Win!",
            message = "You found all the safe tiles!",
            mode = mode,
            onMainMenuClick = { navController.navigate("home") { popUpTo(0) } },
            onPlayAgainClick = { viewModel.startNewGame() }
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
        title = "Minesweeper",
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mines: ${state.totalMines}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Flags: ${state.flagsPlaced}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(state.cols.toFloat() / state.rows.toFloat())
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .border(2.dp, MaterialTheme.colorScheme.outlineVariant)
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
                                            .border(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                            .background(
                                                if (cell.isRevealed) MaterialTheme.colorScheme.surfaceVariant 
                                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                            )
                                            .animateEntrance(delayMillis = (r * state.cols + c) * 10)
                                            .combinedClickable(
                                                interactionSource = cellInteractionSource,
                                                indication = null,
                                                onClick = {
                                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                    if (cell.isRevealed) {
                                                        viewModel.revealNeighbors(r, c)
                                                    } else {
                                                        viewModel.revealCell(r, c)
                                                    }
                                                },
                                                onLongClick = {
                                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                    viewModel.toggleFlag(r, c)
                                                }
                                            )
                                            .animateTapFeedback(cellInteractionSource),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (cell.isRevealed) {
                                            if (cell.isMine) {
                                                Text("💣", fontSize = 16.sp, modifier = Modifier.animatePiecePlacement(trigger = cell.isRevealed))
                                            } else if (cell.neighboringMines > 0) {
                                                Text(
                                                    text = cell.neighboringMines.toString(),
                                                    color = getNumberColor(cell.neighboringMines),
                                                    fontWeight = FontWeight.Black,
                                                    fontSize = 20.sp,
                                                    modifier = Modifier.animatePiecePlacement(trigger = cell.isRevealed)
                                                )
                                            }
                                        } else if (cell.isFlagged) {
                                            Icon(
                                                Icons.Filled.Flag, 
                                                contentDescription = "Flagged", 
                                                tint = Color.Red, 
                                                modifier = Modifier.size(16.dp).animatePiecePlacement(trigger = cell.isFlagged)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getNumberColor(count: Int): Color {
    return when (count) {
        1 -> Color(0xFF1976D2) // Vibrant Blue
        2 -> Color(0xFF388E3C) // Vibrant Green
        3 -> Color(0xFFD32F2F) // Vibrant Red
        4 -> Color(0xFF7B1FA2) // Vibrant Purple
        5 -> Color(0xFFFF5722) // Vibrant Orange
        6 -> Color(0xFF0097A7) // Vibrant Teal
        7 -> Color(0xFFE91E63) // Vibrant Pink
        8 -> Color(0xFF4E342E) // Vibrant Brown
        else -> Color.Black
    }
}
