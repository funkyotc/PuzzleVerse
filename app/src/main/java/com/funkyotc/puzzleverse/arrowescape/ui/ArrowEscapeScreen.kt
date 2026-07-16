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

    LaunchedEffect(uiState.isComplete) {
        if (uiState.isComplete) {
            settingsRepository.addWin()
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Tap the arrows to send them flying off the screen. Arrows can only move if their path is completely unblocked.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (uiState.isComplete) {
        GameEndDialog(
            isWon = true,
            title = "Puzzle Cleared!",
            message = "You have successfully untangled all the arrows.",
            mode = mode,
            onMainMenuClick = {
                navController.navigate("home") { popUpTo(0) }
            },
            onPlayAgainClick = {
                // Wait for an integration to load a new puzzle. For now, pop back to home.
                navController.navigate("home") { popUpTo(0) }
            }
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
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ArrowEscapeGrid(
                arrows = uiState.arrows,
                gridWidth = uiState.gridWidth,
                gridHeight = uiState.gridHeight,
                onArrowTapped = { arrowId, onBump, onMove ->
                    viewModel.onArrowTapped(arrowId, onBump, onMove)
                }
            )
        }
    }
}
