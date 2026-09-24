package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.data.WallSegment
import com.funkyotc.puzzleverse.pullpin.rescue.*
import org.junit.Assert.*
import org.junit.Test

class RescueRulesTest {
    private val king = KingGeometry(200.0, 680.0)
    private val level = RescueLevel("rules", listOf(WallSegment(0f, 680f, 400f, 20f)),
        listOf(RescuePin("a", WallSegment(20f, 400f, 50f, 8f), "A"),
            RescuePin("b", WallSegment(90f, 400f, 50f, 8f), "B")),
        listOf(StoneSpawn("one", 40.0, 300.0)), king)

    /** Dense sample pile around, rather than inside, the solid king. */
    private fun pile(): List<StoneState> = buildList {
        var id = 0
        for (y in -22..35 step 5) for (x in -27..27 step 5) {
            if (y < -12 || kotlin.math.abs(x) > 12) {
                add(StoneState("p${id++}", king.x + x, king.headY + y, 3.0))
            }
        }
    }

    private fun advance(previous: RescueState, stones: List<StoneState> = previous.stones,
        pins: List<PinState> = previous.pins) = RescueRules.evaluate(level, previous, previous.tick + 1, pins, stones)
    private fun state(stones: List<StoneState>) = RescueState(0, RescueStatus.RUNNING, stones, emptyList())

    @Test fun burialNeedsBothSupportedSidesAndCrownNotImpactsOrRemotePiles() {
        val full = pile()
        assertTrue(BurialProbe.measure(king, full).covered)
        assertFalse(BurialProbe.measure(king, full.filter { it.y > king.headY + 12 }).covered)
        assertFalse(BurialProbe.measure(king, full.filter { it.x > king.x }).covered)
        assertFalse(BurialProbe.measure(king, full.map { it.copy(x = it.x - 100) }).covered)
        assertFalse(BurialProbe.measure(king, full.map { it.copy(speed = 100.0) }).covered)
        assertFalse(BurialProbe.measure(king, full.filter { it.y < king.headY + 8 }).covered)
    }

    @Test fun continuousExposureHasGraceAndClearingTheHeadResetsIt() {
        var observed = state(pile())
        repeat(RescueSession.BURIAL_GRACE_TICKS - 1) { observed = advance(observed) }
        assertEquals(RescueStatus.RUNNING, observed.status)
        assertEquals(59, observed.burialTicks)
        val cleared = observed.stones.filter { it.y > king.headY + 12 }
        observed = advance(observed, cleared)
        assertEquals(0, observed.burialTicks)
        assertEquals(RescueStatus.RUNNING, observed.status)
        repeat(59) { observed = advance(observed, pile()) }
        assertEquals(RescueStatus.RUNNING, observed.status)
        observed = advance(observed)
        assertEquals(RescueStatus.LOST, observed.status)
    }

    @Test fun burialWinsPrecedenceOverACompletedSettlingInterval() {
        val previous = state(pile()).copy(burialTicks = 59, settledTicks = 89)
        val next = advance(previous)
        assertEquals(90, next.settledTicks)
        assertEquals(RescueStatus.LOST, next.status)
    }

    @Test fun settlingIncludesRemoteStockAndRejectsBounceApexOrPositionCorrection() {
        val near = StoneState("near", 200.0, 675.0, 4.5)
        val upstream = StoneState("upstream", 40.0, 100.0, 4.5)
        val previous = state(listOf(near, upstream)).copy(settledTicks = 89)
        assertEquals(RescueStatus.WON, advance(previous).status)
        assertEquals(0, advance(previous, listOf(near, upstream.copy(speed = 2.0))).settledTicks)
        assertEquals(0, advance(previous, listOf(near, upstream.copy(y = 100.2))).settledTicks)
        val apex = advance(previous.copy(settledTicks = 0))
        assertEquals(RescueStatus.RUNNING, apex.status)
        assertEquals(0, advance(apex, listOf(near, upstream.copy(speed = 5.0))).settledTicks)
        val pending = listOf(PinState(level.pins.first(), acceptedTick = previous.tick))
        assertEquals(0, advance(previous, pins = pending).settledTicks)
    }

