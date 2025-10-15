package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.bonza.data.BonzaRepository
import com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator
import com.funkyotc.puzzleverse.bonza.ui.DraggableWord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import kotlin.math.roundToInt

class BonzaViewModel(context: Context, private val mode: String?) : ViewModel() {

    private val generator = BonzaPuzzleGenerator()
    private val repository = BonzaRepository(context)
    private val dailyChallengeSeed = LocalDate.now().toEpochDay()
    private val wordsKey = if (mode == "daily") "daily_bonza_words" else "standard_bonza_words"

    private val _draggableWords = MutableStateFlow<List<DraggableWord>>(emptyList())
    val draggableWords: StateFlow<List<DraggableWord>> = _draggableWords

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val _puzzleClue = MutableStateFlow("")
    val puzzleClue: StateFlow<String> = _puzzleClue

    init {
        loadPuzzle()
    }

    private fun loadPuzzle() {
        val loadedWords = repository.loadWords(wordsKey)
        if (loadedWords != null) {
            _draggableWords.value = loadedWords
            // TODO: This assumes the puzzle is the same. Need to store puzzle details too.
        } else {
            newGame()
        }
    }

    fun newGame() {
        val puzzle = if (mode == "daily") {
            generator.generate(dailyChallengeSeed)
        } else {
            generator.generate()
        }
        _puzzleClue.value = puzzle.clue
        _draggableWords.value = puzzle.words.map { DraggableWord(it) }
        repository.saveWords(_draggableWords.value, wordsKey)
        _isGameWon.value = false
    }

