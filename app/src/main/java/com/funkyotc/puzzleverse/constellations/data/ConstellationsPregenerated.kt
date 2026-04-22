package com.funkyotc.puzzleverse.constellations.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedConstellation(
    override val id: String,
    override val difficulty: String,
    val size: Int,
    val regions: Map<Int, List<Pair<Int, Int>>>,
    val solution: List<Pair<Int, Int>>
) : BrowseablePuzzle {
    override val label: String get() = "Level ${id.takeLast(3)}"
    override val subtitle: String get() = "${size}x${size}"
    
    fun toConstellationsPuzzle(): ConstellationsPuzzle {
        val grid = List(size) { r -> List(size) { c ->
            // Find which region contains this (r,c)
            val regionId = regions.entries.find { it.value.contains(r to c) }?.key ?: 0
            Cell(row = r, col = c, regionId = regionId)
        }}
        return ConstellationsPuzzle(size, grid, regions, solution)
    }
}

object ConstellationsPregenerated {
    val ALL_PUZZLES: List<PregeneratedConstellation> by lazy {
        listOf(
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_001",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(0 to 2, 1 to 2, 0 to 1),
                    1 to listOf(3 to 0, 3 to 1, 2 to 0, 4 to 0, 4 to 1, 4 to 2, 4 to 3, 4 to 4),
                    2 to listOf(2 to 1, 2 to 2, 2 to 3, 1 to 1, 1 to 0, 0 to 0),
                    3 to listOf(1 to 4, 0 to 4, 0 to 3, 1 to 3, 2 to 4, 3 to 4),
                    4 to listOf(3 to 2, 3 to 3)
                ),
                solution = listOf(0 to 2, 1 to 4, 2 to 1, 3 to 3, 4 to 0)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_002",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(4 to 0, 3 to 0, 2 to 0, 3 to 1, 4 to 1, 4 to 2, 4 to 3, 2 to 1, 3 to 2),
                    1 to listOf(2 to 4, 3 to 4, 3 to 3, 4 to 4),
                    2 to listOf(1 to 2, 1 to 1, 1 to 0, 2 to 2, 0 to 1, 0 to 0),
                    3 to listOf(0 to 3, 1 to 3, 1 to 4, 0 to 4, 0 to 2),
                    4 to listOf(2 to 3)
                ),
                solution = listOf(0 to 2, 1 to 0, 2 to 3, 3 to 1, 4 to 4)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_003",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(1 to 2),
                    1 to listOf(3 to 3, 3 to 4, 4 to 4, 4 to 3),
                    2 to listOf(3 to 0, 2 to 0, 1 to 0),
                    3 to listOf(2 to 3, 2 to 2, 3 to 2, 3 to 1, 4 to 2, 2 to 4, 4 to 1, 4 to 0),
                    4 to listOf(0 to 2, 0 to 3, 0 to 4, 1 to 3, 1 to 4, 0 to 1, 1 to 1, 0 to 0, 2 to 1)
                ),
                solution = listOf(0 to 4, 1 to 2, 2 to 0, 3 to 3, 4 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_004",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(4 to 0, 3 to 0, 2 to 0, 1 to 0, 0 to 0, 0 to 1),
                    1 to listOf(0 to 3, 1 to 3, 1 to 2, 0 to 2, 1 to 4, 0 to 4, 2 to 4, 3 to 4),
                    2 to listOf(4 to 1, 4 to 2, 4 to 3, 4 to 4),
                    3 to listOf(3 to 1, 3 to 2, 3 to 3),
                    4 to listOf(2 to 1, 1 to 1, 2 to 2, 2 to 3)
                ),
                solution = listOf(0 to 2, 1 to 0, 2 to 3, 3 to 1, 4 to 4)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_005",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(3 to 3, 3 to 2, 4 to 3, 4 to 2),
                    1 to listOf(2 to 1, 2 to 0, 3 to 0, 3 to 1, 4 to 0, 4 to 1),
                    2 to listOf(1 to 1, 1 to 0),
                    3 to listOf(0 to 2, 1 to 2, 0 to 1, 0 to 0),
                    4 to listOf(2 to 3, 2 to 4, 3 to 4, 1 to 4, 0 to 4, 4 to 4, 2 to 2, 1 to 3, 0 to 3)
                ),
                solution = listOf(0 to 2, 1 to 0, 2 to 4, 3 to 1, 4 to 3)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_006",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(2 to 2, 3 to 2, 1 to 2, 1 to 3, 0 to 2, 1 to 1, 0 to 3, 0 to 1, 4 to 2, 4 to 3, 4 to 4, 0 to 0, 3 to 4),
                    1 to listOf(3 to 3),
                    2 to listOf(2 to 3, 2 to 4, 1 to 4, 0 to 4),
                    3 to listOf(4 to 0, 4 to 1, 3 to 1, 3 to 0, 2 to 0, 1 to 0),
                    4 to listOf(2 to 1)
                ),
                solution = listOf(0 to 2, 1 to 4, 2 to 1, 3 to 3, 4 to 0)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_007",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(0 to 1, 0 to 2, 1 to 1, 0 to 3, 1 to 0, 1 to 2, 0 to 0, 1 to 3, 1 to 4, 2 to 4),
                    1 to listOf(2 to 0),
                    2 to listOf(3 to 1, 3 to 2, 2 to 1, 2 to 2, 2 to 3, 3 to 3, 3 to 4),
                    3 to listOf(4 to 1, 4 to 0, 3 to 0, 4 to 2, 4 to 3, 4 to 4),
                    4 to listOf(0 to 4)
                ),
                solution = listOf(0 to 4, 1 to 2, 2 to 0, 3 to 3, 4 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_008",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(2 to 4, 1 to 4, 3 to 4, 0 to 4),
                    1 to listOf(4 to 2, 3 to 2, 2 to 2, 2 to 3, 1 to 2, 1 to 3, 2 to 1, 3 to 3, 0 to 3, 0 to 2, 0 to 1, 1 to 1, 0 to 0),
                    2 to listOf(4 to 0, 3 to 0, 2 to 0, 1 to 0),
                    3 to listOf(4 to 3, 4 to 4),
                    4 to listOf(3 to 1, 4 to 1)
                ),
                solution = listOf(0 to 2, 1 to 0, 2 to 4, 3 to 1, 4 to 3)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_009",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(2 to 2, 1 to 2),
                    1 to listOf(3 to 3, 3 to 2, 3 to 1, 2 to 1, 4 to 3, 3 to 0, 4 to 0, 4 to 2, 4 to 1),
                    2 to listOf(0 to 0, 1 to 0, 0 to 1, 1 to 1, 2 to 0, 0 to 2),
                    3 to listOf(0 to 4, 0 to 3, 1 to 3, 1 to 4),
                    4 to listOf(2 to 3, 2 to 4, 3 to 4, 4 to 4)
                ),
                solution = listOf(0 to 3, 1 to 0, 2 to 2, 3 to 4, 4 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Easy_puzzle_010",
                difficulty = "Easy",
                size = 5,
                regions = mapOf(
                    0 to listOf(0 to 4, 0 to 3),
                    1 to listOf(1 to 0),
                    2 to listOf(1 to 4, 1 to 3, 2 to 3, 2 to 2, 3 to 2, 4 to 2),
                    3 to listOf(2 to 4, 3 to 4, 4 to 4, 4 to 3, 3 to 3),
                    4 to listOf(1 to 2, 1 to 1, 0 to 1, 2 to 1, 0 to 0, 3 to 1, 3 to 0, 0 to 2, 4 to 0, 4 to 1, 2 to 0)
                ),
                solution = listOf(0 to 3, 1 to 0, 2 to 2, 3 to 4, 4 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Hard_puzzle_001",
                difficulty = "Hard",
                size = 8,
                regions = mapOf(
                    0 to listOf(4 to 6, 5 to 6, 5 to 7, 5 to 5, 4 to 7),
                    1 to listOf(0 to 3, 0 to 4, 1 to 3, 1 to 4),
                    2 to listOf(5 to 4, 6 to 4, 7 to 4, 6 to 3, 5 to 3, 6 to 5, 6 to 6, 4 to 3, 7 to 6, 7 to 5, 6 to 7, 4 to 4, 4 to 5, 3 to 5, 7 to 7, 3 to 6, 7 to 3, 7 to 2),
                    3 to listOf(2 to 1, 3 to 1),
                    4 to listOf(0 to 5, 1 to 5, 2 to 5, 0 to 6, 1 to 6, 2 to 6, 2 to 7, 3 to 7, 0 to 7),
                    5 to listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1, 1 to 2, 2 to 0, 2 to 2, 2 to 3, 0 to 2, 3 to 0, 4 to 0, 2 to 4, 4 to 1, 5 to 0),
                    6 to listOf(3 to 2, 3 to 3, 4 to 2, 3 to 4, 5 to 2, 6 to 2, 6 to 1, 5 to 1, 7 to 1, 7 to 0, 6 to 0),
                    7 to listOf(1 to 7)
                ),
                solution = listOf(0 to 3, 1 to 7, 2 to 5, 3 to 1, 4 to 6, 5 to 0, 6 to 2, 7 to 4)
            ),
            PregeneratedConstellation(
                id = "Constellations_Hard_puzzle_002",
                difficulty = "Hard",
                size = 8,
                regions = mapOf(
                    0 to listOf(6 to 1, 7 to 1),
                    1 to listOf(5 to 4, 6 to 4, 7 to 4, 7 to 5, 6 to 5, 5 to 5, 7 to 6, 6 to 6, 5 to 6, 5 to 7, 4 to 7, 4 to 6, 3 to 7, 6 to 7, 2 to 7, 7 to 7),
                    2 to listOf(5 to 3, 4 to 3, 4 to 4, 4 to 5, 3 to 5),
                    3 to listOf(5 to 2, 4 to 2, 5 to 1, 4 to 1, 3 to 1, 4 to 0, 3 to 0, 2 to 0, 1 to 0, 0 to 0),
                    4 to listOf(2 to 6, 1 to 6, 0 to 6, 3 to 6, 1 to 7, 0 to 5, 0 to 7),
                    5 to listOf(6 to 0, 7 to 0, 5 to 0),
                    6 to listOf(7 to 3, 6 to 3, 6 to 2, 7 to 2),
                    7 to listOf(2 to 2, 3 to 2, 2 to 1, 1 to 2, 2 to 3, 1 to 1, 2 to 4, 3 to 3, 3 to 4, 2 to 5, 1 to 4, 0 to 1, 1 to 5, 0 to 2, 1 to 3, 0 to 3, 0 to 4)
                ),
                solution = listOf(0 to 6, 1 to 4, 2 to 7, 3 to 5, 4 to 2, 5 to 0, 6 to 3, 7 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Hard_puzzle_003",
                difficulty = "Hard",
                size = 8,
                regions = mapOf(
                    0 to listOf(6 to 7),
                    1 to listOf(2 to 7, 3 to 7, 4 to 7, 5 to 7, 3 to 6, 1 to 7, 1 to 6, 2 to 6, 2 to 5, 0 to 7),
                    2 to listOf(4 to 3, 4 to 2, 3 to 2, 4 to 1),
                    3 to listOf(7 to 2, 7 to 3, 7 to 4, 6 to 2, 7 to 5, 7 to 1, 6 to 3, 6 to 1, 6 to 5, 5 to 2, 5 to 1, 6 to 4, 6 to 0, 5 to 0, 5 to 3, 4 to 0, 7 to 0),
                    4 to listOf(1 to 4, 0 to 4, 1 to 5, 1 to 3, 2 to 3, 2 to 2, 0 to 5),
                    5 to listOf(0 to 1, 0 to 0, 1 to 0, 1 to 1, 0 to 2, 2 to 0, 1 to 2, 0 to 3, 2 to 1, 3 to 1, 3 to 0),
                    6 to listOf(0 to 6),
                    7 to listOf(4 to 5, 4 to 4, 4 to 6, 5 to 5, 5 to 6, 5 to 4, 6 to 6, 3 to 4, 3 to 5, 7 to 6, 2 to 4, 3 to 3, 7 to 7)
                ),
                solution = listOf(0 to 6, 1 to 3, 2 to 5, 3 to 0, 4 to 2, 5 to 4, 6 to 7, 7 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Hard_puzzle_004",
                difficulty = "Hard",
                size = 8,
                regions = mapOf(
                    0 to listOf(3 to 3, 3 to 2, 3 to 1),
                    1 to listOf(4 to 3, 4 to 2),
                    2 to listOf(7 to 4, 6 to 4, 7 to 3, 7 to 2, 7 to 1, 6 to 3, 6 to 2),
                    3 to listOf(6 to 0, 6 to 1, 5 to 0, 5 to 1, 4 to 0, 5 to 2, 4 to 1, 7 to 0, 3 to 0),
                    4 to listOf(1 to 4, 1 to 3, 2 to 3, 2 to 4, 3 to 4, 3 to 5, 4 to 4, 2 to 5, 5 to 4, 2 to 2, 5 to 3, 2 to 6),
                    5 to listOf(6 to 7, 6 to 6, 5 to 7, 4 to 7, 6 to 5, 5 to 6, 5 to 5, 4 to 6, 4 to 5, 3 to 6, 3 to 7, 2 to 7),
                    6 to listOf(0 to 5, 0 to 4, 0 to 3, 0 to 2, 1 to 5, 1 to 2, 0 to 1, 1 to 1, 0 to 0, 1 to 0, 2 to 1, 1 to 6, 1 to 7, 2 to 0, 0 to 7, 0 to 6),
                    7 to listOf(7 to 7, 7 to 6, 7 to 5)
                ),
                solution = listOf(0 to 6, 1 to 4, 2 to 7, 3 to 1, 4 to 3, 5 to 0, 6 to 2, 7 to 5)
            ),
            PregeneratedConstellation(
                id = "Constellations_Hard_puzzle_005",
                difficulty = "Hard",
                size = 8,
                regions = mapOf(
                    0 to listOf(6 to 4, 7 to 4, 7 to 5, 7 to 6),
                    1 to listOf(3 to 2, 3 to 1, 3 to 0, 4 to 0, 2 to 1, 2 to 2, 1 to 1, 2 to 3, 1 to 2, 0 to 1, 4 to 2, 4 to 1, 0 to 2, 1 to 3, 0 to 3, 2 to 4, 5 to 2),
                    2 to listOf(1 to 6, 2 to 6, 1 to 5, 0 to 5, 0 to 4, 1 to 4),
                    3 to listOf(1 to 0, 2 to 0, 0 to 0),
                    4 to listOf(5 to 5, 6 to 5, 4 to 5, 4 to 4, 6 to 6, 3 to 5, 3 to 6, 4 to 3, 5 to 6, 2 to 5, 3 to 3, 3 to 4, 4 to 6, 3 to 7, 4 to 7),
                    5 to listOf(0 to 7, 1 to 7, 2 to 7, 0 to 6),
                    6 to listOf(7 to 7, 6 to 7, 5 to 7),
                    7 to listOf(6 to 3, 7 to 3, 7 to 2, 5 to 3, 6 to 2, 6 to 1, 5 to 4, 5 to 1, 7 to 1, 6 to 0, 5 to 0, 7 to 0)
                ),
                solution = listOf(0 to 6, 1 to 4, 2 to 0, 3 to 3, 4 to 1, 5 to 7, 6 to 2, 7 to 5)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_001",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(3 to 3, 4 to 3, 2 to 3, 3 to 4, 5 to 3, 2 to 4),
                    1 to listOf(2 to 1),
                    2 to listOf(2 to 5, 3 to 5, 4 to 5, 4 to 4, 5 to 5, 1 to 5, 5 to 4),
                    3 to listOf(5 to 0, 5 to 1, 4 to 0, 4 to 1, 3 to 0, 3 to 1, 4 to 2, 3 to 2),
                    4 to listOf(5 to 2),
                    5 to listOf(0 to 2, 0 to 3, 0 to 1, 1 to 1, 1 to 2, 0 to 0, 2 to 2, 1 to 0, 0 to 4, 1 to 3, 0 to 5, 1 to 4, 2 to 0)
                ),
                solution = listOf(0 to 3, 1 to 5, 2 to 1, 3 to 4, 4 to 0, 5 to 2)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_002",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(3 to 0, 4 to 0, 5 to 0, 5 to 1),
                    1 to listOf(2 to 2, 3 to 2, 2 to 1, 2 to 0, 3 to 1, 1 to 0, 4 to 2, 4 to 1, 0 to 0),
                    2 to listOf(3 to 5, 4 to 5, 4 to 4, 5 to 4, 2 to 5, 3 to 4, 5 to 5, 5 to 3),
                    3 to listOf(1 to 2, 1 to 1, 0 to 2, 0 to 1, 0 to 3),
                    4 to listOf(5 to 2),
                    5 to listOf(1 to 3, 2 to 3, 3 to 3, 2 to 4, 1 to 4, 1 to 5, 0 to 4, 4 to 3, 0 to 5)
                ),
                solution = listOf(0 to 3, 1 to 5, 2 to 1, 3 to 4, 4 to 0, 5 to 2)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_003",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(0 to 0, 1 to 0, 2 to 0, 1 to 1, 0 to 1, 0 to 2),
                    1 to listOf(4 to 4, 3 to 4, 2 to 4, 2 to 3, 2 to 2, 2 to 1),
                    2 to listOf(1 to 3, 1 to 2),
                    3 to listOf(3 to 3, 4 to 3, 3 to 2, 3 to 1),
                    4 to listOf(1 to 4, 1 to 5, 0 to 5, 0 to 4, 0 to 3),
                    5 to listOf(5 to 4, 5 to 5, 4 to 5, 3 to 5, 5 to 3, 2 to 5, 5 to 2, 4 to 2, 5 to 1, 4 to 1, 4 to 0, 3 to 0, 5 to 0)
                ),
                solution = listOf(0 to 5, 1 to 3, 2 to 0, 3 to 2, 4 to 4, 5 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_004",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(3 to 1),
                    1 to listOf(1 to 5, 0 to 5),
                    2 to listOf(1 to 4, 0 to 4, 2 to 4, 2 to 5, 3 to 4, 3 to 5),
                    3 to listOf(1 to 2, 0 to 2, 1 to 1, 0 to 3, 1 to 3, 2 to 3, 2 to 1, 0 to 1, 3 to 3),
                    4 to listOf(2 to 0, 3 to 0, 1 to 0, 0 to 0, 4 to 0, 5 to 0),
                    5 to listOf(4 to 2, 5 to 2, 3 to 2, 5 to 3, 4 to 3, 2 to 2, 4 to 1, 5 to 1, 4 to 4, 5 to 4, 5 to 5, 4 to 5)
                ),
                solution = listOf(0 to 5, 1 to 2, 2 to 4, 3 to 1, 4 to 3, 5 to 0)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_005",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(0 to 2, 0 to 1, 0 to 0, 1 to 0),
                    1 to listOf(3 to 0, 3 to 1, 2 to 0, 4 to 1, 4 to 2, 5 to 2, 4 to 3, 4 to 0, 5 to 0, 5 to 3, 4 to 4, 5 to 4, 5 to 1),
                    2 to listOf(1 to 2, 1 to 1, 2 to 2, 1 to 3, 2 to 1, 1 to 4, 1 to 5),
                    3 to listOf(3 to 3, 2 to 3, 3 to 2, 3 to 4, 2 to 4, 2 to 5, 3 to 5),
                    4 to listOf(4 to 5, 5 to 5),
                    5 to listOf(0 to 3, 0 to 4, 0 to 5)
                ),
                solution = listOf(0 to 3, 1 to 0, 2 to 2, 3 to 4, 4 to 1, 5 to 5)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_006",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(5 to 3, 5 to 4, 5 to 5, 5 to 2, 5 to 1, 4 to 1, 4 to 0, 5 to 0),
                    1 to listOf(4 to 3, 4 to 2),
                    2 to listOf(1 to 2, 2 to 2, 3 to 2, 3 to 1, 2 to 3, 2 to 1, 1 to 3, 2 to 0, 1 to 1, 0 to 1, 1 to 0, 0 to 0, 0 to 2, 3 to 0),
                    3 to listOf(0 to 3),
                    4 to listOf(3 to 4, 2 to 4, 3 to 3, 1 to 4, 0 to 4),
                    5 to listOf(4 to 5, 4 to 4, 3 to 5, 2 to 5, 1 to 5, 0 to 5)
                ),
                solution = listOf(0 to 3, 1 to 5, 2 to 1, 3 to 4, 4 to 2, 5 to 0)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_007",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(3 to 1, 3 to 0, 2 to 0),
                    1 to listOf(2 to 2, 3 to 2, 2 to 1, 3 to 3, 4 to 3, 5 to 3),
                    2 to listOf(1 to 1, 0 to 1, 1 to 0, 0 to 0),
                    3 to listOf(0 to 3, 0 to 2, 1 to 3, 0 to 4, 1 to 2, 1 to 4),
                    4 to listOf(4 to 0, 4 to 1, 4 to 2, 5 to 2, 5 to 1, 5 to 0),
                    5 to listOf(2 to 3, 2 to 4, 2 to 5, 3 to 4, 4 to 4, 1 to 5, 0 to 5, 3 to 5, 5 to 4, 4 to 5, 5 to 5)
                ),
                solution = listOf(0 to 1, 1 to 4, 2 to 0, 3 to 3, 4 to 5, 5 to 2)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_008",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(2 to 0, 3 to 0, 1 to 0, 4 to 0, 0 to 0),
                    1 to listOf(2 to 3, 3 to 3, 2 to 4, 2 to 5, 2 to 2, 1 to 5, 1 to 3, 0 to 3, 1 to 4, 0 to 5, 0 to 4),
                    2 to listOf(3 to 2, 3 to 1, 2 to 1, 1 to 1, 0 to 1),
                    3 to listOf(4 to 4, 4 to 5, 4 to 3, 3 to 4, 5 to 3, 5 to 2, 5 to 4, 4 to 2, 5 to 5, 4 to 1, 5 to 1, 5 to 0),
                    4 to listOf(1 to 2, 0 to 2),
                    5 to listOf(3 to 5)
                ),
                solution = listOf(0 to 2, 1 to 4, 2 to 1, 3 to 5, 4 to 0, 5 to 3)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_009",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(0 to 5, 1 to 5, 0 to 4, 0 to 3, 1 to 4, 2 to 4),
                    1 to listOf(4 to 1, 5 to 1),
                    2 to listOf(0 to 2, 0 to 1, 1 to 1, 1 to 2, 0 to 0, 1 to 0, 2 to 0, 1 to 3),
                    3 to listOf(2 to 2, 3 to 2, 2 to 3, 4 to 2, 3 to 3, 4 to 3, 3 to 4, 5 to 2, 3 to 5, 2 to 5),
                    4 to listOf(2 to 1, 3 to 1, 3 to 0, 4 to 0, 5 to 0),
                    5 to listOf(5 to 3, 5 to 4, 5 to 5, 4 to 5, 4 to 4)
                ),
                solution = listOf(0 to 2, 1 to 5, 2 to 3, 3 to 0, 4 to 4, 5 to 1)
            ),
            PregeneratedConstellation(
                id = "Constellations_Medium_puzzle_010",
                difficulty = "Medium",
                size = 6,
                regions = mapOf(
                    0 to listOf(1 to 5, 2 to 5, 2 to 4, 0 to 5),
                    1 to listOf(5 to 1, 4 to 1, 4 to 2, 5 to 2),
                    2 to listOf(3 to 4, 3 to 5, 4 to 4, 3 to 3, 5 to 4, 5 to 5, 4 to 5, 2 to 3, 4 to 3, 3 to 2, 5 to 3),
                    3 to listOf(1 to 3, 1 to 2, 1 to 4, 0 to 2, 0 to 3, 0 to 1, 1 to 1, 0 to 4, 2 to 2),
                    4 to listOf(2 to 1, 3 to 1, 3 to 0, 2 to 0, 4 to 0, 1 to 0, 5 to 0),
                    5 to listOf(0 to 0)
                ),
                solution = listOf(0 to 0, 1 to 3, 2 to 5, 3 to 1, 4 to 4, 5 to 2)
            ),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedConstellation>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): PregeneratedConstellation? = ALL_PUZZLES.find { it.id == id }
}
