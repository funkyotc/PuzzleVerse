package com.funkyotc.puzzleverse.ui.screens.sudoku


import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import com.funkyotc.puzzleverse.core.ui.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.viewmodel.SudokuViewModel
import com.funkyotc.puzzleverse.sudoku.viewmodel.SudokuViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import androidx.compose.runtime.LaunchedEffect
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager


@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UnusedBoxWithConstraintsScope")
@Composable
fun SudokuScreen(
    navController: NavController, 
    mode: String?, 
    forceNewGame: Boolean = false, 
    context: Context = LocalContext.current,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    puzzleId: String? = null,
    sudokuViewModel: SudokuViewModel = viewModel(factory = SudokuViewModelFactory(context, mode, forceNewGame, streakRepository, puzzleId))
) {
    val board by sudokuViewModel.board.collectAsState()
    val selectedCell by sudokuViewModel.selectedCell.collectAsState()
    val soundManager = LocalSoundManager.current
    val isGameWon by sudokuViewModel.isGameWon.collectAsState()
    val isPencilOn by sudokuViewModel.isPencilOn.collectAsState()
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHintDialog by remember { mutableStateOf(false) }

    if (showHintDialog) {
        GameConfirmDialog(
            title = "Use a Hint?",
            message = "Are you sure you want to use a hint to reveal part of the puzzle?",
            confirmLabel = "Yes",
            cancelLabel = "Cancel",
            onConfirm = {
                showHintDialog = false
                sudokuViewModel.hint()
            },
            onDismiss = { showHintDialog = false }
        )
    }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            soundManager.playSound(SoundManager.SOUND_ID_VICTORY)
            settingsRepository.addWin()
        }
    }

    if (showHowToDialog) {
        GameHowToDialog(
            instructions = "Fill the 9x9 grid so that each column, each row, and each of the nine 3x3 grids contain all of the digits from 1 to 9.",
            onDismiss = { showHowToDialog = false }
        )
    }

    if (isGameWon) {
        val nextPuzzleAction: (() -> Unit)? = if (mode == "puzzle" && puzzleId != null) {
            val currentPuzzle = com.funkyotc.puzzleverse.sudoku.data.SudokuPregenerated.getPuzzleById(puzzleId)
            val sameDiffPuzzles = if (currentPuzzle != null) {
                com.funkyotc.puzzleverse.sudoku.data.SudokuPregenerated.ALL_PUZZLES
                    .filter { it.difficulty == currentPuzzle.difficulty }
            } else emptyList()
            val currentIndex = sameDiffPuzzles.indexOfFirst { it.id == puzzleId }
            val nextPuzzle = if (currentIndex >= 0 && currentIndex < sameDiffPuzzles.size - 1) {
                sameDiffPuzzles[currentIndex + 1]
            } else null
            if (nextPuzzle != null) {
                {
                    navController.navigate("game/sudoku/puzzle/${nextPuzzle.id}") {
                        popUpTo("sudoku/puzzles")
                    }
                }
            } else null
        } else null

        GameEndDialog(
            isWon = true,
            title = "Congratulations!",
            message = "You solved the puzzle!",
            mode = mode,
            onMainMenuClick = {
                if (mode == "puzzle") {
                    navController.popBackStack()
                } else {
                    navController.navigate("home") { popUpTo(0) }
                }
            },
            onPlayAgainClick = {
                if (mode == "daily") {
                    navController.navigate("game/sudoku/standard/new") { popUpTo("home") }
                } else {
                    sudokuViewModel.newGame()
                }
            },
            onNextPuzzleClick = nextPuzzleAction
        )
    }

    if (showNewGameDialog) {
        GameConfirmDialog(
            title = "New Puzzle",
            message = "Are you sure you want to start a new puzzle? Your current progress will be lost.",
            onConfirm = {
                sudokuViewModel.newGame()
                showNewGameDialog = false
            },
            onDismiss = { showNewGameDialog = false }
        )
    }

    StandardGameLayout(
        title = "Sudoku",
        navController = navController,
        onHowToClick = { showHowToDialog = true },
        onNewGameClick = if (mode != "daily") { { showNewGameDialog = true } } else null,
        actions = {
            val hintInteractionSource = remember { MutableInteractionSource() }
            IconButton(
                onClick = {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    showHintDialog = true
                },
                modifier = Modifier.animateTapFeedback(hintInteractionSource),
                interactionSource = hintInteractionSource
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Hint")
            }
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val containerWidth = this.maxWidth
            val containerHeight = this.maxHeight
            val isLandscape = containerWidth > containerHeight

            if (isLandscape) {
                // Calculate custom maximum width for NumberPad in landscape to prevent vertical overflow
                val maxPadHeight = (containerHeight - 32.dp) - 44.dp - 8.dp // container height minus screen padding (32.dp), action row (44.dp), and spacer (8.dp)
                val maxPadWidthFromHeight = (maxPadHeight - 12.dp).coerceAtLeast(120.dp) // Capped based on 3 rows aspect ratio
                val customMaxPadWidth = minOf(260.dp, maxPadWidthFromHeight)

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val boardSize = minOf(containerWidth * 0.55f, containerHeight - 32.dp).coerceAtLeast(180.dp)
                    
                    Box(
                        modifier = Modifier.size(boardSize),
                        contentAlignment = Alignment.Center
                    ) {
                        SudokuBoard(
                            board = board,
                            selectedCell = selectedCell,
                            modifier = Modifier.fillMaxSize().animateEntrance(trigger = board),
                            boardSize = boardSize,
                            onCellSelected = sudokuViewModel::onCellSelected
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ActionRow(
                            isPencilOn = isPencilOn,
                            onPencilToggle = sudokuViewModel::togglePencil,
                            onUndo = sudokuViewModel::undo,
                            onErase = sudokuViewModel::onErase,
                            isCompact = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        NumberPad(
                            board = board,
                            isPencilOn = isPencilOn,
                            onNumberSelected = sudokuViewModel::onNumberInput,
                            isCompact = true,
                            customMaxPadWidth = customMaxPadWidth
                        )
                    }
                }
            } else {
                val isCompact = (containerHeight - (containerWidth - 32.dp)) < 280.dp
                
                // Let's compute the exact padding and size values used by ActionRow and NumberPad in portrait
                val horizontalPadding = if (isCompact) 8.dp else 16.dp
                val verticalPadding = if (isCompact) 2.dp else 8.dp
                val maxPadWidth = if (isCompact) 260.dp else 360.dp
                val rowSpacing = if (isCompact) 4.dp else 8.dp
                val paddingVal = if (isCompact) 4.dp else 12.dp
                val buttonSize = if (isCompact) 36.dp else 48.dp
                val bottomSpacer = if (isCompact) 4.dp else 16.dp

                // Number pad width in portrait is:
                val padWidth = minOf(containerWidth - (horizontalPadding * 2), maxPadWidth)
                
                // Number pad height is padWidth (since 3 rows of weight(1f) aspect 1f buttons = padWidth) + vertical paddings + row spacings
                val numberPadHeight = padWidth + (verticalPadding * 2) + (rowSpacing * 2)
                
                // Action row height is buttonSize + paddingVal * 2
                val actionRowHeight = buttonSize + (paddingVal * 2)
                
                // Total height needed by bottom controls
                val totalBottomHeight = actionRowHeight + numberPadHeight + bottomSpacer
                
                val maxBoardWidth = containerWidth - 32.dp
                val maxBoardHeight = containerHeight - totalBottomHeight - 16.dp // 16.dp safety margin
                val boardSize = minOf(maxBoardWidth, maxBoardHeight).coerceAtLeast(180.dp)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SudokuBoard(
                            board = board,
                            selectedCell = selectedCell,
                            modifier = Modifier.size(boardSize).animateEntrance(trigger = board),
                            boardSize = boardSize,
                            onCellSelected = sudokuViewModel::onCellSelected
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ActionRow(
                            isPencilOn = isPencilOn,
                            onPencilToggle = sudokuViewModel::togglePencil,
                            onUndo = sudokuViewModel::undo,
                            onErase = sudokuViewModel::onErase,
                            isCompact = isCompact
                        )
                        NumberPad(
                            board = board,
                            isPencilOn = isPencilOn,
                            onNumberSelected = sudokuViewModel::onNumberInput,
                            isCompact = isCompact
                        )
                        Spacer(modifier = Modifier.height(if (isCompact) 4.dp else 16.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun SudokuBoard(
    board: SudokuBoard, 
    selectedCell: SudokuCell?, 
    modifier: Modifier = Modifier, 
    boardSize: Dp,
    onCellSelected: (Int, Int) -> Unit
) {
    val fontSize = (boardSize.value / 18f).sp
    val pencilFontSize = (boardSize.value / 40f).coerceAtLeast(6f).sp

    Box(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Column {
            (0..8).forEach { row ->
                Row {
                    (0..8).forEach { col ->
                        val cell = board.getCell(row, col)
                        SudokuCellView(
                            cell = cell, 
                            selectedCell = selectedCell, 
                            fontSize = fontSize,
                            pencilFontSize = pencilFontSize,
                            onCellSelected = onCellSelected
                        )
                    }
                }
            }
        }

        val thinLineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        val thickLineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellSize = size.width / 9f

            // Draw horizontal lines
            (0..9).forEach { i ->
                val isThick = i % 3 == 0
                val strokeWidth = if (isThick) 2.dp.toPx() else 1.dp.toPx()
                val color = if (isThick) thickLineColor else thinLineColor
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = i * cellSize),
                    end = Offset(x = size.width, y = i * cellSize),
                    strokeWidth = strokeWidth
                )
            }

            // Draw vertical lines
            (0..9).forEach { i ->
                val isThick = i % 3 == 0
                val strokeWidth = if (isThick) 2.dp.toPx() else 1.dp.toPx()
                val color = if (isThick) thickLineColor else thinLineColor
                drawLine(
                    color = color,
                    start = Offset(x = i * cellSize, y = 0f),
                    end = Offset(x = i * cellSize, y = size.height),
                    strokeWidth = strokeWidth
                )
            }
        }
    }
}


@Composable
fun RowScope.SudokuCellView(
    cell: SudokuCell, 
    selectedCell: SudokuCell?, 
    fontSize: TextUnit,
    pencilFontSize: TextUnit,
    onCellSelected: (Int, Int) -> Unit
) {
    val isSelected = cell == selectedCell
    val isHighlighted = selectedCell != null && (cell.row == selectedCell.row || cell.col == selectedCell.col || (cell.row / 3 == selectedCell.row / 3 && cell.col / 3 == selectedCell.col / 3))
    val isSameNumber = selectedCell != null && selectedCell.number != 0 && cell.number == selectedCell.number

    val subgridColor = if ((cell.row / 3 + cell.col / 3) % 2 == 0) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        isSameNumber -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
        isHighlighted -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        else -> subgridColor
    }

    val soundManager = LocalSoundManager.current
    val cellInteractionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .weight(1f)
            .background(backgroundColor)
            .animateTapFeedback(cellInteractionSource)
            .clickable(
                interactionSource = cellInteractionSource,
                indication = null
            ) {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                onCellSelected(cell.row, cell.col)
            },
        contentAlignment = Alignment.Center
    ) {
        if (cell.number != 0) {
            val textStyle = if (cell.isHint) {
                TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface, fontSize = fontSize)
            } else if (cell.isError) {
                TextStyle.Default.copy(color = MaterialTheme.colorScheme.error, fontSize = fontSize)
            } else {
                TextStyle.Default.copy(color = MaterialTheme.colorScheme.primary, fontSize = fontSize)
            }
            Text(
                text = cell.number.toString(),
                style = textStyle,
                modifier = Modifier.animatePiecePlacement(trigger = cell.number)
            )
        } else {
            PencilMarks(marks = cell.pencilMarks, fontSize = pencilFontSize)
        }
    }
}


@Composable
fun PencilMarks(marks: Set<Int>?, fontSize: TextUnit) {
    // Gracefully handle null or empty sets to prevent crashes and unnecessary composition.
    if (marks.isNullOrEmpty()) {
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp), // A small padding to prevent numbers from touching the cell borders.
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Create a 3x3 grid for numbers 1-9.
        for (gridRow in 0..2) {
            Row(
                modifier = Modifier.weight(1f), // Each row takes equal height.
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (gridCol in 0..2) {
                    val number = gridRow * 3 + gridCol + 1
                    Text(
                        // If the number is in the marks set, display it. Otherwise, display an empty string to maintain grid alignment.
                        text = if (marks.contains(number)) number.toString() else "",
                        style = TextStyle.Default.copy(
                            fontSize = fontSize,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.weight(1f) // Each number takes equal width.
                    )
                }
            }
        }
    }
}


@Composable
fun ActionRow(
    isPencilOn: Boolean, 
    onPencilToggle: () -> Unit, 
    onUndo: () -> Unit, 
    onErase: () -> Unit,
    isCompact: Boolean = false
) {
    val paddingVal = if (isCompact) 4.dp else 12.dp
    val spacingVal = if (isCompact) 12.dp else 24.dp
    val iconSize = if (isCompact) 20.dp else 24.dp
    val buttonSize = if (isCompact) 36.dp else 48.dp

    val soundManager = LocalSoundManager.current
    val undoInteractionSource = remember { MutableInteractionSource() }
    val pencilInteractionSource = remember { MutableInteractionSource() }
    val eraseInteractionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.padding(vertical = paddingVal),
        horizontalArrangement = Arrangement.spacedBy(spacingVal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                onUndo()
            },
            modifier = Modifier.size(buttonSize).animateTapFeedback(undoInteractionSource),
            interactionSource = undoInteractionSource
        ) {
            Icon(
                Icons.AutoMirrored.Filled.Undo, 
                contentDescription = "Undo",
                modifier = Modifier.size(iconSize)
            )
        }
        IconButton(
            onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                onPencilToggle()
            },
            modifier = Modifier.size(buttonSize).animateTapFeedback(pencilInteractionSource),
            colors = if (isPencilOn) {
                IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            } else {
                IconButtonDefaults.iconButtonColors()
            },
            interactionSource = pencilInteractionSource
        ) {
            Icon(
                Icons.Filled.Create, 
                contentDescription = "Pencil",
                modifier = Modifier.size(iconSize)
            )
        }
        IconButton(
            onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_PENCIL_ERASE)
                onErase()
            },
            modifier = Modifier.size(buttonSize).animateTapFeedback(eraseInteractionSource),
            interactionSource = eraseInteractionSource
        ) {
            Icon(
                Icons.Filled.Delete, 
                contentDescription = "Erase",
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
fun NumberPad(
    board: SudokuBoard, 
    isPencilOn: Boolean, 
    onNumberSelected: (Int) -> Unit,
    isCompact: Boolean = false,
    customMaxPadWidth: Dp? = null
) {
    val horizontalPadding = if (isCompact) 8.dp else 16.dp
    val verticalPadding = if (isCompact) 2.dp else 8.dp
    val maxPadWidth = customMaxPadWidth ?: (if (isCompact) 260.dp else 360.dp)
    val rowSpacing = if (isCompact) 4.dp else 8.dp

    Box(
        modifier = Modifier
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .widthIn(max = maxPadWidth)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(rowSpacing)
        ) {
            (0..2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (1..3).forEach { col ->
                        val number = row * 3 + col
                        val isCompleted = board.isNumberCompleted(number)
                        NumberButton(
                            number = number, 
                            isPencilOn = isPencilOn, 
                            isCompleted = isCompleted, 
                            onClick = { onNumberSelected(number) },
                            isCompact = isCompact
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.NumberButton(
    number: Int, 
    isPencilOn: Boolean, 
    isCompleted: Boolean, 
    onClick: () -> Unit,
    isCompact: Boolean = false
) {
    val alpha = if (isCompleted) 0.3f else 1.0f
    
    val textStyle = when {
        isPencilOn && isCompact -> MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f * alpha),
            fontSize = 11.sp
        )
        isPencilOn -> MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f * alpha)
        )
        isCompact -> MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
            fontSize = 18.sp
        )
        else -> MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
        )
    }

    val buttonPadding = if (isCompact) 2.dp else 4.dp

    val soundManager = LocalSoundManager.current
    val numberInteractionSource = remember { MutableInteractionSource() }
    Surface(
        modifier = Modifier
            .padding(buttonPadding)
            .clip(CircleShape)
            .animateTapFeedback(numberInteractionSource)
            .clickable(
                interactionSource = numberInteractionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                enabled = !isCompleted,
                onClick = {
                    val sound = if (isPencilOn) SoundManager.SOUND_ID_KEY_PRESS else SoundManager.SOUND_ID_TILE_PLACE
                    soundManager.playSound(sound)
                    onClick()
                }
            )
            .weight(1f)
            .aspectRatio(1f),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f * alpha)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = textStyle
            )
        }
    }
}
