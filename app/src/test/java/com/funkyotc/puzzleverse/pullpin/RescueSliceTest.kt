package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.rescue.*
import org.junit.Assert.*
import org.junit.Test

class RescueSliceTest {
    private fun play(level: RescueLevel, actions: List<TimedPull>, limit: Int = 4500): RescueState {
        val session = RescueSession(level)
        session.start()
        while (session.state.status == RescueStatus.RUNNING && session.state.tick < limit) {
            actions.filter { it.tick == session.state.tick }.forEach { assertTrue(session.pull(it.pinId)) }
            session.step()
        }
        println("${level.id} ${actions.joinToString()} => ${session.state.status} tick=${session.state.tick} " +
            "burial=${session.state.burial} basin=${session.state.stones.count { it.x < 260 && it.y > 500 }}")
        return session.state
    }

    @Test fun everyLevelHasATimedRescueAndNoInputBurial() {
        for (level in RescuePrototype.levels) {
            val noInput = play(level, emptyList())
            assertEquals(RescueStatus.LOST, noInput.status)
            assertTrue("No-input burial outside target: ${level.id} at ${noInput.tick}",
                noInput.tick in 15 * 60..25 * 60)
            val solved = play(level, level.solution)
            assertEquals(RescueStatus.WON, solved.status)
            assertEquals(level.stones.map { it.id }.toSet(), solved.stones.map { it.id }.toSet())
            assertFalse(solved.burial.covered)
        }
    }

    @Test fun receivingChamberMustBeOpenedButPromptRecoveryWorks() {
        val level = RescuePrototype.prepare
        assertEquals(RescueStatus.LOST, play(level, listOf(TimedPull(180, "divert"))).status)
        assertEquals(RescueStatus.WON, play(level,
            listOf(TimedPull(180, "divert"), TimedPull(300, "prepare"))).status)
        assertEquals(RescueStatus.WON, play(level,
            listOf(TimedPull(180, "prepare"), TimedPull(181, "divert"))).status)
    }

    @Test fun visibleRouteBackToKingIsDangerous() {
        val level = RescuePrototype.choose
        assertEquals(RescueStatus.LOST, play(level,
            listOf(TimedPull(120, "spill"), TimedPull(180, "divert"))).status)
        assertEquals(RescueStatus.WON, play(level, listOf(TimedPull(240, "divert"))).status)
    }
}
