package com.funkyotc.puzzleverse.bonza.data

/**
 * Represents a complete Bonza puzzle, including the theme, words, and fragments.
 */
data class BonzaPuzzle(
    val theme: String,
    val words: List<String>,
    val fragments: List<WordFragment>,
    val connections: List<BonzaConnection>,
    val solvedFragments: List<WordFragment>
)
