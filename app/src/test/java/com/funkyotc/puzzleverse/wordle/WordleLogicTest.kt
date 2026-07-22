package com.funkyotc.puzzleverse.wordle

import com.funkyotc.puzzleverse.wordle.model.GameStatus
import com.funkyotc.puzzleverse.wordle.model.LetterState
import com.funkyotc.puzzleverse.wordle.model.WordleLetter
import com.funkyotc.puzzleverse.wordle.model.WordleGuess
import com.funkyotc.puzzleverse.wordle.model.WordleState
import org.junit.Assert.*
import org.junit.Test

class WordleLogicTest {

    @Test
    fun testWordleStateInitial() {
        val state = WordleState(
            guesses = emptyList(),
            solution = "APPLE",
            currentGuessIndex = 0
        )
        assertEquals(GameStatus.PLAYING, state.gameStatus)
        assertEquals("APPLE", state.solution)
        assertEquals(0, state.currentGuessIndex)
    }

    @Test
    fun testWordleGuessEvaluation() {
        val solution = "SMART"
        val guessChars = "START"
        
        val letters = guessChars.mapIndexed { index, ch ->
            val state = when {
                ch == solution[index] -> LetterState.CORRECT
                solution.contains(ch) -> LetterState.PRESENT
                else -> LetterState.ABSENT
            }
            WordleLetter(ch, state)
        }

        val guess = WordleGuess(letters)
        assertEquals(LetterState.CORRECT, guess.letters[0].state) // 'S'
        assertEquals(LetterState.PRESENT, guess.letters[1].state) // 'T'
        assertEquals(LetterState.CORRECT, guess.letters[2].state) // 'A'
        assertEquals(LetterState.CORRECT, guess.letters[3].state) // 'R'
        assertEquals(LetterState.CORRECT, guess.letters[4].state) // 'T'
    }
}
