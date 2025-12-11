package com.funkyotc.puzzleverse.constellations.generator

import com.funkyotc.puzzleverse.constellations.data.Cell
import com.funkyotc.puzzleverse.constellations.data.CellState
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPuzzle
import kotlin.random.Random

class ConstellationsPuzzleGenerator {

    fun generate(size: Int = 8, seed: Long = Random.nextLong()): ConstellationsPuzzle {
        val random = Random(seed)
        // 1. Place Stars
        val starPositions = placeStars(size, random)

        // 2. Generate Regions
        val (cells, regions) = generateRegions(size, starPositions, random)

        return ConstellationsPuzzle(size, cells, regions)
    }

    private fun placeStars(size: Int, random: Random): List<Pair<Int, Int>> {
        // Backtracking to place stars satisfying row, col, and adjacency constraints
        val stars = mutableListOf<Pair<Int, Int>>()
        if (solveStars(size, 0, stars, random)) {
            return stars
        }
        // Fallback (should rarely happen for small sizes, but good to have)
        return emptyList()
    }

    private fun solveStars(size: Int, row: Int, stars: MutableList<Pair<Int, Int>>, random: Random): Boolean {
        if (row == size) return true

        val cols = (0 until size).toList().shuffled(random)
        for (col in cols) {
            if (isValidStarPlacement(row, col, stars)) {
                stars.add(row to col)
                if (solveStars(size, row + 1, stars, random)) {
                    return true
                }
                stars.removeAt(stars.size - 1)
            }
        }
        return false
    }

    private fun isValidStarPlacement(row: Int, col: Int, stars: List<Pair<Int, Int>>): Boolean {
        // Check column constraint
        if (stars.any { it.second == col }) return false

        // Check adjacency constraint (including diagonals)
        for ((r, c) in stars) {
            if (kotlin.math.abs(r - row) <= 1 && kotlin.math.abs(c - col) <= 1) {
                return false
            }
        }
        return true
    }

    private fun generateRegions(size: Int, starPositions: List<Pair<Int, Int>>, random: Random): Pair<List<List<Cell>>, Map<Int, List<Pair<Int, Int>>>> {
        val grid = Array(size) { r -> Array(size) { c -> Cell(r, c, -1) } }
        val regions = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()

        // Initialize regions with stars
        val queue = ArrayDeque<Triple<Int, Int, Int>>() // row, col, regionId
        
        starPositions.forEachIndexed { index, (r, c) ->
            grid[r][c] = grid[r][c].copy(regionId = index)
            regions[index] = mutableListOf(r to c)
            queue.add(Triple(r, c, index))
        }

        // Grow regions (BFS)
        // To make it more irregular, we can shuffle neighbors or use a priority queue with random weights
        while (queue.isNotEmpty()) {
            // Randomly pick from queue to make growth irregular? 
            // Standard BFS gives diamond shapes. Let's try to randomize the queue processing slightly or just shuffle neighbors.
            // For better shapes, we can collect all candidates and pick random.
            
            // Let's use a list of candidates instead of a queue for randomness
            val candidates = queue.toMutableList()
            queue.clear() // We will process all current frontier, then add next layer
            
            // Actually, standard BFS with shuffled neighbors is okay, but "Voronoi" style is better.
            // Let's stick to a randomized flood fill.
            
            // Re-implementing with a randomized approach:
            // 1. Put all star cells in a "frontier" list.
            // 2. Loop until grid is full:
            //    a. Pick a random cell from frontier.
            //    b. Try to expand to a random unvisited neighbor.
            //    c. If expanded, add neighbor to frontier.
            //    d. If no unvisited neighbors, remove from frontier.
            break // Breaking out to restart logic below
        }

        // Better Region Growth:
        val frontier = mutableListOf<Triple<Int, Int, Int>>() // r, c, regionId
        starPositions.forEachIndexed { index, (r, c) ->
            frontier.add(Triple(r, c, index))
        }

        while (frontier.isNotEmpty()) {
            val index = random.nextInt(frontier.size)
            val (r, c, regionId) = frontier[index]

            // Find unvisited neighbors
            val neighbors = listOf(
                r - 1 to c, r + 1 to c, r to c - 1, r to c + 1
            ).filter { (nr, nc) ->
                nr in 0 until size && nc in 0 until size && grid[nr][nc].regionId == -1
            }

            if (neighbors.isNotEmpty()) {
                val (nr, nc) = neighbors.random()
                grid[nr][nc] = grid[nr][nc].copy(regionId = regionId)
                regions[regionId]?.add(nr to nc)
                frontier.add(Triple(nr, nc, regionId))
            } else {
                // No place to grow from here
                frontier.removeAt(index)
            }
        }
        
        // Fill any remaining holes (if any) - shouldn't be many if logic is correct, 
        // but sometimes islands can form.
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (grid[r][c].regionId == -1) {
                    // Assign to random neighbor's region
                    val neighbor = listOf(
                        r - 1 to c, r + 1 to c, r to c - 1, r to c + 1
                    ).firstOrNull { (nr, nc) ->
                        nr in 0 until size && nc in 0 until size && grid[nr][nc].regionId != -1
                    }
                    
                    if (neighbor != null) {
                        val regionId = grid[neighbor.first][neighbor.second].regionId
                        grid[r][c] = grid[r][c].copy(regionId = regionId)
                        regions[regionId]?.add(r to c)
                    } else {
                        // Should not happen in a connected grid unless fully isolated 1x1 hole
                        // Fallback: assign to region 0
                         grid[r][c] = grid[r][c].copy(regionId = 0)
                         regions[0]?.add(r to c)
                    }
                }
            }
        }

        // Convert array to list
        val cellList = grid.map { it.toList() }
        return Pair(cellList, regions)
    }
}
