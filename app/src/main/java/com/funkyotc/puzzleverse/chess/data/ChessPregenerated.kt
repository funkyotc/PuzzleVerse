package com.funkyotc.puzzleverse.chess.data

object ChessPregenerated {

    val ALL_PUZZLES: List<PregeneratedChessPuzzle> by lazy {
        listOf(
            PregeneratedChessPuzzle("Chess_Easy_001", "Easy", "6k1/5ppp/8/8/8/8/5PPP/3R2K1 w - - 0 1", listOf("d1d8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_002", "Easy", "1k6/ppp5/8/8/8/8/PPP5/1K2R3 w - - 0 1", listOf("e1e8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_003", "Easy", "1k6/ppp5/8/8/8/8/PPP5/1K2Q3 w - - 0 1", listOf("e1e8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_004", "Easy", "6k1/5ppp/8/8/8/8/5PPP/3Q2K1 w - - 0 1", listOf("d1d8"), 1),
            PregeneratedChessPuzzle("Chess_Easy_005", "Easy", "kr6/pp6/8/3N4/8/8/8/1K6 w - - 0 1", listOf("d5c7"), 1),
            PregeneratedChessPuzzle("Chess_Easy_006", "Easy", "rnbqkbnr/pppp1ppp/8/4p3/5PP1/8/PPPPP2P/RNBQKBNR b KQkq g3 0 2", listOf("d8h4"), 1),
            PregeneratedChessPuzzle("Chess_Easy_007", "Easy", "r1bqkb1r/pppp1ppp/2n5/4p3/2B1P3/5Q2/PPPP1PPP/RNB1K1NR w KQkq - 0 1", listOf("f3f7"), 1),
            PregeneratedChessPuzzle("Chess_Easy_008", "Easy", "6rk/6pp/8/4N3/8/8/8/6K1 w - - 0 1", listOf("e5f7"), 1),
            PregeneratedChessPuzzle("Chess_Medium_001", "Medium", "r1bq2r1/b4pk1/p1pp1p2/1p2pP2/1P2P1PB/3P4/1PPQ2P1/R3K2R w KQ - 1 2", listOf("d2h6", "g7h6", "h4f6"), 2),
            PregeneratedChessPuzzle("Chess_Medium_002", "Medium", "r2q1b1r/1pN1n1pp/p1n3k1/4Pb2/2BP4/8/PPP3PP/R1BQ1RK1 w - - 1 13", listOf("d1g4", "f5g4", "c4f7"), 2),
            PregeneratedChessPuzzle("Chess_Medium_003", "Medium", "1r2qb1r/1kp4b/2p1pp1p/2Pp2p1/BP1P2P1/4P3/1P2QPP1/R2K3R w - - 0 1", listOf("e2a6", "b7a6", "a4c6"), 2),
            PregeneratedChessPuzzle("Chess_Medium_004", "Medium", "r1b1q2r/pp1n1Np1/1k3n1p/2bP4/4PB2/8/PP3PPP/1KR1QB1R w - - 0 1", listOf("e1b4", "c5b4", "f4c7"), 2),
            PregeneratedChessPuzzle("Chess_Hard_001", "Hard", "r1bk1b1r/ppp1pppp/2q5/3n4/3PK3/NP3N2/P4PPP/R1B1QB1R b - - 0 1", listOf("c8f5", "e4f5", "c6g6", "f5e5", "g6e6"), 3),
            PregeneratedChessPuzzle("Chess_Hard_002", "Hard", "r1b1kb1r/pppp1ppp/5q2/4n3/3KP3/2N3PN/PPP4P/R1BQ1B1R b kq - 0 1", listOf("f8c5", "d4c5", "f6b6", "c5d5", "b6d6"), 3),
            PregeneratedChessPuzzle("Chess_Hard_003", "Hard", "r1bq1b1r/ppp4p/2n3pn/3kp3/4N3/5Q2/PPPP1PPP/R1B1KB1R w - - 0 1", listOf("f1c4", "d5c4", "f3b3", "c4d4", "b3d3"), 3)
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
