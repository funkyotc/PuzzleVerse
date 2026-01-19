package com.funkyotc.puzzleverse.bonza.generator

import androidx.compose.ui.geometry.Offset
import com.funkyotc.puzzleverse.bonza.data.BonzaConnection
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzleTheme
import com.funkyotc.puzzleverse.bonza.data.BonzaWord
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.data.WordFragment
import kotlin.random.Random

class BonzaPuzzleGenerator(private val puzzleThemes: List<BonzaPuzzleTheme>) {

    fun generate(seed: Long = Random.nextLong()): BonzaPuzzle {
        val random = Random(seed)
        return generatePuzzle(random)
    }

    private fun generatePuzzle(random: Random): BonzaPuzzle {
        val puzzleTheme = puzzleThemes.random(random)
        val theme = puzzleTheme.theme
        val words = puzzleTheme.words

        val placedWords = mutableListOf<BonzaWord>()
        val grid = mutableMapOf<Pair<Int, Int>, Char>()

        // Place the first word
        val firstWord = words.first()
        val firstWordDirection = if (random.nextBoolean()) ConnectionDirection.HORIZONTAL else ConnectionDirection.VERTICAL
        placeWord(firstWord, 0, 0, firstWordDirection, grid)
        placedWords.add(
            BonzaWord(
                word = firstWord, fragments = emptyList(),
                position = Offset(0f, 0f), direction = firstWordDirection
            )
        )

        // Place remaining words
        for (i in 1 until words.size) {
            val wordToPlace = words[i]
            var placed = false
            var attempts = 0
            while (!placed && attempts < 100) {
                val intersectingWord = placedWords.random(random)
                val intersection = findIntersection(wordToPlace, intersectingWord.word)
                if (intersection != null) {
                    val (charIndexInWord, charIndexInPlacedWord) = intersection
                    val newDirection = if (intersectingWord.direction == ConnectionDirection.HORIZONTAL) ConnectionDirection.VERTICAL else ConnectionDirection.HORIZONTAL

                    val (newX, newY) = calculatePosition(
                        intersectingWord, charIndexInPlacedWord, charIndexInWord
                    )

                    if (canPlaceWord(wordToPlace, newX, newY, newDirection, grid)) {
                        placeWord(wordToPlace, newX, newY, newDirection, grid)
                        placedWords.add(
                            BonzaWord(
                                word = wordToPlace, fragments = emptyList(),
                                position = Offset(newX.toFloat(), newY.toFloat()), direction = newDirection
                            )
                        )
                        placed = true
                    }
                }
                attempts++
            }
        }

        // Create fragments and connections
        val solvedFragments = mutableListOf<WordFragment>()
        val connections = mutableListOf<BonzaConnection>()
        var fragmentIdCounter = 1
        
        // Map grid cell -> Fragment ID
        val gridOwners = mutableMapOf<Pair<Int, Int>, Int>()
        
        // 1. Generate fragments for unclaimed segments
        for (bonzaWord in placedWords) {
            val startXY = Pair(bonzaWord.position.x.toInt(), bonzaWord.position.y.toInt())
            val direction = bonzaWord.direction
            val word = bonzaWord.word
            
            var currentSegmentText = ""
            var currentSegmentStartIndex = -1
            
            for (i in word.indices) {
                val x = if (direction == ConnectionDirection.HORIZONTAL) startXY.first + i else startXY.first
                val y = if (direction == ConnectionDirection.VERTICAL) startXY.second + i else startXY.second
                val cell = Pair(x, y)
                
                if (!gridOwners.containsKey(cell)) {
                    // Start or continue segment
                    if (currentSegmentText.isEmpty()) {
                        currentSegmentStartIndex = i
                    }
                    currentSegmentText += word[i]
                    
                    // Optimization: Randomly split long segments to keep puzzle piece-like chunks
                    // (e.g., max length 4 unless it's the end of word)
                    if (currentSegmentText.length >= 3 && i < word.length - 1 && random.nextFloat() < 0.3f) {
                         // Flush current segment
                         val fragId = fragmentIdCounter++
                         createFragment(
                             fragId, currentSegmentText, currentSegmentStartIndex, 
                             bonzaWord, solvedFragments, gridOwners
                         )
                         currentSegmentText = ""
                         currentSegmentStartIndex = -1
                    }
                } else {
                    // Segment interrupted by existing owner
                    if (currentSegmentText.isNotEmpty()) {
                         val fragId = fragmentIdCounter++
                         createFragment(
                             fragId, currentSegmentText, currentSegmentStartIndex, 
                             bonzaWord, solvedFragments, gridOwners
                         )
                         currentSegmentText = ""
                         currentSegmentStartIndex = -1
                    }
                }
            }
            
            // Flush remaining segment
            if (currentSegmentText.isNotEmpty()) {
                 val fragId = fragmentIdCounter++
                 createFragment(
                     fragId, currentSegmentText, currentSegmentStartIndex, 
                     bonzaWord, solvedFragments, gridOwners
                 )
            }
        }
        
        // 2. Compute Connections based on Grid Adjacency
        // Iterate all fragments and check if they touch any other fragment
        for (frag in solvedFragments) {
            val fragId = frag.id
            // Check all cells of this fragment
            for (i in frag.text.indices) {
                // Calculate Grid Position of this character
                // Note: solvedPosition is in Grid Units now
                val fragX = frag.solvedPosition!!.x.toInt()
                val fragY = frag.solvedPosition!!.y.toInt()
                
                val (cellX, cellY) = if (frag.direction == ConnectionDirection.HORIZONTAL) {
                    Pair(fragX + i, fragY)
                } else {
                    Pair(fragX, fragY + i)
                }
                
                // Check 4 neighbors
                val neighbors = listOf(
                    Pair(cellX + 1, cellY) to ConnectionDirection.HORIZONTAL,
                    Pair(cellX - 1, cellY) to ConnectionDirection.HORIZONTAL,
                    Pair(cellX, cellY + 1) to ConnectionDirection.VERTICAL,
                    Pair(cellX, cellY - 1) to ConnectionDirection.VERTICAL
                )
                
                for ((neighborPos, dir) in neighbors) {
                    val neighborId = gridOwners[neighborPos]
                    if (neighborId != null && neighborId != fragId) {
                        // Avoid duplicates: only add if current < neighbor
                        if (fragId < neighborId) {
                             if (connections.none { it.fragment1Id == fragId && it.fragment2Id == neighborId }) {
                                 connections.add(BonzaConnection(fragId, neighborId, dir))
                             }
                        }
                    }
                }
            }
        }
        
        // 3. Assign Random Initial Positions
        // Generate random starting positions for fragments
        // Use Grid Units (approx 0..10 range)
        val minX = 0f
        val maxX = 8f
        val minY = 0f
        val maxY = 12f

        val shuffledFragments = solvedFragments.map { solvedFragment ->
            val randomX = random.nextFloat() * (maxX - minX) + minX
            val randomY = random.nextFloat() * (maxY - minY) + minY

            solvedFragment.copy(
                initialPosition = Offset(randomX, randomY),
                currentPosition = Offset(randomX, randomY)
            )
        }.shuffled(random)

        return BonzaPuzzle(
            theme = theme, words = words,
            fragments = shuffledFragments,
            connections = connections,
            solvedFragments = solvedFragments
        )
    }
    
