package com.funkyotc.puzzleverse.tfe.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.tfe.data.Direction
import com.funkyotc.puzzleverse.tfe.viewmodel.TfeViewModel
import com.funkyotc.puzzleverse.tfe.viewmodel.TfeViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TfeScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    viewModel: TfeViewModel = viewModel(factory = TfeViewModelFactory(streakRepository, mode))
) {
    val state by viewModel.state.collectAsState()
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
            text = { Text("Swipe to move tiles. Tiles with the same number merge into one when they touch. Get to 2048!") },
            confirmButton = { TextButton(onClick = { showHowToDialog = false }) { Text("OK") } }
        )
    }

    if (state.isGameOver) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Game Over") },
            text = { Text("No more moves left. Score: ${state.score}") },
            confirmButton = {
                Button(onClick = { viewModel.startNewGame() }) { Text("Try Again") }
            }
        )
    }

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("You Win!") },
            text = { Text("You reached 2048! Score: ${state.score}") },
            confirmButton = {
                Button(onClick = { viewModel.startNewGame() }) { Text("Play Again") }
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
                title = { Text("2048") },
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
            Text(
                text = "Score: ${state.score}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFBBADA0))
                    .padding(8.dp)
                    .pointerInput(Unit) {
                        var swipeDirection: Direction? = null
                        detectDragGestures(
                            onDragEnd = {
                                swipeDirection?.let { viewModel.move(it) }
                                swipeDirection = null
                            },
                        ) { change, dragAmount ->
                            change.consume()
                            if (swipeDirection == null) {
                                val (x, y) = dragAmount
                                if (abs(x) > abs(y)) {
                                    if (abs(x) > 20) swipeDirection = if (x > 0) Direction.RIGHT else Direction.LEFT
                                } else {
                                    if (abs(y) > 20) swipeDirection = if (y > 0) Direction.DOWN else Direction.UP
                                }
                            }
                        }
                    }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (r in 0..3) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                            for (c in 0..3) {
                                val value = state.grid[r][c]
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(getTileColor(value)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (value > 0) {
                                        Text(
                                            text = value.toString(),
                                            fontSize = if (value > 1000) 24.sp else 32.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (value <= 4) Color(0xFF776E65) else Color.White
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
}

fun getTileColor(value: Int): Color {
    return when (value) {
        0 -> Color(0xFFCDC1B4)
        2 -> Color(0xFFEEE4DA)
        4 -> Color(0xFFEDE0C8)
        8 -> Color(0xFFF2B179)
        16 -> Color(0xFFF59563)
        32 -> Color(0xFFF67C5F)
        64 -> Color(0xFFF65E3B)
        128 -> Color(0xFFEDCF72)
        256 -> Color(0xFFEDCC61)
        512 -> Color(0xFFEDC850)
        1024 -> Color(0xFFEDC53F)
        2048 -> Color(0xFFEDC22E)
        else -> Color(0xFF3C3A32)
    }
}
