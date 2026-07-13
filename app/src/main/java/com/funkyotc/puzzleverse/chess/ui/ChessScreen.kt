package com.funkyotc.puzzleverse.chess.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.chess.data.chessPieceToUnicode
import com.funkyotc.puzzleverse.chess.viewmodel.ChessViewModel
import com.funkyotc.puzzleverse.chess.viewmodel.ChessViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChessScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    forceNewGame: Boolean = false,
    viewModel: ChessViewModel = viewModel(
        factory = ChessViewModelFactory(streakRepository, mode, puzzleId)
    )
) {
    val state by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Chess") }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    LaunchedEffect(forceNewGame) {
        if (forceNewGame) {
            viewModel.startNewGame()
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Find the checkmate move! Tap your piece, then tap the destination square to make your move. Solve the puzzle by achieving checkmate in the specified number of moves.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = com.funkyotc.puzzleverse.chess.data.ChessPregenerated.ALL_PUZZLES
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) allPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    navController.popBackStack()
                    navController.navigate("game/chess/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Checkmate!",
            message = "You found the solution!\n${state.message}",
            mode = mode,
            onMainMenuClick = {
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = { viewModel.startNewGame() },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Puzzle",
            message = "Start a new checkmate puzzle?",
            onConfirm = {
                viewModel.startNewGame()
                showNewGameDialog = false
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Chess Puzzles",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {
            if (mode != "daily") {
                IconButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showNewGameDialog = true
                }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "New Puzzle")
                }
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
            Text(
                text = state.message,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Difficulty: ${state.difficulty}  -  Attempts: ${state.moveAttempts}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val boardSize = minOf(maxWidth, maxHeight)
                val lightSquareColor = Color(0xFFF0D9B5)
                val darkSquareColor = Color(0xFFB58863)
                val selectedColor = Color(0xFFEEEED2)
                val legalMoveColor = Color(0x6600AA00)

                val legalMoves = if (state.selectedRow >= 0 && state.selectedCol >= 0) {
                    viewModel.getLegalMoveSquares(state.selectedRow, state.selectedCol)
                } else {
                    emptyList()
                }

                Column(
                    modifier = Modifier.size(boardSize)
                ) {
                    for (row in 0..7) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            for (col in 0..7) {
                                val isLight = (row + col) % 2 == 0
                                val isSelected = state.selectedRow == row && state.selectedCol == col
                                val isLegalMove = legalMoves.any { it.first == row && it.second == col }
                                val piece = viewModel.getPieceAt(row, col)

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(if (isLight) lightSquareColor else darkSquareColor)
                                        .then(
                                            if (isSelected) Modifier.background(selectedColor.copy(alpha = 0.5f))
                                            else Modifier
                                        )
                                        .clickable {
                                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                            viewModel.onSquareClicked(row, col)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (piece != null) {
                                        Text(
                                            text = chessPieceToUnicode(piece),
                                            fontSize = (boardSize.value / 8 * 0.7f).sp,
                                            color = if (piece.color == com.funkyotc.puzzleverse.chess.data.PieceColor.WHITE)
                                                Color.White else Color.Black
                                        )
                                    }
                                    if (isLegalMove) {
                                        Box(
                                            modifier = Modifier
                                                .size(boardSize / 8 * 0.35f)
                                                .clip(CircleShape)
                                                .background(legalMoveColor)
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
