package com.funkyotc.puzzleverse.pullpin.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.pullpin.data.CellType
import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModelFactory
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.settings.data.SettingsRepository

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

private fun ballColor(index: Int): Color = BALL_COLORS[index] ?: Color.Gray

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

    PullPinDialogHowToPlay(showHowToPlay) { showHowToPlay = false }

    if (showWinDialog && state != null) {
        PullPinWinDialog(
            state = state!!,
            onDismiss = { showWinDialog = false },
            onNextPuzzle = {
                showWinDialog = false
                viewModel.startNewGame()
            },
            onBackToBrowser = {
                showWinDialog = false
                navController.popBackStack()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pull the Pin") },
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
                        viewModel.startNewGame()
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Restart")
                    }
                }
            )
        }
    ) { paddingValues ->
        state?.let { gameState ->
            PullPinBoard(
                state = gameState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                onPinClick = { row, col ->
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    viewModel.removePin(row, col)
                }
            )
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

    val animatingBalls = remember(state.balls, state.moves) {
        state.balls.filter { !it.inCup }.map { ball ->
            Triple(ball.row, ball.col, ball.color)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(cols.toFloat() / rows.toFloat())
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
}

@Composable
private fun PullPinCell(
    cell: com.funkyotc.puzzleverse.pullpin.data.Cell,
    gridSize: Int,
    onClick: () -> Unit
) {
    val size = when {
        gridSize <= 5 -> 56.dp
        gridSize <= 6 -> 48.dp
        gridSize <= 7 -> 40.dp
        else -> 36.dp
    }

    val pinAlpha = remember { Animatable(1f) }

    LaunchedEffect(cell.type) {
        if (cell.type != CellType.PIN) {
            pinAlpha.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .size(size)
            .then(
                when (cell.type) {
                    CellType.WALL -> Modifier.background(
                        MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    CellType.PIN -> Modifier
                        .alpha(pinAlpha.value)
                        .clip(CircleShape)
                        .background(Color(0xFFD7CCC8))
                        .border(2.dp, Color(0xFF5D4037), CircleShape)
                        .clickable(onClick = onClick)
                    else -> Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        when (cell.type) {
            CellType.BALL -> {
                val color = ballColor(cell.color ?: 1)
                Box(
                    modifier = Modifier
                        .size(size * 0.7f)
                        .clip(CircleShape)
                        .background(color)
                        .border(1.dp, color.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${cell.color ?: ""}",
                        color = Color.White,
                        fontSize = size.value.sp * 0.3f,
                        softWrap = false
                    )
                }
            }
            CellType.CUP -> {
                val color = ballColor(cell.color ?: 1)
                Box(
                    modifier = Modifier
                        .size(size * 0.8f)
                        .border(2.dp, color, RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${cell.color ?: ""}",
                        color = color,
                        fontSize = size.value.sp * 0.3f,
                        softWrap = false
                    )
                }
            }
            CellType.PIN -> {
                Box(
                    modifier = Modifier
                        .size(size * 0.4f)
                        .clip(CircleShape)
                        .background(Color(0xFF8D6E63))
                )
            }
            CellType.EMPTY -> {}
            CellType.WALL -> {}
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
                    Text("• When pins are removed, balls fall due to gravity")
                    Text("• Balls roll down, then left/right to find their path")
                    Text("• Each colored ball must land in its matching cup")
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
