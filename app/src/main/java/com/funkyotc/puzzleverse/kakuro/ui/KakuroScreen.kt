package com.funkyotc.puzzleverse.kakuro.ui

import androidx.compose.foundation.Canvas
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
import com.funkyotc.puzzleverse.kakuro.data.CellType
import com.funkyotc.puzzleverse.kakuro.data.KakuroCell
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModel
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModelFactory
import com.funkyotc.puzzleverse.settings.data.SettingsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakuroScreen(
    navController: NavController,
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    mode: String? = "standard",
    viewModel: KakuroViewModel = viewModel(factory = KakuroViewModelFactory(streakRepository, mode))
) {
    val state by viewModel.state.collectAsState()
    var showHowToDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    LaunchedEffect(state.isWon) {
        if (state.isWon) {
            settingsRepository.addWin()
        }
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Fill white cells with numbers 1-9. Numbers in a run cannot repeat, and must sum up to the clue numbers. The top-right number is the horizontal sum, bottom-left is vertical.") },
            confirmButton = { TextButton(onClick = { showHowToDialog = false }) { Text("OK") } }
        )
    }

    if (state.isWon) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("You Win!") },
            text = { Text("You solved the Kakuro puzzle!") },
            confirmButton = {
                Button(onClick = { viewModel.startNewGame() }) { Text("Play Again") }
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
                title = { Text("Kakuro") },
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
            
            // Grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(state.cols.toFloat() / state.rows.toFloat())
                    .background(Color.Black)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    for (r in 0 until state.rows) {
                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            for (c in 0 until state.cols) {
                                val cell = state.grid[r][c]
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .border(1.dp, Color.Gray)
                                        .clickable {
                                            if (cell.type == CellType.PLAYER_INPUT) {
                                                selectedCell = Pair(r, c)
                                            }
                                        }
                                ) {
                                    KakuroCellView(cell, isSelected = selectedCell == Pair(r, c))
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Numpad
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (col in 1..3) {
                            val num = row * 3 + col
                            Button(
                                onClick = {
                                    selectedCell?.let { (r, c) ->
                                        viewModel.setCellValue(r, c, num)
                                    }
                                },
                                modifier = Modifier.size(64.dp)
                            ) {
                                Text(num.toString(), fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KakuroCellView(cell: KakuroCell, isSelected: Boolean) {
    if (cell.type == CellType.BLACK) {
        Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
    } else if (cell.type == CellType.CLUE) {
        Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(canvasWidth, canvasHeight),
                    color = Color.LightGray,
                    strokeWidth = 2f
                )
            }
            if (cell.clue?.horizontalSum != null) {
                Text(
                    text = cell.clue.horizontalSum.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.TopEnd).padding(end = 4.dp, top = 2.dp)
                )
            }
            if (cell.clue?.verticalSum != null) {
                Text(
                    text = cell.clue.verticalSum.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 4.dp, bottom = 2.dp)
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSelected) Color.Yellow.copy(alpha = 0.3f) else Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (cell.playerValue != null) {
                Text(
                    text = cell.playerValue.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
