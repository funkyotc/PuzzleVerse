package com.funkyotc.puzzleverse.pullpin.rescue

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

/** Versioned production IDs never inherit completion from the retired ball campaign. */
object RescueCampaign {
    const val VERSION = 1
    val levels: List<RescueLevel> = RescuePrototype.levels.mapIndexed { index, prototype ->
        prototype.copy(id = "pullpin_rescue_v1_${(index + 1).toString().padStart(3, '0')}")
    }
    val puzzles: List<RescuePuzzle> = levels.mapIndexed { index, level ->
        RescuePuzzle(level, index + 1)
    }
    val byDifficulty: Map<String, List<BrowseablePuzzle>> = mapOf("Rescue" to puzzles)

    fun level(id: String?): RescueLevel? = levels.firstOrNull { it.id == id }
    fun dailyLevel(epochDay: Long): RescueLevel = levels[Math.floorMod(epochDay, levels.size.toLong()).toInt()]
}

data class RescuePuzzle(val level: RescueLevel, val number: Int) : BrowseablePuzzle {
    override val id: String get() = level.id
    override val difficulty: String = "Rescue"
    override val label: String get() = "Level $number"
    override val subtitle: String get() = level.title.substringAfter('·').trim()
}
