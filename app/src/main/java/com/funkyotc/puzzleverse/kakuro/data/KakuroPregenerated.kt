package com.funkyotc.puzzleverse.kakuro.data

data class PregeneratedKakuro(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<KakuroCell>>
)

object KakuroPregenerated {

    val ALL_PUZZLES: List<PregeneratedKakuro> by lazy {
        listOf(
            PregeneratedKakuro("kakuro_easy_1", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 11), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(4, 9), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(12, 1), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(5, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_10", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 23), null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 12), null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(3, null), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_11", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 18), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 10), null, 2, 2), KakuroCell(CellType.CLUE, Clue(3, null), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(5, 7), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(20, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_12", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 17), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 14), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 1, 2), KakuroCell(CellType.CLUE, Clue(8, 4), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(24, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(3, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(15, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_13", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(8, null), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 2), KakuroCell(CellType.CLUE, Clue(2, null), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(12, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_14", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(20, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(1, 4), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(3, 7), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_15", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(27, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 10), null, 2, 2), KakuroCell(CellType.CLUE, Clue(2, 7), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(11, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_2", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(9, null), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(3, 9), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 1), KakuroCell(CellType.CLUE, Clue(14, 8), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(18, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_3", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 14), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 1), KakuroCell(CellType.CLUE, Clue(9, 13), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(5, 1), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(9, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_4", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(14, 5), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(9, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_5", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 26), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(12, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_6", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 12), null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(3, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_7", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(4, null), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 2), KakuroCell(CellType.CLUE, Clue(3, 6), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(12, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(5, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_8", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 17), null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 18), null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(16, 10), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(9, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(3, null), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_9", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 14), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(17, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_hard_1", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 11), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 1, 3), KakuroCell(CellType.CLUE, Clue(2, 2), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(10, 5), null, 1, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 15), null, 2, 1), KakuroCell(CellType.CLUE, Clue(23, 6), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6), KakuroCell(CellType.CLUE, Clue(7, 13), null, 2, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 21), null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 2), null, 3, 4), KakuroCell(CellType.CLUE, Clue(null, 16), null, 3, 5), KakuroCell(CellType.CLUE, Clue(9, 1), null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(17, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 7), KakuroCell(CellType.CLUE, Clue(null, 8), null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(7, null), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(4, 14), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.CLUE, Clue(6, 12), null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.CLUE, Clue(22, null), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.CLUE, Clue(1, 3), null, 6, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.CLUE, Clue(8, null), null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.CLUE, Clue(10, 5), null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.CLUE, Clue(11, null), null, 8, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_10", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 3), KakuroCell(CellType.CLUE, Clue(14, 18), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.CLUE, Clue(8, 3), null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(12, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(7, 9), null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(4, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.CLUE, Clue(5, null), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.CLUE, Clue(null, 12), null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(5, 8), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(4, 14), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.CLUE, Clue(8, 21), null, 4, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(20, null), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.CLUE, Clue(9, null), null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(6, null), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.CLUE, Clue(3, null), null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.CLUE, Clue(null, 8), null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.CLUE, Clue(3, null), null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.CLUE, Clue(14, 1), null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.CLUE, Clue(8, null), null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_2", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 26), null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(16, 33), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.CLUE, Clue(8, null), null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.CLUE, Clue(9, 6), null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(2, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 10), null, 2, 4), KakuroCell(CellType.CLUE, Clue(16, 9), null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(17, 19), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.CLUE, Clue(null, 3), null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(9, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(2, 18), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.CLUE, Clue(1, 3), null, 4, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(6, 9), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(16, null), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(16, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(9, 12), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.CLUE, Clue(null, 12), null, 6, 7), KakuroCell(CellType.CLUE, Clue(null, 7), null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.CLUE, Clue(13, null), null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.CLUE, Clue(10, 2), null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 8, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.CLUE, Clue(8, null), null, 8, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 4), KakuroCell(CellType.CLUE, Clue(11, null), null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_3", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 21), null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(1, null), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 7), KakuroCell(CellType.CLUE, Clue(null, 21), null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(6, 2), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 5), KakuroCell(CellType.CLUE, Clue(12, null), null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(9, 30), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.CLUE, Clue(null, 7), null, 3, 6), KakuroCell(CellType.CLUE, Clue(4, null), null, 3, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(7, 1), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(1, 13), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.CLUE, Clue(5, 13), null, 4, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(5, 19), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(20, null), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(2, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.CLUE, Clue(9, null), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(8, null), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.CLUE, Clue(3, 9), null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.CLUE, Clue(null, 3), null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.CLUE, Clue(2, 1), null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.CLUE, Clue(17, 5), null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(23, null), null, 8, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 4), KakuroCell(CellType.CLUE, Clue(5, null), null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_4", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 6), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(36, 16), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(null, 11), null, 2, 6), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 7), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 4), KakuroCell(CellType.CLUE, Clue(17, 24), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(16, null), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.CLUE, Clue(null, 9), null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(null, 16), null, 5, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 5, 3), KakuroCell(CellType.CLUE, Clue(7, null), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.CLUE, Clue(null, 2), null, 5, 6), KakuroCell(CellType.CLUE, Clue(3, 19), null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(11, 2), null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(16, 6), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(11, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 2), KakuroCell(CellType.CLUE, Clue(15, null), null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.CLUE, Clue(8, 9), null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.CLUE, Clue(2, null), null, 8, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.CLUE, Clue(18, null), null, 8, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_5", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 10), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 28), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(8, 30), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.CLUE, Clue(6, null), null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(3, null), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 6), KakuroCell(CellType.CLUE, Clue(7, 9), null, 2, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 12), null, 3, 1), KakuroCell(CellType.CLUE, Clue(2, 6), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 3, 4), KakuroCell(CellType.CLUE, Clue(13, 21), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(28, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 8), null, 4, 6), KakuroCell(CellType.CLUE, Clue(8, null), null, 4, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(8, null), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(14, 3), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.CLUE, Clue(2, null), null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.CLUE, Clue(14, null), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.CLUE, Clue(4, null), null, 6, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 7, 1), KakuroCell(CellType.CLUE, Clue(1, 5), null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.CLUE, Clue(1, 5), null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 8, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 2), KakuroCell(CellType.CLUE, Clue(13, null), null, 8, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_6", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 17), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(5, 7), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 1, 3), KakuroCell(CellType.CLUE, Clue(1, null), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.CLUE, Clue(4, null), null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(3, null), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.CLUE, Clue(3, null), null, 2, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(2, 4), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(8, 22), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.CLUE, Clue(null, 33), null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(8, 6), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.CLUE, Clue(8, null), null, 4, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 7), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(19, null), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.CLUE, Clue(12, 8), null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 6, 1), KakuroCell(CellType.CLUE, Clue(null, 7), null, 6, 2), KakuroCell(CellType.CLUE, Clue(1, null), null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.CLUE, Clue(12, null), null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 2), KakuroCell(CellType.CLUE, Clue(4, 8), null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.CLUE, Clue(null, 3), null, 7, 5), KakuroCell(CellType.CLUE, Clue(7, null), null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7), KakuroCell(CellType.CLUE, Clue(null, 2), null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.CLUE, Clue(22, null), null, 8, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 5), KakuroCell(CellType.CLUE, Clue(7, null), null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_7", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 32), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 3), KakuroCell(CellType.CLUE, Clue(7, 13), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(9, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.CLUE, Clue(null, 9), null, 2, 7), KakuroCell(CellType.CLUE, Clue(null, 35), null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 3, 2), KakuroCell(CellType.CLUE, Clue(9, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.CLUE, Clue(13, 22), null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.CLUE, Clue(9, null), null, 4, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(null, 9), null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.CLUE, Clue(4, null), null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.CLUE, Clue(5, 1), null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(16, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.CLUE, Clue(12, 10), null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.CLUE, Clue(5, null), null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.CLUE, Clue(15, null), null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.CLUE, Clue(7, 9), null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.CLUE, Clue(3, null), null, 8, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 3), KakuroCell(CellType.CLUE, Clue(4, null), null, 8, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 5), KakuroCell(CellType.CLUE, Clue(11, null), null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_8", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 27), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 6), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(6, 8), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(16, 7), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(11, 7), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(12, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(9, null), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.CLUE, Clue(null, 1), null, 3, 7), KakuroCell(CellType.CLUE, Clue(null, 26), null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(3, 9), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 15), null, 4, 5), KakuroCell(CellType.CLUE, Clue(6, 2), null, 4, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(18, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(10, 6), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.CLUE, Clue(8, 5), null, 5, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.CLUE, Clue(11, null), null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.CLUE, Clue(11, 10), null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.CLUE, Clue(null, 1), null, 7, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 7, 3), KakuroCell(CellType.CLUE, Clue(9, 4), null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.CLUE, Clue(7, 6), null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.CLUE, Clue(11, null), null, 8, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 4), KakuroCell(CellType.CLUE, Clue(9, null), null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_9", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 14), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 7), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(10, null), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 3), KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(2, 7), null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(11, 9), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(16, 3), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(4, null), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 20), null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(null, 11), null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(2, null), null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.CLUE, Clue(8, null), null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.CLUE, Clue(null, 1), null, 5, 7), KakuroCell(CellType.CLUE, Clue(null, 10), null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.CLUE, Clue(11, null), null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(2, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 7, 4), KakuroCell(CellType.CLUE, Clue(2, 8), null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.CLUE, Clue(1, 7), null, 7, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 8, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 2), KakuroCell(CellType.CLUE, Clue(33, null), null, 8, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 7), KakuroCell(CellType.PLAYER_INPUT, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_medium_1", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(4, 7), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(11, 9), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(15, 23), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(null, 11), null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(10, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(10, 3), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(12, 9), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(2, 11), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(14, null), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(9, null), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.CLUE, Clue(7, null), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(2, null), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_10", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 25), null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 14), null, 2, 2), KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(5, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(5, null), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(6, 8), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.CLUE, Clue(9, 5), null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(19, null), null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_2", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 21), null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(6, null), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 24), null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 11), null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 3, 4), KakuroCell(CellType.CLUE, Clue(6, 6), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(17, 7), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.CLUE, Clue(5, 9), null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(5, null), null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(11, null), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_3", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 15), null, 1, 2), KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.CLUE, Clue(4, null), null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(2, 4), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.CLUE, Clue(5, null), null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(6, 12), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 5, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 5, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(16, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_4", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(8, 9), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 18), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 18), null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(20, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.CLUE, Clue(9, null), null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(null, 16), null, 3, 2), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.CLUE, Clue(2, null), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(7, 11), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(1, 8), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(7, 13), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(31, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(4, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(8, null), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_5", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 34), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(24, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 25), null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(14, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.CLUE, Clue(null, 4), null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(3, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(13, null), null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(7, null), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(8, 9), null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.CLUE, Clue(null, 4), null, 5, 5), KakuroCell(CellType.CLUE, Clue(null, 1), null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(22, null), null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_6", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(8, null), null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 15), null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(11, 7), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(6, 7), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.CLUE, Clue(9, null), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(8, 10), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 7), null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(3, 6), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 5, 3), KakuroCell(CellType.CLUE, Clue(13, 4), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_7", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 13), null, 1, 1), KakuroCell(CellType.CLUE, Clue(9, 5), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(18, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(1, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(11, 24), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.CLUE, Clue(null, 19), null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(17, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 4), KakuroCell(CellType.CLUE, Clue(8, 8), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(8, 1), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(18, 4), null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.CLUE, Clue(2, null), null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_8", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 26), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(20, 21), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(8, 1), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(9, null), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(3, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(4, null), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(6, 12), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.CLUE, Clue(2, 13), null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(17, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(15, null), null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(3, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.CLUE, Clue(4, null), null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_9", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(21, 10), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 32), null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(1, 13), null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.CLUE, Clue(9, 7), null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(2, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(8, 4), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.CLUE, Clue(13, null), null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(5, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(8, null), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(null, 6), null, 5, 1), KakuroCell(CellType.CLUE, Clue(4, 2), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.CLUE, Clue(4, null), null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.CLUE, Clue(5, null), null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6)
                )
            )),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
