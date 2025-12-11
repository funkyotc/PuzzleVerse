package com.funkyotc.puzzleverse.constellations.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.viewmodel.ConstellationsViewModel
import com.funkyotc.puzzleverse.constellations.viewmodel.ConstellationsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstellationsScreen(
    navController: NavController, 
    mode: String? = "standard",
    context: Context = LocalContext.current,
    constellationsViewModel: ConstellationsViewModel = viewModel(factory = ConstellationsViewModelFactory(mode))
) {
    val puzzle by constellationsViewModel.puzzle.collectAsState()
    val isGameWon by constellationsViewModel.isGameWon.collectAsState()
    var showNewGameDialog by remember { mutableStateOf(false) }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { /* Do nothing, wait for user entry */ },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle!") },
            confirmButton = {
                Button(onClick = { constellationsViewModel.loadNewPuzzle() }) {
                    Text("New Game")
                }
            }
        )
    }
    
    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Puzzle") },
            text = { Text("Are you sure you want to start a new puzzle? Your current progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    constellationsViewModel.loadNewPuzzle()
                    showNewGameDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Constellations") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (mode != "daily") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            puzzle?.let { p ->
                val regionColors = rememberRegionColors(p.regions.keys)
                var gridSize by remember { mutableStateOf(IntSize.Zero) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .onGloballyPositioned { coordinates ->
                            gridSize = coordinates.size
                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val cellSize = gridSize.width / p.size.toFloat()
                                val col = (offset.x / cellSize).toInt()
                                val row = (offset.y / cellSize).toInt()
                                if (row in 0 until p.size && col in 0 until p.size) {
                                    constellationsViewModel.onCellClicked(row, col)
                                }
                            }
                        }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                val offset = change.position
                                val cellSize = gridSize.width / p.size.toFloat()
                                val col = (offset.x / cellSize).toInt()
                                val row = (offset.y / cellSize).toInt()
                                if (row in 0 until p.size && col in 0 until p.size) {
                                    constellationsViewModel.onDragOver(row, col)
                                }
                            }
                        }
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        p.cells.forEach { row ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                row.forEach { cell ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .background(regionColors[cell.regionId] ?: Color.LightGray)
                                            .border(1.dp, Color.Black),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        when (cell.state) {
                                            CellState.STAR -> Icon(
                                                Icons.Filled.Star,
                                                contentDescription = "Star",
                                                tint = Color.Black
                                            )
                                            CellState.CROSS -> Icon(
                                                Icons.Filled.Close,
                                                contentDescription = "Cross",
                                                tint = if (cell.isAuto) Color.DarkGray else Color(0xFFD32F2F)
                                            )
                                            CellState.EMPTY -> {}
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

@Composable
fun rememberRegionColors(regionIds: Set<Int>): Map<Int, Color> {
    val colors = listOf(
        Color(0xFFE57373), Color(0xFF81C784), Color(0xFF64B5F6), Color(0xFFFFD54F),
        Color(0xFFBA68C8), Color(0xFF4DB6AC), Color(0xFFFF8A65), Color(0xFFA1887F),
        Color(0xFF90A4AE), Color(0xFFF06292)
    )
    return regionIds.zip(colors + colors + colors).toMap()
}
