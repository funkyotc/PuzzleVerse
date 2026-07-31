package com.funkyotc.puzzleverse.woodnuts.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.woodnuts.data.Bolt
import com.funkyotc.puzzleverse.woodnuts.data.Plank
import com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated
import com.funkyotc.puzzleverse.woodnuts.data.clampBoltCellToPlanks
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModel
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModelFactory
import com.funkyotc.puzzleverse.core.ui.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlin.math.sqrt

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import com.funkyotc.puzzleverse.woodnuts.data.ColorBoard
import com.funkyotc.puzzleverse.woodnuts.data.ScrewColor
import kotlin.math.PI
import kotlin.math.sin

private val PLANK_PALETTE = listOf(
    Color(0xFF8D6E63), Color(0xFFA1887F), Color(0xFFBCAAA4),
    Color(0xFF6D4C41), Color(0xFF5D4037), Color(0xFF4E342E),
    Color(0xFFD7CCC8), Color(0xFFEFEBE9), Color(0xFF795548),
    Color(0xFF8B6F5E), Color(0xFFA0897C), Color(0xFFB8A69B)
)

private const val BOLT_UNSCREW_DURATION = 350

data class FlyingScrewData(
    val id: String,
    val color: ScrewColor,
    val startPos: Offset,
    val targetPos: Offset
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoodNutsScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: WoodNutsViewModel = viewModel(
        factory = WoodNutsViewModelFactory(streakRepository, mode, puzzleId)
    )
) {
    val stateOpt by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showVictoryDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Wood Screws") }

    val state = stateOpt ?: return

    val boardSlotPositions = remember { mutableStateMapOf<String, Offset>() }
    val traySlotPositions = remember { mutableStateMapOf<Int, Offset>() }
    val flyingScrews = remember { mutableStateListOf<FlyingScrewData>() }
    var rootOffset by remember { mutableStateOf(Offset.Zero) }

    // Trigger flying screw animation when a bolt is unscrewed
    LaunchedEffect(state.lastRemovedBoltId) {
        val lastBoltId = state.lastRemovedBoltId
        if (lastBoltId != null) {
            val removedBolt = state.bolts.find { it.id == lastBoltId }
            if (removedBolt != null) {
                val matchingBoard = state.activeBoards.find { it.color == removedBolt.color }
                val targetPos = if (matchingBoard != null) {
                    val key = "${matchingBoard.id}_${(matchingBoard.filledCount - 1).coerceAtLeast(0)}"
                    boardSlotPositions[key] ?: Offset(200f, 150f)
                } else {
                    val trayIdx = (state.trayScrews.size - 1).coerceAtLeast(0)
                    traySlotPositions[trayIdx] ?: Offset(200f, 250f)
                }

                // Add flying screw animation
                val startPos = Offset(300f, 600f) // Canvas relative start
                val animData = FlyingScrewData(
                    id = "${lastBoltId}_${System.currentTimeMillis()}",
                    color = removedBolt.color,
                    startPos = startPos,
                    targetPos = targetPos
                )
                flyingScrews.add(animData)
            }
        }
    }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
            showVictoryDialog = true
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Wooden planks are held together by colored bolts. Tap a bolt to unscrew it. Screws will automatically fly into matching 3-hole color boards. If no matching board has space, the screw goes to the 5-hole overflow tray. When a 3-hole board fills, it clears and next queued board arrives (auto-draining matching screws from tray). Remove all planks and clear all boards to win! Don't let the 5-hole tray overflow!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isFailed) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Level Failed!", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) },
            text = { Text(state.failedReason ?: "Tray full! No space for unscrewed screw.") },
            confirmButton = {
                Button(onClick = { viewModel.startNewGame() }) {
                    Text("Try Again")
                }
            },
            dismissButton = {
                TextButton(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                    Text("Main Menu")
                }
            }
        )
    }

    if (showVictoryDialog) {
        val currentPuzzle = if (puzzleId != null) com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated.ALL_LEVELS.firstOrNull { it.id == puzzleId } else null
        val currentDifficulty = currentPuzzle?.difficulty
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val sameDiffPuzzles = if (currentPuzzle != null) com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated.ALL_LEVELS.filter { it.difficulty == currentPuzzle.difficulty } else com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated.ALL_LEVELS
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex + 1 < sameDiffPuzzles.size) sameDiffPuzzles[currentIndex + 1] else sameDiffPuzzles.firstOrNull()
            if (nextPuzzle != null) {
                {
                    showVictoryDialog = false
                    navController.navigate("game/woodnuts/puzzle/${nextPuzzle.id}") {
                        popUpTo("home")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "All planks removed & color boards cleared in ${state.moves} moves!",
            mode = mode,
            gameId = "woodnuts",
            currentDifficulty = currentDifficulty,
            onMainMenuClick = {
                showVictoryDialog = false
                navController.navigate("home") { popUpTo(0) }
            },
            onBackToListClick = {
                showVictoryDialog = false
                val route = if (currentDifficulty != null) "woodnuts/puzzles?difficulty=$currentDifficulty" else "woodnuts/puzzles"
                navController.navigate(route) { popUpTo("home") }
            },
            onPlayAgainClick = {
                showVictoryDialog = false
                viewModel.startNewGame()
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    StandardGameLayout(
        title = "Wood Screws",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {
            val refreshInteractionSource = remember { MutableInteractionSource() }
            IconButton(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.startNewGame()
                },
                modifier = Modifier.animateTapFeedback(refreshInteractionSource),
                interactionSource = refreshInteractionSource
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .onGloballyPositioned { rootOffset = it.positionInRoot() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Difficulty: ${state.level.difficulty}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )

                // === 3-Hole Active Boards Section (with enter/leave animations) ===
                ActiveBoardsRow(
                    boards = state.activeBoards,
                    rootOffset = rootOffset,
                    slotPositions = boardSlotPositions
                )

                Spacer(modifier = Modifier.height(4.dp))

                // === 5-Hole Overflow Tray Section ===
                OverflowTray(
                    trayScrews = state.trayScrews,
                    rootOffset = rootOffset,
                    slotPositions = traySlotPositions
                )

                BoxWithConstraints(
                    modifier = Modifier.weight(1f).fillMaxSize().padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val density = LocalDensity.current
                    val rows = state.level.rows
                    val cols = state.level.cols
                    val ic = cols + 1
                    val ir = rows + 1

                    val cellDp = minOf(maxWidth / ic, maxHeight / ir)
                    val cellPx = with(density) { cellDp.toPx() }
                    val gridW = cellDp * ic
                    val gridH = cellDp * ir

                    val boltRadius = cellPx * 0.18f
                    val plankCorner = cellPx * 0.12f
                    val plankPad = cellPx * 0.08f
                    val strokePx = with(density) { 2.dp.toPx() }

                    val boltAngles = state.bolts.map { bolt ->
                        val animatedAngle by androidx.compose.animation.core.animateFloatAsState(
                            targetValue = if (bolt.isUnscrewing) 1f else 0f,
                            animationSpec = tween(BOLT_UNSCREW_DURATION),
                            label = "boltUnscrew"
                        )
                        bolt.id to animatedAngle
                    }.toMap()

                    // Clamp bolt grid-line coords into their plank cell span so edge
                    // bolts line up with the plank instead of the adjacent empty cell.
                    val boltCells = state.bolts.associate { bolt ->
                        bolt.id to clampBoltCellToPlanks(bolt, state.planks)
                    }

                    Canvas(
                        modifier = Modifier
                            .size(gridW, gridH)
                            .animateEntrance(trigger = state.level.id)
                            .background(Color(0xFF2E2E2E), shape = RoundedCornerShape(12.dp))
                            .pointerInput(state.bolts) {
                                detectTapGestures { offset ->
                                    val touchRadius = cellPx * 0.45f
                                    val bolt = state.bolts.find { b ->
                                        if (b.removed || b.isUnscrewing) return@find false
                                        val (cc, cr) = boltCells[b.id] ?: (b.col.toFloat() to b.row.toFloat())
                                        val cx = (cc + 0.5f) * cellPx
                                        val cy = (cr + 0.5f) * cellPx
                                        val dx = offset.x - cx
                                        val dy = offset.y - cy
                                        sqrt(dx * dx + dy * dy) <= touchRadius
                                    }
                                    if (bolt != null) {
                                        soundManager.playSound(SoundManager.SOUND_ID_METAL_SHING, 0.9f)
                                        viewModel.removeBolt(bolt.id)
                                    }
                                }
                            }
                    ) {
                        // === PASS 1: Draw planks in depth order ===
                        val sortedPlanks = state.planks.sortedBy { it.depthLayer }
                        for (plank in sortedPlanks) {
                            if (plank.removed) continue

                            val colorIndex = state.planks.indexOf(plank) % PLANK_PALETTE.size
                            val color = PLANK_PALETTE[colorIndex]

                            drawPhysicsPlank(plank, color, cellPx, plankPad, plankCorner, strokePx)
                        }

                        // === PASS 2: Draw bolts ===
                        for (bolt in state.bolts) {
                            if (bolt.removed) continue
                            val unscrewProgress = boltAngles[bolt.id] ?: 0f

                            val (cellCol, cellRow) = boltCells[bolt.id] ?: (bolt.col.toFloat() to bolt.row.toFloat())
                            val cx = (cellCol + 0.5f) * cellPx
                            val cy = (cellRow + 0.5f) * cellPx

                            // Drop shadow for bolt
                            drawCircle(Color.Black.copy(alpha = 0.5f), boltRadius, Offset(cx + strokePx * 1.5f, cy + strokePx * 1.5f))

                            if (unscrewProgress > 0f) {
                                drawUnscrewingBolt(cx, cy, boltRadius, unscrewProgress, bolt.color)
                            } else {
                                drawNormalBolt(cx, cy, boltRadius, bolt.color)
                            }
                        }
                    }
                }

                Text(
                    text = "Moves: ${state.moves}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // === Flying Screws Overlay Animation ===
            for (flyingData in flyingScrews.toList()) {
                key(flyingData.id) {
                    FlyingScrewOverlay(
                        data = flyingData,
                        onComplete = { flyingScrews.remove(flyingData) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ActiveBoardsRow(
    boards: List<ColorBoard>,
    rootOffset: Offset,
    slotPositions: MutableMap<String, Offset>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 3) {
            val board = boards.getOrNull(i)
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = board,
                    transitionSpec = {
                        (slideInVertically { -it / 2 } + fadeIn() + scaleIn(initialScale = 0.85f)) togetherWith
                                (slideOutVertically { it / 2 } + fadeOut() + scaleOut(targetScale = 0.85f))
                    },
                    label = "boardTransition"
                ) { currentBoard ->
                    if (currentBoard != null) {
                        val boardBgColor = Color(currentBoard.color.colorHex)
                        val isComplete = currentBoard.filledCount == 3
                        val scale by animateFloatAsState(
                            targetValue = if (isComplete) 1.08f else 1f,
                            animationSpec = spring(dampingRatio = 0.5f),
                            label = "boardPulse"
                        )
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF3E2723)),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (isComplete) 8.dp else 4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp)
                            ) {
                                Text(
                                    text = currentBoard.color.displayName + if (isComplete) " ✓" else "",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = boardBgColor
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    for (slot in 0 until 3) {
                                        val isFilled = slot < currentBoard.filledCount
                                        val slotScale by animateFloatAsState(
                                            targetValue = if (isFilled) 1.2f else 1f,
                                            animationSpec = spring(dampingRatio = 0.4f),
                                            label = "slotFill"
                                        )
                                        Canvas(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .onGloballyPositioned { coordinates ->
                                                    val pos = coordinates.positionInRoot() - rootOffset
                                                    slotPositions["${currentBoard.id}_$slot"] = pos
                                                }
                                                .graphicsLayer {
                                                    scaleX = slotScale
                                                    scaleY = slotScale
                                                }
                                        ) {
                                            val r = size.minDimension / 2f
                                            val center = Offset(size.width / 2f, size.height / 2f)
                                            if (isFilled) {
                                                drawCircle(Color(0xFF37474F), r, center)
                                                drawCircle(boardBgColor, r * 0.75f, center)
                                                drawCircle(Color.White.copy(alpha = 0.5f), r * 0.35f, center)
                                            } else {
                                                drawCircle(Color(0xFF1E1E1E), r, center)
                                                drawCircle(Color.Black.copy(alpha = 0.6f), r * 0.8f, center)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OverflowTray(
    trayScrews: List<ScrewColor>,
    rootOffset: Offset,
    slotPositions: MutableMap<Int, Offset>
) {
    val isWarning = trayScrews.size >= 4
    val strokeColor = if (trayScrews.size >= 5) Color(0xFFE53935) else if (isWarning) Color(0xFFFB8C00) else Color(0xFF424242)
    
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF263238)),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, strokeColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
        ) {
            Text(
                text = "Tray:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(end = 8.dp)
            )
            for (i in 0 until 5) {
                val screwColor = trayScrews.getOrNull(i)
                val isFilled = screwColor != null
                val scale by animateFloatAsState(
                    targetValue = if (isFilled) 1.15f else 1f,
                    animationSpec = spring(dampingRatio = 0.4f),
                    label = "traySlotScale"
                )
                Canvas(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(22.dp)
                        .onGloballyPositioned { coordinates ->
                            val pos = coordinates.positionInRoot() - rootOffset
                            slotPositions[i] = pos
                        }
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                ) {
                    val r = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    if (screwColor != null) {
                        drawCircle(Color(0xFF37474F), r, center)
                        drawCircle(Color(screwColor.colorHex), r * 0.75f, center)
                        drawCircle(Color.White.copy(alpha = 0.5f), r * 0.35f, center)
                    } else {
                        drawCircle(Color(0xFF151515), r, center)
                        drawCircle(Color.Black.copy(alpha = 0.7f), r * 0.8f, center)
                    }
                }
            }
        }
    }
}

@Composable
private fun FlyingScrewOverlay(
    data: FlyingScrewData,
    onComplete: () -> Unit
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(data.id) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 380, easing = FastOutSlowInEasing)
        )
        onComplete()
    }

    val t = progress.value
    val currX = data.startPos.x + (data.targetPos.x - data.startPos.x) * t
    val arcHeight = sin(t * PI.toFloat()) * 120f
    val currY = data.startPos.y + (data.targetPos.y - data.startPos.y) * t - arcHeight
    val currScale = 1f + sin(t * PI.toFloat()) * 0.35f
    val spinAngle = t * 720f

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                translationX = currX
                translationY = currY
                scaleX = currScale
                scaleY = currScale
                rotationZ = spinAngle
            }
    ) {
        val r = 14.dp.toPx()
        val center = Offset.Zero
        drawCircle(Color(0xFF212121), r, center)
        drawCircle(Color(0xFF78909C), r * 0.85f, center)
        drawCircle(Color(data.color.colorHex), r * 0.65f, center)
        drawCircle(Color.White.copy(alpha = 0.5f), r * 0.3f, Offset(-r * 0.15f, -r * 0.15f))
    }
}

private fun DrawScope.drawPhysicsPlank(
    plank: Plank,
    color: Color,
    cellPx: Float,
    pad: Float,
    corner: Float,
    stroke: Float
) {
    val w = (plank.endCol - plank.startCol + 1) * cellPx - pad * 2
    val h = (plank.endRow - plank.startRow + 1) * cellPx - pad * 2

    val cx: Float
    val cy: Float
    val angle: Float

    if (plank.transform != null) {
        cx = plank.transform.x * cellPx
        cy = plank.transform.y * cellPx
        angle = plank.transform.angle
    } else {
        // Fallback before first physics step
        cx = plank.startCol * cellPx + (plank.endCol - plank.startCol + 1) * cellPx / 2f
        cy = plank.startRow * cellPx + (plank.endRow - plank.startRow + 1) * cellPx / 2f
        angle = plank.angle
    }
    
    val left = cx - w / 2f
    val top = cy - h / 2f

    rotate(angle, Offset(cx, cy)) {
        // Drop shadow
        drawRoundRect(Color.Black.copy(alpha = 0.5f), Offset(left + stroke * 2, top + stroke * 2), Size(w, h), CornerRadius(corner, corner))
        // Base body
        drawRoundRect(color, Offset(left, top), Size(w, h), CornerRadius(corner, corner))
        // Bevel highlight (top/left)
        drawRoundRect(Color.White.copy(alpha = 0.25f), Offset(left + stroke, top + stroke), Size(w - stroke * 2, h - stroke * 2), CornerRadius(corner, corner), style = Stroke(stroke))
        // Inner shadow/outline
        drawRoundRect(Color.Black.copy(alpha = 0.3f), Offset(left, top), Size(w, h), CornerRadius(corner, corner), style = Stroke(stroke * 2))
    }
}

private fun DrawScope.drawNormalBolt(cx: Float, cy: Float, radius: Float, screwColor: ScrewColor) {
    drawCircle(Color(0xFF212121), radius, Offset(cx, cy))
    drawCircle(Color(0xFF78909C), radius * 0.85f, Offset(cx, cy))
    drawCircle(Color(screwColor.colorHex), radius * 0.65f, Offset(cx, cy))
    drawCircle(Color.White.copy(alpha = 0.4f), radius * 0.3f, Offset(cx - radius * 0.15f, cy - radius * 0.15f))
    
    // Cross-slot indicator
    val slotLen = radius * 0.45f
    drawLine(Color.Black.copy(alpha = 0.4f), Offset(cx - slotLen, cy), Offset(cx + slotLen, cy), radius * 0.12f)
    drawLine(Color.Black.copy(alpha = 0.4f), Offset(cx, cy - slotLen), Offset(cx, cy + slotLen), radius * 0.12f)
}

private fun DrawScope.drawUnscrewingBolt(cx: Float, cy: Float, radius: Float, progress: Float, screwColor: ScrewColor) {
    val alpha = 1f - progress
    val scale = 1f - 0.4f * progress
    val r = radius * scale
    val spin = progress * 360f

    rotate(spin, Offset(cx, cy)) {
        drawCircle(Color(0xFF212121).copy(alpha = alpha), r, Offset(cx, cy))
        drawCircle(Color(0xFF78909C).copy(alpha = alpha), r * 0.85f, Offset(cx, cy))
        drawCircle(Color(screwColor.colorHex).copy(alpha = alpha), r * 0.65f, Offset(cx, cy))
        drawCircle(Color.White.copy(alpha = 0.4f * alpha), r * 0.3f, Offset(cx - r * 0.15f, cy - r * 0.15f))

        // Cross-slot indicator
        val slotLen = r * 0.45f
        drawLine(Color.Black.copy(alpha = 0.4f * alpha), Offset(cx - slotLen, cy), Offset(cx + slotLen, cy), r * 0.12f)
        drawLine(Color.Black.copy(alpha = 0.4f * alpha), Offset(cx, cy - slotLen), Offset(cx, cy + slotLen), r * 0.12f)
    }
}
