package com.funkyotc.puzzleverse.wordle.generator

import java.io.File

/**
 * Standalone runner to validate candidate 5-letter words and generate target word sequences into valid_words.txt.
 */
fun main(args: Array<String>) {
    println("================================================================")
    println("WORDLE DICTIONARY GENERATOR RUNNER")
    println("================================================================")

    val candidates = listOf(
        File("app/src/main/assets/wordle/valid_words.txt"),
        File("src/main/assets/wordle/valid_words.txt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/assets/wordle/valid_words.txt")
    )
    val targetFile = candidates.find { it.exists() } ?: candidates.first()
    targetFile.parentFile?.mkdirs()

    val rawWords = if (targetFile.exists()) {
        targetFile.readLines()
    } else {
        emptyList()
    }

    println("Reading existing words count: ${rawWords.size}")

    // Filter and validate words
    val validWords = rawWords
        .map { it.trim().lowercase() }
        .filter { word ->
            val isValid = word.length == 5 && word.all { c -> c in 'a'..'z' }
            if (!isValid && word.isNotEmpty()) {
                println("  [Excluded Invalid Word]: '$word'")
            }
            isValid
        }
        .distinct()

    println("Validated clean 5-letter word dictionary count: ${validWords.size}")

    // Write back normalized sorted list of 5-letter words
    val sb = StringBuilder()
    for (word in validWords) {
        sb.append(word).append("\n")
    }

    targetFile.writeText(sb.toString())
    println("Successfully generated valid 5-letter word list into ${targetFile.absolutePath}")
}
