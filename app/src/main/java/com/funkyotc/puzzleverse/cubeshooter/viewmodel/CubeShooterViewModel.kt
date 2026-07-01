package com.funkyotc.puzzleverse.cubeshooter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.cubeshooter.data.*
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.ZoneOffset

class CubeShooterViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard",
    private val puzzleId: String? = null
) : ViewModel() {

    private val _state = MutableStateFlow<CubeShooterState?>(null)
    val state: StateFlow<CubeShooterState?> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        val selectedLevel = when {
            puzzleId != null -> {
                CubeShooterPregenerated.ALL_LEVELS.find { it.id == puzzleId }
            }
            mode == "daily" -> {
                val today = LocalDate.now(ZoneOffset.UTC).toEpochDay()
                val index = (today % CubeShooterPregenerated.ALL_LEVELS.size).toInt()
                CubeShooterPregenerated.ALL_LEVELS[index]
            }
            else -> {
                val diffName = when (mode?.lowercase()) {
                    "easy" -> "Easy"
                    "medium" -> "Medium"
                    "hard" -> "Hard"
                    else -> null
                }
                if (diffName != null) {
                    CubeShooterPregenerated.LEVELS_BY_DIFFICULTY[diffName]?.random()
                } else {
                    CubeShooterPregenerated.ALL_LEVELS.random()
                }
            }
        } ?: CubeShooterPregenerated.ALL_LEVELS.first()

        val level = Level(
            id = selectedLevel.id,
            difficulty = selectedLevel.difficulty,
            cols = selectedLevel.cols,
            rows = selectedLevel.rows,
            grid = selectedLevel.grid,
            tray = selectedLevel.tray
        )

        val cubesRemaining = level.grid.sumOf { row -> row.count { it != null } }

        _state.value = CubeShooterState(
            level = level,
            tray = level.tray,
            track = emptyList(),
            cubesRemaining = cubesRemaining,
            score = 0,
            isWon = false,
            isGameOver = false
        )
    }

    fun dispatch(tankIndex: Int) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return
        if (currentState.track.size >= 5) return
        if (tankIndex !in currentState.tray.indices) return

        val tankToDispatch = currentState.tray[tankIndex]
        if (tankToDispatch.ammo <= 0) return

        val updatedTray = currentState.tray.toMutableList()
        updatedTray.removeAt(tankIndex)

        val updatedTrack = currentState.track.toMutableList()
        updatedTrack.add(TrackTank(tank = tankToDispatch, position = 0f))

        _state.update {
            it?.copy(
                tray = updatedTray,
                track = updatedTrack
            )
        }
    }

    fun tick(dtMs: Long) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return

        val level = currentState.level
        val cols = level.cols
        val rows = level.rows
        val loopLen = 2 * (cols + rows)
        val speedPerMs = loopLen.toFloat() / 4000f
        val movement = speedPerMs * dtMs

        val currentGrid = level.grid.map { it.toMutableList() }.toMutableList()
        var cubesRemaining = currentState.cubesRemaining
        var score = currentState.score

        val updatedTrack = mutableListOf<TrackTank>()
        val returnedTanks = mutableListOf<Tank>()

        for (trackTank in currentState.track) {
            val oldPos = trackTank.position
            val newPos = oldPos + movement

            if (newPos >= loopLen) {
                if (trackTank.tank.ammo > 0) {
                    returnedTanks.add(trackTank.tank)
                }
            } else {
                val oldCell = oldPos.toInt()
                val newCell = newPos.toInt()

                var tankToKeep = trackTank.copy(position = newPos)

                if (newCell != oldCell) {
                    val cellIdx = newCell % loopLen
                    val targetCoords = getFacingCell(cellIdx, cols, rows)
                    if (targetCoords != null) {
                        val (tr, tc) = findFirstCube(currentGrid, targetCoords.first, targetCoords.second, targetCoords.third, cols, rows)
                        if (tr != null && tc != null) {
                            val cubeColor = currentGrid[tr][tc]
                            if (cubeColor == tankToKeep.tank.color && tankToKeep.tank.ammo > 0) {
                                currentGrid[tr][tc] = null
                                cubesRemaining = currentGrid.sumOf { r -> r.count { it != null } }
                                val updatedTank = tankToKeep.tank.copy(ammo = tankToKeep.tank.ammo - 1)
                                tankToKeep = tankToKeep.copy(tank = updatedTank)
                                score += 10
                            }
                        }
                    }
                }
                updatedTrack.add(tankToKeep)
            }
        }

        val updatedTray = currentState.tray.toMutableList()
        for (t in returnedTanks) {
            updatedTray.add(t)
        }

        val isWon = cubesRemaining == 0
        // Lose when tray overflows (> 5) OR if the tray is empty AND track is empty AND there are cubes left
        val isGameOver = updatedTray.size > 5 || (updatedTray.isEmpty() && updatedTrack.isEmpty() && cubesRemaining > 0)

        if (isWon && !currentState.isWon && mode == "daily") {
            val today = LocalDate.now(ZoneOffset.UTC).toEpochDay()
            streakRepository?.let { repo ->
                val streak = repo.getStreak("cubeshooter")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    repo.saveStreak(newStreak)
                }
            }
        }

        _state.update {
            it?.copy(
                level = level.copy(grid = currentGrid),
                tray = updatedTray,
                track = updatedTrack,
                cubesRemaining = cubesRemaining,
                score = score,
                isWon = isWon,
                isGameOver = isGameOver
            )
        }
    }

    private fun getFacingCell(cellIdx: Int, cols: Int, rows: Int): Triple<Int, Int, String>? {
        return when {
            cellIdx < cols -> {
                Triple(0, cellIdx, "DOWN")
            }
            cellIdx < cols + rows -> {
                Triple(cellIdx - cols, cols - 1, "LEFT")
            }
            cellIdx < 2 * cols + rows -> {
                val c = (cols - 1) - (cellIdx - (cols + rows))
                Triple(rows - 1, c, "UP")
            }
            cellIdx < 2 * cols + 2 * rows -> {
                val r = (rows - 1) - (cellIdx - (2 * cols + rows))
                Triple(r, 0, "RIGHT")
            }
            else -> null
        }
    }

    private fun findFirstCube(
        grid: List<List<Int?>>,
        startRow: Int,
        startCol: Int,
        direction: String,
        cols: Int,
        rows: Int
    ): Pair<Int?, Int?> {
        when (direction) {
            "DOWN" -> {
                for (r in 0 until rows) {
                    if (grid[r][startCol] != null) return Pair(r, startCol)
                }
            }
            "LEFT" -> {
                for (c in cols - 1 downTo 0) {
                    if (grid[startRow][c] != null) return Pair(startRow, c)
                }
            }
            "UP" -> {
                for (r in rows - 1 downTo 0) {
                    if (grid[r][startCol] != null) return Pair(r, startCol)
                }
            }
            "RIGHT" -> {
                for (c in 0 until cols) {
                    if (grid[startRow][c] != null) return Pair(startRow, c)
                }
            }
        }
        return Pair(null, null)
    }
}

class CubeShooterViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CubeShooterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CubeShooterViewModel(streakRepository, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
