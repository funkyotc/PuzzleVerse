package com.funkyotc.puzzleverse.pullpin.rescue

import com.funkyotc.puzzleverse.pullpin.data.WallSegment
import kotlin.math.atan2
import kotlin.math.hypot

/** Handcrafted playable rescue slice, separate from the production ball campaign and progress. */
object RescuePrototype {
    private fun ramp(x1: Double, y1: Double, x2: Double, y2: Double, thickness: Float = 8f): WallSegment {
        val length = hypot(x2 - x1, y2 - y1).toFloat()
        return WallSegment(((x1 + x2) / 2 - length / 2).toFloat(),
            ((y1 + y2) / 2 - thickness / 2).toFloat(), length, thickness,
            Math.toDegrees(atan2(y2 - y1, x2 - x1)).toFloat())
    }
    val divert = RescueLevel(
        id = "pullpin_rescue_prototype_001",
        walls = listOf(
            WallSegment(0f, 0f, 10f, 700f), WallSegment(390f, 0f, 10f, 700f),
            WallSegment(0f, 690f, 400f, 10f), WallSegment(0f, 0f, 400f, 10f),
            WallSegment(184f, 10f, 8f, 290f),
            ramp(10.0, 250.0, 116.0, 356.0), ramp(188.0, 300.0, 156.0, 356.0),
            ramp(100.0, 390.0, 190.0, 450.0),
            ramp(260.0, 496.67, 346.0, 554.0),
            WallSegment(260f, 496.67f, 8f, 193.33f)
        ),
        pins = listOf(RescuePin("divert", ramp(190.0, 450.0, 260.0, 496.67), "Open catch basin")),
        stones = List(22) { row -> List(16) { col ->
            StoneSpawn("stone_${row}_$col", 22.0 + col * 10.0, 25.0 + row * 10.0)
        } }.flatten(),
        king = KingGeometry(334.0, 690.0),
        solution = listOf(TimedPull(180, "divert")),
        title = "1 · Divert",
        lesson = "Tap the gold pin to open the basin below the stream."
    )

    private val receivingWall = listOf(
        WallSegment(200f, 520f, 8f, 65f),
        WallSegment(200f, 645f, 8f, 45f)
    )
    val prepare = divert.copy(
        id = "pullpin_rescue_prototype_002",
        walls = divert.walls + receivingWall,
        pins = divert.pins + RescuePin("prepare", WallSegment(200f, 585f, 8f, 60f), "Open receiving chamber"),
        solution = listOf(TimedPull(90, "prepare"), TimedPull(180, "divert")),
        title = "2 · Prepare, then divert",
        lesson = "Open the lower chamber first, then divert the stream."
    )

    val choose = divert.copy(
        id = "pullpin_rescue_prototype_003",
        walls = divert.walls.filterNot { it.x == 260f && it.y == 496.67f } + listOf(
            WallSegment(260f, 496.67f, 8f, 83.33f),
            WallSegment(260f, 645f, 8f, 45f)
        ),
        pins = divert.pins + RescuePin("spill", WallSegment(260f, 580f, 8f, 65f), "Open route toward king"),
        solution = listOf(TimedPull(180, "divert")),
        title = "3 · Choose the safe route",
        lesson = "One opening catches stones. The other leads back to the king."
    )

    val levels = listOf(divert, prepare, choose)
}
