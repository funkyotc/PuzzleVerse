package com.funkyotc.puzzleverse.flowfree.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.flowfree.data.FlowDifficulty
import com.funkyotc.puzzleverse.flowfree.data.Point
import com.funkyotc.puzzleverse.flowfree.viewmodel.FlowFreeViewModel
import com.funkyotc.puzzleverse.flowfree.viewmodel.FlowFreeViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowFreeScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: FlowFreeViewModel = viewModel(factory = FlowFreeViewModelFactory(streakRepository, mode, puzzleId))
) {
    val state by viewModel.state.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val currentDifficulty by viewModel.difficulty.collectAsState()
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Connect matching colors with pipes to create a flow. Pair all colors, and cover the entire board to solve each puzzle. Pipes cannot branch or cross each other.") },
            confirmButton = {
                TextButton(onClick = { showHowToDialog = false }) { Text("OK") }
            }
        )
    }

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("You Win!") },
            text = { Text("You connected all flows and filled the grid!") },
            confirmButton = {
                if (mode == "daily") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            Text("Main Menu")
                        }
                        Button(onClick = { navController.navigate("game/flowfree/standard/new") { popUpTo("home") } }) {
                            Text("Random Puzzles")
                        }
                    }
                } else if (mode == "puzzle") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Back to List")
                        }
                        Button(onClick = {
                            // Navigate to a random next puzzle from pregenerated list
                            val next = com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated.ALL_PUZZLES
                                .filter { it.id != puzzleId }
                                .randomOrNull()
                            if (next != null) {
                                navController.navigate("game/flowfree/puzzle/${next.id}") {
                                    popUpTo("flowfree/puzzles")
                                }
                            }
                        }) {
                            Text("Next Puzzle")
                        }
                    }
                } else {
                    Button(onClick = { viewModel.startNewGame() }) { Text("Play Again") }
                }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Game") },
            text = { Text("Are you sure you want to start over?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.startNewGame()
                    showNewGameDialog = false
                }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flow Free") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToDialog = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Game")
                        }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Difficulty indicator chip
            if (mode != "daily") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlowDifficulty.entries.forEach { diff ->
                        val isSelected = diff == currentDifficulty
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setDifficulty(diff) },
                            label = {
                                Text(
                                    diff.label,
                                    fontSize = 12.sp
                                )
                            },
                            modifier = Modifier.padding(horizontal = 4.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            } else {
                // Daily mode: show difficulty badge
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Daily • ${currentDifficulty.label} • ${state.rows}×${state.cols}",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Grid size label
            Text(
                text = "${state.rows}×${state.cols} • ${state.dots.size} flows",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (isGenerating) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Generating puzzle...", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                var gridWidth by remember { mutableStateOf(0f) }
                var gridHeight by remember { mutableStateOf(0f) }
                var activeColorId by remember { mutableStateOf<Int?>(null) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(state.cols.toFloat() / state.rows.toFloat())
                        .background(Color.Black)
                        .onGloballyPositioned { coordinates ->
                            gridWidth = coordinates.size.width.toFloat()
                            gridHeight = coordinates.size.height.toFloat()
                        }
                        .pointerInput(gridWidth) { // Re-init if layout changes
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val cellPxW = gridWidth / state.cols.toFloat()
                                    val cellPxH = gridHeight / state.rows.toFloat()
                                    val r = (offset.y / cellPxH).toInt()
                                    val c = (offset.x / cellPxW).toInt()

                                    val dot = state.dots.find { it.start == Point(r, c) || it.end == Point(r, c) }
                                    if (dot != null) {
                                        activeColorId = dot.colorId
                                        viewModel.startPath(dot.colorId, r, c)
                                    } else {
                                        // Resume path from middle
                                        val path = state.paths.find { it.path.contains(Point(r, c)) }
                                        if (path != null) {
                                            activeColorId = path.colorId
                                        }
                                    }
                                },
                                onDragEnd = { activeColorId = null },
                                onDragCancel = { activeColorId = null }
                            ) { change, _ ->
                                change.consume()
                                val offset = change.position
                                val cellPxW = gridWidth / state.cols.toFloat()
                                val cellPxH = gridHeight / state.rows.toFloat()
                                val r = (offset.y / cellPxH).toInt()
                                val c = (offset.x / cellPxW).toInt()

                                if (r in 0 until state.rows && c in 0 until state.cols && activeColorId != null) {
                                    viewModel.extendPath(activeColorId!!, r, c)
                                }
                            }
                        }
                ) {
                    // Background grid lines
                    Column(modifier = Modifier.fillMaxSize()) {
                        for (r in 0 until state.rows) {
                            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                for (c in 0 until state.cols) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .border(0.5.dp, Color.DarkGray)
                                    )
                                }
                            }
                        }
                    }

                    // Draw Dots and Paths
                    if (gridWidth > 0f && gridHeight > 0f) {
                        val cellW = gridWidth / state.cols
                        val cellH = gridHeight / state.rows

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Draw paths
                            for (pathObj in state.paths) {
                                val color = getColor(pathObj.colorId)
                                val pathPts = pathObj.path

                                for (i in 0 until pathPts.size - 1) {
                                    val p1 = pathPts[i]
                                    val p2 = pathPts[i + 1]

                                    val x1 = (p1.c * cellW) + (cellW / 2)
                                    val y1 = (p1.r * cellH) + (cellH / 2)
                                    val x2 = (p2.c * cellW) + (cellW / 2)
                                    val y2 = (p2.r * cellH) + (cellH / 2)

                                    drawLine(
                                        color = color,
                                        start = Offset(x1, y1),
                                        end = Offset(x2, y2),
                                        strokeWidth = cellW * 0.4f,
                                        cap = StrokeCap.Round
                                    )
                                }
                            }

                            // Draw dots
                            for (dot in state.dots) {
                                val color = getColor(dot.colorId)
                                val r1 = dot.start.r
                                val c1 = dot.start.c
                                drawCircle(
                                    color = color,
                                    radius = cellW * 0.35f,
                                    center = Offset(c1 * cellW + cellW / 2, r1 * cellH + cellH / 2)
                                )

                                val r2 = dot.end.r
                                val c2 = dot.end.c
                                drawCircle(
                                    color = color,
                                    radius = cellW * 0.35f,
                                    center = Offset(c2 * cellW + cellW / 2, r2 * cellH + cellH / 2)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getColor(id: Int): Color {
    return when (id) {
        1 -> Color.Red
        2 -> Color.Blue
        3 -> Color.Green
        4 -> Color.Yellow
        5 -> Color(0xFFFFA500) // Orange
        6 -> Color.Cyan
        7 -> Color.Magenta
        8 -> Color(0xFF800000) // Maroon
        9 -> Color(0xFF008080) // Teal
        else -> Color.White
    }
}
