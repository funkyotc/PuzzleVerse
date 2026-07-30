package com.funkyotc.puzzleverse.arrowescape.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ArrowEscapeScreen(
    navController: NavController,
    mode: String?,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    puzzleId: String? = null,
    viewModel: ArrowEscapeViewModel = viewModel { 
        ArrowEscapeViewModel(streakRepository, settingsRepository, mode ?: "standard", puzzleId) 
    }
) {
    val uiState by viewModel.uiState.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val completionRepo = remember { com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository(context, "Arrow Escape") }

    LaunchedEffect(uiState.isComplete) {
        if (uiState.isComplete) {
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Tap the arrows to send them flying off the screen. Arrows can only move if their path is completely unblocked.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (uiState.isComplete) {
        val currentPuzzle = if (puzzleId != null) com.funkyotc.puzzleverse.arrowescape.data.ArrowEscapePregenerated.ALL_PUZZLES.firstOrNull { it.id == puzzleId } else null
        val currentDifficulty = currentPuzzle?.difficulty
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val sameDiffPuzzles = if (currentPuzzle != null) com.funkyotc.puzzleverse.arrowescape.data.ArrowEscapePregenerated.ALL_PUZZLES.filter { it.difficulty == currentPuzzle.difficulty } else com.funkyotc.puzzleverse.arrowescape.data.ArrowEscapePregenerated.ALL_PUZZLES
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex + 1 < sameDiffPuzzles.size) sameDiffPuzzles[currentIndex + 1] else sameDiffPuzzles.firstOrNull()
            if (nextPuzzle != null) {
                {
                    navController.navigate("game/arrowescape/puzzle/${nextPuzzle.id}") {
                        popUpTo("home")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Puzzle Cleared!",
            message = "You have successfully untangled all the arrows.",
            mode = mode,
            gameId = "arrowescape",
            currentDifficulty = currentDifficulty,
            onMainMenuClick = {
                navController.navigate("home") { popUpTo(0) }
            },
            onBackToListClick = {
                val route = if (currentDifficulty != null) "arrowescape/puzzles?difficulty=$currentDifficulty" else "arrowescape/puzzles"
                navController.navigate(route) { popUpTo("home") }
            },
            onPlayAgainClick = {
                if (mode == "daily") {
                    navController.navigate("game/arrowescape/standard") { popUpTo("home") }
                } else {
                    viewModel.resetPuzzle()
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    StandardGameLayout(
        title = "Arrow Escape",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {
            val undoInteractionSource = remember { MutableInteractionSource() }
            IconButton(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.undo()
                },
                modifier = Modifier.animateTapFeedback(undoInteractionSource),
                interactionSource = undoInteractionSource
            ) {
                Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            ArrowEscapeGrid(
                arrows = uiState.arrows,
                gridWidth = uiState.gridWidth,
                gridHeight = uiState.gridHeight,
                shape = uiState.shape,
                onArrowTapped = { arrowId, onBump, onMove ->
                    soundManager.playSound(SoundManager.SOUND_ID_PIECE_SLIDE)
                    viewModel.onArrowTapped(arrowId, onBump, onMove)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
