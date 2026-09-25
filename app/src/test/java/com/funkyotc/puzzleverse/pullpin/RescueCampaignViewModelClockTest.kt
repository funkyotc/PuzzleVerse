package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.rescue.RescueCampaign
import com.funkyotc.puzzleverse.pullpin.rescue.RescueCampaignViewModel
import com.funkyotc.puzzleverse.pullpin.rescue.RescueStatus
import org.junit.Assert.*
import org.junit.Test

class RescueCampaignViewModelClockTest {
    @Test fun overlaysBackgroundRetryAndSelectionPreserveTheRescueClock() {
        val model = RescueCampaignViewModel("standard", null, true, null)
        model.frame(0)
        assertEquals(RescueStatus.READY, model.ui.value.state.status)
        model.frame(10_000_000_000L)
        assertEquals(0, model.ui.value.state.tick)

        model.start()
        model.frame(20_000_000_000L)
        model.frame(21_000_000_000L)
        assertEquals(60, model.ui.value.state.tick)
        model.setOverlayPaused(true)
        model.frame(40_000_000_000L)
        assertEquals(60, model.ui.value.state.tick)
        model.setOverlayPaused(false)
        model.frame(50_000_000_000L)
        assertEquals(60, model.ui.value.state.tick)
        model.frame(51_000_000_000L)
        assertEquals(120, model.ui.value.state.tick)

        model.setBackgroundPaused(true)
        model.frame(80_000_000_000L)
        assertEquals(120, model.ui.value.state.tick)
        model.setBackgroundPaused(false)
        model.frame(90_000_000_000L)
        assertEquals(120, model.ui.value.state.tick)

        model.retry()
        assertEquals(0, model.ui.value.state.tick)
        assertEquals(RescueStatus.RUNNING, model.ui.value.state.status)
        assertTrue(model.ui.value.state.pins.none { it.acceptedTick != null })
        val exact = RescueCampaignViewModel("puzzle", RescueCampaign.levels[1].id, false, null)
        assertEquals(RescueStatus.READY, exact.ui.value.state.status)
        assertEquals(1, exact.ui.value.levelIndex)
        assertFalse(exact.pull("prepare"))
    }
}
