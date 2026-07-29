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
     * Resolve the board after a placement at [placedCoord]: continuously merges connected components
     * of same-color top tiles and pops qualifying stacks until stable.
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

            // 1. Transfer phase: find next connected component of matching top colors to merge
            val componentToMerge = findNextComponentToMerge(cells, activeCoords)
            if (componentToMerge != null) {
                val (component, targetCoord) = componentToMerge
                val targetStack = cells[targetCoord]
                val topColor = targetStack?.lastOrNull()
                if (targetStack != null && topColor != null) {
                    val donors = component.filter { it != targetCoord }
                    for (donorCoord in donors) {
                        val donorStack = cells[donorCoord] ?: continue
                        if (donorStack.lastOrNull() != topColor) continue
                        val run = topRun(donorStack)
                        val tiles = donorStack.takeLast(run)
                        repeat(run) { donorStack.removeAt(donorStack.size - 1) }
                        targetStack.addAll(tiles)
                        if (donorStack.isEmpty()) cells.remove(donorCoord)
                        steps.add(AnimStep.Transfer(donorCoord, targetCoord, tiles))
                    }

                    activeCoords.clear()
                    activeCoords.add(targetCoord)
                    activity = true
                }
            }

            // 2. Pop phase: check for stacks with >= POP_THRESHOLD same-color top tiles
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

            if (!activity) break
        }

        return PlacementResult(
            cells = cells,
            poppedTiles = totalPopped,
            poppingCoords = poppingCoords,
            steps = steps
        )
    }

    /** Finds all cells connected to [start] that share the same top color as [start]. */
    fun findConnectedComponent(
        cells: Map<AxialCoord, List<Int>>,
        start: AxialCoord
    ): Set<AxialCoord> {
        val stack = cells[start] ?: return emptySet()
        val topColor = stack.lastOrNull() ?: return emptySet()
        val component = mutableSetOf<AxialCoord>()
        val queue = ArrayDeque<AxialCoord>()

        queue.add(start)
        component.add(start)

        while (queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            for (n in curr.neighbors()) {
                if (n !in component) {
                    val nStack = cells[n] ?: continue
                    if (nStack.lastOrNull() == topColor) {
                        component.add(n)
                        queue.add(n)
                    }
                }
            }
        }
        return component
    }

    private fun findNextComponentToMerge(
        cells: Map<AxialCoord, List<Int>>,
        activeCoords: List<AxialCoord>
    ): Pair<Set<AxialCoord>, AxialCoord>? {
        // Priority 1: Component containing activeCoords if size > 1
        for (active in activeCoords) {
            if (cells.containsKey(active)) {
                val comp = findConnectedComponent(cells, active)
                if (comp.size > 1) {
                    val target = comp.maxByOrNull { c ->
                        val sSize = cells[c]?.size ?: 0
                        if (c == active) sSize + 100000 else sSize
                    } ?: active
                    return comp to target
                }
            }
        }

        // Priority 2: Any connected component on board with size > 1
        for (coord in cells.keys) {
            val comp = findConnectedComponent(cells, coord)
            if (comp.size > 1) {
                val target = comp.maxByOrNull { cells[it]?.size ?: 0 } ?: coord
                return comp to target
            }
        }

        return null
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
