package com.funkyotc.puzzleverse.pullpin.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.pullpin.data.BallRuntime
import com.funkyotc.puzzleverse.pullpin.data.CupData
import com.funkyotc.puzzleverse.pullpin.data.GameStatus
import com.funkyotc.puzzleverse.pullpin.data.PinData
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.pullpin.data.WallSegment
import com.funkyotc.puzzleverse.pullpin.data.WORLD_H
import com.funkyotc.puzzleverse.pullpin.data.WORLD_W
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.launch

private val BALL_COLORS = mapOf(
    0 to Color(0xFFB0BEC5), // Grey / Uncolored
    1 to Color(0xFFE53935), // Red
    2 to Color(0xFF1E88E5), // Blue
    3 to Color(0xFF43A047), // Green
    4 to Color(0xFFFDD835), // Yellow
    5 to Color(0xFF8E24AA), // Purple
    6 to Color(0xFFFB8C00), // Orange
    7 to Color(0xFF00ACC1), // Cyan
    8 to Color(0xFFD81B60)  // Pink
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullPinScreen(
    navController: NavController,
    mode: String?,
    streakRepository: StreakRepository?,
    settingsRepository: SettingsRepository?,
    puzzleId: String? = null,
    forceNewGame: Boolean = false
) {
    val soundManager = LocalSoundManager.current
    val context = androidx.compose.ui.platform.LocalContext.current

    val viewModel: PullPinViewModel = viewModel(
        factory = PullPinViewModelFactory(streakRepository, mode, puzzleId)
    )

    val state by viewModel.state.collectAsState()

    var showHowToPlay by remember { mutableStateOf(false) }
    var showWinDialog by remember { mutableStateOf(false) }
    var showLoseDialog by remember { mutableStateOf(false) }
    var prevInCup by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(streakRepository) {
        streakRepository?.let {
            viewModel.setCompletionRepo(
                PuzzleCompletionRepository(context, "PullPin")
            )
        }
    }

    LaunchedEffect(state?.status) {
        when (state?.status) {
            GameStatus.WON -> {
                showWinDialog = true
                soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            }
            GameStatus.LOST -> {
                showLoseDialog = true
                soundManager.playSound(SoundManager.SOUND_ID_FAILURE)
            }
            else -> {}
        }
    }

    LaunchedEffect(state?.moves) {
        state?.let { s ->
            val nowInCup = s.balls.filter { it.inCup }.map { it.color }.toSet()
            val newInCup = nowInCup - prevInCup
            if (newInCup.isNotEmpty()) {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
            }
            prevInCup = nowInCup
        }
    }

    if (showHowToPlay) {
        GameHowToDialog(
            title = "How to Play",
            instructions = "Pull the Pin is a logic physics puzzle!\n\n• Tap on pin handles to pull them out.\n• Colored balls fall and roll under gravity.\n• Grey balls have no color. They must touch colored balls to gain color.\n• Guide all balls into their matching colored cups.\n• Avoid letting grey balls or wrong colors enter the cups!",
            onDismiss = { showHowToPlay = false }
        )
    }

    state?.let { s ->
        if (showWinDialog) {
            GameEndDialog(
                isWon = true,
                title = "Victory!",
                message = "All balls are safely in their cups!\nMoves: ${s.moves}",
                mode = mode,
                onMainMenuClick = {
                    showWinDialog = false
                    navController.popBackStack()
                },
                onPlayAgainClick = {
                    showWinDialog = false
                    prevInCup = emptySet()
                    viewModel.startNewGame()
                }
            )
        }
        if (showLoseDialog) {
            GameEndDialog(
                isWon = false,
                title = "Defeat!",
                message = "${s.lostReason ?: "You ran out of pins or balls got stuck."}\n\nTap Retry to try again!",
                mode = mode,
                onMainMenuClick = {
                    showLoseDialog = false
                    navController.popBackStack()
                },
                onPlayAgainClick = {
                    showLoseDialog = false
                    prevInCup = emptySet()
                    viewModel.startNewGame()
                }
            )
        }
    }

    StandardGameLayout(
        title = state?.let { "Pull the Pin (${it.level.difficulty})" } ?: "Pull the Pin",
        navController = navController,
        onHowToClick = { showHowToPlay = true },
        actions = {
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                prevInCup = emptySet()
                showLoseDialog = false
                showWinDialog = false
                viewModel.startNewGame()
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        state?.let { gameState ->
            val ballsRemaining = gameState.balls.count { !it.captured }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Balls: $ballsRemaining/${gameState.balls.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Moves: ${gameState.moves}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                PullPinColorLegend(
                    balls = gameState.balls,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                PullPinBoard(
                    state = gameState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onPinTap = { pinId ->
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.removePin(pinId)
                    }
                )
            }
        }
    }
}

@Composable
private fun PullPinColorLegend(
    balls: List<BallRuntime>,
    modifier: Modifier = Modifier
) {
    val activeColors = balls.map { it.color }.distinct().filter { it != 0 }.sorted()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        activeColors.forEach { colorIdx ->
            val inCup = balls.any { it.color == colorIdx && it.inCup }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background((BALL_COLORS[colorIdx] ?: Color.Gray).copy(alpha = if (inCup) 0.4f else 1f))
                        .border(
                            1.dp,
                            (BALL_COLORS[colorIdx] ?: Color.Gray).copy(alpha = if (inCup) 0.4f else 1f),
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "${colorIdx}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (inCup) 0.4f else 1f)
                )
                if (inCup) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = BALL_COLORS[colorIdx] ?: Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun PullPinBoard(
    state: PullPinState,
    modifier: Modifier = Modifier,
    onPinTap: (String) -> Unit
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val maxHeightPx = with(density) { maxHeight.toPx() }

        val scalePx = minOf(maxWidthPx / WORLD_W, maxHeightPx / WORLD_H)
        val offsetX = (maxWidthPx - WORLD_W * scalePx) / 2f
        val offsetY = (maxHeightPx - WORLD_H * scalePx) / 2f

        val pinProgress = state.pins.associate { p ->
            val target = if (p.isPulling) 1f else 0f
            p.id to animateFloatAsState(
                targetValue = target,
                animationSpec = tween(220),
                label = "pin_pull_${p.id}"
            ).value
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val wx = (offset.x - offsetX) / scalePx
                        val wy = (offset.y - offsetY) / scalePx
                        val pin = state.pins.firstOrNull { p ->
                            !p.removed && !p.isPulling &&
                                wx >= p.x && wx <= p.x + p.w &&
                                wy >= p.y && wy <= p.y + p.h
                        }
                        pin?.let { onPinTap(it.id) }
                    }
                }
        ) {
            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                translate(offsetX, offsetY) {
                    scale(scalePx, pivot = Offset.Zero) {
                        drawWorld(state, pinProgress)
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawWorld(state: PullPinState, pinProgress: Map<String, Float>) {
    with(this) {
        state.level.walls.forEach { w: WallSegment ->
            drawRect(
                color = Color(0xFF37474F),
                topLeft = Offset(w.x, w.y),
                size = Size(w.w, w.h)
            )
        }

        state.level.cups.forEach { cup: CupData ->
            val cupColor = BALL_COLORS[cup.color] ?: Color.Gray
            drawCircle(
                color = cupColor.copy(alpha = 0.25f),
                radius = cup.radius,
                center = Offset(cup.x, cup.y)
            )
            drawCircle(
                color = cupColor,
                radius = cup.radius,
                center = Offset(cup.x, cup.y),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
            )
        }

        state.balls.forEach { b: BallRuntime ->
            if (b.outOfBounds) return@forEach
            val radius = if (b.captured) 14f * 0.7f else 14f
            drawCircle(
                color = BALL_COLORS[b.color] ?: Color.Gray,
                radius = radius,
                center = Offset(b.x, b.y)
            )
            drawCircle(
                color = Color.Black.copy(alpha = 0.35f),
                radius = radius,
                center = Offset(b.x, b.y),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
            )
        }

        state.pins.forEach { p: PinData ->
            if (p.removed) return@forEach
            val progress = pinProgress[p.id] ?: 0f
            val slide = Offset(p.pullDx, p.pullDy) * 60f * progress
            val topLeft = Offset(p.x + slide.x, p.y + slide.y)
            drawRoundRect(
                color = Color(0xFFB0BEC5),
                topLeft = topLeft,
                size = Size(p.w, p.h),
                cornerRadius = CornerRadius(p.h / 2f)
            )
            val handleX = topLeft.x + p.w - p.h / 2f
            val handleY = topLeft.y + p.h / 2f
            drawCircle(
                color = Color(0xFF546E7A),
                radius = p.h * 0.45f,
                center = Offset(handleX, handleY)
            )
        }
    }
}
