package com.funkyotc.puzzleverse.hexastack.data

import kotlin.random.Random

/**
 * Offline generator for Hexa Stack levels (score-target win condition).
 *
 * Strategy ("pop-unit construction"):
 * 1. The deck is emitted as **pop units**: `k` chunks (<=5 tiles) of one color followed
 *    by `k` matching chunks. A greedy player places the first `k` chunks on an empty
 *    "anchor" cell, then the next `k` on an empty neighbor. The placement on the neighbor
 *    triggers the engine's primary merge, which transfers the anchor's top run onto the
 *    placed stack — and the combined stack pops (>= [HexaStackLogic.POP_THRESHOLD]).
 * 2. Units are emitted in color phases. Between phases, leftover partial stacks of the
 *    previous color are dissolved as caps on the next color's first units: the cap chunk
 *    is emitted right after that unit's anchor chunks, so the greedy player drops it on
 *    the anchor and the composite pops host-color-then-cap.
 * 3. `scoreTarget` is a fraction of the total poppable tiles x 10. The level only needs
 *    to reach the target, not fully clear — leftover tiles on the board are fine.
 * 4. Every candidate is verified by a greedy auto-player running through the real state
 *    machine (tray, dealing, win/loss). Cheap (ms) and exact for the intended strategy.
 *
 * This object is not used at runtime by the app; it exists so levels can be
 * generated (e.g. from a scratch test) and pasted into HexaStackPregenerated.kt.
 */
object HexaStackGenerator {

    /** Prints replay diagnostics to stdout (offline tooling only). */
    var debug = false

    data class Config(
        val radius: Int,
        val numColors: Int,
        /** Fraction of total poppable tiles (x10) that becomes the score target. */
        val targetFraction: Double = 0.65,
        val attempts: Int = 200
    )

    /**
     * Generates a greedy-verified level with the given id/difficulty, or null if no
     * candidate reached its score target within [Config.attempts] tries.
     */
    fun generate(id: String, difficulty: String, config: Config, seed: Long): HexaStackLevel? {
        repeat(config.attempts) { attempt ->
            val random = Random(seed + attempt)
            val script = buildScript(config, random) ?: return@repeat
            val totalTiles = script.sumOf { it.group.size }
            val target = (totalTiles * 10 * config.targetFraction).toInt().roundTo10()
            val level = HexaStackLevel(id, difficulty, config.radius, target, emptyMap(), script.map { it.group })
            if (greedyWin(level, script)) return level
        }
        return null
    }

    private fun Int.roundTo10(): Int = (this / 10) * 10

    /** Renders a level as a Kotlin snippet for pasting into HexaStackPregenerated.kt. */
    fun renderKotlin(level: HexaStackLevel): String {
        val sb = StringBuilder()
        sb.append("PregeneratedHexaStack(\n")
        sb.append("    id = \"${level.id}\",\n")
        sb.append("    difficulty = \"${level.difficulty}\",\n")
        sb.append("    radius = ${level.radius},\n")
        sb.append("    scoreTarget = ${level.scoreTarget},\n")
        sb.append("    spawnDeck = listOf(\n")
        for (group in level.spawnDeck) {
            sb.append("        listOf(${group.joinToString(", ")}),\n")
        }
        sb.append("    ),\n")
        sb.append("),")
        return sb.toString()
    }

    // ------------------------------------------------------------------ script building

    /** One scripted move: place [group] on a cell of [role] relative to the current unit. */
    private data class Step(val group: List<Int>, val role: Role)
    private enum class Role { ANCHOR, NEIGHBOR, ANY_EMPTY }

