package com.funkyotc.puzzleverse.woodnuts.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedWoodNutsLevel(
    override val id: String,
    override val difficulty: String,
    val rows: Int,
    val cols: Int,
    val bolts: List<Bolt>,
    val planks: List<Plank>
) : BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String get() = "${rows}x${cols}"
}

object WoodNutsPregenerated {

    val ALL_LEVELS: List<PregeneratedWoodNutsLevel> by lazy { generateAllLevels() }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedWoodNutsLevel>> by lazy {
        ALL_LEVELS.groupBy { it.difficulty }
    }

    fun getPuzzleById(id: String): PregeneratedWoodNutsLevel? = ALL_LEVELS.find { it.id == id }

    private fun generateAllLevels(): List<PregeneratedWoodNutsLevel> {
        return listOf(
            easy1(), easy2(), easy3(), easy4(),
            medium1(), medium2(), medium3(), medium4(),
            hard1(), hard2(), hard3(), hard4()
        )
    }

    private fun easy1() = PregeneratedWoodNutsLevel(
        "woodnuts_easy_001", "Easy", 3, 3,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 3),
            Bolt("b3", 3, 0), Bolt("b4", 3, 3)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 0, 2, listOf("b1", "b2")),
            Plank("p2", 1, 0, 2, 2, listOf("b3", "b4"))
        )
    )

    private fun easy2() = PregeneratedWoodNutsLevel(
        "woodnuts_easy_002", "Easy", 3, 3,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 3),
            Bolt("b3", 3, 0), Bolt("b4", 3, 3),
            Bolt("b5", 1, 1)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 0, 2, listOf("b1", "b2")),
            Plank("p2", 1, 0, 2, 0, listOf("b3")),
            Plank("p3", 1, 1, 2, 2, listOf("b4")),
            Plank("p4", 1, 0, 1, 2, listOf("b5"))
        )
    )

    private fun easy3() = PregeneratedWoodNutsLevel(
        "woodnuts_easy_003", "Easy", 3, 3,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 1, 0),
            Bolt("b3", 0, 3), Bolt("b4", 1, 3)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 0, 2, listOf("b1", "b3")),
            Plank("p2", 1, 0, 2, 2, listOf("b2", "b4"))
        )
    )

    private fun easy4() = PregeneratedWoodNutsLevel(
        "woodnuts_easy_004", "Easy", 3, 3,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 2, 0),
            Bolt("b3", 0, 3), Bolt("b4", 2, 3),
            Bolt("b5", 1, 0), Bolt("b6", 1, 3)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 0, 2, listOf("b1", "b3")),
            Plank("p2", 1, 0, 1, 2, listOf("b5", "b6")),
            Plank("p3", 2, 0, 2, 2, listOf("b2", "b4"))
        )
    )

    private fun medium1() = PregeneratedWoodNutsLevel(
        "woodnuts_medium_001", "Medium", 4, 4,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 4),
            Bolt("b3", 1, 0), Bolt("b4", 1, 4),
            Bolt("b5", 4, 0), Bolt("b6", 4, 4),
            Bolt("b7", 2, 2)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 0, 4, listOf("b1", "b2")),
            Plank("p2", 1, 0, 1, 4, listOf("b3", "b4")),
            Plank("p3", 2, 0, 2, 4, listOf("b7")),
            Plank("p4", 3, 0, 4, 4, listOf("b5", "b6"))
        )
    )

    private fun medium2() = PregeneratedWoodNutsLevel(
        "woodnuts_medium_002", "Medium", 4, 4,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 3),
            Bolt("b3", 2, 0), Bolt("b4", 2, 3),
            Bolt("b5", 2, 1), Bolt("b6", 2, 4),
            Bolt("b7", 4, 1), Bolt("b8", 4, 4)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 1, 2, listOf("b1", "b2")),
            Plank("p2", 0, 3, 1, 4, listOf("b4")),
            Plank("p3", 1, 0, 3, 0, listOf("b3")),
            Plank("p4", 2, 1, 3, 3, listOf("b5", "b6")),
            Plank("p5", 2, 4, 4, 4, listOf("b8")),
            Plank("p6", 4, 1, 4, 3, listOf("b7"))
        )
    )

    private fun medium3() = PregeneratedWoodNutsLevel(
        "woodnuts_medium_003", "Medium", 4, 4,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 4),
            Bolt("b3", 2, 0), Bolt("b4", 2, 4),
            Bolt("b5", 4, 0), Bolt("b6", 4, 4),
            Bolt("b7", 1, 2), Bolt("b8", 3, 2)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 1, 1, listOf("b1")),
            Plank("p2", 0, 2, 1, 4, listOf("b2")),
            Plank("p3", 2, 0, 2, 4, listOf("b3", "b4")),
            Plank("p4", 3, 0, 4, 1, listOf("b5")),
            Plank("p5", 3, 2, 4, 4, listOf("b6")),
            Plank("p6", 1, 1, 3, 1, listOf("b7")),
            Plank("p7", 1, 3, 3, 3, listOf("b8"))
        )
    )

    private fun medium4() = PregeneratedWoodNutsLevel(
        "woodnuts_medium_004", "Medium", 4, 4,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 4),
            Bolt("b3", 4, 0), Bolt("b4", 4, 4),
            Bolt("b5", 2, 0), Bolt("b6", 2, 4),
            Bolt("b7", 1, 1), Bolt("b8", 3, 3)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 1, 4, listOf("b1", "b2")),
            Plank("p2", 2, 0, 2, 4, listOf("b5", "b6")),
            Plank("p3", 3, 0, 4, 4, listOf("b3", "b4")),
            Plank("p4", 1, 1, 3, 1, listOf("b7")),
            Plank("p5", 1, 3, 3, 3, listOf("b8"))
        )
    )

    private fun hard1() = PregeneratedWoodNutsLevel(
        "woodnuts_hard_001", "Hard", 5, 5,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 5),
            Bolt("b3", 5, 0), Bolt("b4", 5, 5),
            Bolt("b5", 2, 0), Bolt("b6", 2, 5),
            Bolt("b7", 0, 2), Bolt("b8", 5, 2),
            Bolt("b9", 1, 1), Bolt("b10", 3, 3),
            Bolt("b11", 1, 4), Bolt("b12", 4, 1)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 1, 1, listOf("b1", "b9")),
            Plank("p2", 0, 3, 1, 5, listOf("b2", "b11")),
            Plank("p3", 2, 0, 2, 5, listOf("b5", "b6")),
            Plank("p4", 3, 0, 5, 1, listOf("b3", "b12")),
            Plank("p5", 3, 3, 5, 5, listOf("b4", "b10")),
            Plank("p6", 0, 2, 1, 2, listOf("b7")),
            Plank("p7", 3, 2, 5, 2, listOf("b8")),
            Plank("p8", 1, 2, 3, 2, listOf("b9", "b10"))
        )
    )

    private fun hard2() = PregeneratedWoodNutsLevel(
        "woodnuts_hard_002", "Hard", 5, 5,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 5),
            Bolt("b3", 2, 0), Bolt("b4", 2, 5),
            Bolt("b5", 5, 0), Bolt("b6", 5, 5),
            Bolt("b7", 3, 1), Bolt("b8", 4, 4),
            Bolt("b9", 1, 2), Bolt("b10", 3, 3),
            Bolt("b11", 0, 3), Bolt("b12", 5, 2)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 1, 2, listOf("b1", "b9")),
            Plank("p2", 0, 3, 1, 5, listOf("b2", "b11")),
            Plank("p3", 2, 0, 2, 2, listOf("b3")),
            Plank("p4", 2, 3, 2, 5, listOf("b4")),
            Plank("p5", 3, 0, 5, 1, listOf("b5", "b7")),
            Plank("p6", 3, 2, 3, 5, listOf("b10")),
            Plank("p7", 4, 2, 5, 5, listOf("b6", "b8")),
            Plank("p8", 1, 2, 3, 2, listOf("b9", "b10")),
            Plank("p9", 0, 2, 0, 2, listOf("b11")),
            Plank("p10", 4, 2, 4, 2, listOf("b12"))
        )
    )

    private fun hard3() = PregeneratedWoodNutsLevel(
        "woodnuts_hard_003", "Hard", 5, 5,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 5),
            Bolt("b3", 5, 0), Bolt("b4", 5, 5),
            Bolt("b5", 2, 0), Bolt("b6", 2, 5),
            Bolt("b7", 0, 2), Bolt("b8", 5, 2),
            Bolt("b9", 1, 1), Bolt("b10", 4, 4),
            Bolt("b11", 1, 4), Bolt("b12", 4, 1),
            Bolt("b13", 3, 0), Bolt("b14", 3, 5)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 0, 1, listOf("b1")),
            Plank("p2", 0, 3, 0, 5, listOf("b2")),
            Plank("p3", 1, 0, 3, 0, listOf("b3", "b13")),
            Plank("p4", 1, 5, 3, 5, listOf("b4", "b14")),
            Plank("p5", 4, 0, 5, 1, listOf("b5", "b12")),
            Plank("p6", 4, 4, 5, 5, listOf("b6", "b10")),
            Plank("p7", 0, 2, 3, 2, listOf("b7", "b13", "b14")),
            Plank("p8", 3, 2, 5, 2, listOf("b8")),
            Plank("p9", 1, 1, 3, 1, listOf("b9")),
            Plank("p10", 1, 3, 3, 3, listOf("b11")),
            Plank("p11", 1, 1, 1, 4, listOf("b9", "b11"))
        )
    )

    private fun hard4() = PregeneratedWoodNutsLevel(
        "woodnuts_hard_004", "Hard", 5, 5,
        bolts = listOf(
            Bolt("b1", 0, 0), Bolt("b2", 0, 5),
            Bolt("b3", 5, 0), Bolt("b4", 5, 5),
            Bolt("b5", 2, 0), Bolt("b6", 2, 5),
            Bolt("b7", 0, 2), Bolt("b8", 5, 2),
            Bolt("b9", 1, 1), Bolt("b10", 4, 4),
            Bolt("b11", 1, 4), Bolt("b12", 4, 1),
            Bolt("b13", 3, 0), Bolt("b14", 3, 5),
            Bolt("b15", 2, 2), Bolt("b16", 2, 3)
        ),
        planks = listOf(
            Plank("p1", 0, 0, 1, 1, listOf("b1", "b9")),
            Plank("p2", 0, 3, 1, 5, listOf("b2", "b11")),
            Plank("p3", 2, 0, 3, 0, listOf("b5", "b13")),
            Plank("p4", 2, 5, 3, 5, listOf("b6", "b14")),
            Plank("p5", 4, 0, 5, 1, listOf("b3", "b12")),
            Plank("p6", 4, 3, 5, 5, listOf("b4", "b10")),
            Plank("p7", 0, 2, 1, 2, listOf("b7")),
            Plank("p8", 4, 2, 5, 2, listOf("b8")),
            Plank("p9", 2, 1, 3, 2, listOf("b15")),
            Plank("p10", 2, 3, 3, 4, listOf("b16")),
            Plank("p11", 1, 1, 4, 1, listOf("b12", "b9")),
            Plank("p12", 1, 4, 4, 4, listOf("b10", "b11"))
        )
    )
}
