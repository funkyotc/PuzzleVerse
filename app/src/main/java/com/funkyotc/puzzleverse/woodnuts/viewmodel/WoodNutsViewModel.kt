package com.funkyotc.puzzleverse.woodnuts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.woodnuts.data.*
import com.funkyotc.puzzleverse.woodnuts.physics.WoodNutsPhysicsEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.funkyotc.puzzleverse.core.todayEpochDay
import kotlinx.coroutines.isActive

class WoodNutsViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow<WoodNutsState?>(null)
    val state: StateFlow<WoodNutsState?> = _state.asStateFlow()

    private val physicsEngine = WoodNutsPhysicsEngine()

    init {
        startNewGame()
        startPhysicsLoop()
    }

    fun startNewGame() {
        val selectedLevel = when {
            puzzleId != null -> {
                WoodNutsPregenerated.ALL_LEVELS.find { it.id == puzzleId }
            }
            mode == "daily" -> {
                val today = todayEpochDay()
                val index = (today % WoodNutsPregenerated.ALL_LEVELS.size).toInt()
                WoodNutsPregenerated.ALL_LEVELS[index]
            }
            else -> {
                val diffName = when (mode?.lowercase()) {
                    "easy" -> "Easy"
                    "medium" -> "Medium"
                    "hard" -> "Hard"
                    else -> null
                }
                if (diffName != null) {
                    WoodNutsPregenerated.PUZZLES_BY_DIFFICULTY[diffName]?.random()
                } else {
                    WoodNutsPregenerated.ALL_LEVELS.random()
                }
            }
        } ?: WoodNutsPregenerated.ALL_LEVELS.first()

        val level = WoodNutsLevel(
            id = selectedLevel.id,
            difficulty = selectedLevel.difficulty,
            rows = selectedLevel.rows,
            cols = selectedLevel.cols,
            bolts = selectedLevel.bolts,
            planks = selectedLevel.planks,
            boardQueue = selectedLevel.boardQueue
        )

        // Assign random depth layers to planks so they collide in layers
        val assignedPlanks = selectedLevel.planks.mapIndexed { index, p -> 
            p.copy(depthLayer = index % 14)
        }

        val queue = selectedLevel.boardQueue
        val activeCount = minOf(3, queue.size)
        val initialActiveBoards = queue.take(activeCount).mapIndexed { idx, color ->
            ColorBoard("board_${idx + 1}", color, 0)
        }
        val initialRemainingQueue = queue.drop(activeCount)

        _state.value = WoodNutsState(
            level = level,
            bolts = selectedLevel.bolts.map { it.copy() },
            planks = assignedPlanks,
            activeBoards = initialActiveBoards,
            remainingBoardQueue = initialRemainingQueue,
            trayScrews = emptyList(),
            isWon = false,
            isFailed = false,
            failedReason = null
        )

        physicsEngine.initWorld(level, assignedPlanks, selectedLevel.bolts)
    }

    private fun startPhysicsLoop() {
        viewModelScope.launch {
            var lastFrame = System.currentTimeMillis()
            while (isActive) {
                val now = System.currentTimeMillis()
                val dt = if (lastFrame == 0L) 1.0 / 60.0
                         else ((now - lastFrame) / 1000.0).coerceAtMost(1.0 / 60.0)
                lastFrame = now

                val currentState = _state.value ?: run { delay(16); continue }
                if (currentState.isWon || currentState.isFailed) { delay(16); continue }

                val transforms = physicsEngine.step(dt)
                var updated = false

                val newPlanks = currentState.planks.map { plank ->
                    val trans = transforms[plank.id]
                    if (trans != null && trans != plank.transform) {
                        updated = true
                        plank.copy(transform = trans)
                    } else plank
                }.toMutableList()

                for (i in newPlanks.indices) {
                    val p = newPlanks[i]
                    if (!p.removed && physicsEngine.isPlankOutOfBounds(p.id, currentState.level.rows + 3f)) {
                        newPlanks[i] = p.copy(removed = true)
                        physicsEngine.removePlank(p.id)
                        updated = true
                    }
                }

                if (updated) {
                    val allPlanksRemoved = newPlanks.all { it.removed }
                    val allBoardsCleared = currentState.activeBoards.isEmpty() && currentState.remainingBoardQueue.isEmpty() && currentState.trayScrews.isEmpty()
                    val isWon = allPlanksRemoved && allBoardsCleared
                    if (isWon && !currentState.isWon) {
                        if (mode == "daily") {
                            val today = todayEpochDay()
                            streakRepository?.let { repo ->
                                val streak = repo.getStreak("woodnuts")
                                if (streak.lastCompletedEpochDay != today) {
                                    val newStreak = streak.copy(
                                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                                        lastCompletedEpochDay = today
                                    )
                                    repo.saveStreak(newStreak)
                                }
                            }
                        }
                    }
                    _state.value = currentState.copy(planks = newPlanks, isWon = isWon)
                }

                delay(16)
            }
        }
    }

    fun removeBolt(boltId: String) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isFailed) return

        val boltIndex = currentState.bolts.indexOfFirst { it.id == boltId && !it.removed && !it.isUnscrewing }
        if (boltIndex == -1) return

        val unscrewingBolts = currentState.bolts.toMutableList()
        unscrewingBolts[boltIndex] = unscrewingBolts[boltIndex].copy(isUnscrewing = true)

        _state.value = currentState.copy(
            bolts = unscrewingBolts,
            moves = currentState.moves + 1
        )

        viewModelScope.launch {
            delay(350)
            finishRemoveBolt(boltId)
        }
    }

    private fun finishRemoveBolt(boltId: String) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isFailed) return

        val boltIndex = currentState.bolts.indexOfFirst { it.id == boltId }
        if (boltIndex == -1) return

        val bolt = currentState.bolts[boltIndex]
        val updatedBolts = currentState.bolts.toMutableList()
        updatedBolts[boltIndex] = bolt.copy(removed = true, isUnscrewing = false)

        physicsEngine.removeBolt(boltId)

        var curActive = currentState.activeBoards.toMutableList()
        var curQueue = currentState.remainingBoardQueue.toMutableList()
        var curTray = currentState.trayScrews.toMutableList()
        var isFailed = false
        var failedReason: String? = null
        var boardIdCounter = currentState.level.bolts.size + curActive.size + 10

        // Attempt to place bolt into matching active board
        val targetBoardIdx = curActive.indexOfFirst { it.color == bolt.color && it.filledCount < 3 }
        if (targetBoardIdx != -1) {
            val updatedBoard = curActive[targetBoardIdx].copy(filledCount = curActive[targetBoardIdx].filledCount + 1)
            curActive[targetBoardIdx] = updatedBoard
            if (updatedBoard.filledCount == 3) {
                curActive.removeAt(targetBoardIdx)
                if (curQueue.isNotEmpty()) {
                    val nextColor = curQueue.removeAt(0)
                    curActive.add(ColorBoard("board_${boardIdCounter++}", nextColor, 0))
                }
            }
        } else {
            // No matching board active with space -> send to tray
            if (curTray.size < 5) {
                curTray.add(bolt.color)
            } else {
                isFailed = true
                failedReason = "Tray full! No space for ${bolt.color.displayName} screw."
            }
        }

        // Process auto-transfer from tray if possible
        if (!isFailed) {
            val (postActive, postQueue, postTray) = processTrayAndBoardState(curActive, curQueue, curTray, boardIdCounter)
            curActive = postActive.toMutableList()
            curQueue = postQueue.toMutableList()
            curTray = postTray.toMutableList()
        }

        val allPlanksRemoved = currentState.planks.all { it.removed }
        val allBoardsCleared = curActive.isEmpty() && curQueue.isEmpty() && curTray.isEmpty()
        val isWon = !isFailed && allPlanksRemoved && allBoardsCleared

        if (isWon && mode == "daily") {
            val today = todayEpochDay()
            streakRepository?.let { repo ->
                val streak = repo.getStreak("woodnuts")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    repo.saveStreak(newStreak)
                }
            }
        }

        _state.value = currentState.copy(
            bolts = updatedBolts,
            activeBoards = curActive,
            remainingBoardQueue = curQueue,
            trayScrews = curTray,
            isWon = isWon,
            isFailed = isFailed,
            failedReason = failedReason,
            lastRemovedBoltId = boltId
        )
    }

    private fun processTrayAndBoardState(
        activeBoards: List<ColorBoard>,
        remainingQueue: List<ScrewColor>,
        tray: List<ScrewColor>,
        startCounter: Int
    ): Triple<List<ColorBoard>, List<ScrewColor>, List<ScrewColor>> {
        var curActive = activeBoards.toMutableList()
        var curQueue = remainingQueue.toMutableList()
        var curTray = tray.toMutableList()
        var counter = startCounter

        var changed = true
        while (changed) {
            changed = false
            val trayIterator = curTray.iterator()
            while (trayIterator.hasNext()) {
                val color = trayIterator.next()
                val matchIdx = curActive.indexOfFirst { it.color == color && it.filledCount < 3 }
                if (matchIdx != -1) {
                    trayIterator.remove()
                    val updatedBoard = curActive[matchIdx].copy(filledCount = curActive[matchIdx].filledCount + 1)
                    curActive[matchIdx] = updatedBoard
                    changed = true

                    if (updatedBoard.filledCount == 3) {
                        curActive.removeAt(matchIdx)
                        if (curQueue.isNotEmpty()) {
                            val nextColor = curQueue.removeAt(0)
                            curActive.add(ColorBoard("board_${counter++}", nextColor, 0))
                        }
                    }
                    break
                }
            }
        }
        return Triple(curActive, curQueue, curTray)
    }
}

class WoodNutsViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WoodNutsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WoodNutsViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
