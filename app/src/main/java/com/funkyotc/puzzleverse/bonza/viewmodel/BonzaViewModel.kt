package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import android.graphics.Rect
import androidx.lifecycle.ViewModel
import com.funkyotc.puzzleverse.bonza.data.BonzaRepository
import com.funkyotc.puzzleverse.bonza.data.DraggableWord
import com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import kotlin.collections.ArrayDeque
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

class BonzaViewModel(
    context: Context,
    private val mode: String?,
    forceNewGame: Boolean,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val generator = BonzaPuzzleGenerator()
    private val repository = BonzaRepository(context)
    private val dailyChallengeSeed = LocalDate.now().toEpochDay()
    private val wordsKey = if (mode == "daily") "daily_bonza_words" else "standard_bonza_words"
    private val clueKey = if (mode == "daily") "daily_bonza_clue" else "standard_bonza_clue"

    private val _draggableWords = MutableStateFlow<List<DraggableWord>>(emptyList())
    val draggableWords: StateFlow<List<DraggableWord>> = _draggableWords

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon

    private val _puzzleClue = MutableStateFlow("")
    val puzzleClue: StateFlow<String> = _puzzleClue

    private var puzzleWordCount = 0

    init {
        _draggableWords.value = generateInitialPuzzle(forceNewGame)
    }

    private fun generateInitialPuzzle(forceNewGame: Boolean): List<DraggableWord> {
        if (forceNewGame) {
            return newGame()
        }

        val loadedWords = repository.loadWords(wordsKey)
        if (loadedWords?.isNotEmpty() == true) {
            _puzzleClue.value = repository.loadClue(clueKey) ?: ""
            puzzleWordCount = loadedWords.size
            _isGameWon.value = false
            return loadedWords
        }

        return newGame()
    }

    fun newGame(): List<DraggableWord> {
        val puzzle = if (mode == "daily") {
            generator.generate(dailyChallengeSeed)
        } else {
            generator.generate()
        }
        _puzzleClue.value = puzzle.clue
        puzzleWordCount = puzzle.words.size

        val draggableWords = puzzle.words.map { DraggableWord(bonzaWord = it) }

        val adj = mutableMapOf<String, MutableList<String>>()
        draggableWords.forEach { word -> adj[word.id] = mutableListOf() }

        for (i in draggableWords.indices) {
            for (j in i + 1 until draggableWords.indices) {
                val word1 = draggableWords[i]
                val word2 = draggableWords[j]
                if (areWordsTouching(word1, word2)) {
                    adj[word1.id]?.add(word2.id)
                    adj[word2.id]?.add(word1.id)
                }
            }
        }

        val adjCopy = adj.mapValues { it.value.toMutableList() }
        adjCopy.forEach { (u, neighbors) ->
            neighbors.forEach { v ->
                if (u < v && Random.nextFloat() < 0.5f) {
                    adj[u]?.remove(v)
                    adj[v]?.remove(u)
                }
            }
        }

        val visited = mutableSetOf<String>()
        val wordToGroup = mutableMapOf<String, String>()
        var groupCount = 0
        draggableWords.forEach { word ->
            if (word.id !in visited) {
                val groupId = "group_${groupCount++}"
                val queue = ArrayDeque<String>().apply { add(word.id) }
                visited.add(word.id)

                while (queue.isNotEmpty()) {
                    val currentWordId = queue.removeFirst()
                    wordToGroup[currentWordId] = groupId
                    adj[currentWordId]?.forEach { neighborId ->
                        if (neighborId !in visited) {
                            visited.add(neighborId)
                            queue.add(neighborId)
                        }
                    }
                }
            }
        }

        val groupOffsets = mutableMapOf<String, Pair<Float, Float>>()
        val newDraggableWords = draggableWords.map { word ->
            val groupId = wordToGroup[word.id]!!
            val (offsetX, offsetY) = groupOffsets.getOrPut(groupId) {
                Pair(Random.nextInt(-800, 800).toFloat(), Random.nextInt(-800, 800).toFloat())
            }
            word.copy(groupId = groupId, offsetX = offsetX, offsetY = offsetY)
        }

        repository.saveWords(newDraggableWords, wordsKey)
        repository.saveClue(puzzle.clue, clueKey)
        _isGameWon.value = false
        return newDraggableWords
    }

    private fun areWordsTouching(word1: DraggableWord, word2: DraggableWord): Boolean {
        val rect1 = word1.bonzaWord.getRect()
        val rect2 = word2.bonzaWord.getRect()
        val expandedRect2 = Rect(rect2.left - 1, rect2.top - 1, rect2.right + 1, rect2.bottom + 1)
        return Rect.intersects(rect1, expandedRect2)
    }

    fun onDrag(wordId: String, dragAmountX: Float, dragAmountY: Float) {
        if (isGameWon.value) return

        val draggedWord = _draggableWords.value.find { it.id == wordId } ?: return
        val dragGroupId = draggedWord.groupId

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == dragGroupId) {
                it.copy(offsetX = it.offsetX + dragAmountX, offsetY = it.offsetY + dragAmountY)
            } else {
                it
            }
        }
    }

    fun onDragEnd(wordId: String, tileSizePx: Float) {
        if (isGameWon.value) return

        val draggedWord = _draggableWords.value.find { it.id == wordId } ?: return
        val dragGroupId = draggedWord.groupId

        var didConnect = false
        for (wordInGroup in _draggableWords.value.filter { it.groupId == dragGroupId }) {
            for (otherWord in _draggableWords.value.filter { it.groupId != dragGroupId }) {
                val intersection = findIntersection(wordInGroup, otherWord, tileSizePx)
                if (intersection != null) {
                    snapToIntersection(wordInGroup, otherWord, intersection, tileSizePx)
                    mergeGroups(dragGroupId, otherWord.groupId)
                    didConnect = true
                    break
                }
            }
            if (didConnect) break
        }

        if (!didConnect) {
            snapGroupToGrid(dragGroupId, tileSizePx)
        }

        repository.saveWords(_draggableWords.value, wordsKey)
        checkWinCondition()
    }

    private fun snapGroupToGrid(groupId: String, tileSize: Float) {
        val groupWords = _draggableWords.value.filter { it.groupId == groupId }
        if (groupWords.isEmpty()) return

        val firstWord = groupWords.first()
        val currentX = firstWord.bonzaWord.x * tileSize + firstWord.offsetX
        val currentY = firstWord.bonzaWord.y * tileSize + firstWord.offsetY

        val snappedGridX = (currentX / tileSize).roundToInt()
        val snappedGridY = (currentY / tileSize).roundToInt()

        val dx = snappedGridX * tileSize - currentX
        val dy = snappedGridY * tileSize - currentY

        if (dx != 0f || dy != 0f) {
            _draggableWords.value = _draggableWords.value.map {
                if (it.groupId == groupId) {
                    it.copy(offsetX = it.offsetX + dx, offsetY = it.offsetY + dy)
                } else {
                    it
                }
            }
        }
    }

    private fun findIntersection(word1: DraggableWord, word2: DraggableWord, tileSize: Float): Pair<Int, Int>? {
        if (word1.bonzaWord.isHorizontal == word2.bonzaWord.isHorizontal) return null

        val hWordDraggable = if (word1.bonzaWord.isHorizontal) word1 else word2
        val vWordDraggable = if (word1.bonzaWord.isHorizontal) word2 else word1

        val hWord = hWordDraggable.bonzaWord
        val vWord = vWordDraggable.bonzaWord

        for (hIndex in hWord.letters.indices) {
            for (vIndex in vWord.letters.indices) {
                if (hWord.letters[hIndex] == vWord.letters[vIndex]) {
                    val hWordLetterX = (hWord.x + hIndex) * tileSize + hWordDraggable.offsetX
                    val hWordLetterY = hWord.y * tileSize + hWordDraggable.offsetY
                    val vWordLetterX = vWord.x * tileSize + vWordDraggable.offsetX
                    val vWordLetterY = (vWord.y + vIndex) * tileSize + vWordDraggable.offsetY

                    val distance = sqrt((hWordLetterX - vWordLetterX).pow(2) + (hWordLetterY - vWordLetterY).pow(2))

                    if (distance < tileSize * 0.8) {
                        return if (word1.bonzaWord.isHorizontal) Pair(hIndex, vIndex) else Pair(vIndex, hIndex)
                    }
                }
            }
        }
        return null
    }

    private fun snapToIntersection(draggedWordInGroup: DraggableWord, otherWord: DraggableWord, intersection: Pair<Int, Int>, tileSize: Float) {
        val (draggedIndex, otherIndex) = intersection

        val targetOffsetX = if (draggedWordInGroup.bonzaWord.isHorizontal) {
            otherWord.offsetX + (otherWord.bonzaWord.x - (draggedWordInGroup.bonzaWord.x + draggedIndex)) * tileSize
        } else {
            otherWord.offsetX + ((otherWord.bonzaWord.x + otherIndex) - draggedWordInGroup.bonzaWord.x) * tileSize
        }
        val targetOffsetY = if (draggedWordInGroup.bonzaWord.isHorizontal) {
            otherWord.offsetY + ((otherWord.bonzaWord.y + otherIndex) - draggedWordInGroup.bonzaWord.y) * tileSize
        } else {
            otherWord.offsetY + (otherWord.bonzaWord.y - (draggedWordInGroup.bonzaWord.y + draggedIndex)) * tileSize
        }

        val dx = targetOffsetX - draggedWordInGroup.offsetX
        val dy = targetOffsetY - draggedWordInGroup.offsetY

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == draggedWordInGroup.groupId) {
                it.copy(offsetX = it.offsetX + dx, offsetY = it.offsetY + dy)
            } else {
                it
            }
        }
    }

    private fun mergeGroups(group1Id: String, group2Id: String) {
        if (group1Id == group2Id) return
        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == group2Id) it.copy(groupId = group1Id) else it
        }
    }

    private fun checkWinCondition() {
        if (_draggableWords.value.isEmpty() || _isGameWon.value) return

        val firstGroupId = _draggableWords.value.first().groupId
        val allInOneGroup = _draggableWords.value.all { it.groupId == firstGroupId }

        if (allInOneGroup && _draggableWords.value.size == puzzleWordCount) {
            _isGameWon.value = true
            if (mode == "daily") {
                val today = LocalDate.now().toEpochDay()
                val streak = streakRepository.getStreak("bonza")
                if (streak.lastCompletedEpochDay != today) {
                    streakRepository.saveStreak(streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    ))
                }
            }
            repository.saveWords(null, wordsKey)
            repository.saveClue(null, clueKey)
        }
    }
}