    private fun createFragment(
        id: Int,
        text: String,
        startIndex: Int,
        originalWord: BonzaWord,
        list: MutableList<WordFragment>,
        owners: MutableMap<Pair<Int, Int>, Int>
    ) {
        val solvedOrigin = if (originalWord.direction == ConnectionDirection.HORIZONTAL) {
             Offset(originalWord.position.x + startIndex, originalWord.position.y) 
        } else {
             Offset(originalWord.position.x, originalWord.position.y + startIndex)
        }
        
        list.add(WordFragment(
            id = id,
            text = text,
            initialPosition = Offset.Zero, // set later
            currentPosition = Offset.Zero, // set later
            solvedPosition = solvedOrigin,
            direction = originalWord.direction
        ))
        
        // Register ownership
        for (i in text.indices) {
             val x = solvedOrigin.x.toInt() + if (originalWord.direction == ConnectionDirection.HORIZONTAL) i else 0
             val y = solvedOrigin.y.toInt() + if (originalWord.direction == ConnectionDirection.VERTICAL) i else 0
             owners[Pair(x, y)] = id
        }
    }

    private fun findIntersection(word1: String, word2: String): Pair<Int, Int>? {
        for (i in word1.indices) {
            for (j in word2.indices) {
                if (word1[i] == word2[j]) {
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun calculatePosition(
        placedWord: BonzaWord, charIndexInPlaced: Int, charIndexInNew: Int
    ): Pair<Int, Int> {
        return if (placedWord.direction == ConnectionDirection.HORIZONTAL) {
            val newX = (placedWord.position.x + charIndexInPlaced).toInt()
            val newY = (placedWord.position.y - charIndexInNew).toInt()
            Pair(newX, newY)
        } else {
            val newX = (placedWord.position.x - charIndexInNew).toInt()
            val newY = (placedWord.position.y + charIndexInPlaced).toInt()
            Pair(newX, newY)
        }
    }

    private fun canPlaceWord(
        word: String, startX: Int, startY: Int, direction: ConnectionDirection, grid: Map<Pair<Int, Int>, Char>
    ): Boolean {
        for (i in word.indices) {
            val (x, y) = if (direction == ConnectionDirection.HORIZONTAL) Pair(startX + i, startY) else Pair(startX, startY + i)
            
            if (grid.containsKey(Pair(x, y))) {
                // Intersection point: Must match
                if (grid[Pair(x, y)] != word[i]) {
                    return false // Collision
                }
            } else {
                // Empty cell: Must not touch other letters unless they are part of the word flow
                val neighbors = listOf(
                    Pair(x + 1, y), Pair(x - 1, y),
                    Pair(x, y + 1), Pair(x, y - 1)
                )

                for (neighbor in neighbors) {
                    if (grid.containsKey(neighbor)) {
                        var isAllowed = false
                        
                        // Check if neighbor is the Previous letter position
                        if (i > 0) {
                            val prevPos = if (direction == ConnectionDirection.HORIZONTAL) Pair(x - 1, y) else Pair(x, y - 1)
                            if (neighbor == prevPos) isAllowed = true
                        }
                        
                        // Check if neighbor is the Next letter position
                        if (i < word.length - 1) {
                            val nextPos = if (direction == ConnectionDirection.HORIZONTAL) Pair(x + 1, y) else Pair(x, y + 1)
                            if (neighbor == nextPos) isAllowed = true
                        }
                        
                        // Neighbor is occupied and not part of our word flow -> Illegal Touch
                        if (!isAllowed) return false
                    }
                }
            }
        }
        return true
    }

    private fun placeWord(
        word: String, startX: Int, startY: Int, direction: ConnectionDirection, grid: MutableMap<Pair<Int, Int>, Char>
    ) {
        for (i in word.indices) {
            val (x, y) = if (direction == ConnectionDirection.HORIZONTAL) Pair(startX + i, startY) else Pair(startX, startY + i)
            grid[Pair(x, y)] = word[i]
        }
    }
}
