package com.funkyotc.puzzleverse.nonogram.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, "Nonogram") }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
            if (mode == "puzzle" && puzzleId != null) {
                completionRepo.markCompleted(puzzleId)
            }
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Use numbers on the top and left to fill the grid. The numbers tell you how many unbroken lines of filled squares there are in any given row or column.") },
            confirmButton = {
                TextButton(onClick = { showHowToDialog = false }) { Text("OK") }
            }
        )
    }

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("You Win!") },
            text = { Text("You solved the Nonogram!") },
            confirmButton = {
                if (mode == "daily") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.navigate("home") { popUpTo(0) } }) {
                            androidx.compose.material3.Text("Main Menu")
                        }
                        androidx.compose.material3.Button(onClick = { navController.navigate("game/nonogram/standard/new") { popUpTo("home") } }) {
                            androidx.compose.material3.Text("Random Puzzles")
                        }
                    }
                } else if (mode == "puzzle") {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        androidx.compose.material3.Button(onClick = { navController.popBackStack() }) {
                            androidx.compose.material3.Text("Back to List")
                        }
                        val nextId = puzzleId?.let { id ->
                            val diffPuzzles = com.funkyotc.puzzleverse.nonogram.data.NonogramPregenerated.ALL_PUZZLES
                            val currentIndex = diffPuzzles.indexOfFirst { it.id == id }
                            if (currentIndex != -1 && currentIndex + 1 < diffPuzzles.size) diffPuzzles[currentIndex + 1].id else null
                        }
                        if (nextId != null) {
                            androidx.compose.material3.Button(onClick = {
                                navController.popBackStack()
                                navController.navigate("game/nonogram/puzzle/$nextId")
                            }) {
                                androidx.compose.material3.Text("Next Puzzle")
                            }
                        }
                    }
                } else {
                    Button(onClick = { viewModel.startNewGame() }) { Text("Play Again") }
                }
            }
        )
    }

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Game") },
            text = { Text("Are you sure you want to start over?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.startNewGame()
                    showNewGameDialog = false
                }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nonogram") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToDialog = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Game")
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            // Draw Top Clues
            Row(modifier = Modifier.fillMaxWidth().padding(start = 60.dp)) {
                for (c in 0 until state.cols) {
                    Column(
                        modifier = Modifier.weight(1f).wrapContentHeight(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        state.colClues[c].forEach { clue ->
                            Text(text = clue.toString(), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            // Draw Rows
            for (r in 0 until state.rows) {
                Row(modifier = Modifier.fillMaxWidth().height(40.dp)) {
                    // Left Clues
                    Row(
                        modifier = Modifier.width(60.dp).fillMaxHeight(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.rowClues[r].forEachIndexed { index, clue ->
                            Text(text = clue.toString(), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            if (index < state.rowClues[r].size - 1) Spacer(modifier = Modifier.width(4.dp))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    
                    // Grid Cells
                    for (c in 0 until state.cols) {
                        val cellState = state.playerGrid[r][c]
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .border(1.dp, Color.Gray)
                                .background(if (cellState == CellState.FILLED) Color.Black else Color.White)
                                .clickable {
                                    // simple toggle: click once to fill, click again to empty
                                    // if you want cross support, maybe double tap, or toggle button at bottom
                                    viewModel.toggleCell(r, c, isFillAction = true)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (cellState == CellState.CROSSED) {
                                Text("X", color = Color.Red, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Interaction Mode Toggle (for mobile)
            var isFillMode by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { isFillMode = true },
                    colors = ButtonDefaults.buttonColors(containerColor = if (isFillMode) MaterialTheme.colorScheme.primary else Color.Gray)
                ) {
                    Text("Fill")
                }
                Button(
                    onClick = { isFillMode = false },
                    colors = ButtonDefaults.buttonColors(containerColor = if (!isFillMode) MaterialTheme.colorScheme.primary else Color.Gray)
                ) {
                    Text("Cross (X)")
                }
            }
            
            Text("Tip: Click a cell again to make it empty.", fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
