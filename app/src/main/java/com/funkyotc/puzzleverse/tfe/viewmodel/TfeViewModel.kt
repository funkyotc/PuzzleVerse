package com.funkyotc.puzzleverse.tfe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.tfe.data.Direction
import com.funkyotc.puzzleverse.tfe.data.TfeState
import com.funkyotc.puzzleverse.tfe.data.Tile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TfeViewModel(
    private val streakRepository: StreakRepository? = null,
    private val mode: String? = "standard"
) : ViewModel() {
    private val _state = MutableStateFlow(TfeState())
    val state: StateFlow<TfeState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        _state.value = TfeState(tiles = emptyList())
        addRandomTile()
        addRandomTile()
    }

    private fun addRandomTile() {
        val current = _state.value
        if (!current.hasEmptyCell()) return
        
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (r in 0..3) {
            for (c in 0..3) {
                if (current.tileAt(r, c) == null) {
                    emptyCells.add(Pair(r, c))
                }
            }
        }
        
        if (emptyCells.isEmpty()) return
        
        val (r, c) = emptyCells.random()
        val newValue = if (Math.random() < 0.9) 2 else 4
        
        val newTile = Tile(id = Tile.createId(), value = newValue, row = r, col = c, isNew = true)
        val newTiles = current.tiles + newTile
        
        _state.update { it.copy(tiles = newTiles) }
    }

    fun move(direction: Direction) {
        val current = _state.value
        if (current.isGameOver) return
        
        // Remove 'isNew' and 'isMerged' flags from previous moves
        var workingTiles = current.tiles.map { it.copy(isNew = false, isMerged = false) }
        var moved = false
        var newScore = current.score
        
        // Helper to process a line
        fun slideLine(lineTiles: List<Tile>, isReverse: Boolean): Pair<List<Tile>, Int> {
            val ordered = if (isReverse) lineTiles.sortedByDescending { if (direction == Direction.UP || direction == Direction.DOWN) it.row else it.col } 
                          else lineTiles.sortedBy { if (direction == Direction.UP || direction == Direction.DOWN) it.row else it.col }
            
            val result = mutableListOf<Tile>()
            var scoreAdd = 0
            var i = 0
            var currentTarget = if (isReverse) 3 else 0
            val step = if (isReverse) -1 else 1

            while (i < ordered.size) {
                val currentTile = ordered[i]
                if (i < ordered.size - 1 && currentTile.value == ordered[i+1].value) {
                    // Merge
                    val nextTile = ordered[i+1]
                    val mergedValue = currentTile.value * 2
                    scoreAdd += mergedValue
                    // Important: Instead of deleting them immediately, we could create a new merged tile.
                    // For Compose animation, it's best to transition the two old tiles into one visually.
                    // For now, we reuse the first tile's ID and double its value, deleting the second.
                    val newRow = if (direction == Direction.UP || direction == Direction.DOWN) currentTarget else currentTile.row
                    val newCol = if (direction == Direction.LEFT || direction == Direction.RIGHT) currentTarget else currentTile.col
                    
                    result.add(currentTile.copy(value = mergedValue, row = newRow, col = newCol, isMerged = true))
                    
                    i += 2
                } else {
                    // Slide
                    val newRow = if (direction == Direction.UP || direction == Direction.DOWN) currentTarget else currentTile.row
                    val newCol = if (direction == Direction.LEFT || direction == Direction.RIGHT) currentTarget else currentTile.col
                    
                    result.add(currentTile.copy(row = newRow, col = newCol))
                    i++
                }
                currentTarget += step
            }
            return Pair(result, scoreAdd)
        }

        val newTilesMerged = mutableListOf<Tile>()
        
        when (direction) {
            Direction.LEFT -> {
                for (r in 0..3) {
                    val line = workingTiles.filter { it.row == r }
                    val (res, addScore) = slideLine(line, false)
                    newTilesMerged.addAll(res)
                    newScore += addScore
                }
            }
            Direction.RIGHT -> {
                for (r in 0..3) {
                    val line = workingTiles.filter { it.row == r }
                    val (res, addScore) = slideLine(line, true)
                    newTilesMerged.addAll(res)
                    newScore += addScore
                }
            }
            Direction.UP -> {
                for (c in 0..3) {
                    val line = workingTiles.filter { it.col == c }
                    val (res, addScore) = slideLine(line, false)
                    newTilesMerged.addAll(res)
                    newScore += addScore
                }
            }
            Direction.DOWN -> {
                for (c in 0..3) {
                    val line = workingTiles.filter { it.col == c }
                    val (res, addScore) = slideLine(line, true)
                    newTilesMerged.addAll(res)
                    newScore += addScore
                }
            }
        }
        
        // Check if anything moved by comparing row/col/value
        moved = workingTiles.size != newTilesMerged.size || workingTiles.any { oldT ->
            val newT = newTilesMerged.find { it.id == oldT.id }
            newT == null || newT.row != oldT.row || newT.col != oldT.col || newT.value != oldT.value
        }
        
        if (moved) {
            _state.update { it.copy(tiles = newTilesMerged, score = newScore) }
            addRandomTile()
            checkGameOver()
        }
    }

    private fun checkGameOver() {
        val st = _state.value
        var canMove = false
        if (st.tiles.size < 16) {
            canMove = true
        } else {
            // Check neighbors
            for (r in 0..3) {
                for (c in 0..3) {
                    val t1 = st.tileAt(r, c)
                    val right = st.tileAt(r, c + 1)
                    val down = st.tileAt(r + 1, c)
                    if (right != null && t1?.value == right.value) canMove = true
                    if (down != null && t1?.value == down.value) canMove = true
                }
            }
        }
        val isWon = st.tiles.any { it.value >= 2048 }
        _state.update { it.copy(isGameOver = !canMove, isWon = isWon) }
    }
}

class TfeViewModelFactory(
    private val streakRepository: StreakRepository,
    private val mode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TfeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TfeViewModel(streakRepository, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
