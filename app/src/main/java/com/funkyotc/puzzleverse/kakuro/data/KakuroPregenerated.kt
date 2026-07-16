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
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_10", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_11", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_12", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_13", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_14", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_15", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_2", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_3", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_4", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_5", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_6", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_7", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_8", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4)
                )
            )),
            PregeneratedKakuro("kakuro_easy_9", "Easy", 5, 5, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4)
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
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_10", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_2", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_3", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_4", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_5", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_6", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_7", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_8", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_hard_9", "Hard", 9, 9, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7), KakuroCell(CellType.BLACK, null, null, 0, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7), KakuroCell(CellType.BLACK, null, null, 1, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7), KakuroCell(CellType.BLACK, null, null, 2, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7), KakuroCell(CellType.BLACK, null, null, 3, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7), KakuroCell(CellType.BLACK, null, null, 4, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7), KakuroCell(CellType.BLACK, null, null, 5, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7), KakuroCell(CellType.BLACK, null, null, 6, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7), KakuroCell(CellType.BLACK, null, null, 7, 8)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 8, 0), KakuroCell(CellType.BLACK, null, null, 8, 1), KakuroCell(CellType.BLACK, null, null, 8, 2), KakuroCell(CellType.BLACK, null, null, 8, 3), KakuroCell(CellType.BLACK, null, null, 8, 4), KakuroCell(CellType.BLACK, null, null, 8, 5), KakuroCell(CellType.BLACK, null, null, 8, 6), KakuroCell(CellType.BLACK, null, null, 8, 7), KakuroCell(CellType.BLACK, null, null, 8, 8)
                )
            )),
            PregeneratedKakuro("kakuro_medium_1", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_10", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_2", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_3", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_4", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_5", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_6", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_7", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_8", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
            PregeneratedKakuro("kakuro_medium_9", "Medium", 7, 7, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6)
                )
            )),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
