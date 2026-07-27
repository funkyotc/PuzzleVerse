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
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 10), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 14), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(11, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_10", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 17), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(13, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_11", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 18), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(17, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 12), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 13), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(24, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(5, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_12", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 27), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(7, 10), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(23, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(14, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_13", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 11), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(24, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 14), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(12, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_14", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 19), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(15, 4), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(8, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_15", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 22), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 16), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(10, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(11, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_2", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 29), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(16, 13), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(20, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_3", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 29), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(14, 11), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(23, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(7, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_4", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 20), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(22, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(4, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_5", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 29), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(12, 14), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(23, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_6", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 10), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 21), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(19, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_7", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 16), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 20), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(24, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(10, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_8", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 11), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 26), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(9, 5), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(11, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(18, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(4, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_9", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 15), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 11), null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(14, null), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_medium_1", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 20), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 29), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(14, 3), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 16), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(16, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(23, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(15, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_2", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 28), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 24), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(15, 3), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(22, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(11, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(16, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_3", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 27), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(10, 4), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(11, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(15, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5)
                )
            )),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
