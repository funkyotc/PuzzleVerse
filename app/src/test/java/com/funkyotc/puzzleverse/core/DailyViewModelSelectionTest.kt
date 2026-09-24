package com.funkyotc.puzzleverse.core

import com.funkyotc.puzzleverse.arrowescape.ui.ArrowEscapeViewModel
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModel
import com.funkyotc.puzzleverse.hexasort.viewmodel.HexaSortViewModel
import com.funkyotc.puzzleverse.hexastack.viewmodel.HexaStackViewModel
import com.funkyotc.puzzleverse.nonogram.viewmodel.NonogramViewModel
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.wordle.data.WordleStatsRepository
import com.funkyotc.puzzleverse.wordle.viewmodel.WordleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DailyViewModelSelectionTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @Before fun setup() = Dispatchers.setMain(dispatcher)
    @After fun teardown() = Dispatchers.resetMain()

    @Test fun checkedInDailyBoardsMatchOnFreshInstancesAndChangeTomorrow() {
        val day = 20_000L
        fun boards(challengeDay: Long): Triple<Any, Any, Any> {
            val clock = UtcDaySource { challengeDay }
            val nonogram = NonogramViewModel(mode = "daily", daySource = clock).also { it.startNewGame() }
            val kakuro = KakuroViewModel(mode = "daily", daySource = clock)
            val arrow = ArrowEscapeViewModel(StreakRepository(), SettingsRepository(), "daily", daySource = clock)
            return Triple(nonogram.state.value.solutionGrid,
                kakuro.state.value.grid, arrow.uiState.value.arrows)
        }
        val firstDevice = boards(day)
        val secondDevice = boards(day)
        val tomorrow = boards(day + 1)
        assertEquals(firstDevice, secondDevice)
        assertNotEquals(firstDevice.first, tomorrow.first)
        assertNotEquals(firstDevice.second, tomorrow.second)
        assertNotEquals(firstDevice.third, tomorrow.third)
    }

    @Test fun wordleDailyWordChangesWithoutAdjacentRepeats() {
        val dictionary = listOf("apple", "beach", "crown", "dream", "eager")
        fun word(day: Long) = WordleViewModel("daily", StreakRepository(),
            WordleStatsRepository(), dictionary, UtcDaySource { day }).wordleState.value!!.solution
        for (day in 20_000L..20_030L) {
            assertEquals(word(day), word(day))
            assertNotEquals(word(day), word(day + 1))
        }
    }

    @Test fun hexaDailyLevelIdsMatchAcrossInstancesAndChangeTomorrow() {
        fun ids(day: Long): Pair<String, String> {
            val clock = UtcDaySource { day }
            val sort = HexaSortViewModel(mode = "daily", streakRepository = StreakRepository(), daySource = clock)
            val stack = HexaStackViewModel(mode = "daily", streakRepository = StreakRepository(), daySource = clock)
            return sort.state.value!!.level.id to stack.state.value!!.level.id
        }
        for (day in 20_000L..20_030L) {
            assertEquals(ids(day), ids(day))
            val today = ids(day)
            val tomorrow = ids(day + 1)
            assertNotEquals(today.first, tomorrow.first)
            assertNotEquals(today.second, tomorrow.second)
        }
    }
}
