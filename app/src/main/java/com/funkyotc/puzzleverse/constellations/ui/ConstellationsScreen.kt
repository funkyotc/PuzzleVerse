package com.funkyotc.puzzleverse.constellations.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.viewmodel.ConstellationsViewModel
import com.funkyotc.puzzleverse.constellations.viewmodel.ConstellationsViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import androidx.compose.runtime.LaunchedEffect
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstellationsScreen(
    navController: NavController, 
    mode: String? = "standard",
    puzzleId: String? = null,
    settingsRepository: SettingsRepository,
    streakRepository: StreakRepository,
    constellationsViewModel: ConstellationsViewModel = viewModel(factory = ConstellationsViewModelFactory(mode, puzzleId))
) {
    val puzzle by constellationsViewModel.puzzle.collectAsState()
    val soundManager = LocalSoundManager.current
    val isGameWon by constellationsViewModel.isGameWon.collectAsState()
    val elapsedSeconds by constellationsViewModel.elapsedSeconds.collectAsState()
    val moves by constellationsViewModel.moves.collectAsState()
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHintDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val completionRepo = remember { com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository(context, "constellations") }

    if (showHintDialog) {
        GameConfirmDialog(
            title = "Use a Hint?",
            message = "Are you sure you want to use a hint to reveal part of the puzzle?",
            confirmLabel = "Yes",
            cancelLabel = "Cancel",
            onConfirm = {
                showHintDialog = false
                constellationsViewModel.hint()
            },
            onDismiss = { showHintDialog = false }
        )
    }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            } else if (mode == "daily") {
                val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                val streak = streakRepository.getStreak("constellations")
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
        GameHowToDialog(
            instructions = "Place one star in every row, column, and colored region. Stars cannot touch each other, not even diagonally.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (isGameWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val currentPuzzle = com.funkyotc.puzzleverse.constellations.data.ConstellationsPregenerated.getPuzzleById(puzzleId)
            val sameDiffPuzzles = if (currentPuzzle != null) {
                com.funkyotc.puzzleverse.constellations.data.ConstellationsPregenerated.ALL_PUZZLES
                    .filter { it.difficulty == currentPuzzle.difficulty }
            } else emptyList()
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex < sameDiffPuzzles.size - 1) {
                sameDiffPuzzles[currentIndex + 1]
            } else null
            if (nextPuzzle != null) {
                {
                    navController.navigate("game/constellations/puzzle/${nextPuzzle.id}") {
                        popUpTo("constellations/puzzles")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Congratulations!",
            message = "You solved the puzzle!",
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
                    navController.navigate("game/constellations/standard/new") { popUpTo("home") }
                } else {
                    constellationsViewModel.loadNewPuzzle()
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }
    
    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Puzzle",
            message = "Are you sure you want to start a new puzzle? Your current progress will be lost.",
            onConfirm = {
                constellationsViewModel.loadNewPuzzle()
                showNewGameDialog = false
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Constellations",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null,
        actions = {
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                showHintDialog = true
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Hint")
            }
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                constellationsViewModel.errorCheck()
            }) {
                Icon(Icons.Filled.Warning, contentDescription = "Check Errors")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val timeFormatted = String.format(java.util.Locale.ROOT, "%02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60)
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Time: $timeFormatted")
                Text(text = "Moves: $moves")
            }
            puzzle?.let { p ->
                val regionColors = rememberRegionColors(p.regions.keys)
                var gridSize by remember { mutableStateOf(IntSize.Zero) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .onGloballyPositioned { coordinates ->
                            gridSize = coordinates.size
                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val cellSize = gridSize.width / p.size.toFloat()
                                val col = (offset.x / cellSize).toInt()
                                val row = (offset.y / cellSize).toInt()
                                if (row in 0 until p.size && col in 0 until p.size) {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    constellationsViewModel.onCellClicked(row, col)
                                }
                            }
                        }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val cellSize = gridSize.width / p.size.toFloat()
                                    val col = (offset.x / cellSize).toInt()
                                    val row = (offset.y / cellSize).toInt()
                                    if (row in 0 until p.size && col in 0 until p.size) {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        constellationsViewModel.onDragStart(row, col)
                                    }
                                },
                                onDrag = { change, _ ->
                                    val offset = change.position
                                    val cellSize = gridSize.width / p.size.toFloat()
                                    val col = (offset.x / cellSize).toInt()
                                    val row = (offset.y / cellSize).toInt()
                                    if (row in 0 until p.size && col in 0 until p.size) {
                                        constellationsViewModel.onDragOver(row, col)
                                    }
                                }
                            )
                        }
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        p.cells.forEach { row ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                row.forEach { cell ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .background(regionColors[cell.regionId] ?: Color.LightGray)
                                            .border(1.dp, Color.Black),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        when (cell.state) {
                                            CellState.STAR -> Icon(
                                                Icons.Filled.Star,
                                                contentDescription = "Star",
                                                tint = if (cell.isError) Color.Red else Color.Black
                                            )
                                            CellState.CROSS -> Icon(
                                                Icons.Filled.Close,
                                                contentDescription = "Cross",
                                                tint = if (cell.isAuto) Color.DarkGray else Color(0xFFD32F2F)
                                            )
                                            CellState.EMPTY -> {}
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

@Composable
fun rememberRegionColors(regionIds: Set<Int>): Map<Int, Color> {
    val colors = listOf(
        Color(0xFFE57373), Color(0xFF81C784), Color(0xFF64B5F6), Color(0xFFFFD54F),
        Color(0xFFBA68C8), Color(0xFF4DB6AC), Color(0xFFFF8A65), Color(0xFFA1887F),
        Color(0xFF90A4AE), Color(0xFFF06292)
    )
    return regionIds.zip(colors + colors + colors).toMap()
}
