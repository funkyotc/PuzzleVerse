package com.funkyotc.puzzleverse.pullpin.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.pullpin.data.Cell
import com.funkyotc.puzzleverse.pullpin.data.CellType
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.launch

private val BALL_COLORS = mapOf(
    1 to Color(0xFFE53935),
    2 to Color(0xFF1E88E5),
    3 to Color(0xFF43A047),
    4 to Color(0xFFFDD835),
    5 to Color(0xFF8E24AA),
    6 to Color(0xFFFB8C00),
    7 to Color(0xFF00ACC1),
    8 to Color(0xFFD81B60)
)

private val COLOR_LABELS = mapOf(
    1 to "Red", 2 to "Blue", 3 to "Green", 4 to "Yellow",
    5 to "Purple", 6 to "Orange", 7 to "Cyan", 8 to "Pink"
)

private fun ballColor(index: Int): Color = BALL_COLORS[index] ?: Color.Gray
private fun colorLabel(index: Int): String = COLOR_LABELS[index] ?: "?"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullPinScreen(
    navController: NavController,
    mode: String?,
    streakRepository: StreakRepository?,
    settingsRepository: SettingsRepository?,
    puzzleId: String? = null,
    forceNewGame: Boolean = false
) {
    val soundManager = LocalSoundManager.current
    val context = LocalContext.current

    val viewModel: PullPinViewModel = viewModel(
        factory = PullPinViewModelFactory(streakRepository, mode, puzzleId)
    )

    val state by viewModel.state.collectAsState()

    var showHowToPlay by remember { mutableStateOf(false) }
    var showWinDialog by remember { mutableStateOf(false) }
    var prevInCup by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(streakRepository) {
        streakRepository?.let {
            viewModel.setCompletionRepo(
                PuzzleCompletionRepository(context, "PullPin")
            )
        }
    }

    LaunchedEffect(state?.isWon) {
        if (state?.isWon == true) {
            showWinDialog = true
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
        }
    }

    LaunchedEffect(state?.moves) {
        state?.let { s ->
            val nowInCup = s.balls.filter { it.inCup }.map { it.color }.toSet()
            val newInCup = nowInCup - prevInCup
            if (newInCup.isNotEmpty()) {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
            }
            prevInCup = nowInCup
        }
    }

    PullPinDialogHowToPlay(showHowToPlay) { showHowToPlay = false }

    state?.let { s ->
        if (showWinDialog) {
            PullPinWinDialog(
                state = s,
                onDismiss = { showWinDialog = false },
                onNextPuzzle = {
                    showWinDialog = false
                    prevInCup = emptySet()
                    viewModel.startNewGame()
                },
                onBackToBrowser = {
                    showWinDialog = false
                    navController.popBackStack()
                }
            )
        }
    }

    Scaffold(
        topBar = {
                TopAppBar(
                title = {
                    Column {
                        Text("Pull the Pin")
                        state?.let { s ->
                            Text(
                                text = "${s.level.difficulty} • ${s.level.subtitle}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToPlay = true }) {
                        Text("?", fontSize = 18.sp)
                    }
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        prevInCup = emptySet()
                        viewModel.startNewGame()
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Restart")
                    }
                }
            )
        }
    ) { paddingValues ->
        state?.let { gameState ->
            val ballsRemaining = gameState.balls.count { !it.inCup }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Balls: $ballsRemaining/${gameState.balls.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Moves: ${gameState.moves}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                PullPinColorLegend(
                    balls = gameState.balls,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                PullPinBoard(
                    state = gameState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onPinClick = { row, col ->
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        viewModel.removePin(row, col)
                    }
                )
            }
        }
    }
}

@Composable
private fun PullPinColorLegend(
    balls: List<com.funkyotc.puzzleverse.pullpin.data.BallState>,
    modifier: Modifier = Modifier
) {
    val activeColors = balls.map { it.color }.distinct().sorted()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        activeColors.forEach { colorIdx ->
            val inCup = balls.any { it.color == colorIdx && it.inCup }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(ballColor(colorIdx).copy(alpha = if (inCup) 0.4f else 1f))
                        .border(
                            1.dp,
                            ballColor(colorIdx).copy(alpha = if (inCup) 0.4f else 1f),
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "$colorIdx:${colorLabel(colorIdx)}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (inCup) 0.4f else 1f)
                )
                if (inCup) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = ballColor(colorIdx)
                    )
                }
            }
        }
    }
}

