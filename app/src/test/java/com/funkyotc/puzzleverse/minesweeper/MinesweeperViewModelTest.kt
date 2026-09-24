package com.funkyotc.puzzleverse.minesweeper

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.core.UtcDaySource
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

        assertTrue("Revealing every safe cell must win", viewModel.state.value.isWon)
        val streak = streakRepository.getStreak("minesweeper")
        assertEquals(4, streak.count)
        assertEquals(today, streak.lastCompletedEpochDay)
    }

    @Test
    fun dailyMinesDependOnlyOnDayAndFirstTap() {
        val clock = UtcDaySource { 20_000L }
        val first = MinesweeperViewModel(mode = "daily", daySource = clock)
        val second = MinesweeperViewModel(mode = "daily", daySource = clock)
        first.revealCell(4, 4)
        second.revealCell(4, 4)
        fun mines(vm: MinesweeperViewModel) = vm.state.value.grid.flatMap { row -> row.filter { it.isMine }.map { it.row to it.col } }.toSet()
        assertEquals(mines(first), mines(second))
        assertTrue(mines(first).none { (r, c) -> r in 3..5 && c in 3..5 })
    }

    @Test
    fun everyDailyOpeningKeepsItsNeighborhoodSafe() {
        val clock = UtcDaySource { 20_000L }
        val sample = MinesweeperViewModel(mode = "daily", daySource = clock).state.value
        for (row in 0 until sample.rows) for (col in 0 until sample.cols) {
            val vm = MinesweeperViewModel(mode = "daily", daySource = clock)
            vm.revealCell(row, col)
            val grid = vm.state.value.grid
            assertTrue("Unsafe opening at $row,$col", grid.indices.all { r ->
                grid[r].indices.all { c ->
                    if (kotlin.math.abs(r - row) <= 1 && kotlin.math.abs(c - col) <= 1) !grid[r][c].isMine else true
                }
            })
        }
    }
}
