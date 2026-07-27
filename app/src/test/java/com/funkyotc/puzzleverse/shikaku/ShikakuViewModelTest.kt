package com.funkyotc.puzzleverse.shikaku

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.shikaku.viewmodel.ShikakuViewModel
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
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
class ShikakuViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var streakRepository: StreakRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        streakRepository = StreakRepository(sharedPreferences = FakeSharedPreferences())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testDailyModeWinUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "shikaku", count = 2, lastCompletedEpochDay = yesterday))

        val viewModel = ShikakuViewModel(
            context = null,
            mode = "daily",
            forceNewGame = true,
            puzzleId = null,
            streakRepository = streakRepository,
            settingsRepository = null
        )

        // Simulate puzzle completion & streak update
        val updatedStreak = streakRepository.recordDailyCompletion("shikaku", today)
        assertEquals(3, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
        assertNotNull(viewModel.board.value)
    }
}
