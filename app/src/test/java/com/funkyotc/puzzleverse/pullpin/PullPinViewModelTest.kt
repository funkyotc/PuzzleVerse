package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
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
class PullPinViewModelTest {

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
    fun testDailyCompletionIncrementsStreakAndDuplicateDoesNotReset() {
        val today = todayEpochDay()
        val yesterday = today - 1

        // 1. Initial streak set to 2 yesterday
        streakRepository.saveStreak(Streak(gameId = "pullpin", count = 2, lastCompletedEpochDay = yesterday))

        val viewModel = PullPinViewModel(
            streakRepository = streakRepository,
            mode = "daily",
            puzzleId = null
        )

        val state = viewModel.state.value
        assertNotNull("Initial state should not be null", state)

        // Remove all pins
        val pinIds = state!!.pins.map { it.id }
        for (id in pinIds) {
            viewModel.removePin(id)
        }

        // Record daily completion on win
        val firstCompletion = streakRepository.recordDailyCompletion("pullpin", today)
        assertEquals(3, firstCompletion.count)
        assertEquals(today, firstCompletion.lastCompletedEpochDay)

        // 2. Simulate duplicate completion on same day
        // Verify streak count remains 3 (does NOT reset to 1)
        val duplicateCompletion = streakRepository.recordDailyCompletion("pullpin", today)
        assertEquals(3, duplicateCompletion.count)
        assertEquals(today, duplicateCompletion.lastCompletedEpochDay)
    }
}
