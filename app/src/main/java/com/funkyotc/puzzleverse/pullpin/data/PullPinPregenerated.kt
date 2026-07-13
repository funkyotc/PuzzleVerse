package com.funkyotc.puzzleverse.pullpin.data

private fun parseGrid(rows: Int, cols: Int, vararg rowStrings: String): List<List<Cell>> {
    return rowStrings.map { rowStr ->
        require(rowStr.length == cols) { "Row '$rowStr' length ${rowStr.length} != $cols" }
        rowStr.map { ch ->
            when {
                ch == '.' -> Cell(CellType.EMPTY)
                ch == 'W' -> Cell(CellType.WALL)
                ch == 'P' -> Cell(CellType.PIN)
                ch == '0' -> Cell(CellType.BALL, color = 0)
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
    /* 1 ball, 1 pin - Warmup */
    lvl("pullpin_easy_001", "Easy", 5, 5,
        ".....",
        "..1..",
        "..P..",
        "..a..",
        "WWWWW"
    ),
    /* 1 ball, 1 pin - Simple Slide */
    lvl("pullpin_easy_002", "Easy", 5, 5,
        ".1...",
        ".....",
        "WWPWW",
        "WWaWW",
        "WWWWW"
    ),
    /* 1 ball, 2 stacked pins */
    lvl("pullpin_easy_003", "Easy", 5, 5,
        "..1..",
        "..P..",
        "..P..",
        "..a..",
        "WWWWW"
    ),
    /* 1 colored, 1 grey - Basic Mix */
    lvl("pullpin_easy_004", "Easy", 5, 5,
        ".1.0.",
        ".P.P.",
        "WWPWW",
        "WWaWW",
        "WWWWW"
    ),
    /* 1 colored, 1 grey, 3 pins - Funnel Mix */
    lvl("pullpin_easy_005", "Easy", 5, 5,
        "W1.0W",
        "WP.PW",
        "WWPWW",
        "WWaWW",
        "WWWWW"
    )
)

/* ----------------------------------------------------------------- */
/*  Medium (6×6): 2–3 balls, mixed pin counts, staggered rows         */
/* ----------------------------------------------------------------- */
private fun chunkMedium(): List<PullPinLevel> = listOf(
    /* 1 colored, 2 grey - Symmetrical Funnel */
    lvl("pullpin_medium_001", "Medium", 6, 6,
        ".010..",
        ".PPPP.",
        "WW..WW",
        "WWPWWW",
        "WWaWWW",
        "WWWWWW"
    ),
    /* 1 colored, 1 grey - Gate Keeper */
    lvl("pullpin_medium_002", "Medium", 6, 6,
        "WW1WWW",
        "WWPWWW",
        "W.0..W",
        "W.P..W",
        "W.a..W",
        "WWWWWW"
    ),
    /* 2 balls - Zig-Zag */
    lvl("pullpin_medium_003", "Medium", 6, 6,
        "10....",
        "PP....",
        "WW.PWW",
        "WW.PWW",
        "WW.aWW",
        "WWWWWW"
    ),
    /* 2 balls - Separate Wells */
    lvl("pullpin_medium_004", "Medium", 6, 6,
        "1W0...",
        "PWPPPP",
        "..P...",
        "..P...",
        "..a...",
        "WWWWWW"
    ),
    /* 1 colored, 2 grey - Color Cascade */
    lvl("pullpin_medium_005", "Medium", 6, 6,
        "W1.0.W",
        "WP.P.W",
        "WW.0.W",
        "WW.P.W",
        "WW.a.W",
        "WWWWWW"
    )
)

/* ----------------------------------------------------------------- */
/*  Hard (7×7): 3 balls, every ball has at least 1 pin               */
/* ----------------------------------------------------------------- */
private fun chunkHard(): List<PullPinLevel> = listOf(
    /* Red and Blue split */
    lvl("pullpin_hard_001", "Hard", 7, 7,
        "1...2..",
        "P...P..",
        "W.P.W..",
        "W.a.bWW",
        "WWWWWWW",
        "WWWWWWW",
        "WWWWWWW"
    ),
    /* Contamination Split */
    lvl("pullpin_hard_002", "Hard", 7, 7,
        ".1.0.2.",
        ".P.P.P.",
        "W..P..W",
        "WW.P.WW",
        "WWa.bWW",
        "WWWWWWW",
        "WWWWWWW"
    ),
    /* Chamber Lock */
    lvl("pullpin_hard_003", "Hard", 7, 7,
        "1..0..2",
        "P..P..P",
        "WW.P.WW",
        "WWa.bWW",
        "WWWWWWW",
        "WWWWWWW",
        "WWWWWWW"
    ),
    /* Gravity Slide */
    lvl("pullpin_hard_004", "Hard", 7, 7,
        "W1.0.2W",
        "WP.P.PW",
        "WW.0.WW",
        "WW.P.WW",
        "WWa.bWW",
        "WWWWWWW",
        "WWWWWWW"
    ),
    /* Dual Mix */
    lvl("pullpin_hard_005", "Hard", 7, 7,
        "10.02..",
        "PP.PP..",
        "W.P.P.W",
        "WWa.bWW",
        "WWWWWWW",
        "WWWWWWW",
        "WWWWWWW"
    )
)

/* ----------------------------------------------------------------- */
/*  Expert (8×8): 3–4 balls, wider board, more pins                  */
/* ----------------------------------------------------------------- */
private fun chunkExpert(): List<PullPinLevel> = listOf(
    /* Sorting Maze */
    lvl("pullpin_expert_001", "Expert", 8, 8,
        "1.0.0.2.",
        "P.P.P.P.",
        "W..P..W.",
        "WW.a.bWW",
        "WWWWWWWW",
        "WWWWWWWW",
        "WWWWWWWW",
        "WWWWWWWW"
    ),
    /* Order Chaos */
    lvl("pullpin_expert_002", "Expert", 8, 8,
        "1.2.3.0.",
        "P.P.P.P.",
        "W.P.P.W.",
        "WWa.b.cW",
        "WWWWWWWW",
        "WWWWWWWW",
        "WWWWWWWW",
        "WWWWWWWW"
    ),
    /* Dual Gate */
    lvl("pullpin_expert_003", "Expert", 8, 8,
        "1..2..3.",
        "........",
        "P..P....",
        "...P....",
        "......P.",
        "........",
        "a..b..c.",
        "WWWWWWWW"
    ),
    /* Contamination Master */
    lvl("pullpin_expert_004", "Expert", 8, 8,
        "1.0..0.2",
        "P.P..P.P",
        "W.0..0.W",
        "W.P..P.W",
        "WW.P..WW",
        "WW.a..WW",
        "Wb..b..W",
        "WWWWWWWW"
    ),
    /* The Pit */
    lvl("pullpin_expert_005", "Expert", 8, 8,
        "...1....",
        "...P....",
        ".0.0.0..",
        ".P.P.P..",
        "WW.P.WWW",
        "WW.a.WWW",
        "WWWWWWWW",
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
