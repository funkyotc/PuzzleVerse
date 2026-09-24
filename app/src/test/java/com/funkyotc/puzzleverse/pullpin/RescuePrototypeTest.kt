package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.rescue.*
import java.io.File
import kotlin.math.hypot
import org.junit.Test
import org.junit.Assert.*

/** Real dyn4j traces; report files are generated evidence, never campaign data. */
class RescuePrototypeTest {
    private val level = RescuePrototype.divert
    private val output = File("build/reports/pullpin-rescue").also { it.mkdirs() }

    private fun run(pullTick: Int, report: Boolean = true): RescueState {
        val session = RescueSession(level)
        session.start()
        val costs = mutableListOf<Long>()
        while (session.state.tick < 3600 && session.state.status == RescueStatus.RUNNING) {
            if (session.state.tick == pullTick) assertTrue(session.pull("divert"))
            val start = System.nanoTime()
            session.step()
            // Exclude initial warm-up; measure the entire step including rule evaluation/snapshot.
            if (session.state.tick > 120) costs += System.nanoTime() - start
            if (report && session.state.tick in listOf(300, 600, 900, 1200)) {
                snapshot("pull-$pullTick-tick-${session.state.tick}", session.state)
            }
        }
        val state = session.state
        assertEquals(level.stones.map { it.id }.toSet(), state.stones.map { it.id }.toSet())
        assertEquals(352, state.stones.size)
        var overlap = 0.0
        for (i in state.stones.indices) for (j in 0 until i) {
            val a = state.stones[i]; val b = state.stones[j]
            overlap = maxOf(overlap, a.radius + b.radius - hypot(a.x - b.x, a.y - b.y))
        }
        assertTrue("Excessive final contact penetration: $overlap", overlap < 1.5)
        if (report) {
            snapshot("pull-$pullTick-final", state)
            println("pull=$pullTick ticks=${state.tick} status=${state.status} " +
                "coverage=${state.burial} maxOverlap=$overlap " +
                "meanStepMs=${costs.average()/1e6} p95StepMs=${costs.sorted()[costs.size*95/100]/1e6}")
        }
        val terminal = session.state
        assertFalse("Trace did not reach a terminal outcome", terminal.status == RescueStatus.RUNNING)
        assertFalse(session.pull("divert"))
        session.step()
        assertEquals(terminal, session.state)
        return state
    }

    @Test fun noInputBuriesWithinTargetAndAnEighteenSecondPullIsTooLate() {
        val noInput = run(-1)
        assertEquals(RescueStatus.LOST, noInput.status)
        assertTrue(noInput.lossReason!!.contains("buried"))
        assertTrue(noInput.tick in 15 * 60..25 * 60)
        val late = run(1080)
        assertEquals(RescueStatus.LOST, late.status)
        assertTrue(late.lossReason!!.contains("buried"))
    }

    @Test fun earlyTimingWindowDivertsFiniteStockAndIsRepeatable() {
        for (tick in listOf(120, level.solution.single().tick, 240)) {
            val result = run(tick)
            assertEquals(RescueStatus.WON, result.status)
            assertTrue(result.stones.count { it.x < 260 && it.y > 500 } > 300)
            assertTrue(result.stones.none { it.y < 380 })
            assertFalse(result.burial.covered)
            if (tick == level.solution.single().tick) assertEquals(result, run(tick, report = false))
        }
    }

    @Test fun delayedPullWinsWithoutExcavatingThePartiallyBuriedKing() {
        val delayed = run(600)
        assertEquals(RescueStatus.WON, delayed.status)
        assertTrue(delayed.stones.count { it.x > 268 && it.y > 580 } > 50)
        assertTrue(delayed.burial.left >= 0.42 && delayed.burial.right >= 0.42)
        assertTrue(delayed.burial.above < 0.30)
        assertEquals(0, delayed.burialTicks)
    }

    private fun snapshot(name: String, state: RescueState) {
        File(output, "$name.csv").writeText("x,y,radius,speed\n" + state.stones.joinToString("\n") {
            "${it.x},${it.y},${it.radius},${it.speed}"
        })
        val svg = buildString {
            append("<svg xmlns='http://www.w3.org/2000/svg' width='400' height='730' viewBox='0 0 400 730'>")
            append("<rect width='400' height='730' fill='#111d32'/>")
            for (w in level.walls) append("<rect x='${w.x}' y='${w.y}' width='${w.w}' height='${w.h}' fill='#627a94' transform='rotate(${w.angle} ${w.x+w.w/2} ${w.y+w.h/2})'/>")
            for (p in state.pins.filter { !it.removed }) {
                val w = p.pin.shape
                append("<rect x='${w.x}' y='${w.y}' width='${w.w}' height='${w.h}' fill='#ffca68' transform='rotate(${w.angle} ${w.x+w.w/2} ${w.y+w.h/2})'/>")
            }
            val k = level.king
            append("<rect x='${k.x-k.bodyWidth/2}' y='${k.floorY-k.bodyHeight}' width='${k.bodyWidth}' height='${k.bodyHeight}' fill='#efad51'/>")
            append("<circle cx='${k.x}' cy='${k.headY}' r='${k.headRadius}' fill='#efad51'/>")
            for (s in state.stones) append("<circle cx='${s.x}' cy='${s.y}' r='${s.radius}' fill='#b2b8c2' stroke='#111d32' stroke-width='0.5'/>")
            append("<text x='12' y='720' fill='white' font-size='12'>$name: ${state.status} tick ${state.tick}</text></svg>")
        }
        File(output, "$name.svg").writeText(svg)
    }
}
