package com.funkyotc.puzzleverse.tangrams

import com.funkyotc.puzzleverse.tangrams.engine.TangramValidator
import org.junit.Assert.assertFalse
import org.junit.Test

class TangramValidatorTest {

    @Test
    fun testEmptyOrInsufficientPieces() {
        // Less than 7 pieces must fail validation
        val result = TangramValidator.isPuzzleSolved(emptyList(), null)
        assertFalse(result)
    }
}
