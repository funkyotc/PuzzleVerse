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

import com.funkyotc.puzzleverse.streak.data.StreakRepository
import java.time.LocalDate

class WordleViewModel(
    private val mode: String?,
    private val streakRepository: StreakRepository
) : ViewModel() {

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
        val seed = if (mode == "daily") LocalDate.now().toEpochDay() else System.currentTimeMillis()
        val solution = validWords.random(Random(seed))
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

        val lastFilledIndex = currentGuess.letters.indexOfLast { it.char != ' ' }
        if (lastFilledIndex != -1) {
            val updatedLetters = currentGuess.letters.toMutableList()
            updatedLetters[lastFilledIndex] = WordleLetter(' ', LetterState.EMPTY)
            
            val updatedGuesses = currentState.guesses.toMutableList()
            updatedGuesses[currentGuessIndex] = WordleGuess(updatedLetters)

            _wordleState.value = currentState.copy(guesses = updatedGuesses, missingFeedback = null)
        }
    }

    fun hint() {
        val currentState = _wordleState.value ?: return
        if (currentState.gameStatus != GameStatus.PLAYING) return

        val solutionChars = currentState.solution.toSet()
        val guessedChars = currentState.keyboardState.keys
        val unguessedChars = solutionChars - guessedChars

        if (unguessedChars.isNotEmpty()) {
            val hintChar = unguessedChars.random()
            val newKeyboardState = currentState.keyboardState.toMutableMap()
            // Mark the hint char as PRESENT so it lights up on the keyboard as Amber
            newKeyboardState[hintChar] = LetterState.PRESENT
            _wordleState.value = currentState.copy(keyboardState = newKeyboardState)
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

        val evaluatedLetters = evaluateGuess(guessWord, currentState.solution)
        val updatedGuesses = currentState.guesses.toMutableList()
        updatedGuesses[currentState.currentGuessIndex] = WordleGuess(evaluatedLetters)

        val newKeyboardState = currentState.keyboardState.toMutableMap()
        evaluatedLetters.forEach { letter ->
            val existingState = newKeyboardState[letter.char]
            if (existingState != LetterState.CORRECT) {
                if (existingState == LetterState.PRESENT && letter.state == LetterState.ABSENT) {
                    // Do nothing
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

        if (newGameStatus == GameStatus.WON && mode == "daily") {
            val today = LocalDate.now().toEpochDay()
            val streak = streakRepository.getStreak("wordle")
            if (streak.lastCompletedEpochDay != today) {
                val newStreak = streak.copy(
                    count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                    lastCompletedEpochDay = today
                )
                streakRepository.saveStreak(newStreak)
            }
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

        for (i in guess.indices) {
            if (guess[i] == solution[i]) {
                result[i] = WordleLetter(guess[i], LetterState.CORRECT)
                solutionCharsLeft[i] = ' '
            }
        }

        for (i in guess.indices) {
            if (result[i].state != LetterState.CORRECT) {
                val indexInSolution = solutionCharsLeft.indexOf(guess[i])
                if (indexInSolution != -1) {
                    result[i] = WordleLetter(guess[i], LetterState.PRESENT)
                    solutionCharsLeft[indexInSolution] = ' '
                } else {
                    result[i] = WordleLetter(guess[i], LetterState.ABSENT)
                }
            }
        }
        return result
    }
}