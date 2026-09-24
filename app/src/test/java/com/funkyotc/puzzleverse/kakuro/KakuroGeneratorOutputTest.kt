package com.funkyotc.puzzleverse.kakuro

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class KakuroGeneratorOutputTest {
    @Test fun regeneratedSourceRetainsBrowserAndGivenContracts() {
        val output = File.createTempFile("kakuro-generated-", ".kt")
        try {
            generators.kakuro.main(arrayOf(output.absolutePath))
            val source = output.readText()
            assertTrue(source.contains(") : BrowseablePuzzle"))
            assertTrue(source.contains("val startingGrid:"))
            assertTrue(source.contains("val givens: Map<Pair<Int, Int>, Int>"))
            assertEquals(24, Regex("PregeneratedKakuro\\(\\\"kakuro_").findAll(source).count())
        } finally {
            output.delete()
        }
    }
}
