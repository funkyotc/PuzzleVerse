package generators.chess

import java.io.File
import java.net.URL

data class ChessPuzzleData(
    val id: String,
    val difficulty: String,
    val fen: String,
    val solutionMoves: List<String>,
    val mateIn: Int
)

fun fetchPuzzleFromUrl(urlStr: String): String? {
    return try {
        URL(urlStr).readText()
    } catch (e: Exception) {
        println("Could not fetch remote puzzle data ($urlStr): ${e.message}. Using built-in tactical set.")
        null
    }
}

fun parseFenPuzzle(line: String): ChessPuzzleData? {
    // Expected CSV or TSV format: id, difficulty, fen, moves (space separated), mateIn
    val parts = line.split(",", "\t")
    if (parts.size < 5) return null
    val id = parts[0].trim()
    val difficulty = parts[1].trim()
    val fen = parts[2].trim()
    val moves = parts[3].trim().split(" ")
    val mateIn = parts[4].trim().toIntOrNull() ?: 1
    return ChessPuzzleData(id, difficulty, fen, moves, mateIn)
}

fun getBuiltInTacticalPuzzles(): List<ChessPuzzleData> {
    return listOf(
        // ===== EASY: Mate in 1 =====
        ChessPuzzleData("Chess_Easy_001", "Easy", "6k1/5ppp/8/8/8/8/5PPP/3R2K1 w - - 0 1", listOf("d1d8"), 1),
        ChessPuzzleData("Chess_Easy_002", "Easy", "r5rk/5p1p/5R2/4Q3/8/8/7P/7K w - - 0 1", listOf("e5e8"), 1),
        ChessPuzzleData("Chess_Easy_003", "Easy", "6k1/5p1p/6p1/8/8/8/5PPP/4R1K1 w - - 0 1", listOf("e1e8"), 1),
        ChessPuzzleData("Chess_Easy_004", "Easy", "6k1/5ppp/8/8/8/8/5PPP/3Q2K1 w - - 0 1", listOf("d1d8"), 1),
        ChessPuzzleData("Chess_Easy_005", "Easy", "6k1/5ppp/8/8/8/8/6PP/5QK1 w - - 0 1", listOf("f1f8"), 1),
        ChessPuzzleData("Chess_Easy_006", "Easy", "rnbqkbnr/pppp1ppp/8/4p3/5PP1/8/PPPPP2P/RNBQKBNR b KQkq g3 0 2", listOf("d8h4"), 1),
        ChessPuzzleData("Chess_Easy_007", "Easy", "r1bqkb1r/pppp1ppp/2n5/4p3/2B1P3/5Q2/PPPP1PPP/RNB1K1NR w KQkq - 0 1", listOf("f3f7"), 1),
        ChessPuzzleData("Chess_Easy_008", "Easy", "6rk/6pp/8/4N3/8/8/8/6K1 w - - 0 1", listOf("e5f7"), 1),

        // ===== MEDIUM: Mate in 2 =====
        ChessPuzzleData("Chess_Medium_001", "Medium", "r1bq2r1/b4pk1/p1pp1p2/1p2pP2/1P2P1PB/3P4/1PPQ2P1/R3K2R w KQ - 1 2", listOf("d2h6", "g7h6", "h4f6"), 2),
        ChessPuzzleData("Chess_Medium_002", "Medium", "r2q1b1r/1pN1n1pp/p1n3k1/4Pb2/2BP4/8/PPP3PP/R1BQ1RK1 w - - 1 13", listOf("d1g4", "f5g4", "c4f7"), 2),
        ChessPuzzleData("Chess_Medium_003", "Medium", "1r2k1r1/pbppnp1p/1b3P2/8/Q7/B1PB1q2/P4PPP/3R2K1 w - - 0 21", listOf("a4d7", "e8d7", "d3f5"), 2),
        ChessPuzzleData("Chess_Medium_004", "Medium", "r1b1k2r/ppppqppp/2n5/4p3/2B1P1n1/3P1N2/PPP2PPP/RN1QK2R w KQkq - 0 1", listOf("c4f7", "e7f7", "f3g5"), 2),

        // ===== HARD: Mate in 3 =====
        ChessPuzzleData("Chess_Hard_001", "Hard", "r5rk/5p1p/5R2/4Q3/8/8/7P/7K w - - 0 1", listOf("e5h8", "r8h8", "f6f7", "g8g7", "f7g7"), 3),
        ChessPuzzleData("Chess_Hard_002", "Hard", "r1b1kb1r/pppp1ppp/5q2/4n3/3KP3/2N3PN/PPP4P/R1BQ1B1R b kq - 0 1", listOf("f8c5", "d4c5", "f6b6", "c5d5", "b6d6"), 3),
        ChessPuzzleData("Chess_Hard_003", "Hard", "r2q1r1k/pb1p1pp1/1p1b1n2/2p1n3/2P5/2N1PN2/PPQ2PPP/R1B1KB1R w KQ - 0 1", listOf("f3g5", "g7g6", "c2g6", "f7g6", "c3d5"), 3)
    )
}

