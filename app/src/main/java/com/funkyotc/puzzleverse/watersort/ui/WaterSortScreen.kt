package com.funkyotc.puzzleverse.watersort.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
    Color(0xFF9E9E9E), // Gray
)

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
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = {
                Text(
                    "Tap a bottle to pick up its top color layer, then tap another bottle to pour.\n\n" +
                    "You can only pour onto a matching color layer or into an empty bottle.\n" +
                    "A bottle can hold at most ${state.level.height} layers.\n\n" +
                    "Goal: Sort each color into its own bottle!"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHowToDialog = false
                }) {
                    Text("Got it")
                }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Game") },
            text = { Text("Are you sure you want to start a new game?") },
            confirmButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showNewGameDialog = false
                    viewModel.startNewGame()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showNewGameDialog = false
                }) {
                    Text("No")
                }
            }
        )
    }

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Victory!") },
            text = { Text("Sorted in ${state.moves} moves!") },
            confirmButton = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (mode) {
                        "puzzle" -> {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    navController.popBackStack()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Back to Browser")
                            }
                            val nextId = puzzleId?.let { id ->
                                val allPuzzles = WaterSortPregenerated.ALL_LEVELS
                                val currentIndex = allPuzzles.indexOfFirst { it.id == id }
                                if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) {
                                    allPuzzles[currentIndex + 1].id
                                } else null
                            }
                            if (nextId != null) {
                                Button(
                                    onClick = {
                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                        navController.popBackStack()
                                        navController.navigate("game/watersort/puzzle/$nextId")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Next Puzzle")
                                }
                            }
                        }
                        "daily" -> {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    navController.popBackStack()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Back to Home")
                            }
                        }
                        else -> {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    viewModel.startNewGame()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Play Again")
                            }
                        }
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Water Sort") },
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
                        Icon(Icons.Filled.Info, contentDescription = "How to Play")
                    }
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        showNewGameDialog = true
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Restart")
                    }
                }
            )
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

            Spacer(modifier = Modifier.height(24.dp))

            val bottles = state.bottles
            val cols = when {
                bottles.size <= 4 -> bottles.size
                bottles.size <= 6 -> 3
                else -> 4
            }
            val rows = (bottles.size + cols - 1) / cols

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.weight(1f)
            ) {
                for (row in 0 until rows) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
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
                                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
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
    val borderColor = if (isSelected) Color(0xFF4CAF50) else Color.Gray.copy(alpha = 0.3f)
    val borderWidth = if (isSelected) 3.dp else 1.dp
    val bgColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .aspectRatio(0.5f)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val segmentHeight = canvasHeight / height
            val bottleWidth = canvasWidth * 0.7f
            val bottleLeft = (canvasWidth - bottleWidth) / 2f
            val cornerRadius = bottleWidth * 0.15f

            val colors = bottle.colors
            val emptySegments = height - colors.size

            if (emptySegments > 0) {
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = Offset(bottleLeft, 0f),
                    size = Size(bottleWidth, emptySegments * segmentHeight),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }

            for (i in colors.indices) {
                val colorIndex = colors[i]
                val segTop = (emptySegments + i) * segmentHeight
                val segBottom = segTop + segmentHeight

                val color = if (colorIndex in WATER_COLORS.indices) {
                    WATER_COLORS[colorIndex]
                } else {
                    Color.Gray
                }

                val isTopSegment = i == colors.lastIndex
                val isBottomSegment = i == 0 && emptySegments == 0

                val drawCornerRadius = if (isTopSegment || (isBottomSegment && colors.size == height)) {
                    CornerRadius(cornerRadius, cornerRadius)
                } else {
                    CornerRadius(0f, 0f)
                }

                drawRoundRect(
                    color = color,
                    topLeft = Offset(bottleLeft, segTop),
                    size = Size(bottleWidth, segmentHeight),
                    cornerRadius = drawCornerRadius
                )

                if (isTopSegment) {
                    val highlightAlpha = 0.25f
                    drawRoundRect(
                        color = Color.White.copy(alpha = highlightAlpha),
                        topLeft = Offset(bottleLeft, segTop),
                        size = Size(bottleWidth, segmentHeight * 0.4f),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }

            drawRoundRect(
                color = borderColor,
                topLeft = Offset(bottleLeft, 0f),
                size = Size(bottleWidth, canvasHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Stroke(width = borderWidth.toPx())
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0x1A4CAF50), RoundedCornerShape(8.dp))
            )
        }
    }
}
