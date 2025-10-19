package com.funkyotc.puzzleverse.bonza.data

import androidx.compose.ui.geometry.Offset

// Represents a single Bonza puzzle, including its theme, words, and word fragments.
data class BonzaPuzzle(
    val theme: String,
    val words: List<String>,
    val fragments: List<WordFragment>,
    val connections: List<BonzaConnection>,
    val solvedFragments: List<WordFragment>
)

// Represents a single, draggable fragment of a word.
data class WordFragment(
    val id: Int,
    val text: String,
    val initialPosition: Offset,
    var currentPosition: Offset = initialPosition
)

// Defines a connection between two word fragments.
data class BonzaConnection(
    val fragment1Id: Int,
    val fragment2Id: Int,
    val direction: ConnectionDirection
)

enum class ConnectionDirection {
    HORIZONTAL,
    VERTICAL
}
