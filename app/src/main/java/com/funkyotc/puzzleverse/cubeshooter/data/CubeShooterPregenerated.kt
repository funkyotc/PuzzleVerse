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

    private fun getChunk0(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_001", "Easy", 6, 6, listOf(
                listOf(
                    0, 1, 1, 0, 1, 0
                ),
                listOf(
                    0, null, 0, 1, 1, 1
                ),
                listOf(
                    0, 1, 0, 1, 0, 1
                ),
                listOf(
                    0, 0, 0, 0, 0, 1
                ),
                listOf(
                    1, 0, 0, 0, 1, 0
                ),
                listOf(
                    0, 1, 0, 1, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk1(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_002", "Easy", 6, 6, listOf(
                listOf(
                    1, 1, 0, 1, 1, 0
                ),
                listOf(
                    0, 0, 1, 0, 0, 1
                ),
                listOf(
                    1, 1, 1, 1, 0, null
                ),
                listOf(
                    1, 1, 0, 1, 0, 0
                ),
                listOf(
                    0, 0, 1, 0, 1, 1
                ),
                listOf(
                    0, 0, 1, 1, 1, 1
                )
            ), listOf(
                Tank(0, 15),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk2(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_003", "Easy", 6, 6, listOf(
                listOf(
                    0, 1, 0, 1, 0, 0
                ),
                listOf(
                    0, 1, 1, 0, 0, 0
                ),
                listOf(
                    0, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 0, 0, 0, 1, 1
                ),
                listOf(
                    0, 0, 1, null, 1, 0
                ),
                listOf(
                    0, 1, 1, 0, 0, 0
                )
            ), listOf(
                Tank(1, 10),
                Tank(0, 25)
            )),
        )
    }

    private fun getChunk3(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_004", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 1, 1, 0, null
                ),
                listOf(
                    0, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 0, 0, 0, 0, 1
                ),
                listOf(
                    1, 1, 1, 1, 0, 1
                ),
                listOf(
                    0, 1, 1, 1, 0, 0
                ),
                listOf(
                    0, 1, 1, 1, 0, 0
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk4(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_005", "Easy", 6, 6, listOf(
                listOf(
                    1, null, 1, 1, 0, 1
                ),
                listOf(
                    0, 1, 0, 1, 1, 1
                ),
                listOf(
                    1, 1, 1, 0, 0, 1
                ),
                listOf(
                    1, 1, 1, 1, 1, 1
                ),
                listOf(
                    0, 1, 1, 1, 1, 0
                ),
                listOf(
                    0, 0, 1, 1, 1, 0
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 25)
            )),
        )
    }

    private fun getChunk5(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_006", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 0, 0, 0, 1
                ),
                listOf(
                    0, 1, 0, 0, 0, 1
                ),
                listOf(
                    1, 0, 1, 1, 0, 0
                ),
                listOf(
                    0, null, 1, 0, 0, 1
                ),
                listOf(
                    0, 1, 1, 0, 1, 1
                ),
                listOf(
                    0, 1, 0, 0, 1, 1
                )
            ), listOf(
                Tank(0, 20),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk6(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_007", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 1, 0, 1, 1
                ),
                listOf(
                    1, 1, 1, 1, 1, 0
                ),
                listOf(
                    0, 1, 1, 1, 1, 0
                ),
                listOf(
                    0, 0, 0, 1, 0, 0
                ),
                listOf(
                    1, 0, 1, 0, 1, 1
                ),
                listOf(
                    0, 1, null, 1, 1, 0
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk7(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_008", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 1, 0, 1, 0
                ),
                listOf(
                    0, 1, 1, 1, 0, 1
                ),
                listOf(
                    0, 0, 1, 0, 1, 1
                ),
                listOf(
                    1, 0, 1, 0, 1, 0
                ),
                listOf(
                    1, 0, 1, 1, 1, 1
                ),
                listOf(
                    0, 1, 0, null, 1, 1
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk8(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_009", "Easy", 6, 6, listOf(
                listOf(
                    1, 0, 0, 0, 1, 0
                ),
                listOf(
                    0, 1, 0, 1, 1, null
                ),
                listOf(
                    1, 1, 0, 1, 0, 1
                ),
                listOf(
                    0, 1, 0, 1, 1, 0
                ),
                listOf(
                    0, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 0, 1, 0, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk9(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_010", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 1, 1, 1, 0
                ),
                listOf(
                    0, 1, 1, 0, 1, 0
                ),
                listOf(
                    1, 1, null, 0, 1, 1
                ),
                listOf(
                    1, 1, 0, 0, 1, 1
                ),
                listOf(
                    1, 0, 1, 0, 1, 1
                ),
                listOf(
                    0, 0, 0, 0, 1, 1
                )
            ), listOf(
                Tank(0, 15),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk10(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_011", "Easy", 6, 6, listOf(
                listOf(
                    1, 1, 1, 1, null, 1
                ),
                listOf(
                    1, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 0, 1, 1, 1, 0
                ),
                listOf(
                    0, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 1, 1, 0, 0, 0
                ),
                listOf(
                    1, 1, 0, 0, 0, 1
                )
            ), listOf(
                Tank(0, 20),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk11(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_012", "Easy", 6, 6, listOf(
                listOf(
                    1, 1, 0, 0, 0, 1
                ),
                listOf(
                    0, 0, 0, 0, 1, 1
                ),
                listOf(
                    1, 0, 1, 0, 0, 1
                ),
                listOf(
                    0, 0, 1, 0, 1, null
                ),
                listOf(
                    1, 0, 0, 0, 0, 1
                ),
                listOf(
                    1, 1, 0, 0, 1, 0
                )
            ), listOf(
                Tank(0, 20),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk12(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_013", "Easy", 6, 6, listOf(
                listOf(
                    0, 1, 1, 0, 0, 1
                ),
                listOf(
                    0, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 0, 1, 0, 0, 1
                ),
                listOf(
                    1, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 0, 1, 0, 1, 0
                ),
                listOf(
                    1, 0, 1, 0, null, 0
                )
            ), listOf(
                Tank(1, 10),
                Tank(0, 25)
            )),
        )
    }

    private fun getChunk13(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_014", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 0, 1, 0, 1
                ),
                listOf(
                    0, 0, 1, 1, 1, null
                ),
                listOf(
                    0, 0, 0, 1, 1, 0
                ),
                listOf(
                    1, 1, 1, 1, 1, 1
                ),
                listOf(
                    0, 0, 1, 1, 1, 0
                ),
                listOf(
                    1, 1, 1, 1, 0, 0
                )
            ), listOf(
                Tank(0, 15),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk14(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_015", "Easy", 6, 6, listOf(
                listOf(
                    0, 1, 0, 0, 1, 0
                ),
                listOf(
                    1, 0, 1, 1, 1, 0
                ),
                listOf(
                    0, 1, 0, 0, 0, 0
                ),
                listOf(
                    0, 1, 0, 1, 1, 1
                ),
                listOf(
                    1, 0, 0, 1, 0, 0
                ),
                listOf(
                    0, 0, null, 1, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk15(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_016", "Easy", 6, 6, listOf(
                listOf(
                    1, 0, 1, 0, 1, 0
                ),
                listOf(
                    0, 0, 0, 1, 1, null
                ),
                listOf(
                    1, 1, 0, 0, 0, 0
                ),
                listOf(
                    0, 0, 1, 0, 1, 1
                ),
                listOf(
                    0, 1, 1, 0, 0, 0
                ),
                listOf(
                    0, 1, 1, 0, 1, 0
                )
            ), listOf(
                Tank(0, 20),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk16(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_017", "Easy", 6, 6, listOf(
                listOf(
                    0, 0, 0, 0, 0, 1
                ),
                listOf(
                    1, 1, 1, 0, 0, 0
                ),
                listOf(
                    1, 1, 1, 1, 1, 0
                ),
                listOf(
                    0, 1, 1, 1, 0, 1
                ),
                listOf(
                    1, 1, 1, 0, 1, 1
                ),
                listOf(
                    null, 0, 1, 0, 0, 1
                )
            ), listOf(
                Tank(0, 15),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk17(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_018", "Easy", 6, 6, listOf(
                listOf(
                    0, 1, 0, 1, 0, 1
                ),
                listOf(
                    1, 0, 0, 1, 0, 1
                ),
                listOf(
                    0, 1, 0, 0, 1, 0
                ),
                listOf(
                    1, 0, 1, 0, 1, 1
                ),
                listOf(
                    1, 1, 0, 0, 0, 0
                ),
                listOf(
                    0, 1, 0, null, 0, 0
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk18(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_019", "Easy", 6, 6, listOf(
                listOf(
                    1, 1, 1, 1, 0, 1
                ),
                listOf(
                    1, 1, 1, 1, 1, 1
                ),
                listOf(
                    1, 0, 0, 1, 1, 1
                ),
                listOf(
                    1, 1, 1, null, 0, 0
                ),
                listOf(
                    1, 1, 0, 1, 1, 1
                ),
                listOf(
                    1, 0, 0, 0, 1, 0
                )
            ), listOf(
                Tank(1, 25),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk19(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_easy_020", "Easy", 6, 6, listOf(
                listOf(
                    1, 0, 0, 1, 0, 0
                ),
                listOf(
                    0, 0, 1, 0, null, 1
                ),
                listOf(
                    0, 1, 1, 0, 1, 0
                ),
                listOf(
                    0, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 1, 1, 1, 0, 1
                ),
                listOf(
                    1, 1, 1, 0, 0, 0
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk20(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_001", "Expert", 15, 25, listOf(
                listOf(
                    2, 3, 1, 1, 0, 3, 3, 1, 3, 2, 1, 0, 2, 2, 3
                ),
                listOf(
                    2, 1, 2, 3, 0, 2, 2, 1, 2, 2, 1, 0, 0, 2, 0
                ),
                listOf(
                    2, 2, 1, 0, 2, 0, 0, 1, 3, 1, 0, 2, 2, 1, 0
                ),
                listOf(
                    1, 3, 1, 1, 2, 1, 3, 1, 1, 3, 2, 3, 0, 2, 0
                ),
                listOf(
                    3, 0, 2, 0, 1, 0, 2, 1, 0, 1, 1, 3, 1, 2, 2
                ),
                listOf(
                    3, 3, 0, 2, 0, 0, 3, 2, 2, 3, 1, 1, 1, 1, 2
                ),
                listOf(
                    1, 2, 0, 1, 2, 0, 0, 0, 0, 1, 0, 0, 3, 3, 3
                ),
                listOf(
                    1, 1, 2, 1, 3, 0, 1, 0, 2, 1, 1, 0, 2, 2, 1
                ),
                listOf(
                    1, 1, 2, 1, 1, 3, 1, 3, 2, 2, 2, 1, 3, 3, 3
                ),
                listOf(
                    3, 0, 0, 2, 2, 1, 1, 0, 0, 1, 3, 2, 1, 3, 3
                ),
                listOf(
                    0, 0, 3, 2, 0, 1, 3, 0, 1, 3, 2, 3, 0, 0, 3
                ),
                listOf(
                    3, 0, 3, 1, 1, 3, 0, 2, 0, 1, 2, 2, 1, 1, 3
                ),
                listOf(
                    1, 1, 1, 1, 2, 3, 1, 1, 1, 3, 2, 2, 0, 2, 3
                ),
                listOf(
                    1, 0, 2, 3, 0, 1, 2, 0, 3, 1, 0, 1, 1, 1, 0
                ),
                listOf(
                    2, 2, 0, 1, 0, 2, 0, 2, 2, 0, 1, 0, 0, 0, 1
                ),
                listOf(
                    1, 0, 0, 2, 1, 3, 2, 2, 1, 0, 2, 2, 0, 3, 0
                ),
                listOf(
                    1, 1, 2, 1, 1, 2, 3, 3, 1, 0, 1, 2, 0, 3, 1
                ),
                listOf(
                    2, 2, 1, 3, 2, 0, 0, 3, 1, 3, 0, 3, 1, 0, 0
                ),
                listOf(
                    2, 1, 3, 1, 1, 0, 3, 0, 3, 2, 0, 1, 0, 0, 3
                ),
                listOf(
                    1, 3, 1, 2, 0, 3, 0, 0, 0, 1, 2, 0, 0, 2, 2
                ),
                listOf(
                    1, 0, 0, 1, 1, 2, 1, 1, 1, 2, 2, 1, 0, 2, 0
                ),
                listOf(
                    0, 2, 3, 1, 2, 3, 1, 1, 0, 0, 2, 3, 0, 0, 0
                ),
                listOf(
                    2, 0, 3, 0, 0, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1
                ),
                listOf(
                    0, 0, 0, 3, 1, 1, 1, 1, 0, 2, 3, 0, 1, 2, 3
                ),
                listOf(
                    3, 1, 3, 1, 2, 2, 3, 2, 3, 0, 2, 1, 2, 0, 0
                )
            ), listOf(
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 25),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 25),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 25),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk21(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_002", "Expert", 15, 25, listOf(
                listOf(
                    2, 2, 1, 2, 0, 0, 2, 2, 0, 0, 2, 3, 3, 0, 1
                ),
                listOf(
                    0, 1, 2, 0, 1, 2, 1, 0, 0, 0, 0, 2, 3, 1, 3
                ),
                listOf(
                    0, 1, 2, 1, 3, 3, 0, 0, 2, 1, 0, 3, 0, 3, 2
                ),
                listOf(
                    2, 0, 1, 1, 1, 0, 3, 2, 3, 3, 0, 3, 1, 1, 3
                ),
                listOf(
                    0, 2, 3, 3, 2, 3, 0, 3, 3, 0, 2, 1, 3, 3, 3
                ),
                listOf(
                    1, 0, 2, 1, 0, 2, 0, 1, 0, 0, 1, 1, 0, 1, 3
                ),
                listOf(
                    2, 2, 3, 3, 1, 3, 3, 2, 0, 3, 2, 2, 0, 0, 3
                ),
                listOf(
                    2, 0, 2, 3, 1, 0, 2, 0, 3, 0, 1, 3, 1, 2, 3
                ),
                listOf(
                    0, 3, 1, 1, 3, 0, 2, 3, 0, 2, 0, 0, 3, 1, 0
                ),
                listOf(
                    0, 0, 1, 0, 2, 2, 1, 2, 0, 1, 2, 2, 2, 0, 0
                ),
                listOf(
                    3, 1, 2, 1, 0, 0, 2, 2, 0, 3, 0, 0, 2, 3, 0
                ),
                listOf(
                    0, 1, 2, 0, 2, 1, 0, 2, 0, 3, 3, 1, 3, 0, 3
                ),
                listOf(
                    1, 0, 2, 2, 1, 2, 0, 0, 3, 0, 2, 1, 2, 0, 1
                ),
                listOf(
                    0, 3, 2, 2, 3, 1, 1, 1, 3, 1, 1, 0, 2, 3, 3
                ),
                listOf(
                    0, 1, 1, 3, 3, 2, 3, 0, 1, 2, 0, 3, 2, 3, 2
                ),
                listOf(
                    0, 3, 1, 1, 1, 3, 1, 1, 0, 0, 1, 3, 0, 2, 0
                ),
                listOf(
                    3, 3, 0, 2, 0, 2, 3, 2, 0, 1, 3, 0, 2, 0, 2
                ),
                listOf(
                    0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 2, 2, 3, 0
                ),
                listOf(
                    0, 0, 0, 0, 2, 0, 3, 1, 3, 3, 2, 3, 0, 0, 1
                ),
                listOf(
                    3, 2, 0, 0, 1, 1, 3, 0, 1, 1, 0, 2, 0, 0, 3
                ),
                listOf(
                    2, 0, 2, 1, 3, 2, 1, 2, 2, 1, 2, 0, 0, 0, 1
                ),
                listOf(
                    0, 3, 2, 1, 3, 2, 2, 2, 2, 2, 1, 1, 2, 2, 0
                ),
                listOf(
                    3, 3, 2, 3, 1, 0, 1, 2, 2, 0, 1, 3, 1, 3, 2
                ),
                listOf(
                    2, 0, 1, 0, 1, 1, 3, 0, 1, 1, 1, 0, 1, 1, 3
                ),
                listOf(
                    1, 2, 0, 3, 1, 2, 3, 2, 0, 2, 0, 3, 3, 1, 1
                )
            ), listOf(
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 25),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk22(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_003", "Expert", 15, 25, listOf(
                listOf(
                    1, 1, 0, 2, 3, 3, 2, 3, 1, 0, 1, 1, 0, 2, 2
                ),
                listOf(
                    2, 1, 2, 3, 2, 2, 1, 0, 2, 0, 3, 0, 0, 0, 3
                ),
                listOf(
                    2, 1, 3, 0, 3, 0, 2, 2, 1, 2, 0, 1, 1, 0, 3
                ),
                listOf(
                    0, 2, 1, 3, 2, 0, 1, 1, 3, 2, 3, 1, 2, 2, 3
                ),
                listOf(
                    2, 0, 2, 2, 0, 2, 2, 0, 3, 2, 3, 2, 2, 1, 1
                ),
                listOf(
                    1, 3, 3, 2, 1, 3, 0, 2, 0, 0, 1, 2, 1, 1, 0
                ),
                listOf(
                    2, 2, 3, 2, 3, 3, 0, 1, 1, 2, 1, 1, 3, 3, 0
                ),
                listOf(
                    2, 0, 1, 1, 1, 2, 3, 0, 0, 2, 2, 3, 0, 0, 3
                ),
                listOf(
                    3, 2, 2, 0, 2, 1, 1, 3, 3, 0, 3, 0, 0, 3, 0
                ),
                listOf(
                    1, 1, 2, 0, 0, 3, 2, 2, 2, 3, 1, 1, 2, 3, 0
                ),
                listOf(
                    1, 2, 2, 3, 1, 3, 3, 1, 3, 3, 0, 0, 0, 3, 3
                ),
                listOf(
                    1, 2, 3, 3, 1, 0, 0, 0, 0, 0, 0, 3, 1, 0, 3
                ),
                listOf(
                    0, 0, 3, 2, 3, 0, 1, 0, 1, 1, 0, 3, 0, 3, 2
                ),
                listOf(
                    3, 3, 1, 2, 0, 1, 1, 1, 3, 1, 0, 2, 3, 3, 3
                ),
                listOf(
                    2, 3, 3, 1, 0, 2, 1, 3, 2, 1, 1, 3, 0, 0, 0
                ),
                listOf(
                    0, 0, 2, 0, 1, 2, 3, 3, 3, 0, 0, 2, 3, 2, 2
                ),
                listOf(
                    0, 1, 1, 1, 0, 2, 3, 2, 0, 2, 2, 0, 2, 0, 0
                ),
                listOf(
                    0, 0, 3, 0, 1, 3, 1, 0, 0, 1, 2, 1, 0, 0, 2
                ),
                listOf(
                    1, 3, 2, 0, 3, 2, 1, 3, 2, 2, 0, 2, 2, 3, 0
                ),
                listOf(
                    1, 3, 0, 2, 0, 2, 2, 0, 3, 2, 0, 0, 3, 0, 0
                ),
                listOf(
                    3, 0, 0, 3, 3, 0, 2, 1, 0, 1, 0, 3, 3, 2, 3
                ),
                listOf(
                    3, 3, 0, 0, 0, 2, 0, 3, 3, 3, 3, 0, 3, 3, 2
                ),
                listOf(
                    2, 1, 1, 2, 3, 0, 3, 0, 2, 0, 2, 3, 0, 0, 0
                ),
                listOf(
                    0, 1, 3, 3, 0, 0, 3, 1, 3, 3, 0, 2, 2, 2, 0
                ),
                listOf(
                    2, 3, 2, 3, 2, 2, 0, 0, 0, 2, 3, 2, 3, 2, 3
                )
            ), listOf(
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 25),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk23(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_004", "Expert", 15, 25, listOf(
                listOf(
                    3, 0, 1, 1, 1, 1, 3, 1, 3, 3, 0, 1, 1, 2, 2
                ),
                listOf(
                    2, 2, 2, 3, 3, 3, 1, 3, 2, 3, 3, 2, 2, 2, 2
                ),
                listOf(
                    1, 1, 3, 1, 3, 2, 0, 1, 1, 2, 1, 0, 1, 2, 3
                ),
                listOf(
                    2, 2, 0, 2, 0, 1, 2, 1, 1, 2, 1, 3, 2, 3, 3
                ),
                listOf(
                    1, 3, 3, 0, 3, 3, 1, 1, 3, 0, 3, 1, 1, 1, 3
                ),
                listOf(
                    1, 3, 3, 2, 3, 0, 1, 0, 1, 2, 2, 3, 2, 0, 3
                ),
                listOf(
                    1, 3, 2, 2, 2, 2, 3, 0, 0, 3, 2, 2, 1, 3, 2
                ),
                listOf(
                    2, 1, 1, 3, 1, 0, 1, 2, 2, 3, 0, 3, 1, 3, 2
                ),
                listOf(
                    3, 1, 0, 2, 2, 1, 3, 3, 2, 2, 2, 3, 2, 3, 0
                ),
                listOf(
                    1, 3, 1, 2, 1, 3, 3, 3, 3, 2, 3, 2, 1, 1, 1
                ),
                listOf(
                    0, 0, 0, 1, 0, 0, 1, 1, 0, 2, 2, 0, 3, 1, 2
                ),
                listOf(
                    3, 2, 2, 2, 1, 2, 1, 3, 2, 0, 0, 0, 3, 2, 2
                ),
                listOf(
                    0, 0, 1, 2, 0, 0, 2, 2, 2, 2, 0, 2, 0, 3, 1
                ),
                listOf(
                    1, 1, 0, 0, 2, 2, 2, 3, 3, 1, 2, 3, 1, 0, 1
                ),
                listOf(
                    1, 2, 1, 1, 1, 2, 3, 1, 1, 2, 3, 3, 3, 3, 1
                ),
                listOf(
                    2, 1, 0, 1, 1, 1, 3, 3, 2, 2, 1, 2, 2, 2, 1
                ),
                listOf(
                    2, 1, 3, 2, 1, 0, 2, 3, 1, 3, 1, 1, 2, 1, 1
                ),
                listOf(
                    2, 1, 3, 2, 2, 2, 3, 1, 1, 0, 2, 3, 3, 1, 2
                ),
                listOf(
                    2, 2, 2, 3, 2, 0, 2, 3, 3, 2, 1, 3, 1, 2, 1
                ),
                listOf(
                    3, 1, 0, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 3
                ),
                listOf(
                    1, 0, 2, 1, 3, 1, 1, 0, 3, 1, 0, 1, 1, 0, 1
                ),
                listOf(
                    3, 3, 0, 0, 0, 2, 1, 0, 2, 3, 1, 1, 3, 0, 1
                ),
                listOf(
                    0, 1, 3, 0, 2, 0, 2, 1, 0, 1, 1, 1, 2, 2, 0
                ),
                listOf(
                    3, 3, 0, 0, 1, 2, 3, 2, 2, 0, 1, 0, 3, 2, 1
                ),
                listOf(
                    3, 2, 1, 2, 1, 0, 2, 3, 3, 1, 3, 1, 2, 2, 1
                )
            ), listOf(
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 15)
            )),
        )
    }

    private fun getChunk24(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_005", "Expert", 15, 25, listOf(
                listOf(
                    1, 0, 0, 2, 1, 1, 2, 0, 2, 2, 1, 2, 1, 0, 0
                ),
                listOf(
                    2, 3, 3, 1, 1, 2, 3, 1, 1, 3, 1, 3, 2, 3, 0
                ),
                listOf(
                    3, 3, 1, 3, 0, 1, 1, 0, 2, 1, 1, 2, 3, 2, 3
                ),
                listOf(
                    0, 3, 2, 0, 1, 0, 1, 2, 1, 0, 1, 1, 3, 1, 0
                ),
                listOf(
                    1, 3, 2, 3, 3, 3, 1, 1, 0, 0, 2, 0, 2, 2, 3
                ),
                listOf(
                    1, 1, 3, 2, 3, 3, 2, 1, 3, 1, 0, 1, 0, 3, 3
                ),
                listOf(
                    1, 2, 0, 0, 1, 2, 2, 1, 0, 0, 1, 1, 3, 3, 1
                ),
                listOf(
                    1, 0, 1, 1, 2, 3, 1, 0, 0, 3, 2, 2, 1, 1, 0
                ),
                listOf(
                    3, 0, 1, 0, 0, 1, 0, 3, 3, 2, 2, 1, 1, 3, 2
                ),
                listOf(
                    2, 1, 3, 1, 2, 2, 3, 3, 0, 2, 1, 1, 2, 3, 2
                ),
                listOf(
                    2, 1, 2, 3, 0, 0, 1, 2, 2, 0, 3, 1, 1, 0, 3
                ),
                listOf(
                    2, 0, 1, 0, 0, 3, 1, 3, 1, 0, 1, 1, 1, 2, 2
                ),
                listOf(
                    1, 3, 3, 0, 2, 1, 2, 1, 1, 1, 2, 3, 2, 2, 2
                ),
                listOf(
                    1, 1, 3, 2, 3, 2, 2, 2, 1, 2, 1, 1, 2, 0, 0
                ),
                listOf(
                    0, 0, 3, 2, 0, 3, 2, 1, 2, 3, 1, 1, 1, 1, 1
                ),
                listOf(
                    1, 3, 3, 3, 1, 1, 1, 2, 1, 3, 3, 3, 2, 0, 2
                ),
                listOf(
                    0, 0, 2, 2, 0, 1, 2, 3, 2, 2, 2, 0, 0, 1, 2
                ),
                listOf(
                    2, 3, 2, 0, 2, 0, 1, 2, 0, 3, 1, 2, 2, 0, 2
                ),
                listOf(
                    1, 2, 3, 1, 3, 3, 0, 1, 2, 3, 3, 1, 2, 1, 2
                ),
                listOf(
                    1, 0, 3, 2, 2, 2, 0, 1, 1, 3, 2, 1, 1, 2, 3
                ),
                listOf(
                    1, 3, 3, 2, 3, 2, 0, 3, 2, 2, 0, 2, 3, 1, 2
                ),
                listOf(
                    0, 3, 1, 3, 3, 3, 2, 3, 1, 3, 2, 1, 0, 0, 0
                ),
                listOf(
                    1, 1, 0, 0, 1, 1, 3, 0, 3, 2, 1, 1, 2, 0, 2
                ),
                listOf(
                    2, 1, 1, 3, 3, 3, 3, 1, 1, 3, 1, 0, 2, 2, 2
                ),
                listOf(
                    3, 3, 1, 0, 3, 2, 1, 2, 3, 3, 1, 3, 1, 1, 2
                )
            ), listOf(
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk25(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_006", "Expert", 15, 25, listOf(
                listOf(
                    1, 1, 2, 0, 1, 3, 0, 2, 1, 1, 2, 0, 3, 0, 3
                ),
                listOf(
                    2, 0, 1, 0, 0, 0, 0, 3, 3, 3, 0, 0, 2, 3, 1
                ),
                listOf(
                    3, 3, 2, 0, 0, 0, 2, 0, 1, 2, 3, 3, 3, 0, 3
                ),
                listOf(
                    1, 3, 3, 1, 0, 2, 3, 3, 0, 1, 2, 0, 2, 3, 2
                ),
                listOf(
                    3, 3, 3, 1, 1, 3, 0, 3, 0, 0, 1, 0, 1, 2, 0
                ),
                listOf(
                    1, 3, 1, 1, 3, 1, 0, 1, 0, 0, 1, 3, 0, 1, 1
                ),
                listOf(
                    0, 1, 0, 1, 0, 2, 3, 0, 0, 1, 3, 0, 1, 0, 0
                ),
                listOf(
                    0, 1, 0, 1, 2, 0, 2, 1, 1, 1, 3, 0, 0, 3, 2
                ),
                listOf(
                    2, 0, 3, 1, 1, 3, 2, 3, 1, 2, 3, 3, 3, 1, 3
                ),
                listOf(
                    1, 1, 2, 1, 2, 2, 3, 0, 3, 1, 0, 1, 3, 0, 2
                ),
                listOf(
                    3, 3, 0, 0, 3, 1, 3, 0, 3, 3, 1, 1, 1, 0, 1
                ),
                listOf(
                    2, 1, 1, 3, 0, 0, 0, 0, 3, 0, 1, 2, 3, 0, 3
                ),
                listOf(
                    3, 0, 2, 3, 0, 0, 0, 3, 0, 2, 0, 2, 1, 1, 3
                ),
                listOf(
                    2, 0, 3, 3, 2, 3, 2, 0, 3, 2, 2, 3, 2, 1, 0
                ),
                listOf(
                    3, 3, 0, 0, 3, 3, 2, 3, 3, 0, 1, 1, 3, 2, 1
                ),
                listOf(
                    3, 0, 2, 1, 0, 3, 0, 1, 2, 2, 2, 0, 0, 2, 0
                ),
                listOf(
                    0, 3, 3, 2, 1, 0, 0, 3, 1, 0, 3, 0, 1, 1, 1
                ),
                listOf(
                    0, 0, 0, 3, 1, 1, 0, 2, 1, 3, 3, 0, 1, 3, 3
                ),
                listOf(
                    1, 0, 1, 0, 1, 1, 3, 1, 3, 2, 1, 3, 3, 0, 0
                ),
                listOf(
                    0, 1, 0, 0, 3, 2, 2, 3, 2, 0, 0, 0, 2, 3, 2
                ),
                listOf(
                    2, 2, 3, 1, 1, 1, 0, 1, 3, 1, 2, 1, 0, 3, 0
                ),
                listOf(
                    0, 0, 3, 2, 0, 0, 3, 0, 3, 2, 2, 3, 0, 1, 2
                ),
                listOf(
                    1, 0, 1, 3, 1, 0, 0, 0, 0, 1, 3, 3, 0, 3, 3
                ),
                listOf(
                    2, 2, 3, 0, 0, 1, 2, 2, 3, 3, 3, 1, 3, 1, 3
                ),
                listOf(
                    3, 3, 0, 1, 1, 0, 0, 3, 1, 0, 2, 2, 2, 0, 2
                )
            ), listOf(
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 25),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk26(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_007", "Expert", 15, 25, listOf(
                listOf(
                    3, 0, 0, 3, 0, 2, 1, 0, 1, 2, 3, 1, 1, 2, 2
                ),
                listOf(
                    1, 1, 1, 0, 3, 3, 1, 1, 0, 1, 3, 2, 0, 0, 1
                ),
                listOf(
                    2, 1, 2, 1, 2, 0, 2, 3, 0, 2, 0, 0, 2, 2, 1
                ),
                listOf(
                    0, 2, 2, 1, 1, 2, 2, 3, 0, 3, 3, 1, 0, 1, 0
                ),
                listOf(
                    1, 1, 2, 3, 3, 3, 2, 2, 0, 3, 3, 0, 3, 3, 3
                ),
                listOf(
                    3, 2, 1, 0, 1, 3, 3, 3, 2, 3, 1, 0, 2, 1, 1
                ),
                listOf(
                    2, 0, 3, 0, 3, 1, 2, 0, 0, 3, 3, 3, 1, 1, 1
                ),
                listOf(
                    0, 3, 3, 2, 0, 3, 0, 1, 2, 1, 0, 2, 3, 1, 2
                ),
                listOf(
                    2, 1, 2, 3, 2, 3, 3, 1, 2, 1, 3, 1, 1, 3, 3
                ),
                listOf(
                    0, 3, 1, 2, 3, 3, 1, 3, 3, 2, 2, 3, 0, 1, 0
                ),
                listOf(
                    2, 2, 3, 3, 2, 1, 0, 3, 3, 2, 3, 1, 3, 3, 0
                ),
                listOf(
                    2, 3, 3, 2, 1, 3, 2, 3, 0, 0, 3, 0, 0, 3, 2
                ),
                listOf(
                    1, 3, 2, 0, 1, 0, 2, 3, 0, 2, 0, 3, 1, 0, 1
                ),
                listOf(
                    0, 2, 3, 0, 2, 2, 1, 1, 3, 1, 1, 0, 1, 3, 0
                ),
                listOf(
                    1, 0, 0, 3, 1, 0, 2, 2, 2, 3, 1, 0, 2, 3, 2
                ),
                listOf(
                    0, 0, 3, 1, 0, 3, 3, 1, 1, 2, 2, 3, 3, 2, 0
                ),
                listOf(
                    2, 2, 1, 2, 0, 0, 2, 1, 0, 1, 0, 3, 0, 1, 1
                ),
                listOf(
                    1, 3, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0, 2, 3, 1
                ),
                listOf(
                    2, 1, 2, 1, 3, 1, 1, 0, 2, 3, 0, 0, 2, 2, 0
                ),
                listOf(
                    0, 1, 3, 0, 2, 1, 3, 0, 0, 2, 3, 2, 3, 1, 2
                ),
                listOf(
                    0, 2, 1, 3, 3, 1, 0, 3, 2, 2, 3, 3, 1, 3, 2
                ),
                listOf(
                    1, 3, 2, 0, 2, 0, 0, 3, 3, 3, 1, 3, 2, 3, 2
                ),
                listOf(
                    2, 3, 3, 2, 3, 3, 0, 3, 1, 3, 1, 1, 0, 1, 2
                ),
                listOf(
                    3, 0, 3, 3, 3, 3, 3, 3, 1, 2, 2, 3, 1, 2, 1
                ),
                listOf(
                    1, 3, 3, 2, 0, 2, 1, 0, 0, 1, 2, 3, 0, 2, 3
                )
            ), listOf(
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 25),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk27(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_008", "Expert", 15, 25, listOf(
                listOf(
                    3, 0, 1, 1, 1, 3, 3, 0, 1, 0, 2, 0, 1, 3, 1
                ),
                listOf(
                    3, 2, 1, 0, 3, 1, 2, 0, 0, 1, 2, 1, 2, 0, 1
                ),
                listOf(
                    0, 1, 3, 2, 2, 1, 2, 0, 3, 0, 2, 3, 1, 1, 1
                ),
                listOf(
                    3, 1, 2, 3, 1, 2, 2, 1, 3, 2, 2, 3, 3, 3, 3
                ),
                listOf(
                    3, 3, 1, 2, 0, 0, 1, 1, 2, 1, 1, 0, 3, 0, 2
                ),
                listOf(
                    0, 1, 1, 2, 3, 1, 3, 0, 1, 2, 3, 1, 3, 2, 3
                ),
                listOf(
                    0, 0, 3, 2, 2, 2, 0, 1, 2, 3, 3, 0, 1, 2, 3
                ),
                listOf(
                    3, 0, 2, 2, 2, 0, 2, 3, 1, 0, 2, 0, 3, 3, 3
                ),
                listOf(
                    2, 0, 1, 1, 1, 3, 3, 3, 0, 1, 3, 0, 2, 0, 3
                ),
                listOf(
                    0, 0, 1, 3, 2, 2, 2, 1, 0, 3, 0, 0, 2, 2, 3
                ),
                listOf(
                    1, 0, 0, 1, 0, 2, 0, 0, 3, 1, 1, 1, 2, 2, 0
                ),
                listOf(
                    3, 3, 1, 1, 3, 3, 3, 1, 0, 1, 3, 2, 2, 1, 2
                ),
                listOf(
                    0, 1, 1, 0, 1, 1, 0, 3, 3, 0, 0, 0, 1, 3, 2
                ),
                listOf(
                    3, 0, 0, 2, 1, 3, 0, 1, 1, 1, 2, 1, 1, 2, 0
                ),
                listOf(
                    0, 0, 1, 2, 1, 2, 0, 0, 1, 3, 0, 2, 2, 2, 1
                ),
                listOf(
                    3, 1, 2, 3, 0, 1, 3, 0, 3, 3, 3, 0, 2, 3, 2
                ),
                listOf(
                    2, 3, 3, 2, 2, 0, 2, 3, 2, 1, 2, 2, 1, 2, 0
                ),
                listOf(
                    0, 3, 2, 2, 3, 2, 1, 2, 2, 0, 1, 0, 3, 1, 0
                ),
                listOf(
                    0, 3, 0, 1, 2, 1, 2, 0, 1, 3, 1, 1, 3, 0, 0
                ),
                listOf(
                    3, 1, 2, 0, 3, 3, 3, 3, 1, 0, 3, 3, 1, 2, 0
                ),
                listOf(
                    0, 3, 3, 3, 3, 2, 2, 3, 1, 1, 2, 1, 3, 0, 1
                ),
                listOf(
                    1, 3, 2, 2, 2, 2, 1, 1, 2, 3, 3, 1, 2, 1, 1
                ),
                listOf(
                    0, 1, 1, 3, 0, 2, 2, 1, 3, 3, 3, 2, 2, 2, 1
                ),
                listOf(
                    0, 3, 0, 3, 1, 3, 0, 3, 3, 3, 0, 2, 0, 1, 1
                ),
                listOf(
                    1, 1, 2, 0, 3, 1, 0, 1, 3, 2, 3, 1, 2, 3, 0
                )
            ), listOf(
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 25),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 25),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 25),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk28(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_009", "Expert", 15, 25, listOf(
                listOf(
                    2, 0, 1, 1, 1, 3, 3, 2, 3, 0, 2, 0, 0, 0, 0
                ),
                listOf(
                    3, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 3, 1, 0, 2
                ),
                listOf(
                    3, 1, 1, 3, 0, 0, 2, 1, 1, 2, 3, 1, 1, 3, 3
                ),
                listOf(
                    2, 1, 2, 0, 3, 1, 1, 0, 2, 1, 1, 1, 3, 0, 1
                ),
                listOf(
                    3, 1, 1, 0, 2, 3, 3, 3, 2, 1, 0, 1, 2, 2, 1
                ),
                listOf(
                    2, 3, 1, 3, 3, 2, 2, 3, 0, 2, 2, 1, 2, 1, 3
                ),
                listOf(
                    3, 2, 3, 3, 2, 1, 2, 1, 3, 2, 0, 3, 2, 0, 3
                ),
                listOf(
                    0, 3, 1, 0, 0, 1, 2, 1, 1, 3, 1, 1, 3, 2, 2
                ),
                listOf(
                    0, 1, 2, 2, 3, 0, 0, 2, 0, 2, 2, 2, 1, 3, 3
                ),
                listOf(
                    2, 3, 0, 2, 3, 3, 0, 0, 3, 1, 2, 3, 1, 0, 1
                ),
                listOf(
                    3, 1, 1, 1, 1, 2, 3, 2, 2, 3, 1, 1, 3, 1, 1
                ),
                listOf(
                    1, 1, 0, 0, 1, 2, 2, 1, 2, 3, 3, 3, 1, 2, 3
                ),
                listOf(
                    0, 0, 0, 3, 2, 1, 1, 2, 0, 2, 2, 0, 1, 2, 0
                ),
                listOf(
                    2, 1, 0, 3, 3, 3, 3, 0, 0, 1, 2, 0, 2, 2, 2
                ),
                listOf(
                    1, 1, 0, 1, 1, 2, 1, 0, 0, 2, 2, 0, 3, 3, 2
                ),
                listOf(
                    0, 3, 3, 1, 1, 3, 2, 0, 3, 1, 0, 0, 0, 0, 1
                ),
                listOf(
                    1, 2, 2, 3, 1, 1, 2, 2, 1, 1, 2, 1, 1, 0, 1
                ),
                listOf(
                    2, 3, 0, 1, 0, 3, 1, 3, 1, 0, 1, 3, 0, 0, 0
                ),
                listOf(
                    2, 3, 1, 3, 3, 1, 2, 0, 2, 2, 0, 3, 0, 2, 0
                ),
                listOf(
                    3, 2, 2, 2, 1, 3, 0, 0, 1, 1, 1, 0, 3, 3, 1
                ),
                listOf(
                    1, 1, 1, 2, 3, 1, 3, 3, 0, 3, 2, 0, 1, 0, 3
                ),
                listOf(
                    1, 2, 1, 2, 3, 3, 0, 2, 3, 2, 0, 2, 2, 3, 2
                ),
                listOf(
                    1, 3, 1, 3, 3, 0, 3, 1, 2, 0, 0, 2, 3, 2, 0
                ),
                listOf(
                    3, 3, 2, 0, 2, 0, 0, 3, 0, 0, 2, 0, 1, 0, 0
                ),
                listOf(
                    2, 1, 3, 1, 2, 3, 0, 0, 0, 1, 0, 2, 2, 3, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(1, 25),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 25),
                Tank(1, 15),
                Tank(2, 25),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk29(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_010", "Expert", 15, 25, listOf(
                listOf(
                    0, 0, 1, 1, 2, 1, 2, 1, 2, 2, 0, 3, 2, 1, 2
                ),
                listOf(
                    0, 0, 0, 0, 2, 1, 3, 1, 3, 2, 3, 2, 3, 1, 0
                ),
                listOf(
                    1, 3, 2, 0, 2, 0, 0, 2, 2, 1, 0, 0, 3, 0, 0
                ),
                listOf(
                    0, 2, 0, 3, 0, 3, 2, 0, 1, 0, 0, 1, 2, 0, 1
                ),
                listOf(
                    0, 1, 2, 2, 1, 0, 2, 0, 3, 0, 2, 1, 0, 1, 2
                ),
                listOf(
                    3, 1, 0, 3, 3, 0, 2, 0, 2, 1, 3, 3, 2, 3, 1
                ),
                listOf(
                    1, 2, 0, 1, 1, 2, 2, 0, 1, 0, 2, 2, 0, 3, 3
                ),
                listOf(
                    2, 3, 0, 3, 0, 3, 2, 3, 0, 0, 1, 3, 3, 1, 0
                ),
                listOf(
                    2, 3, 2, 3, 0, 3, 1, 2, 1, 3, 3, 0, 2, 1, 1
                ),
                listOf(
                    2, 1, 0, 1, 0, 0, 0, 0, 2, 0, 1, 3, 0, 3, 1
                ),
                listOf(
                    0, 0, 0, 1, 3, 0, 2, 2, 2, 1, 0, 0, 0, 1, 2
                ),
                listOf(
                    3, 3, 3, 2, 0, 0, 2, 0, 0, 3, 1, 0, 2, 0, 2
                ),
                listOf(
                    2, 2, 2, 1, 0, 2, 3, 1, 2, 0, 2, 2, 3, 1, 1
                ),
                listOf(
                    3, 1, 2, 2, 2, 2, 0, 3, 2, 1, 3, 0, 3, 1, 1
                ),
                listOf(
                    0, 2, 0, 1, 3, 1, 0, 0, 0, 2, 3, 1, 0, 0, 0
                ),
                listOf(
                    3, 1, 1, 2, 1, 0, 2, 1, 0, 0, 0, 2, 1, 2, 2
                ),
                listOf(
                    1, 1, 0, 1, 1, 2, 3, 2, 0, 2, 0, 1, 0, 0, 1
                ),
                listOf(
                    1, 0, 3, 2, 0, 1, 0, 2, 3, 1, 0, 1, 3, 3, 2
                ),
                listOf(
                    2, 0, 0, 3, 0, 3, 2, 2, 0, 0, 2, 1, 0, 1, 2
                ),
                listOf(
                    0, 2, 2, 0, 3, 3, 2, 3, 1, 0, 0, 1, 3, 1, 0
                ),
                listOf(
                    2, 2, 0, 2, 1, 3, 2, 0, 1, 1, 3, 2, 0, 1, 1
                ),
                listOf(
                    1, 0, 3, 3, 3, 0, 3, 1, 2, 0, 3, 2, 2, 2, 1
                ),
                listOf(
                    2, 1, 3, 0, 0, 2, 0, 1, 3, 0, 0, 0, 3, 3, 1
                ),
                listOf(
                    0, 2, 3, 1, 3, 0, 3, 2, 1, 0, 3, 0, 2, 0, 1
                ),
                listOf(
                    0, 2, 1, 1, 1, 1, 3, 1, 2, 2, 3, 3, 3, 1, 1
                )
            ), listOf(
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 25),
                Tank(1, 25)
            )),
        )
    }

    private fun getChunk30(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_011", "Expert", 15, 25, listOf(
                listOf(
                    1, 2, 2, 3, 2, 3, 2, 0, 2, 2, 3, 2, 3, 1, 1
                ),
                listOf(
                    3, 1, 3, 3, 3, 3, 1, 2, 3, 3, 1, 3, 3, 1, 3
                ),
                listOf(
                    1, 2, 1, 1, 2, 2, 3, 1, 3, 2, 0, 2, 1, 0, 0
                ),
                listOf(
                    1, 2, 1, 2, 3, 2, 0, 2, 2, 2, 2, 1, 0, 1, 2
                ),
                listOf(
                    0, 1, 3, 0, 0, 0, 3, 3, 3, 3, 0, 0, 2, 2, 3
                ),
                listOf(
                    1, 2, 3, 2, 3, 3, 2, 2, 3, 0, 3, 1, 1, 2, 1
                ),
                listOf(
                    2, 0, 2, 3, 0, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2
                ),
                listOf(
                    1, 3, 1, 3, 3, 0, 1, 0, 2, 0, 2, 2, 2, 2, 3
                ),
                listOf(
                    3, 2, 1, 0, 1, 1, 3, 2, 1, 0, 2, 0, 1, 2, 2
                ),
                listOf(
                    2, 1, 1, 1, 1, 3, 2, 1, 0, 2, 1, 2, 2, 3, 2
                ),
                listOf(
                    1, 3, 2, 3, 0, 2, 1, 3, 2, 1, 0, 1, 3, 1, 3
                ),
                listOf(
                    3, 0, 1, 3, 1, 0, 3, 0, 0, 3, 2, 2, 0, 0, 0
                ),
                listOf(
                    3, 3, 2, 1, 2, 2, 1, 3, 2, 0, 1, 1, 1, 3, 2
                ),
                listOf(
                    1, 3, 1, 3, 2, 1, 3, 3, 1, 0, 1, 0, 0, 3, 1
                ),
                listOf(
                    2, 2, 0, 0, 0, 3, 1, 1, 0, 2, 1, 1, 2, 3, 2
                ),
                listOf(
                    1, 3, 3, 1, 2, 1, 2, 3, 3, 2, 2, 2, 2, 1, 3
                ),
                listOf(
                    0, 3, 3, 3, 0, 1, 1, 1, 3, 0, 1, 1, 3, 3, 2
                ),
                listOf(
                    2, 0, 3, 3, 2, 3, 1, 3, 0, 2, 0, 3, 0, 3, 3
                ),
                listOf(
                    3, 2, 1, 1, 0, 2, 2, 2, 2, 3, 2, 0, 2, 1, 0
                ),
                listOf(
                    3, 3, 1, 1, 1, 3, 3, 3, 2, 0, 1, 0, 3, 1, 3
                ),
                listOf(
                    2, 3, 1, 2, 0, 3, 0, 1, 2, 0, 0, 2, 1, 0, 3
                ),
                listOf(
                    3, 0, 3, 3, 3, 3, 0, 0, 2, 0, 1, 2, 1, 3, 3
                ),
                listOf(
                    3, 0, 3, 1, 3, 2, 3, 2, 0, 3, 3, 2, 2, 3, 0
                ),
                listOf(
                    0, 0, 3, 3, 0, 3, 1, 3, 0, 0, 0, 3, 3, 2, 1
                ),
                listOf(
                    3, 1, 3, 3, 2, 3, 1, 1, 2, 0, 3, 2, 3, 3, 3
                )
            ), listOf(
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk31(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_012", "Expert", 15, 25, listOf(
                listOf(
                    3, 3, 1, 0, 0, 0, 3, 3, 2, 0, 3, 2, 2, 0, 0
                ),
                listOf(
                    3, 0, 0, 2, 1, 3, 0, 0, 0, 1, 3, 1, 2, 2, 2
                ),
                listOf(
                    2, 2, 3, 2, 2, 1, 2, 2, 3, 1, 1, 3, 1, 3, 1
                ),
                listOf(
                    1, 0, 1, 2, 0, 3, 1, 3, 3, 1, 3, 0, 0, 2, 3
                ),
                listOf(
                    0, 0, 3, 2, 3, 2, 1, 0, 1, 0, 2, 3, 0, 0, 2
                ),
                listOf(
                    3, 2, 3, 3, 1, 0, 1, 3, 1, 1, 3, 2, 2, 0, 2
                ),
                listOf(
                    2, 1, 0, 1, 2, 0, 2, 0, 1, 1, 0, 2, 1, 2, 1
                ),
                listOf(
                    1, 3, 0, 0, 0, 1, 1, 2, 0, 2, 0, 3, 2, 0, 3
                ),
                listOf(
                    0, 1, 1, 0, 0, 3, 0, 3, 1, 2, 1, 0, 2, 0, 1
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 0, 0, 1, 3, 2, 3, 2, 0, 3
                ),
                listOf(
                    1, 2, 1, 2, 3, 3, 1, 2, 3, 1, 2, 1, 2, 2, 3
                ),
                listOf(
                    1, 3, 3, 2, 3, 0, 0, 1, 1, 0, 2, 3, 1, 3, 3
                ),
                listOf(
                    2, 3, 0, 1, 1, 3, 2, 3, 3, 0, 0, 1, 3, 1, 0
                ),
                listOf(
                    0, 2, 1, 2, 1, 0, 1, 0, 2, 3, 3, 0, 0, 1, 0
                ),
                listOf(
                    0, 0, 3, 3, 0, 2, 3, 0, 1, 3, 2, 1, 0, 1, 0
                ),
                listOf(
                    3, 1, 3, 0, 3, 1, 2, 0, 1, 0, 0, 3, 0, 1, 3
                ),
                listOf(
                    3, 1, 1, 1, 1, 1, 3, 1, 1, 3, 3, 3, 1, 0, 1
                ),
                listOf(
                    1, 0, 1, 1, 1, 1, 1, 0, 3, 2, 3, 0, 0, 3, 0
                ),
                listOf(
                    2, 1, 2, 1, 0, 2, 2, 1, 3, 3, 1, 2, 2, 1, 2
                ),
                listOf(
                    0, 3, 0, 0, 3, 0, 3, 0, 2, 2, 3, 0, 3, 0, 0
                ),
                listOf(
                    0, 0, 1, 2, 1, 2, 2, 3, 0, 2, 3, 2, 1, 2, 2
                ),
                listOf(
                    0, 2, 3, 0, 2, 1, 3, 0, 1, 0, 3, 0, 0, 3, 0
                ),
                listOf(
                    1, 1, 1, 2, 2, 2, 0, 0, 0, 2, 3, 1, 0, 1, 0
                ),
                listOf(
                    0, 0, 3, 3, 3, 0, 3, 0, 3, 1, 2, 3, 2, 3, 1
                ),
                listOf(
                    3, 3, 0, 0, 2, 3, 2, 3, 0, 1, 3, 2, 3, 1, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 25),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 25),
                Tank(3, 10),
                Tank(2, 25),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk32(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_013", "Expert", 15, 25, listOf(
                listOf(
                    0, 2, 0, 2, 2, 1, 0, 1, 3, 2, 2, 0, 3, 0, 0
                ),
                listOf(
                    2, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 3, 0, 3, 0
                ),
                listOf(
                    2, 0, 0, 3, 1, 1, 0, 0, 3, 1, 0, 3, 2, 1, 2
                ),
                listOf(
                    3, 3, 3, 3, 0, 1, 2, 3, 1, 3, 1, 3, 3, 0, 1
                ),
                listOf(
                    0, 2, 1, 3, 0, 2, 0, 3, 1, 1, 2, 2, 3, 2, 2
                ),
                listOf(
                    0, 0, 3, 2, 1, 2, 0, 1, 3, 0, 0, 0, 0, 2, 2
                ),
                listOf(
                    0, 1, 1, 3, 1, 3, 2, 2, 3, 3, 0, 3, 2, 1, 0
                ),
                listOf(
                    1, 2, 0, 0, 3, 2, 2, 2, 1, 3, 3, 1, 3, 2, 0
                ),
                listOf(
                    3, 0, 3, 2, 2, 1, 1, 2, 0, 0, 1, 3, 1, 3, 2
                ),
                listOf(
                    3, 2, 1, 1, 3, 1, 1, 0, 2, 3, 2, 2, 3, 1, 1
                ),
                listOf(
                    3, 3, 0, 3, 3, 0, 3, 3, 3, 3, 2, 3, 2, 1, 0
                ),
                listOf(
                    2, 0, 0, 1, 0, 2, 1, 0, 3, 1, 2, 2, 1, 1, 2
                ),
                listOf(
                    1, 0, 0, 1, 2, 2, 3, 1, 2, 2, 0, 0, 2, 0, 0
                ),
                listOf(
                    1, 2, 0, 2, 0, 1, 0, 1, 0, 3, 3, 1, 1, 1, 2
                ),
                listOf(
                    2, 0, 2, 2, 0, 2, 1, 2, 1, 1, 3, 3, 1, 0, 3
                ),
                listOf(
                    3, 3, 1, 3, 2, 1, 3, 1, 0, 2, 3, 2, 0, 1, 2
                ),
                listOf(
                    3, 0, 1, 3, 0, 3, 0, 1, 0, 1, 3, 3, 2, 1, 1
                ),
                listOf(
                    3, 1, 2, 1, 0, 0, 2, 0, 2, 0, 1, 1, 2, 0, 1
                ),
                listOf(
                    0, 2, 0, 2, 0, 1, 3, 0, 0, 2, 1, 3, 3, 0, 2
                ),
                listOf(
                    1, 2, 2, 0, 3, 2, 1, 0, 0, 0, 1, 3, 2, 0, 3
                ),
                listOf(
                    2, 1, 0, 2, 3, 3, 1, 0, 2, 3, 2, 2, 1, 3, 2
                ),
                listOf(
                    0, 1, 0, 3, 1, 3, 0, 0, 0, 2, 2, 0, 3, 1, 2
                ),
                listOf(
                    1, 2, 1, 2, 0, 3, 3, 3, 2, 0, 3, 0, 3, 2, 0
                ),
                listOf(
                    2, 0, 2, 3, 2, 0, 0, 1, 2, 2, 1, 2, 0, 1, 2
                ),
                listOf(
                    3, 1, 3, 3, 0, 3, 1, 3, 1, 2, 0, 0, 0, 1, 3
                )
            ), listOf(
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 25),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 25),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk33(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_014", "Expert", 15, 25, listOf(
                listOf(
                    0, 3, 0, 1, 2, 1, 2, 2, 0, 3, 2, 1, 3, 1, 3
                ),
                listOf(
                    3, 3, 3, 3, 2, 3, 3, 2, 2, 3, 1, 0, 1, 3, 0
                ),
                listOf(
                    2, 3, 3, 0, 2, 2, 3, 1, 2, 2, 2, 2, 1, 3, 3
                ),
                listOf(
                    2, 3, 2, 0, 3, 0, 0, 2, 3, 3, 0, 2, 3, 3, 3
                ),
                listOf(
                    2, 0, 2, 3, 3, 0, 2, 1, 3, 2, 1, 2, 3, 0, 1
                ),
                listOf(
                    2, 2, 1, 3, 3, 1, 1, 3, 0, 1, 1, 0, 1, 1, 0
                ),
                listOf(
                    2, 2, 0, 2, 1, 3, 1, 3, 0, 0, 3, 3, 1, 0, 1
                ),
                listOf(
                    0, 2, 0, 1, 3, 0, 3, 3, 3, 0, 1, 1, 1, 2, 2
                ),
                listOf(
                    0, 2, 3, 3, 0, 2, 0, 1, 2, 2, 3, 0, 3, 3, 0
                ),
                listOf(
                    2, 1, 1, 0, 3, 1, 1, 3, 3, 0, 2, 1, 2, 0, 1
                ),
                listOf(
                    2, 0, 0, 3, 2, 2, 2, 1, 1, 0, 3, 1, 2, 3, 3
                ),
                listOf(
                    3, 0, 2, 1, 3, 2, 0, 1, 3, 3, 2, 3, 2, 3, 3
                ),
                listOf(
                    2, 3, 3, 3, 2, 3, 1, 3, 2, 1, 3, 2, 2, 3, 3
                ),
                listOf(
                    0, 2, 0, 1, 3, 2, 3, 1, 1, 2, 1, 1, 0, 3, 3
                ),
                listOf(
                    0, 2, 1, 0, 1, 2, 3, 3, 2, 1, 3, 2, 0, 0, 1
                ),
                listOf(
                    0, 2, 2, 0, 2, 2, 0, 0, 2, 3, 1, 1, 1, 1, 2
                ),
                listOf(
                    0, 2, 1, 1, 3, 0, 2, 0, 3, 3, 0, 0, 2, 1, 3
                ),
                listOf(
                    2, 2, 3, 1, 0, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2
                ),
                listOf(
                    0, 3, 2, 3, 2, 0, 1, 2, 3, 2, 2, 3, 3, 3, 3
                ),
                listOf(
                    2, 3, 3, 3, 3, 2, 3, 1, 2, 2, 1, 3, 3, 0, 1
                ),
                listOf(
                    3, 3, 1, 0, 3, 1, 1, 2, 3, 3, 1, 3, 2, 1, 0
                ),
                listOf(
                    2, 2, 2, 2, 0, 0, 0, 2, 2, 1, 3, 2, 3, 0, 1
                ),
                listOf(
                    0, 2, 1, 2, 3, 1, 1, 2, 1, 1, 3, 3, 3, 0, 2
                ),
                listOf(
                    3, 3, 1, 1, 0, 1, 2, 3, 0, 0, 3, 0, 2, 3, 2
                ),
                listOf(
                    1, 3, 3, 2, 2, 0, 2, 3, 2, 3, 1, 3, 1, 0, 1
                )
            ), listOf(
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 25),
                Tank(1, 25),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 25),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk34(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_015", "Expert", 15, 25, listOf(
                listOf(
                    0, 2, 3, 2, 3, 2, 0, 3, 0, 1, 3, 0, 2, 3, 2
                ),
                listOf(
                    0, 0, 0, 0, 0, 1, 3, 2, 0, 3, 2, 2, 0, 0, 1
                ),
                listOf(
                    3, 0, 0, 0, 1, 1, 2, 3, 0, 2, 2, 3, 3, 2, 3
                ),
                listOf(
                    1, 0, 0, 3, 1, 2, 2, 2, 0, 2, 0, 2, 3, 3, 1
                ),
                listOf(
                    3, 3, 1, 3, 3, 2, 2, 3, 3, 3, 2, 2, 2, 3, 0
                ),
                listOf(
                    2, 0, 2, 2, 0, 0, 0, 0, 1, 3, 3, 1, 2, 3, 2
                ),
                listOf(
                    0, 3, 1, 0, 1, 0, 0, 0, 2, 3, 3, 2, 3, 3, 2
                ),
                listOf(
                    2, 2, 0, 0, 0, 3, 3, 3, 2, 1, 0, 2, 0, 3, 1
                ),
                listOf(
                    0, 1, 1, 0, 2, 3, 0, 0, 0, 1, 2, 1, 1, 1, 1
                ),
                listOf(
                    0, 0, 0, 0, 1, 0, 1, 0, 2, 0, 0, 3, 3, 2, 0
                ),
                listOf(
                    3, 0, 3, 1, 0, 2, 3, 3, 1, 2, 0, 1, 2, 2, 0
                ),
                listOf(
                    0, 3, 2, 1, 1, 3, 0, 3, 3, 0, 0, 3, 2, 2, 3
                ),
                listOf(
                    0, 2, 0, 1, 1, 1, 1, 1, 2, 2, 1, 0, 2, 2, 2
                ),
                listOf(
                    3, 0, 0, 0, 2, 3, 0, 1, 3, 3, 0, 3, 0, 0, 0
                ),
                listOf(
                    2, 1, 2, 2, 0, 1, 3, 0, 3, 0, 1, 1, 1, 1, 2
                ),
                listOf(
                    2, 3, 2, 1, 0, 0, 1, 1, 1, 1, 0, 2, 1, 0, 2
                ),
                listOf(
                    0, 3, 0, 2, 2, 2, 3, 1, 0, 2, 2, 3, 0, 2, 0
                ),
                listOf(
                    3, 3, 3, 3, 3, 3, 0, 0, 0, 2, 0, 0, 3, 0, 3
                ),
                listOf(
                    2, 3, 0, 3, 1, 0, 3, 0, 2, 3, 0, 3, 0, 2, 2
                ),
                listOf(
                    0, 0, 3, 2, 0, 3, 3, 0, 2, 0, 2, 0, 0, 2, 1
                ),
                listOf(
                    1, 0, 0, 1, 2, 0, 3, 2, 0, 2, 1, 2, 2, 1, 0
                ),
                listOf(
                    2, 1, 1, 1, 2, 0, 1, 1, 0, 2, 0, 0, 3, 0, 2
                ),
                listOf(
                    1, 3, 0, 2, 1, 0, 3, 3, 3, 0, 2, 3, 2, 2, 0
                ),
                listOf(
                    2, 1, 0, 2, 0, 0, 3, 3, 3, 3, 3, 3, 2, 0, 1
                ),
                listOf(
                    2, 1, 1, 1, 3, 1, 1, 2, 2, 0, 1, 0, 0, 3, 2
                )
            ), listOf(
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 25),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk35(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_016", "Expert", 15, 25, listOf(
                listOf(
                    0, 2, 0, 3, 1, 2, 1, 3, 2, 2, 3, 1, 2, 3, 3
                ),
                listOf(
                    1, 3, 1, 1, 3, 1, 3, 3, 1, 1, 2, 0, 3, 0, 1
                ),
                listOf(
                    2, 2, 3, 1, 2, 3, 0, 3, 1, 3, 1, 1, 2, 3, 0
                ),
                listOf(
                    3, 2, 3, 1, 3, 0, 2, 2, 0, 3, 2, 3, 2, 3, 3
                ),
                listOf(
                    0, 3, 1, 0, 3, 0, 1, 3, 0, 1, 1, 3, 3, 2, 0
                ),
                listOf(
                    1, 3, 3, 2, 2, 1, 0, 3, 3, 1, 1, 1, 1, 2, 0
                ),
                listOf(
                    0, 2, 2, 3, 2, 0, 0, 2, 2, 3, 3, 3, 0, 2, 2
                ),
                listOf(
                    0, 2, 3, 0, 0, 1, 3, 2, 3, 1, 1, 1, 3, 0, 2
                ),
                listOf(
                    2, 0, 1, 2, 0, 3, 0, 1, 3, 0, 1, 1, 3, 3, 1
                ),
                listOf(
                    2, 2, 2, 2, 0, 0, 0, 3, 3, 1, 0, 3, 2, 3, 2
                ),
                listOf(
                    2, 3, 1, 0, 3, 1, 3, 1, 2, 3, 3, 2, 1, 2, 3
                ),
                listOf(
                    1, 2, 2, 0, 1, 3, 3, 0, 1, 1, 0, 3, 1, 1, 1
                ),
                listOf(
                    3, 2, 1, 0, 3, 3, 0, 1, 1, 3, 3, 2, 2, 0, 0
                ),
                listOf(
                    3, 1, 1, 2, 1, 1, 3, 3, 1, 3, 2, 3, 1, 2, 2
                ),
                listOf(
                    2, 2, 3, 2, 3, 1, 3, 3, 1, 3, 3, 2, 0, 1, 0
                ),
                listOf(
                    3, 3, 1, 2, 3, 2, 0, 2, 0, 3, 1, 2, 3, 3, 3
                ),
                listOf(
                    3, 3, 3, 3, 1, 3, 3, 1, 3, 2, 2, 0, 0, 1, 3
                ),
                listOf(
                    2, 1, 0, 0, 3, 2, 3, 2, 1, 2, 0, 0, 1, 3, 1
                ),
                listOf(
                    0, 0, 3, 1, 0, 3, 0, 3, 1, 2, 2, 2, 1, 3, 0
                ),
                listOf(
                    1, 1, 0, 1, 1, 3, 3, 3, 0, 0, 3, 1, 3, 3, 1
                ),
                listOf(
                    3, 2, 0, 2, 1, 2, 1, 1, 1, 3, 1, 2, 2, 0, 2
                ),
                listOf(
                    1, 1, 0, 3, 1, 3, 3, 0, 1, 3, 3, 1, 2, 3, 0
                ),
                listOf(
                    2, 0, 1, 2, 1, 3, 1, 1, 1, 2, 3, 0, 0, 3, 0
                ),
                listOf(
                    3, 3, 0, 1, 2, 3, 3, 0, 0, 1, 2, 3, 3, 1, 1
                ),
                listOf(
                    2, 2, 3, 1, 3, 2, 0, 3, 2, 1, 1, 1, 2, 1, 2
                )
            ), listOf(
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 25),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk36(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_017", "Expert", 15, 25, listOf(
                listOf(
                    3, 1, 2, 3, 0, 3, 2, 0, 0, 0, 3, 2, 1, 0, 0
                ),
                listOf(
                    0, 3, 1, 3, 1, 2, 1, 1, 0, 2, 2, 3, 0, 3, 2
                ),
                listOf(
                    0, 0, 3, 1, 0, 1, 0, 2, 1, 2, 1, 0, 0, 2, 2
                ),
                listOf(
                    3, 3, 3, 2, 1, 2, 2, 0, 2, 3, 3, 2, 2, 3, 2
                ),
                listOf(
                    1, 0, 0, 1, 2, 1, 3, 2, 0, 0, 3, 2, 0, 0, 2
                ),
                listOf(
                    3, 1, 2, 3, 0, 3, 3, 1, 1, 0, 1, 0, 1, 2, 1
                ),
                listOf(
                    3, 1, 2, 3, 2, 2, 1, 2, 1, 1, 1, 0, 3, 2, 1
                ),
                listOf(
                    1, 2, 1, 2, 0, 0, 0, 0, 1, 1, 3, 2, 1, 1, 3
                ),
                listOf(
                    3, 2, 3, 0, 1, 1, 0, 2, 1, 3, 2, 0, 0, 0, 3
                ),
                listOf(
                    1, 3, 1, 0, 2, 1, 0, 0, 1, 2, 1, 1, 1, 3, 0
                ),
                listOf(
                    2, 2, 0, 1, 3, 3, 2, 1, 0, 2, 1, 2, 0, 2, 1
                ),
                listOf(
                    0, 3, 3, 3, 0, 2, 3, 2, 0, 2, 2, 3, 3, 3, 0
                ),
                listOf(
                    3, 0, 2, 2, 3, 2, 0, 0, 3, 0, 3, 3, 3, 1, 2
                ),
                listOf(
                    0, 0, 1, 3, 3, 1, 0, 0, 0, 0, 3, 2, 0, 3, 3
                ),
                listOf(
                    3, 2, 0, 3, 1, 1, 2, 2, 0, 0, 3, 2, 0, 0, 2
                ),
                listOf(
                    2, 2, 0, 3, 2, 3, 3, 0, 3, 3, 2, 2, 3, 2, 2
                ),
                listOf(
                    2, 1, 2, 1, 2, 3, 0, 3, 2, 3, 2, 1, 0, 2, 0
                ),
                listOf(
                    2, 0, 3, 3, 1, 1, 0, 2, 0, 3, 2, 3, 3, 3, 3
                ),
                listOf(
                    3, 0, 0, 3, 0, 3, 0, 3, 2, 3, 3, 2, 3, 0, 0
                ),
                listOf(
                    0, 2, 2, 2, 3, 2, 2, 3, 2, 0, 1, 2, 2, 0, 0
                ),
                listOf(
                    1, 0, 0, 0, 1, 3, 3, 1, 1, 2, 1, 3, 0, 2, 2
                ),
                listOf(
                    3, 2, 1, 0, 3, 0, 1, 3, 3, 0, 3, 3, 0, 1, 2
                ),
                listOf(
                    1, 2, 0, 3, 0, 0, 3, 0, 2, 3, 2, 2, 2, 3, 1
                ),
                listOf(
                    1, 3, 0, 1, 0, 2, 0, 1, 0, 0, 2, 3, 1, 2, 1
                ),
                listOf(
                    3, 1, 0, 3, 1, 2, 3, 3, 2, 0, 1, 2, 3, 2, 0
                )
            ), listOf(
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 25),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 25),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk37(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_018", "Expert", 15, 25, listOf(
                listOf(
                    3, 1, 0, 0, 2, 1, 2, 2, 1, 1, 3, 0, 0, 3, 2
                ),
                listOf(
                    0, 1, 3, 3, 2, 1, 2, 0, 0, 3, 0, 2, 0, 2, 0
                ),
                listOf(
                    3, 2, 0, 2, 0, 1, 1, 2, 0, 1, 2, 1, 0, 3, 1
                ),
                listOf(
                    2, 1, 2, 1, 1, 3, 1, 1, 1, 0, 2, 1, 2, 0, 1
                ),
                listOf(
                    2, 2, 0, 2, 0, 2, 3, 2, 1, 3, 3, 2, 1, 2, 2
                ),
                listOf(
                    1, 1, 0, 1, 0, 0, 0, 1, 1, 2, 0, 2, 0, 1, 0
                ),
                listOf(
                    0, 2, 0, 1, 0, 0, 0, 1, 1, 2, 0, 1, 2, 1, 1
                ),
                listOf(
                    0, 2, 0, 3, 1, 3, 1, 3, 1, 1, 1, 3, 2, 0, 1
                ),
                listOf(
                    0, 1, 0, 3, 1, 2, 2, 1, 0, 0, 3, 0, 2, 1, 2
                ),
                listOf(
                    2, 2, 2, 1, 0, 0, 1, 0, 3, 1, 0, 0, 1, 1, 3
                ),
                listOf(
                    2, 1, 0, 1, 1, 3, 2, 1, 1, 1, 0, 3, 1, 2, 0
                ),
                listOf(
                    0, 3, 1, 1, 3, 1, 3, 0, 3, 0, 0, 3, 0, 0, 2
                ),
                listOf(
                    3, 0, 2, 0, 1, 0, 0, 2, 1, 0, 1, 3, 0, 1, 2
                ),
                listOf(
                    2, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2
                ),
                listOf(
                    0, 2, 2, 2, 0, 2, 2, 2, 0, 1, 3, 0, 1, 2, 2
                ),
                listOf(
                    0, 2, 0, 1, 1, 1, 1, 3, 1, 1, 2, 2, 3, 1, 3
                ),
                listOf(
                    1, 2, 1, 0, 2, 2, 0, 0, 0, 2, 3, 2, 0, 1, 1
                ),
                listOf(
                    0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 1, 2, 2, 3, 3
                ),
                listOf(
                    1, 1, 2, 2, 3, 1, 1, 1, 2, 3, 0, 3, 0, 0, 0
                ),
                listOf(
                    1, 2, 2, 1, 3, 3, 2, 1, 2, 0, 2, 1, 1, 3, 0
                ),
                listOf(
                    0, 2, 2, 1, 0, 0, 0, 3, 1, 2, 2, 2, 3, 2, 2
                ),
                listOf(
                    1, 2, 1, 0, 3, 0, 1, 0, 0, 0, 3, 2, 1, 0, 1
                ),
                listOf(
                    1, 2, 1, 0, 3, 1, 0, 0, 0, 0, 0, 1, 0, 2, 2
                ),
                listOf(
                    0, 1, 2, 1, 1, 3, 1, 0, 1, 2, 1, 0, 0, 2, 2
                ),
                listOf(
                    3, 1, 2, 0, 3, 2, 2, 2, 0, 2, 0, 0, 1, 0, 2
                )
            ), listOf(
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 25),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk38(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_019", "Expert", 15, 25, listOf(
                listOf(
                    2, 1, 3, 3, 3, 2, 3, 3, 2, 2, 0, 2, 3, 3, 2
                ),
                listOf(
                    1, 3, 3, 1, 0, 2, 1, 2, 2, 3, 3, 2, 1, 3, 2
                ),
                listOf(
                    0, 1, 2, 0, 1, 3, 0, 3, 2, 2, 3, 3, 0, 2, 2
                ),
                listOf(
                    0, 3, 1, 3, 2, 3, 1, 0, 3, 3, 1, 1, 2, 3, 3
                ),
                listOf(
                    0, 0, 2, 1, 2, 3, 1, 3, 1, 2, 0, 3, 1, 2, 2
                ),
                listOf(
                    2, 2, 2, 0, 1, 1, 0, 3, 3, 1, 1, 1, 0, 1, 0
                ),
                listOf(
                    2, 0, 3, 0, 1, 3, 1, 0, 0, 0, 0, 2, 1, 1, 0
                ),
                listOf(
                    3, 3, 3, 3, 3, 2, 1, 1, 0, 0, 2, 2, 2, 0, 0
                ),
                listOf(
                    1, 0, 1, 1, 2, 3, 2, 2, 0, 0, 1, 2, 1, 2, 1
                ),
                listOf(
                    0, 1, 1, 2, 3, 0, 0, 2, 2, 3, 3, 3, 0, 2, 1
                ),
                listOf(
                    0, 0, 1, 3, 1, 2, 0, 1, 2, 1, 2, 3, 0, 0, 0
                ),
                listOf(
                    1, 1, 3, 2, 3, 0, 3, 0, 0, 3, 3, 1, 0, 0, 1
                ),
                listOf(
                    2, 2, 3, 1, 0, 0, 3, 1, 1, 1, 3, 0, 1, 0, 2
                ),
                listOf(
                    2, 2, 1, 3, 0, 0, 0, 3, 0, 2, 1, 2, 3, 0, 2
                ),
                listOf(
                    1, 0, 1, 2, 0, 3, 2, 1, 1, 1, 1, 2, 3, 0, 1
                ),
                listOf(
                    2, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 2, 0, 3, 2
                ),
                listOf(
                    1, 0, 0, 0, 0, 2, 2, 3, 2, 2, 3, 2, 2, 0, 2
                ),
                listOf(
                    1, 3, 1, 2, 2, 1, 0, 2, 2, 0, 3, 0, 1, 1, 3
                ),
                listOf(
                    2, 3, 0, 3, 1, 0, 0, 1, 0, 2, 2, 1, 2, 3, 0
                ),
                listOf(
                    0, 3, 3, 2, 1, 1, 0, 3, 0, 2, 1, 3, 0, 1, 3
                ),
                listOf(
                    1, 3, 1, 3, 2, 0, 2, 1, 3, 2, 2, 2, 1, 3, 1
                ),
                listOf(
                    1, 0, 1, 0, 1, 1, 2, 2, 3, 1, 0, 0, 1, 1, 2
                ),
                listOf(
                    3, 3, 0, 1, 2, 0, 1, 2, 0, 1, 0, 2, 0, 2, 0
                ),
                listOf(
                    1, 0, 2, 2, 1, 2, 1, 3, 2, 0, 3, 0, 3, 3, 1
                ),
                listOf(
                    3, 1, 2, 3, 0, 0, 0, 0, 1, 3, 2, 1, 3, 3, 0
                )
            ), listOf(
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 25),
                Tank(2, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 25),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk39(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_expert_020", "Expert", 15, 25, listOf(
                listOf(
                    3, 3, 3, 0, 2, 2, 1, 2, 0, 3, 2, 3, 3, 2, 3
                ),
                listOf(
                    0, 2, 2, 0, 1, 1, 2, 3, 3, 3, 1, 1, 2, 1, 0
                ),
                listOf(
                    2, 3, 2, 1, 3, 0, 3, 0, 1, 0, 0, 0, 3, 1, 1
                ),
                listOf(
                    3, 1, 2, 1, 1, 2, 2, 3, 3, 2, 0, 3, 3, 2, 1
                ),
                listOf(
                    2, 1, 3, 1, 3, 1, 0, 2, 1, 1, 1, 1, 2, 0, 3
                ),
                listOf(
                    3, 1, 3, 3, 2, 1, 0, 3, 1, 2, 0, 0, 2, 3, 1
                ),
                listOf(
                    3, 3, 3, 2, 3, 1, 1, 3, 1, 1, 2, 2, 2, 3, 3
                ),
                listOf(
                    3, 0, 1, 2, 3, 1, 1, 3, 0, 1, 0, 0, 0, 1, 1
                ),
                listOf(
                    1, 0, 3, 2, 2, 1, 1, 3, 3, 2, 1, 0, 0, 3, 1
                ),
                listOf(
                    1, 1, 0, 2, 0, 0, 3, 0, 3, 0, 0, 3, 2, 2, 1
                ),
                listOf(
                    3, 3, 0, 3, 2, 3, 0, 1, 1, 3, 3, 2, 2, 3, 1
                ),
                listOf(
                    3, 1, 2, 2, 2, 3, 3, 2, 1, 0, 1, 3, 1, 1, 3
                ),
                listOf(
                    0, 3, 2, 3, 3, 3, 3, 1, 2, 1, 2, 1, 1, 0, 2
                ),
                listOf(
                    0, 2, 0, 1, 3, 1, 2, 1, 2, 2, 3, 3, 3, 2, 3
                ),
                listOf(
                    0, 2, 1, 0, 1, 1, 1, 3, 3, 0, 2, 3, 2, 2, 1
                ),
                listOf(
                    3, 1, 0, 3, 1, 0, 1, 2, 1, 3, 3, 2, 1, 1, 3
                ),
                listOf(
                    0, 1, 0, 2, 1, 2, 1, 2, 1, 1, 0, 0, 1, 2, 1
                ),
                listOf(
                    0, 0, 1, 0, 2, 2, 2, 2, 3, 3, 1, 1, 2, 3, 2
                ),
                listOf(
                    2, 3, 1, 3, 0, 0, 1, 2, 3, 1, 3, 3, 0, 3, 3
                ),
                listOf(
                    2, 2, 2, 0, 2, 0, 0, 0, 2, 2, 1, 3, 0, 0, 0
                ),
                listOf(
                    0, 3, 0, 3, 1, 0, 1, 3, 0, 0, 2, 0, 2, 3, 2
                ),
                listOf(
                    2, 1, 1, 3, 1, 2, 0, 2, 2, 2, 2, 0, 2, 0, 3
                ),
                listOf(
                    1, 0, 2, 1, 1, 3, 0, 2, 2, 2, 0, 0, 2, 1, 2
                ),
                listOf(
                    0, 2, 0, 3, 3, 1, 3, 1, 2, 1, 1, 2, 3, 0, 3
                ),
                listOf(
                    1, 2, 0, 1, 1, 0, 3, 2, 3, 1, 0, 0, 2, 0, 3
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 25),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 25),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk40(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_001", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 2, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 3, 2, 0, 0, 2, 1, 3, 3, 0, 0, 0, 3, 1, 3, 2, 1, 2
                ),
                listOf(
                    1, 3, 0, 2, 0, 3, 2, 3, 2, 0, 3, 2, 3, 0, 3, 0, 1, 0, 3, 2, 1, 1, 3, 1, 3, 2, 2, 0, 0, 1
                ),
                listOf(
                    3, 2, 2, 1, 3, 2, 0, 3, 3, 2, 0, 1, 0, 3, 2, 2, 1, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 3, 3
                ),
                listOf(
                    1, 2, 2, 0, 1, 0, 2, 1, 3, 2, 3, 2, 1, 0, 3, 1, 0, 3, 3, 2, 3, 2, 2, 1, 2, 0, 3, 0, 2, 3
                ),
                listOf(
                    1, 3, 1, 3, 2, 1, 2, 1, 2, 0, 1, 3, 2, 1, 3, 1, 0, 3, 1, 2, 1, 3, 0, 3, 3, 2, 0, 0, 2, 1
                ),
                listOf(
                    3, 2, 2, 0, 3, 3, 2, 0, 3, 0, 3, 1, 0, 1, 0, 0, 3, 2, 1, 0, 2, 0, 1, 3, 0, 3, 0, 1, 3, 3
                ),
                listOf(
                    1, 0, 2, 3, 0, 0, 0, 3, 2, 2, 3, 1, 0, 0, 0, 1, 0, 0, 2, 0, 3, 0, 2, 0, 2, 0, 1, 2, 1, 0
                ),
                listOf(
                    2, 1, 0, 3, 3, 2, 3, 3, 0, 2, 3, 0, 0, 3, 3, 0, 0, 0, 1, 0, 1, 3, 1, 0, 1, 1, 1, 2, 0, 0
                ),
                listOf(
                    0, 3, 3, 3, 1, 1, 3, 2, 0, 3, 3, 1, 1, 1, 1, 3, 2, 3, 1, 0, 0, 1, 2, 1, 1, 2, 1, 3, 0, 0
                ),
                listOf(
                    0, 2, 1, 2, 0, 3, 2, 3, 3, 0, 3, 2, 2, 0, 2, 2, 3, 1, 3, 1, 0, 1, 0, 3, 3, 2, 1, 1, 3, 2
                ),
                listOf(
                    0, 0, 1, 0, 3, 2, 2, 0, 0, 2, 2, 0, 0, 1, 2, 3, 2, 0, 2, 3, 3, 1, 0, 3, 0, 3, 1, 2, 1, 3
                ),
                listOf(
                    1, 0, 3, 3, 1, 3, 3, 0, 2, 0, 2, 3, 3, 2, 2, 0, 3, 0, 3, 3, 0, 2, 3, 3, 0, 3, 1, 2, 3, 1
                ),
                listOf(
                    2, 3, 3, 2, 0, 3, 1, 2, 2, 2, 0, 2, 2, 2, 3, 0, 2, 3, 2, 2, 0, 1, 1, 3, 1, 3, 2, 0, 2, 3
                ),
                listOf(
                    1, 0, 1, 0, 2, 1, 1, 2, 2, 0, 2, 0, 3, 0, 2, 2, 3, 0, 0, 3, 3, 2, 0, 1, 3, 3, 2, 2, 1, 1
                ),
                listOf(
                    2, 3, 0, 3, 2, 3, 1, 0, 3, 2, 2, 3, 0, 1, 1, 1, 2, 0, 0, 2, 2, 0, 3, 3, 1, 1, 0, 3, 0, 2
                ),
                listOf(
                    0, 2, 1, 0, 0, 3, 2, 0, 1, 0, 3, 2, 3, 2, 1, 3, 0, 3, 1, 2, 3, 3, 2, 3, 1, 0, 2, 0, 1, 3
                ),
                listOf(
                    2, 2, 0, 1, 1, 2, 0, 2, 2, 0, 0, 0, 1, 2, 1, 2, 0, 1, 0, 0, 1, 0, 0, 0, 1, 2, 0, 3, 2, 2
                ),
                listOf(
                    1, 3, 3, 2, 0, 2, 3, 1, 1, 3, 0, 0, 3, 2, 1, 3, 1, 2, 0, 3, 0, 2, 0, 1, 0, 3, 2, 1, 2, 1
                ),
                listOf(
                    1, 0, 0, 0, 0, 0, 0, 0, 2, 2, 3, 2, 0, 2, 3, 2, 3, 1, 2, 0, 3, 1, 0, 0, 2, 1, 0, 2, 2, 3
                ),
                listOf(
                    1, 3, 0, 2, 2, 1, 3, 2, 1, 3, 0, 2, 3, 3, 0, 2, 3, 0, 0, 1, 1, 0, 0, 1, 3, 2, 0, 1, 2, 3
                ),
                listOf(
                    2, 0, 3, 0, 0, 2, 1, 0, 1, 0, 0, 3, 1, 0, 2, 2, 2, 3, 3, 1, 2, 0, 0, 1, 3, 2, 2, 1, 3, 3
                ),
                listOf(
                    1, 1, 2, 0, 3, 3, 0, 3, 2, 3, 0, 1, 0, 2, 3, 0, 2, 3, 2, 2, 1, 2, 3, 1, 0, 3, 1, 3, 3, 1
                ),
                listOf(
                    0, 3, 2, 3, 3, 3, 0, 2, 1, 0, 3, 0, 3, 3, 2, 2, 0, 2, 3, 1, 2, 1, 3, 0, 3, 1, 2, 3, 0, 1
                ),
                listOf(
                    1, 0, 2, 1, 0, 3, 2, 1, 3, 0, 3, 2, 3, 1, 1, 3, 2, 2, 1, 3, 1, 3, 2, 0, 2, 2, 2, 0, 2, 0
                ),
                listOf(
                    0, 3, 2, 2, 3, 3, 0, 0, 1, 0, 2, 3, 3, 3, 2, 0, 0, 3, 1, 1, 2, 3, 1, 2, 2, 2, 3, 1, 1, 2
                ),
                listOf(
                    1, 2, 0, 1, 3, 0, 1, 3, 0, 3, 2, 3, 2, 2, 2, 2, 3, 0, 3, 2, 3, 0, 0, 3, 0, 0, 2, 2, 1, 3
                ),
                listOf(
                    0, 3, 3, 2, 3, 1, 3, 3, 1, 2, 0, 1, 2, 3, 0, 3, 0, 0, 3, 3, 3, 1, 1, 0, 2, 1, 2, 2, 0, 3
                ),
                listOf(
                    2, 0, 1, 2, 2, 0, 0, 0, 1, 1, 2, 1, 0, 3, 2, 0, 2, 0, 0, 1, 0, 1, 3, 2, 3, 0, 1, 2, 3, 1
                ),
                listOf(
                    1, 2, 3, 0, 2, 1, 1, 0, 1, 0, 3, 2, 2, 3, 3, 1, 2, 3, 2, 2, 0, 2, 1, 1, 1, 2, 0, 1, 1, 2
                ),
                listOf(
                    2, 3, 3, 2, 2, 3, 1, 2, 3, 1, 1, 0, 0, 2, 2, 2, 2, 0, 1, 2, 2, 0, 0, 3, 3, 0, 1, 1, 3, 2
                ),
                listOf(
                    0, 1, 3, 0, 2, 1, 0, 3, 1, 3, 3, 0, 1, 3, 3, 0, 1, 2, 0, 1, 3, 1, 1, 1, 3, 2, 3, 2, 2, 0
                ),
                listOf(
                    0, 3, 1, 2, 3, 1, 3, 0, 3, 2, 3, 2, 1, 1, 0, 3, 3, 0, 3, 1, 2, 1, 0, 1, 2, 3, 2, 0, 3, 2
                ),
                listOf(
                    2, 1, 1, 3, 0, 3, 3, 1, 2, 1, 1, 2, 3, 2, 0, 3, 0, 0, 0, 0, 2, 3, 0, 0, 2, 3, 2, 1, 0, 1
                ),
                listOf(
                    1, 0, 3, 1, 3, 2, 0, 0, 2, 3, 3, 0, 1, 1, 2, 1, 0, 2, 3, 2, 3, 2, 1, 3, 1, 3, 1, 2, 0, 3
                ),
                listOf(
                    3, 2, 2, 1, 1, 1, 3, 3, 2, 2, 1, 2, 3, 2, 2, 3, 1, 2, 0, 1, 0, 0, 3, 2, 0, 2, 0, 0, 3, 1
                ),
                listOf(
                    2, 3, 0, 3, 0, 3, 0, 1, 3, 1, 2, 1, 2, 3, 3, 2, 3, 1, 3, 3, 2, 2, 2, 1, 1, 2, 3, 2, 3, 3
                ),
                listOf(
                    3, 3, 2, 0, 1, 1, 3, 2, 2, 0, 2, 3, 2, 3, 1, 3, 2, 0, 2, 2, 1, 0, 3, 3, 0, 0, 1, 3, 2, 2
                ),
                listOf(
                    2, 0, 0, 3, 1, 3, 0, 2, 1, 3, 3, 3, 2, 1, 3, 2, 1, 2, 0, 2, 3, 1, 0, 2, 2, 3, 1, 3, 2, 3
                ),
                listOf(
                    3, 1, 2, 3, 2, 3, 3, 0, 0, 3, 0, 2, 1, 3, 3, 2, 0, 1, 3, 1, 0, 3, 2, 1, 3, 2, 1, 2, 2, 0
                ),
                listOf(
                    0, 2, 0, 0, 3, 2, 0, 2, 0, 3, 3, 1, 0, 2, 3, 3, 3, 2, 3, 0, 3, 1, 2, 3, 3, 0, 2, 1, 3, 2
                ),
                listOf(
                    1, 2, 0, 3, 3, 1, 0, 3, 2, 2, 2, 1, 2, 3, 3, 0, 2, 1, 2, 2, 2, 2, 3, 3, 0, 1, 2, 2, 1, 0
                ),
                listOf(
                    3, 2, 0, 2, 2, 0, 0, 3, 3, 0, 0, 1, 3, 0, 2, 1, 0, 1, 3, 3, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 1, 2, 1, 2, 0, 2, 3, 2, 3, 3, 3, 3, 3, 2, 3, 0, 1, 3, 2, 0, 2, 1, 0, 1, 2, 3, 3, 0, 2
                ),
                listOf(
                    2, 3, 2, 1, 3, 2, 0, 1, 3, 3, 0, 1, 2, 0, 2, 0, 1, 0, 3, 2, 2, 3, 2, 1, 3, 1, 2, 2, 3, 3
                ),
                listOf(
                    3, 2, 3, 1, 2, 1, 0, 1, 3, 0, 3, 1, 3, 2, 1, 0, 2, 3, 1, 2, 3, 1, 1, 3, 3, 3, 1, 0, 1, 0
                ),
                listOf(
                    0, 1, 2, 3, 2, 2, 3, 2, 3, 1, 0, 3, 1, 0, 2, 3, 1, 3, 1, 0, 0, 3, 1, 0, 2, 0, 3, 3, 3, 1
                ),
                listOf(
                    1, 2, 3, 1, 1, 1, 3, 1, 0, 3, 2, 2, 0, 0, 3, 1, 3, 1, 1, 0, 1, 3, 3, 1, 3, 0, 1, 3, 2, 1
                ),
                listOf(
                    0, 0, 1, 1, 3, 3, 3, 1, 1, 3, 3, 0, 0, 3, 0, 1, 2, 2, 0, 0, 2, 0, 2, 3, 1, 1, 0, 0, 0, 3
                ),
                listOf(
                    1, 3, 3, 0, 3, 1, 1, 3, 0, 1, 3, 3, 1, 0, 3, 3, 0, 2, 2, 0, 2, 2, 3, 3, 1, 0, 3, 3, 0, 2
                ),
                listOf(
                    3, 3, 3, 2, 0, 2, 2, 2, 0, 0, 1, 0, 2, 2, 2, 1, 3, 3, 3, 0, 0, 2, 2, 2, 3, 2, 0, 2, 3, 2
                )
            ), listOf(
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 25),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk41(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_002", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 0, 1, 2, 3, 1, 3, 0, 3, 1, 0, 2, 3, 3, 3, 1, 0, 2, 3, 1, 2, 2, 1, 3, 0, 0, 0, 3, 1, 1
                ),
                listOf(
                    3, 1, 2, 3, 0, 0, 3, 3, 3, 0, 2, 1, 0, 2, 3, 0, 1, 1, 0, 2, 3, 1, 0, 2, 2, 2, 2, 2, 2, 2
                ),
                listOf(
                    1, 2, 3, 2, 0, 3, 0, 3, 2, 0, 2, 0, 1, 1, 2, 1, 2, 2, 1, 0, 2, 0, 3, 3, 0, 1, 3, 2, 2, 2
                ),
                listOf(
                    3, 2, 2, 2, 1, 0, 3, 2, 2, 1, 3, 0, 3, 3, 0, 0, 0, 1, 0, 0, 0, 3, 3, 2, 1, 1, 1, 3, 3, 0
                ),
                listOf(
                    0, 2, 3, 0, 0, 3, 0, 1, 2, 1, 0, 2, 2, 1, 1, 0, 2, 0, 0, 1, 0, 0, 1, 1, 0, 3, 3, 3, 0, 3
                ),
                listOf(
                    2, 3, 0, 0, 1, 1, 0, 3, 2, 2, 0, 0, 2, 1, 3, 1, 0, 0, 2, 0, 3, 0, 0, 1, 0, 0, 1, 0, 3, 0
                ),
                listOf(
                    2, 0, 3, 3, 2, 3, 2, 1, 3, 0, 2, 3, 1, 2, 3, 3, 1, 2, 2, 1, 1, 3, 0, 3, 1, 3, 0, 2, 3, 2
                ),
                listOf(
                    2, 2, 3, 3, 0, 0, 3, 0, 3, 2, 0, 0, 3, 1, 3, 2, 0, 3, 3, 3, 3, 3, 3, 3, 1, 0, 3, 0, 0, 2
                ),
                listOf(
                    3, 3, 3, 1, 1, 1, 2, 1, 3, 3, 2, 2, 3, 2, 2, 2, 3, 0, 0, 1, 3, 0, 0, 3, 3, 0, 0, 3, 3, 3
                ),
                listOf(
                    2, 0, 0, 3, 1, 2, 3, 1, 0, 1, 2, 2, 3, 0, 2, 2, 0, 3, 0, 1, 1, 2, 0, 0, 1, 3, 1, 1, 0, 1
                ),
                listOf(
                    1, 3, 1, 0, 2, 1, 1, 0, 0, 3, 0, 2, 0, 2, 0, 1, 3, 2, 1, 1, 2, 2, 1, 3, 3, 1, 2, 0, 1, 0
                ),
                listOf(
                    2, 0, 2, 2, 3, 1, 3, 1, 3, 2, 0, 1, 0, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 0, 3, 0, 0, 0
                ),
                listOf(
                    2, 0, 0, 0, 1, 2, 1, 2, 2, 3, 1, 2, 3, 3, 2, 0, 3, 3, 3, 2, 3, 0, 0, 1, 3, 0, 0, 3, 3, 0
                ),
                listOf(
                    3, 0, 3, 2, 2, 2, 1, 1, 0, 2, 3, 0, 1, 0, 1, 3, 2, 3, 2, 1, 1, 2, 1, 0, 0, 1, 3, 3, 0, 1
                ),
                listOf(
                    3, 2, 0, 2, 0, 3, 3, 1, 0, 2, 3, 1, 0, 1, 2, 1, 2, 0, 2, 0, 0, 3, 3, 2, 0, 1, 2, 1, 1, 2
                ),
                listOf(
                    3, 3, 0, 2, 2, 0, 2, 3, 3, 0, 1, 0, 1, 1, 3, 1, 2, 3, 3, 3, 1, 0, 0, 0, 0, 2, 2, 3, 3, 0
                ),
                listOf(
                    3, 2, 3, 2, 2, 2, 1, 3, 2, 3, 1, 1, 2, 3, 2, 0, 0, 2, 2, 3, 3, 3, 3, 3, 1, 0, 3, 3, 3, 3
                ),
                listOf(
                    3, 3, 2, 3, 0, 0, 2, 2, 0, 2, 3, 0, 0, 2, 0, 1, 3, 1, 1, 3, 3, 3, 0, 3, 3, 0, 1, 0, 0, 2
                ),
                listOf(
                    1, 3, 1, 1, 3, 3, 0, 1, 3, 3, 3, 2, 0, 3, 2, 2, 1, 1, 2, 1, 0, 1, 1, 0, 3, 2, 2, 1, 1, 1
                ),
                listOf(
                    2, 0, 0, 3, 3, 3, 2, 0, 3, 2, 1, 1, 3, 3, 3, 1, 2, 1, 3, 0, 0, 3, 0, 0, 0, 3, 3, 3, 2, 3
                ),
                listOf(
                    1, 2, 2, 3, 3, 1, 0, 3, 0, 0, 0, 3, 1, 2, 3, 1, 3, 1, 3, 0, 3, 3, 0, 1, 0, 1, 0, 1, 3, 2
                ),
                listOf(
                    1, 1, 0, 1, 1, 0, 0, 3, 3, 2, 3, 3, 2, 3, 3, 3, 0, 1, 2, 1, 3, 0, 2, 1, 1, 0, 1, 0, 3, 0
                ),
                listOf(
                    1, 3, 3, 3, 3, 0, 2, 2, 1, 2, 1, 2, 0, 0, 2, 0, 3, 2, 0, 2, 2, 2, 2, 0, 2, 0, 3, 3, 3, 2
                ),
                listOf(
                    2, 0, 2, 3, 1, 0, 3, 1, 3, 3, 1, 1, 0, 3, 3, 2, 0, 1, 3, 0, 1, 0, 0, 3, 1, 2, 2, 3, 0, 1
                ),
                listOf(
                    0, 1, 2, 0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 1, 2, 1, 2, 3, 3, 1, 1, 2, 3, 2, 0, 3, 1, 2, 1, 0
                ),
                listOf(
                    2, 1, 3, 1, 2, 0, 2, 0, 3, 2, 0, 3, 3, 0, 1, 2, 0, 2, 3, 2, 1, 0, 3, 3, 3, 2, 3, 3, 1, 2
                ),
                listOf(
                    2, 1, 3, 0, 3, 2, 0, 0, 2, 1, 2, 1, 2, 1, 1, 0, 1, 1, 3, 3, 0, 3, 1, 1, 2, 3, 2, 2, 0, 0
                ),
                listOf(
                    2, 1, 3, 2, 0, 1, 1, 0, 3, 3, 3, 3, 3, 0, 0, 3, 3, 0, 1, 2, 2, 1, 0, 0, 3, 3, 3, 0, 1, 3
                ),
                listOf(
                    2, 3, 1, 3, 3, 0, 3, 3, 1, 3, 3, 2, 2, 1, 2, 3, 1, 2, 1, 1, 0, 3, 3, 0, 0, 0, 3, 1, 3, 0
                ),
                listOf(
                    2, 3, 3, 2, 1, 1, 2, 1, 3, 2, 0, 0, 3, 1, 1, 1, 0, 1, 0, 1, 1, 0, 3, 0, 2, 0, 1, 1, 2, 1
                ),
                listOf(
                    1, 1, 2, 1, 1, 3, 3, 0, 1, 1, 2, 1, 1, 1, 3, 1, 0, 0, 2, 2, 3, 2, 0, 3, 3, 0, 1, 3, 0, 3
                ),
                listOf(
                    0, 1, 1, 3, 2, 2, 3, 0, 1, 1, 0, 0, 3, 0, 1, 0, 2, 1, 0, 2, 2, 2, 1, 3, 2, 3, 3, 2, 3, 1
                ),
                listOf(
                    0, 0, 0, 3, 1, 1, 3, 3, 3, 1, 0, 3, 1, 0, 2, 0, 2, 1, 0, 3, 3, 1, 0, 2, 2, 1, 2, 1, 3, 1
                ),
                listOf(
                    0, 2, 0, 3, 2, 1, 3, 1, 3, 1, 0, 2, 3, 2, 1, 3, 2, 0, 3, 1, 0, 2, 0, 0, 2, 0, 3, 2, 2, 2
                ),
                listOf(
                    1, 3, 0, 3, 2, 1, 3, 0, 0, 1, 1, 0, 0, 1, 2, 0, 0, 1, 3, 0, 0, 3, 0, 0, 1, 1, 2, 3, 1, 1
                ),
                listOf(
                    0, 3, 2, 3, 0, 3, 1, 2, 1, 3, 1, 0, 3, 0, 2, 1, 2, 2, 1, 1, 0, 3, 0, 1, 2, 1, 0, 1, 1, 1
                ),
                listOf(
                    2, 3, 2, 0, 1, 2, 2, 0, 3, 1, 3, 2, 3, 3, 3, 1, 3, 3, 2, 1, 0, 1, 0, 0, 2, 2, 1, 3, 0, 0
                ),
                listOf(
                    2, 1, 3, 3, 3, 1, 0, 1, 2, 2, 3, 3, 1, 1, 2, 0, 0, 3, 0, 0, 3, 3, 3, 2, 2, 3, 3, 3, 0, 0
                ),
                listOf(
                    3, 0, 3, 2, 1, 3, 0, 2, 2, 0, 0, 2, 3, 2, 3, 3, 2, 0, 0, 2, 2, 1, 2, 3, 0, 2, 1, 3, 0, 0
                ),
                listOf(
                    3, 2, 0, 3, 0, 0, 0, 3, 1, 1, 3, 1, 0, 3, 0, 2, 2, 3, 2, 0, 2, 1, 3, 1, 1, 2, 2, 3, 3, 0
                ),
                listOf(
                    2, 3, 2, 0, 1, 2, 2, 0, 3, 0, 2, 3, 1, 1, 3, 1, 0, 1, 3, 2, 2, 1, 1, 0, 2, 3, 0, 2, 3, 3
                ),
                listOf(
                    3, 0, 3, 1, 3, 0, 2, 2, 0, 0, 0, 2, 2, 3, 3, 0, 0, 1, 0, 2, 3, 3, 3, 2, 2, 1, 3, 3, 2, 3
                ),
                listOf(
                    3, 1, 2, 1, 3, 2, 0, 2, 3, 0, 0, 3, 0, 3, 1, 0, 0, 3, 2, 3, 3, 2, 2, 2, 2, 1, 0, 3, 2, 2
                ),
                listOf(
                    1, 1, 3, 3, 3, 0, 2, 3, 3, 3, 3, 2, 1, 3, 1, 3, 3, 0, 3, 1, 0, 3, 0, 1, 3, 3, 2, 2, 0, 1
                ),
                listOf(
                    2, 0, 2, 0, 1, 1, 3, 3, 3, 3, 2, 1, 1, 2, 0, 3, 3, 1, 0, 0, 2, 2, 2, 3, 3, 3, 1, 0, 3, 1
                ),
                listOf(
                    3, 1, 1, 1, 3, 3, 2, 0, 3, 1, 0, 1, 0, 3, 0, 1, 1, 3, 2, 2, 2, 0, 0, 3, 0, 1, 0, 0, 3, 3
                ),
                listOf(
                    2, 1, 3, 3, 2, 2, 2, 3, 1, 1, 1, 2, 3, 3, 2, 3, 1, 1, 2, 3, 3, 3, 0, 0, 0, 2, 2, 3, 2, 1
                ),
                listOf(
                    2, 0, 3, 0, 1, 2, 2, 3, 1, 1, 1, 2, 0, 3, 3, 0, 3, 1, 3, 1, 0, 2, 3, 3, 2, 2, 3, 2, 3, 0
                ),
                listOf(
                    0, 0, 3, 2, 3, 3, 2, 1, 0, 1, 2, 1, 0, 0, 2, 2, 1, 0, 0, 1, 0, 2, 1, 2, 0, 1, 2, 0, 3, 2
                ),
                listOf(
                    2, 0, 0, 1, 2, 2, 2, 3, 2, 0, 3, 0, 1, 1, 1, 0, 1, 1, 0, 0, 2, 2, 3, 1, 2, 2, 0, 2, 3, 2
                )
            ), listOf(
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 25),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk42(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_003", "Grandmaster", 30, 50, listOf(
                listOf(
                    1, 0, 3, 2, 2, 3, 0, 3, 3, 3, 2, 3, 2, 2, 2, 3, 0, 1, 1, 0, 3, 3, 2, 0, 3, 2, 2, 2, 3, 0
                ),
                listOf(
                    0, 3, 3, 1, 1, 0, 1, 0, 1, 0, 2, 0, 0, 1, 3, 1, 1, 0, 1, 0, 0, 3, 1, 2, 1, 3, 1, 1, 3, 0
                ),
                listOf(
                    0, 3, 3, 3, 3, 1, 3, 0, 3, 1, 1, 3, 3, 3, 2, 2, 3, 3, 0, 1, 1, 1, 2, 3, 3, 0, 1, 3, 3, 1
                ),
                listOf(
                    1, 2, 0, 3, 0, 0, 0, 0, 1, 1, 0, 0, 0, 3, 3, 1, 3, 3, 3, 3, 1, 1, 1, 2, 1, 0, 0, 0, 3, 3
                ),
                listOf(
                    0, 2, 2, 3, 2, 1, 3, 1, 3, 0, 1, 1, 1, 3, 3, 2, 0, 3, 1, 2, 1, 1, 2, 1, 1, 1, 3, 0, 3, 0
                ),
                listOf(
                    0, 1, 0, 0, 1, 0, 1, 0, 3, 3, 1, 0, 2, 2, 0, 2, 3, 3, 1, 2, 1, 3, 2, 2, 2, 2, 1, 2, 0, 3
                ),
                listOf(
                    3, 0, 2, 1, 1, 0, 0, 1, 3, 1, 2, 2, 3, 2, 3, 3, 3, 1, 1, 1, 3, 2, 1, 2, 0, 3, 2, 2, 2, 1
                ),
                listOf(
                    3, 2, 2, 1, 3, 1, 1, 1, 2, 2, 2, 2, 3, 0, 2, 3, 0, 3, 3, 0, 3, 0, 3, 2, 3, 0, 2, 3, 2, 1
                ),
                listOf(
                    0, 0, 0, 1, 3, 3, 3, 3, 2, 1, 3, 3, 1, 3, 3, 1, 0, 0, 1, 2, 1, 3, 2, 2, 1, 3, 1, 0, 2, 3
                ),
                listOf(
                    3, 1, 3, 1, 2, 0, 3, 1, 2, 0, 3, 0, 2, 1, 0, 3, 1, 1, 3, 2, 0, 1, 2, 0, 3, 3, 0, 1, 2, 0
                ),
                listOf(
                    1, 2, 2, 2, 3, 0, 1, 2, 2, 1, 2, 3, 0, 0, 0, 0, 0, 3, 0, 1, 0, 1, 0, 1, 0, 3, 1, 2, 0, 2
                ),
                listOf(
                    3, 1, 2, 3, 3, 3, 2, 1, 3, 1, 3, 1, 1, 3, 1, 2, 0, 1, 1, 0, 2, 3, 2, 3, 1, 3, 1, 3, 3, 0
                ),
                listOf(
                    0, 3, 3, 1, 2, 2, 3, 2, 0, 2, 1, 3, 2, 0, 0, 2, 2, 0, 3, 3, 0, 1, 2, 0, 3, 3, 2, 2, 2, 1
                ),
                listOf(
                    1, 3, 2, 3, 1, 1, 2, 1, 1, 2, 0, 0, 3, 2, 3, 0, 0, 2, 1, 0, 1, 3, 0, 2, 1, 3, 2, 2, 3, 2
                ),
                listOf(
                    2, 2, 2, 0, 1, 1, 3, 1, 1, 1, 0, 3, 1, 1, 0, 1, 1, 3, 1, 3, 3, 3, 1, 1, 1, 2, 3, 0, 3, 3
                ),
                listOf(
                    3, 2, 0, 1, 1, 1, 3, 0, 0, 2, 1, 1, 2, 1, 2, 1, 2, 0, 1, 0, 1, 3, 3, 0, 0, 1, 2, 3, 3, 1
                ),
                listOf(
                    2, 3, 3, 0, 3, 3, 0, 2, 0, 3, 2, 1, 3, 1, 1, 2, 1, 3, 0, 3, 1, 1, 3, 3, 0, 1, 3, 2, 0, 3
                ),
                listOf(
                    2, 3, 1, 1, 0, 1, 1, 1, 0, 0, 3, 2, 1, 1, 0, 3, 3, 2, 3, 3, 3, 3, 1, 2, 2, 2, 2, 3, 2, 1
                ),
                listOf(
                    2, 3, 0, 0, 1, 2, 3, 3, 2, 0, 0, 0, 2, 3, 0, 0, 3, 0, 3, 2, 2, 2, 1, 0, 3, 0, 1, 0, 1, 2
                ),
                listOf(
                    0, 1, 1, 0, 1, 3, 0, 0, 0, 2, 3, 3, 1, 1, 1, 3, 0, 0, 3, 1, 3, 3, 0, 3, 1, 3, 2, 3, 1, 3
                ),
                listOf(
                    2, 0, 1, 1, 1, 0, 2, 3, 0, 3, 2, 0, 0, 0, 3, 0, 1, 3, 0, 1, 2, 2, 1, 0, 3, 3, 1, 0, 0, 2
                ),
                listOf(
                    0, 0, 1, 3, 1, 1, 1, 1, 1, 2, 0, 2, 0, 3, 1, 3, 0, 1, 2, 0, 2, 0, 2, 3, 2, 0, 3, 0, 2, 0
                ),
                listOf(
                    0, 3, 3, 3, 1, 0, 3, 2, 1, 3, 0, 3, 0, 1, 2, 2, 2, 0, 2, 0, 3, 0, 1, 3, 1, 0, 0, 1, 2, 2
                ),
                listOf(
                    1, 2, 0, 3, 1, 2, 0, 3, 1, 1, 3, 2, 2, 3, 0, 2, 1, 3, 0, 2, 1, 3, 1, 1, 2, 3, 1, 2, 1, 1
                ),
                listOf(
                    1, 1, 1, 0, 1, 1, 1, 1, 3, 1, 0, 3, 3, 0, 2, 3, 3, 0, 2, 3, 3, 0, 2, 1, 1, 2, 0, 1, 0, 0
                ),
                listOf(
                    2, 3, 1, 0, 0, 1, 0, 0, 3, 1, 2, 0, 2, 3, 3, 3, 0, 0, 2, 2, 1, 3, 3, 0, 3, 3, 3, 3, 1, 3
                ),
                listOf(
                    0, 2, 0, 3, 1, 2, 2, 1, 2, 1, 1, 3, 1, 1, 2, 2, 0, 1, 0, 2, 3, 0, 3, 1, 0, 0, 2, 0, 1, 1
                ),
                listOf(
                    0, 0, 0, 1, 2, 0, 1, 1, 1, 0, 0, 0, 1, 2, 2, 3, 2, 1, 0, 3, 3, 1, 2, 0, 0, 2, 3, 2, 3, 0
                ),
                listOf(
                    2, 0, 0, 0, 1, 2, 1, 1, 2, 3, 1, 3, 2, 3, 3, 2, 2, 2, 0, 3, 0, 0, 3, 1, 3, 3, 2, 3, 2, 1
                ),
                listOf(
                    2, 1, 1, 1, 2, 1, 3, 2, 2, 1, 0, 1, 1, 3, 1, 2, 3, 0, 0, 0, 0, 1, 3, 1, 3, 0, 2, 3, 1, 3
                ),
                listOf(
                    3, 0, 3, 1, 1, 3, 2, 0, 3, 0, 3, 1, 0, 0, 3, 2, 3, 3, 2, 0, 1, 0, 2, 1, 0, 0, 0, 1, 3, 1
                ),
                listOf(
                    1, 2, 0, 1, 3, 3, 0, 2, 2, 2, 2, 3, 2, 3, 1, 2, 2, 0, 3, 0, 0, 2, 2, 1, 2, 0, 3, 3, 0, 1
                ),
                listOf(
                    0, 2, 2, 2, 2, 0, 3, 3, 2, 2, 1, 2, 1, 0, 3, 2, 0, 0, 0, 2, 2, 3, 0, 3, 3, 2, 1, 0, 3, 0
                ),
                listOf(
                    3, 2, 1, 3, 2, 1, 3, 1, 0, 1, 2, 1, 3, 2, 2, 2, 2, 1, 2, 3, 2, 2, 3, 2, 0, 3, 2, 1, 3, 3
                ),
                listOf(
                    3, 2, 3, 3, 2, 0, 1, 2, 1, 3, 0, 3, 3, 2, 2, 3, 2, 0, 1, 3, 1, 1, 2, 3, 3, 3, 0, 1, 2, 0
                ),
                listOf(
                    3, 1, 2, 0, 0, 0, 0, 2, 0, 3, 2, 3, 3, 2, 1, 3, 1, 3, 1, 3, 3, 3, 2, 2, 3, 1, 1, 3, 0, 1
                ),
                listOf(
                    3, 2, 3, 2, 2, 0, 2, 2, 0, 2, 2, 3, 3, 0, 3, 3, 3, 3, 2, 2, 1, 2, 3, 2, 0, 0, 0, 3, 0, 2
                ),
                listOf(
                    0, 0, 0, 3, 3, 2, 1, 3, 2, 1, 0, 3, 0, 3, 1, 3, 0, 1, 3, 2, 3, 3, 2, 3, 2, 2, 3, 3, 0, 2
                ),
                listOf(
                    0, 1, 2, 0, 3, 0, 1, 3, 0, 1, 2, 3, 0, 3, 2, 0, 3, 3, 3, 3, 1, 3, 2, 0, 3, 0, 0, 3, 1, 0
                ),
                listOf(
                    0, 0, 0, 1, 3, 1, 0, 2, 0, 1, 2, 0, 1, 1, 2, 3, 3, 0, 1, 1, 3, 2, 1, 2, 1, 3, 3, 2, 2, 3
                ),
                listOf(
                    3, 3, 3, 2, 0, 0, 3, 2, 1, 1, 3, 0, 3, 1, 0, 1, 1, 3, 3, 1, 0, 2, 1, 1, 2, 2, 1, 2, 3, 0
                ),
                listOf(
                    1, 2, 3, 1, 2, 2, 0, 2, 0, 3, 0, 2, 2, 3, 0, 0, 0, 0, 3, 3, 1, 0, 3, 1, 1, 2, 0, 3, 2, 3
                ),
                listOf(
                    2, 0, 1, 3, 3, 3, 0, 3, 3, 1, 1, 3, 0, 3, 2, 0, 0, 2, 1, 1, 3, 1, 2, 3, 2, 2, 3, 0, 0, 0
                ),
                listOf(
                    3, 0, 3, 3, 2, 2, 3, 0, 3, 2, 3, 2, 0, 0, 0, 3, 3, 1, 3, 2, 3, 0, 0, 3, 3, 0, 0, 1, 1, 0
                ),
                listOf(
                    2, 2, 0, 0, 2, 0, 1, 3, 1, 2, 3, 2, 1, 1, 1, 1, 3, 1, 2, 2, 2, 0, 2, 2, 3, 2, 2, 1, 0, 3
                ),
                listOf(
                    3, 2, 1, 0, 3, 3, 0, 2, 3, 3, 2, 1, 2, 0, 3, 2, 1, 0, 2, 0, 2, 2, 0, 3, 0, 2, 3, 1, 2, 3
                ),
                listOf(
                    2, 0, 3, 2, 3, 3, 0, 1, 1, 1, 3, 2, 3, 0, 1, 3, 3, 0, 3, 1, 2, 3, 3, 1, 1, 0, 0, 0, 1, 0
                ),
                listOf(
                    0, 0, 3, 2, 3, 0, 2, 0, 0, 3, 0, 2, 1, 2, 1, 3, 1, 3, 3, 2, 0, 0, 2, 1, 2, 1, 3, 3, 2, 3
                ),
                listOf(
                    3, 2, 0, 2, 0, 1, 2, 2, 3, 3, 0, 2, 0, 1, 3, 0, 1, 1, 0, 1, 3, 3, 1, 1, 3, 1, 2, 3, 2, 1
                ),
                listOf(
                    3, 1, 0, 0, 1, 3, 0, 2, 2, 3, 2, 0, 1, 0, 2, 2, 0, 3, 3, 3, 3, 2, 3, 1, 0, 0, 3, 2, 1, 1
                )
            ), listOf(
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk43(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_004", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 3, 0, 3, 0, 2, 1, 3, 1, 1, 3, 1, 0, 3, 3, 2, 0, 3, 1, 0, 1, 0, 0, 2, 2, 2, 0, 3, 2, 3
                ),
                listOf(
                    2, 2, 2, 1, 3, 0, 0, 2, 0, 2, 3, 2, 0, 3, 0, 0, 2, 0, 1, 0, 3, 2, 1, 3, 0, 1, 2, 2, 0, 3
                ),
                listOf(
                    0, 0, 0, 2, 2, 2, 2, 2, 3, 2, 3, 1, 2, 3, 0, 3, 1, 0, 1, 3, 3, 2, 2, 0, 0, 3, 3, 3, 0, 3
                ),
                listOf(
                    1, 3, 0, 0, 2, 2, 2, 3, 1, 1, 1, 0, 1, 0, 2, 3, 3, 3, 3, 0, 3, 1, 2, 0, 1, 2, 0, 2, 3, 1
                ),
                listOf(
                    0, 3, 1, 0, 0, 0, 3, 1, 3, 0, 2, 1, 0, 0, 0, 0, 3, 1, 3, 2, 3, 0, 1, 0, 1, 0, 3, 1, 0, 2
                ),
                listOf(
                    1, 3, 0, 1, 3, 1, 1, 2, 3, 1, 1, 0, 2, 1, 3, 1, 0, 0, 2, 3, 2, 1, 0, 3, 0, 0, 2, 1, 1, 0
                ),
                listOf(
                    0, 1, 1, 1, 1, 3, 0, 0, 1, 0, 3, 3, 3, 2, 1, 0, 3, 1, 3, 3, 1, 0, 3, 3, 3, 0, 2, 1, 2, 1
                ),
                listOf(
                    2, 3, 2, 1, 2, 2, 2, 1, 3, 3, 0, 3, 2, 3, 2, 0, 2, 1, 0, 0, 1, 2, 1, 2, 2, 1, 1, 3, 1, 3
                ),
                listOf(
                    0, 3, 3, 3, 1, 0, 1, 0, 0, 1, 1, 2, 2, 2, 3, 0, 3, 3, 0, 2, 2, 3, 1, 3, 0, 0, 1, 0, 1, 1
                ),
                listOf(
                    3, 2, 3, 1, 0, 0, 1, 3, 3, 2, 2, 0, 0, 2, 2, 0, 0, 2, 1, 2, 2, 2, 1, 2, 3, 2, 0, 1, 2, 2
                ),
                listOf(
                    3, 2, 1, 3, 2, 2, 2, 2, 3, 0, 0, 3, 0, 3, 3, 3, 2, 0, 2, 2, 1, 0, 3, 0, 3, 1, 3, 0, 2, 0
                ),
                listOf(
                    1, 3, 0, 0, 1, 1, 2, 3, 1, 1, 0, 1, 3, 1, 3, 1, 2, 2, 3, 3, 0, 0, 3, 0, 3, 0, 1, 3, 2, 0
                ),
                listOf(
                    1, 2, 2, 2, 2, 3, 1, 3, 0, 0, 2, 2, 3, 3, 0, 2, 3, 1, 0, 2, 3, 0, 2, 3, 2, 0, 3, 1, 2, 0
                ),
                listOf(
                    0, 1, 2, 2, 2, 3, 2, 1, 0, 2, 3, 1, 1, 0, 0, 2, 1, 1, 0, 3, 1, 3, 1, 2, 0, 0, 3, 3, 0, 0
                ),
                listOf(
                    2, 0, 0, 0, 1, 0, 2, 1, 0, 0, 2, 2, 3, 3, 3, 1, 2, 2, 3, 0, 0, 1, 3, 0, 3, 1, 2, 2, 1, 2
                ),
                listOf(
                    1, 1, 2, 2, 0, 3, 2, 0, 1, 3, 0, 1, 1, 1, 3, 0, 2, 2, 0, 2, 2, 3, 1, 2, 0, 0, 3, 1, 1, 0
                ),
                listOf(
                    2, 3, 1, 2, 0, 0, 0, 0, 1, 0, 1, 3, 2, 0, 1, 3, 0, 1, 0, 2, 3, 3, 3, 1, 3, 0, 1, 2, 0, 3
                ),
                listOf(
                    2, 1, 3, 2, 3, 3, 0, 0, 3, 3, 3, 2, 3, 0, 0, 3, 0, 0, 1, 3, 0, 1, 0, 0, 0, 1, 3, 2, 1, 2
                ),
                listOf(
                    0, 0, 1, 3, 0, 0, 3, 1, 0, 2, 3, 3, 1, 3, 2, 1, 2, 1, 0, 3, 3, 1, 2, 2, 1, 1, 3, 0, 1, 1
                ),
                listOf(
                    3, 2, 3, 3, 3, 0, 2, 1, 3, 1, 2, 3, 3, 3, 2, 0, 2, 2, 0, 3, 0, 1, 1, 3, 2, 2, 3, 1, 3, 0
                ),
                listOf(
                    0, 2, 2, 0, 1, 1, 3, 3, 1, 0, 3, 0, 2, 3, 0, 0, 3, 2, 2, 2, 1, 2, 0, 2, 0, 3, 0, 0, 3, 3
                ),
                listOf(
                    3, 0, 1, 0, 3, 0, 2, 3, 1, 1, 1, 2, 3, 2, 1, 1, 3, 2, 0, 3, 1, 0, 0, 0, 2, 3, 3, 1, 3, 1
                ),
                listOf(
                    1, 0, 0, 1, 1, 1, 3, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 3, 1, 3, 2, 2, 0, 2, 0, 1, 1, 2, 0, 1
                ),
                listOf(
                    0, 2, 1, 0, 2, 3, 2, 0, 0, 3, 1, 3, 3, 2, 2, 1, 3, 3, 0, 0, 1, 2, 3, 0, 2, 1, 1, 3, 1, 2
                ),
                listOf(
                    2, 2, 0, 1, 3, 1, 1, 0, 2, 3, 0, 2, 0, 2, 2, 2, 0, 3, 2, 2, 0, 3, 0, 2, 3, 1, 3, 0, 3, 3
                ),
                listOf(
                    0, 3, 2, 2, 0, 3, 0, 1, 3, 2, 1, 2, 0, 1, 3, 3, 1, 3, 2, 0, 2, 2, 0, 3, 1, 3, 1, 0, 3, 2
                ),
                listOf(
                    2, 3, 2, 1, 1, 0, 2, 3, 2, 0, 3, 1, 1, 2, 2, 0, 0, 3, 2, 0, 3, 1, 3, 3, 3, 1, 2, 2, 3, 2
                ),
                listOf(
                    3, 2, 2, 0, 1, 3, 0, 2, 2, 2, 0, 1, 2, 2, 3, 3, 0, 2, 0, 2, 1, 0, 3, 2, 3, 0, 2, 2, 0, 3
                ),
                listOf(
                    0, 1, 0, 2, 3, 2, 3, 3, 3, 1, 1, 1, 3, 1, 1, 1, 2, 2, 0, 3, 0, 0, 2, 1, 3, 3, 2, 1, 0, 0
                ),
                listOf(
                    2, 1, 3, 3, 0, 1, 0, 3, 0, 0, 2, 2, 1, 1, 2, 3, 2, 3, 1, 1, 0, 0, 3, 0, 0, 3, 1, 1, 0, 0
                ),
                listOf(
                    3, 2, 2, 0, 3, 2, 2, 2, 3, 0, 0, 2, 0, 0, 2, 3, 3, 3, 1, 0, 0, 1, 1, 3, 0, 0, 1, 3, 1, 2
                ),
                listOf(
                    2, 0, 2, 1, 3, 0, 3, 2, 2, 1, 3, 2, 2, 3, 1, 3, 2, 2, 3, 1, 0, 0, 2, 0, 3, 3, 0, 1, 2, 2
                ),
                listOf(
                    2, 1, 1, 1, 0, 3, 3, 0, 1, 2, 1, 0, 3, 3, 3, 0, 3, 3, 1, 2, 1, 2, 2, 1, 0, 1, 2, 3, 0, 3
                ),
                listOf(
                    1, 3, 3, 2, 0, 2, 1, 3, 3, 0, 2, 2, 0, 0, 3, 3, 3, 3, 0, 2, 0, 2, 3, 2, 0, 0, 1, 2, 3, 1
                ),
                listOf(
                    3, 1, 0, 1, 3, 2, 3, 1, 0, 1, 2, 3, 0, 3, 1, 1, 2, 1, 2, 2, 2, 0, 2, 2, 3, 0, 2, 3, 0, 2
                ),
                listOf(
                    3, 2, 2, 1, 3, 2, 2, 2, 0, 3, 3, 1, 0, 0, 1, 1, 1, 1, 0, 2, 1, 0, 3, 2, 3, 1, 0, 1, 1, 0
                ),
                listOf(
                    0, 0, 1, 3, 0, 3, 0, 2, 0, 0, 2, 1, 1, 1, 2, 2, 0, 2, 3, 0, 2, 1, 2, 0, 2, 2, 0, 2, 1, 2
                ),
                listOf(
                    3, 2, 2, 0, 2, 3, 2, 2, 2, 3, 3, 2, 0, 3, 2, 1, 1, 2, 0, 2, 0, 1, 2, 2, 0, 1, 0, 2, 0, 2
                ),
                listOf(
                    1, 1, 3, 1, 2, 0, 0, 3, 2, 3, 3, 2, 3, 1, 1, 0, 3, 1, 3, 2, 1, 1, 2, 0, 2, 2, 0, 0, 1, 2
                ),
                listOf(
                    1, 2, 0, 0, 3, 3, 3, 2, 2, 0, 3, 3, 0, 3, 0, 1, 0, 1, 0, 3, 3, 1, 2, 3, 0, 2, 3, 0, 0, 2
                ),
                listOf(
                    0, 2, 2, 1, 3, 2, 3, 0, 0, 3, 0, 1, 3, 3, 3, 2, 3, 1, 1, 2, 0, 3, 0, 1, 2, 3, 3, 0, 0, 0
                ),
                listOf(
                    3, 3, 1, 0, 1, 1, 3, 1, 3, 2, 1, 3, 3, 3, 1, 3, 2, 1, 1, 2, 2, 2, 0, 3, 0, 2, 2, 0, 2, 2
                ),
                listOf(
                    0, 0, 2, 1, 1, 3, 2, 1, 3, 2, 2, 3, 3, 1, 0, 0, 2, 3, 0, 0, 1, 0, 3, 2, 0, 0, 0, 1, 3, 1
                ),
                listOf(
                    1, 2, 3, 0, 2, 1, 1, 1, 1, 2, 2, 3, 2, 3, 3, 2, 2, 2, 3, 0, 1, 1, 2, 1, 1, 0, 3, 3, 0, 2
                ),
                listOf(
                    1, 2, 3, 0, 3, 2, 2, 2, 0, 1, 3, 3, 0, 1, 1, 3, 2, 3, 0, 2, 0, 3, 3, 2, 0, 3, 3, 0, 2, 0
                ),
                listOf(
                    0, 0, 3, 3, 0, 1, 0, 0, 2, 1, 0, 0, 3, 0, 0, 0, 2, 3, 0, 2, 0, 1, 3, 2, 2, 0, 1, 2, 1, 3
                ),
                listOf(
                    1, 3, 3, 2, 2, 2, 3, 2, 3, 2, 1, 2, 2, 0, 0, 1, 0, 0, 3, 2, 1, 1, 3, 0, 1, 3, 0, 3, 0, 3
                ),
                listOf(
                    1, 0, 1, 1, 3, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 3, 2, 3, 3, 0, 2, 1, 1, 2, 0, 3, 3, 2
                ),
                listOf(
                    2, 2, 1, 0, 0, 1, 2, 3, 3, 2, 0, 0, 3, 1, 2, 1, 0, 0, 0, 1, 0, 2, 3, 1, 1, 0, 0, 3, 1, 1
                ),
                listOf(
                    2, 0, 1, 3, 0, 2, 1, 1, 1, 1, 3, 1, 2, 2, 2, 3, 0, 2, 2, 0, 0, 2, 0, 3, 0, 0, 2, 0, 0, 0
                )
            ), listOf(
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(3, 15)
            )),
        )
    }

    private fun getChunk44(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_005", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 0, 0, 2, 2, 3, 2, 1, 2, 2, 1, 3, 3, 1, 2, 3, 2, 2, 3, 1, 3, 3, 3, 2, 3, 0, 2, 3, 0, 0
                ),
                listOf(
                    0, 1, 2, 1, 0, 1, 2, 0, 1, 3, 2, 3, 1, 3, 3, 2, 0, 1, 3, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 2
                ),
                listOf(
                    3, 2, 2, 3, 0, 3, 0, 3, 2, 3, 0, 3, 0, 2, 0, 3, 0, 1, 3, 3, 0, 3, 2, 2, 0, 1, 3, 0, 2, 2
                ),
                listOf(
                    3, 0, 3, 2, 0, 2, 2, 1, 2, 1, 1, 2, 2, 0, 1, 0, 0, 2, 1, 1, 0, 3, 2, 0, 3, 1, 3, 2, 0, 2
                ),
                listOf(
                    3, 2, 3, 2, 0, 1, 0, 0, 3, 1, 2, 0, 2, 3, 3, 0, 0, 1, 2, 1, 0, 2, 1, 3, 3, 0, 3, 2, 2, 1
                ),
                listOf(
                    0, 3, 2, 3, 0, 2, 2, 0, 0, 0, 2, 1, 3, 0, 3, 0, 3, 2, 1, 2, 0, 1, 3, 0, 3, 2, 2, 3, 1, 0
                ),
                listOf(
                    3, 3, 3, 3, 1, 1, 0, 0, 3, 1, 0, 0, 3, 3, 2, 3, 0, 3, 0, 3, 3, 3, 2, 1, 3, 1, 0, 0, 1, 2
                ),
                listOf(
                    2, 2, 2, 0, 0, 1, 1, 0, 2, 3, 2, 0, 1, 2, 1, 3, 0, 3, 2, 1, 3, 0, 3, 3, 0, 1, 1, 3, 3, 3
                ),
                listOf(
                    2, 0, 2, 3, 0, 2, 3, 1, 3, 2, 3, 2, 2, 3, 3, 2, 1, 1, 1, 0, 1, 0, 1, 2, 3, 1, 0, 0, 0, 1
                ),
                listOf(
                    1, 0, 1, 1, 2, 2, 3, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 1, 0, 2, 0, 1, 0, 3, 3, 2, 0, 2, 0, 0
                ),
                listOf(
                    3, 2, 3, 1, 2, 0, 2, 3, 2, 1, 1, 2, 0, 3, 0, 3, 3, 2, 0, 1, 3, 3, 0, 1, 3, 0, 0, 0, 1, 3
                ),
                listOf(
                    1, 1, 1, 3, 0, 0, 0, 0, 2, 2, 2, 2, 3, 0, 1, 0, 2, 3, 2, 2, 0, 3, 0, 0, 2, 3, 2, 2, 3, 0
                ),
                listOf(
                    0, 1, 0, 1, 0, 2, 0, 0, 2, 2, 1, 1, 1, 1, 3, 0, 1, 3, 2, 0, 0, 2, 0, 3, 0, 2, 0, 2, 0, 1
                ),
                listOf(
                    0, 3, 0, 3, 1, 0, 3, 3, 2, 2, 0, 3, 1, 3, 3, 1, 0, 0, 1, 1, 2, 2, 1, 3, 2, 2, 0, 0, 2, 0
                ),
                listOf(
                    0, 2, 1, 2, 1, 3, 0, 2, 0, 0, 2, 0, 0, 1, 3, 3, 3, 0, 2, 3, 0, 3, 1, 3, 2, 2, 3, 1, 1, 1
                ),
                listOf(
                    3, 2, 1, 3, 2, 2, 2, 0, 0, 2, 1, 2, 0, 3, 0, 0, 1, 2, 1, 3, 1, 2, 1, 1, 2, 3, 0, 3, 0, 3
                ),
                listOf(
                    2, 3, 2, 1, 3, 0, 0, 0, 3, 0, 0, 2, 3, 1, 0, 0, 2, 2, 0, 1, 2, 1, 2, 0, 0, 3, 0, 2, 3, 1
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 2, 1, 1, 2, 2, 2, 3, 0, 1, 2, 0, 0, 0, 3, 3, 0, 2, 3, 2, 2, 1, 3, 2, 2
                ),
                listOf(
                    1, 2, 1, 1, 2, 2, 0, 0, 2, 2, 3, 2, 3, 1, 2, 1, 0, 1, 2, 1, 1, 2, 3, 0, 2, 2, 3, 0, 3, 1
                ),
                listOf(
                    3, 1, 3, 2, 3, 2, 2, 1, 0, 3, 3, 3, 2, 3, 2, 2, 0, 1, 2, 2, 3, 3, 0, 0, 3, 0, 1, 0, 2, 0
                ),
                listOf(
                    3, 3, 0, 3, 3, 0, 0, 3, 2, 3, 2, 2, 0, 3, 1, 3, 0, 1, 2, 1, 2, 0, 3, 3, 3, 3, 3, 1, 1, 3
                ),
                listOf(
                    2, 2, 2, 3, 0, 2, 0, 3, 1, 3, 2, 0, 3, 3, 1, 2, 0, 3, 2, 0, 3, 0, 0, 3, 0, 2, 3, 0, 3, 0
                ),
                listOf(
                    3, 0, 0, 0, 3, 2, 2, 0, 1, 2, 0, 2, 1, 3, 3, 2, 0, 0, 0, 2, 0, 0, 0, 2, 2, 1, 1, 0, 3, 2
                ),
                listOf(
                    2, 2, 2, 0, 1, 3, 3, 1, 2, 2, 2, 1, 1, 3, 0, 3, 3, 3, 3, 2, 2, 3, 2, 0, 1, 0, 3, 2, 3, 0
                ),
                listOf(
                    3, 0, 3, 3, 1, 3, 1, 1, 2, 3, 2, 0, 0, 3, 2, 0, 2, 0, 0, 1, 2, 2, 2, 3, 1, 0, 0, 1, 2, 2
                ),
                listOf(
                    3, 3, 2, 0, 2, 3, 2, 2, 3, 2, 2, 3, 0, 3, 3, 3, 0, 0, 3, 3, 3, 2, 1, 2, 3, 2, 1, 1, 3, 1
                ),
                listOf(
                    1, 2, 3, 2, 1, 2, 1, 0, 3, 1, 3, 3, 1, 0, 1, 3, 1, 1, 3, 1, 0, 2, 1, 0, 0, 3, 2, 1, 2, 0
                ),
                listOf(
                    0, 1, 1, 2, 0, 2, 2, 0, 1, 1, 3, 3, 2, 2, 0, 2, 3, 1, 0, 2, 3, 2, 3, 0, 2, 0, 3, 2, 3, 3
                ),
                listOf(
                    0, 1, 0, 2, 1, 3, 3, 1, 1, 3, 3, 0, 1, 3, 3, 1, 2, 0, 0, 3, 0, 3, 2, 3, 2, 1, 2, 1, 1, 0
                ),
                listOf(
                    0, 0, 1, 0, 3, 3, 2, 2, 1, 1, 0, 0, 0, 2, 2, 1, 0, 1, 2, 1, 0, 1, 3, 3, 2, 1, 0, 1, 1, 1
                ),
                listOf(
                    1, 3, 0, 2, 2, 2, 3, 2, 2, 0, 2, 3, 2, 3, 2, 3, 3, 0, 1, 2, 0, 1, 0, 2, 2, 0, 0, 0, 3, 2
                ),
                listOf(
                    3, 2, 2, 0, 3, 2, 3, 1, 0, 0, 0, 3, 2, 1, 2, 3, 0, 3, 2, 2, 2, 2, 2, 0, 0, 1, 2, 1, 2, 0
                ),
                listOf(
                    1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 2, 1, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0, 1, 1, 1, 3
                ),
                listOf(
                    3, 3, 3, 2, 3, 0, 1, 0, 3, 2, 0, 3, 1, 2, 0, 2, 1, 3, 0, 0, 3, 2, 2, 2, 0, 2, 0, 1, 2, 2
                ),
                listOf(
                    2, 1, 3, 3, 2, 2, 3, 1, 0, 2, 1, 1, 3, 2, 0, 0, 0, 2, 2, 1, 1, 3, 0, 2, 3, 3, 3, 0, 3, 1
                ),
                listOf(
                    1, 1, 1, 2, 3, 3, 2, 2, 0, 0, 3, 2, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 3, 2, 2, 1, 3, 3, 2, 0
                ),
                listOf(
                    1, 1, 3, 0, 2, 2, 3, 0, 2, 1, 2, 3, 1, 1, 3, 3, 0, 2, 2, 0, 1, 3, 3, 0, 0, 3, 3, 0, 0, 0
                ),
                listOf(
                    1, 2, 3, 2, 2, 3, 0, 2, 1, 0, 1, 0, 0, 2, 0, 0, 3, 0, 2, 1, 3, 0, 1, 1, 0, 2, 2, 2, 2, 2
                ),
                listOf(
                    2, 0, 2, 0, 3, 2, 1, 1, 2, 1, 2, 2, 3, 3, 1, 2, 2, 3, 2, 2, 3, 2, 3, 2, 2, 1, 1, 2, 2, 2
                ),
                listOf(
                    3, 3, 2, 0, 3, 1, 1, 2, 0, 1, 3, 0, 2, 2, 2, 1, 2, 3, 2, 3, 0, 3, 0, 1, 2, 2, 3, 1, 0, 1
                ),
                listOf(
                    2, 0, 2, 1, 0, 2, 2, 1, 0, 3, 2, 1, 1, 0, 3, 3, 2, 3, 0, 3, 1, 2, 2, 0, 3, 0, 2, 1, 2, 2
                ),
                listOf(
                    1, 2, 2, 2, 0, 1, 0, 2, 2, 3, 2, 2, 0, 3, 3, 1, 2, 1, 0, 2, 0, 1, 3, 2, 2, 0, 2, 2, 3, 1
                ),
                listOf(
                    2, 2, 0, 0, 2, 2, 3, 2, 2, 3, 0, 3, 3, 3, 3, 2, 2, 1, 0, 1, 3, 1, 3, 3, 0, 3, 2, 2, 2, 0
                ),
                listOf(
                    3, 1, 1, 3, 2, 0, 3, 1, 1, 1, 1, 1, 2, 2, 3, 0, 1, 1, 3, 1, 1, 3, 1, 2, 3, 3, 2, 3, 3, 3
                ),
                listOf(
                    1, 3, 3, 2, 3, 3, 3, 3, 0, 2, 0, 1, 1, 0, 0, 1, 2, 1, 3, 3, 0, 0, 1, 2, 2, 3, 3, 3, 2, 3
                ),
                listOf(
                    3, 3, 2, 0, 1, 1, 3, 1, 2, 1, 2, 3, 2, 2, 3, 3, 0, 1, 0, 1, 2, 3, 3, 1, 2, 3, 1, 2, 1, 2
                ),
                listOf(
                    2, 2, 0, 3, 3, 1, 2, 3, 1, 3, 1, 2, 3, 3, 2, 3, 0, 3, 3, 1, 0, 2, 2, 2, 3, 1, 1, 1, 3, 2
                ),
                listOf(
                    1, 2, 1, 2, 1, 2, 0, 1, 1, 2, 1, 1, 3, 2, 1, 2, 3, 1, 0, 1, 0, 3, 0, 1, 2, 1, 2, 2, 2, 3
                ),
                listOf(
                    3, 1, 0, 1, 1, 1, 1, 2, 3, 0, 1, 3, 3, 3, 2, 2, 0, 3, 0, 2, 1, 3, 0, 0, 1, 2, 2, 1, 0, 3
                ),
                listOf(
                    3, 2, 2, 0, 3, 2, 0, 3, 2, 0, 2, 0, 2, 1, 0, 1, 3, 3, 2, 1, 3, 3, 2, 1, 0, 3, 3, 3, 2, 0
                )
            ), listOf(
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 25),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk45(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_006", "Grandmaster", 30, 50, listOf(
                listOf(
                    2, 2, 0, 0, 0, 1, 1, 2, 2, 3, 1, 3, 0, 1, 0, 0, 3, 3, 3, 1, 0, 1, 1, 3, 0, 3, 3, 3, 1, 3
                ),
                listOf(
                    3, 3, 0, 3, 1, 0, 1, 1, 1, 0, 3, 0, 2, 1, 1, 3, 0, 0, 1, 0, 1, 1, 3, 2, 0, 1, 3, 3, 2, 2
                ),
                listOf(
                    1, 1, 1, 0, 2, 1, 1, 1, 2, 1, 2, 2, 2, 2, 3, 3, 1, 1, 3, 0, 3, 3, 3, 2, 3, 0, 3, 1, 3, 0
                ),
                listOf(
                    1, 1, 0, 0, 2, 0, 0, 3, 1, 0, 2, 1, 2, 1, 3, 0, 1, 3, 2, 2, 3, 0, 1, 0, 1, 0, 3, 2, 3, 2
                ),
                listOf(
                    2, 3, 1, 0, 1, 3, 0, 0, 2, 2, 2, 1, 3, 3, 2, 3, 2, 3, 2, 2, 0, 1, 0, 1, 2, 1, 3, 2, 2, 2
                ),
                listOf(
                    0, 3, 3, 0, 3, 0, 3, 0, 1, 2, 2, 1, 0, 2, 3, 1, 3, 2, 3, 2, 0, 0, 2, 1, 2, 0, 0, 2, 1, 3
                ),
                listOf(
                    2, 1, 2, 1, 3, 0, 1, 2, 0, 1, 3, 2, 2, 0, 0, 0, 3, 2, 3, 3, 0, 3, 3, 3, 2, 1, 3, 0, 3, 3
                ),
                listOf(
                    0, 0, 1, 0, 3, 1, 3, 3, 1, 3, 3, 0, 1, 1, 3, 2, 2, 2, 0, 2, 0, 3, 2, 1, 2, 0, 1, 2, 3, 3
                ),
                listOf(
                    0, 0, 3, 0, 2, 1, 0, 2, 2, 0, 0, 0, 2, 2, 0, 0, 0, 2, 2, 2, 1, 0, 0, 0, 2, 2, 2, 3, 1, 0
                ),
                listOf(
                    2, 2, 3, 1, 0, 2, 1, 0, 3, 1, 0, 3, 2, 2, 1, 2, 2, 2, 0, 1, 0, 3, 3, 3, 1, 2, 2, 1, 2, 1
                ),
                listOf(
                    1, 1, 0, 1, 2, 0, 1, 1, 3, 2, 3, 1, 2, 2, 3, 2, 3, 3, 2, 2, 3, 2, 0, 1, 3, 3, 0, 3, 1, 0
                ),
                listOf(
                    0, 2, 1, 1, 3, 3, 1, 1, 2, 3, 0, 0, 3, 2, 1, 1, 1, 0, 1, 3, 1, 0, 0, 2, 0, 0, 3, 0, 0, 0
                ),
                listOf(
                    0, 0, 3, 0, 2, 3, 1, 1, 0, 2, 3, 2, 1, 1, 1, 3, 2, 1, 0, 1, 2, 1, 3, 1, 0, 2, 3, 0, 2, 0
                ),
                listOf(
                    1, 2, 2, 1, 3, 2, 2, 3, 0, 3, 2, 1, 0, 0, 2, 3, 3, 2, 3, 3, 1, 1, 1, 2, 2, 0, 2, 0, 0, 1
                ),
                listOf(
                    2, 2, 3, 0, 1, 3, 3, 1, 1, 2, 2, 2, 3, 0, 3, 1, 0, 1, 1, 1, 3, 0, 3, 1, 1, 2, 0, 0, 1, 0
                ),
                listOf(
                    2, 1, 1, 1, 2, 0, 3, 0, 2, 2, 1, 2, 1, 1, 3, 1, 2, 0, 0, 1, 0, 3, 1, 3, 2, 0, 2, 3, 2, 2
                ),
                listOf(
                    3, 0, 2, 3, 1, 3, 2, 0, 2, 0, 3, 0, 1, 2, 0, 0, 0, 2, 0, 2, 3, 1, 0, 1, 3, 2, 1, 2, 1, 2
                ),
                listOf(
                    0, 0, 3, 1, 2, 0, 1, 2, 0, 2, 0, 0, 1, 3, 3, 3, 2, 3, 3, 0, 3, 0, 2, 2, 0, 0, 0, 2, 0, 3
                ),
                listOf(
                    2, 2, 1, 3, 3, 0, 2, 1, 1, 0, 2, 3, 1, 0, 2, 1, 3, 2, 0, 3, 2, 3, 2, 0, 2, 1, 2, 3, 3, 0
                ),
                listOf(
                    3, 3, 1, 2, 3, 1, 2, 1, 2, 0, 3, 3, 0, 0, 1, 2, 1, 1, 0, 0, 3, 1, 0, 1, 1, 2, 3, 0, 0, 0
                ),
                listOf(
                    2, 1, 2, 1, 1, 1, 0, 0, 3, 2, 2, 1, 2, 3, 0, 0, 2, 1, 2, 1, 0, 0, 1, 0, 2, 3, 2, 2, 1, 2
                ),
                listOf(
                    2, 0, 0, 2, 2, 2, 1, 1, 2, 3, 3, 0, 2, 1, 0, 0, 1, 2, 0, 0, 0, 0, 2, 2, 1, 2, 3, 3, 0, 3
                ),
                listOf(
                    2, 2, 3, 0, 0, 1, 1, 3, 2, 2, 2, 1, 1, 2, 1, 0, 0, 2, 1, 2, 0, 3, 2, 1, 2, 1, 1, 0, 1, 0
                ),
                listOf(
                    3, 0, 0, 1, 0, 0, 0, 1, 0, 2, 3, 3, 1, 1, 0, 1, 1, 3, 3, 3, 3, 3, 3, 0, 2, 0, 3, 0, 3, 2
                ),
                listOf(
                    3, 0, 0, 1, 2, 0, 1, 1, 2, 2, 3, 0, 1, 0, 0, 3, 1, 0, 1, 3, 1, 3, 0, 2, 2, 1, 1, 0, 1, 0
                ),
                listOf(
                    3, 2, 0, 0, 1, 0, 2, 3, 0, 3, 3, 0, 2, 0, 2, 3, 0, 2, 0, 1, 3, 3, 2, 2, 0, 2, 0, 0, 0, 2
                ),
                listOf(
                    3, 0, 2, 2, 3, 1, 0, 2, 1, 3, 2, 0, 2, 2, 1, 0, 3, 0, 1, 3, 2, 2, 3, 0, 3, 1, 2, 3, 2, 3
                ),
                listOf(
                    1, 0, 3, 0, 3, 3, 0, 1, 0, 1, 0, 2, 3, 1, 2, 0, 0, 3, 1, 3, 2, 1, 2, 3, 2, 2, 3, 2, 1, 3
                ),
                listOf(
                    2, 3, 0, 3, 2, 0, 1, 2, 1, 3, 1, 2, 2, 1, 2, 3, 3, 2, 1, 2, 1, 0, 3, 3, 3, 2, 0, 2, 3, 0
                ),
                listOf(
                    1, 3, 3, 2, 3, 2, 1, 3, 2, 2, 0, 0, 3, 3, 3, 0, 0, 3, 1, 1, 2, 0, 0, 1, 0, 1, 0, 2, 0, 1
                ),
                listOf(
                    2, 2, 3, 2, 1, 2, 2, 3, 2, 2, 0, 2, 2, 0, 2, 1, 1, 0, 0, 3, 0, 0, 0, 3, 0, 1, 0, 2, 3, 3
                ),
                listOf(
                    1, 3, 0, 3, 3, 1, 0, 0, 1, 2, 3, 3, 3, 2, 1, 1, 0, 0, 0, 3, 2, 0, 3, 1, 0, 3, 3, 2, 1, 3
                ),
                listOf(
                    2, 1, 0, 0, 0, 1, 0, 3, 0, 1, 0, 3, 1, 3, 0, 2, 0, 1, 1, 2, 0, 1, 2, 2, 1, 1, 1, 3, 1, 3
                ),
                listOf(
                    1, 3, 1, 1, 0, 2, 1, 3, 3, 3, 3, 1, 0, 2, 0, 1, 3, 0, 1, 2, 0, 0, 0, 0, 0, 0, 2, 3, 3, 3
                ),
                listOf(
                    3, 2, 2, 3, 3, 1, 2, 1, 1, 1, 2, 0, 1, 2, 2, 2, 0, 3, 0, 0, 1, 0, 1, 2, 2, 0, 2, 2, 3, 2
                ),
                listOf(
                    0, 3, 3, 3, 0, 0, 3, 1, 3, 3, 3, 3, 0, 2, 1, 3, 1, 1, 1, 3, 3, 2, 3, 1, 0, 1, 3, 2, 0, 2
                ),
                listOf(
                    3, 0, 1, 0, 0, 3, 2, 0, 1, 3, 3, 0, 2, 3, 0, 0, 2, 2, 0, 2, 2, 0, 2, 0, 0, 1, 0, 1, 0, 1
                ),
                listOf(
                    0, 1, 1, 1, 3, 1, 2, 2, 3, 3, 1, 2, 1, 0, 0, 0, 1, 1, 2, 3, 0, 2, 0, 1, 0, 3, 0, 0, 1, 1
                ),
                listOf(
                    2, 0, 2, 2, 1, 2, 1, 1, 3, 1, 1, 0, 0, 2, 2, 3, 3, 2, 0, 0, 3, 3, 1, 2, 0, 0, 3, 2, 2, 2
                ),
                listOf(
                    3, 3, 3, 0, 3, 1, 0, 1, 1, 2, 3, 1, 2, 2, 3, 2, 2, 2, 1, 1, 1, 3, 3, 1, 1, 3, 1, 2, 2, 0
                ),
                listOf(
                    3, 0, 1, 0, 0, 3, 1, 2, 0, 1, 0, 3, 2, 2, 0, 3, 1, 3, 2, 3, 1, 0, 0, 0, 2, 2, 0, 3, 2, 0
                ),
                listOf(
                    2, 1, 3, 1, 2, 1, 2, 3, 2, 0, 3, 3, 0, 1, 1, 0, 2, 3, 2, 1, 1, 1, 3, 1, 3, 0, 2, 2, 1, 2
                ),
                listOf(
                    3, 2, 1, 2, 0, 0, 0, 0, 3, 3, 1, 1, 1, 0, 2, 1, 3, 3, 1, 3, 0, 0, 0, 0, 0, 1, 0, 1, 3, 2
                ),
                listOf(
                    0, 3, 3, 3, 2, 0, 0, 3, 0, 1, 2, 0, 1, 3, 1, 2, 0, 1, 3, 3, 0, 2, 0, 2, 1, 3, 2, 3, 3, 2
                ),
                listOf(
                    1, 3, 3, 1, 1, 2, 3, 0, 1, 1, 2, 1, 1, 0, 1, 2, 3, 3, 2, 1, 3, 0, 0, 2, 2, 2, 2, 0, 2, 2
                ),
                listOf(
                    2, 3, 1, 2, 2, 3, 3, 1, 1, 0, 2, 3, 1, 1, 3, 0, 3, 1, 3, 1, 1, 3, 3, 2, 3, 3, 0, 0, 1, 1
                ),
                listOf(
                    3, 0, 3, 3, 1, 3, 3, 0, 3, 0, 3, 3, 3, 0, 0, 0, 1, 2, 3, 3, 2, 3, 1, 1, 3, 2, 3, 3, 2, 0
                ),
                listOf(
                    1, 0, 2, 1, 2, 0, 3, 2, 1, 2, 0, 0, 3, 3, 2, 2, 3, 1, 0, 3, 0, 2, 0, 0, 2, 1, 2, 2, 0, 0
                ),
                listOf(
                    3, 0, 3, 2, 2, 3, 2, 3, 2, 1, 0, 3, 2, 1, 1, 2, 3, 3, 2, 3, 1, 2, 1, 2, 2, 2, 1, 0, 0, 2
                ),
                listOf(
                    3, 0, 2, 0, 1, 0, 2, 0, 3, 0, 1, 2, 2, 0, 0, 3, 3, 0, 0, 2, 0, 1, 3, 1, 0, 3, 2, 0, 0, 1
                )
            ), listOf(
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(0, 25),
                Tank(3, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 25),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk46(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_007", "Grandmaster", 30, 50, listOf(
                listOf(
                    2, 2, 2, 3, 1, 1, 2, 3, 0, 0, 0, 3, 0, 1, 3, 3, 3, 1, 0, 1, 3, 1, 1, 0, 3, 2, 1, 3, 0, 3
                ),
                listOf(
                    0, 1, 1, 3, 1, 2, 1, 0, 1, 2, 2, 0, 2, 2, 2, 3, 3, 3, 3, 0, 2, 3, 1, 0, 1, 3, 1, 2, 3, 2
                ),
                listOf(
                    2, 2, 3, 2, 1, 1, 1, 3, 0, 3, 2, 0, 0, 3, 3, 2, 0, 3, 1, 0, 1, 2, 1, 0, 3, 1, 0, 3, 2, 2
                ),
                listOf(
                    3, 3, 2, 1, 0, 2, 1, 1, 1, 2, 0, 2, 1, 1, 0, 3, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 0, 3, 3, 2
                ),
                listOf(
                    1, 2, 0, 1, 1, 2, 2, 3, 3, 0, 0, 3, 1, 3, 3, 0, 0, 2, 3, 0, 1, 0, 2, 3, 3, 2, 2, 0, 1, 2
                ),
                listOf(
                    3, 0, 0, 3, 3, 0, 1, 0, 0, 1, 1, 0, 3, 1, 1, 2, 3, 3, 1, 2, 1, 2, 2, 3, 0, 0, 0, 3, 1, 2
                ),
                listOf(
                    1, 3, 0, 0, 2, 0, 3, 2, 1, 3, 2, 1, 3, 1, 2, 1, 0, 2, 0, 0, 1, 2, 1, 3, 3, 0, 2, 2, 1, 1
                ),
                listOf(
                    2, 0, 3, 1, 0, 1, 1, 2, 1, 2, 0, 0, 1, 1, 1, 1, 0, 2, 3, 1, 3, 3, 1, 3, 2, 3, 1, 1, 0, 1
                ),
                listOf(
                    0, 0, 0, 0, 3, 0, 3, 1, 1, 1, 2, 0, 3, 1, 3, 1, 0, 3, 0, 3, 0, 0, 2, 2, 3, 2, 3, 3, 3, 0
                ),
                listOf(
                    2, 3, 1, 0, 3, 0, 3, 2, 3, 1, 0, 3, 0, 0, 1, 2, 0, 3, 3, 1, 2, 3, 2, 0, 0, 1, 0, 1, 2, 2
                ),
                listOf(
                    1, 3, 2, 1, 0, 1, 3, 3, 1, 3, 1, 2, 0, 3, 3, 0, 2, 3, 1, 1, 0, 2, 3, 0, 3, 1, 1, 3, 1, 1
                ),
                listOf(
                    0, 0, 3, 3, 2, 1, 1, 1, 0, 3, 1, 2, 0, 1, 1, 0, 3, 0, 1, 1, 2, 2, 3, 2, 2, 1, 0, 3, 1, 0
                ),
                listOf(
                    2, 1, 1, 1, 1, 3, 2, 2, 0, 0, 0, 2, 0, 1, 1, 0, 0, 2, 3, 2, 3, 3, 0, 0, 1, 2, 0, 0, 2, 0
                ),
                listOf(
                    0, 3, 1, 1, 0, 2, 1, 1, 1, 0, 2, 1, 1, 1, 3, 0, 0, 3, 3, 1, 2, 1, 2, 3, 2, 1, 0, 2, 3, 0
                ),
                listOf(
                    0, 3, 2, 1, 0, 0, 3, 1, 3, 1, 2, 0, 1, 3, 2, 3, 1, 0, 1, 2, 2, 3, 2, 3, 0, 0, 1, 3, 0, 1
                ),
                listOf(
                    0, 1, 0, 2, 2, 2, 2, 3, 2, 0, 0, 2, 3, 0, 1, 1, 3, 1, 1, 0, 2, 1, 0, 1, 0, 3, 2, 3, 0, 0
                ),
                listOf(
                    3, 1, 2, 1, 0, 0, 3, 3, 0, 3, 1, 0, 3, 3, 2, 2, 0, 1, 2, 2, 1, 1, 0, 2, 1, 2, 3, 2, 0, 2
                ),
                listOf(
                    0, 0, 3, 0, 2, 2, 1, 3, 2, 0, 2, 3, 1, 0, 0, 0, 1, 1, 0, 3, 1, 0, 2, 2, 0, 1, 0, 2, 1, 0
                ),
                listOf(
                    2, 1, 2, 3, 2, 0, 1, 0, 1, 2, 2, 0, 2, 3, 2, 1, 1, 0, 1, 0, 1, 0, 1, 3, 1, 3, 3, 3, 1, 1
                ),
                listOf(
                    0, 0, 0, 1, 3, 0, 1, 0, 0, 1, 0, 3, 1, 0, 1, 1, 1, 2, 3, 0, 0, 1, 3, 0, 2, 2, 3, 1, 2, 2
                ),
                listOf(
                    3, 1, 0, 2, 0, 3, 3, 0, 1, 1, 0, 0, 3, 3, 0, 1, 2, 1, 2, 3, 2, 1, 3, 1, 0, 1, 0, 0, 2, 0
                ),
                listOf(
                    1, 0, 3, 1, 3, 0, 2, 0, 0, 2, 0, 1, 3, 0, 3, 3, 1, 3, 2, 2, 2, 0, 0, 3, 1, 3, 3, 0, 0, 2
                ),
                listOf(
                    1, 1, 2, 1, 1, 3, 0, 2, 3, 3, 1, 2, 0, 2, 0, 0, 0, 0, 3, 1, 3, 3, 3, 0, 1, 1, 1, 3, 0, 1
                ),
                listOf(
                    0, 0, 3, 0, 1, 2, 2, 1, 3, 2, 0, 1, 1, 3, 0, 1, 1, 1, 0, 0, 0, 1, 1, 2, 0, 3, 3, 0, 1, 1
                ),
                listOf(
                    2, 1, 1, 1, 1, 0, 1, 1, 0, 1, 3, 2, 2, 0, 0, 2, 1, 2, 1, 2, 3, 1, 0, 2, 1, 3, 2, 0, 2, 1
                ),
                listOf(
                    3, 1, 1, 3, 0, 3, 1, 3, 3, 1, 1, 2, 0, 1, 1, 2, 3, 2, 3, 2, 0, 3, 0, 1, 3, 1, 2, 2, 1, 2
                ),
                listOf(
                    2, 3, 0, 2, 0, 1, 1, 3, 2, 0, 0, 0, 3, 2, 1, 0, 0, 1, 1, 0, 0, 1, 2, 3, 0, 3, 3, 2, 0, 1
                ),
                listOf(
                    1, 3, 1, 2, 0, 2, 2, 2, 2, 1, 1, 2, 1, 3, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 0, 2, 2, 1, 0, 1
                ),
                listOf(
                    2, 1, 3, 3, 0, 2, 3, 2, 0, 1, 0, 0, 3, 0, 2, 0, 3, 2, 0, 0, 2, 0, 2, 0, 0, 3, 1, 0, 1, 0
                ),
                listOf(
                    1, 2, 1, 1, 0, 2, 2, 0, 0, 2, 2, 2, 2, 2, 1, 3, 0, 1, 3, 2, 3, 0, 2, 0, 2, 2, 0, 0, 1, 2
                ),
                listOf(
                    1, 0, 1, 3, 0, 1, 3, 1, 0, 2, 2, 1, 1, 3, 1, 0, 3, 1, 0, 3, 3, 2, 1, 1, 0, 2, 1, 0, 3, 0
                ),
                listOf(
                    0, 0, 3, 2, 1, 0, 1, 2, 1, 0, 2, 1, 2, 3, 0, 1, 0, 2, 0, 1, 3, 1, 1, 3, 2, 2, 3, 1, 0, 1
                ),
                listOf(
                    0, 1, 1, 3, 3, 0, 0, 1, 2, 0, 1, 1, 0, 0, 1, 0, 2, 3, 0, 1, 3, 1, 3, 2, 1, 3, 3, 2, 1, 3
                ),
                listOf(
                    3, 2, 1, 3, 1, 1, 0, 0, 2, 2, 2, 2, 3, 2, 3, 1, 2, 1, 2, 2, 3, 1, 1, 3, 0, 0, 3, 2, 1, 1
                ),
                listOf(
                    0, 2, 1, 0, 2, 1, 1, 3, 1, 3, 1, 3, 1, 0, 1, 2, 1, 0, 0, 3, 2, 0, 3, 0, 3, 2, 0, 2, 0, 1
                ),
                listOf(
                    1, 3, 1, 1, 3, 3, 1, 2, 1, 1, 2, 1, 2, 1, 3, 1, 3, 3, 0, 3, 0, 2, 2, 3, 3, 3, 3, 2, 0, 3
                ),
                listOf(
                    2, 0, 2, 3, 2, 3, 0, 3, 2, 1, 1, 2, 3, 0, 2, 2, 3, 2, 0, 0, 2, 1, 1, 1, 2, 3, 3, 2, 0, 1
                ),
                listOf(
                    2, 1, 3, 2, 2, 2, 2, 3, 2, 1, 1, 3, 0, 3, 1, 0, 2, 3, 3, 1, 1, 0, 3, 2, 3, 3, 0, 0, 3, 2
                ),
                listOf(
                    1, 0, 2, 0, 2, 3, 3, 0, 0, 0, 2, 0, 1, 3, 2, 1, 1, 2, 3, 3, 2, 2, 2, 2, 0, 3, 1, 2, 3, 1
                ),
                listOf(
                    0, 2, 1, 2, 1, 1, 1, 1, 2, 2, 3, 2, 2, 3, 0, 2, 1, 0, 3, 3, 1, 2, 1, 3, 2, 3, 2, 2, 1, 1
                ),
                listOf(
                    3, 0, 3, 3, 2, 0, 0, 3, 0, 1, 1, 2, 0, 1, 2, 3, 3, 2, 0, 3, 0, 0, 3, 1, 2, 3, 3, 3, 2, 1
                ),
                listOf(
                    3, 2, 1, 0, 2, 1, 2, 1, 2, 2, 3, 0, 3, 1, 2, 3, 3, 0, 3, 3, 1, 0, 1, 3, 1, 2, 2, 3, 0, 3
                ),
                listOf(
                    0, 2, 2, 0, 3, 0, 2, 3, 2, 3, 2, 2, 1, 2, 2, 0, 0, 1, 1, 2, 0, 2, 1, 1, 3, 2, 0, 3, 1, 3
                ),
                listOf(
                    2, 2, 3, 3, 0, 1, 1, 1, 2, 3, 1, 0, 0, 2, 1, 2, 3, 3, 1, 0, 0, 2, 0, 0, 1, 0, 1, 2, 1, 3
                ),
                listOf(
                    0, 1, 1, 0, 0, 1, 3, 3, 2, 3, 3, 1, 2, 2, 2, 1, 0, 1, 1, 2, 0, 1, 1, 0, 1, 3, 0, 2, 0, 1
                ),
                listOf(
                    1, 2, 2, 2, 0, 2, 1, 1, 0, 2, 1, 1, 3, 1, 2, 1, 2, 1, 3, 2, 0, 3, 2, 0, 2, 0, 2, 1, 3, 1
                ),
                listOf(
                    0, 3, 2, 0, 2, 0, 1, 1, 3, 1, 1, 1, 0, 0, 2, 1, 0, 0, 3, 2, 2, 1, 1, 1, 1, 3, 2, 1, 0, 2
                ),
                listOf(
                    0, 3, 3, 1, 2, 3, 1, 3, 0, 1, 1, 0, 1, 2, 1, 3, 0, 1, 3, 3, 3, 0, 0, 2, 3, 0, 0, 3, 0, 3
                ),
                listOf(
                    3, 3, 1, 1, 2, 2, 2, 3, 1, 1, 3, 0, 1, 1, 0, 1, 2, 2, 2, 1, 0, 2, 0, 0, 0, 0, 0, 3, 1, 2
                ),
                listOf(
                    0, 3, 1, 1, 1, 1, 2, 2, 3, 3, 3, 0, 2, 0, 3, 0, 3, 3, 2, 3, 3, 3, 2, 1, 1, 1, 3, 2, 1, 0
                )
            ), listOf(
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 25),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk47(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_008", "Grandmaster", 30, 50, listOf(
                listOf(
                    2, 3, 0, 0, 3, 2, 0, 1, 3, 2, 2, 0, 3, 3, 0, 3, 0, 1, 0, 2, 0, 2, 3, 0, 2, 2, 2, 3, 3, 3
                ),
                listOf(
                    2, 0, 0, 3, 0, 3, 2, 1, 1, 3, 0, 1, 0, 2, 1, 3, 1, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 3, 2, 1
                ),
                listOf(
                    3, 2, 2, 0, 3, 3, 3, 3, 1, 0, 3, 0, 1, 3, 1, 3, 2, 3, 1, 0, 2, 1, 2, 3, 2, 1, 0, 3, 2, 3
                ),
                listOf(
                    3, 1, 1, 2, 1, 0, 3, 0, 0, 3, 1, 1, 3, 3, 1, 3, 1, 0, 3, 2, 2, 2, 2, 2, 3, 0, 3, 2, 3, 0
                ),
                listOf(
                    0, 1, 2, 2, 3, 0, 0, 0, 1, 3, 3, 0, 0, 1, 3, 1, 2, 2, 0, 1, 1, 3, 0, 3, 1, 3, 2, 1, 1, 3
                ),
                listOf(
                    3, 3, 2, 1, 0, 1, 0, 1, 2, 2, 1, 1, 2, 3, 3, 3, 1, 2, 3, 0, 0, 1, 1, 0, 3, 3, 3, 2, 1, 1
                ),
                listOf(
                    3, 3, 3, 1, 2, 2, 2, 3, 0, 0, 3, 2, 3, 0, 0, 1, 2, 3, 2, 2, 0, 1, 2, 2, 0, 3, 0, 3, 2, 1
                ),
                listOf(
                    1, 3, 2, 2, 3, 0, 2, 0, 1, 3, 0, 0, 2, 0, 1, 3, 0, 3, 1, 1, 2, 0, 0, 3, 0, 2, 1, 0, 3, 3
                ),
                listOf(
                    1, 2, 2, 2, 0, 1, 0, 3, 2, 1, 3, 3, 3, 0, 3, 3, 3, 3, 1, 3, 0, 1, 3, 1, 3, 3, 2, 2, 2, 3
                ),
                listOf(
                    0, 3, 2, 3, 3, 1, 2, 2, 3, 2, 3, 0, 3, 2, 1, 3, 0, 0, 2, 3, 3, 2, 1, 1, 2, 0, 1, 3, 1, 2
                ),
                listOf(
                    2, 3, 2, 0, 3, 1, 2, 2, 1, 3, 1, 2, 2, 2, 1, 3, 1, 0, 0, 1, 2, 1, 2, 1, 2, 3, 0, 3, 3, 2
                ),
                listOf(
                    2, 3, 3, 1, 0, 1, 1, 3, 2, 2, 2, 1, 3, 0, 3, 3, 1, 3, 0, 0, 3, 1, 0, 2, 3, 3, 3, 2, 1, 1
                ),
                listOf(
                    1, 2, 2, 3, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 1, 1, 1, 3, 2, 0, 1, 1, 1, 1, 2, 1, 1, 1, 3, 0
                ),
                listOf(
                    3, 3, 0, 2, 0, 0, 3, 1, 0, 3, 0, 1, 2, 1, 2, 0, 3, 2, 2, 2, 0, 2, 1, 1, 1, 3, 2, 3, 3, 1
                ),
                listOf(
                    1, 3, 2, 0, 2, 2, 2, 2, 0, 1, 2, 1, 1, 2, 2, 1, 3, 2, 3, 2, 1, 1, 0, 2, 2, 3, 3, 2, 1, 1
                ),
                listOf(
                    2, 3, 2, 0, 2, 1, 0, 1, 0, 2, 1, 2, 3, 2, 1, 0, 3, 3, 3, 2, 2, 2, 0, 0, 2, 0, 2, 1, 1, 2
                ),
                listOf(
                    3, 3, 3, 3, 3, 0, 3, 3, 1, 2, 0, 2, 2, 3, 1, 2, 2, 0, 2, 2, 0, 0, 3, 3, 3, 2, 1, 3, 2, 2
                ),
                listOf(
                    2, 1, 2, 1, 1, 3, 1, 2, 1, 0, 1, 0, 3, 0, 2, 0, 0, 0, 0, 1, 2, 1, 1, 0, 3, 0, 1, 3, 1, 1
                ),
                listOf(
                    2, 2, 2, 2, 1, 1, 3, 2, 2, 3, 2, 0, 1, 1, 3, 0, 0, 1, 3, 2, 2, 2, 3, 3, 3, 0, 2, 3, 1, 2
                ),
                listOf(
                    1, 1, 2, 3, 1, 0, 2, 2, 2, 0, 2, 1, 2, 1, 2, 1, 0, 0, 3, 3, 2, 0, 2, 2, 0, 1, 3, 2, 3, 2
                ),
                listOf(
                    3, 0, 1, 0, 3, 3, 2, 0, 1, 3, 0, 1, 2, 3, 2, 0, 3, 1, 0, 1, 1, 0, 0, 2, 2, 2, 3, 2, 0, 1
                ),
                listOf(
                    0, 0, 3, 2, 3, 1, 1, 1, 3, 3, 3, 1, 3, 0, 2, 1, 2, 0, 0, 3, 3, 2, 1, 3, 2, 2, 2, 1, 1, 0
                ),
                listOf(
                    1, 2, 2, 0, 2, 2, 1, 3, 0, 1, 0, 3, 3, 1, 3, 3, 2, 2, 0, 3, 0, 2, 0, 1, 3, 3, 3, 0, 1, 3
                ),
                listOf(
                    3, 3, 0, 1, 0, 0, 1, 0, 2, 1, 0, 3, 0, 1, 3, 0, 2, 1, 3, 3, 2, 1, 1, 0, 1, 3, 3, 0, 0, 2
                ),
                listOf(
                    3, 2, 0, 0, 3, 0, 2, 3, 0, 0, 1, 2, 3, 2, 3, 1, 1, 3, 0, 1, 3, 2, 0, 2, 2, 0, 2, 1, 1, 3
                ),
                listOf(
                    1, 1, 1, 0, 2, 2, 0, 3, 0, 1, 1, 0, 0, 0, 1, 1, 1, 3, 1, 3, 2, 0, 2, 0, 0, 3, 0, 1, 3, 0
                ),
                listOf(
                    3, 1, 2, 3, 1, 3, 3, 1, 3, 0, 0, 1, 3, 3, 3, 3, 1, 1, 0, 2, 2, 1, 3, 1, 3, 1, 1, 1, 1, 2
                ),
                listOf(
                    1, 0, 0, 3, 0, 1, 0, 2, 1, 0, 2, 2, 0, 3, 1, 3, 2, 0, 2, 1, 0, 2, 2, 2, 3, 1, 2, 1, 1, 3
                ),
                listOf(
                    3, 2, 0, 2, 2, 1, 3, 3, 0, 0, 2, 0, 0, 1, 2, 3, 1, 2, 1, 1, 3, 3, 1, 0, 0, 0, 2, 2, 2, 1
                ),
                listOf(
                    0, 2, 3, 0, 2, 0, 0, 1, 1, 3, 2, 3, 2, 0, 1, 3, 3, 3, 1, 2, 1, 0, 1, 1, 1, 0, 2, 3, 0, 3
                ),
                listOf(
                    2, 2, 2, 1, 2, 3, 0, 2, 2, 0, 3, 0, 0, 3, 1, 2, 1, 1, 3, 0, 3, 0, 3, 2, 3, 2, 1, 2, 2, 3
                ),
                listOf(
                    0, 3, 1, 2, 2, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 2, 0, 3, 1, 0, 0, 3, 0, 1, 1, 2, 3, 2, 2, 1
                ),
                listOf(
                    2, 3, 1, 3, 2, 3, 3, 3, 1, 1, 3, 3, 3, 0, 2, 1, 2, 0, 3, 3, 0, 0, 1, 1, 0, 3, 1, 1, 3, 1
                ),
                listOf(
                    2, 1, 1, 3, 1, 1, 0, 3, 1, 2, 0, 2, 1, 0, 1, 3, 2, 1, 1, 0, 3, 1, 1, 2, 3, 0, 1, 0, 2, 3
                ),
                listOf(
                    2, 1, 1, 1, 2, 3, 3, 3, 3, 3, 0, 0, 2, 1, 3, 1, 0, 3, 0, 2, 3, 3, 0, 1, 2, 2, 1, 2, 2, 0
                ),
                listOf(
                    3, 3, 0, 2, 0, 1, 2, 0, 2, 0, 1, 1, 0, 3, 0, 2, 2, 1, 1, 2, 3, 3, 1, 3, 1, 3, 0, 3, 3, 1
                ),
                listOf(
                    2, 1, 1, 1, 0, 1, 1, 2, 1, 3, 1, 1, 1, 3, 0, 3, 1, 0, 3, 0, 0, 1, 3, 0, 3, 2, 2, 3, 0, 1
                ),
                listOf(
                    2, 1, 3, 1, 1, 3, 2, 1, 1, 1, 1, 0, 1, 0, 1, 3, 1, 1, 1, 3, 3, 1, 1, 3, 0, 1, 3, 3, 0, 3
                ),
                listOf(
                    2, 0, 0, 3, 1, 3, 3, 3, 3, 0, 3, 2, 1, 3, 0, 1, 1, 3, 1, 1, 2, 1, 0, 2, 3, 3, 2, 2, 2, 0
                ),
                listOf(
                    2, 1, 2, 0, 3, 0, 0, 2, 2, 0, 3, 2, 3, 2, 1, 2, 3, 2, 3, 0, 3, 0, 1, 1, 3, 2, 2, 3, 0, 2
                ),
                listOf(
                    2, 0, 3, 1, 2, 3, 3, 3, 3, 1, 3, 1, 1, 0, 1, 3, 2, 1, 0, 3, 0, 1, 1, 3, 0, 0, 2, 0, 1, 3
                ),
                listOf(
                    2, 2, 0, 1, 0, 2, 1, 1, 0, 1, 3, 3, 0, 0, 0, 1, 2, 2, 0, 1, 2, 0, 0, 2, 0, 2, 1, 3, 1, 0
                ),
                listOf(
                    0, 0, 2, 1, 3, 0, 1, 2, 0, 0, 1, 2, 2, 3, 1, 1, 1, 0, 1, 2, 3, 1, 0, 0, 3, 1, 2, 1, 3, 1
                ),
                listOf(
                    3, 1, 1, 2, 3, 1, 2, 3, 1, 2, 2, 1, 1, 2, 1, 0, 2, 3, 1, 3, 3, 2, 1, 1, 3, 2, 0, 0, 2, 1
                ),
                listOf(
                    2, 1, 2, 1, 2, 1, 1, 2, 3, 2, 3, 1, 2, 0, 2, 0, 2, 2, 2, 2, 1, 3, 1, 1, 1, 3, 1, 1, 0, 2
                ),
                listOf(
                    2, 3, 1, 2, 3, 2, 0, 2, 1, 1, 2, 0, 2, 0, 1, 0, 1, 1, 2, 2, 3, 2, 0, 0, 3, 3, 0, 2, 0, 3
                ),
                listOf(
                    2, 3, 1, 1, 0, 0, 3, 1, 3, 2, 0, 2, 0, 3, 2, 3, 3, 2, 2, 2, 2, 2, 3, 2, 0, 2, 3, 2, 2, 3
                ),
                listOf(
                    1, 2, 2, 3, 2, 3, 3, 3, 2, 1, 3, 0, 2, 2, 3, 3, 1, 0, 0, 2, 3, 0, 0, 0, 1, 0, 1, 3, 2, 3
                ),
                listOf(
                    1, 0, 2, 1, 1, 2, 3, 3, 2, 1, 2, 1, 1, 2, 1, 1, 3, 3, 2, 2, 3, 1, 3, 2, 2, 1, 2, 0, 1, 2
                ),
                listOf(
                    3, 3, 1, 0, 3, 1, 3, 0, 2, 1, 3, 2, 1, 3, 2, 0, 2, 0, 2, 2, 3, 1, 0, 1, 3, 2, 2, 2, 2, 0
                )
            ), listOf(
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 15)
            )),
        )
    }

    private fun getChunk48(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_009", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 3, 2, 1, 0, 0, 2, 2, 2, 1, 3, 1, 2, 2, 3, 3, 0, 2, 2, 1, 0, 3, 3, 1, 2, 0, 1, 0, 2, 2
                ),
                listOf(
                    0, 1, 0, 2, 0, 3, 3, 0, 3, 3, 3, 3, 0, 0, 0, 3, 3, 2, 0, 0, 3, 3, 2, 2, 3, 2, 3, 1, 2, 2
                ),
                listOf(
                    0, 2, 0, 2, 3, 0, 0, 0, 2, 2, 1, 0, 2, 0, 2, 1, 0, 3, 1, 2, 0, 3, 2, 0, 2, 3, 0, 0, 0, 3
                ),
                listOf(
                    3, 0, 1, 0, 2, 3, 0, 0, 2, 0, 1, 3, 1, 3, 0, 1, 3, 2, 3, 0, 3, 2, 1, 2, 1, 3, 0, 2, 0, 3
                ),
                listOf(
                    1, 0, 2, 0, 1, 3, 0, 3, 2, 0, 0, 3, 0, 2, 1, 0, 1, 2, 3, 1, 3, 3, 2, 2, 3, 2, 2, 3, 1, 2
                ),
                listOf(
                    2, 0, 0, 2, 3, 0, 0, 3, 0, 0, 2, 0, 2, 1, 0, 0, 3, 0, 3, 0, 1, 3, 2, 2, 1, 3, 1, 0, 0, 1
                ),
                listOf(
                    0, 3, 2, 1, 0, 1, 3, 3, 3, 0, 2, 1, 1, 0, 2, 3, 2, 0, 2, 3, 1, 0, 0, 3, 1, 0, 1, 0, 0, 3
                ),
                listOf(
                    0, 1, 3, 0, 3, 0, 2, 0, 2, 0, 3, 2, 2, 1, 3, 0, 3, 1, 3, 1, 1, 2, 2, 3, 1, 1, 1, 2, 0, 3
                ),
                listOf(
                    1, 3, 3, 3, 2, 3, 1, 2, 1, 2, 0, 2, 1, 3, 0, 1, 2, 3, 1, 2, 0, 0, 0, 0, 1, 2, 0, 3, 3, 0
                ),
                listOf(
                    3, 3, 0, 0, 3, 1, 2, 1, 3, 2, 1, 0, 2, 1, 0, 1, 1, 3, 1, 2, 2, 2, 0, 0, 0, 2, 3, 2, 2, 3
                ),
                listOf(
                    0, 0, 0, 1, 2, 1, 1, 2, 2, 3, 1, 3, 0, 1, 1, 2, 0, 1, 0, 0, 3, 1, 3, 2, 0, 1, 2, 3, 0, 0
                ),
                listOf(
                    1, 0, 2, 2, 0, 3, 0, 2, 1, 2, 3, 3, 3, 1, 2, 1, 2, 1, 0, 2, 3, 0, 1, 0, 3, 0, 2, 3, 2, 2
                ),
                listOf(
                    0, 1, 3, 2, 2, 2, 1, 0, 0, 2, 0, 0, 0, 3, 0, 0, 2, 0, 0, 2, 1, 2, 1, 3, 2, 0, 3, 2, 0, 3
                ),
                listOf(
                    1, 2, 3, 0, 0, 1, 3, 2, 3, 2, 1, 0, 2, 1, 2, 3, 3, 0, 0, 2, 3, 3, 3, 2, 0, 0, 0, 2, 1, 0
                ),
                listOf(
                    1, 2, 0, 0, 3, 3, 0, 0, 0, 3, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 2, 1, 0, 2, 3, 2, 3, 2, 2, 1
                ),
                listOf(
                    2, 2, 1, 0, 3, 2, 2, 2, 3, 1, 1, 3, 1, 1, 2, 3, 2, 1, 3, 3, 1, 0, 0, 0, 1, 1, 3, 0, 1, 1
                ),
                listOf(
                    2, 2, 3, 2, 2, 2, 3, 3, 2, 0, 1, 0, 0, 3, 3, 3, 0, 1, 0, 3, 2, 2, 3, 0, 0, 2, 3, 0, 1, 2
                ),
                listOf(
                    1, 3, 1, 0, 1, 0, 3, 2, 3, 0, 3, 2, 1, 1, 0, 2, 0, 1, 1, 0, 1, 0, 1, 2, 1, 2, 0, 0, 3, 1
                ),
                listOf(
                    3, 1, 2, 1, 3, 0, 0, 2, 1, 0, 2, 0, 0, 2, 0, 3, 0, 1, 1, 1, 0, 3, 1, 2, 3, 1, 1, 2, 0, 0
                ),
                listOf(
                    2, 0, 3, 2, 3, 0, 0, 3, 3, 2, 3, 2, 1, 0, 0, 2, 0, 1, 3, 3, 1, 1, 1, 3, 0, 0, 0, 3, 0, 0
                ),
                listOf(
                    0, 3, 2, 3, 0, 2, 3, 3, 3, 1, 3, 3, 0, 0, 2, 2, 0, 0, 3, 3, 3, 3, 1, 2, 0, 2, 3, 2, 0, 3
                ),
                listOf(
                    3, 0, 1, 2, 1, 2, 3, 0, 1, 1, 1, 2, 2, 0, 3, 1, 0, 3, 2, 2, 3, 2, 2, 0, 1, 0, 3, 0, 0, 0
                ),
                listOf(
                    3, 1, 0, 0, 2, 3, 2, 3, 3, 0, 1, 1, 2, 3, 0, 2, 3, 1, 0, 0, 0, 1, 3, 1, 1, 1, 1, 1, 3, 0
                ),
                listOf(
                    0, 0, 3, 1, 1, 0, 2, 1, 0, 0, 0, 1, 0, 1, 2, 2, 2, 0, 3, 1, 3, 3, 2, 3, 3, 0, 0, 0, 1, 2
                ),
                listOf(
                    0, 3, 0, 3, 0, 1, 0, 0, 2, 1, 0, 3, 0, 0, 3, 2, 2, 1, 2, 0, 3, 1, 1, 1, 2, 1, 2, 0, 3, 3
                ),
                listOf(
                    3, 2, 1, 3, 3, 1, 3, 3, 1, 1, 0, 0, 3, 3, 0, 2, 2, 0, 2, 0, 3, 0, 2, 3, 0, 0, 3, 2, 0, 2
                ),
                listOf(
                    1, 1, 0, 0, 3, 3, 0, 2, 0, 2, 2, 2, 2, 3, 3, 0, 1, 0, 3, 1, 1, 1, 3, 1, 3, 2, 2, 1, 3, 2
                ),
                listOf(
                    3, 2, 2, 1, 1, 3, 0, 3, 3, 2, 3, 1, 1, 0, 0, 2, 1, 3, 1, 0, 0, 0, 3, 1, 0, 0, 3, 0, 1, 2
                ),
                listOf(
                    2, 3, 2, 1, 0, 2, 0, 0, 0, 1, 2, 3, 1, 1, 2, 2, 0, 2, 2, 2, 1, 0, 1, 1, 2, 3, 2, 2, 3, 3
                ),
                listOf(
                    0, 2, 1, 0, 2, 3, 0, 3, 0, 2, 0, 3, 2, 2, 3, 0, 3, 1, 3, 0, 1, 3, 0, 3, 3, 3, 2, 2, 3, 0
                ),
                listOf(
                    3, 0, 0, 1, 2, 2, 0, 3, 1, 1, 1, 2, 0, 3, 0, 1, 0, 2, 1, 0, 0, 0, 0, 3, 2, 0, 0, 3, 3, 2
                ),
                listOf(
                    0, 2, 3, 2, 1, 3, 0, 3, 2, 0, 2, 3, 1, 2, 3, 0, 0, 0, 1, 3, 2, 2, 1, 0, 0, 2, 3, 2, 0, 3
                ),
                listOf(
                    1, 0, 0, 1, 2, 3, 2, 1, 3, 3, 1, 0, 3, 2, 2, 2, 3, 1, 0, 0, 1, 2, 1, 1, 2, 2, 0, 0, 0, 1
                ),
                listOf(
                    2, 0, 3, 1, 0, 2, 2, 3, 1, 2, 2, 0, 3, 1, 3, 3, 3, 2, 0, 2, 0, 1, 2, 3, 3, 3, 2, 2, 2, 2
                ),
                listOf(
                    2, 0, 2, 2, 0, 3, 0, 2, 1, 0, 0, 0, 1, 0, 3, 3, 1, 1, 0, 1, 0, 1, 2, 3, 1, 1, 0, 2, 0, 0
                ),
                listOf(
                    1, 2, 2, 0, 1, 3, 2, 0, 2, 3, 0, 3, 2, 1, 3, 2, 0, 1, 1, 3, 2, 2, 0, 0, 2, 1, 3, 3, 0, 3
                ),
                listOf(
                    3, 2, 1, 0, 0, 3, 1, 2, 3, 3, 1, 3, 0, 3, 0, 1, 0, 2, 0, 3, 0, 0, 2, 1, 3, 2, 2, 3, 1, 0
                ),
                listOf(
                    2, 2, 3, 3, 0, 3, 0, 1, 1, 0, 2, 1, 2, 2, 2, 1, 1, 0, 0, 0, 3, 2, 1, 1, 1, 1, 1, 1, 0, 3
                ),
                listOf(
                    3, 2, 0, 1, 2, 1, 3, 0, 3, 2, 3, 3, 0, 3, 2, 0, 0, 0, 3, 3, 0, 2, 3, 2, 2, 3, 0, 0, 3, 3
                ),
                listOf(
                    3, 3, 1, 2, 3, 3, 0, 3, 2, 2, 1, 3, 3, 1, 2, 0, 2, 0, 1, 0, 0, 0, 2, 0, 3, 0, 1, 3, 1, 2
                ),
                listOf(
                    3, 2, 0, 3, 0, 3, 3, 1, 2, 3, 1, 3, 2, 3, 3, 1, 3, 0, 3, 1, 1, 0, 2, 0, 3, 1, 2, 0, 1, 2
                ),
                listOf(
                    1, 0, 0, 3, 0, 0, 1, 2, 3, 2, 2, 2, 1, 0, 3, 1, 1, 1, 0, 0, 1, 1, 1, 1, 3, 0, 2, 1, 0, 0
                ),
                listOf(
                    0, 0, 2, 3, 1, 1, 3, 0, 3, 3, 3, 2, 3, 3, 0, 3, 3, 1, 1, 0, 3, 2, 3, 2, 1, 1, 1, 0, 0, 3
                ),
                listOf(
                    1, 0, 2, 0, 0, 3, 1, 3, 3, 1, 2, 0, 0, 2, 0, 0, 2, 2, 2, 2, 1, 2, 1, 2, 3, 0, 2, 2, 2, 0
                ),
                listOf(
                    2, 0, 3, 2, 3, 3, 1, 0, 3, 2, 3, 3, 2, 1, 1, 0, 3, 3, 3, 0, 2, 3, 0, 1, 3, 3, 1, 1, 3, 1
                ),
                listOf(
                    3, 2, 3, 0, 0, 0, 1, 0, 2, 0, 3, 0, 2, 1, 3, 1, 3, 1, 3, 0, 3, 0, 0, 2, 3, 3, 3, 0, 1, 3
                ),
                listOf(
                    2, 2, 3, 3, 0, 2, 3, 1, 0, 0, 0, 0, 2, 0, 2, 0, 2, 1, 0, 3, 0, 0, 0, 1, 1, 0, 0, 0, 3, 0
                ),
                listOf(
                    3, 1, 0, 3, 0, 0, 2, 2, 1, 2, 2, 2, 0, 3, 1, 0, 3, 1, 2, 3, 3, 0, 0, 3, 1, 3, 3, 3, 1, 2
                ),
                listOf(
                    3, 1, 3, 1, 2, 2, 2, 0, 1, 2, 0, 0, 0, 0, 1, 3, 3, 0, 2, 2, 0, 3, 1, 0, 1, 3, 2, 0, 3, 3
                ),
                listOf(
                    0, 2, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 0, 3, 1, 1, 0, 0, 1, 3, 0, 3, 0, 2, 2, 0, 1, 0, 0, 2
                )
            ), listOf(
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 25),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 25),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk49(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_010", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 0, 0, 0, 0, 2, 0, 2, 3, 3, 2, 0, 0, 1, 1, 2, 3, 3, 0, 0, 0, 0, 3, 0, 3, 3, 3, 1, 0, 1
                ),
                listOf(
                    2, 2, 1, 3, 1, 3, 2, 2, 1, 2, 0, 0, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 1, 3, 3, 3, 2, 1, 3, 1
                ),
                listOf(
                    2, 2, 2, 0, 3, 2, 2, 1, 0, 2, 0, 0, 0, 0, 0, 1, 3, 2, 0, 1, 1, 2, 0, 1, 0, 0, 3, 3, 1, 1
                ),
                listOf(
                    1, 2, 1, 1, 0, 0, 1, 2, 3, 1, 0, 0, 0, 0, 2, 3, 2, 0, 2, 1, 0, 2, 0, 3, 0, 3, 3, 3, 2, 3
                ),
                listOf(
                    2, 2, 3, 2, 1, 2, 3, 3, 1, 1, 0, 1, 0, 0, 3, 0, 2, 2, 3, 2, 3, 2, 3, 3, 2, 3, 0, 3, 0, 1
                ),
                listOf(
                    0, 0, 2, 0, 1, 3, 0, 0, 2, 0, 0, 0, 1, 3, 0, 3, 0, 1, 3, 0, 0, 3, 2, 1, 3, 3, 2, 3, 2, 3
                ),
                listOf(
                    1, 1, 3, 0, 1, 1, 1, 0, 3, 1, 0, 2, 1, 2, 0, 0, 0, 2, 2, 0, 1, 2, 1, 3, 3, 2, 2, 2, 0, 1
                ),
                listOf(
                    0, 3, 2, 0, 0, 2, 1, 0, 3, 3, 0, 0, 2, 1, 2, 2, 0, 0, 3, 2, 2, 0, 0, 3, 2, 2, 2, 1, 2, 2
                ),
                listOf(
                    0, 2, 2, 2, 2, 1, 2, 0, 0, 0, 1, 1, 1, 0, 1, 0, 3, 3, 0, 2, 2, 1, 3, 3, 0, 0, 3, 2, 3, 3
                ),
                listOf(
                    2, 0, 2, 2, 1, 3, 1, 1, 2, 0, 3, 3, 1, 2, 1, 3, 3, 3, 3, 3, 3, 2, 1, 3, 0, 1, 1, 2, 2, 3
                ),
                listOf(
                    2, 3, 0, 2, 0, 3, 2, 2, 2, 0, 0, 3, 1, 1, 0, 2, 1, 1, 3, 2, 3, 1, 0, 0, 0, 0, 0, 0, 3, 1
                ),
                listOf(
                    0, 1, 0, 2, 0, 1, 3, 2, 0, 1, 3, 3, 2, 3, 1, 3, 2, 2, 0, 0, 3, 3, 2, 1, 2, 2, 1, 0, 3, 1
                ),
                listOf(
                    0, 1, 0, 2, 0, 0, 2, 2, 1, 0, 1, 2, 0, 2, 3, 0, 2, 0, 0, 3, 3, 1, 1, 3, 1, 2, 1, 3, 2, 2
                ),
                listOf(
                    3, 1, 3, 1, 0, 3, 3, 2, 0, 3, 0, 2, 2, 0, 1, 1, 3, 0, 0, 1, 1, 0, 2, 1, 1, 0, 0, 0, 1, 3
                ),
                listOf(
                    1, 2, 3, 2, 2, 0, 1, 3, 0, 2, 2, 2, 2, 3, 1, 0, 1, 3, 3, 1, 2, 2, 2, 1, 2, 3, 2, 3, 1, 1
                ),
                listOf(
                    0, 0, 3, 1, 1, 0, 3, 2, 2, 3, 3, 2, 1, 1, 0, 0, 3, 1, 0, 2, 2, 2, 3, 3, 1, 1, 3, 1, 2, 3
                ),
                listOf(
                    3, 3, 0, 0, 2, 3, 1, 0, 3, 1, 3, 1, 0, 3, 3, 2, 0, 1, 0, 1, 2, 0, 0, 0, 3, 0, 3, 2, 1, 3
                ),
                listOf(
                    2, 0, 3, 3, 3, 2, 2, 2, 0, 1, 2, 0, 2, 3, 3, 1, 2, 0, 1, 1, 1, 0, 3, 2, 1, 3, 2, 3, 1, 1
                ),
                listOf(
                    2, 3, 0, 1, 3, 2, 0, 0, 0, 0, 1, 0, 0, 2, 3, 1, 1, 1, 1, 0, 1, 0, 2, 0, 1, 3, 2, 3, 3, 0
                ),
                listOf(
                    1, 2, 3, 2, 0, 1, 2, 2, 1, 1, 2, 0, 0, 1, 0, 2, 0, 1, 3, 1, 0, 1, 2, 0, 3, 0, 2, 0, 1, 1
                ),
                listOf(
                    3, 1, 3, 0, 2, 0, 0, 1, 0, 3, 2, 3, 2, 0, 3, 2, 0, 2, 2, 1, 2, 1, 0, 3, 0, 2, 1, 2, 3, 3
                ),
                listOf(
                    2, 0, 2, 1, 2, 2, 3, 0, 0, 1, 3, 1, 1, 1, 3, 1, 0, 2, 2, 0, 0, 1, 0, 2, 2, 1, 0, 2, 2, 1
                ),
                listOf(
                    2, 3, 3, 0, 3, 3, 0, 3, 0, 2, 0, 3, 1, 2, 3, 3, 0, 0, 3, 0, 2, 3, 1, 3, 1, 0, 1, 2, 0, 2
                ),
                listOf(
                    3, 1, 3, 1, 3, 0, 2, 3, 0, 2, 0, 0, 2, 2, 2, 2, 0, 3, 0, 1, 2, 0, 0, 3, 2, 1, 3, 2, 3, 0
                ),
                listOf(
                    0, 0, 2, 2, 3, 1, 0, 2, 2, 3, 0, 3, 0, 1, 0, 1, 0, 3, 0, 1, 0, 0, 3, 0, 2, 3, 1, 2, 2, 0
                ),
                listOf(
                    3, 0, 0, 3, 0, 3, 0, 1, 2, 0, 2, 2, 3, 1, 2, 0, 1, 2, 2, 1, 2, 1, 0, 3, 3, 1, 1, 3, 1, 1
                ),
                listOf(
                    0, 2, 0, 1, 2, 0, 0, 1, 1, 0, 1, 3, 1, 1, 3, 3, 1, 2, 0, 3, 0, 0, 0, 2, 2, 2, 1, 1, 0, 1
                ),
                listOf(
                    1, 1, 3, 3, 1, 3, 0, 3, 0, 0, 2, 0, 3, 0, 0, 2, 1, 1, 2, 0, 1, 0, 2, 2, 0, 3, 0, 1, 2, 1
                ),
                listOf(
                    3, 3, 0, 3, 1, 3, 3, 2, 0, 0, 3, 3, 2, 2, 0, 0, 3, 0, 2, 1, 0, 2, 1, 3, 0, 2, 1, 1, 2, 2
                ),
                listOf(
                    3, 2, 3, 1, 3, 2, 2, 3, 1, 2, 1, 1, 2, 0, 2, 2, 0, 2, 3, 0, 0, 2, 1, 2, 3, 0, 1, 3, 0, 3
                ),
                listOf(
                    0, 1, 3, 0, 2, 1, 3, 2, 3, 3, 3, 0, 2, 2, 2, 0, 1, 2, 1, 2, 2, 0, 1, 2, 1, 3, 1, 3, 3, 0
                ),
                listOf(
                    1, 0, 2, 3, 3, 2, 1, 1, 1, 1, 3, 0, 3, 2, 3, 1, 1, 1, 2, 1, 2, 3, 2, 0, 0, 3, 2, 3, 1, 2
                ),
                listOf(
                    0, 2, 0, 0, 0, 0, 2, 0, 0, 3, 1, 3, 0, 0, 0, 3, 0, 1, 3, 2, 1, 2, 1, 0, 1, 1, 3, 2, 3, 2
                ),
                listOf(
                    2, 2, 2, 3, 1, 1, 2, 1, 0, 2, 1, 2, 2, 3, 2, 2, 2, 1, 3, 2, 3, 3, 0, 3, 1, 2, 1, 3, 2, 1
                ),
                listOf(
                    1, 1, 0, 0, 3, 1, 2, 1, 3, 3, 3, 1, 2, 2, 1, 1, 3, 0, 1, 1, 3, 1, 0, 2, 0, 1, 0, 0, 1, 0
                ),
                listOf(
                    3, 2, 0, 3, 2, 1, 3, 0, 2, 0, 0, 3, 2, 1, 1, 0, 0, 3, 2, 1, 1, 0, 0, 2, 3, 3, 0, 0, 2, 1
                ),
                listOf(
                    0, 3, 3, 3, 2, 3, 2, 1, 0, 3, 2, 1, 2, 0, 3, 2, 3, 3, 0, 0, 3, 3, 2, 1, 3, 3, 0, 1, 0, 0
                ),
                listOf(
                    0, 1, 1, 3, 3, 3, 3, 3, 3, 1, 1, 1, 2, 3, 1, 2, 0, 1, 3, 2, 1, 1, 2, 1, 3, 3, 3, 0, 2, 3
                ),
                listOf(
                    3, 0, 0, 2, 0, 0, 3, 3, 3, 3, 1, 3, 2, 3, 2, 0, 0, 2, 3, 1, 2, 2, 2, 1, 0, 1, 2, 0, 1, 1
                ),
                listOf(
                    2, 0, 3, 3, 3, 3, 2, 0, 1, 3, 3, 0, 0, 2, 1, 2, 0, 0, 2, 3, 3, 1, 2, 0, 0, 1, 3, 3, 0, 3
                ),
                listOf(
                    0, 2, 2, 2, 1, 1, 1, 0, 3, 2, 2, 0, 1, 3, 2, 0, 2, 2, 0, 3, 0, 3, 3, 2, 3, 2, 0, 1, 2, 0
                ),
                listOf(
                    0, 2, 2, 3, 3, 0, 1, 0, 2, 2, 0, 3, 0, 3, 2, 3, 1, 0, 2, 1, 2, 2, 2, 2, 2, 3, 0, 3, 1, 0
                ),
                listOf(
                    2, 2, 0, 2, 0, 3, 3, 0, 0, 0, 3, 1, 1, 2, 2, 1, 0, 2, 1, 2, 1, 3, 2, 1, 0, 1, 2, 1, 0, 2
                ),
                listOf(
                    1, 3, 2, 3, 1, 3, 0, 2, 0, 3, 0, 3, 2, 2, 2, 3, 0, 0, 1, 1, 2, 0, 1, 0, 1, 2, 3, 3, 3, 2
                ),
                listOf(
                    1, 3, 0, 0, 1, 3, 3, 2, 2, 2, 1, 2, 0, 3, 3, 1, 0, 0, 3, 3, 2, 1, 2, 3, 0, 2, 1, 2, 1, 1
                ),
                listOf(
                    1, 2, 0, 3, 1, 3, 3, 0, 0, 2, 0, 3, 0, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 2, 0, 0, 0, 0
                ),
                listOf(
                    2, 3, 0, 0, 1, 0, 3, 2, 1, 2, 0, 2, 1, 3, 0, 0, 1, 1, 2, 3, 3, 1, 2, 3, 3, 3, 2, 3, 3, 1
                ),
                listOf(
                    3, 3, 2, 0, 1, 0, 1, 0, 1, 3, 2, 3, 3, 3, 2, 1, 3, 0, 1, 2, 3, 0, 3, 2, 0, 1, 1, 0, 1, 1
                ),
                listOf(
                    2, 1, 0, 1, 2, 2, 1, 1, 2, 0, 2, 0, 0, 3, 3, 2, 3, 0, 1, 1, 0, 0, 2, 2, 2, 3, 3, 2, 1, 3
                ),
                listOf(
                    1, 3, 3, 0, 0, 2, 0, 0, 0, 0, 1, 0, 2, 1, 2, 0, 0, 3, 0, 3, 1, 1, 2, 2, 0, 0, 1, 0, 3, 2
                )
            ), listOf(
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 25),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk50(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_011", "Grandmaster", 30, 50, listOf(
                listOf(
                    2, 1, 0, 2, 3, 3, 0, 3, 2, 1, 1, 0, 2, 3, 2, 2, 0, 1, 2, 2, 2, 0, 3, 3, 2, 0, 2, 0, 3, 2
                ),
                listOf(
                    3, 0, 2, 0, 3, 0, 3, 2, 1, 3, 3, 2, 1, 3, 0, 2, 2, 2, 3, 0, 0, 0, 2, 3, 2, 2, 1, 1, 2, 3
                ),
                listOf(
                    3, 1, 0, 0, 2, 3, 2, 1, 3, 0, 2, 1, 2, 3, 3, 3, 1, 1, 2, 2, 3, 1, 0, 2, 2, 0, 0, 3, 0, 3
                ),
                listOf(
                    0, 1, 0, 3, 2, 0, 1, 0, 1, 3, 2, 1, 1, 0, 2, 2, 2, 2, 2, 2, 3, 2, 3, 1, 2, 0, 1, 2, 2, 3
                ),
                listOf(
                    0, 3, 3, 3, 3, 1, 2, 1, 0, 3, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 3, 0, 3, 1, 1, 2
                ),
                listOf(
                    0, 2, 2, 2, 3, 2, 3, 1, 3, 3, 0, 2, 0, 0, 1, 2, 2, 0, 0, 3, 3, 1, 2, 1, 2, 3, 2, 3, 2, 3
                ),
                listOf(
                    2, 2, 1, 0, 3, 3, 2, 2, 0, 1, 3, 2, 3, 3, 1, 3, 1, 3, 2, 0, 3, 1, 3, 1, 1, 0, 1, 0, 2, 1
                ),
                listOf(
                    2, 3, 2, 1, 2, 3, 1, 0, 0, 0, 3, 2, 2, 2, 1, 0, 1, 3, 1, 1, 0, 3, 0, 1, 2, 3, 3, 2, 1, 2
                ),
                listOf(
                    0, 2, 0, 3, 0, 2, 3, 3, 2, 1, 1, 0, 0, 0, 3, 1, 1, 2, 3, 3, 2, 0, 2, 3, 2, 0, 1, 3, 3, 1
                ),
                listOf(
                    1, 2, 3, 1, 2, 0, 2, 3, 0, 0, 2, 2, 0, 3, 2, 1, 3, 2, 3, 2, 3, 0, 0, 3, 0, 3, 3, 3, 3, 3
                ),
                listOf(
                    1, 3, 2, 0, 1, 2, 3, 2, 3, 2, 2, 2, 2, 1, 3, 0, 1, 3, 1, 2, 1, 2, 0, 1, 3, 3, 1, 0, 1, 0
                ),
                listOf(
                    1, 0, 2, 0, 0, 3, 2, 3, 1, 0, 3, 2, 2, 2, 3, 1, 0, 2, 1, 2, 0, 2, 1, 1, 0, 0, 2, 2, 0, 1
                ),
                listOf(
                    2, 3, 1, 1, 1, 2, 3, 1, 0, 0, 2, 2, 2, 3, 1, 1, 0, 2, 2, 0, 2, 1, 1, 2, 2, 3, 1, 2, 3, 1
                ),
                listOf(
                    3, 3, 3, 3, 3, 2, 3, 0, 1, 2, 2, 2, 1, 0, 3, 2, 0, 1, 1, 1, 3, 0, 1, 1, 1, 0, 2, 2, 1, 3
                ),
                listOf(
                    3, 0, 0, 1, 1, 0, 2, 0, 2, 2, 0, 2, 1, 1, 2, 1, 2, 0, 2, 1, 1, 0, 3, 1, 0, 2, 2, 3, 1, 2
                ),
                listOf(
                    3, 0, 3, 2, 3, 3, 1, 3, 2, 0, 2, 0, 3, 3, 3, 1, 2, 0, 3, 3, 0, 3, 0, 0, 2, 0, 2, 3, 3, 1
                ),
                listOf(
                    1, 3, 1, 1, 1, 3, 3, 2, 2, 3, 0, 3, 1, 1, 0, 2, 2, 3, 3, 0, 1, 3, 1, 0, 2, 3, 0, 2, 3, 3
                ),
                listOf(
                    3, 3, 3, 0, 2, 0, 2, 0, 0, 3, 0, 1, 1, 2, 1, 3, 0, 3, 3, 1, 3, 2, 1, 1, 1, 3, 2, 0, 3, 2
                ),
                listOf(
                    3, 2, 1, 2, 0, 3, 2, 1, 3, 3, 3, 3, 0, 1, 3, 3, 2, 1, 1, 2, 0, 0, 2, 2, 2, 1, 1, 0, 1, 0
                ),
                listOf(
                    3, 2, 3, 1, 3, 0, 1, 0, 2, 0, 3, 3, 2, 1, 3, 1, 1, 2, 0, 0, 3, 1, 0, 0, 0, 2, 1, 2, 1, 0
                ),
                listOf(
                    3, 1, 0, 3, 2, 1, 1, 3, 1, 1, 1, 1, 3, 2, 3, 0, 0, 1, 0, 1, 1, 1, 2, 3, 3, 1, 2, 3, 0, 2
                ),
                listOf(
                    1, 0, 3, 1, 3, 0, 2, 1, 1, 0, 3, 1, 1, 2, 2, 2, 3, 0, 1, 2, 3, 1, 1, 2, 0, 1, 0, 3, 1, 2
                ),
                listOf(
                    1, 3, 1, 0, 2, 3, 1, 1, 0, 0, 3, 2, 2, 3, 0, 1, 0, 2, 2, 3, 2, 3, 2, 1, 2, 2, 2, 2, 0, 1
                ),
                listOf(
                    2, 2, 0, 3, 1, 2, 0, 0, 2, 2, 1, 1, 3, 3, 3, 3, 1, 1, 3, 2, 3, 0, 2, 3, 3, 2, 1, 3, 0, 1
                ),
                listOf(
                    3, 3, 2, 0, 1, 2, 0, 3, 3, 3, 1, 3, 0, 0, 3, 3, 2, 1, 0, 0, 0, 3, 2, 2, 2, 0, 3, 2, 1, 2
                ),
                listOf(
                    1, 3, 2, 1, 0, 2, 0, 2, 0, 0, 2, 3, 2, 0, 1, 2, 3, 0, 1, 3, 1, 2, 1, 1, 1, 1, 1, 2, 2, 3
                ),
                listOf(
                    3, 1, 3, 0, 0, 1, 0, 0, 0, 0, 2, 2, 1, 2, 1, 1, 2, 3, 1, 3, 0, 2, 3, 3, 0, 3, 2, 1, 2, 2
                ),
                listOf(
                    1, 3, 2, 2, 3, 3, 0, 2, 0, 3, 0, 0, 2, 2, 1, 3, 3, 3, 1, 1, 0, 2, 1, 3, 2, 3, 2, 2, 2, 3
                ),
                listOf(
                    0, 1, 0, 0, 0, 3, 3, 3, 1, 1, 2, 3, 3, 2, 3, 3, 2, 3, 2, 3, 1, 3, 1, 2, 2, 1, 0, 2, 0, 3
                ),
                listOf(
                    1, 3, 2, 0, 3, 3, 1, 0, 2, 1, 1, 3, 0, 1, 0, 2, 2, 1, 2, 3, 3, 2, 0, 2, 3, 2, 3, 2, 1, 0
                ),
                listOf(
                    2, 1, 2, 0, 2, 3, 2, 3, 2, 2, 0, 1, 1, 3, 2, 1, 0, 0, 3, 0, 0, 3, 2, 0, 1, 0, 1, 1, 2, 2
                ),
                listOf(
                    3, 2, 2, 0, 3, 3, 0, 3, 0, 2, 0, 1, 0, 0, 0, 1, 2, 1, 3, 2, 1, 2, 2, 2, 3, 3, 1, 2, 3, 3
                ),
                listOf(
                    2, 2, 0, 2, 2, 3, 3, 0, 1, 3, 3, 3, 0, 1, 0, 3, 3, 3, 2, 2, 1, 0, 0, 3, 0, 3, 0, 2, 0, 0
                ),
                listOf(
                    3, 2, 3, 3, 2, 1, 2, 2, 0, 2, 0, 3, 0, 1, 2, 3, 0, 0, 2, 3, 3, 1, 3, 0, 2, 2, 1, 1, 1, 1
                ),
                listOf(
                    0, 0, 3, 2, 1, 3, 1, 0, 0, 0, 1, 3, 3, 1, 2, 0, 3, 3, 3, 2, 3, 1, 1, 3, 0, 1, 2, 3, 3, 3
                ),
                listOf(
                    0, 2, 3, 3, 2, 0, 0, 1, 2, 0, 0, 1, 3, 0, 1, 2, 2, 3, 2, 0, 1, 0, 1, 1, 2, 2, 3, 3, 1, 1
                ),
                listOf(
                    0, 3, 3, 0, 0, 3, 3, 0, 1, 2, 3, 2, 2, 3, 2, 0, 2, 1, 2, 3, 1, 3, 3, 0, 0, 0, 0, 0, 1, 1
                ),
                listOf(
                    2, 3, 1, 1, 2, 1, 3, 1, 1, 2, 3, 0, 1, 1, 0, 0, 3, 2, 1, 1, 2, 0, 1, 2, 3, 1, 2, 2, 2, 1
                ),
                listOf(
                    0, 0, 2, 3, 3, 2, 1, 3, 0, 3, 1, 3, 1, 2, 2, 3, 0, 1, 2, 3, 2, 3, 2, 3, 2, 3, 0, 3, 0, 2
                ),
                listOf(
                    2, 1, 3, 1, 2, 2, 2, 1, 3, 3, 1, 1, 2, 2, 3, 2, 0, 3, 3, 3, 1, 3, 1, 0, 2, 3, 3, 0, 0, 3
                ),
                listOf(
                    1, 0, 3, 0, 0, 3, 2, 3, 2, 3, 2, 0, 2, 2, 2, 1, 2, 0, 2, 3, 2, 2, 2, 2, 0, 2, 3, 1, 2, 0
                ),
                listOf(
                    0, 1, 3, 1, 3, 1, 2, 1, 0, 3, 2, 0, 0, 3, 3, 2, 2, 2, 3, 1, 1, 1, 2, 2, 0, 1, 0, 2, 1, 3
                ),
                listOf(
                    3, 2, 2, 1, 0, 3, 3, 2, 2, 1, 3, 2, 1, 2, 3, 0, 3, 2, 3, 1, 3, 0, 0, 0, 2, 1, 2, 1, 0, 2
                ),
                listOf(
                    2, 2, 3, 1, 2, 2, 0, 2, 0, 3, 0, 2, 1, 3, 0, 3, 0, 0, 2, 1, 3, 0, 3, 0, 2, 3, 0, 2, 1, 2
                ),
                listOf(
                    0, 2, 1, 3, 3, 0, 3, 1, 3, 1, 1, 2, 2, 0, 2, 2, 1, 2, 1, 2, 3, 2, 2, 0, 3, 3, 3, 0, 3, 0
                ),
                listOf(
                    3, 2, 1, 3, 1, 2, 0, 3, 2, 1, 2, 2, 0, 1, 3, 0, 2, 2, 2, 0, 3, 2, 0, 0, 1, 1, 2, 1, 1, 1
                ),
                listOf(
                    1, 1, 3, 1, 0, 0, 0, 2, 3, 1, 1, 2, 1, 1, 3, 3, 3, 2, 2, 3, 0, 3, 1, 1, 3, 3, 3, 2, 2, 3
                ),
                listOf(
                    0, 3, 2, 0, 2, 2, 3, 3, 2, 1, 3, 1, 3, 1, 2, 2, 1, 0, 0, 3, 0, 3, 2, 0, 1, 1, 1, 3, 1, 1
                ),
                listOf(
                    2, 2, 1, 1, 0, 1, 2, 0, 3, 0, 1, 3, 2, 1, 2, 1, 0, 1, 2, 2, 2, 2, 1, 1, 2, 1, 3, 3, 0, 1
                ),
                listOf(
                    0, 1, 3, 2, 0, 0, 1, 0, 2, 2, 2, 1, 0, 1, 0, 2, 2, 3, 3, 0, 0, 1, 0, 0, 2, 1, 2, 2, 2, 0
                )
            ), listOf(
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 25),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk51(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_012", "Grandmaster", 30, 50, listOf(
                listOf(
                    1, 0, 1, 2, 2, 0, 3, 2, 2, 2, 2, 0, 1, 0, 1, 3, 1, 2, 0, 3, 3, 2, 3, 0, 1, 3, 3, 1, 0, 2
                ),
                listOf(
                    2, 1, 0, 0, 3, 3, 1, 1, 0, 2, 0, 1, 0, 1, 3, 0, 1, 1, 3, 1, 1, 2, 2, 3, 2, 2, 0, 0, 0, 0
                ),
                listOf(
                    1, 3, 2, 1, 2, 1, 3, 3, 3, 0, 3, 1, 3, 2, 2, 2, 0, 0, 0, 3, 3, 1, 1, 0, 0, 0, 2, 2, 2, 1
                ),
                listOf(
                    0, 3, 2, 2, 1, 2, 1, 3, 2, 1, 0, 3, 1, 2, 3, 0, 3, 0, 1, 2, 3, 0, 0, 1, 3, 0, 1, 0, 1, 3
                ),
                listOf(
                    0, 2, 1, 3, 2, 0, 1, 3, 0, 2, 1, 2, 1, 0, 1, 1, 1, 3, 1, 2, 0, 3, 3, 1, 0, 1, 0, 1, 0, 0
                ),
                listOf(
                    1, 3, 0, 1, 3, 0, 3, 0, 3, 3, 2, 1, 1, 3, 1, 0, 0, 1, 1, 3, 0, 2, 1, 0, 1, 0, 2, 3, 0, 1
                ),
                listOf(
                    1, 1, 0, 3, 3, 0, 2, 1, 2, 3, 3, 1, 0, 0, 3, 2, 2, 0, 1, 1, 1, 3, 3, 3, 1, 0, 2, 2, 1, 1
                ),
                listOf(
                    2, 2, 1, 2, 0, 1, 1, 3, 1, 3, 1, 1, 1, 2, 0, 0, 3, 1, 1, 0, 0, 3, 2, 2, 1, 1, 1, 1, 3, 1
                ),
                listOf(
                    2, 1, 0, 2, 1, 2, 1, 1, 0, 1, 1, 3, 1, 0, 1, 1, 3, 3, 1, 3, 1, 1, 1, 1, 1, 2, 0, 3, 1, 0
                ),
                listOf(
                    2, 1, 1, 0, 3, 0, 3, 2, 3, 0, 0, 3, 1, 2, 2, 2, 1, 1, 3, 2, 0, 1, 2, 1, 1, 1, 2, 1, 2, 3
                ),
                listOf(
                    2, 3, 3, 0, 1, 2, 2, 0, 3, 2, 2, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 2, 0, 3, 2, 3, 2, 1, 2, 1
                ),
                listOf(
                    2, 1, 0, 1, 2, 2, 0, 1, 0, 1, 2, 1, 0, 3, 2, 0, 1, 1, 1, 1, 0, 3, 0, 0, 2, 3, 2, 1, 3, 3
                ),
                listOf(
                    2, 0, 3, 2, 0, 1, 2, 0, 2, 1, 0, 0, 3, 2, 2, 0, 0, 2, 0, 1, 3, 1, 3, 3, 1, 2, 1, 1, 2, 1
                ),
                listOf(
                    0, 1, 2, 3, 1, 2, 0, 2, 0, 3, 1, 3, 0, 0, 3, 1, 3, 3, 3, 1, 1, 2, 1, 2, 1, 2, 2, 3, 0, 3
                ),
                listOf(
                    1, 2, 2, 0, 1, 1, 0, 3, 1, 2, 0, 2, 2, 3, 3, 2, 3, 0, 0, 0, 0, 0, 1, 3, 1, 2, 1, 2, 2, 1
                ),
                listOf(
                    0, 3, 3, 3, 2, 1, 0, 0, 1, 2, 3, 0, 3, 2, 3, 1, 1, 0, 2, 1, 3, 3, 2, 3, 0, 3, 1, 3, 0, 1
                ),
                listOf(
                    1, 3, 2, 2, 3, 1, 0, 0, 0, 3, 1, 1, 0, 0, 0, 3, 3, 1, 1, 3, 1, 3, 3, 1, 0, 0, 1, 1, 2, 1
                ),
                listOf(
                    2, 2, 2, 0, 2, 1, 1, 2, 1, 2, 0, 1, 2, 1, 3, 3, 1, 1, 3, 0, 3, 0, 2, 2, 0, 1, 1, 2, 2, 3
                ),
                listOf(
                    3, 3, 0, 3, 2, 0, 1, 1, 2, 1, 1, 2, 3, 0, 0, 1, 0, 0, 0, 1, 0, 3, 0, 1, 2, 2, 0, 3, 3, 3
                ),
                listOf(
                    0, 0, 3, 0, 1, 3, 2, 1, 2, 0, 0, 3, 3, 0, 2, 0, 3, 0, 1, 1, 3, 3, 1, 0, 2, 1, 0, 3, 1, 0
                ),
                listOf(
                    0, 1, 3, 1, 1, 0, 3, 1, 1, 0, 2, 2, 1, 2, 0, 2, 1, 2, 1, 2, 2, 1, 0, 1, 1, 0, 2, 0, 0, 1
                ),
                listOf(
                    2, 1, 2, 3, 2, 3, 3, 0, 0, 3, 2, 1, 3, 2, 1, 0, 1, 3, 1, 2, 1, 2, 2, 3, 3, 2, 0, 0, 1, 1
                ),
                listOf(
                    1, 2, 3, 2, 2, 3, 3, 1, 0, 3, 3, 2, 0, 3, 1, 3, 0, 0, 0, 3, 2, 1, 3, 1, 0, 0, 3, 1, 2, 0
                ),
                listOf(
                    0, 1, 1, 0, 1, 1, 3, 3, 0, 3, 3, 2, 3, 3, 3, 1, 2, 1, 2, 0, 0, 1, 1, 2, 3, 0, 2, 0, 0, 0
                ),
                listOf(
                    0, 0, 2, 0, 3, 2, 0, 2, 2, 1, 3, 0, 2, 0, 2, 3, 1, 1, 1, 2, 0, 1, 3, 0, 0, 0, 2, 2, 3, 3
                ),
                listOf(
                    3, 2, 0, 0, 3, 0, 2, 1, 0, 0, 1, 3, 0, 1, 2, 2, 0, 0, 3, 0, 3, 2, 2, 2, 1, 2, 0, 3, 2, 3
                ),
                listOf(
                    3, 3, 2, 0, 3, 2, 1, 2, 2, 1, 0, 0, 0, 3, 1, 1, 3, 2, 0, 2, 2, 3, 1, 0, 3, 0, 2, 3, 0, 2
                ),
                listOf(
                    0, 3, 3, 2, 3, 1, 0, 0, 1, 3, 0, 2, 2, 1, 1, 3, 2, 3, 1, 0, 3, 0, 0, 1, 2, 0, 3, 0, 2, 0
                ),
                listOf(
                    2, 2, 1, 0, 2, 1, 2, 1, 1, 0, 1, 3, 2, 0, 2, 1, 2, 3, 3, 0, 0, 3, 0, 1, 0, 1, 2, 3, 3, 2
                ),
                listOf(
                    3, 0, 2, 3, 2, 1, 2, 0, 1, 2, 0, 2, 3, 1, 1, 0, 3, 1, 3, 0, 3, 1, 3, 2, 1, 1, 0, 1, 3, 2
                ),
                listOf(
                    3, 2, 1, 2, 2, 0, 1, 1, 1, 2, 0, 2, 2, 1, 3, 2, 1, 1, 1, 3, 3, 0, 1, 3, 2, 0, 2, 1, 1, 1
                ),
                listOf(
                    0, 0, 0, 1, 2, 1, 1, 0, 3, 0, 0, 3, 0, 3, 3, 3, 0, 2, 2, 1, 1, 1, 1, 0, 2, 1, 2, 1, 2, 1
                ),
                listOf(
                    3, 2, 3, 0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 0, 1, 2, 1, 2, 1, 2, 1, 1, 1, 3, 1, 1, 2, 0, 2, 1
                ),
                listOf(
                    2, 2, 1, 2, 1, 2, 0, 0, 2, 0, 2, 1, 0, 2, 3, 2, 0, 2, 1, 2, 3, 0, 1, 0, 1, 1, 0, 3, 2, 0
                ),
                listOf(
                    3, 2, 2, 2, 1, 2, 0, 0, 0, 1, 0, 1, 3, 0, 0, 1, 0, 2, 2, 0, 0, 2, 0, 0, 2, 3, 0, 2, 0, 0
                ),
                listOf(
                    2, 2, 0, 0, 1, 3, 2, 1, 3, 3, 3, 2, 3, 0, 2, 0, 2, 1, 1, 0, 0, 1, 1, 2, 1, 3, 1, 3, 0, 0
                ),
                listOf(
                    2, 2, 3, 2, 3, 1, 1, 1, 1, 3, 1, 0, 1, 3, 1, 0, 2, 3, 1, 3, 0, 3, 2, 0, 0, 3, 2, 1, 0, 0
                ),
                listOf(
                    0, 3, 1, 3, 0, 2, 1, 2, 0, 3, 3, 3, 1, 2, 0, 2, 0, 0, 2, 1, 3, 0, 1, 2, 3, 3, 2, 2, 1, 2
                ),
                listOf(
                    0, 3, 1, 1, 1, 0, 2, 0, 1, 2, 1, 2, 3, 0, 1, 0, 2, 0, 3, 2, 1, 3, 3, 0, 0, 0, 1, 0, 2, 1
                ),
                listOf(
                    0, 0, 1, 2, 1, 0, 0, 1, 1, 2, 0, 3, 1, 3, 0, 2, 3, 1, 0, 1, 1, 3, 2, 0, 0, 0, 0, 1, 2, 2
                ),
                listOf(
                    1, 1, 3, 0, 0, 2, 1, 3, 0, 0, 2, 2, 2, 3, 0, 0, 0, 3, 0, 3, 1, 3, 2, 2, 1, 0, 2, 1, 1, 2
                ),
                listOf(
                    2, 0, 0, 1, 1, 0, 1, 0, 2, 3, 1, 0, 3, 1, 3, 1, 0, 2, 3, 2, 1, 1, 3, 2, 0, 3, 1, 2, 3, 0
                ),
                listOf(
                    1, 0, 3, 1, 3, 0, 1, 3, 1, 2, 1, 2, 0, 1, 0, 3, 0, 0, 3, 2, 3, 2, 2, 2, 2, 0, 1, 3, 0, 1
                ),
                listOf(
                    1, 0, 0, 1, 2, 1, 2, 0, 2, 3, 0, 0, 3, 2, 0, 1, 0, 0, 3, 0, 2, 1, 1, 1, 3, 0, 3, 1, 0, 1
                ),
                listOf(
                    3, 1, 0, 3, 2, 0, 0, 3, 2, 0, 2, 0, 0, 0, 2, 3, 1, 3, 0, 1, 2, 2, 2, 3, 1, 1, 0, 3, 1, 0
                ),
                listOf(
                    2, 3, 0, 1, 2, 1, 0, 0, 3, 0, 2, 2, 3, 1, 2, 0, 0, 3, 2, 3, 2, 0, 1, 2, 1, 2, 2, 3, 1, 0
                ),
                listOf(
                    0, 0, 2, 2, 2, 1, 3, 1, 0, 0, 1, 1, 1, 3, 0, 3, 1, 0, 3, 2, 2, 2, 3, 0, 1, 1, 3, 1, 0, 3
                ),
                listOf(
                    0, 3, 1, 3, 0, 1, 0, 2, 3, 0, 0, 0, 3, 2, 2, 3, 3, 1, 2, 0, 0, 3, 0, 0, 0, 2, 1, 0, 3, 0
                ),
                listOf(
                    0, 0, 1, 1, 3, 0, 2, 2, 2, 1, 0, 1, 1, 3, 0, 1, 3, 0, 2, 2, 2, 1, 1, 1, 2, 2, 3, 3, 3, 1
                ),
                listOf(
                    0, 2, 0, 0, 1, 1, 1, 0, 3, 1, 2, 0, 1, 0, 0, 0, 3, 2, 1, 0, 1, 3, 0, 3, 0, 1, 2, 0, 0, 2
                )
            ), listOf(
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk52(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_013", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 2, 1, 2, 1, 1, 0, 2, 3, 1, 0, 0, 2, 3, 2, 1, 1, 1, 3, 1, 0, 0, 1, 1, 0, 3, 1, 0, 2, 0
                ),
                listOf(
                    0, 0, 0, 2, 2, 1, 2, 1, 1, 3, 1, 2, 1, 1, 1, 1, 3, 3, 3, 2, 0, 1, 0, 1, 3, 2, 2, 3, 3, 2
                ),
                listOf(
                    1, 3, 1, 0, 2, 0, 0, 0, 3, 0, 2, 0, 3, 0, 1, 1, 0, 2, 1, 2, 2, 2, 2, 2, 2, 0, 0, 3, 2, 0
                ),
                listOf(
                    2, 1, 2, 2, 1, 3, 2, 3, 3, 3, 2, 3, 3, 0, 0, 1, 3, 1, 1, 2, 1, 2, 3, 1, 3, 3, 0, 0, 2, 2
                ),
                listOf(
                    0, 3, 2, 3, 1, 2, 3, 2, 2, 3, 1, 2, 2, 1, 2, 3, 3, 1, 2, 3, 0, 2, 0, 2, 3, 2, 2, 3, 1, 0
                ),
                listOf(
                    1, 3, 2, 1, 2, 2, 0, 2, 2, 1, 1, 0, 0, 0, 0, 3, 0, 2, 3, 0, 2, 1, 2, 2, 3, 2, 0, 3, 0, 1
                ),
                listOf(
                    2, 0, 1, 0, 0, 1, 3, 0, 2, 0, 2, 0, 1, 1, 1, 2, 3, 3, 3, 2, 1, 0, 2, 0, 1, 3, 2, 0, 1, 2
                ),
                listOf(
                    1, 2, 1, 2, 0, 3, 2, 2, 2, 2, 2, 0, 3, 1, 0, 1, 1, 2, 2, 2, 0, 1, 3, 3, 3, 0, 1, 3, 1, 3
                ),
                listOf(
                    0, 2, 0, 0, 3, 2, 0, 2, 1, 0, 2, 3, 2, 0, 0, 0, 3, 3, 1, 0, 1, 0, 0, 2, 0, 2, 2, 2, 1, 1
                ),
                listOf(
                    3, 2, 0, 0, 1, 3, 2, 0, 0, 2, 3, 1, 0, 3, 1, 1, 1, 2, 1, 0, 2, 1, 1, 2, 0, 1, 0, 2, 0, 0
                ),
                listOf(
                    2, 2, 0, 2, 1, 2, 2, 0, 2, 1, 0, 0, 2, 2, 0, 2, 2, 3, 2, 2, 1, 1, 2, 0, 3, 1, 3, 2, 2, 3
                ),
                listOf(
                    0, 0, 1, 3, 3, 1, 3, 0, 0, 0, 3, 1, 3, 0, 2, 1, 2, 1, 3, 1, 2, 1, 1, 3, 1, 0, 0, 2, 3, 1
                ),
                listOf(
                    3, 1, 0, 3, 0, 3, 1, 3, 0, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 0, 2, 0, 3, 3, 2, 3, 3, 1, 2, 1
                ),
                listOf(
                    2, 1, 0, 1, 1, 0, 2, 0, 1, 3, 2, 0, 3, 3, 2, 0, 1, 2, 2, 3, 2, 1, 1, 0, 2, 1, 3, 2, 1, 3
                ),
                listOf(
                    1, 1, 3, 1, 3, 1, 1, 1, 0, 3, 0, 0, 0, 3, 2, 1, 1, 2, 3, 0, 1, 2, 0, 3, 2, 1, 3, 1, 1, 2
                ),
                listOf(
                    3, 3, 2, 2, 3, 1, 1, 2, 2, 3, 0, 2, 3, 3, 3, 3, 1, 0, 3, 0, 2, 3, 3, 3, 2, 2, 3, 2, 2, 2
                ),
                listOf(
                    0, 2, 2, 0, 3, 2, 3, 2, 1, 2, 0, 2, 3, 3, 1, 0, 2, 3, 2, 3, 0, 3, 0, 1, 0, 0, 3, 1, 3, 1
                ),
                listOf(
                    3, 2, 1, 1, 2, 3, 3, 1, 0, 2, 2, 2, 0, 0, 3, 2, 1, 1, 0, 0, 2, 1, 1, 3, 3, 1, 3, 0, 1, 3
                ),
                listOf(
                    0, 3, 1, 1, 3, 2, 1, 2, 3, 0, 0, 0, 1, 1, 0, 0, 0, 3, 0, 2, 1, 2, 1, 0, 2, 1, 0, 3, 2, 2
                ),
                listOf(
                    3, 0, 0, 0, 0, 3, 1, 0, 1, 2, 1, 0, 3, 2, 3, 0, 1, 1, 1, 1, 2, 3, 1, 2, 0, 2, 0, 1, 1, 2
                ),
                listOf(
                    1, 0, 3, 2, 1, 1, 2, 1, 1, 1, 1, 2, 2, 0, 1, 1, 3, 0, 3, 3, 0, 3, 3, 0, 0, 2, 3, 1, 1, 1
                ),
                listOf(
                    0, 2, 2, 1, 0, 3, 2, 1, 0, 0, 1, 2, 1, 2, 1, 1, 2, 3, 0, 2, 3, 2, 0, 1, 3, 1, 2, 1, 2, 3
                ),
                listOf(
                    0, 3, 2, 2, 0, 2, 2, 3, 2, 0, 1, 3, 3, 0, 0, 2, 3, 3, 3, 2, 1, 0, 3, 1, 2, 0, 1, 2, 0, 2
                ),
                listOf(
                    0, 1, 0, 0, 3, 1, 0, 2, 0, 0, 3, 0, 0, 2, 3, 0, 1, 3, 1, 2, 3, 0, 1, 1, 0, 2, 1, 0, 0, 1
                ),
                listOf(
                    2, 0, 0, 3, 1, 3, 2, 2, 2, 3, 2, 0, 2, 3, 1, 0, 2, 3, 0, 2, 0, 2, 1, 0, 3, 3, 1, 1, 3, 3
                ),
                listOf(
                    3, 0, 0, 2, 2, 0, 0, 2, 0, 1, 0, 2, 1, 1, 3, 1, 0, 3, 0, 1, 3, 0, 0, 2, 1, 0, 1, 2, 2, 0
                ),
                listOf(
                    1, 2, 3, 0, 0, 0, 1, 0, 0, 3, 3, 0, 0, 2, 3, 1, 3, 3, 1, 2, 3, 1, 1, 2, 3, 0, 0, 3, 2, 2
                ),
                listOf(
                    1, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 1, 0, 2, 2, 3, 2, 1, 0, 3, 3, 1, 3, 0, 0, 1, 3, 2, 2, 3
                ),
                listOf(
                    0, 2, 0, 3, 3, 0, 1, 1, 1, 1, 2, 3, 1, 0, 2, 0, 0, 1, 3, 2, 2, 0, 3, 0, 1, 1, 0, 1, 3, 2
                ),
                listOf(
                    0, 2, 3, 1, 0, 3, 2, 2, 1, 2, 3, 3, 2, 0, 0, 1, 0, 0, 1, 1, 2, 1, 0, 1, 3, 3, 0, 3, 1, 0
                ),
                listOf(
                    1, 3, 1, 3, 0, 1, 0, 1, 0, 3, 3, 1, 0, 3, 1, 0, 1, 3, 1, 3, 1, 0, 1, 1, 0, 2, 3, 2, 2, 0
                ),
                listOf(
                    0, 0, 0, 2, 1, 0, 1, 3, 0, 1, 2, 2, 1, 0, 3, 0, 0, 1, 1, 2, 1, 0, 0, 1, 1, 3, 3, 0, 1, 2
                ),
                listOf(
                    2, 0, 1, 3, 0, 0, 1, 2, 1, 0, 0, 2, 2, 2, 1, 3, 3, 3, 2, 2, 1, 1, 3, 2, 0, 1, 0, 3, 2, 2
                ),
                listOf(
                    2, 2, 1, 3, 2, 0, 0, 2, 2, 0, 1, 2, 3, 3, 1, 2, 0, 1, 2, 1, 0, 2, 3, 0, 3, 3, 0, 3, 1, 1
                ),
                listOf(
                    3, 2, 2, 0, 0, 1, 3, 3, 1, 0, 0, 0, 3, 3, 1, 1, 0, 2, 2, 3, 3, 0, 1, 3, 2, 2, 0, 0, 0, 2
                ),
                listOf(
                    1, 3, 1, 3, 2, 2, 0, 2, 3, 1, 3, 1, 0, 2, 1, 0, 3, 0, 1, 2, 1, 0, 2, 0, 3, 0, 3, 2, 2, 1
                ),
                listOf(
                    1, 2, 2, 2, 2, 1, 2, 0, 0, 0, 1, 2, 2, 2, 1, 0, 3, 2, 2, 3, 0, 1, 1, 1, 0, 2, 0, 0, 3, 1
                ),
                listOf(
                    1, 1, 2, 3, 2, 2, 2, 3, 0, 1, 1, 3, 3, 1, 1, 1, 3, 1, 3, 3, 0, 1, 3, 0, 2, 1, 3, 1, 2, 3
                ),
                listOf(
                    0, 3, 1, 2, 1, 1, 1, 0, 0, 3, 2, 3, 2, 1, 1, 0, 3, 1, 3, 0, 1, 3, 2, 0, 3, 1, 0, 2, 3, 0
                ),
                listOf(
                    0, 3, 3, 3, 1, 3, 0, 1, 2, 3, 2, 2, 1, 2, 2, 2, 0, 1, 1, 0, 1, 3, 2, 2, 0, 2, 0, 0, 3, 1
                ),
                listOf(
                    1, 1, 2, 2, 2, 3, 0, 1, 2, 0, 2, 3, 1, 0, 2, 1, 3, 0, 0, 0, 2, 3, 3, 1, 3, 0, 1, 3, 3, 1
                ),
                listOf(
                    2, 2, 3, 1, 3, 2, 2, 2, 3, 1, 3, 1, 2, 3, 1, 1, 2, 2, 0, 0, 0, 2, 2, 1, 3, 2, 1, 2, 2, 1
                ),
                listOf(
                    3, 3, 0, 3, 0, 2, 3, 1, 3, 3, 3, 1, 2, 3, 1, 2, 3, 2, 1, 2, 3, 3, 0, 2, 2, 0, 2, 0, 0, 0
                ),
                listOf(
                    1, 0, 2, 1, 0, 2, 0, 2, 3, 0, 0, 3, 3, 0, 2, 3, 0, 2, 3, 2, 1, 0, 0, 0, 2, 0, 3, 0, 0, 2
                ),
                listOf(
                    0, 3, 1, 0, 0, 0, 0, 0, 1, 2, 2, 1, 2, 0, 0, 1, 3, 0, 1, 1, 2, 0, 3, 3, 1, 1, 3, 3, 2, 2
                ),
                listOf(
                    1, 0, 2, 0, 3, 1, 1, 2, 1, 3, 2, 1, 2, 0, 0, 0, 3, 2, 1, 0, 0, 1, 1, 3, 0, 2, 2, 3, 2, 0
                ),
                listOf(
                    2, 2, 2, 0, 1, 2, 1, 3, 0, 0, 0, 3, 1, 0, 2, 2, 0, 1, 3, 3, 1, 3, 1, 0, 3, 2, 2, 3, 2, 3
                ),
                listOf(
                    3, 1, 0, 2, 3, 3, 2, 0, 1, 0, 1, 0, 3, 0, 2, 3, 2, 2, 3, 3, 0, 0, 1, 0, 1, 3, 0, 0, 1, 0
                ),
                listOf(
                    0, 0, 2, 1, 3, 1, 2, 0, 3, 1, 0, 2, 2, 3, 3, 3, 0, 2, 1, 0, 3, 0, 0, 1, 3, 1, 1, 0, 3, 1
                ),
                listOf(
                    3, 3, 1, 2, 2, 0, 1, 1, 0, 0, 0, 2, 3, 2, 2, 1, 1, 3, 1, 1, 1, 1, 0, 3, 3, 0, 1, 0, 2, 2
                )
            ), listOf(
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 25),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 25),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk53(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_014", "Grandmaster", 30, 50, listOf(
                listOf(
                    2, 1, 2, 3, 0, 1, 2, 1, 2, 3, 2, 1, 2, 0, 0, 3, 1, 1, 2, 0, 2, 3, 2, 3, 2, 0, 2, 1, 2, 1
                ),
                listOf(
                    1, 0, 0, 3, 2, 1, 3, 2, 2, 2, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 3, 2, 0, 0, 1, 0, 2, 0
                ),
                listOf(
                    3, 0, 3, 1, 2, 0, 0, 0, 3, 2, 3, 0, 1, 1, 2, 0, 3, 3, 2, 0, 1, 3, 2, 3, 1, 2, 0, 2, 1, 1
                ),
                listOf(
                    0, 2, 3, 0, 2, 3, 3, 2, 0, 2, 2, 0, 2, 1, 0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 0, 3, 1, 2, 1, 2
                ),
                listOf(
                    3, 0, 2, 2, 0, 3, 3, 3, 3, 2, 2, 3, 1, 3, 2, 0, 3, 2, 0, 0, 3, 0, 3, 1, 2, 0, 1, 0, 2, 2
                ),
                listOf(
                    3, 0, 3, 1, 1, 2, 0, 3, 0, 3, 2, 0, 0, 2, 2, 1, 1, 3, 0, 2, 0, 3, 1, 3, 0, 3, 3, 3, 1, 1
                ),
                listOf(
                    2, 0, 3, 0, 0, 2, 3, 3, 3, 1, 3, 3, 3, 3, 2, 1, 0, 3, 0, 2, 1, 2, 0, 0, 0, 0, 2, 3, 2, 1
                ),
                listOf(
                    2, 1, 0, 3, 1, 3, 3, 1, 3, 3, 1, 3, 0, 2, 2, 0, 2, 2, 2, 2, 0, 1, 2, 1, 0, 0, 3, 0, 0, 2
                ),
                listOf(
                    0, 0, 2, 2, 1, 0, 2, 2, 1, 1, 3, 1, 1, 0, 3, 0, 3, 1, 3, 2, 1, 2, 3, 2, 1, 3, 0, 0, 2, 3
                ),
                listOf(
                    1, 3, 2, 2, 2, 3, 1, 2, 2, 3, 1, 0, 2, 3, 1, 3, 3, 0, 1, 3, 3, 2, 3, 2, 3, 3, 2, 1, 3, 3
                ),
                listOf(
                    0, 3, 3, 2, 1, 1, 1, 3, 0, 2, 2, 2, 1, 0, 0, 2, 1, 3, 3, 2, 2, 3, 0, 1, 0, 2, 0, 2, 2, 1
                ),
                listOf(
                    3, 1, 2, 0, 0, 0, 2, 1, 2, 1, 0, 2, 1, 1, 1, 2, 2, 1, 2, 2, 2, 2, 0, 1, 3, 1, 3, 2, 2, 2
                ),
                listOf(
                    2, 3, 3, 0, 2, 1, 1, 1, 1, 1, 3, 1, 1, 3, 3, 3, 1, 3, 2, 0, 1, 2, 3, 0, 0, 3, 0, 0, 1, 3
                ),
                listOf(
                    3, 3, 3, 3, 2, 2, 2, 0, 3, 0, 2, 3, 3, 2, 3, 0, 1, 0, 2, 1, 2, 2, 0, 2, 2, 1, 3, 3, 0, 1
                ),
                listOf(
                    3, 0, 0, 2, 1, 3, 0, 2, 3, 3, 2, 0, 1, 2, 0, 1, 0, 3, 2, 1, 2, 3, 0, 0, 2, 1, 3, 2, 3, 1
                ),
                listOf(
                    3, 1, 2, 2, 3, 2, 0, 2, 0, 2, 0, 0, 2, 1, 0, 3, 2, 1, 0, 3, 3, 3, 0, 0, 2, 3, 0, 1, 2, 1
                ),
                listOf(
                    3, 1, 0, 3, 3, 3, 0, 2, 2, 3, 3, 3, 2, 1, 2, 3, 0, 2, 0, 1, 1, 2, 0, 1, 0, 1, 3, 2, 1, 1
                ),
                listOf(
                    0, 1, 3, 1, 2, 2, 2, 0, 2, 1, 1, 0, 3, 3, 2, 2, 1, 0, 2, 0, 1, 3, 1, 2, 3, 0, 2, 3, 2, 2
                ),
                listOf(
                    2, 0, 2, 3, 0, 1, 0, 1, 0, 3, 2, 0, 0, 2, 0, 3, 3, 3, 1, 3, 0, 1, 0, 1, 3, 2, 0, 3, 1, 2
                ),
                listOf(
                    0, 1, 0, 0, 3, 0, 2, 3, 2, 3, 1, 0, 0, 3, 1, 0, 2, 1, 3, 2, 1, 3, 2, 1, 0, 2, 3, 3, 2, 1
                ),
                listOf(
                    0, 2, 0, 3, 1, 0, 2, 3, 1, 3, 1, 2, 2, 0, 1, 3, 2, 1, 0, 3, 1, 1, 3, 1, 1, 1, 2, 0, 3, 3
                ),
                listOf(
                    2, 0, 1, 0, 3, 2, 3, 3, 2, 3, 1, 2, 3, 0, 0, 3, 1, 2, 0, 2, 0, 0, 1, 3, 2, 2, 1, 3, 3, 2
                ),
                listOf(
                    1, 0, 1, 2, 1, 1, 3, 3, 0, 3, 3, 1, 2, 0, 2, 0, 0, 3, 3, 1, 1, 1, 0, 3, 2, 1, 3, 2, 1, 1
                ),
                listOf(
                    3, 1, 2, 0, 0, 2, 3, 1, 0, 3, 0, 3, 0, 1, 3, 0, 0, 1, 3, 2, 2, 3, 0, 1, 2, 3, 1, 3, 2, 2
                ),
                listOf(
                    0, 3, 0, 3, 2, 2, 2, 1, 2, 0, 2, 2, 3, 1, 0, 3, 1, 1, 1, 2, 0, 3, 2, 0, 3, 1, 0, 0, 0, 2
                ),
                listOf(
                    3, 0, 2, 1, 2, 0, 1, 0, 1, 3, 3, 1, 2, 2, 1, 2, 1, 0, 1, 0, 3, 0, 3, 0, 1, 0, 1, 1, 2, 1
                ),
                listOf(
                    0, 3, 3, 0, 3, 2, 3, 0, 2, 3, 0, 2, 3, 2, 0, 0, 3, 1, 0, 0, 2, 2, 1, 3, 1, 0, 0, 0, 3, 0
                ),
                listOf(
                    2, 3, 0, 3, 1, 0, 3, 1, 0, 1, 2, 0, 0, 2, 3, 3, 0, 1, 2, 3, 1, 0, 3, 0, 2, 0, 0, 3, 1, 2
                ),
                listOf(
                    1, 3, 1, 1, 3, 1, 0, 3, 2, 3, 0, 1, 2, 2, 3, 0, 0, 1, 0, 3, 2, 2, 0, 1, 2, 3, 3, 3, 3, 3
                ),
                listOf(
                    1, 0, 2, 0, 3, 1, 3, 1, 1, 1, 0, 2, 3, 3, 2, 2, 0, 2, 2, 1, 2, 1, 1, 0, 1, 1, 2, 1, 2, 2
                ),
                listOf(
                    2, 2, 2, 3, 1, 0, 2, 0, 0, 3, 2, 1, 2, 1, 1, 1, 1, 1, 3, 0, 2, 3, 2, 1, 1, 0, 2, 1, 2, 0
                ),
                listOf(
                    1, 1, 2, 0, 1, 2, 0, 0, 1, 0, 3, 1, 3, 2, 3, 0, 0, 2, 1, 1, 2, 1, 1, 3, 2, 3, 1, 1, 1, 2
                ),
                listOf(
                    0, 3, 3, 1, 3, 2, 0, 3, 3, 1, 0, 1, 2, 1, 1, 2, 3, 3, 1, 0, 3, 3, 0, 2, 1, 1, 3, 2, 0, 3
                ),
                listOf(
                    1, 2, 3, 1, 1, 1, 0, 2, 3, 0, 3, 2, 0, 0, 1, 3, 3, 2, 0, 0, 2, 2, 2, 0, 3, 0, 2, 2, 3, 1
                ),
                listOf(
                    0, 0, 1, 3, 2, 0, 1, 2, 1, 1, 3, 2, 3, 1, 1, 0, 1, 2, 1, 0, 3, 2, 3, 3, 2, 3, 1, 2, 3, 0
                ),
                listOf(
                    2, 0, 0, 2, 2, 3, 1, 2, 3, 3, 1, 3, 1, 2, 2, 0, 1, 3, 3, 1, 0, 3, 2, 3, 3, 2, 0, 0, 1, 2
                ),
                listOf(
                    1, 2, 1, 0, 1, 1, 1, 0, 1, 0, 0, 3, 0, 1, 2, 2, 2, 3, 0, 3, 0, 2, 2, 2, 1, 2, 2, 2, 0, 3
                ),
                listOf(
                    3, 0, 1, 3, 0, 2, 1, 1, 2, 1, 0, 2, 1, 3, 3, 1, 2, 2, 0, 1, 3, 0, 3, 3, 2, 1, 3, 3, 0, 3
                ),
                listOf(
                    0, 3, 3, 3, 1, 2, 1, 3, 3, 2, 3, 0, 1, 2, 0, 3, 1, 2, 3, 0, 1, 3, 0, 1, 2, 2, 2, 0, 3, 1
                ),
                listOf(
                    2, 1, 2, 0, 2, 0, 3, 0, 0, 2, 2, 0, 3, 2, 2, 0, 0, 1, 1, 1, 1, 2, 1, 0, 1, 2, 2, 0, 3, 3
                ),
                listOf(
                    3, 1, 3, 3, 0, 0, 0, 0, 1, 1, 1, 3, 3, 1, 0, 2, 0, 0, 1, 2, 2, 1, 2, 3, 3, 1, 1, 0, 0, 0
                ),
                listOf(
                    3, 0, 3, 0, 3, 3, 3, 1, 1, 1, 1, 3, 3, 0, 3, 2, 2, 2, 0, 0, 1, 2, 1, 2, 1, 2, 3, 3, 3, 1
                ),
                listOf(
                    1, 1, 1, 0, 1, 1, 3, 2, 0, 2, 0, 3, 3, 3, 1, 2, 2, 1, 3, 0, 3, 0, 0, 2, 3, 3, 1, 2, 1, 1
                ),
                listOf(
                    0, 1, 1, 2, 1, 1, 3, 1, 3, 0, 1, 3, 0, 3, 1, 0, 0, 2, 0, 0, 1, 1, 2, 3, 3, 3, 2, 3, 3, 0
                ),
                listOf(
                    0, 2, 0, 1, 1, 3, 2, 2, 0, 1, 2, 3, 3, 0, 0, 3, 3, 2, 3, 0, 3, 0, 2, 1, 2, 1, 3, 2, 3, 0
                ),
                listOf(
                    2, 3, 1, 3, 2, 0, 3, 0, 0, 1, 1, 2, 2, 1, 0, 3, 2, 2, 0, 1, 0, 0, 2, 0, 1, 2, 0, 2, 2, 1
                ),
                listOf(
                    3, 0, 0, 0, 0, 2, 3, 1, 0, 0, 0, 2, 2, 1, 1, 2, 2, 1, 1, 0, 0, 1, 2, 3, 0, 1, 1, 1, 3, 1
                ),
                listOf(
                    3, 3, 2, 0, 2, 3, 2, 0, 2, 2, 2, 1, 3, 0, 2, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 2, 2, 2, 0, 1
                ),
                listOf(
                    0, 3, 1, 3, 2, 3, 1, 0, 3, 2, 1, 2, 1, 3, 2, 2, 2, 3, 0, 3, 3, 1, 1, 2, 0, 3, 1, 2, 2, 3
                ),
                listOf(
                    1, 0, 3, 3, 2, 0, 0, 3, 1, 0, 1, 1, 1, 3, 0, 0, 1, 0, 3, 3, 1, 0, 3, 1, 2, 2, 2, 0, 2, 2
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 25),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk54(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_015", "Grandmaster", 30, 50, listOf(
                listOf(
                    1, 3, 2, 2, 3, 2, 2, 1, 2, 3, 2, 0, 3, 1, 0, 3, 3, 0, 2, 3, 3, 1, 2, 3, 2, 2, 3, 1, 1, 0
                ),
                listOf(
                    1, 2, 0, 1, 0, 0, 0, 3, 1, 2, 2, 1, 1, 0, 0, 2, 3, 2, 0, 1, 2, 0, 2, 2, 0, 3, 3, 1, 0, 3
                ),
                listOf(
                    1, 1, 3, 3, 1, 2, 2, 3, 0, 0, 1, 2, 0, 3, 0, 1, 2, 2, 2, 3, 2, 2, 2, 0, 1, 0, 1, 2, 1, 2
                ),
                listOf(
                    3, 0, 1, 2, 1, 1, 3, 2, 2, 3, 3, 3, 1, 0, 2, 2, 1, 3, 1, 0, 3, 1, 0, 3, 2, 1, 3, 0, 0, 2
                ),
                listOf(
                    0, 2, 0, 2, 2, 1, 3, 0, 2, 1, 3, 0, 1, 2, 2, 3, 0, 2, 0, 1, 3, 2, 3, 0, 2, 0, 3, 2, 0, 2
                ),
                listOf(
                    1, 0, 1, 1, 1, 0, 0, 0, 2, 3, 0, 0, 2, 0, 1, 1, 2, 0, 0, 2, 0, 2, 0, 2, 1, 1, 3, 1, 0, 3
                ),
                listOf(
                    3, 1, 1, 3, 2, 2, 1, 3, 1, 3, 0, 1, 1, 1, 1, 2, 2, 2, 0, 2, 2, 2, 0, 3, 2, 1, 3, 0, 3, 3
                ),
                listOf(
                    0, 2, 1, 2, 1, 1, 3, 2, 0, 2, 3, 3, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1, 2, 2, 0, 0, 0, 2, 0
                ),
                listOf(
                    3, 2, 1, 2, 2, 0, 1, 3, 0, 3, 1, 3, 2, 0, 2, 3, 1, 2, 3, 1, 3, 1, 1, 3, 1, 3, 0, 1, 2, 0
                ),
                listOf(
                    2, 0, 3, 1, 0, 1, 0, 0, 1, 2, 0, 1, 3, 0, 3, 0, 2, 3, 1, 0, 0, 2, 2, 3, 1, 3, 1, 2, 1, 0
                ),
                listOf(
                    1, 0, 3, 1, 0, 0, 2, 3, 3, 0, 0, 0, 0, 2, 0, 2, 3, 0, 1, 3, 0, 1, 2, 2, 0, 3, 1, 1, 3, 2
                ),
                listOf(
                    2, 3, 3, 1, 3, 2, 3, 3, 0, 2, 2, 3, 2, 0, 3, 1, 1, 3, 2, 0, 0, 2, 1, 2, 2, 2, 1, 0, 1, 0
                ),
                listOf(
                    3, 0, 2, 0, 2, 1, 1, 1, 2, 1, 2, 3, 3, 1, 0, 0, 3, 3, 2, 1, 3, 0, 0, 3, 1, 1, 1, 1, 2, 3
                ),
                listOf(
                    1, 3, 1, 1, 0, 2, 3, 2, 0, 1, 1, 3, 3, 0, 0, 3, 2, 1, 2, 2, 2, 0, 0, 2, 3, 2, 1, 0, 1, 3
                ),
                listOf(
                    2, 3, 2, 0, 2, 3, 1, 0, 0, 3, 3, 0, 0, 1, 2, 2, 0, 0, 0, 3, 3, 1, 1, 0, 3, 2, 3, 1, 2, 3
                ),
                listOf(
                    0, 1, 0, 3, 1, 0, 0, 3, 1, 3, 2, 0, 3, 0, 3, 1, 2, 1, 0, 2, 3, 3, 1, 3, 3, 0, 3, 2, 1, 0
                ),
                listOf(
                    1, 3, 1, 1, 3, 0, 3, 0, 1, 1, 2, 0, 3, 0, 0, 1, 0, 2, 3, 0, 0, 2, 0, 0, 1, 2, 0, 3, 2, 0
                ),
                listOf(
                    2, 1, 1, 0, 1, 1, 1, 1, 0, 2, 2, 0, 2, 0, 1, 0, 1, 1, 3, 0, 2, 1, 1, 0, 2, 0, 0, 0, 2, 2
                ),
                listOf(
                    1, 1, 1, 0, 3, 2, 1, 2, 3, 1, 3, 3, 0, 0, 0, 1, 2, 0, 2, 3, 1, 1, 1, 1, 0, 1, 1, 2, 3, 3
                ),
                listOf(
                    3, 3, 1, 3, 2, 0, 0, 3, 2, 1, 3, 2, 3, 1, 3, 2, 2, 2, 3, 1, 3, 2, 0, 1, 1, 3, 1, 0, 3, 1
                ),
                listOf(
                    1, 1, 3, 2, 0, 0, 0, 3, 1, 1, 0, 3, 2, 3, 3, 2, 2, 1, 1, 0, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1
                ),
                listOf(
                    1, 0, 2, 2, 2, 1, 1, 2, 0, 0, 2, 3, 1, 2, 0, 1, 2, 2, 1, 0, 0, 3, 1, 3, 0, 1, 0, 0, 2, 1
                ),
                listOf(
                    2, 3, 3, 3, 1, 3, 1, 0, 1, 1, 1, 2, 1, 1, 0, 0, 1, 1, 3, 3, 3, 0, 0, 0, 3, 2, 2, 0, 1, 3
                ),
                listOf(
                    3, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 1, 1, 1, 3, 3, 3, 3, 2, 0, 1, 3, 0, 0, 0, 1, 0, 3, 3, 0
                ),
                listOf(
                    3, 0, 0, 1, 0, 0, 3, 0, 3, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 0, 3, 3, 2, 0, 0, 2, 2
                ),
                listOf(
                    3, 1, 1, 3, 2, 0, 1, 2, 3, 2, 2, 0, 1, 3, 1, 3, 0, 1, 3, 3, 3, 1, 1, 2, 1, 0, 1, 1, 0, 2
                ),
                listOf(
                    3, 3, 3, 1, 3, 0, 1, 0, 3, 1, 2, 3, 3, 2, 2, 3, 1, 1, 1, 0, 2, 0, 1, 3, 3, 0, 3, 0, 1, 3
                ),
                listOf(
                    3, 2, 0, 2, 1, 2, 0, 0, 2, 0, 3, 2, 1, 1, 0, 2, 0, 0, 3, 0, 2, 0, 2, 3, 2, 2, 3, 0, 2, 3
                ),
                listOf(
                    2, 3, 3, 0, 1, 2, 3, 0, 3, 3, 0, 2, 0, 3, 0, 2, 1, 2, 2, 0, 3, 0, 0, 2, 1, 0, 3, 0, 2, 0
                ),
                listOf(
                    1, 2, 2, 0, 1, 2, 0, 0, 1, 3, 3, 0, 2, 3, 3, 1, 3, 3, 0, 1, 1, 1, 0, 2, 0, 3, 1, 0, 3, 2
                ),
                listOf(
                    3, 1, 3, 1, 2, 0, 2, 1, 3, 0, 0, 3, 3, 3, 3, 1, 0, 3, 3, 3, 0, 0, 2, 0, 1, 0, 1, 1, 1, 3
                ),
                listOf(
                    3, 0, 3, 1, 0, 0, 2, 1, 0, 2, 2, 1, 3, 0, 1, 1, 3, 2, 0, 1, 1, 1, 0, 3, 0, 0, 3, 0, 3, 0
                ),
                listOf(
                    1, 1, 0, 1, 2, 3, 3, 0, 1, 3, 1, 0, 0, 0, 3, 0, 1, 2, 1, 2, 1, 3, 1, 0, 3, 1, 1, 1, 0, 3
                ),
                listOf(
                    2, 1, 0, 0, 0, 0, 1, 3, 3, 3, 3, 2, 2, 1, 3, 1, 1, 0, 0, 0, 2, 1, 3, 3, 1, 0, 0, 1, 2, 1
                ),
                listOf(
                    1, 1, 0, 3, 0, 0, 3, 0, 0, 1, 0, 0, 1, 3, 0, 2, 1, 3, 0, 0, 0, 0, 1, 2, 2, 3, 3, 0, 3, 3
                ),
                listOf(
                    1, 2, 3, 2, 3, 3, 3, 1, 3, 3, 1, 1, 3, 1, 1, 1, 2, 1, 3, 1, 0, 3, 3, 0, 0, 0, 3, 2, 3, 3
                ),
                listOf(
                    2, 0, 2, 3, 1, 1, 0, 0, 2, 3, 2, 2, 0, 2, 1, 2, 1, 2, 2, 0, 2, 1, 0, 3, 3, 1, 0, 3, 2, 2
                ),
                listOf(
                    3, 3, 0, 0, 1, 0, 0, 3, 2, 3, 0, 1, 3, 0, 3, 0, 3, 1, 1, 0, 1, 3, 3, 3, 1, 3, 2, 2, 2, 3
                ),
                listOf(
                    2, 2, 0, 2, 0, 1, 2, 1, 2, 0, 3, 1, 0, 3, 1, 0, 3, 1, 3, 3, 3, 3, 3, 1, 2, 0, 1, 1, 3, 3
                ),
                listOf(
                    0, 2, 0, 1, 3, 3, 1, 1, 2, 1, 2, 0, 1, 1, 1, 0, 3, 0, 2, 2, 1, 1, 0, 1, 2, 3, 2, 2, 2, 0
                ),
                listOf(
                    3, 0, 3, 0, 0, 0, 0, 1, 3, 1, 2, 2, 2, 3, 1, 1, 1, 2, 1, 3, 0, 2, 0, 3, 1, 1, 0, 0, 1, 3
                ),
                listOf(
                    0, 3, 3, 2, 3, 0, 1, 3, 0, 1, 0, 2, 1, 3, 3, 1, 3, 3, 1, 1, 1, 1, 3, 0, 0, 1, 0, 3, 2, 1
                ),
                listOf(
                    3, 1, 2, 3, 1, 1, 3, 3, 3, 3, 0, 3, 1, 0, 1, 2, 2, 0, 3, 2, 0, 0, 1, 0, 3, 2, 3, 3, 1, 0
                ),
                listOf(
                    0, 1, 3, 1, 3, 1, 1, 1, 3, 1, 0, 0, 1, 1, 3, 1, 1, 0, 0, 3, 0, 3, 0, 0, 0, 3, 2, 3, 1, 0
                ),
                listOf(
                    2, 2, 3, 1, 1, 2, 1, 1, 2, 0, 3, 2, 2, 0, 3, 3, 2, 1, 1, 0, 2, 1, 3, 2, 1, 2, 3, 3, 3, 3
                ),
                listOf(
                    3, 0, 3, 1, 0, 2, 0, 3, 0, 2, 2, 0, 0, 0, 1, 0, 0, 3, 3, 2, 2, 1, 2, 1, 3, 0, 0, 1, 1, 1
                ),
                listOf(
                    1, 2, 0, 3, 2, 1, 1, 2, 3, 0, 3, 1, 2, 3, 3, 3, 2, 2, 3, 3, 1, 1, 3, 2, 3, 3, 0, 2, 0, 2
                ),
                listOf(
                    1, 1, 1, 2, 2, 3, 1, 3, 2, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 2, 2, 1, 2, 3, 0, 1, 1, 0
                ),
                listOf(
                    2, 0, 1, 1, 1, 2, 2, 2, 1, 0, 0, 1, 2, 3, 2, 0, 2, 1, 3, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 0
                ),
                listOf(
                    3, 2, 0, 0, 2, 1, 1, 0, 1, 1, 0, 0, 0, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 0, 2, 1, 1, 2, 1, 3
                )
            ), listOf(
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 25),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk55(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_016", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 2, 0, 3, 2, 2, 0, 0, 2, 3, 2, 3, 0, 3, 3, 0, 0, 0, 1, 0, 1, 2, 2, 2, 2, 0, 0, 2, 0, 0
                ),
                listOf(
                    0, 0, 1, 0, 1, 0, 3, 0, 1, 2, 2, 0, 1, 2, 0, 3, 0, 0, 1, 3, 3, 2, 1, 1, 3, 3, 0, 1, 2, 3
                ),
                listOf(
                    3, 2, 2, 2, 1, 3, 1, 3, 3, 3, 0, 2, 1, 0, 0, 0, 0, 3, 1, 3, 3, 0, 1, 2, 0, 2, 1, 3, 0, 1
                ),
                listOf(
                    2, 1, 1, 0, 2, 2, 1, 0, 1, 3, 0, 0, 1, 2, 3, 1, 2, 2, 3, 3, 1, 3, 2, 1, 0, 2, 0, 3, 1, 0
                ),
                listOf(
                    1, 0, 0, 1, 2, 0, 3, 0, 2, 1, 0, 0, 3, 0, 1, 3, 3, 1, 2, 0, 0, 3, 0, 0, 0, 1, 3, 1, 1, 0
                ),
                listOf(
                    0, 1, 1, 3, 1, 0, 3, 0, 0, 0, 1, 1, 1, 3, 3, 0, 0, 1, 3, 0, 3, 0, 3, 1, 1, 0, 0, 3, 1, 1
                ),
                listOf(
                    2, 2, 0, 1, 2, 0, 0, 3, 3, 0, 2, 0, 3, 1, 3, 3, 1, 3, 0, 2, 0, 3, 3, 3, 3, 0, 2, 3, 0, 2
                ),
                listOf(
                    1, 0, 2, 3, 0, 2, 0, 0, 2, 2, 2, 0, 1, 1, 1, 1, 1, 0, 0, 2, 0, 3, 3, 0, 0, 3, 0, 2, 3, 3
                ),
                listOf(
                    0, 1, 0, 1, 1, 0, 3, 1, 0, 3, 0, 1, 2, 0, 3, 3, 1, 0, 0, 2, 0, 3, 1, 0, 2, 3, 1, 3, 0, 1
                ),
                listOf(
                    0, 0, 3, 2, 0, 3, 2, 3, 3, 2, 0, 2, 1, 0, 1, 2, 1, 3, 2, 1, 1, 2, 3, 0, 2, 0, 1, 0, 3, 0
                ),
                listOf(
                    1, 2, 0, 1, 3, 2, 0, 2, 3, 1, 3, 1, 3, 1, 0, 0, 0, 1, 3, 0, 3, 2, 3, 1, 1, 0, 0, 3, 0, 2
                ),
                listOf(
                    3, 1, 0, 1, 3, 2, 3, 0, 2, 3, 1, 2, 0, 3, 3, 1, 2, 3, 3, 0, 2, 3, 3, 1, 1, 0, 0, 2, 3, 2
                ),
                listOf(
                    3, 3, 2, 2, 0, 0, 0, 3, 3, 0, 1, 1, 0, 2, 3, 0, 3, 3, 3, 0, 0, 0, 2, 0, 3, 2, 2, 2, 0, 3
                ),
                listOf(
                    1, 3, 2, 0, 2, 2, 2, 3, 0, 0, 1, 2, 3, 2, 0, 2, 0, 3, 0, 1, 0, 1, 0, 1, 0, 2, 3, 2, 2, 3
                ),
                listOf(
                    2, 0, 1, 1, 1, 3, 2, 1, 1, 2, 0, 0, 0, 0, 3, 0, 0, 3, 2, 0, 0, 0, 3, 1, 0, 0, 1, 3, 2, 0
                ),
                listOf(
                    1, 0, 3, 0, 3, 1, 3, 1, 0, 3, 3, 3, 0, 1, 2, 2, 2, 2, 0, 2, 2, 2, 0, 1, 0, 0, 3, 0, 3, 2
                ),
                listOf(
                    2, 2, 0, 2, 2, 2, 0, 3, 0, 3, 1, 1, 0, 3, 0, 3, 1, 3, 1, 3, 3, 0, 3, 3, 2, 1, 1, 3, 2, 1
                ),
                listOf(
                    3, 0, 2, 1, 2, 0, 0, 1, 0, 0, 3, 2, 0, 3, 0, 1, 2, 1, 1, 3, 0, 0, 1, 3, 2, 0, 3, 0, 1, 3
                ),
                listOf(
                    0, 3, 0, 1, 1, 1, 1, 2, 0, 3, 1, 2, 1, 3, 1, 2, 1, 2, 2, 3, 1, 0, 3, 1, 3, 1, 0, 1, 3, 3
                ),
                listOf(
                    1, 0, 3, 0, 3, 0, 2, 3, 1, 2, 2, 0, 3, 0, 3, 1, 1, 1, 2, 1, 3, 0, 0, 1, 1, 3, 1, 3, 3, 3
                ),
                listOf(
                    3, 3, 1, 0, 3, 0, 2, 0, 2, 1, 2, 2, 3, 3, 0, 0, 2, 3, 2, 3, 1, 3, 1, 3, 3, 2, 3, 0, 2, 3
                ),
                listOf(
                    0, 2, 3, 2, 2, 1, 3, 3, 1, 2, 1, 0, 1, 3, 0, 2, 1, 0, 0, 0, 2, 0, 0, 2, 0, 2, 2, 3, 2, 0
                ),
                listOf(
                    1, 2, 2, 1, 1, 2, 1, 3, 0, 0, 0, 0, 0, 2, 1, 2, 3, 0, 2, 3, 1, 1, 3, 2, 0, 0, 2, 1, 1, 0
                ),
                listOf(
                    1, 0, 3, 0, 3, 3, 3, 2, 1, 2, 1, 0, 2, 3, 1, 1, 1, 2, 1, 0, 0, 1, 3, 0, 1, 1, 3, 1, 2, 2
                ),
                listOf(
                    0, 2, 1, 0, 0, 3, 1, 3, 1, 1, 0, 3, 2, 3, 2, 0, 0, 2, 2, 1, 0, 0, 2, 3, 1, 3, 0, 0, 0, 3
                ),
                listOf(
                    0, 3, 2, 0, 0, 2, 0, 2, 1, 1, 0, 3, 0, 1, 0, 1, 1, 0, 1, 2, 0, 3, 0, 0, 2, 2, 0, 0, 3, 1
                ),
                listOf(
                    1, 2, 2, 2, 3, 0, 0, 3, 2, 3, 2, 3, 1, 3, 3, 3, 0, 3, 0, 3, 2, 0, 0, 3, 0, 1, 0, 1, 1, 1
                ),
                listOf(
                    2, 1, 2, 1, 2, 0, 1, 0, 0, 3, 0, 0, 2, 3, 1, 1, 1, 3, 2, 3, 0, 0, 3, 2, 2, 2, 2, 3, 1, 1
                ),
                listOf(
                    0, 3, 3, 2, 3, 3, 1, 0, 0, 0, 1, 0, 0, 2, 3, 3, 0, 1, 1, 2, 0, 1, 0, 1, 1, 3, 3, 3, 0, 1
                ),
                listOf(
                    2, 3, 2, 1, 0, 2, 2, 0, 0, 3, 2, 3, 0, 2, 1, 3, 3, 1, 0, 0, 2, 1, 1, 1, 3, 3, 2, 0, 1, 3
                ),
                listOf(
                    0, 2, 1, 2, 3, 2, 2, 1, 2, 3, 0, 1, 0, 3, 1, 1, 3, 2, 0, 0, 3, 3, 3, 0, 0, 2, 1, 2, 0, 2
                ),
                listOf(
                    1, 3, 1, 3, 2, 2, 1, 2, 1, 1, 3, 2, 0, 2, 2, 1, 3, 3, 2, 0, 0, 3, 1, 3, 3, 0, 1, 0, 3, 0
                ),
                listOf(
                    3, 2, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 0, 0, 3, 1, 1, 0, 3, 2, 1, 2, 3, 2, 2, 0, 3, 3, 2, 3
                ),
                listOf(
                    2, 0, 3, 3, 1, 0, 2, 2, 2, 1, 0, 1, 3, 3, 2, 0, 3, 2, 3, 0, 0, 3, 3, 1, 3, 2, 0, 3, 1, 1
                ),
                listOf(
                    1, 3, 2, 0, 0, 2, 2, 2, 3, 0, 0, 2, 0, 1, 2, 3, 0, 3, 1, 0, 0, 2, 0, 2, 0, 0, 3, 3, 0, 2
                ),
                listOf(
                    2, 0, 1, 0, 0, 0, 0, 0, 3, 2, 1, 2, 0, 2, 1, 0, 0, 2, 2, 2, 2, 0, 2, 3, 3, 1, 3, 0, 0, 3
                ),
                listOf(
                    1, 1, 0, 3, 1, 3, 3, 1, 0, 3, 0, 1, 2, 3, 0, 3, 1, 1, 2, 3, 2, 2, 0, 0, 0, 2, 0, 0, 2, 1
                ),
                listOf(
                    1, 1, 0, 1, 2, 0, 0, 0, 0, 1, 2, 0, 2, 2, 0, 3, 3, 3, 1, 0, 3, 0, 0, 2, 2, 1, 3, 0, 3, 0
                ),
                listOf(
                    1, 3, 0, 0, 3, 2, 2, 2, 0, 3, 0, 1, 1, 1, 3, 1, 0, 2, 1, 0, 3, 3, 0, 3, 0, 0, 3, 1, 3, 2
                ),
                listOf(
                    0, 0, 0, 3, 2, 0, 1, 0, 1, 1, 0, 1, 3, 1, 2, 2, 1, 2, 2, 1, 0, 1, 0, 0, 0, 1, 0, 2, 0, 3
                ),
                listOf(
                    0, 2, 1, 0, 1, 1, 0, 1, 0, 3, 0, 0, 1, 3, 3, 3, 1, 0, 0, 3, 0, 3, 2, 3, 3, 3, 3, 0, 3, 2
                ),
                listOf(
                    0, 0, 0, 3, 3, 3, 3, 1, 0, 1, 1, 1, 2, 2, 0, 1, 3, 2, 3, 3, 3, 3, 2, 2, 1, 0, 3, 2, 1, 1
                ),
                listOf(
                    2, 2, 2, 1, 0, 0, 1, 3, 3, 3, 2, 2, 2, 3, 3, 1, 1, 1, 2, 0, 2, 0, 0, 2, 1, 0, 1, 0, 1, 3
                ),
                listOf(
                    2, 1, 1, 0, 1, 2, 1, 2, 1, 2, 2, 0, 3, 1, 3, 3, 3, 2, 1, 0, 2, 0, 0, 1, 1, 3, 2, 1, 1, 3
                ),
                listOf(
                    2, 0, 1, 2, 0, 3, 0, 1, 0, 0, 1, 1, 1, 1, 2, 1, 1, 1, 3, 1, 3, 2, 1, 0, 3, 2, 3, 1, 0, 3
                ),
                listOf(
                    2, 3, 0, 3, 0, 1, 3, 2, 1, 1, 2, 2, 1, 0, 3, 0, 1, 1, 2, 2, 1, 1, 0, 1, 1, 1, 2, 0, 0, 1
                ),
                listOf(
                    2, 1, 0, 1, 3, 3, 1, 3, 0, 2, 1, 2, 1, 1, 1, 2, 0, 3, 0, 3, 3, 2, 1, 3, 0, 3, 2, 0, 1, 0
                ),
                listOf(
                    0, 2, 2, 1, 3, 3, 1, 3, 1, 0, 1, 0, 1, 2, 1, 3, 0, 1, 2, 3, 1, 0, 1, 3, 3, 2, 2, 1, 2, 1
                ),
                listOf(
                    2, 0, 2, 2, 1, 2, 2, 0, 1, 2, 3, 1, 1, 0, 2, 0, 1, 0, 2, 1, 1, 3, 1, 1, 2, 2, 1, 3, 0, 2
                ),
                listOf(
                    0, 3, 1, 0, 2, 1, 3, 1, 0, 0, 0, 2, 2, 3, 0, 3, 1, 3, 1, 3, 0, 1, 3, 2, 1, 0, 2, 2, 2, 2
                )
            ), listOf(
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 25),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk56(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_017", "Grandmaster", 30, 50, listOf(
                listOf(
                    1, 1, 0, 1, 2, 1, 3, 3, 0, 0, 1, 0, 2, 1, 2, 1, 3, 0, 3, 1, 2, 2, 2, 3, 2, 3, 2, 0, 2, 1
                ),
                listOf(
                    1, 3, 1, 0, 1, 1, 1, 2, 3, 2, 1, 2, 1, 2, 0, 1, 2, 0, 2, 2, 1, 2, 3, 2, 2, 0, 2, 1, 3, 2
                ),
                listOf(
                    0, 1, 2, 1, 2, 3, 3, 0, 1, 1, 0, 0, 1, 3, 0, 1, 3, 0, 0, 0, 3, 0, 2, 0, 3, 0, 0, 0, 2, 1
                ),
                listOf(
                    1, 2, 1, 2, 0, 1, 1, 1, 0, 3, 3, 3, 2, 0, 2, 0, 1, 1, 3, 0, 0, 2, 1, 1, 2, 1, 1, 3, 1, 2
                ),
                listOf(
                    3, 2, 1, 0, 2, 3, 1, 3, 1, 0, 2, 0, 2, 2, 1, 1, 1, 0, 3, 3, 1, 3, 3, 2, 0, 2, 0, 2, 3, 3
                ),
                listOf(
                    2, 3, 1, 3, 0, 2, 2, 2, 3, 0, 0, 2, 2, 0, 1, 1, 3, 2, 1, 3, 0, 1, 0, 2, 2, 0, 3, 1, 2, 2
                ),
                listOf(
                    0, 2, 0, 1, 0, 3, 0, 3, 0, 1, 3, 1, 3, 2, 3, 1, 1, 1, 0, 3, 2, 3, 1, 2, 2, 0, 1, 1, 2, 1
                ),
                listOf(
                    2, 1, 2, 3, 0, 1, 2, 3, 2, 0, 0, 1, 3, 1, 1, 0, 2, 2, 3, 2, 2, 2, 2, 0, 3, 1, 3, 3, 1, 1
                ),
                listOf(
                    0, 2, 0, 3, 2, 1, 0, 1, 1, 2, 3, 2, 3, 2, 3, 1, 1, 3, 2, 3, 1, 2, 2, 1, 3, 0, 1, 0, 0, 1
                ),
                listOf(
                    2, 1, 2, 1, 0, 1, 1, 0, 2, 0, 1, 0, 2, 3, 0, 0, 3, 0, 3, 3, 3, 2, 1, 0, 2, 2, 0, 1, 0, 3
                ),
                listOf(
                    1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 3, 2, 3, 0, 1, 0, 2, 1, 0, 1, 0, 0, 0, 3, 1, 3, 0
                ),
                listOf(
                    3, 3, 3, 3, 0, 0, 2, 1, 0, 1, 1, 3, 2, 1, 2, 1, 1, 3, 2, 0, 3, 2, 1, 1, 2, 0, 2, 1, 2, 2
                ),
                listOf(
                    1, 2, 2, 0, 2, 2, 0, 3, 1, 3, 2, 2, 2, 3, 0, 2, 2, 1, 3, 2, 0, 1, 1, 1, 1, 2, 0, 0, 2, 1
                ),
                listOf(
                    1, 1, 3, 3, 1, 2, 3, 0, 2, 0, 2, 0, 3, 0, 0, 3, 1, 1, 0, 2, 1, 2, 0, 3, 2, 0, 2, 1, 0, 0
                ),
                listOf(
                    1, 0, 0, 0, 2, 3, 0, 2, 2, 2, 0, 0, 3, 1, 0, 1, 2, 2, 0, 1, 3, 2, 0, 2, 2, 2, 1, 2, 1, 0
                ),
                listOf(
                    3, 2, 1, 1, 0, 0, 0, 2, 1, 1, 2, 2, 2, 2, 3, 0, 0, 2, 0, 2, 3, 3, 2, 0, 0, 3, 0, 1, 3, 2
                ),
                listOf(
                    1, 1, 1, 0, 2, 3, 1, 1, 0, 3, 1, 2, 3, 1, 0, 2, 0, 1, 2, 0, 2, 1, 3, 2, 0, 0, 1, 0, 2, 0
                ),
                listOf(
                    0, 0, 1, 0, 0, 2, 1, 1, 0, 2, 0, 0, 3, 2, 3, 2, 3, 1, 2, 1, 1, 2, 3, 3, 3, 2, 3, 2, 2, 0
                ),
                listOf(
                    0, 3, 1, 3, 0, 3, 0, 3, 0, 3, 2, 1, 1, 2, 1, 1, 1, 2, 2, 1, 0, 3, 2, 1, 0, 1, 0, 0, 3, 0
                ),
                listOf(
                    3, 3, 3, 2, 1, 1, 1, 2, 2, 2, 0, 2, 2, 1, 2, 2, 1, 3, 2, 3, 3, 1, 1, 0, 0, 3, 0, 3, 1, 2
                ),
                listOf(
                    2, 2, 1, 1, 3, 0, 2, 1, 3, 1, 0, 1, 0, 0, 2, 3, 1, 2, 1, 0, 2, 1, 2, 0, 3, 3, 2, 0, 1, 2
                ),
                listOf(
                    2, 2, 3, 2, 1, 3, 0, 1, 1, 3, 3, 0, 3, 2, 0, 2, 1, 1, 0, 2, 2, 1, 3, 1, 1, 2, 2, 0, 2, 0
                ),
                listOf(
                    2, 3, 1, 3, 3, 3, 1, 3, 1, 3, 0, 3, 1, 1, 1, 3, 1, 3, 1, 1, 2, 1, 3, 2, 3, 1, 1, 3, 3, 3
                ),
                listOf(
                    1, 0, 0, 0, 0, 1, 2, 2, 3, 2, 1, 0, 3, 3, 3, 1, 3, 3, 2, 0, 0, 1, 0, 1, 0, 1, 1, 1, 2, 1
                ),
                listOf(
                    2, 0, 1, 0, 3, 0, 3, 1, 3, 3, 1, 3, 2, 2, 1, 2, 2, 2, 3, 2, 3, 3, 2, 3, 0, 1, 1, 1, 2, 3
                ),
                listOf(
                    1, 3, 0, 1, 2, 1, 1, 2, 0, 1, 2, 0, 1, 3, 2, 0, 1, 2, 3, 1, 3, 1, 2, 3, 0, 2, 1, 2, 1, 2
                ),
                listOf(
                    0, 2, 3, 1, 3, 1, 3, 0, 1, 1, 0, 2, 3, 0, 2, 0, 1, 0, 2, 1, 3, 0, 1, 0, 0, 3, 2, 0, 0, 2
                ),
                listOf(
                    0, 0, 2, 1, 2, 1, 3, 1, 1, 3, 0, 2, 2, 0, 3, 3, 2, 3, 2, 1, 0, 1, 0, 3, 0, 3, 2, 1, 1, 0
                ),
                listOf(
                    3, 2, 2, 1, 1, 2, 2, 1, 0, 0, 2, 3, 1, 3, 3, 1, 3, 2, 1, 2, 0, 3, 1, 2, 2, 0, 0, 1, 2, 0
                ),
                listOf(
                    3, 1, 3, 1, 0, 2, 3, 1, 3, 3, 3, 2, 0, 2, 1, 1, 1, 3, 0, 1, 2, 0, 1, 3, 1, 3, 3, 1, 0, 3
                ),
                listOf(
                    1, 3, 1, 2, 0, 3, 3, 1, 0, 2, 0, 1, 3, 2, 1, 3, 3, 0, 1, 2, 1, 1, 2, 1, 3, 1, 0, 1, 3, 0
                ),
                listOf(
                    2, 1, 0, 3, 1, 0, 0, 0, 0, 3, 3, 0, 2, 2, 1, 1, 1, 2, 3, 0, 1, 1, 1, 2, 3, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 2, 1, 1, 3, 3, 1, 2, 2, 2, 0, 3, 1, 0, 0, 0, 2, 0, 0, 1, 2, 3, 2, 3, 3, 2, 2, 3, 2, 1
                ),
                listOf(
                    1, 0, 3, 2, 0, 3, 2, 2, 2, 3, 0, 1, 0, 3, 3, 2, 1, 0, 1, 1, 2, 1, 2, 0, 2, 2, 2, 1, 1, 3
                ),
                listOf(
                    2, 3, 3, 0, 0, 1, 3, 1, 1, 2, 1, 3, 0, 2, 2, 0, 2, 3, 2, 2, 3, 3, 2, 3, 3, 1, 3, 3, 3, 3
                ),
                listOf(
                    0, 1, 0, 1, 2, 3, 2, 1, 1, 1, 2, 2, 3, 2, 0, 1, 2, 2, 0, 3, 0, 1, 1, 1, 0, 0, 1, 0, 2, 3
                ),
                listOf(
                    1, 1, 0, 3, 3, 1, 2, 1, 1, 2, 1, 3, 3, 1, 3, 2, 3, 0, 0, 1, 0, 2, 2, 0, 2, 3, 3, 3, 2, 1
                ),
                listOf(
                    1, 1, 2, 2, 1, 1, 1, 3, 3, 3, 3, 1, 1, 3, 0, 0, 0, 1, 3, 2, 0, 2, 3, 1, 2, 0, 2, 3, 1, 3
                ),
                listOf(
                    2, 0, 0, 1, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1, 3, 0, 1, 0, 1, 1, 0, 2, 2, 3, 1, 0, 3, 3, 2
                ),
                listOf(
                    1, 0, 2, 0, 2, 3, 1, 2, 1, 2, 3, 1, 1, 1, 3, 1, 1, 1, 0, 0, 3, 1, 3, 2, 3, 0, 3, 1, 3, 1
                ),
                listOf(
                    3, 0, 2, 2, 0, 2, 2, 0, 2, 3, 3, 0, 2, 3, 1, 2, 0, 1, 0, 0, 3, 0, 2, 2, 3, 0, 2, 1, 2, 0
                ),
                listOf(
                    0, 1, 3, 3, 0, 3, 2, 0, 0, 3, 3, 1, 3, 1, 1, 0, 2, 3, 3, 2, 2, 1, 2, 2, 2, 0, 1, 3, 1, 3
                ),
                listOf(
                    2, 0, 1, 1, 2, 3, 1, 0, 1, 0, 2, 1, 1, 2, 1, 0, 2, 2, 0, 2, 2, 1, 0, 1, 1, 1, 1, 1, 0, 1
                ),
                listOf(
                    3, 2, 0, 1, 2, 0, 0, 2, 3, 0, 3, 0, 2, 2, 2, 1, 3, 2, 3, 0, 2, 1, 2, 2, 3, 2, 1, 1, 0, 0
                ),
                listOf(
                    0, 1, 2, 2, 1, 2, 3, 0, 1, 3, 1, 1, 2, 2, 3, 2, 2, 0, 0, 0, 3, 3, 1, 2, 0, 1, 2, 0, 1, 3
                ),
                listOf(
                    2, 2, 3, 2, 1, 1, 2, 1, 3, 1, 1, 0, 0, 2, 0, 0, 3, 1, 1, 2, 2, 0, 0, 0, 1, 3, 2, 3, 0, 1
                ),
                listOf(
                    2, 3, 2, 3, 0, 0, 0, 1, 0, 2, 0, 0, 0, 3, 2, 1, 3, 3, 3, 1, 0, 0, 1, 1, 0, 1, 3, 2, 2, 0
                ),
                listOf(
                    2, 3, 1, 3, 1, 1, 0, 3, 0, 0, 1, 3, 1, 0, 1, 0, 1, 1, 1, 0, 3, 2, 1, 1, 3, 1, 1, 0, 0, 1
                ),
                listOf(
                    1, 0, 1, 2, 1, 0, 2, 0, 3, 3, 0, 1, 2, 1, 3, 2, 3, 0, 0, 2, 3, 1, 1, 2, 0, 3, 0, 0, 3, 0
                ),
                listOf(
                    3, 0, 2, 0, 2, 1, 0, 0, 3, 2, 3, 3, 2, 0, 2, 0, 2, 1, 0, 3, 0, 1, 0, 1, 3, 3, 3, 0, 2, 1
                )
            ), listOf(
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 25),
                Tank(0, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 25),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 25),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk57(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_018", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 1, 3, 3, 2, 2, 2, 0, 3, 2, 2, 1, 3, 1, 2, 3, 3, 3, 0, 3, 0, 1, 3, 2, 2, 3, 0, 0, 3, 1
                ),
                listOf(
                    3, 2, 2, 1, 2, 0, 0, 3, 0, 3, 3, 3, 2, 0, 0, 2, 3, 1, 0, 0, 1, 2, 1, 3, 3, 2, 3, 0, 3, 3
                ),
                listOf(
                    0, 2, 0, 0, 0, 1, 0, 3, 1, 3, 2, 3, 1, 3, 2, 1, 1, 2, 2, 2, 1, 1, 3, 1, 2, 3, 3, 1, 1, 0
                ),
                listOf(
                    3, 3, 1, 3, 1, 1, 1, 2, 3, 1, 3, 3, 0, 1, 3, 1, 1, 2, 1, 2, 0, 3, 2, 2, 0, 3, 1, 2, 2, 0
                ),
                listOf(
                    2, 3, 0, 0, 3, 1, 1, 1, 3, 3, 1, 1, 0, 2, 3, 0, 3, 0, 0, 3, 2, 1, 1, 3, 2, 3, 0, 0, 1, 2
                ),
                listOf(
                    3, 0, 3, 2, 3, 1, 3, 2, 3, 2, 1, 3, 2, 3, 3, 1, 0, 0, 3, 0, 1, 3, 3, 3, 0, 1, 3, 2, 0, 3
                ),
                listOf(
                    3, 3, 0, 3, 2, 1, 1, 0, 0, 0, 0, 2, 2, 2, 0, 1, 2, 3, 2, 0, 1, 2, 2, 3, 0, 3, 3, 1, 0, 1
                ),
                listOf(
                    2, 1, 1, 0, 0, 2, 1, 0, 1, 2, 3, 2, 0, 2, 0, 2, 1, 3, 3, 2, 3, 2, 1, 1, 3, 3, 0, 3, 3, 3
                ),
                listOf(
                    2, 1, 3, 1, 1, 2, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 3, 2, 2, 0, 0, 2, 2, 2, 0, 3, 2, 2, 1
                ),
                listOf(
                    2, 1, 0, 2, 0, 2, 0, 1, 0, 3, 2, 2, 1, 2, 3, 3, 0, 0, 1, 0, 3, 1, 1, 0, 2, 0, 2, 1, 1, 0
                ),
                listOf(
                    1, 1, 2, 1, 2, 2, 3, 2, 0, 3, 2, 1, 1, 2, 1, 2, 0, 2, 1, 2, 1, 0, 3, 3, 2, 1, 2, 2, 2, 3
                ),
                listOf(
                    2, 3, 2, 3, 2, 1, 2, 1, 1, 1, 1, 3, 3, 0, 0, 0, 0, 1, 2, 3, 2, 2, 2, 3, 0, 0, 1, 3, 3, 0
                ),
                listOf(
                    1, 0, 1, 2, 0, 2, 2, 3, 1, 1, 0, 3, 3, 2, 2, 3, 1, 1, 1, 3, 1, 1, 3, 2, 1, 2, 3, 3, 2, 1
                ),
                listOf(
                    0, 2, 1, 2, 0, 1, 3, 3, 3, 2, 1, 2, 3, 0, 3, 2, 1, 1, 1, 1, 2, 2, 0, 3, 1, 2, 0, 0, 2, 3
                ),
                listOf(
                    1, 1, 0, 2, 1, 2, 0, 1, 1, 1, 1, 2, 1, 1, 3, 3, 2, 2, 1, 3, 2, 0, 1, 0, 1, 2, 1, 2, 0, 2
                ),
                listOf(
                    2, 1, 0, 0, 0, 3, 2, 1, 2, 3, 2, 3, 2, 1, 2, 3, 0, 0, 3, 3, 1, 3, 1, 3, 0, 1, 3, 1, 0, 3
                ),
                listOf(
                    3, 2, 2, 2, 2, 1, 1, 0, 0, 3, 0, 2, 2, 0, 1, 0, 1, 0, 0, 2, 1, 1, 2, 1, 1, 2, 2, 0, 0, 0
                ),
                listOf(
                    1, 1, 1, 3, 2, 0, 0, 2, 2, 3, 1, 3, 0, 3, 2, 0, 1, 0, 0, 1, 1, 1, 1, 2, 3, 0, 1, 3, 2, 3
                ),
                listOf(
                    3, 2, 2, 2, 3, 1, 1, 3, 2, 3, 1, 1, 3, 3, 1, 2, 3, 0, 2, 2, 2, 1, 3, 0, 3, 3, 0, 0, 3, 1
                ),
                listOf(
                    3, 1, 0, 3, 2, 0, 2, 2, 0, 1, 2, 1, 1, 2, 1, 2, 2, 3, 3, 2, 2, 3, 0, 2, 2, 0, 2, 0, 0, 3
                ),
                listOf(
                    1, 0, 2, 1, 1, 1, 1, 1, 0, 3, 2, 2, 1, 2, 0, 0, 1, 3, 3, 0, 3, 0, 0, 3, 0, 0, 0, 3, 3, 1
                ),
                listOf(
                    1, 1, 2, 1, 3, 2, 2, 1, 1, 0, 1, 3, 1, 0, 3, 3, 2, 1, 0, 3, 0, 1, 2, 2, 2, 0, 1, 1, 1, 2
                ),
                listOf(
                    2, 1, 2, 2, 3, 0, 2, 2, 1, 3, 1, 1, 3, 3, 1, 1, 0, 0, 2, 3, 0, 2, 1, 2, 3, 3, 2, 3, 0, 0
                ),
                listOf(
                    2, 2, 2, 2, 3, 3, 1, 0, 3, 0, 3, 2, 1, 3, 0, 1, 2, 1, 1, 1, 0, 0, 3, 3, 2, 2, 3, 0, 1, 3
                ),
                listOf(
                    3, 1, 3, 1, 2, 0, 3, 0, 0, 1, 0, 2, 2, 1, 1, 3, 1, 3, 1, 2, 1, 3, 0, 1, 2, 3, 2, 3, 1, 2
                ),
                listOf(
                    2, 2, 3, 1, 1, 1, 1, 1, 1, 3, 1, 0, 0, 2, 3, 3, 0, 3, 0, 0, 0, 3, 1, 1, 3, 0, 2, 3, 3, 1
                ),
                listOf(
                    1, 1, 0, 1, 1, 0, 3, 0, 1, 3, 0, 0, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 3, 2, 2, 3, 0, 1, 0, 0
                ),
                listOf(
                    1, 0, 1, 3, 2, 3, 1, 2, 2, 1, 2, 3, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 0, 3, 2, 3, 0, 0, 0, 3
                ),
                listOf(
                    1, 2, 1, 0, 0, 2, 3, 0, 1, 0, 2, 2, 3, 2, 0, 1, 1, 1, 1, 0, 3, 3, 1, 2, 1, 1, 1, 2, 3, 3
                ),
                listOf(
                    3, 3, 1, 0, 2, 0, 0, 2, 2, 0, 0, 0, 3, 3, 2, 0, 2, 2, 3, 2, 0, 2, 0, 0, 1, 2, 0, 1, 2, 3
                ),
                listOf(
                    3, 3, 0, 1, 3, 0, 0, 0, 3, 3, 3, 2, 0, 3, 3, 2, 1, 0, 0, 3, 0, 2, 2, 0, 2, 0, 0, 2, 0, 1
                ),
                listOf(
                    1, 1, 2, 2, 3, 0, 3, 2, 3, 3, 0, 2, 3, 2, 0, 2, 2, 0, 3, 0, 3, 3, 3, 1, 2, 0, 2, 3, 0, 0
                ),
                listOf(
                    2, 0, 3, 2, 0, 2, 1, 3, 1, 1, 2, 2, 3, 2, 0, 1, 2, 1, 1, 3, 3, 2, 2, 2, 3, 2, 3, 0, 3, 3
                ),
                listOf(
                    2, 0, 3, 3, 1, 1, 3, 3, 3, 0, 3, 1, 2, 1, 1, 0, 3, 0, 0, 2, 1, 1, 2, 0, 0, 3, 3, 0, 3, 0
                ),
                listOf(
                    0, 0, 1, 2, 3, 1, 2, 1, 0, 2, 1, 1, 2, 2, 3, 3, 2, 2, 2, 0, 0, 0, 1, 0, 3, 3, 3, 1, 2, 3
                ),
                listOf(
                    1, 1, 1, 2, 0, 3, 1, 2, 0, 2, 3, 1, 3, 1, 1, 2, 2, 2, 1, 1, 1, 3, 1, 1, 0, 0, 1, 3, 3, 2
                ),
                listOf(
                    1, 2, 2, 0, 1, 0, 1, 3, 3, 2, 2, 3, 1, 3, 2, 3, 0, 1, 3, 2, 2, 0, 0, 1, 2, 2, 0, 2, 0, 1
                ),
                listOf(
                    1, 0, 2, 2, 3, 1, 0, 2, 1, 2, 3, 1, 3, 2, 3, 3, 3, 3, 3, 3, 0, 3, 2, 0, 1, 3, 2, 3, 1, 1
                ),
                listOf(
                    0, 1, 2, 2, 0, 0, 1, 1, 3, 1, 2, 3, 1, 1, 1, 2, 3, 0, 2, 1, 3, 1, 3, 3, 1, 1, 0, 1, 2, 0
                ),
                listOf(
                    3, 1, 1, 0, 3, 1, 1, 0, 0, 3, 0, 0, 2, 3, 3, 3, 3, 3, 0, 1, 2, 3, 2, 0, 3, 0, 2, 3, 2, 2
                ),
                listOf(
                    3, 0, 3, 3, 1, 0, 2, 1, 3, 3, 1, 1, 0, 1, 1, 0, 2, 2, 3, 0, 1, 0, 3, 2, 1, 3, 2, 0, 1, 3
                ),
                listOf(
                    3, 3, 0, 3, 1, 3, 1, 3, 3, 1, 2, 3, 2, 0, 1, 3, 1, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 0, 3, 0
                ),
                listOf(
                    3, 2, 2, 3, 2, 3, 1, 2, 0, 1, 3, 2, 2, 3, 0, 1, 2, 1, 2, 3, 1, 0, 0, 0, 0, 0, 1, 2, 2, 3
                ),
                listOf(
                    1, 3, 0, 2, 0, 0, 3, 3, 3, 1, 1, 2, 0, 2, 3, 3, 3, 2, 3, 3, 0, 2, 0, 3, 2, 3, 2, 3, 0, 3
                ),
                listOf(
                    3, 2, 1, 3, 0, 3, 1, 2, 3, 1, 3, 0, 1, 0, 2, 0, 3, 2, 0, 0, 2, 2, 3, 2, 2, 1, 1, 3, 0, 2
                ),
                listOf(
                    3, 1, 3, 2, 3, 3, 3, 1, 0, 1, 3, 1, 3, 3, 3, 1, 1, 1, 1, 2, 1, 1, 2, 3, 1, 1, 0, 3, 3, 2
                ),
                listOf(
                    3, 1, 2, 3, 2, 1, 3, 0, 1, 0, 3, 1, 0, 3, 3, 3, 0, 2, 2, 1, 0, 3, 1, 3, 2, 1, 1, 0, 3, 0
                ),
                listOf(
                    3, 0, 2, 3, 3, 3, 0, 0, 1, 1, 0, 0, 0, 0, 2, 0, 1, 2, 0, 3, 1, 2, 0, 2, 1, 2, 1, 2, 1, 3
                ),
                listOf(
                    0, 3, 3, 0, 3, 0, 3, 1, 2, 1, 3, 0, 2, 2, 3, 2, 0, 3, 0, 1, 3, 2, 2, 2, 2, 2, 0, 2, 3, 0
                ),
                listOf(
                    3, 3, 1, 1, 3, 2, 1, 3, 2, 3, 0, 1, 1, 3, 2, 1, 2, 1, 3, 2, 0, 2, 3, 0, 1, 3, 1, 3, 0, 0
                )
            ), listOf(
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 25),
                Tank(2, 25),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 15)
            )),
        )
    }

    private fun getChunk58(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_019", "Grandmaster", 30, 50, listOf(
                listOf(
                    0, 1, 1, 2, 0, 2, 1, 2, 1, 1, 0, 1, 0, 2, 3, 0, 3, 3, 1, 2, 2, 3, 2, 3, 3, 3, 1, 1, 0, 3
                ),
                listOf(
                    1, 3, 0, 2, 3, 0, 0, 3, 2, 0, 3, 1, 1, 1, 0, 2, 0, 0, 1, 1, 1, 0, 1, 1, 1, 2, 1, 0, 2, 1
                ),
                listOf(
                    3, 1, 1, 2, 0, 3, 0, 0, 2, 3, 3, 2, 0, 1, 3, 2, 1, 0, 3, 1, 0, 2, 0, 2, 2, 3, 0, 1, 1, 3
                ),
                listOf(
                    3, 3, 1, 1, 2, 3, 3, 1, 3, 0, 3, 1, 3, 0, 1, 3, 0, 1, 1, 3, 0, 0, 1, 1, 1, 0, 0, 3, 0, 2
                ),
                listOf(
                    3, 1, 3, 2, 3, 0, 3, 3, 3, 2, 0, 2, 2, 1, 2, 0, 0, 1, 2, 3, 3, 3, 0, 0, 1, 1, 0, 3, 1, 2
                ),
                listOf(
                    0, 0, 1, 2, 2, 2, 1, 3, 0, 3, 0, 3, 0, 1, 1, 0, 1, 3, 0, 1, 0, 2, 2, 2, 3, 2, 2, 2, 3, 0
                ),
                listOf(
                    2, 0, 1, 3, 1, 1, 0, 2, 0, 2, 1, 3, 2, 1, 2, 0, 3, 3, 2, 2, 3, 2, 0, 2, 1, 3, 2, 0, 2, 3
                ),
                listOf(
                    0, 1, 1, 1, 3, 2, 1, 1, 1, 1, 0, 3, 0, 0, 1, 3, 1, 0, 0, 1, 2, 1, 3, 0, 2, 0, 3, 3, 1, 0
                ),
                listOf(
                    0, 2, 2, 2, 3, 0, 1, 2, 0, 1, 0, 1, 0, 0, 0, 1, 0, 3, 2, 0, 1, 2, 0, 2, 1, 3, 0, 3, 3, 3
                ),
                listOf(
                    2, 1, 3, 1, 3, 0, 0, 0, 2, 0, 3, 0, 1, 0, 2, 0, 3, 1, 0, 0, 0, 0, 0, 1, 1, 2, 0, 0, 1, 3
                ),
                listOf(
                    1, 0, 2, 3, 0, 0, 0, 3, 3, 3, 0, 0, 2, 3, 3, 0, 3, 3, 3, 3, 2, 2, 1, 0, 3, 0, 0, 3, 3, 3
                ),
                listOf(
                    3, 3, 1, 3, 0, 3, 1, 0, 2, 3, 2, 0, 1, 1, 2, 3, 1, 0, 3, 0, 1, 3, 2, 0, 2, 0, 2, 2, 3, 1
                ),
                listOf(
                    0, 0, 1, 2, 2, 1, 0, 0, 2, 2, 0, 0, 2, 0, 3, 2, 3, 3, 2, 2, 1, 1, 1, 0, 1, 0, 1, 0, 2, 0
                ),
                listOf(
                    2, 1, 2, 1, 1, 3, 1, 2, 3, 1, 2, 3, 2, 1, 1, 1, 1, 0, 0, 1, 3, 3, 0, 2, 0, 1, 0, 2, 2, 0
                ),
                listOf(
                    1, 1, 0, 2, 3, 2, 3, 2, 1, 0, 1, 3, 0, 3, 2, 2, 1, 0, 1, 0, 0, 0, 1, 2, 2, 3, 1, 0, 0, 2
                ),
                listOf(
                    1, 1, 0, 2, 1, 2, 0, 3, 3, 3, 1, 0, 1, 2, 2, 2, 0, 2, 2, 1, 1, 0, 1, 1, 1, 0, 2, 1, 3, 2
                ),
                listOf(
                    3, 0, 3, 2, 0, 1, 0, 3, 2, 1, 0, 0, 0, 3, 1, 3, 2, 2, 1, 2, 0, 1, 0, 3, 2, 1, 3, 0, 3, 0
                ),
                listOf(
                    0, 2, 3, 1, 1, 1, 2, 3, 1, 3, 0, 3, 0, 1, 0, 2, 3, 2, 0, 2, 3, 3, 3, 0, 3, 1, 2, 3, 1, 0
                ),
                listOf(
                    3, 3, 2, 1, 0, 0, 2, 1, 1, 0, 3, 2, 1, 1, 2, 3, 0, 1, 2, 3, 3, 0, 3, 1, 1, 3, 0, 3, 0, 3
                ),
                listOf(
                    1, 1, 3, 1, 1, 1, 2, 2, 0, 1, 3, 1, 0, 3, 0, 2, 0, 2, 1, 0, 3, 2, 3, 1, 2, 0, 1, 0, 2, 1
                ),
                listOf(
                    3, 3, 1, 1, 2, 2, 0, 3, 3, 0, 3, 1, 0, 1, 1, 3, 0, 0, 0, 3, 3, 3, 0, 2, 1, 0, 1, 2, 0, 2
                ),
                listOf(
                    0, 0, 1, 2, 3, 0, 0, 1, 3, 0, 1, 1, 0, 2, 2, 3, 3, 0, 1, 0, 3, 3, 3, 3, 2, 0, 2, 0, 1, 0
                ),
                listOf(
                    1, 1, 0, 2, 2, 2, 0, 2, 1, 1, 3, 2, 0, 0, 2, 3, 0, 0, 3, 1, 2, 0, 1, 0, 3, 1, 3, 1, 1, 3
                ),
                listOf(
                    2, 2, 0, 0, 3, 3, 0, 2, 3, 0, 3, 2, 0, 0, 2, 2, 1, 0, 1, 1, 2, 1, 2, 3, 2, 0, 3, 2, 3, 2
                ),
                listOf(
                    0, 0, 3, 0, 2, 2, 3, 3, 1, 2, 3, 2, 0, 0, 1, 0, 2, 1, 0, 1, 0, 0, 0, 2, 3, 3, 3, 0, 3, 2
                ),
                listOf(
                    1, 2, 1, 1, 0, 0, 1, 3, 3, 0, 2, 3, 1, 2, 2, 0, 0, 1, 1, 2, 2, 1, 1, 0, 1, 0, 3, 1, 3, 1
                ),
                listOf(
                    3, 2, 3, 2, 1, 2, 2, 3, 1, 2, 2, 2, 1, 2, 2, 0, 1, 3, 0, 2, 3, 2, 1, 1, 2, 0, 0, 0, 0, 1
                ),
                listOf(
                    1, 3, 3, 2, 0, 1, 3, 1, 1, 2, 1, 2, 0, 1, 1, 1, 1, 0, 1, 0, 2, 3, 3, 2, 3, 1, 3, 0, 3, 0
                ),
                listOf(
                    3, 1, 2, 2, 3, 3, 3, 1, 2, 0, 0, 2, 2, 2, 3, 2, 1, 1, 0, 3, 1, 1, 0, 3, 3, 3, 0, 3, 0, 2
                ),
                listOf(
                    0, 3, 2, 0, 2, 0, 3, 0, 0, 0, 0, 1, 2, 0, 2, 3, 0, 0, 1, 0, 3, 0, 3, 0, 2, 1, 0, 2, 2, 2
                ),
                listOf(
                    1, 0, 2, 2, 3, 2, 3, 0, 3, 1, 3, 3, 1, 3, 2, 2, 0, 2, 1, 2, 0, 1, 2, 0, 2, 2, 3, 1, 3, 0
                ),
                listOf(
                    3, 3, 1, 0, 0, 3, 3, 1, 3, 3, 1, 1, 0, 3, 3, 2, 3, 1, 2, 0, 3, 0, 2, 3, 1, 0, 3, 2, 2, 2
                ),
                listOf(
                    3, 0, 1, 0, 1, 0, 1, 2, 1, 0, 0, 3, 3, 1, 0, 3, 1, 3, 2, 1, 0, 1, 3, 2, 0, 3, 3, 1, 0, 3
                ),
                listOf(
                    3, 2, 2, 3, 3, 3, 0, 1, 2, 3, 3, 0, 1, 1, 3, 2, 1, 0, 0, 1, 3, 3, 3, 1, 1, 3, 1, 2, 2, 3
                ),
                listOf(
                    1, 1, 3, 0, 3, 3, 0, 3, 0, 3, 3, 3, 3, 2, 1, 1, 1, 2, 0, 1, 3, 0, 3, 3, 0, 1, 3, 2, 2, 0
                ),
                listOf(
                    3, 1, 1, 3, 0, 3, 0, 2, 2, 2, 1, 1, 3, 2, 0, 0, 0, 1, 1, 0, 3, 0, 1, 1, 1, 2, 3, 0, 3, 1
                ),
                listOf(
                    1, 0, 3, 0, 2, 1, 3, 3, 1, 2, 0, 1, 1, 0, 0, 0, 1, 0, 1, 2, 1, 3, 1, 0, 1, 1, 0, 3, 0, 1
                ),
                listOf(
                    0, 3, 3, 3, 2, 3, 1, 0, 2, 0, 0, 3, 3, 3, 1, 1, 1, 3, 1, 3, 1, 0, 0, 3, 2, 3, 3, 2, 3, 3
                ),
                listOf(
                    2, 2, 3, 2, 0, 1, 1, 2, 1, 2, 1, 2, 1, 0, 1, 2, 3, 0, 1, 3, 3, 3, 0, 1, 0, 1, 2, 3, 2, 1
                ),
                listOf(
                    0, 3, 0, 0, 2, 0, 1, 1, 2, 2, 0, 3, 0, 3, 0, 2, 2, 1, 1, 0, 0, 3, 0, 1, 0, 2, 0, 0, 0, 3
                ),
                listOf(
                    2, 3, 2, 1, 0, 2, 1, 1, 0, 1, 0, 1, 2, 2, 0, 3, 1, 2, 2, 1, 2, 2, 1, 3, 1, 0, 3, 0, 1, 1
                ),
                listOf(
                    0, 3, 3, 2, 2, 3, 3, 3, 2, 2, 2, 1, 3, 2, 0, 0, 0, 2, 3, 0, 1, 2, 1, 3, 3, 3, 2, 0, 0, 3
                ),
                listOf(
                    2, 1, 3, 1, 1, 3, 3, 1, 0, 3, 3, 2, 0, 1, 3, 1, 0, 2, 2, 2, 1, 0, 0, 2, 0, 0, 1, 3, 2, 2
                ),
                listOf(
                    3, 0, 2, 0, 1, 1, 3, 1, 1, 2, 2, 3, 0, 0, 2, 0, 3, 2, 3, 2, 0, 3, 3, 3, 1, 2, 3, 2, 1, 1
                ),
                listOf(
                    3, 1, 0, 3, 3, 0, 0, 3, 1, 2, 3, 1, 3, 2, 3, 2, 0, 3, 1, 2, 2, 0, 2, 1, 1, 3, 0, 1, 3, 3
                ),
                listOf(
                    0, 2, 1, 2, 1, 2, 3, 0, 0, 1, 1, 3, 1, 3, 3, 3, 3, 3, 1, 0, 2, 3, 2, 0, 1, 2, 1, 1, 0, 1
                ),
                listOf(
                    3, 1, 2, 0, 2, 3, 0, 3, 3, 3, 1, 2, 3, 1, 1, 1, 0, 3, 2, 3, 0, 2, 3, 2, 0, 3, 1, 2, 2, 3
                ),
                listOf(
                    3, 3, 2, 2, 3, 1, 3, 1, 2, 3, 2, 1, 0, 0, 3, 2, 0, 0, 0, 2, 0, 1, 1, 2, 3, 1, 1, 2, 2, 1
                ),
                listOf(
                    2, 0, 2, 2, 3, 0, 2, 3, 1, 1, 3, 3, 0, 0, 3, 3, 0, 3, 1, 2, 0, 2, 2, 2, 1, 2, 3, 3, 2, 3
                ),
                listOf(
                    3, 2, 1, 3, 0, 1, 2, 2, 3, 3, 0, 2, 3, 3, 0, 3, 2, 0, 2, 3, 1, 0, 0, 3, 3, 0, 1, 3, 3, 3
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 25),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 25),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk59(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_grandmaster_020", "Grandmaster", 30, 50, listOf(
                listOf(
                    2, 1, 3, 1, 3, 1, 3, 0, 0, 1, 2, 3, 2, 0, 2, 0, 1, 2, 0, 2, 0, 0, 0, 0, 2, 1, 0, 1, 2, 2
                ),
                listOf(
                    3, 1, 0, 0, 3, 1, 2, 1, 1, 1, 3, 3, 3, 0, 1, 1, 3, 0, 3, 1, 1, 3, 2, 0, 2, 2, 3, 0, 1, 1
                ),
                listOf(
                    1, 1, 2, 1, 3, 1, 0, 3, 1, 2, 0, 1, 2, 3, 2, 0, 2, 3, 3, 2, 0, 3, 3, 1, 2, 3, 3, 0, 3, 2
                ),
                listOf(
                    1, 0, 1, 2, 0, 1, 2, 2, 2, 1, 3, 1, 1, 0, 0, 0, 3, 1, 0, 3, 2, 2, 3, 1, 1, 0, 2, 2, 3, 0
                ),
                listOf(
                    2, 3, 1, 1, 0, 1, 2, 0, 1, 0, 0, 1, 2, 0, 2, 1, 0, 0, 3, 1, 0, 3, 3, 3, 3, 2, 0, 3, 0, 3
                ),
                listOf(
                    2, 1, 2, 1, 3, 3, 3, 1, 3, 3, 0, 2, 2, 0, 3, 1, 2, 1, 0, 3, 3, 1, 1, 0, 2, 0, 1, 1, 0, 0
                ),
                listOf(
                    0, 3, 1, 2, 2, 2, 1, 1, 2, 0, 2, 0, 3, 2, 0, 1, 1, 1, 0, 1, 2, 0, 0, 3, 0, 2, 3, 2, 0, 1
                ),
                listOf(
                    2, 2, 3, 0, 1, 3, 2, 0, 1, 1, 2, 1, 3, 2, 0, 3, 2, 3, 3, 3, 1, 0, 0, 1, 3, 0, 3, 3, 0, 0
                ),
                listOf(
                    2, 2, 3, 2, 0, 1, 1, 3, 1, 2, 3, 0, 1, 1, 1, 0, 2, 2, 0, 1, 3, 3, 1, 1, 3, 3, 0, 0, 0, 3
                ),
                listOf(
                    1, 3, 1, 3, 2, 2, 2, 1, 0, 0, 3, 2, 3, 3, 1, 0, 3, 2, 0, 1, 3, 1, 3, 1, 2, 1, 0, 3, 0, 2
                ),
                listOf(
                    2, 2, 2, 1, 0, 3, 1, 2, 2, 2, 2, 2, 3, 2, 1, 1, 1, 0, 2, 2, 1, 3, 2, 2, 3, 2, 0, 2, 0, 1
                ),
                listOf(
                    3, 2, 2, 0, 3, 2, 1, 1, 2, 3, 2, 2, 3, 3, 2, 0, 1, 2, 2, 3, 1, 1, 0, 1, 1, 3, 3, 2, 2, 3
                ),
                listOf(
                    2, 0, 3, 0, 3, 2, 1, 3, 3, 3, 2, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 0, 3, 3, 3, 2, 2, 2, 3, 1
                ),
                listOf(
                    3, 2, 2, 1, 2, 0, 3, 2, 1, 0, 0, 0, 3, 2, 2, 3, 0, 0, 0, 2, 0, 3, 3, 3, 2, 2, 2, 2, 0, 2
                ),
                listOf(
                    2, 2, 0, 0, 3, 1, 1, 3, 2, 1, 0, 1, 2, 1, 2, 3, 0, 0, 3, 0, 2, 3, 2, 2, 2, 3, 1, 1, 1, 2
                ),
                listOf(
                    3, 3, 1, 2, 1, 2, 3, 0, 2, 3, 3, 3, 2, 2, 0, 2, 2, 0, 1, 3, 3, 3, 3, 2, 0, 3, 0, 1, 1, 2
                ),
                listOf(
                    3, 1, 2, 0, 3, 2, 3, 1, 0, 3, 2, 3, 2, 2, 2, 3, 3, 3, 3, 3, 1, 0, 0, 0, 3, 0, 0, 0, 3, 3
                ),
                listOf(
                    3, 2, 3, 1, 2, 3, 2, 3, 1, 3, 1, 0, 0, 0, 3, 3, 1, 3, 2, 1, 1, 2, 2, 0, 0, 2, 0, 2, 2, 1
                ),
                listOf(
                    3, 3, 3, 1, 3, 2, 2, 3, 3, 0, 3, 1, 3, 2, 3, 2, 3, 1, 2, 2, 3, 2, 1, 2, 2, 1, 0, 0, 1, 2
                ),
                listOf(
                    0, 3, 1, 3, 3, 3, 1, 2, 1, 2, 2, 0, 2, 0, 0, 2, 2, 3, 3, 3, 2, 1, 1, 2, 2, 0, 0, 0, 0, 1
                ),
                listOf(
                    0, 1, 1, 0, 2, 0, 0, 2, 0, 3, 2, 2, 0, 0, 3, 3, 2, 3, 2, 3, 3, 1, 2, 1, 1, 0, 3, 2, 1, 2
                ),
                listOf(
                    1, 2, 3, 2, 3, 1, 2, 0, 3, 3, 2, 3, 1, 3, 2, 1, 2, 3, 2, 0, 1, 1, 3, 3, 2, 1, 3, 2, 2, 1
                ),
                listOf(
                    1, 0, 1, 1, 0, 1, 0, 1, 1, 3, 3, 2, 0, 3, 0, 3, 1, 2, 2, 1, 0, 3, 3, 3, 2, 3, 3, 3, 3, 2
                ),
                listOf(
                    3, 3, 3, 3, 0, 2, 1, 1, 0, 2, 0, 0, 1, 3, 2, 0, 1, 3, 0, 2, 1, 2, 1, 1, 3, 1, 2, 0, 0, 3
                ),
                listOf(
                    0, 3, 3, 0, 1, 2, 2, 0, 3, 0, 1, 2, 2, 2, 2, 3, 0, 1, 2, 3, 0, 2, 2, 3, 3, 2, 1, 3, 0, 3
                ),
                listOf(
                    0, 1, 2, 1, 2, 3, 0, 1, 1, 1, 3, 2, 0, 1, 2, 2, 2, 0, 0, 2, 3, 0, 1, 2, 3, 1, 2, 1, 3, 3
                ),
                listOf(
                    1, 1, 3, 0, 1, 0, 1, 2, 2, 2, 3, 3, 1, 1, 0, 3, 1, 3, 3, 1, 2, 1, 0, 3, 3, 2, 3, 3, 1, 0
                ),
                listOf(
                    0, 2, 2, 2, 0, 1, 2, 0, 1, 0, 1, 3, 3, 2, 1, 1, 3, 2, 2, 0, 0, 2, 3, 2, 1, 3, 3, 1, 2, 3
                ),
                listOf(
                    3, 3, 3, 3, 2, 3, 1, 1, 1, 0, 0, 2, 2, 2, 3, 2, 3, 1, 2, 3, 1, 2, 1, 2, 3, 0, 3, 2, 0, 2
                ),
                listOf(
                    0, 1, 2, 2, 1, 3, 0, 3, 2, 3, 1, 0, 0, 2, 3, 0, 3, 2, 1, 1, 3, 3, 0, 0, 0, 2, 2, 3, 3, 2
                ),
                listOf(
                    2, 1, 0, 3, 3, 2, 3, 3, 1, 3, 1, 1, 3, 0, 3, 2, 2, 1, 1, 2, 1, 1, 3, 3, 3, 3, 2, 0, 1, 2
                ),
                listOf(
                    2, 2, 3, 0, 1, 1, 1, 2, 2, 2, 2, 1, 3, 3, 3, 3, 1, 2, 0, 3, 1, 1, 2, 3, 2, 0, 0, 2, 1, 3
                ),
                listOf(
                    2, 2, 1, 3, 3, 0, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 1, 1, 2, 0, 0, 1, 2
                ),
                listOf(
                    3, 3, 3, 1, 3, 0, 2, 2, 2, 2, 1, 2, 3, 2, 0, 2, 3, 1, 1, 1, 0, 0, 1, 1, 3, 3, 2, 3, 0, 1
                ),
                listOf(
                    1, 3, 3, 2, 0, 3, 3, 3, 3, 1, 1, 1, 2, 0, 2, 1, 0, 3, 0, 3, 2, 3, 1, 1, 1, 3, 1, 1, 2, 2
                ),
                listOf(
                    3, 3, 0, 1, 1, 3, 0, 0, 2, 1, 1, 3, 0, 3, 3, 3, 3, 1, 1, 2, 0, 0, 1, 1, 1, 1, 2, 0, 3, 2
                ),
                listOf(
                    3, 3, 2, 1, 1, 1, 2, 1, 3, 2, 3, 2, 0, 2, 3, 3, 1, 3, 2, 3, 1, 0, 2, 3, 1, 3, 2, 1, 2, 0
                ),
                listOf(
                    3, 2, 0, 3, 3, 3, 3, 1, 3, 3, 0, 3, 1, 2, 0, 1, 1, 0, 3, 3, 0, 1, 2, 3, 2, 1, 3, 0, 1, 3
                ),
                listOf(
                    3, 1, 0, 0, 2, 2, 1, 3, 3, 3, 0, 1, 0, 3, 1, 2, 1, 1, 0, 0, 3, 0, 1, 2, 1, 1, 0, 2, 2, 2
                ),
                listOf(
                    3, 1, 3, 2, 3, 3, 1, 3, 3, 1, 3, 2, 1, 3, 1, 3, 3, 2, 2, 2, 2, 3, 0, 0, 1, 0, 1, 1, 3, 3
                ),
                listOf(
                    1, 0, 0, 2, 3, 1, 1, 1, 0, 1, 1, 2, 1, 3, 2, 0, 0, 0, 1, 2, 2, 0, 2, 1, 2, 1, 3, 3, 2, 1
                ),
                listOf(
                    2, 0, 2, 1, 1, 1, 0, 3, 0, 2, 2, 3, 3, 0, 0, 1, 0, 3, 1, 2, 2, 2, 1, 0, 1, 0, 1, 2, 2, 2
                ),
                listOf(
                    2, 3, 0, 1, 2, 1, 3, 3, 1, 3, 0, 2, 2, 2, 2, 1, 3, 3, 2, 1, 2, 3, 3, 2, 0, 1, 1, 2, 3, 1
                ),
                listOf(
                    1, 2, 1, 2, 2, 0, 3, 2, 2, 2, 1, 3, 3, 0, 1, 2, 1, 3, 2, 3, 3, 1, 3, 3, 1, 3, 0, 0, 3, 0
                ),
                listOf(
                    3, 0, 0, 1, 0, 1, 1, 3, 0, 0, 0, 2, 0, 2, 0, 0, 1, 0, 3, 3, 1, 3, 0, 3, 3, 2, 3, 1, 2, 2
                ),
                listOf(
                    1, 3, 1, 2, 1, 3, 2, 0, 1, 2, 1, 2, 3, 3, 1, 1, 3, 0, 3, 2, 3, 2, 2, 3, 0, 3, 2, 3, 2, 2
                ),
                listOf(
                    1, 3, 3, 3, 2, 1, 3, 1, 3, 0, 3, 2, 2, 2, 3, 0, 0, 0, 1, 0, 3, 3, 2, 2, 3, 2, 1, 1, 0, 3
                ),
                listOf(
                    3, 1, 1, 3, 0, 3, 2, 0, 0, 0, 0, 3, 0, 2, 0, 0, 2, 3, 0, 2, 2, 3, 2, 1, 0, 0, 1, 3, 1, 2
                ),
                listOf(
                    3, 0, 2, 2, 1, 0, 2, 3, 3, 3, 0, 2, 0, 2, 3, 2, 2, 1, 0, 1, 1, 2, 0, 1, 2, 2, 3, 2, 1, 1
                ),
                listOf(
                    3, 0, 2, 1, 1, 3, 2, 0, 3, 2, 2, 2, 0, 1, 2, 0, 0, 3, 1, 2, 0, 0, 2, 0, 2, 3, 1, 2, 3, 3
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 25),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk60(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_001", "Hard", 10, 10, listOf(
                listOf(
                    2, 2, 1, 0, 2, 2, 3, 1, 2, 3
                ),
                listOf(
                    1, 2, 0, 0, 1, 0, 3, 3, 3, 0
                ),
                listOf(
                    2, 2, 0, 3, 2, 0, 2, 1, 2, 2
                ),
                listOf(
                    3, 0, 2, 3, 0, 0, 0, 1, 3, 1
                ),
                listOf(
                    0, 3, 2, 1, 3, 2, 0, 1, 2, 3
                ),
                listOf(
                    2, 1, 2, 1, 0, 0, 0, 2, 3, 1
                ),
                listOf(
                    0, 3, 0, 3, 2, 0, 2, 0, 2, 1
                ),
                listOf(
                    3, 0, 0, 3, 0, 2, 0, 1, 1, 3
                ),
                listOf(
                    0, 3, 0, 0, 1, 3, 1, 1, 0, 2
                ),
                listOf(
                    1, 0, 0, 1, 2, 1, 1, 1, 1, 1
                )
            ), listOf(
                Tank(0, 20),
                Tank(1, 25),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 25)
            )),
        )
    }

    private fun getChunk61(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_002", "Hard", 10, 10, listOf(
                listOf(
                    3, 0, 1, 3, 1, 0, 3, 0, 0, 0
                ),
                listOf(
                    2, 0, 0, 0, 0, 0, 1, 0, 1, 3
                ),
                listOf(
                    0, 3, 2, 1, 0, 0, 0, 0, 0, 3
                ),
                listOf(
                    2, 0, 0, 0, 1, 2, 1, 1, 3, 0
                ),
                listOf(
                    3, 2, 0, 3, 2, 3, 1, 2, 1, 1
                ),
                listOf(
                    0, 0, 0, 2, 2, 1, 3, 2, 1, 1
                ),
                listOf(
                    1, 1, 0, 3, 3, 0, 1, 2, 0, 3
                ),
                listOf(
                    2, 0, 0, 0, 0, 2, 3, 0, 0, 1
                ),
                listOf(
                    1, 3, 1, 2, 0, 0, 3, 0, 3, 0
                ),
                listOf(
                    0, 3, 0, 0, 0, 0, 0, 0, 3, 2
                )
            ), listOf(
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 25)
            )),
        )
    }

    private fun getChunk62(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_003", "Hard", 10, 10, listOf(
                listOf(
                    2, 2, 0, 0, 1, 1, 3, 3, 0, 0
                ),
                listOf(
                    3, 0, 2, 2, 3, 2, 3, 2, 2, 0
                ),
                listOf(
                    1, 0, 0, 1, 0, 3, 0, 3, 2, 2
                ),
                listOf(
                    3, 0, 0, 0, 2, 3, 2, 1, 1, 2
                ),
                listOf(
                    3, 2, 3, 1, 3, 0, 3, 1, 3, 3
                ),
                listOf(
                    0, 2, 3, 3, 3, 1, 0, 0, 0, 3
                ),
                listOf(
                    2, 2, 2, 0, 2, 0, 0, 2, 2, 2
                ),
                listOf(
                    0, 2, 0, 2, 2, 1, 2, 2, 2, 1
                ),
                listOf(
                    0, 3, 0, 3, 3, 1, 2, 0, 1, 3
                ),
                listOf(
                    2, 0, 1, 3, 3, 0, 2, 0, 0, 1
                )
            ), listOf(
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 25),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk63(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_004", "Hard", 10, 10, listOf(
                listOf(
                    2, 1, 0, 0, 1, 2, 2, 3, 3, 3
                ),
                listOf(
                    0, 3, 3, 2, 3, 3, 3, 3, 0, 0
                ),
                listOf(
                    0, 2, 3, 3, 2, 3, 2, 3, 2, 1
                ),
                listOf(
                    1, 0, 3, 3, 3, 2, 1, 1, 1, 3
                ),
                listOf(
                    1, 3, 2, 0, 1, 3, 2, 1, 3, 0
                ),
                listOf(
                    1, 0, 1, 2, 2, 0, 0, 0, 3, 1
                ),
                listOf(
                    3, 3, 2, 3, 3, 3, 0, 1, 0, 3
                ),
                listOf(
                    2, 3, 3, 3, 2, 0, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 2, 3, 0, 3, 0, 2, 0, 3
                ),
                listOf(
                    3, 2, 2, 2, 2, 2, 2, 0, 3, 2
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk64(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_005", "Hard", 10, 10, listOf(
                listOf(
                    3, 1, 3, 2, 0, 1, 2, 2, 2, 2
                ),
                listOf(
                    3, 2, 0, 0, 1, 3, 1, 3, 2, 0
                ),
                listOf(
                    2, 2, 0, 0, 1, 0, 3, 3, 2, 2
                ),
                listOf(
                    1, 1, 2, 2, 3, 3, 3, 3, 2, 0
                ),
                listOf(
                    2, 1, 0, 2, 0, 3, 1, 0, 0, 1
                ),
                listOf(
                    2, 2, 3, 2, 1, 0, 3, 3, 2, 3
                ),
                listOf(
                    3, 1, 2, 2, 0, 3, 2, 3, 1, 1
                ),
                listOf(
                    3, 0, 3, 1, 2, 0, 2, 3, 0, 2
                ),
                listOf(
                    2, 1, 1, 2, 3, 0, 2, 2, 1, 3
                ),
                listOf(
                    2, 2, 0, 3, 1, 0, 2, 2, 2, 1
                )
            ), listOf(
                Tank(3, 25),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk65(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_006", "Hard", 10, 10, listOf(
                listOf(
                    0, 2, 2, 3, 2, 2, 1, 2, 3, 2
                ),
                listOf(
                    0, 2, 2, 2, 3, 3, 2, 2, 2, 3
                ),
                listOf(
                    0, 0, 2, 1, 2, 0, 1, 2, 2, 2
                ),
                listOf(
                    0, 0, 2, 2, 0, 3, 1, 0, 2, 2
                ),
                listOf(
                    3, 1, 2, 0, 3, 0, 0, 2, 0, 1
                ),
                listOf(
                    1, 2, 0, 0, 2, 2, 2, 1, 2, 0
                ),
                listOf(
                    3, 2, 3, 1, 0, 2, 2, 0, 0, 3
                ),
                listOf(
                    1, 2, 0, 0, 1, 2, 2, 0, 2, 3
                ),
                listOf(
                    2, 2, 3, 3, 0, 0, 2, 2, 0, 2
                ),
                listOf(
                    1, 2, 3, 1, 2, 1, 2, 2, 2, 1
                )
            ), listOf(
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 25)
            )),
        )
    }

    private fun getChunk66(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_007", "Hard", 10, 10, listOf(
                listOf(
                    1, 3, 3, 0, 3, 0, 2, 3, 0, 2
                ),
                listOf(
                    0, 2, 3, 1, 0, 3, 0, 0, 2, 3
                ),
                listOf(
                    0, 2, 2, 1, 3, 1, 0, 0, 2, 0
                ),
                listOf(
                    0, 3, 2, 0, 2, 0, 3, 0, 0, 3
                ),
                listOf(
                    3, 1, 1, 3, 2, 0, 3, 3, 2, 3
                ),
                listOf(
                    3, 0, 2, 0, 1, 0, 3, 2, 0, 1
                ),
                listOf(
                    0, 3, 3, 2, 2, 0, 3, 3, 3, 3
                ),
                listOf(
                    0, 0, 1, 3, 0, 3, 1, 0, 3, 3
                ),
                listOf(
                    3, 3, 3, 3, 2, 2, 1, 2, 3, 2
                ),
                listOf(
                    1, 1, 0, 0, 1, 3, 2, 0, 3, 1
                )
            ), listOf(
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk67(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_008", "Hard", 10, 10, listOf(
                listOf(
                    1, 1, 1, 3, 3, 3, 1, 2, 3, 1
                ),
                listOf(
                    1, 3, 0, 1, 2, 0, 2, 3, 3, 1
                ),
                listOf(
                    3, 2, 3, 3, 3, 1, 1, 1, 1, 2
                ),
                listOf(
                    3, 1, 0, 0, 1, 0, 3, 2, 3, 3
                ),
                listOf(
                    2, 3, 1, 3, 2, 2, 1, 2, 2, 1
                ),
                listOf(
                    1, 1, 3, 3, 3, 3, 1, 1, 1, 3
                ),
                listOf(
                    0, 3, 3, 1, 3, 2, 2, 3, 3, 3
                ),
                listOf(
                    1, 0, 1, 2, 3, 3, 1, 1, 2, 2
                ),
                listOf(
                    1, 3, 2, 1, 2, 0, 3, 3, 0, 3
                ),
                listOf(
                    1, 1, 1, 2, 2, 1, 0, 1, 1, 3
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk68(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_009", "Hard", 10, 10, listOf(
                listOf(
                    3, 0, 0, 0, 0, 2, 0, 1, 1, 3
                ),
                listOf(
                    2, 0, 1, 0, 3, 1, 2, 2, 3, 1
                ),
                listOf(
                    2, 3, 0, 2, 0, 0, 1, 1, 0, 2
                ),
                listOf(
                    1, 3, 0, 0, 3, 2, 0, 2, 0, 1
                ),
                listOf(
                    0, 2, 2, 2, 2, 3, 2, 2, 0, 2
                ),
                listOf(
                    3, 3, 0, 0, 1, 1, 0, 0, 3, 1
                ),
                listOf(
                    1, 1, 1, 3, 2, 1, 0, 3, 2, 2
                ),
                listOf(
                    0, 0, 3, 3, 1, 1, 0, 1, 2, 2
                ),
                listOf(
                    3, 3, 2, 2, 0, 0, 0, 1, 0, 1
                ),
                listOf(
                    2, 3, 1, 1, 3, 3, 2, 0, 1, 1
                )
            ), listOf(
                Tank(2, 25),
                Tank(1, 25),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk69(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_010", "Hard", 10, 10, listOf(
                listOf(
                    1, 3, 1, 2, 3, 3, 3, 3, 1, 3
                ),
                listOf(
                    1, 1, 1, 1, 3, 0, 1, 3, 1, 1
                ),
                listOf(
                    3, 2, 3, 2, 1, 1, 1, 3, 1, 0
                ),
                listOf(
                    1, 3, 1, 1, 1, 2, 3, 2, 1, 1
                ),
                listOf(
                    0, 2, 0, 0, 2, 1, 1, 3, 3, 0
                ),
                listOf(
                    3, 3, 2, 2, 1, 1, 3, 3, 2, 0
                ),
                listOf(
                    1, 1, 0, 0, 0, 0, 1, 1, 3, 1
                ),
                listOf(
                    2, 3, 1, 0, 2, 0, 0, 0, 1, 3
                ),
                listOf(
                    0, 3, 3, 1, 2, 2, 0, 0, 0, 2
                ),
                listOf(
                    0, 0, 0, 3, 1, 0, 1, 1, 0, 0
                )
            ), listOf(
                Tank(1, 20),
                Tank(3, 25),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 25)
            )),
        )
    }

    private fun getChunk70(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_011", "Hard", 10, 10, listOf(
                listOf(
                    2, 2, 0, 1, 0, 3, 0, 3, 0, 0
                ),
                listOf(
                    1, 3, 0, 0, 3, 1, 0, 3, 3, 3
                ),
                listOf(
                    3, 1, 0, 3, 1, 3, 2, 3, 1, 0
                ),
                listOf(
                    1, 2, 3, 3, 1, 3, 3, 1, 1, 3
                ),
                listOf(
                    0, 3, 3, 3, 0, 3, 3, 3, 1, 3
                ),
                listOf(
                    0, 1, 0, 3, 0, 2, 0, 1, 2, 3
                ),
                listOf(
                    2, 1, 0, 0, 0, 0, 1, 3, 3, 0
                ),
                listOf(
                    0, 0, 0, 2, 0, 3, 3, 0, 2, 3
                ),
                listOf(
                    3, 1, 0, 3, 3, 1, 3, 3, 3, 1
                ),
                listOf(
                    0, 3, 1, 3, 0, 0, 1, 3, 3, 2
                )
            ), listOf(
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk71(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_012", "Hard", 10, 10, listOf(
                listOf(
                    3, 2, 0, 0, 2, 3, 3, 0, 2, 3
                ),
                listOf(
                    3, 3, 0, 1, 3, 3, 0, 1, 0, 2
                ),
                listOf(
                    0, 0, 1, 0, 3, 0, 3, 2, 2, 0
                ),
                listOf(
                    0, 3, 2, 2, 0, 3, 0, 3, 3, 3
                ),
                listOf(
                    0, 3, 1, 2, 3, 3, 1, 3, 0, 2
                ),
                listOf(
                    3, 1, 3, 0, 3, 3, 3, 2, 0, 3
                ),
                listOf(
                    0, 3, 1, 3, 1, 2, 2, 0, 1, 2
                ),
                listOf(
                    0, 3, 0, 3, 2, 0, 0, 3, 2, 3
                ),
                listOf(
                    0, 2, 1, 3, 2, 0, 3, 3, 0, 0
                ),
                listOf(
                    1, 2, 3, 2, 0, 1, 1, 0, 1, 1
                )
            ), listOf(
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk72(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_013", "Hard", 10, 10, listOf(
                listOf(
                    2, 0, 1, 2, 3, 2, 1, 2, 3, 3
                ),
                listOf(
                    2, 2, 3, 0, 3, 1, 0, 0, 2, 2
                ),
                listOf(
                    2, 0, 3, 1, 1, 0, 2, 2, 1, 1
                ),
                listOf(
                    2, 1, 0, 1, 3, 1, 1, 0, 1, 1
                ),
                listOf(
                    2, 1, 3, 3, 3, 0, 1, 1, 3, 1
                ),
                listOf(
                    0, 2, 3, 0, 1, 2, 1, 1, 1, 1
                ),
                listOf(
                    0, 1, 1, 3, 1, 1, 3, 1, 0, 3
                ),
                listOf(
                    3, 3, 1, 0, 1, 3, 3, 0, 2, 3
                ),
                listOf(
                    3, 0, 2, 1, 1, 3, 0, 3, 2, 0
                ),
                listOf(
                    3, 1, 2, 0, 1, 3, 2, 1, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 25)
            )),
        )
    }

    private fun getChunk73(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_014", "Hard", 10, 10, listOf(
                listOf(
                    1, 3, 3, 0, 1, 2, 3, 2, 1, 0
                ),
                listOf(
                    3, 2, 1, 3, 3, 0, 0, 3, 1, 3
                ),
                listOf(
                    1, 1, 1, 0, 0, 3, 0, 1, 1, 1
                ),
                listOf(
                    0, 0, 0, 3, 0, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 3, 0, 1, 3, 0, 3, 3, 3, 0
                ),
                listOf(
                    0, 0, 3, 0, 0, 2, 0, 0, 0, 2
                ),
                listOf(
                    1, 1, 0, 0, 1, 1, 0, 3, 0, 3
                ),
                listOf(
                    3, 2, 2, 2, 1, 2, 3, 0, 0, 2
                ),
                listOf(
                    2, 1, 0, 2, 3, 0, 2, 3, 0, 3
                ),
                listOf(
                    0, 3, 1, 0, 1, 0, 0, 2, 2, 3
                )
            ), listOf(
                Tank(2, 15),
                Tank(3, 25),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk74(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_015", "Hard", 10, 10, listOf(
                listOf(
                    1, 2, 0, 3, 0, 3, 1, 1, 1, 2
                ),
                listOf(
                    3, 1, 2, 3, 2, 3, 3, 3, 2, 3
                ),
                listOf(
                    0, 2, 3, 0, 2, 2, 3, 3, 0, 1
                ),
                listOf(
                    3, 2, 0, 3, 2, 3, 3, 1, 3, 3
                ),
                listOf(
                    1, 1, 0, 1, 3, 0, 1, 1, 2, 0
                ),
                listOf(
                    2, 3, 3, 1, 1, 1, 0, 2, 0, 2
                ),
                listOf(
                    3, 1, 2, 0, 3, 0, 0, 0, 1, 1
                ),
                listOf(
                    2, 2, 2, 2, 3, 1, 1, 2, 2, 1
                ),
                listOf(
                    1, 0, 3, 1, 3, 3, 1, 0, 2, 3
                ),
                listOf(
                    2, 3, 2, 0, 2, 0, 3, 0, 3, 1
                )
            ), listOf(
                Tank(3, 15),
                Tank(2, 25),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 25)
            )),
        )
    }

    private fun getChunk75(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_016", "Hard", 10, 10, listOf(
                listOf(
                    3, 3, 3, 3, 2, 3, 1, 2, 0, 1
                ),
                listOf(
                    0, 0, 0, 2, 3, 0, 1, 3, 0, 3
                ),
                listOf(
                    1, 0, 2, 1, 2, 0, 1, 3, 3, 1
                ),
                listOf(
                    3, 0, 2, 1, 2, 0, 3, 0, 3, 0
                ),
                listOf(
                    0, 1, 2, 1, 2, 1, 0, 2, 1, 0
                ),
                listOf(
                    0, 1, 1, 1, 0, 0, 1, 1, 2, 0
                ),
                listOf(
                    0, 2, 1, 1, 0, 0, 2, 0, 1, 0
                ),
                listOf(
                    1, 2, 0, 1, 3, 3, 3, 2, 2, 1
                ),
                listOf(
                    3, 0, 0, 0, 0, 0, 2, 0, 3, 1
                ),
                listOf(
                    1, 2, 2, 0, 0, 3, 0, 3, 2, 0
                )
            ), listOf(
                Tank(1, 25),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 25),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk76(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_017", "Hard", 10, 10, listOf(
                listOf(
                    2, 1, 1, 2, 1, 2, 3, 2, 2, 2
                ),
                listOf(
                    2, 0, 0, 0, 0, 0, 3, 0, 0, 3
                ),
                listOf(
                    2, 0, 2, 3, 1, 0, 0, 3, 1, 2
                ),
                listOf(
                    2, 2, 3, 0, 0, 3, 2, 3, 1, 0
                ),
                listOf(
                    3, 1, 0, 2, 3, 0, 0, 0, 2, 2
                ),
                listOf(
                    1, 2, 0, 1, 2, 2, 3, 2, 3, 1
                ),
                listOf(
                    0, 0, 0, 2, 2, 0, 3, 2, 2, 3
                ),
                listOf(
                    2, 3, 2, 0, 1, 3, 3, 1, 3, 3
                ),
                listOf(
                    2, 2, 3, 0, 0, 1, 1, 0, 2, 3
                ),
                listOf(
                    0, 3, 2, 0, 0, 3, 3, 0, 3, 1
                )
            ), listOf(
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 25),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk77(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_018", "Hard", 10, 10, listOf(
                listOf(
                    3, 1, 0, 1, 1, 1, 0, 1, 0, 1
                ),
                listOf(
                    3, 3, 1, 2, 0, 0, 0, 2, 1, 1
                ),
                listOf(
                    1, 0, 3, 1, 0, 3, 1, 0, 1, 1
                ),
                listOf(
                    0, 2, 1, 0, 1, 0, 3, 1, 2, 2
                ),
                listOf(
                    0, 3, 2, 0, 2, 1, 3, 0, 1, 1
                ),
                listOf(
                    0, 1, 0, 0, 0, 0, 0, 2, 0, 2
                ),
                listOf(
                    3, 1, 2, 1, 1, 0, 0, 1, 0, 2
                ),
                listOf(
                    1, 3, 0, 2, 0, 2, 3, 0, 0, 0
                ),
                listOf(
                    3, 0, 0, 0, 1, 3, 0, 0, 0, 3
                ),
                listOf(
                    1, 0, 2, 0, 1, 0, 2, 0, 3, 1
                )
            ), listOf(
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk78(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_019", "Hard", 10, 10, listOf(
                listOf(
                    3, 2, 0, 3, 0, 0, 0, 2, 0, 1
                ),
                listOf(
                    3, 3, 2, 0, 1, 2, 0, 1, 1, 3
                ),
                listOf(
                    1, 0, 2, 3, 0, 1, 0, 0, 1, 1
                ),
                listOf(
                    1, 1, 1, 2, 3, 3, 3, 0, 0, 0
                ),
                listOf(
                    1, 0, 3, 2, 0, 3, 1, 0, 2, 0
                ),
                listOf(
                    1, 2, 1, 0, 3, 1, 1, 1, 1, 1
                ),
                listOf(
                    2, 1, 2, 2, 1, 0, 2, 0, 2, 3
                ),
                listOf(
                    1, 2, 2, 0, 3, 3, 2, 3, 3, 1
                ),
                listOf(
                    1, 3, 0, 3, 3, 1, 2, 3, 2, 3
                ),
                listOf(
                    1, 2, 1, 3, 3, 0, 3, 1, 0, 1
                )
            ), listOf(
                Tank(3, 25),
                Tank(0, 25),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk79(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_hard_020", "Hard", 10, 10, listOf(
                listOf(
                    0, 1, 2, 0, 3, 1, 3, 0, 2, 2
                ),
                listOf(
                    1, 1, 2, 3, 3, 2, 1, 0, 2, 3
                ),
                listOf(
                    3, 2, 2, 3, 2, 0, 3, 0, 2, 2
                ),
                listOf(
                    3, 1, 2, 2, 0, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 0, 3, 3, 2, 1, 1, 3, 2, 2
                ),
                listOf(
                    0, 3, 0, 0, 0, 1, 3, 1, 1, 3
                ),
                listOf(
                    0, 1, 1, 2, 3, 3, 1, 3, 3, 0
                ),
                listOf(
                    2, 3, 0, 1, 3, 1, 3, 2, 3, 0
                ),
                listOf(
                    3, 3, 1, 2, 2, 0, 2, 2, 3, 3
                ),
                listOf(
                    3, 3, 0, 0, 2, 3, 2, 1, 1, 0
                )
            ), listOf(
                Tank(2, 25),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 25)
            )),
        )
    }

    private fun getChunk80(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_001", "Legendary", 40, 60, listOf(
                listOf(
                    0, 3, 2, 1, 0, 2, 2, 3, 1, 3, 1, 0, 1, 3, 3, 2, 3, 3, 0, 0, 2, 3, 1, 2, 0, 2, 2, 1, 3, 3, 2, 0, 3, 0, 2, 1, 0, 3, 1, 2
                ),
                listOf(
                    0, 1, 3, 3, 1, 2, 2, 3, 0, 1, 3, 2, 0, 1, 0, 2, 2, 0, 3, 0, 2, 2, 0, 1, 1, 0, 3, 3, 1, 0, 0, 3, 2, 3, 0, 2, 3, 2, 3, 3
                ),
                listOf(
                    0, 1, 3, 3, 3, 1, 2, 0, 0, 0, 3, 2, 1, 0, 1, 1, 1, 2, 1, 1, 0, 1, 3, 0, 0, 1, 1, 3, 0, 0, 2, 3, 2, 0, 2, 3, 2, 3, 0, 0
                ),
                listOf(
                    0, 1, 2, 3, 2, 0, 2, 0, 1, 1, 0, 0, 3, 2, 3, 3, 0, 1, 1, 3, 3, 0, 3, 0, 3, 3, 0, 1, 0, 0, 1, 0, 0, 0, 2, 3, 3, 1, 2, 2
                ),
                listOf(
                    1, 1, 0, 0, 0, 2, 0, 3, 0, 3, 2, 1, 0, 2, 2, 2, 0, 0, 3, 2, 3, 1, 0, 0, 0, 2, 1, 0, 2, 1, 1, 0, 3, 2, 0, 3, 2, 1, 1, 3
                ),
                listOf(
                    0, 0, 0, 3, 2, 2, 2, 0, 1, 1, 1, 2, 3, 2, 0, 3, 2, 0, 3, 1, 0, 0, 2, 2, 1, 2, 3, 0, 1, 1, 1, 2, 3, 2, 0, 0, 3, 2, 1, 3
                ),
                listOf(
                    2, 1, 0, 1, 1, 0, 3, 2, 2, 2, 3, 2, 0, 3, 1, 1, 3, 3, 2, 2, 0, 1, 2, 0, 1, 2, 3, 3, 1, 1, 3, 2, 2, 2, 0, 1, 2, 1, 3, 2
                ),
                listOf(
                    2, 0, 3, 0, 0, 2, 3, 1, 1, 3, 2, 2, 2, 3, 1, 1, 0, 0, 2, 3, 0, 3, 0, 3, 1, 1, 3, 2, 1, 3, 3, 1, 2, 3, 2, 3, 2, 3, 3, 2
                ),
                listOf(
                    2, 1, 2, 2, 3, 2, 0, 3, 3, 2, 0, 3, 0, 1, 1, 1, 3, 1, 3, 0, 3, 1, 0, 3, 3, 1, 1, 0, 3, 2, 2, 2, 0, 1, 3, 1, 3, 1, 1, 2
                ),
                listOf(
                    0, 1, 3, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 3, 2, 2, 1, 0, 3, 3, 1, 0, 3, 2, 0, 0, 3, 3, 1, 1, 3, 2, 3, 1, 1, 0, 3, 3
                ),
                listOf(
                    0, 3, 1, 2, 3, 0, 1, 2, 3, 3, 1, 2, 0, 1, 0, 1, 3, 1, 3, 0, 1, 2, 3, 0, 3, 3, 0, 2, 0, 3, 0, 3, 3, 2, 3, 0, 3, 0, 2, 1
                ),
                listOf(
                    0, 3, 2, 2, 3, 3, 3, 2, 3, 1, 1, 2, 3, 2, 2, 2, 3, 3, 2, 1, 2, 0, 3, 1, 2, 3, 1, 3, 3, 2, 1, 2, 3, 0, 2, 1, 0, 3, 0, 1
                ),
                listOf(
                    0, 0, 3, 3, 1, 0, 2, 1, 0, 0, 0, 3, 3, 1, 1, 3, 1, 2, 3, 3, 3, 0, 3, 2, 3, 0, 0, 2, 1, 1, 1, 3, 0, 3, 2, 2, 3, 2, 0, 2
                ),
                listOf(
                    0, 1, 3, 1, 2, 2, 1, 1, 3, 0, 0, 3, 1, 2, 0, 1, 1, 3, 1, 0, 2, 2, 2, 1, 3, 3, 1, 2, 3, 1, 3, 0, 2, 2, 3, 3, 0, 2, 1, 0
                ),
                listOf(
                    2, 0, 2, 1, 1, 3, 3, 1, 1, 0, 3, 3, 3, 0, 1, 1, 1, 0, 1, 0, 2, 1, 0, 0, 3, 2, 3, 3, 0, 0, 0, 2, 3, 2, 1, 2, 3, 3, 3, 1
                ),
                listOf(
                    0, 3, 1, 0, 3, 3, 3, 1, 2, 3, 2, 1, 1, 0, 1, 1, 0, 2, 2, 1, 2, 0, 0, 2, 2, 3, 0, 1, 1, 2, 0, 1, 3, 2, 0, 0, 1, 2, 1, 1
                ),
                listOf(
                    0, 1, 1, 3, 2, 0, 1, 2, 0, 2, 2, 1, 3, 2, 1, 2, 2, 1, 3, 0, 0, 0, 2, 3, 2, 2, 2, 2, 1, 3, 0, 3, 0, 3, 3, 0, 3, 2, 1, 0
                ),
                listOf(
                    3, 3, 2, 3, 0, 0, 0, 1, 0, 1, 1, 1, 3, 1, 2, 2, 1, 2, 0, 1, 1, 0, 1, 0, 1, 3, 3, 3, 0, 0, 2, 3, 1, 2, 0, 0, 1, 0, 2, 1
                ),
                listOf(
                    2, 3, 2, 0, 0, 0, 0, 3, 0, 0, 2, 3, 0, 0, 0, 0, 3, 3, 1, 3, 1, 0, 3, 0, 0, 3, 0, 3, 2, 3, 2, 3, 2, 1, 2, 3, 0, 2, 2, 3
                ),
                listOf(
                    2, 0, 0, 1, 3, 1, 3, 1, 1, 2, 3, 1, 1, 1, 0, 2, 3, 3, 2, 1, 3, 0, 3, 2, 2, 1, 0, 1, 1, 2, 0, 0, 3, 2, 3, 1, 2, 3, 0, 1
                ),
                listOf(
                    3, 3, 3, 0, 1, 2, 2, 1, 1, 1, 0, 2, 2, 0, 1, 0, 3, 0, 2, 3, 0, 2, 3, 0, 3, 2, 0, 1, 2, 0, 3, 3, 1, 2, 1, 0, 1, 3, 0, 3
                ),
                listOf(
                    3, 2, 1, 2, 3, 2, 2, 3, 3, 2, 1, 2, 0, 0, 0, 1, 0, 2, 0, 1, 1, 3, 3, 0, 2, 3, 2, 2, 3, 1, 2, 1, 3, 1, 3, 2, 2, 3, 0, 0
                ),
                listOf(
                    3, 2, 0, 3, 1, 0, 3, 3, 2, 2, 0, 0, 3, 2, 3, 0, 2, 1, 1, 3, 1, 3, 2, 2, 2, 1, 1, 2, 3, 1, 3, 2, 3, 1, 1, 1, 1, 3, 3, 3
                ),
                listOf(
                    3, 2, 1, 1, 3, 0, 1, 1, 2, 1, 0, 3, 3, 3, 0, 2, 2, 1, 0, 0, 0, 1, 1, 3, 0, 1, 0, 3, 0, 3, 2, 3, 3, 2, 3, 1, 3, 3, 3, 3
                ),
                listOf(
                    0, 0, 0, 2, 1, 3, 3, 1, 1, 0, 0, 3, 2, 2, 0, 0, 2, 1, 3, 3, 3, 2, 3, 0, 0, 2, 3, 1, 3, 3, 1, 2, 0, 0, 3, 2, 0, 2, 0, 0
                ),
                listOf(
                    2, 2, 0, 2, 1, 3, 0, 3, 0, 0, 1, 3, 3, 0, 0, 0, 0, 3, 2, 3, 3, 2, 0, 0, 2, 2, 2, 0, 2, 0, 2, 2, 0, 2, 3, 1, 0, 2, 3, 0
                ),
                listOf(
                    1, 0, 3, 3, 1, 0, 2, 1, 1, 2, 3, 2, 0, 2, 3, 3, 2, 3, 1, 0, 2, 1, 1, 0, 0, 0, 3, 2, 0, 3, 1, 3, 3, 0, 0, 1, 3, 2, 1, 0
                ),
                listOf(
                    2, 2, 0, 2, 1, 2, 1, 3, 1, 1, 0, 3, 0, 1, 1, 3, 1, 0, 1, 0, 1, 0, 3, 0, 3, 2, 0, 3, 0, 2, 3, 2, 1, 0, 2, 3, 2, 2, 2, 2
                ),
                listOf(
                    2, 3, 3, 2, 3, 0, 2, 2, 0, 2, 2, 0, 3, 1, 3, 3, 3, 1, 2, 2, 2, 1, 0, 1, 2, 2, 0, 0, 0, 3, 3, 2, 0, 0, 2, 1, 1, 2, 1, 1
                ),
                listOf(
                    0, 3, 2, 1, 0, 3, 0, 0, 2, 1, 3, 2, 1, 0, 1, 1, 1, 2, 2, 0, 2, 0, 1, 1, 0, 1, 3, 2, 1, 2, 0, 3, 1, 2, 1, 1, 1, 1, 0, 2
                ),
                listOf(
                    0, 3, 3, 0, 0, 1, 0, 1, 1, 3, 3, 2, 2, 0, 3, 3, 0, 3, 2, 2, 2, 3, 3, 2, 0, 3, 0, 1, 1, 3, 3, 0, 1, 1, 1, 2, 3, 2, 3, 0
                ),
                listOf(
                    2, 0, 0, 3, 0, 0, 0, 0, 0, 3, 1, 0, 1, 1, 0, 0, 1, 0, 3, 1, 2, 2, 2, 2, 2, 2, 1, 3, 0, 1, 3, 2, 1, 3, 0, 3, 1, 2, 0, 1
                ),
                listOf(
                    0, 3, 0, 3, 3, 0, 3, 2, 0, 1, 2, 1, 2, 2, 2, 3, 0, 2, 3, 2, 2, 1, 3, 0, 2, 0, 0, 3, 0, 1, 3, 2, 3, 0, 1, 2, 1, 2, 1, 3
                ),
                listOf(
                    0, 0, 2, 3, 2, 2, 0, 0, 3, 3, 1, 0, 0, 1, 3, 2, 2, 1, 2, 3, 1, 3, 0, 1, 1, 1, 2, 1, 0, 1, 0, 1, 3, 1, 1, 0, 3, 3, 1, 0
                ),
                listOf(
                    2, 3, 2, 0, 2, 3, 1, 3, 2, 3, 1, 0, 0, 2, 2, 2, 2, 0, 2, 1, 3, 3, 3, 1, 0, 1, 0, 1, 3, 0, 3, 3, 1, 3, 0, 1, 3, 1, 1, 2
                ),
                listOf(
                    2, 0, 2, 2, 3, 1, 1, 0, 3, 3, 0, 0, 0, 1, 0, 2, 0, 2, 1, 3, 3, 0, 2, 1, 0, 0, 1, 3, 3, 3, 2, 0, 1, 3, 2, 0, 0, 1, 3, 2
                ),
                listOf(
                    2, 2, 2, 1, 2, 3, 3, 0, 0, 3, 2, 0, 0, 2, 0, 0, 3, 3, 0, 0, 1, 3, 2, 2, 1, 2, 0, 1, 2, 3, 0, 0, 2, 2, 0, 3, 3, 0, 1, 3
                ),
                listOf(
                    0, 2, 1, 2, 2, 1, 0, 1, 2, 1, 2, 0, 1, 2, 2, 0, 3, 1, 0, 3, 2, 1, 0, 2, 2, 0, 0, 3, 0, 1, 1, 2, 2, 3, 1, 3, 1, 2, 2, 0
                ),
                listOf(
                    2, 1, 3, 0, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 0, 1, 1, 2, 0, 1, 3, 0, 0, 2, 2, 3, 1, 0, 1, 0, 0, 2, 0, 3, 2, 1, 1, 2, 3, 1
                ),
                listOf(
                    1, 3, 0, 0, 0, 0, 1, 3, 1, 3, 1, 1, 2, 0, 2, 3, 3, 2, 1, 2, 2, 1, 0, 0, 3, 1, 0, 3, 3, 3, 3, 0, 3, 1, 1, 2, 0, 0, 0, 3
                ),
                listOf(
                    0, 2, 3, 1, 3, 1, 2, 0, 3, 3, 2, 2, 3, 1, 3, 2, 0, 0, 1, 1, 1, 1, 3, 0, 3, 1, 3, 2, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 3, 1
                ),
                listOf(
                    1, 2, 0, 0, 1, 3, 1, 0, 0, 1, 3, 2, 3, 2, 0, 0, 3, 1, 2, 0, 2, 2, 1, 0, 0, 1, 2, 2, 1, 3, 2, 0, 0, 0, 2, 0, 3, 3, 0, 0
                ),
                listOf(
                    3, 2, 2, 0, 1, 3, 3, 0, 0, 0, 2, 0, 1, 3, 2, 3, 0, 0, 3, 1, 2, 1, 2, 0, 1, 1, 1, 1, 3, 2, 2, 3, 2, 2, 3, 1, 1, 1, 3, 3
                ),
                listOf(
                    1, 2, 0, 0, 1, 3, 0, 1, 1, 3, 0, 2, 0, 3, 1, 2, 3, 1, 3, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 1, 1, 2, 1
                ),
                listOf(
                    0, 0, 3, 1, 3, 0, 3, 1, 0, 0, 3, 1, 3, 3, 0, 1, 2, 1, 1, 2, 1, 0, 1, 3, 1, 3, 2, 3, 2, 0, 3, 2, 0, 2, 0, 3, 1, 2, 1, 0
                ),
                listOf(
                    3, 0, 2, 2, 2, 3, 2, 0, 3, 1, 0, 1, 3, 1, 2, 1, 2, 2, 3, 3, 3, 1, 3, 0, 3, 0, 1, 2, 2, 0, 0, 0, 3, 1, 0, 2, 0, 2, 0, 0
                ),
                listOf(
                    2, 0, 1, 0, 0, 3, 2, 2, 3, 3, 0, 2, 3, 3, 0, 1, 3, 3, 2, 1, 1, 3, 3, 2, 3, 2, 3, 1, 1, 1, 3, 2, 0, 1, 2, 3, 1, 2, 0, 3
                ),
                listOf(
                    1, 0, 1, 1, 3, 3, 1, 2, 1, 0, 3, 3, 3, 2, 0, 1, 1, 3, 3, 1, 2, 3, 3, 0, 3, 2, 2, 3, 3, 2, 3, 0, 3, 0, 0, 0, 0, 3, 0, 2
                ),
                listOf(
                    3, 3, 3, 0, 0, 2, 2, 3, 1, 3, 2, 1, 1, 1, 3, 0, 1, 3, 2, 2, 1, 1, 1, 2, 2, 0, 3, 3, 3, 3, 1, 0, 0, 0, 3, 0, 0, 1, 1, 3
                ),
                listOf(
                    1, 0, 2, 3, 2, 2, 3, 3, 2, 0, 3, 0, 0, 1, 1, 2, 1, 3, 1, 1, 1, 0, 2, 0, 0, 0, 1, 2, 3, 3, 3, 0, 2, 2, 1, 2, 0, 0, 3, 3
                ),
                listOf(
                    1, 2, 2, 2, 2, 0, 0, 1, 3, 3, 2, 1, 1, 3, 1, 2, 1, 3, 2, 3, 1, 1, 3, 0, 0, 2, 1, 3, 1, 0, 3, 1, 3, 3, 3, 2, 3, 2, 0, 1
                ),
                listOf(
                    1, 0, 3, 2, 3, 2, 1, 2, 0, 0, 2, 0, 3, 1, 0, 0, 1, 0, 0, 1, 3, 3, 1, 1, 0, 2, 0, 3, 3, 3, 3, 0, 0, 1, 3, 0, 0, 0, 3, 1
                ),
                listOf(
                    1, 3, 3, 3, 1, 3, 0, 1, 3, 3, 0, 2, 3, 1, 3, 2, 0, 0, 3, 0, 1, 3, 2, 2, 3, 0, 3, 0, 1, 2, 3, 1, 2, 1, 3, 0, 1, 0, 0, 0
                ),
                listOf(
                    1, 1, 0, 0, 3, 3, 3, 3, 0, 0, 3, 1, 1, 3, 3, 1, 1, 0, 1, 0, 3, 0, 0, 2, 0, 0, 1, 1, 2, 2, 3, 0, 0, 2, 2, 2, 0, 0, 2, 0
                ),
                listOf(
                    3, 2, 0, 3, 0, 3, 1, 0, 2, 1, 2, 3, 2, 3, 2, 0, 0, 0, 0, 3, 0, 2, 3, 0, 1, 0, 1, 1, 0, 0, 3, 2, 1, 0, 2, 2, 3, 3, 1, 3
                ),
                listOf(
                    1, 0, 0, 3, 1, 3, 3, 3, 2, 1, 0, 0, 1, 0, 2, 3, 0, 3, 2, 2, 3, 0, 1, 3, 1, 0, 3, 0, 2, 3, 0, 0, 3, 1, 0, 3, 3, 3, 2, 3
                ),
                listOf(
                    1, 3, 2, 3, 1, 3, 1, 3, 3, 0, 2, 1, 3, 2, 3, 2, 3, 1, 0, 0, 3, 3, 3, 1, 0, 1, 0, 1, 1, 3, 0, 3, 2, 3, 2, 2, 2, 0, 2, 0
                ),
                listOf(
                    2, 2, 3, 2, 3, 0, 2, 0, 1, 1, 0, 3, 3, 3, 2, 1, 0, 2, 0, 3, 0, 2, 0, 3, 2, 3, 3, 2, 3, 0, 0, 3, 0, 0, 3, 1, 2, 1, 0, 0
                ),
                listOf(
                    1, 3, 2, 0, 0, 0, 2, 0, 1, 1, 0, 3, 1, 1, 3, 2, 1, 3, 2, 0, 2, 2, 1, 3, 1, 1, 2, 3, 3, 1, 2, 2, 2, 3, 0, 0, 3, 3, 2, 1
                ),
                listOf(
                    0, 2, 1, 0, 2, 0, 1, 3, 2, 3, 0, 1, 2, 2, 1, 1, 2, 0, 0, 1, 2, 0, 3, 3, 0, 1, 2, 1, 3, 3, 3, 1, 2, 3, 3, 0, 1, 3, 0, 3
                )
            ), listOf(
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 25),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk81(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_002", "Legendary", 40, 60, listOf(
                listOf(
                    3, 2, 0, 0, 0, 2, 0, 2, 3, 3, 2, 2, 0, 1, 0, 0, 3, 0, 1, 3, 1, 1, 2, 1, 1, 1, 3, 2, 2, 3, 0, 2, 0, 3, 2, 1, 1, 1, 2, 2
                ),
                listOf(
                    2, 2, 1, 2, 0, 2, 2, 1, 2, 2, 1, 0, 0, 3, 0, 0, 1, 2, 2, 2, 1, 3, 1, 1, 3, 1, 1, 0, 2, 2, 1, 1, 0, 3, 0, 0, 2, 1, 3, 1
                ),
                listOf(
                    0, 0, 2, 2, 0, 0, 3, 1, 2, 3, 1, 1, 1, 3, 0, 3, 2, 3, 0, 1, 3, 2, 1, 2, 1, 2, 2, 3, 1, 3, 0, 2, 1, 0, 1, 0, 0, 0, 0, 2
                ),
                listOf(
                    3, 1, 3, 3, 1, 3, 3, 0, 1, 1, 1, 2, 3, 1, 1, 2, 1, 2, 1, 3, 3, 1, 2, 2, 3, 3, 3, 2, 0, 1, 2, 1, 3, 3, 1, 1, 1, 2, 0, 2
                ),
                listOf(
                    3, 0, 0, 0, 1, 1, 1, 3, 3, 0, 3, 3, 2, 0, 0, 0, 2, 0, 2, 1, 3, 3, 1, 3, 0, 2, 2, 3, 1, 0, 2, 2, 1, 1, 0, 3, 1, 0, 2, 1
                ),
                listOf(
                    1, 1, 3, 1, 2, 0, 1, 3, 1, 3, 3, 1, 3, 2, 2, 2, 3, 0, 1, 1, 0, 1, 2, 1, 2, 2, 2, 2, 1, 1, 3, 3, 2, 2, 0, 3, 2, 1, 3, 1
                ),
                listOf(
                    2, 0, 0, 1, 1, 0, 2, 3, 3, 2, 2, 3, 1, 2, 2, 2, 2, 3, 0, 1, 3, 0, 0, 0, 2, 1, 2, 2, 0, 2, 1, 0, 0, 1, 2, 3, 0, 2, 1, 3
                ),
                listOf(
                    3, 3, 0, 0, 1, 3, 0, 2, 3, 0, 2, 1, 3, 3, 0, 1, 0, 1, 3, 3, 3, 1, 2, 0, 0, 1, 2, 2, 2, 0, 1, 2, 2, 1, 0, 1, 2, 1, 2, 2
                ),
                listOf(
                    0, 2, 0, 1, 1, 1, 3, 3, 0, 0, 2, 3, 2, 3, 0, 0, 3, 2, 2, 3, 3, 3, 3, 3, 0, 1, 2, 1, 1, 2, 3, 2, 2, 2, 0, 2, 3, 0, 1, 1
                ),
                listOf(
                    1, 1, 0, 2, 2, 3, 2, 0, 3, 0, 1, 0, 3, 3, 3, 1, 3, 1, 1, 3, 2, 3, 1, 3, 3, 0, 2, 1, 1, 3, 2, 3, 2, 3, 1, 3, 0, 1, 0, 3
                ),
                listOf(
                    2, 1, 0, 2, 3, 3, 2, 1, 0, 3, 0, 1, 0, 2, 1, 0, 0, 3, 1, 0, 3, 1, 0, 0, 3, 2, 0, 2, 3, 0, 2, 1, 1, 1, 3, 2, 3, 3, 0, 2
                ),
                listOf(
                    3, 3, 0, 1, 0, 1, 1, 1, 3, 2, 0, 1, 1, 3, 1, 0, 1, 1, 2, 0, 0, 3, 3, 2, 2, 0, 1, 2, 0, 3, 1, 3, 1, 1, 0, 3, 1, 0, 2, 3
                ),
                listOf(
                    3, 3, 0, 1, 2, 1, 1, 3, 0, 0, 3, 0, 2, 0, 2, 0, 0, 1, 3, 0, 0, 2, 3, 2, 0, 2, 0, 2, 0, 2, 1, 1, 1, 1, 2, 1, 0, 2, 1, 0
                ),
                listOf(
                    2, 1, 3, 1, 3, 0, 1, 2, 3, 2, 2, 0, 1, 1, 3, 2, 1, 3, 1, 2, 1, 1, 2, 0, 2, 2, 1, 0, 1, 2, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2
                ),
                listOf(
                    2, 1, 2, 2, 1, 0, 0, 2, 0, 2, 2, 2, 2, 2, 2, 0, 0, 3, 1, 2, 0, 2, 0, 3, 1, 0, 2, 3, 3, 2, 3, 3, 1, 1, 0, 1, 1, 1, 3, 1
                ),
                listOf(
                    0, 2, 0, 3, 2, 1, 0, 2, 0, 1, 1, 0, 3, 1, 0, 0, 2, 3, 1, 2, 3, 2, 2, 3, 3, 1, 2, 3, 0, 1, 1, 0, 2, 3, 1, 0, 3, 0, 0, 1
                ),
                listOf(
                    2, 3, 0, 2, 0, 3, 3, 2, 1, 0, 0, 1, 2, 1, 2, 2, 3, 2, 0, 2, 1, 1, 1, 0, 2, 3, 2, 1, 0, 1, 2, 2, 3, 0, 1, 0, 1, 2, 3, 0
                ),
                listOf(
                    1, 0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 2, 1, 1, 3, 1, 2, 2, 0, 3, 3, 1, 2, 1, 3, 1, 2, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 3, 2
                ),
                listOf(
                    3, 0, 2, 0, 1, 3, 0, 1, 3, 3, 2, 2, 2, 3, 0, 1, 2, 2, 2, 0, 0, 3, 2, 1, 2, 2, 0, 3, 2, 0, 2, 1, 3, 2, 3, 1, 2, 2, 3, 0
                ),
                listOf(
                    2, 2, 2, 3, 0, 0, 3, 3, 2, 2, 1, 0, 2, 2, 0, 3, 2, 1, 3, 0, 2, 3, 1, 0, 0, 2, 0, 1, 2, 1, 0, 3, 1, 3, 0, 1, 0, 2, 3, 1
                ),
                listOf(
                    0, 1, 3, 2, 1, 1, 2, 2, 1, 1, 0, 2, 0, 1, 3, 2, 2, 1, 0, 1, 0, 0, 2, 1, 3, 1, 3, 2, 0, 3, 1, 0, 1, 1, 3, 0, 0, 0, 3, 0
                ),
                listOf(
                    1, 2, 0, 0, 1, 0, 0, 3, 0, 1, 2, 0, 0, 1, 0, 1, 0, 0, 3, 0, 1, 1, 1, 2, 2, 2, 1, 3, 2, 1, 3, 2, 2, 2, 2, 0, 3, 1, 2, 1
                ),
                listOf(
                    2, 0, 0, 1, 3, 2, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 2, 2, 3, 3, 1, 1, 1, 3, 2, 2, 1, 0, 3, 0, 0, 2, 1, 3, 3, 2, 2, 3, 1, 1
                ),
                listOf(
                    2, 3, 0, 2, 1, 3, 0, 1, 3, 1, 2, 3, 3, 0, 2, 1, 1, 0, 2, 2, 2, 1, 2, 3, 2, 1, 2, 0, 3, 1, 1, 1, 2, 1, 2, 3, 2, 1, 2, 3
                ),
                listOf(
                    3, 2, 2, 2, 1, 3, 2, 3, 2, 0, 3, 2, 1, 1, 2, 2, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 2, 0, 3, 1, 2, 0, 0, 1, 1, 2, 0, 1, 2, 3
                ),
                listOf(
                    3, 3, 1, 3, 2, 0, 0, 1, 3, 2, 0, 1, 1, 1, 2, 0, 0, 2, 3, 1, 1, 3, 0, 1, 3, 2, 3, 3, 0, 1, 0, 0, 2, 1, 2, 1, 3, 2, 1, 3
                ),
                listOf(
                    0, 0, 0, 1, 1, 3, 3, 2, 1, 1, 0, 3, 2, 3, 3, 2, 3, 1, 1, 1, 2, 0, 2, 1, 0, 3, 2, 0, 0, 3, 2, 3, 2, 1, 3, 0, 2, 2, 0, 2
                ),
                listOf(
                    3, 0, 3, 0, 3, 3, 1, 0, 2, 1, 3, 2, 1, 3, 0, 1, 2, 2, 1, 3, 3, 3, 1, 2, 1, 3, 1, 0, 3, 0, 2, 1, 3, 2, 3, 2, 2, 1, 3, 2
                ),
                listOf(
                    0, 0, 0, 3, 1, 0, 2, 1, 3, 2, 2, 0, 0, 2, 0, 0, 1, 1, 1, 3, 3, 2, 0, 1, 2, 3, 3, 3, 2, 3, 2, 0, 3, 2, 2, 0, 3, 3, 3, 1
                ),
                listOf(
                    0, 0, 2, 2, 2, 1, 1, 2, 2, 2, 0, 2, 2, 2, 2, 0, 1, 0, 2, 2, 0, 2, 0, 2, 3, 2, 0, 1, 1, 2, 3, 2, 1, 0, 0, 2, 0, 3, 3, 1
                ),
                listOf(
                    2, 2, 1, 2, 2, 2, 3, 2, 0, 1, 1, 2, 2, 0, 2, 2, 1, 1, 0, 1, 1, 2, 0, 0, 0, 3, 1, 3, 1, 2, 1, 1, 2, 0, 2, 1, 1, 3, 3, 2
                ),
                listOf(
                    1, 2, 2, 2, 3, 2, 1, 2, 0, 1, 2, 2, 1, 2, 0, 3, 0, 3, 3, 2, 0, 3, 1, 1, 0, 2, 1, 1, 1, 3, 3, 1, 2, 1, 0, 1, 2, 2, 1, 0
                ),
                listOf(
                    3, 1, 2, 1, 1, 2, 3, 3, 3, 3, 3, 2, 0, 3, 2, 1, 1, 0, 0, 3, 2, 2, 2, 0, 3, 3, 2, 2, 1, 3, 1, 0, 0, 1, 1, 2, 1, 3, 1, 1
                ),
                listOf(
                    2, 2, 1, 1, 2, 2, 3, 1, 2, 3, 2, 0, 3, 1, 1, 2, 0, 2, 1, 1, 2, 1, 1, 1, 0, 1, 0, 1, 1, 2, 1, 1, 2, 1, 3, 1, 1, 2, 2, 2
                ),
                listOf(
                    0, 3, 3, 3, 2, 1, 3, 1, 1, 2, 3, 3, 0, 1, 2, 0, 1, 2, 3, 1, 3, 2, 3, 3, 3, 3, 3, 2, 1, 0, 1, 2, 2, 1, 2, 2, 1, 1, 3, 1
                ),
                listOf(
                    2, 3, 1, 3, 3, 2, 0, 2, 0, 0, 1, 3, 3, 0, 0, 2, 2, 1, 3, 1, 2, 1, 1, 2, 3, 3, 1, 3, 3, 1, 0, 1, 3, 3, 1, 0, 3, 2, 1, 0
                ),
                listOf(
                    2, 3, 3, 2, 3, 3, 1, 1, 3, 3, 2, 3, 0, 0, 0, 0, 1, 0, 1, 3, 2, 1, 1, 3, 0, 2, 3, 2, 1, 1, 3, 0, 1, 2, 1, 2, 3, 0, 1, 3
                ),
                listOf(
                    3, 2, 0, 1, 2, 3, 3, 3, 3, 0, 2, 0, 1, 1, 1, 3, 1, 3, 1, 2, 3, 2, 1, 2, 3, 2, 1, 3, 0, 1, 2, 1, 3, 0, 2, 0, 3, 0, 0, 3
                ),
                listOf(
                    3, 1, 0, 2, 3, 0, 3, 3, 1, 1, 1, 1, 1, 3, 0, 3, 0, 2, 1, 1, 2, 1, 1, 3, 2, 0, 2, 0, 2, 3, 2, 1, 3, 0, 2, 2, 1, 3, 3, 3
                ),
                listOf(
                    3, 0, 2, 3, 0, 0, 1, 0, 1, 2, 1, 2, 1, 3, 1, 3, 2, 3, 2, 0, 3, 2, 3, 2, 0, 2, 1, 0, 3, 1, 1, 0, 3, 1, 2, 2, 3, 1, 0, 1
                ),
                listOf(
                    2, 2, 0, 1, 2, 0, 0, 2, 1, 1, 0, 3, 2, 1, 0, 3, 3, 2, 3, 1, 0, 3, 0, 0, 2, 2, 3, 0, 3, 1, 2, 0, 2, 0, 3, 2, 2, 1, 3, 0
                ),
                listOf(
                    0, 0, 2, 3, 1, 0, 1, 2, 0, 2, 0, 3, 3, 3, 2, 2, 2, 3, 0, 2, 0, 0, 0, 0, 3, 3, 1, 1, 2, 1, 0, 2, 2, 1, 1, 0, 3, 1, 3, 3
                ),
                listOf(
                    2, 3, 0, 0, 1, 3, 2, 1, 1, 1, 1, 0, 1, 3, 0, 2, 2, 3, 2, 3, 3, 0, 3, 2, 1, 3, 0, 1, 2, 1, 0, 1, 0, 1, 3, 2, 1, 2, 3, 3
                ),
                listOf(
                    3, 2, 2, 1, 0, 2, 3, 1, 0, 3, 1, 1, 2, 3, 2, 2, 2, 3, 0, 3, 3, 0, 3, 0, 2, 3, 0, 1, 1, 0, 3, 0, 3, 1, 1, 2, 3, 2, 2, 1
                ),
                listOf(
                    2, 3, 1, 3, 0, 3, 0, 0, 3, 3, 0, 0, 2, 3, 0, 1, 1, 0, 3, 0, 0, 3, 1, 0, 1, 2, 2, 0, 3, 1, 0, 2, 0, 2, 2, 0, 0, 3, 2, 1
                ),
                listOf(
                    1, 3, 2, 1, 3, 3, 0, 2, 2, 3, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 3, 0, 0, 1, 3, 1, 0, 1, 2, 2, 2, 1, 1, 0, 3, 1, 2, 3, 1
                ),
                listOf(
                    2, 2, 3, 0, 1, 1, 0, 3, 1, 1, 3, 0, 1, 3, 0, 2, 1, 3, 0, 0, 1, 0, 3, 2, 3, 2, 1, 0, 3, 0, 0, 1, 1, 2, 3, 3, 1, 2, 1, 2
                ),
                listOf(
                    3, 1, 0, 1, 3, 3, 1, 1, 1, 0, 1, 3, 2, 1, 0, 3, 0, 0, 2, 2, 1, 0, 1, 0, 3, 1, 1, 1, 2, 3, 1, 3, 3, 0, 3, 2, 1, 2, 3, 1
                ),
                listOf(
                    3, 3, 2, 2, 1, 0, 2, 2, 2, 2, 3, 2, 3, 1, 0, 1, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 3, 2, 0, 1, 3, 1, 0, 1, 3, 1, 2, 3, 2
                ),
                listOf(
                    3, 3, 3, 3, 1, 3, 0, 1, 1, 3, 3, 0, 0, 0, 2, 3, 1, 2, 3, 2, 1, 2, 2, 0, 0, 0, 3, 1, 3, 0, 2, 3, 0, 3, 3, 2, 1, 3, 0, 2
                ),
                listOf(
                    2, 0, 3, 3, 2, 1, 1, 3, 3, 0, 0, 1, 1, 2, 0, 3, 1, 1, 3, 0, 1, 0, 1, 1, 2, 1, 1, 2, 3, 3, 3, 1, 0, 0, 0, 2, 1, 2, 2, 1
                ),
                listOf(
                    0, 2, 3, 3, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 2, 1, 3, 1, 3, 0, 0, 1, 3, 0, 2, 1, 2, 3, 0, 2, 0, 2, 0, 3, 0, 3, 0, 2, 1, 1
                ),
                listOf(
                    2, 1, 2, 3, 2, 2, 3, 2, 0, 2, 3, 2, 3, 0, 3, 3, 2, 1, 2, 0, 0, 2, 1, 2, 2, 1, 3, 2, 2, 0, 3, 2, 2, 1, 1, 3, 0, 2, 0, 1
                ),
                listOf(
                    3, 1, 2, 1, 3, 1, 1, 1, 0, 3, 2, 1, 2, 3, 0, 3, 3, 0, 1, 3, 1, 3, 1, 3, 1, 2, 1, 2, 1, 0, 2, 3, 0, 1, 3, 0, 2, 1, 1, 1
                ),
                listOf(
                    1, 3, 0, 3, 0, 1, 3, 2, 3, 3, 1, 1, 0, 3, 1, 1, 2, 1, 3, 0, 0, 1, 3, 0, 2, 1, 2, 0, 2, 1, 2, 2, 0, 0, 3, 3, 1, 2, 3, 1
                ),
                listOf(
                    3, 2, 0, 1, 1, 1, 2, 0, 0, 0, 3, 2, 1, 1, 2, 2, 1, 3, 2, 1, 0, 2, 1, 0, 1, 0, 0, 1, 1, 2, 2, 2, 1, 0, 2, 3, 1, 3, 2, 2
                ),
                listOf(
                    1, 1, 1, 2, 3, 1, 0, 2, 1, 1, 2, 1, 2, 0, 0, 0, 2, 1, 0, 3, 3, 1, 1, 3, 3, 2, 0, 1, 0, 0, 0, 0, 0, 0, 3, 3, 0, 2, 1, 3
                ),
                listOf(
                    0, 2, 3, 2, 3, 1, 3, 0, 1, 1, 2, 2, 1, 0, 1, 3, 0, 3, 1, 3, 3, 1, 0, 3, 0, 1, 1, 0, 1, 2, 1, 1, 1, 2, 1, 1, 0, 0, 0, 2
                ),
                listOf(
                    2, 1, 0, 2, 3, 2, 0, 0, 1, 0, 1, 0, 3, 0, 0, 3, 2, 1, 3, 3, 1, 2, 3, 1, 2, 1, 0, 0, 2, 1, 1, 0, 3, 3, 1, 2, 3, 2, 1, 3
                ),
                listOf(
                    2, 0, 2, 0, 1, 2, 2, 0, 3, 1, 0, 1, 2, 3, 3, 3, 1, 3, 1, 3, 2, 1, 0, 1, 2, 1, 3, 3, 3, 0, 0, 3, 3, 0, 0, 2, 0, 1, 2, 2
                )
            ), listOf(
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 25),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 25),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk82(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_003", "Legendary", 40, 60, listOf(
                listOf(
                    3, 3, 0, 1, 3, 0, 3, 1, 0, 2, 0, 0, 0, 2, 0, 2, 1, 3, 0, 0, 3, 2, 0, 2, 1, 0, 2, 0, 1, 2, 2, 2, 1, 3, 0, 0, 2, 0, 3, 2
                ),
                listOf(
                    0, 2, 2, 1, 3, 0, 0, 1, 0, 0, 0, 0, 2, 2, 0, 3, 0, 3, 1, 2, 1, 0, 0, 2, 1, 2, 3, 1, 3, 3, 1, 2, 3, 2, 2, 3, 3, 1, 2, 1
                ),
                listOf(
                    3, 1, 0, 0, 0, 0, 2, 3, 3, 3, 1, 3, 2, 3, 3, 1, 2, 3, 0, 0, 0, 1, 1, 2, 3, 0, 1, 2, 0, 0, 1, 0, 2, 2, 3, 0, 1, 3, 3, 2
                ),
                listOf(
                    0, 3, 3, 1, 2, 1, 2, 3, 0, 3, 2, 1, 2, 0, 2, 3, 0, 1, 2, 0, 2, 0, 1, 0, 1, 3, 0, 0, 0, 2, 3, 2, 1, 2, 1, 2, 0, 0, 1, 1
                ),
                listOf(
                    2, 1, 2, 1, 0, 1, 3, 0, 3, 1, 3, 0, 1, 3, 3, 3, 0, 2, 1, 3, 0, 3, 2, 0, 2, 2, 1, 3, 0, 3, 0, 3, 2, 0, 1, 3, 0, 0, 2, 3
                ),
                listOf(
                    0, 3, 1, 3, 2, 3, 1, 1, 3, 3, 2, 2, 3, 3, 0, 0, 2, 2, 1, 2, 1, 0, 1, 1, 1, 0, 0, 2, 0, 0, 3, 1, 1, 2, 1, 3, 3, 3, 1, 2
                ),
                listOf(
                    2, 0, 1, 1, 1, 0, 0, 0, 0, 3, 2, 1, 1, 2, 1, 1, 0, 1, 3, 1, 2, 1, 3, 3, 1, 1, 0, 0, 0, 2, 1, 3, 0, 0, 0, 1, 0, 1, 3, 1
                ),
                listOf(
                    2, 1, 0, 0, 0, 2, 1, 0, 1, 3, 2, 0, 2, 0, 1, 1, 1, 3, 2, 3, 0, 0, 0, 3, 0, 1, 0, 1, 1, 3, 2, 0, 1, 0, 3, 0, 2, 3, 3, 2
                ),
                listOf(
                    2, 1, 0, 0, 0, 2, 0, 1, 1, 0, 1, 2, 3, 3, 1, 1, 1, 3, 1, 0, 2, 3, 0, 0, 1, 1, 0, 1, 3, 1, 2, 0, 0, 2, 1, 1, 0, 2, 2, 2
                ),
                listOf(
                    1, 2, 3, 0, 2, 2, 1, 0, 1, 1, 0, 3, 2, 0, 0, 3, 3, 2, 3, 1, 1, 1, 2, 1, 2, 3, 0, 3, 0, 2, 3, 2, 2, 1, 0, 0, 1, 1, 0, 2
                ),
                listOf(
                    1, 0, 1, 0, 2, 1, 0, 0, 0, 3, 3, 3, 0, 3, 1, 2, 1, 1, 0, 3, 1, 3, 0, 1, 1, 1, 2, 2, 1, 0, 1, 0, 0, 2, 0, 2, 1, 3, 2, 2
                ),
                listOf(
                    3, 2, 0, 3, 2, 1, 1, 0, 2, 1, 1, 2, 3, 0, 1, 2, 1, 2, 2, 2, 3, 3, 3, 2, 3, 1, 0, 2, 1, 0, 2, 0, 1, 1, 3, 2, 1, 3, 2, 2
                ),
                listOf(
                    3, 3, 1, 0, 1, 0, 3, 3, 0, 3, 1, 3, 1, 2, 2, 1, 1, 1, 0, 3, 0, 3, 1, 2, 1, 0, 1, 3, 1, 2, 2, 3, 0, 1, 1, 1, 0, 3, 0, 1
                ),
                listOf(
                    0, 0, 0, 2, 2, 1, 2, 0, 2, 2, 3, 0, 2, 3, 3, 0, 3, 2, 2, 1, 1, 3, 3, 2, 2, 3, 3, 3, 3, 0, 2, 1, 0, 0, 0, 2, 2, 3, 2, 1
                ),
                listOf(
                    0, 3, 3, 2, 0, 2, 2, 2, 1, 1, 1, 0, 2, 2, 0, 3, 3, 1, 0, 2, 2, 2, 0, 0, 3, 2, 2, 2, 2, 3, 3, 1, 0, 0, 0, 0, 0, 3, 0, 1
                ),
                listOf(
                    3, 1, 0, 2, 2, 3, 3, 0, 2, 2, 2, 0, 3, 0, 0, 1, 2, 0, 2, 1, 1, 2, 3, 1, 1, 0, 2, 2, 1, 1, 1, 1, 2, 0, 2, 1, 0, 0, 3, 2
                ),
                listOf(
                    2, 0, 2, 1, 2, 2, 1, 1, 1, 1, 1, 0, 0, 1, 3, 3, 0, 3, 1, 2, 2, 0, 1, 0, 3, 2, 2, 1, 0, 3, 2, 3, 1, 2, 3, 3, 2, 2, 2, 1
                ),
                listOf(
                    1, 1, 2, 2, 2, 2, 2, 0, 1, 3, 1, 2, 0, 2, 3, 2, 2, 3, 3, 3, 1, 1, 1, 2, 2, 3, 2, 1, 2, 3, 1, 2, 1, 0, 2, 2, 1, 2, 0, 0
                ),
                listOf(
                    0, 1, 0, 2, 0, 3, 3, 0, 1, 3, 1, 2, 1, 1, 3, 1, 3, 1, 3, 0, 1, 2, 1, 2, 2, 3, 1, 0, 0, 3, 2, 3, 1, 0, 2, 2, 3, 1, 2, 2
                ),
                listOf(
                    2, 2, 3, 3, 3, 3, 0, 1, 0, 2, 2, 1, 0, 3, 2, 1, 0, 0, 1, 0, 3, 2, 3, 0, 2, 0, 2, 1, 3, 3, 2, 3, 0, 3, 0, 2, 3, 3, 0, 3
                ),
                listOf(
                    0, 3, 3, 0, 2, 3, 3, 2, 1, 0, 3, 2, 3, 1, 2, 3, 0, 2, 1, 1, 0, 3, 1, 2, 3, 3, 1, 3, 2, 0, 2, 2, 0, 0, 3, 3, 1, 0, 0, 3
                ),
                listOf(
                    1, 1, 1, 3, 0, 2, 1, 0, 3, 0, 3, 2, 0, 0, 1, 1, 3, 2, 2, 1, 0, 1, 1, 0, 1, 1, 2, 0, 1, 0, 1, 2, 1, 0, 2, 1, 2, 3, 2, 3
                ),
                listOf(
                    3, 0, 1, 2, 1, 3, 0, 3, 3, 3, 1, 2, 2, 1, 1, 0, 3, 1, 2, 2, 1, 1, 1, 2, 2, 2, 3, 3, 2, 2, 2, 3, 3, 1, 2, 3, 0, 0, 0, 1
                ),
                listOf(
                    3, 0, 2, 1, 0, 3, 0, 1, 3, 3, 1, 3, 0, 1, 0, 2, 2, 3, 2, 2, 3, 0, 1, 0, 0, 2, 3, 1, 1, 1, 1, 1, 2, 2, 0, 0, 1, 3, 3, 2
                ),
                listOf(
                    0, 2, 2, 2, 2, 1, 3, 2, 1, 3, 3, 2, 0, 0, 1, 1, 0, 1, 3, 0, 3, 2, 1, 3, 0, 2, 2, 2, 1, 1, 0, 2, 3, 1, 0, 3, 2, 2, 0, 2
                ),
                listOf(
                    2, 1, 0, 2, 0, 3, 2, 2, 3, 0, 2, 1, 1, 2, 3, 1, 3, 0, 2, 1, 0, 0, 1, 3, 0, 1, 3, 0, 1, 2, 3, 3, 2, 0, 3, 3, 0, 2, 0, 1
                ),
                listOf(
                    2, 2, 3, 2, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 2, 3, 2, 3, 3, 0, 0, 2, 2, 1, 2, 2, 2, 2, 3, 3, 2, 2, 0, 1, 2, 0, 3, 3, 1
                ),
                listOf(
                    0, 2, 0, 0, 2, 2, 3, 3, 1, 1, 2, 2, 0, 3, 3, 2, 1, 0, 0, 0, 1, 1, 0, 1, 2, 1, 1, 3, 3, 2, 0, 0, 2, 3, 3, 3, 1, 0, 0, 2
                ),
                listOf(
                    1, 3, 1, 2, 0, 3, 3, 0, 1, 3, 0, 0, 0, 0, 0, 3, 1, 3, 2, 1, 3, 2, 0, 2, 1, 3, 3, 1, 2, 3, 3, 1, 0, 1, 0, 2, 2, 1, 0, 3
                ),
                listOf(
                    2, 1, 0, 1, 3, 1, 3, 0, 1, 1, 2, 1, 2, 2, 2, 3, 0, 2, 2, 2, 1, 1, 1, 2, 0, 2, 1, 0, 3, 1, 1, 0, 0, 2, 3, 1, 2, 3, 0, 1
                ),
                listOf(
                    0, 0, 2, 2, 0, 0, 3, 1, 3, 1, 2, 2, 3, 1, 3, 3, 3, 0, 2, 0, 2, 3, 0, 2, 1, 0, 0, 0, 1, 1, 3, 0, 0, 3, 2, 0, 3, 1, 2, 2
                ),
                listOf(
                    3, 0, 2, 0, 1, 2, 1, 1, 3, 0, 3, 2, 1, 2, 0, 0, 0, 2, 0, 3, 3, 0, 2, 3, 0, 0, 3, 1, 3, 3, 2, 3, 2, 0, 2, 2, 2, 3, 0, 1
                ),
                listOf(
                    1, 1, 3, 1, 3, 2, 1, 0, 2, 2, 0, 0, 1, 3, 2, 2, 1, 0, 3, 3, 1, 2, 3, 2, 1, 3, 2, 0, 0, 3, 3, 1, 1, 0, 1, 2, 2, 1, 1, 1
                ),
                listOf(
                    0, 3, 2, 3, 2, 2, 0, 3, 0, 0, 1, 1, 0, 1, 1, 0, 0, 2, 3, 1, 2, 3, 3, 1, 0, 1, 3, 1, 0, 3, 0, 1, 2, 2, 3, 0, 1, 2, 0, 2
                ),
                listOf(
                    2, 0, 2, 0, 0, 0, 2, 1, 2, 2, 1, 3, 1, 3, 1, 0, 0, 2, 3, 3, 2, 0, 2, 0, 3, 0, 1, 1, 1, 3, 0, 3, 0, 3, 2, 3, 1, 0, 3, 1
                ),
                listOf(
                    1, 1, 2, 2, 1, 0, 2, 0, 2, 3, 1, 3, 3, 0, 3, 0, 3, 3, 3, 3, 1, 3, 3, 2, 0, 2, 1, 2, 3, 1, 3, 2, 1, 2, 1, 3, 3, 1, 2, 3
                ),
                listOf(
                    0, 1, 2, 0, 2, 2, 2, 1, 3, 1, 3, 0, 2, 3, 1, 3, 1, 0, 2, 0, 1, 3, 1, 1, 2, 3, 3, 2, 1, 0, 3, 3, 0, 0, 2, 2, 0, 2, 0, 2
                ),
                listOf(
                    2, 3, 3, 3, 0, 1, 0, 2, 1, 3, 3, 2, 1, 0, 3, 3, 1, 0, 1, 2, 0, 0, 3, 0, 1, 2, 3, 2, 0, 3, 0, 3, 3, 2, 0, 1, 3, 3, 2, 3
                ),
                listOf(
                    1, 2, 2, 2, 2, 0, 0, 1, 3, 1, 1, 3, 3, 3, 1, 2, 2, 2, 2, 2, 1, 0, 3, 1, 2, 1, 3, 3, 1, 2, 2, 2, 3, 0, 1, 2, 1, 0, 1, 1
                ),
                listOf(
                    0, 2, 3, 1, 2, 2, 2, 3, 0, 2, 2, 3, 1, 3, 1, 3, 3, 2, 1, 2, 2, 1, 0, 2, 3, 2, 2, 1, 1, 1, 3, 2, 1, 3, 1, 1, 3, 1, 2, 1
                ),
                listOf(
                    3, 1, 0, 1, 1, 2, 1, 1, 3, 2, 2, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 2, 2, 0, 3, 2, 0, 3, 0, 1, 3, 0, 3, 2, 1, 0, 2, 3, 3
                ),
                listOf(
                    0, 1, 2, 3, 2, 0, 0, 3, 1, 2, 2, 2, 1, 2, 2, 3, 1, 1, 2, 0, 2, 0, 0, 3, 2, 1, 2, 3, 1, 0, 2, 2, 3, 3, 2, 3, 3, 2, 1, 2
                ),
                listOf(
                    1, 0, 3, 0, 3, 2, 3, 0, 0, 1, 3, 2, 2, 3, 2, 2, 2, 1, 3, 1, 1, 1, 2, 3, 1, 0, 0, 0, 1, 0, 2, 0, 2, 0, 2, 2, 3, 2, 2, 2
                ),
                listOf(
                    2, 2, 0, 1, 2, 2, 3, 1, 1, 0, 2, 0, 2, 0, 0, 2, 2, 1, 0, 3, 1, 1, 0, 2, 3, 0, 1, 3, 1, 1, 0, 1, 2, 0, 0, 3, 2, 1, 1, 1
                ),
                listOf(
                    0, 2, 2, 2, 2, 0, 3, 2, 1, 2, 1, 2, 0, 1, 0, 1, 1, 1, 0, 1, 3, 1, 1, 0, 3, 1, 0, 0, 3, 1, 2, 3, 0, 1, 3, 1, 3, 2, 1, 0
                ),
                listOf(
                    1, 0, 0, 1, 2, 0, 1, 0, 1, 1, 1, 2, 2, 3, 1, 1, 0, 1, 3, 0, 0, 1, 1, 1, 0, 3, 2, 2, 2, 3, 3, 2, 2, 1, 0, 3, 1, 3, 1, 3
                ),
                listOf(
                    2, 3, 1, 2, 3, 3, 0, 3, 3, 2, 1, 3, 3, 0, 0, 0, 3, 2, 2, 3, 0, 1, 2, 3, 3, 1, 0, 3, 1, 2, 2, 2, 1, 0, 2, 0, 1, 2, 2, 2
                ),
                listOf(
                    0, 3, 2, 1, 1, 2, 0, 3, 2, 3, 2, 0, 2, 1, 0, 1, 0, 1, 2, 1, 0, 2, 0, 1, 0, 0, 0, 1, 2, 0, 0, 0, 1, 0, 3, 0, 1, 0, 3, 1
                ),
                listOf(
                    0, 2, 1, 2, 0, 2, 1, 2, 3, 2, 2, 0, 3, 2, 1, 0, 1, 2, 2, 3, 1, 0, 0, 3, 2, 2, 2, 0, 2, 1, 0, 0, 1, 1, 3, 1, 3, 0, 3, 1
                ),
                listOf(
                    3, 1, 1, 1, 2, 3, 2, 0, 1, 0, 1, 3, 2, 3, 2, 3, 3, 3, 1, 1, 2, 0, 0, 0, 3, 2, 0, 0, 3, 1, 1, 3, 0, 0, 1, 0, 2, 0, 1, 0
                ),
                listOf(
                    3, 1, 2, 1, 1, 0, 0, 2, 3, 3, 3, 1, 0, 2, 1, 1, 0, 3, 0, 3, 0, 0, 2, 1, 3, 3, 1, 1, 1, 3, 0, 2, 0, 1, 0, 2, 0, 3, 1, 2
                ),
                listOf(
                    2, 1, 0, 2, 2, 3, 1, 1, 3, 1, 3, 2, 0, 0, 0, 0, 0, 1, 3, 1, 0, 1, 2, 1, 3, 3, 0, 0, 3, 0, 0, 3, 3, 3, 1, 2, 0, 3, 1, 0
                ),
                listOf(
                    1, 0, 3, 3, 1, 1, 1, 1, 2, 1, 3, 1, 1, 3, 2, 1, 1, 3, 3, 3, 1, 3, 1, 2, 0, 0, 1, 2, 1, 0, 0, 2, 3, 0, 3, 1, 2, 2, 1, 0
                ),
                listOf(
                    3, 2, 3, 2, 2, 0, 2, 0, 2, 0, 3, 0, 2, 2, 0, 1, 2, 3, 0, 1, 2, 3, 3, 3, 2, 0, 1, 0, 3, 2, 3, 2, 3, 1, 2, 3, 3, 0, 0, 1
                ),
                listOf(
                    3, 0, 3, 2, 3, 3, 1, 0, 2, 0, 3, 3, 1, 2, 0, 2, 1, 2, 2, 1, 1, 3, 3, 2, 0, 0, 2, 2, 2, 0, 0, 1, 2, 2, 3, 1, 0, 0, 2, 1
                ),
                listOf(
                    2, 3, 3, 3, 2, 1, 2, 1, 3, 1, 2, 1, 3, 3, 2, 3, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 2, 0, 0, 2, 0, 2, 3, 2, 0, 2, 3
                ),
                listOf(
                    3, 0, 3, 0, 3, 0, 1, 0, 0, 2, 2, 2, 1, 3, 0, 0, 2, 0, 0, 1, 0, 1, 2, 0, 1, 0, 2, 0, 1, 2, 1, 0, 2, 3, 0, 0, 0, 2, 0, 2
                ),
                listOf(
                    1, 3, 1, 1, 1, 2, 1, 3, 0, 0, 2, 3, 0, 0, 2, 0, 0, 0, 2, 3, 2, 1, 1, 0, 1, 0, 3, 1, 0, 3, 2, 2, 3, 2, 0, 2, 2, 0, 3, 2
                ),
                listOf(
                    0, 3, 2, 0, 3, 2, 3, 3, 1, 0, 3, 2, 1, 1, 0, 1, 2, 2, 0, 0, 1, 3, 1, 2, 3, 2, 2, 1, 2, 0, 2, 0, 3, 1, 2, 1, 2, 2, 1, 2
                ),
                listOf(
                    1, 1, 1, 3, 3, 3, 0, 1, 0, 0, 0, 2, 2, 1, 1, 3, 3, 1, 2, 0, 0, 0, 1, 3, 1, 0, 0, 3, 2, 3, 1, 2, 0, 1, 2, 3, 3, 0, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 25),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk83(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_004", "Legendary", 40, 60, listOf(
                listOf(
                    2, 3, 0, 3, 2, 3, 0, 3, 0, 0, 0, 3, 3, 1, 3, 1, 2, 0, 2, 2, 1, 2, 1, 3, 3, 1, 2, 0, 2, 1, 1, 2, 2, 1, 1, 1, 2, 1, 3, 1
                ),
                listOf(
                    0, 2, 2, 3, 2, 1, 3, 1, 2, 0, 0, 2, 1, 3, 1, 1, 3, 2, 0, 3, 0, 2, 3, 3, 0, 1, 3, 3, 0, 0, 1, 2, 1, 0, 2, 3, 0, 1, 0, 0
                ),
                listOf(
                    2, 0, 2, 1, 0, 2, 3, 1, 3, 3, 2, 0, 0, 1, 3, 2, 2, 2, 2, 3, 3, 2, 2, 3, 3, 0, 2, 3, 0, 0, 2, 2, 0, 1, 1, 3, 0, 2, 1, 1
                ),
                listOf(
                    1, 0, 3, 2, 2, 0, 0, 2, 1, 3, 2, 1, 0, 1, 2, 1, 3, 1, 1, 0, 1, 0, 3, 1, 0, 1, 2, 3, 1, 1, 0, 1, 1, 3, 0, 0, 2, 2, 2, 1
                ),
                listOf(
                    1, 3, 0, 3, 3, 2, 0, 3, 0, 0, 3, 0, 3, 1, 3, 2, 1, 0, 1, 2, 1, 1, 1, 2, 1, 3, 2, 2, 0, 1, 0, 1, 0, 0, 2, 0, 1, 3, 3, 2
                ),
                listOf(
                    1, 3, 2, 3, 0, 2, 2, 3, 3, 3, 3, 3, 0, 1, 0, 3, 2, 3, 3, 3, 3, 2, 2, 2, 2, 1, 3, 1, 0, 0, 2, 2, 0, 3, 1, 2, 0, 0, 0, 0
                ),
                listOf(
                    3, 1, 0, 0, 3, 3, 1, 1, 2, 0, 1, 3, 0, 0, 1, 3, 1, 1, 0, 3, 2, 1, 0, 1, 0, 0, 0, 3, 1, 0, 0, 3, 3, 0, 1, 0, 1, 2, 3, 3
                ),
                listOf(
                    2, 0, 0, 3, 1, 3, 3, 2, 2, 2, 0, 0, 0, 2, 2, 2, 0, 2, 3, 1, 1, 0, 3, 1, 0, 3, 2, 2, 3, 3, 3, 3, 3, 1, 0, 2, 0, 0, 2, 2
                ),
                listOf(
                    0, 0, 3, 3, 0, 2, 2, 1, 3, 2, 1, 0, 1, 1, 2, 2, 3, 0, 0, 3, 2, 1, 1, 3, 3, 0, 1, 0, 1, 0, 2, 0, 3, 3, 0, 0, 3, 1, 0, 3
                ),
                listOf(
                    0, 3, 0, 0, 0, 0, 2, 3, 1, 1, 2, 3, 2, 3, 3, 1, 3, 2, 2, 1, 0, 1, 0, 2, 1, 2, 3, 2, 0, 2, 1, 0, 1, 2, 1, 2, 3, 1, 1, 1
                ),
                listOf(
                    3, 2, 3, 3, 0, 3, 0, 0, 1, 1, 3, 1, 1, 0, 2, 0, 2, 1, 1, 1, 2, 0, 2, 2, 3, 0, 1, 0, 0, 0, 3, 2, 1, 1, 1, 1, 3, 2, 3, 3
                ),
                listOf(
                    1, 3, 2, 1, 3, 0, 2, 1, 2, 2, 0, 2, 2, 2, 2, 3, 3, 1, 1, 2, 3, 0, 2, 1, 0, 1, 0, 2, 3, 0, 1, 0, 3, 2, 0, 1, 0, 3, 2, 0
                ),
                listOf(
                    3, 2, 2, 0, 3, 1, 2, 1, 2, 2, 1, 2, 0, 0, 3, 3, 1, 0, 2, 3, 1, 0, 0, 0, 0, 2, 0, 3, 2, 1, 0, 2, 1, 1, 0, 0, 1, 0, 1, 3
                ),
                listOf(
                    2, 1, 0, 1, 2, 3, 3, 1, 0, 1, 2, 2, 3, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 2, 2, 2, 1, 3, 2, 0, 2, 1, 0, 1, 0, 0, 2, 1
                ),
                listOf(
                    1, 1, 2, 2, 2, 3, 2, 2, 1, 2, 1, 0, 1, 2, 2, 3, 1, 1, 2, 3, 1, 2, 3, 3, 0, 0, 1, 0, 0, 1, 2, 1, 0, 2, 3, 2, 0, 3, 1, 0
                ),
                listOf(
                    1, 2, 2, 2, 3, 1, 1, 3, 2, 3, 1, 1, 1, 1, 3, 1, 0, 0, 1, 0, 0, 3, 2, 0, 1, 2, 3, 2, 2, 2, 1, 1, 1, 1, 3, 0, 0, 0, 0, 3
                ),
                listOf(
                    2, 0, 0, 3, 0, 2, 3, 0, 0, 2, 2, 0, 0, 0, 3, 1, 0, 1, 1, 0, 0, 0, 3, 1, 2, 0, 0, 3, 2, 3, 1, 2, 2, 2, 1, 2, 0, 1, 3, 2
                ),
                listOf(
                    2, 1, 0, 3, 0, 2, 1, 0, 2, 1, 3, 1, 1, 2, 2, 2, 3, 2, 2, 3, 3, 3, 0, 2, 1, 1, 3, 2, 2, 1, 1, 3, 0, 2, 2, 2, 0, 3, 3, 2
                ),
                listOf(
                    1, 1, 3, 3, 1, 3, 2, 1, 0, 1, 1, 1, 3, 3, 2, 3, 1, 2, 0, 2, 2, 2, 1, 3, 2, 1, 1, 1, 0, 3, 3, 0, 0, 0, 2, 1, 3, 0, 1, 2
                ),
                listOf(
                    3, 3, 1, 2, 1, 3, 0, 2, 3, 3, 3, 1, 3, 2, 1, 2, 2, 2, 2, 2, 2, 0, 3, 3, 0, 0, 0, 3, 1, 3, 1, 0, 1, 3, 2, 1, 0, 2, 2, 3
                ),
                listOf(
                    1, 3, 1, 1, 3, 0, 3, 1, 1, 3, 0, 3, 1, 3, 1, 1, 0, 2, 3, 3, 2, 1, 0, 3, 0, 2, 3, 1, 0, 2, 2, 1, 2, 0, 0, 3, 2, 0, 0, 1
                ),
                listOf(
                    2, 0, 2, 0, 3, 0, 2, 3, 2, 2, 2, 2, 1, 2, 0, 2, 0, 3, 1, 2, 1, 1, 2, 3, 3, 3, 0, 1, 0, 0, 3, 1, 3, 0, 0, 1, 2, 3, 2, 2
                ),
                listOf(
                    0, 1, 1, 1, 2, 1, 1, 3, 2, 2, 0, 2, 3, 2, 0, 1, 2, 1, 3, 1, 0, 3, 2, 1, 3, 1, 2, 2, 0, 1, 3, 1, 1, 0, 1, 3, 3, 2, 3, 0
                ),
                listOf(
                    3, 0, 2, 1, 2, 0, 0, 3, 3, 2, 0, 0, 0, 0, 2, 3, 0, 1, 2, 2, 1, 0, 0, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 3, 1, 1, 1, 1, 3, 3
                ),
                listOf(
                    3, 1, 3, 1, 1, 1, 0, 0, 3, 3, 0, 3, 1, 0, 1, 3, 0, 1, 0, 0, 1, 2, 3, 0, 1, 1, 1, 2, 3, 0, 0, 2, 2, 1, 2, 3, 1, 2, 2, 1
                ),
                listOf(
                    3, 3, 0, 3, 1, 2, 1, 2, 2, 2, 2, 0, 1, 2, 1, 3, 1, 1, 0, 0, 2, 3, 3, 0, 2, 0, 0, 0, 1, 3, 2, 1, 1, 3, 2, 1, 1, 2, 0, 0
                ),
                listOf(
                    0, 0, 0, 1, 1, 2, 0, 2, 3, 0, 1, 0, 0, 1, 1, 1, 3, 0, 1, 0, 1, 1, 2, 2, 2, 3, 1, 2, 1, 1, 0, 0, 3, 3, 1, 2, 3, 1, 2, 3
                ),
                listOf(
                    1, 1, 1, 2, 2, 2, 0, 3, 3, 2, 2, 3, 0, 1, 1, 1, 1, 0, 3, 3, 0, 1, 3, 3, 3, 1, 3, 2, 1, 3, 0, 0, 2, 0, 3, 1, 3, 0, 3, 0
                ),
                listOf(
                    0, 1, 3, 1, 1, 0, 2, 0, 0, 1, 3, 0, 2, 1, 1, 1, 0, 3, 0, 3, 1, 2, 2, 3, 1, 3, 3, 2, 2, 1, 2, 1, 2, 2, 2, 0, 0, 0, 3, 0
                ),
                listOf(
                    3, 1, 0, 3, 0, 3, 0, 2, 1, 1, 0, 2, 0, 0, 0, 2, 3, 0, 0, 2, 1, 1, 2, 1, 0, 3, 3, 2, 2, 2, 2, 3, 0, 1, 0, 1, 3, 0, 3, 0
                ),
                listOf(
                    2, 2, 0, 2, 0, 0, 2, 1, 2, 3, 3, 3, 0, 1, 3, 0, 2, 2, 2, 0, 3, 3, 0, 1, 2, 1, 0, 3, 2, 3, 0, 2, 2, 3, 3, 3, 3, 1, 3, 2
                ),
                listOf(
                    3, 0, 1, 2, 1, 3, 3, 1, 3, 1, 3, 1, 2, 0, 3, 3, 2, 3, 0, 3, 3, 0, 2, 2, 3, 0, 0, 0, 3, 0, 1, 0, 1, 3, 3, 3, 3, 1, 0, 0
                ),
                listOf(
                    0, 3, 0, 0, 0, 3, 2, 0, 1, 2, 0, 0, 1, 1, 1, 3, 3, 2, 1, 1, 3, 0, 3, 2, 1, 0, 3, 1, 3, 0, 3, 3, 0, 1, 0, 3, 1, 2, 2, 1
                ),
                listOf(
                    1, 3, 2, 3, 0, 3, 0, 3, 3, 3, 2, 1, 2, 0, 0, 1, 1, 0, 3, 0, 3, 3, 2, 0, 3, 3, 3, 1, 3, 2, 2, 1, 2, 0, 0, 0, 1, 1, 1, 2
                ),
                listOf(
                    2, 0, 1, 3, 2, 3, 0, 0, 1, 2, 0, 1, 3, 2, 0, 1, 3, 2, 3, 3, 3, 3, 2, 2, 3, 0, 0, 0, 3, 2, 1, 3, 0, 0, 0, 3, 0, 2, 3, 0
                ),
                listOf(
                    3, 3, 0, 2, 1, 1, 0, 1, 0, 3, 1, 3, 0, 0, 0, 2, 3, 2, 3, 0, 3, 1, 1, 3, 0, 3, 0, 2, 1, 1, 0, 0, 1, 0, 0, 2, 3, 2, 3, 0
                ),
                listOf(
                    2, 0, 0, 1, 1, 0, 2, 2, 1, 3, 2, 0, 3, 0, 2, 1, 3, 0, 3, 3, 3, 0, 0, 1, 3, 1, 2, 2, 3, 0, 2, 3, 0, 3, 0, 0, 1, 1, 2, 1
                ),
                listOf(
                    1, 0, 2, 1, 3, 3, 0, 2, 3, 3, 2, 1, 2, 2, 3, 3, 0, 1, 1, 0, 2, 3, 0, 2, 0, 2, 1, 2, 2, 1, 1, 1, 0, 1, 2, 3, 1, 3, 3, 3
                ),
                listOf(
                    2, 3, 1, 3, 1, 3, 1, 2, 2, 3, 1, 1, 1, 3, 1, 3, 0, 3, 3, 1, 1, 3, 1, 0, 0, 2, 3, 3, 1, 1, 0, 0, 2, 0, 1, 1, 1, 0, 2, 2
                ),
                listOf(
                    2, 3, 2, 0, 2, 0, 0, 0, 2, 3, 1, 3, 0, 1, 3, 0, 2, 0, 1, 2, 0, 1, 2, 0, 1, 3, 3, 3, 2, 2, 0, 3, 0, 1, 0, 1, 3, 3, 2, 1
                ),
                listOf(
                    2, 3, 2, 2, 1, 2, 3, 2, 2, 3, 0, 2, 3, 3, 2, 3, 1, 1, 1, 0, 0, 0, 2, 0, 1, 3, 3, 2, 2, 0, 2, 0, 1, 1, 0, 1, 2, 3, 1, 0
                ),
                listOf(
                    2, 2, 1, 0, 3, 0, 2, 3, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 2, 0, 0, 0, 2, 1, 0, 1, 2, 1, 2, 2, 3, 0, 0, 0, 2, 1, 2, 1, 2, 1
                ),
                listOf(
                    0, 2, 2, 0, 1, 1, 3, 3, 1, 0, 2, 3, 3, 1, 3, 3, 0, 1, 2, 0, 1, 0, 2, 3, 1, 2, 0, 2, 2, 2, 0, 2, 1, 3, 0, 2, 3, 0, 0, 1
                ),
                listOf(
                    0, 0, 0, 0, 1, 2, 1, 2, 0, 1, 0, 1, 1, 0, 1, 0, 2, 3, 3, 1, 3, 0, 2, 3, 2, 1, 0, 1, 0, 1, 0, 3, 2, 1, 3, 3, 1, 2, 2, 2
                ),
                listOf(
                    0, 1, 2, 0, 2, 0, 2, 2, 1, 1, 1, 1, 2, 3, 1, 3, 2, 2, 3, 2, 2, 0, 1, 3, 3, 2, 3, 0, 3, 3, 1, 3, 3, 2, 1, 3, 1, 0, 2, 3
                ),
                listOf(
                    2, 3, 2, 1, 2, 3, 3, 3, 3, 2, 0, 2, 3, 3, 3, 2, 0, 1, 3, 2, 2, 2, 0, 1, 3, 0, 2, 3, 0, 2, 0, 2, 1, 2, 0, 2, 0, 1, 1, 0
                ),
                listOf(
                    3, 1, 3, 1, 2, 1, 1, 3, 1, 0, 2, 0, 2, 1, 0, 0, 0, 0, 1, 0, 0, 0, 3, 3, 0, 1, 3, 3, 3, 1, 1, 2, 2, 0, 0, 3, 2, 0, 1, 0
                ),
                listOf(
                    2, 0, 2, 3, 2, 1, 3, 2, 0, 1, 3, 0, 2, 3, 3, 0, 3, 1, 0, 0, 2, 2, 3, 3, 1, 1, 1, 0, 0, 3, 2, 0, 1, 1, 1, 2, 0, 1, 1, 3
                ),
                listOf(
                    1, 3, 3, 3, 0, 2, 0, 3, 3, 0, 2, 2, 1, 3, 0, 1, 3, 0, 2, 1, 0, 3, 1, 3, 0, 3, 3, 0, 3, 3, 2, 1, 3, 3, 2, 3, 0, 2, 0, 1
                ),
                listOf(
                    0, 1, 1, 0, 2, 3, 2, 2, 1, 3, 2, 3, 0, 0, 3, 0, 1, 0, 3, 1, 0, 2, 3, 3, 2, 1, 0, 1, 3, 2, 3, 3, 3, 1, 1, 1, 0, 2, 3, 1
                ),
                listOf(
                    2, 0, 1, 0, 0, 0, 0, 3, 1, 1, 1, 3, 1, 1, 2, 1, 0, 3, 3, 3, 2, 3, 2, 1, 0, 2, 3, 3, 3, 0, 2, 0, 1, 3, 0, 2, 2, 1, 3, 2
                ),
                listOf(
                    1, 2, 3, 3, 0, 2, 2, 1, 1, 0, 2, 0, 0, 0, 3, 0, 3, 2, 0, 1, 3, 0, 2, 2, 2, 1, 1, 0, 2, 0, 0, 1, 1, 0, 3, 0, 3, 2, 0, 0
                ),
                listOf(
                    1, 2, 2, 2, 2, 0, 1, 3, 2, 0, 0, 1, 3, 2, 3, 1, 1, 3, 2, 2, 1, 0, 3, 1, 2, 0, 2, 3, 2, 2, 2, 3, 3, 1, 0, 0, 0, 1, 0, 3
                ),
                listOf(
                    0, 2, 2, 1, 2, 3, 1, 3, 3, 0, 2, 0, 1, 0, 2, 1, 1, 2, 1, 1, 2, 3, 3, 1, 1, 0, 1, 2, 1, 3, 2, 3, 0, 0, 2, 3, 1, 3, 0, 1
                ),
                listOf(
                    0, 0, 3, 2, 3, 1, 3, 3, 1, 0, 2, 1, 2, 3, 0, 1, 0, 2, 0, 2, 2, 2, 2, 3, 1, 1, 1, 2, 0, 2, 0, 2, 0, 2, 1, 2, 2, 2, 2, 0
                ),
                listOf(
                    0, 3, 3, 3, 2, 0, 2, 2, 1, 0, 2, 2, 3, 0, 3, 3, 1, 1, 3, 3, 0, 2, 1, 2, 0, 1, 0, 2, 0, 1, 0, 2, 3, 0, 1, 3, 2, 2, 3, 0
                ),
                listOf(
                    3, 2, 2, 1, 2, 1, 1, 1, 2, 1, 1, 3, 0, 3, 0, 1, 0, 3, 3, 2, 0, 2, 2, 2, 2, 3, 0, 1, 0, 2, 0, 2, 2, 0, 3, 1, 0, 2, 0, 2
                ),
                listOf(
                    3, 1, 1, 0, 0, 0, 1, 3, 2, 3, 1, 3, 1, 2, 0, 2, 3, 0, 2, 1, 1, 2, 2, 1, 3, 1, 3, 2, 3, 0, 2, 3, 3, 1, 0, 2, 0, 2, 1, 0
                ),
                listOf(
                    0, 1, 2, 3, 1, 2, 3, 3, 0, 0, 2, 3, 3, 0, 2, 1, 1, 0, 3, 2, 1, 3, 2, 0, 3, 3, 1, 0, 2, 2, 2, 0, 2, 0, 3, 2, 3, 1, 1, 0
                ),
                listOf(
                    2, 3, 1, 3, 3, 2, 0, 0, 2, 3, 2, 2, 1, 0, 1, 2, 2, 3, 2, 1, 1, 2, 2, 2, 2, 1, 1, 3, 3, 0, 0, 2, 3, 1, 2, 1, 2, 1, 1, 0
                )
            ), listOf(
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 25),
                Tank(3, 20),
                Tank(1, 25),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk84(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_005", "Legendary", 40, 60, listOf(
                listOf(
                    1, 2, 1, 1, 0, 1, 1, 0, 0, 2, 2, 0, 2, 0, 1, 1, 1, 3, 2, 3, 2, 3, 0, 2, 2, 0, 1, 3, 0, 0, 2, 2, 2, 0, 2, 0, 1, 0, 0, 1
                ),
                listOf(
                    2, 1, 3, 3, 2, 0, 2, 3, 2, 1, 1, 0, 0, 0, 3, 3, 0, 2, 1, 3, 0, 1, 3, 1, 1, 3, 3, 0, 0, 0, 1, 0, 2, 2, 2, 1, 1, 0, 3, 0
                ),
                listOf(
                    2, 3, 0, 2, 0, 1, 2, 3, 1, 0, 0, 1, 2, 0, 1, 1, 1, 0, 3, 3, 2, 0, 1, 2, 1, 3, 2, 2, 0, 1, 2, 0, 2, 3, 2, 3, 1, 0, 2, 2
                ),
                listOf(
                    0, 3, 3, 2, 3, 0, 0, 3, 0, 2, 1, 1, 3, 0, 0, 3, 1, 1, 1, 1, 2, 1, 0, 2, 1, 2, 3, 2, 0, 0, 2, 1, 0, 3, 1, 1, 3, 1, 1, 1
                ),
                listOf(
                    0, 3, 2, 3, 0, 2, 3, 2, 2, 2, 0, 3, 0, 3, 1, 3, 0, 1, 1, 0, 0, 2, 0, 0, 3, 3, 2, 3, 3, 0, 2, 0, 3, 0, 1, 1, 0, 1, 2, 0
                ),
                listOf(
                    3, 3, 2, 2, 0, 3, 0, 0, 0, 0, 1, 3, 3, 2, 2, 2, 0, 1, 0, 2, 2, 3, 0, 3, 2, 0, 3, 0, 2, 0, 3, 0, 1, 3, 2, 1, 3, 2, 1, 1
                ),
                listOf(
                    3, 2, 1, 0, 3, 0, 3, 2, 2, 1, 2, 3, 1, 2, 2, 1, 2, 3, 3, 3, 1, 3, 2, 0, 2, 2, 3, 2, 2, 2, 1, 1, 2, 0, 0, 1, 3, 3, 1, 2
                ),
                listOf(
                    1, 2, 2, 2, 0, 3, 0, 3, 0, 1, 1, 2, 3, 3, 2, 0, 3, 3, 1, 2, 1, 2, 3, 0, 3, 2, 2, 1, 0, 2, 0, 1, 1, 1, 2, 3, 3, 0, 2, 2
                ),
                listOf(
                    3, 2, 2, 3, 1, 0, 3, 3, 2, 3, 2, 1, 0, 1, 2, 2, 1, 3, 0, 3, 3, 2, 0, 0, 1, 0, 2, 1, 2, 2, 1, 0, 2, 3, 2, 1, 2, 1, 1, 1
                ),
                listOf(
                    3, 2, 2, 1, 2, 2, 3, 3, 2, 3, 1, 2, 3, 3, 0, 1, 3, 0, 2, 3, 1, 2, 2, 2, 0, 2, 0, 1, 0, 0, 0, 3, 2, 0, 0, 3, 3, 0, 1, 3
                ),
                listOf(
                    2, 1, 2, 1, 3, 2, 1, 0, 1, 3, 2, 1, 2, 2, 3, 1, 2, 0, 1, 2, 3, 2, 1, 3, 2, 2, 1, 1, 1, 3, 3, 2, 2, 3, 1, 2, 0, 2, 1, 2
                ),
                listOf(
                    1, 0, 1, 1, 3, 2, 3, 3, 0, 1, 2, 3, 1, 3, 1, 1, 1, 3, 3, 1, 0, 0, 2, 0, 3, 0, 3, 2, 2, 1, 1, 2, 1, 0, 2, 2, 2, 0, 2, 2
                ),
                listOf(
                    3, 2, 2, 1, 2, 1, 2, 0, 2, 3, 3, 1, 3, 3, 2, 2, 1, 1, 1, 1, 3, 3, 3, 2, 3, 2, 2, 2, 1, 1, 3, 3, 0, 0, 1, 2, 1, 2, 3, 1
                ),
                listOf(
                    1, 3, 1, 1, 1, 1, 0, 3, 0, 3, 3, 1, 2, 2, 1, 0, 2, 0, 1, 2, 3, 0, 1, 3, 2, 1, 0, 0, 2, 0, 0, 2, 3, 3, 3, 1, 0, 0, 1, 1
                ),
                listOf(
                    3, 2, 0, 1, 0, 1, 1, 1, 3, 0, 0, 2, 2, 0, 2, 1, 3, 2, 2, 3, 0, 2, 0, 3, 3, 3, 3, 1, 3, 0, 3, 0, 3, 0, 0, 1, 2, 3, 2, 0
                ),
                listOf(
                    1, 0, 2, 0, 2, 2, 3, 2, 0, 1, 0, 2, 2, 1, 0, 2, 0, 3, 0, 1, 3, 2, 1, 3, 1, 2, 0, 1, 1, 2, 0, 3, 0, 0, 2, 1, 2, 2, 0, 2
                ),
                listOf(
                    0, 2, 3, 1, 3, 2, 2, 0, 2, 2, 2, 1, 3, 0, 1, 2, 0, 2, 1, 2, 2, 0, 3, 0, 0, 2, 0, 3, 3, 1, 1, 1, 2, 1, 0, 3, 0, 1, 1, 1
                ),
                listOf(
                    1, 0, 2, 3, 1, 0, 3, 3, 0, 2, 0, 2, 2, 2, 3, 0, 2, 2, 1, 2, 2, 2, 0, 0, 3, 0, 0, 2, 2, 3, 0, 3, 3, 0, 3, 1, 0, 0, 3, 0
                ),
                listOf(
                    3, 2, 1, 2, 0, 1, 0, 0, 0, 3, 0, 3, 3, 0, 2, 1, 0, 1, 3, 1, 3, 0, 2, 1, 1, 2, 3, 1, 3, 2, 3, 0, 0, 1, 2, 1, 2, 3, 2, 3
                ),
                listOf(
                    1, 0, 0, 3, 0, 0, 3, 2, 1, 1, 3, 2, 0, 1, 0, 1, 0, 3, 2, 0, 0, 3, 2, 3, 2, 2, 3, 2, 2, 1, 0, 0, 2, 0, 2, 2, 2, 2, 1, 0
                ),
                listOf(
                    0, 3, 2, 2, 1, 3, 1, 1, 0, 1, 2, 1, 3, 0, 2, 1, 0, 1, 2, 2, 1, 1, 3, 1, 3, 0, 0, 1, 1, 3, 2, 2, 1, 1, 1, 0, 3, 0, 1, 2
                ),
                listOf(
                    1, 2, 2, 3, 3, 1, 0, 2, 1, 2, 0, 0, 0, 2, 2, 1, 3, 1, 0, 2, 3, 0, 0, 2, 1, 2, 0, 0, 2, 2, 2, 1, 1, 3, 3, 2, 3, 0, 3, 1
                ),
                listOf(
                    2, 1, 3, 1, 1, 1, 2, 0, 0, 2, 2, 3, 0, 1, 1, 0, 2, 3, 1, 1, 1, 2, 1, 2, 3, 3, 3, 3, 1, 1, 3, 0, 3, 1, 3, 2, 3, 0, 3, 2
                ),
                listOf(
                    1, 2, 2, 0, 0, 3, 2, 3, 2, 2, 3, 3, 0, 1, 1, 2, 1, 1, 2, 1, 1, 0, 3, 2, 2, 3, 3, 3, 2, 2, 1, 1, 1, 1, 0, 2, 3, 3, 0, 3
                ),
                listOf(
                    3, 0, 1, 1, 0, 3, 0, 1, 2, 1, 3, 3, 3, 2, 3, 0, 0, 1, 1, 3, 2, 1, 0, 2, 2, 3, 0, 3, 0, 3, 1, 2, 3, 3, 0, 3, 3, 0, 0, 3
                ),
                listOf(
                    2, 3, 3, 1, 2, 0, 1, 0, 3, 0, 3, 2, 2, 2, 3, 1, 3, 3, 0, 3, 3, 3, 0, 3, 1, 3, 0, 3, 2, 0, 3, 0, 3, 3, 0, 1, 2, 3, 3, 0
                ),
                listOf(
                    1, 3, 2, 1, 3, 2, 2, 1, 3, 0, 1, 1, 1, 0, 2, 1, 3, 0, 2, 1, 3, 1, 3, 0, 2, 1, 0, 3, 2, 0, 3, 3, 0, 0, 3, 2, 2, 0, 1, 1
                ),
                listOf(
                    2, 0, 2, 2, 1, 1, 0, 3, 1, 0, 1, 1, 0, 1, 2, 2, 0, 3, 2, 2, 0, 0, 0, 2, 3, 0, 0, 2, 2, 1, 0, 3, 0, 2, 0, 0, 3, 0, 3, 0
                ),
                listOf(
                    1, 0, 1, 0, 0, 3, 2, 2, 1, 2, 2, 3, 0, 2, 0, 1, 0, 1, 3, 3, 3, 0, 0, 2, 3, 1, 0, 0, 3, 1, 2, 1, 0, 0, 2, 2, 0, 3, 0, 1
                ),
                listOf(
                    2, 2, 2, 2, 1, 2, 1, 3, 0, 3, 2, 1, 3, 3, 2, 1, 1, 1, 2, 1, 0, 0, 0, 2, 1, 1, 0, 2, 2, 3, 1, 2, 3, 3, 0, 1, 3, 3, 3, 3
                ),
                listOf(
                    0, 2, 1, 3, 2, 2, 0, 2, 3, 0, 0, 3, 0, 1, 3, 1, 1, 1, 3, 2, 1, 3, 2, 3, 0, 2, 1, 1, 0, 2, 2, 3, 2, 0, 2, 2, 1, 0, 3, 0
                ),
                listOf(
                    3, 0, 2, 1, 3, 3, 1, 1, 2, 2, 0, 1, 0, 3, 0, 1, 2, 3, 1, 0, 3, 2, 1, 1, 0, 2, 3, 2, 3, 0, 2, 0, 3, 0, 1, 0, 1, 3, 1, 1
                ),
                listOf(
                    2, 3, 0, 3, 2, 2, 2, 3, 1, 3, 1, 2, 1, 1, 0, 3, 3, 3, 0, 2, 1, 3, 0, 2, 2, 3, 2, 2, 1, 2, 3, 0, 3, 0, 2, 0, 1, 1, 3, 1
                ),
                listOf(
                    3, 1, 3, 2, 1, 0, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 2, 1, 2, 1, 0, 3, 3, 1, 3, 2, 2, 1, 3, 0, 1, 1, 1, 2, 2, 1, 1, 1, 2, 1
                ),
                listOf(
                    0, 2, 3, 2, 0, 2, 0, 1, 1, 1, 2, 2, 3, 0, 3, 3, 1, 1, 2, 0, 2, 2, 3, 3, 0, 1, 0, 1, 0, 2, 3, 1, 0, 1, 0, 1, 2, 0, 0, 1
                ),
                listOf(
                    1, 0, 2, 1, 0, 2, 3, 1, 2, 0, 0, 3, 2, 0, 2, 2, 0, 1, 0, 1, 0, 3, 2, 3, 3, 3, 2, 2, 3, 2, 0, 2, 0, 3, 2, 3, 1, 1, 0, 1
                ),
                listOf(
                    0, 2, 3, 1, 1, 1, 2, 2, 3, 0, 0, 3, 1, 0, 1, 1, 0, 1, 2, 1, 3, 0, 1, 1, 1, 1, 1, 0, 2, 1, 3, 0, 3, 3, 1, 1, 2, 0, 0, 1
                ),
                listOf(
                    3, 3, 1, 2, 2, 3, 1, 3, 3, 0, 2, 1, 1, 3, 1, 3, 2, 1, 0, 2, 3, 3, 2, 0, 2, 0, 1, 3, 3, 0, 2, 1, 1, 0, 3, 2, 2, 2, 2, 3
                ),
                listOf(
                    1, 2, 0, 2, 1, 3, 2, 0, 0, 3, 2, 1, 1, 1, 1, 3, 0, 3, 3, 0, 3, 3, 1, 1, 1, 3, 1, 3, 3, 2, 1, 1, 2, 3, 1, 1, 2, 1, 3, 1
                ),
                listOf(
                    3, 2, 2, 1, 0, 1, 0, 2, 2, 0, 1, 0, 0, 3, 2, 0, 1, 1, 3, 0, 0, 0, 2, 1, 3, 2, 2, 1, 3, 0, 2, 0, 2, 3, 3, 3, 0, 2, 3, 1
                ),
                listOf(
                    3, 0, 3, 0, 0, 2, 1, 2, 3, 2, 1, 3, 0, 3, 3, 1, 0, 3, 1, 1, 0, 1, 0, 0, 1, 2, 1, 1, 3, 1, 2, 2, 2, 0, 2, 2, 1, 2, 2, 0
                ),
                listOf(
                    3, 1, 0, 0, 1, 2, 1, 3, 0, 0, 1, 1, 1, 2, 3, 2, 2, 2, 3, 3, 0, 1, 3, 1, 1, 1, 1, 3, 2, 1, 3, 1, 1, 1, 2, 2, 1, 1, 1, 1
                ),
                listOf(
                    1, 3, 2, 0, 3, 3, 1, 3, 0, 2, 2, 1, 3, 1, 1, 0, 2, 2, 1, 2, 1, 2, 0, 0, 2, 0, 1, 3, 2, 3, 2, 3, 2, 0, 3, 3, 3, 3, 2, 1
                ),
                listOf(
                    2, 2, 2, 2, 0, 0, 3, 1, 3, 2, 0, 3, 0, 0, 2, 3, 0, 3, 2, 0, 1, 3, 1, 3, 3, 2, 1, 1, 0, 1, 2, 2, 0, 0, 3, 2, 0, 3, 2, 2
                ),
                listOf(
                    0, 3, 0, 2, 0, 1, 1, 1, 2, 0, 2, 3, 1, 0, 1, 1, 1, 1, 3, 2, 1, 1, 3, 2, 1, 3, 3, 3, 3, 3, 3, 2, 0, 2, 3, 0, 3, 3, 2, 1
                ),
                listOf(
                    3, 0, 3, 0, 2, 1, 1, 2, 1, 0, 2, 0, 2, 1, 0, 0, 2, 1, 3, 3, 2, 2, 3, 1, 0, 2, 0, 3, 3, 2, 3, 2, 3, 2, 0, 3, 1, 2, 1, 2
                ),
                listOf(
                    1, 0, 3, 2, 1, 3, 1, 2, 0, 0, 3, 2, 3, 1, 3, 2, 2, 1, 3, 2, 3, 2, 1, 1, 3, 2, 1, 2, 3, 2, 3, 3, 2, 0, 1, 0, 0, 3, 1, 0
                ),
                listOf(
                    0, 3, 2, 2, 1, 3, 2, 0, 3, 1, 0, 3, 3, 3, 0, 2, 2, 2, 0, 2, 0, 0, 3, 1, 3, 0, 1, 0, 1, 0, 2, 1, 1, 1, 1, 3, 3, 3, 2, 3
                ),
                listOf(
                    0, 1, 3, 0, 0, 2, 1, 3, 2, 1, 1, 3, 0, 2, 1, 0, 3, 0, 0, 0, 1, 1, 0, 3, 1, 3, 2, 2, 1, 0, 0, 2, 2, 1, 0, 1, 1, 3, 1, 3
                ),
                listOf(
                    0, 3, 0, 3, 2, 1, 0, 2, 2, 1, 2, 3, 3, 0, 1, 1, 0, 0, 1, 2, 1, 1, 3, 2, 0, 1, 0, 2, 3, 1, 2, 1, 3, 1, 3, 1, 1, 0, 2, 1
                ),
                listOf(
                    3, 2, 3, 0, 3, 3, 3, 3, 3, 3, 2, 1, 2, 2, 0, 2, 0, 3, 3, 0, 0, 1, 0, 1, 2, 0, 2, 2, 0, 1, 2, 2, 2, 3, 0, 0, 1, 0, 0, 1
                ),
                listOf(
                    0, 1, 1, 1, 0, 1, 1, 0, 1, 2, 1, 3, 1, 3, 2, 2, 1, 3, 1, 0, 2, 3, 3, 2, 3, 1, 1, 1, 2, 0, 2, 0, 1, 3, 0, 1, 0, 2, 3, 2
                ),
                listOf(
                    3, 1, 3, 3, 3, 3, 1, 2, 3, 3, 2, 3, 2, 3, 3, 0, 0, 2, 2, 2, 0, 3, 2, 1, 2, 0, 3, 1, 1, 1, 2, 3, 2, 1, 2, 1, 3, 2, 0, 3
                ),
                listOf(
                    0, 2, 0, 1, 2, 1, 1, 2, 0, 1, 3, 0, 3, 1, 3, 2, 3, 1, 3, 0, 3, 3, 3, 3, 3, 0, 1, 1, 1, 1, 3, 3, 2, 3, 1, 0, 0, 3, 2, 1
                ),
                listOf(
                    3, 2, 0, 1, 0, 0, 3, 2, 2, 1, 3, 0, 3, 1, 2, 0, 0, 0, 1, 1, 3, 0, 2, 3, 1, 0, 2, 2, 3, 0, 3, 3, 2, 1, 3, 2, 3, 1, 2, 0
                ),
                listOf(
                    0, 0, 1, 3, 1, 0, 1, 2, 1, 1, 1, 1, 0, 1, 0, 2, 2, 2, 1, 0, 3, 0, 1, 1, 3, 2, 1, 2, 3, 1, 2, 0, 3, 0, 1, 2, 3, 2, 3, 0
                ),
                listOf(
                    3, 3, 1, 1, 3, 0, 1, 1, 3, 0, 1, 1, 2, 2, 2, 1, 1, 2, 3, 1, 0, 0, 3, 1, 2, 3, 1, 3, 0, 2, 1, 3, 1, 1, 1, 0, 0, 1, 2, 0
                ),
                listOf(
                    2, 2, 1, 1, 3, 0, 2, 0, 3, 2, 1, 2, 3, 0, 3, 3, 1, 1, 0, 2, 0, 3, 0, 0, 3, 3, 2, 0, 1, 1, 3, 1, 1, 2, 1, 0, 3, 2, 3, 2
                ),
                listOf(
                    2, 0, 2, 3, 1, 3, 3, 1, 0, 3, 3, 2, 3, 1, 2, 2, 0, 1, 3, 2, 2, 1, 2, 0, 2, 1, 0, 2, 2, 1, 0, 0, 0, 3, 3, 3, 1, 0, 1, 3
                ),
                listOf(
                    0, 2, 2, 0, 0, 0, 3, 2, 2, 1, 1, 3, 2, 2, 2, 2, 1, 0, 1, 2, 2, 0, 1, 3, 2, 0, 0, 2, 0, 0, 1, 0, 1, 2, 3, 3, 0, 1, 0, 0
                )
            ), listOf(
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 25),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 25),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk85(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_006", "Legendary", 40, 60, listOf(
                listOf(
                    3, 2, 3, 0, 2, 0, 2, 1, 3, 0, 1, 0, 3, 0, 3, 3, 1, 1, 2, 2, 1, 3, 2, 0, 2, 1, 0, 3, 1, 2, 2, 2, 2, 3, 3, 1, 3, 1, 1, 3
                ),
                listOf(
                    1, 3, 1, 2, 3, 2, 0, 3, 1, 2, 1, 1, 2, 1, 0, 2, 2, 1, 3, 1, 2, 1, 0, 1, 2, 0, 3, 3, 2, 0, 2, 1, 0, 1, 1, 1, 2, 1, 1, 1
                ),
                listOf(
                    1, 3, 3, 2, 2, 3, 0, 1, 0, 0, 1, 3, 3, 2, 0, 0, 0, 2, 1, 0, 3, 0, 0, 3, 2, 0, 0, 1, 1, 0, 1, 2, 1, 2, 3, 0, 1, 1, 0, 1
                ),
                listOf(
                    3, 3, 2, 3, 0, 0, 0, 1, 0, 3, 3, 0, 0, 0, 0, 3, 2, 3, 3, 3, 3, 2, 3, 2, 0, 1, 3, 3, 2, 0, 3, 2, 2, 1, 0, 2, 2, 3, 1, 0
                ),
                listOf(
                    1, 1, 1, 1, 3, 3, 3, 2, 2, 3, 0, 3, 2, 1, 0, 2, 3, 2, 3, 1, 1, 2, 2, 3, 2, 1, 1, 2, 3, 2, 0, 3, 3, 1, 3, 0, 1, 3, 2, 0
                ),
                listOf(
                    1, 3, 3, 2, 2, 2, 0, 0, 0, 1, 3, 1, 0, 1, 2, 1, 0, 1, 1, 0, 3, 1, 2, 0, 3, 2, 1, 0, 2, 3, 1, 2, 2, 1, 3, 0, 3, 1, 1, 2
                ),
                listOf(
                    0, 1, 0, 0, 3, 0, 2, 2, 1, 0, 3, 0, 3, 2, 2, 2, 0, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 0, 3, 1, 2, 2, 0, 2, 1, 3, 2, 2, 0, 2
                ),
                listOf(
                    0, 2, 2, 3, 2, 3, 2, 2, 2, 3, 2, 0, 2, 2, 0, 2, 1, 2, 0, 1, 0, 3, 2, 1, 2, 1, 2, 1, 3, 2, 1, 1, 2, 1, 1, 2, 3, 2, 1, 1
                ),
                listOf(
                    2, 2, 3, 2, 2, 3, 2, 3, 1, 1, 1, 2, 1, 3, 0, 2, 1, 0, 1, 1, 1, 3, 2, 1, 0, 0, 1, 1, 3, 0, 0, 3, 2, 3, 1, 3, 3, 1, 1, 3
                ),
                listOf(
                    3, 3, 1, 1, 2, 3, 3, 3, 0, 0, 2, 2, 1, 3, 2, 0, 2, 2, 0, 0, 0, 1, 1, 2, 1, 2, 2, 1, 0, 2, 1, 2, 1, 3, 2, 2, 2, 0, 3, 0
                ),
                listOf(
                    2, 1, 3, 0, 3, 1, 3, 1, 3, 3, 1, 2, 3, 1, 0, 1, 0, 0, 0, 1, 3, 2, 2, 2, 2, 1, 3, 2, 0, 1, 0, 1, 0, 0, 1, 3, 0, 1, 3, 3
                ),
                listOf(
                    2, 1, 2, 1, 0, 0, 2, 0, 2, 3, 3, 0, 0, 0, 2, 2, 1, 0, 1, 1, 0, 0, 2, 0, 0, 0, 0, 2, 3, 3, 3, 0, 1, 3, 0, 1, 1, 2, 0, 0
                ),
                listOf(
                    2, 1, 3, 3, 3, 2, 0, 1, 1, 2, 0, 2, 2, 0, 0, 2, 2, 0, 3, 1, 3, 0, 3, 0, 3, 0, 0, 3, 2, 1, 2, 1, 3, 1, 3, 3, 2, 0, 3, 2
                ),
                listOf(
                    3, 3, 2, 2, 2, 1, 0, 2, 3, 0, 3, 3, 3, 3, 3, 1, 0, 2, 1, 3, 2, 3, 2, 1, 0, 0, 0, 1, 1, 0, 0, 0, 2, 1, 2, 2, 0, 1, 3, 3
                ),
                listOf(
                    1, 3, 3, 2, 2, 1, 3, 3, 0, 2, 1, 0, 1, 0, 1, 3, 3, 1, 1, 3, 2, 0, 1, 1, 3, 0, 1, 1, 1, 0, 1, 0, 0, 1, 3, 1, 1, 1, 2, 2
                ),
                listOf(
                    0, 0, 1, 0, 2, 1, 3, 1, 0, 1, 0, 1, 1, 3, 2, 0, 2, 1, 3, 3, 2, 2, 2, 1, 2, 0, 0, 0, 3, 3, 2, 3, 2, 3, 2, 2, 0, 3, 3, 1
                ),
                listOf(
                    0, 1, 0, 2, 1, 0, 0, 2, 3, 2, 3, 3, 2, 3, 2, 2, 2, 1, 1, 2, 0, 1, 0, 3, 2, 0, 0, 3, 0, 0, 3, 1, 2, 2, 1, 2, 2, 0, 2, 1
                ),
                listOf(
                    3, 0, 1, 2, 1, 0, 3, 2, 2, 2, 2, 2, 0, 3, 0, 1, 1, 2, 2, 1, 0, 2, 0, 1, 1, 2, 3, 2, 1, 3, 0, 3, 0, 3, 1, 0, 1, 0, 3, 0
                ),
                listOf(
                    1, 1, 0, 3, 2, 0, 1, 0, 3, 3, 0, 1, 2, 2, 1, 1, 1, 0, 3, 0, 0, 2, 1, 3, 1, 1, 2, 2, 1, 2, 3, 1, 3, 0, 3, 2, 2, 0, 1, 2
                ),
                listOf(
                    3, 3, 0, 0, 2, 2, 2, 0, 1, 1, 2, 0, 0, 3, 3, 3, 0, 1, 3, 2, 1, 3, 1, 2, 3, 3, 1, 1, 2, 3, 0, 2, 3, 1, 3, 1, 3, 3, 3, 2
                ),
                listOf(
                    3, 3, 3, 0, 2, 2, 3, 2, 2, 1, 1, 1, 3, 3, 0, 3, 2, 0, 1, 1, 1, 1, 3, 3, 1, 2, 2, 1, 1, 3, 3, 3, 0, 3, 3, 0, 2, 3, 3, 3
                ),
                listOf(
                    2, 3, 0, 1, 0, 2, 0, 1, 3, 1, 3, 2, 1, 2, 0, 2, 3, 0, 3, 1, 1, 1, 0, 1, 0, 1, 0, 1, 3, 0, 1, 0, 1, 0, 1, 0, 1, 1, 3, 0
                ),
                listOf(
                    2, 1, 2, 0, 3, 2, 0, 1, 2, 2, 0, 1, 3, 1, 2, 2, 1, 3, 3, 3, 0, 1, 3, 1, 3, 1, 1, 1, 1, 2, 2, 1, 0, 2, 2, 2, 0, 3, 3, 3
                ),
                listOf(
                    0, 1, 2, 3, 1, 0, 1, 2, 0, 1, 0, 3, 2, 3, 3, 0, 1, 2, 1, 1, 0, 2, 1, 3, 3, 0, 0, 3, 1, 0, 3, 0, 1, 0, 2, 0, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 0, 1, 0, 0, 2, 0, 0, 2, 2, 2, 1, 2, 1, 3, 1, 0, 1, 2, 1, 3, 0, 0, 1, 1, 2, 0, 0, 0, 3, 0, 2, 3, 2, 1, 3, 1, 1, 1
                ),
                listOf(
                    1, 1, 3, 2, 2, 0, 1, 1, 3, 3, 1, 2, 2, 1, 1, 3, 0, 2, 3, 2, 2, 1, 2, 0, 2, 3, 0, 3, 1, 0, 2, 3, 2, 1, 3, 1, 1, 2, 1, 0
                ),
                listOf(
                    3, 1, 0, 1, 1, 3, 1, 1, 1, 2, 3, 0, 0, 1, 0, 3, 1, 1, 0, 1, 3, 0, 3, 3, 3, 3, 1, 0, 0, 2, 3, 0, 1, 0, 1, 3, 1, 0, 2, 0
                ),
                listOf(
                    3, 2, 3, 0, 0, 2, 2, 2, 3, 3, 3, 1, 0, 0, 2, 2, 3, 3, 3, 0, 3, 3, 3, 2, 2, 2, 1, 2, 0, 3, 0, 2, 3, 3, 2, 1, 3, 0, 2, 1
                ),
                listOf(
                    3, 0, 3, 1, 2, 1, 2, 1, 0, 1, 0, 0, 3, 1, 3, 1, 0, 3, 3, 0, 1, 1, 3, 2, 3, 2, 0, 0, 0, 2, 2, 0, 0, 2, 3, 3, 1, 0, 3, 3
                ),
                listOf(
                    0, 2, 0, 2, 1, 0, 1, 0, 3, 2, 3, 2, 2, 1, 1, 1, 0, 2, 2, 1, 2, 2, 3, 2, 0, 1, 2, 0, 3, 3, 2, 3, 1, 1, 2, 2, 1, 3, 1, 2
                ),
                listOf(
                    1, 2, 3, 1, 1, 0, 2, 1, 1, 3, 3, 2, 2, 1, 1, 0, 1, 1, 0, 0, 0, 2, 3, 1, 3, 0, 1, 3, 0, 2, 2, 3, 0, 3, 2, 3, 2, 2, 3, 3
                ),
                listOf(
                    1, 0, 0, 2, 1, 0, 2, 2, 0, 1, 0, 1, 3, 3, 0, 0, 3, 2, 2, 3, 1, 2, 1, 3, 1, 0, 2, 3, 2, 2, 3, 1, 2, 0, 2, 0, 2, 3, 0, 3
                ),
                listOf(
                    3, 1, 1, 0, 0, 0, 1, 2, 2, 2, 0, 0, 0, 1, 0, 0, 0, 2, 1, 1, 2, 2, 0, 2, 0, 3, 3, 1, 1, 0, 0, 0, 2, 1, 0, 3, 3, 3, 3, 0
                ),
                listOf(
                    0, 3, 0, 1, 0, 2, 0, 1, 0, 0, 3, 0, 0, 2, 1, 3, 0, 0, 0, 2, 2, 2, 0, 3, 0, 3, 0, 2, 2, 1, 3, 3, 3, 1, 2, 2, 1, 3, 1, 2
                ),
                listOf(
                    1, 3, 1, 0, 2, 2, 0, 1, 1, 1, 1, 1, 1, 0, 2, 2, 1, 0, 3, 0, 0, 2, 3, 1, 0, 3, 1, 1, 0, 2, 1, 1, 3, 3, 1, 3, 3, 2, 1, 0
                ),
                listOf(
                    0, 0, 0, 1, 3, 0, 1, 1, 3, 2, 1, 3, 3, 3, 3, 1, 1, 2, 1, 0, 3, 2, 2, 3, 0, 3, 1, 1, 3, 2, 1, 2, 3, 1, 1, 2, 0, 0, 3, 0
                ),
                listOf(
                    1, 2, 2, 2, 2, 1, 3, 1, 2, 2, 1, 2, 3, 3, 1, 0, 0, 2, 1, 0, 2, 1, 0, 1, 3, 0, 1, 1, 1, 0, 1, 3, 0, 0, 1, 3, 2, 2, 2, 0
                ),
                listOf(
                    3, 1, 3, 1, 1, 0, 1, 0, 3, 3, 1, 0, 0, 1, 2, 0, 3, 1, 2, 0, 2, 0, 1, 3, 2, 1, 2, 3, 2, 0, 2, 1, 3, 2, 2, 0, 3, 2, 1, 3
                ),
                listOf(
                    0, 2, 2, 1, 3, 0, 2, 2, 1, 1, 3, 1, 3, 0, 2, 1, 0, 2, 2, 3, 3, 1, 1, 0, 2, 2, 1, 2, 0, 1, 2, 0, 1, 3, 2, 3, 3, 1, 0, 1
                ),
                listOf(
                    0, 2, 2, 3, 2, 0, 3, 1, 0, 0, 3, 2, 2, 0, 3, 2, 2, 0, 0, 1, 1, 2, 0, 1, 3, 3, 2, 1, 1, 1, 0, 0, 3, 0, 2, 1, 3, 3, 0, 3
                ),
                listOf(
                    2, 0, 2, 1, 0, 1, 1, 2, 2, 3, 3, 3, 3, 0, 1, 2, 3, 1, 3, 1, 2, 3, 2, 3, 2, 1, 1, 1, 1, 2, 0, 1, 3, 3, 1, 0, 3, 2, 1, 0
                ),
                listOf(
                    1, 1, 0, 3, 0, 2, 1, 0, 2, 1, 2, 0, 1, 1, 3, 0, 1, 3, 0, 1, 1, 0, 1, 0, 0, 2, 1, 1, 0, 3, 0, 1, 1, 2, 1, 2, 2, 2, 2, 2
                ),
                listOf(
                    3, 0, 3, 3, 3, 1, 1, 2, 1, 3, 2, 1, 0, 0, 0, 3, 0, 3, 0, 0, 2, 0, 2, 2, 3, 0, 1, 2, 0, 1, 2, 3, 1, 0, 0, 1, 1, 3, 3, 1
                ),
                listOf(
                    0, 0, 2, 0, 2, 1, 2, 2, 1, 1, 2, 1, 1, 0, 2, 2, 3, 0, 1, 0, 2, 1, 0, 3, 3, 1, 2, 3, 1, 2, 0, 1, 3, 3, 2, 1, 3, 1, 3, 1
                ),
                listOf(
                    2, 3, 2, 0, 2, 2, 2, 2, 0, 2, 1, 1, 1, 2, 3, 3, 1, 0, 0, 1, 2, 0, 0, 2, 1, 1, 0, 0, 1, 2, 0, 3, 1, 0, 1, 1, 1, 0, 0, 0
                ),
                listOf(
                    0, 0, 0, 2, 2, 2, 1, 0, 1, 1, 0, 2, 0, 3, 3, 1, 3, 3, 0, 3, 2, 2, 0, 0, 2, 1, 0, 2, 2, 0, 0, 0, 2, 3, 0, 3, 0, 3, 1, 1
                ),
                listOf(
                    2, 2, 3, 2, 2, 1, 2, 1, 1, 0, 3, 1, 3, 2, 2, 2, 1, 0, 1, 1, 2, 2, 0, 2, 1, 0, 2, 2, 0, 0, 2, 1, 1, 2, 1, 2, 0, 3, 0, 2
                ),
                listOf(
                    2, 0, 3, 1, 3, 0, 3, 1, 0, 0, 3, 2, 0, 0, 2, 2, 0, 1, 2, 3, 3, 2, 0, 1, 1, 0, 1, 3, 3, 3, 3, 3, 1, 3, 2, 0, 3, 0, 1, 3
                ),
                listOf(
                    3, 3, 3, 3, 1, 1, 1, 0, 2, 2, 1, 2, 1, 2, 3, 3, 1, 3, 3, 2, 1, 0, 2, 2, 2, 3, 1, 3, 2, 3, 1, 0, 2, 1, 2, 1, 2, 1, 3, 1
                ),
                listOf(
                    2, 2, 0, 3, 2, 0, 0, 0, 1, 3, 0, 3, 1, 1, 0, 2, 0, 1, 3, 0, 3, 0, 2, 3, 2, 0, 1, 2, 0, 0, 2, 3, 2, 3, 1, 3, 2, 2, 0, 2
                ),
                listOf(
                    2, 0, 1, 3, 1, 1, 2, 2, 0, 3, 1, 3, 2, 1, 3, 2, 3, 2, 1, 3, 2, 1, 1, 3, 3, 0, 1, 0, 1, 3, 2, 0, 2, 1, 2, 1, 2, 3, 0, 3
                ),
                listOf(
                    2, 0, 1, 0, 2, 3, 3, 3, 2, 2, 2, 1, 1, 0, 1, 2, 0, 0, 0, 3, 0, 2, 2, 0, 1, 0, 0, 3, 1, 3, 3, 3, 1, 0, 3, 1, 2, 3, 1, 2
                ),
                listOf(
                    0, 1, 3, 3, 0, 2, 0, 2, 0, 1, 3, 2, 0, 0, 3, 1, 2, 2, 1, 1, 3, 2, 3, 3, 0, 3, 1, 2, 3, 2, 0, 2, 2, 2, 0, 3, 2, 3, 3, 1
                ),
                listOf(
                    0, 3, 0, 1, 0, 1, 3, 0, 0, 1, 3, 0, 1, 3, 3, 3, 1, 3, 3, 1, 2, 3, 1, 3, 2, 3, 2, 3, 1, 3, 1, 2, 0, 1, 3, 1, 1, 3, 3, 2
                ),
                listOf(
                    2, 2, 3, 1, 0, 3, 0, 3, 1, 3, 2, 3, 3, 3, 2, 2, 1, 2, 1, 1, 2, 0, 2, 2, 1, 2, 1, 3, 2, 0, 2, 3, 3, 0, 1, 2, 1, 0, 2, 3
                ),
                listOf(
                    2, 2, 1, 2, 2, 1, 2, 1, 0, 0, 2, 2, 2, 1, 1, 3, 3, 3, 3, 1, 3, 2, 3, 1, 3, 2, 3, 2, 1, 1, 1, 3, 0, 3, 0, 2, 1, 1, 3, 1
                ),
                listOf(
                    2, 0, 1, 0, 1, 2, 1, 2, 3, 3, 3, 1, 1, 3, 2, 2, 1, 0, 1, 0, 2, 2, 2, 3, 1, 2, 0, 3, 3, 3, 0, 3, 0, 1, 3, 2, 2, 1, 1, 0
                ),
                listOf(
                    1, 2, 2, 2, 1, 1, 2, 2, 0, 0, 0, 1, 2, 0, 2, 0, 2, 1, 0, 0, 3, 1, 2, 1, 3, 3, 1, 1, 2, 3, 2, 2, 1, 0, 2, 2, 0, 3, 0, 0
                ),
                listOf(
                    1, 0, 3, 2, 2, 3, 3, 2, 3, 3, 0, 3, 2, 3, 0, 3, 3, 0, 2, 2, 3, 3, 2, 2, 3, 0, 3, 3, 3, 3, 1, 0, 1, 0, 0, 2, 2, 2, 2, 0
                ),
                listOf(
                    2, 0, 3, 1, 2, 0, 2, 3, 1, 3, 0, 3, 1, 1, 3, 2, 0, 1, 2, 1, 2, 2, 0, 0, 2, 0, 2, 2, 2, 2, 1, 1, 0, 2, 0, 3, 0, 2, 3, 1
                )
            ), listOf(
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 25),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 25),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk86(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_007", "Legendary", 40, 60, listOf(
                listOf(
                    1, 1, 0, 3, 0, 3, 3, 0, 3, 3, 3, 0, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 3, 3, 2, 0, 1, 0, 1, 1, 2, 0, 2, 2, 2, 0, 1, 0, 1, 2
                ),
                listOf(
                    3, 3, 0, 3, 2, 0, 2, 0, 2, 1, 2, 3, 1, 2, 0, 2, 3, 0, 3, 1, 2, 0, 3, 1, 2, 3, 3, 3, 3, 2, 1, 2, 2, 2, 1, 0, 0, 3, 1, 2
                ),
                listOf(
                    0, 2, 0, 2, 1, 1, 2, 0, 1, 0, 3, 2, 0, 1, 3, 1, 2, 0, 0, 2, 3, 0, 0, 0, 1, 3, 3, 0, 3, 0, 1, 3, 2, 2, 1, 0, 3, 2, 2, 0
                ),
                listOf(
                    3, 0, 3, 3, 1, 2, 0, 0, 3, 2, 2, 0, 1, 3, 2, 1, 0, 2, 1, 1, 3, 2, 1, 3, 3, 2, 0, 0, 3, 1, 1, 2, 2, 1, 1, 2, 1, 2, 2, 1
                ),
                listOf(
                    0, 1, 0, 0, 2, 1, 0, 2, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 3, 2, 0, 1, 2, 0, 3, 2, 0, 3, 1, 3, 2, 2, 0, 0, 0, 3, 3, 1, 0, 1
                ),
                listOf(
                    0, 3, 0, 1, 3, 0, 0, 2, 0, 1, 1, 2, 1, 1, 0, 0, 3, 2, 3, 0, 3, 1, 1, 1, 3, 2, 1, 1, 0, 2, 2, 0, 2, 0, 1, 2, 0, 2, 3, 0
                ),
                listOf(
                    3, 2, 1, 0, 2, 3, 2, 0, 1, 2, 2, 0, 0, 0, 2, 1, 1, 2, 3, 1, 2, 2, 0, 0, 3, 1, 3, 0, 2, 1, 3, 0, 3, 0, 2, 2, 1, 3, 1, 1
                ),
                listOf(
                    3, 3, 2, 3, 0, 0, 3, 1, 2, 3, 2, 2, 1, 3, 2, 0, 2, 3, 3, 3, 1, 0, 0, 2, 1, 3, 2, 2, 2, 1, 3, 1, 2, 1, 2, 1, 3, 2, 0, 1
                ),
                listOf(
                    2, 3, 1, 0, 3, 1, 0, 2, 1, 3, 1, 1, 3, 1, 2, 2, 2, 1, 1, 0, 0, 1, 0, 0, 1, 1, 3, 1, 3, 1, 1, 2, 1, 0, 3, 0, 2, 2, 3, 3
                ),
                listOf(
                    1, 2, 2, 0, 1, 3, 0, 1, 2, 2, 0, 1, 2, 2, 3, 0, 1, 3, 3, 2, 3, 1, 3, 1, 2, 0, 2, 0, 1, 0, 3, 2, 3, 2, 3, 2, 2, 1, 0, 0
                ),
                listOf(
                    0, 3, 1, 0, 2, 2, 0, 1, 0, 2, 2, 1, 0, 2, 2, 2, 0, 2, 2, 3, 2, 3, 0, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 0, 0, 1, 0, 1, 2
                ),
                listOf(
                    1, 0, 3, 2, 1, 1, 3, 0, 1, 1, 3, 0, 0, 2, 1, 1, 2, 3, 1, 3, 1, 1, 0, 2, 3, 1, 2, 3, 1, 0, 1, 0, 1, 3, 0, 1, 1, 1, 1, 2
                ),
                listOf(
                    2, 2, 2, 1, 0, 2, 0, 3, 2, 1, 1, 1, 2, 0, 3, 0, 2, 3, 0, 0, 3, 1, 2, 2, 0, 3, 2, 3, 2, 1, 3, 0, 2, 2, 0, 3, 1, 3, 1, 0
                ),
                listOf(
                    1, 1, 0, 0, 2, 3, 2, 2, 0, 1, 2, 1, 3, 0, 0, 2, 2, 0, 0, 1, 3, 1, 0, 1, 2, 1, 2, 0, 2, 2, 2, 0, 2, 1, 2, 3, 2, 0, 1, 2
                ),
                listOf(
                    0, 1, 1, 2, 0, 1, 0, 1, 3, 0, 1, 3, 0, 3, 1, 1, 3, 0, 1, 2, 3, 2, 3, 1, 1, 0, 2, 3, 2, 2, 2, 3, 1, 2, 3, 0, 3, 3, 2, 2
                ),
                listOf(
                    0, 2, 1, 2, 0, 2, 3, 3, 3, 2, 1, 3, 1, 3, 1, 1, 3, 1, 3, 3, 3, 2, 2, 1, 3, 2, 0, 2, 1, 2, 2, 2, 3, 1, 3, 3, 3, 0, 1, 1
                ),
                listOf(
                    2, 1, 2, 2, 3, 2, 0, 3, 1, 2, 0, 0, 2, 0, 1, 3, 0, 3, 2, 2, 3, 1, 1, 1, 0, 3, 0, 3, 3, 3, 2, 0, 0, 0, 1, 3, 3, 2, 0, 3
                ),
                listOf(
                    3, 2, 0, 0, 2, 3, 2, 0, 1, 3, 2, 1, 0, 3, 0, 0, 2, 2, 1, 1, 0, 1, 3, 2, 1, 2, 0, 3, 1, 2, 2, 3, 1, 1, 1, 1, 3, 2, 0, 2
                ),
                listOf(
                    2, 1, 1, 2, 3, 0, 1, 2, 0, 1, 2, 2, 1, 0, 2, 0, 0, 1, 1, 0, 1, 1, 1, 3, 3, 3, 2, 1, 1, 0, 0, 3, 0, 2, 0, 2, 3, 3, 2, 1
                ),
                listOf(
                    0, 3, 2, 2, 3, 1, 1, 3, 3, 2, 2, 2, 1, 0, 3, 1, 1, 3, 1, 1, 1, 2, 2, 3, 0, 3, 1, 1, 3, 0, 2, 2, 0, 2, 1, 2, 3, 1, 3, 2
                ),
                listOf(
                    1, 0, 0, 1, 1, 2, 3, 2, 3, 1, 2, 0, 1, 2, 0, 0, 2, 3, 2, 0, 1, 1, 3, 0, 3, 2, 1, 1, 2, 3, 1, 0, 1, 1, 0, 0, 3, 1, 1, 1
                ),
                listOf(
                    1, 2, 0, 2, 2, 3, 2, 0, 3, 1, 1, 1, 2, 2, 3, 2, 0, 2, 3, 0, 3, 2, 0, 0, 2, 2, 3, 0, 1, 1, 3, 0, 1, 3, 0, 3, 0, 0, 0, 3
                ),
                listOf(
                    3, 3, 1, 1, 0, 2, 2, 1, 0, 1, 1, 0, 1, 3, 1, 1, 1, 0, 0, 1, 1, 2, 1, 1, 2, 1, 2, 0, 1, 0, 1, 1, 1, 0, 2, 1, 1, 3, 0, 3
                ),
                listOf(
                    2, 0, 1, 2, 3, 2, 2, 1, 0, 1, 1, 1, 0, 2, 1, 2, 1, 3, 1, 1, 0, 3, 1, 2, 0, 0, 0, 3, 1, 0, 1, 1, 3, 2, 1, 2, 2, 3, 0, 1
                ),
                listOf(
                    2, 2, 1, 0, 2, 1, 3, 1, 0, 3, 3, 0, 3, 3, 1, 3, 3, 0, 0, 3, 1, 1, 1, 3, 2, 3, 2, 2, 3, 2, 2, 2, 1, 1, 0, 1, 2, 1, 3, 3
                ),
                listOf(
                    3, 2, 0, 0, 1, 1, 2, 3, 0, 3, 3, 3, 2, 3, 1, 2, 3, 1, 0, 0, 2, 3, 1, 2, 1, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 1, 1, 1, 2
                ),
                listOf(
                    3, 0, 3, 2, 0, 2, 0, 2, 0, 0, 1, 1, 0, 1, 3, 3, 2, 0, 2, 1, 2, 0, 0, 3, 1, 0, 1, 1, 1, 1, 1, 0, 2, 2, 2, 1, 3, 1, 0, 3
                ),
                listOf(
                    3, 0, 2, 2, 1, 1, 0, 2, 0, 1, 0, 2, 0, 0, 0, 3, 0, 3, 0, 3, 1, 1, 3, 1, 1, 1, 0, 1, 3, 1, 1, 1, 2, 3, 0, 0, 3, 2, 1, 1
                ),
                listOf(
                    3, 2, 0, 2, 2, 2, 2, 2, 0, 3, 2, 2, 2, 0, 3, 0, 1, 1, 2, 3, 0, 2, 1, 2, 1, 2, 0, 0, 1, 3, 3, 1, 3, 0, 3, 3, 0, 1, 2, 1
                ),
                listOf(
                    1, 1, 3, 0, 3, 1, 3, 1, 3, 0, 0, 2, 1, 1, 0, 3, 3, 0, 3, 0, 3, 3, 3, 1, 0, 1, 2, 2, 1, 2, 1, 3, 1, 3, 3, 2, 2, 1, 1, 1
                ),
                listOf(
                    1, 3, 3, 3, 3, 1, 2, 1, 1, 1, 3, 1, 3, 1, 3, 3, 2, 2, 3, 3, 1, 1, 2, 1, 2, 2, 2, 1, 2, 2, 0, 3, 0, 1, 3, 1, 2, 0, 2, 2
                ),
                listOf(
                    3, 0, 1, 1, 3, 0, 2, 3, 1, 1, 2, 1, 1, 0, 2, 2, 3, 2, 3, 2, 2, 1, 3, 0, 2, 2, 1, 3, 0, 2, 1, 1, 2, 0, 0, 2, 3, 1, 0, 3
                ),
                listOf(
                    3, 3, 0, 3, 3, 2, 3, 2, 0, 2, 0, 2, 0, 3, 1, 2, 3, 1, 2, 3, 3, 3, 2, 3, 3, 2, 1, 1, 2, 0, 2, 1, 3, 2, 1, 2, 2, 1, 1, 0
                ),
                listOf(
                    1, 0, 1, 2, 0, 2, 3, 3, 2, 1, 3, 1, 3, 3, 0, 1, 0, 1, 2, 2, 0, 1, 2, 1, 3, 1, 1, 2, 1, 1, 0, 0, 3, 2, 2, 1, 3, 3, 0, 3
                ),
                listOf(
                    3, 3, 3, 1, 1, 3, 2, 1, 1, 1, 1, 0, 1, 2, 3, 3, 2, 1, 3, 1, 0, 3, 1, 1, 1, 2, 2, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1
                ),
                listOf(
                    0, 3, 3, 2, 2, 1, 1, 2, 0, 3, 3, 0, 1, 3, 0, 3, 1, 0, 3, 0, 1, 2, 0, 0, 3, 1, 3, 0, 1, 3, 3, 3, 0, 1, 1, 1, 0, 0, 3, 0
                ),
                listOf(
                    1, 3, 0, 3, 0, 1, 1, 0, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 2, 2, 1, 0, 3, 0, 3, 1, 0, 2, 3, 2, 3, 3, 2, 0, 0, 0, 3, 1, 1
                ),
                listOf(
                    0, 3, 2, 0, 1, 2, 0, 1, 3, 0, 0, 2, 1, 2, 1, 0, 3, 3, 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 1, 1, 2, 3, 0, 1, 3, 1, 0, 3, 2, 1
                ),
                listOf(
                    1, 3, 0, 2, 2, 0, 1, 1, 3, 0, 0, 2, 1, 1, 0, 2, 1, 2, 1, 2, 3, 2, 0, 3, 2, 0, 2, 1, 1, 2, 0, 2, 0, 3, 2, 1, 0, 2, 0, 0
                ),
                listOf(
                    3, 3, 2, 1, 2, 1, 1, 3, 0, 2, 2, 1, 0, 2, 2, 2, 2, 1, 2, 1, 2, 1, 1, 0, 0, 2, 1, 1, 2, 0, 2, 2, 2, 0, 0, 3, 1, 3, 0, 2
                ),
                listOf(
                    2, 1, 2, 1, 0, 0, 2, 1, 3, 3, 1, 0, 3, 2, 0, 2, 0, 1, 3, 0, 0, 1, 1, 1, 2, 2, 0, 2, 0, 3, 0, 1, 1, 3, 3, 3, 1, 1, 3, 0
                ),
                listOf(
                    1, 1, 2, 2, 3, 1, 1, 1, 1, 1, 2, 2, 2, 3, 0, 3, 2, 0, 3, 2, 1, 1, 0, 1, 0, 3, 0, 3, 2, 2, 1, 1, 0, 2, 1, 1, 1, 0, 2, 2
                ),
                listOf(
                    2, 1, 3, 3, 1, 2, 1, 2, 1, 0, 3, 0, 1, 0, 0, 2, 0, 2, 1, 3, 1, 1, 0, 1, 3, 0, 0, 1, 0, 3, 0, 1, 0, 3, 3, 2, 1, 3, 0, 1
                ),
                listOf(
                    0, 0, 3, 2, 1, 3, 2, 0, 2, 2, 1, 2, 2, 1, 3, 2, 1, 2, 0, 0, 0, 3, 0, 2, 1, 3, 3, 2, 3, 1, 1, 2, 2, 2, 0, 2, 1, 0, 2, 0
                ),
                listOf(
                    1, 2, 1, 1, 1, 2, 2, 0, 1, 0, 0, 2, 2, 3, 3, 0, 3, 3, 0, 3, 0, 2, 0, 2, 0, 1, 2, 1, 3, 2, 1, 0, 0, 0, 3, 3, 2, 3, 1, 2
                ),
                listOf(
                    1, 0, 3, 1, 3, 2, 3, 1, 0, 3, 2, 0, 3, 2, 1, 2, 1, 0, 3, 1, 2, 2, 1, 2, 3, 1, 2, 0, 0, 0, 3, 1, 1, 0, 0, 3, 2, 1, 2, 3
                ),
                listOf(
                    3, 0, 1, 3, 1, 3, 2, 3, 1, 1, 3, 0, 2, 2, 3, 2, 1, 1, 0, 3, 3, 3, 1, 2, 2, 2, 2, 3, 2, 1, 1, 3, 0, 1, 1, 2, 1, 1, 3, 2
                ),
                listOf(
                    1, 0, 0, 0, 0, 2, 1, 3, 2, 1, 3, 1, 1, 0, 0, 1, 3, 1, 0, 3, 2, 1, 3, 0, 0, 2, 0, 2, 2, 2, 2, 2, 0, 1, 1, 1, 1, 0, 3, 3
                ),
                listOf(
                    3, 3, 2, 2, 1, 0, 3, 3, 3, 3, 0, 2, 2, 3, 1, 2, 1, 0, 2, 2, 3, 1, 0, 3, 0, 2, 1, 3, 0, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1
                ),
                listOf(
                    0, 2, 0, 0, 2, 2, 2, 1, 0, 2, 1, 3, 2, 2, 3, 1, 1, 0, 2, 1, 0, 1, 2, 3, 3, 3, 3, 2, 3, 0, 2, 3, 0, 3, 1, 0, 2, 3, 0, 0
                ),
                listOf(
                    1, 0, 3, 2, 1, 0, 3, 1, 1, 2, 0, 3, 2, 1, 1, 0, 3, 2, 0, 1, 3, 1, 3, 0, 0, 1, 0, 1, 2, 0, 2, 2, 3, 2, 1, 3, 1, 0, 2, 3
                ),
                listOf(
                    3, 2, 3, 2, 2, 0, 1, 0, 1, 3, 1, 1, 1, 2, 3, 3, 0, 1, 3, 2, 2, 0, 3, 2, 3, 1, 2, 0, 2, 1, 3, 0, 2, 0, 2, 1, 2, 3, 3, 0
                ),
                listOf(
                    1, 3, 1, 0, 2, 2, 2, 0, 1, 3, 1, 2, 2, 1, 3, 2, 2, 0, 0, 3, 0, 1, 2, 2, 3, 3, 1, 2, 1, 0, 1, 1, 1, 2, 2, 1, 1, 2, 3, 3
                ),
                listOf(
                    0, 0, 0, 3, 3, 3, 0, 0, 0, 3, 0, 1, 2, 3, 2, 0, 0, 3, 2, 0, 1, 1, 2, 1, 1, 3, 2, 2, 1, 1, 2, 1, 1, 1, 0, 1, 1, 3, 0, 3
                ),
                listOf(
                    0, 2, 3, 2, 1, 2, 0, 2, 0, 3, 1, 1, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 3, 3, 1, 1, 1, 3, 0, 2, 2, 2, 3, 2, 1, 2, 3, 1, 1
                ),
                listOf(
                    2, 3, 1, 3, 0, 1, 2, 1, 2, 1, 1, 3, 3, 3, 1, 3, 1, 3, 2, 0, 2, 2, 2, 1, 2, 1, 0, 3, 0, 3, 2, 0, 1, 0, 0, 3, 2, 1, 2, 1
                ),
                listOf(
                    0, 1, 1, 1, 0, 3, 0, 2, 2, 2, 0, 3, 2, 0, 2, 1, 3, 0, 3, 2, 3, 1, 3, 3, 3, 1, 2, 3, 3, 1, 2, 0, 3, 3, 3, 3, 0, 1, 1, 1
                ),
                listOf(
                    0, 3, 3, 1, 0, 1, 3, 3, 3, 1, 0, 2, 0, 1, 3, 1, 2, 2, 3, 2, 2, 1, 2, 3, 2, 0, 0, 1, 3, 1, 1, 1, 3, 1, 2, 1, 0, 3, 3, 1
                ),
                listOf(
                    0, 3, 1, 0, 2, 2, 3, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 2, 1, 3, 2, 1, 2, 1, 0, 3, 0, 2, 3, 1, 1, 1, 2, 2, 3, 2, 0, 1, 1
                ),
                listOf(
                    3, 0, 2, 2, 0, 0, 3, 0, 3, 3, 1, 1, 3, 1, 1, 0, 0, 3, 1, 1, 0, 0, 1, 1, 1, 0, 1, 2, 3, 0, 3, 0, 1, 3, 0, 3, 1, 1, 0, 0
                )
            ), listOf(
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(3, 25),
                Tank(1, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk87(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_008", "Legendary", 40, 60, listOf(
                listOf(
                    2, 1, 2, 3, 0, 1, 2, 0, 2, 2, 1, 3, 1, 2, 0, 0, 3, 1, 3, 1, 3, 2, 0, 1, 1, 2, 3, 1, 0, 1, 0, 1, 2, 3, 1, 1, 1, 1, 1, 2
                ),
                listOf(
                    1, 3, 2, 3, 2, 2, 0, 3, 1, 3, 3, 0, 0, 2, 1, 2, 0, 2, 2, 3, 3, 2, 1, 3, 0, 1, 0, 2, 2, 0, 2, 3, 3, 1, 2, 0, 0, 2, 2, 0
                ),
                listOf(
                    2, 3, 0, 3, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1, 2, 2, 0, 0, 2, 2, 0, 3, 0, 0, 0, 3, 2, 1, 0, 0, 1, 1, 0, 3, 0, 2, 3, 2, 3, 0
                ),
                listOf(
                    1, 2, 1, 2, 0, 0, 3, 0, 3, 1, 1, 1, 0, 1, 0, 0, 3, 3, 1, 0, 2, 2, 3, 0, 0, 1, 2, 0, 3, 2, 3, 1, 2, 2, 1, 2, 2, 1, 2, 3
                ),
                listOf(
                    2, 0, 1, 1, 2, 1, 2, 0, 0, 1, 0, 2, 2, 3, 2, 1, 0, 1, 2, 3, 2, 1, 2, 0, 3, 1, 1, 1, 0, 1, 2, 2, 0, 3, 2, 3, 1, 3, 1, 0
                ),
                listOf(
                    2, 3, 3, 2, 2, 1, 0, 1, 2, 0, 1, 3, 3, 0, 1, 0, 2, 1, 2, 1, 3, 3, 0, 0, 2, 3, 2, 2, 3, 3, 2, 0, 3, 1, 3, 3, 2, 1, 0, 2
                ),
                listOf(
                    3, 3, 2, 1, 2, 2, 1, 0, 1, 0, 2, 0, 1, 1, 0, 2, 2, 2, 0, 3, 3, 0, 3, 2, 1, 1, 0, 2, 1, 3, 3, 1, 0, 2, 3, 2, 3, 1, 1, 1
                ),
                listOf(
                    2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 0, 1, 0, 0, 1, 1, 1, 3, 1, 2, 3, 0, 1, 3, 1, 1, 1, 3, 2, 3, 0, 3, 3, 3, 2, 3, 2, 1, 3
                ),
                listOf(
                    0, 1, 1, 1, 1, 3, 3, 2, 3, 0, 2, 1, 2, 2, 0, 0, 3, 0, 1, 0, 0, 2, 1, 3, 0, 2, 2, 2, 3, 3, 1, 2, 1, 0, 3, 0, 3, 2, 2, 1
                ),
                listOf(
                    1, 2, 0, 0, 3, 1, 2, 1, 0, 3, 1, 2, 2, 3, 2, 3, 1, 1, 0, 3, 3, 1, 2, 3, 0, 2, 2, 2, 2, 2, 1, 3, 3, 1, 0, 2, 1, 3, 0, 1
                ),
                listOf(
                    2, 3, 2, 0, 3, 0, 2, 2, 2, 2, 0, 0, 2, 3, 0, 3, 2, 2, 2, 0, 0, 3, 0, 1, 3, 1, 2, 2, 2, 0, 2, 0, 1, 1, 0, 0, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 1, 0, 1, 3, 0, 1, 3, 3, 0, 2, 1, 2, 1, 3, 0, 1, 2, 3, 0, 0, 2, 3, 2, 0, 1, 1, 0, 0, 1, 3, 3, 3, 3, 1, 3, 1, 0, 1
                ),
                listOf(
                    2, 3, 2, 2, 0, 2, 2, 2, 1, 1, 0, 0, 1, 0, 2, 2, 1, 1, 1, 1, 3, 0, 1, 0, 3, 3, 0, 0, 1, 0, 0, 1, 2, 2, 0, 2, 0, 0, 0, 1
                ),
                listOf(
                    1, 2, 1, 0, 1, 1, 0, 2, 0, 1, 3, 3, 3, 1, 2, 2, 3, 2, 2, 2, 3, 2, 2, 0, 2, 1, 0, 1, 0, 3, 0, 0, 2, 3, 1, 2, 0, 3, 3, 0
                ),
                listOf(
                    0, 0, 2, 2, 3, 3, 3, 2, 3, 2, 1, 3, 1, 3, 1, 2, 0, 0, 2, 2, 2, 1, 2, 1, 3, 2, 1, 2, 1, 3, 0, 1, 3, 1, 1, 2, 2, 2, 3, 2
                ),
                listOf(
                    1, 2, 0, 3, 1, 0, 1, 2, 2, 0, 3, 3, 2, 3, 1, 2, 3, 3, 1, 1, 3, 0, 2, 2, 3, 0, 3, 1, 1, 2, 1, 1, 0, 1, 1, 3, 1, 1, 1, 2
                ),
                listOf(
                    1, 2, 2, 2, 2, 1, 3, 0, 0, 2, 2, 0, 0, 2, 3, 1, 2, 0, 3, 0, 2, 0, 3, 2, 1, 0, 1, 2, 1, 1, 1, 0, 2, 1, 3, 3, 2, 2, 3, 2
                ),
                listOf(
                    2, 0, 2, 0, 2, 3, 2, 3, 0, 0, 1, 2, 2, 2, 1, 2, 3, 1, 3, 0, 1, 3, 3, 3, 0, 1, 0, 2, 3, 0, 1, 0, 1, 2, 0, 2, 1, 1, 2, 3
                ),
                listOf(
                    1, 0, 2, 2, 3, 1, 3, 3, 3, 0, 2, 3, 2, 0, 2, 0, 0, 2, 0, 3, 0, 3, 2, 1, 0, 2, 2, 2, 2, 2, 2, 2, 0, 3, 1, 3, 2, 3, 3, 0
                ),
                listOf(
                    3, 3, 1, 2, 0, 1, 0, 1, 3, 2, 0, 1, 2, 2, 3, 2, 0, 2, 1, 1, 2, 2, 2, 1, 2, 2, 1, 1, 1, 3, 1, 0, 0, 3, 0, 2, 0, 2, 0, 0
                ),
                listOf(
                    0, 3, 3, 1, 0, 1, 3, 2, 0, 1, 0, 3, 3, 0, 3, 1, 0, 2, 2, 0, 2, 3, 3, 0, 2, 0, 0, 0, 0, 1, 2, 2, 3, 1, 1, 0, 1, 1, 0, 2
                ),
                listOf(
                    0, 3, 2, 2, 1, 0, 2, 3, 1, 0, 1, 3, 0, 1, 2, 3, 0, 2, 2, 0, 1, 3, 0, 2, 0, 1, 2, 2, 0, 2, 2, 0, 2, 1, 1, 2, 2, 1, 1, 1
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 1, 2, 1, 0, 1, 0, 1, 2, 1, 0, 2, 1, 1, 1, 1, 1, 1, 2, 3, 2, 1, 2, 3, 1, 1, 2, 1, 3, 0, 2, 1, 3, 3, 0
                ),
                listOf(
                    3, 2, 0, 3, 1, 0, 0, 3, 0, 1, 2, 3, 1, 2, 2, 3, 0, 2, 3, 0, 0, 2, 2, 2, 1, 1, 2, 2, 0, 3, 1, 0, 2, 3, 1, 1, 3, 2, 3, 3
                ),
                listOf(
                    0, 3, 3, 0, 2, 2, 1, 0, 1, 0, 2, 2, 2, 0, 3, 0, 3, 3, 1, 0, 2, 0, 2, 1, 1, 1, 3, 2, 2, 2, 3, 2, 1, 1, 2, 2, 3, 2, 0, 1
                ),
                listOf(
                    1, 3, 1, 3, 3, 0, 2, 0, 2, 3, 0, 2, 3, 1, 0, 2, 0, 0, 1, 3, 2, 2, 1, 3, 2, 0, 3, 2, 2, 2, 1, 3, 1, 3, 1, 0, 3, 3, 0, 2
                ),
                listOf(
                    0, 2, 2, 3, 0, 3, 1, 2, 0, 0, 1, 1, 3, 3, 2, 3, 3, 1, 1, 2, 2, 3, 3, 2, 1, 1, 2, 3, 2, 2, 2, 1, 3, 3, 3, 3, 2, 2, 0, 1
                ),
                listOf(
                    3, 3, 3, 2, 3, 2, 3, 3, 3, 2, 2, 2, 2, 0, 1, 1, 3, 2, 2, 1, 0, 2, 3, 3, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 2, 2, 3, 1, 0
                ),
                listOf(
                    1, 0, 3, 3, 2, 2, 0, 3, 1, 1, 3, 3, 2, 2, 1, 2, 3, 3, 3, 3, 3, 1, 0, 1, 2, 3, 1, 1, 0, 2, 2, 0, 2, 3, 3, 2, 0, 2, 2, 0
                ),
                listOf(
                    2, 0, 3, 2, 0, 0, 3, 0, 3, 1, 0, 2, 0, 3, 1, 2, 1, 1, 1, 2, 3, 3, 1, 2, 0, 1, 3, 0, 3, 2, 3, 2, 2, 0, 2, 1, 1, 1, 3, 0
                ),
                listOf(
                    1, 2, 1, 2, 1, 1, 3, 2, 2, 1, 0, 2, 1, 2, 2, 0, 3, 1, 0, 3, 0, 1, 0, 2, 3, 2, 2, 2, 0, 0, 0, 3, 0, 3, 3, 2, 2, 3, 0, 0
                ),
                listOf(
                    2, 3, 2, 2, 2, 3, 3, 3, 0, 0, 2, 0, 3, 1, 3, 0, 2, 3, 1, 2, 3, 0, 1, 0, 2, 2, 3, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 2, 1, 3
                ),
                listOf(
                    1, 2, 0, 2, 3, 1, 3, 0, 0, 3, 2, 2, 2, 1, 3, 2, 2, 0, 1, 2, 1, 2, 2, 1, 1, 0, 1, 0, 3, 1, 0, 2, 0, 3, 2, 1, 0, 2, 0, 1
                ),
                listOf(
                    3, 0, 3, 1, 3, 2, 2, 1, 0, 2, 1, 2, 2, 0, 3, 2, 3, 2, 0, 1, 0, 3, 1, 0, 0, 2, 1, 0, 2, 1, 1, 1, 1, 2, 0, 3, 2, 3, 0, 1
                ),
                listOf(
                    2, 1, 2, 3, 1, 3, 0, 0, 3, 3, 3, 3, 2, 2, 3, 0, 1, 0, 2, 0, 0, 0, 1, 1, 1, 3, 1, 2, 0, 2, 2, 1, 1, 0, 3, 0, 1, 1, 3, 0
                ),
                listOf(
                    2, 0, 3, 2, 2, 2, 2, 0, 2, 0, 1, 0, 2, 2, 2, 3, 1, 2, 3, 1, 1, 2, 0, 3, 1, 0, 0, 3, 2, 3, 2, 0, 0, 1, 2, 2, 3, 3, 0, 3
                ),
                listOf(
                    3, 1, 1, 3, 1, 2, 2, 0, 3, 0, 1, 3, 2, 3, 1, 2, 0, 0, 0, 2, 0, 1, 2, 0, 3, 2, 3, 2, 2, 3, 2, 0, 2, 2, 2, 2, 3, 2, 0, 3
                ),
                listOf(
                    2, 3, 2, 3, 3, 0, 2, 2, 2, 0, 2, 3, 2, 3, 3, 1, 2, 0, 2, 1, 2, 2, 3, 1, 3, 1, 1, 2, 1, 3, 0, 1, 1, 3, 1, 3, 3, 0, 2, 2
                ),
                listOf(
                    1, 0, 1, 2, 2, 1, 0, 0, 1, 2, 1, 0, 1, 0, 2, 0, 2, 3, 3, 1, 2, 1, 0, 0, 3, 2, 3, 2, 2, 0, 2, 1, 1, 3, 0, 1, 2, 0, 2, 0
                ),
                listOf(
                    1, 3, 1, 1, 2, 2, 2, 2, 1, 3, 0, 1, 2, 3, 3, 0, 2, 3, 3, 0, 0, 3, 0, 2, 1, 0, 0, 1, 3, 0, 0, 0, 1, 1, 0, 2, 1, 0, 1, 3
                ),
                listOf(
                    3, 2, 0, 1, 0, 0, 2, 3, 0, 0, 2, 3, 3, 3, 0, 1, 1, 2, 2, 3, 0, 3, 1, 2, 2, 1, 0, 1, 3, 1, 0, 2, 1, 2, 1, 0, 2, 3, 2, 2
                ),
                listOf(
                    2, 2, 3, 3, 2, 3, 0, 2, 3, 0, 0, 2, 2, 3, 2, 2, 1, 2, 0, 3, 1, 0, 1, 2, 1, 1, 0, 2, 2, 1, 1, 2, 3, 1, 1, 3, 0, 0, 0, 1
                ),
                listOf(
                    1, 2, 0, 1, 3, 0, 0, 1, 1, 0, 1, 3, 0, 3, 1, 0, 3, 2, 2, 1, 2, 0, 3, 0, 2, 0, 3, 3, 2, 2, 2, 2, 0, 1, 0, 3, 3, 1, 3, 2
                ),
                listOf(
                    2, 2, 2, 0, 0, 1, 0, 2, 0, 0, 2, 0, 1, 2, 3, 3, 2, 1, 2, 3, 0, 1, 1, 2, 3, 2, 1, 1, 0, 1, 3, 1, 0, 3, 1, 1, 0, 3, 0, 0
                ),
                listOf(
                    2, 0, 3, 2, 1, 3, 3, 0, 2, 0, 0, 0, 2, 2, 2, 0, 2, 2, 2, 2, 1, 2, 2, 1, 0, 0, 0, 3, 3, 0, 2, 1, 1, 0, 1, 1, 1, 3, 3, 3
                ),
                listOf(
                    1, 3, 0, 0, 3, 3, 0, 3, 0, 3, 3, 2, 1, 3, 1, 2, 3, 2, 1, 2, 1, 3, 1, 2, 1, 0, 2, 3, 3, 2, 2, 0, 0, 3, 3, 1, 0, 3, 3, 2
                ),
                listOf(
                    0, 1, 2, 2, 3, 3, 3, 2, 2, 2, 3, 1, 1, 1, 1, 0, 2, 2, 2, 3, 0, 3, 0, 1, 0, 3, 0, 1, 1, 2, 0, 3, 2, 1, 2, 0, 2, 1, 2, 2
                ),
                listOf(
                    0, 2, 0, 2, 0, 2, 0, 3, 1, 0, 1, 0, 0, 0, 2, 0, 2, 2, 3, 3, 0, 2, 3, 2, 1, 0, 0, 3, 2, 0, 0, 2, 3, 3, 2, 2, 0, 3, 2, 2
                ),
                listOf(
                    0, 2, 1, 2, 2, 1, 2, 0, 0, 0, 1, 3, 2, 1, 2, 2, 1, 3, 3, 0, 1, 0, 2, 2, 3, 0, 2, 1, 1, 1, 0, 2, 1, 0, 0, 2, 0, 2, 3, 0
                ),
                listOf(
                    3, 3, 1, 2, 0, 2, 0, 0, 0, 1, 3, 3, 0, 2, 3, 3, 2, 0, 3, 1, 0, 3, 3, 3, 2, 1, 2, 2, 0, 3, 1, 0, 3, 0, 0, 1, 2, 3, 2, 3
                ),
                listOf(
                    2, 2, 3, 3, 3, 3, 2, 0, 1, 0, 0, 2, 3, 1, 3, 0, 3, 3, 1, 0, 1, 3, 1, 3, 1, 3, 1, 3, 0, 1, 0, 2, 2, 1, 1, 0, 2, 2, 2, 2
                ),
                listOf(
                    2, 1, 0, 0, 0, 2, 2, 0, 3, 3, 0, 2, 1, 2, 0, 1, 3, 3, 2, 2, 2, 2, 2, 1, 2, 1, 1, 3, 1, 2, 1, 3, 1, 3, 3, 2, 2, 0, 1, 1
                ),
                listOf(
                    3, 0, 0, 2, 2, 0, 1, 3, 2, 3, 3, 2, 2, 2, 2, 3, 2, 0, 2, 1, 3, 1, 2, 1, 2, 2, 1, 2, 2, 2, 0, 3, 1, 2, 1, 3, 3, 1, 0, 0
                ),
                listOf(
                    1, 2, 2, 0, 0, 0, 3, 1, 0, 2, 1, 2, 1, 3, 3, 2, 0, 2, 1, 1, 3, 3, 1, 3, 0, 0, 0, 0, 1, 1, 1, 2, 0, 3, 0, 2, 3, 3, 1, 2
                ),
                listOf(
                    2, 0, 2, 2, 1, 3, 0, 0, 1, 3, 3, 3, 1, 0, 0, 1, 3, 3, 2, 3, 0, 3, 1, 1, 2, 2, 0, 2, 2, 2, 0, 0, 1, 2, 1, 3, 2, 1, 0, 2
                ),
                listOf(
                    0, 3, 0, 0, 2, 0, 2, 3, 2, 3, 2, 0, 3, 1, 2, 0, 0, 1, 3, 1, 0, 0, 1, 3, 0, 0, 2, 2, 3, 3, 1, 2, 1, 1, 3, 2, 2, 2, 2, 1
                ),
                listOf(
                    0, 3, 1, 1, 2, 0, 2, 1, 2, 3, 3, 2, 3, 3, 2, 0, 1, 1, 0, 2, 3, 2, 2, 1, 0, 3, 1, 3, 1, 1, 2, 3, 3, 2, 3, 0, 2, 0, 1, 1
                ),
                listOf(
                    1, 2, 3, 3, 2, 0, 1, 2, 1, 0, 0, 0, 0, 3, 3, 3, 3, 2, 1, 2, 3, 1, 3, 3, 3, 2, 0, 0, 1, 3, 3, 1, 2, 2, 2, 3, 3, 3, 3, 0
                ),
                listOf(
                    1, 1, 1, 1, 2, 1, 0, 1, 3, 2, 2, 2, 1, 2, 2, 0, 0, 2, 1, 1, 3, 3, 2, 1, 0, 1, 1, 2, 2, 0, 1, 2, 2, 2, 2, 3, 3, 0, 0, 1
                ),
                listOf(
                    1, 3, 2, 0, 0, 2, 1, 0, 0, 1, 3, 0, 0, 0, 3, 0, 0, 2, 1, 0, 0, 0, 2, 2, 2, 2, 1, 0, 1, 3, 3, 3, 1, 0, 0, 3, 0, 1, 1, 0
                )
            ), listOf(
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 25),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 25),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk88(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_009", "Legendary", 40, 60, listOf(
                listOf(
                    1, 2, 2, 2, 3, 1, 1, 3, 0, 2, 2, 0, 3, 1, 1, 3, 2, 3, 1, 0, 2, 3, 2, 3, 2, 1, 1, 1, 2, 0, 0, 3, 2, 3, 0, 0, 2, 3, 3, 3
                ),
                listOf(
                    1, 1, 3, 1, 3, 0, 1, 0, 1, 3, 0, 1, 3, 3, 2, 3, 2, 1, 0, 1, 3, 0, 2, 3, 2, 0, 3, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 1, 0
                ),
                listOf(
                    3, 2, 0, 0, 3, 3, 1, 1, 0, 1, 0, 1, 0, 2, 3, 1, 1, 3, 0, 1, 2, 0, 3, 1, 1, 2, 2, 1, 1, 2, 1, 0, 1, 1, 1, 3, 3, 0, 0, 3
                ),
                listOf(
                    1, 1, 3, 1, 1, 1, 3, 0, 3, 0, 0, 1, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3, 3, 2, 3, 3, 1, 3, 3, 2, 2, 0, 2, 0, 1, 2, 3, 1, 1, 0
                ),
                listOf(
                    0, 3, 3, 3, 1, 3, 2, 2, 3, 2, 0, 3, 2, 1, 0, 3, 1, 1, 1, 1, 2, 1, 3, 1, 0, 1, 3, 3, 1, 1, 0, 0, 3, 2, 2, 0, 1, 3, 3, 2
                ),
                listOf(
                    0, 1, 1, 3, 3, 2, 2, 3, 0, 2, 1, 2, 0, 2, 2, 3, 0, 3, 0, 2, 1, 2, 2, 3, 1, 3, 1, 3, 1, 2, 2, 0, 2, 1, 2, 1, 1, 3, 0, 1
                ),
                listOf(
                    1, 0, 3, 0, 3, 2, 0, 1, 1, 0, 0, 1, 0, 0, 3, 2, 1, 1, 1, 2, 0, 1, 0, 2, 0, 1, 1, 3, 3, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 2
                ),
                listOf(
                    3, 2, 3, 2, 0, 2, 0, 1, 2, 3, 0, 0, 0, 2, 2, 3, 3, 0, 2, 1, 2, 1, 1, 2, 1, 3, 3, 3, 2, 1, 2, 1, 0, 2, 2, 3, 0, 2, 1, 0
                ),
                listOf(
                    3, 2, 2, 3, 1, 2, 0, 1, 3, 2, 2, 1, 1, 3, 0, 1, 0, 2, 2, 0, 0, 1, 1, 2, 2, 0, 2, 1, 0, 3, 1, 0, 3, 3, 1, 1, 2, 3, 1, 2
                ),
                listOf(
                    2, 1, 1, 2, 2, 3, 1, 3, 1, 1, 1, 2, 2, 3, 0, 2, 2, 2, 2, 2, 0, 1, 2, 2, 3, 0, 0, 1, 3, 2, 2, 1, 1, 3, 1, 0, 2, 1, 1, 1
                ),
                listOf(
                    3, 2, 3, 1, 0, 3, 0, 3, 2, 1, 3, 3, 3, 3, 0, 2, 3, 3, 3, 1, 0, 3, 3, 1, 1, 0, 2, 2, 0, 2, 1, 1, 0, 1, 2, 3, 0, 3, 1, 1
                ),
                listOf(
                    0, 1, 1, 3, 0, 1, 1, 2, 3, 0, 0, 2, 2, 1, 2, 1, 0, 2, 2, 0, 3, 1, 0, 2, 0, 1, 2, 3, 3, 2, 3, 2, 2, 0, 2, 0, 2, 3, 0, 2
                ),
                listOf(
                    3, 2, 2, 0, 2, 2, 0, 0, 2, 1, 3, 2, 3, 1, 3, 0, 2, 1, 1, 3, 3, 3, 1, 0, 0, 2, 3, 0, 0, 2, 1, 3, 1, 3, 1, 3, 0, 1, 0, 1
                ),
                listOf(
                    0, 3, 0, 2, 1, 2, 3, 1, 1, 2, 0, 3, 1, 2, 3, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 0, 0, 2, 1, 3, 1, 0, 2, 1, 1, 3, 3, 2, 1, 0
                ),
                listOf(
                    1, 0, 2, 0, 1, 0, 3, 3, 1, 0, 2, 0, 0, 0, 1, 3, 2, 1, 0, 1, 1, 0, 1, 1, 0, 3, 1, 0, 3, 1, 1, 2, 2, 0, 0, 1, 1, 2, 3, 3
                ),
                listOf(
                    2, 1, 2, 3, 2, 3, 1, 1, 3, 3, 2, 2, 3, 2, 1, 3, 3, 1, 1, 3, 1, 0, 1, 0, 0, 1, 1, 3, 1, 0, 1, 0, 1, 0, 1, 1, 2, 0, 2, 0
                ),
                listOf(
                    1, 1, 0, 0, 3, 3, 1, 1, 3, 1, 1, 0, 0, 3, 1, 1, 1, 3, 1, 1, 3, 2, 1, 1, 0, 1, 0, 1, 1, 3, 1, 0, 1, 1, 1, 2, 1, 1, 1, 1
                ),
                listOf(
                    2, 0, 3, 1, 2, 1, 0, 1, 2, 0, 3, 1, 1, 1, 1, 2, 2, 3, 1, 3, 1, 3, 0, 2, 1, 3, 0, 1, 0, 2, 1, 1, 3, 1, 2, 1, 0, 3, 2, 2
                ),
                listOf(
                    0, 1, 3, 0, 3, 3, 2, 3, 1, 1, 3, 3, 1, 1, 3, 1, 2, 2, 0, 1, 1, 0, 0, 0, 2, 1, 1, 3, 3, 0, 1, 0, 2, 3, 2, 0, 1, 3, 1, 2
                ),
                listOf(
                    1, 1, 1, 1, 2, 3, 2, 0, 1, 0, 1, 2, 1, 1, 3, 1, 1, 2, 1, 2, 2, 0, 2, 1, 3, 2, 0, 1, 3, 2, 2, 0, 1, 2, 1, 2, 3, 2, 2, 3
                ),
                listOf(
                    0, 2, 1, 2, 0, 0, 1, 2, 1, 0, 2, 1, 0, 2, 2, 0, 3, 2, 3, 1, 1, 3, 3, 3, 1, 2, 2, 3, 3, 1, 1, 0, 3, 1, 1, 2, 1, 1, 0, 0
                ),
                listOf(
                    1, 0, 0, 1, 1, 1, 0, 3, 3, 0, 3, 3, 1, 1, 2, 3, 0, 1, 2, 2, 0, 3, 0, 2, 1, 0, 1, 1, 2, 2, 2, 1, 0, 3, 3, 0, 0, 0, 0, 1
                ),
                listOf(
                    3, 1, 1, 3, 3, 1, 1, 1, 2, 3, 3, 1, 0, 2, 0, 2, 1, 0, 1, 3, 1, 3, 2, 2, 0, 2, 1, 3, 1, 1, 2, 1, 2, 0, 3, 0, 0, 1, 2, 0
                ),
                listOf(
                    3, 1, 0, 0, 3, 1, 2, 2, 1, 3, 2, 3, 2, 0, 3, 0, 3, 1, 3, 2, 0, 2, 1, 1, 3, 0, 1, 0, 3, 1, 1, 0, 0, 3, 3, 3, 3, 3, 2, 2
                ),
                listOf(
                    0, 2, 2, 1, 0, 0, 2, 3, 1, 3, 2, 3, 1, 1, 1, 1, 2, 1, 0, 1, 2, 1, 1, 0, 3, 0, 2, 2, 2, 3, 0, 3, 0, 1, 0, 2, 3, 2, 1, 2
                ),
                listOf(
                    0, 1, 1, 0, 0, 0, 3, 2, 1, 2, 0, 0, 0, 1, 1, 2, 1, 1, 2, 0, 1, 1, 3, 0, 2, 0, 1, 1, 3, 0, 1, 3, 1, 2, 2, 2, 1, 3, 0, 3
                ),
                listOf(
                    0, 3, 1, 1, 3, 1, 3, 1, 2, 3, 2, 1, 2, 1, 2, 3, 1, 3, 3, 3, 2, 0, 0, 3, 0, 2, 2, 1, 1, 2, 0, 0, 0, 1, 2, 1, 0, 2, 3, 0
                ),
                listOf(
                    1, 3, 3, 1, 3, 1, 2, 2, 2, 3, 1, 3, 1, 2, 3, 1, 3, 0, 3, 3, 3, 3, 3, 0, 2, 2, 1, 1, 3, 2, 0, 1, 3, 3, 2, 3, 0, 2, 1, 3
                ),
                listOf(
                    3, 0, 0, 0, 1, 2, 1, 2, 3, 0, 1, 1, 2, 0, 3, 3, 1, 2, 1, 1, 3, 2, 0, 0, 1, 1, 0, 2, 0, 0, 2, 1, 0, 2, 1, 1, 1, 0, 2, 1
                ),
                listOf(
                    0, 0, 2, 1, 1, 1, 3, 1, 1, 2, 0, 1, 1, 3, 2, 2, 3, 3, 3, 1, 3, 2, 3, 0, 3, 2, 1, 3, 1, 1, 1, 2, 2, 1, 1, 1, 3, 1, 1, 3
                ),
                listOf(
                    0, 0, 1, 3, 1, 3, 2, 2, 3, 1, 1, 0, 0, 2, 1, 3, 1, 2, 0, 1, 3, 3, 0, 3, 1, 3, 3, 1, 3, 2, 3, 3, 2, 2, 1, 0, 1, 3, 3, 2
                ),
                listOf(
                    0, 0, 1, 1, 3, 1, 0, 2, 2, 3, 0, 1, 0, 1, 0, 2, 2, 3, 1, 1, 3, 1, 2, 1, 1, 3, 2, 3, 3, 0, 1, 0, 2, 1, 1, 0, 1, 3, 0, 1
                ),
                listOf(
                    0, 3, 3, 0, 0, 2, 3, 1, 3, 3, 0, 3, 1, 2, 1, 2, 2, 2, 3, 1, 1, 3, 3, 2, 3, 2, 0, 3, 2, 1, 2, 2, 0, 0, 2, 2, 2, 0, 3, 0
                ),
                listOf(
                    1, 2, 2, 1, 3, 1, 0, 3, 3, 0, 2, 3, 1, 1, 0, 0, 0, 2, 0, 0, 1, 2, 1, 3, 0, 3, 0, 1, 3, 0, 0, 0, 0, 1, 2, 1, 2, 2, 3, 3
                ),
                listOf(
                    0, 1, 1, 0, 2, 1, 2, 3, 2, 2, 1, 1, 3, 2, 1, 2, 0, 3, 2, 1, 2, 3, 3, 0, 1, 3, 1, 3, 3, 0, 0, 2, 0, 3, 0, 2, 3, 0, 3, 0
                ),
                listOf(
                    3, 1, 0, 0, 1, 3, 1, 0, 2, 1, 0, 2, 2, 3, 0, 1, 3, 3, 2, 0, 2, 3, 0, 1, 1, 1, 1, 1, 1, 1, 0, 2, 0, 0, 1, 0, 1, 3, 1, 3
                ),
                listOf(
                    1, 3, 0, 2, 3, 1, 3, 3, 1, 1, 1, 2, 3, 1, 2, 2, 0, 2, 0, 2, 0, 0, 1, 2, 1, 2, 0, 1, 1, 3, 0, 1, 2, 2, 0, 2, 2, 1, 1, 3
                ),
                listOf(
                    0, 1, 3, 3, 2, 1, 1, 1, 3, 3, 3, 2, 1, 1, 3, 1, 2, 3, 2, 3, 3, 0, 3, 2, 3, 1, 3, 3, 2, 2, 2, 1, 2, 1, 0, 0, 0, 1, 2, 1
                ),
                listOf(
                    2, 0, 1, 3, 0, 3, 2, 2, 1, 3, 1, 1, 3, 3, 1, 3, 1, 2, 1, 1, 3, 3, 3, 0, 1, 3, 1, 3, 0, 1, 3, 0, 0, 0, 1, 2, 3, 2, 3, 0
                ),
                listOf(
                    1, 0, 3, 3, 2, 0, 2, 2, 3, 2, 2, 3, 2, 0, 0, 3, 3, 2, 0, 3, 1, 0, 0, 0, 0, 3, 1, 1, 2, 1, 0, 1, 3, 1, 1, 3, 1, 1, 1, 3
                ),
                listOf(
                    2, 1, 1, 3, 0, 0, 1, 2, 1, 1, 1, 1, 0, 1, 1, 1, 3, 1, 1, 1, 0, 2, 3, 2, 3, 1, 3, 3, 1, 1, 0, 3, 0, 3, 3, 1, 3, 1, 0, 3
                ),
                listOf(
                    2, 3, 1, 2, 0, 0, 0, 3, 2, 1, 0, 3, 2, 3, 1, 0, 0, 1, 3, 1, 2, 0, 1, 2, 0, 3, 3, 1, 1, 1, 1, 3, 3, 2, 3, 3, 3, 2, 1, 1
                ),
                listOf(
                    1, 0, 2, 3, 1, 3, 0, 3, 2, 2, 2, 3, 1, 3, 3, 3, 3, 0, 1, 3, 1, 2, 3, 0, 1, 3, 2, 1, 0, 2, 2, 0, 0, 3, 1, 1, 0, 1, 2, 0
                ),
                listOf(
                    2, 0, 0, 3, 1, 2, 1, 1, 3, 2, 0, 1, 2, 3, 1, 0, 1, 0, 0, 0, 3, 2, 0, 3, 2, 0, 0, 1, 1, 2, 3, 2, 2, 1, 1, 3, 3, 2, 2, 3
                ),
                listOf(
                    1, 3, 0, 3, 0, 1, 2, 1, 1, 3, 2, 1, 2, 2, 2, 0, 3, 2, 1, 2, 2, 0, 1, 3, 3, 2, 1, 0, 0, 2, 3, 3, 1, 2, 3, 0, 0, 3, 2, 1
                ),
                listOf(
                    1, 2, 0, 0, 0, 1, 0, 1, 2, 3, 1, 1, 1, 3, 3, 2, 0, 1, 0, 2, 1, 2, 1, 2, 1, 0, 3, 1, 2, 1, 3, 3, 2, 0, 1, 2, 1, 3, 2, 1
                ),
                listOf(
                    3, 0, 3, 0, 0, 1, 1, 2, 0, 3, 1, 1, 1, 2, 2, 2, 1, 1, 1, 3, 0, 1, 2, 1, 3, 1, 2, 3, 0, 3, 0, 0, 0, 2, 2, 1, 1, 1, 2, 3
                ),
                listOf(
                    0, 2, 2, 2, 3, 3, 3, 3, 0, 2, 0, 3, 3, 2, 0, 1, 1, 0, 3, 2, 3, 3, 3, 2, 2, 2, 2, 3, 1, 3, 0, 3, 2, 0, 2, 3, 2, 1, 1, 2
                ),
                listOf(
                    1, 1, 3, 0, 3, 0, 2, 0, 2, 0, 3, 2, 0, 3, 2, 3, 2, 0, 2, 0, 2, 0, 1, 0, 3, 1, 3, 0, 3, 3, 1, 1, 3, 3, 1, 3, 3, 0, 0, 0
                ),
                listOf(
                    2, 1, 1, 3, 1, 2, 2, 1, 0, 0, 3, 3, 1, 2, 0, 0, 0, 2, 3, 2, 3, 1, 3, 3, 1, 3, 0, 2, 3, 1, 0, 0, 0, 2, 0, 0, 3, 2, 0, 3
                ),
                listOf(
                    0, 1, 1, 2, 3, 3, 2, 3, 3, 0, 2, 1, 3, 2, 0, 0, 0, 3, 3, 2, 0, 1, 1, 3, 3, 1, 0, 2, 2, 3, 2, 2, 0, 2, 2, 3, 0, 0, 3, 2
                ),
                listOf(
                    2, 1, 0, 3, 1, 2, 2, 2, 1, 3, 3, 3, 1, 2, 2, 0, 1, 1, 1, 3, 1, 0, 3, 1, 1, 2, 2, 3, 2, 1, 0, 1, 2, 1, 3, 2, 0, 3, 1, 1
                ),
                listOf(
                    2, 3, 2, 0, 1, 1, 3, 1, 2, 1, 1, 0, 0, 3, 3, 2, 3, 0, 0, 2, 2, 0, 2, 1, 3, 1, 1, 3, 3, 2, 2, 2, 3, 1, 1, 0, 1, 1, 0, 2
                ),
                listOf(
                    1, 3, 1, 2, 1, 1, 3, 2, 1, 2, 2, 0, 3, 3, 2, 2, 2, 2, 3, 3, 3, 2, 1, 3, 3, 3, 0, 2, 3, 1, 3, 0, 3, 1, 2, 1, 1, 1, 0, 0
                ),
                listOf(
                    3, 2, 3, 3, 2, 1, 1, 2, 2, 0, 0, 1, 3, 2, 0, 1, 1, 2, 3, 0, 0, 0, 3, 0, 0, 3, 2, 2, 3, 1, 1, 0, 1, 1, 1, 1, 3, 1, 3, 0
                ),
                listOf(
                    2, 1, 3, 1, 1, 1, 0, 0, 2, 1, 0, 1, 0, 1, 1, 2, 2, 0, 3, 0, 3, 3, 0, 1, 3, 2, 3, 0, 2, 2, 2, 0, 3, 0, 3, 0, 1, 1, 0, 0
                ),
                listOf(
                    1, 2, 0, 3, 0, 0, 1, 1, 1, 3, 2, 1, 3, 0, 2, 1, 2, 2, 0, 3, 0, 1, 0, 2, 3, 3, 0, 1, 0, 2, 3, 3, 1, 1, 2, 3, 0, 2, 2, 2
                ),
                listOf(
                    1, 1, 1, 1, 3, 1, 3, 0, 0, 2, 0, 0, 2, 3, 3, 0, 1, 1, 2, 2, 1, 1, 3, 1, 0, 0, 3, 1, 2, 0, 1, 3, 2, 1, 1, 0, 1, 3, 1, 1
                ),
                listOf(
                    1, 3, 3, 0, 3, 2, 2, 3, 3, 3, 0, 3, 2, 0, 1, 1, 1, 1, 1, 2, 0, 1, 3, 3, 1, 1, 1, 3, 0, 3, 3, 3, 1, 2, 3, 2, 0, 0, 2, 2
                ),
                listOf(
                    2, 1, 0, 2, 2, 2, 3, 1, 2, 0, 3, 2, 2, 0, 1, 1, 2, 1, 2, 1, 1, 3, 1, 2, 1, 1, 1, 1, 2, 0, 2, 1, 3, 2, 1, 3, 0, 1, 2, 0
                )
            ), listOf(
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 25),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 25),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk89(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_010", "Legendary", 40, 60, listOf(
                listOf(
                    1, 3, 1, 0, 2, 1, 3, 2, 0, 1, 0, 0, 1, 3, 3, 1, 0, 2, 1, 2, 0, 2, 3, 2, 2, 0, 0, 3, 3, 2, 0, 1, 1, 1, 1, 1, 3, 1, 1, 0
                ),
                listOf(
                    3, 1, 3, 3, 2, 3, 2, 0, 3, 2, 0, 0, 1, 0, 2, 2, 3, 0, 3, 0, 2, 0, 1, 1, 2, 1, 2, 1, 0, 1, 1, 1, 2, 1, 1, 3, 2, 3, 2, 0
                ),
                listOf(
                    1, 2, 0, 0, 2, 1, 0, 0, 1, 2, 1, 1, 3, 1, 3, 2, 0, 2, 1, 0, 2, 3, 0, 0, 3, 0, 1, 2, 1, 0, 3, 0, 0, 3, 2, 2, 1, 3, 1, 0
                ),
                listOf(
                    2, 0, 0, 3, 1, 1, 1, 1, 0, 2, 3, 1, 1, 3, 2, 1, 3, 1, 3, 1, 0, 2, 0, 2, 2, 3, 2, 0, 0, 2, 1, 2, 1, 1, 2, 2, 3, 1, 3, 1
                ),
                listOf(
                    2, 2, 1, 0, 0, 1, 2, 0, 3, 1, 3, 3, 3, 0, 1, 3, 3, 0, 2, 0, 1, 1, 0, 3, 0, 0, 2, 3, 0, 2, 0, 1, 3, 2, 0, 0, 3, 2, 0, 3
                ),
                listOf(
                    3, 3, 1, 2, 2, 0, 0, 1, 2, 2, 1, 0, 3, 3, 2, 0, 0, 2, 3, 1, 1, 3, 0, 2, 1, 1, 1, 3, 2, 3, 1, 0, 0, 2, 3, 0, 2, 0, 0, 2
                ),
                listOf(
                    3, 1, 3, 0, 0, 3, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 3, 3, 3, 1, 1, 3, 1, 2, 0, 2, 1, 0, 2, 2, 1, 0, 3, 2, 3, 2, 2, 0, 3, 1
                ),
                listOf(
                    0, 2, 1, 3, 3, 2, 3, 2, 0, 0, 0, 0, 0, 2, 0, 3, 1, 3, 1, 2, 1, 1, 0, 2, 3, 2, 3, 0, 0, 2, 0, 0, 3, 0, 0, 0, 0, 2, 3, 1
                ),
                listOf(
                    0, 1, 1, 1, 0, 2, 2, 2, 2, 0, 2, 1, 3, 3, 0, 0, 3, 0, 1, 0, 2, 2, 0, 0, 1, 1, 3, 2, 2, 0, 0, 1, 0, 3, 0, 2, 0, 1, 1, 3
                ),
                listOf(
                    0, 1, 0, 1, 2, 1, 1, 1, 1, 3, 2, 2, 3, 1, 0, 2, 2, 0, 0, 1, 3, 3, 0, 2, 1, 1, 2, 2, 1, 3, 3, 0, 3, 0, 2, 2, 2, 2, 2, 0
                ),
                listOf(
                    1, 2, 3, 0, 3, 2, 2, 0, 1, 2, 0, 1, 0, 2, 1, 0, 0, 2, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 3, 3, 3, 3, 3, 2, 1, 3, 0, 0, 2, 0
                ),
                listOf(
                    3, 0, 1, 2, 2, 3, 0, 1, 2, 2, 3, 1, 0, 2, 2, 0, 3, 2, 1, 0, 3, 2, 3, 2, 1, 3, 1, 2, 0, 0, 2, 2, 2, 0, 0, 3, 0, 2, 1, 3
                ),
                listOf(
                    1, 0, 0, 2, 3, 2, 1, 2, 2, 3, 2, 1, 1, 2, 2, 2, 1, 2, 2, 3, 1, 1, 0, 1, 0, 0, 1, 1, 3, 0, 0, 0, 2, 3, 3, 3, 0, 3, 2, 2
                ),
                listOf(
                    0, 0, 2, 1, 1, 3, 3, 2, 1, 1, 1, 3, 0, 0, 1, 1, 2, 1, 1, 2, 0, 1, 1, 0, 1, 1, 3, 0, 1, 0, 3, 1, 3, 1, 1, 2, 2, 0, 1, 0
                ),
                listOf(
                    2, 3, 3, 3, 3, 0, 1, 1, 0, 3, 1, 2, 0, 0, 0, 1, 0, 2, 0, 0, 0, 2, 2, 3, 2, 3, 3, 0, 3, 1, 2, 3, 0, 1, 3, 0, 0, 0, 0, 3
                ),
                listOf(
                    3, 0, 1, 0, 0, 0, 3, 1, 2, 0, 3, 1, 1, 1, 1, 3, 0, 1, 2, 3, 0, 2, 2, 0, 0, 3, 2, 3, 1, 1, 0, 3, 0, 0, 1, 0, 3, 0, 2, 1
                ),
                listOf(
                    1, 0, 3, 0, 3, 1, 1, 0, 2, 1, 2, 2, 3, 2, 3, 2, 2, 1, 3, 1, 1, 1, 0, 3, 3, 3, 2, 2, 1, 1, 2, 1, 2, 1, 2, 1, 3, 0, 1, 1
                ),
                listOf(
                    1, 3, 0, 2, 3, 2, 2, 0, 2, 3, 2, 0, 2, 2, 0, 0, 2, 1, 1, 2, 1, 1, 3, 2, 0, 1, 0, 0, 3, 1, 0, 2, 2, 3, 3, 3, 3, 3, 0, 3
                ),
                listOf(
                    0, 2, 0, 2, 0, 1, 0, 0, 0, 3, 0, 3, 3, 2, 2, 1, 3, 2, 1, 0, 2, 2, 2, 3, 1, 3, 3, 0, 0, 3, 3, 2, 2, 3, 3, 1, 0, 2, 2, 1
                ),
                listOf(
                    2, 2, 2, 2, 0, 0, 2, 1, 3, 3, 1, 0, 2, 3, 3, 1, 0, 3, 2, 1, 2, 3, 2, 3, 1, 2, 1, 0, 0, 2, 2, 0, 0, 0, 2, 2, 3, 2, 1, 1
                ),
                listOf(
                    2, 0, 1, 2, 2, 2, 3, 3, 2, 1, 0, 1, 1, 0, 2, 0, 0, 3, 1, 0, 2, 2, 1, 2, 3, 3, 0, 3, 2, 2, 0, 2, 1, 2, 3, 2, 1, 0, 0, 0
                ),
                listOf(
                    2, 1, 2, 3, 2, 1, 0, 3, 0, 1, 3, 1, 0, 1, 2, 2, 3, 1, 2, 2, 3, 0, 0, 1, 0, 2, 3, 0, 2, 1, 2, 1, 2, 0, 3, 3, 1, 2, 2, 3
                ),
                listOf(
                    0, 2, 2, 3, 0, 0, 3, 2, 1, 3, 0, 2, 0, 3, 0, 1, 0, 2, 2, 2, 2, 0, 3, 0, 3, 3, 1, 0, 3, 0, 1, 3, 0, 3, 1, 0, 1, 3, 2, 1
                ),
                listOf(
                    2, 0, 3, 0, 1, 0, 0, 1, 3, 0, 3, 0, 0, 3, 3, 0, 2, 2, 0, 1, 3, 2, 3, 3, 1, 0, 1, 0, 3, 0, 3, 2, 1, 1, 2, 0, 0, 2, 0, 0
                ),
                listOf(
                    0, 2, 2, 3, 2, 0, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 2, 3, 1, 2, 2, 1, 2, 2, 0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 2, 2, 3, 2, 1, 0
                ),
                listOf(
                    2, 2, 2, 3, 2, 3, 1, 2, 0, 2, 3, 2, 0, 1, 0, 1, 1, 2, 2, 0, 2, 1, 0, 2, 3, 1, 2, 3, 1, 2, 0, 0, 2, 2, 2, 0, 3, 0, 3, 0
                ),
                listOf(
                    3, 2, 0, 1, 1, 1, 3, 1, 1, 2, 2, 1, 1, 0, 3, 0, 2, 1, 2, 2, 1, 1, 1, 1, 3, 1, 2, 3, 3, 3, 0, 2, 2, 1, 2, 3, 2, 0, 1, 1
                ),
                listOf(
                    1, 0, 2, 3, 0, 0, 0, 3, 2, 2, 2, 2, 0, 3, 2, 0, 2, 0, 2, 1, 3, 3, 0, 0, 3, 3, 0, 2, 2, 0, 3, 0, 3, 2, 2, 2, 2, 1, 0, 0
                ),
                listOf(
                    2, 2, 2, 3, 2, 0, 2, 1, 2, 0, 1, 2, 1, 0, 3, 1, 1, 1, 3, 3, 2, 1, 2, 2, 0, 0, 2, 0, 0, 2, 3, 2, 3, 0, 3, 3, 3, 3, 1, 0
                ),
                listOf(
                    0, 3, 2, 1, 3, 2, 3, 1, 1, 3, 1, 1, 0, 1, 0, 0, 1, 2, 2, 0, 3, 0, 2, 1, 3, 3, 0, 1, 2, 1, 0, 1, 2, 2, 0, 0, 1, 0, 2, 0
                ),
                listOf(
                    2, 0, 0, 0, 0, 3, 2, 0, 2, 0, 0, 0, 2, 3, 0, 3, 1, 2, 1, 1, 3, 2, 2, 3, 0, 2, 2, 3, 1, 3, 3, 1, 2, 3, 3, 0, 2, 1, 1, 1
                ),
                listOf(
                    0, 1, 1, 3, 3, 0, 1, 0, 0, 2, 2, 3, 0, 1, 2, 0, 3, 1, 2, 1, 0, 3, 2, 2, 2, 3, 2, 2, 3, 3, 2, 3, 1, 3, 1, 1, 3, 0, 3, 0
                ),
                listOf(
                    0, 3, 2, 2, 1, 0, 1, 2, 2, 3, 0, 3, 2, 0, 0, 3, 2, 1, 0, 3, 3, 2, 3, 0, 0, 1, 3, 0, 0, 2, 2, 0, 1, 2, 2, 3, 1, 1, 1, 2
                ),
                listOf(
                    2, 0, 3, 1, 2, 3, 3, 0, 3, 0, 2, 1, 2, 1, 0, 3, 2, 1, 0, 1, 1, 3, 3, 0, 0, 3, 2, 2, 3, 0, 3, 0, 3, 1, 1, 0, 1, 0, 2, 2
                ),
                listOf(
                    2, 0, 3, 3, 2, 3, 2, 1, 0, 2, 2, 2, 3, 3, 2, 2, 3, 2, 0, 3, 3, 1, 0, 0, 3, 3, 2, 0, 1, 3, 1, 3, 1, 2, 1, 3, 0, 2, 0, 0
                ),
                listOf(
                    0, 2, 2, 0, 1, 1, 2, 0, 0, 2, 1, 2, 1, 3, 3, 2, 3, 1, 0, 1, 0, 2, 3, 0, 1, 3, 3, 1, 2, 0, 2, 1, 3, 1, 3, 0, 0, 1, 1, 3
                ),
                listOf(
                    2, 1, 2, 0, 2, 0, 1, 2, 3, 2, 0, 1, 3, 0, 3, 2, 2, 3, 0, 0, 0, 0, 3, 0, 3, 3, 0, 1, 2, 0, 3, 1, 2, 0, 1, 0, 2, 2, 3, 3
                ),
                listOf(
                    0, 2, 3, 2, 1, 2, 3, 3, 0, 1, 0, 0, 1, 2, 3, 2, 1, 1, 1, 1, 1, 2, 3, 1, 0, 1, 1, 1, 0, 1, 3, 1, 0, 3, 2, 3, 3, 3, 1, 1
                ),
                listOf(
                    1, 0, 3, 1, 0, 3, 1, 3, 2, 3, 0, 1, 3, 0, 0, 2, 0, 1, 2, 2, 0, 3, 1, 1, 0, 2, 1, 3, 0, 1, 0, 1, 2, 2, 2, 1, 3, 0, 3, 1
                ),
                listOf(
                    2, 0, 2, 0, 1, 1, 2, 1, 1, 3, 1, 3, 3, 2, 2, 1, 3, 1, 0, 2, 3, 2, 3, 0, 3, 1, 1, 0, 2, 2, 0, 1, 1, 3, 2, 0, 3, 2, 0, 2
                ),
                listOf(
                    3, 1, 0, 2, 2, 3, 0, 2, 0, 1, 0, 3, 2, 3, 1, 1, 3, 3, 3, 0, 1, 3, 3, 0, 1, 1, 3, 1, 2, 3, 2, 3, 3, 3, 1, 2, 2, 2, 2, 3
                ),
                listOf(
                    0, 0, 0, 2, 2, 1, 0, 0, 1, 0, 1, 1, 0, 3, 1, 0, 2, 2, 3, 0, 1, 1, 0, 0, 0, 3, 0, 0, 2, 3, 3, 2, 0, 2, 0, 3, 3, 3, 2, 2
                ),
                listOf(
                    2, 0, 0, 2, 0, 0, 2, 2, 1, 1, 0, 1, 3, 0, 1, 3, 0, 2, 2, 3, 2, 3, 2, 1, 2, 0, 0, 3, 1, 3, 1, 1, 0, 2, 0, 0, 1, 2, 3, 3
                ),
                listOf(
                    0, 3, 2, 0, 2, 1, 2, 0, 1, 2, 3, 2, 1, 1, 1, 3, 2, 1, 2, 3, 1, 3, 3, 0, 3, 0, 1, 2, 2, 0, 3, 2, 0, 0, 0, 1, 0, 0, 0, 1
                ),
                listOf(
                    2, 1, 3, 2, 2, 1, 1, 3, 2, 0, 1, 2, 0, 0, 2, 0, 0, 1, 0, 0, 2, 2, 2, 3, 3, 2, 0, 2, 0, 2, 3, 0, 3, 3, 0, 1, 1, 1, 3, 0
                ),
                listOf(
                    3, 1, 1, 0, 2, 2, 0, 3, 3, 0, 3, 3, 1, 0, 1, 1, 2, 2, 0, 0, 0, 3, 2, 2, 1, 0, 1, 0, 0, 0, 2, 2, 0, 2, 0, 2, 0, 2, 1, 3
                ),
                listOf(
                    0, 2, 0, 2, 1, 0, 3, 3, 1, 2, 0, 0, 3, 1, 1, 3, 1, 3, 0, 3, 0, 1, 2, 0, 1, 3, 2, 3, 3, 0, 2, 0, 2, 2, 3, 2, 2, 3, 1, 1
                ),
                listOf(
                    2, 2, 1, 1, 2, 3, 2, 3, 3, 2, 3, 1, 3, 0, 1, 3, 0, 0, 3, 0, 2, 3, 1, 2, 3, 2, 0, 2, 2, 2, 0, 2, 0, 2, 0, 2, 0, 3, 3, 0
                ),
                listOf(
                    2, 2, 2, 2, 1, 0, 1, 2, 2, 2, 1, 2, 1, 0, 0, 1, 1, 2, 0, 2, 0, 2, 3, 2, 0, 3, 2, 3, 0, 3, 0, 2, 0, 0, 3, 2, 2, 2, 3, 0
                ),
                listOf(
                    3, 1, 2, 0, 2, 1, 0, 1, 0, 0, 2, 3, 0, 2, 1, 2, 3, 0, 2, 3, 0, 0, 0, 0, 3, 0, 2, 1, 1, 3, 3, 3, 1, 1, 1, 0, 1, 1, 2, 1
                ),
                listOf(
                    0, 1, 0, 1, 2, 0, 0, 3, 1, 3, 2, 0, 2, 2, 1, 2, 1, 0, 1, 2, 1, 0, 1, 3, 0, 3, 3, 1, 3, 1, 3, 1, 2, 2, 0, 3, 3, 1, 1, 1
                ),
                listOf(
                    3, 1, 2, 1, 2, 0, 3, 1, 3, 3, 2, 1, 3, 2, 3, 3, 3, 2, 2, 1, 1, 0, 3, 3, 1, 1, 1, 0, 1, 3, 1, 0, 0, 0, 1, 3, 1, 1, 0, 1
                ),
                listOf(
                    3, 2, 1, 3, 0, 1, 3, 1, 0, 1, 0, 1, 2, 3, 3, 0, 3, 0, 3, 2, 0, 3, 3, 0, 3, 1, 1, 2, 2, 1, 1, 2, 3, 2, 2, 3, 0, 2, 2, 3
                ),
                listOf(
                    1, 1, 3, 2, 3, 2, 2, 3, 0, 2, 3, 1, 2, 2, 2, 0, 1, 0, 0, 1, 1, 2, 0, 2, 0, 3, 3, 1, 3, 2, 0, 1, 0, 1, 2, 3, 2, 2, 3, 0
                ),
                listOf(
                    3, 3, 3, 3, 3, 0, 1, 1, 0, 0, 3, 2, 0, 0, 0, 2, 2, 0, 0, 3, 3, 2, 3, 2, 2, 3, 2, 2, 2, 2, 1, 0, 0, 1, 3, 0, 0, 0, 2, 3
                ),
                listOf(
                    3, 2, 3, 0, 3, 0, 2, 0, 0, 2, 2, 2, 3, 0, 0, 2, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 3, 3, 0, 0, 0, 2, 2, 0, 0, 2, 2, 3, 2, 1
                ),
                listOf(
                    2, 2, 3, 1, 3, 0, 0, 2, 0, 0, 1, 2, 1, 0, 2, 3, 1, 3, 0, 1, 1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 3, 0, 3, 1, 2, 0, 3, 1, 3, 2
                ),
                listOf(
                    3, 0, 0, 0, 3, 0, 3, 3, 0, 2, 2, 0, 2, 0, 1, 2, 1, 0, 3, 1, 2, 2, 3, 2, 1, 0, 3, 1, 1, 2, 2, 2, 2, 2, 2, 0, 2, 1, 3, 3
                ),
                listOf(
                    2, 3, 1, 3, 2, 3, 1, 3, 2, 0, 3, 3, 0, 3, 2, 3, 1, 0, 1, 2, 3, 0, 0, 1, 2, 1, 0, 2, 2, 0, 0, 1, 3, 2, 2, 2, 2, 3, 0, 1
                ),
                listOf(
                    2, 2, 1, 3, 3, 1, 0, 1, 1, 3, 0, 3, 3, 3, 3, 2, 2, 2, 0, 1, 3, 0, 3, 2, 1, 3, 2, 3, 0, 0, 3, 2, 2, 0, 2, 2, 0, 0, 0, 1
                )
            ), listOf(
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 25),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk90(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_011", "Legendary", 40, 60, listOf(
                listOf(
                    3, 2, 3, 3, 1, 3, 0, 1, 0, 0, 0, 3, 2, 1, 2, 2, 0, 0, 2, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 2, 2, 2, 2, 0, 3, 3, 1, 0
                ),
                listOf(
                    0, 2, 1, 3, 0, 3, 3, 0, 1, 3, 0, 1, 3, 3, 1, 1, 2, 1, 3, 2, 0, 2, 1, 1, 2, 2, 1, 2, 1, 0, 2, 3, 1, 3, 0, 3, 1, 0, 2, 1
                ),
                listOf(
                    0, 2, 1, 3, 1, 0, 0, 0, 2, 0, 2, 3, 2, 2, 3, 0, 1, 2, 1, 3, 3, 0, 3, 2, 0, 0, 3, 2, 2, 0, 3, 2, 2, 1, 2, 1, 1, 1, 2, 0
                ),
                listOf(
                    0, 3, 1, 0, 1, 2, 2, 1, 0, 2, 0, 1, 0, 1, 2, 3, 0, 3, 0, 3, 0, 0, 0, 1, 2, 0, 2, 3, 2, 1, 3, 1, 3, 1, 2, 0, 2, 1, 3, 2
                ),
                listOf(
                    2, 3, 2, 1, 0, 0, 0, 0, 1, 0, 0, 0, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 1, 0, 0, 0, 3, 3, 1, 2, 2, 0, 3, 0, 1, 0, 1, 3, 2, 0
                ),
                listOf(
                    2, 0, 1, 1, 0, 1, 0, 3, 3, 0, 2, 0, 3, 2, 0, 3, 3, 3, 2, 3, 2, 1, 1, 0, 3, 2, 1, 1, 3, 0, 0, 2, 0, 1, 2, 2, 0, 1, 1, 0
                ),
                listOf(
                    0, 3, 2, 1, 0, 0, 0, 0, 3, 0, 1, 3, 0, 1, 2, 3, 1, 3, 0, 3, 1, 1, 2, 1, 0, 2, 1, 0, 0, 1, 0, 2, 1, 1, 3, 1, 0, 2, 0, 2
                ),
                listOf(
                    1, 2, 0, 0, 1, 3, 0, 2, 0, 0, 2, 1, 1, 0, 0, 3, 0, 1, 3, 1, 1, 1, 1, 1, 1, 2, 1, 3, 3, 0, 1, 2, 1, 0, 3, 0, 3, 3, 0, 1
                ),
                listOf(
                    2, 0, 1, 1, 0, 1, 1, 2, 3, 3, 3, 2, 2, 2, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 2, 2, 1, 3, 3, 3, 1, 2, 2, 1, 0, 2, 3, 0, 0
                ),
                listOf(
                    1, 1, 0, 0, 0, 3, 1, 3, 0, 0, 3, 2, 3, 1, 3, 2, 0, 1, 0, 2, 0, 1, 1, 2, 3, 2, 1, 1, 1, 3, 1, 0, 1, 3, 3, 3, 0, 0, 3, 3
                ),
                listOf(
                    1, 1, 3, 0, 0, 2, 3, 1, 2, 0, 3, 1, 3, 3, 3, 0, 3, 2, 0, 3, 2, 3, 2, 1, 2, 1, 0, 0, 3, 2, 1, 0, 0, 0, 2, 3, 1, 1, 0, 1
                ),
                listOf(
                    3, 1, 0, 1, 3, 1, 1, 2, 0, 0, 3, 3, 3, 3, 3, 1, 3, 2, 1, 2, 2, 0, 2, 0, 3, 1, 1, 0, 1, 2, 1, 1, 1, 3, 2, 2, 1, 1, 1, 3
                ),
                listOf(
                    0, 1, 1, 3, 1, 2, 1, 1, 0, 2, 0, 0, 3, 3, 1, 1, 1, 0, 1, 1, 0, 2, 2, 3, 1, 3, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 3, 3, 0, 3
                ),
                listOf(
                    3, 1, 2, 0, 0, 2, 0, 3, 0, 1, 0, 0, 3, 1, 3, 1, 3, 0, 0, 3, 0, 3, 0, 2, 3, 1, 1, 3, 1, 0, 2, 2, 3, 0, 0, 1, 1, 2, 0, 2
                ),
                listOf(
                    0, 3, 1, 3, 2, 1, 1, 2, 0, 0, 3, 1, 2, 0, 0, 0, 0, 3, 1, 0, 1, 2, 2, 0, 2, 1, 1, 1, 1, 0, 3, 0, 0, 1, 2, 3, 2, 1, 2, 1
                ),
                listOf(
                    2, 3, 3, 0, 1, 1, 1, 2, 3, 3, 0, 1, 1, 1, 1, 3, 2, 0, 2, 3, 2, 2, 0, 0, 1, 2, 1, 2, 0, 3, 2, 3, 3, 1, 0, 1, 2, 2, 1, 1
                ),
                listOf(
                    2, 3, 1, 2, 2, 2, 1, 3, 3, 1, 3, 2, 2, 1, 2, 2, 2, 2, 3, 0, 3, 0, 3, 1, 2, 1, 0, 2, 2, 3, 0, 2, 2, 0, 2, 2, 0, 0, 3, 3
                ),
                listOf(
                    3, 3, 1, 2, 0, 0, 2, 1, 3, 0, 2, 0, 3, 3, 1, 3, 1, 1, 0, 1, 3, 1, 1, 1, 1, 2, 0, 3, 2, 3, 1, 3, 2, 1, 0, 2, 2, 1, 0, 1
                ),
                listOf(
                    1, 1, 1, 2, 3, 0, 0, 3, 3, 2, 0, 1, 0, 2, 0, 1, 0, 3, 2, 3, 0, 2, 2, 2, 2, 2, 3, 2, 3, 1, 0, 0, 2, 1, 0, 2, 2, 2, 2, 0
                ),
                listOf(
                    0, 0, 0, 0, 2, 3, 2, 0, 2, 2, 3, 1, 1, 2, 2, 1, 1, 3, 2, 0, 1, 1, 2, 1, 2, 3, 0, 1, 1, 1, 1, 0, 2, 3, 0, 1, 2, 1, 0, 3
                ),
                listOf(
                    3, 1, 2, 3, 3, 3, 3, 3, 2, 0, 0, 1, 2, 0, 0, 2, 2, 2, 2, 1, 2, 0, 0, 3, 1, 1, 2, 0, 0, 1, 1, 2, 1, 3, 3, 1, 2, 1, 0, 0
                ),
                listOf(
                    3, 1, 3, 1, 2, 2, 1, 2, 3, 1, 3, 0, 1, 3, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 1, 3, 2, 2, 1, 0, 1, 1, 2, 0, 3, 0, 0, 1, 2, 2
                ),
                listOf(
                    0, 3, 2, 2, 1, 3, 3, 2, 2, 1, 2, 0, 1, 2, 1, 2, 1, 2, 3, 1, 1, 0, 2, 0, 1, 1, 1, 1, 3, 1, 0, 0, 1, 0, 3, 3, 0, 1, 3, 1
                ),
                listOf(
                    3, 1, 3, 1, 1, 0, 2, 1, 3, 3, 0, 3, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 1, 0, 2, 0, 0, 1, 2, 3, 3, 2, 1, 1, 1, 1, 3, 1, 3
                ),
                listOf(
                    0, 2, 0, 0, 2, 0, 0, 3, 3, 3, 3, 2, 0, 3, 0, 3, 3, 1, 2, 1, 3, 1, 3, 1, 3, 0, 2, 3, 0, 0, 1, 2, 3, 1, 3, 3, 3, 3, 0, 1
                ),
                listOf(
                    0, 3, 0, 0, 2, 2, 3, 1, 2, 1, 0, 3, 2, 2, 1, 3, 0, 2, 3, 0, 0, 1, 3, 0, 2, 3, 0, 1, 3, 3, 1, 2, 0, 2, 0, 1, 2, 3, 1, 1
                ),
                listOf(
                    1, 1, 2, 2, 0, 3, 2, 1, 3, 3, 1, 3, 1, 2, 1, 0, 1, 1, 3, 3, 0, 3, 0, 1, 1, 0, 2, 3, 0, 2, 3, 3, 3, 2, 2, 1, 2, 1, 3, 3
                ),
                listOf(
                    1, 2, 1, 3, 1, 0, 0, 2, 2, 2, 3, 2, 3, 3, 3, 3, 1, 2, 3, 1, 3, 3, 2, 0, 2, 1, 0, 0, 0, 0, 3, 0, 3, 1, 2, 1, 3, 1, 1, 1
                ),
                listOf(
                    0, 3, 1, 2, 1, 3, 0, 2, 1, 0, 3, 1, 1, 1, 0, 0, 2, 2, 1, 1, 3, 3, 0, 3, 1, 3, 1, 1, 2, 2, 1, 1, 2, 0, 3, 1, 3, 2, 0, 2
                ),
                listOf(
                    0, 0, 0, 0, 3, 0, 2, 2, 2, 3, 0, 3, 1, 0, 0, 1, 0, 1, 0, 0, 0, 2, 3, 2, 0, 0, 0, 0, 3, 0, 0, 1, 3, 0, 3, 3, 1, 1, 0, 1
                ),
                listOf(
                    2, 0, 1, 1, 2, 1, 0, 3, 0, 0, 0, 2, 0, 3, 2, 0, 1, 2, 3, 2, 2, 3, 1, 0, 2, 2, 0, 2, 2, 0, 1, 3, 2, 0, 0, 1, 3, 2, 3, 1
                ),
                listOf(
                    3, 1, 3, 0, 3, 3, 1, 1, 0, 3, 0, 0, 3, 2, 3, 3, 0, 2, 3, 2, 3, 1, 2, 0, 1, 1, 3, 3, 3, 1, 1, 2, 3, 2, 3, 2, 1, 0, 1, 2
                ),
                listOf(
                    0, 2, 0, 3, 3, 2, 0, 1, 2, 1, 1, 0, 0, 3, 1, 2, 1, 2, 2, 3, 3, 2, 1, 2, 2, 1, 1, 3, 1, 0, 3, 2, 3, 0, 1, 1, 0, 0, 1, 0
                ),
                listOf(
                    1, 0, 3, 1, 1, 1, 1, 3, 1, 0, 0, 2, 1, 3, 1, 1, 2, 1, 2, 0, 3, 1, 2, 0, 3, 0, 2, 0, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 1, 0
                ),
                listOf(
                    1, 1, 0, 0, 0, 2, 3, 2, 2, 3, 2, 3, 3, 0, 0, 3, 3, 0, 0, 2, 2, 1, 2, 3, 1, 1, 0, 1, 2, 0, 0, 0, 1, 1, 0, 3, 2, 2, 1, 2
                ),
                listOf(
                    3, 2, 3, 1, 0, 2, 1, 0, 0, 3, 0, 3, 3, 1, 2, 0, 3, 2, 2, 1, 0, 0, 3, 1, 0, 1, 1, 3, 3, 3, 2, 1, 2, 0, 2, 2, 3, 2, 3, 0
                ),
                listOf(
                    1, 1, 1, 1, 2, 1, 0, 3, 1, 2, 2, 0, 1, 3, 0, 3, 3, 3, 2, 0, 3, 2, 1, 0, 3, 1, 0, 2, 2, 1, 0, 1, 0, 3, 2, 0, 1, 2, 0, 2
                ),
                listOf(
                    0, 3, 1, 2, 3, 1, 0, 0, 1, 1, 1, 3, 0, 3, 1, 2, 2, 3, 3, 3, 3, 1, 2, 1, 2, 2, 1, 2, 0, 3, 2, 3, 2, 2, 0, 1, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 2, 0, 0, 0, 0, 0, 1, 3, 2, 2, 1, 1, 0, 2, 2, 2, 2, 0, 2, 3, 0, 1, 0, 2, 3, 1, 2, 1, 3, 3, 2, 3, 0, 0, 2, 1, 1, 1
                ),
                listOf(
                    2, 0, 2, 0, 2, 1, 0, 1, 0, 2, 2, 2, 3, 0, 2, 2, 0, 3, 2, 1, 2, 0, 0, 1, 2, 1, 0, 1, 0, 2, 3, 0, 1, 2, 1, 2, 0, 1, 0, 3
                ),
                listOf(
                    1, 1, 1, 1, 1, 3, 0, 2, 1, 1, 3, 0, 1, 1, 3, 3, 3, 3, 3, 2, 1, 1, 0, 3, 2, 3, 3, 2, 0, 1, 0, 2, 2, 1, 0, 1, 2, 3, 1, 3
                ),
                listOf(
                    0, 3, 1, 1, 3, 2, 1, 0, 1, 3, 0, 0, 2, 0, 2, 0, 1, 3, 1, 1, 1, 2, 1, 0, 3, 0, 1, 0, 1, 2, 0, 2, 1, 0, 1, 3, 2, 0, 1, 1
                ),
                listOf(
                    0, 0, 1, 1, 1, 3, 1, 1, 0, 2, 3, 2, 0, 2, 0, 1, 3, 0, 1, 2, 0, 0, 1, 3, 2, 3, 0, 3, 2, 1, 1, 1, 2, 3, 1, 1, 0, 0, 3, 3
                ),
                listOf(
                    0, 3, 3, 1, 0, 2, 2, 0, 0, 0, 2, 1, 0, 1, 1, 1, 0, 1, 2, 1, 2, 1, 1, 1, 3, 0, 2, 3, 0, 1, 3, 1, 2, 0, 3, 2, 0, 1, 2, 2
                ),
                listOf(
                    1, 2, 1, 1, 3, 2, 0, 1, 3, 2, 0, 3, 2, 0, 1, 3, 3, 2, 2, 1, 3, 0, 2, 0, 0, 2, 1, 3, 2, 1, 3, 2, 3, 3, 1, 1, 2, 3, 0, 0
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 3, 2, 1, 1, 1, 1, 0, 2, 3, 3, 3, 3, 2, 0, 0, 3, 0, 0, 0, 1, 0, 3, 0, 0, 0, 1, 3, 0, 0, 2, 0, 2, 2, 0
                ),
                listOf(
                    2, 3, 3, 0, 2, 3, 3, 2, 0, 1, 2, 0, 2, 3, 2, 0, 2, 3, 2, 3, 1, 3, 3, 1, 0, 0, 3, 1, 3, 2, 0, 1, 0, 1, 1, 1, 2, 3, 3, 1
                ),
                listOf(
                    0, 2, 3, 3, 2, 2, 1, 3, 2, 1, 3, 3, 2, 1, 3, 0, 2, 1, 2, 1, 1, 0, 0, 1, 2, 1, 1, 3, 2, 2, 2, 3, 3, 0, 2, 0, 1, 0, 3, 2
                ),
                listOf(
                    0, 0, 2, 0, 2, 0, 2, 3, 1, 1, 2, 0, 3, 0, 0, 2, 3, 3, 1, 0, 2, 2, 2, 3, 3, 3, 3, 1, 2, 1, 1, 2, 1, 2, 3, 3, 0, 0, 3, 1
                ),
                listOf(
                    1, 0, 1, 2, 3, 1, 1, 0, 1, 0, 1, 2, 1, 2, 1, 1, 1, 0, 2, 1, 2, 0, 1, 1, 3, 1, 1, 1, 2, 0, 3, 2, 2, 0, 1, 2, 0, 2, 2, 3
                ),
                listOf(
                    3, 0, 1, 1, 1, 3, 3, 2, 3, 1, 3, 2, 3, 2, 0, 3, 2, 2, 2, 2, 0, 0, 3, 0, 1, 3, 0, 2, 1, 0, 2, 0, 2, 0, 1, 3, 2, 1, 0, 3
                ),
                listOf(
                    3, 2, 2, 1, 2, 1, 1, 2, 2, 3, 3, 2, 2, 2, 2, 3, 0, 1, 0, 0, 3, 1, 3, 3, 3, 0, 0, 0, 1, 0, 2, 2, 0, 0, 0, 0, 3, 3, 1, 1
                ),
                listOf(
                    0, 2, 0, 2, 2, 0, 3, 2, 1, 1, 3, 1, 2, 1, 0, 2, 0, 0, 0, 1, 3, 2, 3, 2, 0, 0, 0, 1, 2, 0, 3, 3, 2, 2, 3, 1, 3, 0, 0, 1
                ),
                listOf(
                    3, 3, 1, 1, 3, 3, 3, 3, 3, 2, 2, 1, 2, 2, 2, 0, 0, 3, 2, 3, 2, 2, 0, 0, 3, 1, 1, 2, 3, 3, 1, 0, 0, 0, 1, 1, 0, 1, 3, 3
                ),
                listOf(
                    0, 2, 1, 1, 2, 1, 3, 2, 3, 1, 3, 3, 0, 0, 0, 0, 0, 3, 2, 3, 0, 2, 3, 1, 0, 1, 3, 0, 2, 3, 2, 3, 3, 3, 0, 2, 2, 2, 2, 3
                ),
                listOf(
                    0, 3, 1, 0, 3, 3, 0, 3, 3, 3, 2, 3, 3, 3, 3, 1, 0, 2, 0, 0, 0, 0, 3, 2, 3, 0, 1, 0, 1, 2, 0, 1, 1, 1, 1, 0, 2, 3, 1, 2
                ),
                listOf(
                    1, 2, 3, 3, 1, 2, 3, 1, 1, 1, 1, 0, 2, 1, 3, 0, 1, 2, 0, 0, 3, 1, 2, 2, 1, 1, 3, 1, 1, 1, 0, 2, 1, 3, 2, 2, 2, 0, 0, 1
                ),
                listOf(
                    1, 3, 3, 3, 2, 3, 0, 2, 2, 2, 1, 1, 3, 1, 1, 2, 3, 2, 0, 0, 2, 1, 1, 2, 0, 0, 1, 1, 3, 1, 2, 2, 1, 1, 0, 0, 3, 2, 3, 3
                ),
                listOf(
                    2, 2, 3, 0, 3, 1, 2, 3, 0, 2, 2, 2, 1, 3, 2, 1, 1, 2, 2, 2, 3, 0, 0, 3, 0, 2, 2, 2, 2, 3, 0, 3, 2, 2, 1, 1, 0, 1, 1, 0
                ),
                listOf(
                    3, 1, 1, 1, 0, 0, 1, 0, 1, 0, 3, 2, 0, 2, 0, 1, 1, 2, 0, 0, 0, 0, 1, 0, 3, 0, 3, 1, 2, 2, 2, 1, 0, 0, 1, 2, 1, 2, 1, 2
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 25),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 25),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk91(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_012", "Legendary", 40, 60, listOf(
                listOf(
                    0, 3, 0, 3, 3, 0, 3, 0, 3, 1, 0, 1, 1, 1, 3, 0, 0, 0, 0, 3, 1, 2, 3, 1, 0, 1, 1, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 1, 0, 2
                ),
                listOf(
                    3, 3, 3, 0, 3, 2, 0, 3, 0, 3, 2, 2, 1, 2, 2, 1, 3, 1, 1, 1, 3, 3, 0, 1, 2, 3, 0, 2, 0, 1, 0, 1, 2, 0, 1, 3, 1, 3, 1, 0
                ),
                listOf(
                    0, 2, 1, 3, 2, 2, 0, 1, 3, 0, 3, 2, 3, 3, 2, 2, 2, 3, 0, 2, 0, 3, 1, 1, 2, 1, 2, 3, 0, 1, 2, 3, 3, 0, 3, 2, 1, 3, 3, 2
                ),
                listOf(
                    0, 3, 0, 1, 0, 3, 2, 1, 0, 3, 2, 2, 0, 2, 0, 1, 1, 3, 1, 2, 0, 3, 2, 0, 2, 2, 1, 3, 2, 0, 0, 2, 3, 3, 2, 3, 0, 2, 2, 2
                ),
                listOf(
                    0, 2, 2, 3, 0, 0, 3, 3, 0, 0, 1, 1, 1, 2, 3, 3, 3, 3, 1, 2, 2, 0, 3, 1, 3, 0, 2, 3, 1, 0, 0, 1, 1, 1, 2, 3, 2, 0, 3, 3
                ),
                listOf(
                    1, 3, 3, 0, 3, 1, 1, 3, 2, 2, 1, 2, 3, 3, 1, 2, 3, 0, 0, 0, 0, 3, 1, 1, 3, 3, 3, 0, 2, 2, 0, 3, 1, 1, 0, 3, 0, 3, 3, 2
                ),
                listOf(
                    3, 0, 0, 2, 0, 1, 0, 3, 3, 3, 1, 1, 3, 0, 1, 0, 0, 0, 3, 2, 3, 1, 2, 0, 3, 0, 1, 1, 3, 2, 0, 3, 1, 0, 1, 3, 3, 0, 1, 2
                ),
                listOf(
                    2, 2, 0, 3, 3, 3, 0, 0, 1, 1, 3, 1, 1, 3, 2, 1, 2, 2, 0, 3, 1, 2, 1, 0, 0, 0, 0, 2, 3, 3, 1, 3, 3, 1, 2, 3, 0, 0, 2, 0
                ),
                listOf(
                    2, 0, 0, 2, 0, 3, 2, 3, 1, 0, 0, 3, 1, 3, 3, 3, 1, 0, 2, 2, 2, 3, 0, 0, 0, 2, 1, 1, 2, 0, 0, 0, 3, 1, 0, 1, 0, 0, 1, 3
                ),
                listOf(
                    1, 2, 0, 2, 2, 2, 3, 2, 2, 3, 1, 0, 1, 3, 2, 1, 2, 3, 2, 1, 0, 0, 3, 3, 0, 3, 1, 2, 0, 2, 0, 0, 3, 3, 2, 3, 2, 0, 3, 3
                ),
                listOf(
                    2, 3, 3, 0, 0, 2, 1, 1, 0, 2, 0, 3, 2, 3, 2, 2, 1, 3, 2, 3, 2, 3, 1, 1, 1, 1, 2, 0, 1, 3, 2, 1, 0, 2, 3, 2, 1, 2, 3, 3
                ),
                listOf(
                    3, 1, 3, 3, 2, 1, 3, 1, 3, 0, 1, 0, 2, 3, 0, 0, 3, 3, 1, 0, 0, 3, 1, 2, 1, 2, 0, 3, 2, 0, 0, 2, 1, 3, 2, 2, 2, 2, 0, 2
                ),
                listOf(
                    2, 1, 1, 0, 0, 1, 3, 0, 3, 2, 2, 0, 2, 3, 0, 2, 3, 2, 0, 0, 0, 1, 0, 2, 1, 2, 3, 0, 2, 1, 3, 2, 2, 1, 3, 0, 2, 1, 2, 3
                ),
                listOf(
                    2, 1, 1, 2, 2, 0, 0, 0, 1, 2, 0, 1, 0, 2, 0, 1, 2, 0, 1, 1, 0, 0, 3, 2, 2, 1, 0, 0, 3, 0, 1, 2, 1, 0, 3, 3, 3, 3, 3, 3
                ),
                listOf(
                    2, 3, 2, 3, 3, 0, 1, 2, 1, 1, 3, 1, 2, 3, 2, 0, 1, 1, 3, 3, 3, 2, 1, 1, 0, 0, 3, 3, 1, 3, 2, 0, 2, 2, 3, 3, 3, 0, 3, 3
                ),
                listOf(
                    1, 0, 2, 1, 0, 0, 1, 2, 0, 3, 3, 1, 1, 0, 3, 3, 3, 0, 3, 0, 3, 1, 3, 2, 3, 3, 2, 2, 3, 0, 2, 0, 0, 0, 1, 1, 3, 0, 0, 2
                ),
                listOf(
                    0, 3, 0, 2, 2, 1, 2, 2, 2, 0, 2, 0, 3, 1, 0, 1, 2, 3, 3, 3, 3, 1, 3, 0, 3, 2, 3, 0, 1, 1, 1, 3, 0, 1, 1, 2, 2, 2, 0, 3
                ),
                listOf(
                    2, 2, 0, 3, 2, 1, 1, 3, 0, 2, 1, 2, 2, 1, 0, 2, 2, 0, 1, 1, 2, 0, 1, 0, 3, 1, 3, 2, 0, 0, 1, 1, 1, 2, 0, 3, 1, 1, 0, 0
                ),
                listOf(
                    1, 2, 3, 2, 1, 3, 1, 2, 0, 2, 0, 2, 1, 0, 0, 0, 1, 0, 2, 1, 0, 1, 0, 1, 2, 1, 1, 1, 2, 3, 3, 0, 2, 3, 2, 2, 0, 1, 0, 0
                ),
                listOf(
                    3, 3, 1, 3, 1, 2, 1, 3, 2, 2, 2, 1, 3, 3, 1, 1, 1, 1, 0, 2, 3, 2, 1, 3, 3, 1, 2, 2, 0, 1, 3, 1, 0, 1, 1, 3, 0, 0, 3, 1
                ),
                listOf(
                    1, 3, 3, 0, 0, 2, 2, 1, 1, 3, 3, 2, 3, 2, 1, 3, 2, 0, 0, 1, 1, 0, 2, 0, 0, 1, 0, 2, 3, 3, 3, 3, 2, 3, 0, 2, 1, 3, 0, 0
                ),
                listOf(
                    3, 2, 3, 2, 1, 2, 1, 1, 3, 3, 1, 1, 2, 0, 0, 0, 3, 2, 3, 2, 1, 0, 0, 2, 2, 2, 2, 2, 0, 0, 1, 2, 1, 3, 0, 1, 0, 0, 0, 1
                ),
                listOf(
                    0, 2, 1, 0, 3, 2, 3, 1, 2, 1, 3, 0, 2, 0, 2, 3, 2, 3, 1, 2, 3, 3, 0, 0, 0, 2, 1, 1, 0, 2, 1, 2, 2, 3, 2, 3, 3, 0, 0, 0
                ),
                listOf(
                    3, 3, 2, 0, 1, 2, 2, 2, 1, 1, 3, 1, 0, 1, 3, 1, 2, 2, 2, 0, 3, 3, 0, 2, 3, 2, 1, 0, 0, 0, 3, 3, 3, 2, 1, 2, 3, 3, 2, 3
                ),
                listOf(
                    0, 2, 3, 2, 1, 1, 2, 1, 3, 0, 0, 3, 3, 1, 3, 3, 0, 1, 1, 3, 1, 1, 0, 1, 3, 0, 3, 3, 3, 0, 0, 2, 3, 1, 3, 0, 0, 0, 1, 0
                ),
                listOf(
                    3, 2, 0, 2, 1, 2, 0, 3, 1, 2, 3, 1, 3, 1, 3, 3, 0, 0, 1, 2, 0, 1, 0, 2, 0, 3, 1, 3, 2, 3, 1, 3, 0, 3, 3, 0, 3, 3, 2, 2
                ),
                listOf(
                    3, 3, 1, 1, 1, 2, 1, 2, 2, 2, 2, 0, 3, 0, 1, 0, 3, 3, 2, 1, 1, 3, 0, 3, 3, 0, 2, 1, 2, 1, 3, 3, 0, 0, 3, 2, 2, 1, 2, 3
                ),
                listOf(
                    3, 3, 2, 3, 0, 3, 0, 2, 0, 1, 3, 2, 1, 1, 1, 0, 2, 0, 0, 1, 1, 1, 0, 3, 0, 2, 2, 1, 0, 1, 1, 2, 1, 0, 2, 0, 3, 0, 0, 0
                ),
                listOf(
                    1, 3, 3, 0, 2, 2, 3, 0, 3, 0, 0, 1, 2, 0, 1, 3, 0, 1, 2, 0, 2, 3, 3, 1, 1, 3, 0, 1, 3, 2, 2, 0, 3, 2, 0, 2, 2, 2, 1, 2
                ),
                listOf(
                    2, 3, 0, 2, 0, 1, 3, 3, 3, 3, 2, 3, 2, 2, 3, 2, 3, 2, 1, 3, 2, 0, 0, 0, 0, 2, 0, 0, 3, 3, 0, 0, 2, 0, 2, 2, 3, 3, 1, 1
                ),
                listOf(
                    1, 0, 3, 3, 1, 0, 3, 1, 0, 1, 1, 2, 2, 0, 1, 3, 3, 2, 3, 0, 1, 0, 0, 3, 1, 0, 0, 3, 3, 2, 1, 2, 1, 1, 3, 0, 0, 2, 1, 2
                ),
                listOf(
                    3, 3, 2, 2, 1, 3, 2, 0, 0, 0, 3, 0, 0, 2, 2, 1, 2, 3, 2, 0, 1, 3, 3, 3, 1, 1, 0, 2, 3, 0, 1, 3, 1, 2, 3, 1, 2, 3, 1, 0
                ),
                listOf(
                    0, 3, 2, 2, 3, 3, 3, 2, 3, 3, 3, 3, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 3, 3, 3, 1, 1, 0, 3, 1, 0, 0, 1, 0, 3, 3, 2, 3, 3, 1
                ),
                listOf(
                    0, 2, 1, 3, 0, 0, 0, 1, 2, 1, 0, 2, 3, 0, 1, 0, 2, 1, 1, 0, 3, 0, 1, 0, 0, 0, 0, 2, 1, 0, 2, 1, 2, 3, 1, 1, 3, 0, 1, 1
                ),
                listOf(
                    1, 0, 3, 0, 3, 3, 3, 3, 1, 2, 1, 3, 1, 3, 2, 0, 3, 0, 2, 3, 3, 1, 1, 1, 3, 3, 1, 0, 3, 0, 1, 3, 1, 0, 3, 1, 3, 3, 2, 1
                ),
                listOf(
                    0, 0, 0, 0, 2, 2, 3, 2, 2, 1, 0, 0, 1, 2, 1, 2, 0, 2, 0, 0, 3, 1, 1, 1, 3, 3, 3, 2, 2, 0, 3, 2, 3, 2, 2, 0, 2, 0, 2, 0
                ),
                listOf(
                    3, 3, 3, 1, 0, 0, 1, 1, 1, 2, 1, 2, 3, 3, 0, 0, 0, 3, 0, 1, 2, 2, 3, 1, 0, 3, 3, 1, 2, 0, 1, 2, 3, 3, 3, 2, 0, 2, 0, 2
                ),
                listOf(
                    2, 1, 3, 2, 3, 3, 2, 3, 3, 1, 3, 0, 0, 3, 0, 0, 0, 2, 0, 1, 1, 1, 2, 1, 3, 2, 3, 2, 1, 1, 3, 1, 2, 3, 2, 0, 0, 3, 3, 0
                ),
                listOf(
                    2, 0, 2, 1, 3, 1, 0, 1, 1, 2, 1, 1, 1, 3, 2, 1, 2, 1, 0, 1, 0, 0, 0, 0, 3, 1, 1, 3, 2, 0, 0, 2, 2, 2, 0, 3, 2, 0, 0, 3
                ),
                listOf(
                    1, 0, 0, 3, 2, 1, 2, 1, 0, 3, 0, 1, 0, 3, 3, 3, 1, 3, 1, 3, 0, 1, 3, 3, 3, 3, 3, 2, 1, 2, 3, 1, 2, 0, 2, 0, 3, 2, 2, 2
                ),
                listOf(
                    1, 0, 1, 1, 2, 3, 1, 2, 3, 0, 0, 2, 3, 2, 3, 1, 0, 1, 1, 0, 0, 1, 3, 1, 1, 1, 2, 1, 2, 0, 2, 1, 0, 1, 2, 3, 3, 0, 3, 2
                ),
                listOf(
                    1, 1, 2, 1, 1, 2, 1, 0, 1, 1, 0, 3, 3, 0, 1, 0, 0, 3, 0, 0, 3, 2, 0, 3, 1, 0, 3, 0, 0, 3, 0, 3, 1, 1, 2, 0, 3, 0, 1, 3
                ),
                listOf(
                    3, 0, 0, 0, 1, 0, 3, 3, 0, 1, 3, 3, 2, 2, 3, 3, 1, 1, 3, 3, 1, 0, 2, 3, 1, 1, 1, 3, 3, 0, 0, 3, 0, 1, 3, 2, 1, 1, 2, 1
                ),
                listOf(
                    0, 2, 1, 3, 2, 1, 3, 2, 1, 2, 0, 1, 3, 2, 3, 1, 0, 2, 3, 0, 3, 3, 0, 1, 1, 0, 2, 3, 0, 0, 2, 2, 3, 2, 0, 0, 3, 1, 0, 0
                ),
                listOf(
                    0, 3, 1, 1, 2, 3, 1, 2, 2, 1, 3, 2, 0, 3, 1, 2, 0, 2, 2, 3, 3, 2, 1, 1, 0, 1, 3, 0, 1, 3, 3, 1, 1, 1, 1, 2, 1, 2, 2, 2
                ),
                listOf(
                    3, 1, 2, 0, 0, 3, 2, 3, 2, 3, 2, 3, 3, 2, 3, 1, 2, 0, 0, 2, 2, 0, 3, 2, 3, 1, 1, 2, 1, 0, 2, 0, 3, 2, 2, 3, 0, 2, 2, 1
                ),
                listOf(
                    0, 1, 2, 0, 0, 2, 2, 2, 1, 3, 0, 3, 0, 0, 1, 3, 3, 1, 0, 3, 0, 2, 3, 0, 0, 2, 3, 1, 3, 2, 3, 0, 3, 2, 0, 3, 1, 1, 1, 2
                ),
                listOf(
                    2, 1, 1, 0, 2, 3, 2, 1, 0, 1, 3, 3, 0, 2, 1, 3, 2, 3, 0, 1, 3, 0, 1, 3, 2, 3, 1, 1, 3, 2, 3, 0, 0, 0, 0, 1, 2, 1, 0, 2
                ),
                listOf(
                    1, 3, 3, 2, 3, 1, 2, 2, 0, 0, 3, 1, 0, 2, 1, 1, 0, 0, 0, 2, 1, 2, 1, 3, 1, 2, 1, 0, 3, 1, 2, 1, 0, 1, 2, 2, 1, 2, 3, 2
                ),
                listOf(
                    2, 0, 0, 0, 0, 0, 2, 0, 2, 1, 2, 3, 2, 1, 1, 1, 3, 0, 0, 3, 0, 1, 3, 3, 3, 1, 2, 1, 1, 2, 1, 3, 0, 3, 0, 3, 2, 2, 2, 1
                ),
                listOf(
                    3, 3, 3, 0, 0, 2, 0, 0, 0, 2, 1, 2, 0, 2, 2, 0, 3, 2, 2, 0, 2, 1, 3, 1, 2, 0, 2, 1, 3, 2, 0, 2, 0, 3, 3, 3, 1, 0, 3, 2
                ),
                listOf(
                    0, 1, 1, 3, 1, 3, 1, 1, 1, 1, 0, 0, 2, 3, 0, 1, 2, 3, 1, 1, 0, 3, 3, 1, 0, 1, 1, 0, 2, 3, 1, 1, 1, 0, 1, 3, 1, 1, 3, 3
                ),
                listOf(
                    2, 3, 1, 3, 0, 3, 0, 1, 1, 1, 2, 3, 3, 1, 1, 3, 2, 0, 0, 3, 0, 3, 1, 1, 3, 2, 3, 2, 3, 1, 0, 3, 0, 0, 2, 1, 2, 1, 2, 2
                ),
                listOf(
                    2, 2, 2, 3, 3, 1, 0, 3, 1, 2, 3, 0, 3, 3, 1, 3, 2, 1, 1, 0, 3, 1, 2, 2, 3, 2, 0, 2, 2, 3, 2, 2, 0, 2, 2, 1, 2, 0, 0, 3
                ),
                listOf(
                    0, 0, 2, 0, 3, 1, 0, 1, 2, 0, 2, 2, 1, 0, 0, 2, 0, 0, 0, 1, 3, 0, 1, 0, 1, 0, 2, 3, 0, 1, 1, 1, 3, 2, 0, 1, 1, 3, 2, 1
                ),
                listOf(
                    0, 1, 2, 3, 0, 0, 1, 3, 2, 0, 3, 1, 0, 0, 3, 0, 1, 0, 3, 0, 0, 1, 3, 0, 0, 2, 3, 1, 3, 3, 1, 2, 0, 3, 3, 0, 2, 1, 0, 3
                ),
                listOf(
                    3, 0, 0, 1, 2, 1, 0, 0, 1, 2, 3, 0, 2, 2, 1, 0, 1, 3, 3, 2, 3, 1, 2, 0, 2, 2, 3, 0, 3, 2, 0, 3, 2, 1, 0, 2, 3, 0, 0, 2
                ),
                listOf(
                    1, 1, 1, 2, 3, 1, 3, 0, 1, 0, 3, 2, 3, 1, 2, 0, 1, 0, 1, 2, 3, 2, 2, 3, 2, 0, 0, 0, 2, 2, 1, 0, 2, 2, 2, 2, 2, 1, 2, 3
                ),
                listOf(
                    2, 2, 0, 3, 3, 0, 3, 3, 0, 1, 2, 0, 1, 1, 1, 2, 3, 0, 2, 3, 0, 1, 1, 1, 1, 2, 0, 1, 1, 1, 3, 2, 1, 0, 3, 1, 2, 1, 1, 2
                ),
                listOf(
                    1, 2, 3, 2, 2, 0, 0, 1, 1, 2, 1, 2, 2, 0, 2, 3, 2, 0, 0, 2, 0, 1, 2, 0, 0, 2, 1, 2, 1, 3, 3, 3, 3, 2, 3, 0, 3, 1, 3, 2
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk92(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_013", "Legendary", 40, 60, listOf(
                listOf(
                    2, 3, 1, 3, 0, 2, 2, 1, 2, 0, 3, 3, 3, 0, 1, 3, 3, 1, 3, 1, 3, 2, 2, 0, 3, 1, 0, 0, 3, 1, 2, 2, 1, 3, 1, 0, 3, 0, 0, 2
                ),
                listOf(
                    1, 1, 3, 0, 0, 1, 2, 3, 3, 3, 0, 3, 3, 2, 0, 0, 1, 2, 2, 1, 0, 0, 3, 2, 0, 0, 0, 0, 3, 2, 3, 3, 1, 0, 0, 0, 0, 0, 1, 2
                ),
                listOf(
                    1, 3, 3, 0, 2, 3, 1, 0, 0, 0, 3, 0, 1, 3, 1, 2, 3, 0, 1, 0, 2, 2, 1, 3, 3, 0, 0, 3, 2, 3, 1, 0, 3, 2, 3, 1, 2, 0, 1, 0
                ),
                listOf(
                    3, 2, 0, 0, 1, 3, 3, 3, 3, 3, 2, 1, 0, 2, 3, 1, 3, 3, 0, 0, 2, 1, 3, 1, 2, 2, 2, 0, 0, 3, 2, 3, 3, 1, 0, 1, 1, 2, 3, 3
                ),
                listOf(
                    2, 1, 0, 2, 2, 3, 3, 0, 1, 0, 1, 3, 1, 1, 0, 3, 0, 2, 0, 2, 0, 0, 1, 2, 2, 1, 1, 1, 2, 2, 0, 1, 3, 0, 0, 3, 2, 0, 0, 3
                ),
                listOf(
                    3, 2, 1, 0, 3, 0, 0, 0, 2, 2, 3, 1, 1, 3, 0, 0, 0, 0, 3, 3, 2, 1, 3, 3, 2, 2, 2, 1, 2, 2, 3, 1, 1, 1, 3, 3, 1, 3, 0, 2
                ),
                listOf(
                    3, 3, 2, 3, 0, 2, 2, 2, 1, 0, 0, 1, 2, 3, 3, 3, 3, 0, 1, 3, 0, 2, 0, 2, 1, 0, 3, 1, 1, 3, 0, 0, 2, 0, 1, 3, 3, 0, 2, 0
                ),
                listOf(
                    2, 0, 1, 2, 3, 2, 3, 0, 0, 3, 2, 1, 1, 1, 1, 3, 0, 0, 1, 2, 2, 0, 0, 1, 3, 0, 3, 0, 3, 3, 0, 2, 0, 1, 0, 1, 1, 3, 0, 3
                ),
                listOf(
                    3, 0, 2, 2, 2, 3, 2, 0, 1, 3, 1, 1, 3, 0, 3, 1, 0, 0, 3, 2, 3, 3, 0, 3, 0, 1, 1, 2, 2, 2, 2, 2, 3, 0, 1, 0, 2, 1, 1, 3
                ),
                listOf(
                    0, 1, 0, 1, 1, 3, 3, 0, 3, 3, 3, 1, 2, 2, 3, 2, 2, 3, 0, 0, 3, 0, 0, 1, 3, 2, 2, 2, 1, 0, 3, 1, 1, 0, 3, 2, 0, 1, 1, 3
                ),
                listOf(
                    3, 2, 3, 2, 3, 1, 1, 2, 1, 1, 0, 2, 0, 0, 2, 0, 1, 0, 0, 0, 0, 3, 1, 0, 0, 0, 0, 0, 3, 1, 3, 0, 3, 2, 2, 0, 0, 2, 1, 0
                ),
                listOf(
                    0, 2, 0, 1, 0, 0, 0, 3, 0, 3, 2, 3, 3, 2, 3, 1, 1, 0, 2, 2, 2, 3, 2, 1, 0, 3, 0, 2, 1, 3, 0, 1, 0, 0, 0, 2, 2, 3, 3, 2
                ),
                listOf(
                    3, 0, 3, 1, 3, 2, 1, 1, 0, 1, 2, 2, 1, 0, 0, 0, 1, 2, 2, 2, 2, 2, 0, 2, 0, 0, 2, 3, 3, 3, 1, 0, 3, 3, 3, 3, 3, 2, 2, 2
                ),
                listOf(
                    3, 2, 3, 1, 2, 0, 3, 2, 3, 0, 0, 3, 3, 1, 3, 2, 2, 2, 1, 1, 1, 2, 0, 0, 0, 0, 3, 3, 1, 1, 3, 3, 0, 2, 1, 3, 0, 0, 1, 3
                ),
                listOf(
                    1, 1, 2, 2, 0, 1, 0, 3, 2, 3, 3, 0, 1, 2, 0, 3, 1, 2, 0, 0, 0, 2, 2, 2, 1, 3, 1, 0, 3, 0, 1, 1, 2, 1, 2, 0, 2, 2, 0, 3
                ),
                listOf(
                    1, 2, 2, 0, 1, 3, 1, 3, 0, 2, 1, 2, 3, 2, 2, 1, 2, 3, 3, 0, 3, 3, 0, 1, 0, 0, 3, 0, 2, 3, 1, 1, 1, 1, 1, 0, 2, 3, 2, 1
                ),
                listOf(
                    1, 3, 3, 1, 1, 2, 1, 3, 0, 2, 0, 3, 3, 0, 1, 2, 1, 1, 3, 3, 2, 3, 0, 1, 1, 0, 0, 1, 3, 0, 0, 3, 2, 2, 0, 1, 1, 3, 0, 0
                ),
                listOf(
                    2, 0, 1, 3, 0, 3, 2, 0, 1, 0, 3, 0, 0, 0, 3, 1, 2, 1, 3, 2, 3, 3, 1, 0, 0, 1, 2, 3, 2, 1, 1, 2, 1, 3, 3, 0, 3, 3, 0, 3
                ),
                listOf(
                    3, 2, 3, 3, 3, 1, 1, 3, 3, 2, 3, 0, 1, 0, 3, 1, 0, 1, 1, 3, 3, 1, 1, 1, 3, 1, 3, 3, 3, 1, 3, 1, 1, 1, 3, 1, 0, 3, 3, 1
                ),
                listOf(
                    0, 3, 3, 3, 1, 2, 0, 2, 1, 1, 2, 0, 3, 2, 1, 0, 3, 0, 3, 1, 3, 2, 3, 1, 0, 0, 3, 1, 0, 3, 3, 0, 2, 2, 3, 1, 0, 3, 1, 2
                ),
                listOf(
                    0, 1, 2, 0, 2, 2, 2, 0, 2, 0, 1, 3, 1, 2, 1, 0, 1, 2, 1, 1, 3, 3, 3, 2, 3, 2, 1, 1, 3, 1, 3, 1, 1, 1, 0, 3, 3, 0, 1, 3
                ),
                listOf(
                    3, 2, 1, 1, 2, 0, 2, 2, 2, 2, 0, 3, 0, 2, 1, 3, 2, 2, 3, 2, 1, 1, 1, 1, 3, 3, 2, 0, 0, 0, 0, 1, 0, 2, 3, 2, 2, 3, 3, 2
                ),
                listOf(
                    1, 3, 3, 2, 3, 0, 0, 0, 2, 0, 1, 3, 3, 0, 1, 0, 2, 3, 3, 2, 0, 1, 2, 3, 1, 2, 3, 3, 3, 0, 0, 2, 3, 3, 3, 2, 2, 0, 3, 1
                ),
                listOf(
                    0, 0, 2, 1, 3, 3, 1, 3, 1, 2, 2, 0, 3, 1, 1, 1, 3, 1, 2, 0, 2, 0, 0, 3, 2, 0, 3, 0, 2, 1, 0, 0, 0, 3, 0, 0, 0, 2, 0, 1
                ),
                listOf(
                    2, 3, 0, 3, 0, 0, 1, 1, 2, 3, 3, 1, 0, 0, 3, 3, 2, 1, 3, 1, 1, 2, 3, 0, 1, 0, 0, 0, 0, 2, 3, 2, 1, 2, 1, 3, 2, 0, 3, 3
                ),
                listOf(
                    2, 0, 2, 2, 2, 0, 1, 1, 0, 3, 0, 2, 3, 1, 0, 3, 2, 0, 1, 2, 2, 3, 3, 2, 3, 3, 3, 3, 0, 0, 2, 2, 2, 3, 1, 3, 2, 0, 3, 1
                ),
                listOf(
                    1, 0, 1, 1, 1, 0, 3, 2, 0, 3, 1, 2, 2, 1, 1, 0, 1, 0, 2, 3, 2, 2, 3, 0, 2, 1, 1, 0, 0, 2, 1, 1, 0, 2, 1, 3, 0, 3, 2, 2
                ),
                listOf(
                    2, 2, 3, 2, 2, 1, 2, 2, 3, 0, 2, 1, 0, 3, 0, 1, 3, 1, 0, 2, 3, 1, 2, 2, 3, 0, 1, 0, 3, 2, 1, 0, 3, 3, 1, 0, 2, 1, 0, 2
                ),
                listOf(
                    3, 2, 1, 2, 2, 2, 0, 3, 0, 0, 3, 2, 1, 0, 2, 2, 1, 1, 3, 0, 2, 3, 3, 1, 3, 3, 2, 1, 0, 1, 1, 0, 3, 3, 1, 2, 0, 1, 3, 2
                ),
                listOf(
                    1, 2, 3, 3, 0, 0, 1, 3, 0, 0, 2, 1, 1, 2, 3, 0, 2, 0, 0, 1, 2, 2, 1, 2, 3, 2, 1, 2, 1, 0, 2, 1, 2, 2, 2, 3, 2, 2, 2, 3
                ),
                listOf(
                    2, 0, 1, 3, 0, 1, 3, 0, 0, 0, 1, 2, 2, 0, 0, 2, 1, 1, 1, 3, 1, 0, 2, 1, 1, 2, 3, 1, 3, 2, 0, 3, 0, 0, 0, 3, 1, 3, 3, 0
                ),
                listOf(
                    0, 3, 1, 2, 1, 0, 0, 3, 0, 0, 3, 3, 1, 2, 0, 0, 0, 0, 3, 1, 1, 2, 0, 0, 1, 3, 0, 0, 2, 0, 1, 3, 2, 3, 3, 0, 0, 3, 0, 1
                ),
                listOf(
                    2, 0, 3, 2, 3, 0, 3, 0, 0, 0, 3, 2, 1, 0, 1, 1, 3, 1, 0, 2, 3, 1, 0, 0, 0, 0, 0, 2, 3, 2, 3, 1, 1, 1, 1, 1, 0, 3, 0, 1
                ),
                listOf(
                    3, 3, 0, 0, 1, 1, 3, 2, 3, 3, 1, 3, 3, 3, 1, 1, 2, 1, 2, 0, 2, 3, 3, 3, 0, 0, 1, 2, 3, 0, 0, 1, 3, 0, 3, 3, 3, 0, 3, 2
                ),
                listOf(
                    3, 0, 3, 3, 1, 0, 2, 1, 3, 3, 2, 1, 3, 1, 3, 1, 1, 2, 2, 1, 0, 0, 0, 2, 3, 3, 1, 2, 0, 1, 2, 3, 2, 3, 3, 1, 1, 2, 2, 3
                ),
                listOf(
                    2, 3, 2, 1, 0, 1, 2, 0, 3, 0, 0, 0, 1, 1, 0, 3, 1, 2, 0, 0, 1, 1, 2, 2, 2, 3, 0, 0, 2, 1, 3, 1, 1, 3, 3, 1, 1, 3, 0, 1
                ),
                listOf(
                    0, 3, 2, 0, 0, 3, 3, 2, 3, 1, 1, 1, 0, 2, 1, 3, 2, 0, 0, 2, 0, 1, 3, 1, 0, 1, 1, 3, 0, 1, 0, 0, 3, 0, 2, 2, 3, 2, 2, 1
                ),
                listOf(
                    3, 1, 3, 0, 0, 1, 0, 1, 3, 1, 3, 1, 1, 0, 1, 3, 3, 3, 2, 0, 3, 3, 2, 3, 0, 3, 0, 0, 0, 2, 1, 3, 2, 1, 1, 0, 1, 2, 3, 1
                ),
                listOf(
                    3, 1, 1, 3, 3, 1, 1, 0, 1, 1, 2, 0, 0, 2, 0, 0, 1, 2, 2, 0, 0, 3, 3, 3, 1, 1, 2, 1, 2, 0, 2, 3, 3, 3, 1, 2, 3, 2, 0, 0
                ),
                listOf(
                    1, 1, 1, 0, 0, 3, 0, 3, 1, 3, 2, 1, 2, 2, 2, 1, 0, 0, 3, 1, 0, 3, 2, 0, 1, 1, 2, 2, 3, 2, 3, 0, 1, 3, 2, 1, 2, 2, 2, 0
                ),
                listOf(
                    0, 3, 0, 1, 1, 0, 1, 0, 0, 3, 1, 3, 3, 0, 0, 0, 3, 0, 3, 2, 2, 2, 3, 1, 3, 0, 0, 2, 1, 0, 2, 3, 0, 2, 0, 1, 0, 1, 1, 3
                ),
                listOf(
                    1, 1, 1, 1, 3, 1, 3, 0, 0, 2, 2, 0, 3, 2, 3, 1, 0, 0, 0, 0, 3, 2, 2, 2, 2, 2, 3, 3, 0, 1, 1, 2, 3, 2, 2, 0, 0, 3, 3, 1
                ),
                listOf(
                    3, 2, 3, 2, 1, 1, 2, 2, 3, 2, 2, 3, 0, 1, 2, 0, 2, 1, 1, 2, 2, 3, 0, 3, 0, 3, 0, 0, 3, 2, 0, 1, 1, 2, 3, 1, 2, 2, 2, 3
                ),
                listOf(
                    1, 3, 2, 0, 1, 0, 0, 3, 1, 1, 3, 2, 2, 2, 3, 1, 0, 0, 3, 1, 0, 1, 0, 2, 1, 3, 2, 0, 0, 3, 0, 0, 3, 3, 3, 1, 0, 0, 1, 3
                ),
                listOf(
                    3, 0, 0, 3, 2, 1, 3, 0, 0, 0, 3, 2, 0, 2, 0, 3, 1, 3, 3, 1, 0, 2, 0, 1, 1, 1, 1, 3, 3, 3, 0, 1, 0, 2, 2, 0, 0, 1, 1, 0
                ),
                listOf(
                    3, 0, 1, 3, 0, 0, 3, 3, 3, 3, 1, 2, 2, 0, 1, 0, 0, 0, 2, 2, 2, 3, 2, 0, 2, 2, 1, 0, 3, 1, 2, 3, 2, 0, 2, 3, 0, 3, 2, 3
                ),
                listOf(
                    2, 2, 3, 1, 0, 1, 0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 3, 0, 3, 2, 2, 2, 0, 1, 0, 2, 3, 0, 0, 0, 1, 2, 1, 3, 3, 0, 3, 1, 0, 3
                ),
                listOf(
                    2, 0, 1, 2, 3, 1, 0, 3, 3, 0, 0, 1, 0, 0, 3, 2, 1, 2, 3, 2, 1, 0, 2, 3, 3, 2, 0, 3, 1, 1, 0, 0, 3, 3, 2, 1, 0, 1, 2, 2
                ),
                listOf(
                    2, 2, 3, 0, 0, 3, 3, 3, 3, 1, 0, 3, 3, 2, 1, 3, 0, 1, 3, 0, 3, 1, 0, 0, 1, 3, 1, 2, 1, 2, 1, 1, 2, 2, 1, 3, 0, 2, 1, 3
                ),
                listOf(
                    0, 3, 3, 2, 0, 3, 3, 1, 0, 3, 0, 0, 0, 2, 0, 1, 0, 0, 0, 1, 0, 0, 3, 0, 1, 2, 3, 2, 1, 0, 1, 3, 1, 0, 2, 3, 1, 0, 0, 2
                ),
                listOf(
                    3, 2, 3, 3, 3, 1, 3, 0, 0, 0, 3, 1, 0, 3, 2, 3, 2, 0, 3, 0, 1, 3, 1, 1, 2, 3, 1, 2, 1, 3, 3, 0, 3, 3, 2, 0, 1, 1, 0, 3
                ),
                listOf(
                    3, 0, 3, 2, 1, 0, 1, 3, 0, 3, 2, 3, 1, 1, 0, 2, 3, 0, 2, 3, 3, 2, 3, 3, 3, 0, 2, 0, 1, 2, 3, 0, 1, 0, 2, 3, 2, 3, 1, 0
                ),
                listOf(
                    0, 0, 0, 0, 3, 0, 1, 2, 1, 3, 2, 2, 3, 1, 2, 1, 3, 0, 1, 3, 0, 3, 0, 3, 1, 3, 0, 2, 1, 0, 3, 2, 1, 0, 0, 1, 3, 1, 2, 3
                ),
                listOf(
                    0, 3, 3, 2, 1, 0, 2, 0, 0, 0, 3, 2, 0, 0, 2, 0, 2, 2, 1, 3, 2, 2, 2, 0, 0, 2, 3, 2, 2, 3, 0, 0, 2, 3, 2, 1, 3, 0, 3, 1
                ),
                listOf(
                    2, 3, 0, 3, 2, 2, 3, 1, 2, 2, 1, 2, 2, 3, 3, 2, 2, 3, 0, 3, 2, 3, 1, 1, 0, 2, 1, 1, 1, 2, 0, 0, 1, 1, 0, 2, 3, 1, 1, 0
                ),
                listOf(
                    3, 2, 2, 2, 0, 2, 0, 0, 0, 2, 3, 3, 2, 2, 0, 1, 1, 2, 0, 3, 0, 3, 0, 0, 1, 1, 0, 3, 0, 3, 2, 3, 1, 0, 1, 1, 0, 3, 1, 1
                ),
                listOf(
                    2, 0, 0, 0, 1, 3, 3, 3, 2, 0, 3, 1, 0, 0, 3, 1, 0, 0, 1, 1, 0, 2, 3, 2, 1, 1, 1, 1, 3, 3, 0, 0, 1, 1, 2, 3, 1, 0, 1, 3
                ),
                listOf(
                    3, 2, 0, 3, 0, 1, 3, 2, 0, 1, 2, 1, 2, 1, 2, 3, 2, 0, 0, 1, 2, 0, 1, 0, 3, 3, 1, 1, 0, 0, 2, 2, 1, 0, 1, 3, 0, 1, 3, 3
                ),
                listOf(
                    1, 0, 0, 3, 2, 0, 3, 3, 1, 3, 0, 1, 2, 2, 2, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 3, 1, 2, 1, 0, 0, 1, 0, 0, 2, 3, 2, 3, 3, 0
                ),
                listOf(
                    3, 1, 0, 2, 3, 0, 0, 0, 0, 2, 3, 3, 2, 3, 0, 3, 1, 3, 2, 0, 0, 3, 3, 3, 1, 1, 2, 0, 1, 0, 1, 3, 0, 3, 0, 0, 1, 2, 1, 3
                )
            ), listOf(
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 25),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 25),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk93(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_014", "Legendary", 40, 60, listOf(
                listOf(
                    2, 2, 1, 3, 1, 1, 2, 3, 0, 1, 2, 0, 3, 3, 0, 3, 2, 3, 1, 3, 1, 3, 1, 3, 2, 2, 0, 0, 2, 2, 3, 2, 3, 3, 0, 3, 1, 2, 0, 2
                ),
                listOf(
                    0, 1, 0, 1, 3, 1, 1, 1, 0, 3, 1, 1, 1, 3, 0, 1, 1, 3, 3, 1, 1, 3, 2, 3, 3, 2, 0, 2, 2, 3, 2, 0, 2, 0, 2, 3, 0, 1, 1, 1
                ),
                listOf(
                    0, 3, 0, 3, 3, 3, 0, 1, 0, 2, 1, 2, 1, 3, 2, 0, 1, 3, 1, 2, 1, 3, 3, 1, 2, 0, 1, 1, 3, 0, 0, 1, 3, 0, 2, 0, 3, 1, 0, 0
                ),
                listOf(
                    3, 2, 1, 3, 1, 0, 0, 0, 0, 2, 3, 2, 1, 3, 2, 1, 3, 0, 3, 1, 1, 3, 3, 0, 3, 3, 3, 3, 0, 2, 1, 3, 1, 3, 0, 1, 0, 0, 0, 3
                ),
                listOf(
                    0, 2, 3, 2, 3, 1, 3, 2, 3, 0, 1, 3, 1, 2, 2, 2, 2, 2, 1, 2, 3, 1, 0, 1, 3, 0, 2, 1, 0, 1, 1, 0, 3, 1, 3, 2, 2, 0, 0, 1
                ),
                listOf(
                    3, 2, 1, 0, 0, 3, 3, 2, 1, 0, 0, 2, 3, 1, 1, 2, 3, 1, 1, 2, 1, 3, 1, 0, 2, 3, 3, 2, 0, 0, 1, 0, 1, 1, 2, 2, 3, 2, 3, 1
                ),
                listOf(
                    1, 0, 2, 1, 1, 0, 0, 3, 1, 0, 2, 1, 3, 0, 2, 1, 2, 1, 0, 1, 3, 2, 2, 3, 1, 0, 0, 1, 3, 1, 0, 2, 1, 1, 3, 3, 2, 3, 0, 3
                ),
                listOf(
                    3, 2, 2, 0, 0, 0, 1, 2, 1, 2, 1, 3, 2, 2, 2, 0, 0, 3, 3, 3, 0, 0, 2, 3, 2, 2, 0, 1, 0, 3, 1, 3, 3, 1, 2, 0, 3, 3, 0, 3
                ),
                listOf(
                    1, 0, 1, 0, 3, 1, 0, 2, 1, 1, 0, 2, 1, 1, 3, 1, 2, 1, 3, 1, 1, 3, 0, 0, 1, 3, 3, 1, 1, 0, 3, 2, 1, 2, 2, 2, 0, 1, 2, 1
                ),
                listOf(
                    2, 3, 3, 0, 1, 0, 2, 1, 0, 0, 3, 0, 1, 1, 0, 3, 1, 1, 2, 1, 1, 0, 3, 1, 1, 0, 2, 1, 3, 1, 1, 1, 0, 0, 2, 1, 3, 1, 2, 1
                ),
                listOf(
                    0, 0, 1, 0, 0, 1, 1, 3, 1, 2, 0, 3, 0, 1, 3, 3, 2, 3, 3, 1, 1, 3, 0, 2, 3, 0, 1, 0, 3, 2, 1, 1, 3, 0, 2, 1, 1, 1, 2, 1
                ),
                listOf(
                    0, 0, 1, 0, 1, 2, 2, 1, 3, 2, 0, 2, 1, 3, 1, 1, 0, 3, 2, 3, 0, 3, 2, 0, 1, 2, 0, 2, 1, 1, 0, 0, 1, 2, 0, 1, 0, 1, 0, 3
                ),
                listOf(
                    2, 1, 1, 2, 0, 1, 3, 1, 3, 1, 3, 3, 0, 1, 2, 0, 1, 1, 2, 2, 3, 0, 1, 2, 2, 1, 0, 1, 3, 1, 3, 0, 1, 3, 2, 1, 2, 2, 1, 2
                ),
                listOf(
                    0, 0, 2, 1, 0, 2, 3, 3, 3, 1, 0, 1, 1, 2, 3, 0, 1, 1, 3, 3, 1, 3, 0, 0, 3, 0, 3, 1, 3, 1, 3, 2, 2, 0, 0, 0, 1, 0, 0, 1
                ),
                listOf(
                    0, 3, 1, 0, 2, 2, 2, 1, 2, 3, 1, 0, 3, 2, 3, 1, 1, 1, 0, 2, 2, 3, 1, 0, 2, 2, 3, 1, 1, 0, 0, 0, 0, 0, 3, 2, 1, 2, 3, 2
                ),
                listOf(
                    0, 2, 0, 0, 2, 2, 1, 0, 1, 3, 2, 3, 1, 2, 1, 3, 2, 0, 3, 0, 0, 2, 1, 3, 3, 3, 0, 0, 3, 1, 0, 1, 2, 1, 3, 3, 1, 1, 1, 1
                ),
                listOf(
                    0, 3, 2, 2, 2, 1, 1, 2, 0, 0, 1, 1, 0, 0, 3, 1, 1, 3, 0, 0, 1, 3, 2, 1, 2, 1, 0, 3, 0, 3, 3, 1, 1, 3, 0, 1, 2, 2, 1, 1
                ),
                listOf(
                    0, 0, 2, 1, 0, 2, 1, 1, 1, 3, 1, 3, 1, 0, 2, 2, 1, 0, 2, 2, 0, 2, 2, 3, 3, 1, 0, 1, 0, 2, 2, 0, 3, 0, 3, 1, 0, 3, 2, 3
                ),
                listOf(
                    1, 0, 2, 0, 2, 1, 3, 3, 1, 2, 1, 0, 0, 2, 3, 3, 1, 3, 3, 0, 3, 3, 1, 1, 3, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 3, 3, 2, 0, 3
                ),
                listOf(
                    3, 0, 3, 1, 1, 1, 2, 0, 1, 2, 0, 2, 2, 2, 2, 3, 0, 2, 3, 0, 2, 0, 3, 0, 3, 1, 1, 0, 0, 2, 1, 0, 2, 2, 3, 2, 3, 1, 0, 0
                ),
                listOf(
                    0, 2, 1, 0, 2, 1, 0, 3, 0, 0, 3, 2, 1, 1, 0, 2, 2, 2, 0, 2, 3, 0, 3, 3, 3, 3, 2, 2, 2, 2, 3, 1, 1, 1, 1, 1, 3, 1, 2, 3
                ),
                listOf(
                    2, 0, 1, 0, 3, 3, 2, 3, 1, 3, 2, 1, 0, 3, 1, 0, 2, 3, 1, 1, 3, 0, 0, 3, 2, 0, 0, 2, 1, 0, 0, 1, 3, 0, 1, 2, 3, 0, 1, 0
                ),
                listOf(
                    3, 0, 0, 2, 2, 1, 0, 0, 0, 2, 3, 0, 2, 1, 1, 3, 1, 3, 1, 3, 1, 2, 1, 1, 2, 2, 3, 2, 1, 3, 2, 0, 1, 0, 3, 2, 0, 1, 0, 3
                ),
                listOf(
                    1, 1, 2, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 3, 1, 3, 2, 1, 2, 0, 0, 0, 3, 1, 1, 1, 1, 0, 3, 3, 3, 3, 1, 0, 2, 2, 0
                ),
                listOf(
                    3, 3, 2, 1, 1, 1, 0, 3, 1, 2, 0, 3, 3, 2, 1, 2, 1, 0, 0, 1, 1, 2, 1, 2, 3, 0, 0, 2, 0, 3, 1, 1, 0, 3, 1, 2, 0, 3, 2, 2
                ),
                listOf(
                    2, 1, 1, 1, 3, 0, 0, 1, 1, 0, 2, 2, 0, 0, 1, 3, 3, 3, 3, 2, 3, 2, 0, 1, 3, 1, 2, 3, 3, 0, 1, 1, 2, 3, 0, 0, 1, 0, 0, 3
                ),
                listOf(
                    0, 3, 1, 2, 2, 0, 2, 0, 0, 3, 3, 3, 1, 0, 1, 2, 3, 1, 0, 1, 2, 0, 1, 3, 2, 3, 0, 3, 0, 3, 2, 0, 0, 0, 3, 2, 0, 2, 3, 2
                ),
                listOf(
                    3, 3, 1, 3, 1, 3, 0, 1, 0, 2, 0, 3, 1, 1, 1, 0, 0, 2, 1, 0, 0, 3, 2, 0, 1, 1, 1, 1, 2, 3, 0, 0, 0, 1, 0, 3, 3, 2, 1, 1
                ),
                listOf(
                    0, 1, 1, 1, 2, 1, 1, 1, 1, 0, 2, 0, 3, 1, 3, 0, 3, 0, 3, 1, 0, 0, 0, 2, 2, 3, 0, 1, 1, 0, 3, 0, 3, 3, 2, 3, 3, 2, 1, 2
                ),
                listOf(
                    3, 0, 1, 2, 3, 3, 3, 1, 3, 3, 3, 2, 1, 1, 3, 1, 0, 2, 0, 1, 1, 0, 3, 2, 1, 3, 0, 3, 1, 3, 0, 2, 3, 2, 1, 3, 0, 3, 1, 1
                ),
                listOf(
                    1, 1, 2, 2, 1, 1, 1, 3, 3, 2, 3, 3, 1, 2, 3, 0, 2, 0, 2, 3, 1, 0, 1, 3, 3, 0, 2, 0, 1, 0, 3, 1, 1, 1, 1, 1, 2, 0, 0, 3
                ),
                listOf(
                    3, 0, 2, 2, 3, 3, 3, 3, 3, 2, 2, 3, 0, 2, 0, 3, 2, 1, 1, 3, 0, 1, 3, 2, 1, 1, 1, 0, 3, 1, 2, 0, 0, 1, 1, 3, 3, 3, 3, 2
                ),
                listOf(
                    2, 1, 3, 1, 2, 1, 1, 1, 0, 3, 3, 2, 0, 2, 0, 1, 2, 3, 2, 1, 3, 2, 1, 2, 1, 0, 0, 0, 2, 2, 0, 0, 0, 1, 0, 1, 2, 1, 2, 0
                ),
                listOf(
                    0, 3, 0, 3, 3, 1, 3, 3, 1, 3, 3, 3, 1, 1, 0, 3, 3, 0, 0, 0, 0, 1, 2, 0, 3, 3, 3, 3, 3, 0, 0, 1, 2, 0, 2, 1, 0, 3, 1, 3
                ),
                listOf(
                    2, 0, 0, 0, 1, 3, 2, 0, 3, 2, 2, 1, 2, 0, 2, 3, 1, 3, 2, 1, 2, 2, 1, 0, 1, 2, 1, 1, 1, 0, 1, 2, 2, 1, 0, 1, 0, 3, 0, 3
                ),
                listOf(
                    0, 3, 1, 3, 3, 2, 1, 1, 0, 0, 2, 3, 2, 1, 3, 0, 0, 0, 0, 3, 1, 1, 0, 2, 1, 0, 2, 2, 2, 0, 3, 2, 2, 3, 0, 3, 3, 1, 1, 2
                ),
                listOf(
                    0, 2, 3, 2, 3, 3, 1, 3, 1, 0, 0, 1, 2, 1, 2, 1, 0, 2, 0, 2, 0, 0, 0, 0, 1, 0, 0, 2, 2, 1, 1, 2, 1, 1, 3, 2, 0, 3, 1, 3
                ),
                listOf(
                    0, 2, 0, 1, 2, 3, 0, 0, 1, 0, 1, 3, 3, 2, 0, 2, 1, 2, 2, 0, 0, 3, 3, 0, 1, 3, 2, 3, 3, 3, 1, 0, 3, 3, 2, 0, 1, 1, 3, 0
                ),
                listOf(
                    3, 3, 0, 1, 2, 2, 1, 0, 0, 1, 2, 1, 0, 1, 1, 1, 1, 0, 2, 0, 0, 1, 1, 2, 2, 3, 2, 0, 1, 1, 2, 0, 3, 2, 1, 1, 2, 3, 0, 3
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 3, 2, 0, 3, 1, 1, 3, 2, 3, 2, 0, 2, 1, 0, 0, 3, 0, 2, 1, 2, 3, 3, 3, 0, 1, 2, 0, 3, 1, 2, 0, 0, 0, 1
                ),
                listOf(
                    3, 1, 2, 0, 2, 2, 0, 0, 2, 3, 2, 1, 1, 3, 1, 0, 0, 1, 0, 3, 0, 0, 3, 0, 1, 0, 0, 3, 1, 3, 2, 3, 3, 0, 0, 1, 2, 2, 1, 3
                ),
                listOf(
                    2, 0, 3, 3, 1, 3, 3, 3, 3, 1, 0, 2, 0, 2, 3, 1, 1, 1, 2, 0, 3, 1, 1, 3, 0, 3, 3, 0, 1, 2, 2, 2, 3, 3, 3, 2, 0, 1, 2, 1
                ),
                listOf(
                    3, 3, 0, 2, 1, 0, 3, 0, 3, 0, 0, 0, 0, 1, 2, 2, 3, 2, 3, 3, 1, 2, 0, 2, 1, 1, 0, 1, 0, 1, 1, 3, 2, 0, 1, 2, 0, 2, 0, 0
                ),
                listOf(
                    1, 3, 0, 1, 3, 1, 0, 3, 1, 3, 1, 0, 0, 1, 3, 0, 2, 0, 3, 2, 1, 0, 3, 0, 0, 3, 2, 2, 3, 1, 2, 3, 2, 0, 2, 3, 2, 3, 0, 3
                ),
                listOf(
                    1, 3, 1, 0, 3, 1, 1, 1, 2, 0, 2, 1, 0, 3, 3, 3, 0, 0, 3, 3, 2, 2, 0, 2, 2, 1, 1, 2, 3, 0, 1, 3, 1, 1, 0, 3, 0, 3, 3, 2
                ),
                listOf(
                    0, 3, 2, 1, 0, 0, 2, 1, 3, 0, 0, 3, 1, 3, 3, 2, 3, 2, 3, 3, 1, 0, 0, 3, 2, 3, 1, 1, 1, 3, 2, 0, 1, 3, 2, 1, 0, 3, 2, 1
                ),
                listOf(
                    0, 0, 2, 3, 3, 0, 2, 1, 1, 2, 2, 1, 3, 0, 0, 3, 2, 1, 1, 1, 0, 3, 3, 2, 2, 3, 1, 3, 3, 1, 2, 1, 3, 0, 1, 2, 3, 0, 1, 0
                ),
                listOf(
                    0, 3, 1, 3, 1, 0, 2, 1, 3, 1, 1, 3, 2, 3, 2, 2, 2, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 2, 0, 2, 3, 2, 2, 2, 1, 3, 0, 1
                ),
                listOf(
                    3, 2, 1, 1, 1, 3, 1, 1, 2, 1, 1, 2, 0, 0, 0, 3, 0, 1, 3, 0, 1, 1, 1, 3, 3, 0, 3, 1, 1, 2, 1, 1, 0, 0, 2, 0, 2, 0, 3, 2
                ),
                listOf(
                    2, 1, 2, 1, 2, 1, 1, 3, 3, 3, 2, 2, 1, 3, 1, 3, 0, 1, 2, 1, 1, 2, 3, 0, 0, 2, 0, 1, 2, 3, 0, 1, 0, 1, 2, 3, 1, 3, 3, 2
                ),
                listOf(
                    0, 3, 1, 2, 1, 1, 2, 0, 2, 1, 3, 2, 0, 3, 0, 0, 3, 1, 1, 2, 0, 1, 0, 2, 1, 1, 1, 0, 1, 1, 1, 0, 3, 0, 2, 1, 0, 0, 1, 0
                ),
                listOf(
                    1, 1, 2, 1, 2, 0, 3, 0, 2, 2, 0, 3, 1, 3, 0, 2, 1, 3, 1, 3, 2, 3, 1, 2, 0, 2, 1, 3, 0, 3, 2, 3, 0, 0, 0, 3, 2, 1, 0, 2
                ),
                listOf(
                    2, 0, 0, 2, 0, 1, 1, 0, 0, 0, 2, 3, 2, 2, 2, 2, 1, 3, 3, 1, 2, 0, 2, 3, 0, 2, 2, 1, 3, 3, 2, 0, 2, 1, 1, 0, 3, 0, 0, 2
                ),
                listOf(
                    2, 3, 1, 0, 1, 2, 2, 3, 1, 3, 1, 1, 0, 0, 2, 3, 0, 0, 0, 1, 2, 3, 3, 2, 2, 2, 3, 3, 1, 1, 1, 2, 0, 2, 2, 3, 0, 3, 1, 2
                ),
                listOf(
                    0, 1, 3, 2, 0, 3, 1, 2, 0, 3, 2, 2, 2, 2, 1, 1, 0, 2, 0, 3, 1, 2, 1, 1, 2, 2, 1, 2, 0, 2, 2, 1, 3, 3, 3, 0, 3, 2, 2, 0
                ),
                listOf(
                    2, 2, 2, 1, 3, 1, 0, 1, 1, 3, 0, 0, 3, 2, 3, 2, 2, 0, 1, 0, 1, 0, 3, 2, 1, 3, 0, 3, 3, 3, 2, 1, 1, 3, 2, 2, 2, 0, 3, 2
                ),
                listOf(
                    0, 2, 0, 1, 1, 2, 1, 3, 2, 1, 2, 2, 1, 0, 2, 1, 2, 1, 0, 3, 1, 3, 2, 1, 2, 3, 3, 2, 3, 2, 1, 1, 2, 3, 2, 3, 3, 1, 1, 3
                ),
                listOf(
                    1, 0, 0, 1, 3, 2, 0, 0, 3, 0, 1, 0, 1, 1, 1, 2, 3, 3, 0, 0, 1, 3, 2, 2, 1, 2, 2, 3, 1, 2, 2, 0, 2, 0, 3, 2, 3, 3, 3, 1
                ),
                listOf(
                    1, 2, 2, 3, 0, 1, 2, 3, 1, 0, 0, 1, 1, 2, 3, 2, 1, 0, 2, 2, 1, 2, 2, 1, 1, 1, 0, 1, 2, 0, 3, 0, 1, 0, 0, 3, 0, 2, 3, 3
                ),
                listOf(
                    1, 1, 2, 2, 0, 3, 2, 2, 3, 2, 0, 3, 3, 3, 0, 0, 3, 0, 3, 2, 1, 0, 3, 3, 0, 1, 0, 2, 3, 0, 1, 1, 2, 1, 1, 0, 0, 2, 2, 3
                )
            ), listOf(
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk94(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_015", "Legendary", 40, 60, listOf(
                listOf(
                    0, 1, 0, 3, 3, 0, 1, 1, 2, 0, 0, 3, 3, 2, 1, 2, 2, 0, 2, 2, 0, 2, 2, 1, 1, 1, 1, 2, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 3
                ),
                listOf(
                    3, 1, 2, 0, 3, 0, 0, 3, 2, 0, 2, 0, 0, 2, 1, 1, 0, 2, 0, 0, 1, 2, 2, 2, 0, 3, 1, 3, 0, 2, 2, 3, 0, 3, 1, 0, 0, 3, 3, 0
                ),
                listOf(
                    1, 3, 3, 3, 0, 3, 1, 2, 2, 1, 0, 3, 1, 2, 1, 2, 0, 3, 1, 1, 2, 0, 2, 0, 3, 3, 2, 2, 0, 1, 3, 3, 0, 3, 2, 0, 2, 0, 0, 1
                ),
                listOf(
                    0, 2, 0, 3, 0, 3, 2, 0, 0, 3, 3, 2, 3, 2, 0, 0, 2, 3, 3, 0, 3, 0, 1, 3, 0, 2, 1, 2, 0, 1, 2, 2, 2, 1, 1, 3, 0, 2, 0, 3
                ),
                listOf(
                    1, 2, 0, 1, 2, 1, 0, 1, 0, 2, 0, 2, 3, 1, 1, 1, 1, 2, 3, 0, 3, 1, 0, 0, 3, 1, 2, 0, 2, 1, 1, 1, 2, 2, 0, 1, 3, 1, 2, 0
                ),
                listOf(
                    1, 1, 2, 2, 3, 0, 3, 2, 1, 0, 0, 1, 1, 2, 0, 1, 1, 1, 2, 1, 0, 3, 1, 3, 1, 0, 2, 1, 3, 2, 3, 0, 0, 0, 3, 3, 0, 1, 1, 3
                ),
                listOf(
                    3, 3, 2, 1, 3, 0, 0, 2, 0, 3, 3, 1, 0, 2, 3, 1, 1, 0, 3, 2, 2, 1, 2, 3, 1, 0, 1, 2, 2, 1, 2, 2, 1, 1, 3, 3, 3, 1, 0, 2
                ),
                listOf(
                    2, 1, 2, 2, 0, 2, 1, 2, 1, 0, 1, 3, 0, 0, 0, 0, 2, 3, 2, 2, 0, 1, 0, 3, 2, 1, 1, 0, 2, 2, 0, 0, 3, 3, 2, 2, 1, 2, 0, 0
                ),
                listOf(
                    2, 2, 0, 1, 0, 0, 0, 2, 1, 1, 3, 2, 2, 0, 0, 2, 0, 2, 3, 3, 2, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 2, 1, 0, 0, 1, 3, 2, 0
                ),
                listOf(
                    0, 3, 2, 0, 0, 0, 1, 0, 2, 2, 3, 0, 3, 3, 0, 2, 2, 1, 0, 2, 2, 2, 3, 2, 0, 0, 2, 2, 0, 3, 2, 2, 0, 1, 1, 2, 2, 1, 1, 0
                ),
                listOf(
                    3, 0, 1, 1, 2, 2, 0, 3, 0, 2, 1, 1, 3, 0, 0, 0, 3, 0, 0, 2, 3, 3, 3, 2, 1, 3, 0, 3, 2, 3, 2, 3, 3, 1, 2, 0, 3, 3, 1, 0
                ),
                listOf(
                    0, 0, 2, 0, 3, 1, 1, 2, 0, 2, 0, 2, 2, 0, 2, 2, 3, 0, 0, 1, 2, 3, 0, 1, 2, 2, 2, 2, 2, 1, 1, 2, 0, 3, 2, 2, 1, 3, 0, 3
                ),
                listOf(
                    3, 3, 0, 1, 2, 0, 2, 3, 3, 3, 0, 2, 3, 0, 3, 2, 3, 0, 3, 0, 2, 1, 2, 0, 0, 3, 3, 3, 1, 1, 1, 3, 1, 2, 2, 1, 0, 0, 1, 3
                ),
                listOf(
                    3, 0, 1, 0, 1, 1, 1, 0, 3, 3, 1, 3, 0, 0, 0, 3, 1, 2, 1, 1, 0, 0, 2, 2, 2, 3, 3, 1, 2, 0, 1, 0, 0, 0, 0, 2, 3, 0, 0, 1
                ),
                listOf(
                    0, 1, 2, 0, 2, 0, 3, 2, 3, 2, 0, 3, 0, 3, 0, 2, 0, 0, 0, 0, 3, 0, 2, 1, 1, 1, 0, 3, 3, 2, 3, 2, 0, 1, 3, 0, 3, 2, 3, 2
                ),
                listOf(
                    2, 3, 0, 0, 0, 1, 2, 0, 2, 3, 2, 0, 0, 3, 3, 1, 0, 0, 2, 0, 3, 2, 1, 3, 3, 2, 2, 0, 1, 3, 1, 0, 0, 2, 2, 2, 3, 0, 0, 2
                ),
                listOf(
                    3, 2, 2, 1, 3, 2, 1, 3, 0, 0, 1, 0, 3, 2, 2, 1, 0, 0, 2, 0, 1, 1, 2, 1, 2, 2, 0, 0, 3, 2, 2, 1, 3, 0, 1, 3, 1, 1, 1, 2
                ),
                listOf(
                    0, 2, 1, 0, 3, 3, 1, 0, 3, 0, 2, 3, 3, 2, 2, 2, 1, 2, 0, 1, 2, 0, 1, 1, 3, 2, 1, 2, 3, 3, 1, 3, 3, 3, 0, 1, 1, 1, 1, 3
                ),
                listOf(
                    3, 3, 2, 2, 3, 2, 1, 0, 0, 0, 2, 0, 2, 0, 1, 1, 1, 1, 1, 1, 2, 1, 0, 3, 0, 3, 3, 0, 1, 1, 2, 1, 2, 2, 3, 1, 0, 1, 2, 3
                ),
                listOf(
                    2, 2, 0, 3, 0, 2, 2, 1, 2, 3, 1, 3, 2, 1, 3, 0, 1, 3, 1, 1, 1, 3, 3, 0, 3, 0, 0, 1, 2, 1, 3, 2, 2, 1, 1, 3, 1, 0, 3, 0
                ),
                listOf(
                    0, 0, 2, 3, 3, 0, 3, 3, 0, 0, 0, 3, 1, 2, 2, 1, 1, 0, 2, 2, 0, 1, 1, 3, 3, 3, 2, 1, 1, 0, 2, 1, 1, 0, 2, 2, 0, 3, 0, 1
                ),
                listOf(
                    3, 3, 0, 1, 3, 1, 1, 3, 1, 1, 0, 3, 2, 3, 1, 0, 3, 0, 1, 0, 3, 3, 3, 2, 1, 2, 2, 0, 0, 0, 0, 1, 2, 1, 1, 3, 1, 1, 2, 0
                ),
                listOf(
                    0, 3, 2, 2, 0, 0, 1, 3, 2, 1, 2, 3, 1, 1, 2, 0, 1, 3, 1, 1, 2, 3, 3, 3, 0, 3, 0, 3, 2, 1, 1, 0, 3, 2, 3, 3, 3, 3, 2, 1
                ),
                listOf(
                    2, 2, 1, 0, 3, 0, 2, 2, 1, 3, 2, 3, 3, 3, 2, 2, 3, 1, 1, 0, 0, 0, 1, 1, 1, 2, 3, 1, 0, 1, 1, 3, 3, 3, 1, 0, 1, 2, 2, 1
                ),
                listOf(
                    1, 2, 0, 2, 2, 2, 1, 3, 1, 3, 2, 2, 1, 2, 3, 2, 2, 2, 3, 3, 1, 3, 3, 2, 2, 2, 3, 2, 1, 0, 3, 2, 0, 3, 0, 1, 2, 1, 2, 3
                ),
                listOf(
                    1, 3, 3, 3, 2, 3, 1, 1, 3, 0, 1, 1, 1, 0, 3, 3, 0, 0, 1, 3, 1, 3, 2, 0, 1, 2, 1, 3, 2, 1, 3, 3, 1, 1, 0, 0, 0, 3, 0, 3
                ),
                listOf(
                    1, 0, 0, 1, 0, 0, 2, 3, 0, 3, 1, 2, 3, 1, 0, 3, 0, 3, 2, 1, 2, 2, 1, 0, 3, 2, 0, 3, 0, 2, 3, 1, 1, 1, 3, 1, 2, 1, 3, 3
                ),
                listOf(
                    3, 0, 0, 2, 1, 1, 3, 3, 0, 3, 0, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 1, 3, 0, 1, 0, 2, 1, 1, 0, 0, 0, 2, 3, 0, 1, 3, 3, 0, 0
                ),
                listOf(
                    1, 1, 0, 3, 3, 2, 2, 0, 2, 2, 3, 1, 1, 2, 2, 2, 3, 3, 1, 3, 0, 0, 3, 0, 2, 3, 1, 1, 3, 2, 1, 2, 0, 0, 1, 2, 3, 1, 0, 1
                ),
                listOf(
                    0, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 0, 3, 1, 0, 3, 3, 2, 3, 0, 2, 0, 1, 1, 2, 0, 0, 3, 3, 1, 0, 2, 2, 0, 1, 2, 0, 2, 2, 2
                ),
                listOf(
                    3, 2, 2, 0, 1, 0, 2, 0, 2, 3, 1, 1, 0, 1, 3, 2, 2, 2, 2, 3, 1, 2, 0, 1, 2, 3, 1, 0, 3, 1, 1, 1, 2, 3, 3, 0, 3, 0, 2, 3
                ),
                listOf(
                    1, 3, 0, 1, 2, 0, 3, 0, 3, 2, 0, 0, 0, 1, 3, 2, 3, 0, 1, 1, 3, 2, 2, 3, 1, 2, 1, 0, 2, 2, 1, 0, 0, 1, 1, 0, 2, 1, 0, 2
                ),
                listOf(
                    3, 2, 1, 3, 3, 3, 2, 0, 3, 1, 0, 1, 1, 1, 3, 0, 2, 2, 2, 1, 1, 0, 3, 3, 3, 2, 0, 1, 1, 2, 3, 3, 2, 1, 1, 1, 2, 2, 2, 0
                ),
                listOf(
                    1, 2, 2, 2, 0, 0, 1, 3, 3, 2, 0, 3, 3, 1, 2, 2, 1, 1, 3, 2, 0, 3, 1, 2, 3, 0, 2, 0, 2, 1, 2, 3, 2, 1, 1, 2, 0, 2, 0, 0
                ),
                listOf(
                    3, 0, 2, 1, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1, 0, 2, 2, 3, 1, 3, 0, 0, 0, 0, 0, 0, 2, 1, 3, 0, 2, 3, 0, 0, 1, 2, 0, 3, 2
                ),
                listOf(
                    2, 1, 3, 3, 1, 1, 1, 0, 2, 1, 3, 3, 1, 0, 3, 2, 3, 2, 2, 0, 3, 1, 1, 3, 2, 1, 0, 1, 2, 3, 1, 1, 2, 1, 1, 1, 0, 3, 0, 0
                ),
                listOf(
                    2, 0, 0, 2, 3, 2, 2, 2, 0, 1, 2, 0, 2, 2, 3, 2, 2, 1, 3, 0, 1, 2, 3, 2, 3, 2, 2, 3, 2, 1, 0, 2, 0, 1, 1, 1, 0, 1, 3, 3
                ),
                listOf(
                    0, 0, 3, 0, 1, 3, 0, 3, 2, 1, 3, 0, 2, 1, 2, 3, 0, 2, 0, 2, 3, 2, 3, 1, 3, 2, 2, 2, 3, 0, 2, 3, 2, 0, 2, 0, 3, 2, 2, 3
                ),
                listOf(
                    0, 2, 3, 0, 2, 0, 3, 0, 0, 0, 3, 0, 0, 3, 2, 3, 2, 3, 0, 3, 1, 0, 3, 0, 3, 1, 1, 2, 1, 1, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0
                ),
                listOf(
                    3, 1, 3, 0, 0, 2, 1, 1, 2, 0, 3, 0, 1, 1, 0, 2, 2, 2, 3, 3, 3, 3, 0, 2, 3, 3, 1, 1, 2, 1, 1, 0, 2, 2, 0, 3, 1, 1, 2, 2
                ),
                listOf(
                    3, 2, 0, 1, 3, 2, 1, 1, 2, 3, 3, 0, 3, 2, 0, 0, 2, 3, 0, 3, 2, 2, 2, 0, 2, 2, 3, 2, 0, 2, 3, 0, 2, 0, 3, 0, 0, 1, 1, 1
                ),
                listOf(
                    2, 0, 3, 3, 0, 0, 2, 1, 0, 2, 0, 2, 1, 0, 0, 1, 0, 1, 0, 1, 3, 2, 2, 0, 3, 2, 3, 0, 1, 0, 0, 2, 2, 3, 2, 0, 2, 1, 3, 2
                ),
                listOf(
                    1, 1, 2, 1, 0, 2, 0, 0, 2, 0, 1, 1, 1, 3, 0, 1, 3, 2, 3, 2, 2, 0, 0, 0, 2, 2, 3, 3, 3, 0, 2, 3, 0, 1, 1, 0, 1, 1, 3, 1
                ),
                listOf(
                    3, 1, 3, 0, 0, 1, 0, 0, 1, 1, 0, 1, 3, 2, 1, 0, 3, 1, 2, 1, 3, 3, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2, 2, 0, 2, 1, 3, 3, 1, 0
                ),
                listOf(
                    3, 3, 1, 3, 3, 3, 0, 0, 1, 2, 0, 1, 3, 1, 1, 2, 3, 3, 3, 1, 2, 1, 0, 2, 2, 1, 2, 0, 0, 1, 3, 0, 1, 2, 1, 1, 1, 1, 2, 1
                ),
                listOf(
                    1, 1, 1, 1, 1, 1, 3, 0, 2, 0, 3, 3, 2, 1, 3, 3, 0, 0, 1, 2, 3, 2, 2, 0, 1, 1, 2, 2, 2, 2, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2
                ),
                listOf(
                    0, 2, 1, 2, 1, 0, 0, 0, 0, 0, 2, 0, 2, 3, 0, 3, 0, 1, 3, 0, 0, 3, 0, 1, 1, 2, 3, 1, 1, 3, 3, 0, 2, 0, 0, 0, 3, 3, 3, 1
                ),
                listOf(
                    3, 2, 1, 3, 3, 3, 1, 3, 1, 0, 3, 1, 0, 1, 2, 2, 3, 0, 3, 1, 2, 2, 1, 1, 3, 1, 1, 3, 3, 1, 3, 2, 3, 2, 0, 0, 0, 0, 0, 2
                ),
                listOf(
                    2, 2, 2, 2, 0, 1, 3, 0, 3, 1, 1, 0, 2, 1, 3, 0, 3, 3, 1, 0, 2, 1, 1, 2, 1, 2, 3, 3, 3, 3, 1, 3, 0, 1, 2, 0, 0, 0, 2, 0
                ),
                listOf(
                    2, 2, 3, 2, 2, 3, 0, 1, 2, 1, 1, 3, 3, 3, 0, 0, 0, 1, 2, 2, 0, 0, 0, 3, 1, 1, 2, 2, 1, 2, 3, 0, 0, 2, 2, 0, 3, 3, 3, 3
                ),
                listOf(
                    2, 3, 2, 0, 3, 2, 2, 2, 1, 0, 0, 2, 0, 1, 3, 0, 1, 2, 2, 0, 2, 1, 0, 0, 0, 3, 2, 1, 1, 0, 0, 3, 1, 2, 2, 1, 0, 3, 3, 0
                ),
                listOf(
                    3, 2, 3, 2, 1, 3, 2, 0, 0, 2, 0, 2, 0, 0, 0, 0, 1, 3, 3, 0, 0, 0, 2, 1, 2, 3, 1, 1, 1, 2, 0, 3, 0, 2, 2, 0, 1, 3, 0, 1
                ),
                listOf(
                    3, 2, 2, 0, 2, 3, 3, 1, 2, 2, 1, 1, 1, 3, 3, 2, 1, 1, 1, 1, 0, 3, 3, 1, 1, 1, 0, 1, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 0, 1
                ),
                listOf(
                    1, 3, 3, 3, 0, 0, 0, 2, 1, 0, 0, 3, 0, 0, 1, 2, 1, 0, 2, 0, 0, 2, 1, 0, 2, 3, 1, 1, 2, 0, 3, 0, 0, 3, 0, 2, 3, 2, 2, 0
                ),
                listOf(
                    2, 1, 0, 0, 0, 1, 1, 0, 0, 1, 2, 2, 1, 0, 2, 2, 2, 2, 1, 0, 0, 3, 0, 2, 1, 3, 3, 2, 1, 2, 0, 1, 2, 2, 3, 3, 2, 0, 0, 1
                ),
                listOf(
                    3, 3, 3, 3, 2, 2, 3, 2, 3, 3, 0, 2, 1, 1, 0, 0, 0, 3, 3, 0, 0, 0, 2, 0, 1, 2, 3, 0, 3, 2, 2, 3, 2, 0, 1, 3, 1, 2, 3, 2
                ),
                listOf(
                    0, 2, 3, 3, 3, 3, 0, 1, 0, 2, 3, 2, 1, 2, 1, 1, 2, 3, 0, 0, 2, 0, 1, 3, 1, 0, 1, 2, 3, 3, 3, 2, 3, 1, 2, 2, 1, 2, 2, 1
                ),
                listOf(
                    2, 0, 0, 3, 1, 2, 3, 2, 0, 2, 1, 1, 1, 3, 1, 3, 0, 2, 0, 3, 1, 0, 1, 1, 2, 1, 0, 0, 0, 2, 0, 3, 1, 0, 2, 1, 2, 1, 0, 1
                ),
                listOf(
                    0, 0, 2, 2, 3, 0, 1, 2, 2, 3, 1, 0, 0, 1, 3, 1, 3, 0, 2, 2, 2, 2, 3, 0, 3, 2, 1, 0, 1, 3, 3, 3, 2, 2, 3, 3, 1, 2, 2, 2
                ),
                listOf(
                    0, 2, 1, 3, 2, 2, 1, 3, 3, 0, 2, 0, 1, 0, 0, 0, 2, 0, 3, 0, 2, 1, 1, 2, 0, 1, 2, 2, 1, 1, 3, 3, 1, 3, 3, 2, 0, 0, 0, 2
                )
            ), listOf(
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 25),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk95(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_016", "Legendary", 40, 60, listOf(
                listOf(
                    2, 2, 2, 1, 1, 0, 2, 2, 2, 3, 0, 1, 3, 2, 1, 3, 3, 3, 1, 3, 0, 3, 0, 0, 1, 0, 1, 2, 3, 0, 3, 2, 0, 1, 0, 2, 2, 1, 2, 2
                ),
                listOf(
                    1, 3, 1, 2, 3, 3, 2, 0, 3, 3, 1, 2, 1, 1, 1, 0, 0, 2, 1, 2, 2, 1, 2, 0, 3, 2, 0, 1, 3, 0, 1, 0, 3, 0, 2, 3, 1, 2, 0, 2
                ),
                listOf(
                    2, 1, 2, 0, 0, 2, 0, 3, 0, 0, 1, 3, 3, 2, 2, 2, 2, 1, 1, 0, 3, 0, 3, 1, 0, 2, 3, 1, 1, 1, 2, 1, 0, 0, 2, 0, 3, 2, 1, 2
                ),
                listOf(
                    1, 2, 3, 2, 1, 3, 0, 3, 0, 0, 1, 2, 3, 3, 3, 2, 1, 0, 0, 0, 0, 3, 3, 3, 1, 0, 3, 3, 3, 3, 1, 2, 2, 1, 2, 0, 3, 1, 3, 1
                ),
                listOf(
                    2, 0, 2, 2, 3, 1, 2, 2, 3, 3, 0, 2, 3, 0, 1, 3, 1, 1, 3, 0, 0, 0, 3, 3, 2, 2, 1, 1, 0, 3, 2, 3, 2, 0, 3, 3, 2, 1, 3, 1
                ),
                listOf(
                    1, 1, 0, 3, 3, 2, 3, 3, 0, 3, 0, 3, 0, 2, 2, 0, 3, 3, 3, 3, 0, 3, 0, 1, 0, 1, 0, 0, 0, 2, 3, 3, 0, 0, 2, 2, 2, 2, 3, 1
                ),
                listOf(
                    1, 2, 0, 0, 2, 2, 0, 1, 2, 3, 2, 0, 1, 3, 2, 1, 0, 2, 2, 2, 0, 2, 2, 1, 1, 0, 2, 3, 3, 3, 2, 2, 0, 0, 0, 3, 1, 1, 2, 2
                ),
                listOf(
                    1, 3, 3, 1, 0, 1, 1, 0, 0, 1, 2, 1, 0, 0, 1, 0, 0, 1, 0, 3, 0, 2, 2, 2, 2, 2, 3, 3, 0, 0, 3, 2, 2, 1, 2, 2, 2, 3, 1, 3
                ),
                listOf(
                    3, 0, 2, 3, 3, 0, 2, 2, 2, 3, 1, 1, 1, 2, 3, 2, 2, 2, 2, 3, 3, 0, 0, 1, 3, 0, 0, 3, 0, 2, 2, 3, 2, 1, 3, 0, 1, 2, 1, 0
                ),
                listOf(
                    0, 0, 1, 0, 0, 2, 1, 2, 3, 3, 1, 0, 3, 0, 3, 2, 3, 1, 0, 0, 2, 1, 3, 3, 0, 3, 3, 3, 1, 0, 3, 3, 3, 2, 3, 2, 3, 0, 2, 0
                ),
                listOf(
                    1, 1, 0, 3, 0, 0, 2, 0, 2, 3, 3, 2, 1, 3, 2, 1, 3, 3, 2, 1, 3, 3, 3, 3, 3, 2, 2, 2, 0, 3, 2, 3, 2, 2, 1, 2, 1, 3, 2, 1
                ),
                listOf(
                    3, 3, 1, 2, 0, 3, 0, 3, 1, 1, 3, 2, 0, 1, 2, 0, 2, 1, 0, 1, 2, 0, 1, 2, 0, 1, 1, 1, 3, 0, 0, 0, 0, 3, 0, 2, 3, 2, 0, 1
                ),
                listOf(
                    2, 0, 3, 2, 1, 0, 0, 1, 3, 1, 1, 0, 0, 2, 0, 3, 2, 0, 3, 1, 1, 3, 1, 3, 3, 3, 2, 0, 0, 1, 0, 3, 3, 3, 2, 0, 0, 2, 3, 1
                ),
                listOf(
                    1, 1, 0, 3, 2, 0, 0, 2, 3, 2, 3, 0, 3, 1, 2, 0, 2, 3, 1, 0, 1, 0, 3, 1, 3, 1, 3, 2, 2, 2, 0, 2, 0, 1, 2, 1, 0, 1, 0, 2
                ),
                listOf(
                    2, 2, 1, 3, 2, 2, 3, 0, 1, 1, 2, 0, 0, 2, 1, 3, 0, 0, 1, 1, 3, 3, 0, 0, 1, 2, 1, 2, 1, 0, 3, 1, 3, 2, 0, 3, 2, 3, 2, 0
                ),
                listOf(
                    3, 0, 0, 0, 2, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 2, 3, 1, 3, 2, 2, 3, 3, 2, 0, 1, 0, 3, 0, 2, 1, 0, 3, 3, 0, 1, 1, 0, 2
                ),
                listOf(
                    3, 0, 1, 0, 0, 0, 0, 1, 2, 0, 0, 0, 2, 1, 2, 0, 0, 3, 0, 0, 2, 2, 0, 1, 1, 1, 3, 3, 2, 0, 2, 3, 1, 1, 0, 1, 2, 1, 2, 3
                ),
                listOf(
                    0, 0, 1, 3, 1, 0, 2, 1, 2, 2, 3, 3, 2, 3, 2, 1, 3, 1, 3, 3, 0, 1, 1, 1, 2, 0, 0, 3, 2, 2, 2, 1, 1, 0, 0, 1, 0, 0, 2, 0
                ),
                listOf(
                    1, 2, 0, 0, 0, 3, 0, 3, 2, 0, 1, 3, 0, 0, 2, 0, 0, 1, 2, 3, 1, 3, 0, 0, 2, 1, 3, 1, 2, 2, 3, 1, 1, 0, 3, 2, 0, 1, 2, 1
                ),
                listOf(
                    2, 0, 1, 3, 3, 0, 1, 1, 3, 1, 1, 3, 1, 0, 1, 2, 1, 1, 1, 2, 0, 3, 3, 2, 0, 2, 3, 2, 2, 3, 1, 3, 1, 0, 0, 3, 2, 0, 3, 0
                ),
                listOf(
                    2, 1, 2, 3, 3, 0, 3, 0, 1, 2, 1, 2, 0, 0, 2, 0, 2, 2, 1, 2, 2, 2, 1, 2, 3, 3, 2, 3, 0, 0, 3, 0, 3, 3, 3, 0, 0, 1, 3, 1
                ),
                listOf(
                    3, 0, 0, 2, 0, 3, 2, 1, 2, 3, 1, 0, 1, 0, 1, 2, 0, 0, 1, 0, 2, 2, 2, 2, 3, 2, 2, 1, 2, 0, 1, 3, 0, 0, 3, 2, 2, 1, 3, 1
                ),
                listOf(
                    0, 1, 1, 2, 2, 3, 1, 0, 2, 3, 2, 1, 3, 1, 2, 2, 0, 0, 1, 1, 3, 1, 1, 2, 3, 0, 3, 2, 0, 3, 2, 0, 0, 3, 0, 0, 2, 1, 3, 2
                ),
                listOf(
                    1, 2, 0, 1, 0, 2, 3, 1, 0, 3, 1, 1, 1, 0, 3, 3, 2, 2, 2, 3, 1, 3, 2, 2, 3, 1, 3, 2, 0, 1, 1, 1, 3, 0, 0, 0, 3, 2, 0, 0
                ),
                listOf(
                    3, 1, 2, 3, 3, 2, 0, 3, 3, 0, 2, 1, 3, 0, 1, 3, 3, 2, 1, 0, 0, 1, 3, 1, 0, 3, 0, 2, 2, 1, 1, 1, 2, 2, 3, 0, 1, 1, 2, 0
                ),
                listOf(
                    0, 1, 2, 1, 0, 2, 2, 3, 3, 0, 3, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 2, 2, 3, 1, 3, 3, 1, 1, 3, 1, 1, 1, 3, 1, 3, 2, 2, 0, 3
                ),
                listOf(
                    1, 3, 0, 0, 2, 0, 3, 0, 3, 2, 2, 3, 1, 2, 0, 2, 0, 1, 1, 0, 0, 3, 2, 2, 3, 0, 1, 3, 1, 3, 3, 1, 3, 0, 3, 0, 3, 3, 0, 2
                ),
                listOf(
                    1, 2, 3, 0, 0, 3, 0, 3, 1, 3, 2, 1, 0, 2, 1, 1, 1, 1, 2, 2, 2, 0, 3, 1, 0, 3, 0, 1, 2, 0, 0, 3, 3, 1, 1, 3, 2, 2, 1, 1
                ),
                listOf(
                    0, 0, 0, 0, 3, 2, 0, 3, 2, 2, 3, 3, 0, 3, 2, 3, 0, 1, 0, 0, 3, 3, 2, 2, 0, 2, 3, 3, 1, 0, 0, 1, 2, 0, 2, 3, 2, 2, 2, 1
                ),
                listOf(
                    3, 3, 2, 2, 2, 3, 0, 3, 0, 2, 3, 1, 3, 3, 1, 2, 3, 0, 1, 3, 0, 2, 3, 3, 0, 1, 2, 3, 1, 0, 1, 0, 2, 2, 1, 3, 1, 1, 2, 3
                ),
                listOf(
                    2, 3, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 3, 0, 2, 3, 1, 2, 2, 2, 0, 2, 1, 0, 1, 0, 2, 3, 3, 1, 2, 0, 2, 3, 1, 1, 0, 0, 0, 1
                ),
                listOf(
                    1, 3, 3, 1, 0, 2, 2, 0, 0, 3, 3, 3, 0, 2, 0, 1, 3, 0, 2, 3, 1, 3, 2, 2, 2, 3, 3, 2, 1, 2, 2, 0, 3, 0, 3, 3, 2, 2, 3, 0
                ),
                listOf(
                    2, 2, 0, 1, 2, 0, 2, 0, 1, 2, 1, 1, 1, 0, 2, 2, 1, 0, 0, 1, 2, 3, 2, 0, 2, 3, 1, 3, 1, 0, 3, 3, 3, 2, 1, 2, 1, 3, 0, 1
                ),
                listOf(
                    3, 2, 3, 2, 1, 1, 0, 2, 0, 0, 2, 3, 3, 2, 0, 1, 1, 2, 2, 2, 3, 0, 3, 2, 1, 3, 3, 3, 3, 3, 0, 2, 0, 1, 3, 1, 2, 1, 0, 2
                ),
                listOf(
                    1, 3, 1, 2, 3, 1, 3, 3, 3, 2, 0, 0, 1, 3, 3, 1, 1, 3, 1, 3, 3, 3, 1, 1, 1, 1, 1, 1, 3, 0, 2, 3, 0, 3, 1, 3, 0, 0, 2, 2
                ),
                listOf(
                    2, 0, 2, 1, 3, 1, 3, 2, 1, 1, 2, 0, 0, 1, 0, 2, 3, 2, 1, 0, 0, 1, 0, 3, 3, 3, 1, 3, 0, 2, 0, 3, 2, 1, 2, 2, 3, 1, 1, 3
                ),
                listOf(
                    2, 3, 2, 1, 0, 3, 1, 0, 0, 3, 2, 0, 1, 3, 0, 1, 1, 0, 0, 2, 1, 1, 2, 1, 3, 2, 2, 0, 1, 0, 0, 2, 1, 3, 1, 0, 3, 3, 2, 0
                ),
                listOf(
                    2, 3, 0, 0, 0, 2, 0, 0, 2, 0, 1, 0, 3, 3, 1, 2, 0, 1, 0, 1, 0, 3, 1, 2, 1, 2, 2, 2, 2, 3, 3, 1, 0, 2, 0, 1, 2, 0, 2, 2
                ),
                listOf(
                    0, 0, 2, 3, 1, 2, 2, 1, 0, 1, 2, 1, 3, 1, 1, 0, 0, 3, 2, 0, 3, 0, 0, 3, 0, 2, 3, 3, 1, 0, 2, 3, 3, 0, 3, 1, 2, 1, 2, 2
                ),
                listOf(
                    3, 3, 1, 3, 1, 1, 0, 3, 1, 1, 2, 3, 3, 2, 0, 0, 1, 0, 0, 1, 2, 2, 1, 1, 2, 0, 1, 2, 1, 3, 2, 1, 2, 1, 0, 0, 1, 0, 0, 0
                ),
                listOf(
                    2, 2, 3, 2, 1, 3, 2, 2, 3, 1, 1, 0, 3, 0, 3, 3, 3, 1, 0, 1, 1, 3, 0, 0, 1, 3, 2, 0, 2, 0, 0, 2, 0, 3, 2, 1, 1, 0, 2, 0
                ),
                listOf(
                    3, 0, 1, 3, 2, 2, 3, 3, 3, 3, 0, 0, 2, 1, 0, 3, 1, 0, 0, 2, 3, 0, 3, 3, 0, 0, 0, 3, 0, 2, 1, 2, 2, 2, 2, 1, 2, 0, 1, 2
                ),
                listOf(
                    1, 2, 0, 2, 0, 2, 3, 2, 2, 0, 0, 3, 0, 2, 0, 2, 0, 2, 3, 1, 2, 3, 0, 3, 2, 0, 2, 0, 1, 1, 1, 1, 0, 1, 2, 2, 1, 2, 0, 3
                ),
                listOf(
                    3, 0, 0, 2, 2, 2, 3, 1, 0, 3, 2, 3, 3, 3, 0, 1, 0, 0, 3, 1, 2, 3, 0, 1, 1, 3, 3, 0, 2, 0, 0, 1, 2, 1, 3, 3, 3, 1, 3, 0
                ),
                listOf(
                    1, 0, 1, 2, 3, 0, 0, 1, 2, 2, 0, 0, 2, 1, 0, 2, 3, 3, 3, 2, 3, 2, 0, 2, 1, 1, 0, 1, 2, 0, 1, 0, 2, 2, 1, 0, 0, 0, 0, 0
                ),
                listOf(
                    1, 3, 3, 3, 3, 0, 0, 2, 0, 2, 2, 2, 2, 3, 1, 3, 1, 2, 0, 0, 2, 1, 3, 3, 3, 3, 2, 2, 1, 3, 1, 1, 1, 3, 0, 2, 2, 0, 0, 1
                ),
                listOf(
                    0, 0, 2, 2, 0, 0, 3, 1, 1, 0, 3, 1, 0, 0, 0, 1, 2, 1, 2, 0, 1, 1, 0, 1, 2, 3, 1, 1, 0, 0, 1, 2, 0, 2, 0, 3, 0, 0, 0, 2
                ),
                listOf(
                    3, 0, 1, 3, 3, 0, 0, 3, 1, 0, 2, 2, 1, 3, 0, 1, 3, 0, 3, 2, 0, 2, 2, 1, 3, 1, 3, 2, 1, 3, 3, 2, 0, 3, 1, 3, 1, 0, 3, 1
                ),
                listOf(
                    0, 2, 2, 3, 0, 3, 3, 3, 2, 0, 2, 0, 3, 2, 0, 0, 0, 2, 0, 2, 2, 3, 0, 0, 3, 2, 3, 3, 1, 3, 2, 1, 3, 1, 1, 3, 1, 2, 3, 2
                ),
                listOf(
                    0, 3, 0, 1, 1, 2, 0, 3, 3, 1, 3, 3, 0, 1, 3, 0, 2, 0, 0, 2, 0, 0, 0, 0, 3, 3, 2, 1, 1, 0, 2, 0, 0, 2, 2, 0, 2, 2, 2, 0
                ),
                listOf(
                    3, 1, 3, 0, 1, 0, 2, 3, 0, 3, 3, 2, 1, 1, 0, 1, 3, 1, 3, 1, 1, 0, 0, 3, 1, 1, 3, 0, 3, 2, 1, 0, 1, 1, 2, 3, 2, 1, 0, 2
                ),
                listOf(
                    1, 1, 0, 2, 1, 0, 3, 3, 0, 2, 3, 0, 1, 1, 1, 0, 0, 3, 3, 3, 2, 3, 2, 3, 2, 0, 1, 1, 2, 1, 0, 0, 1, 0, 2, 1, 1, 3, 1, 3
                ),
                listOf(
                    3, 2, 0, 0, 1, 0, 2, 0, 0, 0, 2, 3, 3, 3, 3, 2, 3, 1, 2, 2, 1, 0, 1, 3, 0, 0, 1, 3, 1, 2, 3, 3, 3, 1, 1, 2, 1, 3, 2, 1
                ),
                listOf(
                    3, 3, 0, 2, 1, 3, 1, 0, 3, 0, 3, 3, 3, 1, 1, 0, 2, 2, 3, 0, 2, 0, 0, 1, 3, 1, 1, 2, 2, 0, 0, 3, 0, 1, 0, 1, 2, 3, 3, 2
                ),
                listOf(
                    2, 1, 1, 3, 0, 3, 1, 3, 3, 3, 3, 3, 2, 3, 1, 0, 1, 3, 0, 0, 1, 0, 0, 3, 0, 3, 0, 2, 2, 1, 3, 3, 3, 3, 2, 1, 3, 2, 2, 1
                ),
                listOf(
                    1, 0, 1, 1, 0, 1, 2, 1, 0, 3, 1, 1, 1, 1, 3, 0, 0, 1, 2, 2, 2, 3, 1, 1, 2, 1, 1, 3, 0, 3, 0, 1, 1, 3, 1, 1, 0, 2, 1, 1
                ),
                listOf(
                    3, 2, 2, 0, 2, 2, 1, 1, 0, 0, 0, 2, 2, 0, 0, 2, 2, 1, 3, 1, 1, 2, 0, 2, 0, 3, 0, 1, 2, 0, 0, 2, 2, 3, 3, 3, 1, 0, 1, 3
                ),
                listOf(
                    0, 3, 3, 2, 2, 0, 0, 0, 3, 0, 2, 2, 2, 1, 1, 1, 0, 2, 1, 3, 3, 0, 1, 2, 3, 3, 2, 2, 3, 0, 2, 0, 1, 1, 0, 2, 1, 0, 0, 1
                ),
                listOf(
                    2, 3, 0, 1, 0, 1, 1, 0, 3, 3, 3, 2, 3, 0, 2, 3, 3, 2, 3, 0, 1, 3, 0, 3, 0, 2, 1, 3, 2, 1, 1, 2, 3, 3, 0, 0, 3, 0, 3, 1
                ),
                listOf(
                    3, 2, 2, 3, 2, 3, 0, 2, 0, 2, 2, 2, 3, 3, 2, 1, 0, 3, 0, 1, 3, 3, 0, 2, 3, 0, 0, 1, 2, 1, 3, 3, 1, 0, 2, 0, 3, 1, 0, 2
                )
            ), listOf(
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 25),
                Tank(2, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 10)
            )),
        )
    }

    private fun getChunk96(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_017", "Legendary", 40, 60, listOf(
                listOf(
                    0, 3, 2, 0, 0, 2, 1, 3, 3, 3, 1, 1, 2, 3, 0, 0, 0, 0, 3, 0, 3, 2, 1, 3, 3, 0, 0, 0, 3, 1, 1, 0, 1, 1, 1, 3, 0, 2, 3, 1
                ),
                listOf(
                    0, 3, 0, 0, 0, 1, 3, 1, 0, 1, 1, 0, 0, 1, 1, 3, 1, 3, 0, 0, 3, 2, 3, 3, 2, 3, 1, 1, 0, 2, 1, 1, 1, 3, 2, 0, 1, 3, 3, 0
                ),
                listOf(
                    3, 1, 1, 2, 2, 3, 3, 1, 0, 0, 2, 1, 0, 2, 0, 0, 2, 0, 3, 0, 0, 2, 1, 2, 1, 1, 0, 2, 2, 2, 1, 1, 1, 0, 3, 2, 0, 2, 1, 3
                ),
                listOf(
                    0, 1, 2, 2, 2, 1, 2, 1, 0, 3, 3, 0, 0, 3, 2, 3, 0, 0, 1, 1, 0, 2, 0, 0, 3, 0, 1, 3, 3, 0, 2, 1, 3, 2, 1, 0, 0, 3, 1, 3
                ),
                listOf(
                    2, 3, 0, 1, 2, 0, 0, 1, 2, 3, 0, 0, 0, 3, 0, 0, 3, 2, 1, 2, 3, 1, 3, 3, 0, 1, 1, 2, 1, 3, 0, 0, 0, 2, 2, 1, 3, 2, 1, 0
                ),
                listOf(
                    0, 3, 3, 3, 2, 1, 2, 2, 1, 1, 0, 3, 0, 0, 2, 0, 0, 2, 2, 3, 3, 3, 3, 3, 3, 0, 1, 0, 0, 3, 2, 3, 2, 2, 2, 2, 1, 3, 2, 2
                ),
                listOf(
                    2, 1, 0, 2, 3, 3, 1, 2, 3, 0, 3, 1, 0, 2, 1, 3, 0, 0, 3, 0, 3, 2, 2, 3, 2, 0, 1, 0, 1, 1, 3, 2, 2, 2, 1, 3, 1, 0, 1, 2
                ),
                listOf(
                    3, 0, 1, 3, 1, 1, 0, 2, 0, 2, 3, 2, 2, 2, 1, 1, 3, 1, 2, 2, 2, 1, 2, 1, 3, 3, 3, 1, 2, 1, 1, 2, 2, 2, 2, 0, 1, 3, 2, 1
                ),
                listOf(
                    0, 2, 1, 2, 0, 3, 3, 2, 0, 2, 0, 3, 2, 2, 0, 3, 2, 0, 2, 3, 0, 0, 2, 2, 1, 1, 2, 3, 3, 2, 0, 2, 3, 2, 3, 3, 3, 1, 2, 2
                ),
                listOf(
                    1, 2, 2, 3, 3, 2, 1, 0, 0, 1, 1, 0, 3, 3, 3, 3, 3, 2, 2, 1, 0, 1, 2, 2, 1, 3, 3, 3, 3, 0, 1, 1, 0, 1, 1, 3, 0, 1, 3, 3
                ),
                listOf(
                    0, 2, 0, 3, 3, 2, 3, 2, 0, 1, 0, 2, 1, 3, 0, 2, 1, 0, 1, 3, 3, 1, 2, 2, 3, 2, 2, 2, 1, 1, 1, 1, 2, 3, 2, 3, 3, 2, 2, 3
                ),
                listOf(
                    0, 1, 2, 3, 0, 2, 1, 2, 3, 0, 1, 3, 0, 0, 3, 0, 1, 3, 0, 3, 1, 3, 0, 1, 1, 1, 0, 0, 3, 2, 2, 3, 1, 2, 0, 0, 0, 1, 1, 2
                ),
                listOf(
                    2, 0, 3, 1, 0, 3, 3, 1, 3, 0, 0, 3, 0, 2, 2, 2, 2, 1, 0, 1, 1, 3, 3, 2, 2, 0, 1, 0, 2, 2, 0, 0, 3, 2, 1, 2, 2, 2, 2, 1
                ),
                listOf(
                    2, 0, 2, 3, 2, 0, 2, 3, 1, 1, 3, 2, 0, 0, 2, 0, 3, 2, 1, 0, 3, 1, 3, 3, 0, 1, 0, 3, 0, 2, 2, 1, 1, 2, 1, 3, 3, 2, 0, 0
                ),
                listOf(
                    2, 1, 0, 0, 1, 1, 2, 2, 3, 1, 1, 3, 3, 1, 2, 3, 0, 0, 3, 3, 2, 0, 1, 2, 2, 1, 2, 3, 0, 0, 0, 0, 3, 1, 2, 2, 2, 0, 1, 3
                ),
                listOf(
                    1, 0, 0, 3, 2, 0, 2, 2, 0, 0, 3, 3, 2, 3, 2, 0, 1, 0, 2, 1, 0, 3, 3, 1, 2, 2, 2, 1, 0, 1, 1, 0, 3, 2, 3, 0, 1, 1, 1, 1
                ),
                listOf(
                    0, 1, 1, 0, 3, 2, 3, 0, 1, 3, 0, 2, 3, 2, 0, 3, 0, 0, 1, 3, 1, 1, 0, 2, 1, 2, 2, 0, 3, 2, 3, 1, 1, 2, 0, 2, 1, 0, 0, 3
                ),
                listOf(
                    1, 2, 3, 1, 1, 3, 0, 3, 2, 2, 2, 0, 2, 3, 1, 0, 2, 2, 3, 0, 3, 3, 1, 0, 1, 2, 0, 1, 0, 3, 1, 3, 3, 3, 0, 2, 3, 0, 2, 0
                ),
                listOf(
                    0, 0, 0, 1, 0, 0, 3, 0, 0, 1, 0, 0, 2, 0, 1, 3, 3, 1, 2, 1, 3, 3, 3, 2, 1, 1, 0, 2, 3, 3, 2, 2, 0, 1, 3, 0, 1, 3, 3, 2
                ),
                listOf(
                    0, 1, 2, 2, 1, 1, 1, 3, 2, 0, 1, 0, 3, 1, 1, 3, 1, 0, 1, 2, 1, 0, 3, 1, 1, 3, 1, 1, 2, 2, 0, 0, 3, 3, 1, 2, 3, 0, 2, 3
                ),
                listOf(
                    1, 2, 0, 0, 0, 1, 2, 1, 2, 1, 3, 1, 0, 0, 2, 2, 2, 0, 1, 2, 2, 0, 2, 3, 1, 1, 2, 3, 1, 1, 0, 0, 2, 3, 1, 2, 1, 0, 3, 3
                ),
                listOf(
                    3, 2, 1, 2, 3, 1, 2, 1, 2, 2, 2, 0, 3, 0, 0, 1, 3, 0, 3, 0, 0, 3, 0, 0, 1, 1, 0, 2, 3, 3, 3, 2, 2, 1, 1, 2, 1, 0, 0, 3
                ),
                listOf(
                    2, 2, 1, 1, 2, 3, 0, 1, 3, 2, 1, 1, 2, 2, 0, 1, 3, 0, 3, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 2, 0, 2, 0, 0, 0, 0, 3, 1, 2, 3
                ),
                listOf(
                    3, 3, 0, 1, 0, 2, 1, 0, 1, 2, 0, 1, 0, 1, 3, 0, 2, 0, 2, 0, 3, 1, 0, 1, 0, 0, 2, 2, 3, 3, 0, 2, 3, 2, 0, 0, 2, 0, 3, 1
                ),
                listOf(
                    3, 2, 2, 0, 0, 1, 0, 3, 0, 2, 0, 1, 3, 3, 0, 3, 0, 2, 2, 1, 0, 1, 1, 2, 3, 2, 0, 1, 3, 0, 1, 3, 1, 2, 3, 2, 0, 0, 3, 3
                ),
                listOf(
                    0, 2, 1, 0, 2, 2, 2, 2, 0, 2, 2, 3, 3, 2, 0, 0, 1, 0, 3, 3, 1, 3, 0, 2, 0, 0, 0, 1, 1, 3, 1, 3, 0, 0, 0, 1, 2, 0, 2, 1
                ),
                listOf(
                    3, 0, 2, 0, 2, 0, 3, 2, 1, 1, 0, 1, 0, 2, 1, 2, 0, 2, 0, 0, 1, 1, 2, 2, 1, 3, 2, 2, 3, 0, 1, 0, 3, 3, 2, 1, 2, 1, 2, 2
                ),
                listOf(
                    1, 3, 3, 0, 1, 3, 0, 1, 2, 1, 3, 0, 2, 0, 2, 0, 0, 3, 0, 0, 1, 2, 2, 1, 3, 2, 0, 1, 2, 2, 1, 1, 1, 3, 1, 1, 3, 3, 2, 3
                ),
                listOf(
                    0, 3, 3, 3, 2, 1, 2, 0, 3, 1, 1, 3, 3, 1, 1, 2, 1, 3, 1, 1, 3, 3, 1, 2, 0, 0, 0, 1, 2, 0, 1, 2, 1, 0, 2, 1, 3, 2, 2, 2
                ),
                listOf(
                    2, 3, 1, 1, 0, 3, 2, 0, 2, 1, 0, 0, 2, 3, 1, 2, 0, 0, 2, 2, 1, 3, 1, 0, 1, 3, 0, 3, 0, 1, 2, 1, 2, 0, 2, 2, 2, 3, 0, 1
                ),
                listOf(
                    3, 0, 1, 2, 2, 3, 3, 2, 0, 3, 3, 0, 1, 2, 0, 0, 3, 2, 2, 3, 2, 0, 0, 1, 3, 1, 3, 3, 1, 1, 3, 0, 0, 2, 2, 2, 0, 1, 0, 1
                ),
                listOf(
                    1, 2, 1, 3, 0, 2, 2, 0, 2, 1, 0, 3, 2, 1, 3, 3, 0, 1, 1, 3, 3, 2, 2, 3, 3, 0, 1, 3, 0, 2, 3, 2, 1, 2, 2, 3, 1, 3, 0, 1
                ),
                listOf(
                    0, 1, 0, 2, 0, 0, 2, 0, 0, 3, 3, 3, 0, 1, 2, 2, 0, 3, 2, 2, 1, 0, 0, 0, 3, 1, 1, 3, 1, 0, 3, 2, 3, 1, 1, 2, 2, 2, 0, 3
                ),
                listOf(
                    3, 1, 1, 3, 3, 2, 1, 3, 0, 2, 2, 2, 1, 1, 0, 0, 1, 0, 1, 2, 1, 3, 2, 0, 3, 1, 1, 3, 1, 1, 2, 1, 1, 0, 1, 1, 0, 1, 2, 3
                ),
                listOf(
                    3, 1, 2, 2, 2, 3, 0, 3, 1, 0, 2, 3, 2, 3, 1, 2, 1, 3, 2, 1, 0, 2, 0, 2, 2, 3, 2, 3, 1, 2, 3, 3, 0, 3, 2, 0, 0, 2, 0, 3
                ),
                listOf(
                    0, 0, 1, 2, 0, 3, 0, 3, 3, 2, 2, 0, 0, 0, 2, 2, 0, 1, 0, 2, 3, 3, 0, 3, 3, 0, 0, 0, 2, 1, 3, 0, 1, 2, 2, 2, 0, 0, 2, 3
                ),
                listOf(
                    0, 1, 3, 2, 2, 1, 0, 0, 2, 1, 3, 0, 2, 0, 1, 2, 3, 2, 1, 2, 2, 0, 0, 0, 3, 1, 0, 0, 3, 1, 3, 3, 0, 0, 3, 1, 2, 1, 0, 1
                ),
                listOf(
                    0, 0, 3, 0, 2, 3, 2, 1, 0, 3, 2, 3, 3, 1, 3, 0, 0, 2, 1, 3, 0, 3, 1, 3, 0, 2, 3, 3, 1, 2, 0, 2, 2, 2, 2, 0, 3, 0, 3, 2
                ),
                listOf(
                    2, 1, 2, 3, 3, 3, 3, 3, 1, 3, 2, 2, 3, 1, 0, 2, 3, 1, 3, 2, 3, 0, 2, 2, 3, 3, 3, 1, 1, 3, 2, 1, 2, 0, 1, 0, 2, 1, 3, 0
                ),
                listOf(
                    2, 1, 1, 1, 3, 0, 0, 0, 3, 2, 1, 2, 0, 2, 3, 1, 1, 3, 1, 2, 1, 3, 0, 3, 2, 2, 1, 2, 1, 2, 2, 3, 2, 0, 3, 2, 1, 0, 2, 0
                ),
                listOf(
                    1, 2, 0, 0, 3, 0, 1, 1, 2, 2, 0, 3, 3, 0, 1, 0, 2, 3, 2, 3, 1, 1, 2, 0, 1, 2, 3, 0, 1, 0, 0, 0, 3, 0, 3, 3, 3, 2, 2, 1
                ),
                listOf(
                    3, 3, 2, 1, 3, 1, 1, 2, 3, 2, 0, 2, 2, 1, 1, 0, 0, 3, 3, 0, 1, 1, 2, 1, 1, 0, 0, 3, 1, 3, 1, 3, 2, 1, 0, 3, 0, 3, 1, 2
                ),
                listOf(
                    0, 2, 0, 2, 0, 0, 0, 2, 3, 2, 3, 2, 1, 0, 0, 1, 3, 2, 0, 3, 2, 3, 2, 3, 2, 1, 3, 3, 1, 2, 1, 1, 0, 2, 1, 0, 2, 1, 2, 0
                ),
                listOf(
                    0, 0, 1, 0, 2, 0, 0, 1, 3, 3, 2, 2, 0, 2, 3, 1, 2, 3, 1, 2, 1, 0, 2, 3, 3, 1, 2, 2, 2, 3, 1, 3, 2, 0, 2, 0, 1, 1, 0, 0
                ),
                listOf(
                    2, 2, 2, 1, 0, 0, 3, 0, 2, 3, 0, 0, 3, 3, 1, 3, 0, 3, 0, 0, 3, 3, 1, 3, 2, 1, 1, 1, 2, 1, 0, 3, 2, 1, 2, 3, 0, 1, 3, 1
                ),
                listOf(
                    2, 0, 1, 1, 1, 0, 0, 3, 3, 0, 3, 0, 2, 2, 1, 1, 3, 2, 3, 0, 0, 3, 0, 2, 2, 1, 1, 0, 0, 1, 2, 0, 0, 1, 0, 2, 2, 3, 1, 0
                ),
                listOf(
                    2, 1, 0, 3, 0, 0, 2, 1, 1, 1, 2, 0, 0, 3, 0, 2, 1, 2, 1, 2, 0, 1, 1, 3, 1, 0, 1, 2, 3, 0, 3, 3, 1, 3, 0, 1, 3, 2, 3, 2
                ),
                listOf(
                    2, 3, 3, 0, 0, 3, 1, 2, 1, 3, 1, 2, 0, 0, 1, 2, 1, 3, 1, 0, 3, 0, 3, 2, 0, 0, 2, 3, 0, 3, 1, 2, 2, 0, 0, 2, 1, 2, 2, 2
                ),
                listOf(
                    2, 0, 1, 3, 2, 0, 3, 1, 0, 3, 1, 1, 2, 0, 2, 0, 2, 1, 3, 2, 2, 2, 2, 0, 1, 2, 0, 1, 3, 1, 3, 0, 0, 1, 0, 3, 3, 3, 3, 1
                ),
                listOf(
                    2, 3, 2, 2, 1, 0, 0, 0, 3, 3, 0, 3, 1, 0, 2, 2, 3, 2, 2, 2, 0, 1, 1, 2, 1, 1, 2, 1, 3, 0, 1, 2, 3, 0, 3, 0, 0, 2, 2, 2
                ),
                listOf(
                    2, 2, 1, 1, 2, 2, 0, 2, 0, 2, 0, 0, 3, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 0, 2, 2, 1, 1, 0, 1, 0, 2, 2, 3, 1, 2, 2, 3, 1
                ),
                listOf(
                    1, 2, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 2, 0, 0, 3, 0, 3, 2, 2, 0, 2, 3, 3, 3, 2, 1, 0, 2, 3, 2, 1, 3, 2, 1, 0, 3, 3, 2, 0
                ),
                listOf(
                    3, 3, 1, 3, 3, 2, 0, 0, 3, 3, 3, 3, 3, 2, 2, 1, 2, 0, 0, 3, 0, 0, 0, 1, 2, 3, 3, 0, 3, 3, 0, 2, 3, 3, 1, 3, 3, 3, 0, 2
                ),
                listOf(
                    2, 0, 0, 1, 1, 3, 3, 2, 1, 0, 2, 3, 1, 1, 1, 3, 2, 1, 0, 0, 2, 2, 0, 2, 0, 2, 3, 0, 1, 1, 2, 1, 2, 0, 3, 0, 2, 0, 3, 1
                ),
                listOf(
                    3, 3, 0, 2, 0, 0, 2, 3, 0, 2, 1, 3, 3, 1, 2, 0, 0, 3, 1, 3, 2, 0, 3, 0, 2, 2, 3, 1, 0, 0, 2, 1, 1, 3, 3, 2, 3, 3, 3, 2
                ),
                listOf(
                    0, 2, 2, 1, 2, 0, 1, 0, 2, 3, 2, 3, 1, 1, 2, 0, 3, 0, 1, 1, 2, 1, 2, 0, 3, 0, 0, 0, 0, 0, 1, 3, 1, 0, 1, 3, 1, 3, 1, 3
                ),
                listOf(
                    3, 2, 1, 3, 0, 3, 1, 1, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 0, 1, 0, 0, 3, 1, 2, 2, 2, 1, 0, 0, 3, 1, 3, 3, 2, 0, 0, 2
                ),
                listOf(
                    0, 1, 0, 2, 1, 1, 1, 1, 0, 2, 0, 3, 0, 2, 2, 2, 2, 3, 1, 1, 2, 2, 2, 1, 3, 1, 0, 2, 2, 3, 1, 3, 0, 0, 3, 1, 2, 3, 3, 3
                ),
                listOf(
                    1, 0, 1, 0, 1, 3, 1, 3, 2, 3, 1, 3, 0, 3, 3, 2, 1, 3, 1, 1, 3, 2, 3, 1, 3, 0, 3, 0, 2, 0, 1, 2, 0, 3, 2, 3, 1, 1, 1, 0
                ),
                listOf(
                    1, 0, 3, 2, 0, 1, 2, 1, 2, 1, 3, 0, 0, 3, 3, 0, 3, 0, 2, 2, 0, 3, 1, 2, 3, 1, 1, 1, 0, 2, 3, 0, 0, 1, 0, 2, 0, 1, 0, 0
                )
            ), listOf(
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 25),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk97(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_018", "Legendary", 40, 60, listOf(
                listOf(
                    2, 0, 1, 2, 2, 1, 3, 0, 1, 2, 0, 1, 2, 2, 3, 0, 1, 1, 2, 1, 3, 2, 1, 2, 0, 1, 0, 1, 2, 2, 0, 2, 2, 2, 3, 3, 3, 3, 3, 3
                ),
                listOf(
                    1, 1, 2, 1, 2, 2, 0, 0, 3, 0, 1, 0, 3, 1, 0, 1, 3, 1, 0, 3, 3, 3, 0, 2, 1, 0, 3, 1, 3, 3, 3, 0, 3, 1, 3, 1, 1, 0, 1, 1
                ),
                listOf(
                    1, 0, 3, 1, 1, 3, 0, 0, 3, 0, 1, 3, 3, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 3, 0, 2, 2, 1, 1, 2, 2, 0, 3, 1, 0, 3, 3, 1, 1, 0
                ),
                listOf(
                    3, 0, 1, 0, 2, 2, 1, 3, 3, 0, 2, 2, 3, 3, 1, 1, 1, 2, 0, 2, 1, 1, 1, 0, 1, 1, 3, 2, 0, 1, 1, 3, 0, 2, 1, 0, 3, 2, 1, 2
                ),
                listOf(
                    3, 0, 3, 0, 3, 1, 1, 0, 2, 1, 0, 0, 1, 0, 3, 2, 3, 3, 3, 0, 0, 3, 0, 0, 1, 1, 2, 2, 3, 1, 1, 0, 0, 0, 3, 1, 2, 2, 1, 0
                ),
                listOf(
                    1, 1, 1, 0, 1, 2, 1, 0, 0, 1, 3, 1, 0, 3, 0, 3, 0, 1, 2, 1, 1, 2, 1, 3, 2, 1, 2, 2, 1, 0, 3, 3, 3, 2, 1, 0, 1, 1, 3, 0
                ),
                listOf(
                    3, 0, 1, 2, 2, 3, 3, 0, 3, 3, 0, 0, 3, 1, 0, 3, 3, 0, 1, 2, 1, 1, 0, 2, 1, 2, 0, 0, 2, 1, 3, 1, 1, 0, 3, 2, 3, 0, 3, 0
                ),
                listOf(
                    0, 0, 3, 0, 0, 1, 3, 1, 3, 0, 0, 2, 2, 0, 0, 2, 0, 0, 1, 1, 0, 3, 0, 3, 2, 1, 1, 1, 1, 1, 3, 3, 0, 3, 0, 1, 1, 1, 1, 0
                ),
                listOf(
                    3, 3, 0, 1, 3, 1, 3, 0, 2, 0, 3, 1, 0, 2, 1, 1, 1, 1, 2, 3, 1, 3, 1, 0, 1, 0, 3, 3, 1, 3, 2, 3, 2, 1, 3, 3, 0, 1, 3, 1
                ),
                listOf(
                    0, 1, 1, 0, 1, 2, 0, 0, 2, 2, 3, 1, 0, 2, 2, 0, 3, 1, 3, 1, 3, 1, 2, 3, 3, 1, 1, 3, 1, 0, 0, 3, 2, 0, 0, 3, 3, 1, 1, 1
                ),
                listOf(
                    0, 1, 3, 3, 2, 1, 1, 0, 1, 3, 2, 3, 3, 2, 2, 2, 0, 0, 1, 2, 2, 0, 2, 1, 3, 1, 1, 3, 3, 2, 3, 2, 3, 1, 3, 3, 3, 1, 3, 1
                ),
                listOf(
                    1, 3, 1, 3, 3, 2, 0, 3, 1, 2, 0, 3, 3, 0, 0, 0, 1, 1, 2, 2, 2, 3, 0, 0, 3, 3, 0, 2, 2, 1, 2, 0, 0, 2, 3, 0, 0, 1, 1, 1
                ),
                listOf(
                    1, 3, 3, 3, 1, 2, 0, 2, 1, 1, 2, 1, 2, 1, 3, 1, 1, 2, 0, 1, 0, 0, 2, 1, 0, 0, 3, 3, 3, 1, 0, 1, 3, 2, 2, 1, 1, 3, 3, 2
                ),
                listOf(
                    3, 3, 3, 0, 0, 2, 1, 0, 3, 2, 1, 1, 0, 1, 1, 2, 0, 2, 0, 3, 3, 1, 1, 3, 2, 3, 0, 2, 1, 1, 0, 0, 0, 1, 0, 0, 3, 1, 0, 0
                ),
                listOf(
                    3, 2, 1, 3, 2, 2, 1, 1, 2, 1, 1, 1, 0, 1, 3, 1, 2, 1, 2, 1, 2, 1, 0, 3, 3, 1, 3, 1, 0, 1, 0, 0, 0, 2, 2, 1, 2, 2, 3, 2
                ),
                listOf(
                    0, 3, 1, 1, 1, 2, 0, 1, 0, 1, 3, 2, 3, 3, 2, 0, 2, 1, 3, 0, 3, 3, 0, 0, 1, 1, 1, 3, 1, 3, 1, 0, 0, 2, 0, 3, 2, 1, 3, 1
                ),
                listOf(
                    1, 1, 3, 2, 2, 0, 0, 2, 1, 0, 3, 0, 1, 0, 1, 0, 1, 2, 1, 2, 2, 0, 3, 1, 2, 3, 3, 0, 1, 0, 2, 1, 1, 0, 3, 3, 0, 3, 1, 2
                ),
                listOf(
                    3, 3, 0, 1, 1, 2, 1, 3, 1, 0, 3, 1, 3, 3, 3, 0, 1, 3, 3, 1, 0, 3, 0, 0, 1, 1, 1, 3, 2, 0, 3, 1, 0, 3, 1, 0, 1, 3, 0, 2
                ),
                listOf(
                    3, 0, 2, 3, 3, 2, 3, 3, 2, 2, 3, 1, 2, 1, 3, 3, 1, 2, 1, 3, 1, 0, 3, 0, 2, 3, 3, 1, 3, 0, 0, 3, 1, 0, 3, 1, 3, 3, 3, 1
                ),
                listOf(
                    0, 0, 0, 1, 3, 2, 3, 2, 1, 2, 0, 3, 3, 0, 2, 0, 0, 2, 2, 0, 2, 3, 0, 1, 1, 1, 0, 2, 3, 3, 2, 2, 0, 3, 1, 1, 3, 3, 3, 2
                ),
                listOf(
                    3, 0, 3, 0, 3, 1, 3, 2, 1, 0, 3, 1, 3, 3, 3, 2, 1, 3, 3, 0, 2, 3, 1, 2, 2, 3, 1, 1, 1, 0, 0, 1, 0, 1, 3, 3, 3, 2, 0, 3
                ),
                listOf(
                    2, 2, 3, 3, 1, 3, 1, 1, 2, 3, 3, 1, 3, 2, 2, 2, 1, 2, 0, 1, 1, 0, 0, 2, 2, 1, 1, 0, 2, 1, 0, 0, 2, 2, 3, 2, 0, 3, 1, 1
                ),
                listOf(
                    2, 0, 0, 2, 3, 1, 3, 3, 0, 1, 1, 0, 3, 0, 1, 1, 3, 0, 3, 3, 3, 3, 3, 1, 1, 2, 1, 3, 2, 0, 1, 2, 1, 0, 0, 3, 2, 2, 3, 1
                ),
                listOf(
                    0, 3, 2, 1, 1, 1, 0, 2, 2, 1, 0, 3, 0, 3, 1, 2, 2, 2, 0, 1, 3, 1, 1, 0, 3, 0, 2, 0, 2, 3, 0, 2, 1, 2, 3, 2, 1, 3, 1, 2
                ),
                listOf(
                    2, 1, 0, 0, 0, 1, 3, 1, 1, 0, 3, 1, 3, 2, 2, 0, 1, 2, 2, 2, 2, 2, 1, 3, 3, 3, 3, 1, 1, 1, 1, 1, 3, 0, 2, 2, 3, 1, 3, 0
                ),
                listOf(
                    2, 2, 1, 0, 2, 1, 1, 3, 3, 1, 1, 2, 2, 0, 1, 1, 2, 0, 0, 2, 1, 0, 3, 3, 0, 1, 1, 2, 0, 1, 3, 0, 1, 3, 0, 0, 1, 2, 3, 3
                ),
                listOf(
                    3, 0, 2, 3, 1, 0, 2, 1, 1, 1, 0, 1, 3, 2, 3, 2, 2, 1, 2, 2, 0, 0, 1, 1, 0, 1, 2, 1, 1, 2, 1, 2, 2, 2, 3, 2, 3, 1, 2, 0
                ),
                listOf(
                    2, 0, 1, 0, 1, 0, 1, 1, 2, 1, 1, 0, 0, 2, 1, 2, 2, 1, 2, 1, 0, 3, 3, 0, 0, 1, 2, 1, 0, 2, 1, 0, 0, 0, 2, 0, 1, 2, 3, 1
                ),
                listOf(
                    2, 2, 0, 1, 2, 3, 0, 0, 2, 0, 0, 0, 1, 3, 1, 2, 1, 2, 1, 1, 3, 2, 1, 2, 2, 1, 0, 3, 0, 0, 0, 1, 1, 3, 1, 2, 1, 3, 1, 2
                ),
                listOf(
                    0, 1, 1, 0, 1, 0, 0, 3, 2, 0, 2, 1, 0, 3, 0, 0, 3, 3, 0, 1, 3, 0, 2, 1, 0, 1, 1, 1, 0, 2, 3, 0, 2, 1, 1, 1, 0, 1, 2, 2
                ),
                listOf(
                    2, 2, 3, 3, 3, 2, 1, 1, 3, 2, 0, 3, 3, 0, 3, 1, 2, 2, 0, 1, 3, 0, 0, 1, 3, 1, 3, 0, 3, 1, 3, 1, 2, 1, 0, 1, 0, 0, 2, 1
                ),
                listOf(
                    3, 1, 0, 0, 3, 1, 3, 3, 0, 0, 0, 0, 3, 3, 1, 0, 0, 2, 3, 3, 3, 0, 0, 3, 3, 2, 1, 3, 3, 0, 2, 1, 1, 0, 1, 3, 3, 1, 1, 3
                ),
                listOf(
                    2, 1, 2, 2, 0, 3, 0, 1, 0, 2, 0, 1, 1, 3, 1, 1, 2, 0, 2, 0, 3, 0, 0, 1, 3, 0, 1, 1, 0, 2, 1, 1, 3, 2, 1, 3, 0, 3, 0, 1
                ),
                listOf(
                    3, 2, 0, 1, 2, 0, 3, 3, 3, 3, 2, 1, 3, 2, 0, 1, 1, 0, 2, 0, 2, 2, 1, 1, 2, 2, 0, 2, 1, 2, 3, 0, 0, 0, 2, 1, 3, 3, 1, 0
                ),
                listOf(
                    0, 0, 1, 0, 1, 1, 2, 1, 0, 3, 0, 2, 1, 0, 1, 0, 3, 1, 2, 1, 1, 3, 3, 1, 3, 1, 3, 3, 0, 0, 3, 3, 1, 1, 1, 3, 0, 2, 0, 0
                ),
                listOf(
                    3, 2, 1, 2, 3, 3, 1, 2, 1, 2, 3, 0, 3, 2, 1, 3, 0, 1, 0, 1, 2, 2, 1, 3, 1, 1, 3, 1, 0, 3, 2, 0, 1, 3, 3, 0, 1, 3, 3, 3
                ),
                listOf(
                    0, 0, 3, 3, 1, 0, 0, 3, 2, 3, 0, 3, 2, 3, 1, 2, 3, 1, 3, 0, 2, 0, 3, 3, 2, 3, 1, 1, 3, 3, 3, 0, 3, 1, 3, 0, 0, 2, 2, 1
                ),
                listOf(
                    0, 2, 1, 3, 0, 0, 0, 0, 1, 2, 3, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 3, 3, 2, 2, 1, 3, 2, 0, 0, 1, 2, 2, 0, 1, 1, 3, 0, 0, 0
                ),
                listOf(
                    2, 1, 2, 3, 1, 1, 3, 2, 2, 3, 2, 1, 2, 1, 2, 3, 3, 1, 1, 2, 0, 0, 1, 0, 3, 0, 0, 1, 2, 0, 3, 2, 1, 1, 0, 1, 0, 1, 2, 2
                ),
                listOf(
                    1, 1, 3, 0, 3, 0, 1, 0, 3, 3, 2, 0, 2, 0, 2, 1, 3, 1, 0, 0, 2, 0, 0, 1, 2, 1, 0, 1, 2, 0, 1, 2, 3, 3, 3, 0, 1, 2, 1, 0
                ),
                listOf(
                    0, 0, 3, 1, 0, 0, 2, 1, 0, 2, 0, 0, 1, 2, 3, 1, 1, 1, 2, 1, 1, 1, 3, 0, 2, 3, 3, 1, 2, 0, 2, 0, 2, 1, 0, 1, 0, 3, 3, 3
                ),
                listOf(
                    1, 3, 0, 1, 3, 2, 0, 2, 3, 0, 3, 2, 0, 3, 3, 0, 2, 1, 0, 1, 2, 2, 1, 3, 3, 3, 3, 2, 2, 3, 2, 0, 2, 3, 1, 3, 0, 1, 2, 0
                ),
                listOf(
                    0, 0, 2, 3, 2, 0, 2, 1, 1, 3, 2, 1, 3, 2, 0, 2, 2, 3, 3, 2, 3, 0, 3, 0, 1, 0, 3, 0, 1, 3, 2, 1, 0, 3, 1, 2, 2, 3, 2, 2
                ),
                listOf(
                    1, 1, 1, 0, 2, 0, 3, 2, 2, 3, 1, 3, 2, 3, 1, 2, 3, 0, 1, 3, 2, 0, 2, 1, 0, 1, 1, 3, 3, 1, 1, 3, 0, 0, 1, 3, 1, 3, 2, 0
                ),
                listOf(
                    1, 3, 2, 0, 3, 1, 3, 2, 2, 2, 3, 0, 3, 0, 2, 3, 3, 2, 1, 2, 1, 3, 0, 2, 2, 1, 0, 2, 2, 0, 2, 3, 0, 0, 3, 3, 0, 2, 2, 0
                ),
                listOf(
                    0, 1, 3, 1, 0, 1, 1, 3, 2, 3, 1, 2, 2, 0, 2, 2, 3, 0, 1, 3, 3, 2, 1, 0, 0, 3, 1, 0, 2, 2, 0, 1, 3, 3, 0, 1, 1, 0, 0, 0
                ),
                listOf(
                    2, 3, 3, 1, 3, 1, 1, 1, 3, 1, 1, 2, 0, 1, 0, 3, 2, 1, 0, 1, 1, 0, 3, 2, 1, 3, 0, 0, 0, 0, 1, 1, 3, 3, 0, 1, 0, 1, 1, 3
                ),
                listOf(
                    1, 2, 2, 1, 2, 2, 3, 3, 0, 1, 2, 0, 0, 2, 2, 0, 0, 3, 3, 1, 0, 3, 1, 1, 3, 1, 1, 0, 0, 0, 2, 2, 3, 3, 1, 2, 0, 1, 0, 3
                ),
                listOf(
                    1, 2, 3, 0, 1, 0, 2, 2, 1, 0, 0, 1, 2, 3, 1, 0, 0, 0, 1, 0, 2, 3, 3, 1, 1, 2, 0, 3, 3, 0, 0, 3, 0, 0, 3, 2, 3, 2, 1, 3
                ),
                listOf(
                    3, 0, 0, 3, 3, 0, 2, 1, 0, 1, 2, 2, 0, 1, 1, 1, 0, 3, 1, 2, 3, 1, 1, 0, 1, 0, 3, 3, 3, 2, 3, 2, 3, 1, 1, 0, 0, 2, 2, 0
                ),
                listOf(
                    0, 2, 1, 0, 2, 2, 3, 0, 0, 0, 0, 0, 3, 3, 3, 2, 3, 3, 2, 1, 0, 2, 0, 2, 3, 2, 0, 1, 0, 2, 2, 0, 1, 3, 1, 2, 2, 3, 0, 2
                ),
                listOf(
                    1, 1, 1, 2, 2, 0, 3, 1, 0, 1, 0, 2, 0, 2, 3, 3, 2, 3, 3, 3, 1, 3, 2, 0, 3, 2, 1, 3, 2, 1, 3, 2, 2, 2, 2, 2, 3, 0, 0, 0
                ),
                listOf(
                    0, 2, 0, 3, 0, 1, 3, 0, 0, 0, 1, 3, 3, 0, 1, 0, 2, 3, 2, 0, 2, 3, 1, 3, 0, 2, 3, 3, 0, 1, 2, 0, 0, 3, 2, 2, 3, 1, 1, 3
                ),
                listOf(
                    2, 3, 1, 3, 2, 3, 2, 3, 0, 2, 1, 0, 1, 0, 3, 0, 3, 1, 3, 3, 3, 1, 3, 0, 3, 2, 3, 1, 3, 3, 1, 2, 2, 1, 0, 1, 1, 1, 0, 2
                ),
                listOf(
                    3, 0, 2, 0, 2, 0, 1, 1, 0, 1, 3, 3, 0, 3, 3, 0, 0, 1, 0, 3, 1, 1, 3, 3, 3, 3, 1, 3, 1, 3, 0, 3, 0, 1, 0, 1, 1, 2, 1, 1
                ),
                listOf(
                    1, 0, 2, 1, 0, 0, 0, 0, 0, 3, 1, 3, 3, 3, 2, 3, 3, 1, 2, 0, 3, 1, 1, 3, 1, 1, 3, 0, 2, 1, 2, 3, 1, 1, 1, 0, 0, 1, 2, 3
                ),
                listOf(
                    0, 2, 1, 2, 2, 3, 3, 3, 3, 2, 0, 1, 2, 2, 0, 3, 2, 1, 2, 2, 0, 2, 3, 3, 1, 3, 1, 0, 2, 1, 0, 0, 1, 0, 2, 1, 1, 1, 3, 0
                ),
                listOf(
                    2, 0, 3, 1, 1, 3, 0, 2, 3, 2, 3, 1, 1, 1, 0, 0, 0, 0, 0, 3, 1, 2, 1, 2, 3, 3, 2, 1, 1, 2, 1, 3, 0, 1, 1, 3, 0, 0, 1, 3
                ),
                listOf(
                    2, 2, 1, 3, 3, 1, 1, 0, 0, 2, 3, 3, 2, 3, 3, 1, 3, 2, 2, 3, 3, 3, 3, 2, 3, 1, 2, 3, 1, 0, 1, 2, 2, 2, 3, 2, 1, 1, 0, 0
                ),
                listOf(
                    3, 1, 1, 3, 1, 3, 1, 3, 1, 0, 3, 2, 0, 3, 3, 0, 3, 0, 3, 2, 3, 1, 2, 2, 3, 0, 3, 2, 3, 3, 0, 2, 1, 0, 1, 2, 0, 2, 2, 2
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 25),
                Tank(3, 25),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk98(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_019", "Legendary", 40, 60, listOf(
                listOf(
                    2, 2, 2, 0, 1, 0, 1, 1, 3, 0, 3, 1, 2, 1, 1, 1, 1, 1, 3, 0, 2, 3, 0, 3, 2, 0, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2, 0, 2, 0, 0
                ),
                listOf(
                    0, 2, 1, 2, 3, 1, 1, 1, 2, 0, 0, 2, 3, 1, 0, 3, 3, 0, 0, 0, 0, 1, 2, 3, 3, 1, 0, 2, 1, 0, 0, 3, 2, 0, 2, 2, 2, 0, 1, 0
                ),
                listOf(
                    1, 3, 2, 1, 3, 2, 2, 3, 1, 0, 0, 0, 3, 3, 1, 1, 2, 1, 1, 2, 0, 0, 1, 1, 2, 2, 1, 3, 2, 1, 2, 1, 3, 3, 3, 1, 0, 1, 2, 3
                ),
                listOf(
                    3, 1, 1, 2, 3, 2, 2, 2, 0, 3, 3, 2, 2, 0, 2, 0, 2, 1, 3, 3, 0, 2, 1, 1, 1, 0, 2, 3, 1, 1, 2, 2, 3, 1, 1, 1, 1, 0, 2, 0
                ),
                listOf(
                    3, 2, 0, 0, 2, 2, 3, 2, 1, 2, 1, 2, 0, 0, 0, 2, 1, 2, 3, 1, 0, 2, 0, 2, 3, 3, 3, 1, 1, 3, 0, 1, 2, 1, 0, 1, 1, 0, 1, 3
                ),
                listOf(
                    0, 2, 3, 0, 3, 2, 2, 2, 2, 2, 3, 2, 3, 1, 3, 0, 0, 0, 0, 3, 3, 2, 1, 2, 3, 2, 3, 3, 2, 1, 0, 0, 2, 1, 2, 3, 2, 1, 3, 1
                ),
                listOf(
                    0, 0, 0, 3, 3, 2, 3, 1, 1, 2, 3, 1, 2, 0, 0, 2, 2, 2, 1, 3, 3, 0, 3, 3, 0, 2, 1, 1, 1, 1, 2, 2, 1, 0, 1, 1, 0, 3, 3, 3
                ),
                listOf(
                    0, 0, 1, 1, 0, 0, 0, 3, 1, 2, 2, 0, 1, 3, 2, 3, 0, 1, 3, 3, 0, 3, 3, 0, 0, 3, 1, 3, 0, 3, 3, 3, 1, 0, 2, 3, 1, 2, 1, 0
                ),
                listOf(
                    2, 3, 3, 3, 1, 0, 2, 3, 1, 2, 1, 3, 2, 3, 2, 3, 1, 0, 0, 2, 0, 3, 0, 2, 1, 3, 1, 2, 0, 2, 3, 2, 2, 2, 0, 3, 3, 3, 2, 3
                ),
                listOf(
                    2, 0, 3, 3, 0, 1, 1, 3, 2, 0, 1, 3, 1, 0, 1, 1, 0, 1, 1, 1, 1, 2, 0, 3, 2, 2, 0, 3, 0, 3, 0, 1, 3, 0, 0, 3, 0, 2, 1, 0
                ),
                listOf(
                    1, 2, 1, 2, 2, 1, 0, 1, 3, 3, 1, 3, 2, 1, 1, 1, 3, 3, 1, 3, 1, 2, 1, 3, 0, 3, 1, 3, 1, 2, 3, 3, 2, 0, 0, 0, 0, 2, 1, 3
                ),
                listOf(
                    2, 0, 1, 0, 0, 0, 0, 2, 1, 0, 0, 1, 1, 3, 1, 2, 2, 0, 3, 2, 2, 0, 1, 2, 0, 3, 0, 1, 1, 2, 1, 0, 1, 1, 2, 0, 0, 2, 2, 2
                ),
                listOf(
                    2, 0, 1, 3, 2, 0, 0, 2, 2, 1, 1, 3, 2, 2, 1, 2, 0, 3, 0, 2, 3, 0, 3, 1, 3, 2, 0, 2, 2, 1, 2, 2, 2, 0, 3, 2, 1, 0, 0, 0
                ),
                listOf(
                    2, 0, 3, 0, 2, 2, 3, 1, 3, 2, 2, 1, 3, 0, 3, 1, 3, 1, 1, 3, 1, 1, 3, 2, 0, 3, 0, 1, 3, 3, 3, 0, 0, 1, 0, 1, 3, 1, 1, 0
                ),
                listOf(
                    3, 3, 0, 2, 2, 2, 2, 1, 3, 0, 0, 2, 1, 1, 0, 1, 0, 1, 1, 0, 2, 1, 0, 0, 1, 0, 0, 1, 2, 0, 1, 0, 0, 0, 0, 2, 0, 1, 1, 1
                ),
                listOf(
                    3, 2, 1, 1, 3, 0, 3, 2, 3, 1, 3, 3, 2, 2, 0, 1, 2, 1, 2, 2, 3, 1, 1, 1, 1, 1, 2, 0, 0, 3, 2, 1, 2, 1, 2, 2, 3, 3, 3, 0
                ),
                listOf(
                    1, 3, 1, 3, 0, 3, 3, 1, 0, 3, 2, 0, 3, 1, 0, 0, 1, 3, 1, 3, 3, 1, 1, 2, 2, 1, 2, 1, 0, 0, 3, 1, 0, 3, 3, 1, 0, 2, 2, 2
                ),
                listOf(
                    1, 3, 0, 1, 3, 0, 3, 1, 0, 3, 2, 3, 1, 3, 0, 2, 2, 0, 2, 1, 2, 0, 3, 2, 3, 0, 3, 3, 1, 0, 3, 3, 0, 3, 0, 3, 0, 2, 1, 2
                ),
                listOf(
                    1, 2, 3, 1, 2, 3, 0, 0, 2, 0, 3, 1, 1, 2, 0, 3, 2, 1, 0, 0, 1, 3, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2, 2, 0, 1, 0, 3, 0, 1, 1
                ),
                listOf(
                    2, 2, 3, 3, 0, 1, 2, 1, 0, 3, 1, 0, 3, 1, 1, 2, 0, 0, 0, 3, 0, 3, 3, 0, 2, 1, 0, 2, 0, 0, 3, 2, 3, 2, 1, 2, 1, 0, 1, 3
                ),
                listOf(
                    1, 0, 0, 2, 2, 3, 1, 0, 2, 2, 2, 1, 1, 2, 3, 1, 3, 1, 0, 2, 3, 2, 2, 2, 1, 2, 3, 0, 0, 2, 3, 0, 0, 2, 3, 0, 0, 0, 2, 3
                ),
                listOf(
                    1, 1, 0, 3, 3, 1, 3, 1, 2, 1, 2, 1, 0, 1, 0, 1, 3, 1, 3, 3, 1, 0, 1, 0, 2, 0, 1, 3, 1, 0, 3, 1, 1, 0, 2, 0, 3, 0, 0, 3
                ),
                listOf(
                    1, 0, 1, 0, 3, 1, 0, 3, 0, 0, 1, 2, 3, 0, 2, 3, 3, 0, 0, 1, 3, 0, 1, 3, 3, 0, 1, 2, 1, 1, 2, 0, 3, 1, 0, 1, 0, 0, 0, 1
                ),
                listOf(
                    2, 1, 1, 2, 0, 0, 1, 2, 1, 1, 1, 3, 0, 3, 0, 2, 1, 2, 1, 0, 0, 3, 1, 1, 2, 2, 0, 0, 0, 0, 2, 3, 3, 0, 2, 0, 3, 3, 0, 0
                ),
                listOf(
                    2, 1, 3, 3, 2, 2, 0, 0, 2, 1, 0, 1, 3, 2, 3, 0, 0, 1, 2, 1, 2, 1, 1, 3, 2, 2, 0, 0, 2, 0, 0, 0, 3, 3, 3, 3, 0, 0, 3, 1
                ),
                listOf(
                    2, 1, 0, 0, 0, 1, 2, 2, 2, 1, 1, 3, 2, 2, 2, 1, 0, 0, 0, 1, 3, 3, 1, 2, 1, 0, 3, 1, 3, 0, 1, 3, 1, 1, 2, 2, 3, 3, 2, 3
                ),
                listOf(
                    1, 2, 0, 2, 1, 1, 2, 3, 1, 0, 3, 3, 0, 2, 0, 3, 1, 1, 0, 2, 2, 3, 2, 2, 2, 1, 1, 1, 2, 0, 3, 0, 2, 0, 2, 1, 1, 2, 0, 2
                ),
                listOf(
                    2, 2, 0, 2, 2, 3, 2, 3, 1, 0, 3, 3, 2, 1, 1, 3, 1, 2, 2, 2, 3, 2, 3, 3, 2, 1, 3, 3, 0, 1, 1, 3, 2, 1, 2, 2, 3, 1, 2, 3
                ),
                listOf(
                    3, 0, 0, 0, 0, 1, 3, 2, 1, 2, 2, 3, 3, 2, 3, 2, 2, 0, 2, 1, 0, 2, 1, 3, 3, 0, 3, 0, 2, 3, 0, 3, 3, 3, 3, 3, 1, 3, 2, 3
                ),
                listOf(
                    2, 0, 2, 1, 3, 2, 1, 3, 1, 1, 0, 3, 2, 2, 2, 1, 1, 3, 0, 0, 2, 3, 1, 3, 1, 2, 3, 1, 2, 0, 0, 3, 1, 0, 3, 3, 0, 0, 3, 2
                ),
                listOf(
                    0, 3, 0, 1, 2, 3, 2, 1, 3, 3, 2, 0, 3, 2, 2, 0, 1, 0, 2, 1, 3, 1, 0, 1, 1, 0, 1, 3, 2, 1, 3, 1, 3, 3, 2, 1, 2, 0, 0, 1
                ),
                listOf(
                    3, 3, 3, 2, 0, 0, 3, 3, 3, 2, 1, 1, 1, 3, 2, 3, 0, 3, 2, 2, 3, 1, 1, 3, 3, 2, 0, 1, 1, 3, 0, 1, 0, 3, 2, 3, 3, 2, 3, 3
                ),
                listOf(
                    1, 3, 3, 2, 3, 1, 0, 2, 3, 1, 2, 2, 1, 1, 3, 2, 1, 0, 0, 0, 1, 1, 0, 1, 3, 0, 2, 1, 1, 3, 1, 1, 1, 1, 2, 2, 2, 2, 0, 2
                ),
                listOf(
                    0, 1, 2, 2, 1, 3, 1, 0, 3, 0, 1, 1, 3, 0, 1, 2, 2, 3, 0, 1, 1, 2, 0, 0, 2, 2, 2, 2, 3, 1, 1, 1, 0, 3, 3, 1, 0, 2, 0, 0
                ),
                listOf(
                    0, 0, 3, 3, 3, 2, 1, 0, 1, 3, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 1, 1, 3, 2, 3, 1, 3, 0, 3, 0, 3, 2, 1, 3, 2, 1, 0, 1, 2, 1
                ),
                listOf(
                    0, 3, 0, 0, 2, 2, 0, 3, 3, 0, 0, 3, 0, 1, 2, 0, 2, 0, 3, 1, 1, 3, 3, 3, 3, 2, 2, 2, 3, 0, 2, 0, 3, 0, 2, 2, 2, 3, 3, 0
                ),
                listOf(
                    1, 2, 2, 3, 2, 0, 0, 2, 3, 3, 3, 3, 3, 3, 3, 3, 2, 0, 0, 3, 2, 0, 3, 1, 2, 2, 2, 1, 1, 3, 3, 1, 1, 1, 1, 1, 0, 3, 0, 0
                ),
                listOf(
                    3, 1, 2, 1, 3, 3, 3, 2, 0, 1, 3, 0, 2, 1, 1, 1, 1, 3, 1, 1, 0, 2, 2, 1, 1, 3, 2, 3, 0, 1, 1, 2, 2, 2, 3, 0, 2, 3, 0, 0
                ),
                listOf(
                    3, 0, 1, 1, 2, 1, 3, 2, 0, 2, 2, 3, 0, 1, 2, 1, 0, 2, 1, 3, 2, 1, 2, 1, 1, 3, 2, 0, 2, 1, 3, 0, 0, 3, 2, 1, 2, 0, 1, 1
                ),
                listOf(
                    2, 2, 3, 1, 0, 3, 1, 0, 2, 0, 0, 2, 0, 2, 3, 0, 2, 2, 0, 1, 2, 1, 2, 1, 2, 2, 2, 2, 1, 0, 0, 1, 2, 0, 1, 1, 1, 0, 2, 3
                ),
                listOf(
                    3, 0, 1, 2, 1, 3, 2, 2, 0, 1, 1, 1, 2, 0, 1, 2, 3, 1, 2, 2, 2, 0, 2, 0, 3, 3, 3, 0, 2, 3, 0, 3, 3, 0, 0, 3, 3, 2, 1, 2
                ),
                listOf(
                    3, 0, 2, 1, 0, 3, 0, 1, 2, 1, 0, 2, 1, 0, 1, 3, 2, 2, 0, 3, 0, 3, 2, 2, 3, 1, 0, 0, 3, 2, 1, 1, 1, 2, 3, 0, 2, 0, 1, 3
                ),
                listOf(
                    0, 3, 2, 2, 3, 0, 2, 0, 2, 2, 1, 0, 3, 1, 1, 1, 3, 0, 2, 1, 3, 1, 2, 1, 3, 1, 1, 3, 1, 2, 3, 2, 2, 0, 3, 3, 3, 0, 2, 1
                ),
                listOf(
                    2, 0, 2, 0, 0, 0, 3, 0, 0, 3, 2, 1, 3, 1, 2, 1, 2, 2, 3, 2, 1, 3, 3, 1, 2, 0, 1, 2, 1, 0, 1, 0, 1, 1, 2, 1, 1, 2, 1, 1
                ),
                listOf(
                    1, 3, 0, 2, 1, 1, 3, 2, 3, 1, 3, 0, 0, 0, 0, 2, 1, 2, 3, 3, 2, 2, 0, 3, 1, 2, 2, 0, 3, 3, 2, 1, 1, 0, 2, 1, 3, 3, 3, 3
                ),
                listOf(
                    1, 3, 1, 1, 3, 3, 0, 2, 0, 1, 3, 0, 1, 1, 1, 3, 1, 3, 3, 1, 0, 0, 0, 3, 0, 2, 0, 2, 3, 3, 3, 2, 0, 3, 3, 3, 0, 0, 3, 2
                ),
                listOf(
                    1, 2, 1, 1, 3, 0, 1, 3, 1, 0, 2, 2, 1, 3, 2, 1, 3, 1, 1, 2, 3, 3, 3, 2, 0, 1, 0, 1, 1, 0, 2, 2, 2, 3, 2, 0, 2, 2, 0, 2
                ),
                listOf(
                    0, 0, 1, 1, 0, 0, 2, 1, 2, 2, 3, 1, 2, 1, 3, 0, 1, 0, 1, 0, 1, 1, 3, 1, 1, 1, 3, 1, 2, 3, 1, 1, 3, 3, 3, 2, 1, 0, 1, 0
                ),
                listOf(
                    0, 3, 0, 2, 2, 1, 1, 1, 0, 1, 0, 1, 1, 1, 3, 0, 0, 0, 3, 0, 2, 1, 3, 0, 2, 3, 1, 0, 3, 2, 0, 2, 2, 3, 2, 1, 1, 0, 3, 0
                ),
                listOf(
                    1, 2, 3, 0, 2, 1, 2, 0, 0, 1, 3, 2, 3, 3, 2, 3, 0, 1, 0, 2, 2, 2, 2, 1, 1, 0, 3, 3, 1, 1, 0, 0, 3, 3, 3, 0, 0, 2, 1, 0
                ),
                listOf(
                    1, 0, 2, 2, 2, 0, 0, 1, 0, 0, 3, 1, 0, 2, 3, 3, 2, 1, 1, 2, 2, 3, 0, 3, 3, 0, 3, 3, 1, 1, 1, 3, 3, 2, 1, 1, 1, 3, 3, 2
                ),
                listOf(
                    3, 3, 1, 0, 3, 3, 2, 3, 1, 3, 3, 3, 3, 0, 1, 3, 0, 1, 2, 1, 0, 1, 0, 0, 0, 0, 2, 0, 2, 0, 2, 1, 0, 0, 0, 2, 0, 1, 2, 2
                ),
                listOf(
                    3, 0, 2, 2, 3, 3, 2, 3, 1, 3, 0, 3, 2, 2, 0, 1, 2, 2, 2, 0, 1, 2, 1, 2, 2, 0, 1, 0, 1, 3, 3, 0, 2, 2, 2, 3, 2, 3, 0, 3
                ),
                listOf(
                    0, 3, 2, 2, 3, 2, 1, 1, 3, 2, 0, 1, 0, 0, 1, 1, 1, 2, 0, 0, 0, 3, 0, 2, 3, 2, 1, 3, 1, 2, 0, 3, 1, 1, 3, 1, 3, 2, 0, 3
                ),
                listOf(
                    1, 3, 2, 0, 3, 0, 3, 2, 3, 3, 0, 1, 1, 1, 2, 3, 1, 3, 1, 3, 2, 2, 2, 2, 2, 2, 0, 2, 3, 1, 1, 2, 0, 2, 0, 1, 2, 2, 0, 1
                ),
                listOf(
                    0, 0, 3, 0, 2, 1, 3, 1, 3, 0, 1, 1, 2, 2, 2, 1, 3, 3, 2, 2, 3, 2, 0, 0, 2, 2, 1, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0
                ),
                listOf(
                    2, 2, 3, 2, 0, 2, 2, 2, 1, 0, 0, 2, 1, 1, 2, 0, 3, 1, 0, 3, 1, 0, 1, 0, 1, 3, 1, 1, 3, 1, 3, 3, 3, 3, 3, 3, 0, 1, 1, 3
                ),
                listOf(
                    2, 2, 2, 1, 1, 2, 3, 1, 2, 3, 3, 3, 1, 1, 0, 0, 0, 1, 2, 2, 0, 1, 2, 0, 2, 1, 2, 2, 1, 1, 2, 1, 3, 3, 1, 3, 2, 2, 3, 3
                ),
                listOf(
                    3, 1, 1, 2, 2, 1, 3, 0, 2, 0, 3, 0, 1, 3, 1, 2, 3, 3, 2, 2, 3, 3, 1, 1, 0, 1, 2, 2, 0, 1, 3, 2, 0, 2, 3, 3, 0, 3, 1, 0
                ),
                listOf(
                    1, 0, 2, 2, 0, 1, 2, 1, 0, 2, 0, 0, 0, 3, 1, 1, 1, 3, 3, 3, 1, 0, 1, 2, 0, 0, 1, 1, 3, 2, 2, 0, 0, 0, 2, 2, 1, 0, 2, 3
                )
            ), listOf(
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(1, 25),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 25),
                Tank(3, 20),
                Tank(3, 25),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk99(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_legendary_020", "Legendary", 40, 60, listOf(
                listOf(
                    2, 1, 1, 3, 0, 3, 1, 1, 0, 0, 1, 1, 3, 0, 2, 1, 1, 0, 2, 2, 0, 3, 2, 0, 3, 2, 2, 1, 1, 0, 3, 3, 0, 0, 1, 1, 3, 1, 2, 3
                ),
                listOf(
                    2, 3, 3, 0, 2, 3, 2, 1, 3, 2, 2, 0, 1, 1, 2, 0, 0, 3, 1, 2, 1, 0, 2, 2, 2, 2, 3, 3, 0, 0, 1, 2, 1, 0, 3, 2, 2, 3, 2, 2
                ),
                listOf(
                    2, 2, 1, 2, 1, 2, 1, 2, 0, 0, 0, 2, 3, 3, 3, 2, 3, 3, 3, 3, 3, 0, 1, 0, 0, 2, 3, 3, 2, 3, 2, 1, 1, 1, 0, 2, 1, 1, 2, 3
                ),
                listOf(
                    2, 1, 0, 1, 1, 0, 0, 2, 3, 2, 3, 0, 0, 3, 2, 1, 1, 1, 2, 1, 3, 1, 3, 2, 0, 1, 3, 0, 1, 2, 2, 2, 1, 0, 2, 1, 0, 1, 3, 0
                ),
                listOf(
                    3, 0, 3, 0, 1, 2, 1, 1, 0, 2, 0, 1, 3, 1, 2, 2, 2, 2, 1, 2, 0, 3, 1, 3, 2, 3, 2, 0, 0, 0, 1, 3, 3, 2, 0, 2, 3, 0, 3, 0
                ),
                listOf(
                    2, 3, 0, 1, 1, 0, 1, 0, 3, 2, 0, 1, 0, 2, 0, 1, 0, 1, 2, 1, 1, 3, 0, 0, 0, 2, 2, 0, 0, 1, 0, 1, 0, 1, 3, 1, 1, 2, 2, 2
                ),
                listOf(
                    0, 2, 2, 3, 3, 0, 3, 0, 2, 3, 2, 0, 1, 0, 2, 3, 3, 0, 2, 1, 2, 1, 0, 1, 1, 0, 1, 1, 2, 0, 3, 3, 2, 1, 1, 0, 3, 2, 3, 1
                ),
                listOf(
                    3, 3, 2, 1, 3, 0, 3, 2, 3, 0, 3, 0, 3, 2, 0, 1, 2, 3, 3, 0, 0, 3, 1, 0, 0, 3, 3, 3, 3, 3, 1, 2, 3, 0, 1, 2, 1, 1, 3, 2
                ),
                listOf(
                    0, 0, 1, 0, 2, 0, 3, 3, 0, 1, 0, 3, 1, 0, 3, 1, 3, 3, 1, 0, 2, 0, 2, 2, 2, 2, 1, 3, 0, 3, 3, 0, 3, 0, 2, 2, 2, 0, 0, 1
                ),
                listOf(
                    3, 1, 0, 3, 1, 0, 2, 0, 1, 2, 3, 1, 0, 0, 0, 1, 3, 3, 0, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 1, 1, 2, 0, 2, 0, 2, 1, 3
                ),
                listOf(
                    2, 1, 2, 2, 0, 0, 3, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 3, 2, 3, 1, 3, 2, 3, 2, 1, 1, 0, 2, 3, 2, 3, 2, 0, 2, 0, 2, 3, 3
                ),
                listOf(
                    1, 3, 1, 0, 3, 3, 3, 3, 0, 0, 1, 2, 0, 1, 1, 2, 3, 2, 1, 2, 0, 0, 1, 1, 3, 3, 2, 2, 3, 3, 1, 0, 1, 1, 1, 2, 2, 0, 1, 3
                ),
                listOf(
                    0, 0, 2, 2, 0, 0, 0, 3, 0, 0, 2, 3, 1, 3, 3, 2, 0, 0, 3, 1, 1, 1, 3, 0, 2, 0, 3, 3, 0, 0, 2, 0, 0, 0, 3, 0, 1, 2, 0, 3
                ),
                listOf(
                    1, 1, 3, 3, 1, 1, 1, 3, 0, 1, 1, 1, 0, 2, 2, 1, 0, 3, 2, 1, 1, 2, 3, 0, 2, 1, 0, 2, 3, 0, 1, 3, 2, 3, 3, 3, 1, 2, 0, 3
                ),
                listOf(
                    2, 0, 3, 1, 2, 1, 1, 2, 3, 0, 3, 3, 2, 1, 1, 0, 3, 2, 2, 2, 3, 1, 3, 2, 0, 2, 0, 1, 1, 0, 1, 3, 3, 1, 1, 1, 0, 0, 0, 2
                ),
                listOf(
                    1, 2, 2, 3, 2, 3, 1, 0, 1, 1, 2, 2, 1, 1, 1, 2, 3, 1, 2, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 2, 3, 1, 3, 3, 2, 3, 2, 1, 0
                ),
                listOf(
                    2, 3, 3, 3, 0, 1, 1, 0, 0, 3, 0, 3, 1, 3, 1, 3, 1, 2, 1, 2, 0, 1, 0, 3, 2, 0, 1, 0, 2, 3, 3, 0, 2, 2, 0, 3, 1, 3, 1, 3
                ),
                listOf(
                    2, 0, 2, 3, 3, 1, 0, 2, 0, 3, 2, 3, 3, 2, 3, 0, 0, 0, 0, 3, 2, 0, 0, 3, 1, 0, 1, 1, 2, 1, 3, 2, 2, 0, 3, 0, 0, 0, 2, 0
                ),
                listOf(
                    3, 1, 2, 3, 2, 3, 1, 3, 2, 2, 0, 3, 3, 0, 3, 0, 1, 2, 0, 3, 0, 1, 1, 1, 0, 1, 1, 3, 2, 1, 2, 2, 1, 2, 3, 1, 3, 3, 0, 3
                ),
                listOf(
                    3, 0, 3, 3, 0, 2, 1, 0, 3, 3, 3, 0, 0, 3, 3, 0, 1, 1, 1, 1, 1, 2, 2, 3, 0, 3, 3, 1, 0, 1, 2, 3, 2, 2, 1, 0, 1, 3, 1, 1
                ),
                listOf(
                    1, 1, 1, 2, 3, 1, 2, 3, 0, 0, 3, 2, 2, 3, 2, 1, 3, 3, 0, 3, 2, 2, 2, 2, 3, 1, 0, 1, 1, 1, 1, 1, 3, 3, 0, 2, 3, 0, 2, 3
                ),
                listOf(
                    1, 2, 3, 0, 0, 3, 1, 0, 3, 1, 2, 2, 0, 3, 2, 2, 1, 1, 2, 1, 2, 3, 0, 3, 1, 0, 0, 2, 2, 2, 3, 3, 1, 3, 0, 3, 1, 3, 1, 3
                ),
                listOf(
                    0, 1, 2, 0, 0, 2, 0, 0, 2, 0, 1, 1, 3, 3, 0, 2, 2, 1, 1, 1, 2, 0, 2, 0, 1, 2, 1, 3, 0, 2, 2, 3, 2, 2, 1, 2, 3, 0, 0, 0
                ),
                listOf(
                    1, 2, 3, 1, 2, 1, 0, 2, 2, 2, 0, 1, 3, 3, 1, 2, 0, 3, 2, 1, 1, 0, 3, 3, 3, 0, 3, 2, 1, 3, 0, 0, 1, 2, 1, 2, 0, 3, 1, 1
                ),
                listOf(
                    0, 2, 3, 1, 1, 3, 1, 0, 3, 3, 1, 3, 3, 1, 3, 2, 0, 0, 3, 2, 0, 0, 3, 3, 1, 1, 1, 2, 1, 0, 0, 0, 3, 1, 2, 3, 3, 2, 0, 1
                ),
                listOf(
                    3, 2, 0, 3, 0, 2, 1, 0, 0, 2, 2, 0, 0, 3, 1, 0, 0, 3, 3, 2, 0, 3, 3, 1, 2, 0, 2, 0, 2, 0, 1, 1, 1, 0, 1, 0, 3, 1, 0, 1
                ),
                listOf(
                    1, 1, 2, 0, 1, 3, 1, 3, 3, 1, 3, 2, 3, 3, 0, 0, 2, 2, 3, 2, 3, 0, 1, 1, 2, 3, 0, 1, 0, 1, 1, 0, 3, 1, 1, 1, 1, 0, 2, 3
                ),
                listOf(
                    2, 3, 0, 0, 0, 0, 3, 0, 2, 3, 0, 2, 2, 0, 1, 2, 0, 1, 0, 1, 1, 2, 2, 2, 2, 3, 1, 0, 0, 0, 2, 0, 0, 3, 3, 2, 2, 0, 2, 2
                ),
                listOf(
                    3, 2, 2, 0, 1, 3, 2, 3, 0, 2, 2, 2, 1, 2, 1, 0, 1, 3, 1, 3, 3, 3, 0, 0, 0, 2, 2, 1, 2, 1, 1, 0, 1, 0, 3, 3, 1, 3, 2, 2
                ),
                listOf(
                    0, 1, 2, 2, 3, 1, 1, 3, 1, 0, 3, 3, 1, 0, 1, 2, 1, 1, 1, 3, 0, 3, 3, 3, 2, 1, 3, 3, 0, 2, 0, 2, 1, 3, 3, 2, 1, 3, 0, 3
                ),
                listOf(
                    1, 0, 3, 0, 2, 0, 3, 2, 0, 3, 2, 1, 0, 1, 1, 3, 0, 2, 0, 0, 2, 0, 3, 2, 3, 1, 0, 1, 1, 0, 1, 2, 3, 1, 0, 3, 0, 2, 0, 2
                ),
                listOf(
                    1, 3, 1, 2, 2, 3, 3, 0, 2, 0, 0, 2, 1, 3, 0, 1, 1, 3, 3, 0, 0, 3, 1, 1, 2, 2, 3, 2, 0, 3, 1, 2, 3, 0, 0, 1, 3, 2, 0, 3
                ),
                listOf(
                    0, 1, 0, 3, 1, 2, 2, 0, 2, 1, 3, 2, 0, 3, 1, 0, 0, 0, 0, 1, 0, 0, 1, 3, 0, 0, 3, 3, 0, 3, 0, 3, 1, 1, 3, 3, 0, 3, 3, 3
                ),
                listOf(
                    2, 3, 0, 3, 1, 1, 3, 0, 3, 1, 0, 0, 1, 3, 3, 3, 2, 2, 2, 0, 0, 3, 2, 1, 3, 3, 2, 2, 0, 2, 1, 2, 1, 1, 3, 0, 3, 2, 1, 1
                ),
                listOf(
                    3, 0, 0, 3, 3, 1, 1, 1, 3, 3, 1, 0, 0, 1, 0, 0, 0, 3, 3, 3, 2, 3, 1, 1, 3, 1, 0, 1, 1, 2, 1, 0, 2, 2, 0, 1, 0, 0, 2, 0
                ),
                listOf(
                    0, 1, 1, 2, 0, 1, 0, 0, 1, 3, 1, 3, 0, 2, 0, 0, 0, 1, 3, 3, 1, 1, 0, 0, 2, 1, 1, 0, 2, 1, 1, 2, 3, 2, 1, 0, 2, 0, 0, 0
                ),
                listOf(
                    2, 1, 1, 0, 3, 0, 3, 0, 2, 1, 0, 0, 0, 1, 2, 1, 0, 1, 1, 3, 3, 3, 0, 0, 2, 0, 3, 1, 2, 0, 3, 2, 0, 1, 2, 2, 1, 0, 1, 3
                ),
                listOf(
                    1, 3, 2, 2, 0, 2, 0, 0, 2, 2, 0, 0, 1, 2, 2, 1, 0, 2, 1, 2, 2, 1, 2, 1, 2, 0, 2, 3, 1, 0, 3, 0, 0, 0, 2, 2, 2, 3, 1, 2
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 2, 1, 2, 0, 0, 3, 3, 2, 1, 3, 1, 1, 2, 2, 0, 0, 2, 2, 0, 2, 1, 0, 2, 1, 1, 1, 3, 3, 3, 3, 3, 2, 1, 1
                ),
                listOf(
                    3, 2, 0, 0, 1, 2, 1, 1, 1, 3, 2, 0, 3, 2, 0, 0, 2, 0, 3, 0, 1, 1, 0, 1, 3, 1, 0, 3, 2, 1, 2, 2, 2, 1, 1, 2, 3, 2, 0, 1
                ),
                listOf(
                    0, 3, 3, 1, 2, 2, 2, 1, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 1, 3, 3, 0, 2, 1, 0, 2, 0, 2, 2, 2, 2, 3, 2, 0, 2, 3, 1, 1, 2, 0
                ),
                listOf(
                    3, 3, 3, 2, 2, 1, 1, 3, 1, 2, 0, 3, 0, 1, 1, 0, 3, 3, 2, 1, 3, 1, 3, 2, 2, 1, 1, 0, 2, 2, 0, 1, 3, 2, 3, 1, 3, 0, 1, 3
                ),
                listOf(
                    3, 0, 0, 0, 0, 0, 0, 1, 0, 2, 3, 0, 1, 1, 2, 0, 0, 1, 2, 0, 0, 2, 3, 1, 3, 1, 3, 3, 2, 2, 2, 3, 2, 3, 1, 2, 0, 2, 3, 1
                ),
                listOf(
                    0, 3, 0, 0, 3, 2, 2, 3, 2, 3, 2, 0, 0, 0, 0, 3, 3, 3, 0, 3, 0, 0, 2, 1, 1, 3, 0, 3, 0, 0, 0, 2, 2, 3, 3, 2, 3, 2, 1, 0
                ),
                listOf(
                    0, 0, 2, 1, 0, 0, 2, 0, 3, 3, 0, 1, 2, 3, 0, 1, 3, 2, 2, 2, 0, 2, 1, 3, 0, 3, 0, 3, 2, 0, 2, 2, 2, 0, 1, 2, 0, 0, 1, 1
                ),
                listOf(
                    0, 3, 3, 1, 2, 0, 0, 0, 2, 0, 2, 2, 3, 1, 0, 1, 0, 3, 1, 2, 0, 3, 1, 0, 1, 1, 0, 1, 1, 2, 2, 1, 2, 0, 1, 2, 3, 2, 1, 3
                ),
                listOf(
                    2, 0, 0, 3, 1, 3, 0, 0, 2, 3, 0, 2, 2, 3, 3, 1, 2, 1, 2, 0, 2, 3, 0, 1, 1, 0, 2, 1, 2, 1, 2, 0, 0, 3, 3, 3, 3, 2, 3, 1
                ),
                listOf(
                    3, 3, 0, 0, 3, 0, 3, 2, 1, 1, 3, 1, 0, 1, 2, 0, 3, 2, 0, 2, 3, 3, 0, 1, 3, 0, 0, 3, 3, 0, 0, 2, 3, 3, 2, 2, 0, 3, 0, 2
                ),
                listOf(
                    1, 3, 2, 2, 3, 3, 3, 1, 2, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 2, 2, 1, 3, 0, 0, 2, 3, 1, 1, 2, 3, 2, 3, 3, 3, 1, 1, 3, 3, 0
                ),
                listOf(
                    0, 1, 2, 3, 3, 2, 3, 3, 2, 3, 1, 1, 2, 1, 0, 2, 2, 2, 2, 2, 3, 2, 3, 2, 1, 3, 3, 1, 3, 2, 2, 2, 3, 2, 3, 3, 2, 3, 1, 2
                ),
                listOf(
                    2, 2, 0, 2, 1, 1, 1, 2, 0, 2, 3, 1, 2, 2, 1, 1, 1, 2, 2, 3, 3, 2, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 2, 1, 2, 1, 1, 2, 3, 3
                ),
                listOf(
                    3, 3, 0, 3, 3, 1, 2, 1, 0, 2, 2, 3, 0, 0, 3, 3, 3, 3, 0, 0, 1, 0, 2, 3, 2, 0, 0, 3, 2, 1, 1, 0, 3, 3, 3, 1, 0, 1, 0, 3
                ),
                listOf(
                    0, 3, 2, 0, 2, 3, 3, 0, 2, 3, 1, 0, 3, 3, 3, 3, 0, 0, 0, 3, 3, 2, 3, 2, 2, 2, 2, 3, 0, 1, 2, 0, 2, 3, 0, 3, 3, 2, 1, 1
                ),
                listOf(
                    2, 1, 1, 1, 0, 3, 1, 1, 1, 1, 0, 3, 0, 3, 2, 1, 3, 2, 3, 1, 1, 3, 3, 0, 3, 0, 3, 0, 3, 0, 1, 2, 2, 3, 0, 1, 1, 0, 3, 2
                ),
                listOf(
                    1, 0, 1, 0, 0, 3, 3, 2, 2, 0, 2, 1, 0, 2, 0, 0, 3, 0, 1, 2, 3, 0, 3, 0, 0, 3, 2, 1, 0, 0, 3, 1, 2, 0, 3, 2, 0, 1, 3, 1
                ),
                listOf(
                    1, 3, 3, 0, 0, 3, 1, 2, 2, 0, 3, 2, 2, 0, 3, 2, 1, 1, 2, 2, 3, 1, 0, 3, 2, 3, 0, 1, 0, 2, 1, 3, 2, 0, 3, 3, 0, 2, 0, 0
                ),
                listOf(
                    0, 2, 3, 2, 0, 1, 0, 2, 3, 3, 1, 0, 3, 3, 0, 1, 0, 2, 3, 1, 3, 3, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 2, 2, 3, 2, 0
                ),
                listOf(
                    0, 0, 1, 1, 3, 1, 2, 1, 1, 2, 3, 0, 1, 3, 0, 2, 2, 2, 3, 2, 3, 3, 3, 0, 2, 3, 3, 3, 3, 0, 2, 1, 1, 3, 0, 0, 2, 3, 2, 0
                ),
                listOf(
                    2, 1, 0, 2, 2, 3, 3, 2, 2, 1, 0, 2, 2, 1, 1, 0, 3, 2, 2, 1, 2, 1, 2, 2, 3, 2, 2, 0, 2, 0, 3, 3, 2, 1, 1, 3, 0, 1, 0, 3
                ),
                listOf(
                    2, 0, 1, 1, 1, 3, 3, 3, 1, 3, 0, 0, 2, 1, 2, 3, 3, 1, 0, 1, 0, 2, 3, 2, 1, 3, 2, 0, 3, 3, 1, 2, 2, 3, 1, 3, 3, 3, 0, 3
                )
            ), listOf(
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 25),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 25),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 10)
            )),
        )
    }

    private fun getChunk100(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_001", "Master", 20, 40, listOf(
                listOf(
                    3, 0, 3, 1, 2, 2, 1, 3, 3, 0, 3, 3, 1, 0, 2, 2, 1, 2, 1, 3
                ),
                listOf(
                    0, 2, 0, 2, 1, 3, 0, 0, 0, 2, 2, 2, 0, 2, 3, 3, 1, 1, 1, 0
                ),
                listOf(
                    1, 1, 1, 2, 0, 3, 2, 0, 3, 0, 2, 2, 2, 1, 3, 3, 3, 3, 3, 2
                ),
                listOf(
                    1, 1, 2, 3, 0, 1, 2, 1, 2, 1, 0, 3, 3, 1, 3, 2, 0, 3, 3, 0
                ),
                listOf(
                    1, 0, 0, 0, 3, 2, 2, 3, 0, 3, 2, 3, 1, 2, 3, 2, 2, 0, 2, 0
                ),
                listOf(
                    1, 1, 1, 0, 2, 1, 0, 0, 0, 0, 3, 3, 1, 3, 3, 3, 3, 0, 0, 0
                ),
                listOf(
                    3, 2, 1, 0, 0, 0, 3, 1, 2, 0, 3, 3, 2, 2, 1, 2, 2, 0, 1, 0
                ),
                listOf(
                    2, 3, 2, 1, 2, 2, 2, 2, 2, 0, 2, 1, 2, 1, 2, 1, 2, 0, 1, 3
                ),
                listOf(
                    0, 3, 2, 1, 0, 2, 0, 2, 2, 2, 2, 1, 0, 0, 3, 0, 0, 0, 0, 2
                ),
                listOf(
                    2, 2, 1, 1, 3, 1, 2, 3, 2, 1, 1, 0, 1, 0, 0, 0, 1, 0, 3, 3
                ),
                listOf(
                    3, 0, 3, 2, 1, 1, 2, 0, 3, 3, 0, 0, 0, 1, 2, 0, 3, 1, 0, 2
                ),
                listOf(
                    3, 3, 2, 3, 0, 0, 0, 1, 3, 3, 2, 0, 1, 1, 1, 2, 1, 1, 0, 1
                ),
                listOf(
                    3, 0, 1, 3, 0, 1, 1, 0, 3, 1, 0, 1, 1, 0, 1, 1, 2, 2, 2, 1
                ),
                listOf(
                    0, 1, 1, 0, 0, 1, 0, 3, 3, 3, 3, 1, 0, 1, 0, 3, 2, 0, 2, 0
                ),
                listOf(
                    0, 0, 1, 0, 0, 2, 2, 2, 1, 0, 2, 0, 0, 2, 3, 3, 2, 3, 3, 0
                ),
                listOf(
                    0, 0, 2, 3, 1, 1, 2, 0, 0, 0, 2, 1, 1, 1, 0, 3, 2, 1, 2, 1
                ),
                listOf(
                    3, 2, 2, 2, 0, 3, 0, 1, 0, 3, 0, 1, 1, 2, 2, 1, 2, 0, 3, 3
                ),
                listOf(
                    2, 1, 2, 2, 2, 2, 0, 2, 3, 2, 3, 2, 3, 1, 1, 3, 1, 0, 0, 0
                ),
                listOf(
                    0, 0, 2, 1, 1, 2, 3, 3, 2, 3, 3, 1, 0, 0, 1, 1, 3, 3, 3, 0
                ),
                listOf(
                    2, 3, 2, 3, 3, 2, 0, 0, 3, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 1
                ),
                listOf(
                    2, 0, 2, 2, 2, 1, 2, 0, 3, 3, 0, 2, 1, 3, 0, 1, 1, 2, 2, 3
                ),
                listOf(
                    3, 3, 3, 2, 1, 2, 3, 2, 2, 2, 0, 2, 0, 0, 1, 0, 3, 0, 2, 2
                ),
                listOf(
                    1, 1, 3, 2, 3, 2, 2, 3, 1, 1, 3, 3, 0, 2, 1, 2, 0, 2, 2, 0
                ),
                listOf(
                    2, 0, 2, 0, 2, 3, 1, 0, 0, 2, 1, 3, 3, 0, 0, 2, 0, 1, 0, 2
                ),
                listOf(
                    0, 0, 3, 1, 2, 1, 3, 3, 2, 2, 2, 2, 0, 3, 1, 3, 3, 0, 0, 0
                ),
                listOf(
                    0, 2, 0, 2, 1, 3, 0, 2, 1, 0, 0, 2, 3, 0, 1, 2, 2, 3, 1, 3
                ),
                listOf(
                    0, 2, 0, 2, 1, 1, 2, 2, 1, 3, 0, 1, 3, 1, 0, 0, 0, 0, 3, 3
                ),
                listOf(
                    1, 3, 0, 2, 1, 1, 1, 3, 2, 2, 2, 0, 3, 3, 3, 2, 1, 1, 2, 0
                ),
                listOf(
                    0, 3, 2, 1, 0, 1, 1, 2, 1, 0, 3, 1, 1, 3, 3, 2, 2, 3, 3, 2
                ),
                listOf(
                    2, 3, 1, 1, 2, 1, 2, 1, 0, 2, 3, 3, 2, 3, 3, 0, 0, 1, 2, 0
                ),
                listOf(
                    1, 0, 1, 2, 2, 0, 2, 0, 1, 0, 2, 3, 2, 2, 3, 3, 2, 0, 0, 2
                ),
                listOf(
                    1, 3, 0, 0, 2, 2, 1, 1, 3, 0, 0, 2, 2, 1, 1, 0, 2, 2, 0, 2
                ),
                listOf(
                    2, 0, 0, 0, 1, 0, 2, 3, 3, 0, 0, 3, 1, 2, 0, 1, 0, 2, 0, 3
                ),
                listOf(
                    0, 0, 2, 2, 3, 1, 2, 1, 1, 2, 0, 3, 2, 3, 0, 1, 2, 2, 1, 3
                ),
                listOf(
                    2, 2, 0, 1, 3, 0, 2, 3, 0, 1, 1, 1, 1, 0, 1, 0, 0, 3, 2, 3
                ),
                listOf(
                    2, 3, 3, 2, 3, 1, 2, 0, 3, 3, 2, 2, 1, 3, 3, 0, 2, 1, 3, 0
                ),
                listOf(
                    1, 0, 2, 3, 1, 0, 0, 0, 0, 1, 3, 1, 3, 0, 1, 0, 1, 0, 2, 3
                ),
                listOf(
                    1, 0, 0, 0, 2, 0, 1, 2, 2, 1, 1, 0, 2, 0, 2, 3, 1, 1, 0, 0
                ),
                listOf(
                    0, 2, 3, 1, 0, 1, 0, 2, 3, 1, 0, 1, 2, 1, 0, 2, 0, 1, 3, 0
                ),
                listOf(
                    1, 2, 2, 3, 0, 3, 0, 2, 0, 0, 3, 3, 3, 2, 2, 2, 2, 2, 1, 0
                )
            ), listOf(
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 25),
                Tank(2, 15),
                Tank(2, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 25),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk101(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_002", "Master", 20, 40, listOf(
                listOf(
                    0, 2, 3, 0, 1, 2, 0, 1, 3, 1, 1, 3, 3, 3, 2, 1, 0, 3, 2, 3
                ),
                listOf(
                    0, 3, 3, 1, 3, 1, 3, 1, 1, 1, 2, 3, 1, 1, 1, 0, 0, 1, 2, 0
                ),
                listOf(
                    1, 3, 2, 2, 1, 1, 3, 3, 3, 3, 3, 1, 2, 2, 2, 0, 1, 0, 1, 3
                ),
                listOf(
                    3, 3, 3, 0, 2, 0, 2, 0, 3, 2, 1, 3, 1, 3, 1, 0, 2, 2, 1, 1
                ),
                listOf(
                    0, 1, 0, 1, 2, 2, 2, 0, 1, 0, 1, 2, 0, 2, 0, 0, 0, 3, 1, 3
                ),
                listOf(
                    0, 2, 1, 0, 0, 1, 0, 2, 3, 1, 2, 2, 3, 2, 0, 1, 2, 2, 1, 0
                ),
                listOf(
                    2, 1, 3, 2, 1, 2, 2, 3, 0, 0, 0, 3, 1, 0, 0, 0, 3, 0, 2, 0
                ),
                listOf(
                    2, 0, 0, 0, 1, 3, 0, 3, 0, 1, 3, 2, 2, 3, 1, 2, 1, 1, 3, 0
                ),
                listOf(
                    1, 3, 3, 0, 1, 3, 3, 2, 3, 2, 2, 2, 0, 1, 3, 3, 1, 0, 2, 0
                ),
                listOf(
                    2, 3, 1, 0, 0, 2, 0, 3, 2, 0, 0, 0, 0, 0, 2, 3, 3, 2, 2, 2
                ),
                listOf(
                    0, 3, 1, 2, 3, 1, 2, 3, 0, 2, 1, 2, 2, 3, 2, 1, 2, 2, 3, 1
                ),
                listOf(
                    3, 3, 1, 2, 3, 3, 2, 2, 2, 3, 3, 0, 0, 3, 3, 0, 2, 1, 1, 3
                ),
                listOf(
                    0, 3, 2, 3, 2, 1, 3, 3, 0, 2, 3, 2, 1, 1, 3, 0, 2, 2, 3, 0
                ),
                listOf(
                    1, 0, 1, 3, 1, 0, 3, 0, 3, 2, 0, 1, 1, 2, 3, 1, 1, 1, 2, 1
                ),
                listOf(
                    1, 2, 2, 2, 3, 1, 3, 1, 3, 0, 3, 0, 0, 3, 1, 3, 3, 3, 2, 3
                ),
                listOf(
                    3, 1, 1, 2, 2, 1, 3, 1, 3, 3, 2, 3, 0, 1, 2, 0, 3, 1, 1, 0
                ),
                listOf(
                    3, 0, 1, 3, 1, 3, 3, 3, 2, 3, 0, 2, 0, 1, 0, 1, 3, 3, 0, 3
                ),
                listOf(
                    3, 3, 3, 3, 0, 3, 1, 3, 3, 1, 2, 0, 2, 0, 1, 0, 3, 1, 3, 2
                ),
                listOf(
                    0, 3, 0, 2, 1, 0, 0, 3, 3, 3, 3, 0, 3, 0, 0, 2, 2, 0, 3, 1
                ),
                listOf(
                    3, 0, 2, 2, 0, 3, 1, 3, 3, 1, 3, 2, 0, 3, 2, 1, 2, 3, 3, 1
                ),
                listOf(
                    2, 3, 1, 0, 0, 3, 0, 3, 3, 1, 3, 3, 3, 3, 2, 1, 3, 2, 3, 0
                ),
                listOf(
                    3, 3, 0, 3, 2, 3, 1, 3, 3, 2, 3, 1, 3, 2, 2, 0, 3, 2, 0, 1
                ),
                listOf(
                    3, 2, 2, 1, 0, 0, 3, 2, 2, 1, 2, 1, 2, 0, 3, 2, 1, 2, 1, 1
                ),
                listOf(
                    1, 0, 2, 3, 3, 2, 1, 2, 0, 2, 3, 1, 1, 0, 1, 2, 1, 0, 1, 1
                ),
                listOf(
                    3, 1, 1, 0, 0, 0, 1, 3, 1, 1, 2, 3, 1, 1, 2, 3, 3, 3, 3, 1
                ),
                listOf(
                    1, 1, 1, 3, 0, 3, 2, 0, 1, 1, 3, 2, 2, 0, 2, 2, 1, 1, 1, 3
                ),
                listOf(
                    2, 3, 1, 0, 3, 2, 2, 3, 2, 0, 2, 0, 0, 3, 1, 0, 0, 3, 0, 1
                ),
                listOf(
                    0, 1, 0, 3, 1, 3, 2, 3, 3, 3, 2, 2, 3, 0, 1, 3, 0, 3, 3, 1
                ),
                listOf(
                    1, 3, 1, 3, 1, 2, 2, 3, 2, 2, 3, 1, 3, 2, 3, 3, 0, 2, 0, 3
                ),
                listOf(
                    3, 3, 0, 3, 1, 2, 3, 0, 2, 0, 2, 0, 2, 0, 2, 1, 1, 1, 2, 2
                ),
                listOf(
                    2, 1, 3, 0, 2, 1, 1, 3, 3, 1, 1, 3, 0, 0, 0, 3, 3, 3, 0, 2
                ),
                listOf(
                    0, 1, 3, 3, 1, 0, 1, 3, 0, 0, 3, 3, 1, 1, 0, 0, 3, 2, 0, 1
                ),
                listOf(
                    0, 3, 1, 3, 2, 0, 2, 2, 3, 3, 0, 2, 1, 2, 0, 3, 2, 2, 2, 0
                ),
                listOf(
                    0, 2, 1, 3, 1, 1, 1, 2, 2, 3, 2, 2, 3, 2, 0, 1, 3, 0, 3, 0
                ),
                listOf(
                    3, 3, 0, 3, 3, 1, 3, 0, 3, 1, 2, 1, 1, 0, 2, 0, 2, 0, 1, 3
                ),
                listOf(
                    1, 0, 2, 0, 1, 3, 1, 1, 1, 3, 1, 3, 2, 1, 1, 3, 1, 1, 3, 3
                ),
                listOf(
                    1, 2, 2, 1, 1, 3, 1, 0, 2, 0, 2, 2, 0, 1, 1, 0, 2, 1, 3, 0
                ),
                listOf(
                    2, 2, 2, 1, 2, 1, 2, 0, 0, 3, 1, 3, 0, 1, 0, 0, 0, 2, 3, 2
                ),
                listOf(
                    3, 2, 3, 1, 3, 3, 2, 3, 2, 0, 3, 1, 3, 1, 2, 2, 3, 0, 0, 3
                ),
                listOf(
                    3, 3, 1, 2, 2, 1, 3, 1, 3, 2, 1, 0, 0, 2, 2, 3, 2, 2, 3, 2
                )
            ), listOf(
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 25),
                Tank(1, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk102(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_003", "Master", 20, 40, listOf(
                listOf(
                    1, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 1, 1, 2, 0, 0, 3, 3, 3, 3
                ),
                listOf(
                    1, 0, 3, 1, 1, 3, 1, 0, 3, 0, 1, 1, 1, 3, 1, 0, 2, 2, 1, 2
                ),
                listOf(
                    0, 0, 1, 0, 1, 1, 2, 3, 1, 1, 3, 0, 1, 2, 2, 3, 0, 2, 3, 2
                ),
                listOf(
                    3, 3, 1, 3, 2, 3, 1, 1, 1, 0, 3, 0, 1, 0, 0, 0, 3, 1, 3, 0
                ),
                listOf(
                    1, 1, 0, 0, 3, 3, 1, 1, 3, 1, 0, 0, 1, 2, 1, 2, 1, 1, 3, 1
                ),
                listOf(
                    1, 1, 0, 2, 1, 3, 1, 1, 2, 1, 1, 3, 1, 3, 3, 3, 0, 0, 1, 3
                ),
                listOf(
                    1, 1, 2, 1, 1, 3, 1, 1, 1, 0, 2, 0, 2, 0, 1, 1, 0, 3, 2, 2
                ),
                listOf(
                    2, 1, 2, 1, 0, 2, 3, 2, 2, 0, 1, 1, 3, 2, 2, 0, 1, 1, 2, 0
                ),
                listOf(
                    3, 3, 0, 3, 0, 0, 1, 2, 1, 1, 1, 0, 1, 3, 1, 0, 2, 2, 2, 2
                ),
                listOf(
                    3, 3, 3, 0, 1, 2, 3, 2, 3, 1, 3, 3, 3, 3, 0, 0, 2, 3, 3, 1
                ),
                listOf(
                    2, 2, 0, 1, 2, 0, 3, 1, 2, 0, 2, 3, 0, 1, 3, 0, 2, 0, 1, 1
                ),
                listOf(
                    1, 3, 3, 3, 3, 3, 1, 2, 2, 3, 1, 1, 0, 3, 1, 1, 0, 0, 2, 3
                ),
                listOf(
                    0, 2, 0, 1, 0, 3, 1, 3, 1, 0, 3, 3, 2, 2, 0, 1, 1, 0, 1, 3
                ),
                listOf(
                    3, 1, 0, 3, 0, 3, 3, 0, 3, 1, 1, 2, 0, 0, 1, 1, 0, 1, 0, 2
                ),
                listOf(
                    3, 0, 1, 3, 2, 2, 2, 3, 0, 1, 0, 0, 0, 3, 1, 3, 1, 2, 1, 2
                ),
                listOf(
                    3, 2, 0, 0, 1, 3, 0, 0, 1, 1, 3, 2, 0, 2, 0, 2, 3, 3, 1, 0
                ),
                listOf(
                    0, 0, 3, 1, 3, 0, 0, 1, 3, 3, 2, 0, 1, 0, 3, 1, 3, 0, 1, 2
                ),
                listOf(
                    2, 0, 3, 0, 0, 3, 0, 2, 1, 0, 0, 3, 0, 0, 1, 3, 0, 2, 1, 0
                ),
                listOf(
                    2, 0, 0, 1, 1, 3, 1, 3, 2, 1, 1, 1, 3, 1, 1, 3, 2, 0, 1, 3
                ),
                listOf(
                    1, 1, 3, 0, 0, 3, 2, 2, 1, 1, 2, 1, 1, 1, 0, 2, 1, 1, 0, 2
                ),
                listOf(
                    3, 3, 0, 2, 2, 2, 0, 0, 3, 3, 2, 1, 0, 0, 3, 2, 0, 1, 0, 3
                ),
                listOf(
                    3, 3, 3, 0, 1, 3, 2, 0, 1, 3, 0, 2, 3, 2, 2, 2, 2, 3, 1, 3
                ),
                listOf(
                    3, 2, 2, 2, 2, 0, 0, 3, 3, 2, 0, 3, 3, 3, 1, 1, 1, 0, 0, 3
                ),
                listOf(
                    3, 0, 0, 2, 0, 2, 0, 1, 3, 1, 0, 1, 3, 3, 0, 1, 2, 2, 3, 2
                ),
                listOf(
                    2, 0, 1, 0, 3, 0, 3, 1, 2, 2, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1
                ),
                listOf(
                    3, 2, 0, 1, 0, 3, 3, 0, 1, 1, 1, 2, 2, 1, 1, 0, 1, 0, 0, 3
                ),
                listOf(
                    2, 1, 1, 1, 2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 3, 0, 0, 2, 0
                ),
                listOf(
                    2, 1, 1, 3, 2, 0, 1, 3, 2, 2, 1, 3, 1, 2, 0, 2, 1, 1, 0, 3
                ),
                listOf(
                    0, 0, 1, 0, 1, 0, 1, 1, 3, 2, 1, 1, 0, 1, 3, 2, 0, 0, 0, 1
                ),
                listOf(
                    1, 3, 3, 3, 2, 0, 0, 0, 1, 1, 3, 2, 2, 2, 1, 3, 1, 3, 0, 0
                ),
                listOf(
                    1, 1, 1, 3, 3, 0, 1, 3, 1, 0, 0, 3, 2, 1, 1, 1, 0, 2, 3, 1
                ),
                listOf(
                    0, 1, 3, 2, 2, 3, 1, 3, 2, 0, 3, 2, 3, 0, 3, 3, 1, 3, 1, 1
                ),
                listOf(
                    2, 3, 2, 3, 0, 2, 1, 2, 0, 1, 1, 2, 0, 3, 3, 0, 2, 0, 1, 1
                ),
                listOf(
                    0, 1, 0, 2, 3, 1, 3, 3, 3, 2, 0, 3, 0, 3, 1, 2, 0, 1, 1, 2
                ),
                listOf(
                    1, 0, 2, 1, 2, 1, 3, 1, 3, 3, 1, 3, 1, 3, 1, 1, 3, 3, 0, 1
                ),
                listOf(
                    2, 1, 1, 2, 2, 2, 2, 0, 1, 1, 2, 3, 1, 0, 0, 1, 3, 1, 1, 3
                ),
                listOf(
                    2, 0, 3, 0, 1, 3, 1, 0, 1, 3, 0, 0, 3, 0, 0, 0, 3, 3, 2, 2
                ),
                listOf(
                    3, 0, 1, 0, 1, 0, 1, 0, 0, 1, 2, 1, 2, 2, 1, 0, 1, 1, 2, 0
                ),
                listOf(
                    3, 3, 2, 0, 0, 3, 2, 2, 2, 0, 1, 0, 1, 2, 0, 2, 1, 1, 1, 0
                ),
                listOf(
                    2, 2, 2, 2, 1, 1, 0, 1, 0, 1, 1, 2, 2, 0, 0, 3, 2, 3, 2, 3
                )
            ), listOf(
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 25),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 25),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(2, 25),
                Tank(3, 20),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk103(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_004", "Master", 20, 40, listOf(
                listOf(
                    0, 2, 0, 3, 2, 2, 3, 0, 0, 3, 0, 1, 2, 1, 2, 2, 3, 1, 2, 2
                ),
                listOf(
                    1, 0, 1, 2, 1, 2, 0, 3, 1, 2, 2, 2, 0, 1, 3, 0, 2, 3, 1, 1
                ),
                listOf(
                    1, 0, 3, 3, 3, 3, 0, 2, 0, 3, 1, 2, 0, 0, 3, 1, 3, 0, 1, 1
                ),
                listOf(
                    2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 3, 2, 3, 3, 0, 2, 0, 2, 1, 1
                ),
                listOf(
                    0, 0, 3, 1, 2, 1, 1, 1, 1, 0, 2, 3, 1, 1, 2, 2, 1, 3, 1, 0
                ),
                listOf(
                    1, 0, 0, 2, 1, 1, 1, 0, 0, 3, 1, 2, 1, 1, 0, 3, 1, 1, 1, 2
                ),
                listOf(
                    0, 0, 1, 0, 3, 1, 0, 2, 2, 3, 2, 1, 2, 2, 2, 0, 0, 3, 3, 1
                ),
                listOf(
                    2, 3, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 1, 2, 2, 2, 0, 0, 2, 3
                ),
                listOf(
                    3, 0, 2, 0, 3, 0, 0, 0, 0, 3, 0, 1, 1, 0, 3, 0, 3, 1, 0, 3
                ),
                listOf(
                    0, 0, 2, 1, 0, 1, 0, 0, 2, 3, 1, 2, 1, 1, 1, 2, 0, 1, 0, 2
                ),
                listOf(
                    1, 2, 3, 0, 2, 3, 1, 0, 1, 0, 3, 0, 1, 1, 1, 1, 3, 3, 3, 2
                ),
                listOf(
                    3, 3, 3, 0, 1, 3, 1, 2, 0, 0, 0, 0, 2, 0, 1, 1, 1, 1, 0, 2
                ),
                listOf(
                    3, 1, 0, 2, 1, 2, 1, 1, 2, 2, 2, 2, 1, 0, 3, 0, 0, 0, 2, 0
                ),
                listOf(
                    1, 1, 3, 3, 2, 2, 2, 0, 1, 3, 1, 1, 2, 0, 0, 3, 3, 3, 3, 0
                ),
                listOf(
                    1, 3, 3, 3, 3, 3, 2, 2, 0, 0, 2, 1, 0, 1, 1, 2, 1, 3, 2, 2
                ),
                listOf(
                    2, 0, 0, 2, 3, 1, 2, 2, 1, 2, 0, 1, 0, 0, 1, 1, 0, 0, 2, 2
                ),
                listOf(
                    3, 0, 2, 1, 0, 0, 3, 1, 0, 1, 0, 0, 2, 2, 2, 0, 0, 0, 0, 3
                ),
                listOf(
                    3, 0, 1, 0, 2, 1, 2, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 2, 1
                ),
                listOf(
                    2, 2, 0, 2, 3, 3, 0, 3, 3, 2, 1, 0, 0, 2, 2, 1, 1, 0, 0, 2
                ),
                listOf(
                    2, 2, 1, 1, 0, 3, 1, 1, 3, 3, 1, 2, 3, 0, 0, 2, 1, 0, 3, 2
                ),
                listOf(
                    2, 3, 2, 3, 3, 2, 2, 3, 0, 0, 2, 3, 2, 2, 1, 2, 3, 0, 1, 3
                ),
                listOf(
                    3, 2, 1, 1, 1, 2, 1, 3, 2, 0, 0, 1, 3, 1, 3, 0, 0, 0, 0, 2
                ),
                listOf(
                    3, 3, 0, 0, 0, 0, 1, 3, 3, 2, 0, 3, 1, 2, 2, 2, 2, 1, 1, 1
                ),
                listOf(
                    2, 1, 2, 0, 0, 0, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 0, 3, 2, 2
                ),
                listOf(
                    1, 1, 0, 0, 1, 0, 0, 0, 3, 1, 0, 1, 0, 3, 1, 0, 0, 2, 3, 3
                ),
                listOf(
                    3, 1, 2, 0, 2, 1, 3, 1, 3, 1, 3, 1, 1, 0, 2, 3, 2, 0, 2, 1
                ),
                listOf(
                    2, 3, 0, 0, 2, 0, 2, 2, 2, 3, 2, 2, 1, 0, 0, 2, 0, 2, 1, 2
                ),
                listOf(
                    1, 1, 3, 1, 2, 0, 2, 0, 2, 3, 1, 2, 0, 2, 1, 3, 1, 1, 0, 1
                ),
                listOf(
                    2, 2, 1, 0, 3, 2, 3, 2, 2, 0, 0, 1, 0, 2, 0, 0, 2, 1, 1, 2
                ),
                listOf(
                    0, 3, 2, 2, 0, 0, 2, 2, 0, 0, 3, 3, 3, 0, 3, 0, 2, 2, 1, 1
                ),
                listOf(
                    1, 3, 0, 1, 0, 3, 1, 1, 3, 0, 1, 2, 0, 0, 1, 0, 3, 0, 1, 1
                ),
                listOf(
                    3, 3, 3, 3, 3, 0, 1, 1, 1, 2, 3, 1, 0, 1, 0, 2, 3, 2, 1, 3
                ),
                listOf(
                    2, 3, 2, 0, 0, 1, 2, 0, 2, 0, 0, 1, 1, 3, 2, 2, 2, 1, 3, 1
                ),
                listOf(
                    1, 2, 1, 1, 1, 1, 3, 2, 1, 0, 0, 2, 0, 2, 2, 1, 1, 2, 0, 0
                ),
                listOf(
                    2, 0, 2, 1, 0, 1, 1, 1, 1, 0, 3, 1, 2, 3, 3, 1, 0, 1, 0, 3
                ),
                listOf(
                    3, 2, 0, 1, 3, 0, 3, 1, 2, 2, 0, 2, 3, 2, 0, 1, 3, 2, 3, 0
                ),
                listOf(
                    1, 1, 2, 3, 2, 2, 3, 1, 3, 2, 2, 1, 3, 1, 0, 3, 2, 2, 2, 1
                ),
                listOf(
                    3, 3, 0, 1, 1, 1, 2, 2, 1, 0, 1, 3, 2, 2, 2, 0, 2, 2, 2, 3
                ),
                listOf(
                    0, 3, 2, 2, 2, 0, 1, 0, 1, 3, 1, 3, 2, 3, 2, 0, 1, 1, 0, 0
                ),
                listOf(
                    0, 1, 0, 0, 2, 0, 2, 0, 0, 1, 0, 2, 1, 3, 2, 2, 2, 3, 3, 3
                )
            ), listOf(
                Tank(3, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 25),
                Tank(0, 15),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 25),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(2, 25),
                Tank(2, 20),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk104(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_005", "Master", 20, 40, listOf(
                listOf(
                    2, 3, 0, 2, 3, 0, 1, 1, 3, 0, 1, 0, 0, 1, 3, 1, 2, 1, 1, 1
                ),
                listOf(
                    1, 1, 2, 1, 3, 2, 1, 0, 2, 1, 1, 0, 3, 2, 1, 1, 2, 0, 2, 0
                ),
                listOf(
                    3, 2, 1, 3, 3, 1, 1, 0, 2, 2, 1, 2, 3, 0, 1, 0, 0, 1, 1, 3
                ),
                listOf(
                    3, 3, 3, 0, 1, 2, 3, 1, 0, 3, 3, 0, 1, 2, 0, 2, 0, 1, 0, 0
                ),
                listOf(
                    0, 2, 2, 2, 0, 3, 0, 3, 3, 0, 3, 0, 3, 0, 0, 2, 1, 1, 1, 2
                ),
                listOf(
                    1, 1, 0, 0, 2, 0, 0, 3, 3, 2, 2, 1, 1, 1, 1, 3, 3, 3, 3, 1
                ),
                listOf(
                    3, 1, 0, 1, 0, 3, 0, 1, 3, 1, 2, 2, 0, 0, 2, 3, 0, 0, 2, 0
                ),
                listOf(
                    3, 0, 2, 1, 2, 0, 0, 0, 0, 1, 3, 0, 2, 0, 3, 1, 1, 3, 1, 1
                ),
                listOf(
                    3, 1, 1, 3, 0, 3, 3, 1, 1, 3, 1, 3, 1, 0, 3, 2, 2, 1, 0, 3
                ),
                listOf(
                    0, 2, 3, 3, 3, 3, 2, 2, 2, 3, 1, 3, 0, 3, 1, 2, 1, 0, 3, 3
                ),
                listOf(
                    1, 2, 1, 3, 0, 0, 3, 3, 3, 1, 1, 3, 1, 2, 1, 3, 3, 0, 3, 1
                ),
                listOf(
                    2, 0, 2, 3, 1, 3, 1, 1, 0, 2, 0, 0, 3, 0, 3, 3, 3, 1, 1, 0
                ),
                listOf(
                    2, 0, 1, 1, 1, 3, 3, 2, 2, 1, 1, 3, 0, 3, 1, 2, 1, 0, 3, 2
                ),
                listOf(
                    1, 3, 0, 1, 1, 1, 0, 3, 2, 1, 2, 3, 1, 2, 0, 3, 3, 3, 1, 2
                ),
                listOf(
                    3, 2, 2, 3, 2, 3, 2, 1, 2, 0, 2, 2, 3, 1, 1, 3, 0, 2, 3, 0
                ),
                listOf(
                    1, 0, 0, 3, 2, 0, 3, 1, 3, 2, 2, 1, 2, 1, 0, 2, 2, 2, 2, 3
                ),
                listOf(
                    1, 1, 0, 3, 2, 1, 3, 2, 3, 1, 3, 0, 0, 1, 1, 2, 1, 3, 3, 2
                ),
                listOf(
                    0, 0, 0, 0, 1, 1, 0, 2, 0, 2, 0, 0, 0, 3, 1, 3, 1, 0, 3, 0
                ),
                listOf(
                    3, 2, 0, 3, 2, 2, 3, 2, 0, 1, 1, 2, 0, 0, 1, 3, 1, 2, 0, 3
                ),
                listOf(
                    3, 1, 1, 1, 0, 0, 2, 2, 0, 3, 3, 1, 2, 1, 0, 1, 3, 1, 1, 0
                ),
                listOf(
                    3, 0, 0, 2, 2, 2, 1, 1, 2, 1, 3, 2, 3, 0, 2, 3, 1, 0, 3, 2
                ),
                listOf(
                    1, 2, 2, 0, 3, 0, 3, 1, 3, 0, 2, 3, 2, 3, 1, 1, 2, 0, 0, 3
                ),
                listOf(
                    2, 2, 0, 1, 2, 3, 1, 1, 0, 1, 3, 1, 3, 2, 0, 0, 0, 3, 3, 1
                ),
                listOf(
                    2, 3, 1, 3, 3, 1, 3, 0, 0, 0, 1, 2, 1, 3, 3, 0, 1, 1, 0, 0
                ),
                listOf(
                    0, 1, 1, 1, 0, 3, 3, 1, 2, 1, 2, 1, 2, 3, 0, 3, 3, 1, 1, 1
                ),
                listOf(
                    0, 2, 2, 1, 3, 1, 2, 1, 1, 1, 0, 3, 2, 3, 2, 0, 2, 0, 1, 2
                ),
                listOf(
                    1, 2, 0, 3, 1, 0, 2, 3, 0, 3, 1, 3, 2, 0, 1, 1, 1, 0, 3, 3
                ),
                listOf(
                    3, 0, 3, 3, 3, 1, 2, 1, 1, 3, 3, 1, 1, 1, 0, 3, 2, 1, 3, 0
                ),
                listOf(
                    2, 0, 1, 3, 3, 2, 0, 3, 0, 1, 3, 3, 1, 1, 0, 3, 3, 0, 2, 0
                ),
                listOf(
                    0, 1, 0, 2, 2, 1, 2, 0, 0, 2, 1, 2, 1, 1, 3, 2, 3, 0, 2, 1
                ),
                listOf(
                    3, 3, 3, 0, 2, 1, 3, 2, 0, 2, 3, 0, 2, 1, 3, 0, 0, 0, 1, 1
                ),
                listOf(
                    0, 2, 1, 0, 0, 0, 3, 0, 2, 1, 1, 3, 0, 1, 1, 1, 1, 0, 0, 0
                ),
                listOf(
                    0, 3, 0, 3, 2, 3, 1, 0, 0, 1, 1, 3, 3, 3, 3, 3, 0, 1, 1, 3
                ),
                listOf(
                    2, 3, 1, 3, 0, 1, 3, 1, 1, 0, 0, 0, 0, 3, 3, 1, 3, 1, 0, 0
                ),
                listOf(
                    0, 1, 1, 0, 1, 0, 1, 1, 1, 3, 1, 2, 0, 1, 3, 3, 3, 3, 1, 1
                ),
                listOf(
                    1, 1, 3, 1, 2, 0, 1, 3, 0, 0, 0, 3, 2, 0, 2, 0, 1, 0, 1, 2
                ),
                listOf(
                    1, 3, 3, 3, 3, 1, 0, 3, 1, 1, 2, 3, 2, 3, 1, 3, 0, 2, 0, 0
                ),
                listOf(
                    1, 2, 1, 2, 3, 0, 0, 1, 3, 1, 0, 1, 0, 2, 3, 2, 3, 1, 1, 2
                ),
                listOf(
                    3, 1, 1, 0, 2, 2, 3, 2, 0, 0, 0, 2, 0, 3, 0, 3, 1, 2, 1, 0
                ),
                listOf(
                    0, 3, 3, 0, 2, 1, 3, 3, 1, 3, 3, 3, 3, 0, 3, 1, 2, 1, 2, 1
                )
            ), listOf(
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 25)
            )),
        )
    }

    private fun getChunk105(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_006", "Master", 20, 40, listOf(
                listOf(
                    0, 3, 3, 0, 2, 0, 0, 2, 2, 2, 0, 2, 3, 2, 2, 0, 3, 2, 2, 1
                ),
                listOf(
                    1, 3, 1, 1, 2, 1, 1, 1, 3, 0, 0, 3, 0, 2, 2, 3, 3, 0, 2, 2
                ),
                listOf(
                    0, 1, 2, 2, 3, 2, 2, 2, 0, 3, 1, 3, 2, 1, 3, 1, 2, 3, 3, 0
                ),
                listOf(
                    0, 0, 2, 0, 3, 0, 1, 3, 0, 3, 2, 1, 1, 2, 0, 3, 2, 1, 3, 2
                ),
                listOf(
                    1, 1, 3, 1, 2, 0, 2, 3, 3, 0, 2, 3, 1, 2, 0, 2, 3, 2, 0, 3
                ),
                listOf(
                    0, 0, 0, 0, 2, 0, 2, 0, 3, 3, 2, 0, 1, 0, 1, 2, 1, 3, 0, 1
                ),
                listOf(
                    1, 0, 1, 1, 1, 0, 3, 1, 1, 3, 2, 3, 1, 0, 3, 1, 3, 1, 1, 2
                ),
                listOf(
                    0, 3, 1, 3, 2, 1, 0, 3, 0, 0, 2, 0, 3, 0, 3, 0, 3, 2, 1, 1
                ),
                listOf(
                    2, 0, 0, 2, 2, 3, 1, 3, 1, 1, 3, 0, 0, 1, 3, 1, 1, 0, 0, 1
                ),
                listOf(
                    1, 3, 3, 3, 0, 3, 2, 0, 2, 0, 2, 3, 0, 1, 0, 0, 1, 0, 2, 0
                ),
                listOf(
                    2, 1, 0, 3, 1, 1, 3, 0, 1, 0, 1, 2, 1, 3, 0, 0, 0, 0, 0, 0
                ),
                listOf(
                    0, 3, 1, 3, 3, 0, 0, 0, 2, 3, 1, 1, 2, 1, 0, 3, 0, 0, 1, 3
                ),
                listOf(
                    0, 3, 3, 1, 3, 2, 1, 1, 1, 0, 1, 0, 2, 2, 0, 3, 1, 2, 0, 1
                ),
                listOf(
                    1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 0, 0, 3, 2, 3, 3, 2, 1, 0, 2
                ),
                listOf(
                    2, 1, 2, 3, 2, 3, 1, 2, 1, 2, 0, 2, 2, 1, 0, 2, 2, 2, 3, 3
                ),
                listOf(
                    2, 0, 0, 2, 2, 0, 2, 2, 3, 1, 0, 0, 1, 1, 3, 3, 1, 1, 3, 3
                ),
                listOf(
                    0, 1, 3, 1, 3, 1, 3, 3, 2, 3, 2, 2, 0, 3, 1, 3, 1, 2, 2, 1
                ),
                listOf(
                    1, 1, 2, 1, 1, 0, 2, 0, 3, 0, 1, 1, 0, 0, 0, 0, 1, 3, 2, 3
                ),
                listOf(
                    2, 2, 3, 0, 3, 2, 0, 3, 2, 1, 2, 1, 0, 3, 2, 2, 1, 1, 2, 2
                ),
                listOf(
                    1, 1, 1, 0, 3, 3, 3, 2, 0, 0, 0, 0, 2, 3, 1, 2, 1, 3, 0, 2
                ),
                listOf(
                    1, 2, 1, 3, 0, 3, 3, 0, 3, 0, 1, 0, 1, 0, 1, 3, 3, 0, 1, 2
                ),
                listOf(
                    3, 3, 2, 0, 0, 3, 3, 3, 0, 3, 0, 1, 1, 1, 2, 2, 2, 1, 0, 2
                ),
                listOf(
                    2, 0, 0, 0, 3, 0, 1, 0, 2, 3, 1, 2, 2, 3, 2, 2, 3, 1, 2, 0
                ),
                listOf(
                    1, 3, 3, 3, 0, 1, 3, 2, 2, 0, 0, 1, 1, 2, 0, 1, 0, 0, 0, 2
                ),
                listOf(
                    1, 1, 1, 2, 3, 1, 1, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 2, 3, 3
                ),
                listOf(
                    1, 1, 1, 1, 3, 2, 2, 0, 0, 3, 2, 0, 2, 1, 0, 2, 0, 0, 3, 1
                ),
                listOf(
                    2, 0, 2, 0, 3, 1, 2, 3, 2, 1, 1, 3, 2, 3, 0, 0, 3, 0, 0, 0
                ),
                listOf(
                    2, 1, 3, 1, 3, 0, 3, 2, 2, 0, 1, 1, 0, 0, 3, 1, 3, 2, 0, 1
                ),
                listOf(
                    1, 1, 3, 3, 0, 0, 1, 2, 2, 1, 1, 0, 0, 1, 1, 0, 1, 3, 2, 2
                ),
                listOf(
                    0, 2, 1, 3, 2, 2, 0, 3, 2, 3, 3, 0, 2, 0, 2, 3, 1, 3, 1, 3
                ),
                listOf(
                    0, 1, 3, 1, 2, 3, 0, 0, 1, 3, 3, 1, 3, 1, 3, 0, 1, 0, 2, 1
                ),
                listOf(
                    1, 1, 3, 0, 3, 3, 2, 3, 2, 0, 3, 1, 0, 0, 0, 2, 0, 3, 0, 0
                ),
                listOf(
                    1, 0, 2, 0, 3, 2, 0, 3, 3, 3, 3, 3, 2, 1, 3, 2, 3, 2, 0, 1
                ),
                listOf(
                    2, 2, 1, 1, 2, 3, 2, 0, 1, 1, 3, 2, 3, 0, 2, 1, 3, 2, 1, 1
                ),
                listOf(
                    1, 0, 3, 2, 1, 1, 3, 2, 1, 0, 0, 2, 0, 1, 3, 3, 1, 2, 3, 0
                ),
                listOf(
                    3, 3, 0, 0, 3, 2, 3, 3, 0, 1, 0, 0, 0, 3, 1, 0, 1, 1, 3, 2
                ),
                listOf(
                    1, 1, 3, 1, 1, 3, 3, 1, 1, 3, 2, 3, 2, 3, 1, 3, 1, 3, 1, 1
                ),
                listOf(
                    0, 3, 3, 0, 1, 0, 0, 2, 2, 1, 3, 1, 2, 3, 1, 2, 3, 2, 3, 1
                ),
                listOf(
                    2, 1, 3, 0, 1, 3, 0, 2, 3, 2, 3, 2, 0, 3, 0, 1, 0, 0, 3, 0
                ),
                listOf(
                    3, 1, 1, 2, 1, 0, 2, 3, 1, 2, 1, 3, 3, 2, 3, 0, 1, 0, 3, 3
                )
            ), listOf(
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 20),
                Tank(2, 20),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk106(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_007", "Master", 20, 40, listOf(
                listOf(
                    2, 2, 1, 3, 3, 0, 3, 3, 0, 1, 0, 0, 1, 2, 0, 1, 3, 1, 2, 1
                ),
                listOf(
                    0, 1, 2, 2, 2, 1, 3, 2, 2, 1, 0, 1, 1, 2, 3, 2, 0, 1, 0, 2
                ),
                listOf(
                    0, 2, 1, 1, 3, 2, 1, 2, 1, 1, 1, 3, 3, 1, 2, 0, 3, 0, 0, 0
                ),
                listOf(
                    1, 1, 3, 3, 1, 0, 1, 0, 2, 1, 0, 2, 3, 0, 2, 0, 0, 1, 2, 1
                ),
                listOf(
                    1, 3, 2, 2, 0, 1, 2, 3, 3, 3, 0, 1, 3, 0, 3, 0, 3, 1, 3, 0
                ),
                listOf(
                    3, 2, 2, 3, 0, 3, 2, 0, 3, 1, 1, 3, 1, 1, 1, 3, 1, 3, 3, 1
                ),
                listOf(
                    1, 1, 1, 0, 3, 3, 3, 3, 2, 2, 0, 0, 3, 0, 3, 3, 1, 0, 2, 3
                ),
                listOf(
                    0, 3, 1, 3, 1, 1, 3, 2, 2, 3, 2, 2, 0, 1, 1, 0, 2, 2, 2, 0
                ),
                listOf(
                    2, 2, 1, 0, 1, 2, 1, 2, 2, 3, 2, 2, 2, 1, 3, 2, 2, 2, 0, 0
                ),
                listOf(
                    2, 3, 2, 3, 1, 3, 0, 3, 3, 0, 3, 3, 1, 3, 1, 3, 0, 0, 0, 2
                ),
                listOf(
                    3, 2, 1, 3, 2, 0, 3, 2, 1, 3, 2, 2, 2, 3, 3, 0, 1, 2, 2, 3
                ),
                listOf(
                    3, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 3, 1, 3, 2, 0, 0, 2
                ),
                listOf(
                    1, 1, 3, 3, 2, 1, 2, 3, 3, 3, 1, 2, 2, 0, 1, 3, 2, 0, 2, 2
                ),
                listOf(
                    3, 1, 2, 2, 3, 3, 2, 1, 0, 1, 3, 0, 1, 2, 1, 3, 1, 3, 0, 1
                ),
                listOf(
                    1, 0, 2, 2, 3, 3, 2, 3, 3, 2, 1, 3, 3, 2, 1, 1, 0, 2, 2, 1
                ),
                listOf(
                    3, 0, 3, 3, 2, 3, 1, 3, 3, 2, 1, 2, 0, 2, 2, 3, 3, 1, 1, 3
                ),
                listOf(
                    2, 3, 0, 0, 1, 1, 1, 3, 0, 1, 3, 2, 1, 2, 0, 3, 1, 2, 2, 2
                ),
                listOf(
                    2, 2, 0, 2, 0, 0, 3, 1, 1, 1, 0, 2, 3, 2, 0, 3, 3, 2, 0, 3
                ),
                listOf(
                    1, 2, 0, 3, 2, 1, 2, 3, 3, 1, 3, 0, 2, 1, 1, 1, 3, 3, 1, 3
                ),
                listOf(
                    2, 3, 1, 3, 2, 1, 3, 0, 1, 2, 1, 2, 2, 1, 1, 3, 3, 3, 3, 0
                ),
                listOf(
                    0, 2, 3, 3, 0, 1, 0, 0, 0, 1, 2, 1, 1, 2, 2, 1, 0, 1, 3, 3
                ),
                listOf(
                    0, 3, 3, 3, 1, 2, 1, 1, 2, 2, 3, 3, 2, 1, 2, 3, 0, 1, 3, 3
                ),
                listOf(
                    1, 3, 3, 2, 0, 2, 0, 1, 2, 3, 3, 1, 0, 2, 3, 2, 3, 0, 0, 3
                ),
                listOf(
                    2, 3, 1, 2, 0, 3, 0, 0, 2, 0, 3, 1, 0, 1, 0, 1, 3, 0, 2, 3
                ),
                listOf(
                    1, 3, 3, 1, 0, 1, 2, 0, 1, 1, 0, 2, 1, 3, 2, 3, 2, 0, 1, 1
                ),
                listOf(
                    3, 1, 0, 3, 3, 0, 3, 3, 3, 0, 0, 0, 0, 3, 3, 2, 0, 2, 1, 2
                ),
                listOf(
                    2, 3, 0, 3, 3, 0, 1, 2, 0, 3, 2, 1, 2, 0, 0, 3, 1, 1, 3, 3
                ),
                listOf(
                    1, 3, 2, 1, 0, 3, 1, 2, 1, 2, 0, 3, 3, 0, 0, 2, 3, 3, 3, 2
                ),
                listOf(
                    3, 2, 0, 0, 3, 0, 2, 1, 2, 1, 1, 0, 0, 2, 1, 0, 1, 1, 3, 1
                ),
                listOf(
                    0, 2, 3, 1, 0, 2, 2, 3, 2, 3, 2, 2, 2, 3, 1, 0, 1, 0, 2, 1
                ),
                listOf(
                    1, 2, 2, 2, 2, 0, 1, 2, 2, 2, 1, 2, 0, 1, 1, 0, 2, 2, 0, 2
                ),
                listOf(
                    2, 0, 2, 3, 3, 0, 1, 0, 3, 2, 0, 1, 0, 1, 3, 2, 3, 2, 1, 2
                ),
                listOf(
                    1, 3, 3, 3, 2, 1, 2, 3, 3, 3, 1, 0, 3, 2, 0, 2, 1, 3, 2, 3
                ),
                listOf(
                    1, 1, 0, 0, 3, 2, 2, 2, 0, 2, 2, 1, 3, 3, 1, 2, 2, 1, 3, 1
                ),
                listOf(
                    0, 0, 1, 0, 3, 3, 2, 1, 0, 1, 2, 2, 1, 0, 2, 3, 1, 3, 1, 2
                ),
                listOf(
                    1, 2, 0, 3, 3, 2, 1, 3, 1, 3, 0, 3, 2, 2, 3, 3, 3, 2, 2, 3
                ),
                listOf(
                    0, 1, 2, 3, 2, 2, 3, 1, 3, 1, 0, 3, 2, 1, 2, 2, 1, 0, 0, 1
                ),
                listOf(
                    1, 2, 1, 0, 0, 2, 2, 3, 1, 1, 0, 0, 3, 0, 0, 3, 0, 2, 0, 3
                ),
                listOf(
                    2, 0, 1, 1, 3, 2, 0, 0, 2, 1, 1, 1, 2, 0, 0, 1, 3, 3, 2, 2
                ),
                listOf(
                    1, 0, 0, 1, 0, 0, 1, 1, 3, 0, 1, 1, 2, 2, 0, 2, 3, 1, 2, 3
                )
            ), listOf(
                Tank(2, 10),
                Tank(3, 20),
                Tank(1, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(2, 20),
                Tank(3, 15)
            )),
        )
    }

    private fun getChunk107(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_008", "Master", 20, 40, listOf(
                listOf(
                    2, 2, 3, 0, 1, 1, 2, 1, 0, 3, 3, 3, 1, 3, 0, 0, 3, 3, 3, 3
                ),
                listOf(
                    2, 1, 2, 2, 3, 1, 0, 0, 1, 3, 0, 1, 0, 0, 3, 1, 0, 2, 3, 0
                ),
                listOf(
                    3, 1, 0, 2, 2, 0, 3, 3, 3, 1, 2, 2, 3, 1, 1, 0, 0, 1, 1, 3
                ),
                listOf(
                    0, 2, 0, 0, 2, 1, 0, 3, 3, 3, 2, 2, 2, 0, 3, 2, 2, 3, 1, 2
                ),
                listOf(
                    2, 0, 0, 3, 0, 2, 2, 0, 1, 3, 3, 1, 1, 1, 0, 0, 0, 3, 1, 3
                ),
                listOf(
                    0, 3, 3, 3, 0, 3, 1, 0, 1, 2, 1, 1, 1, 0, 2, 1, 1, 3, 1, 0
                ),
                listOf(
                    1, 3, 3, 0, 1, 2, 1, 0, 2, 1, 0, 0, 1, 1, 3, 0, 1, 2, 2, 2
                ),
                listOf(
                    2, 0, 1, 0, 2, 0, 2, 3, 0, 3, 1, 1, 2, 0, 1, 2, 0, 2, 1, 2
                ),
                listOf(
                    0, 3, 0, 3, 1, 1, 0, 1, 0, 1, 2, 0, 2, 2, 0, 1, 0, 2, 1, 3
                ),
                listOf(
                    0, 0, 2, 0, 3, 3, 2, 2, 3, 1, 1, 2, 0, 0, 1, 0, 2, 3, 3, 3
                ),
                listOf(
                    1, 2, 2, 0, 2, 1, 2, 0, 2, 1, 3, 0, 3, 3, 3, 1, 2, 3, 3, 3
                ),
                listOf(
                    1, 1, 2, 0, 2, 0, 0, 3, 1, 3, 1, 0, 1, 3, 3, 3, 1, 3, 2, 2
                ),
                listOf(
                    0, 3, 2, 2, 2, 2, 0, 1, 2, 2, 1, 1, 3, 1, 1, 2, 2, 1, 0, 0
                ),
                listOf(
                    1, 3, 3, 1, 0, 0, 3, 1, 2, 0, 3, 3, 1, 1, 3, 3, 1, 0, 1, 3
                ),
                listOf(
                    2, 1, 0, 2, 2, 2, 1, 1, 1, 3, 2, 1, 0, 2, 1, 1, 3, 0, 1, 0
                ),
                listOf(
                    1, 2, 0, 3, 0, 2, 3, 1, 2, 2, 0, 1, 3, 3, 1, 0, 2, 2, 0, 3
                ),
                listOf(
                    0, 3, 1, 3, 3, 0, 2, 0, 2, 3, 3, 1, 3, 1, 0, 2, 3, 2, 0, 1
                ),
                listOf(
                    1, 0, 1, 2, 1, 1, 3, 0, 3, 0, 1, 3, 2, 1, 2, 0, 2, 1, 2, 3
                ),
                listOf(
                    3, 3, 0, 0, 3, 3, 1, 2, 0, 3, 3, 0, 2, 0, 3, 3, 0, 1, 2, 1
                ),
                listOf(
                    1, 0, 1, 1, 0, 3, 1, 3, 1, 0, 1, 3, 2, 3, 1, 0, 1, 1, 1, 1
                ),
                listOf(
                    0, 2, 0, 1, 1, 0, 1, 0, 0, 1, 0, 2, 1, 3, 2, 1, 3, 1, 2, 0
                ),
                listOf(
                    2, 3, 1, 1, 2, 1, 3, 2, 2, 1, 3, 1, 3, 0, 2, 2, 0, 3, 2, 2
                ),
                listOf(
                    2, 2, 1, 3, 3, 1, 0, 1, 2, 1, 3, 3, 2, 0, 0, 1, 0, 2, 1, 3
                ),
                listOf(
                    2, 0, 0, 1, 0, 1, 1, 1, 1, 0, 2, 2, 3, 0, 1, 1, 1, 2, 0, 2
                ),
                listOf(
                    2, 3, 3, 1, 2, 2, 2, 2, 2, 3, 2, 0, 2, 3, 2, 0, 1, 0, 0, 0
                ),
                listOf(
                    2, 2, 0, 2, 0, 1, 3, 3, 2, 2, 0, 2, 3, 0, 3, 1, 3, 3, 3, 1
                ),
                listOf(
                    0, 0, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 0, 0
                ),
                listOf(
                    2, 2, 0, 1, 3, 1, 0, 2, 1, 0, 2, 1, 3, 3, 3, 0, 0, 1, 2, 3
                ),
                listOf(
                    1, 3, 3, 2, 3, 2, 0, 1, 2, 1, 0, 3, 1, 1, 3, 3, 3, 2, 2, 2
                ),
                listOf(
                    2, 2, 2, 3, 3, 0, 0, 0, 1, 1, 3, 1, 2, 1, 3, 3, 3, 0, 0, 1
                ),
                listOf(
                    0, 1, 2, 3, 3, 2, 0, 2, 0, 3, 0, 2, 3, 0, 2, 2, 3, 1, 0, 3
                ),
                listOf(
                    1, 0, 2, 1, 0, 2, 2, 2, 2, 1, 0, 3, 1, 1, 1, 2, 1, 3, 3, 0
                ),
                listOf(
                    2, 1, 3, 3, 1, 1, 1, 0, 2, 2, 0, 1, 3, 1, 0, 1, 2, 1, 3, 2
                ),
                listOf(
                    2, 0, 3, 2, 3, 2, 1, 1, 1, 0, 2, 1, 1, 0, 1, 2, 3, 0, 0, 0
                ),
                listOf(
                    1, 1, 2, 2, 3, 0, 1, 3, 1, 0, 2, 0, 2, 3, 1, 0, 1, 0, 2, 3
                ),
                listOf(
                    3, 2, 1, 3, 3, 2, 2, 0, 1, 1, 0, 0, 2, 3, 1, 0, 1, 0, 0, 1
                ),
                listOf(
                    3, 0, 3, 2, 3, 1, 2, 3, 1, 0, 1, 1, 1, 0, 2, 3, 2, 0, 1, 3
                ),
                listOf(
                    2, 1, 1, 3, 1, 1, 3, 3, 1, 2, 3, 3, 0, 3, 2, 1, 0, 1, 2, 1
                ),
                listOf(
                    3, 0, 3, 2, 2, 0, 2, 1, 1, 2, 1, 3, 2, 3, 2, 3, 2, 3, 1, 0
                ),
                listOf(
                    3, 1, 1, 2, 1, 0, 1, 2, 2, 2, 2, 1, 3, 1, 3, 2, 1, 3, 0, 0
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(2, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk108(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_009", "Master", 20, 40, listOf(
                listOf(
                    2, 2, 0, 3, 0, 3, 3, 3, 2, 1, 0, 0, 2, 0, 1, 3, 1, 2, 1, 0
                ),
                listOf(
                    3, 0, 0, 0, 2, 2, 2, 1, 3, 3, 3, 3, 0, 1, 0, 3, 3, 1, 3, 3
                ),
                listOf(
                    2, 0, 2, 2, 3, 0, 0, 2, 2, 3, 1, 3, 2, 2, 2, 3, 3, 1, 1, 1
                ),
                listOf(
                    1, 1, 0, 0, 1, 3, 1, 3, 3, 0, 0, 1, 1, 2, 2, 3, 0, 1, 0, 0
                ),
                listOf(
                    2, 2, 0, 1, 1, 3, 0, 3, 1, 3, 0, 3, 3, 3, 2, 2, 2, 3, 2, 2
                ),
                listOf(
                    1, 0, 3, 2, 2, 3, 2, 2, 1, 3, 3, 0, 3, 1, 1, 2, 0, 1, 0, 2
                ),
                listOf(
                    2, 0, 3, 0, 1, 2, 3, 0, 3, 0, 0, 3, 2, 1, 1, 1, 1, 2, 3, 1
                ),
                listOf(
                    3, 1, 1, 3, 1, 1, 3, 1, 3, 0, 3, 3, 0, 1, 2, 3, 0, 0, 1, 1
                ),
                listOf(
                    1, 1, 2, 2, 0, 3, 3, 3, 2, 2, 0, 1, 1, 1, 0, 2, 2, 2, 0, 2
                ),
                listOf(
                    3, 1, 1, 3, 1, 1, 1, 3, 1, 1, 2, 1, 2, 3, 2, 1, 3, 3, 0, 2
                ),
                listOf(
                    0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 1, 0, 2, 1, 0, 3, 3
                ),
                listOf(
                    0, 0, 3, 0, 1, 0, 1, 0, 2, 2, 0, 0, 2, 0, 2, 1, 1, 1, 2, 0
                ),
                listOf(
                    1, 0, 1, 1, 1, 0, 2, 0, 1, 3, 0, 2, 3, 1, 1, 2, 1, 3, 2, 3
                ),
                listOf(
                    2, 3, 2, 0, 2, 1, 2, 3, 3, 1, 3, 3, 2, 3, 3, 2, 0, 1, 1, 0
                ),
                listOf(
                    3, 2, 1, 1, 2, 1, 0, 3, 3, 2, 0, 0, 1, 1, 1, 1, 1, 0, 3, 0
                ),
                listOf(
                    0, 0, 2, 0, 0, 2, 3, 3, 2, 3, 0, 2, 0, 0, 0, 1, 3, 0, 1, 2
                ),
                listOf(
                    3, 0, 2, 3, 0, 2, 3, 2, 0, 0, 3, 0, 1, 0, 2, 3, 0, 1, 2, 1
                ),
                listOf(
                    1, 1, 2, 1, 2, 1, 3, 0, 0, 1, 1, 0, 0, 2, 1, 3, 1, 0, 2, 1
                ),
                listOf(
                    3, 0, 1, 3, 3, 0, 0, 3, 0, 3, 3, 0, 1, 1, 3, 3, 2, 3, 0, 0
                ),
                listOf(
                    2, 3, 0, 2, 3, 0, 3, 1, 0, 3, 1, 0, 0, 3, 3, 0, 0, 1, 3, 3
                ),
                listOf(
                    1, 1, 1, 2, 2, 3, 3, 0, 1, 2, 0, 3, 0, 0, 3, 1, 1, 0, 0, 0
                ),
                listOf(
                    3, 3, 1, 2, 0, 3, 2, 3, 1, 2, 3, 2, 3, 0, 3, 0, 2, 3, 3, 3
                ),
                listOf(
                    3, 1, 0, 2, 3, 1, 1, 0, 0, 0, 2, 0, 2, 3, 3, 3, 1, 3, 2, 1
                ),
                listOf(
                    1, 1, 1, 3, 0, 2, 3, 0, 2, 3, 1, 3, 0, 2, 2, 0, 0, 2, 1, 3
                ),
                listOf(
                    1, 0, 3, 0, 0, 0, 1, 3, 0, 0, 1, 1, 2, 0, 1, 0, 2, 0, 3, 2
                ),
                listOf(
                    1, 0, 0, 0, 2, 0, 3, 0, 2, 1, 3, 0, 2, 1, 2, 3, 3, 3, 3, 3
                ),
                listOf(
                    2, 1, 1, 0, 1, 1, 0, 3, 3, 3, 1, 0, 0, 0, 0, 2, 1, 2, 2, 2
                ),
                listOf(
                    0, 3, 2, 3, 1, 3, 3, 0, 1, 2, 1, 2, 2, 2, 0, 0, 0, 3, 3, 3
                ),
                listOf(
                    1, 3, 3, 0, 1, 2, 2, 0, 1, 3, 2, 0, 2, 3, 0, 3, 3, 1, 1, 0
                ),
                listOf(
                    3, 3, 2, 2, 2, 2, 1, 1, 0, 0, 1, 2, 1, 2, 2, 2, 1, 0, 1, 3
                ),
                listOf(
                    1, 0, 0, 2, 0, 0, 3, 0, 0, 3, 1, 1, 2, 3, 1, 1, 0, 1, 2, 1
                ),
                listOf(
                    1, 0, 1, 0, 1, 3, 0, 0, 0, 2, 2, 3, 0, 1, 0, 1, 3, 0, 1, 3
                ),
                listOf(
                    2, 0, 3, 0, 2, 1, 1, 3, 0, 3, 0, 0, 2, 0, 2, 0, 1, 0, 3, 3
                ),
                listOf(
                    3, 1, 1, 1, 0, 0, 1, 1, 2, 2, 2, 3, 3, 0, 0, 1, 0, 1, 0, 2
                ),
                listOf(
                    1, 2, 3, 0, 0, 0, 1, 2, 0, 0, 1, 1, 0, 1, 0, 2, 2, 2, 3, 3
                ),
                listOf(
                    0, 0, 2, 0, 2, 3, 0, 1, 0, 3, 0, 3, 0, 3, 0, 1, 3, 0, 3, 2
                ),
                listOf(
                    0, 0, 2, 1, 3, 0, 2, 0, 2, 2, 3, 0, 1, 1, 0, 2, 0, 1, 1, 3
                ),
                listOf(
                    2, 1, 1, 0, 2, 2, 3, 3, 1, 2, 3, 1, 3, 0, 2, 0, 2, 3, 0, 3
                ),
                listOf(
                    3, 2, 1, 3, 2, 3, 3, 0, 0, 3, 3, 1, 0, 3, 1, 3, 1, 1, 1, 2
                ),
                listOf(
                    2, 0, 0, 0, 1, 2, 2, 1, 3, 2, 1, 1, 1, 1, 0, 2, 3, 3, 0, 3
                )
            ), listOf(
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk109(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_010", "Master", 20, 40, listOf(
                listOf(
                    3, 2, 2, 0, 0, 1, 3, 1, 3, 0, 0, 0, 1, 2, 3, 0, 3, 1, 1, 3
                ),
                listOf(
                    1, 1, 2, 2, 0, 0, 1, 2, 0, 2, 0, 2, 2, 2, 1, 2, 1, 1, 1, 0
                ),
                listOf(
                    1, 2, 0, 2, 1, 1, 1, 1, 0, 3, 0, 1, 0, 0, 2, 3, 3, 1, 0, 0
                ),
                listOf(
                    2, 0, 3, 1, 1, 2, 2, 0, 1, 0, 1, 0, 0, 1, 2, 1, 0, 3, 2, 0
                ),
                listOf(
                    3, 1, 3, 3, 2, 3, 2, 2, 3, 1, 0, 3, 3, 2, 1, 0, 2, 2, 1, 1
                ),
                listOf(
                    2, 0, 2, 3, 1, 3, 1, 0, 2, 1, 3, 3, 2, 0, 1, 1, 3, 2, 2, 0
                ),
                listOf(
                    0, 1, 3, 2, 1, 1, 3, 0, 3, 3, 1, 3, 3, 0, 3, 0, 3, 3, 2, 2
                ),
                listOf(
                    3, 3, 0, 2, 3, 0, 3, 2, 3, 0, 3, 1, 2, 2, 3, 0, 1, 1, 3, 2
                ),
                listOf(
                    2, 1, 2, 2, 2, 1, 0, 3, 1, 2, 3, 3, 2, 1, 1, 1, 0, 1, 1, 2
                ),
                listOf(
                    3, 1, 0, 0, 0, 2, 0, 1, 3, 2, 3, 1, 1, 2, 1, 2, 0, 3, 3, 1
                ),
                listOf(
                    1, 3, 2, 3, 0, 2, 2, 0, 3, 2, 3, 1, 1, 0, 2, 0, 0, 1, 1, 3
                ),
                listOf(
                    0, 2, 2, 3, 3, 1, 3, 0, 2, 2, 2, 2, 1, 1, 2, 1, 0, 3, 0, 0
                ),
                listOf(
                    1, 0, 1, 1, 0, 1, 1, 1, 2, 3, 2, 2, 3, 1, 3, 0, 1, 1, 3, 3
                ),
                listOf(
                    0, 0, 2, 0, 1, 3, 1, 0, 0, 1, 1, 2, 1, 0, 3, 3, 3, 3, 0, 1
                ),
                listOf(
                    1, 2, 0, 2, 3, 2, 2, 2, 1, 0, 2, 1, 3, 1, 1, 2, 3, 1, 0, 1
                ),
                listOf(
                    2, 0, 2, 2, 0, 0, 2, 1, 1, 2, 3, 3, 0, 0, 0, 2, 3, 3, 1, 1
                ),
                listOf(
                    3, 0, 3, 0, 3, 0, 0, 3, 3, 2, 2, 1, 3, 2, 0, 3, 0, 2, 3, 3
                ),
                listOf(
                    0, 3, 1, 2, 3, 1, 2, 2, 1, 3, 3, 3, 1, 2, 1, 0, 3, 0, 2, 3
                ),
                listOf(
                    2, 0, 1, 3, 3, 3, 3, 2, 0, 2, 1, 3, 1, 2, 3, 2, 3, 2, 1, 2
                ),
                listOf(
                    2, 2, 2, 2, 0, 3, 3, 1, 1, 2, 1, 3, 1, 2, 2, 2, 1, 2, 3, 3
                ),
                listOf(
                    3, 3, 1, 3, 1, 2, 2, 2, 1, 2, 3, 1, 0, 3, 0, 2, 3, 3, 1, 3
                ),
                listOf(
                    2, 1, 3, 3, 0, 0, 3, 2, 3, 1, 2, 3, 0, 2, 3, 3, 3, 1, 1, 1
                ),
                listOf(
                    1, 3, 3, 3, 0, 3, 1, 0, 2, 0, 0, 1, 2, 3, 3, 1, 2, 2, 3, 0
                ),
                listOf(
                    2, 1, 3, 1, 1, 1, 2, 3, 2, 0, 1, 2, 2, 1, 0, 1, 0, 2, 3, 0
                ),
                listOf(
                    1, 2, 2, 1, 1, 2, 1, 1, 3, 3, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1
                ),
                listOf(
                    3, 0, 2, 1, 0, 1, 0, 1, 3, 0, 3, 1, 2, 1, 2, 0, 3, 0, 3, 0
                ),
                listOf(
                    1, 1, 1, 2, 0, 3, 2, 1, 1, 0, 0, 1, 2, 0, 3, 1, 3, 0, 1, 3
                ),
                listOf(
                    3, 1, 2, 3, 1, 0, 3, 2, 3, 2, 2, 1, 3, 3, 0, 3, 3, 1, 2, 1
                ),
                listOf(
                    1, 2, 1, 3, 2, 2, 2, 2, 2, 2, 3, 2, 3, 3, 2, 1, 1, 1, 1, 3
                ),
                listOf(
                    2, 1, 3, 3, 0, 2, 1, 1, 3, 2, 3, 0, 1, 1, 2, 3, 1, 1, 3, 2
                ),
                listOf(
                    2, 3, 3, 2, 0, 1, 2, 2, 1, 3, 2, 3, 3, 3, 0, 1, 2, 2, 3, 0
                ),
                listOf(
                    2, 0, 3, 2, 1, 1, 1, 3, 2, 1, 2, 1, 3, 1, 3, 3, 1, 2, 0, 3
                ),
                listOf(
                    3, 2, 3, 1, 3, 2, 0, 2, 3, 2, 3, 1, 1, 1, 2, 1, 2, 3, 3, 3
                ),
                listOf(
                    2, 3, 2, 1, 3, 3, 3, 0, 3, 3, 2, 3, 1, 2, 0, 3, 2, 3, 1, 2
                ),
                listOf(
                    2, 2, 0, 3, 3, 2, 2, 1, 3, 3, 1, 0, 2, 1, 3, 3, 2, 2, 2, 2
                ),
                listOf(
                    3, 0, 2, 0, 2, 0, 1, 3, 1, 0, 3, 2, 2, 0, 3, 0, 1, 2, 3, 1
                ),
                listOf(
                    0, 2, 0, 2, 2, 0, 3, 2, 3, 0, 1, 2, 3, 0, 2, 1, 2, 2, 1, 3
                ),
                listOf(
                    0, 0, 1, 3, 2, 0, 2, 2, 2, 0, 2, 1, 0, 2, 0, 0, 1, 1, 0, 3
                ),
                listOf(
                    3, 0, 2, 0, 3, 2, 2, 2, 1, 2, 2, 0, 1, 0, 0, 1, 1, 2, 3, 2
                ),
                listOf(
                    3, 2, 3, 2, 0, 2, 2, 0, 2, 0, 1, 1, 2, 1, 2, 0, 2, 1, 1, 1
                )
            ), listOf(
                Tank(3, 20),
                Tank(2, 25),
                Tank(3, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk110(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_011", "Master", 20, 40, listOf(
                listOf(
                    2, 2, 0, 2, 3, 2, 1, 1, 3, 0, 3, 0, 2, 2, 1, 3, 2, 3, 0, 3
                ),
                listOf(
                    0, 3, 3, 3, 0, 1, 3, 0, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 2, 2
                ),
                listOf(
                    0, 0, 3, 3, 3, 2, 0, 2, 2, 1, 2, 3, 3, 1, 0, 3, 2, 1, 0, 0
                ),
                listOf(
                    3, 0, 2, 1, 1, 3, 1, 3, 3, 1, 0, 1, 1, 1, 2, 3, 2, 2, 3, 3
                ),
                listOf(
                    0, 3, 3, 2, 2, 2, 2, 2, 1, 0, 0, 0, 1, 3, 3, 0, 0, 3, 3, 0
                ),
                listOf(
                    1, 1, 1, 0, 3, 0, 2, 3, 0, 3, 0, 2, 3, 3, 2, 1, 0, 3, 3, 1
                ),
                listOf(
                    2, 1, 0, 3, 1, 2, 1, 3, 0, 3, 0, 0, 0, 3, 3, 2, 2, 1, 2, 2
                ),
                listOf(
                    3, 3, 0, 3, 2, 0, 3, 1, 2, 1, 3, 3, 3, 2, 0, 0, 3, 3, 1, 0
                ),
                listOf(
                    3, 3, 1, 0, 0, 1, 3, 2, 3, 0, 3, 0, 0, 1, 3, 2, 1, 0, 3, 2
                ),
                listOf(
                    2, 0, 0, 2, 3, 3, 3, 2, 2, 2, 2, 0, 3, 3, 3, 1, 0, 2, 1, 3
                ),
                listOf(
                    1, 3, 3, 3, 3, 0, 3, 1, 0, 1, 1, 1, 3, 3, 2, 3, 0, 1, 0, 2
                ),
                listOf(
                    3, 1, 1, 2, 3, 3, 2, 0, 0, 0, 1, 1, 2, 0, 1, 1, 2, 3, 1, 3
                ),
                listOf(
                    2, 1, 0, 3, 2, 1, 2, 1, 2, 3, 2, 3, 2, 3, 2, 1, 1, 2, 0, 3
                ),
                listOf(
                    1, 0, 1, 2, 2, 3, 2, 3, 2, 0, 1, 3, 2, 3, 3, 3, 2, 1, 2, 2
                ),
                listOf(
                    0, 1, 3, 1, 0, 0, 1, 2, 1, 0, 3, 3, 3, 0, 1, 3, 2, 3, 3, 1
                ),
                listOf(
                    3, 3, 0, 0, 1, 1, 0, 0, 3, 3, 3, 0, 0, 2, 0, 1, 1, 3, 1, 3
                ),
                listOf(
                    2, 3, 3, 2, 1, 3, 0, 0, 1, 0, 1, 2, 3, 1, 2, 1, 3, 2, 0, 0
                ),
                listOf(
                    1, 3, 1, 0, 3, 3, 3, 2, 0, 1, 2, 2, 3, 3, 3, 0, 0, 1, 0, 0
                ),
                listOf(
                    0, 1, 3, 1, 0, 3, 1, 3, 0, 1, 3, 0, 3, 3, 1, 2, 2, 3, 2, 1
                ),
                listOf(
                    0, 0, 1, 2, 0, 2, 2, 2, 3, 3, 2, 1, 1, 0, 0, 3, 0, 1, 3, 1
                ),
                listOf(
                    3, 3, 3, 0, 1, 0, 0, 0, 1, 1, 2, 1, 0, 0, 3, 2, 2, 0, 1, 0
                ),
                listOf(
                    1, 0, 3, 1, 3, 1, 2, 3, 2, 1, 1, 1, 3, 3, 1, 3, 1, 2, 1, 1
                ),
                listOf(
                    0, 0, 3, 3, 3, 3, 1, 1, 1, 2, 1, 0, 3, 3, 0, 3, 0, 3, 0, 2
                ),
                listOf(
                    2, 1, 0, 2, 1, 0, 1, 3, 3, 3, 3, 1, 3, 2, 0, 3, 0, 0, 1, 3
                ),
                listOf(
                    0, 0, 1, 3, 2, 2, 1, 0, 3, 3, 0, 0, 3, 2, 2, 0, 1, 1, 0, 0
                ),
                listOf(
                    3, 0, 3, 0, 0, 2, 3, 2, 1, 1, 3, 0, 0, 0, 3, 2, 0, 1, 0, 3
                ),
                listOf(
                    1, 0, 2, 0, 0, 0, 2, 2, 0, 3, 0, 2, 2, 3, 0, 1, 0, 2, 3, 3
                ),
                listOf(
                    2, 2, 2, 1, 3, 0, 3, 3, 0, 3, 3, 3, 1, 3, 1, 0, 1, 2, 2, 0
                ),
                listOf(
                    0, 3, 0, 0, 0, 2, 3, 3, 0, 0, 1, 3, 2, 2, 1, 0, 1, 0, 2, 3
                ),
                listOf(
                    0, 3, 1, 3, 1, 3, 3, 3, 3, 1, 1, 2, 1, 3, 2, 3, 1, 3, 1, 1
                ),
                listOf(
                    3, 0, 3, 0, 0, 1, 0, 0, 2, 0, 0, 0, 3, 0, 3, 3, 0, 3, 0, 3
                ),
                listOf(
                    1, 0, 2, 1, 2, 2, 3, 3, 3, 0, 3, 1, 3, 0, 0, 3, 2, 3, 3, 2
                ),
                listOf(
                    3, 0, 0, 3, 2, 3, 0, 0, 0, 3, 2, 3, 1, 2, 2, 0, 1, 0, 0, 2
                ),
                listOf(
                    2, 3, 2, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 2, 3, 2, 1, 1
                ),
                listOf(
                    1, 0, 0, 3, 3, 1, 1, 1, 0, 0, 0, 2, 0, 3, 0, 0, 1, 1, 0, 2
                ),
                listOf(
                    1, 3, 2, 0, 3, 0, 3, 3, 2, 0, 3, 0, 1, 0, 2, 2, 0, 1, 2, 0
                ),
                listOf(
                    2, 0, 3, 3, 0, 0, 3, 1, 2, 0, 3, 2, 2, 0, 1, 0, 1, 3, 3, 1
                ),
                listOf(
                    1, 2, 3, 0, 2, 1, 1, 3, 1, 1, 3, 2, 3, 0, 3, 0, 1, 3, 3, 1
                ),
                listOf(
                    0, 0, 3, 1, 1, 1, 0, 3, 2, 1, 1, 2, 3, 3, 3, 2, 0, 2, 0, 3
                ),
                listOf(
                    2, 1, 1, 3, 1, 1, 1, 3, 2, 3, 1, 3, 0, 1, 3, 3, 1, 3, 2, 3
                )
            ), listOf(
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(0, 20),
                Tank(3, 10),
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(2, 15),
                Tank(3, 10),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 25),
                Tank(1, 20),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk111(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_012", "Master", 20, 40, listOf(
                listOf(
                    0, 2, 2, 2, 3, 0, 0, 0, 0, 2, 3, 1, 1, 1, 1, 2, 2, 0, 0, 1
                ),
                listOf(
                    0, 2, 2, 3, 0, 3, 0, 3, 3, 0, 3, 3, 1, 1, 3, 1, 0, 3, 2, 0
                ),
                listOf(
                    3, 0, 3, 3, 1, 2, 0, 1, 0, 2, 2, 0, 0, 1, 2, 2, 3, 3, 0, 3
                ),
                listOf(
                    2, 0, 0, 2, 3, 3, 2, 0, 0, 2, 1, 1, 2, 1, 2, 3, 3, 3, 1, 1
                ),
                listOf(
                    3, 3, 1, 2, 1, 3, 3, 1, 1, 3, 3, 0, 0, 0, 3, 0, 0, 2, 3, 3
                ),
                listOf(
                    2, 1, 0, 3, 0, 1, 1, 3, 3, 2, 1, 1, 3, 0, 2, 0, 3, 2, 2, 3
                ),
                listOf(
                    1, 0, 3, 2, 3, 1, 2, 3, 3, 1, 3, 2, 3, 3, 1, 3, 3, 1, 2, 2
                ),
                listOf(
                    3, 3, 0, 2, 2, 3, 0, 0, 3, 2, 0, 1, 1, 2, 3, 2, 3, 1, 3, 0
                ),
                listOf(
                    3, 3, 0, 1, 0, 0, 3, 0, 0, 3, 3, 1, 3, 3, 0, 2, 0, 0, 3, 1
                ),
                listOf(
                    2, 1, 0, 3, 2, 1, 2, 2, 2, 1, 0, 3, 2, 3, 1, 1, 1, 3, 1, 2
                ),
                listOf(
                    0, 3, 1, 0, 1, 3, 2, 1, 0, 3, 3, 1, 1, 3, 3, 1, 1, 3, 2, 3
                ),
                listOf(
                    3, 1, 0, 1, 0, 2, 0, 2, 3, 2, 2, 3, 1, 1, 1, 2, 3, 1, 0, 3
                ),
                listOf(
                    2, 2, 0, 3, 1, 0, 3, 1, 2, 1, 0, 1, 1, 1, 3, 1, 1, 3, 3, 0
                ),
                listOf(
                    2, 2, 3, 0, 1, 1, 2, 1, 3, 0, 2, 2, 3, 1, 3, 2, 1, 2, 1, 0
                ),
                listOf(
                    1, 3, 1, 3, 1, 1, 3, 2, 3, 3, 0, 1, 2, 3, 1, 2, 2, 2, 1, 0
                ),
                listOf(
                    1, 2, 0, 1, 0, 1, 0, 0, 3, 3, 3, 0, 0, 3, 3, 3, 2, 3, 1, 3
                ),
                listOf(
                    3, 1, 2, 2, 0, 1, 0, 3, 1, 0, 0, 3, 2, 2, 2, 0, 2, 0, 3, 3
                ),
                listOf(
                    1, 0, 1, 1, 1, 0, 2, 3, 2, 2, 2, 3, 1, 2, 1, 3, 3, 1, 1, 3
                ),
                listOf(
                    3, 3, 0, 2, 1, 1, 0, 3, 3, 3, 3, 1, 1, 3, 1, 3, 0, 2, 3, 0
                ),
                listOf(
                    3, 0, 0, 2, 1, 1, 3, 3, 3, 1, 0, 2, 2, 1, 1, 3, 1, 3, 1, 1
                ),
                listOf(
                    2, 3, 2, 1, 0, 2, 2, 0, 2, 3, 2, 2, 3, 1, 3, 2, 2, 1, 0, 2
                ),
                listOf(
                    1, 0, 1, 3, 3, 1, 2, 3, 0, 2, 2, 0, 1, 1, 1, 1, 0, 2, 3, 2
                ),
                listOf(
                    2, 0, 0, 3, 3, 0, 2, 1, 0, 3, 2, 3, 0, 3, 2, 2, 1, 2, 3, 3
                ),
                listOf(
                    2, 3, 0, 3, 1, 3, 0, 3, 3, 0, 2, 3, 2, 1, 0, 2, 0, 3, 0, 3
                ),
                listOf(
                    2, 2, 3, 1, 0, 1, 0, 3, 1, 3, 2, 2, 2, 2, 0, 1, 3, 0, 2, 0
                ),
                listOf(
                    3, 0, 3, 3, 3, 2, 3, 2, 1, 2, 1, 2, 3, 0, 0, 3, 1, 3, 3, 2
                ),
                listOf(
                    2, 0, 1, 1, 0, 2, 0, 1, 1, 0, 0, 3, 3, 1, 1, 3, 0, 1, 3, 1
                ),
                listOf(
                    1, 0, 0, 2, 1, 3, 2, 3, 3, 1, 3, 3, 0, 3, 3, 0, 3, 3, 3, 2
                ),
                listOf(
                    2, 0, 1, 3, 1, 0, 3, 1, 0, 1, 0, 1, 0, 1, 1, 1, 2, 0, 2, 1
                ),
                listOf(
                    0, 3, 0, 0, 3, 1, 1, 2, 2, 1, 3, 3, 0, 3, 2, 2, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 3, 1, 0, 1, 3, 1, 1, 0, 0, 1, 1, 0, 3, 3, 3, 2, 0, 1
                ),
                listOf(
                    1, 2, 1, 2, 3, 0, 0, 1, 2, 1, 2, 2, 0, 2, 3, 0, 0, 2, 1, 2
                ),
                listOf(
                    3, 3, 3, 2, 0, 0, 0, 0, 3, 2, 0, 1, 3, 1, 3, 3, 3, 3, 0, 0
                ),
                listOf(
                    0, 1, 1, 1, 3, 2, 2, 1, 2, 0, 3, 2, 3, 2, 2, 3, 3, 0, 1, 2
                ),
                listOf(
                    0, 1, 3, 3, 1, 1, 0, 3, 0, 0, 2, 3, 0, 0, 3, 1, 1, 3, 2, 2
                ),
                listOf(
                    3, 2, 2, 3, 1, 2, 1, 1, 3, 3, 3, 2, 2, 0, 3, 1, 0, 3, 3, 3
                ),
                listOf(
                    2, 0, 2, 3, 3, 3, 3, 1, 3, 3, 0, 3, 2, 0, 2, 2, 2, 0, 1, 1
                ),
                listOf(
                    1, 0, 2, 0, 0, 2, 2, 0, 3, 1, 2, 3, 2, 3, 2, 2, 0, 1, 2, 2
                ),
                listOf(
                    3, 3, 0, 1, 2, 0, 3, 2, 0, 0, 1, 0, 3, 1, 2, 3, 2, 2, 3, 3
                ),
                listOf(
                    0, 0, 0, 0, 2, 2, 3, 3, 0, 0, 3, 1, 2, 2, 0, 2, 3, 1, 3, 1
                )
            ), listOf(
                Tank(1, 10),
                Tank(1, 10),
                Tank(0, 25),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(0, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(0, 20),
                Tank(3, 25),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(2, 25),
                Tank(1, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(3, 20)
            )),
        )
    }

    private fun getChunk112(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_013", "Master", 20, 40, listOf(
                listOf(
                    3, 3, 1, 0, 3, 1, 2, 1, 3, 3, 3, 0, 1, 0, 3, 3, 0, 3, 3, 2
                ),
                listOf(
                    1, 3, 2, 2, 1, 3, 0, 2, 2, 2, 3, 1, 2, 1, 0, 3, 0, 0, 3, 1
                ),
                listOf(
                    3, 1, 3, 2, 1, 2, 2, 2, 3, 3, 2, 1, 3, 1, 0, 1, 3, 0, 0, 0
                ),
                listOf(
                    3, 2, 2, 2, 2, 3, 1, 3, 3, 3, 0, 3, 2, 3, 3, 2, 3, 1, 3, 0
                ),
                listOf(
                    0, 3, 0, 1, 3, 3, 1, 2, 1, 3, 2, 3, 3, 1, 1, 0, 1, 1, 2, 0
                ),
                listOf(
                    3, 2, 1, 1, 1, 3, 2, 0, 3, 2, 2, 3, 3, 1, 2, 1, 1, 3, 0, 0
                ),
                listOf(
                    0, 3, 3, 3, 3, 2, 3, 2, 2, 1, 2, 3, 3, 2, 2, 3, 2, 3, 2, 1
                ),
                listOf(
                    3, 1, 1, 3, 0, 3, 3, 1, 0, 3, 1, 2, 1, 1, 3, 1, 2, 3, 1, 3
                ),
                listOf(
                    0, 2, 3, 2, 3, 2, 3, 1, 1, 1, 1, 1, 3, 2, 0, 0, 1, 2, 2, 2
                ),
                listOf(
                    3, 1, 2, 3, 1, 0, 1, 3, 2, 2, 1, 0, 1, 2, 1, 0, 3, 1, 2, 3
                ),
                listOf(
                    2, 0, 2, 3, 3, 1, 1, 1, 3, 3, 3, 2, 3, 1, 1, 1, 1, 3, 1, 0
                ),
                listOf(
                    3, 3, 3, 2, 1, 2, 2, 2, 3, 3, 1, 2, 1, 0, 0, 3, 0, 3, 0, 3
                ),
                listOf(
                    1, 0, 3, 2, 3, 1, 2, 3, 3, 3, 2, 1, 0, 3, 3, 1, 2, 3, 2, 3
                ),
                listOf(
                    0, 3, 1, 1, 0, 2, 2, 2, 0, 3, 3, 1, 1, 2, 2, 0, 3, 1, 2, 1
                ),
                listOf(
                    3, 3, 1, 3, 3, 3, 1, 3, 2, 3, 0, 2, 0, 2, 3, 2, 3, 3, 2, 0
                ),
                listOf(
                    3, 3, 1, 1, 1, 0, 3, 2, 2, 0, 3, 2, 0, 1, 3, 2, 0, 3, 0, 3
                ),
                listOf(
                    2, 0, 1, 3, 1, 3, 3, 2, 3, 1, 0, 1, 3, 0, 0, 1, 0, 3, 1, 0
                ),
                listOf(
                    0, 0, 0, 3, 3, 2, 3, 2, 3, 0, 3, 1, 2, 2, 0, 2, 1, 0, 2, 1
                ),
                listOf(
                    3, 2, 2, 3, 1, 1, 2, 0, 3, 1, 2, 0, 3, 3, 3, 1, 0, 0, 0, 2
                ),
                listOf(
                    2, 0, 1, 3, 1, 3, 3, 3, 0, 0, 3, 1, 2, 1, 0, 1, 3, 2, 3, 0
                ),
                listOf(
                    2, 1, 2, 0, 1, 3, 3, 1, 1, 3, 3, 1, 1, 2, 2, 1, 3, 3, 2, 2
                ),
                listOf(
                    2, 1, 0, 3, 0, 3, 3, 0, 2, 1, 3, 2, 0, 1, 1, 1, 0, 3, 3, 2
                ),
                listOf(
                    2, 3, 1, 2, 2, 2, 2, 3, 1, 2, 2, 2, 1, 0, 3, 3, 3, 1, 1, 1
                ),
                listOf(
                    2, 2, 2, 0, 3, 1, 3, 2, 2, 1, 3, 2, 2, 0, 3, 0, 1, 0, 1, 3
                ),
                listOf(
                    2, 3, 3, 1, 3, 2, 0, 0, 3, 2, 3, 0, 2, 3, 3, 3, 2, 1, 1, 3
                ),
                listOf(
                    3, 1, 1, 0, 3, 1, 3, 1, 0, 3, 2, 3, 3, 1, 2, 3, 3, 1, 2, 3
                ),
                listOf(
                    1, 1, 2, 3, 2, 1, 2, 2, 3, 3, 3, 2, 3, 2, 2, 2, 3, 2, 1, 3
                ),
                listOf(
                    2, 2, 3, 2, 1, 0, 1, 3, 2, 1, 3, 3, 3, 0, 2, 1, 2, 3, 3, 1
                ),
                listOf(
                    2, 3, 2, 2, 3, 2, 1, 3, 2, 3, 3, 0, 0, 3, 2, 0, 0, 2, 1, 0
                ),
                listOf(
                    1, 1, 3, 0, 0, 2, 3, 2, 0, 0, 3, 1, 1, 2, 3, 1, 3, 1, 0, 2
                ),
                listOf(
                    0, 3, 3, 1, 2, 2, 2, 1, 0, 1, 2, 0, 3, 1, 1, 0, 3, 3, 3, 3
                ),
                listOf(
                    3, 3, 1, 3, 0, 2, 3, 0, 1, 1, 1, 3, 2, 3, 3, 1, 2, 1, 1, 2
                ),
                listOf(
                    1, 1, 2, 1, 3, 3, 2, 1, 2, 1, 1, 1, 2, 1, 1, 0, 1, 1, 2, 3
                ),
                listOf(
                    1, 1, 3, 1, 0, 1, 3, 3, 3, 0, 3, 0, 3, 3, 3, 2, 3, 1, 0, 2
                ),
                listOf(
                    1, 1, 2, 0, 1, 2, 3, 2, 3, 1, 3, 0, 0, 3, 3, 1, 3, 1, 3, 2
                ),
                listOf(
                    1, 3, 3, 2, 1, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 0, 0, 1, 0
                ),
                listOf(
                    2, 1, 1, 3, 3, 2, 3, 3, 2, 2, 1, 2, 1, 3, 2, 3, 1, 2, 3, 1
                ),
                listOf(
                    1, 3, 2, 2, 1, 2, 3, 1, 2, 3, 2, 1, 3, 3, 2, 1, 0, 3, 3, 1
                ),
                listOf(
                    0, 3, 1, 3, 3, 0, 1, 3, 3, 3, 1, 1, 1, 0, 1, 3, 1, 0, 2, 1
                ),
                listOf(
                    2, 0, 1, 1, 2, 2, 1, 2, 0, 3, 3, 0, 2, 3, 2, 1, 2, 2, 3, 2
                )
            ), listOf(
                Tank(3, 25),
                Tank(3, 20),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(0, 25),
                Tank(1, 20),
                Tank(2, 20),
                Tank(3, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(2, 20),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 10),
                Tank(3, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk113(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_014", "Master", 20, 40, listOf(
                listOf(
                    1, 3, 2, 3, 0, 1, 0, 3, 1, 0, 0, 3, 2, 1, 3, 2, 0, 1, 2, 2
                ),
                listOf(
                    2, 3, 3, 2, 1, 2, 2, 1, 2, 3, 3, 0, 1, 0, 1, 2, 0, 1, 0, 1
                ),
                listOf(
                    3, 2, 1, 3, 0, 3, 0, 3, 1, 1, 0, 2, 3, 3, 0, 0, 1, 0, 1, 3
                ),
                listOf(
                    1, 0, 3, 2, 0, 0, 0, 1, 3, 1, 2, 2, 2, 3, 3, 3, 2, 3, 1, 2
                ),
                listOf(
                    3, 3, 2, 1, 2, 1, 3, 2, 2, 0, 3, 1, 3, 0, 1, 2, 2, 0, 0, 3
                ),
                listOf(
                    0, 0, 0, 2, 0, 1, 0, 2, 3, 1, 2, 0, 0, 2, 0, 2, 2, 1, 1, 0
                ),
                listOf(
                    2, 2, 2, 0, 1, 3, 3, 1, 0, 3, 0, 3, 1, 3, 1, 3, 3, 0, 0, 1
                ),
                listOf(
                    1, 1, 0, 0, 3, 3, 2, 0, 3, 0, 2, 1, 0, 2, 3, 3, 2, 1, 0, 0
                ),
                listOf(
                    0, 1, 1, 2, 2, 3, 3, 1, 0, 3, 2, 3, 1, 3, 1, 1, 0, 1, 3, 3
                ),
                listOf(
                    0, 1, 3, 3, 3, 2, 2, 0, 2, 3, 0, 2, 2, 1, 1, 1, 2, 1, 2, 1
                ),
                listOf(
                    0, 2, 3, 2, 3, 1, 0, 2, 3, 3, 1, 3, 0, 1, 0, 0, 1, 3, 3, 1
                ),
                listOf(
                    0, 1, 3, 3, 3, 3, 2, 1, 3, 3, 3, 0, 1, 0, 2, 3, 0, 1, 1, 1
                ),
                listOf(
                    0, 0, 1, 2, 3, 3, 1, 3, 0, 1, 0, 0, 0, 2, 0, 3, 1, 0, 2, 0
                ),
                listOf(
                    2, 3, 3, 3, 0, 3, 2, 2, 0, 2, 0, 1, 0, 3, 1, 3, 0, 1, 0, 2
                ),
                listOf(
                    3, 2, 3, 3, 0, 0, 3, 3, 0, 0, 0, 1, 0, 0, 1, 3, 1, 0, 2, 2
                ),
                listOf(
                    2, 0, 3, 1, 1, 0, 3, 2, 1, 0, 3, 3, 3, 0, 3, 1, 2, 2, 3, 0
                ),
                listOf(
                    3, 0, 0, 3, 3, 2, 1, 0, 0, 1, 1, 0, 1, 1, 3, 1, 3, 1, 0, 3
                ),
                listOf(
                    1, 0, 2, 1, 2, 0, 2, 2, 0, 1, 0, 3, 3, 3, 0, 3, 0, 2, 2, 3
                ),
                listOf(
                    1, 2, 0, 0, 0, 1, 2, 1, 0, 1, 2, 3, 3, 3, 3, 2, 0, 3, 3, 0
                ),
                listOf(
                    2, 2, 1, 1, 1, 2, 2, 1, 1, 0, 3, 3, 2, 1, 0, 3, 3, 2, 1, 2
                ),
                listOf(
                    2, 0, 1, 1, 2, 0, 0, 3, 0, 0, 2, 1, 1, 3, 0, 1, 2, 0, 0, 3
                ),
                listOf(
                    0, 2, 1, 0, 2, 1, 2, 3, 0, 3, 1, 3, 1, 1, 3, 1, 0, 2, 3, 2
                ),
                listOf(
                    1, 3, 0, 0, 1, 1, 0, 0, 0, 1, 1, 3, 1, 2, 0, 3, 1, 1, 3, 0
                ),
                listOf(
                    0, 0, 2, 0, 0, 0, 1, 2, 3, 3, 0, 3, 1, 0, 1, 3, 2, 0, 3, 0
                ),
                listOf(
                    3, 0, 2, 3, 2, 0, 3, 1, 3, 3, 3, 3, 1, 1, 0, 0, 2, 2, 3, 1
                ),
                listOf(
                    1, 1, 2, 1, 3, 3, 3, 2, 0, 1, 2, 2, 1, 2, 3, 2, 1, 0, 3, 1
                ),
                listOf(
                    3, 1, 0, 3, 1, 3, 2, 1, 1, 3, 3, 0, 0, 0, 2, 1, 1, 2, 2, 0
                ),
                listOf(
                    3, 0, 0, 1, 0, 3, 2, 2, 1, 2, 0, 2, 0, 0, 2, 1, 0, 1, 3, 3
                ),
                listOf(
                    3, 2, 0, 1, 1, 0, 3, 1, 2, 2, 3, 3, 0, 1, 3, 3, 1, 2, 0, 2
                ),
                listOf(
                    0, 3, 0, 3, 3, 2, 0, 3, 2, 3, 3, 1, 2, 1, 1, 3, 0, 2, 2, 2
                ),
                listOf(
                    1, 0, 1, 2, 0, 0, 0, 0, 3, 3, 3, 3, 2, 2, 0, 3, 0, 0, 2, 1
                ),
                listOf(
                    2, 0, 1, 1, 2, 1, 0, 2, 3, 3, 1, 2, 1, 1, 1, 2, 0, 3, 1, 1
                ),
                listOf(
                    0, 3, 1, 0, 1, 0, 3, 2, 3, 0, 0, 0, 2, 2, 3, 0, 2, 2, 1, 3
                ),
                listOf(
                    0, 1, 0, 1, 3, 2, 2, 2, 0, 3, 3, 0, 0, 1, 1, 0, 0, 3, 3, 0
                ),
                listOf(
                    3, 1, 0, 2, 1, 3, 3, 1, 3, 2, 2, 0, 0, 2, 0, 0, 1, 0, 1, 0
                ),
                listOf(
                    0, 2, 1, 3, 1, 3, 2, 1, 3, 2, 2, 2, 3, 1, 3, 1, 1, 3, 2, 2
                ),
                listOf(
                    3, 2, 0, 2, 0, 0, 3, 0, 2, 0, 3, 2, 3, 0, 0, 3, 0, 0, 3, 2
                ),
                listOf(
                    3, 1, 2, 2, 0, 3, 1, 1, 1, 2, 1, 0, 1, 0, 2, 3, 1, 0, 1, 0
                ),
                listOf(
                    0, 2, 2, 3, 3, 3, 0, 1, 3, 3, 2, 0, 1, 1, 0, 0, 1, 3, 2, 3
                ),
                listOf(
                    1, 2, 2, 0, 1, 0, 3, 1, 0, 2, 0, 3, 3, 0, 1, 1, 2, 0, 1, 2
                )
            ), listOf(
                Tank(3, 10),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 10),
                Tank(0, 25),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 25),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 15),
                Tank(2, 25),
                Tank(2, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(1, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk114(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_015", "Master", 20, 40, listOf(
                listOf(
                    3, 1, 2, 0, 1, 3, 3, 3, 2, 3, 1, 3, 0, 3, 1, 2, 3, 0, 3, 3
                ),
                listOf(
                    3, 2, 0, 0, 2, 1, 3, 3, 0, 0, 2, 2, 3, 0, 1, 3, 0, 2, 1, 3
                ),
                listOf(
                    2, 1, 1, 3, 1, 2, 3, 3, 2, 3, 3, 2, 0, 3, 2, 1, 0, 0, 2, 3
                ),
                listOf(
                    2, 0, 0, 0, 0, 1, 2, 1, 1, 2, 0, 0, 3, 2, 1, 3, 0, 0, 0, 1
                ),
                listOf(
                    3, 3, 0, 2, 2, 1, 2, 2, 1, 2, 0, 3, 0, 3, 0, 3, 1, 0, 0, 0
                ),
                listOf(
                    1, 1, 2, 3, 3, 2, 3, 3, 0, 3, 3, 2, 2, 3, 3, 2, 3, 2, 3, 3
                ),
                listOf(
                    1, 3, 1, 1, 1, 2, 1, 2, 3, 0, 0, 1, 1, 3, 3, 1, 2, 2, 3, 1
                ),
                listOf(
                    1, 1, 3, 3, 3, 0, 0, 1, 1, 2, 2, 1, 1, 0, 3, 3, 1, 2, 3, 0
                ),
                listOf(
                    1, 3, 1, 3, 0, 2, 0, 2, 2, 0, 2, 2, 2, 3, 1, 3, 0, 1, 0, 1
                ),
                listOf(
                    0, 3, 1, 1, 2, 3, 2, 3, 1, 2, 1, 3, 2, 2, 2, 3, 1, 1, 1, 3
                ),
                listOf(
                    2, 3, 1, 2, 1, 3, 1, 1, 2, 1, 0, 1, 2, 0, 3, 2, 2, 0, 2, 2
                ),
                listOf(
                    3, 1, 2, 1, 1, 0, 3, 0, 2, 2, 2, 2, 2, 1, 2, 0, 0, 1, 1, 3
                ),
                listOf(
                    3, 2, 3, 0, 2, 2, 1, 3, 3, 2, 0, 0, 0, 3, 1, 3, 3, 2, 0, 3
                ),
                listOf(
                    2, 1, 3, 1, 3, 3, 3, 0, 3, 3, 2, 3, 0, 1, 0, 0, 3, 3, 3, 3
                ),
                listOf(
                    3, 0, 2, 1, 3, 3, 3, 1, 0, 0, 1, 3, 0, 3, 0, 0, 0, 2, 2, 3
                ),
                listOf(
                    1, 3, 0, 3, 3, 2, 0, 2, 2, 3, 0, 2, 2, 3, 1, 0, 1, 0, 1, 0
                ),
                listOf(
                    1, 0, 2, 3, 2, 1, 1, 0, 2, 1, 2, 2, 0, 3, 3, 0, 0, 3, 0, 1
                ),
                listOf(
                    2, 3, 3, 3, 3, 0, 0, 0, 2, 1, 0, 2, 2, 3, 3, 0, 3, 1, 3, 2
                ),
                listOf(
                    0, 1, 2, 3, 2, 3, 3, 3, 0, 2, 3, 0, 0, 2, 0, 3, 3, 3, 1, 0
                ),
                listOf(
                    3, 1, 1, 0, 2, 1, 0, 2, 1, 3, 2, 3, 0, 2, 0, 2, 1, 1, 0, 1
                ),
                listOf(
                    1, 1, 0, 2, 2, 3, 3, 1, 2, 1, 1, 0, 3, 3, 3, 1, 0, 3, 0, 3
                ),
                listOf(
                    3, 3, 3, 1, 3, 3, 1, 1, 3, 1, 0, 0, 3, 3, 2, 2, 3, 2, 3, 1
                ),
                listOf(
                    0, 0, 2, 2, 2, 3, 3, 0, 3, 0, 3, 2, 3, 0, 1, 3, 0, 3, 1, 2
                ),
                listOf(
                    1, 2, 1, 0, 2, 1, 2, 0, 0, 3, 3, 0, 2, 3, 0, 2, 3, 0, 1, 3
                ),
                listOf(
                    1, 3, 1, 1, 2, 1, 1, 1, 2, 0, 3, 1, 3, 0, 1, 3, 2, 3, 2, 1
                ),
                listOf(
                    0, 2, 0, 2, 3, 2, 3, 2, 2, 3, 1, 0, 1, 3, 3, 1, 0, 2, 2, 3
                ),
                listOf(
                    2, 1, 3, 0, 3, 2, 0, 1, 3, 3, 2, 1, 3, 2, 1, 0, 2, 1, 1, 1
                ),
                listOf(
                    0, 3, 1, 3, 0, 2, 1, 1, 0, 2, 3, 3, 1, 2, 1, 0, 3, 1, 2, 2
                ),
                listOf(
                    1, 3, 1, 3, 3, 1, 1, 3, 0, 3, 2, 2, 0, 3, 1, 2, 3, 3, 3, 3
                ),
                listOf(
                    2, 2, 3, 1, 2, 1, 0, 1, 3, 3, 2, 2, 3, 3, 0, 2, 1, 2, 1, 0
                ),
                listOf(
                    1, 2, 2, 1, 3, 1, 1, 3, 0, 1, 0, 1, 3, 1, 3, 0, 0, 1, 0, 1
                ),
                listOf(
                    1, 2, 2, 3, 2, 3, 1, 1, 1, 0, 0, 2, 0, 1, 3, 1, 1, 3, 3, 1
                ),
                listOf(
                    2, 2, 0, 3, 1, 2, 3, 3, 2, 1, 3, 0, 2, 3, 0, 0, 0, 2, 3, 0
                ),
                listOf(
                    0, 3, 1, 1, 3, 0, 3, 0, 2, 0, 1, 1, 1, 1, 2, 3, 3, 2, 0, 1
                ),
                listOf(
                    1, 3, 0, 2, 2, 1, 3, 3, 0, 2, 3, 3, 1, 0, 1, 3, 2, 0, 3, 3
                ),
                listOf(
                    1, 2, 0, 0, 1, 0, 1, 0, 0, 0, 3, 3, 2, 2, 0, 0, 0, 3, 2, 3
                ),
                listOf(
                    0, 3, 0, 2, 2, 3, 0, 3, 2, 3, 0, 3, 3, 1, 0, 2, 2, 0, 3, 0
                ),
                listOf(
                    2, 3, 3, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 1, 3, 0
                ),
                listOf(
                    3, 3, 2, 2, 0, 3, 1, 3, 1, 3, 1, 2, 0, 0, 3, 3, 1, 2, 3, 0
                ),
                listOf(
                    0, 0, 0, 3, 1, 1, 3, 3, 1, 2, 3, 3, 1, 1, 1, 3, 3, 1, 0, 1
                )
            ), listOf(
                Tank(0, 20),
                Tank(2, 10),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 10),
                Tank(1, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(3, 20),
                Tank(0, 15),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 15),
                Tank(0, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 25),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk115(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_016", "Master", 20, 40, listOf(
                listOf(
                    0, 3, 2, 3, 3, 0, 1, 1, 1, 3, 0, 3, 1, 1, 2, 0, 1, 0, 3, 0
                ),
                listOf(
                    0, 2, 0, 3, 1, 2, 3, 0, 0, 2, 1, 1, 3, 0, 3, 2, 1, 2, 3, 1
                ),
                listOf(
                    0, 0, 2, 2, 3, 1, 2, 3, 1, 1, 3, 2, 1, 1, 2, 0, 3, 3, 0, 1
                ),
                listOf(
                    3, 1, 2, 3, 2, 2, 2, 2, 1, 1, 2, 2, 1, 3, 2, 2, 1, 2, 1, 1
                ),
                listOf(
                    1, 0, 3, 3, 0, 0, 0, 2, 3, 2, 0, 2, 0, 1, 0, 3, 2, 2, 0, 1
                ),
                listOf(
                    0, 3, 3, 2, 3, 3, 2, 2, 3, 0, 1, 3, 3, 2, 1, 1, 1, 2, 2, 0
                ),
                listOf(
                    2, 2, 3, 0, 3, 3, 0, 1, 0, 2, 1, 3, 3, 3, 0, 0, 2, 1, 2, 2
                ),
                listOf(
                    1, 1, 3, 2, 0, 3, 3, 1, 3, 3, 2, 2, 1, 2, 2, 3, 2, 1, 0, 2
                ),
                listOf(
                    3, 1, 3, 0, 3, 3, 1, 2, 0, 3, 1, 1, 3, 0, 3, 2, 0, 1, 2, 0
                ),
                listOf(
                    3, 0, 0, 1, 3, 2, 0, 3, 1, 3, 0, 3, 1, 0, 2, 0, 2, 0, 3, 3
                ),
                listOf(
                    3, 2, 2, 3, 1, 2, 1, 0, 1, 3, 0, 2, 1, 3, 3, 0, 3, 3, 1, 0
                ),
                listOf(
                    3, 2, 1, 0, 2, 3, 3, 0, 2, 0, 1, 2, 3, 3, 1, 0, 3, 2, 3, 2
                ),
                listOf(
                    0, 3, 3, 3, 2, 3, 0, 2, 3, 3, 1, 3, 2, 3, 1, 0, 0, 2, 3, 0
                ),
                listOf(
                    1, 0, 3, 2, 0, 2, 1, 0, 0, 2, 3, 1, 1, 1, 3, 3, 3, 3, 3, 3
                ),
                listOf(
                    0, 2, 2, 3, 3, 0, 3, 0, 2, 3, 3, 3, 3, 0, 2, 2, 1, 1, 2, 2
                ),
                listOf(
                    0, 2, 3, 3, 2, 3, 0, 1, 2, 0, 0, 2, 3, 1, 2, 0, 3, 2, 1, 3
                ),
                listOf(
                    3, 3, 1, 3, 0, 2, 1, 0, 2, 0, 0, 1, 3, 1, 1, 1, 1, 2, 0, 1
                ),
                listOf(
                    2, 1, 0, 3, 1, 2, 2, 2, 0, 1, 0, 0, 3, 2, 3, 3, 3, 2, 3, 1
                ),
                listOf(
                    0, 3, 2, 3, 1, 2, 0, 3, 3, 1, 2, 2, 0, 0, 3, 1, 1, 2, 1, 1
                ),
                listOf(
                    0, 3, 2, 2, 0, 2, 2, 1, 2, 1, 0, 0, 2, 2, 2, 3, 1, 2, 2, 3
                ),
                listOf(
                    2, 0, 3, 2, 1, 2, 2, 3, 2, 1, 2, 0, 2, 2, 1, 0, 3, 2, 3, 0
                ),
                listOf(
                    0, 2, 3, 0, 0, 3, 2, 3, 2, 3, 1, 2, 2, 0, 0, 1, 0, 1, 1, 0
                ),
                listOf(
                    0, 0, 3, 0, 2, 0, 2, 1, 3, 2, 3, 2, 2, 1, 0, 1, 0, 2, 0, 2
                ),
                listOf(
                    1, 3, 3, 3, 2, 3, 0, 2, 1, 2, 2, 3, 3, 3, 1, 0, 2, 1, 2, 1
                ),
                listOf(
                    2, 3, 3, 3, 2, 0, 3, 1, 0, 3, 2, 1, 3, 1, 3, 0, 2, 0, 3, 1
                ),
                listOf(
                    2, 3, 0, 2, 3, 0, 3, 2, 3, 3, 2, 1, 1, 1, 0, 0, 0, 0, 2, 3
                ),
                listOf(
                    0, 3, 3, 1, 1, 3, 1, 1, 0, 2, 1, 0, 0, 2, 2, 1, 0, 2, 3, 0
                ),
                listOf(
                    0, 3, 0, 3, 1, 3, 0, 3, 2, 1, 3, 3, 3, 3, 3, 2, 1, 1, 1, 2
                ),
                listOf(
                    1, 2, 3, 3, 3, 1, 0, 3, 2, 3, 0, 1, 3, 0, 1, 0, 0, 3, 2, 3
                ),
                listOf(
                    3, 0, 3, 3, 1, 3, 3, 0, 1, 2, 0, 3, 2, 0, 0, 3, 2, 2, 3, 2
                ),
                listOf(
                    3, 3, 2, 2, 2, 1, 1, 1, 2, 1, 3, 2, 3, 3, 0, 1, 3, 1, 2, 2
                ),
                listOf(
                    0, 1, 0, 3, 3, 0, 0, 3, 2, 2, 1, 2, 1, 1, 2, 0, 2, 1, 0, 2
                ),
                listOf(
                    3, 1, 2, 2, 3, 2, 0, 1, 0, 3, 2, 3, 2, 0, 1, 2, 3, 2, 2, 0
                ),
                listOf(
                    2, 1, 3, 3, 2, 2, 1, 0, 0, 0, 3, 0, 1, 3, 2, 2, 2, 1, 0, 2
                ),
                listOf(
                    0, 0, 3, 1, 0, 0, 1, 1, 3, 0, 1, 0, 2, 0, 3, 2, 2, 0, 2, 2
                ),
                listOf(
                    2, 2, 3, 3, 3, 3, 0, 0, 1, 1, 3, 3, 3, 0, 1, 0, 1, 3, 3, 1
                ),
                listOf(
                    0, 1, 3, 1, 0, 0, 2, 0, 2, 0, 0, 1, 3, 0, 2, 0, 1, 0, 2, 0
                ),
                listOf(
                    1, 3, 1, 2, 3, 2, 2, 0, 3, 1, 1, 0, 3, 1, 1, 3, 2, 3, 0, 2
                ),
                listOf(
                    1, 0, 3, 2, 1, 0, 3, 1, 3, 2, 3, 0, 2, 2, 3, 2, 2, 0, 2, 3
                ),
                listOf(
                    1, 1, 0, 3, 3, 2, 0, 2, 2, 0, 0, 0, 1, 2, 0, 2, 2, 0, 0, 2
                )
            ), listOf(
                Tank(3, 15),
                Tank(3, 15),
                Tank(2, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(0, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(3, 10),
                Tank(1, 15),
                Tank(3, 15),
                Tank(3, 10),
                Tank(0, 25),
                Tank(0, 15),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 20),
                Tank(3, 10),
                Tank(2, 15),
                Tank(1, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(2, 10),
                Tank(2, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(2, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 20),
                Tank(3, 15),
                Tank(1, 20),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk116(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_017", "Master", 20, 40, listOf(
                listOf(
                    1, 0, 2, 2, 1, 3, 2, 2, 2, 1, 1, 0, 0, 1, 3, 0, 1, 1, 0, 0
                ),
                listOf(
                    2, 3, 1, 1, 1, 0, 0, 2, 2, 3, 0, 1, 2, 1, 2, 2, 3, 3, 2, 2
                ),
                listOf(
                    3, 1, 3, 1, 0, 0, 2, 1, 1, 0, 3, 2, 3, 3, 1, 2, 2, 3, 1, 0
                ),
                listOf(
                    1, 1, 3, 3, 1, 2, 3, 2, 0, 3, 0, 1, 1, 0, 3, 1, 1, 2, 0, 1
                ),
                listOf(
                    0, 1, 0, 1, 3, 1, 3, 3, 3, 2, 2, 0, 2, 2, 3, 3, 1, 3, 1, 3
                ),
                listOf(
                    2, 1, 1, 2, 3, 2, 0, 2, 1, 3, 2, 1, 3, 2, 2, 1, 2, 0, 2, 2
                ),
                listOf(
                    1, 1, 3, 2, 1, 2, 1, 3, 1, 3, 3, 2, 2, 3, 2, 1, 1, 2, 0, 1
                ),
                listOf(
                    3, 1, 0, 0, 3, 2, 3, 3, 2, 1, 0, 1, 0, 2, 0, 1, 3, 3, 2, 3
                ),
                listOf(
                    0, 1, 0, 3, 0, 2, 0, 0, 2, 1, 3, 3, 2, 0, 2, 3, 0, 1, 1, 1
                ),
                listOf(
                    0, 3, 1, 0, 1, 1, 1, 3, 3, 2, 3, 2, 1, 1, 3, 3, 1, 3, 0, 3
                ),
                listOf(
                    1, 2, 2, 3, 3, 2, 2, 1, 0, 0, 2, 2, 2, 1, 3, 0, 1, 2, 2, 3
                ),
                listOf(
                    3, 1, 2, 3, 1, 0, 1, 1, 2, 1, 1, 3, 1, 2, 2, 2, 1, 0, 3, 2
                ),
                listOf(
                    3, 0, 3, 0, 0, 2, 1, 3, 0, 0, 0, 1, 1, 1, 3, 2, 1, 1, 3, 1
                ),
                listOf(
                    3, 0, 3, 3, 2, 2, 3, 2, 0, 2, 3, 3, 2, 2, 0, 1, 0, 2, 0, 3
                ),
                listOf(
                    1, 2, 1, 0, 2, 3, 1, 0, 1, 3, 0, 3, 3, 3, 3, 3, 2, 1, 1, 0
                ),
                listOf(
                    3, 2, 2, 2, 2, 0, 2, 0, 0, 2, 0, 0, 1, 2, 3, 0, 3, 1, 1, 2
                ),
                listOf(
                    0, 1, 2, 2, 2, 1, 1, 2, 0, 0, 3, 3, 2, 2, 1, 1, 0, 3, 2, 2
                ),
                listOf(
                    2, 3, 3, 0, 3, 0, 0, 3, 3, 2, 1, 0, 1, 3, 2, 2, 2, 1, 0, 1
                ),
                listOf(
                    1, 2, 0, 2, 3, 0, 1, 1, 0, 1, 2, 0, 0, 3, 3, 0, 2, 2, 2, 3
                ),
                listOf(
                    2, 2, 3, 0, 1, 1, 1, 2, 2, 1, 0, 2, 3, 1, 2, 0, 0, 3, 2, 2
                ),
                listOf(
                    1, 1, 2, 2, 0, 3, 2, 1, 1, 2, 0, 3, 1, 3, 2, 3, 3, 2, 0, 1
                ),
                listOf(
                    2, 3, 2, 2, 3, 3, 1, 0, 3, 0, 2, 3, 2, 1, 2, 2, 2, 2, 2, 0
                ),
                listOf(
                    1, 3, 1, 0, 0, 2, 2, 2, 1, 2, 2, 3, 2, 0, 0, 2, 0, 3, 2, 0
                ),
                listOf(
                    1, 0, 0, 2, 1, 0, 2, 2, 1, 2, 2, 2, 1, 3, 1, 3, 2, 1, 0, 1
                ),
                listOf(
                    2, 1, 0, 2, 1, 1, 2, 0, 0, 3, 3, 0, 3, 2, 0, 2, 2, 1, 1, 0
                ),
                listOf(
                    1, 3, 3, 0, 2, 1, 3, 1, 3, 2, 1, 0, 3, 2, 3, 0, 2, 1, 3, 0
                ),
                listOf(
                    3, 3, 2, 2, 2, 1, 2, 2, 3, 2, 3, 2, 2, 0, 0, 1, 0, 2, 0, 2
                ),
                listOf(
                    1, 1, 2, 3, 0, 1, 0, 3, 3, 3, 0, 3, 1, 1, 1, 3, 0, 2, 2, 3
                ),
                listOf(
                    2, 2, 3, 2, 1, 2, 2, 1, 1, 3, 2, 1, 1, 2, 1, 1, 1, 2, 2, 1
                ),
                listOf(
                    3, 2, 3, 0, 2, 1, 1, 3, 3, 0, 1, 3, 2, 2, 2, 3, 3, 3, 3, 0
                ),
                listOf(
                    3, 3, 0, 1, 2, 2, 2, 2, 3, 3, 3, 2, 1, 3, 3, 3, 2, 3, 2, 1
                ),
                listOf(
                    2, 2, 2, 3, 3, 2, 2, 1, 3, 3, 3, 0, 3, 2, 3, 3, 3, 0, 1, 2
                ),
                listOf(
                    2, 2, 2, 1, 1, 0, 2, 0, 1, 1, 1, 0, 0, 1, 3, 1, 0, 2, 3, 2
                ),
                listOf(
                    3, 2, 0, 0, 1, 2, 1, 1, 3, 1, 1, 0, 3, 0, 3, 0, 3, 2, 1, 2
                ),
                listOf(
                    0, 3, 2, 2, 1, 2, 2, 0, 1, 2, 1, 2, 2, 0, 2, 3, 3, 1, 2, 3
                ),
                listOf(
                    0, 1, 1, 2, 1, 3, 3, 0, 1, 0, 2, 1, 3, 2, 1, 1, 3, 0, 1, 0
                ),
                listOf(
                    2, 3, 0, 1, 2, 3, 3, 0, 1, 0, 1, 3, 1, 0, 0, 3, 1, 0, 3, 1
                ),
                listOf(
                    1, 3, 2, 3, 2, 2, 2, 0, 2, 2, 1, 2, 2, 3, 1, 0, 0, 0, 2, 2
                ),
                listOf(
                    1, 0, 1, 3, 0, 0, 1, 0, 0, 2, 0, 1, 1, 3, 2, 2, 0, 2, 0, 0
                ),
                listOf(
                    0, 0, 0, 3, 1, 2, 3, 1, 1, 3, 3, 2, 0, 2, 1, 3, 1, 1, 3, 0
                )
            ), listOf(
                Tank(3, 15),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 10),
                Tank(2, 20),
                Tank(2, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 10),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(2, 20),
                Tank(2, 10),
                Tank(2, 20),
                Tank(1, 10),
                Tank(1, 25),
                Tank(0, 20),
                Tank(0, 15),
                Tank(3, 20),
                Tank(1, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(2, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(0, 10),
                Tank(3, 15),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk117(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_018", "Master", 20, 40, listOf(
                listOf(
                    1, 0, 0, 0, 1, 1, 0, 1, 3, 0, 3, 0, 0, 0, 3, 3, 0, 2, 3, 0
                ),
                listOf(
                    3, 0, 3, 1, 2, 0, 0, 2, 3, 1, 2, 3, 0, 1, 3, 1, 2, 0, 1, 0
                ),
                listOf(
                    2, 3, 2, 2, 3, 0, 2, 3, 1, 3, 1, 1, 0, 2, 0, 0, 3, 0, 0, 3
                ),
                listOf(
                    2, 0, 3, 0, 3, 3, 3, 1, 1, 3, 2, 1, 3, 1, 2, 1, 2, 2, 3, 2
                ),
                listOf(
                    1, 3, 3, 3, 3, 3, 2, 2, 3, 3, 1, 0, 1, 0, 0, 3, 3, 0, 3, 2
                ),
                listOf(
                    0, 2, 3, 2, 3, 2, 2, 3, 1, 3, 2, 3, 3, 0, 1, 1, 3, 2, 0, 2
                ),
                listOf(
                    2, 0, 3, 2, 2, 0, 2, 1, 3, 3, 1, 1, 3, 3, 3, 2, 3, 0, 3, 3
                ),
                listOf(
                    3, 3, 0, 3, 0, 0, 0, 3, 0, 2, 0, 2, 3, 2, 1, 1, 0, 1, 3, 1
                ),
                listOf(
                    3, 1, 3, 3, 2, 1, 2, 1, 3, 3, 1, 0, 2, 3, 3, 1, 2, 0, 3, 3
                ),
                listOf(
                    2, 3, 2, 3, 2, 0, 3, 3, 3, 0, 1, 1, 3, 2, 1, 0, 3, 3, 3, 0
                ),
                listOf(
                    0, 0, 3, 2, 3, 0, 0, 2, 2, 3, 1, 0, 3, 3, 0, 0, 3, 2, 2, 3
                ),
                listOf(
                    3, 1, 3, 0, 2, 2, 2, 3, 0, 2, 1, 2, 2, 3, 3, 0, 0, 2, 1, 2
                ),
                listOf(
                    2, 2, 0, 2, 0, 0, 1, 0, 2, 3, 1, 1, 0, 2, 1, 3, 0, 3, 3, 2
                ),
                listOf(
                    1, 2, 3, 2, 1, 0, 2, 3, 0, 1, 0, 1, 1, 0, 3, 0, 0, 1, 2, 1
                ),
                listOf(
                    0, 3, 0, 3, 0, 1, 0, 3, 2, 0, 3, 1, 3, 3, 2, 2, 0, 1, 3, 1
                ),
                listOf(
                    1, 3, 0, 0, 1, 3, 1, 3, 1, 2, 3, 3, 0, 3, 0, 3, 0, 0, 2, 1
                ),
                listOf(
                    1, 1, 0, 2, 3, 0, 1, 0, 3, 1, 0, 3, 1, 3, 0, 0, 0, 0, 1, 2
                ),
                listOf(
                    3, 3, 0, 0, 0, 2, 3, 1, 2, 3, 2, 0, 2, 2, 0, 2, 2, 3, 1, 2
                ),
                listOf(
                    2, 1, 2, 2, 0, 3, 2, 3, 0, 3, 3, 2, 1, 0, 1, 1, 3, 2, 2, 2
                ),
                listOf(
                    3, 0, 3, 2, 3, 1, 2, 1, 3, 3, 3, 3, 1, 0, 0, 2, 2, 3, 0, 3
                ),
                listOf(
                    2, 1, 2, 2, 0, 0, 3, 0, 3, 3, 0, 3, 0, 3, 0, 0, 1, 1, 2, 0
                ),
                listOf(
                    2, 0, 1, 0, 3, 3, 0, 2, 2, 2, 1, 2, 3, 3, 0, 1, 1, 3, 3, 3
                ),
                listOf(
                    3, 1, 2, 1, 2, 3, 0, 2, 1, 1, 0, 2, 3, 3, 0, 3, 3, 1, 1, 2
                ),
                listOf(
                    0, 1, 0, 3, 3, 3, 3, 3, 1, 3, 1, 0, 0, 2, 1, 1, 2, 2, 2, 0
                ),
                listOf(
                    1, 3, 2, 0, 3, 0, 3, 0, 1, 3, 1, 0, 1, 1, 3, 1, 0, 0, 3, 1
                ),
                listOf(
                    1, 3, 2, 0, 3, 0, 3, 2, 0, 0, 0, 3, 3, 2, 2, 1, 3, 1, 2, 0
                ),
                listOf(
                    3, 0, 3, 0, 3, 3, 2, 3, 0, 3, 2, 1, 1, 0, 2, 2, 1, 0, 0, 2
                ),
                listOf(
                    2, 0, 2, 2, 0, 2, 1, 2, 0, 3, 1, 1, 3, 0, 2, 3, 0, 1, 0, 0
                ),
                listOf(
                    1, 3, 3, 2, 1, 0, 3, 0, 1, 2, 0, 1, 0, 3, 1, 2, 1, 0, 1, 3
                ),
                listOf(
                    2, 1, 3, 1, 3, 2, 1, 1, 1, 2, 0, 3, 0, 1, 1, 3, 3, 1, 1, 1
                ),
                listOf(
                    1, 3, 3, 0, 3, 2, 3, 0, 3, 2, 3, 3, 3, 1, 3, 3, 3, 0, 3, 1
                ),
                listOf(
                    2, 3, 0, 2, 2, 0, 3, 3, 3, 2, 0, 0, 2, 1, 3, 2, 0, 0, 1, 1
                ),
                listOf(
                    3, 3, 1, 0, 2, 2, 2, 3, 0, 2, 3, 3, 3, 0, 0, 3, 3, 3, 1, 0
                ),
                listOf(
                    3, 0, 1, 3, 3, 3, 2, 1, 1, 0, 0, 2, 1, 3, 2, 2, 3, 3, 0, 2
                ),
                listOf(
                    3, 0, 0, 0, 0, 2, 0, 0, 1, 0, 1, 1, 2, 2, 3, 0, 3, 3, 1, 0
                ),
                listOf(
                    0, 2, 0, 2, 3, 1, 0, 3, 3, 2, 1, 2, 2, 3, 2, 3, 1, 1, 3, 3
                ),
                listOf(
                    0, 0, 3, 0, 2, 3, 2, 0, 3, 3, 2, 1, 2, 0, 0, 2, 1, 3, 1, 3
                ),
                listOf(
                    3, 3, 0, 3, 3, 2, 0, 0, 3, 3, 2, 2, 0, 3, 3, 0, 2, 3, 1, 3
                ),
                listOf(
                    2, 2, 1, 0, 0, 0, 3, 2, 2, 3, 1, 0, 1, 3, 2, 1, 0, 0, 2, 0
                ),
                listOf(
                    2, 0, 3, 0, 0, 0, 1, 0, 3, 0, 2, 3, 2, 1, 2, 0, 2, 0, 1, 2
                )
            ), listOf(
                Tank(1, 10),
                Tank(2, 10),
                Tank(3, 15),
                Tank(0, 20),
                Tank(3, 20),
                Tank(2, 20),
                Tank(3, 20),
                Tank(1, 10),
                Tank(1, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(2, 10),
                Tank(1, 15),
                Tank(3, 10),
                Tank(3, 20),
                Tank(2, 15),
                Tank(0, 10),
                Tank(3, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(2, 10),
                Tank(0, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(3, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 10),
                Tank(3, 10),
                Tank(0, 15),
                Tank(3, 15),
                Tank(2, 20),
                Tank(0, 15),
                Tank(0, 25),
                Tank(3, 20),
                Tank(2, 25),
                Tank(3, 15),
                Tank(0, 20),
                Tank(1, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(1, 10),
                Tank(0, 10),
                Tank(3, 15),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk118(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_019", "Master", 20, 40, listOf(
                listOf(
                    0, 2, 2, 0, 1, 0, 0, 1, 1, 3, 3, 1, 2, 2, 2, 1, 0, 0, 0, 1
                ),
                listOf(
                    1, 0, 2, 3, 2, 2, 0, 2, 2, 0, 3, 3, 2, 1, 3, 3, 1, 2, 1, 3
                ),
                listOf(
                    1, 2, 3, 1, 0, 0, 3, 0, 2, 1, 1, 1, 0, 0, 0, 3, 2, 2, 1, 1
                ),
                listOf(
                    0, 1, 1, 1, 0, 3, 1, 1, 1, 0, 2, 2, 1, 3, 3, 2, 1, 3, 1, 0
                ),
                listOf(
                    2, 3, 0, 0, 0, 1, 3, 1, 3, 2, 1, 3, 0, 2, 3, 0, 2, 2, 1, 2
                ),
                listOf(
                    1, 0, 2, 3, 1, 2, 3, 1, 3, 2, 0, 3, 0, 0, 1, 2, 2, 0, 0, 2
                ),
                listOf(
                    3, 0, 3, 2, 0, 0, 0, 1, 2, 2, 1, 0, 3, 0, 0, 3, 2, 3, 0, 0
                ),
                listOf(
                    0, 0, 0, 1, 3, 2, 3, 2, 3, 2, 1, 3, 0, 1, 1, 1, 0, 3, 2, 1
                ),
                listOf(
                    1, 1, 0, 0, 0, 0, 0, 3, 0, 2, 0, 2, 0, 2, 1, 2, 2, 0, 2, 1
                ),
                listOf(
                    1, 3, 0, 0, 2, 2, 1, 0, 0, 1, 1, 0, 3, 0, 3, 3, 1, 1, 1, 1
                ),
                listOf(
                    0, 1, 1, 1, 1, 0, 2, 3, 0, 0, 0, 0, 3, 3, 1, 3, 2, 1, 3, 1
                ),
                listOf(
                    0, 2, 0, 3, 1, 2, 0, 1, 3, 2, 1, 0, 0, 3, 1, 1, 0, 3, 1, 2
                ),
                listOf(
                    0, 0, 3, 3, 3, 3, 0, 1, 3, 0, 3, 3, 3, 2, 2, 2, 0, 0, 0, 1
                ),
                listOf(
                    3, 3, 2, 0, 1, 0, 1, 3, 0, 2, 1, 0, 2, 0, 3, 1, 3, 2, 2, 2
                ),
                listOf(
                    0, 0, 2, 2, 3, 3, 2, 1, 2, 0, 3, 3, 2, 1, 0, 3, 0, 1, 2, 3
                ),
                listOf(
                    2, 2, 2, 0, 3, 1, 0, 2, 0, 1, 3, 3, 0, 0, 3, 0, 3, 2, 2, 1
                ),
                listOf(
                    0, 1, 0, 0, 3, 0, 2, 2, 1, 2, 1, 2, 1, 1, 1, 0, 3, 3, 3, 0
                ),
                listOf(
                    2, 0, 0, 1, 1, 3, 0, 3, 1, 3, 1, 1, 0, 3, 1, 1, 1, 1, 2, 3
                ),
                listOf(
                    0, 0, 1, 2, 2, 3, 3, 3, 2, 0, 2, 2, 2, 1, 0, 0, 1, 0, 3, 2
                ),
                listOf(
                    3, 3, 0, 2, 3, 1, 3, 2, 0, 3, 1, 1, 3, 2, 3, 3, 3, 0, 1, 2
                ),
                listOf(
                    2, 1, 2, 0, 3, 0, 3, 0, 3, 0, 3, 1, 3, 2, 0, 0, 0, 3, 0, 0
                ),
                listOf(
                    3, 0, 2, 3, 0, 3, 3, 2, 2, 3, 2, 0, 0, 0, 1, 1, 2, 3, 3, 1
                ),
                listOf(
                    0, 2, 2, 2, 1, 3, 3, 2, 2, 2, 0, 1, 2, 0, 1, 0, 2, 1, 2, 2
                ),
                listOf(
                    3, 3, 0, 0, 0, 3, 3, 1, 0, 3, 2, 2, 0, 2, 0, 0, 1, 2, 0, 1
                ),
                listOf(
                    1, 0, 0, 0, 2, 2, 1, 0, 0, 3, 0, 3, 3, 1, 1, 1, 2, 1, 2, 0
                ),
                listOf(
                    2, 3, 0, 0, 0, 0, 2, 3, 3, 3, 2, 2, 0, 0, 3, 0, 2, 0, 3, 0
                ),
                listOf(
                    3, 1, 1, 0, 0, 1, 2, 0, 1, 3, 2, 1, 1, 0, 2, 3, 0, 3, 1, 1
                ),
                listOf(
                    1, 1, 1, 2, 0, 0, 0, 0, 2, 1, 0, 0, 2, 1, 1, 0, 2, 2, 0, 0
                ),
                listOf(
                    2, 3, 2, 0, 1, 1, 3, 2, 2, 3, 2, 0, 1, 3, 2, 1, 1, 3, 1, 2
                ),
                listOf(
                    1, 1, 0, 1, 1, 3, 1, 0, 0, 3, 1, 0, 1, 1, 1, 1, 2, 1, 0, 3
                ),
                listOf(
                    1, 2, 3, 0, 1, 1, 0, 1, 0, 3, 2, 2, 2, 0, 2, 3, 1, 2, 3, 0
                ),
                listOf(
                    2, 0, 1, 0, 3, 3, 0, 0, 2, 0, 2, 3, 1, 1, 1, 2, 1, 3, 0, 3
                ),
                listOf(
                    0, 0, 3, 0, 1, 0, 3, 0, 2, 0, 2, 0, 3, 3, 2, 0, 1, 3, 3, 1
                ),
                listOf(
                    0, 2, 0, 3, 1, 2, 2, 1, 1, 2, 2, 1, 2, 0, 1, 3, 1, 0, 3, 1
                ),
                listOf(
                    1, 0, 1, 3, 1, 1, 2, 3, 1, 1, 3, 0, 0, 1, 3, 0, 2, 2, 3, 2
                ),
                listOf(
                    0, 0, 1, 2, 2, 0, 2, 1, 2, 0, 3, 0, 1, 0, 2, 1, 1, 3, 3, 1
                ),
                listOf(
                    1, 0, 1, 2, 0, 0, 2, 0, 1, 1, 3, 0, 2, 1, 1, 0, 3, 0, 3, 3
                ),
                listOf(
                    1, 1, 2, 1, 1, 0, 0, 1, 3, 3, 2, 2, 1, 1, 0, 0, 1, 3, 0, 0
                ),
                listOf(
                    1, 0, 3, 2, 3, 1, 2, 3, 2, 3, 3, 3, 1, 2, 1, 3, 0, 0, 3, 1
                ),
                listOf(
                    3, 0, 1, 2, 3, 1, 0, 3, 0, 0, 0, 0, 2, 1, 0, 0, 3, 2, 0, 2
                )
            ), listOf(
                Tank(0, 10),
                Tank(1, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(1, 20),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(2, 10),
                Tank(3, 20),
                Tank(3, 10),
                Tank(1, 10),
                Tank(3, 20),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 20),
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 10),
                Tank(2, 20),
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(0, 15),
                Tank(0, 10),
                Tank(1, 10),
                Tank(1, 20),
                Tank(3, 15),
                Tank(3, 10),
                Tank(2, 10),
                Tank(0, 20),
                Tank(0, 15),
                Tank(1, 20),
                Tank(3, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk119(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_master_020", "Master", 20, 40, listOf(
                listOf(
                    2, 3, 0, 0, 0, 1, 2, 2, 0, 1, 3, 0, 3, 1, 3, 3, 3, 2, 2, 1
                ),
                listOf(
                    2, 0, 2, 3, 0, 3, 2, 3, 2, 3, 2, 2, 0, 0, 2, 2, 0, 1, 0, 0
                ),
                listOf(
                    3, 0, 1, 0, 0, 0, 3, 3, 3, 0, 2, 3, 1, 3, 1, 2, 0, 2, 2, 2
                ),
                listOf(
                    0, 0, 1, 2, 1, 0, 1, 1, 0, 0, 0, 1, 0, 3, 3, 1, 1, 2, 0, 2
                ),
                listOf(
                    0, 0, 1, 3, 2, 0, 3, 3, 0, 3, 1, 0, 3, 3, 3, 2, 0, 3, 2, 1
                ),
                listOf(
                    1, 1, 2, 1, 2, 1, 2, 1, 3, 3, 1, 3, 3, 0, 0, 3, 1, 1, 2, 2
                ),
                listOf(
                    0, 3, 1, 2, 0, 3, 0, 2, 2, 1, 0, 3, 1, 0, 1, 1, 2, 2, 2, 2
                ),
                listOf(
                    2, 1, 0, 1, 1, 0, 2, 1, 1, 2, 3, 0, 1, 2, 3, 1, 2, 3, 0, 3
                ),
                listOf(
                    3, 0, 3, 0, 0, 0, 0, 3, 2, 1, 1, 1, 0, 1, 3, 2, 3, 3, 3, 1
                ),
                listOf(
                    0, 0, 0, 3, 3, 2, 2, 3, 3, 0, 2, 2, 1, 0, 2, 0, 1, 2, 1, 2
                ),
                listOf(
                    0, 1, 1, 1, 1, 0, 0, 1, 1, 2, 1, 3, 1, 1, 2, 0, 1, 1, 2, 3
                ),
                listOf(
                    1, 1, 1, 2, 2, 0, 3, 2, 2, 2, 0, 3, 3, 0, 1, 1, 3, 3, 3, 3
                ),
                listOf(
                    1, 1, 3, 2, 0, 0, 1, 0, 0, 3, 2, 0, 3, 2, 1, 3, 2, 1, 1, 3
                ),
                listOf(
                    0, 1, 3, 0, 3, 2, 1, 0, 2, 3, 1, 2, 1, 1, 2, 2, 3, 1, 1, 2
                ),
                listOf(
                    1, 1, 2, 3, 3, 0, 0, 1, 0, 2, 2, 1, 2, 3, 0, 1, 2, 0, 3, 1
                ),
                listOf(
                    3, 0, 0, 0, 0, 0, 1, 0, 3, 1, 2, 3, 0, 3, 1, 2, 0, 0, 2, 2
                ),
                listOf(
                    0, 3, 0, 2, 0, 0, 3, 1, 1, 0, 3, 2, 1, 3, 2, 3, 0, 2, 3, 2
                ),
                listOf(
                    1, 2, 2, 3, 2, 1, 2, 2, 0, 0, 0, 1, 0, 3, 3, 1, 3, 1, 3, 0
                ),
                listOf(
                    3, 2, 1, 0, 1, 3, 0, 3, 3, 2, 3, 3, 1, 1, 1, 0, 1, 1, 2, 1
                ),
                listOf(
                    2, 3, 2, 0, 2, 1, 3, 2, 1, 3, 1, 0, 3, 2, 3, 3, 0, 3, 2, 3
                ),
                listOf(
                    1, 1, 0, 2, 0, 0, 3, 0, 1, 2, 0, 0, 2, 3, 0, 1, 1, 1, 3, 2
                ),
                listOf(
                    2, 1, 1, 2, 1, 0, 2, 1, 0, 0, 0, 3, 1, 1, 2, 2, 1, 3, 0, 0
                ),
                listOf(
                    2, 3, 1, 1, 0, 1, 3, 0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 0, 1, 3
                ),
                listOf(
                    3, 3, 2, 2, 2, 1, 2, 1, 2, 0, 3, 0, 2, 0, 3, 2, 2, 0, 0, 0
                ),
                listOf(
                    0, 2, 3, 1, 3, 2, 3, 3, 0, 0, 3, 0, 2, 1, 2, 0, 0, 2, 1, 0
                ),
                listOf(
                    0, 2, 1, 3, 1, 2, 2, 2, 0, 1, 2, 1, 1, 0, 2, 0, 0, 2, 1, 2
                ),
                listOf(
                    0, 0, 2, 1, 2, 3, 0, 0, 3, 0, 2, 2, 0, 1, 3, 0, 3, 0, 3, 1
                ),
                listOf(
                    2, 3, 1, 3, 1, 2, 3, 0, 1, 0, 1, 3, 0, 1, 3, 3, 1, 1, 3, 0
                ),
                listOf(
                    0, 3, 0, 0, 1, 3, 1, 2, 1, 1, 0, 2, 3, 2, 1, 2, 3, 1, 2, 3
                ),
                listOf(
                    1, 1, 3, 0, 0, 1, 3, 1, 3, 2, 1, 1, 0, 1, 1, 1, 1, 1, 3, 2
                ),
                listOf(
                    1, 2, 1, 0, 0, 0, 0, 3, 3, 2, 2, 0, 1, 3, 1, 0, 3, 1, 3, 0
                ),
                listOf(
                    0, 2, 1, 1, 3, 3, 0, 1, 2, 3, 0, 2, 0, 1, 2, 1, 3, 2, 1, 0
                ),
                listOf(
                    1, 1, 0, 3, 2, 2, 0, 2, 1, 0, 1, 1, 0, 2, 3, 1, 3, 3, 2, 3
                ),
                listOf(
                    0, 1, 1, 2, 3, 1, 0, 0, 2, 0, 3, 1, 0, 3, 3, 1, 0, 1, 0, 2
                ),
                listOf(
                    3, 2, 1, 3, 3, 2, 0, 1, 0, 1, 0, 0, 3, 2, 1, 3, 1, 3, 3, 2
                ),
                listOf(
                    1, 3, 1, 2, 2, 1, 0, 1, 3, 3, 1, 3, 1, 1, 1, 1, 2, 3, 2, 3
                ),
                listOf(
                    1, 3, 0, 0, 2, 3, 2, 1, 3, 3, 1, 1, 3, 2, 1, 3, 1, 0, 0, 3
                ),
                listOf(
                    3, 0, 1, 1, 1, 0, 1, 0, 3, 2, 1, 1, 1, 3, 3, 0, 2, 1, 0, 3
                ),
                listOf(
                    0, 1, 1, 1, 2, 0, 3, 3, 2, 0, 1, 1, 0, 3, 1, 2, 3, 3, 1, 1
                ),
                listOf(
                    1, 2, 0, 0, 0, 1, 3, 0, 1, 1, 0, 2, 3, 3, 3, 1, 1, 3, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(3, 10),
                Tank(1, 10),
                Tank(2, 10),
                Tank(0, 10),
                Tank(3, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(3, 20),
                Tank(3, 20),
                Tank(1, 15),
                Tank(0, 20),
                Tank(3, 15),
                Tank(2, 15),
                Tank(2, 15),
                Tank(1, 20),
                Tank(3, 10),
                Tank(0, 15),
                Tank(1, 15),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 20),
                Tank(2, 15),
                Tank(1, 20),
                Tank(1, 15),
                Tank(2, 20),
                Tank(1, 10),
                Tank(3, 15),
                Tank(3, 20),
                Tank(0, 20),
                Tank(0, 15),
                Tank(0, 15),
                Tank(0, 20),
                Tank(2, 20),
                Tank(1, 25),
                Tank(2, 10),
                Tank(3, 15),
                Tank(1, 20),
                Tank(2, 15),
                Tank(3, 10),
                Tank(2, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(1, 15),
                Tank(0, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(3, 10),
                Tank(3, 15),
                Tank(3, 15),
                Tank(1, 10)
            )),
        )
    }

    private fun getChunk120(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_001", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 1, 2, 0, 0, 2, 0
                ),
                listOf(
                    0, 0, 2, 2, 1, null, 2, 0
                ),
                listOf(
                    1, 0, null, 2, 0, 0, 0, 2
                ),
                listOf(
                    0, 0, 1, 0, 0, null, 0, 0
                ),
                listOf(
                    1, 0, 2, 2, 0, 0, 2, 0
                ),
                listOf(
                    0, 2, 0, 1, 0, 2, 0, 2
                ),
                listOf(
                    0, 0, null, 2, 1, 0, 2, 1
                ),
                listOf(
                    2, 0, 0, 2, 1, 2, 0, 2
                )
            ), listOf(
                Tank(2, 20),
                Tank(0, 10),
                Tank(1, 10),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk121(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_002", "Medium", 8, 8, listOf(
                listOf(
                    0, 0, 1, 1, 0, 0, 0, 2
                ),
                listOf(
                    1, 1, 1, 0, 2, 0, 0, 0
                ),
                listOf(
                    1, 1, 1, 1, 1, 0, 1, 0
                ),
                listOf(
                    1, 0, 2, 2, 2, 0, 0, 1
                ),
                listOf(
                    0, 0, 1, 0, 2, 1, 0, 0
                ),
                listOf(
                    0, null, 2, null, 2, 1, 1, 1
                ),
                listOf(
                    null, 1, 1, 0, 0, 0, null, 2
                ),
                listOf(
                    0, 0, 0, 0, 2, 0, 0, 0
                )
            ), listOf(
                Tank(2, 10),
                Tank(0, 10),
                Tank(0, 20),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk122(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_003", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 1, 2, 2, 0, 1, 0
                ),
                listOf(
                    1, 1, 2, 0, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 0, 1, 1, 2, 2, 1
                ),
                listOf(
                    0, 1, 0, 0, 2, 2, null, null
                ),
                listOf(
                    null, 1, 2, 2, 0, 2, 2, 2
                ),
                listOf(
                    1, 2, null, 1, 2, 2, 0, 1
                ),
                listOf(
                    2, 1, 1, 1, 2, 2, 2, 2
                ),
                listOf(
                    1, 2, 2, 1, 2, 0, 1, 2
                )
            ), listOf(
                Tank(0, 10),
                Tank(2, 10),
                Tank(1, 20),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk123(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_004", "Medium", 8, 8, listOf(
                listOf(
                    0, 1, 2, 1, 1, 2, 0, 1
                ),
                listOf(
                    1, 2, 0, null, 2, 1, 1, 2
                ),
                listOf(
                    2, 1, 1, 1, 0, 1, 1, 1
                ),
                listOf(
                    0, 1, 0, 2, 0, 2, 1, 1
                ),
                listOf(
                    1, 2, 0, 0, 0, 0, null, 1
                ),
                listOf(
                    0, 0, 2, 0, 2, 1, 0, 0
                ),
                listOf(
                    0, 2, null, 1, 2, 1, 2, 0
                ),
                listOf(
                    0, null, 0, 1, 2, 1, 1, 1
                )
            ), listOf(
                Tank(1, 25),
                Tank(0, 20),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk124(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_005", "Medium", 8, 8, listOf(
                listOf(
                    1, 1, 0, 0, 0, 1, 0, 0
                ),
                listOf(
                    1, 0, 2, 0, 0, 2, 1, 0
                ),
                listOf(
                    0, 2, 1, 2, 2, null, null, 0
                ),
                listOf(
                    0, 0, 2, 1, 2, 2, 0, 1
                ),
                listOf(
                    2, 2, 1, null, 2, 0, 0, 1
                ),
                listOf(
                    0, 1, 2, 0, 2, 1, 2, 2
                ),
                listOf(
                    1, 0, 2, 2, 0, 2, 1, null
                ),
                listOf(
                    1, 1, 2, 1, 1, 1, 1, 2
                )
            ), listOf(
                Tank(1, 20),
                Tank(2, 20),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk125(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_006", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 2, 1, 1, 1, null, 1
                ),
                listOf(
                    1, 1, null, null, 2, 0, 1, 1
                ),
                listOf(
                    1, 1, 1, 1, 0, 0, 1, 2
                ),
                listOf(
                    0, 0, 1, 0, 0, 0, 1, 1
                ),
                listOf(
                    1, 0, 0, 0, 2, 2, 0, 1
                ),
                listOf(
                    0, 0, 2, 1, 1, 2, 1, 0
                ),
                listOf(
                    0, 1, 1, 0, 2, null, 1, 0
                ),
                listOf(
                    1, 1, 0, 1, 2, 0, 1, 1
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 20),
                Tank(1, 10),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk126(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_007", "Medium", 8, 8, listOf(
                listOf(
                    0, 0, 1, 0, 2, 0, 1, 2
                ),
                listOf(
                    2, 1, 0, 2, 0, 0, 0, 2
                ),
                listOf(
                    0, 2, null, 1, 0, 2, 2, 2
                ),
                listOf(
                    2, 2, 2, 0, null, null, 1, 2
                ),
                listOf(
                    2, 1, 2, 1, 1, 2, 0, 2
                ),
                listOf(
                    2, 0, 1, 1, 1, 0, 0, 0
                ),
                listOf(
                    1, 0, 2, 0, 0, 2, 2, 2
                ),
                listOf(
                    1, 2, 2, 1, 1, 0, null, 2
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 20),
                Tank(2, 25)
            )),
        )
    }

    private fun getChunk127(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_008", "Medium", 8, 8, listOf(
                listOf(
                    0, 0, 1, 0, 2, 1, 1, 2
                ),
                listOf(
                    1, 1, 1, 1, 0, 2, null, 1
                ),
                listOf(
                    0, 0, 1, 1, 1, 1, 2, 0
                ),
                listOf(
                    2, 1, 2, 1, 1, 1, null, 1
                ),
                listOf(
                    2, null, 2, 1, 1, 1, 1, 2
                ),
                listOf(
                    2, 0, 1, 2, 1, 1, 0, 1
                ),
                listOf(
                    1, 1, 0, 2, 1, 0, 0, 1
                ),
                listOf(
                    null, 2, 1, 0, 2, 2, 0, 0
                )
            ), listOf(
                Tank(0, 15),
                Tank(2, 15),
                Tank(1, 15),
                Tank(1, 15)
            )),
        )
    }

    private fun getChunk128(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_009", "Medium", 8, 8, listOf(
                listOf(
                    1, 0, 1, 1, 2, 0, 0, null
                ),
                listOf(
                    0, 1, null, 2, 2, 0, 1, 1
                ),
                listOf(
                    2, 1, 1, null, 1, 0, 0, 1
                ),
                listOf(
                    2, 1, 2, 1, 0, 1, 1, 1
                ),
                listOf(
                    1, 1, 1, 0, 0, 2, 1, 2
                ),
                listOf(
                    2, 1, 1, 1, null, 1, 0, 2
                ),
                listOf(
                    0, 2, 1, 1, 0, 1, 2, 1
                ),
                listOf(
                    0, 1, 1, 2, 1, 0, 2, 2
                )
            ), listOf(
                Tank(2, 15),
                Tank(1, 10),
                Tank(0, 15),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk129(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_010", "Medium", 8, 8, listOf(
                listOf(
                    2, 1, null, 1, 0, 1, 1, 2
                ),
                listOf(
                    2, 0, 0, null, 2, 2, 0, 1
                ),
                listOf(
                    2, 2, 0, 1, 1, 1, 1, 2
                ),
                listOf(
                    2, 0, 2, 1, 2, 2, null, 0
                ),
                listOf(
                    0, 0, null, 2, 1, 1, 1, 2
                ),
                listOf(
                    1, 1, 2, 1, 2, 1, 1, 0
                ),
                listOf(
                    2, 0, 2, 0, 2, 0, 0, 2
                ),
                listOf(
                    2, 2, 1, 1, 2, 2, 2, 0
                )
            ), listOf(
                Tank(1, 20),
                Tank(0, 15),
                Tank(2, 25)
            )),
        )
    }

    private fun getChunk130(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_011", "Medium", 8, 8, listOf(
                listOf(
                    2, 2, 0, 1, 0, 1, 2, 1
                ),
                listOf(
                    2, 2, 0, 2, 2, 2, 2, 2
                ),
                listOf(
                    0, 1, 0, 2, 2, 2, 2, null
                ),
                listOf(
                    1, 2, 2, 2, 1, 2, 1, 2
                ),
                listOf(
                    1, 2, 2, 2, 2, 2, 0, 2
                ),
                listOf(
                    1, 1, 2, 1, 2, null, 1, 0
                ),
                listOf(
                    0, null, 2, 0, 2, 1, 2, 1
                ),
                listOf(
                    2, null, 2, 2, 0, 2, 2, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk131(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_012", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 2, 2, 0, 1, 1, 2
                ),
                listOf(
                    1, 0, 0, 2, 2, 1, 2, 0
                ),
                listOf(
                    2, 0, 0, 2, 1, 2, 0, 2
                ),
                listOf(
                    null, 1, 0, 1, 2, 2, 2, 2
                ),
                listOf(
                    2, 2, 1, 2, 2, 0, 1, 1
                ),
                listOf(
                    2, 2, 1, null, null, 0, 1, 0
                ),
                listOf(
                    2, 2, 0, 2, 2, 2, 2, null
                ),
                listOf(
                    1, 0, 1, 2, 2, 0, 0, 2
                )
            ), listOf(
                Tank(0, 15),
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 20)
            )),
        )
    }

    private fun getChunk132(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_013", "Medium", 8, 8, listOf(
                listOf(
                    2, 1, 0, 1, 1, 2, 0, 1
                ),
                listOf(
                    2, 2, 2, 1, 0, null, 1, 0
                ),
                listOf(
                    2, 0, 2, 2, 2, 0, 1, 2
                ),
                listOf(
                    0, 2, 2, 2, 1, 0, 2, 2
                ),
                listOf(
                    2, 1, 1, 0, 0, 2, 0, 0
                ),
                listOf(
                    2, 2, 0, 1, null, 2, 2, 1
                ),
                listOf(
                    2, null, 1, 1, 2, 2, 1, null
                ),
                listOf(
                    2, 2, 2, 0, 0, 2, 2, 2
                )
            ), listOf(
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 20),
                Tank(2, 10)
            )),
        )
    }

    private fun getChunk133(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_014", "Medium", 8, 8, listOf(
                listOf(
                    2, 0, 2, 0, 0, 2, 0, 2
                ),
                listOf(
                    2, 1, 2, 0, 0, 2, 1, 1
                ),
                listOf(
                    0, 2, null, 1, 0, 1, 0, 2
                ),
                listOf(
                    2, 1, null, 1, 2, 2, 0, 1
                ),
                listOf(
                    2, 0, 2, 0, 2, 2, 2, 2
                ),
                listOf(
                    2, 1, 0, 1, 1, 2, 2, 0
                ),
                listOf(
                    0, 0, 2, 0, 1, null, 0, 0
                ),
                listOf(
                    1, null, 2, 1, 2, 1, 0, 2
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 25),
                Tank(0, 20)
            )),
        )
    }

    private fun getChunk134(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_015", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 1, 0, 2, 2, 2, 0
                ),
                listOf(
                    0, 2, 2, 2, 0, 0, 2, 2
                ),
                listOf(
                    2, 0, 2, 1, 1, 2, null, 1
                ),
                listOf(
                    0, 2, 2, 0, null, 2, 2, 1
                ),
                listOf(
                    0, 0, 1, 1, 0, 2, 1, 2
                ),
                listOf(
                    0, 2, 1, 2, 2, 2, 1, 0
                ),
                listOf(
                    2, null, 2, 1, 2, 2, 1, 2
                ),
                listOf(
                    2, 2, 2, 0, 1, 0, 1, null
                )
            ), listOf(
                Tank(2, 10),
                Tank(1, 15),
                Tank(2, 20),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk135(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_016", "Medium", 8, 8, listOf(
                listOf(
                    2, null, 2, 0, 0, 1, 0, 0
                ),
                listOf(
                    2, 1, 1, 0, 0, 2, 0, 1
                ),
                listOf(
                    0, 0, 1, 1, 0, 0, 1, 0
                ),
                listOf(
                    2, 0, 2, 1, 1, null, 2, 2
                ),
                listOf(
                    2, 1, 1, 0, 2, 0, 0, 1
                ),
                listOf(
                    1, 1, null, 0, 0, 1, 0, 1
                ),
                listOf(
                    2, 0, 0, null, 0, 1, 0, 1
                ),
                listOf(
                    1, 2, 2, 2, 2, 0, 1, 0
                )
            ), listOf(
                Tank(0, 25),
                Tank(1, 20),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk136(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_017", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 2, 0, 1, 0, 1, 2
                ),
                listOf(
                    1, 1, 1, null, 1, 2, 1, null
                ),
                listOf(
                    2, null, 1, 0, 0, 1, 0, 1
                ),
                listOf(
                    1, 1, 1, 2, 2, 1, 1, null
                ),
                listOf(
                    2, 0, 1, 1, 1, 0, 1, 2
                ),
                listOf(
                    2, 2, 1, 1, 1, 1, 1, 1
                ),
                listOf(
                    0, 2, 2, 1, 1, 2, 1, 0
                ),
                listOf(
                    1, 1, 1, 1, 1, 0, 2, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(2, 15),
                Tank(0, 10),
                Tank(1, 20)
            )),
        )
    }

    private fun getChunk137(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_018", "Medium", 8, 8, listOf(
                listOf(
                    1, 1, 1, 2, 1, 0, 1, 2
                ),
                listOf(
                    1, 1, 0, null, 0, 1, 1, 1
                ),
                listOf(
                    1, null, 2, 1, 0, 2, 2, 1
                ),
                listOf(
                    1, 0, null, 1, 1, 0, 1, 1
                ),
                listOf(
                    1, 2, 0, 0, 2, 2, 0, 1
                ),
                listOf(
                    1, 1, 1, 1, 1, 0, null, 1
                ),
                listOf(
                    2, 0, 1, 2, 0, 2, 2, 2
                ),
                listOf(
                    2, 0, 2, 1, 1, 0, 0, 1
                )
            ), listOf(
                Tank(1, 15),
                Tank(1, 15),
                Tank(0, 15),
                Tank(2, 15)
            )),
        )
    }

    private fun getChunk138(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_019", "Medium", 8, 8, listOf(
                listOf(
                    1, 2, 0, 2, 0, 2, 1, 0
                ),
                listOf(
                    2, 1, 1, null, 1, 1, 0, null
                ),
                listOf(
                    2, 2, 0, 1, 0, 0, 2, 2
                ),
                listOf(
                    2, 0, 2, 1, 1, 1, null, 0
                ),
                listOf(
                    2, 0, 1, 1, 0, 1, 0, 1
                ),
                listOf(
                    1, 2, 1, 2, 1, 2, 1, 2
                ),
                listOf(
                    2, 0, 2, 1, null, 1, 2, 0
                ),
                listOf(
                    2, 2, 0, 1, 1, 1, 1, 1
                )
            ), listOf(
                Tank(2, 20),
                Tank(1, 25),
                Tank(0, 15)
            )),
        )
    }

    private fun getChunk139(): List<PregeneratedLevel> {
        return listOf(
            PregeneratedLevel("cubeshooter_medium_020", "Medium", 8, 8, listOf(
                listOf(
                    1, null, 1, 0, 2, 0, 1, 0
                ),
                listOf(
                    0, 0, 2, 1, 1, 2, 2, 0
                ),
                listOf(
                    1, 1, 1, 1, 1, 1, 1, 2
                ),
                listOf(
                    2, 1, 2, 2, 1, 1, 1, 1
                ),
                listOf(
                    2, 2, 2, 1, null, 2, 1, 1
                ),
                listOf(
                    0, 2, 1, null, 1, 0, 2, 1
                ),
                listOf(
                    2, 2, 0, 0, 1, 0, 0, 2
                ),
                listOf(
                    2, 0, 1, null, 2, 2, 0, 0
                )
            ), listOf(
                Tank(0, 15),
                Tank(2, 20),
                Tank(1, 25)
            )),
        )
    }

    val ALL_LEVELS: List<PregeneratedLevel> by lazy {
        getChunk0() + getChunk1() + getChunk2() + getChunk3() + getChunk4() + getChunk5() + getChunk6() + getChunk7() + getChunk8() + getChunk9() + getChunk10() + getChunk11() + getChunk12() + getChunk13() + getChunk14() + getChunk15() + getChunk16() + getChunk17() + getChunk18() + getChunk19() + getChunk20() + getChunk21() + getChunk22() + getChunk23() + getChunk24() + getChunk25() + getChunk26() + getChunk27() + getChunk28() + getChunk29() + getChunk30() + getChunk31() + getChunk32() + getChunk33() + getChunk34() + getChunk35() + getChunk36() + getChunk37() + getChunk38() + getChunk39() + getChunk40() + getChunk41() + getChunk42() + getChunk43() + getChunk44() + getChunk45() + getChunk46() + getChunk47() + getChunk48() + getChunk49() + getChunk50() + getChunk51() + getChunk52() + getChunk53() + getChunk54() + getChunk55() + getChunk56() + getChunk57() + getChunk58() + getChunk59() + getChunk60() + getChunk61() + getChunk62() + getChunk63() + getChunk64() + getChunk65() + getChunk66() + getChunk67() + getChunk68() + getChunk69() + getChunk70() + getChunk71() + getChunk72() + getChunk73() + getChunk74() + getChunk75() + getChunk76() + getChunk77() + getChunk78() + getChunk79() + getChunk80() + getChunk81() + getChunk82() + getChunk83() + getChunk84() + getChunk85() + getChunk86() + getChunk87() + getChunk88() + getChunk89() + getChunk90() + getChunk91() + getChunk92() + getChunk93() + getChunk94() + getChunk95() + getChunk96() + getChunk97() + getChunk98() + getChunk99() + getChunk100() + getChunk101() + getChunk102() + getChunk103() + getChunk104() + getChunk105() + getChunk106() + getChunk107() + getChunk108() + getChunk109() + getChunk110() + getChunk111() + getChunk112() + getChunk113() + getChunk114() + getChunk115() + getChunk116() + getChunk117() + getChunk118() + getChunk119() + getChunk120() + getChunk121() + getChunk122() + getChunk123() + getChunk124() + getChunk125() + getChunk126() + getChunk127() + getChunk128() + getChunk129() + getChunk130() + getChunk131() + getChunk132() + getChunk133() + getChunk134() + getChunk135() + getChunk136() + getChunk137() + getChunk138() + getChunk139()
    }

    val LEVELS_BY_DIFFICULTY: Map<String, List<PregeneratedLevel>> by lazy { ALL_LEVELS.groupBy { it.difficulty } }
}
