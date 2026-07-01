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
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
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

                Box(modifier = Modifier.wrapContentSize()) {
                    // 0. Unified continuous background route Canvas
                    val pathTrackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    val pathIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val trackPathWidth = (cols + 1) * cellSize.toPx()
                        val trackPathHeight = (rows + 1) * cellSize.toPx()
                        val left = 0.5f * cellSize.toPx()
                        val top = 0.5f * cellSize.toPx()

                        // Draw thick background lane line
                        drawRoundRect(
                            color = pathTrackColor,
                            topLeft = Offset(left, top),
                            size = Size(trackPathWidth, trackPathHeight),
                            cornerRadius = CornerRadius(cellSize.toPx() * 0.4f, cellSize.toPx() * 0.4f),
                            style = Stroke(width = cellSize.toPx() * 0.55f)
                        )

                        // Draw thin glowing center guideline
                        drawRoundRect(
                            color = pathIndicatorColor,
                            topLeft = Offset(left, top),
                            size = Size(trackPathWidth, trackPathHeight),
                            cornerRadius = CornerRadius(cellSize.toPx() * 0.4f, cellSize.toPx() * 0.4f),
                            style = Stroke(width = 2.5f.dp.toPx())
                        )
                    }

                    // 1. Static Grid: Active cubes
                    Column(
                        modifier = Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (r in 0 until totalRows) {
                            Row(modifier = Modifier.wrapContentSize()) {
                                for (c in 0 until totalCols) {
                                    val isTrack = isTrackCell(r, c, cols, rows)
                                    val isCube = r in 1..rows && c in 1..cols

                                    Box(
                                        modifier = Modifier
                                            .size(cellSize)
                                            .padding(1.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isTrack) {
                                            // Empty content so it acts as a transparent layout spacing placeholder
                                        } else if (isCube) {
                                            val cubeColorId = state.level.grid[r - 1][c - 1]
                                            if (cubeColorId != null) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(getComposeColor(cubeColorId))
                                                )
                                            } else {
                                                // Check for Fading/Exploding Cubes
                                                val fadingCube = state.fadingCubes.find { it.row == r - 1 && it.col == c - 1 }
                                                if (fadingCube != null) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .scale(1f - fadingCube.progress)
                                                            .alpha(1f - fadingCube.progress)
                                                            .clip(RoundedCornerShape(4.dp))
                                                            .background(getComposeColor(fadingCube.color))
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 2. Projectiles Canvas (Lasers / Bullets)
                    Canvas(modifier = Modifier.matchParentSize()) {
                        state.projectiles.forEach { p ->
                            val currCol = p.startCol + (p.endCol - p.startCol) * p.progress
                            val currRow = p.startRow + (p.endRow - p.startRow) * p.progress

                            val x = (currCol + 0.5f) * cellSize.toPx()
                            val y = (currRow + 0.5f) * cellSize.toPx()

                            drawCircle(
                                color = getComposeColor(p.color),
                                radius = cellSize.toPx() * 0.15f,
                                center = Offset(x, y)
                            )

                            val startX = (p.startCol + 0.5f) * cellSize.toPx()
                            val startY = (p.startRow + 0.5f) * cellSize.toPx()
                            drawLine(
                                color = getComposeColor(p.color).copy(alpha = 0.6f * (1f - p.progress)),
                                start = Offset(startX, startY),
                                end = Offset(x, y),
                                strokeWidth = 3.dp.toPx()
                            )
                        }
                    }

                    // 3. Smoothly Animated Tanks Overlay
                    val loopLen = 2 * (cols + rows)
                    state.track.forEach { trackTank ->
                        val pos = trackTank.position
                        val idx1 = pos.toInt() % loopLen
                        val idx2 = (idx1 + 1) % loopLen
                        val fraction = pos - pos.toInt()

                        val coord1 = getTrackCellCoordinates(idx1, cols, rows)
                        val coord2 = getTrackCellCoordinates(idx2, cols, rows)

                        val r = coord1.first + (coord2.first - coord1.first) * fraction
                        val c = coord1.second + (coord2.second - coord1.second) * fraction

                        val xOffset = c * cellSize.value
                        val yOffset = r * cellSize.value

                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .offset(x = xOffset.dp, y = yOffset.dp)
                                .padding(1.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(getComposeColor(trackTank.tank.color)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = trackTank.tank.ammo.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (cellSize.value * 0.45f).sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Section (Storage Tray on Left, Source Columns on Right)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Card: Storage Tray (Max 5)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Storage (Max 5)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )

                        if (state.storageTray.isEmpty()) {
                            Box(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Empty",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                state.storageTray.forEachIndexed { index, tank ->
                                    val isDispatchEnabled = state.track.size < 5 && tank.ammo > 0
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                getComposeColor(tank.color).copy(
                                                    alpha = if (isDispatchEnabled) 1f else 0.4f
                                                )
                                            )
                                            .clickable(enabled = isDispatchEnabled) {
                                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                viewModel.dispatchFromStorage(index)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "A:${tank.ammo}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Right Card: Incoming Columns (Top tank accessible)
                Card(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Source Columns",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            state.sourceColumns.forEachIndexed { colIndex, colTanks ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    if (colTanks.isEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .background(Color.Transparent),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("—", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), fontSize = 10.sp)
                                        }
                                    } else {
                                        // Show up to 3 tanks vertically from bottom to top
                                        val displayList = colTanks.takeLast(3)
                                        val totalCount = colTanks.size
                                        
                                        if (totalCount > 3) {
                                            Text(
                                                text = "+${totalCount - 3}",
                                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        
                                        for (i in displayList.indices.reversed()) {
                                            val tank = displayList[i]
                                            val isTop = (i == displayList.lastIndex)
                                            val isDispatchEnabled = isTop && state.track.size < 5
                                            
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(
                                                        getComposeColor(tank.color).copy(
                                                            alpha = if (isDispatchEnabled) 1f else if (isTop) 0.5f else 0.2f
                                                        )
                                                    )
                                                    .clickable(enabled = isDispatchEnabled) {
                                                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                        viewModel.dispatchFromSource(colIndex)
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = tank.ammo.toString(),
                                                    color = Color.White,
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

private fun getTrackCellCoordinates(index: Int, cols: Int, rows: Int): Pair<Int, Int> {
    val loopLen = 2 * (cols + rows)
    val idx = (index % loopLen + loopLen) % loopLen
    return when {
        idx < cols -> {
            Pair(0, idx + 1)
        }
        idx < cols + rows -> {
            val r = idx - cols + 1
            Pair(r, cols + 1)
        }
        idx < 2 * cols + rows -> {
            val c = cols - (idx - (cols + rows))
            Pair(rows + 1, c)
        }
        else -> {
            val r = rows - (idx - (2 * cols + rows))
            Pair(r, 0)
        }
    }
}
