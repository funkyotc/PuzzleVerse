package com.funkyotc.puzzleverse.bonza.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedBonza(
    override val id: String,
    val theme: String,
    val words: List<String>,
    val fragments: List<WordFragment>
) : BrowseablePuzzle {
    override val difficulty: String get() = theme
    override val label: String get() = theme
    override val subtitle: String get() = "${words.size} words"
    
    fun toBonzaPuzzle() = BonzaPuzzle(
        theme = theme,
        words = words,
        fragments = fragments,
        connections = emptyList(),
        solvedFragments = emptyList()
    )
}

object BonzaPregenerated {
    val ALL_PUZZLES: List<PregeneratedBonza> by lazy {
        listOf(
            PregeneratedBonza(
                id = "Bonza_Animals_puzzle_001",
                theme = "Animals",
                words = listOf("DOG", "CAT", "MOUSE", "HORSE"),
                fragments = listOf(
                    WordFragment(id=0, text="DO", initialPosition=androidx.compose.ui.geometry.Offset(0f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="G", initialPosition=androidx.compose.ui.geometry.Offset(9f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="CA", initialPosition=androidx.compose.ui.geometry.Offset(7f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 2f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="T", initialPosition=androidx.compose.ui.geometry.Offset(4f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 2f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=4, text="MO", initialPosition=androidx.compose.ui.geometry.Offset(0f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=5, text="US", initialPosition=androidx.compose.ui.geometry.Offset(9f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=6, text="E", initialPosition=androidx.compose.ui.geometry.Offset(10f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=7, text="HO", initialPosition=androidx.compose.ui.geometry.Offset(2f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=8, text="RSE", initialPosition=androidx.compose.ui.geometry.Offset(2f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 5f), direction=ConnectionDirection.VERTICAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Animals_puzzle_002",
                theme = "Animals",
                words = listOf("LION", "TIGER", "BEAR", "WOLF"),
                fragments = listOf(
                    WordFragment(id=0, text="LI", initialPosition=androidx.compose.ui.geometry.Offset(0f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 2f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="ON", initialPosition=androidx.compose.ui.geometry.Offset(4f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 2f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="TIG", initialPosition=androidx.compose.ui.geometry.Offset(10f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 1f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="ER", initialPosition=androidx.compose.ui.geometry.Offset(2f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 4f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="BE", initialPosition=androidx.compose.ui.geometry.Offset(2f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=5, text="AR", initialPosition=androidx.compose.ui.geometry.Offset(4f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=6, text="WOL", initialPosition=androidx.compose.ui.geometry.Offset(3f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=7, text="F", initialPosition=androidx.compose.ui.geometry.Offset(2f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 3f), direction=ConnectionDirection.VERTICAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Animals_puzzle_003",
                theme = "Animals",
                words = listOf("EAGLE", "HAWK", "OWL", "FALCON"),
                fragments = listOf(
                    WordFragment(id=0, text="EAG", initialPosition=androidx.compose.ui.geometry.Offset(2f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 2f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="LE", initialPosition=androidx.compose.ui.geometry.Offset(2f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 2f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="HA", initialPosition=androidx.compose.ui.geometry.Offset(8f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 1f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="WK", initialPosition=androidx.compose.ui.geometry.Offset(8f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="OW", initialPosition=androidx.compose.ui.geometry.Offset(6f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="L", initialPosition=androidx.compose.ui.geometry.Offset(2f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 2f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=6, text="FA", initialPosition=androidx.compose.ui.geometry.Offset(1f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=7, text="LC", initialPosition=androidx.compose.ui.geometry.Offset(0f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=8, text="ON", initialPosition=androidx.compose.ui.geometry.Offset(6f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Colors_puzzle_001",
                theme = "Colors",
                words = listOf("RED", "BLUE", "GREEN", "YELLOW"),
                fragments = listOf(
                    WordFragment(id=0, text="RE", initialPosition=androidx.compose.ui.geometry.Offset(7f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 3f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="D", initialPosition=androidx.compose.ui.geometry.Offset(0f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 3f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="BLU", initialPosition=androidx.compose.ui.geometry.Offset(9f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="E", initialPosition=androidx.compose.ui.geometry.Offset(3f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="GR", initialPosition=androidx.compose.ui.geometry.Offset(3f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 2f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="EE", initialPosition=androidx.compose.ui.geometry.Offset(7f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 4f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=6, text="N", initialPosition=androidx.compose.ui.geometry.Offset(9f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 6f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=7, text="YEL", initialPosition=androidx.compose.ui.geometry.Offset(6f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 1f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=8, text="LO", initialPosition=androidx.compose.ui.geometry.Offset(2f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 1f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=9, text="W", initialPosition=androidx.compose.ui.geometry.Offset(0f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 1f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Colors_puzzle_002",
                theme = "Colors",
                words = listOf("PURPLE", "ORANGE", "PINK", "BROWN"),
                fragments = listOf(
                    WordFragment(id=0, text="PUR", initialPosition=androidx.compose.ui.geometry.Offset(9f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 1f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="PLE", initialPosition=androidx.compose.ui.geometry.Offset(1f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 1f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="ORA", initialPosition=androidx.compose.ui.geometry.Offset(3f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="NG", initialPosition=androidx.compose.ui.geometry.Offset(9f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="E", initialPosition=androidx.compose.ui.geometry.Offset(10f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 5f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="PI", initialPosition=androidx.compose.ui.geometry.Offset(1f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 1f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=6, text="NK", initialPosition=androidx.compose.ui.geometry.Offset(9f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=7, text="BR", initialPosition=androidx.compose.ui.geometry.Offset(0f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=8, text="OW", initialPosition=androidx.compose.ui.geometry.Offset(8f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=9, text="N", initialPosition=androidx.compose.ui.geometry.Offset(0f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Colors_puzzle_003",
                theme = "Colors",
                words = listOf("CYAN", "MAGENTA", "TEAL", "MAROON"),
                fragments = listOf(
                    WordFragment(id=0, text="CYA", initialPosition=androidx.compose.ui.geometry.Offset(8f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 5f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="N", initialPosition=androidx.compose.ui.geometry.Offset(3f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 5f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="MA", initialPosition=androidx.compose.ui.geometry.Offset(10f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 4f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="GE", initialPosition=androidx.compose.ui.geometry.Offset(10f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 6f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="NT", initialPosition=androidx.compose.ui.geometry.Offset(9f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 8f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="A", initialPosition=androidx.compose.ui.geometry.Offset(0f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 10f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=6, text="TEA", initialPosition=androidx.compose.ui.geometry.Offset(10f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 7f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=7, text="L", initialPosition=androidx.compose.ui.geometry.Offset(8f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 7f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=8, text="MA", initialPosition=androidx.compose.ui.geometry.Offset(2f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=9, text="ROO", initialPosition=androidx.compose.ui.geometry.Offset(3f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 2f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=10, text="N", initialPosition=androidx.compose.ui.geometry.Offset(5f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 5f), direction=ConnectionDirection.VERTICAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Space_puzzle_001",
                theme = "Space",
                words = listOf("SUN", "MOON", "STAR", "PLANET"),
                fragments = listOf(
                    WordFragment(id=0, text="SUN", initialPosition=androidx.compose.ui.geometry.Offset(3f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 3f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="MO", initialPosition=androidx.compose.ui.geometry.Offset(6f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=2, text="ON", initialPosition=androidx.compose.ui.geometry.Offset(4f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 2f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="ST", initialPosition=androidx.compose.ui.geometry.Offset(5f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="AR", initialPosition=androidx.compose.ui.geometry.Offset(4f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 5f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="PLA", initialPosition=androidx.compose.ui.geometry.Offset(8f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=6, text="NET", initialPosition=androidx.compose.ui.geometry.Offset(7f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 4f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Space_puzzle_002",
                theme = "Space",
                words = listOf("MARS", "VENUS", "EARTH", "JUPITER"),
                fragments = listOf(
                    WordFragment(id=0, text="MAR", initialPosition=androidx.compose.ui.geometry.Offset(2f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 6f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="S", initialPosition=androidx.compose.ui.geometry.Offset(4f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 6f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="VE", initialPosition=androidx.compose.ui.geometry.Offset(2f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 2f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="NU", initialPosition=androidx.compose.ui.geometry.Offset(9f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 4f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="S", initialPosition=androidx.compose.ui.geometry.Offset(7f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 6f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="EAR", initialPosition=androidx.compose.ui.geometry.Offset(10f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 5f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=6, text="TH", initialPosition=androidx.compose.ui.geometry.Offset(5f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 8f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=7, text="JUP", initialPosition=androidx.compose.ui.geometry.Offset(0f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=8, text="IT", initialPosition=androidx.compose.ui.geometry.Offset(3f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=9, text="ER", initialPosition=androidx.compose.ui.geometry.Offset(7f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 5f), direction=ConnectionDirection.VERTICAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Space_puzzle_003",
                theme = "Space",
                words = listOf("COMET", "METEOR", "ASTEROID", "GALAXY"),
                fragments = listOf(
                    WordFragment(id=0, text="COM", initialPosition=androidx.compose.ui.geometry.Offset(4f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="ET", initialPosition=androidx.compose.ui.geometry.Offset(4f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 4f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="MET", initialPosition=androidx.compose.ui.geometry.Offset(10f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 0f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=3, text="EO", initialPosition=androidx.compose.ui.geometry.Offset(9f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 3f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=4, text="R", initialPosition=androidx.compose.ui.geometry.Offset(7f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(1f, 5f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=5, text="AST", initialPosition=androidx.compose.ui.geometry.Offset(6f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 1f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=6, text="ER", initialPosition=androidx.compose.ui.geometry.Offset(7f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 4f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=7, text="OI", initialPosition=androidx.compose.ui.geometry.Offset(3f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 6f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=8, text="D", initialPosition=androidx.compose.ui.geometry.Offset(8f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 8f), direction=ConnectionDirection.VERTICAL),
                    WordFragment(id=9, text="GAL", initialPosition=androidx.compose.ui.geometry.Offset(3f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 1f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=10, text="AXY", initialPosition=androidx.compose.ui.geometry.Offset(6f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 1f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
        )
    }

    val PUZZLES_BY_THEME: Map<String, List<PregeneratedBonza>> by lazy { ALL_PUZZLES.groupBy { it.theme } }

    fun getPuzzleById(id: String): PregeneratedBonza? = ALL_PUZZLES.find { it.id == id }
}
