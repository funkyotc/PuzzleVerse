package com.funkyotc.puzzleverse.pullpin.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.pullpin.data.Cell
import com.funkyotc.puzzleverse.pullpin.data.CellType
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.pullpin.data.GameStatus
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

private val COLOR_LABELS = mapOf(
    0 to "Grey",
    1 to "Red", 2 to "Blue", 3 to "Green", 4 to "Yellow",
    5 to "Purple", 6 to "Orange", 7 to "Cyan", 8 to "Pink"
)

private fun ballColor(index: Int): Color = BALL_COLORS[index] ?: Color.Gray
private fun colorLabel(index: Int): String = COLOR_LABELS[index] ?: "?"

private fun getBallLightColor(colorIdx: Int): Color {
    return when (colorIdx) {
        0 -> Color(0xFFECEFF1)
        1 -> Color(0xFFFF8A80)
        2 -> Color(0xFF82B1FF)
        3 -> Color(0xFFB9F6CA)
        4 -> Color(0xFFFFFF8D)
        5 -> Color(0xFFEA80FC)
        6 -> Color(0xFFFFD180)
        7 -> Color(0xFF84FFFF)
        8 -> Color(0xFFFF80AB)
        else -> Color.White
    }
}

private fun getBallDarkColor(colorIdx: Int): Color {
    return when (colorIdx) {
        0 -> Color(0xFF455A64)
        1 -> Color(0xFFB71C1C)
        2 -> Color(0xFF0D47A1)
        3 -> Color(0xFF1B5E20)
        4 -> Color(0xFFF57F17)
        5 -> Color(0xFF4A148C)
        6 -> Color(0xFFE65100)
        7 -> Color(0xFF006064)
        8 -> Color(0xFF880E4F)
        else -> Color.Black
    }
}

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
    val context = LocalContext.current

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

    PullPinDialogHowToPlay(showHowToPlay) { showHowToPlay = false }

    state?.let { s ->
        if (showWinDialog) {
            PullPinWinDialog(
                state = s,
                onDismiss = { showWinDialog = false },
                onNextPuzzle = {
                    showWinDialog = false
                    prevInCup = emptySet()
                    viewModel.startNewGame()
                },
                onBackToBrowser = {
                    showWinDialog = false
                    navController.popBackStack()
                }
            )
        }
        if (showLoseDialog) {
            PullPinLoseDialog(
                state = s,
                onDismiss = { showLoseDialog = false },
                onRetry = {
                    showLoseDialog = false
                    prevInCup = emptySet()
                    viewModel.startNewGame()
                },
                onBackToBrowser = {
                    showLoseDialog = false
                    navController.popBackStack()
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Pull the Pin")
                        state?.let { s ->
                            Text(
                                text = "${s.level.difficulty} • ${s.level.subtitle}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToPlay = true }) {
                        Text("?", fontSize = 18.sp)
                    }
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
            )
        }
    ) { paddingValues ->
        state?.let { gameState ->
            val ballsRemaining = gameState.balls.count { !it.inCup }

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
                    onPinClick = { row, col ->
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.removePin(row, col)
                    }
                )
            }
        }
    }
}

@Composable
private fun PullPinColorLegend(
    balls: List<com.funkyotc.puzzleverse.pullpin.data.BallState>,
    modifier: Modifier = Modifier
) {
    val activeColors = balls.map { it.color }.distinct().sorted()
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
                        .background(ballColor(colorIdx).copy(alpha = if (inCup) 0.4f else 1f))
                        .border(
                            1.dp,
                            ballColor(colorIdx).copy(alpha = if (inCup) 0.4f else 1f),
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "$colorIdx:${colorLabel(colorIdx)}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (inCup) 0.4f else 1f)
                )
                if (inCup) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = ballColor(colorIdx)
                    )
                }
            }
        }
    }
}

@Composable
private fun PullPinDrawing(
    size: Dp,
    slideOffset: Float,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .graphicsLayer(translationX = slideOffset * size.value * 1.5f)
            .clickable(enabled = slideOffset == 0f, onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        // Needle Shaft
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(4.dp)
                .background(Color(0xFF78909C), RoundedCornerShape(2.dp))
        )
        // Loop Handle
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(size * 0.4f)
                .border(3.dp, Color(0xFF546E7A), CircleShape)
                .background(Color(0xFF90A4AE), CircleShape)
        )
    }
}

