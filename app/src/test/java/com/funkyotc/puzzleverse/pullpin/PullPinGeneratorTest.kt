package com.funkyotc.puzzleverse.pullpin

import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.pullpin.data.WORLD_H
import com.funkyotc.puzzleverse.pullpin.data.WORLD_W
import org.junit.Assert.*
import org.junit.Test

class PullPinGeneratorTest {

    @Test
    fun testVerifyPullPinLevels() {
        val allLevels = PullPinPregenerated.ALL_LEVELS
        assertTrue("PullPinPregenerated should contain levels", allLevels.isNotEmpty())
        assertEquals(48, allLevels.size)

        val difficulties = listOf("Easy", "Medium", "Hard", "Expert")
        for (diff in difficulties) {
            val levels = PullPinPregenerated.PUZZLES_BY_DIFFICULTY[diff]
            assertNotNull("Levels for difficulty $diff should exist", levels)
            assertEquals(12, levels!!.size)
        }

        for (level in allLevels) {
            assertTrue("Level ${level.id} should have walls", level.walls.isNotEmpty())
            assertTrue("Level ${level.id} should have cups", level.cups.isNotEmpty())
            assertTrue("Level ${level.id} should have pins", level.pins.isNotEmpty())
            assertTrue("Level ${level.id} should have balls", level.balls.isNotEmpty())

            // Verify cup bounds
            for (cup in level.cups) {
                assertTrue("Cup X within bounds", cup.x in 0f..WORLD_W)
                assertTrue("Cup Y within bounds", cup.y in 0f..WORLD_H)
                assertTrue("Cup color valid", cup.color in 1..8)
            }

            // Verify ball bounds
            for (ball in level.balls) {
                assertTrue("Ball X within bounds", ball.x in 0f..WORLD_W)
                assertTrue("Ball Y within bounds", ball.y in 0f..WORLD_H)
                assertTrue("Ball color valid", ball.color in 0..8)
            }
        }
    }
}
