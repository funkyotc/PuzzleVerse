package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.core.UtcDaySource
import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.data.SaveStateRepository
import com.funkyotc.puzzleverse.pullpin.rescue.*
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import org.junit.Assert.*
import org.junit.Test

class RescueCampaignIntegrationTest {
    private fun solve(model: RescueCampaignViewModel) {
        val level = RescueCampaign.levels[model.ui.value.levelIndex]
        model.start()
        model.frame(0)
        for (action in level.solution) {
            model.frame(action.tick * 1_000_000_000L / 60)
            assertTrue(model.pull(action.pinId))
        }
        model.frame(80_000_000_000L)
        assertEquals(RescueStatus.WON, model.ui.value.state.status)
    }

    @Test fun versionedProgressSelectsFirstUnfinishedAndNewGameStartsAtFirst() {
        val prefs = InMemorySharedPreferences()
        val completion = PuzzleCompletionRepository(prefs = prefs)
        completion.markCompleted("pullpin_easy_001")
        completion.markCompleted(RescueCampaign.levels[0].id)
        val standard = RescueCampaignViewModel("standard", null, false, null)
        standard.setCompletionRepo(completion)
        assertEquals(1, standard.ui.value.levelIndex)
        solve(standard)
        assertTrue(completion.isCompleted(RescueCampaign.levels[1].id))
        standard.advance()
        assertEquals(2, standard.ui.value.levelIndex)
        assertEquals(RescueStatus.READY, standard.ui.value.state.status)
        val newGame = RescueCampaignViewModel("standard", null, true, null)
        newGame.setCompletionRepo(completion)
        assertEquals(0, newGame.ui.value.levelIndex)
        val exact = RescueCampaignViewModel("puzzle", RescueCampaign.levels[2].id, false, null)
        exact.setCompletionRepo(completion)
        assertEquals(2, exact.ui.value.levelIndex)
        assertFalse(exact.canAdvance)
    }

    @Test fun dailyUsesUtcDayAndCreditsOnlyCurrentDayVictory() {
        var day = 21_000L
        val source = UtcDaySource { day }
        val streak = StreakRepository(sharedPreferences = InMemorySharedPreferences(), daySource = source)
        val completion = PuzzleCompletionRepository(prefs = InMemorySharedPreferences())
        val current = RescueCampaignViewModel("daily", null, false, streak.forDailyRun(day), source)
        current.setCompletionRepo(completion)
        assertEquals(RescueCampaign.dailyLevel(day), RescueCampaign.levels[current.ui.value.levelIndex])
        solve(current)
        assertEquals(day, streak.getStreak("pullpin").lastCompletedEpochDay)
        current.retry()
        solve(current)
        assertEquals(1, streak.getStreak("pullpin").count)
        val stale = RescueCampaignViewModel("daily", null, false, streak.forDailyRun(day), source)
        stale.setCompletionRepo(completion)
        day++
        stale.start()
        stale.frame(0)
        stale.frame(80_000_000_000L)
        assertEquals(0, stale.ui.value.state.tick)
        assertEquals(day - 1, streak.getStreak("pullpin").lastCompletedEpochDay)
        val next = RescueCampaignViewModel("daily", null, false, streak.forDailyRun(day), source)
        next.setCompletionRepo(completion)
        assertEquals(RescueCampaign.dailyLevel(day), RescueCampaign.levels[next.ui.value.levelIndex])
    }

    @Test fun migrationRetiresOnlyOldPullPinSaveAndIsIdempotent() {
        val savePrefs = InMemorySharedPreferences()
        val saves = SaveStateRepository(sharedPreferences = savePrefs)
        saves.saveGameState("pullpin", "standard", "pullpin_easy_001")
        saves.saveGameState("sudoku", "standard", "sudoku_001")
        val progressPrefs = InMemorySharedPreferences()
        val progress = PuzzleCompletionRepository(prefs = progressPrefs)
        progress.markCompleted("pullpin_easy_001")
        progress.markCompleted(RescueCampaign.levels[0].id)
        val migration = RescueMigration(InMemorySharedPreferences(), saves)
        migration.migrate()
        assertNull(saves.getSaveState("pullpin"))
        assertEquals("sudoku_001", saves.getSaveState("sudoku")?.puzzleId)
        assertTrue(progress.isCompleted("pullpin_easy_001"))
        assertTrue(progress.isCompleted(RescueCampaign.levels[0].id))
        saves.saveGameState("pullpin", "standard", RescueCampaign.levels[0].id)
        migration.migrate()
        assertEquals(RescueCampaign.levels[0].id, saves.getSaveState("pullpin")?.puzzleId)
    }
}
