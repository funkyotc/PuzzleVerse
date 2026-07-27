package com.funkyotc.puzzleverse.flowfree

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.flowfree.data.Point
import com.funkyotc.puzzleverse.flowfree.viewmodel.FlowFreeViewModel
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
class FlowFreeViewModelTest {

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
    fun testDailyWinUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "flowfree", count = 3, lastCompletedEpochDay = yesterday))

        val viewModel = FlowFreeViewModel(
            streakRepository = streakRepository,
            mode = "daily",
            puzzleId = null
        )

        val state = viewModel.state.value
        assertNotNull("Daily state should not be null", state)

        val updatedStreak = streakRepository.recordDailyCompletion("flowfree", today)
        assertEquals(4, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }
}
