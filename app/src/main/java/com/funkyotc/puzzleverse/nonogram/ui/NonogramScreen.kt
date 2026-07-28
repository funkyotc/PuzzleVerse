package com.funkyotc.puzzleverse.nonogram.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.nonogram.data.CellState
import com.funkyotc.puzzleverse.nonogram.viewmodel.NonogramViewModel
import com.funkyotc.puzzleverse.nonogram.viewmodel.NonogramViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.funkyotc.puzzleverse.core.ui.animateEntrance
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.core.ui.animatePiecePlacement
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.core.ui.GameHowToDialog
import com.funkyotc.puzzleverse.core.ui.GameConfirmDialog
import com.funkyotc.puzzleverse.core.ui.GameEndDialog

private fun isClueSatisfied(lineCells: List<CellState>, clueIndex: Int, clues: List<Int>): Boolean {
    val runs = mutableListOf<Int>()
    var currentRun = 0
    for (cell in lineCells) {
        if (cell == CellState.FILLED) {
            currentRun++
        } else if (currentRun > 0) {
            runs.add(currentRun)
            currentRun = 0
        }
    }
    if (currentRun > 0) {
        runs.add(currentRun)
    }

    if (clues.size == 1 && clues[0] == 0) {
        return runs.isEmpty()
    }

    if (runs.isEmpty()) return false
    if (runs.size > clues.size) return false

    var prefixCount = 0
    while (prefixCount < clues.size && prefixCount < runs.size && clues[prefixCount] == runs[prefixCount]) {
        prefixCount++
    }

    var suffixCount = 0
    val maxSuffix = minOf(clues.size - prefixCount, runs.size - prefixCount)
    while (suffixCount < maxSuffix && clues[clues.size - 1 - suffixCount] == runs[runs.size - 1 - suffixCount]) {
        suffixCount++
    }

    return clueIndex < prefixCount || clueIndex >= clues.size - suffixCount
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonogramScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    puzzleId: String? = null,
    viewModel: NonogramViewModel = viewModel(factory = NonogramViewModelFactory(streakRepository, mode, puzzleId))
) {
    val state by viewModel.state.collectAsState()
    val soundManager = LocalSoundManager.current
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var isFillMode by remember { mutableStateOf(true) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Nonogram") }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    LaunchedEffect(state.mistakes) {
        if (state.mistakes > 0) {
            soundManager.playSound(SoundManager.SOUND_ID_FAILURE)
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Use numbers on the top and left to fill the grid. The numbers tell you how many unbroken lines of filled squares there are in any given row or column.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (state.isWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val diffPuzzles = com.funkyotc.puzzleverse.nonogram.data.NonogramPregenerated.ALL_PUZZLES
            val currentIndex = diffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextId = if (currentIndex != -1 && currentIndex + 1 < diffPuzzles.size) diffPuzzles[currentIndex + 1].id else null
            if (nextId != null) {
                {
                    navController.popBackStack()
                    navController.navigate("game/nonogram/puzzle/$nextId")
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "You Win!",
            message = "You solved the Nonogram!",
            mode = mode,
            gameId = "nonogram",
            onMainMenuClick = {
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = {
                if (mode == "daily") {
                    navController.navigate("game/nonogram/standard/new") { popUpTo("home") }
                } else {
                    viewModel.startNewGame()
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Game",
            message = "Are you sure you want to start over?",
            onConfirm = {
                viewModel.startNewGame()
                showNewGameDialog = false
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Nonogram",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val isStateReady = state.cols > 0 && state.rows > 0 &&
                        state.playerGrid.size == state.rows &&
                        state.rowClues.size == state.rows &&
                        state.colClues.size == state.cols

                if (isStateReady) {
                    val availableWidth = maxWidth - 32.dp
                    val availableHeight = maxHeight - 64.dp
                    val gridWidthDp = minOf(availableWidth, availableHeight)

                    Column(
                        modifier = Modifier.size(gridWidthDp + 32.dp, gridWidthDp + 64.dp)
                    ) {
                        // Draw Top Clues
                        Row(modifier = Modifier.fillMaxWidth().height(64.dp).padding(start = 32.dp)) {
                            for (c in 0 until state.cols) {
                                val colCells = state.playerGrid.map { it[c] }
                                val clues = state.colClues[c]
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable {
                                            if (viewModel.autoFillColCrosses(c)) {
                                                soundManager.playSound(SoundManager.SOUND_ID_PENCIL_ERASE)
                                            }
                                        },
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    clues.forEachIndexed { index, clue ->
                                        val satisfied = isClueSatisfied(colCells, index, clues)
                                        Text(
                                            text = clue.toString(),
                                            fontSize = 10.sp,
                                            fontWeight = if (satisfied) FontWeight.Normal else FontWeight.Bold,
                                            color = if (satisfied) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f) else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        // Draw Grid Container with Tap & Drag pointerInputs
                        Column(
                            modifier = Modifier
                                .size(gridWidthDp + 32.dp, gridWidthDp)
                                .pointerInput(gridWidthDp, isFillMode) {
                                    detectTapGestures(
                                        onTap = { offset ->
                                            val cellWidthPx = (gridWidthDp.toPx()) / state.cols
                                            val cellHeightPx = (gridWidthDp.toPx()) / state.rows
                                            val gridX = offset.x - 32.dp.toPx()
                                            val gridY = offset.y
                                            val col = (gridX / cellWidthPx).toInt()
                                            val row = (gridY / cellHeightPx).toInt()
                                            if (row in 0 until state.rows && col in 0 until state.cols) {
                                                val current = state.playerGrid[row][col]
                                                val sound = when (current) {
                                                    CellState.EMPTY -> SoundManager.SOUND_ID_TILE_PLACE
                                                    CellState.FILLED -> SoundManager.SOUND_ID_CLICK
                                                    CellState.CROSSED -> SoundManager.SOUND_ID_PENCIL_ERASE
                                                }
                                                soundManager.playSound(sound)
                                                viewModel.cycleCell(row, col)
                                            }
                                        }
                                    )
                                }
                                .pointerInput(gridWidthDp, isFillMode) {
                                    var dragActionState: CellState? = null
                                    val cellsModifiedDuringDrag = mutableSetOf<Pair<Int, Int>>()
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            val cellWidthPx = (gridWidthDp.toPx()) / state.cols
                                            val cellHeightPx = (gridWidthDp.toPx()) / state.rows
                                            val gridX = offset.x - 32.dp.toPx()
                                            val gridY = offset.y
                                            val col = (gridX / cellWidthPx).toInt()
                                            val row = (gridY / cellHeightPx).toInt()
                                            if (row in 0 until state.rows && col in 0 until state.cols) {
                                                val current = state.playerGrid[row][col]
                                                if (current != CellState.CROSSED) {
                                                    val targetModeState = if (isFillMode) CellState.FILLED else CellState.CROSSED
                                                    val target = if (current == targetModeState) CellState.EMPTY else targetModeState

                                                    dragActionState = target
                                                    cellsModifiedDuringDrag.clear()
                                                    cellsModifiedDuringDrag.add(Pair(row, col))
                                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                    viewModel.setCellState(row, col, target)
                                                } else {
                                                    dragActionState = null
                                                    cellsModifiedDuringDrag.clear()
                                                }
                                            }
                                        },
                                        onDrag = { change, _ ->
                                            change.consume()
                                            val target = dragActionState
                                            if (target != null) {
                                                val cellWidthPx = (gridWidthDp.toPx()) / state.cols
                                                val cellHeightPx = (gridWidthDp.toPx()) / state.rows
                                                val gridX = change.position.x - 32.dp.toPx()
                                                val gridY = change.position.y
                                                val col = (gridX / cellWidthPx).toInt()
                                                val row = (gridY / cellHeightPx).toInt()
                                                if (row in 0 until state.rows && col in 0 until state.cols) {
                                                    val cellPair = Pair(row, col)
                                                    if (!cellsModifiedDuringDrag.contains(cellPair)) {
                                                        cellsModifiedDuringDrag.add(cellPair)
                                                        val current = state.playerGrid[row][col]
                                                        if (current != CellState.CROSSED) {
                                                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                                            viewModel.setCellState(row, col, target)
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            dragActionState = null
                                            cellsModifiedDuringDrag.clear()
                                        },
                                        onDragCancel = {
                                            dragActionState = null
                                            cellsModifiedDuringDrag.clear()
                                        }
                                    )
                                }
                        ) {
                            val gridBorderColor = MaterialTheme.colorScheme.onSurface
                            for (r in 0 until state.rows) {
                                Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                                    // Left Clues
                                    Row(
                                        modifier = Modifier
                                            .width(32.dp)
                                            .fillMaxHeight()
                                            .clickable {
                                                if (viewModel.autoFillRowCrosses(r)) {
                                                    soundManager.playSound(SoundManager.SOUND_ID_PENCIL_ERASE)
                                                }
                                            },
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val rowCells = state.playerGrid[r]
                                        val clues = state.rowClues[r]
                                        clues.forEachIndexed { index, clue ->
                                            val satisfied = isClueSatisfied(rowCells, index, clues)
                                            Text(
                                                text = clue.toString(),
                                                fontSize = 10.sp,
                                                fontWeight = if (satisfied) FontWeight.Normal else FontWeight.Bold,
                                                color = if (satisfied) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f) else MaterialTheme.colorScheme.onSurface
                                            )
                                            if (index < clues.size - 1) Spacer(modifier = Modifier.width(2.dp))
                                        }
                                        Spacer(modifier = Modifier.width(2.dp))
                                    }

                                    // Grid Cells Container for Row r with Canvas Drawing Overlay for borders
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .drawWithContent {
                                                drawContent()
                                                val cols = state.cols
                                                if (cols > 0) {
                                                    val cellWidth = size.width / cols
                                                    val thinColor = gridBorderColor.copy(alpha = 0.25f)
                                                    val thickColor = gridBorderColor.copy(alpha = 0.85f)
                                                    val thinStroke = 0.75.dp.toPx()
                                                    val thickStroke = 2.5.dp.toPx()

                                                    // Thin / Thick Vertical line after each col except last
                                                    for (c in 1 until cols) {
                                                        val x = c * cellWidth
                                                        val isFiveLine = c % 5 == 0
                                                        drawLine(
                                                            color = if (isFiveLine) thickColor else thinColor,
                                                            start = Offset(x, 0f),
                                                            end = Offset(x, size.height),
                                                            strokeWidth = if (isFiveLine) thickStroke else thinStroke
                                                        )
                                                    }

                                                    // Horizontal line at bottom of row r
                                                    val isFiveRow = (r + 1) % 5 == 0 && (r + 1) < state.rows
                                                    drawLine(
                                                        color = if (isFiveRow) thickColor else thinColor,
                                                        start = Offset(0f, size.height),
                                                        end = Offset(size.width, size.height),
                                                        strokeWidth = if (isFiveRow) thickStroke else thinStroke
                                                    )

                                                    // Top thick outer border if r == 0
                                                    if (r == 0) {
                                                        drawLine(
                                                            color = thickColor,
                                                            start = Offset(0f, 0f),
                                                            end = Offset(size.width, 0f),
                                                            strokeWidth = thickStroke
                                                        )
                                                    }
                                                    // Bottom thick outer border if r == state.rows - 1
                                                    if (r == state.rows - 1) {
                                                        drawLine(
                                                            color = thickColor,
                                                            start = Offset(0f, size.height),
                                                            end = Offset(size.width, size.height),
                                                            strokeWidth = thickStroke
                                                        )
                                                    }
                                                    // Left thick outer border
                                                    drawLine(
                                                        color = thickColor,
                                                        start = Offset(0f, 0f),
                                                        end = Offset(0f, size.height),
                                                        strokeWidth = thickStroke
                                                    )
                                                    // Right thick outer border
                                                    drawLine(
                                                        color = thickColor,
                                                        start = Offset(size.width, 0f),
                                                        end = Offset(size.width, size.height),
                                                        strokeWidth = thickStroke
                                                    )
                                                }
                                            }
                                    ) {
                                        Row(modifier = Modifier.fillMaxSize()) {
                                            for (c in 0 until state.cols) {
                                                val cellState = state.playerGrid[r][c]
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxHeight()
                                                        .background(MaterialTheme.colorScheme.surface)
                                                        .animateEntrance(delayMillis = (r * state.cols + c) * 10),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    if (cellState == CellState.FILLED) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .background(MaterialTheme.colorScheme.onSurface)
                                                                .animatePiecePlacement(trigger = cellState)
                                                        )
                                                    } else if (cellState == CellState.CROSSED) {
                                                        Text(
                                                            text = "X",
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontSize = 14.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.animatePiecePlacement(trigger = cellState)
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
                } else {
                    CircularProgressIndicator()
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Interaction Mode Toggle (for mobile)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val fillInteractionSource = remember { MutableInteractionSource() }
            Button(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    isFillMode = true
                },
                interactionSource = fillInteractionSource,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFillMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isFillMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.animateTapFeedback(fillInteractionSource)
            ) {
                Text("Fill")
            }
            val crossInteractionSource = remember { MutableInteractionSource() }
            Button(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    isFillMode = false
                },
                interactionSource = crossInteractionSource,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isFillMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (!isFillMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.animateTapFeedback(crossInteractionSource)
            ) {
                Text("Cross (X)")
            }
        }

        Text("Tip: Tap a cell to cycle Empty -> Filled -> Cross (X) -> Empty.", fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
    }
}