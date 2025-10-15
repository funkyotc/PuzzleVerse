package com.puzzleverse.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(onGameSelected: (String) -> Unit) {
    val games = listOf("Sudoku", "Bonza", "Constellations", "Shapes", "Wordle")
    LazyColumn {
        items(games) { game ->
            Text(text = game)
        }
    }
}