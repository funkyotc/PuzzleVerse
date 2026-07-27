package com.funkyotc.puzzleverse.streak.data

import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StreakRepositoryTest {

    private lateinit var streakRepository: StreakRepository

    @Before
    fun setUp() {
        val fakePrefs = FakeSharedPreferences()
        streakRepository = StreakRepository(sharedPreferences = fakePrefs)
    }

    @Test
    fun testSaveAndGetStreak() {
        val initial = streakRepository.getStreak("test_game")
        assertEquals("test_game", initial.gameId)
        assertEquals(0, initial.count)
        assertNull(initial.lastCompletedEpochDay)

        val updated = Streak(gameId = "test_game", count = 3, lastCompletedEpochDay = 20000L)
        streakRepository.saveStreak(updated)

        val retrieved = streakRepository.getStreak("test_game")
        assertEquals(3, retrieved.count)
        assertEquals(20000L, retrieved.lastCompletedEpochDay)
    }

    @Test
    fun testConsecutiveDayCompletionIncrementsStreakCount() {
        val today = 20000L
        val yesterday = today - 1

        val initialStreak = Streak(gameId = "sudoku", count = 4, lastCompletedEpochDay = yesterday)
        streakRepository.saveStreak(initialStreak)

        val result = streakRepository.recordDailyCompletion("sudoku", today)
        assertEquals(5, result.count)
        assertEquals(today, result.lastCompletedEpochDay)

        val retrieved = streakRepository.getStreak("sudoku")
        assertEquals(5, retrieved.count)
        assertEquals(today, retrieved.lastCompletedEpochDay)
    }

    @Test
    fun testDuplicateSameDayCompletionGuard() {
        val today = 20000L
        val initialStreak = Streak(gameId = "minesweeper", count = 5, lastCompletedEpochDay = today)
        streakRepository.saveStreak(initialStreak)

        val result = streakRepository.recordDailyCompletion("minesweeper", today)
        assertEquals(5, result.count)
        assertEquals(today, result.lastCompletedEpochDay)
    }

    @Test
    fun testMissedDayResetsStreakCount() {
        val today = 20000L
        val twoDaysAgo = today - 2

        val initialStreak = Streak(gameId = "blockpuzzle", count = 10, lastCompletedEpochDay = twoDaysAgo)
        streakRepository.saveStreak(initialStreak)

        val result = streakRepository.recordDailyCompletion("blockpuzzle", today)
        assertEquals(1, result.count)
        assertEquals(today, result.lastCompletedEpochDay)
    }

    @Test
    fun testIsCompletedToday() {
        val today = 20000L
        val yesterday = today - 1

        assertFalse(streakRepository.isCompletedToday("pullpin", today))

        val streakYesterday = Streak(gameId = "pullpin", count = 2, lastCompletedEpochDay = yesterday)
        streakRepository.saveStreak(streakYesterday)
        assertFalse(streakRepository.isCompletedToday("pullpin", today))

        val streakToday = Streak(gameId = "pullpin", count = 3, lastCompletedEpochDay = today)
        streakRepository.saveStreak(streakToday)
        assertTrue(streakRepository.isCompletedToday("pullpin", today))
    }
}