    @Test fun escapedStockLosesAndIsNeverDeleted() {
        val previous = state(listOf(StoneState("escaped", -10.0, 100.0, 4.5)))
        val next = advance(previous.copy(settledTicks = 89))
        assertEquals(RescueStatus.LOST, next.status)
        assertEquals(previous.stones, next.stones)
        assertTrue(next.lossReason!!.contains("escaped"))
    }

    @Test fun startPauseIndependentPullsCollisionTickAndRetry() {
        val session = RescueSession(level)
        val initial = session.state
        repeat(120) { session.step() }
        assertEquals(initial, session.state)
        assertFalse(session.pull("a"))
        session.start()
        assertTrue(session.pull("a"))
        assertFalse(session.pull("a"))
        session.step()
        assertTrue(session.pull("b"))
        assertEquals(listOf(TimedPull(0, "a"), TimedPull(1, "b")), session.actions)
        session.setPaused(true)
        val frozen = session.state
        repeat(200) { session.step() }
        assertEquals(frozen, session.state)
        assertFalse(session.pull("missing"))
        session.setPaused(false)
        repeat(12) { session.step() }
        assertFalse(session.state.pins[0].removed)
        assertEquals(13f / 14f, session.state.pins[0].progress(session.state.tick), 0.00001f)
        session.step()
        assertTrue(session.state.pins[0].removed)
        assertFalse(session.state.pins[1].removed)
        val retry = session.retry()
        assertEquals(initial, retry.state)
        assertTrue(retry.actions.isEmpty())
        retry.start()
        repeat(20) { retry.step() }
        assertTrue(retry.state.pins.none { it.removed || it.acceptedTick != null })
        session.step()
        assertTrue(session.state.pins.all { it.removed })
    }

    @Test fun frameCadenceAndPausedWallTimeDoNotChangeSimulation() {
        fun play(frames: Int): RescueState {
            val session = RescueSession(level)
            val clock = RescueClock(session)
            session.start()
            clock.frame(0)
            for (i in 1..frames) clock.frame(i * 2_000_000_000L / frames)
            assertEquals(120, session.state.tick)
            assertTrue(session.pull("a"))
            clock.setPaused(true)
            clock.frame(50_000_000_000L)
            val frozen = session.state
            assertFalse(session.pull("b"))
            clock.setPaused(false)
            clock.frame(100_000_000_000L)
            assertEquals(frozen, session.state)
            clock.frame(101_000_000_000L)
            return session.state
        }
        val fast = play(288)
        assertEquals(180, fast.tick)
        assertEquals(fast, play(60))
        assertEquals(fast, play(7))
    }

    @Test fun retainedStockCanWinAndTerminalStateRejectsAllFurtherActions() {
        val retained = level.copy(stones = listOf(StoneSpawn("one", 40.0, 390.0)))
        val session = RescueSession(retained)
        session.start()
        repeat(900) { session.step() }
        assertEquals(RescueStatus.WON, session.state.status)
        assertEquals(0, session.actions.size)
        assertEquals(1, session.state.stones.size)
        val terminal = session.state
        assertFalse(session.pull("a"))
        repeat(60) { session.step() }
        assertEquals(terminal, session.state)
    }

    @Test fun readinessAndAnUnrenderedPauseNeverAccumulateCatchUp() {
        val session = RescueSession(level)
        val clock = RescueClock(session)
        clock.frame(0)
        session.start()
        clock.frame(20_000_000_000L)
        assertEquals(0, session.state.tick)
        clock.frame(21_000_000_000L)
        assertEquals(60, session.state.tick)
        session.setPaused(true)
        session.setPaused(false)
        clock.frame(100_000_000_000L)
        assertEquals(60, session.state.tick)
    }
}
