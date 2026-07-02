package com.funkyotc.puzzleverse.cubeshooter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.Dp
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
import androidx.compose.ui.graphics.drawscope.rotate
import com.funkyotc.puzzleverse.cubeshooter.data.Tank
import com.funkyotc.puzzleverse.cubeshooter.data.TrackTank
import com.funkyotc.puzzleverse.cubeshooter.viewmodel.CubeShooterViewModel
import com.funkyotc.puzzleverse.cubeshooter.viewmodel.CubeShooterViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.android.awaitFrame
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.runtime.key

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

    var lastProjectileCount by remember { mutableIntStateOf(0) }
    LaunchedEffect(state.projectiles.size) {
        if (state.projectiles.size > lastProjectileCount) {
            soundManager.playSound(SoundManager.SOUND_ID_CLICK, 0.72f)
        }
        lastProjectileCount = state.projectiles.size
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
                    val themeBgColor = MaterialTheme.colorScheme.background
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

                        // Draw visual gap at the start/end point (bottom middle of the track)
                        val gapWidth = cellSize.toPx() * 1.2f
                        val gapHeight = cellSize.toPx() * 0.8f
                        drawRect(
                            color = themeBgColor,
                            topLeft = Offset(left + trackPathWidth / 2f - gapWidth / 2f, top + trackPathHeight - gapHeight / 2f),
                            size = Size(gapWidth, gapHeight)
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
                                radius = cellSize.toPx() * 0.28f,
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

                        val tankSize = 56.dp
                        val xOffset = c * cellSize.value + (cellSize.value - tankSize.value) / 2f
                        val yOffset = r * cellSize.value + (cellSize.value - tankSize.value) / 2f

                        val sideR = coord1.first
                        val sideC = coord1.second
                        val angle = when {
                            sideR == 0 -> 180f     // Top edge: points DOWN
                            sideC == cols + 1 -> 270f // Right edge: points LEFT
                            sideR == rows + 1 -> 0f   // Bottom edge: points UP
                            sideC == 0 -> 90f      // Left edge: points RIGHT
                            else -> 0f
                        }

                        TankView(
                            colorId = trackTank.tank.color,
                            ammo = trackTank.tank.ammo,
                            size = tankSize,
                            angle = angle,
                            modifier = Modifier
                                .offset(x = xOffset.dp, y = yOffset.dp)
                                .padding(1.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Section (Stacked Vertically)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Storage Tray (Top with larger tanks)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (i in 0 until 5) {
                                 if (i < state.storageTray.size) {
                                     val tank = state.storageTray[i]
                                     val isDispatchEnabled = state.track.size < 5 && tank.ammo > 0
                                     val borderModifier = if (isDispatchEnabled) {
                                         Modifier.border(
                                             width = 2.dp,
                                             color = Color.Green,
                                             shape = RoundedCornerShape(8.dp)
                                         )
                                     } else {
                                         Modifier
                                     }
                                     val alpha = 1f
                                     TankView(
                                         colorId = tank.color,
                                         ammo = tank.ammo,
                                         size = 56.dp, // Bigger tanks
                                         alpha = alpha,
                                         angle = 0f,
                                         modifier = borderModifier
                                             .clickable(enabled = isDispatchEnabled) {
                                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                viewModel.dispatchFromStorage(i)
                                             }
                                     )
                                 } else {
                                     // Empty slot
                                     Box(
                                         modifier = Modifier
                                             .size(56.dp) // Bigger slot
                                             .clip(RoundedCornerShape(8.dp))
                                             .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f))
                                             .border(
                                                 width = 1.dp,
                                                 color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                                                 shape = RoundedCornerShape(8.dp)
                                             )
                                     )
                                 }
                            }
                        }
                    }
                }

                // 2. Stats and Source Columns Layout (Side-by-side)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Stats Column
                    Column(
                        modifier = Modifier.width(90.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Diff:",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = state.level.difficulty,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Left: ${state.cubesRemaining}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Center: Source Columns Card (Taller and wider)
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(horizontal = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.Top
                            ) {
                                state.sourceColumns.forEachIndexed { colIndex, colTanks ->
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxHeight()
                                    ) {
                                        if (colTanks.isEmpty()) {
                                            Box(
                                                modifier = Modifier
                                                    .size(36.dp)
                                                    .background(Color.Transparent),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("—", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), fontSize = 12.sp)
                                            }
                                        } else {
                                            val indexedTanks = colTanks.mapIndexed { idx, t -> Pair(idx, t) }
                                            val displayList = indexedTanks.takeLast(3).reversed()
                                            
                                            Box(
                                                modifier = Modifier
                                                    .width(56.dp) // Match storage tank size
                                                    .height(140.dp)
                                            ) {
                                                 for (i in displayList.indices) {
                                                     val (originalIndex, tank) = displayList[i]
                                                     val isTop = (i == 0)
                                                     val isDispatchEnabled = isTop && state.track.size < 5
                                                     val alpha = 1f
                                                     val borderModifier = if (isDispatchEnabled) {
                                                         Modifier.border(
                                                             width = 2.dp,
                                                             color = Color.Green,
                                                             shape = RoundedCornerShape(8.dp)
                                                         )
                                                     } else {
                                                         Modifier
                                                     }
                                                     
                                                     key(originalIndex) {
                                                         val targetY = (i * 40).dp // Overlap spacing
                                                         val animatedY by animateDpAsState(
                                                             targetValue = targetY,
                                                             animationSpec = spring(stiffness = Spring.StiffnessLow),
                                                             label = "tank_y"
                                                         )
                                                         
                                                         TankView(
                                                             colorId = tank.color,
                                                             ammo = tank.ammo,
                                                             size = 56.dp, // Match storage tank size
                                                             alpha = alpha,
                                                             angle = 0f,
                                                             modifier = Modifier
                                                                 .offset(y = animatedY)
                                                                 .then(borderModifier)
                                                                 .clickable(enabled = isDispatchEnabled) {
                                                                     soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                                     viewModel.dispatchFromSource(colIndex)
                                                                 }
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

                    // Right Stats Column
                    Column(
                        modifier = Modifier.width(90.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Score:",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${state.score}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Track: ${state.track.size}/5",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold
                        )
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

@Composable
fun TankView(
    colorId: Int,
    ammo: Int,
    size: Dp,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    angle: Float = 0f
) {
    val baseColor = getComposeColor(colorId).copy(alpha = alpha)
    val textColor = if (colorId == 2) Color(0xFF1A1A1A) else Color.White

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.toPx()
            val h = size.toPx()
            
            rotate(angle, pivot = Offset(w / 2f, h / 2f)) {
                // Main tank body
                drawRoundRect(
                    color = baseColor,
                    topLeft = Offset(w * 0.15f, h * 0.25f),
                    size = Size(w * 0.7f, h * 0.55f),
                    cornerRadius = CornerRadius(w * 0.1f, w * 0.1f)
                )
                
                // Left Tread
                drawRoundRect(
                    color = Color.DarkGray.copy(alpha = alpha),
                    topLeft = Offset(w * 0.05f, h * 0.22f),
                    size = Size(w * 0.12f, h * 0.61f),
                    cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
                )
                
                // Right Tread
                drawRoundRect(
                    color = Color.DarkGray.copy(alpha = alpha),
                    topLeft = Offset(w * 0.83f, h * 0.22f),
                    size = Size(w * 0.12f, h * 0.61f),
                    cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
                )
                
                // Gun Turret Barrel
                drawRect(
                    color = baseColor,
                    topLeft = Offset(w * 0.44f, h * 0.05f),
                    size = Size(w * 0.12f, h * 0.25f)
                )
                
                // Gun nozzle cap
                drawRect(
                    color = Color.DarkGray.copy(alpha = alpha),
                    topLeft = Offset(w * 0.41f, h * 0.02f),
                    size = Size(w * 0.18f, h * 0.05f)
                )
                
                // Turret Dome
                drawCircle(
                    color = baseColor,
                    radius = w * 0.22f,
                    center = Offset(w / 2f, h / 2f)
                )
                
                // Turret inner dome rim
                drawCircle(
                    color = Color.DarkGray.copy(alpha = alpha * 0.3f),
                    radius = w * 0.18f,
                    center = Offset(w / 2f, h / 2f),
                    style = Stroke(width = w * 0.04f)
                )
            }
        }
        
        Text(
            text = ammo.toString(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = (size.value * 0.4f).coerceAtLeast(8f).sp
        )
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
