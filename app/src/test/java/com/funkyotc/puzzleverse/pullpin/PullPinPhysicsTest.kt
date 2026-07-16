package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.data.BallSpawn
import com.funkyotc.puzzleverse.pullpin.data.CupData
import com.funkyotc.puzzleverse.pullpin.data.PinData
import com.funkyotc.puzzleverse.pullpin.data.PullPinLevel
import com.funkyotc.puzzleverse.pullpin.data.WallSegment
import com.funkyotc.puzzleverse.pullpin.physics.PullPinPhysicsEngine
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PullPinPhysicsTest {

    private fun stepN(engine: PullPinPhysicsEngine, n: Int) {
        repeat(n) { engine.step(1.0 / 60.0) }
    }

    private fun fallingSetup(): PullPinLevel {
        return PullPinLevel(
            id = "test_falling",
            difficulty = "easy",
            walls = listOf(WallSegment(0f, 680f, 400f, 20f)),
            cups = emptyList(),
            pins = listOf(PinData("p", 160f, 618f, 80f, 10f, 1f, 0f, false, false)),
            balls = listOf(BallSpawn("b", 200f, 600f, 1, 16f))
        )
    }

    @Test
    fun testBallFallsWhenPinRemoved() {
        val engine = PullPinPhysicsEngine()
        engine.initWorld(fallingSetup())

        stepN(engine, 60)
        val yBefore = engine.getBallTransforms()["b"]!!.second
        assertTrue("Ball should rest near pin (y ~600) before removal", yBefore in 590f..610f)

        engine.removePin("p")
        stepN(engine, 60)
        val yAfter = engine.getBallTransforms()["b"]!!.second
        assertTrue("Ball should have fallen after pin removal (y > 600)", yAfter > 600f)
    }

    @Test
    fun testWallsBlockBall() {
        val engine = PullPinPhysicsEngine()
        val level = PullPinLevel(
            id = "test_wall",
            difficulty = "easy",
            walls = listOf(WallSegment(100f, 650f, 200f, 30f)),
            cups = emptyList(),
            pins = emptyList(),
            balls = listOf(BallSpawn("b", 200f, 600f, 1, 16f))
        )
        engine.initWorld(level)

        stepN(engine, 60)
        val y = engine.getBallTransforms()["b"]!!.second
        assertTrue("Ball should rest on wall (y < 650)", y < 650f)
    }

    @Test
    fun testRemovePinOpensPath() {
        val engine = PullPinPhysicsEngine()
        engine.initWorld(fallingSetup())

        assertFalse("Ball should not be out of bounds initially", engine.isBallOutOfBounds("b"))

        engine.removePin("p")
        stepN(engine, 30)

        val transforms = engine.getBallTransforms()
        val pos = transforms["b"]
        assertNotNull("Ball transform should be present", pos)
        assertTrue("Ball should have moved (y > 600) after removing pin", pos!!.second > 600f)
    }

    @Test
    fun testBallOutOfBoundsDetection() {
        val engine = PullPinPhysicsEngine()
        val level = PullPinLevel(
            id = "test_oob",
            difficulty = "easy",
            walls = emptyList(),
            cups = emptyList(),
            pins = emptyList(),
            balls = listOf(BallSpawn("b", 200f, 300f, 1, 16f))
        )
        engine.initWorld(level)

        stepN(engine, 400)
        assertTrue("Ball should be detected out of bounds after falling past world", engine.isBallOutOfBounds("b"))
    }
}
