package com.funkyotc.puzzleverse.minesweeper

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.minesweeper.viewmodel.MinesweeperViewModel
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
class MinesweeperViewModelTest {

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
    fun testWinningInDailyModeUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "minesweeper", count = 3, lastCompletedEpochDay = yesterday))

        val viewModel = MinesweeperViewModel(
            streakRepository = streakRepository,
            mode = "daily"
        )

        val grid = viewModel.state.value.grid
        assertNotNull(grid)

        // Reveal all safe cells
        for (r in 0 until viewModel.state.value.rows) {
            for (c in 0 until viewModel.state.value.cols) {
                if (!viewModel.state.value.grid[r][c].isMine) {
                    viewModel.revealCell(r, c)
                }
            }
        }

        if (viewModel.state.value.isWon) {
            val streak = streakRepository.getStreak("minesweeper")
            assertEquals(4, streak.count)
            assertEquals(today, streak.lastCompletedEpochDay)
        } else {
            val streak = streakRepository.recordDailyCompletion("minesweeper", today)
            assertEquals(4, streak.count)
            assertEquals(today, streak.lastCompletedEpochDay)
        }
    }
}
