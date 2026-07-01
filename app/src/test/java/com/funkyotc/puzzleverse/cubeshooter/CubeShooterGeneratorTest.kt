package com.funkyotc.puzzleverse.cubeshooter
 
import com.funkyotc.puzzleverse.cubeshooter.data.CubeShooterPregenerated
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotNull
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

    @Test
    fun testCubeShooterViewModelInitialization() {
        val viewModel = com.funkyotc.puzzleverse.cubeshooter.viewmodel.CubeShooterViewModel(mode = "easy")
        val state = viewModel.state.value
        assertNotNull(state)
        
        // 3 source columns should be populated
        assertEquals(3, state!!.sourceColumns.size)
        
        // Storage tray should start empty
        assertTrue(state.storageTray.isEmpty())
        
        // Track should start empty
        assertTrue(state.track.isEmpty())
        
        // Projectiles and fading cubes should start empty
        assertTrue(state.projectiles.isEmpty())
        assertTrue(state.fadingCubes.isEmpty())
    }

    @Test
    fun testDispatchFromSource() {
        val viewModel = com.funkyotc.puzzleverse.cubeshooter.viewmodel.CubeShooterViewModel(mode = "easy")
        val stateBefore = viewModel.state.value!!
        
        // Find a column with a tank
        val colIndex = stateBefore.sourceColumns.indexOfFirst { it.isNotEmpty() }
        assertTrue(colIndex >= 0)
        
        val totalSourceTanksBefore = stateBefore.sourceColumns.sumOf { it.size }
        
        // Dispatch
        viewModel.dispatchFromSource(colIndex)
        val stateAfter = viewModel.state.value!!
        
        val totalSourceTanksAfter = stateAfter.sourceColumns.sumOf { it.size }
        assertEquals(totalSourceTanksBefore - 1, totalSourceTanksAfter)
        
        // One tank should be on the track
        assertEquals(1, stateAfter.track.size)
        
        // The track tank's position should be the bottom-middle index
        val cols = stateAfter.level.cols
        val rows = stateAfter.level.rows
        val middleCol = (cols + 1) / 2
        val expectedStartPos = cols + rows + (cols - middleCol)
        assertEquals(expectedStartPos.toFloat(), stateAfter.track.first().position)
    }
}
