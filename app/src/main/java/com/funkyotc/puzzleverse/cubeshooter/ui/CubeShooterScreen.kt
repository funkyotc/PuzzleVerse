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
import androidx.compose.runtime.withFrameMillis
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import androidx.compose.runtime.key
import androidx.compose.ui.platform.LocalContext
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.cubeshooter.data.CubeShooterPregenerated
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.funkyotc.puzzleverse.core.ui.*

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

    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Cube Shooter") }

    val state = stateOpt ?: return

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    var lastProjectileCount by remember { mutableIntStateOf(0) }
    LaunchedEffect(state.projectiles.size) {
        if (state.projectiles.size > lastProjectileCount) {
            soundManager.playSound(SoundManager.SOUND_ID_CLICK, 0.72f, cooldownMs = 70L)
        }
        lastProjectileCount = state.projectiles.size
    }

    // Keep ticking the ViewModel
    LaunchedEffect(state.isWon, state.isGameOver) {
        if (!state.isWon && !state.isGameOver) {
            var lastTime = System.currentTimeMillis()
            while (true) {
                withFrameMillis {
                    val now = System.currentTimeMillis()
                    val dtMs = now - lastTime
                    lastTime = now
                    if (dtMs > 0) {
                        viewModel.tick(dtMs)
                    }
                }
            }
        }
    }

    val overlayRoot = remember { mutableStateOf(Offset.Zero) }
    val sourceColRootPositions = remember { mutableStateMapOf<Int, Offset>() }
    val traySlotRootPositions = remember { mutableStateMapOf<Int, Offset>() }
    var trackEntryRootPos by remember { mutableStateOf(Offset.Zero) }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Cubes form Tetris-like clusters in the grid. Tanks on the track move clockwise. When a tank is adjacent to a same-color cube, it fires automatically, clearing the cube and losing 1 ammo. Tap a tank in the tray to send it onto the track. Clear all cubes to win!",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val allPuzzles = CubeShooterPregenerated.ALL_LEVELS
            val currentIndex = allPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < allPuzzles.size) allPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    navController.popBackStack()
                    navController.navigate("game/cubeshooter/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Victory!",
            message = "You cleared all cubes! Final Score: ${state.score}",
            mode = mode,
            onMainMenuClick = {
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = { viewModel.startNewGame() },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (state.isGameOver && !state.isWon) {
        GameEndDialog(
            isWon = false,
            title = "Game Over",
            message = "Storage tray overflowed. Try again!",
            mode = mode,
            onMainMenuClick = { navController.navigate("home") { popUpTo(0) } },
            onPlayAgainClick = { viewModel.startNewGame() }
        )
    }

    StandardGameLayout(
        title = "Cube Shooter",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        actions = {
            IconButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                viewModel.startNewGame()
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { overlayRoot.value = it.positionInRoot() }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
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
                val totalCols = cols + 4
                val totalRows = rows + 4

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
                        val trackPathWidth = (cols + 3) * cellSize.toPx()
                        val trackPathHeight = (rows + 3) * cellSize.toPx()
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
                                    val isCube = r in 2..rows + 1 && c in 2..cols + 1

                                    Box(
                                        modifier = Modifier
                                            .size(cellSize)
                                            .padding(1.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isTrack) {
                                            // Empty content so it acts as a transparent layout spacing placeholder
                                        } else if (isCube) {
                                            val cubeColorId = state.level.grid[r - 2][c - 2]
                                            if (cubeColorId != null) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(getComposeColor(cubeColorId))
                                                        .animatePiecePlacement(trigger = cubeColorId)
                                                )
                                            } else {
                                                // Check for Fading/Exploding Cubes
                                                val fadingCube = state.fadingCubes.find { it.row == r - 2 && it.col == c - 2 }
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
                    val loopLen = 2 * (cols + rows + 4)
                    val tankSize = 56.dp
                    val edgeBump = 15f
                    state.track.forEach { trackTank ->
                        val pos = trackTank.position
                        val idx1 = pos.toInt() % loopLen
                        val idx2 = (idx1 + 1) % loopLen
                        val fraction = pos - pos.toInt()

                        val coord1 = getTrackCellCoordinates(idx1, cols, rows)
                        val coord2 = getTrackCellCoordinates(idx2, cols, rows)

                        val r = coord1.first + (coord2.first - coord1.first) * fraction
                        val c = coord1.second + (coord2.second - coord1.second) * fraction

                        val sideR = coord1.first
                        val sideC = coord1.second
                        val bumpX = when {
                            sideC == 0 -> -edgeBump
                            sideC == cols + 3 -> edgeBump
                            else -> 0f
                        }
                        val bumpY = when {
                            sideR == 0 -> -edgeBump
                            sideR == rows + 3 -> edgeBump
                            else -> 0f
                        }
                        val xOffset = c * cellSize.value + (cellSize.value - tankSize.value) / 2f + bumpX
                        val yOffset = r * cellSize.value + (cellSize.value - tankSize.value) / 2f + bumpY

                        val angle = when {
                            sideR == 0 -> 180f         // Top edge: points DOWN
                            sideC == cols + 3 -> 270f  // Right edge: points LEFT
                            sideR == rows + 3 -> 0f    // Bottom edge: points UP
                            sideC == 0 -> 90f          // Left edge: points RIGHT
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

                    // 4. Track entry position marker (matches TrackTank top-left positioning)
                    val middleColEntry = (cols + 3) / 2
                    val entryXOff = middleColEntry * cellSize.value + (cellSize.value - tankSize.value) / 2f
                    val entryYOff = (rows + 3) * cellSize.value + (cellSize.value - tankSize.value) / 2f + edgeBump
                    Box(
                        modifier = Modifier
                            .offset(x = entryXOff.dp, y = entryYOff.dp)
                            .size(1.dp)
                            .onGloballyPositioned { trackEntryRootPos = it.positionInRoot() }
                    )
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
                                      val isDispatchEnabled = state.track.size + state.transitions.size < 5 && tank.ammo > 0
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
                                      val interactionSource = remember(i) { MutableInteractionSource() }
                                      TankView(
                                          colorId = tank.color,
                                          ammo = tank.ammo,
                                          size = 56.dp, // Bigger tanks
                                          alpha = alpha,
                                          angle = 0f,
                                          modifier = Modifier
                                              .onGloballyPositioned { traySlotRootPositions[i] = it.positionInRoot() }
                                              .then(borderModifier)
                                              .clickable(
                                                  interactionSource = interactionSource,
                                                  indication = null,
                                                  enabled = isDispatchEnabled
                                              ) {
                                                 soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                 viewModel.dispatchFromStorage(i)
                                              }
                                              .animateTapFeedback(interactionSource)
                                      )
                                  } else {
                                      // Empty slot
                                      Box(
                                          modifier = Modifier
                                              .size(56.dp) // Bigger slot
                                              .onGloballyPositioned { traySlotRootPositions[i] = it.positionInRoot() }
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
                                                      val lockedCols = state.transitions.mapNotNull { it.fromCol }.toSet()
                                                      val isDispatchEnabled = isTop && state.track.size + state.transitions.size < 5 && colIndex !in lockedCols
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
                                                      val posModifier = if (isTop) {
                                                          Modifier.onGloballyPositioned { sourceColRootPositions[colIndex] = it.positionInRoot() }
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
                                                         val interactionSource = remember(originalIndex) { MutableInteractionSource() }
                                                         TankView(
                                                             colorId = tank.color,
                                                             ammo = tank.ammo,
                                                             size = 56.dp, // Match storage tank size
                                                             alpha = alpha,
                                                             angle = 0f,
                                                              modifier = Modifier
                                                                  .offset(y = animatedY)
                                                                  .then(posModifier)
                                                                  .then(borderModifier)
                                                                  .clickable(
                                                                      interactionSource = interactionSource,
                                                                      indication = null,
                                                                      enabled = isDispatchEnabled
                                                                  ) {
                                                                     soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                                     viewModel.dispatchFromSource(colIndex)
                                                                 }
                                                                 .animateTapFeedback(interactionSource)
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

        // Overlay: transitioning tanks (source → track)
        val rootPos = overlayRoot.value
        if (rootPos != Offset.Zero) {
            state.transitions.forEach { t ->
                key(t.id) {
                    val anim = remember { Animatable(0f) }
                    LaunchedEffect(Unit) {
                        anim.snapTo(0f)
                        anim.animateTo(1f, animationSpec = tween(durationMillis = 200))
                        viewModel.completeTransition(t.id)
                    }
                    val progress = anim.value
                    val originRoot = when {
                        t.fromCol != null -> sourceColRootPositions[t.fromCol]
                        t.fromTraySlot != null -> traySlotRootPositions[t.fromTraySlot]
                        else -> null
                    }
                    val targetRoot = trackEntryRootPos
                    if (originRoot != null && targetRoot != Offset.Zero) {
                        val x = originRoot.x + (targetRoot.x - originRoot.x) * progress
                        val y = originRoot.y + (targetRoot.y - originRoot.y) * progress
                        TankView(
                            colorId = t.tank.color, ammo = t.tank.ammo,
                            size = 56.dp, angle = 0f,
                            modifier = Modifier
                                .offset { IntOffset((x - rootPos.x).roundToInt(), (y - rootPos.y).roundToInt()) }
                        )
                    }
                }
            }

            // Overlay: returning tanks (track → tray)
            state.returns.forEach { r ->
                key(r.id) {
                    val anim = remember { Animatable(0f) }
                    LaunchedEffect(Unit) {
                        anim.snapTo(0f)
                        anim.animateTo(1f, animationSpec = tween(durationMillis = 200))
                        viewModel.completeReturn(r.id)
                    }
                    val progress = anim.value
                    val targetIdx = state.storageTray.size
                    val targetSlotRoot = traySlotRootPositions[targetIdx]
                    if (targetSlotRoot != null) {
                        val originRoot = trackEntryRootPos
                        val x = originRoot.x + (targetSlotRoot.x - originRoot.x) * progress
                        val y = originRoot.y + (targetSlotRoot.y - originRoot.y) * progress
                        TankView(
                            colorId = r.tank.color, ammo = r.tank.ammo,
                            size = 56.dp, angle = 0f,
                            modifier = Modifier
                                .offset { IntOffset((x - rootPos.x).roundToInt(), (y - rootPos.y).roundToInt()) }
                        )
                    }
                }
            }
        }
            }
        }
}

private fun isTrackCell(r: Int, c: Int, cols: Int, rows: Int): Boolean {
    return (r == 0 && c in 1..cols + 2) ||
           (r in 1..rows + 2 && c == cols + 3) ||
           (r == rows + 3 && c in 1..cols + 2) ||
           (r in 1..rows + 2 && c == 0)
}

private fun getTrackIndex(r: Int, c: Int, cols: Int, rows: Int): Int {
    val topCount = cols + 2
    val rightCount = rows + 2
    return when {
        r == 0 && c in 1..cols + 2 -> {
            c - 1
        }
        r in 1..rows + 2 && c == cols + 3 -> {
            topCount + (r - 1)
        }
        r == rows + 3 && c in 1..cols + 2 -> {
            topCount + rightCount + (cols + 2 - c)
        }
        r in 1..rows + 2 && c == 0 -> {
            topCount + rightCount + topCount + (rows + 2 - r)
        }
        else -> -1
    }
}

private fun getTrackArrow(r: Int, c: Int, cols: Int, rows: Int): String {
    return when {
        r == 0 -> "→"
        c == cols + 3 -> "↓"
        r == rows + 3 -> "←"
        c == 0 -> "↑"
        else -> ""
    }
}

private fun getComposeColor(colorId: Int?): Color {
    return when (colorId) {
        0 -> Color(0xFF00BCD4) // Cyan
        1 -> Color(0xFFE040FB) // Magenta (vivid, not pink-red)
        2 -> Color(0xFFFFEB3B) // Yellow
        3 -> Color(0xFF4CAF50) // Green
        4 -> Color(0xFFFF9800) // Orange
        5 -> Color(0xFF9C27B0) // Purple
        6 -> Color(0xFFE53935) // Red
        7 -> Color(0xFF2196F3) // Blue
        8 -> Color(0xFF00897B) // Teal
        9 -> Color(0xFFF48FB1) // Pink (lighter)
        10 -> Color(0xFF3F51B5) // Indigo
        11 -> Color(0xFFC0CA33) // Lime (bright yellow-green)
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
    val textColor = if (colorId == 2 || colorId == 4 || colorId == 9 || colorId == 11) Color(0xFF1A1A1A) else Color.White

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
                    topLeft = Offset(w * 0.15f, h * 0.17f),
                    size = Size(w * 0.7f, h * 0.66f),
                    cornerRadius = CornerRadius(w * 0.1f, w * 0.1f)
                )
                
                // Top Tread (horizontal, follows the path direction)
                drawRoundRect(
                    color = Color.DarkGray.copy(alpha = alpha),
                    topLeft = Offset(w * 0.12f, h * 0.05f),
                    size = Size(w * 0.76f, h * 0.13f),
                    cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
                )
                
                // Bottom Tread (horizontal, follows the path direction)
                drawRoundRect(
                    color = Color.DarkGray.copy(alpha = alpha),
                    topLeft = Offset(w * 0.12f, h * 0.82f),
                    size = Size(w * 0.76f, h * 0.13f),
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
    val topCount = cols + 2
    val rightCount = rows + 2
    val loopLen = 2 * (cols + rows + 4)
    val idx = (index % loopLen + loopLen) % loopLen
    return when {
        idx < topCount -> {
            Pair(0, idx + 1)
        }
        idx < topCount + rightCount -> {
            val r = idx - topCount + 1
            Pair(r, cols + 3)
        }
        idx < topCount + rightCount + topCount -> {
            val c = (cols + 2) - (idx - (topCount + rightCount))
            Pair(rows + 3, c)
        }
        else -> {
            val r = (rows + 2) - (idx - (topCount + rightCount + topCount))
            Pair(r, 0)
        }
    }
}
