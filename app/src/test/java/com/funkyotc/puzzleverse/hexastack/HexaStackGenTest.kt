package com.funkyotc.puzzleverse.hexastack

import com.funkyotc.puzzleverse.hexastack.data.AxialCoord
import com.funkyotc.puzzleverse.hexastack.data.HexaStackGenerator
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLevel
import com.funkyotc.puzzleverse.hexastack.data.HexaStackLogic
import org.junit.Test

/**
 * Scratch runner for offline level generation. Run with:
 *   gradlew.bat testDebugUnitTest --tests "*HexaStackGenTest*"
 * Rendered Kotlin snippets are written to app/build/hexastack-levels.txt.
 */
class HexaStackGenTest {

    @Test
    fun engineSanity() {
        val logic = HexaStackLogic
        // 1) Adjacent same-top stacks should merge and pop at 10.
        val level = HexaStackLevel(
            "s", "Easy", 2, scoreTarget = 100,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1, 1), AxialCoord(1, 0) to listOf(2, 2)),
            spawnDeck = listOf(listOf(1, 1, 1, 1, 1), listOf(2, 2, 2), listOf(0))
        )
        var s = logic.initialState(level)
        println("start cells: ${s.cells}")
        // Place the five 1s next to (0,0) — (1,0) is occupied, use (0,1).
        s = logic.placeAndResolve(s, 0, AxialCoord(0, 1))!!
        println("after placing 1s at (0,1): cells=${s.cells} popped=${s.lastPoppedTiles} popping=${s.poppingCoords}")
        // 2) Same-color NON-adjacent placement should NOT merge.
        val level2 = HexaStackLevel(
            "s2", "Easy", 2, scoreTarget = 100,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1, 1)),
            spawnDeck = listOf(listOf(1, 1), listOf(2), listOf(0))
        )
        var s2 = logic.initialState(level2)
        s2 = logic.placeAndResolve(s2, 0, AxialCoord(2, 0))!! // far from (0,0)
        println("non-adjacent same-color: cells=${s2.cells} popped=${s2.lastPoppedTiles}")
        // 3) Win fires at the score target.
        val level3 = HexaStackLevel(
            "s3", "Easy", 2, scoreTarget = 100,
            initialStacks = mapOf(AxialCoord(0, 0) to listOf(1, 1, 1, 1, 1)),
            spawnDeck = listOf(listOf(1, 1, 1, 1, 1), listOf(2), listOf(0))
        )
        var s3 = logic.initialState(level3)
        s3 = logic.placeAndResolve(s3, 0, AxialCoord(0, 1))!! // merges, pops 10, score=100
        println("target win: score=${s3.score} won=${s3.isWon}")
        check(s3.isWon) { "expected win at score target" }
    }

    @Test
    fun debugReplay() {
        HexaStackGenerator.debug = true
        val level = HexaStackGenerator.generate(
            "d", "Easy", HexaStackGenerator.Config(radius = 2, numColors = 3), 1977L
        )
        println("level: ${if (level == null) "null" else "OK (${level.spawnDeck.size} groups, target ${level.scoreTarget})"}")
        HexaStackGenerator.debug = false
    }

    @Test
    fun generateSampleLevels() {
        val configs = listOf(
            Triple("Easy", HexaStackGenerator.Config(radius = 2, numColors = 3, targetFraction = 0.6), 20),
            Triple("Medium", HexaStackGenerator.Config(radius = 2, numColors = 4, targetFraction = 0.65), 20),
            Triple("Hard", HexaStackGenerator.Config(radius = 3, numColors = 4, targetFraction = 0.65), 15),
            Triple("Expert", HexaStackGenerator.Config(radius = 3, numColors = 5, targetFraction = 0.7), 15),
        )
        val out = java.io.File("build/hexastack-levels.txt")
        out.parentFile.mkdirs()
        out.printWriter().use { pw ->
            for ((difficulty, config, count) in configs) {
                var successes = 0
                var i = 0
                var seed = 1000L
                while (successes < count && i < count * 3) {
                    i++
                    seed += 977
                    val level = HexaStackGenerator.generate(
                        "${difficulty.lowercase()}-$successes", difficulty, config, seed
                    ) ?: continue
                    successes++
                    pw.println(HexaStackGenerator.renderKotlin(level))
                    pw.println()
                }
                println("[$difficulty] $successes/$count generated ($i seeds tried)")
            }
        }
        println("Wrote ${out.absolutePath}")
    }
}
