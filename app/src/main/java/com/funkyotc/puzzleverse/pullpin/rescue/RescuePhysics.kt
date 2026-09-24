package com.funkyotc.puzzleverse.pullpin.rescue

import com.funkyotc.puzzleverse.pullpin.data.WallSegment
import org.dyn4j.dynamics.Body
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.MassType
import org.dyn4j.geometry.Vector2
import org.dyn4j.world.World

/** Finite stock: every stone stays in the world, including after an escape fault. */
internal class RescuePhysics(private val level: RescueLevel) {
    private val world = World<Body>()
    private val stones = linkedMapOf<String, Body>()
    private val pins = linkedMapOf<String, Body>()

    init {
        world.gravity = Vector2(0.0, 0.5)
        level.walls.forEach { wall(it) }
        level.pins.forEach { pins[it.id] = wall(it.shape) }
        val king = level.king
        wall(WallSegment((king.x - king.bodyWidth / 2).toFloat(),
            (king.floorY - king.bodyHeight).toFloat(), king.bodyWidth.toFloat(), king.bodyHeight.toFloat()))
        val head = Body()
        head.addFixture(Geometry.createCircle(king.headRadius / SCALE)).apply {
            friction = 0.45; restitution = 0.0
        }
        head.translate(king.x / SCALE, king.headY / SCALE)
        head.setMass(MassType.INFINITE)
        world.addBody(head)
        level.stones.forEach { spawn ->
            val body = Body()
            body.addFixture(Geometry.createCircle(spawn.radius / SCALE)).apply {
                density = 2.5; friction = 0.45; restitution = 0.0
            }
            body.translate(spawn.x / SCALE, spawn.y / SCALE)
            body.setMass(MassType.NORMAL)
            // Circular collision shapes need rolling resistance to behave like coarse stones.
            body.linearDamping = 0.1
            body.angularDamping = 0.8
            world.addBody(body)
            stones[spawn.id] = body
        }
    }

    private fun wall(shape: WallSegment): Body {
        val body = Body()
        body.addFixture(Geometry.createRectangle(shape.w / SCALE, shape.h / SCALE)).apply {
            friction = 0.4; restitution = 0.0
        }
        body.rotate(Math.toRadians(shape.angle.toDouble()))
        body.translate((shape.x + shape.w / 2) / SCALE, (shape.y + shape.h / 2) / SCALE)
        body.setMass(MassType.INFINITE)
        world.addBody(body)
        return body
    }

    fun removePin(id: String) { pins.remove(id)?.let { world.removeBody(it) } }
    fun step() { world.step(1, 1.0 / RescueSession.TICKS_PER_SECOND) }
    fun snapshot(): List<StoneState> = level.stones.map { spawn ->
        val body = stones.getValue(spawn.id)
        StoneState(spawn.id, body.transform.translationX * SCALE, body.transform.translationY * SCALE,
            spawn.radius, body.linearVelocity.magnitude * SCALE, body.isAtRest)
    }

    private companion object { const val SCALE = 100.0 }
}
