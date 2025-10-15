package com.funkyotc.puzzleverse.wordle.model

// Represents the state of a single letter in a guess
enum class LetterState {
    CORRECT, // Right letter, right position
    PRESENT, // Right letter, wrong position
    ABSENT,  // Wrong letter
    EMPTY    // Not yet guessed
}

// Represents a single cell on the Wordle grid
data class WordleLetter(val char: Char, val state: LetterState)

// Represents a single guess (a row on the grid)
data class WordleGuess(val letters: List<WordleLetter>)

// Represents the entire state of the Wordle game
data class WordleState(
    val guesses: List<WordleGuess>,
    val solution: String,
    val currentGuessIndex: Int
)