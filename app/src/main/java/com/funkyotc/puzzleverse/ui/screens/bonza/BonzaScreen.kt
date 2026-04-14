package com.funkyotc.puzzleverse.ui.screens.bonza

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModelFactory
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.BoxWithConstraints
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BonzaScreen(
    navController: NavController,
    mode: String?,
    puzzleId: String? = null,
    forceNewGame: Boolean = false,
    context: Context = LocalContext.current,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    bonzaViewModel: BonzaViewModel = viewModel(factory = BonzaViewModelFactory(context, mode, puzzleId, forceNewGame, streakRepository))
) {
    val isGameWon by bonzaViewModel.isGameWon.collectAsState()
    val puzzle by bonzaViewModel.puzzle.collectAsState()
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHintDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val completionRepo = remember { com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository(context, "bonza") }
    
    if (showHintDialog) {
        AlertDialog(
            onDismissRequest = { showHintDialog = false },
            title = { Text("Use a Hint?") },
            text = { Text("Are you sure you want to use a hint to reveal part of the puzzle?") },
            confirmButton = {
                TextButton(onClick = {
                    showHintDialog = false
                    bonzaViewModel.hint() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showHintDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Move the puzzle pieces around to form interlocking words based on the level's theme.") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = { navController.navigate("game/bonza/standard/new") { popUpTo("home") } }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else {

                TextButton(onClick = { showHowToDialog = false }) {
                    Text("OK")

                }
                }
            }
        )
    }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle!") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = { navController.navigate("game/bonza/standard/new") { popUpTo("home") } }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else if (mode == "puzzle" && puzzleId != null) {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.popBackStack() }) {
                            androidx.compose.material3.Text("Back to List")
                        }
                        androidx.compose.material3.Button(onClick = {
                            val currentTheme = com.funkyotc.puzzleverse.bonza.data.BonzaPregenerated.getPuzzleById(puzzleId)?.theme
                            val themePuzzles = com.funkyotc.puzzleverse.bonza.data.BonzaPregenerated.PUZZLES_BY_THEME[currentTheme] ?: emptyList()
                            val currentIndex = themePuzzles.indexOfFirst { it.id == puzzleId }
                            val nextPuzzle = if (currentIndex != -1 && currentIndex + 1 < themePuzzles.size) themePuzzles[currentIndex + 1] else null
                            if (nextPuzzle != null) {
                                navController.navigate("game/bonza/puzzle/${nextPuzzle.id}") {
                                    popUpTo("bonza/puzzles")
                                }
                            } else {
                                navController.popBackStack()
                            }
                        }) {
                            androidx.compose.material3.Text("Next Puzzle")
                        }
                    }
                } else {

                Button(onClick = { bonzaViewModel.newGame() }) {
                    Text("New Game")

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
                    bonzaViewModel.newGame()
                    showNewGameDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bonza - ${puzzle.theme}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToDialog = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    IconButton(onClick = { showHintDialog = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Hint")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            BonzaBoard(
                puzzle = puzzle,
                viewModel = bonzaViewModel
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BonzaBoard(puzzle: BonzaPuzzle, viewModel: BonzaViewModel) {
    val textMeasurer = rememberTextMeasurer()
    val primaryColor = MaterialTheme.colorScheme.primary
    
    // Scale configuration
    val density = LocalDensity.current
    val letterBoxSizeDp = 48.dp
    val letterBoxSizePx = with(density) { letterBoxSizeDp.toPx() }
    val letterBoxCornerRadius = 16f
    
    // Transformable state for Pan and Zoom
    val scale = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    
    // Track dragging state to prevent auto-zoom during interaction
    var isDragging by remember { mutableStateOf(false) }
    
    // Auto-Fit Logic
    androidx.compose.runtime.LaunchedEffect(puzzle, isDragging) {
        // Don't auto-fit while dragging
        if (isDragging) return@LaunchedEffect

        val bounds = viewModel.getPuzzleBounds()
        if (bounds.isEmpty) return@LaunchedEffect
        
        // Calculate needed scale to fit bounds
        // ... (rest of logic needs to be inside BoxWithConstraints or layout phase)
        // Actually, the main logic is in the second LaunchedEffect below. 
        // We can just keep this one empty or remove it if redundant. 
        // The one inside BoxWithConstraints is the active one.
    }
    
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()
        
        androidx.compose.runtime.LaunchedEffect(puzzle, isDragging, screenWidth, screenHeight) {
            if (isDragging) return@LaunchedEffect
            
            val bounds = viewModel.getPuzzleBounds()
            if (!bounds.isEmpty && screenWidth > 0 && screenHeight > 0) {
                 val boundsWidthPx = bounds.width * letterBoxSizePx
                 val boundsHeightPx = bounds.height * letterBoxSizePx
                 
                 // Target scale to fit
                 val paddingFactor = 0.8f
                 val targetScale = minOf(
                     screenWidth / boundsWidthPx,
                     screenHeight / boundsHeightPx
                 ) * paddingFactor
                 
                 // Target center in Pixel Space (if scale was 1)
                 val boundsCenterPx = bounds.center * letterBoxSizePx
                 
                 // We want boundsCenterPx * targetScale + offset = screenCenter
                 // offset = screenCenter - (boundsCenterPx * targetScale)
                 val screenCenter = Offset(screenWidth / 2, screenHeight / 2)
                 val targetOffset = screenCenter - (boundsCenterPx * targetScale)
                 
                 launch {
                     scale.animateTo(targetScale)
                 }
                 launch {
                     offsetX.animateTo(targetOffset.x)
                 }
                 launch {
                     offsetY.animateTo(targetOffset.y)
                 }
            }
        }
        
        val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
        
        val state = rememberTransformableState { zoomChange, offsetChange, _ ->
             coroutineScope.launch {
                 scale.snapTo(scale.value * zoomChange)
                 offsetX.snapTo(offsetX.value + offsetChange.x)
                 offsetY.snapTo(offsetY.value + offsetChange.y)
             }
        }
        
        val draggedGroupId by viewModel.draggedGroupId.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .transformable(state = state)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { position ->
                            isDragging = true
                            // Convert screen position to puzzle space (Grid Units)
                            val currentScale = scale.value
                            val currentOffset = Offset(offsetX.value, offsetY.value)
                            val puzzlePos = (position - currentOffset) / currentScale / letterBoxSizePx
                            viewModel.onDragStart(puzzlePos)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val currentScale = scale.value
                            val currentOffset = Offset(offsetX.value, offsetY.value)
                            val puzzlePos = (change.position - currentOffset) / currentScale / letterBoxSizePx
                            viewModel.onDrag(puzzlePos)
                        },
                        onDragEnd = { 
                            isDragging = false
                            viewModel.onDragEnd() 
                        },
                        onDragCancel = {
                            isDragging = false
                            viewModel.onDragEnd()
                        }
                    )
                }
        ) {
            // Pre-calculate animations outside Canvas
            val fragmentAnimations = puzzle.fragments.associate { fragment ->
                val isDragged = fragment.groupId == draggedGroupId
                // Position animation (snapping)
                val animatedPos by animateOffsetAsState(
                    targetValue = fragment.currentPosition,
                    animationSpec = if (isDragged) snap() else spring(stiffness = Spring.StiffnessMediumLow),
                    label = "position_anim_${fragment.id}"
                )
                
                // Vertical lift animation
                val liftUnit = if (isDragged) -0.5f else 0f
                val verticalLift by animateFloatAsState(
                    targetValue = liftUnit,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium),
                    label = "lift_anim_${fragment.id}"
                )
                
                fragment.id to Pair(animatedPos, verticalLift)
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                translate(offsetX.value, offsetY.value) {
                    scale(scale.value, pivot = Offset.Zero) {
                        puzzle.fragments.forEach { fragment ->
                            val animData = fragmentAnimations[fragment.id]
                            val animPos = animData?.first ?: fragment.currentPosition
                            val vLift = animData?.second ?: 0f

                            fragment.text.forEachIndexed { index, char ->
                                val gridPos = if (fragment.direction == ConnectionDirection.HORIZONTAL) {
                                   Offset(animPos.x + index, animPos.y + vLift)
                                } else {
                                   Offset(animPos.x, animPos.y + index + vLift)
                                }
                                
                                val letterOffset = gridPos * letterBoxSizePx
    
                                drawRoundRect(
                                    color = primaryColor,
                                    topLeft = letterOffset,
                                    size = Size(letterBoxSizePx, letterBoxSizePx),
                                    cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius)
                                )
    
                                drawRoundRect(
                                    color = Color.Black,
                                    topLeft = letterOffset,
                                    size = Size(letterBoxSizePx, letterBoxSizePx),
                                    cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius),
                                    style = Stroke(width = 2f)
                                )
    
                                val textLayoutResult = textMeasurer.measure(
                                    text = char.toString(),
                                    style = TextStyle(color = Color.White, fontSize = 24.sp)
                                )
    
                                drawText(
                                    textLayoutResult,
                                    topLeft = letterOffset + Offset(
                                        (letterBoxSizePx - textLayoutResult.size.width) / 2,
                                        (letterBoxSizePx - textLayoutResult.size.height) / 2
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
