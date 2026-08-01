package com.funkyotc.puzzleverse.pullpin.data

import kotlin.random.Random

private val COMMON_WALLS = listOf(
    WallSegment(0f, 680f, 400f, 20f),   // floor
    WallSegment(0f, 0f, 12f, 700f),     // left
    WallSegment(388f, 0f, 12f, 700f),   // right
    WallSegment(0f, 0f, 400f, 12f)      // top
)

private fun makeLevel(id: String, difficulty: String, idx: Int): PullPinLevel {
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

    val cupCount = when (difficulty) {
        "Easy" -> 2
        "Medium" -> 3
        "Hard" -> 4
        else -> 5
    }
    val hasGrey = difficulty == "Hard" || difficulty == "Expert" || idx % 2 == 1

    for (i in 0 until cupCount) {
        val frac = (i + 0.5f) / cupCount.toFloat()
        val cx = left + interior * frac
        val color = (i % 8) + 1

        // Dividers between cups
        if (i < cupCount - 1) {
            val nextFrac = (i + 1.5f) / cupCount.toFloat()
            val nextCx = left + interior * nextFrac
            val midX = (cx + nextCx) / 2f
            val dividerH = if (idx % 3 == 0) 380f else 320f
            walls.add(WallSegment(midX - 6f, 700f - dividerH, 12f, dividerH - 20f))
        }

        cups.add(CupData("cup_$i", cx, cupY, cupRadius, color))
    }

    // Layout templates based on level index and difficulty
    when (idx % 4) {
        0 -> { // Standard vertical stack with funnel ramps
            walls.add(WallSegment(30f, 250f, 100f, 12f)) // Left ramp
            walls.add(WallSegment(270f, 250f, 100f, 12f)) // Right ramp
            for (i in 0 until cupCount) {
                val frac = (i + 0.5f) / cupCount.toFloat()
                val cx = left + interior * frac
                val color = (i % 8) + 1
                balls.add(BallSpawn("ball_$i", cx, 580f, color, 16f))
                val pullDx = if (i % 2 == 0) 1f else -1f
                pins.add(PinData("pin_$i", cx - 45f, 610f, 90f, 10f, pullDx, 0f))
                if (hasGrey) {
                    balls.add(BallSpawn("ball_${i}_g", cx, 440f, 0, 16f))
                    pins.add(PinData("pin_${i}_g", cx - 45f, 480f, 90f, 10f, -pullDx, 0f))
                }
            }
        }
        1 -> { // Central mixing chamber layout
            val midX = 200f
            walls.add(WallSegment(60f, 200f, 120f, 12f))
            walls.add(WallSegment(220f, 200f, 120f, 12f))
            pins.add(PinData("pin_center_mix", midX - 60f, 320f, 120f, 12f, 1f, 0f))
            for (i in 0 until cupCount) {
                val frac = (i + 0.5f) / cupCount.toFloat()
                val cx = left + interior * frac
                val color = (i % 8) + 1
                balls.add(BallSpawn("ball_$i", cx, 520f, color, 16f))
                pins.add(PinData("pin_$i", cx - 40f, 560f, 80f, 10f, if (i % 2 == 0) -1f else 1f, 0f))
                if (hasGrey) {
                    balls.add(BallSpawn("ball_${i}_g", midX + (i - cupCount / 2f) * 30f, 150f, 0, 16f))
                }
            }
        }
        2 -> { // Multi-tier staggered pins
            for (i in 0 until cupCount) {
                val frac = (i + 0.5f) / cupCount.toFloat()
                val cx = left + interior * frac
                val color = (i % 8) + 1
                balls.add(BallSpawn("ball_$i", cx, 560f - i * 20f, color, 16f))
                pins.add(PinData("pin_$i", cx - 45f, 600f - i * 20f, 90f, 10f, 1f, 0f))
                if (hasGrey) {
                    balls.add(BallSpawn("ball_${i}_g", cx, 360f - i * 20f, 0, 16f))
                    pins.add(PinData("pin_${i}_g", cx - 45f, 400f - i * 20f, 90f, 10f, -1f, 0f))
                }
            }
        }
        else -> { // Cross-chamber funnel layout
            walls.add(WallSegment(40f, 180f, 140f, 12f))
            walls.add(WallSegment(220f, 300f, 140f, 12f))
            for (i in 0 until cupCount) {
                val frac = (i + 0.5f) / cupCount.toFloat()
                val cx = left + interior * frac
                val color = (i % 8) + 1
                balls.add(BallSpawn("ball_$i", cx, 540f, color, 16f))
                pins.add(PinData("pin_$i", cx - 45f, 580f, 90f, 10f, if (i % 2 == 0) 1f else -1f, 0f))
                if (hasGrey) {
                    balls.add(BallSpawn("ball_${i}_g", cx, 380f, 0, 16f))
                    pins.add(PinData("pin_${i}_g", cx - 45f, 420f, 90f, 10f, if (i % 2 == 0) -1f else 1f, 0f))
                }
            }
        }
    }

    return PullPinLevel(id, difficulty, walls, cups, pins, balls)
}

private fun levelsFor(difficulty: String, count: Int): List<PullPinLevel> {
    return List(count) { idx ->
        val id = "pullpin_${difficulty.lowercase()}_${(idx + 1).toString().padStart(3, '0')}"
        makeLevel(id, difficulty, idx)
    }
}

object PullPinPregenerated {
    val ALL_LEVELS: List<PullPinLevel> = buildList {
        addAll(levelsFor("Easy", 12))
        addAll(levelsFor("Medium", 12))
        addAll(levelsFor("Hard", 12))
        addAll(levelsFor("Expert", 12))
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PullPinLevel>> = ALL_LEVELS.groupBy { it.difficulty }
}
