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
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(11, 4), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 11), null, 1, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(16, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(17, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(4, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4))
            ),
            // Level 3: 5x5
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 23), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 30), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(16, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 17), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 15), null, 1, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(24, 7), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(15, 8), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(14, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4))
            ),
            // Level 4: 6x6
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 17), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 23), null, 0, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(16, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(13, 8), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(25, 4), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(12, 14), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(15, 9), null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)),
                listOf(KakuroCell(CellType.CLUE, Clue(20, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 11), null, 4, 5)),
                listOf(KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(10, null), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5))
            ),
            // Level 5: 7x7
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 20), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 25), null, 0, 6)),
                listOf(KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(12, 10), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(14, 5), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6)),
                listOf(KakuroCell(CellType.CLUE, Clue(22, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)),
                listOf(KakuroCell(CellType.CLUE, Clue(15, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.CLUE, Clue(19, null), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(18, 12), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)),
                listOf(KakuroCell(CellType.CLUE, Clue(25, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)),
                listOf(KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.CLUE, Clue(10, null), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6))
            )
        )
        return puzzles.random()
    }
}
