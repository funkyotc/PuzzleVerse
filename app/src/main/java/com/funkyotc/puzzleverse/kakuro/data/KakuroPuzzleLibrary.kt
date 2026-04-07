package com.funkyotc.puzzleverse.kakuro.data

object KakuroPuzzleLibrary {
    fun getRandomPuzzle(): List<List<KakuroCell>> {
        val puzzles = listOf(
            // Puzzle 1 (3x3)
            listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0),
                    KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1),
                    KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 2)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(3, null), null, 1, 0),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 0),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2)
                )
            ),
            // Puzzle 2 (5x5)
            listOf(
                listOf(KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(11, 4), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 11), null, 1, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(16, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)),
                listOf(KakuroCell(CellType.CLUE, Clue(17, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)),
                listOf(KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(4, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4))
            )
        )
        return puzzles.random()
    }
}
