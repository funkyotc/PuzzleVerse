package com.funkyotc.puzzleverse.watersort

import com.funkyotc.puzzleverse.watersort.data.WaterSortPregenerated
import com.funkyotc.puzzleverse.watersort.viewmodel.WaterSortViewModel
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class WaterSortCompletionTest {
    @Test fun everyGeneratedLevelWinsThroughRealPours() {
        val failures = mutableListOf<String>()
        assertEquals(64, WaterSortPregenerated.ALL_LEVELS.size)
        for (level in WaterSortPregenerated.ALL_LEVELS) {
            val alreadySolved = level.bottles.count { bottle ->
                bottle.size == level.height && bottle.distinct().size == 1
            } == level.numColors
            if (alreadySolved) failures += "${level.id}: starts solved"
            val vm = WaterSortViewModel(mode = "puzzle", puzzleId = level.id)
            level.winningMoves.forEachIndexed { step, (from, to) ->
                if (vm.state.value?.isWon == true) return@forEachIndexed
                vm.selectBottle(from)
                vm.selectBottle(to)
                if (vm.state.value?.selectedIndex != -1) {
                    failures += "${level.id}: rejected pour $step ($from to $to)"
                    return@forEachIndexed
                }
            }
            if (vm.state.value?.isWon != true) failures += "${level.id}: did not reach win"
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }
}
