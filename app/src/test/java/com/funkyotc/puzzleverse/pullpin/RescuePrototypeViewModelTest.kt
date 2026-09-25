package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.rescue.RescuePrototypeViewModel
import com.funkyotc.puzzleverse.pullpin.rescue.RescueStatus
import org.junit.Assert.*
import org.junit.Test

class RescuePrototypeViewModelTest {
    @Test fun overlaysBackgroundRetryAndSelectionPreserveTheRescueClock() {
        val model = RescuePrototypeViewModel()
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
        model.selectLevel(1)
        assertEquals(RescueStatus.READY, model.ui.value.state.status)
        assertEquals(1, model.ui.value.levelIndex)
        assertFalse(model.pull("prepare"))
    }
}
