package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.data.*
import com.funkyotc.puzzleverse.pullpin.physics.PullPinSession
import org.junit.Assert.*
import org.junit.Test

class PullPinCampaignTest {
    @Test fun everyLevelWinsThroughRealPhysicsAndRules() {
        val failures = mutableListOf<String>()
        for (waitFrames in listOf(120, 300)) for (level in PullPinPregenerated.ALL_LEVELS) {
            val session = PullPinSession(level)
            repeat(120) { session.step() }
            for (pin in level.solution) {
                if (!session.pull(pin)) { failures += "${level.id}: rejected $pin (${session.state.lostReason})"; break }
                repeat(waitFrames) { session.step() }
            }
            if (session.state.status != GameStatus.WON) failures += "${level.id}: ${session.state.status} ${session.state.lostReason} remaining=${session.state.balls.filter { !it.captured }}"
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }

    @Test fun openingExitBeforeMixingCannotWin() {
        val session = PullPinSession(PullPinPregenerated.ALL_LEVELS.first())
        session.pull("exit_0")
        repeat(300) { session.step() }
        assertNotEquals(GameStatus.WON, session.state.status)
        session.pull("mix_0")
        repeat(300) { session.step() }
        assertEquals(GameStatus.LOST, session.state.status)
    }

    @Test fun bombContactLosesAndUndoRestoresTheBoard() {
        val level = PullPinPregenerated.ALL_LEVELS.first { it.balls.any { b -> b.isBomb } }
        val session = PullPinSession(level)
        val bombPin = level.pins.first { it.id.startsWith("hazard") }
        session.pull(bombPin.id)
        repeat(300) { session.step() }
        assertEquals(GameStatus.LOST, session.state.status)
        val restored = session.undo()
        assertEquals(GameStatus.IDLE, restored.state.status)
        assertEquals(0, restored.state.moves)
        assertFalse(restored.state.pins.any { it.removed || it.isPulling })
    }

    @Test fun lowerReservoirCannotBeDrainedWhileGrey() {
        val level = PullPinPregenerated.ALL_LEVELS.first { it.pins.any { p -> p.id.startsWith("drain") } }
        val session = PullPinSession(level)
        assertTrue(session.pull("drain_0"))
        repeat(200) { session.step() }
        assertEquals(GameStatus.LOST, session.state.status)
        assertTrue(session.state.lostReason!!.contains("grey"))
    }

    @Test fun lockedGateRejectsEarlyPullWithoutSpendingMove() {
        val level = PullPinPregenerated.ALL_LEVELS.first { it.pins.any { p -> p.unlockAfter > 0 } }
        val session = PullPinSession(level)
        assertFalse(session.pull(level.pins.first { it.unlockAfter > 0 }.id))
        assertEquals(0, session.state.moves)
    }

    @Test fun closedPinDoesNotColorAcrossBarrier() {
        val level = PullPinLevel("barrier", "Easy", emptyList(), emptyList(),
            listOf(PinData("pin", 170f, 200f, 60f, 10f)),
            listOf(BallSpawn("grey", 200f, 187f, 0, 12f), BallSpawn("color", 200f, 223f, 1, 12f)))
        val session = PullPinSession(level)
        session.step()
        assertEquals(0, session.state.balls.first().color)
    }

    @Test fun undoReplaysEarlierMotionExactly() {
        val session = PullPinSession(PullPinPregenerated.ALL_LEVELS.first())
        session.pull("mix_0")
        repeat(200) { session.step() }
        val before = session.state
        session.pull("exit_0")
        repeat(20) { session.step() }
        assertEquals(before, session.undo().state)
    }
}
