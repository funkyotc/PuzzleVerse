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

    /** Result of a fully resolved placement. */
    data class PlacementResult(
        val cells: Map<AxialCoord, List<Int>>,
        val poppedTiles: Int,
        val poppingCoords: Set<AxialCoord>
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
     * Resolve the board after a placement at [placedCoord]: primary merge onto the placed
     * stack, then loop { pop all qualifying stacks; cascade-merge equal-top neighbors onto
     * the highest stack } until stable.
     */
    fun resolve(
        cells: MutableMap<AxialCoord, MutableList<Int>>,
        placedCoord: AxialCoord?
    ): PlacementResult {
        var totalPopped = 0
        val poppingCoords = mutableSetOf<AxialCoord>()

        if (placedCoord != null) {
            primaryMerge(cells, placedCoord)
        }

        while (true) {
            // Pop phase: every stack with >= POP_THRESHOLD contiguous same-color top tiles.
            var poppedThisRound = 0
            val toCheck = cells.keys.toList()
            for (c in toCheck) {
                val stack = cells[c] ?: continue
                val run = topRun(stack)
                if (run >= POP_THRESHOLD) {
                    repeat(run) { stack.removeAt(stack.size - 1) }
                    poppedThisRound += run
                    poppingCoords.add(c)
                    if (stack.isEmpty()) cells.remove(c)
                }
            }
            totalPopped += poppedThisRound

            // Cascade phase: merge equal-top neighbors onto the highest stack, one pair at a time.
            var merged: Boolean
            do {
                merged = cascadeStep(cells)
            } while (merged)

            if (poppedThisRound == 0) break
        }

        return PlacementResult(
            cells = cells,
            poppedTiles = totalPopped,
            poppingCoords = poppingCoords
        )
    }

    /** Transfer matching contiguous top tiles from neighbors onto the stack at [target]. */
    private fun primaryMerge(cells: MutableMap<AxialCoord, MutableList<Int>>, target: AxialCoord) {
        val targetStack = cells[target] ?: return
        while (true) {
            val topColor = targetStack.lastOrNull() ?: return
            val donor = target.neighbors()
                .mapNotNull { n -> cells[n]?.let { n to it } }
                .firstOrNull { (_, stack) -> stack.lastOrNull() == topColor }
                ?: return
            val (donorCoord, donorStack) = donor
            val run = topRun(donorStack)
            val tiles = donorStack.takeLast(run)
            repeat(run) { donorStack.removeAt(donorStack.size - 1) }
            targetStack.addAll(tiles)
            if (donorStack.isEmpty()) cells.remove(donorCoord)
        }
    }

    /**
     * One cascade step: find the adjacent stack pair with equal top colors where the taller
     * stack is tallest overall, and move the shorter stack's contiguous top run onto it.
     * Returns true if a merge happened.
     */
    private fun cascadeStep(cells: MutableMap<AxialCoord, MutableList<Int>>): Boolean {
        data class Candidate(val from: AxialCoord, val to: AxialCoord, val height: Int)
        var best: Candidate? = null
        for ((coord, stack) in cells) {
            val top = stack.lastOrNull() ?: continue
            for (n in coord.neighbors()) {
                val nStack = cells[n] ?: continue
                if (nStack.lastOrNull() != top) continue
                // Merge the shorter into the taller; ties merge coord into n deterministically.
                val (from, to) = if (stack.size <= nStack.size) coord to n else n to coord
                val height = maxOf(stack.size, nStack.size)
                if (best == null || height > best!!.height) {
                    best = Candidate(from, to, height)
                }
            }
        }
        val c = best ?: return false
        val fromStack = cells[c.from] ?: return false
        val toStack = cells[c.to] ?: return false
        val run = topRun(fromStack)
        val tiles = fromStack.takeLast(run)
        repeat(run) { fromStack.removeAt(fromStack.size - 1) }
        toStack.addAll(tiles)
        if (fromStack.isEmpty()) cells.remove(c.from)
        return true
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

    /** A discrete move of a contiguous run of tiles from one coord to another. */
    data class Move(val from: AxialCoord, val to: AxialCoord, val tiles: List<Int>)

    data class PlacementResultWithMoves(
        val cells: Map<AxialCoord, List<Int>>,
        val poppedTiles: Int,
        val poppingCoords: Set<AxialCoord>,
        val moves: List<Move>
    )

    /** Variant of resolve that records the sequence of transfers (moves) performed. */
    private fun resolveWithMoves(
        cells: MutableMap<AxialCoord, MutableList<Int>>,
        placedCoord: AxialCoord?
    ): PlacementResultWithMoves {
        var totalPopped = 0
        val poppingCoords = mutableSetOf<AxialCoord>()
        val moves = mutableListOf<Move>()

        if (placedCoord != null) {
            // primary merge: record each donor->target transfer as a Move (tiles in donor order)
            while (true) {
                val targetStack = cells[placedCoord] ?: break
                val topColor = targetStack.lastOrNull() ?: break
                val donor = placedCoord.neighbors()
                    .mapNotNull { n -> cells[n]?.let { n to it } }
                    .firstOrNull { (_, stack) -> stack.lastOrNull() == topColor }
                    ?: break
                val (donorCoord, donorStack) = donor
                val run = topRun(donorStack)
                val tiles = donorStack.takeLast(run)
                repeat(run) { donorStack.removeAt(donorStack.size - 1) }
                targetStack.addAll(tiles)
                if (donorStack.isEmpty()) cells.remove(donorCoord)
                moves.add(Move(donorCoord, placedCoord, tiles))
            }
        }

        while (true) {
            // Pop phase
            var poppedThisRound = 0
            val toCheck = cells.keys.toList()
            for (c in toCheck) {
                val stack = cells[c] ?: continue
                val run = topRun(stack)
                if (run >= POP_THRESHOLD) {
                    repeat(run) { stack.removeAt(stack.size - 1) }
                    poppedThisRound += run
                    poppingCoords.add(c)
                    if (stack.isEmpty()) cells.remove(c)
                }
            }
            totalPopped += poppedThisRound

            // Cascade phase: record each cascade merge as a Move
            var merged: Boolean
            do {
                // Find best candidate as in cascadeStep
                data class Candidate(val from: AxialCoord, val to: AxialCoord, val height: Int)
                var best: Candidate? = null
                for ((coord, stack) in cells) {
                    val top = stack.lastOrNull() ?: continue
                    for (n in coord.neighbors()) {
                        val nStack = cells[n] ?: continue
                        if (nStack.lastOrNull() != top) continue
                        val (from, to) = if (stack.size <= nStack.size) coord to n else n to coord
                        val height = maxOf(stack.size, nStack.size)
                        if (best == null || height > best!!.height) {
                            best = Candidate(from, to, height)
                        }
                    }
                }
                val c = best
                if (c == null) {
                    merged = false
                } else {
                    val fromStack = cells[c.from] ?: break
                    val toStack = cells[c.to] ?: break
                    val run = topRun(fromStack)
                    val tiles = fromStack.takeLast(run)
                    repeat(run) { fromStack.removeAt(fromStack.size - 1) }
                    toStack.addAll(tiles)
                    if (fromStack.isEmpty()) cells.remove(c.from)
                    moves.add(Move(c.from, c.to, tiles))
                    merged = true
                }
            } while (merged)

            if (poppedThisRound == 0) break
        }

        return PlacementResultWithMoves(cells = cells, poppedTiles = totalPopped, poppingCoords = poppingCoords, moves = moves)
    }

    /** Variant of placeAndResolve that returns recorded Moves alongside the resolved state. */
    fun placeAndResolveWithMoves(state: HexaStackState, traySlot: Int, coord: AxialCoord): Pair<HexaStackState, List<Move>>? {
        if (state.isWon || state.isGameOver) return null
        if (traySlot !in 0 until TRAY_SLOTS) return null
        val group = state.tray[traySlot] ?: return null
        if (group.isEmpty() || !canPlace(state, coord)) return null

        val cells = state.cells.mapValues { it.value.toMutableList() }.toMutableMap()
        cells[coord] = group.toMutableList()

        val resultWithMoves = resolveWithMoves(cells, placedCoord = coord)

        val newTray = state.tray.toMutableList()
        newTray[traySlot] = null

        val resolved = state.copy(
            cells = resultWithMoves.cells.mapValues { it.value.toList() },
            tray = newTray,
            score = state.score + resultWithMoves.poppedTiles * 10,
            moves = state.moves + 1,
            poppingCoords = resultWithMoves.poppingCoords,
            lastPoppedTiles = resultWithMoves.poppedTiles
        )
        return Pair(dealIfNeeded(resolved).let { finish(it) }, resultWithMoves.moves)
    }
}
