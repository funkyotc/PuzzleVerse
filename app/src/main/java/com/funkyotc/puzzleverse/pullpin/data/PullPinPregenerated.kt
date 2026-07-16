package com.funkyotc.puzzleverse.pullpin.data

import kotlin.random.Random

private val COMMON_WALLS = listOf(
    WallSegment(0f, 680f, 400f, 20f),   // floor
    WallSegment(0f, 0f, 12f, 700f),     // left
    WallSegment(388f, 0f, 12f, 700f),   // right
    WallSegment(0f, 0f, 400f, 12f)      // top
)

private fun makeLevel(id: String, difficulty: String, cupCount: Int, extraGrey: Boolean): PullPinLevel {
    val rng = Random(id.hashCode().toLong())
    val walls = COMMON_WALLS.toMutableList()
    val cups = mutableListOf<CupData>()
    val pins = mutableListOf<PinData>()
    val balls = mutableListOf<BallSpawn>()

    val left = 12f
    val right = 388f
    val interior = right - left
    val cupY = 658f
    val cupRadius = 22f

    for (i in 0 until cupCount) {
        val frac = (i + 0.5f) / cupCount.toFloat()
        val cx = left + interior * frac
        val color = (i % 8) + 1

        // divider between this cup and the next
        if (i < cupCount - 1) {
            val nextFrac = (i + 1.5f) / cupCount.toFloat()
            val nextCx = left + interior * nextFrac
            val midX = (cx + nextCx) / 2f
            walls.add(WallSegment(midX - 6f, 300f, 12f, 355f))
        }

        cups.add(CupData("cup_$i", cx, cupY, cupRadius, color))

        // colored ball directly above its cup, resting on a holding pin
        balls.add(BallSpawn("ball_$i", cx, 600f, color, 16f))
        val pullDx = if (i % 2 == 0) 1f else -1f
        pins.add(PinData("pin_$i", cx - 40f, 620f, 80f, 10f, pullDx, 0f, false, false))

        if (extraGrey) {
            // a grey ball above, held by a second pin (colored on contact)
            balls.add(BallSpawn("ball_${i}_g", cx, 500f, 0, 16f))
            pins.add(PinData("pin_${i}_g", cx - 40f, 540f, 80f, 10f, pullDx, 0f, false, false))
        }
    }

    return PullPinLevel(id, difficulty, walls, cups, pins, balls)
}

private fun levelsFor(difficulty: String, count: Int, cupCount: Int, extraGrey: Boolean): List<PullPinLevel> {
    return List(count) { idx ->
        val id = "pullpin_${difficulty.lowercase()}_${(idx + 1).toString().padStart(3, '0')}"
        makeLevel(id, difficulty, cupCount, extraGrey)
    }
}

object PullPinPregenerated {
    val ALL_LEVELS: List<PullPinLevel> = buildList {
        addAll(levelsFor("Easy", 12, 2, false))
        addAll(levelsFor("Medium", 12, 3, false))
        addAll(levelsFor("Hard", 12, 4, true))
        addAll(levelsFor("Expert", 12, 5, true))
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PullPinLevel>> = ALL_LEVELS.groupBy { it.difficulty }
}
