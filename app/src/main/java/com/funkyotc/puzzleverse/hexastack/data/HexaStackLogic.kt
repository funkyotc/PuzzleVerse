package com.funkyotc.puzzleverse.hexastack.data

/**
 * Pure Hexa Stack game engine. No Android dependencies — fully unit-testable.
 *
 * Rules (per docs/hexastack-plan.md):
 * 1. A tray group may only be placed on an empty cell.
 * 2. Primary merge: neighbors whose top color matches the newly placed stack's top
 *    transfer their contiguous same-color top run onto the new stack.
 * 3. Pop: any stack with >= [POP_THRESHOLD] contiguous same-color top tiles pops that run.
 * 4. Cascade: after transfers/pops change top colors, adjacent stacks with equal top colors
 *    merge onto the highest stack; pops re-checked; loop until stable.
 * 5. Win: score reaches the level's [HexaStackLevel.scoreTarget]. Loss: the spawn deck is
 *    exhausted with all tray stacks placed and the target not met, or no empty cell exists
 *    for any remaining tray stack.
 */
object HexaStackLogic {

    const val POP_THRESHOLD = 10
    const val TRAY_SLOTS = 3

    /** A discrete animation step in the resolution process. */
    sealed class AnimStep {
        data class Transfer(val from: AxialCoord, val to: AxialCoord, val tiles: List<Int>) : AnimStep()
        data class Pop(val coord: AxialCoord, val count: Int, val color: Int) : AnimStep()
    }

    /** Result of a fully resolved placement with step-by-step animation instructions. */
    data class PlacementResult(
        val cells: Map<AxialCoord, List<Int>>,
        val poppedTiles: Int,
        val poppingCoords: Set<AxialCoord>,
        val steps: List<AnimStep> = emptyList()
    )

    fun initialState(level: HexaStackLevel): HexaStackState {
        val tray = MutableList<List<Int>?>(TRAY_SLOTS) { null }
        var deckIndex = 0
        for (i in 0 until TRAY_SLOTS) {
            if (deckIndex < level.spawnDeck.size) {
                tray[i] = level.spawnDeck[deckIndex]
                deckIndex++
            }
        }
        return HexaStackState(
            level = level,
            cells = level.initialStacks.mapValues { it.value.toList() },
            tray = tray,
            deckIndex = deckIndex
        )
    }

    /** True if [coord] is a playable, currently empty cell. */
    fun canPlace(state: HexaStackState, coord: AxialCoord): Boolean =
        coord in state.level.cells && coord !in state.cells

    /** True if any tray stack can be placed somewhere on the board. */
    fun hasValidPlacement(state: HexaStackState): Boolean {
        if (state.tray.all { it == null }) return false
        return state.level.cells.any { it !in state.cells }
    }

    /**
     * Place [traySlot] onto [coord] and fully resolve merges, pops and cascades.
     * Returns null if the move is illegal. Does not deal the next tray hand —
     * call [dealIfNeeded] afterwards.
     */
    fun placeAndResolve(state: HexaStackState, traySlot: Int, coord: AxialCoord): HexaStackState? {
        if (state.isWon || state.isGameOver) return null
        if (traySlot !in 0 until TRAY_SLOTS) return null
        val group = state.tray[traySlot] ?: return null
        if (group.isEmpty() || !canPlace(state, coord)) return null

        val cells = state.cells.mapValues { it.value.toMutableList() }.toMutableMap()
        cells[coord] = group.toMutableList()

        val result = resolve(cells, placedCoord = coord)

        val newTray = state.tray.toMutableList()
        newTray[traySlot] = null

        val resolved = state.copy(
            cells = result.cells.mapValues { it.value.toList() },
            tray = newTray,
            score = state.score + result.poppedTiles * 10,
            moves = state.moves + 1,
            poppingCoords = result.poppingCoords,
            lastPoppedTiles = result.poppedTiles
        )
        return dealIfNeeded(resolved).let { finish(it) }
    }

    /** Deal the next 3 deck groups into the tray once all slots are empty. */
    fun dealIfNeeded(state: HexaStackState): HexaStackState {
        if (state.tray.any { it != null }) return state
        var deckIndex = state.deckIndex
        if (deckIndex >= state.level.spawnDeck.size) return state
        val tray = MutableList<List<Int>?>(TRAY_SLOTS) { null }
        for (i in 0 until TRAY_SLOTS) {
            if (deckIndex < state.level.spawnDeck.size) {
                tray[i] = state.level.spawnDeck[deckIndex]
                deckIndex++
            }
        }
        return state.copy(tray = tray, deckIndex = deckIndex)
    }

    /** Evaluate terminal conditions: win on reaching the score target, lose when the deck runs dry or no placement remains. */
    fun finish(state: HexaStackState): HexaStackState {
        if (state.score >= state.level.scoreTarget) {
            return state.copy(isWon = true, isGameOver = false)
        }
        val trayEmpty = state.tray.all { it == null }
        val deckExhausted = state.deckIndex >= state.level.spawnDeck.size
        if (trayEmpty && deckExhausted) {
            return state.copy(isWon = false, isGameOver = true)
        }
        val noMoves = !trayEmpty && !hasValidPlacement(state)
        return state.copy(isGameOver = noMoves, isWon = false)
    }

