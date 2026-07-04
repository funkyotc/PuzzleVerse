package com.funkyotc.puzzleverse.pullpin.data

private fun parseGrid(rows: Int, cols: Int, vararg rowStrings: String): List<List<Cell>> {
    return rowStrings.map { rowStr ->
        require(rowStr.length == cols) { "Row length $rowStr != $cols" }
        rowStr.map { ch ->
            when {
                ch == '.' -> Cell(CellType.EMPTY)
                ch == 'W' -> Cell(CellType.WALL)
                ch == 'P' -> Cell(CellType.PIN)
                ch in '1'..'9' -> Cell(CellType.BALL, color = ch - '0')
                ch in 'a'..'i' -> Cell(CellType.CUP, color = ch - 'a' + 1)
                else -> throw IllegalArgumentException("Unknown cell char: $ch")
            }
        }
    }
}

private fun level(id: String, difficulty: String, rows: Int, cols: Int, vararg rowStrings: String): PullPinLevel {
    return PullPinLevel(
        id = id,
        difficulty = difficulty,
        rows = rows,
        cols = cols,
        grid = parseGrid(rows, cols, *rowStrings)
    )
}

private fun chunkEasy(): List<PullPinLevel> = listOf(
    level("pullpin_easy_001", "Easy", 5, 5,
        ".....",
        "..1..",
        "..P..",
        "..a..",
        "WWWWW"
    ),
    level("pullpin_easy_002", "Easy", 5, 5,
        ".1.2.",
        ".....",
        "..P..",
        ".a.b.",
        "WWWWW"
    ),
    level("pullpin_easy_003", "Easy", 5, 5,
        ".1...",
        ".....",
        "..P..",
        "...a.",
        "WWWWW"
    ),
    level("pullpin_easy_004", "Easy", 5, 5,
        "..1..",
        ".....",
        ".P...",
        "..a..",
        "WWWWW"
    )
)

private fun chunkMedium(): List<PullPinLevel> = listOf(
    level("pullpin_medium_001", "Medium", 6, 6,
        ".1.2.3",
        "......",
        "...P..",
        "......",
        ".a.b.c",
        "WWWWWW"
    ),
    level("pullpin_medium_002", "Medium", 6, 6,
        ".1..2.",
        "......",
        ".WPPW.",
        "......",
        ".a..b.",
        "WWWWWW"
    ),
    level("pullpin_medium_003", "Medium", 6, 6,
        "1.....",
        "......",
        "..P...",
        "...W..",
        "....a.",
        "WWWWWW"
    ),
    level("pullpin_medium_004", "Medium", 6, 6,
        "..1...",
        "..2...",
        "..P...",
        "..P...",
        "..a.b.",
        "WWWWWW"
    )
)

private fun chunkHard(): List<PullPinLevel> = listOf(
    level("pullpin_hard_001", "Hard", 7, 7,
        ".1..2..",
        ".......",
        "..PW...",
        "...P...",
        "......3",
        "..a.b.c",
        "WWWWWWW"
    ),
    level("pullpin_hard_002", "Hard", 7, 7,
        "...1...",
        "..2P...",
        ".W..W..",
        "..P.3..",
        "......W",
        ".a.b.c.",
        "WWWWWWW"
    ),
    level("pullpin_hard_003", "Hard", 7, 7,
        "1..2...",
        ".......",
        ".W.P.W.",
        "...P...",
        ".......",
        "a..b..c",
        "WWWWWWW"
    )
)

private fun chunkExpert(): List<PullPinLevel> = listOf(
    level("pullpin_expert_001", "Expert", 8, 8,
        ".1..2..3",
        "........",
        "..W..W..",
        "...PP...",
        "...PP...",
        "..W..W..",
        ".a..b..c",
        "WWWWWWWW"
    ),
    level("pullpin_expert_002", "Expert", 8, 8,
        "1.......",
        ".2.....3",
        "..P.P...",
        "...W....",
        "...P....",
        "......4.",
        "a..b..cd",
        "WWWWWWWW"
    ),
    level("pullpin_expert_003", "Expert", 8, 8,
        "..1..2..",
        "..3..4..",
        "..P..P..",
        "..W..W..",
        "..P..P..",
        "........",
        ".a.b.c.d",
        "WWWWWWWW"
    )
)

object PullPinPregenerated {
    val ALL_LEVELS: List<PullPinLevel> by lazy {
        chunkEasy() + chunkMedium() + chunkHard() + chunkExpert()
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PullPinLevel>> by lazy {
        ALL_LEVELS.groupBy { it.difficulty }
    }
}
