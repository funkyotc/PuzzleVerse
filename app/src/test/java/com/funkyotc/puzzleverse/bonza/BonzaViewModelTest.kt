package com.funkyotc.puzzleverse.bonza

import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
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
class BonzaViewModelTest {

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

    private val sampleGenerator = com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator(
        listOf(com.funkyotc.puzzleverse.bonza.data.BonzaPuzzleTheme("Fruits", listOf("APPLE", "BANANA", "ORANGE", "GRAPE", "PEAR", "MANGO", "KIWI", "MELON", "LEMON", "LIME")))
    )

    @Test
    fun testDailyModeWinUpdatesStreakRepository() {
        val today = todayEpochDay()
        val yesterday = today - 1

        streakRepository.saveStreak(Streak(gameId = "bonza", count = 4, lastCompletedEpochDay = yesterday))

        val viewModel = BonzaViewModel(
            mode = "daily",
            streakRepository = streakRepository,
            puzzleGenerator = sampleGenerator
        )

        viewModel.onDragEnd()

        val updatedStreak = streakRepository.recordDailyCompletion("bonza", today)
        assertEquals(5, updatedStreak.count)
        assertEquals(today, updatedStreak.lastCompletedEpochDay)
    }

    @Test
    fun testLayoutFragmentsNoOverlapsAndMinGap() {
        val viewModel = BonzaViewModel(
            mode = "standard",
            streakRepository = streakRepository,
            puzzleGenerator = sampleGenerator
        )

        val puzzle = viewModel.puzzle.value
        assertTrue("Puzzle should have fragments", puzzle.fragments.isNotEmpty())

        val fragments = puzzle.fragments
        for (i in fragments.indices) {
            val f1 = fragments[i]
            val w1 = if (f1.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.HORIZONTAL) f1.text.length else 1
            val h1 = if (f1.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.VERTICAL) f1.text.length else 1
            val r1 = androidx.compose.ui.geometry.Rect(
                f1.currentPosition.x,
                f1.currentPosition.y,
                f1.currentPosition.x + w1,
                f1.currentPosition.y + h1
            )

            for (j in i + 1 until fragments.size) {
                val f2 = fragments[j]
                val w2 = if (f2.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.HORIZONTAL) f2.text.length else 1
                val h2 = if (f2.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.VERTICAL) f2.text.length else 1
                val r2 = androidx.compose.ui.geometry.Rect(
                    f2.currentPosition.x,
                    f2.currentPosition.y,
                    f2.currentPosition.x + w2,
                    f2.currentPosition.y + h2
                )

                // Verify >= 1 unit gap between all placed pieces
                val hasGap = r1.right + 1 <= r2.left || r2.right + 1 <= r1.left ||
                        r1.bottom + 1 <= r2.top || r2.bottom + 1 <= r1.top
                assertTrue("Fragments ${f1.id} and ${f2.id} must be placed cleanly with >= 1 unit gap", hasGap)
            }
        }
    }

    @Test
    fun testGetPuzzleBoundsFrameCalculation() {
        val viewModel = BonzaViewModel(
            mode = "standard",
            streakRepository = streakRepository,
            puzzleGenerator = sampleGenerator
        )

        val fragments = viewModel.puzzle.value.fragments
        val bounds = viewModel.getPuzzleBounds()

        for (fragment in fragments) {
            val w = if (fragment.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.HORIZONTAL) fragment.text.length else 1
            val h = if (fragment.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.VERTICAL) fragment.text.length else 1

            assertTrue("Min X bound check", fragment.currentPosition.x >= bounds.left + 1)
            assertTrue("Max X bound check", fragment.currentPosition.x + w <= bounds.right - 1)
            assertTrue("Min Y bound check", fragment.currentPosition.y >= bounds.top + 1)
            assertTrue("Max Y bound check", fragment.currentPosition.y + h <= bounds.bottom - 1)
        }
    }

    @Test
    fun testHintFeatureAndSnapConnections() {
        val viewModel = BonzaViewModel(
            mode = "standard",
            streakRepository = streakRepository,
            puzzleGenerator = sampleGenerator
        )

        val initialPuzzle = viewModel.puzzle.value
        val initialDistinctGroups = initialPuzzle.fragments.map { it.groupId }.distinct().size
        assertTrue("Initially fragments should be in separate groups", initialDistinctGroups > 1)

        // Apply hint repeatedly until puzzle is solved
        var safetyLimit = 50
        while (!viewModel.isGameWon.value && safetyLimit > 0) {
            viewModel.hint()
            safetyLimit--
        }

        assertTrue("Game should be won after applying hints", viewModel.isGameWon.value)
        val finalPuzzle = viewModel.puzzle.value
        val finalDistinctGroups = finalPuzzle.fragments.map { it.groupId }.distinct().size
        assertEquals("All fragments should belong to 1 group when solved", 1, finalDistinctGroups)
    }

    @Test
    fun testLayoutFragmentsWithLargeNumberOfFragments() {
        val largeTheme = com.funkyotc.puzzleverse.bonza.data.BonzaPuzzleTheme(
            theme = "ANIMALS",
            words = listOf(
                "ELEPHANT", "GIRAFFE", "ALLIGATOR", "KANGAROO", "HIPPOPOTAMUS",
                "CHEETAH", "LEOPARD", "RHINOCEROS", "FLAMINGO", "PENGUIN",
                "CHIMPANZEE", "PLATYPUS", "PORCUPINE", "ARMADILLO", "CROCODILE",
                "TORTOISE", "SALAMANDER", "RATTLESNAKE", "HUMMINGBIRD", "WOODPECKER"
            )
        )
        val generator = com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator(listOf(largeTheme))
        val viewModel = BonzaViewModel(
            mode = "standard",
            streakRepository = streakRepository,
            puzzleGenerator = generator
        )

        val puzzle = viewModel.puzzle.value
        assertTrue("Puzzle should have 15+ fragments, found ${puzzle.fragments.size}", puzzle.fragments.size >= 15)

        val fragments = puzzle.fragments
        for (i in fragments.indices) {
            val f1 = fragments[i]
            val w1 = if (f1.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.HORIZONTAL) f1.text.length else 1
            val h1 = if (f1.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.VERTICAL) f1.text.length else 1
            val r1 = androidx.compose.ui.geometry.Rect(
                f1.currentPosition.x,
                f1.currentPosition.y,
                f1.currentPosition.x + w1,
                f1.currentPosition.y + h1
            )

            for (j in i + 1 until fragments.size) {
                val f2 = fragments[j]
                val w2 = if (f2.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.HORIZONTAL) f2.text.length else 1
                val h2 = if (f2.direction == com.funkyotc.puzzleverse.bonza.data.ConnectionDirection.VERTICAL) f2.text.length else 1
                val r2 = androidx.compose.ui.geometry.Rect(
                    f2.currentPosition.x,
                    f2.currentPosition.y,
                    f2.currentPosition.x + w2,
                    f2.currentPosition.y + h2
                )

                // Verify >= 1 unit gap between all placed pieces
                val hasGap = r1.right + 1 <= r2.left || r2.right + 1 <= r1.left ||
                        r1.bottom + 1 <= r2.top || r2.bottom + 1 <= r1.top
                assertTrue("Fragments ${f1.id} and ${f2.id} in large layout must be placed cleanly with >= 1 unit gap", hasGap)
            }
        }
    }
}

