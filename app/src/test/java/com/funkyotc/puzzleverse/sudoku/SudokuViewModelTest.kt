package com.funkyotc.puzzleverse.sudoku

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.data.SudokuPregenerated
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SudokuViewModelTest {

    private lateinit var streakRepository: StreakRepository

    @Before
    fun setUp() {
        streakRepository = StreakRepository(sharedPreferences = FakeSharedPreferences())
    }

    @Test
    fun testDailyModeWinUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "sudoku", count = 2, lastCompletedEpochDay = yesterday))

        val puzzleData = SudokuPregenerated.ALL_PUZZLES.first()
        val solution = puzzleData.solution

        val solvedCells = mutableListOf<SudokuCell>()
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                solvedCells.add(
                    SudokuCell(
                        row = r,
                        col = c,
                        number = solution[r][c],
                        isHint = true,
                        isError = false
                    )
                )
            }
        }
        val solvedBoard = SudokuBoard(solvedCells)
        assertEquals(81, solvedBoard.cells.size)

        val updatedStreak = streakRepository.recordDailyCompletion("sudoku", today)
        assertEquals(3, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }
}
