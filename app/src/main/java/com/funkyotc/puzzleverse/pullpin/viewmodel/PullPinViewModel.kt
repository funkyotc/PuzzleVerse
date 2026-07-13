package com.funkyotc.puzzleverse.pullpin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.pullpin.data.BallState
import com.funkyotc.puzzleverse.pullpin.data.Cell
import com.funkyotc.puzzleverse.pullpin.data.CellType
import com.funkyotc.puzzleverse.pullpin.data.GameStatus
import com.funkyotc.puzzleverse.pullpin.data.PullPinLevel
import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PullPinViewModel(
    private val streakRepository: StreakRepository?,
    private val mode: String?,
    private val puzzleId: String?
) : ViewModel() {

    private val _state = MutableStateFlow<PullPinState?>(null)
    val state: StateFlow<PullPinState?> = _state.asStateFlow()

    private var completionRepo: PuzzleCompletionRepository? = null

    init {
        startNewGame()
    }

    fun setCompletionRepo(repo: PuzzleCompletionRepository) {
        completionRepo = repo
    }

    fun startNewGame() {
        val level = selectLevel()
        val balls = findBalls(level.grid)
        _state.value = PullPinState(
            level = level,
            grid = level.grid.map { it.toList() },
            balls = balls,
            removedPins = emptySet(),
            moves = 0,
            status = GameStatus.IDLE,
            lostReason = null
        )
    }

    fun removePin(row: Int, col: Int) {
        val current = _state.value ?: return
        if (current.status != GameStatus.IDLE) return

        val cell = current.grid.getOrNull(row)?.getOrNull(col) ?: return
        if (cell.type != CellType.PIN) return
        if ("$row,$col" in current.removedPins) return

        val newGrid = current.grid.map { it.toMutableList() }.toMutableList()
        newGrid[row][col] = Cell(CellType.EMPTY)
        val newRemovedPins = current.removedPins + "$row,$col"

        _state.value = current.copy(
            grid = newGrid,
            removedPins = newRemovedPins,
            moves = current.moves + 1,
            status = GameStatus.SIMULATING
        )

        startSimulationLoop()
    }

    private fun startSimulationLoop() {
        viewModelScope.launch {
            var anyMoved = true
            while (anyMoved) {
                delay(120) // tick interval
                val current = _state.value ?: break
                if (current.status != GameStatus.SIMULATING) break

                val (nextGrid, nextBalls, moved, lostReason) = stepPhysics(current.grid, current.balls)
                
                // Color propagation step
                val (coloredGrid, coloredBalls, colorChanged) = propagateColors(nextGrid, nextBalls)

                val didMove = moved || colorChanged

                val status = when {
                    lostReason != null -> GameStatus.LOST
                    coloredBalls.all { it.inCup } -> GameStatus.WON
                    !didMove -> {
                        // All balls have stopped moving.
                        // Check if we lost (some balls not in cup and no more pins to pull)
                        val hasRemainingPins = coloredGrid.any { r -> r.any { it.type == CellType.PIN } }
                        val allInCups = coloredBalls.all { it.inCup }
                        if (allInCups) {
                            GameStatus.WON
                        } else if (!hasRemainingPins) {
                            GameStatus.LOST
                        } else {
                            GameStatus.IDLE
                        }
                    }
                    else -> GameStatus.SIMULATING
                }

                val finalLostReason = if (status == GameStatus.LOST) {
                    lostReason ?: "Balls are stuck and no more pins can be pulled!"
                } else null

                _state.value = current.copy(
                    grid = coloredGrid,
                    balls = coloredBalls,
                    status = status,
                    lostReason = finalLostReason
                )

                if (status == GameStatus.WON) {
                    onWin()
                }

                anyMoved = didMove && status == GameStatus.SIMULATING
            }
        }
    }

    internal fun stepPhysics(
        grid: List<List<Cell>>,
        balls: List<BallState>
    ): PhysicsResult {
        val rows = grid.size
        val cols = grid[0].size
        val workGrid = grid.map { it.toMutableList() }.toMutableList()
        val nextBalls = balls.map { it.copy() }.toMutableList()
        var movedAny = false
        var lostReason: String? = null

        // Clear all non-inCup balls from the grid temporarily before moving them.
        for (b in balls) {
            if (!b.inCup && b.row < rows && b.col < cols) {
                workGrid[b.row][b.col] = Cell(CellType.EMPTY)
            }
        }

        // Process balls from bottom to top (highest row index first)
        val orderedIndices = nextBalls.indices.sortedByDescending { nextBalls[it].row }
        
        for (i in orderedIndices) {
            val b = nextBalls[i]
            if (b.inCup) continue

            val r = b.row
            val c = b.col
            val nr = r + 1

            // 1. Check out of bounds
            if (nr >= rows) {
                movedAny = true
                lostReason = "A ball fell out of bounds!"
                nextBalls[i] = b.copy(row = nr, inCup = false)
                continue
            }

            // 2. Check cell directly below
            val cellBelow = workGrid[nr][c]
            if (cellBelow.type == CellType.EMPTY) {
                movedAny = true
                nextBalls[i] = b.copy(row = nr)
                workGrid[nr][c] = Cell(CellType.BALL, b.color)
                continue
            } else if (cellBelow.type == CellType.CUP) {
                movedAny = true
                if (b.color == 0) {
                    lostReason = "A grey ball entered a cup without being colored!"
                } else if (cellBelow.color != b.color) {
                    lostReason = "A ball entered a cup of the wrong color!"
                }
                nextBalls[i] = b.copy(row = nr, inCup = true)
                continue
            }

            // 3. Below is blocked (WALL, PIN, BALL, or CUP). Check diagonal slides.
            // Check slide left:
            val canSlideLeft = c - 1 >= 0 &&
                    workGrid[r][c - 1].type == CellType.EMPTY &&
                    (workGrid[nr][c - 1].type == CellType.EMPTY || workGrid[nr][c - 1].type == CellType.CUP)

            // Check slide right:
            val canSlideRight = c + 1 < cols &&
                    workGrid[r][c + 1].type == CellType.EMPTY &&
                    (workGrid[nr][c + 1].type == CellType.EMPTY || workGrid[nr][c + 1].type == CellType.CUP)

            if (canSlideLeft && canSlideRight) {
                movedAny = true
                val goLeft = java.util.Random().nextBoolean()
                val targetCol = if (goLeft) c - 1 else c + 1
                val targetCell = workGrid[nr][targetCol]
                if (targetCell.type == CellType.CUP) {
                    if (b.color == 0) {
                        lostReason = "A grey ball entered a cup without being colored!"
                    } else if (targetCell.color != b.color) {
                        lostReason = "A ball entered a cup of the wrong color!"
                    }
                    nextBalls[i] = b.copy(row = nr, col = targetCol, inCup = true)
                } else {
                    nextBalls[i] = b.copy(row = nr, col = targetCol)
                    workGrid[nr][targetCol] = Cell(CellType.BALL, b.color)
                }
            } else if (canSlideLeft) {
                movedAny = true
                val targetCol = c - 1
                val targetCell = workGrid[nr][targetCol]
                if (targetCell.type == CellType.CUP) {
                    if (b.color == 0) {
                        lostReason = "A grey ball entered a cup without being colored!"
                    } else if (targetCell.color != b.color) {
                        lostReason = "A ball entered a cup of the wrong color!"
                    }
                    nextBalls[i] = b.copy(row = nr, col = targetCol, inCup = true)
                } else {
                    nextBalls[i] = b.copy(row = nr, col = targetCol)
                    workGrid[nr][targetCol] = Cell(CellType.BALL, b.color)
                }
            } else if (canSlideRight) {
                movedAny = true
                val targetCol = c + 1
                val targetCell = workGrid[nr][targetCol]
                if (targetCell.type == CellType.CUP) {
                    if (b.color == 0) {
                        lostReason = "A grey ball entered a cup without being colored!"
                    } else if (targetCell.color != b.color) {
                        lostReason = "A ball entered a cup of the wrong color!"
                    }
                    nextBalls[i] = b.copy(row = nr, col = targetCol, inCup = true)
                } else {
                    nextBalls[i] = b.copy(row = nr, col = targetCol)
                    workGrid[nr][targetCol] = Cell(CellType.BALL, b.color)
                }
            } else {
                // Cannot move. Keep position.
                workGrid[r][c] = Cell(CellType.BALL, b.color)
            }
        }

        return PhysicsResult(
            grid = workGrid.map { it.toList() },
            balls = nextBalls,
            moved = movedAny,
            lostReason = lostReason
        )
    }

    internal fun propagateColors(
        grid: List<List<Cell>>,
        balls: List<BallState>
    ): PropagationResult {
        val nextBalls = balls.map { it.copy() }.toMutableList()
        val workGrid = grid.map { it.toMutableList() }.toMutableList()
        var changedAny = false

        var changedThisPass = true
        while (changedThisPass) {
            changedThisPass = false
            for (i in nextBalls.indices) {
                val b = nextBalls[i]
                if (b.color == 0 && !b.inCup) {
                    val r = b.row
                    val c = b.col
                    
                    val neighborCoords = listOf(
                        r - 1 to c,
                        r + 1 to c,
                        r to c - 1,
                        r to c + 1
                    )

                    var newColor = 0
                    for ((nr, nc) in neighborCoords) {
                        if (nr in workGrid.indices && nc in workGrid[0].indices) {
                            val cell = workGrid[nr][nc]
                            if (cell.type == CellType.BALL && cell.color != null && cell.color > 0) {
                                newColor = cell.color
                                break
                            }
                        }
                    }

                    if (newColor > 0) {
                        nextBalls[i] = b.copy(color = newColor)
                        workGrid[r][c] = Cell(CellType.BALL, newColor)
                        changedAny = true
                        changedThisPass = true
                    }
                }
            }
        }

        return PropagationResult(
            grid = workGrid.map { it.toList() },
            balls = nextBalls,
            changed = changedAny
        )
    }

    private fun onWin() {
        val current = _state.value ?: return
        if (mode == "daily" && streakRepository != null) {
            val streak = streakRepository.getStreak("pullpin")
            val today = todayEpochDay()
            val newCount = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1
            streakRepository.saveStreak(
                streak.copy(count = newCount, lastCompletedEpochDay = today)
            )
        }
        if (mode == "puzzle" && puzzleId != null && completionRepo != null) {
            completionRepo!!.markCompleted(puzzleId)
        }
    }

    private fun selectLevel(): PullPinLevel {
        val allLevels = PullPinPregenerated.ALL_LEVELS
        if (allLevels.isEmpty()) error("No pullpin levels available")

        if (puzzleId != null) {
            return allLevels.find { it.id == puzzleId }
                ?: allLevels.first()
        }

        return when (mode) {
            "daily" -> {
                val today = todayEpochDay()
                allLevels[(today % allLevels.size).toInt()]
            }
            "easy" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Easy"]?.random() ?: allLevels.first()
            "medium" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Medium"]?.random() ?: allLevels.first()
            "hard" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Hard"]?.random() ?: allLevels.first()
            "expert" -> PullPinPregenerated.PUZZLES_BY_DIFFICULTY["Expert"]?.random() ?: allLevels.first()
            else -> allLevels.random()
        }
    }

    private fun findBalls(grid: List<List<Cell>>): List<BallState> {
        val balls = mutableListOf<BallState>()
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                val cell = grid[r][c]
                if (cell.type == CellType.BALL && cell.color != null) {
                    balls.add(BallState(row = r, col = c, color = cell.color))
                }
            }
        }
        return balls
    }
}

data class PhysicsResult(
    val grid: List<List<Cell>>,
    val balls: List<BallState>,
    val moved: Boolean,
    val lostReason: String?
)

data class PropagationResult(
    val grid: List<List<Cell>>,
    val balls: List<BallState>,
    val changed: Boolean
)

class PullPinViewModelFactory(
    private val streakRepository: StreakRepository?,
    private val mode: String?,
    private val puzzleId: String?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PullPinViewModel(streakRepository, mode, puzzleId) as T
    }
}