@Composable
private fun PullPinBoard(
    state: PullPinState,
    modifier: Modifier = Modifier,
    onPinClick: (Int, Int) -> Unit
) {
    val rows = state.level.rows
    val cols = state.level.cols

    Box(
        modifier = modifier
            .aspectRatio(cols.toFloat() / rows.toFloat()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (r in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (c in 0 until cols) {
                        val cell = state.grid[r][c]
                        PullPinCell(
                            cell = cell,
                            gridSize = cols,
                            onClick = {
                                if (cell.type == CellType.PIN) {
                                    onPinClick(r, c)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PullPinCell(
    cell: Cell,
    gridSize: Int,
    onClick: () -> Unit
) {
    val size = when {
        gridSize <= 5 -> 56.dp
        gridSize <= 6 -> 48.dp
        gridSize <= 7 -> 40.dp
        else -> 36.dp
    }

    var prevType by remember { mutableStateOf(cell.type) }
    val fadingPinAlpha = remember { Animatable(0f) }
    var showFadingPin by remember { mutableStateOf(false) }

    LaunchedEffect(cell.type) {
        if (prevType == CellType.PIN && cell.type != CellType.PIN) {
            showFadingPin = true
            fadingPinAlpha.snapTo(1f)
            fadingPinAlpha.animateTo(0f, tween(durationMillis = 400, easing = FastOutLinearInEasing))
            showFadingPin = false
        }
        prevType = cell.type
    }

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (showFadingPin) {
            Box(
                modifier = Modifier
                    .size(size)
                    .graphicsLayer(alpha = fadingPinAlpha.value)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFFD7CCC8))
                    .border(2.dp, Color(0xFF5D4037), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .size(size * 0.4f)
                        .clip(CircleShape)
                        .background(Color(0xFF8D6E63))
                )
            }
        }

        if (cell.type == CellType.WALL) {
            Box(
                Modifier
                    .size(size)
                    .shadow(2.dp, RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp))
            )
        }

        if (cell.type == CellType.PIN) {
            Box(
                modifier = Modifier
                    .size(size)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFFD7CCC8))
                    .border(2.dp, Color(0xFF5D4037), CircleShape)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .size(size * 0.4f)
                        .clip(CircleShape)
                        .background(Color(0xFF8D6E63))
                )
            }
        }

        if (cell.type == CellType.BALL) {
            val ballColor = ballColor(cell.color ?: 1)
            val scale = remember { Animatable(0.3f) }

            LaunchedEffect(Unit) {
                launch {
                    scale.animateTo(1.2f, tween(150, easing = FastOutSlowInEasing))
                }
                launch {
                    scale.animateTo(1.0f, spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ))
                }
            }

            Box(
                modifier = Modifier
                    .size(size * 0.7f)
                    .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                    .clip(CircleShape)
                    .background(ballColor)
                    .border(1.dp, ballColor.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${cell.color ?: ""}",
                    color = Color.White,
                    fontSize = (size.value * 0.3f).sp,
                    fontWeight = FontWeight.Bold,
                    softWrap = false
                )
            }
        }

        if (cell.type == CellType.CUP) {
            val cupColor = ballColor(cell.color ?: 1)
            Box(
                modifier = Modifier
                    .size(size * 0.9f)
                    .border(
                        border = androidx.compose.foundation.BorderStroke(2.dp, cupColor),
                        shape = RoundedCornerShape(
                            topStart = 0.dp, topEnd = 0.dp,
                            bottomStart = 6.dp, bottomEnd = 6.dp
                        )
                    )
                    .clip(RoundedCornerShape(
                        topStart = 0.dp, topEnd = 0.dp,
                        bottomStart = 6.dp, bottomEnd = 6.dp
                    )),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${cell.color ?: ""}",
                    color = cupColor,
                    fontSize = (size.value * 0.3f).sp,
                    fontWeight = FontWeight.Bold,
                    softWrap = false
                )
            }
        }
    }
}

@Composable
private fun PullPinDialogHowToPlay(visible: Boolean, onDismiss: () -> Unit) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("How to Play") },
            text = {
                Column {
                    Text("Pull the Pin is a logic puzzle game!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Tap on pins to remove them")
                    Text("• Each ball falls straight down when its path is clear")
                    Text("• Each colored ball must land in its matching cup")
                    Text("• The legend shows which colors are in cup (✓)")
                    Text("• Clear all balls to win!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tip: Plan which pin to remove first!")
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Got it!")
                }
            }
        )
    }
}

@Composable
private fun PullPinWinDialog(
    state: PullPinState,
    onDismiss: () -> Unit,
    onNextPuzzle: () -> Unit,
    onBackToBrowser: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Victory!") },
        text = {
            Column {
                Text("All balls are in their cups!")
                Text("Moves: ${state.moves}")
            }
        },
        confirmButton = {
            Button(onClick = onNextPuzzle) {
                Text("Next Puzzle")
            }
        },
        dismissButton = {
            TextButton(onClick = onBackToBrowser) {
                Text("Back")
            }
        }
    )
}
