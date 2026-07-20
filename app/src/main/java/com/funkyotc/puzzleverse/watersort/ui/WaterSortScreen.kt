package com.funkyotc.puzzleverse.watersort.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.watersort.data.WaterSortPregenerated
import com.funkyotc.puzzleverse.watersort.viewmodel.WaterSortViewModel
import com.funkyotc.puzzleverse.watersort.viewmodel.WaterSortViewModelFactory
import com.funkyotc.puzzleverse.core.ui.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlin.math.PI
import kotlin.math.sin

private val WATER_COLORS = listOf(
    Color(0xFFE53935), // Red
    Color(0xFF1E88E5), // Blue
    Color(0xFF43A047), // Green
    Color(0xFFFBC02D), // Yellow
    Color(0xFFFF6F00), // Orange
    Color(0xFF8E24AA), // Purple
    Color(0xFF00ACC1), // Cyan
    Color(0xFFD81B60), // Pink
    Color(0xFF6D4C41), // Brown
    Color(0xFF3949AB), // Indigo
    Color(0xFF00BCD4), // Teal
    Color(0xFF78909C), // Blue Gray
)

private val GLASS_COLOR = Color.White.copy(alpha = 0.15f)
private val GLASS_BORDER = Color.White.copy(alpha = 0.4f)
private val GLASS_HIGHLIGHT = Color.White.copy(alpha = 0.2f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterSortScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: WaterSortViewModel = viewModel(
        factory = WaterSortViewModelFactory(streakRepository, mode, puzzleId)
    )
) {
    val stateOpt by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Water Sort") }

    val state = stateOpt ?: return

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Tap a bottle to pick up its top color, then tap another bottle to pour.\n\nYou can only pour onto a matching color or into an empty bottle.\nA bottle can hold at most ${state.level.height} layers.\n\nGoal: Sort each color into its own bottle!",
            onDismiss = { showHowToDialog = false }
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

    if (state.isWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = WaterSortPregenerated.ALL_LEVELS
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) allPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    navController.popBackStack()
                    navController.navigate("game/watersort/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "Sorted in ${state.moves} moves!",
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

    StandardGameLayout(
        title = "Water Sort",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {
            val refreshInteractionSource = remember { MutableInteractionSource() }
            IconButton(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showNewGameDialog = true
                },
                modifier = Modifier.animateTapFeedback(refreshInteractionSource),
                interactionSource = refreshInteractionSource
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Moves: ${state.moves}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = state.level.difficulty,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val bottles = state.bottles
            val cols = when {
                bottles.size <= 3 -> bottles.size
                bottles.size <= 6 -> 3
                bottles.size <= 9 -> 4
                bottles.size <= 12 -> 5
                else -> 6
            }
            val rows = (bottles.size + cols - 1) / cols
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (row in 0 until rows) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (col in 0 until cols) {
                            val index = row * cols + col
                            if (index < bottles.size) {
                                BottleView(
                                    bottle = bottles[index],
                                    height = state.level.height,
                                    isSelected = state.selectedIndex == index,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = if (cols >= 5) 2.dp else 6.dp)
                                        .animateEntrance(delayMillis = index * 40, trigger = state.level),
                                    onClick = {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        viewModel.selectBottle(index)
                                    }
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BottleView(
    bottle: com.funkyotc.puzzleverse.watersort.data.Bottle,
    height: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val layerColors = remember { mutableStateListOf<Int?>().apply {
        repeat(height) { add(null) }
        bottle.colors.forEachIndexed { i, c -> this[i] = c }
    } }
    
    LaunchedEffect(bottle.colors) {
        bottle.colors.forEachIndexed { i, c -> layerColors[i] = c }
    }

    val layerHeights = (0 until height).map { i ->
        androidx.compose.animation.core.animateFloatAsState(
            targetValue = if (i < bottle.colors.size) 1f else 0f,
            animationSpec = tween(400, easing = androidx.compose.animation.core.FastOutSlowInEasing),
            label = "layerHeight_$i"
        ).value
    }

    val transition = rememberInfiniteTransition(label = "selection")
    val pulseAlpha by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    val pulseGlow by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val borderGlowColor = Color(0xFF4CAF50).copy(alpha = pulseAlpha)
    val bgColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)

    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .aspectRatio(0.65f)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .animateTapFeedback(interactionSource)
            .graphicsLayer(
                scaleX = if (isSelected) scale else 1f,
                scaleY = if (isSelected) scale else 1f
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(3.dp)) {
            val w = size.width
            val h = size.height
            val bodyHeight = h * 0.82f
            val neckHeight = h - bodyHeight
            val bodyWidth = w * 0.78f
            val neckWidth = w * 0.38f
            val bodyLeft = (w - bodyWidth) / 2f
            val bodyTop = neckHeight
            val neckLeft = (w - neckWidth) / 2f
            val corner = bodyWidth * 0.12f
            val neckCorner = neckWidth * 0.25f

            val segmentHeight = bodyHeight / height

            val bodyPath = Path().apply {
                addRoundRect(RoundRect(
                    left = bodyLeft, top = bodyTop,
                    right = bodyLeft + bodyWidth, bottom = bodyTop + bodyHeight,
                    cornerRadius = CornerRadius(corner, corner)
                ))
            }

            if (isSelected) {
                val glowRadius = bodyWidth * 0.15f + pulseGlow * bodyWidth * 0.1f
                drawRoundRect(
                    color = borderGlowColor.copy(alpha = 0.3f),
                    topLeft = Offset(bodyLeft - glowRadius * 0.3f, bodyTop - glowRadius * 0.3f),
                    size = Size(bodyWidth + glowRadius * 0.6f, bodyHeight + glowRadius * 0.6f + neckHeight * 0.6f),
                    cornerRadius = CornerRadius(corner + glowRadius * 0.1f, corner + glowRadius * 0.1f)
                )
            }

            drawRoundRect(
                color = GLASS_COLOR,
                topLeft = Offset(bodyLeft, bodyTop),
                size = Size(bodyWidth, bodyHeight),
                cornerRadius = CornerRadius(corner, corner)
            )

            if (neckHeight > 0f) {
                drawRoundRect(
                    color = GLASS_COLOR,
                    topLeft = Offset(neckLeft, 0f),
                    size = Size(neckWidth, neckHeight),
                    cornerRadius = CornerRadius(neckCorner, neckCorner)
                )
            }

            var currentY = bodyTop + bodyHeight
            val topVisibleIndex = layerHeights.indexOfLast { it > 0.001f }
            
            clipPath(bodyPath) {
                for (i in 0 until height) {
                    val fillRatio = layerHeights[i]
                    if (fillRatio <= 0.001f) continue

                    val colorIndex = layerColors[i] ?: continue
                    val baseColor = if (colorIndex in WATER_COLORS.indices) WATER_COLORS[colorIndex] else Color.Gray

                    val currentSegmentHeight = segmentHeight * fillRatio
                    val segTop = currentY - currentSegmentHeight
                    val isTopSegment = i == topVisibleIndex

                    val bottomRadius = if (i == 0) corner else 0f
                    val rect = androidx.compose.ui.geometry.RoundRect(
                        left = bodyLeft,
                        top = segTop,
                        right = bodyLeft + bodyWidth,
                        bottom = segTop + currentSegmentHeight,
                        bottomLeftCornerRadius = CornerRadius(bottomRadius, bottomRadius),
                        bottomRightCornerRadius = CornerRadius(bottomRadius, bottomRadius)
                    )
                    val segmentPath = Path().apply { addRoundRect(rect) }

                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(
                        baseColor.copy(alpha = 0.85f),
                        baseColor,
                        baseColor.copy(alpha = 0.9f)
                    ),
                    startY = segTop,
                    endY = segTop + currentSegmentHeight
                )

                drawPath(
                    path = segmentPath,
                    brush = gradientBrush
                )

                if (isTopSegment) {
                    val waveAmplitude = segmentHeight * 0.08f
                    val wavePath = Path().apply {
                        moveTo(bodyLeft, segTop)
                        for (x in 0..((bodyWidth / 2f).toInt())) {
                            val px = bodyLeft + x * 2f
                            val relX = (px - bodyLeft) / bodyWidth
                            val wave = sin(relX * PI.toFloat() * 2f) * waveAmplitude
                            lineTo(px, segTop - wave)
                        }
                        lineTo(bodyLeft + bodyWidth, segTop)
                        close()
                    }
                    drawPath(
                        path = wavePath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                baseColor.copy(alpha = 0.7f),
                                baseColor.copy(alpha = 0.0f)
                            ),
                            startY = segTop - waveAmplitude,
                            endY = segTop + waveAmplitude
                        )
                    )
                }

                if (i > 0) {
                    val dividerY = segTop + currentSegmentHeight - 1f
                    drawLine(
                        color = baseColor.copy(alpha = 0.3f),
                        start = Offset(bodyLeft + 4f, dividerY),
                        end = Offset(bodyLeft + bodyWidth - 4f, dividerY),
                        strokeWidth = 1.5f
                    )
                }

                val highlightWidth = bodyWidth * 0.12f
                val highlightLeft = bodyLeft + bodyWidth * 0.15f
                val highlightBottomRadius = if (i == 0) corner * 0.5f else 0f
                val highlightRect = androidx.compose.ui.geometry.RoundRect(
                    left = highlightLeft,
                    top = segTop,
                    right = highlightLeft + highlightWidth,
                    bottom = segTop + currentSegmentHeight,
                    bottomLeftCornerRadius = CornerRadius(highlightBottomRadius, highlightBottomRadius),
                    bottomRightCornerRadius = CornerRadius(highlightBottomRadius, highlightBottomRadius)
                )
                val highlightPath = Path().apply { addRoundRect(highlightRect) }
                
                drawPath(
                    path = highlightPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f),
                            Color.White.copy(alpha = 0.05f)
                        ),
                        startY = segTop,
                        endY = segTop + currentSegmentHeight
                    )
                )
                
                currentY -= currentSegmentHeight
                }
            }

            val borderBrush = if (isSelected) {
                Brush.verticalGradient(
                    colors = listOf(
                        borderGlowColor,
                        borderGlowColor.copy(alpha = 0.7f),
                        borderGlowColor
                    )
                )
            } else {
                SolidColor(GLASS_BORDER)
            }
            val borderWidth = if (isSelected) 2.5f else 1.8f

            drawRoundRect(
                brush = borderBrush,
                topLeft = Offset(bodyLeft, bodyTop),
                size = Size(bodyWidth, bodyHeight),
                cornerRadius = CornerRadius(corner, corner),
                style = Stroke(width = borderWidth.dp.toPx())
            )

            if (neckHeight > 0f) {
                drawRoundRect(
                    brush = borderBrush,
                    topLeft = Offset(neckLeft, 0f),
                    size = Size(neckWidth, neckHeight),
                    cornerRadius = CornerRadius(neckCorner, neckCorner),
                    style = Stroke(width = borderWidth.dp.toPx())
                )

                val flareLeft = bodyLeft + bodyWidth * 0.1f
                val flareWidth = bodyWidth * 0.8f
                val flareTop = neckHeight - bodyWidth * 0.08f
                val flareH = bodyWidth * 0.08f
                drawRoundRect(
                    color = GLASS_COLOR,
                    topLeft = Offset(flareLeft, flareTop),
                    size = Size(flareWidth, flareH),
                    cornerRadius = CornerRadius(flareH * 0.3f, flareH * 0.3f)
                )
                drawRoundRect(
                    brush = borderBrush,
                    topLeft = Offset(flareLeft, flareTop),
                    size = Size(flareWidth, flareH),
                    cornerRadius = CornerRadius(flareH * 0.3f, flareH * 0.3f),
                    style = Stroke(width = borderWidth.dp.toPx())
                )
            }

            val glassHighlightLeft = bodyLeft + bodyWidth * 0.08f
            val glassHighlightWidth = bodyWidth * 0.06f
            drawRoundRect(
                color = GLASS_HIGHLIGHT,
                topLeft = Offset(glassHighlightLeft, bodyTop + bodyHeight * 0.05f),
                size = Size(glassHighlightWidth, bodyHeight * 0.9f),
                cornerRadius = CornerRadius(glassHighlightWidth * 0.3f, glassHighlightWidth * 0.3f)
            )
        }
    }
}
