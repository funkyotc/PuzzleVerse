package com.funkyotc.puzzleverse.hexastack.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.hexastack.data.AxialCoord
import com.funkyotc.puzzleverse.hexastack.data.HexaStackPregenerated
import com.funkyotc.puzzleverse.hexastack.viewmodel.HexaStackEvent
import com.funkyotc.puzzleverse.hexastack.viewmodel.HexaStackViewModel
import com.funkyotc.puzzleverse.hexastack.viewmodel.HexaStackViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

private val paletteColors = listOf(
    Color(0xFFE53935),
    Color(0xFF1E88E5),
    Color(0xFF43A047),
    Color(0xFFFDD835),
    Color(0xFF8E24AA),
    Color(0xFFFF6F00)
)

private fun Color.lighten(factor: Float = 0.25f): Color =
    Color(
        red = (red + (1f - red) * factor).coerceIn(0f, 1f),
        green = (green + (1f - green) * factor).coerceIn(0f, 1f),
        blue = (blue + (1f - blue) * factor).coerceIn(0f, 1f),
        alpha = alpha
    )

private fun Color.darken(factor: Float = 0.25f): Color =
    Color(
        red = (red * (1f - factor)).coerceIn(0f, 1f),
        green = (green * (1f - factor)).coerceIn(0f, 1f),
        blue = (blue * (1f - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )

private data class PopParticle(
    var x: Float, var y: Float, var vx: Float, var vy: Float,
    val color: Color, var alpha: Float, val size: Float, var life: Float
)

private data class ScoreFloatText(
    val text: String, val x: Float, val y: Float,
    var currentYOffset: Float, var alpha: Float, var life: Float
)

private data class BoardMetrics(
    val r: Float,
    val centers: Map<AxialCoord, Offset>
)

private fun boardMetrics(cells: Set<AxialCoord>, w: Float, h: Float): BoardMetrics {
    if (cells.isEmpty() || w <= 0f || h <= 0f) return BoardMetrics(20f, emptyMap())
    // Pointy-top axial layout: x = sqrt(3)*R*(q + r/2), y = 1.5*R*r
    val minQ = cells.minOf { it.q }.toFloat()
    val maxQ = cells.maxOf { it.q }.toFloat()
    val minR = cells.minOf { it.r }.toFloat()
    val maxR = cells.maxOf { it.r }.toFloat()
    val wUnits = sqrt(3f) * ((maxQ - minQ) + (maxR - minR) / 2f + 1f)
    val hUnits = 1.5f * (maxR - minR) + 2f
    val r = minOf(w / wUnits, h / hUnits) * 0.92f
    val dx = sqrt(3f) * r
    val dy = 1.5f * r
    val gridW = dx * ((maxQ - minQ) + (maxR - minR) / 2f) + dx
    val gridH = dy * (maxR - minR) + 2f * r
    val originX = (w - gridW) / 2f + dx / 2f
    val originY = (h - gridH) / 2f + r
    val centers = cells.associateWith { c ->
        Offset(originX + dx * ((c.q - minQ) + (c.r - minR) / 2f), originY + dy * (c.r - minR))
    }
    return BoardMetrics(r, centers)
}

private fun hitTestCell(point: Offset, metrics: BoardMetrics): AxialCoord? {
    val dx = sqrt(3f) * metrics.r
    var best: AxialCoord? = null
    var bestDist = Float.MAX_VALUE
    for ((coord, center) in metrics.centers) {
        val dxc = abs(point.x - center.x)
        val dyc = abs(point.y - center.y)
        if (dxc <= dx / 2f && dyc + dxc / sqrt(3f) <= metrics.r) {
            val dist = dxc * dxc + dyc * dyc
            if (dist < bestDist) {
                bestDist = dist
                best = coord
            }
        }
    }
    return best
}

private fun hitTestTray(point: Offset, trayW: Float, trayH: Float, trayCount: Int): Int? {
    if (trayW <= 0f) return null
    val slotW = trayW / 3f
    for (i in 0 until 3) {
        val cx = slotW * (i + 0.5f)
        if (abs(point.x - cx) <= slotW * 0.45f && abs(point.y - trayH / 2f) <= trayH * 0.48f && i < trayCount) {
            return i
        }
    }
    return null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HexaStackScreen(
    navController: NavController,
    mode: String? = "standard",
    forceNewGame: Boolean = false,
    puzzleId: String? = null,
    settingsRepository: SettingsRepository,
    streakRepository: StreakRepository,
    context: Context = LocalContext.current,
    viewModel: HexaStackViewModel = viewModel(
        factory = HexaStackViewModelFactory(context, mode, forceNewGame, streakRepository, settingsRepository, puzzleId)
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

    val particles = remember { mutableStateListOf<PopParticle>() }
    val scoreTexts = remember { mutableStateListOf<ScoreFloatText>() }
    var boardSize by remember { mutableStateOf(IntSize.Zero) }
    var boardBounds by remember { mutableStateOf(Rect.Zero) }
    var trayBounds by remember { mutableStateOf(Rect.Zero) }
    val textMeasurer = rememberTextMeasurer()

    // Moving tile animation state
    class MovingTileState(val start: Offset, val end: Offset, val colorIdx: Int) {
        var progress by mutableStateOf(0f)
    }
    val movingTiles = remember { mutableStateListOf<MovingTileState>() }

    var dragSlot by remember { mutableStateOf(-1) }
    var dragPosition by remember { mutableStateOf(Offset.Zero) }
    var hoverCoord by remember { mutableStateOf<AxialCoord?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HexaStackEvent.Won -> {
                    victoryScore = event.score
                    showVictoryDialog = true
                    soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
                }
                is HexaStackEvent.GameOver -> {
                    showGameOverDialog = true
                    soundManager.playSound(SoundManager.SOUND_ID_FAILURE)
                }
                is HexaStackEvent.Placed -> {
                    soundManager.playSound(SoundManager.SOUND_ID_TILE_PLACE)
                }
                is HexaStackEvent.Popped -> {
                    soundManager.playSound(SoundManager.SOUND_ID_MERGE_POP)
                    if (event.tiles >= 14) {
                        soundManager.playSound(SoundManager.SOUND_ID_TRIUMPHANT_CHIME)
                    }
                }
                is HexaStackEvent.Moves -> {
                    // handled by a separate LaunchedEffect for animations
                }
            }
        }
    }

    // Collect move events and animate moving tiles
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is HexaStackEvent.Moves) {
                val s = state ?: return@collect
                if (boardSize.width == 0) return@collect
                val metrics = boardMetrics(s.level.cells, boardSize.width.toFloat(), boardSize.height.toFloat())
                val tileH = metrics.r * 0.14f
                // Simulate destination heights so tiles land stacked properly
                val heights = s.cells.mapValues { it.value.size }.toMutableMap()
                val perTileMs = 160
                val tileStaggerMs = 40
                val stackStaggerMs = 120
                for (move in event.moves) {
                    val fromCenter = metrics.centers[move.from] ?: continue
                    val toCenter = metrics.centers[move.to] ?: continue
                    val fromStackSize = s.cells[move.from]?.size ?: 0
                    for ((idx, colorIdx) in move.tiles.withIndex()) {
                        val layerIndex = fromStackSize - move.tiles.size + idx
                        val startY = fromCenter.y - layerIndex * tileH
                        val destLayer = heights.getOrDefault(move.to, 0)
                        val endY = toCenter.y - destLayer * tileH
                        val mt = MovingTileState(Offset(fromCenter.x, startY), Offset(toCenter.x, endY), colorIdx)
                        movingTiles.add(mt)
                        // Stagger between tiles within this stack
                        if (idx > 0) delay(tileStaggerMs.toLong())
                        val anim = Animatable(0f)
                        anim.animateTo(1f, tween(durationMillis = perTileMs)) {
                            mt.progress = value
                        }
                        heights[move.to] = heights.getOrDefault(move.to, 0) + 1
                    }
                    // Small cleanup & stagger
                    delay(40)
                    repeat(move.tiles.size) { if (movingTiles.isNotEmpty()) movingTiles.removeAt(0) }
                    delay(stackStaggerMs.toLong())
                }
            }
        }
    }

    // Spawn particles + floating score when pops occur
    LaunchedEffect(state?.lastPoppedTiles, state?.moves) {
        val s = state ?: return@LaunchedEffect
        if (s.lastPoppedTiles <= 0 || boardSize.width == 0) return@LaunchedEffect
        val metrics = boardMetrics(s.level.cells, boardSize.width.toFloat(), boardSize.height.toFloat())
        for (coord in s.poppingCoords) {
            val center = metrics.centers[coord] ?: continue
            val topColor = s.cells[coord]?.lastOrNull() ?: 0
            val baseColor = paletteColors[topColor.coerceIn(0, paletteColors.lastIndex)]
            repeat(12) {
                val angle = Random.nextFloat() * 2 * PI.toFloat()
                val speed = Random.nextFloat() * 4f + 1f
                particles.add(
                    PopParticle(
                        x = center.x, y = center.y,
                        vx = cos(angle) * speed, vy = sin(angle) * speed - 1.5f,
                        color = baseColor, alpha = 1f,
                        size = Random.nextFloat() * metrics.r * 0.12f + 3f, life = 1f
                    )
                )
            }
            scoreTexts.add(
                ScoreFloatText(
                    text = "+${s.lastPoppedTiles * 10}",
                    x = center.x, y = center.y - metrics.r,
                    currentYOffset = 0f, alpha = 1f, life = 1f
                )
            )
        }
    }

    // Particle/text ticker
    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                val pi = particles.iterator()
                while (pi.hasNext()) {
                    val p = pi.next()
                    p.x += p.vx; p.y += p.vy; p.vy += 0.2f
                    p.life -= 0.03f
                    p.alpha = p.life.coerceIn(0f, 1f)
                    if (p.life <= 0f) pi.remove()
                }
                val ti = scoreTexts.iterator()
                while (ti.hasNext()) {
                    val t = ti.next()
                    t.currentYOffset -= 1.2f
                    t.life -= 0.02f
                    t.alpha = t.life.coerceIn(0f, 1f)
                    if (t.life <= 0f) ti.remove()
                }
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Drag stack groups from the tray onto empty cells. Adjacent stacks with matching top colors merge. Stacks of 10+ same-color tiles on top pop for points. Reach the score target to win!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (showVictoryDialog) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = HexaStackPregenerated.ALL_PUZZLES
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) allPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    showVictoryDialog = false
                    navController.popBackStack()
                    navController.navigate("game/hexastack/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "Target reached! Score: $victoryScore",
            mode = mode,
            onMainMenuClick = {
                showVictoryDialog = false
                if (mode == "puzzle") navController.popBackStack()
                else navController.navigate("home") { popUpTo(0) }
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
            message = "Out of moves before reaching the target!",
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
        title = "Hexa Stack",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null
    ) { paddingValues ->
        // Outer Box: the drag ghost must overlay the content, not participate in
        // Column layout (a fillMaxSize Canvas inside the Column would collapse the
        // weighted board and shove the tray to the top while dragging).
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state?.let { s ->
                val target = s.level.scoreTarget
                val progress = (s.score.toFloat() / target).coerceIn(0f, 1f)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Score: ${s.score}", fontSize = 16.sp)
                    Text(text = "Target: $target", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    val timeFormatted = String.format(java.util.Locale.ROOT, "%02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60)
                    Text(text = timeFormatted, fontSize = 16.sp)
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .padding(bottom = 8.dp),
                    color = if (progress >= 1f) Color(0xFF43A047) else MaterialTheme.colorScheme.primary
                )

                // Board
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .onGloballyPositioned {
                            boardSize = it.size
                            boardBounds = it.boundsInRoot()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val metrics = boardMetrics(s.level.cells, size.width, size.height)
                        val tileH = metrics.r * 0.14f

                        // 1. Empty cell outlines
                        for ((coord, center) in metrics.centers) {
                            if (!s.cells.containsKey(coord)) {
                                val path = createHexPath(center.x, center.y, metrics.r * 0.92f)
                                drawPath(path, Color.Gray.copy(alpha = 0.15f), style = Stroke(width = 1.5f))
                            }
                        }

                        // 2. Hover highlight while dragging
                        hoverCoord?.let { hc ->
                            if (dragSlot >= 0 && !s.cells.containsKey(hc)) {
                                metrics.centers[hc]?.let { center ->
                                    val path = createHexPath(center.x, center.y, metrics.r * 0.92f)
                                    drawPath(path, Color.White.copy(alpha = 0.25f), style = Fill)
                                    drawPath(path, Color.White.copy(alpha = 0.7f), style = Stroke(width = 2.5f))
                                }
                            }
                        }

                        // 3. Stacks (sorted bottom-to-top so lower rows draw first)
                        val entries = s.cells.entries.sortedBy { (coord, _) -> metrics.centers[coord]?.y ?: 0f }
                        for ((coord, tiles) in entries) {
                            val center = metrics.centers[coord] ?: continue
                            drawHexStack(center, tiles, metrics.r, tileH, s.poppingCoords.contains(coord))
                        }

                        // Moving tiles overlay (animated transfers)
                        for (mt in movingTiles) {
                            val cx = mt.start.x + (mt.end.x - mt.start.x) * mt.progress
                            val cy = mt.start.y + (mt.end.y - mt.start.y) * mt.progress
                            val scaleY = kotlin.math.cos(mt.progress * PI.toFloat())
                            val base = paletteColors[mt.colorIdx.coerceIn(0, paletteColors.lastIndex)]
                            // Side shading
                            val side = createHexPath(cx, cy + tileH * 0.55f, metrics.r * 0.92f)
                            withTransform({ scale(1f, scaleY, Offset(cx, cy)) }) {
                                drawPath(side, base.darken(0.35f), alpha = 0.9f, style = Fill)
                            }
                            // Top face
                            val top = createHexPath(cx, cy, metrics.r * 0.92f)
                            val brush = Brush.linearGradient(
                                colors = listOf(base.lighten(0.3f), base),
                                start = Offset(cx - metrics.r, cy - metrics.r),
                                end = Offset(cx + metrics.r, cy + metrics.r)
                            )
                            withTransform({ scale(1f, scaleY, Offset(cx, cy)) }) {
                                drawPath(top, brush, alpha = 1f, style = Fill)
                                drawPath(top, Color.White.copy(alpha = 0.3f), style = Stroke(width = 1.2f))
                            }
                        }

                        // 4. Particles
                        particles.forEach { p ->
                            drawCircle(color = p.color, radius = p.size * p.life, center = Offset(p.x, p.y), alpha = p.alpha)
                        }

                        // 5. Floating score texts
                        scoreTexts.forEach { t ->
                            val layout = textMeasurer.measure(
                                text = t.text,
                                style = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                            )
                            drawText(layout, topLeft = Offset(t.x - layout.size.width / 2f, t.y + t.currentYOffset), alpha = t.alpha)
                        }
                    }
                }

                // Tray (drag source; explicit pointer tracking per AGENTS.md multi-touch gotcha)
                val fingerOffsetPx = with(LocalDensity.current) { 224.dp.toPx() }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .onGloballyPositioned { trayBounds = it.boundsInRoot() }
                        .pointerInput(s.tray, s.isWon, s.isGameOver, boardSize, boardBounds) {
                                    if (s.isWon || s.isGameOver) return@pointerInput
                                    awaitEachGesture {
                                        val down = awaitFirstDown()
                                        val slot = hitTestTray(
                                            down.position,
                                            size.width.toFloat(),
                                            size.height.toFloat(),
                                            s.tray.size
                                        ) ?: return@awaitEachGesture
                                if (s.tray.getOrNull(slot) == null) return@awaitEachGesture
                                dragSlot = slot
                                dragPosition = down.position
                                hoverCoord = null

                                var current: androidx.compose.ui.input.pointer.PointerInputChange? = awaitDragOrCancellation(down.id)
                                while (current != null) {
                                    val c = current
                                    dragPosition = c.position
                                    // Root coords = tray-local + tray origin in root; board-local = root - board origin
                                    val rootX = trayBounds.left + c.position.x
                                    val rootY = trayBounds.top + c.position.y - fingerOffsetPx
                                    val boardLocal = Offset(rootX - boardBounds.left, rootY - boardBounds.top)
                                    val metrics = boardMetrics(
                                        s.level.cells,
                                        boardSize.width.toFloat(),
                                        boardSize.height.toFloat()
                                    )
                                    hoverCoord = hitTestCell(boardLocal, metrics)
                                    if (c.positionChange() != Offset.Zero) c.consume()
                                    if (!c.pressed) break
                                    current = awaitPointerEvent().changes.firstOrNull { it.id == c.id }
                                }
                                if (current != null) {
                                    val dropTarget = hoverCoord
                                    if (dropTarget != null && !s.cells.containsKey(dropTarget)) {
                                        viewModel.place(slot, dropTarget)
                                    } else {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    }
                                }
                                dragSlot = -1
                                hoverCoord = null
                            }
                        }
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val slotW = size.width / 3f
                        val r = minOf(slotW, size.height) * 0.24f
                        val tileH = r * 0.16f
                        for (i in 0 until 3) {
                            val group = s.tray.getOrNull(i)
                            val center = Offset(slotW * (i + 0.5f), size.height * 0.55f)
                            if (group != null && i != dragSlot) {
                                drawHexStack(center, group, r, tileH, popping = false)
                            } else {
                                val outline = createHexPath(center.x, center.y, r * 0.95f)
                                drawPath(outline, Color.Gray.copy(alpha = 0.2f), style = Stroke(width = 1.5f))
                            }
                        }
                    }
                }

            }
        }

        // Drag ghost overlay (follows the finger, drawn over the whole screen).
        // The ghost floats well above the finger so the player can see the stack.
        state?.let { s ->
            if (dragSlot >= 0) {
                val group = s.tray.getOrNull(dragSlot)
                if (group != null) {
                    val ghostR = with(LocalDensity.current) { 26.dp.toPx() }
                    // Double the previous offset so the ghost sits much higher above the finger
                    val fingerOffset = with(LocalDensity.current) { 224.dp.toPx() }
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val rootX = trayBounds.left + dragPosition.x
                        val rootY = trayBounds.top + dragPosition.y
                        withTransform({ translate(rootX, rootY - fingerOffset) }) {
                            drawHexStack(Offset.Zero, group, ghostR, ghostR * 0.16f, popping = false)
                        }
                    }
                }
            }
        }
        }
    }
}

