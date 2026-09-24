package com.funkyotc.puzzleverse.pullpin.data

/** Authored chamber recipes. Stable IDs preserve existing browser links. */
object PullPinPregenerated {
    private val names = listOf("First Contact", "Double Drop", "Grey Matter", "Downhill",
        "Split Decision", "Chain Reaction", "Danger Above", "Staggered Falls", "Reservoir",
        "Lockstep", "Three Rivers", "The Vault")
    val ALL_LEVELS = listOf("Easy", "Medium", "Hard", "Expert").flatMapIndexed { tier, difficulty ->
        List(12) { index -> makeLevel(difficulty, tier, index) }
    }
    val PUZZLES_BY_DIFFICULTY = ALL_LEVELS.groupBy { it.difficulty }

    private fun makeLevel(difficulty: String, tier: Int, index: Int): PullPinLevel {
        val lanes = when { index == 0 && tier == 0 -> 1; index >= 10 || tier >= 2 -> 3; else -> 2 }
        val greyCount = 1 + (if (tier >= 1 && index % 3 == 2) 1 else 0) + (if (tier == 3) 1 else 0)
        val bombs = index >= 6 || tier >= 2
        val locks = index == 5 || index == 9 || index == 11 || tier == 3
        val reservoir = index == 8 || index == 11
        val slopes = index >= 3 || tier > 0
        val walls = mutableListOf(WallSegment(0f, 680f, 400f, 20f),
            WallSegment(0f, 0f, 10f, 700f), WallSegment(390f, 0f, 10f, 700f))
        val pins = mutableListOf<PinData>()
        val balls = mutableListOf<BallSpawn>()
        val cups = mutableListOf<CupData>()
        val solution = mutableListOf<String>()
        val laneWidth = 380f / lanes
        for (lane in 0 until lanes) {
            val left = 10f + lane * laneWidth
            val center = left + laneWidth / 2f
            val shift = if (slopes) (if ((index + lane) % 2 == 0) -1 else 1) * laneWidth * .18f else 0f
            val chamber = center + shift
            val color = (lane + tier + index) % 8 + 1
            val stagger = if (index % 4 == 3) lane * 32f else 0f
            if (lane > 0) walls += WallSegment(left - 4f, 0f, 8f, 680f)
            // Narrow mixing throats force contact, then open into the routing bay.
            walls += WallSegment(chamber - 28f, 30f, 8f, 370f + stagger)
            walls += WallSegment(chamber + 20f, 30f, 8f, 370f + stagger)
            balls += BallSpawn("color_$lane", chamber, 340f + stagger, color, 12f)
            repeat(greyCount) { n -> balls += BallSpawn("grey_${lane}_$n", chamber, 205f + stagger - n * 26f, 0, 12f) }
            pins += PinData("mix_$lane", chamber - 24f, 222f + stagger, 48f, 10f, -1f, label = "Mix")
            pins += PinData("exit_$lane", chamber - 24f, 360f + stagger, 48f, 10f, 1f,
                unlockAfter = if (locks) lane * (greyCount + 1 + if (reservoir) 2 else 0) else 0, label = "Exit")
            solution += "mix_$lane"
            solution += "exit_$lane"
            if (reservoir) {
                balls += BallSpawn("reservoir_${lane}_0", center, 577f, 0, 12f)
                balls += BallSpawn("reservoir_${lane}_1", center, 551f, 0, 12f)
                pins += PinData("drain_$lane", center - 32f, 594f, 64f, 10f, -1f, label = "Drain")
                solution += "drain_$lane"
            }
            if (bombs && (lane == index % lanes || tier == 3)) {
                balls += BallSpawn("bomb_$lane", chamber, 65f, 0, 12f, isBomb = true)
                pins += PinData("hazard_$lane", chamber - 24f, 86f, 48f, 10f, 1f, label = "Danger")
            }
            // Real angled surfaces, with a 56-unit central outlet for 24-unit balls.
            if (slopes) {
                val run = laneWidth / 2f - 28f
                val angle = 32f
                val length = run / kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                val rise = run * kotlin.math.tan(Math.toRadians(angle.toDouble())).toFloat()
                walls += WallSegment(left + run / 2f - length / 2f, 530f - rise / 2f, length, 10f, angle)
                walls += WallSegment(center + 28f + run / 2f - length / 2f, 530f - rise / 2f, length, 10f, -angle)
            }
            if (slopes) {
                walls += WallSegment(center - 36f, 535f, 8f, 145f)
                walls += WallSegment(center + 28f, 535f, 8f, 145f)
            }
            cups += CupData("cup_$lane", center, 650f, 36f, color)
        }
        return PullPinLevel("pullpin_${difficulty.lowercase()}_${(index + 1).toString().padStart(3, '0')}",
            difficulty, walls, cups, pins, balls,
            title = names[index] + if (tier == 0) "" else " ${tier + 1}",
            lesson = when {
                reservoir -> "Two mixing stages! Color the lower reservoir before pulling DRAIN. Keep bombs sealed."
                locks && bombs -> "Rescue each chamber to unlock the next. Leave bombs sealed."
                bombs -> "Some pins should stay in. Keep bombs away from your balls."
                locks -> "Saved balls unlock the next exit. Work from left to right."
                slopes -> "Mix in the upper chamber, then use the ramps to reach the cups."
                else -> "Pull the mixing pin. Wait for every ball to gain color, then open the exit."
            }, solution = solution)
    }
}
