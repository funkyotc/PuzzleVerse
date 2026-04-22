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
                id = "Bonza_Cities_puzzle_001",
                theme = "Cities",
                words = listOf("BERLIN"),
                fragments = listOf(
                    WordFragment(id=0, text="BER", initialPosition=androidx.compose.ui.geometry.Offset(6f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="LIN", initialPosition=androidx.compose.ui.geometry.Offset(5f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Cities_puzzle_002",
                theme = "Cities",
                words = listOf("SYDNEY"),
                fragments = listOf(
                    WordFragment(id=0, text="SY", initialPosition=androidx.compose.ui.geometry.Offset(8f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="DN", initialPosition=androidx.compose.ui.geometry.Offset(1f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="EY", initialPosition=androidx.compose.ui.geometry.Offset(1f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Cities_puzzle_003",
                theme = "Cities",
                words = listOf("NEWYORK"),
                fragments = listOf(
                    WordFragment(id=0, text="NEW", initialPosition=androidx.compose.ui.geometry.Offset(9f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="YO", initialPosition=androidx.compose.ui.geometry.Offset(3f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="RK", initialPosition=androidx.compose.ui.geometry.Offset(1f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Cities_puzzle_004",
                theme = "Cities",
                words = listOf("LONDON"),
                fragments = listOf(
                    WordFragment(id=0, text="LO", initialPosition=androidx.compose.ui.geometry.Offset(4f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="ND", initialPosition=androidx.compose.ui.geometry.Offset(5f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="ON", initialPosition=androidx.compose.ui.geometry.Offset(2f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Cities_puzzle_005",
                theme = "Cities",
                words = listOf("NEWYORK"),
                fragments = listOf(
                    WordFragment(id=0, text="NE", initialPosition=androidx.compose.ui.geometry.Offset(5f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="WY", initialPosition=androidx.compose.ui.geometry.Offset(10f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="OR", initialPosition=androidx.compose.ui.geometry.Offset(10f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="K", initialPosition=androidx.compose.ui.geometry.Offset(3f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(6f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Fruit_puzzle_001",
                theme = "Fruit",
                words = listOf("ELDERBERRY"),
                fragments = listOf(
                    WordFragment(id=0, text="EL", initialPosition=androidx.compose.ui.geometry.Offset(1f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="DE", initialPosition=androidx.compose.ui.geometry.Offset(2f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="RBE", initialPosition=androidx.compose.ui.geometry.Offset(7f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="RR", initialPosition=androidx.compose.ui.geometry.Offset(4f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=4, text="Y", initialPosition=androidx.compose.ui.geometry.Offset(3f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(9f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Fruit_puzzle_002",
                theme = "Fruit",
                words = listOf("CHERRY"),
                fragments = listOf(
                    WordFragment(id=0, text="CH", initialPosition=androidx.compose.ui.geometry.Offset(9f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="ER", initialPosition=androidx.compose.ui.geometry.Offset(0f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="RY", initialPosition=androidx.compose.ui.geometry.Offset(1f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Fruit_puzzle_003",
                theme = "Fruit",
                words = listOf("ELDERBERRY"),
                fragments = listOf(
                    WordFragment(id=0, text="ELD", initialPosition=androidx.compose.ui.geometry.Offset(7f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="ERB", initialPosition=androidx.compose.ui.geometry.Offset(9f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="ERR", initialPosition=androidx.compose.ui.geometry.Offset(5f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(6f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="Y", initialPosition=androidx.compose.ui.geometry.Offset(7f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(9f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Fruit_puzzle_004",
                theme = "Fruit",
                words = listOf("ELDERBERRY"),
                fragments = listOf(
                    WordFragment(id=0, text="ELD", initialPosition=androidx.compose.ui.geometry.Offset(0f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="ER", initialPosition=androidx.compose.ui.geometry.Offset(2f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="BE", initialPosition=androidx.compose.ui.geometry.Offset(10f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="RR", initialPosition=androidx.compose.ui.geometry.Offset(3f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=4, text="Y", initialPosition=androidx.compose.ui.geometry.Offset(8f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(9f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Fruit_puzzle_005",
                theme = "Fruit",
                words = listOf("HONEYDEW"),
                fragments = listOf(
                    WordFragment(id=0, text="HON", initialPosition=androidx.compose.ui.geometry.Offset(4f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="EYD", initialPosition=androidx.compose.ui.geometry.Offset(2f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="EW", initialPosition=androidx.compose.ui.geometry.Offset(8f, 2f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 2f), solvedPosition=androidx.compose.ui.geometry.Offset(6f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Nature_puzzle_001",
                theme = "Nature",
                words = listOf("VALLEY"),
                fragments = listOf(
                    WordFragment(id=0, text="VAL", initialPosition=androidx.compose.ui.geometry.Offset(3f, 6f), currentPosition=androidx.compose.ui.geometry.Offset(3f, 6f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="LEY", initialPosition=androidx.compose.ui.geometry.Offset(6f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Nature_puzzle_002",
                theme = "Nature",
                words = listOf("MOUNTAIN"),
                fragments = listOf(
                    WordFragment(id=0, text="MOU", initialPosition=androidx.compose.ui.geometry.Offset(1f, 7f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 7f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="NT", initialPosition=androidx.compose.ui.geometry.Offset(2f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="AI", initialPosition=androidx.compose.ui.geometry.Offset(7f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="N", initialPosition=androidx.compose.ui.geometry.Offset(8f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Nature_puzzle_003",
                theme = "Nature",
                words = listOf("MOUNTAIN"),
                fragments = listOf(
                    WordFragment(id=0, text="MOU", initialPosition=androidx.compose.ui.geometry.Offset(8f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="NTA", initialPosition=androidx.compose.ui.geometry.Offset(8f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="IN", initialPosition=androidx.compose.ui.geometry.Offset(10f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(10f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(6f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Nature_puzzle_004",
                theme = "Nature",
                words = listOf("FOREST"),
                fragments = listOf(
                    WordFragment(id=0, text="FOR", initialPosition=androidx.compose.ui.geometry.Offset(9f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="EST", initialPosition=androidx.compose.ui.geometry.Offset(4f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Nature_puzzle_005",
                theme = "Nature",
                words = listOf("VALLEY"),
                fragments = listOf(
                    WordFragment(id=0, text="VA", initialPosition=androidx.compose.ui.geometry.Offset(9f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="LL", initialPosition=androidx.compose.ui.geometry.Offset(2f, 0f), currentPosition=androidx.compose.ui.geometry.Offset(2f, 0f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="EY", initialPosition=androidx.compose.ui.geometry.Offset(8f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Technology_puzzle_001",
                theme = "Technology",
                words = listOf("ALGORITHM"),
                fragments = listOf(
                    WordFragment(id=0, text="ALG", initialPosition=androidx.compose.ui.geometry.Offset(6f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="OR", initialPosition=androidx.compose.ui.geometry.Offset(6f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(3f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="IT", initialPosition=androidx.compose.ui.geometry.Offset(1f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="HM", initialPosition=androidx.compose.ui.geometry.Offset(4f, 10f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 10f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Technology_puzzle_002",
                theme = "Technology",
                words = listOf("ALGORITHM"),
                fragments = listOf(
                    WordFragment(id=0, text="AL", initialPosition=androidx.compose.ui.geometry.Offset(6f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="GOR", initialPosition=androidx.compose.ui.geometry.Offset(9f, 9f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 9f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="IT", initialPosition=androidx.compose.ui.geometry.Offset(9f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(9f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="HM", initialPosition=androidx.compose.ui.geometry.Offset(8f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Technology_puzzle_003",
                theme = "Technology",
                words = listOf("ALGORITHM"),
                fragments = listOf(
                    WordFragment(id=0, text="AL", initialPosition=androidx.compose.ui.geometry.Offset(4f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="GO", initialPosition=androidx.compose.ui.geometry.Offset(0f, 1f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 1f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="RIT", initialPosition=androidx.compose.ui.geometry.Offset(7f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(7f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="HM", initialPosition=androidx.compose.ui.geometry.Offset(5f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Technology_puzzle_004",
                theme = "Technology",
                words = listOf("ALGORITHM"),
                fragments = listOf(
                    WordFragment(id=0, text="AL", initialPosition=androidx.compose.ui.geometry.Offset(0f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(0f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="GOR", initialPosition=androidx.compose.ui.geometry.Offset(6f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(6f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="IT", initialPosition=androidx.compose.ui.geometry.Offset(1f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(5f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="HM", initialPosition=androidx.compose.ui.geometry.Offset(5f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
            PregeneratedBonza(
                id = "Bonza_Technology_puzzle_005",
                theme = "Technology",
                words = listOf("SOFTWARE"),
                fragments = listOf(
                    WordFragment(id=0, text="SO", initialPosition=androidx.compose.ui.geometry.Offset(1f, 8f), currentPosition=androidx.compose.ui.geometry.Offset(1f, 8f), solvedPosition=androidx.compose.ui.geometry.Offset(0f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=1, text="FT", initialPosition=androidx.compose.ui.geometry.Offset(8f, 4f), currentPosition=androidx.compose.ui.geometry.Offset(8f, 4f), solvedPosition=androidx.compose.ui.geometry.Offset(2f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=2, text="WAR", initialPosition=androidx.compose.ui.geometry.Offset(4f, 3f), currentPosition=androidx.compose.ui.geometry.Offset(4f, 3f), solvedPosition=androidx.compose.ui.geometry.Offset(4f, 0f), direction=ConnectionDirection.HORIZONTAL),
                    WordFragment(id=3, text="E", initialPosition=androidx.compose.ui.geometry.Offset(5f, 5f), currentPosition=androidx.compose.ui.geometry.Offset(5f, 5f), solvedPosition=androidx.compose.ui.geometry.Offset(7f, 0f), direction=ConnectionDirection.HORIZONTAL),
                )
            ),
        )
    }

    val PUZZLES_BY_THEME: Map<String, List<PregeneratedBonza>> by lazy { ALL_PUZZLES.groupBy { it.theme } }

    fun getPuzzleById(id: String): PregeneratedBonza? = ALL_PUZZLES.find { it.id == id }
}
