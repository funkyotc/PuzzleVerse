package com.funkyotc.puzzleverse.shapes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.shapes.util.GeometryUtils
import com.funkyotc.puzzleverse.shapes.viewmodel.ShapesViewModel
import com.funkyotc.puzzleverse.shapes.viewmodel.ShapesViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.animation.core.animateFloatAsState
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.funkyotc.puzzleverse.core.ui.animateEntrance
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.core.ui.PuzzleVerseAnimationSpecs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapesScreen(
    navController: NavController, 
    mode: String? = "standard",
    puzzleId: String? = null,
    settingsRepository: SettingsRepository,
    streakRepository: StreakRepository,
    viewModel: ShapesViewModel? = null
) {
    val context = LocalContext.current
    val vm = viewModel ?: viewModel(factory = ShapesViewModelFactory(context, mode, puzzleId))
    val puzzle by vm.puzzle.collectAsState()
    val soundManager = LocalSoundManager.current
    val isGameWon by vm.isGameWon.collectAsState()
    var selectedPieceId by remember { mutableStateOf<Int?>(null) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHintDialog by remember { mutableStateOf(false) }

    val completionRepo = remember { com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository(context, "shapes") }

    if (showHintDialog) {
        GameConfirmDialog(
            title = "Use a Hint?",
            message = "Are you sure you want to use a hint to reveal part of the puzzle?",
            confirmLabel = "Yes",
            cancelLabel = "Cancel",
            onConfirm = {
                showHintDialog = false
                vm.hint()
            },
            onDismiss = { showHintDialog = false }
        )
    }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            } else if (mode == "daily") {
                val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                val streak = streakRepository.getStreak("shapes")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    streakRepository.saveStreak(newStreak)
                }
            }
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Drag and rotate the 7 Tangram pieces so they fit perfectly into the target silhouette without overlapping.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (isGameWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val currentPuzzle = com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.getPuzzleById(puzzleId)
            val sameDiffPuzzles = if (currentPuzzle != null) {
                com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated.ALL_PUZZLES
                    .filter { it.difficulty == currentPuzzle.difficulty }
            } else emptyList()
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex < sameDiffPuzzles.size - 1) {
                sameDiffPuzzles[currentIndex + 1]
            } else null
            if (nextPuzzle != null) {
                {
                    navController.navigate("game/shapes/puzzle/${nextPuzzle.id}") {
                        popUpTo("shapes/puzzles")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Congratulations!",
            message = "You solved the Tangram puzzle! All 7 pieces fit perfectly.",
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
                    navController.navigate("game/shapes/standard/new") { popUpTo("home") }
                } else {
                    vm.loadNewPuzzle()
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Puzzle",
            message = "Are you sure you want to start a new puzzle? Your current progress will be lost.",
            onConfirm = {
                vm.loadNewPuzzle()
                showNewGameDialog = false
                selectedPieceId = null
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Shapes",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null,
        bottomBar = {
            // Controls for the selected piece
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val rotateLInteractionSource = remember { MutableInteractionSource() }
                Button(
                    onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        selectedPieceId?.let { vm.rotatePiece(it, -45f) }
                    },
                    enabled = selectedPieceId != null,
                    interactionSource = rotateLInteractionSource,
                    modifier = Modifier.animateTapFeedback(rotateLInteractionSource)
                ) {
                    Text("Rotate L")
                }
                Spacer(modifier = Modifier.width(16.dp))
                val rotateRInteractionSource = remember { MutableInteractionSource() }
                Button(
                    onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        selectedPieceId?.let { vm.rotatePiece(it, 45f) }
                    },
                    enabled = selectedPieceId != null,
                    interactionSource = rotateRInteractionSource,
                    modifier = Modifier.animateTapFeedback(rotateRInteractionSource)
                ) {
                    Text("Rotate R")
                }
            }
        },
        actions = {
            val hintInteractionSource = remember { MutableInteractionSource() }
            IconButton(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHintDialog = true
                },
                interactionSource = hintInteractionSource,
                modifier = Modifier.animateTapFeedback(hintInteractionSource)
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Hint")
            }
        }
    ) { paddingValues ->
        val currentPuzzleState by rememberUpdatedState(puzzle)

        puzzle?.let { puzzleState ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val pieceAnimations = puzzleState.pieces.associate { piece ->
                    val animatedRotation by animateFloatAsState(
                        targetValue = piece.rotation,
                        animationSpec = PuzzleVerseAnimationSpecs.fastMovementSpec(),
                        label = "rotation_anim_${piece.id}"
                    )
                    piece.id to animatedRotation
                }

                val density = LocalDensity.current
                val actualWidth = with(density) { maxWidth.toPx() }
                val actualHeight = with(density) { maxHeight.toPx() }

                // Map virtual space [0..400, 0..700] cleanly to screen bounds
                val contentMinX = 0f
                val contentMaxX = 400f
                val contentMinY = 0f
                val contentMaxY = 700f
                val contentWidth = contentMaxX - contentMinX
                val contentHeight = contentMaxY - contentMinY

                val scaleX = actualWidth / contentWidth
                val scaleY = actualHeight / contentHeight
                val scale = minOf(scaleX, scaleY) * 0.98f

                val offsetX = (actualWidth - contentWidth * scale) / 2f
                val offsetY = (actualHeight - contentHeight * scale) / 2f

                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .animateEntrance(trigger = puzzleId)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { actualOffset ->
                                currentPuzzleState?.let { puzzleVal ->
                                    val virtualOffset = Offset(
                                        (actualOffset.x - offsetX) / scale,
                                        (actualOffset.y - offsetY) / scale
                                    )
                                    val clickedPiece = puzzleVal.pieces.findLast { piece ->
                                         GeometryUtils.isPointInPolygon(virtualOffset, piece.currentVertices)
                                    }
                                    if (clickedPiece != null) {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    }
                                    selectedPieceId = clickedPiece?.id
                                }
                            },
                            onDoubleTap = { actualOffset ->
                                currentPuzzleState?.let { puzzleVal ->
                                     val virtualOffset = Offset(
                                         (actualOffset.x - offsetX) / scale,
                                         (actualOffset.y - offsetY) / scale
                                     )
                                     val clickedPiece = puzzleVal.pieces.findLast { piece ->
                                          GeometryUtils.isPointInPolygon(virtualOffset, piece.currentVertices)
                                     }
                                     if (clickedPiece != null) {
                                         soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                         selectedPieceId = clickedPiece.id
                                         vm.rotatePiece(clickedPiece.id, 45f)
                                     }
                                }
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { actualOffset ->
                                currentPuzzleState?.let { puzzleVal ->
                                    val virtualOffset = Offset(
                                        (actualOffset.x - offsetX) / scale,
                                        (actualOffset.y - offsetY) / scale
                                    )
                                    val clickedPiece = puzzleVal.pieces.findLast { piece ->
                                         GeometryUtils.isPointInPolygon(virtualOffset, piece.currentVertices)
                                    }
                                    if (clickedPiece != null) {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        selectedPieceId = clickedPiece.id
                                    }
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                selectedPieceId?.let { id ->
                                    val virtualDragAmount = Offset(dragAmount.x / scale, dragAmount.y / scale)
                                    vm.movePieceDelta(id, virtualDragAmount)
                                }
                            },
                            onDragEnd = {
                                selectedPieceId?.let { id ->
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    vm.snapPiece(id)
                                }
                            },
                            onDragCancel = {
                                selectedPieceId?.let { id ->
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    vm.snapPiece(id)
                                }
                            }
                        )
                    }
                ) {
                    translate(offsetX, offsetY) {
                        scale(scale, pivot = Offset.Zero) {
                            // 1. Draw Blueprint Background Grid
                            for (x in 0..400 step 40) {
                                drawLine(
                                    color = Color(0x15FFFFFF),
                                    start = Offset(x.toFloat(), 0f),
                                    end = Offset(x.toFloat(), 700f),
                                    strokeWidth = 1f
                                )
                            }
                            for (y in 0..700 step 40) {
                                drawLine(
                                    color = Color(0x15FFFFFF),
                                    start = Offset(0f, y.toFloat()),
                                    end = Offset(400f, y.toFloat()),
                                    strokeWidth = 1f
                                )
                            }

                            // 2. Draw piece dock tray at the bottom (visually holds the unplaced pieces)
                            val trayPath = Path().apply {
                                addRoundRect(
                                    RoundRect(
                                        left = 15f,
                                        top = 425f,
                                        right = 385f,
                                        bottom = 685f,
                                        cornerRadius = CornerRadius(24f, 24f)
                                    )
                                )
                            }
                            drawPath(trayPath, Color.White.copy(alpha = 0.04f), style = Fill)
                            drawPath(
                                trayPath, 
                                Color.White.copy(alpha = 0.12f), 
                                style = Stroke(
                                    width = 1.5f,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f), 0f)
                                )
                            )

                            // 3. Draw Target Silhouette Centered in Upper Area
                            val targetPath = Path().apply {
                                if (puzzleState.target.vertices.isNotEmpty()) {
                                    val first = puzzleState.target.vertices.first()
                                    moveTo(first.x, first.y)
                                    puzzleState.target.vertices.drop(1).forEach {
                                        lineTo(it.x, it.y)
                                    }
                                    close()
                                }
                            }
                            
                            drawPath(targetPath, Color(0xFF1E293B).copy(alpha = 0.6f), style = Fill) // Slate fill
                            clipPath(targetPath) {
                                drawPath(
                                    targetPath, 
                                    Color(0xFF38BDF8), // Cyan silhouette outline
                                    style = Stroke(
                                        width = 5.0f,
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 4f), 0f)
                                    )
                                )
                            }

                            // 4. Draw Pieces
                            puzzleState.pieces.forEach { piece ->
                                val isSelected = piece.id == selectedPieceId
                                val animRotation = pieceAnimations[piece.id] ?: piece.rotation

                                val animatedVertices = GeometryUtils.transformPolygon(
                                    piece.initialVertices,
                                    piece.position,
                                    animRotation,
                                    isFlipped = piece.isFlipped
                                )

                                val piecePath = Path().apply {
                                    if (animatedVertices.isNotEmpty()) {
                                        val first = animatedVertices.first()
                                        moveTo(first.x, first.y)
                                        animatedVertices.drop(1).forEach {
                                            lineTo(it.x, it.y)
                                        }
                                        close()
                                    }
                                }
                                
                                // Draw 3D drop shadow
                                val shadowOffset = if (isSelected) Offset(3f, 6f) else Offset(1.5f, 3f)
                                val shadowAlpha = if (isSelected) 0.4f else 0.25f
                                val shadowPath = Path().apply {
                                    if (animatedVertices.isNotEmpty()) {
                                        val first = animatedVertices.first()
                                        moveTo(first.x + shadowOffset.x, first.y + shadowOffset.y)
                                        animatedVertices.drop(1).forEach {
                                            lineTo(it.x + shadowOffset.x, it.y + shadowOffset.y)
                                        }
                                        close()
                                    }
                                }
                                drawPath(shadowPath, Color.Black.copy(alpha = shadowAlpha), style = Fill)

                                // Draw piece fill and elegant border
                                drawPath(piecePath, piece.color, style = Fill)
                                clipPath(piecePath) {
                                    drawPath(
                                        piecePath, 
                                        if (isSelected) Color(0xFFFFD700) else Color.White.copy(alpha = 0.35f),
                                        style = Stroke(width = if (isSelected) 7.0f else 3.0f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}