    /**
     * Resolve the board after a placement at [placedCoord]: continuously cycles pops and
     * matching-color transfers until stable, recording step-by-step animation instructions.
     */
    fun resolve(
        cells: MutableMap<AxialCoord, MutableList<Int>>,
        placedCoord: AxialCoord?
    ): PlacementResult {
        var totalPopped = 0
        val poppingCoords = mutableSetOf<AxialCoord>()
        val steps = mutableListOf<AnimStep>()
        val activeCoords = mutableListOf<AxialCoord>()
        if (placedCoord != null) activeCoords.add(placedCoord)

        while (true) {
            var activity = false

            // 1. Pop phase: check for stacks with >= POP_THRESHOLD same-color top tiles
            val coordsToCheck = cells.keys.toList()
            for (c in coordsToCheck) {
                val stack = cells[c] ?: continue
                val run = topRun(stack)
                if (run >= POP_THRESHOLD) {
                    val color = stack.last()
                    repeat(run) { stack.removeAt(stack.size - 1) }
                    totalPopped += run
                    poppingCoords.add(c)
                    steps.add(AnimStep.Pop(c, run, color))
                    if (stack.isEmpty()) cells.remove(c)
                    activity = true
                }
            }

            // 2. Transfer phase: merge adjacent stacks with matching top color
            val candidate = findBestMergeCandidate(cells, activeCoords)
            if (candidate != null) {
                val (fromCoord, toCoord) = candidate
                val fromStack = cells[fromCoord] ?: continue
                val toStack = cells[toCoord] ?: continue
                val run = topRun(fromStack)
                val tiles = fromStack.takeLast(run)
                repeat(run) { fromStack.removeAt(fromStack.size - 1) }
                toStack.addAll(tiles)
                if (fromStack.isEmpty()) cells.remove(fromCoord)

                steps.add(AnimStep.Transfer(fromCoord, toCoord, tiles))
                activeCoords.clear()
                activeCoords.add(toCoord)
                activity = true
            }

            if (!activity) break
        }

        return PlacementResult(
            cells = cells,
            poppedTiles = totalPopped,
            poppingCoords = poppingCoords,
            steps = steps
        )
    }

    private fun findBestMergeCandidate(
        cells: Map<AxialCoord, List<Int>>,
        activeCoords: List<AxialCoord>
    ): Pair<AxialCoord, AxialCoord>? {
        // Priority 1: Merges involving activeCoords (chain reaction)
        for (active in activeCoords) {
            val stack = cells[active] ?: continue
            val topColor = stack.lastOrNull() ?: continue
            for (n in active.neighbors()) {
                val nStack = cells[n] ?: continue
                if (nStack.lastOrNull() == topColor) {
                    return if (nStack.size > stack.size) active to n else n to active
                }
            }
        }

        // Priority 2: Any adjacent matching stacks on the board (taller receives)
        data class Candidate(val from: AxialCoord, val to: AxialCoord, val height: Int)
        var best: Candidate? = null
        for ((coord, stack) in cells) {
            val top = stack.lastOrNull() ?: continue
            for (n in coord.neighbors()) {
                val nStack = cells[n] ?: continue
                if (nStack.lastOrNull() != top) continue
                val (from, to) = if (stack.size <= nStack.size) coord to n else n to coord
                val height = maxOf(stack.size, nStack.size)
                if (best == null || height > best.height) {
                    best = Candidate(from, to, height)
                }
            }
        }
        return best?.let { it.from to it.to }
    }

    /** Length of the contiguous same-color run at the top of [stack]. */
    fun topRun(stack: List<Int>): Int {
        if (stack.isEmpty()) return 0
        val top = stack.last()
        var run = 0
        for (i in stack.indices.reversed()) {
            if (stack[i] == top) run++ else break
        }
        return run
    }

    /** A discrete move of a contiguous run of tiles from one coord to another (legacy compatibility). */
    data class Move(val from: AxialCoord, val to: AxialCoord, val tiles: List<Int>)

    data class PlacementResultWithMoves(
        val cells: Map<AxialCoord, List<Int>>,
        val poppedTiles: Int,
        val poppingCoords: Set<AxialCoord>,
        val moves: List<Move>
    )

    fun placeAndResolveWithMoves(state: HexaStackState, traySlot: Int, coord: AxialCoord): Pair<HexaStackState, List<Move>>? {
        val result = placeAndResolveWithSteps(state, traySlot, coord) ?: return null
        val legacyMoves = result.second.filterIsInstance<AnimStep.Transfer>().map { Move(it.from, it.to, it.tiles) }
        return Pair(result.first, legacyMoves)
    }

    fun placeAndResolveWithSteps(state: HexaStackState, traySlot: Int, coord: AxialCoord): Pair<HexaStackState, List<AnimStep>>? {
        if (state.isWon || state.isGameOver) return null
        if (traySlot !in 0 until TRAY_SLOTS) return null
        val group = state.tray[traySlot] ?: return null
        if (group.isEmpty() || !canPlace(state, coord)) return null

        val cells = state.cells.mapValues { it.value.toMutableList() }.toMutableMap()
        cells[coord] = group.toMutableList()

        val result = resolve(cells, placedCoord = coord)

        val newTray = state.tray.toMutableList()
        newTray[traySlot] = null

        val resolved = state.copy(
            cells = result.cells.mapValues { it.value.toList() },
            tray = newTray,
            score = state.score + result.poppedTiles * 10,
            moves = state.moves + 1,
            poppingCoords = result.poppingCoords,
            lastPoppedTiles = result.poppedTiles
        )
        return Pair(dealIfNeeded(resolved).let { finish(it) }, result.steps)
    }
}
