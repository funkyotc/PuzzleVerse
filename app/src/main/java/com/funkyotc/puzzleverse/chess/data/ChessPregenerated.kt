package com.funkyotc.puzzleverse.chess.data

object ChessPregenerated {

    val ALL_PUZZLES: List<PregeneratedChessPuzzle> by lazy {
        listOf(
            // ===== EASY: Mate in 1 =====
            PregeneratedChessPuzzle("Chess_Easy_001", "Easy", "6k1/5ppp/8/8/8/8/5PPP/3R2K1 w - - 0 1", listOf("d1d8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_002", "Easy", "r5rk/5p1p/5R2/4Q3/8/8/7P/7K w - - 0 1", listOf("e5e8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_003", "Easy", "6k1/5p1p/6p1/8/8/8/5PPP/4R1K1 w - - 0 1", listOf("e1e8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_004", "Easy", "6k1/5ppp/8/8/8/8/5PPP/3Q2K1 w - - 0 1", listOf("d1d8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_005", "Easy", "6k1/5ppp/8/8/8/8/6PP/5QK1 w - - 0 1", listOf("f1f8"), 1),

            // ===== MEDIUM: Mate in 2 =====
            PregeneratedChessPuzzle("Chess_Medium_001", "Medium", "r1bq2r1/b4pk1/p1pp1p2/1p2pP2/1P2P1PB/3P4/1PPQ2P1/R3K2R w KQ - 1 2", listOf("d2h6", "g7h6", "h4f6"), 2),
            PregeneratedChessPuzzle("Chess_Medium_002", "Medium", "r2q1b1r/1pN1n1pp/p1n3k1/4Pb2/2BP4/8/PPP3PP/R1BQ1RK1 w - - 1 13", listOf("d1g4", "f5g4", "c4f7"), 2),
            PregeneratedChessPuzzle("Chess_Medium_003", "Medium", "1r2k1r1/pbppnp1p/1b3P2/8/Q7/B1PB1q2/P4PPP/3R2K1 w - - 0 21", listOf("a4d7", "e8d7", "d3f5"), 2),
            PregeneratedChessPuzzle("Chess_Medium_004", "Medium", "rnbqkbnr/pppp1ppp/8/4p3/5PP1/8/PPPPP2P/RNBQKBNR b KQkq g3 0 2", listOf("d8h4"), 1), // Fool's mate

            // ===== HARD: Mate in 3 =====
            PregeneratedChessPuzzle("Chess_Hard_001", "Hard", "r5rk/5p1p/5R2/4Q3/8/8/7P/7K w - - 0 1", listOf("e5h8", "r8h8", "f6f7", "g8g7", "f7g7"), 3),
            PregeneratedChessPuzzle("Chess_Hard_002", "Hard", "r1b1kb1r/pppp1ppp/5q2/4n3/3KP3/2N3PN/PPP4P/R1BQ1B1R b kq - 0 1", listOf("f8c5", "d4c5", "f6b6", "c5d5", "b6d6"), 3)
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
