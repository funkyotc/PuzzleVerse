package com.funkyotc.puzzleverse.kakuro.data

object KakuroPuzzleLibrary {
    fun getRandomPuzzle(): List<List<KakuroCell>> {
        val puzzles = listOf(
            // Puzzle 1
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
            // Puzzle 2
            listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0),
                    KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 1),
                    KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 2)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 1, 0),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2)
                )
            ),
            // Puzzle 3
            listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0),
                    KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 1),
                    KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 2)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 1, 0),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1),
                    KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2)
                )
            )
        )
        return puzzles.random()
    }
}
