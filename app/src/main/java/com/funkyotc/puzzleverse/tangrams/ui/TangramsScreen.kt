package com.funkyotc.puzzleverse.tangrams.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.tangrams.data.TangramsPregenerated
import com.funkyotc.puzzleverse.tangrams.model.TangramPiece

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangramsScreen(
    navController: NavController,
    mode: String? = "standard",
    puzzleId: String? = null,
    settingsRepository: SettingsRepository,
    streakRepository: StreakRepository,
    viewModel: TangramsViewModel? = null
) {
    val context = LocalContext.current
    val vm = viewModel ?: viewModel(factory = TangramsViewModelFactory(context, mode, puzzleId))
    
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val pieces by vm.pieces.collectAsState()
    val targetPath by vm.targetSilhouette.collectAsState()
    val isSolved by vm.isPuzzleSolved.collectAsState()
    val selectedPieceId by vm.selectedPieceId.collectAsState()
    val puzzleName by vm.currentPuzzleName.collectAsState()
    val currentPuzzleId by vm.currentPuzzleId.collectAsState()

    var activeDragPieceId by remember { mutableStateOf<Int?>(null) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { (configuration.screenHeightDp.dp - 150.dp).toPx() } // Subtract top bar / bottom bar approx

    val soundManager = LocalSoundManager.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "tangrams") }

    LaunchedEffect(screenWidthPx, screenHeightPx) {
        if (screenWidthPx > 0f && screenHeightPx > 0f) {
            vm.initialize(screenWidthPx, screenHeightPx)
        }
    }

    LaunchedEffect(isSolved) {
        if (isSolved) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && currentPuzzleId.isNotEmpty()) {
                completionRepo.markCompleted(currentPuzzleId)
            } else if (mode == "daily") {
                val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                val streak = streakRepository.getStreak("tangrams")
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
            instructions = "Drag and rotate the Tangram pieces so they fit perfectly into the target silhouette without overlapping. Pieces will magnetically snap into place.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (isSolved) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && currentPuzzleId.isNotEmpty()) {
            val currentPuzzle = TangramsPregenerated.getPuzzleById(currentPuzzleId)
            val sameDiffPuzzles = if (currentPuzzle != null) {
                TangramsPregenerated.ALL_PUZZLES.filter { it.difficulty == currentPuzzle.difficulty }
            } else emptyList()
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == currentPuzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex < sameDiffPuzzles.size - 1) {
                sameDiffPuzzles[currentIndex + 1]
            } else null
            if (nextPuzzle != null) {
                {
                    navController.navigate("game/tangrams/puzzle/${nextPuzzle.id}") {
                        popUpTo("tangrams/puzzles")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Congratulations!",
            message = "You solved the $puzzleName Tangram!",
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
                    navController.navigate("game/tangrams/standard/new") { popUpTo("home") }
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
            message = "Are you sure you want to start a new puzzle?",
            onConfirm = {
                vm.loadNewPuzzle()
                showNewGameDialog = false
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Tangrams",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val rotateInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                Button(
                    onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        selectedPieceId?.let { vm.rotatePiece(it) }
                    },
                    enabled = selectedPieceId != null,
                    interactionSource = rotateInteractionSource,
                    modifier = Modifier.animateTapFeedback(rotateInteractionSource)
                ) {
                    Text("Rotate")
                }
                val flipInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                Button(
                    onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        selectedPieceId?.let { vm.flipPiece(it) }
                    },
                    enabled = selectedPieceId != null,
                    interactionSource = flipInteractionSource,
                    modifier = Modifier.animateTapFeedback(flipInteractionSource)
                ) {
                    Text("Flip")
                }
                val resetInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                Button(
                    onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        vm.loadDefaultPieces()
                    },
                    interactionSource = resetInteractionSource,
                    modifier = Modifier.animateTapFeedback(resetInteractionSource)
                ) {
                    Text("Reset")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF0F172A)) // Modern Dark Slate
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = { offset ->
                                val hitPiece = pieces.reversed().firstOrNull { it.containsPoint(offset.x, offset.y) }
                                hitPiece?.let { piece ->
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    vm.rotatePiece(piece.id)
                                    vm.selectPiece(piece.id)
                                }
                            },
                            onTap = { offset ->
                                val hitPiece = pieces.reversed().firstOrNull { it.containsPoint(offset.x, offset.y) }
                                if (hitPiece != null) {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                }
                                vm.selectPiece(hitPiece?.id)
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val hitPiece = pieces.reversed().firstOrNull { it.containsPoint(offset.x, offset.y) }
                                hitPiece?.let { piece ->
                                    activeDragPieceId = piece.id
                                    vm.selectPiece(piece.id)
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                activeDragPieceId?.let { id ->
                                    vm.updatePiecePosition(id, dragAmount.x, dragAmount.y)
                                }
                            },
                            onDragEnd = {
                                activeDragPieceId?.let { id -> 
                                    soundManager.playSound(SoundManager.SOUND_ID_SNAP_CONNECT)
                                    vm.snapPiece(id) 
                                }
                                activeDragPieceId = null
                            },
                            onDragCancel = {
                                activeDragPieceId = null
                            }
                        )
                    }
            ) {
                // 1. Draw Target Silhouette
                targetPath?.let { path ->
                    val composePath = path.asComposePath()
                    // Fill silhouette
                    drawPath(
                        path = composePath,
                        color = Color(0xFF1E293B),
                        style = Fill
                    )
                    // Dashed stroke silhouette outline
                    drawPath(
                        path = composePath,
                        color = Color(0xFF64748B),
                        style = Stroke(
                            width = 4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    )
                }

                // 2. Draw Tangram Pieces
                pieces.forEach { piece ->
                    val piecePath = piece.getTransformedPath().asComposePath()
                    val isSelected = piece.id == selectedPieceId

                    // Draw Piece Solid Fill
                    drawPath(
                        path = piecePath,
                        color = piece.color,
                        style = Fill
                    )

                    // Draw Outer Stroke
                    drawPath(
                        path = piecePath,
                        color = if (isSelected) Color.White else Color.Black.copy(alpha = 0.5f),
                        style = Stroke(width = if (isSelected) 5f else 2.5f)
                    )
                }
            }
        }
    }
}
