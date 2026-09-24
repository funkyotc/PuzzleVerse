package com.funkyotc.puzzleverse.hashi

import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.hashi.data.HashiPregenerated
import com.funkyotc.puzzleverse.hashi.data.Island
import com.funkyotc.puzzleverse.hashi.viewmodel.HashiViewModel
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
class HashiViewModelTest {

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
    fun testDateSeededPuzzleLoading() {
        val today = todayEpochDay()
        val expectedPuzzleIndex = (today % HashiPregenerated.MEDIUM_PUZZLES.size).toInt()
        val expectedPuzzle = HashiPregenerated.MEDIUM_PUZZLES[expectedPuzzleIndex]

        val viewModel = HashiViewModel(
            streakRepository = streakRepository,
            mode = "daily",
            puzzleId = null
        )

        val loadedPuzzle = viewModel.puzzle.value
        assertNotNull("Daily puzzle should be loaded", loadedPuzzle)
        assertEquals(expectedPuzzle.id, loadedPuzzle!!.id)
    }
}
