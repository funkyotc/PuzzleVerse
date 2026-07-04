package com.funkyotc.puzzleverse.pullpin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository
import com.funkyotc.puzzleverse.core.todayEpochDay
import com.funkyotc.puzzleverse.pullpin.data.BallState
import com.funkyotc.puzzleverse.pullpin.data.Cell
import com.funkyotc.puzzleverse.pullpin.data.CellType
import com.funkyotc.puzzleverse.pullpin.data.PullPinLevel
import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.pullpin.data.PullPinState
import com.funkyotc.puzzleverse.streak.data.Streak
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
            isWon = false
        )
    }

    fun removePin(row: Int, col: Int) {
        val current = _state.value ?: return
        if (current.isWon) return

        val cell = current.grid.getOrNull(row)?.getOrNull(col) ?: return
        if (cell.type != CellType.PIN) return
        if (Pair(row, col).toString() in current.removedPins) return

        val newGrid = current.grid.map { it.toMutableList() }.toMutableList()
        newGrid[row][col] = Cell(CellType.EMPTY)
        val newRemovedPins = current.removedPins + "${row},${col}"

        val (finalGrid, finalBalls) = simulateCascade(newGrid, current.balls)
        val won = finalBalls.all { it.inCup }

        _state.value = current.copy(
            grid = finalGrid,
            balls = finalBalls,
            removedPins = newRemovedPins,
            moves = current.moves + 1,
            isWon = won
        )

        if (won) {
            onWin()
        }
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

    internal fun simulateCascade(
        grid: List<List<Cell>>,
        balls: List<BallState>
    ): Pair<List<List<Cell>>, List<BallState>> {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        var currentBalls = balls.map { it.copy() }.toMutableList()
        val rows = newGrid.size
        val cols = newGrid[0].size

        var changed = true
        var iterations = 0
        val maxIterations = rows * cols * 2

        val remainingBalls = currentBalls.filter { !it.inCup }.toMutableList()
        val settledBalls = currentBalls.filter { it.inCup }.toMutableList()

        while (changed && iterations < maxIterations) {
            changed = false
            iterations++

            val toRemove = mutableListOf<BallState>()
            val toAdd = mutableListOf<BallState>()

            for (ball in remainingBalls) {
                if (ball.inCup) {
                    toRemove.add(ball)
                    settledBalls.add(ball)
                    continue
                }

                val moved = tryMoveBall(ball, newGrid, rows, cols)
                if (moved != null) {
                    newGrid[ball.row][ball.col] = Cell(CellType.EMPTY)
                    val landedOnCup = newGrid[moved.row][moved.col].type == CellType.CUP
                    val matchColor = !landedOnCup || newGrid[moved.row][moved.col].color == ball.color

                    if (landedOnCup && matchColor) {
                        newGrid[moved.row][moved.col] = Cell(CellType.CUP, ball.color)
                        toRemove.add(ball)
                        settledBalls.add(moved.copy(inCup = true))
                    } else {
                        newGrid[moved.row][moved.col] = Cell(CellType.BALL, ball.color)
                        toRemove.add(ball)
                        toAdd.add(moved)
                    }
                    changed = true
                }
            }

            remainingBalls.removeAll(toRemove)
            remainingBalls.addAll(toAdd)
            toRemove.clear()
            toAdd.clear()

            remainingBalls.removeAll { it.inCup }
        }

        return Pair(newGrid.map { it.toList() }, settledBalls + remainingBalls)
    }

    private fun tryMoveBall(
        ball: BallState,
        grid: MutableList<MutableList<Cell>>,
        rows: Int,
        cols: Int
    ): BallState? {
        val directions = listOf(
            Pair(1, 0),
            Pair(0, -1),
            Pair(0, 1)
        )

        for ((dr, dc) in directions) {
            val nr = ball.row + dr
            val nc = ball.col + dc

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue

            val target = grid[nr][nc]
            if (target.type == CellType.WALL) continue
            if (target.type == CellType.BALL) continue
            if (target.type == CellType.PIN) continue
            if (target.type == CellType.CUP && target.color != ball.color) continue

            return BallState(row = nr, col = nc, color = ball.color)
        }

        return null
    }
}

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
