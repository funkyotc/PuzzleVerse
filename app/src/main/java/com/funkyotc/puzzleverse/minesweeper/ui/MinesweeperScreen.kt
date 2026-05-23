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

@OptIn(ExperimentalMaterial3Api::class)
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
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Tap to reveal a cell. Long press to place a flag on suspected mines. If you hit a mine, game over!") },
            confirmButton = { TextButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                showHowToDialog = false }) { Text("OK") } }
        )
    }

    if (state.isGameOver) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Game Over") },
            text = { Text("You hit a mine!") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("home") { popUpTo(0) }
                        }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/minesweeper/standard/new") { popUpTo("home") }
                        }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else {

                Button(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.startNewGame()
                }) { Text("Try Again") }

                }
            }
        )
    }

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("You Win!") },
            text = { Text("You found all the safe tiles!") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("home") { popUpTo(0) }
                        }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/minesweeper/standard/new") { popUpTo("home") }
                        }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else {

                Button(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.startNewGame()
                }) { Text("Play Again") }

                }
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
                title = { Text("Minesweeper") },
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
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .border(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                            .background(
                                                if (cell.isRevealed) MaterialTheme.colorScheme.surfaceVariant 
                                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                            )
                                            .pointerInput(Unit) {
                                                detectTapGestures(
                                                    onTap = {
                                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                        viewModel.revealCell(r, c)
                                                    },
                                                    onLongPress = {
                                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                        viewModel.toggleFlag(r, c)
                                                    }
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (cell.isRevealed) {
                                            if (cell.isMine) {
                                                Text("💣", fontSize = 16.sp)
                                            } else if (cell.neighboringMines > 0) {
                                                Text(
                                                    text = cell.neighboringMines.toString(),
                                                    color = getNumberColor(cell.neighboringMines),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp
                                                )
                                            }
                                        } else if (cell.isFlagged) {
                                            Icon(Icons.Filled.Flag, contentDescription = "Flagged", tint = Color.Red, modifier = Modifier.size(16.dp))
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
        1 -> Color.Blue
        2 -> Color(0xFF388E3C) // Dark Green
        3 -> Color.Red
        4 -> Color(0xFF303F9F) // Dark Blue
        5 -> Color(0xFF7B1FA2) // Purple
        6 -> Color(0xFF0097A7) // Cyan
        7 -> Color.Black
        8 -> Color.DarkGray
        else -> Color.Black
    }
}
