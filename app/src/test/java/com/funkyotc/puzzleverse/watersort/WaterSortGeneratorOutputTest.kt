package com.funkyotc.puzzleverse.watersort

import com.funkyotc.puzzleverse.watersort.data.WaterSortPregenerated
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class WaterSortGeneratorOutputTest {
    @Test fun exportedLevelsRetainWinningReplays() {
        val output = File.createTempFile("watersort-generated-", ".kt")
        try {
            com.funkyotc.puzzleverse.generators.watersort.main(arrayOf(output.absolutePath))
            val source = output.readText()
            assertTrue(source.contains("val winningMoves: List<Pair<Int, Int>>"))
            assertEquals(WaterSortPregenerated.ALL_LEVELS.size,
                source.lines().count { it.contains("PregeneratedWaterSortLevel(\"") })
        } finally {
            output.delete()
        }
    }
}
