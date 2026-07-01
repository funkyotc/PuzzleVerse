package com.funkyotc.puzzleverse.cubeshooter.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedLevel(
    override val id: String,
    override val difficulty: String,
    val cols: Int,
    val rows: Int,
    val grid: List<List<Int?>>,
    val tray: List<Tank>
) : BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String get() = "${cols}x${rows}"
}

object CubeShooterPregenerated {

    val ALL_LEVELS: List<PregeneratedLevel> by lazy {
        listOf(
            PregeneratedLevel("cubeshooter_easy_001", "Easy", 6, 6, listOf(
                listOf(
                    0, 1, 0, null, 3, null
                ),
                listOf(
                    0, 3, 0, null, 2, 0
                ),
                listOf(
                    1, 3, 1, 2, null, 2
                ),
                listOf(
                    null, 2, 2, 3, 1, 1
                ),
                listOf(
                    null, 1, 3, null, null, null
                ),
                listOf(
                    null, 2, 0, 3, null, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_002", "Easy", 6, 6, listOf(
                listOf(
                    2, null, null, null, 1, 1
                ),
                listOf(
                    0, 3, 2, 0, 2, 1
                ),
                listOf(
                    null, null, 3, 0, null, 1
                ),
                listOf(
                    null, null, 0, 3, null, 0
                ),
                listOf(
                    3, 2, 1, 2, 1, 0
                ),
                listOf(
                    null, null, 3, 2, null, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_003", "Easy", 6, 6, listOf(
                listOf(
                    null, null, null, null, null, null
                ),
                listOf(
                    null, 2, null, 1, 3, 3
                ),
                listOf(
                    null, 3, 0, 2, 0, 1
                ),
                listOf(
                    null, 0, 2, 1, null, 3
                ),
                listOf(
                    0, 2, null, 2, 2, 3
                ),
                listOf(
                    1, 3, 0, 1, 1, 0
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_004", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 1, 2, 2, 0
                ),
                listOf(
                    1, 1, 2, 2, 2, null
                ),
                listOf(
                    3, 3, 1, 3, 2, null
                ),
                listOf(
                    1, 1, null, null, 3, 3
                ),
                listOf(
                    null, null, null, null, 3, null
                ),
                listOf(
                    null, 0, 0, 0, null, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_005", "Easy", 6, 6, listOf(
                listOf(
                    1, null, 2, 1, null, null
                ),
                listOf(
                    1, 2, 1, 1, null, null
                ),
                listOf(
                    null, null, 2, 0, 0, 0
                ),
                listOf(
                    2, 3, 2, 3, null, 0
                ),
                listOf(
                    3, 0, null, 1, null, 2
                ),
                listOf(
                    3, null, 0, 3, null, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_006", "Easy", 6, 6, listOf(
                listOf(
                    0, 2, 1, null, null, 3
                ),
                listOf(
                    null, 1, null, null, null, 2
                ),
                listOf(
                    0, null, 3, 3, 0, 2
                ),
                listOf(
                    1, 1, 0, null, null, null
                ),
                listOf(
                    1, null, 2, 0, 3, null
                ),
                listOf(
                    null, null, 1, 2, 0, 3
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 5),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_007", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 2, null, 2, null
                ),
                listOf(
                    null, 0, null, 3, 1, null
                ),
                listOf(
                    2, 1, 1, 2, 3, null
                ),
                listOf(
                    0, 2, 3, 2, 3, 0
                ),
                listOf(
                    null, 1, 3, null, 1, null
                ),
                listOf(
                    null, 3, 0, 1, null, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_008", "Easy", 6, 6, listOf(
                listOf(
                    null, null, null, 0, 1, 0
                ),
                listOf(
                    null, 2, 0, 2, 0, 0
                ),
                listOf(
                    2, 3, 1, 2, null, 1
                ),
                listOf(
                    null, 1, 3, 2, null, 3
                ),
                listOf(
                    2, 3, 3, 0, 1, null
                ),
                listOf(
                    null, null, null, 1, null, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_009", "Easy", 6, 6, listOf(
                listOf(
                    2, 1, 0, null, 3, null
                ),
                listOf(
                    1, 0, null, null, 2, 2
                ),
                listOf(
                    null, 0, null, null, 2, 1
                ),
                listOf(
                    null, 3, 1, null, null, 3
                ),
                listOf(
                    null, 3, 0, 1, 0, 1
                ),
                listOf(
                    null, null, null, 2, 3, 0
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 5),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_010", "Easy", 6, 6, listOf(
                listOf(
                    null, 2, 1, 3, null, 2
                ),
                listOf(
                    2, 3, 1, 1, 0, 0
                ),
                listOf(
                    null, 0, 0, null, 0, null
                ),
                listOf(
                    null, 0, 3, 1, null, 2
                ),
                listOf(
                    null, 3, null, 3, 1, 2
                ),
                listOf(
                    null, null, null, 1, 2, 3
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_011", "Easy", 6, 6, listOf(
                listOf(
                    null, null, null, null, 3, null
                ),
                listOf(
                    null, null, 2, null, 1, 3
                ),
                listOf(
                    1, null, 0, 0, 3, 3
                ),
                listOf(
                    1, 0, 3, null, 1, 1
                ),
                listOf(
                    2, 1, null, null, 2, 0
                ),
                listOf(
                    0, 0, 2, null, 2, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 5),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_012", "Easy", 6, 6, listOf(
                listOf(
                    1, 2, 2, null, null, null
                ),
                listOf(
                    1, null, 0, 2, null, null
                ),
                listOf(
                    0, null, 1, 2, 1, null
                ),
                listOf(
                    3, 0, null, 2, 3, 3
                ),
                listOf(
                    3, 0, 2, 0, 0, 1
                ),
                listOf(
                    null, 1, null, 3, 3, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_013", "Easy", 6, 6, listOf(
                listOf(
                    1, 3, 1, 1, 2, null
                ),
                listOf(
                    0, null, null, 0, 2, 1
                ),
                listOf(
                    2, null, null, null, 1, 2
                ),
                listOf(
                    null, 3, 1, null, 3, 0
                ),
                listOf(
                    null, 2, null, 0, null, 0
                ),
                listOf(
                    null, 3, null, 2, 3, 0
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_014", "Easy", 6, 6, listOf(
                listOf(
                    null, null, 2, null, null, null
                ),
                listOf(
                    null, 1, 2, 2, 3, null
                ),
                listOf(
                    0, 0, 0, 2, 0, 3
                ),
                listOf(
                    null, 0, 3, 1, 1, 1
                ),
                listOf(
                    2, 1, null, 2, 0, 1
                ),
                listOf(
                    3, null, null, 3, 3, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_015", "Easy", 6, 6, listOf(
                listOf(
                    3, null, null, 1, 2, 3
                ),
                listOf(
                    1, 0, null, null, 0, 1
                ),
                listOf(
                    2, 1, 3, 0, 2, 2
                ),
                listOf(
                    null, null, null, 3, 0, 1
                ),
                listOf(
                    null, null, 0, 0, 2, null
                ),
                listOf(
                    null, 0, 3, 1, 2, 3
                )
            ), listOf(
                Tank(0, 7),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_016", "Easy", 6, 6, listOf(
                listOf(
                    null, null, 3, 0, 0, 2
                ),
                listOf(
                    null, 0, 3, null, null, 1
                ),
                listOf(
                    1, null, 3, 3, 2, null
                ),
                listOf(
                    2, 3, 0, null, 1, null
                ),
                listOf(
                    2, 1, 2, 1, 0, 0
                ),
                listOf(
                    2, 3, 1, 0, null, null
                )
            ), listOf(
                Tank(0, 7),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_017", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 0, 3, 0, null
                ),
                listOf(
                    null, 2, 1, null, 3, 3
                ),
                listOf(
                    null, null, 0, null, 1, null
                ),
                listOf(
                    null, null, 2, 3, 1, 1
                ),
                listOf(
                    null, null, 0, 2, 3, 1
                ),
                listOf(
                    null, null, 2, 2, 1, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 5),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_018", "Easy", 6, 6, listOf(
                listOf(
                    null, 0, 1, 3, 1, null
                ),
                listOf(
                    null, 0, 1, 0, 0, 1
                ),
                listOf(
                    null, null, 1, 2, 2, null
                ),
                listOf(
                    null, null, 0, 2, null, null
                ),
                listOf(
                    null, null, 2, null, null, null
                ),
                listOf(
                    3, 0, 2, 3, 3, 3
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 5),
                Tank(2, 5),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_easy_019", "Easy", 6, 6, listOf(
                listOf(
                    1, 3, 0, null, 0, 0
                ),
                listOf(
                    null, 2, 0, 2, 0, 1
                ),
                listOf(
                    null, 2, 1, null, null, null
                ),
                listOf(
                    null, 2, 3, 0, 2, 3
                ),
                listOf(
                    null, 3, 1, 1, 3, 2
                ),
                listOf(
                    null, null, 3, null, 1, null
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 6)
            )),
            PregeneratedLevel("cubeshooter_easy_020", "Easy", 6, 6, listOf(
                listOf(
                    0, 3, 3, 1, null, null
                ),
                listOf(
                    0, 2, null, 0, null, null
                ),
                listOf(
                    0, 1, null, 2, 1, null
                ),
                listOf(
                    2, 1, null, 0, 2, 3
                ),
                listOf(
                    2, 1, null, 3, null, null
                ),
                listOf(
                    null, 1, null, 2, 3, 0
                )
            ), listOf(
                Tank(0, 6),
                Tank(1, 6),
                Tank(2, 6),
                Tank(3, 5)
            )),
            PregeneratedLevel("cubeshooter_hard_001", "Hard", 10, 10, listOf(
                listOf(
                    null, 2, null, null, null, null, 1, 3, null, null
                ),
                listOf(
                    3, 3, 2, 3, 2, 0, 3, 0, null, null
                ),
                listOf(
                    null, 1, 1, 2, 3, 1, 0, 2, 0, 1
                ),
                listOf(
                    0, 2, 1, null, 3, null, 0, 3, 3, null
                ),
                listOf(
                    null, 2, 1, 3, 1, null, 2, 1, 2, null
                ),
                listOf(
                    null, null, 0, 1, 2, 1, null, null, 3, 2
                ),
                listOf(
                    1, 1, 0, null, 1, null, 1, null, 0, 0
                ),
                listOf(
                    null, 2, null, null, 3, 0, 3, 2, null, 0
                ),
                listOf(
                    null, 3, 0, 1, 2, 1, 0, 0, 0, 2
                ),
                listOf(
                    null, null, null, null, 2, 0, 3, null, 3, null
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 17),
                Tank(2, 16),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_002", "Hard", 10, 10, listOf(
                listOf(
                    null, 3, 0, 2, null, 3, 0, 2, null, 3
                ),
                listOf(
                    null, 3, 0, null, null, 0, 1, 2, 2, 1
                ),
                listOf(
                    0, 1, 2, 0, 3, 3, 0, 0, 2, 1
                ),
                listOf(
                    null, null, null, 3, 1, null, null, 3, null, null
                ),
                listOf(
                    1, 2, 2, 2, 2, 1, 1, null, null, null
                ),
                listOf(
                    null, 1, 0, 0, null, null, null, 0, 3, null
                ),
                listOf(
                    0, 2, 2, 3, 1, 3, 3, 1, 1, null
                ),
                listOf(
                    null, 2, 2, null, 3, 3, null, null, 1, null
                ),
                listOf(
                    null, 1, 0, null, null, 0, null, null, 0, null
                ),
                listOf(
                    null, null, null, null, 3, 1, 0, null, 2, null
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_003", "Hard", 10, 10, listOf(
                listOf(
                    3, null, null, null, 1, 2, 2, 1, 0, 2
                ),
                listOf(
                    3, null, null, 0, 2, 0, null, 2, 1, null
                ),
                listOf(
                    1, 3, 0, null, null, 0, 1, 2, 2, 1
                ),
                listOf(
                    null, null, 0, 3, 0, 0, 1, 1, 3, 2
                ),
                listOf(
                    null, 0, 3, 2, null, 1, 2, 0, null, 0
                ),
                listOf(
                    null, 0, null, 0, 1, null, null, 3, 3, 0
                ),
                listOf(
                    null, 2, null, 2, null, null, 2, 2, 1, null
                ),
                listOf(
                    null, 1, null, 3, null, null, 1, null, 3, null
                ),
                listOf(
                    2, 0, 2, 1, 3, 3, 3, 0, null, null
                ),
                listOf(
                    3, null, 1, 1, null, null, 3, null, null, null
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_004", "Hard", 10, 10, listOf(
                listOf(
                    3, null, null, null, null, null, 0, null, null, null
                ),
                listOf(
                    1, 1, 2, 2, 2, 1, 0, 1, null, null
                ),
                listOf(
                    null, 0, 0, 3, 3, 2, 0, 1, 0, 0
                ),
                listOf(
                    1, 0, null, 1, null, null, 3, null, 1, null
                ),
                listOf(
                    null, 0, 1, 2, null, 0, 0, 1, null, null
                ),
                listOf(
                    null, 2, 3, 3, 1, 2, 1, 0, 3, null
                ),
                listOf(
                    0, null, 0, 2, 2, 2, 1, null, 3, 1
                ),
                listOf(
                    3, 3, 2, 1, 1, null, 3, 3, 2, null
                ),
                listOf(
                    null, null, 0, 3, 2, 2, 3, null, 2, 1
                ),
                listOf(
                    null, null, null, 3, 0, 3, 2, null, 0, 2
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 17),
                Tank(2, 17),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_005", "Hard", 10, 10, listOf(
                listOf(
                    null, null, null, 3, 3, null, 3, 0, 2, 1
                ),
                listOf(
                    1, 3, null, 0, 2, 2, null, null, 0, 1
                ),
                listOf(
                    2, 0, 3, null, 1, 0, 2, null, 1, 3
                ),
                listOf(
                    3, 1, 0, null, null, 0, null, 3, 1, 1
                ),
                listOf(
                    null, null, 3, 2, null, null, null, 2, null, null
                ),
                listOf(
                    0, 3, null, 1, null, 0, null, 1, 0, 2
                ),
                listOf(
                    1, null, null, 3, 1, 2, 3, 0, null, null
                ),
                listOf(
                    3, null, 1, 2, 1, null, 2, 2, 2, 3
                ),
                listOf(
                    0, null, null, 2, null, 3, 0, null, 1, 0
                ),
                listOf(
                    0, 0, 2, null, null, null, null, null, null, 1
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_006", "Hard", 10, 10, listOf(
                listOf(
                    null, 2, 0, 2, 2, 3, 2, 1, 3, 3
                ),
                listOf(
                    null, null, 1, 2, 0, 2, null, null, 0, null
                ),
                listOf(
                    null, null, 3, 1, 0, null, 2, 1, 0, null
                ),
                listOf(
                    null, 2, null, null, null, null, null, null, 1, 3
                ),
                listOf(
                    null, 3, 1, 3, 1, null, null, 1, 1, 0
                ),
                listOf(
                    3, 2, 1, 2, 2, 3, null, 1, null, 1
                ),
                listOf(
                    null, null, null, 1, 0, null, 3, 2, 0, 2
                ),
                listOf(
                    2, null, null, 2, 1, null, null, null, 3, 3
                ),
                listOf(
                    0, 1, 0, 3, 0, 1, 3, 0, null, 0
                ),
                listOf(
                    0, 0, 0, 1, null, 0, 2, 3, 3, null
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 17),
                Tank(2, 16),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_007", "Hard", 10, 10, listOf(
                listOf(
                    null, 2, null, 2, 2, 2, null, null, null, null
                ),
                listOf(
                    3, 0, 3, 2, 3, 0, null, 3, 2, 0
                ),
                listOf(
                    null, 3, 0, null, 3, 0, 1, null, null, 1
                ),
                listOf(
                    1, 3, null, null, null, 0, 3, 1, 0, 3
                ),
                listOf(
                    1, 3, 3, null, null, null, 0, null, 1, null
                ),
                listOf(
                    null, null, 2, 1, 3, null, null, 0, 1, null
                ),
                listOf(
                    2, 1, null, 2, 1, null, null, null, 1, 1
                ),
                listOf(
                    2, 1, 2, 0, null, 0, 3, 2, null, 0
                ),
                listOf(
                    1, 0, 2, 0, 1, null, 3, null, 0, 0
                ),
                listOf(
                    null, null, null, 2, 2, 3, null, null, 1, null
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_008", "Hard", 10, 10, listOf(
                listOf(
                    null, 0, 1, 0, 0, null, null, null, null, null
                ),
                listOf(
                    3, 3, 0, 1, 2, 2, 1, null, 3, null
                ),
                listOf(
                    3, 3, 2, 1, 0, 2, 3, null, 0, null
                ),
                listOf(
                    1, null, 2, 3, 3, 2, 0, 1, 2, null
                ),
                listOf(
                    null, null, 1, null, null, null, 3, null, null, null
                ),
                listOf(
                    0, 1, 3, 3, null, 1, 2, 0, 0, null
                ),
                listOf(
                    null, 2, null, 0, 0, null, 2, 2, 3, 2
                ),
                listOf(
                    1, 0, null, 1, 2, null, 2, null, null, 3
                ),
                listOf(
                    3, 0, 1, 2, 1, null, null, null, 0, 1
                ),
                listOf(
                    3, null, 0, null, null, null, null, 1, 2, 1
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_009", "Hard", 10, 10, listOf(
                listOf(
                    null, 0, 0, null, null, null, null, null, null, null
                ),
                listOf(
                    1, 0, null, 2, 1, 2, 3, 2, 3, 1
                ),
                listOf(
                    1, 0, null, null, 1, 1, 3, 3, 3, 2
                ),
                listOf(
                    0, 0, 0, null, 2, 1, 3, 2, null, 2
                ),
                listOf(
                    3, 0, 1, null, 0, 2, 3, null, 0, 2
                ),
                listOf(
                    0, 1, 0, 2, 2, 1, 0, null, 2, 2
                ),
                listOf(
                    null, 1, null, 1, 1, 3, 3, 2, 3, 3
                ),
                listOf(
                    null, null, null, null, 0, null, null, null, null, 1
                ),
                listOf(
                    3, 2, 1, 0, 0, null, 3, 3, 3, null
                ),
                listOf(
                    null, null, 2, 1, null, null, null, 0, null, null
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_010", "Hard", 10, 10, listOf(
                listOf(
                    null, null, 1, 3, null, null, null, 3, null, null
                ),
                listOf(
                    null, 0, 2, null, null, 1, 2, 2, 3, null
                ),
                listOf(
                    null, 0, 0, null, 2, 0, null, 0, null, null
                ),
                listOf(
                    1, 0, null, 0, null, 3, 0, 3, null, 0
                ),
                listOf(
                    null, null, null, 1, 1, null, 2, null, 1, 3
                ),
                listOf(
                    null, 1, null, 3, 3, 1, 2, 3, 2, 3
                ),
                listOf(
                    0, 3, 1, 3, 3, 1, 1, 0, 1, 0
                ),
                listOf(
                    null, 2, 1, 2, 0, 3, 0, 1, 2, 2
                ),
                listOf(
                    null, null, 1, 0, 2, null, null, 1, null, 2
                ),
                listOf(
                    null, null, null, null, 0, null, 3, 2, null, 2
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_011", "Hard", 10, 10, listOf(
                listOf(
                    null, null, null, 1, 2, 0, 0, 3, 3, 0
                ),
                listOf(
                    1, 1, 1, 3, 0, 3, null, 2, null, 2
                ),
                listOf(
                    3, 2, null, null, 2, 0, 2, 1, null, 2
                ),
                listOf(
                    null, 1, 3, 1, 3, null, null, 1, null, null
                ),
                listOf(
                    null, 1, 3, 2, null, null, 2, 0, 3, null
                ),
                listOf(
                    3, 0, 2, 1, 1, null, null, 0, 3, null
                ),
                listOf(
                    null, 0, null, 3, null, null, 2, 2, null, null
                ),
                listOf(
                    null, null, 0, 2, 1, null, null, 0, null, 3
                ),
                listOf(
                    null, 1, 3, 1, null, 0, 2, 1, 3, 0
                ),
                listOf(
                    null, 0, 0, null, 0, 3, 2, null, 1, 2
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_012", "Hard", 10, 10, listOf(
                listOf(
                    null, null, null, null, 2, 3, null, 2, null, 3
                ),
                listOf(
                    null, 0, 2, 3, 0, 3, 0, 3, 3, 2
                ),
                listOf(
                    null, 1, 2, 3, 1, 0, null, 1, 3, 3
                ),
                listOf(
                    1, 0, null, 1, null, null, 3, 0, 0, 1
                ),
                listOf(
                    2, 2, 1, 3, 3, 3, 2, null, 0, null
                ),
                listOf(
                    0, 0, 3, 0, 1, null, null, null, 2, 1
                ),
                listOf(
                    null, null, 1, 2, 1, null, 0, 1, 1, null
                ),
                listOf(
                    null, null, null, 2, 1, 0, null, 1, 2, null
                ),
                listOf(
                    null, 0, 0, null, 2, 1, null, 1, 2, null
                ),
                listOf(
                    null, 3, 2, null, null, 2, 3, 0, 2, 0
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 17),
                Tank(2, 17),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_013", "Hard", 10, 10, listOf(
                listOf(
                    null, 3, null, null, 1, null, null, null, 1, null
                ),
                listOf(
                    null, 3, 0, 0, 3, 2, 2, 0, 3, 1
                ),
                listOf(
                    null, 3, 0, 1, 3, 1, 2, 0, 2, null
                ),
                listOf(
                    null, null, null, 3, 0, 3, 0, 0, null, null
                ),
                listOf(
                    null, null, null, 1, 1, 1, 2, 1, 2, 0
                ),
                listOf(
                    0, 0, 0, null, null, 1, 1, 0, null, null
                ),
                listOf(
                    null, null, 2, null, 2, 2, null, 1, 3, 2
                ),
                listOf(
                    null, 0, 1, 2, 3, 3, 3, null, 2, null
                ),
                listOf(
                    null, 2, 1, 3, null, 2, 3, 0, 1, 1
                ),
                listOf(
                    2, 3, null, null, null, null, null, 2, 0, null
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_014", "Hard", 10, 10, listOf(
                listOf(
                    null, 0, 1, null, null, null, null, null, null, null
                ),
                listOf(
                    1, 3, 3, 1, 2, 3, 3, 1, null, null
                ),
                listOf(
                    3, 3, 2, null, null, 3, 0, 2, null, null
                ),
                listOf(
                    2, 0, 0, 0, 1, 3, 1, null, 1, null
                ),
                listOf(
                    0, 1, null, null, null, 2, null, 2, 1, 1
                ),
                listOf(
                    0, 2, 0, 1, 0, 1, 3, 0, null, 3
                ),
                listOf(
                    2, null, 0, 0, 1, null, null, 2, null, 3
                ),
                listOf(
                    0, 2, 0, 2, 0, 1, 3, null, 1, 3
                ),
                listOf(
                    2, 3, 3, 2, null, 0, 2, 0, null, null
                ),
                listOf(
                    null, 3, null, null, null, 2, null, 2, 1, null
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_015", "Hard", 10, 10, listOf(
                listOf(
                    null, null, null, null, null, null, 3, null, null, null
                ),
                listOf(
                    1, 3, 3, 0, null, 3, 2, null, 3, 1
                ),
                listOf(
                    1, 1, 2, 1, null, 0, null, 1, 2, 0
                ),
                listOf(
                    null, null, 0, 2, 0, null, 3, 3, null, 2
                ),
                listOf(
                    null, 2, 0, 2, 0, 3, 2, null, null, 1
                ),
                listOf(
                    null, null, 0, 1, 3, null, 3, null, null, 1
                ),
                listOf(
                    0, 2, 2, null, 1, 3, 0, 0, null, null
                ),
                listOf(
                    0, 3, 3, 1, 1, 0, 0, 2, 1, null
                ),
                listOf(
                    0, null, null, null, null, 2, null, 2, 3, 1
                ),
                listOf(
                    null, null, null, null, 2, 1, 2, 1, 0, 3
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_016", "Hard", 10, 10, listOf(
                listOf(
                    null, 1, 2, null, null, 1, 3, null, null, null
                ),
                listOf(
                    3, 2, 1, 3, 1, null, 3, 2, null, null
                ),
                listOf(
                    1, 0, 0, null, 2, 2, null, 2, 2, null
                ),
                listOf(
                    0, 1, 2, null, null, 1, 1, 0, null, 0
                ),
                listOf(
                    null, null, 0, null, null, 1, null, 2, null, 1
                ),
                listOf(
                    null, 1, 1, 2, 0, 0, 3, 3, 0, 3
                ),
                listOf(
                    null, 3, null, 3, 0, null, 2, 2, 1, null
                ),
                listOf(
                    0, 3, 2, null, 3, null, 2, 0, 3, null
                ),
                listOf(
                    null, null, 3, 1, 0, 0, null, null, 1, 0
                ),
                listOf(
                    null, 3, 0, null, 3, 2, null, null, 1, null
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_017", "Hard", 10, 10, listOf(
                listOf(
                    null, null, null, 3, null, null, null, null, 3, null
                ),
                listOf(
                    null, null, 0, 3, null, 2, 0, 1, 1, null
                ),
                listOf(
                    null, 3, 1, 3, 2, 1, null, null, 0, 0
                ),
                listOf(
                    1, 0, 2, 3, 2, 1, null, 0, 0, 1
                ),
                listOf(
                    null, null, 3, 1, 0, null, 1, 1, 0, 2
                ),
                listOf(
                    2, 3, 2, 1, 0, null, 1, 2, null, null
                ),
                listOf(
                    1, null, 3, 2, null, 3, 3, 1, 2, null
                ),
                listOf(
                    0, 3, 2, 3, 3, 0, null, 1, 0, 2
                ),
                listOf(
                    0, 2, 3, 3, null, 2, 2, null, null, null
                ),
                listOf(
                    null, null, null, 0, null, null, null, 0, 2, 1
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 16)
            )),
            PregeneratedLevel("cubeshooter_hard_018", "Hard", 10, 10, listOf(
                listOf(
                    1, null, null, null, null, null, null, 0, null, null
                ),
                listOf(
                    0, 0, 0, 3, 2, null, 3, 1, 3, 2
                ),
                listOf(
                    null, 3, null, null, 1, null, 2, 0, null, 2
                ),
                listOf(
                    null, null, null, null, 3, null, 2, null, null, 1
                ),
                listOf(
                    1, 3, 0, 1, 3, null, 0, null, 1, null
                ),
                listOf(
                    2, null, 1, null, null, 0, 0, 3, 3, null
                ),
                listOf(
                    2, 2, 3, 3, 0, null, null, 3, 1, null
                ),
                listOf(
                    1, 0, 0, 1, 2, 1, 2, null, null, null
                ),
                listOf(
                    3, 0, null, 2, 1, 1, 0, 2, 2, 0
                ),
                listOf(
                    2, 3, 2, null, null, null, 0, 1, 3, null
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_hard_019", "Hard", 10, 10, listOf(
                listOf(
                    null, null, null, null, 1, 3, null, 1, 0, 3
                ),
                listOf(
                    null, 0, 1, 3, 1, 0, 3, 0, 1, 2
                ),
                listOf(
                    null, null, 3, 0, 3, 1, 2, 0, null, 3
                ),
                listOf(
                    null, null, 3, null, 1, 3, 0, 0, 2, 0
                ),
                listOf(
                    0, 2, 0, null, 2, 2, 2, 0, 0, 2
                ),
                listOf(
                    2, 3, 2, null, null, null, 3, null, 1, 1
                ),
                listOf(
                    null, 1, 1, 2, null, null, null, 1, 0, null
                ),
                listOf(
                    3, null, 1, 2, 3, 1, 0, 1, 2, null
                ),
                listOf(
                    3, 1, null, 2, 2, 0, 3, null, 0, null
                ),
                listOf(
                    null, 2, null, 1, 2, 3, 3, null, null, null
                )
            ), listOf(
                Tank(0, 17),
                Tank(1, 17),
                Tank(2, 17),
                Tank(3, 17)
            )),
            PregeneratedLevel("cubeshooter_hard_020", "Hard", 10, 10, listOf(
                listOf(
                    null, 0, null, 2, null, null, null, null, 1, 2
                ),
                listOf(
                    null, 3, 3, 3, 2, null, null, null, 3, 0
                ),
                listOf(
                    2, 2, null, 0, 0, 3, null, 1, null, null
                ),
                listOf(
                    2, 3, null, null, 2, 1, 1, 3, 1, 0
                ),
                listOf(
                    2, 0, 2, null, null, 1, 1, 2, 0, null
                ),
                listOf(
                    null, null, 2, 3, null, 2, 2, 1, 0, null
                ),
                listOf(
                    null, 1, 1, 0, 2, 3, 2, null, 2, null
                ),
                listOf(
                    0, 1, 0, 3, null, 1, null, 1, 1, null
                ),
                listOf(
                    null, null, 3, 0, null, 0, null, null, 3, null
                ),
                listOf(
                    null, null, 3, 1, 0, 0, 0, 3, 1, 3
                )
            ), listOf(
                Tank(0, 16),
                Tank(1, 16),
                Tank(2, 16),
                Tank(3, 15)
            )),
            PregeneratedLevel("cubeshooter_medium_001", "Medium", 8, 8, listOf(
                listOf(
                    null, null, null, 3, null, null, null, null
                ),
                listOf(
                    null, 3, 0, 3, 2, null, 2, null
                ),
                listOf(
                    null, 2, null, 0, 1, 2, 3, null
                ),
                listOf(
                    3, 1, 2, 0, null, 0, 1, null
                ),
                listOf(
                    0, 1, null, 1, null, 2, 3, null
                ),
                listOf(
                    null, 1, 0, 0, 1, 1, 3, null
                ),
                listOf(
                    null, 2, 1, 1, null, 0, 0, 2
                ),
                listOf(
                    null, 2, null, 0, null, 2, 3, 3
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_002", "Medium", 8, 8, listOf(
                listOf(
                    1, null, 0, 1, 2, null, 2, 3
                ),
                listOf(
                    2, 3, null, 1, 1, null, 1, 2
                ),
                listOf(
                    0, 0, null, 0, 2, null, 2, null
                ),
                listOf(
                    null, 3, null, 3, 2, null, 0, null
                ),
                listOf(
                    1, 0, null, 3, 0, 0, 0, null
                ),
                listOf(
                    null, 1, null, 1, null, 3, 0, null
                ),
                listOf(
                    3, 3, 2, 2, 3, null, 1, 0
                ),
                listOf(
                    null, 3, 1, 2, null, null, null, null
                )
            ), listOf(
                Tank(0, 11),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_003", "Medium", 8, 8, listOf(
                listOf(
                    null, null, null, 0, null, 1, 2, 2
                ),
                listOf(
                    null, 3, 2, 3, 1, 2, 0, 0
                ),
                listOf(
                    null, 3, 0, 2, 1, null, 0, 1
                ),
                listOf(
                    1, 0, null, 0, null, null, null, null
                ),
                listOf(
                    3, 0, null, 0, 2, 2, null, null
                ),
                listOf(
                    3, 1, 3, 1, null, 2, 3, 3
                ),
                listOf(
                    3, 1, null, null, null, null, 3, null
                ),
                listOf(
                    null, 0, 2, null, 1, 1, 2, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_004", "Medium", 8, 8, listOf(
                listOf(
                    null, null, null, 3, 2, 0, null, null
                ),
                listOf(
                    null, null, 1, null, 0, null, 1, 0
                ),
                listOf(
                    2, 3, 2, null, null, null, 0, null
                ),
                listOf(
                    3, null, 2, 0, null, 0, 1, null
                ),
                listOf(
                    2, null, 3, 2, 3, 2, 0, 1
                ),
                listOf(
                    3, 3, 1, 2, null, null, 0, 0
                ),
                listOf(
                    null, 2, 1, null, null, 3, 0, 1
                ),
                listOf(
                    null, null, 1, 1, null, 3, 1, 2
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_005", "Medium", 8, 8, listOf(
                listOf(
                    null, 0, null, null, null, null, null, null
                ),
                listOf(
                    1, 2, 1, null, null, null, 3, null
                ),
                listOf(
                    null, 2, 0, null, 3, null, 0, null
                ),
                listOf(
                    3, 2, 0, null, 3, 1, 1, null
                ),
                listOf(
                    3, 3, 1, null, 1, 3, 1, 2
                ),
                listOf(
                    null, null, 2, 2, 3, null, null, null
                ),
                listOf(
                    0, 2, 1, 3, 1, null, 1, 0
                ),
                listOf(
                    2, 2, null, 0, 0, null, 0, 0
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 9),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_006", "Medium", 8, 8, listOf(
                listOf(
                    3, 1, 3, 2, 2, 0, 1, null
                ),
                listOf(
                    2, 1, 2, null, null, null, 0, null
                ),
                listOf(
                    null, 0, 3, 2, 1, 3, 0, 1
                ),
                listOf(
                    null, 2, null, null, 1, 1, 1, null
                ),
                listOf(
                    1, 3, 3, 0, 3, 0, 0, 3
                ),
                listOf(
                    null, 2, null, 0, 2, 3, 3, null
                ),
                listOf(
                    null, null, 2, 2, 1, null, 1, 0
                ),
                listOf(
                    null, null, null, null, null, null, 0, 0
                )
            ), listOf(
                Tank(0, 11),
                Tank(1, 11),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_007", "Medium", 8, 8, listOf(
                listOf(
                    null, null, null, null, null, 0, 3, 1
                ),
                listOf(
                    null, 1, null, 1, 2, 1, 0, 2
                ),
                listOf(
                    1, 0, 0, 2, 2, null, 1, 2
                ),
                listOf(
                    null, 3, 2, null, 3, 3, null, 1
                ),
                listOf(
                    null, null, 3, 0, 3, 0, null, null
                ),
                listOf(
                    null, null, 3, null, null, null, 2, null
                ),
                listOf(
                    null, null, 0, 2, 0, null, 0, 0
                ),
                listOf(
                    1, 3, 3, null, 1, 1, null, 2
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 9),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_008", "Medium", 8, 8, listOf(
                listOf(
                    1, 3, 0, null, 0, null, 3, null
                ),
                listOf(
                    1, 0, null, null, 3, null, 2, 0
                ),
                listOf(
                    null, 0, 0, 1, 1, 1, 2, 3
                ),
                listOf(
                    null, 0, 0, null, null, null, null, 0
                ),
                listOf(
                    0, 2, null, null, 2, 3, 3, 2
                ),
                listOf(
                    3, 2, null, null, 2, 3, 1, 1
                ),
                listOf(
                    null, 2, 1, 3, 2, null, 1, 3
                ),
                listOf(
                    null, null, null, null, 0, 2, 1, null
                )
            ), listOf(
                Tank(0, 11),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_009", "Medium", 8, 8, listOf(
                listOf(
                    null, 3, 1, null, null, 0, 3, null
                ),
                listOf(
                    1, 1, 0, 0, 1, 0, null, null
                ),
                listOf(
                    1, 3, null, 0, 2, null, null, null
                ),
                listOf(
                    1, 3, null, null, 2, 1, 3, 2
                ),
                listOf(
                    2, 0, 0, 1, 1, 2, null, 3
                ),
                listOf(
                    null, 2, 2, 3, null, null, null, 3
                ),
                listOf(
                    1, 2, 3, 0, null, 0, null, null
                ),
                listOf(
                    2, 0, null, null, 3, 0, 2, null
                )
            ), listOf(
                Tank(0, 11),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_010", "Medium", 8, 8, listOf(
                listOf(
                    null, 3, 3, 1, null, null, 1, 0
                ),
                listOf(
                    1, 3, 3, 0, 1, 2, 2, 3
                ),
                listOf(
                    null, null, null, 2, null, 1, 0, 3
                ),
                listOf(
                    null, 1, 3, 0, null, 2, null, 2
                ),
                listOf(
                    null, 1, null, 2, 0, null, null, 3
                ),
                listOf(
                    2, 1, null, 2, 2, null, null, null
                ),
                listOf(
                    null, 1, null, 0, 1, null, null, null
                ),
                listOf(
                    3, 0, 0, null, 2, 0, 0, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_011", "Medium", 8, 8, listOf(
                listOf(
                    2, 0, null, null, null, null, null, null
                ),
                listOf(
                    2, 1, null, null, 3, 1, 1, 3
                ),
                listOf(
                    null, 0, 0, null, null, 0, null, 2
                ),
                listOf(
                    null, 1, 3, null, 3, 0, 1, 1
                ),
                listOf(
                    1, 2, null, null, 3, 2, 1, 3
                ),
                listOf(
                    1, 3, null, null, 3, null, 0, 2
                ),
                listOf(
                    null, 0, null, 3, 0, null, 2, 0
                ),
                listOf(
                    2, 1, 2, null, null, null, 2, 0
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_012", "Medium", 8, 8, listOf(
                listOf(
                    null, null, 0, null, null, null, 3, 2
                ),
                listOf(
                    null, 3, 0, 1, null, null, 0, 2
                ),
                listOf(
                    null, null, 3, null, 3, null, 1, 1
                ),
                listOf(
                    1, null, 1, null, 1, 0, null, null
                ),
                listOf(
                    3, 0, 2, 2, 2, 0, null, null
                ),
                listOf(
                    3, null, 1, null, 2, 2, 3, null
                ),
                listOf(
                    1, 2, 1, 0, 0, 2, 3, null
                ),
                listOf(
                    2, 1, 3, null, 0, 0, null, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_013", "Medium", 8, 8, listOf(
                listOf(
                    null, null, null, null, 0, null, null, 1
                ),
                listOf(
                    null, 1, null, null, 3, 3, 1, 1
                ),
                listOf(
                    3, 0, 2, null, 0, 3, 0, 2
                ),
                listOf(
                    null, 2, 1, 3, 0, 2, 1, null
                ),
                listOf(
                    null, null, 0, 0, null, null, 1, 2
                ),
                listOf(
                    null, null, 2, 2, null, 2, 0, null
                ),
                listOf(
                    0, 2, 3, 0, 3, 2, 3, 1
                ),
                listOf(
                    null, null, 3, 1, 1, null, null, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_014", "Medium", 8, 8, listOf(
                listOf(
                    2, null, null, 0, 2, 3, null, 0
                ),
                listOf(
                    3, 3, 2, 1, null, 3, 0, 3
                ),
                listOf(
                    2, null, 0, 0, 0, 1, null, null
                ),
                listOf(
                    2, null, 0, 3, 2, 3, null, null
                ),
                listOf(
                    0, null, 1, null, null, 2, null, 1
                ),
                listOf(
                    1, 3, 2, 3, null, 1, 0, 2
                ),
                listOf(
                    null, 0, null, 1, 2, 1, 1, 1
                ),
                listOf(
                    null, null, null, null, null, null, null, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_015", "Medium", 8, 8, listOf(
                listOf(
                    2, 3, null, null, 1, 1, 0, null
                ),
                listOf(
                    null, 1, 3, null, 2, 1, 3, 0
                ),
                listOf(
                    1, null, null, 2, 2, null, null, 3
                ),
                listOf(
                    3, 3, 3, 2, 2, null, null, 1
                ),
                listOf(
                    0, null, 0, 3, 2, 2, null, 0
                ),
                listOf(
                    null, 0, 2, 3, 1, 2, 1, null
                ),
                listOf(
                    3, null, 1, null, 0, 0, null, null
                ),
                listOf(
                    0, 0, 1, null, null, null, null, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_016", "Medium", 8, 8, listOf(
                listOf(
                    2, 3, null, null, 0, 3, 2, null
                ),
                listOf(
                    1, null, 0, null, 3, 0, 2, null
                ),
                listOf(
                    3, 2, 3, null, 2, 1, 2, 3
                ),
                listOf(
                    null, 3, 0, null, 1, null, null, 2
                ),
                listOf(
                    null, 0, 1, 1, null, null, 1, null
                ),
                listOf(
                    null, null, 1, 1, 1, 3, 2, null
                ),
                listOf(
                    0, 0, 2, 1, null, 0, 2, 3
                ),
                listOf(
                    null, null, 0, null, null, 3, 0, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10)
            )),
            PregeneratedLevel("cubeshooter_medium_017", "Medium", 8, 8, listOf(
                listOf(
                    null, 3, 1, 2, null, null, 0, 0
                ),
                listOf(
                    null, 2, 3, null, null, 2, 3, 1
                ),
                listOf(
                    null, 1, 3, 1, 1, 2, null, null
                ),
                listOf(
                    null, null, 2, 1, 1, 0, 0, 0
                ),
                listOf(
                    null, null, null, 0, 2, 2, 1, 3
                ),
                listOf(
                    null, 0, null, 3, 3, 2, null, null
                ),
                listOf(
                    null, 2, 1, 1, null, 2, 0, 0
                ),
                listOf(
                    null, null, null, null, null, 3, 3, 0
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_018", "Medium", 8, 8, listOf(
                listOf(
                    1, 0, 2, null, null, 3, null, null
                ),
                listOf(
                    null, null, 0, 0, 3, 1, 0, null
                ),
                listOf(
                    0, null, 3, 3, 1, 1, null, null
                ),
                listOf(
                    0, null, null, 3, null, 2, 3, null
                ),
                listOf(
                    0, 2, 2, 2, 2, 0, 3, null
                ),
                listOf(
                    3, 1, 2, 3, 2, 1, 2, null
                ),
                listOf(
                    null, null, 0, 1, null, 1, 1, null
                ),
                listOf(
                    null, null, null, 0, 2, 1, null, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_019", "Medium", 8, 8, listOf(
                listOf(
                    null, null, null, null, null, 0, 3, 3
                ),
                listOf(
                    null, 3, 0, 1, 1, 2, 2, null
                ),
                listOf(
                    2, null, null, 0, null, 1, 2, null
                ),
                listOf(
                    3, null, 1, 2, 0, null, null, 1
                ),
                listOf(
                    0, null, null, 3, 1, 1, null, 2
                ),
                listOf(
                    null, null, null, 3, 0, 0, 1, 2
                ),
                listOf(
                    1, 3, null, 0, 0, 2, 3, 1
                ),
                listOf(
                    3, 2, null, 0, null, null, 2, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
            PregeneratedLevel("cubeshooter_medium_020", "Medium", 8, 8, listOf(
                listOf(
                    0, 0, 0, null, null, 2, null, null
                ),
                listOf(
                    null, 2, 0, null, null, 2, 2, null
                ),
                listOf(
                    null, 0, 2, 0, 3, 3, null, null
                ),
                listOf(
                    null, 2, 2, 0, 1, null, null, null
                ),
                listOf(
                    null, 1, 1, 3, 0, 3, 3, 0
                ),
                listOf(
                    0, 2, 1, 1, 3, 1, 2, 2
                ),
                listOf(
                    3, 3, 1, null, null, 3, 1, null
                ),
                listOf(
                    null, null, null, null, null, 1, 1, null
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 9)
            )),
        )
    }

    val LEVELS_BY_DIFFICULTY: Map<String, List<PregeneratedLevel>> by lazy { ALL_LEVELS.groupBy { it.difficulty } }
}
