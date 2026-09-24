package com.funkyotc.puzzleverse.bonza

import com.funkyotc.puzzleverse.bonza.data.BonzaPregenerated
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BonzaCompletionTest {
    private val dispatcher = UnconfinedTestDispatcher()
    @Before fun setup() = Dispatchers.setMain(dispatcher)
    @After fun teardown() = Dispatchers.resetMain()

    @Test fun everyCatalogPuzzleCanReachTheViewModelWinRule() {
        val puzzles = BonzaPregenerated.ALL_PUZZLES
        assertEquals(90, puzzles.size)
        val failures = mutableListOf<String>()
        for (puzzle in puzzles) {
            val vm = BonzaViewModel(mode = "puzzle", puzzleId = puzzle.id,
                streakRepository = StreakRepository())
            val fragmentCount = vm.puzzle.value.fragments.size
            repeat(fragmentCount) {
                if (!vm.isGameWon.value) vm.hint()
            }
            if (!vm.isGameWon.value) failures += "${puzzle.id}: no win after $fragmentCount legal hints"
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }
}