@Composable
private fun PullPinBoard(
    state: PullPinState,
    modifier: Modifier = Modifier,
    onPinClick: (Int, Int) -> Unit
) {
    val rows = state.level.rows
    val cols = state.level.cols

    BoxWithConstraints(
        modifier = modifier.aspectRatio(cols.toFloat() / rows.toFloat()),
        contentAlignment = Alignment.Center
    ) {
        val cellWidth = maxWidth / cols
        val cellHeight = maxHeight / rows
        val ballSize = minOf(cellWidth, cellHeight) * 0.72f
        val pinSize = minOf(cellWidth, cellHeight)

        // 1. Draw static grid cells (WALL, PIN, CUP, EMPTY)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (r in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (c in 0 until cols) {
                        val cell = state.grid[r][c]
                        val pulled = "$r,$c" in state.removedPins
                        
                        Box(
                            modifier = Modifier.size(cellWidth, cellHeight),
                            contentAlignment = Alignment.Center
                        ) {
                            if (cell.type == CellType.WALL) {
                                Box(
                                    Modifier
                                        .size(cellWidth * 0.95f, cellHeight * 0.95f)
                                        .shadow(2.dp, RoundedCornerShape(4.dp))
                                        .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp))
                                )
                            }
                            
                            if (cell.type == CellType.CUP) {
                                val cupColor = ballColor(cell.color ?: 1)
                                Box(
                                    modifier = Modifier
                                        .size(cellWidth * 0.9f, cellHeight * 0.9f)
                                        .border(
                                            border = androidx.compose.foundation.BorderStroke(3.dp, cupColor),
                                            shape = RoundedCornerShape(
                                                topStart = 0.dp, topEnd = 0.dp,
                                                bottomStart = 8.dp, bottomEnd = 8.dp
                                            )
                                        )
                                        .clip(RoundedCornerShape(
                                            topStart = 0.dp, topEnd = 0.dp,
                                            bottomStart = 8.dp, bottomEnd = 8.dp
                                        )),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${cell.color ?: ""}",
                                        color = cupColor,
                                        fontSize = (cellHeight.value * 0.3f).sp,
                                        fontWeight = FontWeight.Bold,
                                        softWrap = false
                                    )
                                }
                            }

                            // Render Pin sliding out if it was pulled, or normally if not pulled
                            val slideOffset = remember { Animatable(0f) }
                            var isPinPulled by remember { mutableStateOf(pulled) }
                            
                            LaunchedEffect(pulled) {
                                if (pulled && !isPinPulled) {
                                    isPinPulled = true
                                    slideOffset.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(durationMillis = 350, easing = FastOutLinearInEasing)
                                    )
                                }
                            }

                            if (cell.type == CellType.PIN) {
                                PullPinDrawing(
                                    size = pinSize,
                                    slideOffset = 0f,
                                    onClick = { onPinClick(r, c) }
                                )
                            } else if (pulled && slideOffset.value < 1f) {
                                PullPinDrawing(
                                    size = pinSize,
                                    slideOffset = slideOffset.value,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Draw active balls as overlays with smooth position animations
        state.balls.forEach { ball ->
            // If ball fell out of bounds, don't draw it anymore
            if (ball.row >= rows) return@forEach

            val animRow by animateFloatAsState(
                targetValue = ball.row.toFloat(),
                animationSpec = tween(durationMillis = 110, easing = LinearEasing),
                label = "row_${ball.color}"
            )
            val animCol by animateFloatAsState(
                targetValue = ball.col.toFloat(),
                animationSpec = tween(durationMillis = 110, easing = LinearEasing),
                label = "col_${ball.color}"
            )

            // Scale down when inside cup
            val scale by animateFloatAsState(
                targetValue = if (ball.inCup) 0.6f else 1.0f,
                animationSpec = tween(durationMillis = 150),
                label = "scale_${ball.color}"
            )

            val baseColor = ballColor(ball.color)
            
            Box(
                modifier = Modifier
                    .size(ballSize)
                    .graphicsLayer(
                        translationX = animCol * cellWidth.value + (cellWidth.value - ballSize.value) / 2,
                        translationY = animRow * cellHeight.value + (cellHeight.value - ballSize.value) / 2,
                        scaleX = scale,
                        scaleY = scale
                    )
                    .shadow(if (ball.inCup) 1.dp else 4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                getBallLightColor(ball.color),
                                baseColor,
                                getBallDarkColor(ball.color)
                            ),
                            center = Offset(0.3f, 0.3f)
                        )
                    )
                    .border(1.dp, baseColor.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (ball.color > 0) {
                    Text(
                        text = "${ball.color}",
                        color = Color.White,
                        fontSize = (ballSize.value * 0.35f).sp,
                        fontWeight = FontWeight.Bold,
                        softWrap = false
                    )
                }
            }
        }
    }
}

@Composable
private fun PullPinDialogHowToPlay(visible: Boolean, onDismiss: () -> Unit) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("How to Play") },
            text = {
                Column {
                    Text("Pull the Pin is a logic physics puzzle!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Tap on pin handles to pull them out.")
                    Text("• Colored balls fall and roll under gravity.")
                    Text("• Grey balls have no color. They must touch colored balls to gain color.")
                    Text("• Guide all balls into their matching colored cups.")
                    Text("• Avoid letting grey balls or wrong colors enter the cups!")
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Got it!")
                }
            }
        )
    }
}

@Composable
private fun PullPinWinDialog(
    state: PullPinState,
    onDismiss: () -> Unit,
    onNextPuzzle: () -> Unit,
    onBackToBrowser: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Victory!") },
        text = {
            Column {
                Text("All balls are safely in their cups!")
                Text("Moves: ${state.moves}")
            }
        },
        confirmButton = {
            Button(onClick = onNextPuzzle) {
                Text("Next Puzzle")
            }
        },
        dismissButton = {
            TextButton(onClick = onBackToBrowser) {
                Text("Back")
            }
        }
    )
}

@Composable
private fun PullPinLoseDialog(
    state: PullPinState,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    onBackToBrowser: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Defeat!") },
        text = {
            Column {
                Text(state.lostReason ?: "You ran out of pins or balls got stuck.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Tap Retry to try again!")
            }
        },
        confirmButton = {
            Button(onClick = onRetry) {
                Text("Retry")
            }
        },
        dismissButton = {
            TextButton(onClick = onBackToBrowser) {
                Text("Back")
            }
        }
    )
}
