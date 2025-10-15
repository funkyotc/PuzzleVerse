package com.funkyotc.puzzleverse.wordle.viewmodel

import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.wordle.model.LetterState
import com.funkyotc.puzzleverse.wordle.model.WordleGuess
import com.funkyotc.puzzleverse.wordle.model.WordleLetter
import com.funkyotc.puzzleverse.wordle.model.WordleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WordleViewModel : ViewModel() {

    private val _wordleState = MutableStateFlow<WordleState?>(null)
    val wordleState: StateFlow<WordleState?> = _wordleState

    private val validWords = listOf("HELLO", "WORLD", "COMPOSE", "ANDROID")
    private val solution = "COMPOSE"

    init {
        startNewGame()
    }

    fun startNewGame() {
        val emptyGuesses = List(6) { 
            WordleGuess(List(5) { WordleLetter(' ', LetterState.EMPTY) })
        }
        _wordleState.value = WordleState(emptyGuesses, solution, 0)
    }

    fun onLetterTyped(char: Char) {
        // Handle letter input
    }

    fun onBackspace() {
        // Handle backspace
    }

    fun onSubmitGuess() {
        val currentState = _wordleState.value ?: return
        if (currentState.currentGuessIndex >= 6) return

        val currentGuess = currentState.guesses[currentState.currentGuessIndex]
        val guessWord = currentGuess.letters.joinToString("") { it.char.toString() }

        if (guessWord.length == 5 && guessWord in validWords) {
            val evaluatedLetters = evaluateGuess(guessWord)
            val updatedGuesses = currentState.guesses.toMutableList()
            updatedGuesses[currentState.currentGuessIndex] = WordleGuess(evaluatedLetters)

            _wordleState.value = currentState.copy(
                guesses = updatedGuesses,
                currentGuessIndex = currentState.currentGuessIndex + 1
            )
        }
    }

    private fun evaluateGuess(guess: String): List<WordleLetter> {
        val result = mutableListOf<WordleLetter>()
        for (i in guess.indices) {
            val state = when {
                guess[i] == solution[i] -> LetterState.CORRECT
                solution.contains(guess[i]) -> LetterState.PRESENT
                else -> LetterState.ABSENT
            }
            result.add(WordleLetter(guess[i], state))
        }
        return result
    }
}