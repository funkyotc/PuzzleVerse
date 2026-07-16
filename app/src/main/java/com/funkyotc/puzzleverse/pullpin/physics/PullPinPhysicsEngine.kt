package com.funkyotc.puzzleverse.pullpin.physics

import com.funkyotc.puzzleverse.pullpin.data.BallSpawn
import com.funkyotc.puzzleverse.pullpin.data.CupData
import com.funkyotc.puzzleverse.pullpin.data.PinData
import com.funkyotc.puzzleverse.pullpin.data.PullPinLevel
import com.funkyotc.puzzleverse.pullpin.data.WallSegment
import com.funkyotc.puzzleverse.pullpin.data.WORLD_H
import com.funkyotc.puzzleverse.pullpin.data.WORLD_W
import org.dyn4j.dynamics.Body
import org.dyn4j.dynamics.BodyFixture
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.MassType
import org.dyn4j.geometry.Vector2
import org.dyn4j.world.World

class PullPinPhysicsEngine {
    private val world = World<Body>()

    private val ballBodies = mutableMapOf<String, Body>()
    private val pinBodies = mutableMapOf<String, Body>()

    init {
        // Gravity points toward +y, which we treat as "down" to match the
        // screen/world coordinate system (y grows downward, bottom = WORLD_H).
        world.gravity = Vector2(0.0, 900.0)
    }

    fun initWorld(level: PullPinLevel) {
        world.removeAllBodies()
        ballBodies.clear()
        pinBodies.clear()

        // Static wall bodies.
        for (wall in level.walls) {
            val body = Body()
            val fixture = BodyFixture(
                Geometry.createRectangle(wall.w.toDouble(), wall.h.toDouble())
            )
            fixture.friction = 0.4
            fixture.restitution = 0.1
            body.addFixture(fixture)
            body.translate(wall.x + wall.w / 2.0, wall.y + wall.h / 2.0)
            body.setMass(MassType.INFINITE)
            world.addBody(body)
        }

        // Static pin bodies (only the ones that have not been removed).
        for (pin in level.pins) {
            if (pin.removed) continue
            val body = Body()
            val fixture = BodyFixture(
                Geometry.createRectangle(pin.w.toDouble(), pin.h.toDouble())
            )
            fixture.friction = 0.4
            fixture.restitution = 0.1
            body.addFixture(fixture)
            body.translate(pin.x + pin.w / 2.0, pin.y + pin.h / 2.0)
            body.setMass(MassType.INFINITE)
            world.addBody(body)
            pinBodies[pin.id] = body
        }

        // Dynamic ball bodies.
        for (ball in level.balls) {
            val body = Body()
            val fixture = BodyFixture(Geometry.createCircle(ball.radius.toDouble()))
            fixture.density = 1.0
            fixture.friction = 0.3
            fixture.restitution = 0.2
            body.addFixture(fixture)
            body.translate(ball.x.toDouble(), ball.y.toDouble())
            body.setMass(MassType.NORMAL)
            world.addBody(body)
            ballBodies[ball.id] = body
        }
    }

    fun removePin(pinId: String) {
        val body = pinBodies.remove(pinId)
        if (body != null) {
            world.removeBody(body)
        }
    }

    fun step(dt: Double) {
        world.step(1, dt)
    }

    fun getBallTransforms(): Map<String, Pair<Float, Float>> {
        val transforms = mutableMapOf<String, Pair<Float, Float>>()
        for ((id, body) in ballBodies) {
            val transform = body.transform
            transforms[id] = transform.translationX.toFloat() to transform.translationY.toFloat()
        }
        return transforms
    }

    fun getBallColors(): Map<String, Int> {
        // Colors are owned by the ViewModel; the engine does not mutate them.
        // Return any colors tracked alongside spawns by re-reading from nothing here.
        // Kept for API completeness; the ViewModel supplies authoritative colors.
        return emptyMap()
    }

    fun isBallOutOfBounds(ballId: String): Boolean {
        val body = ballBodies[ballId] ?: return false
        val x = body.transform.translationX
        val y = body.transform.translationY
        return y > WORLD_H + 50f ||
            x < -50f ||
            x > WORLD_W + 50f
    }
}
