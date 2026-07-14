package com.funkyotc.puzzleverse.chess.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.R
import com.funkyotc.puzzleverse.chess.data.ChessPieceUiModel
import com.funkyotc.puzzleverse.chess.data.PieceColor
import com.funkyotc.puzzleverse.chess.data.PieceType
import com.funkyotc.puzzleverse.chess.viewmodel.ChessViewModel
import com.funkyotc.puzzleverse.chess.viewmodel.ChessViewModelFactory
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
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
                val squareSize = boardSize / 8
                val lightSquareColor = Color(0xFFF0D9B5)
                val darkSquareColor = Color(0xFFB58863)
                val selectedColor = Color(0xFFEEEED2).copy(alpha = 0.5f)
                val legalMoveColor = Color(0x6600AA00)

                val legalMoves = if (state.selectedRow >= 0 && state.selectedCol >= 0) {
                    viewModel.getLegalMoveSquares(state.selectedRow, state.selectedCol)
                } else {
                    emptyList()
                }

                Box(modifier = Modifier.size(boardSize)) {
                    // Draw Board and Highlights
                    Column(modifier = Modifier.fillMaxSize()) {
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

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .background(if (isLight) lightSquareColor else darkSquareColor)
                                            .then(
                                                if (isSelected) Modifier.background(selectedColor)
                                                else Modifier
                                            )
                                            .clickable {
                                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                viewModel.onSquareClicked(row, col)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isLegalMove) {
                                            Box(
                                                modifier = Modifier
                                                    .size(squareSize * 0.35f)
                                                    .clip(CircleShape)
                                                    .background(legalMoveColor)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Draw Animated Pieces
                    state.pieces.forEach { piece ->
                        val animatedOffsetX by animateDpAsState(
                            targetValue = squareSize * piece.col,
                            animationSpec = tween(durationMillis = 300),
                            label = "pieceX_${piece.id}"
                        )
                        val animatedOffsetY by animateDpAsState(
                            targetValue = squareSize * piece.row,
                            animationSpec = tween(durationMillis = 300),
                            label = "pieceY_${piece.id}"
                        )

                        val alpha by androidx.compose.animation.core.animateFloatAsState(
                            targetValue = if (piece.isCaptured) 0f else 1f,
                            animationSpec = tween(durationMillis = 200),
                            label = "pieceAlpha_${piece.id}"
                        )
                        val scale by androidx.compose.animation.core.animateFloatAsState(
                            targetValue = if (piece.isCaptured) 0f else 1f,
                            animationSpec = tween(durationMillis = 200),
                            label = "pieceScale_${piece.id}"
                        )

                        if (alpha > 0f) {
                            Box(
                                modifier = Modifier
                                    .offset(x = animatedOffsetX, y = animatedOffsetY)
                                    .size(squareSize)
                                    .zIndex(if (piece.row == state.selectedRow && piece.col == state.selectedCol) 10f else 1f)
                                    .alpha(alpha)
                                    .scale(scale)
                            ) {
                                Image(
                                    painter = painterResource(id = getDrawableResId(piece)),
                                    contentDescription = "${piece.color} ${piece.type}",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getDrawableResId(piece: ChessPieceUiModel): Int {
    return when (piece.color) {
        PieceColor.WHITE -> when (piece.type) {
            PieceType.KING -> R.drawable.ic_chess_white_king
            PieceType.QUEEN -> R.drawable.ic_chess_white_queen
            PieceType.ROOK -> R.drawable.ic_chess_white_rook
            PieceType.BISHOP -> R.drawable.ic_chess_white_bishop
            PieceType.KNIGHT -> R.drawable.ic_chess_white_knight
            PieceType.PAWN -> R.drawable.ic_chess_white_pawn
        }
        PieceColor.BLACK -> when (piece.type) {
            PieceType.KING -> R.drawable.ic_chess_black_king
            PieceType.QUEEN -> R.drawable.ic_chess_black_queen
            PieceType.ROOK -> R.drawable.ic_chess_black_rook
            PieceType.BISHOP -> R.drawable.ic_chess_black_bishop
            PieceType.KNIGHT -> R.drawable.ic_chess_black_knight
            PieceType.PAWN -> R.drawable.ic_chess_black_pawn
        }
    }
}