private fun DrawScope.drawHexStack(
    center: Offset,
    tiles: List<Int>,
    r: Float,
    tileH: Float,
    popping: Boolean
) {
    val maxVisible = 10
    val visible = tiles.takeLast(maxVisible)
    val hidden = tiles.size - visible.size
    visible.forEachIndexed { i, colorIdx ->
        val layer = hidden + i
        val cy = center.y - layer * tileH
        val base = paletteColors[colorIdx.coerceIn(0, paletteColors.lastIndex)]
        val alpha = if (popping) 0.55f else 1f
        // Side shading below each tile for pseudo-3D depth
        val side = createHexPath(center.x, cy + tileH * 0.55f, r * 0.92f)
        drawPath(side, base.darken(0.35f), alpha = alpha * 0.9f, style = Fill)
        val top = createHexPath(center.x, cy, r * 0.92f)
        val brush = Brush.linearGradient(
            colors = listOf(base.lighten(0.3f), base),
            start = Offset(center.x - r, cy - r),
            end = Offset(center.x + r, cy + r)
        )
        drawPath(top, brush, alpha = alpha, style = Fill)
        drawPath(top, Color.White.copy(alpha = 0.3f * alpha), style = Stroke(width = 1.2f))
    }
    if (popping) {
        val ring = createHexPath(center.x, center.y - (tiles.size - 1) * tileH, r)
        drawPath(ring, Color.White.copy(alpha = 0.8f), style = Stroke(width = 3f))
    }
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
