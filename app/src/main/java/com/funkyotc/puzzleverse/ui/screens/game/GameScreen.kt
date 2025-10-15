package com.funkyotc.puzzleverse.ui.screens.game

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GameScreen(gameId: String?, mode: String?) {
    Text(text = "Game Screen: $gameId ($mode)")
}