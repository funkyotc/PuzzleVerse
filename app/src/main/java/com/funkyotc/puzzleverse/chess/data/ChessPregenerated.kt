package com.funkyotc.puzzleverse.chess.data

object ChessPregenerated {

    val ALL_PUZZLES: List<PregeneratedChessPuzzle> by lazy {
        listOf(
            // ===== EASY: Mate in 1 (15 puzzles) =====
            PregeneratedChessPuzzle("Chess_Easy_001", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/3R2K1 w - - 0 1", "d1", "d8", 1),
            PregeneratedChessPuzzle("Chess_Easy_002", "Easy",
                "r5rk/5p1p/5R2/4Q3/8/8/7P/7K w - - 0 1", "e5", "e8", 1),
            PregeneratedChessPuzzle("Chess_Easy_003", "Easy",
                "6k1/5p1p/6p1/8/8/8/5PPP/4R1K1 w - - 0 1", "e1", "e8", 1),
            PregeneratedChessPuzzle("Chess_Easy_004", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/3Q2K1 w - - 0 1", "d1", "d8", 1),
            PregeneratedChessPuzzle("Chess_Easy_005", "Easy",
                "6k1/5ppp/8/8/8/8/6PP/5QK1 w - - 0 1", "f1", "f8", 1),
            PregeneratedChessPuzzle("Chess_Easy_006", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/6KQ w - - 0 1", "h1", "h8", 1),
            PregeneratedChessPuzzle("Chess_Easy_007", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/R6K w - - 0 1", "a1", "a8", 1),
            PregeneratedChessPuzzle("Chess_Easy_008", "Easy",
                "6k1/5ppp/8/8/8/8/4Q1PP/6K1 w - - 0 1", "e1", "e8", 1),
            PregeneratedChessPuzzle("Chess_Easy_009", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/2Q3K1 w - - 0 1", "c1", "c8", 1),
            PregeneratedChessPuzzle("Chess_Easy_010", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/1R4K1 w - - 0 1", "b1", "b8", 1),
            PregeneratedChessPuzzle("Chess_Easy_011", "Easy",
                "7k/5ppp/8/8/8/8/5PPP/3R3K w - - 0 1", "d1", "d8", 1),
            PregeneratedChessPuzzle("Chess_Easy_012", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/6KR w - - 0 1", "h1", "h8", 1),
            PregeneratedChessPuzzle("Chess_Easy_013", "Easy",
                "6k1/5ppp/8/8/8/8/5PPP/4R1K1 w - - 0 1", "e1", "e8", 1),
            PregeneratedChessPuzzle("Chess_Easy_014", "Easy",
                "6k1/6pp/8/8/8/8/6PP/R5K1 w - - 0 1", "a1", "a8", 1),
            PregeneratedChessPuzzle("Chess_Easy_015", "Easy",
                "7k/6pp/8/8/8/8/6PP/3Q2K1 w - - 0 1", "d1", "d8", 1),

            // ===== MEDIUM: Mate in 2 (15 puzzles) =====
            PregeneratedChessPuzzle("Chess_Medium_001", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/2R2K1R w - - 0 1", "h1", "h7", 2),
            PregeneratedChessPuzzle("Chess_Medium_002", "Medium",
                "r1bq1r1k/ppp2ppp/2n5/3pp3/1b1P4/2NQ1N2/PPP2PPP/R1B2RK1 w - - 0 1", "d3", "h7", 2),
            PregeneratedChessPuzzle("Chess_Medium_003", "Medium",
                "r4rk1/ppp2ppp/8/8/8/8/PPP2PPP/2KR3R w - - 0 1", "d1", "d8", 2),
            PregeneratedChessPuzzle("Chess_Medium_004", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/1Q3KR1 w - - 0 1", "b2", "f6", 2),
            PregeneratedChessPuzzle("Chess_Medium_005", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/3R1K1R w - - 0 1", "h1", "h7", 2),
            PregeneratedChessPuzzle("Chess_Medium_006", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/1R2K1R1 w - - 0 1", "b1", "b8", 2),
            PregeneratedChessPuzzle("Chess_Medium_007", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/2Q1K1R1 w - - 0 1", "c2", "c8", 2),
            PregeneratedChessPuzzle("Chess_Medium_008", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/3Rk1R1 w - - 0 1", "h1", "h7", 2),
            PregeneratedChessPuzzle("Chess_Medium_009", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/4K2R w - - 0 1", "h1", "h8", 2),
            PregeneratedChessPuzzle("Chess_Medium_010", "Medium",
                "r5k1/5ppp/8/8/8/8/5PPP/2KR3R w - - 0 1", "d1", "d8", 2),
            PregeneratedChessPuzzle("Chess_Medium_011", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/1R1K1R2 w - - 0 1", "f1", "f8", 2),
            PregeneratedChessPuzzle("Chess_Medium_012", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/2R1K2R w - - 0 1", "h1", "h7", 2),
            PregeneratedChessPuzzle("Chess_Medium_013", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/1Q1K2R1 w - - 0 1", "b2", "b8", 2),
            PregeneratedChessPuzzle("Chess_Medium_014", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/2KR3R w - - 0 1", "h1", "h8", 2),
            PregeneratedChessPuzzle("Chess_Medium_015", "Medium",
                "6k1/5ppp/8/8/8/8/5PPP/3R1K1R w - - 0 1", "h1", "h7", 2),

            // ===== HARD: Mate in 3+ (10 puzzles) =====
            PregeneratedChessPuzzle("Chess_Hard_001", "Hard",
                "r5rk/5p1p/5R2/4Q3/8/8/7P/7K w - - 0 1", "e5", "h8", 3),
            PregeneratedChessPuzzle("Chess_Hard_002", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/2QR2K1 w - - 0 1", "c2", "c8", 3),
            PregeneratedChessPuzzle("Chess_Hard_003", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/1R1Q2K1 w - - 0 1", "d1", "d8", 3),
            PregeneratedChessPuzzle("Chess_Hard_004", "Hard",
                "r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5Q2/PPPP1PPP/RNB1K1NR w - - 0 1", "f3", "f7", 3),
            PregeneratedChessPuzzle("Chess_Hard_005", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/1QR2RK1 w - - 0 1", "c2", "c8", 3),
            PregeneratedChessPuzzle("Chess_Hard_006", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/2QR1K1R w - - 0 1", "h1", "h7", 3),
            PregeneratedChessPuzzle("Chess_Hard_007", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/1Q1R1K1R w - - 0 1", "h1", "h7", 3),
            PregeneratedChessPuzzle("Chess_Hard_008", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/1R1QK1R1 w - - 0 1", "h1", "h7", 3),
            PregeneratedChessPuzzle("Chess_Hard_009", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/2Q1K1R1 w - - 0 1", "h1", "h7", 3),
            PregeneratedChessPuzzle("Chess_Hard_010", "Hard",
                "6k1/5ppp/8/8/8/8/5PPP/2KR2R1 w - - 0 1", "h1", "h7", 3),
        )
    }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedChessPuzzle>> by lazy {
        ALL_PUZZLES.groupBy { it.difficulty }
    }

    fun getPuzzleById(id: String): PregeneratedChessPuzzle? = ALL_PUZZLES.find { it.id == id }

    fun getRandomPuzzle(difficulty: String? = null): PregeneratedChessPuzzle? {
        return if (difficulty != null) {
            PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()
        } else {
            ALL_PUZZLES.randomOrNull()
        }
    }

    fun getDailyPuzzle(epochDay: Long): PregeneratedChessPuzzle {
        val index = (epochDay % ALL_PUZZLES.size).toInt()
        return ALL_PUZZLES[index]
    }
}
