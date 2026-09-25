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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.CustomAccessibilityAction
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
import com.funkyotc.puzzleverse.pullpin.data.GameStatus
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.pullpin.data.WORLD_H
import com.funkyotc.puzzleverse.pullpin.data.WORLD_W
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository

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
    if (mode == "standard" && puzzleId == null) {
        com.funkyotc.puzzleverse.pullpin.rescue.RescuePrototypeScreen(navController)
        return
    }
    val soundManager = LocalSoundManager.current
    val context = androidx.compose.ui.platform.LocalContext.current

    val viewModel: PullPinViewModel = viewModel(
        factory = PullPinViewModelFactory(streakRepository, mode, puzzleId)
    )

    val state by viewModel.state.collectAsState()

    var showHowToPlay by remember { mutableStateOf(false) }
    var showWinDialog by remember { mutableStateOf(false) }
    var showLoseDialog by remember { mutableStateOf(false) }
    var prevInCup by remember { mutableStateOf(setOf<String>()) }
    var hint by remember { mutableStateOf<String?>(null) }
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, viewModel) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_STOP) viewModel.backgroundPaused = true
            if (event == androidx.lifecycle.Lifecycle.Event.ON_START) viewModel.backgroundPaused = false
        }
        viewModel.backgroundPaused = !lifecycleOwner.lifecycle.currentState.isAtLeast(androidx.lifecycle.Lifecycle.State.STARTED)
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer); viewModel.backgroundPaused = true }
    }
    LaunchedEffect(showHowToPlay, hint) { viewModel.paused = showHowToPlay || hint != null }
    if (hint != null) AlertDialog(onDismissRequest = { hint = null },
        title = { Text("A little nudge") }, text = { Text(hint!!) },
        confirmButton = { TextButton(onClick = { hint = null }) { Text("Got it") } })

    LaunchedEffect(context) {
        run {
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
            else -> { showWinDialog = false; showLoseDialog = false }
        }
    }

    LaunchedEffect(state?.balls?.count { it.captured }) {
        state?.let { s ->
            val nowInCup = s.balls.filter { it.inCup }.map { it.id }.toSet()
            val newInCup = nowInCup - prevInCup
            if (newInCup.isNotEmpty()) {
                soundManager.playSound(SoundManager.SOUND_ID_COIN_COLLECT)
            }
            prevInCup = nowInCup
        }
    }

    if (showHowToPlay) {
        GameHowToDialog(
            title = "How to Play",
            instructions = "Pull the Pin is a logic physics puzzle!\n\n• Tap on pin handles to pull them out.\n• Colored balls fall and roll under gravity.\n• Grey balls have no color. They must touch colored balls to gain color.\n• Guide all balls into their matching colored cups.\n• Avoid letting grey balls or wrong colors enter the cups!\n• Keep bombs sealed: you do not need to pull every pin.\n• Numbered locks open after you rescue enough balls.\n• Undo rewinds your last pull; Retry keeps the same board.",
            onDismiss = { showHowToPlay = false }
        )
    }

    state?.let { s ->
        val currentPuzzle = if (puzzleId != null) com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated.ALL_LEVELS.firstOrNull { it.id == puzzleId } else null
        val currentDifficulty = currentPuzzle?.difficulty
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val sameDiffPuzzles = if (currentPuzzle != null) com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated.ALL_LEVELS.filter { it.difficulty == currentPuzzle.difficulty } else com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated.ALL_LEVELS
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex + 1 < sameDiffPuzzles.size) sameDiffPuzzles[currentIndex + 1] else sameDiffPuzzles.firstOrNull()
            if (nextPuzzle != null) {
                {
                    showWinDialog = false
                    navController.navigate("game/pullpin/puzzle/${nextPuzzle.id}") {
                        popUpTo("home")
                    }
                }
            } else null
        } else null

        if (showWinDialog) {
            GameEndDialog(
                isWon = true,
                title = "Victory!",
                message = "All balls are safely in their cups!\nMoves: ${s.moves}",
                mode = mode,
                gameId = "pullpin",
                currentDifficulty = currentDifficulty,
                onMainMenuClick = {
                    showWinDialog = false
                    navController.navigate("home") { popUpTo(0) }
                },
                onBackToListClick = {
                    showWinDialog = false
                    val route = if (currentDifficulty != null) "pullpin/puzzles?difficulty=$currentDifficulty" else "pullpin/puzzles"
                    navController.navigate(route) { popUpTo("home") }
                },
                onPlayAgainClick = {
                    showWinDialog = false
                    prevInCup = emptySet()
                    if (mode == "daily") navController.navigate("game/pullpin/standard/new") { popUpTo("home") }
                    else viewModel.startNewGame()
                },
                onNextPuzzleClick = nextPuzzleAction
            )
        }
        if (showLoseDialog) {
            AlertDialog(
                onDismissRequest = { showLoseDialog = false },
                title = { Text("Try another approach") },
                text = { Text(s.lostReason ?: "Try a different pin order.") },
                confirmButton = { TextButton(onClick = { showLoseDialog = false; viewModel.undo() }) { Text("Undo last pull") } },
                dismissButton = { TextButton(onClick = { showLoseDialog = false; viewModel.retry() }) { Text("Retry level") } }
            )
        }
    }

    StandardGameLayout(
        title = "Pull the Pin",
        navController = navController,
        onHowToClick = { showHowToPlay = true },
        actions = {
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                prevInCup = emptySet()
                showLoseDialog = false
                showWinDialog = false
                viewModel.retry()
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        state?.let { gameState ->
            val safeIds = gameState.level.balls.filter { !it.isBomb }.map { it.id }.toSet()
            val ballsRemaining = gameState.balls.count { it.id in safeIds && !it.captured }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(gameState.level.title, style = MaterialTheme.typography.titleLarge)
                Text("${gameState.level.difficulty} · Level ${gameState.level.label}", style = MaterialTheme.typography.labelMedium)
                Text(gameState.level.lesson, style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Saved: ${safeIds.size - ballsRemaining}/${safeIds.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Moves: ${gameState.moves}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { showLoseDialog = false; viewModel.undo() }, enabled = viewModel.canUndo && gameState.status != GameStatus.WON) { Text("Undo") }
                    TextButton(onClick = { hint = viewModel.hint() }) { Text("Hint") }
                    TextButton(onClick = { viewModel.retry() }) { Text("Retry") }
                }
                PullPinColorLegend(
                    balls = gameState.balls.filter { it.id in safeIds },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                PullPinBoard(
                    state = gameState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onPinTap = { pinId ->
                        if (viewModel.removePin(pinId)) soundManager.playSound(SoundManager.SOUND_ID_METAL_SHING)
                        else gameState.pins.firstOrNull { it.id == pinId }?.let { pin ->
                            val needed = pin.unlockAfter - gameState.balls.count { it.captured }
                            if (needed > 0) hint = "Rescue $needed more balls to unlock this exit."
                        }
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
    val currentState by rememberUpdatedState(state)
    val currentTap by rememberUpdatedState(onPinTap)

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
                .semantics {
                    contentDescription = "${state.level.title}. ${state.balls.count { it.captured }} balls saved."
                    customActions = state.pins.filter { !it.removed && !it.isPulling }.map { pin ->
                        val chamber = pin.id.substringAfterLast('_').toIntOrNull()?.plus(1) ?: 1
                        CustomAccessibilityAction("${pin.label}, chamber $chamber" +
                            if (pin.unlockAfter > state.balls.count { it.captured }) ", locked" else "") {
                            currentTap(pin.id)
                            true
                        }
                    }
                }
                .pointerInput(scalePx, offsetX, offsetY) {
                    detectTapGestures { offset ->
                        val wx = (offset.x - offsetX) / scalePx
                        val wy = (offset.y - offsetY) / scalePx
                        val touchPad = with(density) { 24.dp.toPx() } / scalePx
                        val pin = currentState.pins.filter { p ->
                            !p.removed && !p.isPulling &&
                                wx >= p.x - touchPad && wx <= p.x + p.w + touchPad &&
                                kotlin.math.abs(wy - p.y - p.h / 2f) <= touchPad
                        }.minByOrNull { p ->
                            val dx = (wx - (p.x + p.w / 2f))
                            val dy = (wy - (p.y + p.h / 2f))
                            dx * dx + dy * dy
                        }
                        pin?.let { currentTap(it.id) }
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
    drawRoundRect(Color(0xFF111D32), size = Size(WORLD_W, WORLD_H), cornerRadius = CornerRadius(18f))
    val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }
    fun label(text: String, x: Float, y: Float, size: Float = 12f, color: Int = android.graphics.Color.WHITE) {
        paint.textSize = size
        paint.color = color
        drawContext.canvas.nativeCanvas.drawText(text, x, y, paint)
    }
    state.level.walls.forEach { w ->
        rotate(w.angle, Offset(w.x + w.w / 2f, w.y + w.h / 2f)) {
            drawRoundRect(Color(0xFF344861), Offset(w.x, w.y), Size(w.w, w.h), CornerRadius(3f))
            drawLine(Color(0xFF627A94), Offset(w.x, w.y), Offset(w.x + w.w, w.y), 2f)
        }
    }
    state.level.cups.forEach { cup ->
        val color = BALL_COLORS[cup.color] ?: Color.Gray
        val width = cup.radius * 2
        drawRoundRect(color.copy(alpha = .22f), Offset(cup.x - width / 2, cup.y - 24), Size(width, 48f), CornerRadius(10f))
        drawLine(color, Offset(cup.x - width / 2, cup.y - 24), Offset(cup.x - width / 2, cup.y + 24), 4f)
        drawLine(color, Offset(cup.x + width / 2, cup.y - 24), Offset(cup.x + width / 2, cup.y + 24), 4f)
        drawLine(color, Offset(cup.x - width / 2, cup.y + 24), Offset(cup.x + width / 2, cup.y + 24), 4f)
        val count = state.balls.count { it.captured && it.color == cup.color }
        label("${cup.color}  |  $count", cup.x, cup.y + 8, 17f)
    }
    val spawns = state.level.balls.associateBy { it.id }
    state.balls.filter { !it.captured && !it.outOfBounds }.forEach { b ->
        val spawn = spawns.getValue(b.id)
        val radius = spawn.radius
        val color = if (spawn.isBomb) Color(0xFF25232E) else BALL_COLORS[b.color] ?: Color.Gray
        drawCircle(Color.Black.copy(alpha = .3f), radius + 2f, Offset(b.x + 2, b.y + 3))
        drawCircle(color, radius, Offset(b.x, b.y))
        drawCircle(if (spawn.isBomb) Color(0xFFFF6875) else Color.White.copy(alpha = .55f), radius,
            Offset(b.x, b.y), style = androidx.compose.ui.graphics.drawscope.Stroke(1.5f))
        if (spawn.isBomb) {
            drawLine(Color(0xFFFF6875), Offset(b.x + 3, b.y - radius), Offset(b.x + 8, b.y - radius - 7), 3f)
            label("!", b.x, b.y + 5, 15f)
        } else label(if (b.color == 0) "?" else "${b.color}", b.x, b.y + 4, 11f, android.graphics.Color.BLACK)
    }
    val saved = state.balls.count { it.captured }
    state.pins.filter { !it.removed }.forEach { p ->
        val progress = pinProgress[p.id] ?: 0f
        val slide = Offset(p.pullDx, p.pullDy) * 70f * progress
        val origin = Offset(p.x, p.y) + slide
        val locked = saved < p.unlockAfter
        val danger = p.id.startsWith("hazard")
        val color = when { locked -> Color(0xFF9C8BDD); danger -> Color(0xFFFF6875); else -> Color(0xFFFFCA68) }
        drawRoundRect(color, origin, Size(p.w, p.h), CornerRadius(5f))
        val handle = Offset(if (p.pullDx < 0) origin.x else origin.x + p.w, origin.y + p.h / 2)
        drawCircle(Color(0xFF111D32), 12f, handle)
        drawCircle(color, 12f, handle, style = androidx.compose.ui.graphics.drawscope.Stroke(4f))
        label(if (locked) "${p.unlockAfter - saved}" else if (p.pullDx < 0) "<" else ">", handle.x, handle.y + 4, 12f)
        label(if (locked) "SAVE ${p.unlockAfter - saved}" else p.label.uppercase(), p.x + p.w / 2, p.y + 29, 10f)
    }
}
