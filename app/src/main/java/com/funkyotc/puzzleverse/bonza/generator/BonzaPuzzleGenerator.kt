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
        val puzzle = generatePuzzle(random)

        // Generate random starting positions for fragments
        val minX = 50f
        val maxX = 800f
        val minY = 100f
        val maxY = 1200f

        val shuffledFragments = puzzle.solvedFragments.map { solvedFragment ->
            val randomX = random.nextFloat() * (maxX - minX) + minX
            val randomY = random.nextFloat() * (maxY - minY) + minY

            WordFragment(
                id = solvedFragment.id,
                text = solvedFragment.text,
                initialPosition = Offset(randomX, randomY),
                currentPosition = Offset(randomX, randomY),
                solvedPosition = solvedFragment.solvedPosition,
                direction = solvedFragment.direction
            )
        }

        return puzzle.copy(fragments = shuffledFragments)
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

        for (bonzaWord in placedWords) {
            if (bonzaWord.word.length > 2 && random.nextBoolean()) {
                val splitIndex = random.nextInt(1, bonzaWord.word.length - 1)
                val text1 = bonzaWord.word.substring(0, splitIndex)
                val text2 = bonzaWord.word.substring(splitIndex)

                val frag1Id = fragmentIdCounter++
                val frag2Id = fragmentIdCounter++

                val solvedPos1 = if (bonzaWord.direction == ConnectionDirection.HORIZONTAL) {
                    Offset(bonzaWord.position.x + splitIndex, bonzaWord.position.y) * 80f
                } else {
                    Offset(bonzaWord.position.x, bonzaWord.position.y + splitIndex) * 80f
                }
                
                // Scale initial word position too for solvedPosition
                val solvedOrigin = bonzaWord.position * 80f

                solvedFragments.add(
                    WordFragment(
                        id = frag1Id, text = text1, initialPosition = Offset.Zero, currentPosition = Offset.Zero,
                        solvedPosition = solvedOrigin, direction = bonzaWord.direction
                    )
                )
                solvedFragments.add(
                    WordFragment(
                        id = frag2Id, text = text2, initialPosition = Offset.Zero, currentPosition = Offset.Zero,
                        solvedPosition = solvedPos1, direction = bonzaWord.direction
                    )
                )
                connections.add(BonzaConnection(frag1Id, frag2Id, bonzaWord.direction))
            } else {
                solvedFragments.add(
                    WordFragment(
                        id = fragmentIdCounter++, text = bonzaWord.word, initialPosition = Offset.Zero,
                        currentPosition = Offset.Zero, solvedPosition = bonzaWord.position * 80f, direction = bonzaWord.direction
                    )
                )
            }
        }

        return BonzaPuzzle(
            theme = theme, words = words,
            fragments = emptyList(), // to be populated by generate()
            connections = connections,
            solvedFragments = solvedFragments
        )
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
            if (grid.containsKey(Pair(x, y)) && grid[Pair(x, y)] != word[i]) {
                return false // Collision
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
