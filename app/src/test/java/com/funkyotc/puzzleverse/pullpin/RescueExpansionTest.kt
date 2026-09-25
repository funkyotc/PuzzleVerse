package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.rescue.*
import org.junit.Assert.*
import org.junit.Test

class RescueExpansionTest {
    private fun play(level: RescueLevel, actions: List<TimedPull>, limit: Int = 4500): RescueState {
        val session = RescueSession(level)
        session.start()
        while (session.state.status == RescueStatus.RUNNING && session.state.tick < limit) {
            actions.filter { it.tick == session.state.tick }.forEach { assertTrue(session.pull(it.pinId)) }
            session.step()
        }
        assertEquals(level.stones.map { it.id }.toSet(), session.state.stones.map { it.id }.toSet())
        return session.state
    }

    @Test fun drainageSurvivalKeepsTheHeadSafeWhileStockFallsIntoTheLowerChamber() {
        val level = RescueCampaign.levels[3]
        assertEquals(RescueObjective.SURVIVE, level.objective)
        val ignored = play(level, emptyList())
        assertEquals(RescueStatus.LOST, ignored.status)
        assertTrue(ignored.tick in 15 * 60..25 * 60)
        val drainedOnly = play(level, listOf(TimedPull(450, "drain")))
        assertEquals(RescueStatus.LOST, drainedOnly.status)
        assertTrue("The physical drain should delay burial", drainedOnly.tick > ignored.tick + 100)
        val rescued = play(level, level.solution)
        assertEquals(RescueStatus.WON, rescued.status)
        assertTrue(rescued.stones.any { it.y > 660 && it.x > 260 })
        assertFalse(rescued.burial.covered)
    }

    @Test fun fullClearRequiresDrainAfterSafeDiversion() {
        val level = RescueCampaign.levels[4]
        assertEquals(RescueObjective.CLEAR, level.objective)
        assertEquals(RescueStatus.LOST, play(level, emptyList()).status)
        val diverted = play(level, listOf(TimedPull(180, "divert")))
        assertEquals(RescueStatus.RUNNING, diverted.status)
        assertFalse(diverted.bodyClear)
        val freed = play(level, level.solution)
        assertEquals(RescueStatus.WON, freed.status)
        assertTrue(freed.bodyClear)
        assertFalse(freed.burial.covered)
    }

    @Test fun physicalExitNeedsAnOpenGateAndSafeArrival() {
        val level = RescueCampaign.levels[5]
        assertEquals(RescueObjective.ESCAPE, level.objective)
        val noInput = play(level, emptyList(), limit = 900)
        assertEquals(RescueStatus.RUNNING, noInput.status)
        assertFalse(noInput.exitReached)
        val blocked = play(level, listOf(TimedPull(180, "divert")), limit = 900)
        assertEquals(RescueStatus.RUNNING, blocked.status)
        assertFalse(blocked.exitReached)
        assertTrue(blocked.king!!.x < level.exitX!!)
        assertEquals(RescueStatus.LOST, play(level, listOf(TimedPull(450, "exit")), limit = 1500).status)
        val escaped = play(level, level.solution)
        assertEquals(RescueStatus.WON, escaped.status)
        assertTrue(escaped.exitReached)
        assertTrue(escaped.bodyClear)
        assertEquals(level.king.floorY, escaped.king!!.floorY, 3.0)
    }
}
