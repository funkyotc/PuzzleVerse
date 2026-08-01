package com.funkyotc.puzzleverse.tfe

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.data.SaveStateRepository
import com.funkyotc.puzzleverse.tfe.data.Direction
import com.funkyotc.puzzleverse.tfe.data.TfeRepository
import com.funkyotc.puzzleverse.tfe.data.TfeState
import com.funkyotc.puzzleverse.tfe.data.Tile
import com.funkyotc.puzzleverse.tfe.viewmodel.TfeViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

private class FakeTestContext : ContextWrapper(null) {
    private val prefsMap = mutableMapOf<String, SharedPreferences>()

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences {
        return prefsMap.getOrPut(name ?: "default") { InMemorySharedPreferences() }
    }

    override fun getApplicationContext(): Context = this
}

class TfeSaveStateTest {

    private lateinit var context: FakeTestContext
    private lateinit var tfeRepo: TfeRepository
    private lateinit var saveStateRepo: SaveStateRepository

    @Before
    fun setUp() {
        context = FakeTestContext()
        tfeRepo = TfeRepository(context)
        saveStateRepo = SaveStateRepository(context)
    }

    @Test
    fun testSavingAndResumingStandardGame() {
        val viewModel1 = TfeViewModel(context = context, mode = "standard")
        val initialTiles = viewModel1.state.value.tiles
        assertEquals(2, initialTiles.size)

        // Force a known state with custom tiles and score
        val testTiles = listOf(
            Tile(id = "tile-1", value = 4, row = 0, col = 0),
            Tile(id = "tile-2", value = 8, row = 1, col = 1)
        )
        val customState = TfeState(tiles = testTiles, score = 100, isGameOver = false, isWon = false)
        tfeRepo.saveGame("standard_tfe_board", customState)

        assertTrue("SaveStateRepository must record active save state for TFE standard mode", saveStateRepo.hasSaveState("tfe"))
        val meta = saveStateRepo.getSaveState("tfe")
        assertNotNull(meta)
        assertEquals("standard", meta?.mode)

        // Resume game with a new ViewModel instance
        val viewModel2 = TfeViewModel(context = context, mode = "standard")
        val restoredState = viewModel2.state.value

        assertEquals(100, restoredState.score)
        assertEquals(2, restoredState.tiles.size)
        assertEquals(4, restoredState.tileAt(0, 0)?.value)
        assertEquals(8, restoredState.tileAt(1, 1)?.value)
        assertFalse(restoredState.isGameOver)
        assertFalse(restoredState.isWon)
    }

    @Test
    fun testSavingAndResumingDailyGame() {
        val viewModel1 = TfeViewModel(context = context, mode = "daily")
        
        val testTiles = listOf(
            Tile(id = "tile-d1", value = 16, row = 2, col = 2),
            Tile(id = "tile-d2", value = 32, row = 3, col = 3)
        )
        val dailyState = TfeState(tiles = testTiles, score = 250, isGameOver = false, isWon = false)
        tfeRepo.saveGame("daily_tfe_board", dailyState)

        assertTrue("SaveStateRepository must record active save state for TFE daily mode", saveStateRepo.hasSaveState("tfe"))
        val meta = saveStateRepo.getSaveState("tfe")
        assertNotNull(meta)
        assertEquals("daily", meta?.mode)

        // Resume daily game
        val viewModel2 = TfeViewModel(context = context, mode = "daily")
        val restoredState = viewModel2.state.value

        assertEquals(250, restoredState.score)
        assertEquals(2, restoredState.tiles.size)
        assertEquals(16, restoredState.tileAt(2, 2)?.value)
        assertEquals(32, restoredState.tileAt(3, 3)?.value)
    }

    @Test
    fun testGameOverClearsSaveState() {
        // Construct a full 4x4 board with alternating values (no moves possible)
        val fullGridTiles = mutableListOf<Tile>()
        val values = listOf(
            listOf(2, 4, 2, 4),
            listOf(4, 2, 4, 2),
            listOf(2, 4, 2, 4),
            listOf(4, 2, 4, 2)
        )
        for (r in 0..3) {
            for (c in 0..3) {
                fullGridTiles.add(Tile(id = "t_${r}_$c", value = values[r][c], row = r, col = c))
            }
        }
        val fullState = TfeState(tiles = fullGridTiles, score = 500, isGameOver = false, isWon = false)
        tfeRepo.saveGame("standard_tfe_board", fullState)
        assertTrue("Save state should exist before game over check", saveStateRepo.hasSaveState("tfe"))

        // Create ViewModel which loads fullState
        val viewModel = TfeViewModel(context = context, mode = "standard")

        // Trigger move which detects game over
        viewModel.move(Direction.UP)

        assertTrue("Game should be game over", viewModel.state.value.isGameOver)
        assertFalse("SaveStateRepository must clear save state on Game Over", saveStateRepo.hasSaveState("tfe"))
        assertNull("Repository loadGame must return null after Game Over", tfeRepo.loadGame("standard_tfe_board"))
    }

    @Test
    fun testNewGameClearsSaveState() {
        val testTiles = listOf(Tile(id = "t1", value = 16, row = 0, col = 0))
        val activeState = TfeState(tiles = testTiles, score = 300, isGameOver = false, isWon = false)
        tfeRepo.saveGame("standard_tfe_board", activeState)
        assertTrue(saveStateRepo.hasSaveState("tfe"))

        // Starting new game with forceNewGame = true
        val newViewModel = TfeViewModel(context = context, mode = "standard", forceNewGame = true)

        val freshState = newViewModel.state.value
        assertEquals("Fresh game starts with score 0", 0, freshState.score)
        assertEquals("Fresh game starts with 2 random tiles", 2, freshState.tiles.size)
    }

    @Test
    fun testWonStateDoesNotResumeWonModal() {
        val testTiles = listOf(Tile(id = "w1", value = 2048, row = 0, col = 0))
        val wonState = TfeState(tiles = testTiles, score = 2048, isGameOver = false, isWon = true)
        
        // Save won state explicitly
        tfeRepo.saveGame("standard_tfe_board", wonState)

        // Attempting loadGame or initializing ViewModel on wonState should return null / start new game
        assertNull("loadGame should return null for won state", tfeRepo.loadGame("standard_tfe_board"))
        assertFalse("SaveStateRepository should be cleared for won state", saveStateRepo.hasSaveState("tfe"))

        val viewModel = TfeViewModel(context = context, mode = "standard")
        assertFalse("Resumed game must not start in won state", viewModel.state.value.isWon)
        assertEquals(2, viewModel.state.value.tiles.size)
    }

    @Test
    fun testCorruptedJsonOrNullTilesSafety() {
        val prefs = context.getSharedPreferences("TfePrefs", Context.MODE_PRIVATE)
        // Store invalid JSON with null tiles
        prefs.edit().putString("savedState_standard_tfe_board", "{\"tiles\": null, \"score\": 100}").commit()

        val loaded = tfeRepo.loadGame("standard_tfe_board")
        assertNull("Corrupted JSON with null tiles should return null safely", loaded)

        val viewModel = TfeViewModel(context = context, mode = "standard")
        assertNotNull(viewModel.state.value)
        assertEquals(2, viewModel.state.value.tiles.size)
    }
}
