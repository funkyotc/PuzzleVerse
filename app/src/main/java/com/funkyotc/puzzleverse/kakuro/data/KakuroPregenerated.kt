package com.funkyotc.puzzleverse.kakuro.data

data class PregeneratedKakuro(
    override val id: String,
    override val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<KakuroCell>>
) : com.funkyotc.puzzleverse.core.data.BrowseablePuzzle {
    override val label: String get() = id.replace("_", " ").replaceFirstChar { it.uppercase() }
    override val subtitle: String get() = "${rows}x${cols} • $difficulty"
}

object KakuroPregenerated {

    val ALL_PUZZLES: List<PregeneratedKakuro> by lazy {
        listOf(
            PregeneratedKakuro("kakuro_easy_1", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 1), null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_10", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_11", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(11, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_12", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_13", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(18, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_14", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(15, null), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_15", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_2", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(16, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_3", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 1), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_4", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(22, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(7, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_5", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(11, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(16, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_6", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(5, 5), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_7", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 11), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(8, 2), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_8", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(12, 4), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_easy_9", "Easy", 4, 4, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 2, 3)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3)
                )
            )),
            PregeneratedKakuro("kakuro_hard_1", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 1), null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 6), KakuroCell(CellType.CLUE, Clue(null, 1), null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.CLUE, Clue(4, 17), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 3), KakuroCell(CellType.CLUE, Clue(10, 5), null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(20, 3), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.CLUE, Clue(null, 4), null, 5, 6), KakuroCell(CellType.CLUE, Clue(null, 8), null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(5, null), null, 6, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 6, 4), KakuroCell(CellType.CLUE, Clue(12, 9), null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.CLUE, Clue(17, null), null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_2", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 32), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(17, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(15, null), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 6), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 18), null, 2, 3), KakuroCell(CellType.CLUE, Clue(21, 2), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 3, 1), KakuroCell(CellType.CLUE, Clue(8, 9), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.CLUE, Clue(null, 4), null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.CLUE, Clue(10, 8), null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(13, 3), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.CLUE, Clue(null, 1), null, 5, 6), KakuroCell(CellType.CLUE, Clue(null, 4), null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(10, null), null, 6, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(10, 6), null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.CLUE, Clue(7, null), null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_3", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(19, null), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 6), KakuroCell(CellType.CLUE, Clue(null, 7), null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 2, 2), KakuroCell(CellType.BLACK, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(20, 6), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 7), null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 6), KakuroCell(CellType.CLUE, Clue(null, 8), null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(34, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 6, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 6, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 6, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(26, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_4", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 5), null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 6), KakuroCell(CellType.CLUE, Clue(null, 1), null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 13), null, 2, 3), KakuroCell(CellType.CLUE, Clue(8, 3), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 5), null, 3, 1), KakuroCell(CellType.CLUE, Clue(10, 2), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.BLACK, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(null, 3), null, 5, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 5, 4), KakuroCell(CellType.CLUE, Clue(null, 7), null, 5, 5), KakuroCell(CellType.CLUE, Clue(null, 2), null, 5, 6), KakuroCell(CellType.CLUE, Clue(null, 1), null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.CLUE, Clue(21, null), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_5", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 9), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5), KakuroCell(CellType.BLACK, null, null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(21, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 3, 1), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5), KakuroCell(CellType.CLUE, Clue(null, 3), null, 3, 6), KakuroCell(CellType.CLUE, Clue(null, 8), null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(25, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(10, 4), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(5, null), null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 6, 4), KakuroCell(CellType.CLUE, Clue(null, 1), null, 6, 5), KakuroCell(CellType.CLUE, Clue(null, 2), null, 6, 6), KakuroCell(CellType.CLUE, Clue(null, 6), null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.CLUE, Clue(18, null), null, 7, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_6", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 21), null, 0, 5), KakuroCell(CellType.BLACK, null, null, 0, 6), KakuroCell(CellType.BLACK, null, null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 1), null, 1, 2), KakuroCell(CellType.CLUE, Clue(13, 17), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.CLUE, Clue(null, 1), null, 1, 6), KakuroCell(CellType.BLACK, null, null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(12, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(10, 8), null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(24, null), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.BLACK, null, null, 3, 6), KakuroCell(CellType.BLACK, null, null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5), KakuroCell(CellType.CLUE, Clue(null, 15), null, 4, 6), KakuroCell(CellType.CLUE, Clue(null, 2), null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 5, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 5, 4), KakuroCell(CellType.CLUE, Clue(7, 7), null, 5, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 6, 1), KakuroCell(CellType.CLUE, Clue(25, 9), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 6), KakuroCell(CellType.CLUE, Clue(null, 4), null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(17, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.CLUE, Clue(5, null), null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_7", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 6), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 1), KakuroCell(CellType.CLUE, Clue(27, 8), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(19, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5), KakuroCell(CellType.CLUE, Clue(null, 9), null, 2, 6), KakuroCell(CellType.CLUE, Clue(null, 2), null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 21), null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 3, 4), KakuroCell(CellType.CLUE, Clue(8, 8), null, 3, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 1), KakuroCell(CellType.CLUE, Clue(25, 2), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.BLACK, null, null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 6, 1), KakuroCell(CellType.CLUE, Clue(10, 4), null, 6, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 6, 4), KakuroCell(CellType.BLACK, null, null, 6, 5), KakuroCell(CellType.BLACK, null, null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 7, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 3), KakuroCell(CellType.BLACK, null, null, 7, 4), KakuroCell(CellType.BLACK, null, null, 7, 5), KakuroCell(CellType.BLACK, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_hard_8", "Hard", 8, 8, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 17), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 6), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 21), null, 1, 4), KakuroCell(CellType.CLUE, Clue(5, null), null, 1, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(16, 5), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 2), null, 2, 5), KakuroCell(CellType.BLACK, null, null, 2, 6), KakuroCell(CellType.BLACK, null, null, 2, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(6, 12), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5), KakuroCell(CellType.CLUE, Clue(null, 1), null, 3, 6), KakuroCell(CellType.CLUE, Clue(null, 5), null, 3, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 4, 1), KakuroCell(CellType.CLUE, Clue(16, 1), null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.CLUE, Clue(6, null), null, 4, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 6), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 7)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(15, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5), KakuroCell(CellType.BLACK, null, null, 5, 6), KakuroCell(CellType.BLACK, null, null, 5, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 6, 0), KakuroCell(CellType.BLACK, null, null, 6, 1), KakuroCell(CellType.BLACK, null, null, 6, 2), KakuroCell(CellType.BLACK, null, null, 6, 3), KakuroCell(CellType.BLACK, null, null, 6, 4), KakuroCell(CellType.CLUE, Clue(null, 2), null, 6, 5), KakuroCell(CellType.CLUE, Clue(null, 6), null, 6, 6), KakuroCell(CellType.BLACK, null, null, 6, 7)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 7, 0), KakuroCell(CellType.BLACK, null, null, 7, 1), KakuroCell(CellType.BLACK, null, null, 7, 2), KakuroCell(CellType.BLACK, null, null, 7, 3), KakuroCell(CellType.CLUE, Clue(8, null), null, 7, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 5), KakuroCell(CellType.PLAYER_INPUT, null, null, 7, 6), KakuroCell(CellType.BLACK, null, null, 7, 7)
                )
            )),
            PregeneratedKakuro("kakuro_medium_1", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(7, 6), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(14, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(10, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 5), null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(24, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_10", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(11, null), null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 1), KakuroCell(CellType.CLUE, Clue(21, 5), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(14, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 9), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.CLUE, Clue(16, null), null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_2", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(25, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(13, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.CLUE, Clue(16, null), null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_3", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 1), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(18, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.BLACK, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(9, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_4", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 12), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 3), null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.CLUE, Clue(13, 5), null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(7, null), null, 2, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 1), null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(19, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.BLACK, null, null, 4, 1), KakuroCell(CellType.BLACK, null, null, 4, 2), KakuroCell(CellType.BLACK, null, null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.CLUE, Clue(9, null), null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_5", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 2), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 19), null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(6, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 3), null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(15, 3), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(10, null), null, 3, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 1), null, 3, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 3, 4), KakuroCell(CellType.CLUE, Clue(null, 9), null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(17, null), null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_6", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 13), null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(21, null), null, 1, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 1, 4), KakuroCell(CellType.CLUE, Clue(null, 6), null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(19, null), null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(null, 2), null, 3, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 3, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(11, null), null, 4, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 4, 3), KakuroCell(CellType.BLACK, null, null, 4, 4), KakuroCell(CellType.BLACK, null, null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 5, 0), KakuroCell(CellType.BLACK, null, null, 5, 1), KakuroCell(CellType.BLACK, null, null, 5, 2), KakuroCell(CellType.BLACK, null, null, 5, 3), KakuroCell(CellType.BLACK, null, null, 5, 4), KakuroCell(CellType.BLACK, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_7", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.BLACK, null, null, 0, 3), KakuroCell(CellType.BLACK, null, null, 0, 4), KakuroCell(CellType.BLACK, null, null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.BLACK, null, null, 1, 2), KakuroCell(CellType.BLACK, null, null, 1, 3), KakuroCell(CellType.BLACK, null, null, 1, 4), KakuroCell(CellType.BLACK, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.BLACK, null, null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 4), null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.CLUE, Clue(11, null), null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 3), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 5), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 9), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(27, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_8", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 5), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 4), null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(16, null), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.BLACK, null, null, 2, 1), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 2), KakuroCell(CellType.CLUE, Clue(null, 2), null, 2, 3), KakuroCell(CellType.BLACK, null, null, 2, 4), KakuroCell(CellType.BLACK, null, null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.CLUE, Clue(8, null), null, 3, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 3), KakuroCell(CellType.BLACK, null, null, 3, 4), KakuroCell(CellType.BLACK, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 9), null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 5), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(26, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
            PregeneratedKakuro("kakuro_medium_9", "Medium", 6, 6, listOf(
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 0, 0), KakuroCell(CellType.BLACK, null, null, 0, 1), KakuroCell(CellType.BLACK, null, null, 0, 2), KakuroCell(CellType.CLUE, Clue(null, 7), null, 0, 3), KakuroCell(CellType.CLUE, Clue(null, 9), null, 0, 4), KakuroCell(CellType.CLUE, Clue(null, 1), null, 0, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 1, 0), KakuroCell(CellType.BLACK, null, null, 1, 1), KakuroCell(CellType.CLUE, Clue(16, 9), null, 1, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 1, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 2, 0), KakuroCell(CellType.CLUE, Clue(10, null), null, 2, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 2, 3), KakuroCell(CellType.CLUE, Clue(null, 8), null, 2, 4), KakuroCell(CellType.CLUE, Clue(null, 6), null, 2, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 3, 0), KakuroCell(CellType.BLACK, null, null, 3, 1), KakuroCell(CellType.BLACK, null, null, 3, 2), KakuroCell(CellType.CLUE, Clue(14, null), null, 3, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 3, 5)
                ),
                listOf(
                    KakuroCell(CellType.BLACK, null, null, 4, 0), KakuroCell(CellType.CLUE, Clue(null, 8), null, 4, 1), KakuroCell(CellType.CLUE, Clue(null, 2), null, 4, 2), KakuroCell(CellType.CLUE, Clue(null, 6), null, 4, 3), KakuroCell(CellType.CLUE, Clue(null, 4), null, 4, 4), KakuroCell(CellType.CLUE, Clue(null, 1), null, 4, 5)
                ),
                listOf(
                    KakuroCell(CellType.CLUE, Clue(21, null), null, 5, 0), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 1), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 2), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 3), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 4), KakuroCell(CellType.PLAYER_INPUT, null, null, 5, 5)
                )
            )),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }
}
