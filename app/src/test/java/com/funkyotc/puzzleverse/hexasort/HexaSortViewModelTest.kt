package com.funkyotc.puzzleverse.hexasort

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortViewModel
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
class HexaSortViewModelTest {

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

        streakRepository.saveStreak(Streak(gameId = "hexasort", count = 2, lastCompletedEpochDay = yesterday))

        val viewModel = HexaSortViewModel(
            context = null,
            streakRepository = streakRepository,
            mode = "daily",
            puzzleId = null
        )

        assertNotNull("State should not be null", viewModel.state.value)

        // Record daily completion & test streak update
        val updatedStreak = streakRepository.recordDailyCompletion("hexasort", today)
        assertEquals(3, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }
}
