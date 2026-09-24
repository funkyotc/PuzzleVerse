package com.funkyotc.puzzleverse.core

import com.funkyotc.puzzleverse.arrowescape.data.ArrowEscapePregenerated
import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.data.SaveStateRepository
import com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated
import com.funkyotc.puzzleverse.hexasort.data.HexaSortRepository
import com.funkyotc.puzzleverse.hexastack.data.HexaStackPregenerated
import com.funkyotc.puzzleverse.hexastack.data.HexaStackRepository
import com.funkyotc.puzzleverse.kakuro.data.KakuroPregenerated
import com.funkyotc.puzzleverse.nonogram.data.NonogramPregenerated
import com.funkyotc.puzzleverse.shikaku.data.ShikakuBoard
import com.funkyotc.puzzleverse.shikaku.data.ShikakuRepository
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuRepository
import org.junit.Assert.*
import org.junit.Test

class DailyChallengeTest {
    @Test
    fun activeDailyRunGetsAReplacementRouteOnceAtMidnight() {
        val day = longArrayOf(20_000L)
        val tracker = DailyRolloverTracker(UtcDaySource { day[0] })
        assertNull(tracker.routeToReload("kakuro", "daily"))
        day[0]++
        assertEquals("game/kakuro/daily", tracker.routeToReload("kakuro", "daily"))
        assertNull(tracker.routeToReload("kakuro", "daily"))
        day[0]++
        assertNull(tracker.routeToReload("kakuro", "standard"))
    }

    @Test
    fun checkedInSelectionsAreStableAndDoNotRepeatTomorrow() {
        val poolSizes = listOf(
            NonogramPregenerated.ALL_PUZZLES.size,
            KakuroPregenerated.ALL_PUZZLES.size,
            ArrowEscapePregenerated.PUZZLES_BY_DIFFICULTY.getValue("Medium").size,
            HexaSortPregenerated.ALL_PUZZLES.size,
            HexaStackPregenerated.ALL_PUZZLES.size
        )
        for (size in poolSizes) {
            assertTrue(size > 1)
            for (day in 20_000L..20_030L) {
                val selected = dailyIndex(day, size)
                assertEquals(selected, dailyIndex(day, size))
                assertNotEquals(selected, dailyIndex(day + 1, size))
            }
        }
    }

    @Test
    fun kakuroEntriesImplementBrowserContract() {
        val puzzles: List<BrowseablePuzzle> = KakuroPregenerated.ALL_PUZZLES
        assertTrue(puzzles.isNotEmpty())
        assertTrue(puzzles.all { it.label.isNotBlank() && it.subtitle.isNotBlank() })
    }

    @Test
    fun legacyAndExpiredDailyMetadataIsRejectedButStandardIsPreserved() {
        val day = longArrayOf(20_000L)
        val repository = SaveStateRepository(daySource = UtcDaySource { day[0] },
            sharedPreferences = InMemorySharedPreferences())
        repository.saveGameState("sudoku", mode = "daily")
        assertEquals(20_000L, repository.getSaveState("sudoku")?.challengeEpochDay)
        repository.saveGameState("wordle", mode = "standard")
        day[0]++
        assertNull(repository.getSaveState("sudoku"))
        assertNotNull(repository.getSaveState("wordle"))

        val legacyPrefs = InMemorySharedPreferences()
        legacyPrefs.edit().putString("save_kakuro", """{"gameId":"kakuro","mode":"daily"}""").apply()
        val legacy = SaveStateRepository(daySource = UtcDaySource { day[0] }, sharedPreferences = legacyPrefs)
        assertNull(legacy.getSaveState("kakuro"))
    }

    @Test
    fun sameGameStandardSaveSurvivesDailyRolloverAndDailyReplay() {
        val day = longArrayOf(20_000L)
        val repository = SaveStateRepository(daySource = UtcDaySource { day[0] },
            sharedPreferences = InMemorySharedPreferences())
        repository.saveGameState("sudoku", mode = "standard", jsonState = "standard board")
        repository.saveGameState("sudoku", mode = "daily", jsonState = "daily board")
        assertEquals("daily board", repository.getSaveState("sudoku")?.jsonState)
        repository.clearSaveState("sudoku", onlyMode = "daily")
        assertEquals("standard board", repository.getSaveState("sudoku")?.jsonState)
        repository.saveGameState("sudoku", mode = "daily", jsonState = "another daily board")
        day[0]++
        assertEquals("standard board", repository.getSaveState("sudoku")?.jsonState)
        assertEquals("standard", repository.getSaveState("sudoku")?.mode)
    }

    @Test
    fun expiredDailySaveIsRemovedFromHomeListOnRestartAndAtMidnight() {
        val day = longArrayOf(20_000L)
        val preferences = InMemorySharedPreferences()
        val clock = UtcDaySource { day[0] }
        val original = SaveStateRepository(daySource = clock, sharedPreferences = preferences)
        original.saveGameState("sudoku", mode = "daily")
        day[0]++
        val restarted = SaveStateRepository(daySource = clock, sharedPreferences = preferences)
        assertFalse(restarted.savedGameIds.value.contains("sudoku"))
        restarted.saveGameState("kakuro", mode = "daily")
        day[0]++
        restarted.expireDailySaveStates()
        assertFalse(restarted.savedGameIds.value.contains("kakuro"))
    }

    @Test
    fun boardRepositoriesRejectExpiredDailyData() {
        val day = longArrayOf(20_000L)
        val clock = UtcDaySource { day[0] }
        val sudoku = SudokuRepository(sharedPreferences = InMemorySharedPreferences(), daySource = clock)
        sudoku.saveBoard(SudokuBoard(emptyList()), "daily_sudoku_board")
        sudoku.saveBoard(SudokuBoard(emptyList()), "standard_sudoku_board")
        val shikaku = ShikakuRepository(sharedPreferences = InMemorySharedPreferences(), daySource = clock)
        shikaku.saveBoard(ShikakuBoard(emptyList(), 8, 1L, "daily", true), "daily_shikaku_board")
        val sort = HexaSortRepository(prefs = InMemorySharedPreferences(), daySource = clock)
        sort.saveGrid(listOf(listOf(1)), "daily_hexasort_grid")
        val stack = HexaStackRepository(prefs = InMemorySharedPreferences(), daySource = clock)
        val stackSave = HexaStackRepository.HexaStackSave("level", emptyList(), emptyList(), 0, 0, 0)
        stack.saveGame("daily_hexastack_grid", stackSave)
        assertNotNull(sudoku.loadBoard("daily_sudoku_board"))
        assertNotNull(shikaku.loadBoard("daily_shikaku_board"))
        assertNotNull(sort.loadGrid("daily_hexasort_grid"))
        assertNotNull(stack.loadGame("daily_hexastack_grid"))
        day[0]++
        assertNull(sudoku.loadBoard("daily_sudoku_board"))
        assertNull(shikaku.loadBoard("daily_shikaku_board"))
        assertNull(sort.loadGrid("daily_hexasort_grid"))
        assertNull(stack.loadGame("daily_hexastack_grid"))
        assertNotNull(sudoku.loadBoard("standard_sudoku_board"))
    }
}
