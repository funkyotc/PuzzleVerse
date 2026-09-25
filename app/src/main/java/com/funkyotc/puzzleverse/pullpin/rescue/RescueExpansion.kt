package com.funkyotc.puzzleverse.pullpin.rescue

import com.funkyotc.puzzleverse.pullpin.data.WallSegment

/** Later mechanics are authored separately from the accepted three-board prototype. */
object RescueExpansion {
    private val original = RescuePrototype.divert
    private val raisedFloor = listOf(
        WallSegment(266f, 650f, 99f, 8f)
    )
    private val drain = RescuePin("drain", WallSegment(365f, 650f, 25f, 8f), "Drain stones beside the king")
    val drainage = original.copy(
        walls = original.walls + raisedFloor,
        pins = original.pins + drain,
        king = KingGeometry(356.0, 650.0),
        solution = listOf(TimedPull(180, "divert"), TimedPull(450, "drain")),
        title = "4 · Drain the pile",
        lesson = "Open the lower gold floor to drain stones before the pile reaches the king."
    )
    val clear = drainage.copy(
        objective = RescueObjective.CLEAR,
        solution = drainage.solution,
        title = "5 · Free the king",
        lesson = "Drain the stones and leave the king's whole body clear when the load settles."
    )
    private val exitGate = RescuePin("exit", WallSegment(365f, 620f, 8f, 70f), "Open the exit gate")
    val escape = original.copy(
        pins = original.pins + exitGate,
        king = KingGeometry(332.0, 690.0),
        objective = RescueObjective.ESCAPE,
        exitX = 378.0,
        solution = listOf(TimedPull(180, "divert"), TimedPull(360, "exit")),
        title = "6 · Reach the exit",
        lesson = "Divert the stream, then open the gate. The king must walk into the blue exit."
    )
    val levels = listOf(drainage, clear, escape)
}