fun main(args: Array<String>) {
    println("Generating Chess tactical FEN puzzles...")
    val urlArg = args.getOrNull(0)
    val fetchedContent = if (urlArg != null) fetchPuzzleFromUrl(urlArg) else null

    val puzzles = mutableListOf<ChessPuzzleData>()
    if (fetchedContent != null) {
        fetchedContent.lines().forEach { line ->
            val parsed = parseFenPuzzle(line)
            if (parsed != null) puzzles.add(parsed)
        }
    }

    if (puzzles.isEmpty()) {
        puzzles.addAll(getBuiltInTacticalPuzzles())
    }

    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.chess.data")
    sb.appendLine()
    sb.appendLine("object ChessPregenerated {")
    sb.appendLine()
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedChessPuzzle> by lazy {")
    sb.appendLine("        listOf(")

    for ((idx, p) in puzzles.withIndex()) {
        val moveListStr = p.solutionMoves.joinToString(", ") { "\"$it\"" }
        val comma = if (idx < puzzles.size - 1) "," else ""
        sb.appendLine("            PregeneratedChessPuzzle(\"${p.id}\", \"${p.difficulty}\", \"${p.fen}\", listOf($moveListStr), ${p.mateIn})$comma")
    }

    sb.appendLine("        )")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedChessPuzzle>> by lazy {")
    sb.appendLine("        ALL_PUZZLES.groupBy { it.difficulty }")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): PregeneratedChessPuzzle? = ALL_PUZZLES.find { it.id == id }")
    sb.appendLine()
    sb.appendLine("    fun getRandomPuzzle(difficulty: String? = null): PregeneratedChessPuzzle? {")
    sb.appendLine("        return if (difficulty != null) {")
    sb.appendLine("            PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()")
    sb.appendLine("        } else {")
    sb.appendLine("            ALL_PUZZLES.randomOrNull()")
    sb.appendLine("        }")
    sb.appendLine("    }")
    sb.appendLine()
    sb.appendLine("    fun getDailyPuzzle(epochDay: Long): PregeneratedChessPuzzle {")
    sb.appendLine("        val index = (epochDay % ALL_PUZZLES.size).toInt()")
    sb.appendLine("        return ALL_PUZZLES[index]")
    sb.appendLine("    }")
    sb.appendLine("}")

    val targetFileCandidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/chess/data/ChessPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/chess/data/ChessPregenerated.kt")
    )
    val targetFile = targetFileCandidates.find { it.parentFile.exists() } ?: targetFileCandidates.first()
    targetFile.parentFile.mkdirs()
    targetFile.writeText(sb.toString())
    println("Successfully output ${puzzles.size} Chess FEN tactical puzzles to ${targetFile.absolutePath}")
}
