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

        val numCols = 3
        val sourceCols = List(numCols) { mutableListOf<Tank>() }
        level.tray.forEachIndexed { index, tank ->
            sourceCols[index % numCols].add(tank)
        }

        _state.value = CubeShooterState(
            level = level,
            sourceColumns = sourceCols,
            storageTray = emptyList(),
            track = emptyList(),
            cubesRemaining = cubesRemaining,
            score = 0,
            isWon = false,
            isGameOver = false,
            projectiles = emptyList(),
            fadingCubes = emptyList()
        )
    }

    fun dispatchFromSource(colIndex: Int) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return
        if (currentState.track.size + currentState.transitions.size >= 5) return
        if (currentState.transitions.any { it.fromCol == colIndex }) return
        if (colIndex !in currentState.sourceColumns.indices) return

        val column = currentState.sourceColumns[colIndex]
        if (column.isEmpty()) return

        val tankToDispatch = column.last()
        if (tankToDispatch.ammo <= 0) return

        val updatedSourceColumns = currentState.sourceColumns.map { it.toMutableList() }
        updatedSourceColumns[colIndex].removeAt(column.lastIndex)

        val transition = TankTransition(
            id = java.util.UUID.randomUUID().toString(),
            tank = tankToDispatch,
            fromCol = colIndex
        )

        _state.update {
            it?.copy(
                sourceColumns = updatedSourceColumns,
                transitions = it.transitions + transition
            )
        }
    }

    fun dispatchFromStorage(index: Int) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return
        if (currentState.track.size + currentState.transitions.size >= 5) return
        if (index !in currentState.storageTray.indices) return

        val tankToDispatch = currentState.storageTray[index]
        if (tankToDispatch.ammo <= 0) return

        val updatedStorage = currentState.storageTray.toMutableList()
        updatedStorage.removeAt(index)

        val transition = TankTransition(
            id = java.util.UUID.randomUUID().toString(),
            tank = tankToDispatch,
            fromTraySlot = index
        )

        _state.update {
            it?.copy(
                storageTray = updatedStorage,
                transitions = it.transitions + transition
            )
        }
    }

    fun completeTransition(id: String) {
        _state.update { currentState ->
            if (currentState == null) return@update null
            val transition = currentState.transitions.find { it.id == id } ?: return@update currentState
            if (currentState.isWon || currentState.isGameOver) return@update currentState

            val bottomMiddleIndex = getBottomMiddleTrackIndex(currentState.level.cols, currentState.level.rows)

            val (finalTank, updatedGrid, fireResult) = checkImmediateFireOnDispatch(
                tank = transition.tank, bottomMiddleIndex = bottomMiddleIndex,
                cols = currentState.level.cols, rows = currentState.level.rows,
                grid = currentState.level.grid,
                projectiles = currentState.projectiles,
                fadingCubes = currentState.fadingCubes,
                score = currentState.score,
                cubesRemaining = currentState.cubesRemaining
            )

            val updatedTrack = currentState.track.toMutableList()
            if (finalTank.ammo > 0) {
                updatedTrack.add(TrackTank(tank = finalTank, position = bottomMiddleIndex.toFloat()))
            }

            val updatedTransitions = currentState.transitions.filter { it.id != id }

            val isWon = fireResult.cubesRemaining == 0
            val sourceIsEmpty = currentState.sourceColumns.all { col -> col.isEmpty() }
            val totalAmmoOnTrack = updatedTrack.sumOf { it.tank.ammo } + updatedTransitions.sumOf { it.tank.ammo }
            val isGameOver = currentState.storageTray.size > 5 || (sourceIsEmpty && currentState.storageTray.isEmpty() && totalAmmoOnTrack == 0 && fireResult.cubesRemaining > 0)

            currentState.copy(
                level = currentState.level.copy(grid = updatedGrid),
                track = updatedTrack,
                transitions = updatedTransitions,
                cubesRemaining = fireResult.cubesRemaining,
                score = fireResult.score,
                projectiles = fireResult.projectiles,
                fadingCubes = fireResult.fadingCubes,
                isWon = isWon,
                isGameOver = isGameOver
            )
        }
    }

    fun completeReturn(id: String) {
        _state.update { currentState ->
            if (currentState == null) return@update null
            val ret = currentState.returns.find { it.id == id } ?: return@update currentState
            val updatedStorage = currentState.storageTray.toMutableList()
            updatedStorage.add(ret.tank)
            val updatedReturns = currentState.returns.filter { it.id != id }
            val sourceIsEmpty = currentState.sourceColumns.all { it.isEmpty() }
            val totalAmmoOnTrack = currentState.track.sumOf { it.tank.ammo } + currentState.transitions.sumOf { it.tank.ammo }
            val isGameOver = updatedStorage.size > 5 || (sourceIsEmpty && updatedStorage.isEmpty() && totalAmmoOnTrack == 0 && currentState.cubesRemaining > 0)
            currentState.copy(
                storageTray = updatedStorage,
                returns = updatedReturns,
                isGameOver = isGameOver
            )
        }
    }

    private fun checkImmediateFireOnDispatch(
        tank: Tank,
        bottomMiddleIndex: Int,
        cols: Int,
        rows: Int,
        grid: List<List<Int?>>,
        projectiles: List<Projectile>,
        fadingCubes: List<FadingCube>,
        score: Int,
        cubesRemaining: Int
    ): Triple<Tank, List<List<Int?>>, FireDispatchResult> {
        val currentGrid = grid.map { it.toMutableList() }.toMutableList()
        val updatedProjectiles = projectiles.toMutableList()
        val updatedFadingCubes = fadingCubes.toMutableList()
        var updatedScore = score
        var updatedCubesRemaining = cubesRemaining
        var updatedTank = tank

        val targetCoords = getFacingCell(bottomMiddleIndex, cols, rows)
        if (targetCoords != null) {
            val (tr, tc) = findFirstCube(currentGrid, targetCoords.first, targetCoords.second, targetCoords.third, cols, rows, projectiles)
            if (tr != null && tc != null) {
                val cubeColor = currentGrid[tr][tc]
                if (cubeColor == updatedTank.color && updatedTank.ammo > 0) {
                    updatedTank = updatedTank.copy(ammo = updatedTank.ammo - 1)

                    val tankCoord = getTrackCellCoordinates(bottomMiddleIndex, cols, rows)
                    updatedProjectiles.add(
                        Projectile(
                            id = java.util.UUID.randomUUID().toString(),
                            startCol = tankCoord.second.toFloat(),
                            startRow = tankCoord.first.toFloat(),
                            endCol = tc + 1f,
                            endRow = tr + 1f,
                            color = cubeColor,
                            progress = 0f
                        )
                    )
                }
            }
        }

        return Triple(
            updatedTank,
            currentGrid,
            FireDispatchResult(
                projectiles = updatedProjectiles,
                fadingCubes = updatedFadingCubes,
                score = updatedScore,
                cubesRemaining = updatedCubesRemaining
            )
        )
    }

    private data class FireDispatchResult(
        val projectiles: List<Projectile>,
        val fadingCubes: List<FadingCube>,
        val score: Int,
        val cubesRemaining: Int
    )

    fun tick(dtMs: Long) {
        val currentState = _state.value ?: return
        if (currentState.isWon || currentState.isGameOver) return

        val level = currentState.level
        val cols = level.cols
        val rows = level.rows
        val loopLen = 2 * (cols + rows)
        val speedPerMs = loopLen.toFloat() / 4000f
        val movement = speedPerMs * dtMs

        var currentGrid: MutableList<MutableList<Int?>>? = null
        var cubesRemaining = currentState.cubesRemaining
        var score = currentState.score

        val updatedTrack = mutableListOf<TrackTank>()

        // Update existing fading cubes progress
        val updatedFadingCubes = currentState.fadingCubes.map {
            it.copy(progress = it.progress + dtMs.toFloat() / 250f)
        }.filter { it.progress < 1f }.toMutableList()

        // Update projectiles progress and trigger hit on arrival
        val updatedProjectiles = mutableListOf<Projectile>()
        for (p in currentState.projectiles) {
            val dx = p.endCol - p.startCol
            val dy = p.endRow - p.startRow
            val distance = kotlin.math.hypot(dx, dy)
            val speed = 0.04f // cells per millisecond
            val progressIncrement = if (distance > 0f) (dtMs * speed) / distance else 1f
            val nextProgress = p.progress + progressIncrement
            if (nextProgress >= 1f) {
                val tr = (p.endRow - 1f).toInt()
                val tc = (p.endCol - 1f).toInt()
                val mutGrid = currentGrid ?: level.grid.map { it.toMutableList() }.toMutableList().also { currentGrid = it }
                mutGrid[tr][tc] = null
                cubesRemaining = mutGrid.sumOf { r -> r.count { it != null } }
                score += 10
                updatedFadingCubes.add(FadingCube(tr, tc, p.color, 0f))
            } else {
                updatedProjectiles.add(p.copy(progress = nextProgress))
            }
        }

        val bottomMiddleIndex = getBottomMiddleTrackIndex(cols, rows)

        val newReturns = mutableListOf<TankReturn>()
        for (trackTank in currentState.track) {
            val oldPos = trackTank.position
            val newPos = oldPos + movement

            if (newPos >= bottomMiddleIndex + loopLen) {
                if (trackTank.tank.ammo > 0) {
                    newReturns.add(TankReturn(
                        id = java.util.UUID.randomUUID().toString(),
                        tank = trackTank.tank
                    ))
                }
            } else {
                val oldCell = oldPos.toInt()
                val newCell = newPos.toInt()

                var tankToKeep = trackTank.copy(position = newPos)

                if (newCell != oldCell) {
                    val cellIdx = newCell % loopLen
                    val targetCoords = getFacingCell(cellIdx, cols, rows)
                    if (targetCoords != null) {
                        val gridToRead = currentGrid ?: level.grid
                        val (tr, tc) = findFirstCube(gridToRead, targetCoords.first, targetCoords.second, targetCoords.third, cols, rows, updatedProjectiles)
                        if (tr != null && tc != null) {
                            val cubeColor = gridToRead[tr][tc]
                            if (cubeColor == tankToKeep.tank.color && tankToKeep.tank.ammo > 0) {
                                val updatedTank = tankToKeep.tank.copy(ammo = tankToKeep.tank.ammo - 1)
                                tankToKeep = tankToKeep.copy(tank = updatedTank)

                                val tankCoord = getTrackCellCoordinates(cellIdx, cols, rows)
                                updatedProjectiles.add(
                                    Projectile(
                                        id = java.util.UUID.randomUUID().toString(),
                                        startCol = tankCoord.second.toFloat(),
                                        startRow = tankCoord.first.toFloat(),
                                        endCol = tc + 1f,
                                        endRow = tr + 1f,
                                        color = cubeColor,
                                        progress = 0f
                                    )
                                )
                            }
                        }
                    }
                }
                if (tankToKeep.tank.ammo > 0) {
                    updatedTrack.add(tankToKeep)
                }
            }
        }

        val allReturns = currentState.returns + newReturns
        val isWon = cubesRemaining == 0
        val sourceIsEmpty = currentState.sourceColumns.all { col -> col.isEmpty() }
        val totalAmmoOnTrack = updatedTrack.sumOf { it.tank.ammo } + currentState.transitions.sumOf { it.tank.ammo }
        val isGameOver = (currentState.storageTray.size + allReturns.size) > 5 || (sourceIsEmpty && currentState.storageTray.isEmpty() && totalAmmoOnTrack == 0 && cubesRemaining > 0)

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
                level = level.copy(grid = currentGrid ?: level.grid),
                sourceColumns = currentState.sourceColumns,
                storageTray = currentState.storageTray,
                track = updatedTrack,
                cubesRemaining = cubesRemaining,
                score = score,
                isWon = isWon,
                isGameOver = isGameOver,
                projectiles = updatedProjectiles,
                fadingCubes = updatedFadingCubes,
                returns = allReturns
            )
        }
    }

    private fun getBottomMiddleTrackIndex(cols: Int, rows: Int): Int {
        val middleCol = (cols + 1) / 2
        return getTrackIndex(rows + 1, middleCol, cols, rows)
    }

    private fun getTrackIndex(r: Int, c: Int, cols: Int, rows: Int): Int {
        return when {
            r == 0 && c in 1..cols -> {
                c - 1
            }
            r in 1..rows && c == cols + 1 -> {
                cols + (r - 1)
            }
            r == rows + 1 && c in 1..cols -> {
                (cols + rows) + (cols - c)
            }
            r in 1..rows && c == 0 -> {
                (2 * cols + rows) + (rows - r)
            }
            else -> -1
        }
    }

    private fun getTrackCellCoordinates(index: Int, cols: Int, rows: Int): Pair<Int, Int> {
        val loopLen = 2 * (cols + rows)
        val idx = (index % loopLen + loopLen) % loopLen
        return when {
            idx < cols -> {
                Pair(0, idx + 1)
            }
            idx < cols + rows -> {
                val r = idx - cols + 1
                Pair(r, cols + 1)
            }
            idx < 2 * cols + rows -> {
                val c = cols - (idx - (cols + rows))
                Pair(rows + 1, c)
            }
            else -> {
                val r = rows - (idx - (2 * cols + rows))
                Pair(r, 0)
            }
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
        rows: Int,
        projectiles: List<Projectile>
    ): Pair<Int?, Int?> {
        val targeted = projectiles.map {
            Pair((it.endRow - 1f).toInt(), (it.endCol - 1f).toInt())
        }.toSet()

        when (direction) {
            "DOWN" -> {
                for (r in 0 until rows) {
                    if (grid[r][startCol] != null && !targeted.contains(Pair(r, startCol))) return Pair(r, startCol)
                }
            }
            "LEFT" -> {
                for (c in cols - 1 downTo 0) {
                    if (grid[startRow][c] != null && !targeted.contains(Pair(startRow, c))) return Pair(startRow, c)
                }
            }
            "UP" -> {
                for (r in rows - 1 downTo 0) {
                    if (grid[r][startCol] != null && !targeted.contains(Pair(r, startCol))) return Pair(r, startCol)
                }
            }
            "RIGHT" -> {
                for (c in 0 until cols) {
                    if (grid[startRow][c] != null && !targeted.contains(Pair(startRow, c))) return Pair(startRow, c)
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
