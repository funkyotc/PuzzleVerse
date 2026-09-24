package com.funkyotc.puzzleverse.pullpin.physics

import com.funkyotc.puzzleverse.pullpin.data.PullPinLevel
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
        world.gravity = Vector2(0.0, 9.0)
        // dyn4j uses metres. Scale the 400 x 700 drawing coordinates by 100
        // so its contact tolerances and correction limits remain appropriate.
    }

    fun initWorld(level: PullPinLevel) {
        world.removeAllBodies()
        ballBodies.clear()
        pinBodies.clear()

        // Static wall bodies.
        for (wall in level.walls) {
            val body = Body()
            val fixture = BodyFixture(
                Geometry.createRectangle(wall.w / 100.0, wall.h / 100.0)
            )
            fixture.friction = 0.4
            fixture.restitution = 0.1
            body.addFixture(fixture)
            body.rotate(Math.toRadians(wall.angle.toDouble()))
            body.translate((wall.x + wall.w / 2.0) / 100, (wall.y + wall.h / 2.0) / 100)
            body.setMass(MassType.INFINITE)
            world.addBody(body)
        }

        // Static pin bodies (only the ones that have not been removed).
        for (pin in level.pins) {
            if (pin.removed) continue
            val body = Body()
            val fixture = BodyFixture(
                Geometry.createRectangle(pin.w / 100.0, pin.h / 100.0)
            )
            fixture.friction = 0.4
            fixture.restitution = 0.1
            body.addFixture(fixture)
            body.translate((pin.x + pin.w / 2.0) / 100, (pin.y + pin.h / 2.0) / 100)
            body.setMass(MassType.INFINITE)
            world.addBody(body)
            pinBodies[pin.id] = body
        }

        // Dynamic ball bodies.
        for (ball in level.balls) {
            val body = Body()
            val fixture = BodyFixture(Geometry.createCircle(ball.radius / 100.0))
            fixture.density = 1.0
            fixture.friction = 0.3
            fixture.restitution = 0.05
            body.addFixture(fixture)
            body.translate(ball.x / 100.0, ball.y / 100.0)
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

    fun removeBall(id: String) {
        ballBodies.remove(id)?.let { world.removeBody(it) }
    }

    fun isSettled(): Boolean = ballBodies.values.all { it.isAtRest }

    fun getBallTransforms(): Map<String, Pair<Float, Float>> {
        val transforms = mutableMapOf<String, Pair<Float, Float>>()
        for ((id, body) in ballBodies) {
            val transform = body.transform
            transforms[id] = (transform.translationX * 100).toFloat() to (transform.translationY * 100).toFloat()
        }
        return transforms
    }

    fun isBallOutOfBounds(ballId: String): Boolean {
        val body = ballBodies[ballId] ?: return false
        val x = body.transform.translationX * 100
        val y = body.transform.translationY * 100
        return y > WORLD_H + 50f ||
            x < -50f ||
            x > WORLD_W + 50f
    }
}
