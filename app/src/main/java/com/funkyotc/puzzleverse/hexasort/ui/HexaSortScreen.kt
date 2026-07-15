package com.funkyotc.puzzleverse.hexasort.ui

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.funkyotc.puzzleverse.core.ui.PuzzleVerseAnimationSpecs
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortEvent
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortViewModel
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.abs
import kotlin.random.Random
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

private val paletteColors = listOf(
    Color(0xFFE53935),
    Color(0xFF43A047),
    Color(0xFF1E88E5),
    Color(0xFFFDD835),
    Color(0xFF8E24AA),
    Color(0xFFFF6F00)
)

fun Color.lighten(factor: Float = 0.25f): Color {
    return Color(
        red = (red + (1f - red) * factor).coerceIn(0f, 1f),
        green = (green + (1f - green) * factor).coerceIn(0f, 1f),
        blue = (blue + (1f - blue) * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}

fun Color.darken(factor: Float = 0.25f): Color {
    return Color(
        red = (red * (1f - factor)).coerceIn(0f, 1f),
        green = (green * (1f - factor)).coerceIn(0f, 1f),
        blue = (blue * (1f - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )
}

class VisualTile(
    val color: Int,
    var row: Int,
    var col: Int,
    val offsetY: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val scale: Animatable<Float, AnimationVector1D> = Animatable(1f),
    val alpha: Animatable<Float, AnimationVector1D> = Animatable(1f),
    val offsetX: Animatable<Float, AnimationVector1D> = Animatable(0f)
)

data class HexParticle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val color: Color,
    var alpha: Float,
    val size: Float,
    var life: Float
)

data class HexComboText(
    val text: String,
    val x: Float,
    val y: Float,
    val color: Color,
    var currentYOffset: Float,
    var alpha: Float,
    var life: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HexaSortScreen(
    navController: NavController,
    mode: String? = "standard",
    forceNewGame: Boolean = false,
    puzzleId: String? = null,
    settingsRepository: SettingsRepository,
    streakRepository: StreakRepository,
    context: Context = LocalContext.current,
    viewModel: HexaSortViewModel = viewModel(
        factory = HexaSortViewModelFactory(context, mode, forceNewGame, streakRepository, puzzleId)
    )
) {
    val state by viewModel.state.collectAsState()
    val elapsedSeconds by viewModel.elapsedSeconds.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showVictoryDialog by remember { mutableStateOf(false) }
    var showGameOverDialog by remember { mutableStateOf(false) }
    var victoryScore by remember { mutableStateOf(0) }
    var streakUpdated by remember { mutableStateOf(false) }

    val activeTiles = remember { mutableMapOf<Pair<Int, Int>, VisualTile>() }
    val disappearingTiles = remember { mutableStateListOf<VisualTile>() }
    val particles = remember { mutableStateListOf<HexParticle>() }
    val comboTexts = remember { mutableStateListOf<HexComboText>() }
    val coroutineScope = rememberCoroutineScope()
    var canvasSize by remember { mutableStateOf(androidx.compose.ui.unit.IntSize.Zero) }
    var isShufflingUI by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HexaSortEvent.Won -> {
                    victoryScore = event.score
                    showVictoryDialog = true
                    settingsRepository.addWin()
                    soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
                    if (mode == "puzzle" && puzzleId != null) {
                        com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository(context, "HexaSort").markCompleted(puzzleId)
                    } else if (mode == "daily" && !streakUpdated) {
                        streakUpdated = true
                        val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                        val streak = streakRepository.getStreak("hexasort")
                        if (streak.lastCompletedEpochDay != today) {
                            val newStreak = streak.copy(
                                count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                                lastCompletedEpochDay = today
                            )
                            streakRepository.saveStreak(newStreak)
                        }
                    }
                }
                is HexaSortEvent.GameOver -> {
                    showGameOverDialog = true
                    soundManager.playSound(SoundManager.SOUND_ID_FAILURE)
                }
            }
        }
    }

    LaunchedEffect(state?.grid, canvasSize) {
        val gs = state ?: return@LaunchedEffect
        val grid = gs.grid
        val level = gs.level
        val rows = level.rows
        val cols = level.cols
        if (canvasSize.width == 0 || canvasSize.height == 0) return@LaunchedEffect

        val cW = canvasSize.width.toFloat()
        val cH = canvasSize.height.toFloat()

        val (hexW, _) = computeHexSize(rows, cols, cW, cH)
        val R = hexW / sqrt(3.0).toFloat()
        val dx = sqrt(3.0).toFloat() * R
        val dy = 1.5f * R
        val gridWidth = (cols + 0.5f) * dx
        val gridHeight = (1.5f * rows + 0.5f) * R
        val offsetX = (cW - gridWidth) / 2 + dx / 2
        val offsetY = (cH - gridHeight) / 2 + R

        if (gs.moves == 0 || activeTiles.isEmpty() || isShufflingUI) {
            val isInitial = activeTiles.isEmpty() && !isShufflingUI
            activeTiles.clear()
            disappearingTiles.clear()
            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    val colorIdx = grid[r][c]
                    if (colorIdx != null) {
                        activeTiles[r to c] = VisualTile(
                            color = colorIdx,
                            row = r,
                            col = c,
                            offsetY = Animatable(0f),
                            scale = Animatable(if (isShufflingUI || isInitial) 0f else 1f),
                            alpha = Animatable(if (isShufflingUI || isInitial) 0f else 1f)
                        )
                        if (isShufflingUI) {
                            coroutineScope.launch {
                                launch { activeTiles[r to c]?.scale?.animateTo(1f, tween(250)) }
                                launch { activeTiles[r to c]?.alpha?.animateTo(1f, tween(250)) }
                            }
                        } else if (isInitial) {
                            val delayVal = (r * cols + c) * 30L
                            coroutineScope.launch {
                                kotlinx.coroutines.delay(delayVal)
                                launch { activeTiles[r to c]?.scale?.animateTo(1f, PuzzleVerseAnimationSpecs.fastMovementSpec()) }
                                launch { activeTiles[r to c]?.alpha?.animateTo(1f, PuzzleVerseAnimationSpecs.fastTweenSpec(180)) }
                            }
                        }
                    }
                }
            }
            if (isShufflingUI) {
                isShufflingUI = false
            }
        } else {
            val flashing = gs.flashingCells
            if (flashing.isNotEmpty()) {
                flashing.forEach { coord ->
                    val tile = activeTiles.remove(coord)
                    if (tile != null) {
                        disappearingTiles.add(tile)
                        coroutineScope.launch {
                            tile.scale.animateTo(0f, tween(200))
                            disappearingTiles.remove(tile)
                        }
                        coroutineScope.launch {
                            tile.alpha.animateTo(0f, tween(200))
                        }

                        val cx = offsetX + coord.second * dx + (if (coord.first % 2 == 1) dx / 2 else 0f)
                        val cy = offsetY + coord.first * dy
                        val baseColor = paletteColors[tile.color.coerceIn(0, paletteColors.lastIndex)]
                        repeat(10) {
                            val angle = Random.nextFloat() * 2 * PI.toFloat()
                            val speed = Random.nextFloat() * 4f + 1f
                            particles.add(
                                HexParticle(
                                    x = cx,
                                    y = cy,
                                    vx = cos(angle) * speed,
                                    vy = sin(angle) * speed - 1.5f,
                                    color = baseColor,
                                    alpha = 1f,
                                    size = Random.nextFloat() * 6f + 3f,
                                    life = 1f
                                )
                            )
                        }
                    }
                }

                if (flashing.size >= 5) {
                    val avgX = flashing.map { it.second }.average().toFloat()
                    val avgR = flashing.map { it.first }.average().toFloat()
                    val cx = offsetX + avgX * dx + (if (avgR.toInt() % 2 == 1) dx / 2 else 0f)
                    val cy = offsetY + avgR * dy
                    val tileColor = flashing.firstNotNullOfOrNull { grid[it.first][it.second] }
                        ?: flashing.firstNotNullOfOrNull { disappearingTiles.find { dt -> dt.row == it.first && dt.col == it.second }?.color }
                        ?: 0
                    val baseColor = paletteColors[tileColor.coerceIn(0, paletteColors.lastIndex)]
                    comboTexts.add(
                        HexComboText(
                            text = "${flashing.size} Hex Combo!",
                            x = cx,
                            y = cy - 20f,
                            color = baseColor,
                            currentYOffset = 0f,
                            alpha = 1f,
                            life = 1f
                        )
                    )
                }
            } else {
                val newActive = mutableMapOf<Pair<Int, Int>, VisualTile>()
                for (c in 0 until cols) {
                    val remainingOldTiles = mutableListOf<VisualTile>()
                    for (r in rows - 1 downTo 0) {
                        val tile = activeTiles[r to c]
                        if (tile != null) {
                            remainingOldTiles.add(tile)
                        }
                    }

                    val newOccupiedRows = mutableListOf<Int>()
                    for (r in rows - 1 downTo 0) {
                        if (grid[r][c] != null) {
                            newOccupiedRows.add(r)
                        }
                    }

                    for (i in 0 until minOf(remainingOldTiles.size, newOccupiedRows.size)) {
                        val tile = remainingOldTiles[i]
                        val oldRow = tile.row
                        val newRow = newOccupiedRows[i]

                        tile.row = newRow
                        newActive[newRow to c] = tile

                        if (oldRow != newRow) {
                            val startOffsetPx = (oldRow - newRow) * dy
                            coroutineScope.launch {
                                tile.offsetY.snapTo(startOffsetPx)
                                tile.offsetY.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }
                    }
                }
                activeTiles.clear()
                activeTiles.putAll(newActive)
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                val iterator = particles.iterator()
                while (iterator.hasNext()) {
                    val p = iterator.next()
                    p.x += p.vx
                    p.y += p.vy
                    p.vy += 0.2f
                    p.life -= 0.03f
                    p.alpha = p.life.coerceIn(0f, 1f)
                    if (p.life <= 0f) {
                        iterator.remove()
                    }
                }

                val textIterator = comboTexts.iterator()
                while (textIterator.hasNext()) {
                    val t = textIterator.next()
                    t.currentYOffset -= 1f
                    t.life -= 0.02f
                    t.alpha = t.life.coerceIn(0f, 1f)
                    if (t.life <= 0f) {
                        textIterator.remove()
                    }
                }
            }
        }
    }

    val onShuffleClick = {
        if (state != null) {
            val gs = state!!
            if (!isShufflingUI && !gs.isWon && !gs.isGameOver && gs.shufflesRemaining > 0 && gs.flashingCells.isEmpty()) {
                isShufflingUI = true
                coroutineScope.launch {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    val jobs = activeTiles.values.map { tile ->
                        launch {
                            tile.scale.animateTo(0f, tween(150))
                            tile.alpha.animateTo(0f, tween(150))
                        }
                    }
                    jobs.joinAll()
                    viewModel.shuffleGrid()
                }
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Tap groups of 2+ same-colored hexes to clear them. Clear all hexes to win!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (showVictoryDialog) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated.ALL_PUZZLES
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) allPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    showVictoryDialog = false
                    navController.popBackStack()
                    navController.navigate("game/hexasort/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "All hexes cleared! Score: $victoryScore",
            mode = mode,
            onMainMenuClick = {
                showVictoryDialog = false
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = {
                showVictoryDialog = false
                viewModel.startNewGame()
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showGameOverDialog) {
        GameEndDialog(
            isWon = false,
            title = "Game Over",
            message = "No more moves available!",
            mode = mode,
            onMainMenuClick = {
                showGameOverDialog = false
                navController.navigate("home") { popUpTo(0) }
            },
            onPlayAgainClick = {
                showGameOverDialog = false
                viewModel.startNewGame()
            }
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Game",
            message = "Are you sure you want to start a new game?",
            onConfirm = {
                showNewGameDialog = false
                viewModel.startNewGame()
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Hexa Sort",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state?.let { gs ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Score: ${gs.score}", fontSize = 16.sp)
                    Text(text = "Moves: ${gs.moves}", fontSize = 16.sp)
                    val timeFormatted = String.format(java.util.Locale.ROOT, "%02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60)
                    Text(text = timeFormatted, fontSize = 16.sp)
                }

                val shuffleInteractionSource = remember { MutableInteractionSource() }
                Button(
                    onClick = onShuffleClick,
                    enabled = gs.shufflesRemaining > 0 && !isShufflingUI && !gs.isWon && !gs.isGameOver && gs.flashingCells.isEmpty(),
                    interactionSource = shuffleInteractionSource,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9100),
                        contentColor = Color.White,
                        disabledContainerColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .animateTapFeedback(shuffleInteractionSource)
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = "Shuffle")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Shuffle (${gs.shufflesRemaining})", fontSize = 14.sp)
                }

                val level = gs.level

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned { canvasSize = it.size }
                            .pointerInput(level.rows, level.cols, canvasSize) {
                                if (canvasSize.width == 0 || canvasSize.height == 0) return@pointerInput
                                val cW = canvasSize.width.toFloat()
                                val cH = canvasSize.height.toFloat()
                                val (hexW, _) = computeHexSize(level.rows, level.cols, cW, cH)
                                detectTapGestures { offset ->
                                    val cellCoords = hitTestHex(
                                        offset, level.rows, level.cols, hexW, cW, cH
                                    )
                                    if (cellCoords != null) {
                                        val (r, c) = cellCoords
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        viewModel.tapCell(r, c)
                                    }
                                }
                            }
                    ) {
                        val rows = level.rows
                        val cols = level.cols
                        val (hexW, _) = computeHexSize(rows, cols, size.width, size.height)
                        val R = hexW / sqrt(3.0).toFloat()
                        val dx = sqrt(3.0).toFloat() * R
                        val dy = 1.5f * R
                        val gridWidth = (cols + 0.5f) * dx
                        val gridHeight = (1.5f * rows + 0.5f) * R
                        val offsetX = (size.width - gridWidth) / 2 + dx / 2
                        val offsetY = (size.height - gridHeight) / 2 + R

                        // 1. Draw empty outlines
                        for (r in 0 until rows) {
                            for (c in 0 until cols) {
                                if (gs.grid[r][c] == null) {
                                    val cx = offsetX + c * dx + (if (r % 2 == 1) dx / 2 else 0f)
                                    val cy = offsetY + r * dy
                                    val path = createHexPath(cx, cy, R)
                                    drawPath(path, Color.Gray.copy(alpha = 0.15f), style = Stroke(width = 1.5f))
                                }
                            }
                        }

                        // 2. Draw active tiles
                        activeTiles.values.forEach { tile ->
                            val scale = tile.scale.value
                            val alpha = tile.alpha.value
                            if (scale > 0f && alpha > 0f) {
                                val cx = offsetX + tile.col * dx + (if (tile.row % 2 == 1) dx / 2 else 0f)
                                val cy = offsetY + tile.row * dy + tile.offsetY.value

                                val baseColor = paletteColors[tile.color.coerceIn(0, paletteColors.lastIndex)]
                                val lightColor = baseColor.lighten(0.3f)
                                val darkColor = baseColor.darken(0.3f)

                                val shadowPath = createHexPath(cx + 2f, cy + 4f, R * scale)
                                drawPath(shadowPath, Color.Black.copy(alpha = 0.15f * alpha), style = Fill)

                                val path = createHexPath(cx, cy, R * scale)
                                val brush = Brush.linearGradient(
                                    colors = listOf(lightColor, darkColor),
                                    start = Offset(cx - R * scale, cy - R * scale),
                                    end = Offset(cx + R * scale, cy + R * scale)
                                )
                                drawPath(path, brush, alpha = alpha, style = Fill)
                                drawPath(path, Color.White.copy(alpha = 0.35f * alpha), style = Stroke(width = 1.5f * scale))
                            }
                        }

                        // 3. Draw disappearing tiles
                        disappearingTiles.forEach { tile ->
                            val scale = tile.scale.value
                            val alpha = tile.alpha.value
                            if (scale > 0f && alpha > 0f) {
                                val cx = offsetX + tile.col * dx + (if (tile.row % 2 == 1) dx / 2 else 0f)
                                val cy = offsetY + tile.row * dy + tile.offsetY.value

                                val baseColor = paletteColors[tile.color.coerceIn(0, paletteColors.lastIndex)]
                                val lightColor = baseColor.lighten(0.3f)
                                val darkColor = baseColor.darken(0.3f)

                                val shadowPath = createHexPath(cx + 2f, cy + 4f, R * scale)
                                drawPath(shadowPath, Color.Black.copy(alpha = 0.15f * alpha), style = Fill)

                                val path = createHexPath(cx, cy, R * scale)
                                val brush = Brush.linearGradient(
                                    colors = listOf(lightColor, darkColor),
                                    start = Offset(cx - R * scale, cy - R * scale),
                                    end = Offset(cx + R * scale, cy + R * scale)
                                )
                                drawPath(path, brush, alpha = alpha, style = Fill)
                                drawPath(path, Color.White.copy(alpha = 0.35f * alpha), style = Stroke(width = 1.5f * scale))
                            }
                        }

                        // 4. Draw particles
                        particles.forEach { p ->
                            drawCircle(
                                color = p.color,
                                radius = p.size * p.life,
                                center = Offset(p.x, p.y),
                                alpha = p.alpha
                            )
                        }

                        // 5. Draw combo texts
                        comboTexts.forEach { t ->
                            val textLayoutResult = textMeasurer.measure(
                                text = t.text,
                                style = TextStyle(
                                    color = t.color,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            drawText(
                                textLayoutResult = textLayoutResult,
                                topLeft = Offset(t.x - textLayoutResult.size.width / 2f, t.y + t.currentYOffset),
                                alpha = t.alpha
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun computeHexSize(rows: Int, cols: Int, canvasW: Float, canvasH: Float): Pair<Float, Float> {
    if (rows == 0 || cols == 0 || canvasW <= 0 || canvasH <= 0) return 40f to 34.64f
    val rWidthMax = canvasW / ((cols + 0.5f) * sqrt(3.0f))
    val rHeightMax = canvasH / (1.5f * rows + 0.5f)
    val r = minOf(rWidthMax, rHeightMax, 40f)
    val hexW = r * sqrt(3.0f)
    return hexW to (1.5f * r)
}

private fun createHexPath(centerX: Float, centerY: Float, radius: Float): Path {
    val path = Path()
    for (i in 0..5) {
        val angle = PI / 3 * i - PI / 6
        val px = centerX + (radius * cos(angle)).toFloat()
        val py = centerY + (radius * sin(angle)).toFloat()
        if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
    }
    path.close()
    return path
}

private fun hitTestHex(
    tap: Offset,
    rows: Int,
    cols: Int,
    hexWidth: Float,
    canvasWidth: Float,
    canvasHeight: Float
): Pair<Int, Int>? {
    val R = hexWidth / sqrt(3.0).toFloat()
    val dx = sqrt(3.0).toFloat() * R
    val dy = 1.5f * R
    val gridWidth = (cols + 0.5f) * dx
    val gridHeight = (1.5f * rows + 0.5f) * R
    val offsetX = (canvasWidth - gridWidth) / 2 + dx / 2
    val offsetY = (canvasHeight - gridHeight) / 2 + R

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val cx = offsetX + c * dx + (if (r % 2 == 1) dx / 2 else 0f)
            val cy = offsetY + r * dy
            val dxCell = tap.x - cx
            val dyCell = tap.y - cy

            if (abs(dxCell) <= dx / 2f && abs(dyCell) + abs(dxCell) / sqrt(3.0f) <= R) {
                return r to c
            }
        }
    }
    return null
}
