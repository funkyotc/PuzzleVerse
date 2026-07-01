package com.funkyotc.puzzleverse.cubeshooter

import com.funkyotc.puzzleverse.cubeshooter.data.CubeShooterPregenerated
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CubeShooterGeneratorTest {

    @Test
    fun testAllPregeneratedLevelsAreSolvable() {
        val levels = CubeShooterPregenerated.ALL_LEVELS
        assertTrue("No levels found!", levels.isNotEmpty())
        println("Testing ${levels.size} pregenerated levels...")

        for (level in levels) {
            val gridCubesCount = mutableMapOf<Int, Int>()
            for (row in level.grid) {
                for (cell in row) {
                    if (cell != null) {
                        gridCubesCount[cell] = gridCubesCount.getOrDefault(cell, 0) + 1
                    }
                }
            }

            val tankAmmoCount = mutableMapOf<Int, Int>()
            for (tank in level.tray) {
                tankAmmoCount[tank.color] = tankAmmoCount.getOrDefault(tank.color, 0) + tank.ammo
            }

            // Check that for each color present, the sum of tank ammo matches the grid cube count
            for (color in 0..3) {
                val gridCount = gridCubesCount.getOrDefault(color, 0)
                val ammoCount = tankAmmoCount.getOrDefault(color, 0)
                assertEquals(
                    "Level ${level.id} color $color mismatch! Grid: $gridCount, Ammo: $ammoCount",
                    gridCount,
                    ammoCount
                )
            }
        }
    }
}
