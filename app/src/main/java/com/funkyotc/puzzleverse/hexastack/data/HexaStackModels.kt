package com.funkyotc.puzzleverse.hexastack.data

/**
 * Axial hex coordinate. The 6 neighbor directions follow the standard axial basis:
 * (+1,0), (+1,-1), (0,-1), (-1,0), (-1,+1), (0,+1).
 */
data class AxialCoord(val q: Int, val r: Int) {
    fun neighbors(): List<AxialCoord> = DIRECTIONS.map { (dq, dr) -> AxialCoord(q + dq, r + dr) }

    companion object {
        val DIRECTIONS = listOf(
            AxialCoord(1, 0), AxialCoord(1, -1), AxialCoord(0, -1),
            AxialCoord(-1, 0), AxialCoord(-1, 1), AxialCoord(0, 1)
        )

        /** All cells of a hex-shaped grid with the given radius (radius 2 = 19 cells, radius 3 = 37). */
        fun hexGrid(radius: Int): Set<AxialCoord> {
            val cells = mutableSetOf<AxialCoord>()
            for (q in -radius..radius) {
                val rMin = maxOf(-radius, -q - radius)
                val rMax = minOf(radius, -q + radius)
                for (r in rMin..rMax) cells.add(AxialCoord(q, r))
            }
            return cells
        }
    }
}

/**
 * A level definition. [cells] is the full set of playable cells (hex-shaped by [radius]);
 * [initialStacks] seeds the board with pre-placed stacks (tile lists, last = top);
 * [spawnDeck] is the deterministic sequence of tile groups dealt 3 at a time into the tray.
 * The level is won once [scoreTarget] points are scored (10 per popped tile).
 */
data class HexaStackLevel(
    val id: String,
    val difficulty: String,
    val radius: Int,
    val scoreTarget: Int,
    val initialStacks: Map<AxialCoord, List<Int>> = emptyMap(),
    val spawnDeck: List<List<Int>>
) {
    val cells: Set<AxialCoord> get() = AxialCoord.hexGrid(radius)
}

data class HexaStackState(
    val level: HexaStackLevel,
    /** Occupied cells -> stack tiles (last = top). Empty cells are absent from the map. */
    val cells: Map<AxialCoord, List<Int>>,
    /** 3 reserve slots; null = slot already placed / not yet dealt. */
    val tray: List<List<Int>?>,
    /** Index into level.spawnDeck of the next group to deal. */
    val deckIndex: Int,
    val score: Int = 0,
    val moves: Int = 0,
    val isWon: Boolean = false,
    val isGameOver: Boolean = false,
    /** Cells currently playing the pop animation (tiles already removed from [cells]). */
    val poppingCoords: Set<AxialCoord> = emptySet(),
    /** Number of tiles popped during the latest cascade step (for combo display). */
    val lastPoppedTiles: Int = 0
)
