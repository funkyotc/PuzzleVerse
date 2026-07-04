package com.funkyotc.puzzleverse.pullpin.data

private fun parseGrid(rows: Int, cols: Int, vararg rowStrings: String): List<List<Cell>> {
    return rowStrings.map { rowStr ->
        require(rowStr.length == cols) { "Row '$rowStr' length ${rowStr.length} != $cols" }
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

private fun lvl(id: String, difficulty: String, rows: Int, cols: Int, vararg rowStrings: String): PullPinLevel {
    return PullPinLevel(
        id = id,
        difficulty = difficulty,
        rows = rows,
        cols = cols,
        grid = parseGrid(rows, cols, *rowStrings)
    )
}

/* ----------------------------------------------------------------- */
/*  Easy (5×5): 1–2 balls, direct vertical drops, 1–2 pins each      */
/*  Ball→Cup columns are identical; pins sit in same column.          */
/* ----------------------------------------------------------------- */
private fun chunkEasy(): List<PullPinLevel> = listOf(
    /* 1 ball, 1 pin */
    lvl("pullpin_easy_001", "Easy", 5, 5,
        ".....",
        "..1..",
        "..P..",
        "..a..",
        "WWWWW"
    ),
    /* 2 balls, 2 pins */
    lvl("pullpin_easy_002", "Easy", 5, 5,
        ".1.2.",
        ".....",
        ".P.P.",
        ".a.b.",
        "WWWWW"
    ),
    /* 1 ball, 2 stacked pins (need 2 removals) */
    lvl("pullpin_easy_003", "Easy", 5, 5,
        "..1..",
        "..P..",
        "..P..",
        "..a..",
        "WWWWW"
    ),
    /* 2 balls, 2 pins at row 1 */
    lvl("pullpin_easy_004", "Easy", 5, 5,
        ".1.2.",
        ".P.P.",
        ".....",
        ".a.b.",
        "WWWWW"
    )
)

/* ----------------------------------------------------------------- */
/*  Medium (6×6): 2–3 balls, mixed pin counts, staggered rows         */
/* ----------------------------------------------------------------- */
private fun chunkMedium(): List<PullPinLevel> = listOf(
    /* 3 balls, 3 pins — one each */
    lvl("pullpin_medium_001", "Medium", 6, 6,
        ".1.2.3",
        "......",
        ".P.P.P",
        "......",
        ".a.b.c",
        "WWWWWW"
    ),
    /* 2 balls, different pin rows */
    lvl("pullpin_medium_002", "Medium", 6, 6,
        ".1..2.",
        ".P....",
        "......",
        "...P..",
        ".a..b.",
        "WWWWWW"
    ),
    /* 2 balls, stacked pins on ball 1 */
    lvl("pullpin_medium_003", "Medium", 6, 6,
        "..1.2.",
        "......",
        "..P...",
        "..P...",
        "..a.b.",
        "WWWWWW"
    ),
    /* 3 balls, 2 pins, mixed columns */
    lvl("pullpin_medium_004", "Medium", 6, 6,
        "1.2.3.",
        "..P...",
        "....P.",
        "......",
        "a.b.c.",
        "WWWWWW"
    )
)

/* ----------------------------------------------------------------- */
/*  Hard (7×7): 3 balls, every ball has at least 1 pin               */
/* ----------------------------------------------------------------- */
private fun chunkHard(): List<PullPinLevel> = listOf(
    lvl("pullpin_hard_001", "Hard", 7, 7,
        "1..2..3",
        ".......",
        "..P....",
        "...P...",
        "......P",
        "a..b..c",
        "WWWWWWW"
    ),
    lvl("pullpin_hard_002", "Hard", 7, 7,
        ".1.2.3.",
        ".P.....",
        "...P...",
        ".....P.",
        ".......",
        ".a.b.c.",
        "WWWWWWW"
    ),
    lvl("pullpin_hard_003", "Hard", 7, 7,
        "1..2..3",
        ".......",
        "P......",
        "...P...",
        "......P",
        "a..b..c",
        "WWWWWWW"
    )
)

/* ----------------------------------------------------------------- */
/*  Expert (8×8): 3–4 balls, wider board, more pins                  */
/* ----------------------------------------------------------------- */
private fun chunkExpert(): List<PullPinLevel> = listOf(
    /* 4 balls, 4 pins */
    lvl("pullpin_expert_001", "Expert", 8, 8,
        "1.2.3.4.",
        "........",
        "..P..P..",
        "......P.",
        "........",
        "........",
        "a.b.c.d.",
        "WWWWWWWW"
    ),
    /* 3 balls, 3 pins, staggered */
    lvl("pullpin_expert_002", "Expert", 8, 8,
        "..1.2.3.",
        "........",
        "..P.....",
        "....P...",
        "......P.",
        "........",
        "..a.b.c.",
        "WWWWWWWW"
    ),
    /* 3 balls, 4 pins (ball2 has 2) */
    lvl("pullpin_expert_003", "Expert", 8, 8,
        "1..2..3.",
        "........",
        "P..P....",
        "...P....",
        "......P.",
        "........",
        "a..b..c.",
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
