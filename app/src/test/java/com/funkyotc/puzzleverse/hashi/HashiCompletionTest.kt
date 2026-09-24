package com.funkyotc.puzzleverse.hashi

import com.funkyotc.puzzleverse.hashi.data.HashiPregenerated
import com.funkyotc.puzzleverse.hashi.data.HashiPuzzle
import com.funkyotc.puzzleverse.hashi.viewmodel.HashiViewModel
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/** Finds legal bridge counts, then enters them through the same action used by the screen. */
@OptIn(ExperimentalCoroutinesApi::class)
class HashiCompletionTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @Before fun setup() = Dispatchers.setMain(dispatcher)
    @After fun teardown() = Dispatchers.resetMain()

    @Test fun everyCheckedInPuzzleCanWinThroughBridgeActions() {
        val failures = mutableListOf<String>()
        assertEquals(40, HashiPregenerated.ALL_PUZZLES.size)
        for (puzzle in HashiPregenerated.ALL_PUZZLES) {
            val solution = solve(puzzle)
            if (solution == null) {
                failures += "${puzzle.id}: no connected noncrossing bridge assignment"
                continue
            }
            val vm = HashiViewModel(StreakRepository(sharedPreferences = FakeSharedPreferences()), "puzzle", puzzle.id)
            for ((edge, count) in solution) repeat(count) {
                vm.toggleBridge(puzzle.islands[edge.first], puzzle.islands[edge.second])
            }
            if (!vm.isGameWon.value) failures += "${puzzle.id}: ViewModel did not award win"
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }

    @Test fun dailyWinCreditsStreakOnlyAfterActualBridgeWin() {
        val repository = StreakRepository(sharedPreferences = FakeSharedPreferences())
        val vm = HashiViewModel(repository, "daily")
        val puzzle = vm.puzzle.value!!
        val solution = solve(puzzle) ?: error("${puzzle.id} has no solution")
        assertEquals(0, repository.getStreak("hashi").count)
        for ((edge, count) in solution) repeat(count) {
            vm.toggleBridge(puzzle.islands[edge.first], puzzle.islands[edge.second])
        }
        assertTrue(vm.isGameWon.value)
        assertEquals(todayEpochDay(), repository.getStreak("hashi").lastCompletedEpochDay)
    }

    private fun solve(puzzle: HashiPuzzle): List<Pair<Pair<Int, Int>, Int>>? {
        val islands = puzzle.islands
        val edges = buildList {
            for (a in islands.indices) for (b in a + 1 until islands.size) {
                val x = islands[a]
                val y = islands[b]
                if (x.x != y.x && x.y != y.y) continue
                if (islands.any { z -> z != x && z != y &&
                        ((x.x == y.x && z.x == x.x && z.y > minOf(x.y, y.y) && z.y < maxOf(x.y, y.y)) ||
                         (x.y == y.y && z.y == x.y && z.x > minOf(x.x, y.x) && z.x < maxOf(x.x, y.x))) }) continue
                add(a to b)
            }
        }
        fun crosses(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
            val u = islands[a.first]; val v = islands[a.second]
            val w = islands[b.first]; val z = islands[b.second]
            if ((u.y == v.y) == (w.y == z.y)) return false
            val h1 = if (u.y == v.y) u else w
            val h2 = if (u.y == v.y) v else z
            val v1 = if (u.y == v.y) w else u
            val v2 = if (u.y == v.y) z else v
            return v1.x > minOf(h1.x, h2.x) && v1.x < maxOf(h1.x, h2.x) &&
                h1.y > minOf(v1.y, v2.y) && h1.y < maxOf(v1.y, v2.y)
        }
        val counts = IntArray(edges.size)
        val remaining = islands.map { it.requiredBridges }.toIntArray()
        var steps = 0
        fun search(index: Int): Boolean {
            if (++steps > 2_000_000) return false
            if (index == edges.size) {
                if (remaining.any { it != 0 }) return false
                val seen = mutableSetOf(0)
                val queue = ArrayDeque<Int>(); queue.add(0)
                while (queue.isNotEmpty()) {
                    val current = queue.removeFirst()
                    edges.forEachIndexed { i, edge ->
                        if (counts[i] > 0) {
                            val next = if (edge.first == current) edge.second else if (edge.second == current) edge.first else -1
                            if (next >= 0 && seen.add(next)) queue.add(next)
                        }
                    }
                }
                return seen.size == islands.size
            }
            val (a, b) = edges[index]
            for (count in minOf(2, remaining[a], remaining[b]) downTo 0) {
                if (count > 0 && (0 until index).any { counts[it] > 0 && crosses(edges[index], edges[it]) }) continue
                counts[index] = count
                remaining[a] -= count; remaining[b] -= count
                val possible = remaining.indices.all { island ->
                    val capacity = edges.indices.drop(index + 1).count { islandsEdge ->
                        edges[islandsEdge].first == island || edges[islandsEdge].second == island
                    } * 2
                    remaining[island] in 0..capacity
                }
                if (possible && search(index + 1)) return true
                remaining[a] += count; remaining[b] += count
            }
            counts[index] = 0
            return false
        }
        return if (search(0)) edges.indices.filter { counts[it] > 0 }.map { edges[it] to counts[it] } else null
    }
}
