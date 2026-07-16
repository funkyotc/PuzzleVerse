package com.funkyotc.puzzleverse.woodnuts.physics

import com.funkyotc.puzzleverse.woodnuts.data.Bolt
import com.funkyotc.puzzleverse.woodnuts.data.PhysicsTransform
import com.funkyotc.puzzleverse.woodnuts.data.Plank
import com.funkyotc.puzzleverse.woodnuts.data.WoodNutsLevel
import org.dyn4j.dynamics.Body
import org.dyn4j.dynamics.BodyFixture
import org.dyn4j.dynamics.joint.RevoluteJoint
import org.dyn4j.collision.CategoryFilter
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.MassType
import org.dyn4j.geometry.Vector2
import org.dyn4j.world.World

class WoodNutsPhysicsEngine {
    private val world = World<Body>()
    private val plankBodies = mutableMapOf<String, Body>()
    private val boltBodies = mutableMapOf<String, Body>()
    private val joints = mutableListOf<RevoluteJoint<Body>>()

    init {
        world.gravity = Vector2(0.0, 15.0) // Gravity down (tuned for feel)
    }

    fun initWorld(level: WoodNutsLevel, planks: List<Plank>, bolts: List<Bolt>) {
        world.removeAllBodies()
        world.removeAllJoints()
        plankBodies.clear()
        boltBodies.clear()
        joints.clear()

        // Create bolts (static)
        for (bolt in bolts) {
            if (bolt.removed) continue
            val body = Body()
            // Radius of bolt is 0.18 in relative terms (matching cellPx * 0.18f in UI)
            val fixture = BodyFixture(Geometry.createCircle(0.18))
            val category = 1L shl 15 // Bolts are in category 15
            val mask = 0xFFFFL // Collide with everything
            fixture.filter = CategoryFilter(category, mask)
            fixture.friction = 0.3
            fixture.restitution = 0.2
            
            body.addFixture(fixture)
            body.translate(bolt.col.toDouble() + 0.5, bolt.row.toDouble() + 0.5)
            body.setMass(MassType.INFINITE)
            world.addBody(body)
            boltBodies[bolt.id] = body
        }

        // Create planks
        for (plank in planks) {
            if (plank.removed) continue
            val body = Body()
            
            val w = (plank.endCol - plank.startCol + 1).toDouble() - 0.16 // padding 0.08 * 2
            val h = (plank.endRow - plank.startRow + 1).toDouble() - 0.16
            
            val fixture = BodyFixture(Geometry.createRectangle(w, h))
            // Depth layer mapping for collision. Layer N only collides with Layer N and Bolts
            val layerCategory = 1L shl plank.depthLayer
            val layerMask = layerCategory or (1L shl 15) // Collide with same layer and bolts
            fixture.filter = CategoryFilter(layerCategory, layerMask)
            
            fixture.density = 1.0
            fixture.friction = 0.3
            fixture.restitution = 0.2
            
            body.addFixture(fixture)
            
            val cx = plank.startCol + (plank.endCol - plank.startCol + 1) / 2.0
            val cy = plank.startRow + (plank.endRow - plank.startRow + 1) / 2.0
            
            body.translate(cx, cy)
            // Apply initial rotation if necessary. We assume 0 for start.
            body.rotateAboutCenter(Math.toRadians(plank.angle.toDouble()))
            body.setMass(MassType.NORMAL)
            
            world.addBody(body)
            plankBodies[plank.id] = body
            
            // Attach to bolts
            for (boltId in plank.boltIds) {
                val boltBody = boltBodies[boltId]
                if (boltBody != null) {
                    val joint = RevoluteJoint(boltBody, body, boltBody.worldCenter)
                    world.addJoint(joint)
                    joints.add(joint)
                }
            }
        }
    }

    fun removeBolt(boltId: String) {
        val boltBody = boltBodies.remove(boltId)
        if (boltBody != null) {
            // Remove joints attached to this bolt
            val jointsToRemove = joints.filter { it.body1 == boltBody || it.body2 == boltBody }
            jointsToRemove.forEach { 
                world.removeJoint(it)
                joints.remove(it)
            }
            world.removeBody(boltBody)
        }
    }
    
    fun step(dt: Double): Map<String, PhysicsTransform> {
        world.step(1, dt)
        
        val transforms = mutableMapOf<String, PhysicsTransform>()
        for ((id, body) in plankBodies) {
            val transform = body.transform
            val x = transform.translationX.toFloat()
            val y = transform.translationY.toFloat()
            // Convert angle back to degrees for Compose UI
            val angle = Math.toDegrees(transform.rotationAngle).toFloat()
            
            transforms[id] = PhysicsTransform(x, y, angle)
        }
        return transforms
    }
    
    fun isPlankOutOfBounds(plankId: String, screenHeightUnits: Float): Boolean {
        val body = plankBodies[plankId] ?: return true
        return body.transform.translationY > screenHeightUnits
    }
    
    fun removePlank(plankId: String) {
        val body = plankBodies.remove(plankId)
        if (body != null) {
            world.removeBody(body)
        }
    }
}