    fun onDrag(wordId: String, offsetX: Dp, offsetY: Dp) {
        if (isGameWon.value) return

        val draggedWord = _draggableWords.value.find { it.id == wordId } ?: return
        val dragGroupId = draggedWord.groupId

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == dragGroupId) {
                it.copy(offsetX = it.offsetX + offsetX, offsetY = it.offsetY + offsetY)
            } else {
                it
            }
        }
    }

    fun onDragEnd(wordId: String, tileSize: Dp) {
        if (isGameWon.value) return

        val draggedWord = _draggableWords.value.find { it.id == wordId } ?: return

        // Check for connections and snap
        var didConnect = false
        val otherWords = _draggableWords.value.filter { it.groupId != draggedWord.groupId }

        for (otherWord in otherWords) {
            val intersection = findIntersection(draggedWord, otherWord, tileSize)
            if (intersection != null) {
                // Snap the dragged word into place
                snapToIntersection(draggedWord, otherWord, intersection, tileSize)
                mergeGroups(draggedWord, otherWord)
                didConnect = true
                break // Stop after the first connection
            }
        }

        if (!didConnect) {
            // If no connection, just snap the group to the grid
            snapGroupToGrid(draggedWord.groupId, tileSize)
        }

        repository.saveWords(_draggableWords.value, wordsKey)
        checkWinCondition()
    }

    private fun snapGroupToGrid(groupId: String, tileSize: Dp) {
        val groupWords = _draggableWords.value.filter { it.groupId == groupId }
        if (groupWords.isEmpty()) return

        val firstWord = groupWords.first()
        val snappedWord = snapToGrid(firstWord, tileSize)
        val dx = snappedWord.offsetX - firstWord.offsetX
        val dy = snappedWord.offsetY - firstWord.offsetY

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == groupId) {
                it.copy(offsetX = it.offsetX + dx, offsetY = it.offsetY + dy)
            } else {
                it
            }
        }
    }

    private fun snapToGrid(word: DraggableWord, tileSize: Dp): DraggableWord {
        val currentX = word.bonzaWord.x * tileSize.value + word.offsetX.value
        val currentY = word.bonzaWord.y * tileSize.value + word.offsetY.value

        val snappedGridX = (currentX / tileSize.value).roundToInt()
        val snappedGridY = (currentY / tileSize.value).roundToInt()

        val snappedX = snappedGridX * tileSize.value
        val snappedY = snappedGridY * tileSize.value

        val newOffsetX = snappedX - word.bonzaWord.x * tileSize.value
        val newOffsetY = snappedY - word.bonzaWord.y * tileSize.value

        return word.copy(offsetX = newOffsetX.dp, offsetY = newOffsetY.dp)
    }

    private fun findIntersection(word1: DraggableWord, word2: DraggableWord, tileSize: Dp): Pair<Int, Int>? {
        if (word1.bonzaWord.isHorizontal == word2.bonzaWord.isHorizontal) return null

        val hWord = if (word1.bonzaWord.isHorizontal) word1 else word2
        val vWord = if (word1.bonzaWord.isHorizontal) word2 else word1

        val hWordX = (hWord.bonzaWord.x * tileSize.value + hWord.offsetX.value).roundToInt()
        val hWordY = (hWord.bonzaWord.y * tileSize.value + hWord.offsetY.value).roundToInt()
        val vWordX = (vWord.bonzaWord.x * tileSize.value + vWord.offsetX.value).roundToInt()
        val vWordY = (vWord.bonzaWord.y * tileSize.value + vWord.offsetY.value).roundToInt()

        val xOffset = vWordX - hWordX
        val yOffset = hWordY - vWordY

        val hIndex = xOffset / tileSize.value.roundToInt()
        val vIndex = yOffset / tileSize.value.roundToInt()

        if (hIndex in hWord.bonzaWord.letters.indices && vIndex in vWord.bonzaWord.letters.indices) {
            if (hWord.bonzaWord.letters[hIndex] == vWord.bonzaWord.letters[vIndex]) {
                if (hWord == word1) {
                    return Pair(hIndex, vIndex)
                } else {
                    return Pair(vIndex, hIndex)
                }
            }
        }

        return null
    }

    private fun snapToIntersection(draggedWord: DraggableWord, otherWord: DraggableWord, intersection: Pair<Int, Int>, tileSize: Dp) {
        val draggedIndex = intersection.first
        val otherIndex = intersection.second

        val newOffsetX: Dp
        val newOffsetY: Dp

        if (draggedWord.bonzaWord.isHorizontal) {
            // Dragged is Horizontal, Other is Vertical
            val targetXValue = otherWord.offsetX.value + (otherWord.bonzaWord.x - draggedWord.bonzaWord.x - draggedIndex) * tileSize.value
            val targetYValue = otherWord.offsetY.value + (otherWord.bonzaWord.y + otherIndex - draggedWord.bonzaWord.y) * tileSize.value
            newOffsetX = targetXValue.dp
            newOffsetY = targetYValue.dp
        } else {
            // Dragged is Vertical, Other is Horizontal
            val targetXValue = otherWord.offsetX.value + (otherWord.bonzaWord.x + otherIndex - draggedWord.bonzaWord.x) * tileSize.value
            val targetYValue = otherWord.offsetY.value + (otherWord.bonzaWord.y - draggedWord.bonzaWord.y - draggedIndex) * tileSize.value
            newOffsetX = targetXValue.dp
            newOffsetY = targetYValue.dp
        }

        val dx = newOffsetX - draggedWord.offsetX
        val dy = newOffsetY - draggedWord.offsetY

        // Apply the same delta to all words in the dragged group
        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == draggedWord.groupId) {
                it.copy(offsetX = it.offsetX + dx, offsetY = it.offsetY + dy)
            } else {
                it
            }
        }
    }


    private fun mergeGroups(word1: DraggableWord, word2: DraggableWord) {
        val group1 = word1.groupId
        val group2 = word2.groupId

        if (group1 == group2) return

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == group2) {
                it.copy(groupId = group1)
            } else {
                it
            }
        }
    }

    private fun checkWinCondition() {
        val allConnected = _draggableWords.value.all { it.groupId == _draggableWords.value.first().groupId }
        _isGameWon.value = allConnected
    }
}
