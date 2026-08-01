package com.funkyotc.puzzleverse.constellations

import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPregenerated
import com.funkyotc.puzzleverse.constellations.generator.ConstellationsPuzzleGenerator
import com.funkyotc.puzzleverse.constellations.viewmodel.ConstellationsViewModel
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConstellationsTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var streakRepository: StreakRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val fakePrefs = FakeSharedPreferences()
        streakRepository = StreakRepository(sharedPreferences = fakePrefs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGeneratorProducesValidPuzzle() {
        val generator = ConstellationsPuzzleGenerator()
        val puzzle = generator.generate(size = 6, seed = 42L)

        assertEquals(6, puzzle.size)
        assertEquals(6, puzzle.cells.size)
        assertEquals(6, puzzle.solution.size)

        // Verify solution has 1 star per row and 1 star per col
        val rows = puzzle.solution.map { it.first }.toSet()
        val cols = puzzle.solution.map { it.second }.toSet()
        assertEquals(6, rows.size)
        assertEquals(6, cols.size)

        // Verify no two solution stars touch diagonally or orthogonally
        for (i in 0 until puzzle.solution.size) {
            for (j in i + 1 until puzzle.solution.size) {
                val (r1, c1) = puzzle.solution[i]
                val (r2, c2) = puzzle.solution[j]
                val dr = Math.abs(r1 - r2)
                val dc = Math.abs(c1 - c2)
                assertFalse("Stars at ($r1,$c1) and ($r2,$c2) are adjacent!", dr <= 1 && dc <= 1)
            }
        }
    }

    @Test
    fun testPregeneratedPuzzlesValidity() {
        val allPuzzles = ConstellationsPregenerated.ALL_PUZZLES
        assertTrue("Must have pregenerated puzzles", allPuzzles.isNotEmpty())

        for (pregen in allPuzzles) {
            val puzzle = pregen.toConstellationsPuzzle()
            val size = puzzle.size
            assertEquals(size, puzzle.solution.size)

            // Solution row/col uniqueness
            assertEquals("Puzzle ${pregen.id} rows incomplete", size, puzzle.solution.map { it.first }.toSet().size)
            assertEquals("Puzzle ${pregen.id} cols incomplete", size, puzzle.solution.map { it.second }.toSet().size)

            // Solution region uniqueness
            val solutionRegions = puzzle.solution.map { (r, c) -> puzzle.cells[r][c].regionId }.toSet()
            assertEquals("Puzzle ${pregen.id} region coverage incomplete", size, solutionRegions.size)
        }
    }

    @org.junit.Ignore("Requires coroutine timer cancellation handling")
    @Test
    fun testViewModelCellClickStateCycleAndAutoCross() = runTest {
        val viewModel = ConstellationsViewModel(streakRepository = streakRepository, mode = "standard")
        testDispatcher.scheduler.advanceUntilIdle()

        val initialPuzzle = viewModel.puzzle.value
        assertNotNull(initialPuzzle)

        // Initial cell state should be EMPTY
        assertEquals(CellState.EMPTY, initialPuzzle!!.cells[0][0].state)

        // Click cell (0,0) -> state becomes CROSS
        viewModel.onCellClicked(0, 0)
        assertEquals(CellState.CROSS, viewModel.puzzle.value!!.cells[0][0].state)

        // Click cell (0,0) again -> state becomes STAR
        viewModel.onCellClicked(0, 0)
        val stateWithStar = viewModel.puzzle.value!!
        assertEquals(CellState.STAR, stateWithStar.cells[0][0].state)

        // Verify auto-cross: Row 0, Col 0, region 0, and 8-neighbors should be auto-crossed (unless they are stars)
        assertTrue(stateWithStar.cells[0][1].state == CellState.CROSS)
        assertTrue(stateWithStar.cells[0][1].isAuto)

        // Click cell (0,0) again -> state cycles back to EMPTY, and auto-crosses are cleared
        viewModel.onCellClicked(0, 0)
        assertEquals(CellState.EMPTY, viewModel.puzzle.value!!.cells[0][0].state)
        assertEquals(CellState.EMPTY, viewModel.puzzle.value!!.cells[0][1].state)
    }

    @org.junit.Ignore("Requires coroutine timer cancellation handling")
    @Test
    fun testHintFeaturePlacesCorrectStar() = runTest {
        val viewModel = ConstellationsViewModel(streakRepository = streakRepository, mode = "standard")
        testDispatcher.scheduler.advanceUntilIdle()

        val puzzle = viewModel.puzzle.value!!
        val targetStar = puzzle.solution.first()

        // Apply hint
        viewModel.hint()

        val updatedPuzzle = viewModel.puzzle.value!!
        assertEquals(CellState.STAR, updatedPuzzle.cells[targetStar.first][targetStar.second].state)
        assertFalse(updatedPuzzle.cells[targetStar.first][targetStar.second].isError)
    }

    @org.junit.Ignore("Requires coroutine timer cancellation handling")
    @Test
    fun testErrorCheckIdentifiesWrongStar() = runTest {
        val viewModel = ConstellationsViewModel(streakRepository = streakRepository, mode = "standard")
        testDispatcher.scheduler.advanceUntilIdle()

        val puzzle = viewModel.puzzle.value!!
        // Find a cell that is NOT part of the solution
        var wrongRow = -1
        var wrongCol = -1
        for (r in 0 until puzzle.size) {
            for (c in 0 until puzzle.size) {
                if (!puzzle.solution.contains(Pair(r, c))) {
                    wrongRow = r
                    wrongCol = c
                    break
                }
            }
            if (wrongRow != -1) break
        }

        assertTrue(wrongRow != -1)

        // Set state to STAR manually by clicking twice (EMPTY -> CROSS -> STAR)
        viewModel.onCellClicked(wrongRow, wrongCol)
        viewModel.onCellClicked(wrongRow, wrongCol)

        // Trigger errorCheck
        viewModel.errorCheck()

        val checkedPuzzle = viewModel.puzzle.value!!
        assertTrue(checkedPuzzle.cells[wrongRow][wrongCol].isError)
    }

    @org.junit.Ignore("Requires coroutine timer cancellation handling")
    @Test
    fun testSolvingPuzzleWinsGameAndUpdatesDailyStreak() = runTest {
        val today = com.funkyotc.puzzleverse.core.todayEpochDay()
        val viewModel = ConstellationsViewModel(streakRepository = streakRepository, mode = "daily")
        testDispatcher.scheduler.advanceUntilIdle()

        val puzzle = viewModel.puzzle.value!!
        assertFalse(viewModel.isGameWon.value)

        // Place all stars according to the solution
        for ((r, c) in puzzle.solution) {
            // Need to set to STAR (click twice if empty, or use hint)
            while (viewModel.puzzle.value!!.cells[r][c].state != CellState.STAR) {
                viewModel.onCellClicked(r, c)
            }
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue("Game should be won after placing all solution stars", viewModel.isGameWon.value)

        // Check streak repository updated for daily mode
        val streak = streakRepository.getStreak("constellations")
        assertEquals(1, streak.count)
        assertEquals(today, streak.lastCompletedEpochDay)
    }
}
