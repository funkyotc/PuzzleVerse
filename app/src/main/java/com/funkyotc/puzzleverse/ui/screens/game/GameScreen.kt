package com.funkyotc.puzzleverse.ui.screens.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, gameId: String?, mode: String?) {
    val gameName = gameId?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } ?: "Game"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(gameName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (gameId) {
                "sudoku" -> {
                    // This will be handled by SudokuScreen
                    Text(text = "Sudoku Game")
                }
                "nonogram" -> {
                    // This will be handled by NonogramScreen
                    Text(text = "Nonogram Game")
                }
                "minesweeper" -> {
                    // This will be handled by MinesweeperScreen
                    Text(text = "Minesweeper Game")
                }
                else -> {
                    Text(text = "Game Screen: $gameId ($mode)")
                }
            }
        }
    }
}