    /**
     * Builds the deck plus the intended greedy roles. Colors are processed in phases:
     * each phase emits pop units of one color, and the phase's final leftover partial
     * (if any) becomes a cap on the next phase's first unit.
     *
     * Unit shape: [anchor chunks x k][optional cap chunk][neighbor chunks x k].
     * With k chunks of 4-5 tiles, the anchor grows to k*4..k*5 tiles; the neighbor
     * placement then primary-merges the anchor's run over and the combined stack pops.
     */
    private fun buildScript(config: Config, random: Random): List<Step>? {
        val steps = mutableListOf<Step>()
        // Total tiles per color, randomized a bit so levels differ.
        val colorBudgets = IntArray(config.numColors) { random.nextInt(20, 31) }

        var pendingCap: List<Int>? = null
        for (color in 0 until config.numColors) {
            var budget = colorBudgets[color]
            var firstUnit = true
            while (budget >= HexaStackLogic.POP_THRESHOLD) {
                // Split this unit's tiles into anchor and neighbor halves.
                val unitTiles = minOf(budget, random.nextInt(10, 15))
                val anchorTiles = unitTiles / 2
                val neighborTiles = unitTiles - anchorTiles
                if (anchorTiles < 5 || neighborTiles < 5) break

                appendChunks(steps, anchorTiles, color, Role.ANCHOR, random)
                // A pending cap from the previous color rides on this unit's anchor
                // (chunked like everything else so no tray group exceeds 5 tiles).
                if (firstUnit && pendingCap != null) {
                    appendChunks(steps, pendingCap.size, pendingCap.first(), Role.ANCHOR, random)
                    pendingCap = null
                }
                appendChunks(steps, neighborTiles, color, Role.NEIGHBOR, random)

                budget -= unitTiles
                firstUnit = false
            }
            // Leftover partial of this color (if any) becomes a cap on the next color.
            pendingCap = if (budget > 0) List(budget) { color } else null
        }
        // A dangling cap of the LAST color has no host — drop it as filler chunks.
        pendingCap?.let { appendChunks(steps, it.size, it.first(), Role.ANY_EMPTY, random) }

        return steps
    }

    /** Appends [totalTiles] of [color] as <=5-deep chunks with the given [role]. */
    private fun appendChunks(
        steps: MutableList<Step>,
        totalTiles: Int,
        color: Int,
        role: Role,
        random: Random
    ) {
        var remaining = totalTiles
        while (remaining > 0) {
            val take = minOf(5, remaining)
            steps.add(Step(List(take) { color }, role))
            remaining -= take
        }
    }

    // ------------------------------------------------------------------ greedy replay

    /**
     * Plays the level with the intended strategy: each scripted step is placed on an
     * empty cell chosen by its role — ANCHOR: any empty cell whose neighbors are all
     * empty (no accidental merges); NEIGHBOR: an empty cell adjacent to the tallest
     * stack whose top color matches; ANY_EMPTY: any empty cell. Returns true only if
     * the replay reaches the won state (score target).
     */
    private fun greedyWin(level: HexaStackLevel, script: List<Step>): Boolean {
        var state = HexaStackLogic.initialState(level)
        for ((idx, step) in script.withIndex()) {
            if (state.isWon) return true
            val slot = state.tray.indexOfFirst { it == step.group }
            if (slot == -1) {
                if (debug) println("greedy: group $idx ${step.group} not in tray ${state.tray}")
                return false
            }
            val coord = pickCell(state, step) ?: run {
                if (debug) println("greedy: no cell for role ${step.role} at move $idx; cells=${state.cells.size}")
                return false
            }
            state = HexaStackLogic.placeAndResolve(state, slot, coord) ?: run {
                if (debug) println("greedy: move $idx rejected (${step.group} -> $coord)")
                return false
            }
            if (state.isGameOver) {
                if (debug) println("greedy: game over at move $idx; score=${state.score}/${state.level.scoreTarget}")
                return false
            }
        }
        if (debug && !state.isWon) println("greedy: deck out; score=${state.score}/${state.level.scoreTarget}")
        return state.isWon
    }

    private fun pickCell(state: HexaStackState, step: Step): AxialCoord? {
        val empties = state.level.cells.filter { it !in state.cells }
        return when (step.role) {
            Role.ANCHOR -> empties.firstOrNull { c -> c.neighbors().all { it !in state.cells } }
                ?: empties.firstOrNull()
            Role.NEIGHBOR -> {
                val top = step.group.last()
                // Tallest matching-top stack with an empty neighbor.
                state.cells.entries
                    .filter { (_, stack) -> stack.lastOrNull() == top }
                    .maxByOrNull { (_, stack) -> stack.size }
                    ?.let { (c, _) -> c.neighbors().firstOrNull { it in empties } }
                    ?: empties.firstOrNull()
            }
            Role.ANY_EMPTY -> empties.firstOrNull()
        }
    }

    private fun Config.cells(): Set<AxialCoord> = AxialCoord.hexGrid(radius)
}
