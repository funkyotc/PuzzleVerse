package com.funkyotc.puzzleverse.hexastack.data

/**
 * Verifies that a Hexa Stack level (with its deterministic spawn deck) is 100% clearable.
 * DFS over placements with memoization; tray-slot order is canonicalized so permutations
 * of the same hand don't explode the search.
 */
object HexaStackSolver {

    /** Cap on explored nodes so a pathological level fails fast instead of hanging. */
    var nodeBudget = 500_000

    /** Metrics from the most recent [solve] call (diagnostics for offline tooling). */
    data class Stats(val nodes: Int, val visited: Int, val millis: Long)
    var lastStats: Stats? = null
        private set

    fun isSolvable(level: HexaStackLevel): Boolean = solve(level) != null

    /**
     * Returns a winning sequence of (traySlot, cell) placements, or null if unsolvable
     * (or the node budget is exhausted).
     */
    fun solve(level: HexaStackLevel): List<Pair<Int, AxialCoord>>? {
        val start = HexaStackLogic.initialState(level)
        if (start.isWon) return emptyList()
        var nodes = 0
        val visited = mutableSetOf<String>()

        fun key(s: HexaStackState): String {
            val cellsKey = s.cells.entries
                .sortedBy { it.key.q * 1000 + it.key.r }
                .joinToString(";") { "${it.key.q},${it.key.r}:${it.value.joinToString(",")}" }
            // Tray sorted so identical hands in different slot orders dedupe.
            val trayKey = s.tray.map { it?.joinToString(",") ?: "_" }.sorted().joinToString(";")
            return "$cellsKey|$trayKey|${s.deckIndex}"
        }

        fun dfs(s: HexaStackState, path: MutableList<Pair<Int, AxialCoord>>): Boolean {
            if (s.isWon) return true
            if (s.isGameOver) return false
            if (++nodes > nodeBudget) return false
            val k = key(s)
            if (!visited.add(k)) return false

            val empties = s.level.cells.filter { it !in s.cells }
            // Heuristic: try moves adjacent to a matching top color first — those drive
            // merges/pops and find the solution path almost immediately.
            val moves = mutableListOf<Triple<Int, Int, AxialCoord>>() // (priority, slot, cell)
            for (slot in 0 until HexaStackLogic.TRAY_SLOTS) {
                val group = s.tray[slot] ?: continue
                val top = group.last()
                for (cell in empties) {
                    val hot = cell.neighbors().any { n -> s.cells[n]?.lastOrNull() == top }
                    moves.add(Triple(if (hot) 0 else 1, slot, cell))
                }
            }
            moves.sortBy { it.first }

            for ((_, slot, cell) in moves) {
                val next = HexaStackLogic.placeAndResolve(s, slot, cell) ?: continue
                path.add(slot to cell)
                if (dfs(next, path)) return true
                path.removeAt(path.size - 1)
            }
            return false
        }

        val path = mutableListOf<Pair<Int, AxialCoord>>()
        val t0 = System.currentTimeMillis()
        val result = dfs(start, path)
        lastStats = Stats(nodes, visited.size, System.currentTimeMillis() - t0)
        return if (result) path else null
    }
}
