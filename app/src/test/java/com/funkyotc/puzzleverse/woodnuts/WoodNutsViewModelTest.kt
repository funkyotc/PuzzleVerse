package com.funkyotc.puzzleverse.woodnuts

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModel
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
class WoodNutsViewModelTest {

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

        streakRepository.saveStreak(Streak(gameId = "woodnuts", count = 3, lastCompletedEpochDay = yesterday))

        val viewModel = WoodNutsViewModel(
            mode = "daily",
            puzzleId = null,
            streakRepository = streakRepository
        )

        assertNotNull("State should not be null", viewModel.state.value)

        // Record daily completion & test streak update
        val updatedStreak = streakRepository.recordDailyCompletion("woodnuts", today)
        assertEquals(4, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }
}
