package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

/** Necessary win-rule checks; dynamic pin-order and capture proof remains separate. */
class PullPinWinPreconditionsTest {
    @Test fun everyCupCanReceiveAColorPresentInItsLevel() {
        val failures = mutableListOf<String>()
        assertEquals(48, PullPinPregenerated.ALL_LEVELS.size)
        for (level in PullPinPregenerated.ALL_LEVELS) {
            val colors = level.balls.map { it.color }.filter { it != 0 }.toSet()
            val missing = level.cups.map { it.color }.filter { it !in colors }
            if (missing.isNotEmpty()) failures += "${level.id}: missing ball colors $missing"
        }
        assertTrue(failures.joinToString("\n"), failures.isEmpty())
    }
}
