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
        if ("$row,$col" in current.removedPins) return

        val newGrid = current.grid.map { it.toMutableList() }.toMutableList()
        newGrid[row][col] = Cell(CellType.EMPTY)
        val newRemovedPins = current.removedPins + "$row,$col"

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
        val rows = grid.size
        val cols = grid[0].size
        val workGrid = grid.map { it.toMutableList() }.toMutableList()

        val resultBalls = balls.map { it.copy() }.toMutableList()

        // Move the lowest balls first. A ball above must see the space that a
        // lower ball has just vacated for a cascade to resolve correctly.
        val orderedIndices = resultBalls.indices.sortedByDescending { resultBalls[it].row }
        for (i in orderedIndices) {
            val b = resultBalls[i]
            if (b.inCup) continue

            workGrid[b.row][b.col] = Cell(CellType.EMPTY)

            val finalPos = dropBall(b.row, b.col, b.color, workGrid, rows, cols)
            resultBalls[i] = finalPos

            if (finalPos.inCup) {
                workGrid[finalPos.row][finalPos.col] = Cell(CellType.CUP, finalPos.color)
            } else {
                workGrid[finalPos.row][finalPos.col] = Cell(CellType.BALL, finalPos.color)
            }
        }

        return Pair(workGrid.map { it.toList() }, resultBalls)
    }

    private fun dropBall(
        startRow: Int,
        startCol: Int,
        color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int,
        cols: Int
    ): BallState {
        if (grid[startRow][startCol].type == CellType.CUP && grid[startRow][startCol].color == color) {
            return BallState(startRow, startCol, color, inCup = true)
        }

        val visited = mutableSetOf(startRow to startCol)
        var r = startRow
        var c = startCol

        while (true) {
            val moved = step(r, c, color, grid, rows, cols, visited)
            if (moved == null) break
            r = moved.first
            c = moved.second
            visited.add(r to c)

            if (grid[r][c].type == CellType.CUP && grid[r][c].color == color) {
                return BallState(r, c, color, inCup = true)
            }
        }

        return BallState(row = r, col = c, color = color, inCup = false)
    }

    private fun step(
        r: Int, c: Int, color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int, cols: Int,
        visited: MutableSet<Pair<Int, Int>>
    ): Pair<Int, Int>? {
        val tryMoves = listOf(
            ::moveDown,
            ::moveDownLeft,
            ::moveDownRight,
            ::moveLeft,
            ::moveRight
        )

        for (move in tryMoves) {
            val result = move(r, c, color, grid, rows, cols, visited)
            if (result != null) return result
        }
        return null
    }

    private fun moveDown(
        r: Int, c: Int, color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int, cols: Int,
        visited: MutableSet<Pair<Int, Int>>
    ): Pair<Int, Int>? {
        val nr = r + 1
        if (nr >= rows) return null
        if (!canEnter(grid[nr][c], color)) return null
        return nr to c
    }

    private fun canEnter(cell: Cell, ballColor: Int): Boolean {
        return cell.type == CellType.EMPTY ||
                (cell.type == CellType.CUP && cell.color == ballColor)
    }

    private fun moveDownLeft(
        r: Int, c: Int, color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int, cols: Int,
        visited: MutableSet<Pair<Int, Int>>
    ): Pair<Int, Int>? {
        if (r + 1 >= rows) return null
        val nc = c - 1
        if (nc < 0) return null
        if (!canEnter(grid[r][nc], color)) return null
        if (r to nc in visited) return null
        if (!canEnter(grid[r + 1][nc], color)) return null
        return r to nc
    }

    private fun moveDownRight(
        r: Int, c: Int, color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int, cols: Int,
        visited: MutableSet<Pair<Int, Int>>
    ): Pair<Int, Int>? {
        if (r + 1 >= rows) return null
        val nc = c + 1
        if (nc >= cols) return null
        if (!canEnter(grid[r][nc], color)) return null
        if (r to nc in visited) return null
        if (!canEnter(grid[r + 1][nc], color)) return null
        return r to nc
    }

    private fun moveLeft(
        r: Int, c: Int, color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int, cols: Int,
        visited: MutableSet<Pair<Int, Int>>
    ): Pair<Int, Int>? {
        val nc = c - 1
        if (nc < 0) return null
        if (!canEnter(grid[r][nc], color)) return null
        if (r to nc in visited) return null
        return r to nc
    }

    private fun moveRight(
        r: Int, c: Int, color: Int,
        grid: MutableList<MutableList<Cell>>,
        rows: Int, cols: Int,
        visited: MutableSet<Pair<Int, Int>>
    ): Pair<Int, Int>? {
        val nc = c + 1
        if (nc >= cols) return null
        if (!canEnter(grid[r][nc], color)) return null
        if (r to nc in visited) return null
        return r to nc
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
