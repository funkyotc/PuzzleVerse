package com.funkyotc.puzzleverse.ui.screens.sudoku


import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.viewmodel.SudokuViewModel
import com.funkyotc.puzzleverse.sudoku.viewmodel.SudokuViewModelFactory


@Composable
fun SudokuScreen(mode: String?, context: Context = LocalContext.current, sudokuViewModel: SudokuViewModel = viewModel(factory = SudokuViewModelFactory(context, mode))) {
    val board by sudokuViewModel.board.collectAsState()
    val selectedCell by sudokuViewModel.selectedCell.collectAsState()
    val isGameWon by sudokuViewModel.isGameWon.collectAsState()
    val isPencilOn by sudokuViewModel.isPencilOn.collectAsState()


    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle!") },
            confirmButton = {
                androidx.compose.material3.Button(onClick = { sudokuViewModel.newGame() }) {
                    Text("New Game")
                }
            }
        )
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SudokuBoard(board, selectedCell, sudokuViewModel::onCellSelected)
        ActionRow(isPencilOn = isPencilOn, onPencilToggle = sudokuViewModel::togglePencil, onUndo = sudokuViewModel::undo)
        NumberPad(onNumberSelected = sudokuViewModel::onNumberInput)
    }
}


@Composable
fun SudokuBoard(board: SudokuBoard, selectedCell: SudokuCell?, onCellSelected: (Int, Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .aspectRatio(1f)
    ) {
        Column {
            (0..8).forEach { row ->
                Row {
                    (0..8).forEach { col ->
                        val cell = board.getCell(row, col)
                        SudokuCellView(cell, selectedCell, onCellSelected)
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
fun RowScope.SudokuCellView(cell: SudokuCell, selectedCell: SudokuCell?, onCellSelected: (Int, Int) -> Unit) {
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

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .weight(1f)
            .background(backgroundColor)
            .clickable { onCellSelected(cell.row, cell.col) },
        contentAlignment = Alignment.Center
    ) {
        if (cell.number != 0) {
            val textStyle = if (cell.isHint) {
                TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface, fontSize = 20.sp)
            } else if (cell.isError) {
                TextStyle.Default.copy(color = MaterialTheme.colorScheme.error, fontSize = 20.sp)
            } else {
                TextStyle.Default.copy(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
            }
            Text(
                text = cell.number.toString(),
                style = textStyle
            )
        } else {
            PencilMarks(marks = cell.pencilMarks)
        }
    }
}


@Composable
fun PencilMarks(marks: Set<Int>?) {
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
                            fontSize = 9.sp,
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
fun ActionRow(isPencilOn: Boolean, onPencilToggle: () -> Unit, onUndo: () -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onUndo) {
            Icon(Icons.Filled.Undo, contentDescription = "Undo")
        }
        IconButton(onClick = onPencilToggle) {
            Icon(
                imageVector = Icons.Filled.Create,
                contentDescription = "Pencil",
                tint = if (isPencilOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun NumberPad(onNumberSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0..2).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..3).forEach { col ->
                    val number = row * 3 + col
                    NumberButton(number = number, onClick = { onNumberSelected(number) })
                }
            }
        }
    }
}

@Composable
fun RowScope.NumberButton(number: Int, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .weight(1f)
            .aspectRatio(1f),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
