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
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModel
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModelFactory
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import kotlin.math.sqrt

private val PLANK_PALETTE = listOf(
    Color(0xFF8D6E63), Color(0xFFA1887F), Color(0xFFBCAAA4),
    Color(0xFF6D4C41), Color(0xFF5D4037), Color(0xFF4E342E),
    Color(0xFFD7CCC8), Color(0xFFEFEBE9), Color(0xFF795548),
    Color(0xFF8B6F5E), Color(0xFFA0897C), Color(0xFFB8A69B)
)

private const val MAX_TILT_DEGREES = 18f
private const val BOLT_UNSCREW_DURATION = 350

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
    val completionRepo = remember { PuzzleCompletionRepository(context, "Wood Nuts") }

    val state = stateOpt ?: return

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
            instructions = "Wooden planks are held together by bolts. Tap a bolt to unscrew it. When the last bolt holding a plank is removed, the plank falls off. Planks falling may expose other bolts and trigger chain reactions. Remove all planks to win!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (showVictoryDialog) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val all = WoodNutsPregenerated.ALL_LEVELS
            val i = all.indexOfFirst { it.id == puzzleId }
            val nextId = if (i != -1 && i + 1 < all.size) all[i + 1].id else null
            if (nextId != null) {
                {
                    showVictoryDialog = false
                    navController.popBackStack()
                    navController.navigate("game/woodnuts/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "All planks removed in ${state.moves} moves!",
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

    StandardGameLayout(
        title = "Wood Nuts & Bolts",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                viewModel.startNewGame()
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
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
                    modifier = Modifier.padding(top = 8.dp)
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

                    // State for plank falling animation
                    val fallAnimProgress = remember { mutableStateMapOf<String, Float>() }

                    val scope = rememberCoroutineScope()

                    val boltAngles = state.bolts.map { bolt ->
                        val animatedAngle by androidx.compose.animation.core.animateFloatAsState(
                            targetValue = if (bolt.isUnscrewing) 1f else 0f,
                            animationSpec = tween(BOLT_UNSCREW_DURATION),
                            label = "boltUnscrew"
                        )
                        bolt.id to animatedAngle
                    }.toMap()

                    val plankAngles = state.planks.map { plank ->
                        val animatedAngle by androidx.compose.animation.core.animateFloatAsState(
                            targetValue = plank.angle,
                            animationSpec = androidx.compose.animation.core.spring(
                                dampingRatio = 0.6f,
                                stiffness = 300f
                            ),
                            label = "plankAngle"
                        )
                        plank.id to animatedAngle
                    }.toMap()

                    // Trigger plank falling animation on new removal
                    LaunchedEffect(state.lastRemovedPlankId) {
                        state.lastRemovedPlankId?.let { id ->
                            if (id !in fallAnimProgress) {
                                fallAnimProgress[id] = 0f
                                val anim = Animatable(0f)
                                anim.animateTo(1f, animationSpec = tween(500))
                                fallAnimProgress[id] = 1f
                            }
                        }
                    }

                    Canvas(
                        modifier = Modifier
                            .size(gridW, gridH)
                            .background(Color(0xFF2E2E2E))
                            .pointerInput(state.bolts) {
                                detectTapGestures { offset ->
                                    val touchRadius = cellPx * 0.45f
                                    val bolt = state.bolts.find { b ->
                                        if (b.removed || b.isUnscrewing) return@find false
                                        val cx = b.col * cellPx
                                        val cy = b.row * cellPx
                                        val dx = offset.x - cx
                                        val dy = offset.y - cy
                                        sqrt(dx * dx + dy * dy) <= touchRadius
                                    }
                                    if (bolt != null) {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK, 0.8f)
                                        viewModel.removeBolt(bolt.id)
                                    }
                                }
                            }
                    ) {
                        // === PASS 1: Draw flat & tilted planks ===
                        for (plank in state.planks) {
                            if (plank.removed && fallAnimProgress.containsKey(plank.id)) {
                                // drawn in pass 2
                                continue
                            }
                            if (plank.removed && !fallAnimProgress.containsKey(plank.id)) {
                                continue
                            }

                            val colorIndex = state.planks.indexOf(plank) % PLANK_PALETTE.size
                            val color = PLANK_PALETTE[colorIndex]

                            val remainingBolts = plank.boltIds
                                .mapNotNull { id -> state.bolts.find { it.id == id } }
                                .filter { !it.removed && !it.isUnscrewing }

                            val currentAngle = plankAngles[plank.id] ?: 0f

                            drawRotatedPlank(plank, remainingBolts, color, cellPx, plankPad, plankCorner, strokePx, currentAngle)
                        }

                        // === PASS 2: Draw falling planks (on top) ===
                        for (plank in state.planks) {
                            if (!plank.removed) continue
                            val progress = fallAnimProgress[plank.id] ?: continue
                            if (progress >= 1f) continue

                            val colorIndex = state.planks.indexOf(plank) % PLANK_PALETTE.size
                            drawFallingPlank(plank, PLANK_PALETTE[colorIndex], cellPx, plankPad, plankCorner, strokePx, progress)
                        }

                        // === PASS 3: Draw bolts ===
                        for (bolt in state.bolts) {
                            if (bolt.removed) continue
                            val unscrewProgress = boltAngles[bolt.id] ?: 0f

                            val cx = bolt.col * cellPx
                            val cy = bolt.row * cellPx

                            // Drop shadow for bolt
                            drawCircle(Color.Black.copy(alpha = 0.5f), boltRadius, Offset(cx + strokePx * 1.5f, cy + strokePx * 1.5f))

                            if (unscrewProgress > 0f) {
                                drawUnscrewingBolt(cx, cy, boltRadius, unscrewProgress)
                            } else {
                                drawNormalBolt(cx, cy, boltRadius)
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
        }
    }
}

private fun DrawScope.drawRotatedPlank(
    plank: Plank,
    remainingBolts: List<Bolt>,
    color: Color,
    cellPx: Float,
    pad: Float,
    corner: Float,
    stroke: Float,
    angle: Float
) {
    val pivot = if (remainingBolts.isNotEmpty()) {
        val sx = remainingBolts.map { it.col.toFloat() * cellPx }.average().toFloat()
        val sy = remainingBolts.map { it.row.toFloat() * cellPx }.average().toFloat()
        Offset(sx, sy)
    } else {
        val left = plank.startCol * cellPx + pad
        val top = plank.startRow * cellPx + pad
        val w = (plank.endCol - plank.startCol + 1) * cellPx - pad * 2
        val h = (plank.endRow - plank.startRow + 1) * cellPx - pad * 2
        Offset(left + w / 2f, top + h / 2f)
    }

    rotate(angle, pivot) {
        val left = plank.startCol * cellPx + pad
        val top = plank.startRow * cellPx + pad
        val w = (plank.endCol - plank.startCol + 1) * cellPx - pad * 2
        val h = (plank.endRow - plank.startRow + 1) * cellPx - pad * 2

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

private fun DrawScope.drawFallingPlank(
    plank: Plank,
    color: Color,
    cellPx: Float,
    pad: Float,
    corner: Float,
    stroke: Float,
    progress: Float
) {
    val alpha = 1f - progress
    val fallDistance = cellPx * 3f * progress

    // Random-ish spin based on plank id
    val spinDir = if (plank.id.hashCode() % 2 == 0) 1f else -1f
    val spin = spinDir * progress * 35f

    val left = plank.startCol * cellPx + pad
    val top = plank.startRow * cellPx + pad
    val w = (plank.endCol - plank.startCol + 1) * cellPx - pad * 2
    val h = (plank.endRow - plank.startRow + 1) * cellPx - pad * 2

    val centerX = left + w / 2f
    val centerY = top + h / 2f

    rotate(spin, Offset(centerX, centerY + fallDistance)) {
        val transl = top + fallDistance
        
        drawRoundRect(Color.Black.copy(alpha = alpha * 0.5f), Offset(left + stroke * 2, transl + stroke * 2), Size(w, h), CornerRadius(corner, corner))
        drawRoundRect(color.copy(alpha = alpha), Offset(left, transl), Size(w, h), CornerRadius(corner, corner))
        drawRoundRect(Color.White.copy(alpha = alpha * 0.25f), Offset(left + stroke, transl + stroke), Size(w - stroke * 2, h - stroke * 2), CornerRadius(corner, corner), style = Stroke(stroke))
        drawRoundRect(Color.Black.copy(alpha = alpha * 0.3f), Offset(left, transl), Size(w, h), CornerRadius(corner, corner), style = Stroke(stroke * 2))
    }
}

private fun DrawScope.drawNormalBolt(cx: Float, cy: Float, radius: Float) {
    drawCircle(Color(0xFF37474F), radius, Offset(cx, cy))
    drawCircle(Color(0xFF78909C), radius * 0.7f, Offset(cx, cy))
    drawCircle(Color(0xFFB0BEC5), radius * 0.4f, Offset(cx, cy))
}

private fun DrawScope.drawUnscrewingBolt(cx: Float, cy: Float, radius: Float, progress: Float) {
    val alpha = 1f - progress
    val scale = 1f - 0.4f * progress
    val r = radius * scale
    val spin = progress * 360f

    rotate(spin, Offset(cx, cy)) {
        drawCircle(Color(0xFF37474F).copy(alpha = alpha), r, Offset(cx, cy))
        drawCircle(Color(0xFF78909C).copy(alpha = alpha), r * 0.7f, Offset(cx, cy))
        drawCircle(Color(0xFFB0BEC5).copy(alpha = alpha), r * 0.4f, Offset(cx, cy))

        // Cross-slot indicator
        val slotLen = r * 0.5f
        drawLine(Color(0xFF546E7A).copy(alpha = alpha), Offset(cx - slotLen, cy), Offset(cx + slotLen, cy), r * 0.15f)
        drawLine(Color(0xFF546E7A).copy(alpha = alpha), Offset(cx, cy - slotLen), Offset(cx, cy + slotLen), r * 0.15f)
    }
}
