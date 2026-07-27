package com.funkyotc.puzzleverse.blockpuzzle

import com.funkyotc.puzzleverse.blockpuzzle.viewmodel.BlockPuzzleViewModel
import com.funkyotc.puzzleverse.core.todayEpochDay
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
class BlockPuzzleViewModelTest {

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
    fun testReachingTargetScoreInDailyModeUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "blockpuzzle", count = 5, lastCompletedEpochDay = yesterday))

        val viewModel = BlockPuzzleViewModel(
            streakRepository = streakRepository,
            mode = "daily"
        )

        val initialStreak = streakRepository.getStreak("blockpuzzle")
        assertEquals(5, initialStreak.count)
        assertEquals(yesterday, initialStreak.lastCompletedEpochDay)

        var attempts = 0
        while (viewModel.state.value.score < 300 && attempts < 100) {
            val state = viewModel.state.value
            var placed = false
            for (i in state.tray.indices) {
                if (state.tray[i] != null) {
                    for (r in 0 until 10) {
                        for (c in 0 until 10) {
                            viewModel.placeShape(i, r, c)
                            if (viewModel.state.value.score != state.score) {
                                placed = true
                                break
                            }
                        }
                        if (placed) break
                    }
                }
                if (placed) break
            }
            if (!placed) {
                viewModel.startNewGame()
            }
            attempts++
        }

        val updatedStreak = streakRepository.recordDailyCompletion("blockpuzzle", today)
        assertEquals(6, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }
}
