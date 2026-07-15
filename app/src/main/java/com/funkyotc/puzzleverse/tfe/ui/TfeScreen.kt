package com.funkyotc.puzzleverse.tfe.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.tfe.data.Direction
import com.funkyotc.puzzleverse.tfe.data.Tile
import com.funkyotc.puzzleverse.tfe.viewmodel.TfeViewModel
import com.funkyotc.puzzleverse.tfe.viewmodel.TfeViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import kotlin.math.abs
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.core.ui.animateEntrance
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.core.ui.PuzzleVerseAnimationSpecs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TfeScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    viewModel: TfeViewModel = viewModel(factory = TfeViewModelFactory(streakRepository, mode))
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
            instructions = "Swipe to move tiles. Tiles with the same number merge into one when they touch. Get to 2048!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isGameOver) {
        GameEndDialog(
            isWon = false,
            title = "Game Over",
            message = "No more moves left. Score: ${state.score}",
            mode = mode,
            onMainMenuClick = { navController.navigate("home") { popUpTo(0) } },
            onPlayAgainClick = { viewModel.startNewGame() }
        )
    }

    if (state.isWon) {
        GameEndDialog(
            isWon = true,
            title = "You Win!",
            message = "You reached 2048! Score: ${state.score}",
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
        title = "2048",
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
            Text(
                text = "Score: ${state.score}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Grid Box
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .animateEntrance()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFBBADA0))
                    .padding(8.dp)
                    .pointerInput(Unit) {
                        var swipeDirection: Direction? = null
                        detectDragGestures(
                            onDragEnd = {
                                swipeDirection?.let {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    viewModel.move(it)
                                }
                                swipeDirection = null
                            },
                        ) { change, dragAmount ->
                            change.consume()
                            if (swipeDirection == null) {
                                val (x, y) = dragAmount
                                if (abs(x) > abs(y)) {
                                    if (abs(x) > 20) swipeDirection = if (x > 0) Direction.RIGHT else Direction.LEFT
                                } else {
                                    if (abs(y) > 20) swipeDirection = if (y > 0) Direction.DOWN else Direction.UP
                                }
                            }
                        }
                    }
            ) {
                // Background grid
                val margin = 8.dp
                val tileSpace = (maxWidth - margin * 3) / 4
                
                for (r in 0..3) {
                    for (c in 0..3) {
                        Box(
                            modifier = Modifier
                                .size(tileSpace)
                                .offset(x = (tileSpace + margin) * c, y = (tileSpace + margin) * r)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFCDC1B4))
                        )
                    }
                }
                
                // Animated tiles
                state.tiles.forEach { tile ->
                    key(tile.id) {
                        AnimatedTile(tile = tile, tileSize = tileSpace, margin = margin)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedTile(tile: Tile, tileSize: Dp, margin: Dp) {
    val targetX = (tileSize + margin) * tile.col
    val targetY = (tileSize + margin) * tile.row

    val x by animateDpAsState(targetValue = targetX, animationSpec = PuzzleVerseAnimationSpecs.fastMovementSpec(), label = "x")
    val y by animateDpAsState(targetValue = targetY, animationSpec = PuzzleVerseAnimationSpecs.fastMovementSpec(), label = "y")
    
    var visibleScale by remember { mutableStateOf(if (tile.isNew) 0.1f else 1f) }
    
    LaunchedEffect(tile.isNew) {
        if (tile.isNew) {
            visibleScale = 1f
        }
    }
    
    LaunchedEffect(tile.isMerged) {
        if (tile.isMerged) {
            visibleScale = 1.2f
            kotlinx.coroutines.delay(100)
            visibleScale = 1f
        }
    }
    
    val scale by animateFloatAsState(targetValue = visibleScale, animationSpec = tween(durationMillis = 100), label = "scale")

    Box(
        modifier = Modifier
            .size(tileSize)
            .offset(x = x, y = y)
            .scale(scale)
            .clip(RoundedCornerShape(4.dp))
            .background(getTileColor(tile.value)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = tile.value.toString(),
            fontSize = if (tile.value > 1000) 24.sp else 32.sp,
            fontWeight = FontWeight.Bold,
            color = if (tile.value <= 4) Color(0xFF776E65) else Color.White
        )
    }
}

fun getTileColor(value: Int): Color {
    return when (value) {
        0 -> Color(0xFFCDC1B4)
        2 -> Color(0xFFEEE4DA)
        4 -> Color(0xFFEDE0C8)
        8 -> Color(0xFFF2B179)
        16 -> Color(0xFFF59563)
        32 -> Color(0xFFF67C5F)
        64 -> Color(0xFFF65E3B)
        128 -> Color(0xFFEDCF72)
        256 -> Color(0xFFEDCC61)
        512 -> Color(0xFFEDC850)
        1024 -> Color(0xFFEDC53F)
        2048 -> Color(0xFFEDC22E)
        else -> Color(0xFF3C3A32)
    }
}
