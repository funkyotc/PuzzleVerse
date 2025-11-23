package com.funkyotc.puzzleverse.bonza.data

import androidx.compose.ui.geometry.Offset

/**
 * Represents a single word in the Bonza puzzle.
 */
data class BonzaWord(
    val word: String,
    val fragments: List<WordFragment>,
    val position: Offset,
    val direction: ConnectionDirection
)
