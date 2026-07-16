package com.funkyotc.puzzleverse.hashi.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.funkyotc.puzzleverse.hashi.data.HashiPregenerated
import com.funkyotc.puzzleverse.hashi.data.Island
import com.funkyotc.puzzleverse.hashi.viewmodel.HashiViewModel
import com.funkyotc.puzzleverse.hashi.viewmodel.HashiViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HashiScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    forceNewGame: Boolean = false,
    puzzleId: String? = null,
    viewModel: HashiViewModel = viewModel(factory = HashiViewModelFactory(streakRepository, mode, puzzleId))
) {
    val puzzle by viewModel.puzzle.collectAsState()
    val bridges by viewModel.bridges.collectAsState()
    val isGameWon by viewModel.isGameWon.collectAsState()
    
    var showHowToDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val soundManager = LocalSoundManager.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Hashi") }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            title = "How To Play Hashi",
            instructions = "Connect all islands with bridges.\n\n1. Bridges must run horizontally or vertically.\n2. Bridges cannot cross each other.\n3. The number on each island indicates how many bridges connect to it.\n4. You can have at most two bridges connecting any pair of islands.\n\nDrag between two islands to create or upgrade a bridge. Drag again to remove it.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (isGameWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = HashiPregenerated.PUZZLES_BY_DIFFICULTY.values.flatten()
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) {
                val nextId = allPuzzles[currentIndex + 1].id
                {
                    navController.popBackStack()
                    navController.navigate("game/hashi/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "You Win!",
            message = "You solved the Hashi puzzle!",
            mode = mode,
            onMainMenuClick = {
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = {
                if (mode == "daily") {
                    navController.navigate("game/hashi/standard") { popUpTo("home") }
                } else {
                    navController.popBackStack()
                    navController.navigate("game/hashi/standard/new")
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    StandardGameLayout(
        title = "Hashi",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (puzzle != null) {
                HashiBoard(
                    puzzle = puzzle!!,
                    bridges = bridges,
                    onToggleBridge = { i1, i2 ->
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.toggleBridge(i1, i2)
                    }
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun HashiBoard(
    puzzle: com.funkyotc.puzzleverse.hashi.data.HashiPuzzle,
    bridges: List<com.funkyotc.puzzleverse.hashi.viewmodel.Bridge>,
    onToggleBridge: (Island, Island) -> Unit
) {
    var dragStartIsland by remember { mutableStateOf<Island?>(null) }
    var currentDragPos by remember { mutableStateOf<Offset?>(null) }
    
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    val bridgeColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(surfaceColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        
        val gridWidth = puzzle.width
        val gridHeight = puzzle.height
        
        val cellWidth = width / gridWidth
        val cellHeight = height / gridHeight
        val islandRadius = minOf(cellWidth, cellHeight) * 0.35f

        fun getIslandAt(x: Float, y: Float): Island? {
            val col = (x / cellWidth).toInt()
            val row = (y / cellHeight).toInt()
            return puzzle.islands.find { it.x == col && it.y == row }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            dragStartIsland = getIslandAt(offset.x, offset.y)
                            currentDragPos = offset
                        },
                        onDrag = { change, _ ->
                            currentDragPos = change.position
                        },
                        onDragEnd = {
                            val endIsland = currentDragPos?.let { getIslandAt(it.x, it.y) }
                            if (dragStartIsland != null && endIsland != null && dragStartIsland != endIsland) {
                                // Must be horizontal or vertical
                                if (dragStartIsland!!.x == endIsland.x || dragStartIsland!!.y == endIsland.y) {
                                    onToggleBridge(dragStartIsland!!, endIsland)
                                }
                            }
                            dragStartIsland = null
                            currentDragPos = null
                        },
                        onDragCancel = {
                            dragStartIsland = null
                            currentDragPos = null
                        }
                    )
                }
        ) {
            // Draw Bridges
            bridges.forEach { bridge ->
                val startX = bridge.island1.x * cellWidth + cellWidth / 2
                val startY = bridge.island1.y * cellHeight + cellHeight / 2
                val endX = bridge.island2.x * cellWidth + cellWidth / 2
                val endY = bridge.island2.y * cellHeight + cellHeight / 2

                if (bridge.count == 1) {
                    drawLine(
                        color = bridgeColor,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 6f
                    )
                } else if (bridge.count == 2) {
                    val isHorizontal = bridge.island1.y == bridge.island2.y
                    val offset = 6f
                    if (isHorizontal) {
                        drawLine(
                            color = bridgeColor,
                            start = Offset(startX, startY - offset),
                            end = Offset(endX, endY - offset),
                            strokeWidth = 6f
                        )
                        drawLine(
                            color = bridgeColor,
                            start = Offset(startX, startY + offset),
                            end = Offset(endX, endY + offset),
                            strokeWidth = 6f
                        )
                    } else {
                        drawLine(
                            color = bridgeColor,
                            start = Offset(startX - offset, startY),
                            end = Offset(endX - offset, endY),
                            strokeWidth = 6f
                        )
                        drawLine(
                            color = bridgeColor,
                            start = Offset(startX + offset, startY),
                            end = Offset(endX + offset, endY),
                            strokeWidth = 6f
                        )
                    }
                }
            }

            // Draw current drag line
            if (dragStartIsland != null && currentDragPos != null) {
                val startX = dragStartIsland!!.x * cellWidth + cellWidth / 2
                val startY = dragStartIsland!!.y * cellHeight + cellHeight / 2
                drawLine(
                    color = primaryColor.copy(alpha = 0.5f),
                    start = Offset(startX, startY),
                    end = currentDragPos!!,
                    strokeWidth = 6f
                )
            }

            // Draw Islands
            val paint = Paint().asFrameworkPaint()
            puzzle.islands.forEach { island ->
                val cx = island.x * cellWidth + cellWidth / 2
                val cy = island.y * cellHeight + cellHeight / 2
                
                // Determine if fulfilled
                val connectedBridges = bridges.filter { it.island1 == island || it.island2 == island }.sumOf { it.count }
                val isFulfilled = connectedBridges == island.requiredBridges
                val isOver = connectedBridges > island.requiredBridges

                val islandColor = when {
                    isOver -> Color.Red
                    isFulfilled -> primaryColor.copy(alpha = 0.5f)
                    else -> primaryColor
                }

                drawCircle(
                    color = islandColor,
                    radius = islandRadius,
                    center = Offset(cx, cy)
                )

                drawIntoCanvas { canvas ->
                    paint.color = android.graphics.Color.WHITE
                    paint.textSize = islandRadius * 1.2f
                    paint.textAlign = android.graphics.Paint.Align.CENTER
                    paint.isAntiAlias = true
                    // adjust y for centering text vertically
                    val textOffset = (paint.descent() + paint.ascent()) / 2
                    canvas.nativeCanvas.drawText(
                        island.requiredBridges.toString(),
                        cx,
                        cy - textOffset,
                        paint
                    )
                }
            }
        }
    }
}
