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

    @Test
    fun testPregeneratedPuzzlesValidity() {
        val puzzles = com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated.ALL_PUZZLES
        assertTrue("Pregenerated puzzles should not be empty", puzzles.isNotEmpty())

        for (puzzle in puzzles) {
            assertTrue("Puzzle grid size should be >= 5", puzzle.size >= 5)
            assertTrue("Puzzle should have dots", puzzle.dots.isNotEmpty())

            for (dot in puzzle.dots) {
                assertNotEquals("Start and end points must be distinct", dot.start, dot.end)
                assertTrue("Start row in bounds", dot.start.r in 0 until puzzle.size)
                assertTrue("Start col in bounds", dot.start.c in 0 until puzzle.size)
                assertTrue("End row in bounds", dot.end.r in 0 until puzzle.size)
                assertTrue("End col in bounds", dot.end.c in 0 until puzzle.size)
            }
        }
    }

    @Test
    fun testWinConditionRequiresFullCoverage() {
        val puzzle = com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated.ALL_PUZZLES.first()
        val viewModel = FlowFreeViewModel(
            streakRepository = streakRepository,
            mode = "standard",
            puzzleId = puzzle.id
        )

        assertFalse("Initially new game is not won", viewModel.state.value.isWon)

        // Draw incomplete paths
        for (dot in puzzle.dots) {
            viewModel.startPath(dot.colorId, dot.start.r, dot.start.c)
        }
        assertFalse("Incomplete paths should not win", viewModel.state.value.isWon)
    }
}
