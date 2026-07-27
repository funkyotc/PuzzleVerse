package com.funkyotc.puzzleverse.wordle

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import com.funkyotc.puzzleverse.wordle.data.WordleStatsRepository
import com.funkyotc.puzzleverse.wordle.model.GameStatus
import com.funkyotc.puzzleverse.wordle.viewmodel.WordleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WordleViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var streakRepository: StreakRepository
    private lateinit var statsRepository: WordleStatsRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val fakePrefs = FakeSharedPreferences()
        streakRepository = StreakRepository(sharedPreferences = fakePrefs)
        statsRepository = WordleStatsRepository(prefs = fakePrefs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testDailyModeWinUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "wordle", count = 3, lastCompletedEpochDay = yesterday))

        val dictionary = listOf("APPLE", "BANANA", "CHERRY")

        val viewModel = WordleViewModel(
            mode = "daily",
            streakRepository = streakRepository,
            statsRepository = statsRepository,
            dictionary = dictionary
        )

        val solution = viewModel.wordleState.value?.solution?.uppercase() ?: "APPLE"
        for (char in solution) {
            viewModel.onLetterTyped(char)
        }
        viewModel.onSubmitGuess()

        val streak = streakRepository.recordDailyCompletion("wordle", today)
        assertEquals(4, streak.count)
        assertEquals(today, streak.lastCompletedEpochDay)
    }
}
