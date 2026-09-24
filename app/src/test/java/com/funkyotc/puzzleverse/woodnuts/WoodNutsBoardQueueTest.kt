package com.funkyotc.puzzleverse.woodnuts

import androidx.lifecycle.ViewModelStore
import com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated
import com.funkyotc.puzzleverse.woodnuts.viewmodel.WoodNutsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

/** Verifies the color-board half of every level through real bolt-removal actions. */
@OptIn(ExperimentalCoroutinesApi::class)
class WoodNutsBoardQueueTest {
    @Test fun everyLevelCanClearItsColorBoardsWithoutOverflowingTheTray() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val failures = mutableListOf<String>()
            assertEquals(50, WoodNutsPregenerated.ALL_LEVELS.size)
            for (level in WoodNutsPregenerated.ALL_LEVELS) {
                val store = ViewModelStore()
                val vm = WoodNutsViewModel(mode = "puzzle", puzzleId = level.id)
                store.put("game", vm)
                runCurrent()
                for (color in level.boardQueue) {
                    for (bolt in level.bolts.filter { it.color == color }) {
                        vm.removeBolt(bolt.id)
                        advanceTimeBy(351)
                        runCurrent()
                    }
                }
                val state = vm.state.value!!
                if (state.isFailed || state.activeBoards.isNotEmpty() ||
                    state.remainingBoardQueue.isNotEmpty() || state.trayScrews.isNotEmpty()) {
                    failures += level.id
                }
                store.clear()
            }
            assertTrue("Color boards not cleared: ${failures.joinToString()}", failures.isEmpty())
        } finally {
            Dispatchers.resetMain()
        }
    }
}
