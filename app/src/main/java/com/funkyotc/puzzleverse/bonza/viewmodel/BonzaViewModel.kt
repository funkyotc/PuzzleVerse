package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
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

        val draggableWords = puzzle.words.map { DraggableWord(bonzaWord = it) }

        // 1. Build adjacency list for the puzzle's solved state
        val adj = mutableMapOf<String, MutableList<String>>()
        for (word1 in draggableWords) {
            adj.putIfAbsent(word1.id, mutableListOf())
            for (word2 in draggableWords) {
                if (word1.id != word2.id && areWordsTouching(word1, word2)) {
                    adj[word1.id]?.add(word2.id)
                }
            }
        }

        // 2. Randomly remove some edges to create fragments (break connections)
        val adjCopy = adj.mapValues { it.value.toMutableList() }
        for ((u, neighbors) in adjCopy) {
            neighbors.forEach { v ->
                if (u < v) { // Process each edge only once to avoid double removal
                    if (Random.nextFloat() < 0.5f) { // 50% chance of removing an edge
                        adj[u]?.remove(v)
                        adj[v]?.remove(u)
                    }
                }
            }
        }

        // 3. Find connected components (these will be our groups)
        val visited = mutableSetOf<String>()
        val wordToGroup = mutableMapOf<String, String>()
        var groupCount = 0
        for (word in draggableWords) {
            if (word.id !in visited) {
                val groupId = "group_${groupCount++}"
                val stack = ArrayDeque<String>()
                stack.add(word.id)
                visited.add(word.id)

                while (stack.isNotEmpty()) {
                    val currentWordId = stack.removeLast()
                    wordToGroup[currentWordId] = groupId
                    adj[currentWordId]?.forEach { neighborId ->
                        if (neighborId !in visited) {
                            visited.add(neighborId)
                            stack.add(neighborId)
                        }
                    }
                }
            }
        }

        // 4. Assign random offsets to each group, not each word
        val groupOffsets = mutableMapOf<String, Pair<Float, Float>>()
        val newDraggableWords = draggableWords.map { word ->
            val groupId = wordToGroup[word.id] ?: word.id
            val (offsetX, offsetY) = groupOffsets.getOrPut(groupId) {
                Pair(Random.nextInt(-500, 500).toFloat(), Random.nextInt(-500, 500).toFloat())
            }
            word.copy(groupId = groupId, offsetX = offsetX, offsetY = offsetY)
        }

        repository.saveWords(newDraggableWords, wordsKey)
        repository.saveClue(puzzle.clue, clueKey)
        _isGameWon.value = false
        return newDraggableWords
    }


    private fun findNeighbors(word: DraggableWord, others: List<DraggableWord>): List<DraggableWord> {
        val neighbors = mutableListOf<DraggableWord>()
        for (other in others) {
            if (areWordsTouching(word, other)) {
                neighbors.add(other)
            }
        }
        return neighbors
    }

    private fun areWordsTouching(word1: DraggableWord, word2: DraggableWord): Boolean {
        // Check if words intersect or are adjacent
        val rect1 = word1.bonzaWord.getRect()
        val rect2 = word2.bonzaWord.getRect()
        return rect1.intersects(rect2.left - 1, rect2.top - 1, rect2.right + 1, rect2.bottom + 1)
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
        val otherWords = _draggableWords.value.filter { it.groupId != dragGroupId }

        for (otherWord in otherWords) {
            val intersection = findIntersection(draggedWord, otherWord, tileSizePx)
            if (intersection != null) {
                snapToIntersection(draggedWord, otherWord, intersection, tileSizePx)
                mergeGroups(draggedWord, otherWord)
                didConnect = true
            }
        }

        val finalGroupId = _draggableWords.value.find { it.id == wordId }?.groupId ?: return
        if (!didConnect) { // Only snap to grid if no connection was made
            snapGroupToGrid(finalGroupId, tileSizePx)
        }

        repository.saveWords(_draggableWords.value, wordsKey)
        checkWinCondition()
    }

    private fun snapGroupToGrid(groupId: String, tileSize: Float) {
        val groupWords = _draggableWords.value.filter { it.groupId == groupId }
        if (groupWords.isEmpty()) return

        val firstWord = groupWords.first()
        val snappedWord = snapToGrid(firstWord, tileSize)
        val dx = snappedWord.offsetX - firstWord.offsetX
        val dy = snappedWord.offsetY - firstWord.offsetY

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

    private fun snapToGrid(word: DraggableWord, tileSize: Float): DraggableWord {
        val currentX = word.bonzaWord.x * tileSize + word.offsetX
        val currentY = word.bonzaWord.y * tileSize + word.offsetY

        val snappedGridX = (currentX / tileSize).roundToInt()
        val snappedGridY = (currentY / tileSize).roundToInt()

        val snappedX = snappedGridX * tileSize
        val snappedY = snappedGridY * tileSize

        val newOffsetX = snappedX - (word.bonzaWord.x * tileSize)
        val newOffsetY = snappedY - (word.bonzaWord.y * tileSize)

        return word.copy(offsetX = newOffsetX, offsetY = newOffsetY)
    }

    private fun findIntersection(word1: DraggableWord, word2: DraggableWord, tileSize: Float): Pair<Int, Int>? {
        if (word1.bonzaWord.isHorizontal == word2.bonzaWord.isHorizontal) return null

        val hWord = if (word1.bonzaWord.isHorizontal) word1 else word2
        val vWord = if (word1.bonzaWord.isHorizontal) word2 else word1

        for (hIndex in hWord.bonzaWord.letters.indices) {
            for (vIndex in vWord.bonzaWord.letters.indices) {
                if (hWord.bonzaWord.letters[hIndex] == vWord.bonzaWord.letters[vIndex]) {
                    val hWordLetterX = (hWord.bonzaWord.x + hIndex) * tileSize + hWord.offsetX
                    val hWordLetterY = hWord.bonzaWord.y * tileSize + hWord.offsetY
                    val vWordLetterX = vWord.bonzaWord.x * tileSize + vWord.offsetX
                    val vWordLetterY = (vWord.bonzaWord.y + vIndex) * tileSize + vWord.offsetY

                    val distance = sqrt((hWordLetterX - vWordLetterX).pow(2) + (hWordLetterY - vWordLetterY).pow(2))

                    if (distance < tileSize * 0.5) {
                        return if (hWord == word1) Pair(hIndex, vIndex) else Pair(vIndex, hIndex)
                    }
                }
            }
        }
        return null
    }

    private fun snapToIntersection(draggedWord: DraggableWord, otherWord: DraggableWord, intersection: Pair<Int, Int>, tileSize: Float) {
        val (draggedIndex, otherIndex) = intersection
        val newOffsetX: Float
        val newOffsetY: Float

        val draggedGroupId = draggedWord.groupId

        if (draggedWord.bonzaWord.isHorizontal) {
            val hWord = draggedWord
            val vWord = otherWord
            newOffsetX = vWord.offsetX + (vWord.bonzaWord.x - (hWord.bonzaWord.x + draggedIndex)) * tileSize
            newOffsetY = vWord.offsetY + ((vWord.bonzaWord.y + otherIndex) - hWord.bonzaWord.y) * tileSize
        } else {
            val vWord = draggedWord
            val hWord = otherWord
            newOffsetX = hWord.offsetX + ((hWord.bonzaWord.x + otherIndex) - vWord.bonzaWord.x) * tileSize
            newOffsetY = hWord.offsetY + (hWord.bonzaWord.y - (vWord.bonzaWord.y + draggedIndex)) * tileSize
        }

        val dx = newOffsetX - draggedWord.offsetX
        val dy = newOffsetY - draggedWord.offsetY

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == draggedGroupId) {
                it.copy(offsetX = it.offsetX + dx, offsetY = it.offsetY + dy)
            } else {
                it
            }
        }
    }

    private fun mergeGroups(word1: DraggableWord, word2: DraggableWord) {
        val group1Id = _draggableWords.value.find { it.id == word1.id }?.groupId ?: return
        val group2Id = _draggableWords.value.find { it.id == word2.id }?.groupId ?: return

        if (group1Id == group2Id) return

        _draggableWords.value = _draggableWords.value.map {
            if (it.groupId == group2Id) {
                it.copy(groupId = group1Id)
            } else {
                it
            }
        }
    }

    private fun checkWinCondition() {
        if (_draggableWords.value.isEmpty()) return
        val firstGroupId = _draggableWords.value.first().groupId
        val allConnected = _draggableWords.value.all { it.groupId == firstGroupId }

        if (allConnected && !_isGameWon.value) {
            _isGameWon.value = true

            if (mode == "daily") {
                val today = LocalDate.now().toEpochDay()
                val streak = streakRepository.getStreak("bonza")
                if (streak.lastCompletedEpochDay != today) {
                    val newStreak = streak.copy(
                        count = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1,
                        lastCompletedEpochDay = today
                    )
                    streakRepository.saveStreak(newStreak)
                }
            }
            repository.saveWords(null, wordsKey)
            repository.saveClue(null, clueKey)
        }
    }
}
