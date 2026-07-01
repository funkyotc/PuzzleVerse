package com.funkyotc.puzzleverse.cubeshooter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.cubeshooter.data.Tank
import com.funkyotc.puzzleverse.cubeshooter.data.TrackTank
import com.funkyotc.puzzleverse.cubeshooter.viewmodel.CubeShooterViewModel
import com.funkyotc.puzzleverse.cubeshooter.viewmodel.CubeShooterViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CubeShooterScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: CubeShooterViewModel = viewModel(
        factory = CubeShooterViewModelFactory(streakRepository, mode, puzzleId)
    )
) {
    val stateOpt by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }

    val state = stateOpt ?: return

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
        }
    }

    // Keep ticking the ViewModel
    LaunchedEffect(state.isWon, state.isGameOver) {
        if (!state.isWon && !state.isGameOver) {
            var lastTime = System.currentTimeMillis()
            while (true) {
                awaitFrame()
                val now = System.currentTimeMillis()
                val dtMs = now - lastTime
                lastTime = now
                if (dtMs > 0) {
                    viewModel.tick(dtMs)
                }
            }
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = {
                Text(
                    "Cubes form Tetris-like clusters in the grid. " +
                    "Tanks on the track move clockwise. " +
                    "When a tank is adjacent to a same-color cube, it fires automatically, clearing the cube and losing 1 ammo. " +
                    "Tap a tank in the tray to send it onto the track. " +
                    "Clear all cubes to win!"
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

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Victory!") },
            text = { Text("You cleared all cubes! Final Score: ${state.score}") },
            confirmButton = {
                Button(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    if (mode == "puzzle") {
                        navController.popBackStack()
                    } else {
                        viewModel.startNewGame()
                    }
                }) {
                    Text(if (mode == "puzzle") "Back to Browser" else "Play Again")
                }
            }
        )
    }

    if (state.isGameOver && !state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Game Over") },
            text = { Text("No more tanks left or tray overflowed. Try again!") },
            confirmButton = {
                Button(onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.startNewGame()
                }) {
                    Text("Try Again")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cube Shooter") },
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
                        viewModel.startNewGame()
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Stats Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Difficulty: ${state.level.difficulty}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cubes Left: ${state.cubesRemaining}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Score: ${state.score}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Track: ${state.track.size}/5",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Board and Track
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val cols = state.level.cols
                val rows = state.level.rows
                val totalCols = cols + 2
                val totalRows = rows + 2

                val cellSize = minOf(
                    maxWidth / totalCols,
                    maxHeight / totalRows
                )

                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    for (r in 0 until totalRows) {
                        Row(modifier = Modifier.wrapContentSize()) {
                            for (c in 0 until totalCols) {
                                val isTrack = isTrackCell(r, c, cols, rows)
                                val trackIndex = if (isTrack) getTrackIndex(r, c, cols, rows) else -1
                                val isCube = r in 1..rows && c in 1..cols

                                Box(
                                    modifier = Modifier
                                        .size(cellSize)
                                        .padding(1.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isTrack) {
                                        // Find if there is a tank on this track cell
                                        val tanksAtCell = state.track.filter {
                                            it.position.toInt() % (2 * (cols + rows)) == trackIndex
                                        }

                                        if (tanksAtCell.isNotEmpty()) {
                                            // Render tank
                                            val tank = tanksAtCell.first()
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(CircleShape)
                                                    .background(getComposeColor(tank.tank.color)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = tank.tank.ammo.toString(),
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = (cellSize.value * 0.45f).sp
                                                )
                                            }
                                        } else {
                                            // Render track background with directions
                                            val directionArrow = getTrackArrow(r, c, cols, rows)
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = directionArrow,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                                    fontSize = (cellSize.value * 0.4f).sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    } else if (isCube) {
                                        val cubeColorId = state.level.grid[r - 1][c - 1]
                                        if (cubeColorId != null) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(getComposeColor(cubeColorId))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Tray Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tanks Tray (Max 5)",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (state.tray.isEmpty()) {
                        Text(
                            text = "No tanks in tray",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            state.tray.forEachIndexed { index, tank ->
                                val isDispatchEnabled = state.track.size < 5 && tank.ammo > 0
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            getComposeColor(tank.color).copy(
                                                alpha = if (isDispatchEnabled) 1f else 0.4f
                                            )
                                        )
                                        .clickable(enabled = isDispatchEnabled) {
                                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                            viewModel.dispatch(index)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "A: ${tank.ammo}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                        Text(
                                            text = "TAP",
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
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

private fun isTrackCell(r: Int, c: Int, cols: Int, rows: Int): Boolean {
    return (r == 0 && c in 1..cols) ||
           (r in 1..rows && c == cols + 1) ||
           (r == rows + 1 && c in 1..cols) ||
           (r in 1..rows && c == 0)
}

private fun getTrackIndex(r: Int, c: Int, cols: Int, rows: Int): Int {
    return when {
        r == 0 && c in 1..cols -> {
            c - 1
        }
        r in 1..rows && c == cols + 1 -> {
            cols + (r - 1)
        }
        r == rows + 1 && c in 1..cols -> {
            (cols + rows) + (cols - c)
        }
        r in 1..rows && c == 0 -> {
            (2 * cols + rows) + (rows - r)
        }
        else -> -1
    }
}

private fun getTrackArrow(r: Int, c: Int, cols: Int, rows: Int): String {
    return when {
        r == 0 -> "→"
        c == cols + 1 -> "↓"
        r == rows + 1 -> "←"
        c == 0 -> "↑"
        else -> ""
    }
}

private fun getComposeColor(colorId: Int?): Color {
    return when (colorId) {
        0 -> Color(0xFF00ACC1) // Cyan
        1 -> Color(0xFFD81B60) // Magenta
        2 -> Color(0xFFFBC02D) // Yellow
        3 -> Color(0xFF43A047) // Green
        else -> Color.Transparent
    }
}
