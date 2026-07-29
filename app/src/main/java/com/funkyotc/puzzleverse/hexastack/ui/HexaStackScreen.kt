package com.funkyotc.puzzleverse.hexastack.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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
    var frameTick by remember { mutableIntStateOf(0) }
    var boardSize by remember { mutableStateOf(IntSize.Zero) }
    var boardBounds by remember { mutableStateOf(Rect.Zero) }
    var trayBounds by remember { mutableStateOf(Rect.Zero) }
    val textMeasurer = rememberTextMeasurer()

    // Local animated board cells state to prevent tile duplication glitches
    val visibleCells = remember { mutableStateMapOf<AxialCoord, SnapshotStateList<Int>>() }

    // Sync visibleCells with state when idle
    LaunchedEffect(state?.cells) {
        val s = state ?: return@LaunchedEffect
        visibleCells.clear()
        s.cells.forEach { (coord, list) ->
            visibleCells[coord] = list.toMutableStateList()
        }
    }

    // Track stacks currently playing a pop animation
    class PopAnimState(val colorIdx: Int, val tileCount: Int) {
        var progress by mutableStateOf(0f)
    }
    val poppingStackAnims = remember { mutableStateMapOf<AxialCoord, PopAnimState>() }

    // Moving tile animation state
    class MovingTileState(
        val start: Offset,
        val end: Offset,
        val colorIdx: Int,
        val tileH: Float,
        val r: Float
    ) {
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
                    // handled via Steps for enhanced 3D animation sequence
                }
                is HexaStackEvent.Steps -> {
                    val s = state ?: return@collect
                    if (boardSize.width == 0) return@collect
                    val metrics = boardMetrics(s.level.cells, boardSize.width.toFloat(), boardSize.height.toFloat())
                    val tileH = metrics.r * 0.14f

                    for (step in event.steps) {
                        when (step) {
                            is com.funkyotc.puzzleverse.hexastack.data.HexaStackLogic.AnimStep.Transfer -> {
                                val fromCenter = metrics.centers[step.from] ?: continue
                                val toCenter = metrics.centers[step.to] ?: continue

                                val perTileMs = 150
                                // Peel top-most tiles off donor stack first
                                val tileSequence = step.tiles.asReversed()

                                for (colorIdx in tileSequence) {
                                    val fromStack = visibleCells[step.from] ?: break
                                    if (fromStack.isEmpty()) break

                                    // Top tile position on donor stack
                                    val startLayer = fromStack.size - 1
                                    val startY = fromCenter.y - startLayer * tileH

                                    // Peel top tile off donor stack
                                    fromStack.removeAt(fromStack.lastIndex)
                                    if (fromStack.isEmpty()) visibleCells.remove(step.from)

                                    // Landing position on target stack
                                    val toStack = visibleCells.getOrPut(step.to) { mutableStateListOf() }
                                    val destLayer = toStack.size
                                    val endY = toCenter.y - destLayer * tileH

                                    val mt = MovingTileState(
                                        start = Offset(fromCenter.x, startY),
                                        end = Offset(toCenter.x, endY),
                                        colorIdx = colorIdx,
                                        tileH = tileH,
                                        r = metrics.r
                                    )
                                    movingTiles.add(mt)

                                    val anim = Animatable(0f)
                                    anim.animateTo(1f, tween(durationMillis = perTileMs)) {
                                        mt.progress = value
                                    }

                                    toStack.add(colorIdx)
                                    movingTiles.remove(mt)
                                    soundManager.playSound(SoundManager.SOUND_ID_TILE_PLACE)

                                    delay(25L)
                                }

                                delay(40L)
                            }
                            is com.funkyotc.puzzleverse.hexastack.data.HexaStackLogic.AnimStep.Pop -> {
                                val center = metrics.centers[step.coord]
                                val baseColor = paletteColors[step.color.coerceIn(0, paletteColors.lastIndex)]

                                if (center != null) {
                                    repeat(18) {
                                        val angle = Random.nextFloat() * 2 * PI.toFloat()
                                        val speed = Random.nextFloat() * 5f + 2f
                                        particles.add(
                                            PopParticle(
                                                x = center.x, y = center.y,
                                                vx = cos(angle) * speed, vy = sin(angle) * speed - 2f,
                                                color = baseColor, alpha = 1f,
                                                size = Random.nextFloat() * metrics.r * 0.16f + 4f, life = 1f
                                            )
                                        )
                                    }
                                    scoreTexts.add(
                                        ScoreFloatText(
                                            text = "+${step.count * 10}",
                                            x = center.x, y = center.y - metrics.r * 1.2f,
                                            currentYOffset = 0f, alpha = 1f, life = 1f
                                        )
                                    )
                                    soundManager.playSound(SoundManager.SOUND_ID_MERGE_POP)
                                }

                                val popAnim = PopAnimState(step.color, step.count)
                                poppingStackAnims[step.coord] = popAnim

                                val anim = Animatable(0f)
                                anim.animateTo(1f, tween(durationMillis = 320)) {
                                    popAnim.progress = value
                                }

                                val stack = visibleCells[step.coord]
                                if (stack != null) {
                                    repeat(step.count) { if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex) }
                                    if (stack.isEmpty()) visibleCells.remove(step.coord)
                                }
                                poppingStackAnims.remove(step.coord)
                                delay(50L)
                            }
                        }
                    }

                    viewModel.onAnimationFinished(event.finalState)
                }
            }
        }
    }

    // Particle/text ticker
    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                if (particles.isNotEmpty() || scoreTexts.isNotEmpty()) {
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
                    frameTick++
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val ghostOffsetPx = with(LocalDensity.current) { 224.dp.toPx() }
            val dropOffsetPx = with(LocalDensity.current) { 112.dp.toPx() }
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
                        .padding(vertical = 4.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.5.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                            RoundedCornerShape(16.dp)
                        )
                        .onGloballyPositioned {
                            boardSize = it.size
                            boardBounds = it.boundsInRoot()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val socketFill = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                    val socketBorder = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    val emptySlotOutline = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
                    val hoverFill = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                    val hoverBorder = MaterialTheme.colorScheme.primary

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        @Suppress("UNUSED_VARIABLE")
                        val tick = frameTick
                        val metrics = boardMetrics(s.level.cells, size.width, size.height)
                        val tileH = metrics.r * 0.14f

                        // 1. Board cell base sockets & empty cell outlines
                        for ((coord, center) in metrics.centers) {
                            val path = createHexPath(center.x, center.y, metrics.r * 0.92f)
                            drawPath(path, socketFill, style = Fill)
                            if (!visibleCells.containsKey(coord) || visibleCells[coord]?.isEmpty() == true) {
                                drawPath(path, emptySlotOutline, style = Stroke(width = 2f))
                            } else {
                                drawPath(path, socketBorder, style = Stroke(width = 1.2f))
                            }
                        }

                        // 2. Hover highlight while dragging
                        hoverCoord?.let { hc ->
                            if (dragSlot >= 0 && (!visibleCells.containsKey(hc) || visibleCells[hc]?.isEmpty() == true)) {
                                metrics.centers[hc]?.let { center ->
                                    val path = createHexPath(center.x, center.y, metrics.r * 0.92f)
                                    drawPath(path, hoverFill, style = Fill)
                                    drawPath(path, hoverBorder, style = Stroke(width = 3f))
                                }
                            }
                        }

                        // 3. Stacks (rendered from visibleCells state)
                        val entries = visibleCells.entries
                            .filter { it.value.isNotEmpty() }
                            .sortedBy { (coord, _) -> metrics.centers[coord]?.y ?: 0f }
                        for ((coord, tiles) in entries) {
                            val center = metrics.centers[coord] ?: continue
                            val popAnim = poppingStackAnims[coord]
                            if (popAnim != null) {
                                val t = popAnim.progress
                                val pulse = 1f + 0.15f * sin(t * PI.toFloat()) - 0.2f * t * t
                                val alpha = (1f - t * 1.15f).coerceIn(0f, 1f)
                                withTransform({
                                    scale(pulse, pulse, center)
                                }) {
                                    drawHexStack(center, tiles, metrics.r, tileH, popping = true, alphaOverride = alpha)
                                }
                            } else {
                                drawHexStack(center, tiles, metrics.r, tileH, s.poppingCoords.contains(coord))
                            }
                        }

                        // 4. Moving tiles overlay (3D parabolic arc flip animation)
                        for (mt in movingTiles) {
                            val t = mt.progress
                            val cx = mt.start.x + (mt.end.x - mt.start.x) * t
                            val cyLinear = mt.start.y + (mt.end.y - mt.start.y) * t

                            val dist = sqrt((mt.end.x - mt.start.x).let { it * it } + (mt.end.y - mt.start.y).let { it * it })
                            val maxBoardStackTiles = visibleCells.values.maxOfOrNull { it.size } ?: 0
                            val maxBoardHeightPx = maxBoardStackTiles * mt.tileH
                            val arcHeight = maxOf(mt.r * 2.5f, dist * 0.5f, maxBoardHeightPx + mt.r * 1.8f)
                            val elevation = arcHeight * 4f * t * (1f - t)
                            val cy = cyLinear - elevation

                            // Drop shadow on board surface
                            val shadowR = metrics.r * 0.88f * (1f - (elevation / (arcHeight * 3.5f)))
                            val shadowAlpha = (0.28f * (1f - (elevation / (arcHeight * 1.8f)))).coerceIn(0f, 0.28f)
                            val shadowPath = createHexPath(cx, cyLinear, shadowR)
                            drawPath(shadowPath, Color.Black.copy(alpha = shadowAlpha), style = Fill)

                            // 3D flip angle & scaling
                            val flipAngle = t * PI.toFloat()
                            val cosVal = cos(flipAngle)
                            val absScaleY = abs(cosVal).coerceAtLeast(0.06f)

                            val base = paletteColors[mt.colorIdx.coerceIn(0, paletteColors.lastIndex)]

                            // Side shading (always offset downwards in screen space)
                            val sideOffset = tileH * 0.55f * absScaleY
                            val sidePath = createHexPath(cx, cy + sideOffset, metrics.r * 0.92f)
                            withTransform({ scale(1f, absScaleY, Offset(cx, cy)) }) {
                                drawPath(sidePath, base.darken(0.35f), alpha = 0.92f, style = Fill)
                            }

                            // Top face
                            val topPath = createHexPath(cx, cy, metrics.r * 0.92f)
                            val lightenFactor = 0.25f + 0.15f * (elevation / arcHeight)
                            val brush = Brush.linearGradient(
                                colors = listOf(base.lighten(lightenFactor), base),
                                start = Offset(cx - metrics.r, cy - metrics.r),
                                end = Offset(cx + metrics.r, cy + metrics.r)
                            )
                            withTransform({ scale(1f, absScaleY, Offset(cx, cy)) }) {
                                drawPath(topPath, brush, alpha = 1f, style = Fill)
                                drawPath(topPath, Color.White.copy(alpha = 0.35f), style = Stroke(width = 1.2f))
                            }
                        }

                        // 5. Particles
                        particles.forEach { p ->
                            drawCircle(color = p.color, radius = p.size * p.life, center = Offset(p.x, p.y), alpha = p.alpha)
                        }

                        // 6. Floating score texts
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                            RoundedCornerShape(12.dp)
                        )
                        .border(
                            1.5.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                            RoundedCornerShape(12.dp)
                        )
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
                                    val rootY = trayBounds.top + c.position.y - dropOffsetPx
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
                    val traySocketFill = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    val trayOutlineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
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
                                drawPath(outline, traySocketFill, style = Fill)
                                drawPath(outline, trayOutlineColor, style = Stroke(width = 1.5f))
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
                    val ghostOffset = ghostOffsetPx
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val rootX = trayBounds.left + dragPosition.x
                        val rootY = trayBounds.top + dragPosition.y
                        withTransform({ translate(rootX, rootY - ghostOffset) }) {
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
    popping: Boolean,
    alphaOverride: Float = 1f
) {
    val maxVisible = 10
    val visible = tiles.takeLast(maxVisible)
    val hidden = tiles.size - visible.size
    visible.forEachIndexed { i, colorIdx ->
        val layer = hidden + i
        val cy = center.y - layer * tileH
        val base = paletteColors[colorIdx.coerceIn(0, paletteColors.lastIndex)]
        val alpha = (if (popping) 0.85f else 1f) * alphaOverride
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
        drawPath(top, Color.White.copy(alpha = 0.35f * alpha), style = Stroke(width = 1.2f))
    }
    if (popping) {
        val ring = createHexPath(center.x, center.y - (tiles.size - 1) * tileH, r * 1.05f)
        drawPath(ring, Color.White.copy(alpha = 0.9f * alphaOverride), style = Stroke(width = 3.5f))
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
