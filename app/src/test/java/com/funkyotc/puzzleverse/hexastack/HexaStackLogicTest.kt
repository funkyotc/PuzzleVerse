package com.funkyotc.puzzleverse.hexastack

import com.funkyotc.puzzleverse.hexastack.data.AxialCoord
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLevel
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLogic
import com.funkyotc.puzzleverse.hexastack.data.HexaStackPregenerated
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Unit tests for the pure HexaStack engine (merge, pop, cascade, win/loss). */
class HexaStackLogicTest {

    private val logic = HexaStackLogic

    @Test
    fun gridSizesMatchRadius() {
        assertEquals(19, AxialCoord.hexGrid(2).size)
        assertEquals(37, AxialCoord.hexGrid(3).size)
    }

    @Test
    fun initialStateDealsFirstThreeDeckGroups() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 50,
            spawnDeck = listOf(listOf(1), listOf(2), listOf(3), listOf(4))
        )
        val s = logic.initialState(level)
        assertEquals(listOf(listOf(1), listOf(2), listOf(3)), s.tray)
        assertEquals(3, s.deckIndex)
    }

    @Test
    fun canPlaceRequiresEmptyPlayableCell() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 50,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1)),
            spawnDeck = listOf(listOf(1))
        )
        val s = logic.initialState(level)
        assertFalse(logic.canPlace(s, AxialCoord(0, 0)))       // occupied
        assertFalse(logic.canPlace(s, AxialCoord(9, 9)))       // off grid
        assertTrue(logic.canPlace(s, AxialCoord(1, 0)))        // empty, playable
    }

    @Test
    fun primaryMergePopsAtThreshold() {
        // 5 ones on (0,0); placing 5 more ones adjacent merges to 10 and pops all 10.
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000, // out of reach so we can inspect mid-game state
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1, 1)),
            spawnDeck = listOf(listOf(1, 1, 1, 1, 1), listOf(2), listOf(0))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(0, 1))!!
        assertEquals(10, s.lastPoppedTiles)
        assertEquals(100, s.score)
        // The five 1s at (0,0) were transferred onto the placed stack at (0,1), then all 10 popped.
        assertTrue(AxialCoord(0, 0) !in s.cells)
        assertTrue(AxialCoord(0, 1) !in s.cells)
        assertTrue(AxialCoord(0, 1) in s.poppingCoords)
    }

    @Test
    fun nonAdjacentSameColorDoesNotMerge() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1, 1)),
            spawnDeck = listOf(listOf(1, 1), listOf(2), listOf(0))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(2, 0))!! // not adjacent to (0,0)
        assertEquals(0, s.lastPoppedTiles)
        assertEquals(listOf(1, 1, 1, 1, 1), s.cells[AxialCoord(0, 0)])
        assertEquals(listOf(1, 1), s.cells[AxialCoord(2, 0)])
    }

    @Test
    fun runBelowThresholdDoesNotPop() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1)),
            spawnDeck = listOf(listOf(1, 1, 1, 1, 1), listOf(2), listOf(0))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(0, 1))!! // merges to 9 total — under 10
        assertEquals(0, s.lastPoppedTiles)
        assertEquals(0, s.score)
        assertEquals(9, s.cells.values.single().size)
    }

    @Test
    fun primaryMergeKeepsPullingSameColorDonors() {
        // Board: (0,0) has 8 ones capped by 2 twos. Placing 5 twos at (-1,1) —
        // adjacent to (0,0) — pulls the 2 twos off (0,0) (run 7 < 10, no pop),
        // exposing the 8 ones at (0,0). A SECOND placement adjacent to (0,0) then
        // finds the 8+2=10 run: place 2 ones at (1,0) (adjacent to (0,0)) — the
        // primary merge pulls the 8 ones onto it, reaching 10 and popping.
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = mapOf(
                AxialCoord(0, 0) to List(8) { 1 } + listOf(2, 2)
            ),
            spawnDeck = listOf(listOf(2, 2, 2, 2, 2), listOf(1, 1), listOf(0))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(-1, 1))!!
        // No pop yet: the 10-run at (0,0) formed only after this resolve's pop round.
        assertEquals(0, s.lastPoppedTiles)
        assertEquals(List(7) { 2 }, s.cells[AxialCoord(-1, 1)])
        assertEquals(List(8) { 1 }, s.cells[AxialCoord(0, 0)])
        s = logic.placeAndResolve(s, 1, AxialCoord(1, 0))!!
        assertEquals(10, s.lastPoppedTiles)
        assertEquals(100, s.score)
        assertTrue(AxialCoord(0, 0) !in s.cells)
        assertTrue(AxialCoord(1, 0) !in s.cells)
    }

    @Test
    fun cascadePopsImmediatelyWhenReachingThreshold() {
        // A cascade merge that forms a 10+ run pops immediately in the same resolve step
        // without waiting for a subsequent placement.
        // Place a color-2 tile at (1,0) which is adjacent to both (0,1) and (0,0).
        // All three merge into one 10-tile stack and pop.
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = mapOf(
                AxialCoord(0, 1) to List(7) { 2 },
                AxialCoord(0, 0) to List(2) { 2 }
            ),
            spawnDeck = listOf(listOf(2), listOf(0), listOf(0))
        )
        val s0 = logic.initialState(level)
        val s1 = logic.placeAndResolve(s0, 0, AxialCoord(1, 0))!!
        assertEquals(10, s1.lastPoppedTiles)
        assertEquals(100, s1.score)
        assertTrue(AxialCoord(0, 1) !in s1.cells)
        assertTrue(AxialCoord(0, 0) !in s1.cells)
        assertTrue(AxialCoord(1, 0) !in s1.cells)
    }

    @Test
    fun cascadeMergesEqualTopNeighbors() {
        // Two adjacent stacks with equal top colors (7 twos at (0,1), 3 twos at (0,0)).
        // The cascade merges the shorter run onto the taller stack and pops the resulting 10-run.
        val cells = mutableMapOf<AxialCoord, MutableList<Int>>(
            AxialCoord(0, 1) to (List(7) { 2 }).toMutableList(),
            AxialCoord(0, 0) to (List(3) { 2 }).toMutableList()
        )
        val result = logic.resolve(cells, placedCoord = null)
        assertEquals(10, result.poppedTiles)
        assertTrue(result.cells.isEmpty())
        assertEquals(setOf(AxialCoord(0, 1)), result.poppingCoords)
    }

    @Test
    fun allConnectedSameColorStacksMergeTogether() {
        // 4 connected cells in a line: (0,-1)=4 RED, (0,0)=3 RED, (0,1)=1 RED, (0,2)=5 RED.
        // Total = 13 RED tiles across the connected component.
        // Placing at (0,1) must flood-fill merge ALL 4 connected stacks together into 1 stack and pop all 13 tiles.
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = mapOf(
                AxialCoord(0, -1) to List(4) { 1 },
                AxialCoord(0, 0) to List(3) { 1 },
                AxialCoord(0, 2) to List(5) { 1 }
            ),
            spawnDeck = listOf(listOf(1), listOf(0), listOf(0))
        )
        val s0 = logic.initialState(level)
        val s1 = logic.placeAndResolve(s0, 0, AxialCoord(0, 1))!!
        assertEquals(13, s1.lastPoppedTiles)
        assertEquals(130, s1.score)
        assertTrue(AxialCoord(0, -1) !in s1.cells)
        assertTrue(AxialCoord(0, 0) !in s1.cells)
        assertTrue(AxialCoord(0, 1) !in s1.cells)
        assertTrue(AxialCoord(0, 2) !in s1.cells)
    }

    @Test
    fun cascadeFiresAfterPopRound() {
        // A board that starts with a pop: 10 ones at (0,0) pop first; the cascade
        // phase then merges the adjacent 7 twos at (0,1) with the 3 twos at (1,0)
        // into a run of 10, which pops on the next pop round.
        val cells = mutableMapOf<AxialCoord, MutableList<Int>>(
            AxialCoord(0, 0) to (List(10) { 1 }).toMutableList(),
            AxialCoord(0, 1) to (List(7) { 2 }).toMutableList(),
            AxialCoord(1, 0) to (List(3) { 2 }).toMutableList()
        )
        val result = logic.resolve(cells, placedCoord = null)
        assertEquals(20, result.poppedTiles)
        assertTrue(result.cells.isEmpty())
        assertEquals(setOf(AxialCoord(0, 0), AxialCoord(0, 1)), result.poppingCoords)
    }

    @Test
    fun scoreTargetTriggersWin() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 100,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1, 1)),
            spawnDeck = listOf(listOf(1, 1, 1, 1, 1), listOf(2), listOf(0))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(0, 1))!!
        assertTrue(s.isWon)
        assertFalse(s.isGameOver)
    }

    @Test
    fun deckExhaustionWithoutTargetLoses() {
        // One deck group of 3 ones with a target of 100: placing it can't reach 100,
        // and afterwards tray + deck are empty => loss.
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 100,
            spawnDeck = listOf(listOf(1, 1, 1))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(0, 0))!!
        assertFalse(s.isWon)
        assertTrue(s.isGameOver)
    }

    @Test
    fun fullBoardWithNoPlacementLoses() {
        // Fill the entire 19-cell grid with singletons (no two adjacent equal tops),
        // leave one unplaceable tray group and a dry deck => loss by "no valid placement".
        val cells = AxialCoord.hexGrid(2)
        val colorOf = { c: AxialCoord -> Math.floorMod(c.q - c.r, 3) } // pairwise non-adjacent same class
        val initial = cells.associateWith { listOf(colorOf(it)) }
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = initial,
            spawnDeck = listOf(listOf(0))
        )
        val s = logic.initialState(level)
        assertFalse(logic.hasValidPlacement(s))
        val done = logic.finish(s)
        assertTrue(done.isGameOver)
        assertFalse(done.isWon)
    }

    @Test
    fun dealRefillsTrayWhenAllSlotsEmpty() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            spawnDeck = listOf(listOf(1), listOf(2), listOf(0), listOf(2), listOf(2), listOf(0))
        )
        var s = logic.initialState(level)
        s = logic.placeAndResolve(s, 0, AxialCoord(0, 0))!!
        s = logic.placeAndResolve(s, 1, AxialCoord(0, 1))!!
        assertTrue(s.tray.any { it != null })
        s = logic.placeAndResolve(s, 2, AxialCoord(1, 0))!! // last slot -> auto-deal
        assertEquals(listOf(listOf(2), listOf(2), listOf(0)), s.tray)
        assertEquals(6, s.deckIndex)
    }

    @Test
    fun illegalMovesAreRejected() {
        val level = HexaStackLevel(
            "t", "Easy", 2, scoreTarget = 1000,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1)),
            spawnDeck = listOf(listOf(1), listOf(2), listOf(0))
        )
        val s = logic.initialState(level)
        assertNull(logic.placeAndResolve(s, 0, AxialCoord(0, 0)))  // occupied cell
        assertNull(logic.placeAndResolve(s, 0, AxialCoord(9, 9)))  // off grid
        assertNull(logic.placeAndResolve(s, 5, AxialCoord(0, 1)))  // bad slot
    }

    @Test
    fun everyPregeneratedLevelIsWinnableByGreedyPlayer() {
        // Stronger than the generator's scripted replay: a simple greedy auto-player that
        // places the FIRST tray group adjacent to a matching top when possible, otherwise
        // any empty cell — and crucially must be able to place EVERY deck group this way.
        // That matches real play: no scripted order, every group the game deals must be
        // placeable somewhere without stranding the level.
        for (puzzle in HexaStackPregenerated.ALL_PUZZLES) {
            val level = puzzle.toLevel()
            var s = logic.initialState(level)
            var placed = 0
            var guard = 0
            while (!s.isWon && !s.isGameOver && guard++ < 10_000) {
                val slot = s.tray.indexOfFirst { it != null }
                check(slot >= 0) { "${puzzle.id}: live state with empty tray" }
                val top = s.tray[slot]!!.last()
                val empties = level.cells.filter { it !in s.cells }
                val target = empties.firstOrNull { c ->
                    c.neighbors().any { n -> s.cells[n]?.lastOrNull() == top }
                } ?: empties.firstOrNull()
                checkNotNull(target) { "${puzzle.id}: no empty cell while live" }
                s = logic.placeAndResolve(s, slot, target)!!
                placed++
            }
            assertTrue(
                "${puzzle.id}: greedy player failed after $placed placements " +
                    "(score=${s.score}, target=${level.scoreTarget}, gameOver=${s.isGameOver})",
                s.isWon
            )
        }
        assertEquals(70, HexaStackPregenerated.ALL_PUZZLES.size)
    }

    @Test
    fun pregeneratedLevelChunksFitTrayConvention() {
        for (puzzle in HexaStackPregenerated.ALL_PUZZLES) {
            for ((i, group) in puzzle.spawnDeck.withIndex()) {
                assertTrue(
                    "${puzzle.id} deck[$i] has ${group.size} tiles (max 5)",
                    group.size in 1..5
                )
            }
            assertNotNull(HexaStackPregenerated.getPuzzleById(puzzle.id))
        }
    }

    @Test
    fun pregeneratedLevelsHaveMixedColorChunks() {
        // The decks should be visually varied: most multi-tile chunks carry 2-3 color
        // layers (chunks of the first color phase are mono — color 0 has no predecessor
        // color to layer underneath), and no chunk exceeds 3 layers.
        var multiTile = 0
        var mixed = 0
        for (puzzle in HexaStackPregenerated.ALL_PUZZLES) {
            for ((i, group) in puzzle.spawnDeck.withIndex()) {
                if (group.size >= 2) {
                    multiTile++
                    if (group.toSet().size >= 2) mixed++
                }
                assertTrue(
                    "${puzzle.id} deck[$i] has ${group.toSet().size} colors (max 3)",
                    group.toSet().size in 1..3
                )
            }
        }
        assertTrue(
            "only $mixed/$multiTile multi-tile chunks are mixed",
            mixed * 2 >= multiTile // >=50% of multi-tile chunks are mixed
        )
    }
}
