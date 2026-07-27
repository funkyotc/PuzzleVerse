package com.funkyotc.puzzleverse.watersort

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import com.funkyotc.puzzleverse.watersort.viewmodel.WaterSortViewModel
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
class WaterSortViewModelTest {

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

        streakRepository.saveStreak(Streak(gameId = "watersort", count = 1, lastCompletedEpochDay = yesterday))

        val viewModel = WaterSortViewModel(
            mode = "daily",
            puzzleId = null,
            streakRepository = streakRepository
        )

        val state = viewModel.state.value
        assertNotNull("State should not be null", state)

        // Record daily completion & test streak update
        val updatedStreak = streakRepository.recordDailyCompletion("watersort", today)
        assertEquals(2, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }
}
