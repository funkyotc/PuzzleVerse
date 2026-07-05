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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Info
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
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager

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
        AlertDialog(
            onDismissRequest = { showHintDialog = false },
            title = { Text("Use a Hint?") },
            text = { Text("Are you sure you want to use a hint to reveal part of the puzzle?") },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHintDialog = false
                    vm.hint() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHintDialog = false }) {
                    Text("Cancel")
                }
            }
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
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Drag and rotate the pieces so they fit perfectly into the target shape.") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("home") { popUpTo(0) }
                        }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/shapes/standard/new") { popUpTo("home") }
                        }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else {

                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHowToDialog = false
                }) {
                    Text("OK")

                }
                }
            }
        )
    }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { vm.loadNewPuzzle() },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle! All pieces fit perfectly.") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("home") { popUpTo(0) }
                        }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/shapes/standard/new") { popUpTo("home") }
                        }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else if (mode == "puzzle" && puzzleId != null) {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.popBackStack()
                        }) {
                            Text("Back to List")
                        }
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
                            Button(onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                navController.navigate("game/shapes/puzzle/${nextPuzzle.id}") {
                                    popUpTo("shapes/puzzles")
                                }
                            }) {
                                Text("Next Puzzle")
                            }
                        }
                    }
                } else {

                Button(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    vm.loadNewPuzzle()
                }) {
                    Text("New Puzzle")

                }
                }
            }
        )
    }
    
    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Puzzle") },
            text = { Text("Are you sure you want to start a new puzzle? Your current progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    vm.loadNewPuzzle()
                    showNewGameDialog = false
                    selectedPieceId = null // Deselect on new game
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showNewGameDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shapes") },
                navigationIcon = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        showHowToDialog = true
                    }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        showHintDialog = true
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "Hint")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            showNewGameDialog = true
                        }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
                        }
                    }
                }
            )
        },
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
                  Button(
                      onClick = {
                          soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                          selectedPieceId?.let { vm.rotatePiece(it, -45f) }
                      },
                      enabled = selectedPieceId != null
                  ) {
                      Text("Rotate L")
                  }
                  Spacer(modifier = Modifier.width(16.dp))
                  Button(
                      onClick = {
                          soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                          selectedPieceId?.let { vm.rotatePiece(it, 45f) }
                      },
                      enabled = selectedPieceId != null
                  ) {
                      Text("Rotate R")
                  }
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
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        label = "rotation_anim_${piece.id}"
                    )
                    piece.id to animatedRotation
                }

                val density = LocalDensity.current
                val actualWidth = with(density) { maxWidth.toPx() }
                val actualHeight = with(density) { maxHeight.toPx() }

                // The standard virtual size of our shape board
                val virtualWidth = 400f
                val virtualHeight = 700f

                val scaleX = actualWidth / virtualWidth
                val scaleY = actualHeight / virtualHeight
                val scale = minOf(scaleX, scaleY)

                val offsetX = (actualWidth - virtualWidth * scale) / 2f
                val offsetY = (actualHeight - virtualHeight * scale) / 2f

                Canvas(modifier = Modifier
                    .fillMaxSize()
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
                            val gridSpacing = 40f
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

                            // 2. Draw piece dock tray at the bottom (visually holds the pieces)
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

                            // 3. Draw Target Silhouette
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
                                        width = 5.0f, // Doubled for internal clipping (2.5f visible)
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
                                
                                // Draw beautiful 3D drop shadow
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

                                // Draw piece fill and elegant outline
                                drawPath(piecePath, piece.color, style = Fill)
                                clipPath(piecePath) {
                                    drawPath(
                                        piecePath, 
                                        if (isSelected) Color(0xFFFFD700) else Color.White.copy(alpha = 0.35f), // Gold border for selected
                                        style = Stroke(width = if (isSelected) 7.0f else 3.0f) // Doubled for internal clipping (3.5f or 1.5f visible)
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