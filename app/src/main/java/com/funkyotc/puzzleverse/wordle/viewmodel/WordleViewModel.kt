package com.funkyotc.puzzleverse.wordle.viewmodel

import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.wordle.model.LetterState
import com.funkyotc.puzzleverse.wordle.model.GameStatus
import com.funkyotc.puzzleverse.wordle.model.WordleGuess
import com.funkyotc.puzzleverse.wordle.model.WordleLetter
import com.funkyotc.puzzleverse.wordle.model.WordleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class WordleViewModel : ViewModel() {

    private val _wordleState = MutableStateFlow<WordleState?>(null)
    val wordleState: StateFlow<WordleState?> = _wordleState

    private val validWords = listOf(
        "APPLE", "CRATE", "ROAST", "REACT", "BUILD", "DEBUG", "STORM", "PLANT", "CLOCK", "TRAIN", 
        "BRICK", "GHOST", "SOUND", "SMART", "FRAME", "WATER", "LIGHT", "HOUSE", "DREAM", "SLEEP"
    )

    init {
        startNewGame()
    }

    fun startNewGame() {
        val emptyGuesses = List(6) { 
            WordleGuess(List(5) { WordleLetter(' ', LetterState.EMPTY) })
        }
        val solution = validWords.random(Random(System.currentTimeMillis()))
        _wordleState.value = WordleState(
            guesses = emptyGuesses, 
            solution = solution, 
            currentGuessIndex = 0,
            gameStatus = GameStatus.PLAYING,
            keyboardState = emptyMap(),
            missingFeedback = null
        )
    }

    fun onLetterTyped(char: Char) {
        val currentState = _wordleState.value ?: return
        if (currentState.gameStatus != GameStatus.PLAYING) return

        val currentGuessIndex = currentState.currentGuessIndex
        val currentGuess = currentState.guesses[currentGuessIndex]

        val firstEmptyIndex = currentGuess.letters.indexOfFirst { it.char == ' ' }
        if (firstEmptyIndex != -1) {
            val updatedLetters = currentGuess.letters.toMutableList()
            updatedLetters[firstEmptyIndex] = WordleLetter(char.uppercaseChar(), LetterState.EMPTY)
            
            val updatedGuesses = currentState.guesses.toMutableList()
            updatedGuesses[currentGuessIndex] = WordleGuess(updatedLetters)

            _wordleState.value = currentState.copy(guesses = updatedGuesses, missingFeedback = null)
        }
    }

    fun onBackspace() {
        val currentState = _wordleState.value ?: return
        if (currentState.gameStatus != GameStatus.PLAYING) return

        val currentGuessIndex = currentState.currentGuessIndex
        val currentGuess = currentState.guesses[currentGuessIndex]

        // Find the last filled index
        val lastFilledIndex = currentGuess.letters.indexOfLast { it.char != ' ' }
        if (lastFilledIndex != -1) {
            val updatedLetters = currentGuess.letters.toMutableList()
            updatedLetters[lastFilledIndex] = WordleLetter(' ', LetterState.EMPTY)
            
            val updatedGuesses = currentState.guesses.toMutableList()
            updatedGuesses[currentGuessIndex] = WordleGuess(updatedLetters)

            _wordleState.value = currentState.copy(guesses = updatedGuesses, missingFeedback = null)
        }
    }

    fun onSubmitGuess() {
        val currentState = _wordleState.value ?: return
        if (currentState.gameStatus != GameStatus.PLAYING) return
        if (currentState.currentGuessIndex >= 6) return

        val currentGuess = currentState.guesses[currentState.currentGuessIndex]
        val guessWord = currentGuess.letters.joinToString("") { it.char.toString() }

        if (guessWord.contains(" ")) {
            _wordleState.value = currentState.copy(missingFeedback = "Not enough letters")
            return
        }

        if (guessWord !in validWords) {
            // For a small dictionary prototype, letting them guess any 5 chars could be ok, 
            // but strict check feels more realistic. Optionally bypass.
            // Let's bypass strict dictionary for arbitrary guesses since dict is only 20 words for now to avoid frustration.
        }

        val evaluatedLetters = evaluateGuess(guessWord, currentState.solution)
        val updatedGuesses = currentState.guesses.toMutableList()
        updatedGuesses[currentState.currentGuessIndex] = WordleGuess(evaluatedLetters)

        // Update keyboard state globally
        val newKeyboardState = currentState.keyboardState.toMutableMap()
        evaluatedLetters.forEach { letter ->
            val existingState = newKeyboardState[letter.char]
            if (existingState != LetterState.CORRECT) { // Never downgrade a correct letter
                if (existingState == LetterState.PRESENT && letter.state == LetterState.ABSENT) {
                    // Don't downgrade present to absent if they guessed it again in wrong spot
                } else {
                    newKeyboardState[letter.char] = letter.state
                }
            }
        }

        val newGameStatus = when {
            guessWord == currentState.solution -> GameStatus.WON
            currentState.currentGuessIndex == 5 -> GameStatus.LOST
            else -> GameStatus.PLAYING
        }

        _wordleState.value = currentState.copy(
            guesses = updatedGuesses,
            currentGuessIndex = currentState.currentGuessIndex + 1,
            keyboardState = newKeyboardState,
            gameStatus = newGameStatus,
            missingFeedback = null
        )
    }

    private fun evaluateGuess(guess: String, solution: String): List<WordleLetter> {
        val result = MutableList(5) { WordleLetter(' ', LetterState.EMPTY) }
        val solutionCharsLeft = solution.toMutableList()

        // Pass 1: Find CORRECT
        for (i in guess.indices) {
            if (guess[i] == solution[i]) {
                result[i] = WordleLetter(guess[i], LetterState.CORRECT)
                solutionCharsLeft[i] = ' ' // nullify
            }
        }

        // Pass 2: Find PRESENT or ABSENT
        for (i in guess.indices) {
            if (result[i].state != LetterState.CORRECT) {
                val indexInSolution = solutionCharsLeft.indexOf(guess[i])
                if (indexInSolution != -1) {
                    result[i] = WordleLetter(guess[i], LetterState.PRESENT)
                    solutionCharsLeft[indexInSolution] = ' ' // use it up
                } else {
                    result[i] = WordleLetter(guess[i], LetterState.ABSENT)
                }
            }
        }
        return result
    }
}