package com.funkyotc.puzzleverse.kakuro.data

object KakuroPuzzleLibrary {
    fun getRandomPuzzle(): List<List<KakuroCell>> {
        val puzzles = listOf(
            // Level 1: 3x3
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 2)),
                listOf(KakuroCell(CellType.CLUE, Clue(3, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2)),
                listOf(KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2))
            ),
            // Level 2: 5x5
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 19), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 10), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(19, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(12, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(5, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4))
            ),
            // Level 3: 6x6
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 20), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 29), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(14, 3), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 16), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(16, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(23, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(15, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5))
            ),
            // Level 4: 6x6
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 28), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(15, 3), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(22, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(11, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(16, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5))
            )
        )
        return puzzles.random()
    }
}
