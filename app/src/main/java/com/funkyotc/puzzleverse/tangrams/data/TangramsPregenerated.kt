package com.funkyotc.puzzleverse.tangrams.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class TangramPuzzleInfo(
    override val id: String,
    override val difficulty: String,
    val name: String,
    val assetFileName: String,
    val emoji: String
) : BrowseablePuzzle {
    override val label: String get() = name
    override val subtitle: String get() = emoji
}

object TangramsPregenerated {
    val ALL_PUZZLES = listOf(
        TangramPuzzleInfo("triangle", "Easy", "Triangle", "tangrams/triangle.svg", "🔺"),
        TangramPuzzleInfo("rhombus", "Easy", "Rhombus", "tangrams/rhombus.svg", "🔷"),
        TangramPuzzleInfo("gem", "Easy", "Gem", "tangrams/gem.svg", "💎"),
        TangramPuzzleInfo("candle", "Easy", "Candle", "tangrams/candle.svg", "🕯️"),
        TangramPuzzleInfo("house", "Easy", "House", "tangrams/house.svg", "🏠"),
        TangramPuzzleInfo("fish", "Easy", "Fish", "tangrams/fish.svg", "🐟"),
        TangramPuzzleInfo("snake", "Easy", "Snake", "tangrams/snake.svg", "🐍"),
        TangramPuzzleInfo("swan", "Medium", "Swan", "tangrams/swan.svg", "🦢"),
        TangramPuzzleInfo("cat", "Medium", "Cat", "tangrams/cat.svg", "🐱"),
        TangramPuzzleInfo("dog", "Medium", "Dog", "tangrams/dog.svg", "🐕"),
        TangramPuzzleInfo("fox", "Medium", "Fox", "tangrams/fox.svg", "🦊"),
        TangramPuzzleInfo("tree", "Medium", "Tree", "tangrams/tree.svg", "🌲"),
        TangramPuzzleInfo("bridge", "Medium", "Bridge", "tangrams/bridge.svg", "🌉"),
        TangramPuzzleInfo("person", "Medium", "Person", "tangrams/person.svg", "🧍"),
        TangramPuzzleInfo("boat", "Hard", "Boat", "tangrams/boat.svg", "⛵")
    )

    val PUZZLES_BY_DIFFICULTY: Map<String, List<TangramPuzzleInfo>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }

    fun getPuzzleById(id: String): TangramPuzzleInfo? = ALL_PUZZLES.find { it.id == id }
}
