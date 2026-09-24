package com.funkyotc.puzzleverse.pullpin

import androidx.lifecycle.ViewModelStore
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.pullpin.data.GameStatus
import com.funkyotc.puzzleverse.pullpin.viewmodel.PullPinViewModel
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.test.FakeSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PullPinViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val store = ViewModelStore()
    @Before fun setup() { Dispatchers.setMain(dispatcher) }
    @After fun teardown() { store.clear(); Dispatchers.resetMain() }
    private fun keep(vm: PullPinViewModel): PullPinViewModel { store.put("test", vm); return vm }
    private fun settle() { dispatcher.scheduler.advanceTimeBy(5100); dispatcher.scheduler.runCurrent() }

    @Test fun actualDailyWinAwardsStreakOnce() {
        val today = todayEpochDay()
        val repo = StreakRepository(sharedPreferences = FakeSharedPreferences())
        repo.saveStreak(Streak(gameId = "pullpin", count = 2, lastCompletedEpochDay = today - 1))
        val vm = keep(PullPinViewModel(repo, "daily", null))
        repeat(2) {
            vm.state.value!!.level.solution.forEach { pin -> assertTrue(vm.removePin(pin)); settle() }
            assertEquals(GameStatus.WON, vm.state.value!!.status)
            assertEquals(3, repo.getStreak("pullpin").count)
            assertEquals(today, repo.getStreak("pullpin").lastCompletedEpochDay)
            vm.retry()
        }
    }

    @Test fun campaignResumesAtFirstUnfinishedBoard() {
        val repo = com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository()
        val levels = com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated.ALL_LEVELS
        repo.markCompleted(levels.first().id)
        val vm = keep(PullPinViewModel(null, "standard", null))
        vm.setCompletionRepo(repo)
        assertEquals(levels[1].id, vm.state.value!!.level.id)
        vm.state.value!!.level.solution.forEach { pin -> assertTrue(vm.removePin(pin)); settle() }
        assertEquals(GameStatus.WON, vm.state.value!!.status)
        assertTrue(repo.isCompleted(levels[1].id))
    }

    @Test fun retryKeepsLevelAndCancelsAnUnfinishedPull() {
        val vm = keep(PullPinViewModel(null, "standard", null))
        val level = vm.state.value!!.level
        assertTrue(vm.removePin("mix_0"))
        vm.retry()
        settle()
        assertEquals(level, vm.state.value!!.level)
        assertEquals(0, vm.state.value!!.moves)
        assertTrue(vm.state.value!!.pins.none { it.removed || it.isPulling })
        vm.startNewGame()
        assertNotEquals(level.id, vm.state.value!!.level.id)
    }

    @Test fun pauseStopsSimulationAndUndoRestoresBeforePull() {
        val vm = keep(PullPinViewModel(null, "standard", null))
        vm.paused = true
        val before = vm.state.value!!
        assertTrue(vm.removePin("mix_0"))
        settle()
        assertTrue(vm.state.value!!.pins.first().isPulling)
        vm.undo()
        assertEquals(before, vm.state.value)
        vm.paused = false
        assertTrue(vm.removePin("mix_0"))
        settle()
        assertTrue(vm.state.value!!.balls.all { it.color > 0 })
    }
}
