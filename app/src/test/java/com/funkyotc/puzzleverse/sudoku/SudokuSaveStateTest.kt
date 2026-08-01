package com.funkyotc.puzzleverse.sudoku

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.data.SaveStateRepository
import com.funkyotc.puzzleverse.sudoku.data.SudokuBoard
import com.funkyotc.puzzleverse.sudoku.data.SudokuCell
import com.funkyotc.puzzleverse.sudoku.data.SudokuRepository
import com.funkyotc.puzzleverse.sudoku.generator.SudokuGenerator
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FakeTestContext : ContextWrapper(null) {
    private val prefsMap = mutableMapOf<String, SharedPreferences>()

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences {
        return prefsMap.getOrPut(name ?: "default") { InMemorySharedPreferences() }
    }

    override fun getApplicationContext(): Context = this
}

class SudokuSaveStateTest {

    private lateinit var context: FakeTestContext
    private lateinit var sudokuRepo: SudokuRepository
    private lateinit var saveStateRepo: SaveStateRepository
    private val generator = SudokuGenerator()

    @Before
    fun setUp() {
        context = FakeTestContext()
        sudokuRepo = SudokuRepository(context)
        saveStateRepo = SaveStateRepository(context)
    }

    @Test
    fun testPencilMarksOnlySavesStateAndResumable() {
        val initialBoard = generator.generate(seed = 12345L)
        val cells = initialBoard.cells.toMutableList()

        // Find an empty non-hint cell
        val emptyIndex = cells.indexOfFirst { !it.isHint && it.number == 0 }
        assertTrue("Board must have empty cells", emptyIndex != -1)

        val targetCell = cells[emptyIndex]
        cells[emptyIndex] = targetCell.copy(pencilMarks = setOf(3, 7))
        val boardWithPencilMarks = SudokuBoard(cells)

        sudokuRepo.saveBoard(boardWithPencilMarks, "standard_sudoku_board")

        // 1. Verify SaveStateRepository records active save state
        assertTrue("Save state should exist when user places pencil marks", saveStateRepo.hasSaveState("sudoku"))
        val saveMeta = saveStateRepo.getSaveState("sudoku")
        assertNotNull(saveMeta)
        assertEquals("standard", saveMeta?.mode)

        // 2. Verify board loading restores pencil marks
        val loadedBoard = sudokuRepo.loadBoard("standard_sudoku_board")
        assertNotNull(loadedBoard)
        val restoredCell = loadedBoard!!.getCell(targetCell.row, targetCell.col)
        assertEquals(setOf(3, 7), restoredCell.pencilMarks)
        assertEquals(0, restoredCell.number)
    }

    @Test
    fun testCompletingPuzzleClearsSaveState() {
        // First save a board with active user moves
        val initialBoard = generator.generate(seed = 12345L)
        val cells = initialBoard.cells.toMutableList()
        val emptyIndex = cells.indexOfFirst { !it.isHint }
        cells[emptyIndex] = cells[emptyIndex].copy(number = 5)
        sudokuRepo.saveBoard(SudokuBoard(cells), "standard_sudoku_board")
        assertTrue("Save state should exist before completion", saveStateRepo.hasSaveState("sudoku"))

        // Complete puzzle (clears save board by saving empty board)
        sudokuRepo.saveBoard(SudokuBoard(emptyList()), "standard_sudoku_board")

        // Verify save state is cleared
        assertFalse("Save state must be cleared when puzzle is completed", saveStateRepo.hasSaveState("sudoku"))
    }

    @Test
    fun testNewGameClearsSaveStateAndGeneratesFreshBoard() {
        // 1. User has an existing saved game
        val initialBoard = generator.generate(seed = 12345L)
        val cells = initialBoard.cells.toMutableList()
        val emptyIndex = cells.indexOfFirst { !it.isHint }
        cells[emptyIndex] = cells[emptyIndex].copy(number = 5)
        sudokuRepo.saveBoard(SudokuBoard(cells), "standard_sudoku_board")
        assertTrue(saveStateRepo.hasSaveState("sudoku"))

        // 2. User clicks "New Game" -> clears save state repo
        saveStateRepo.clearSaveState("sudoku")
        assertFalse("Save state should be cleared on New Game action", saveStateRepo.hasSaveState("sudoku"))

        // 3. Fresh board is generated and saved (has 0 user moves)
        val freshBoard = generator.generate(seed = 99999L)
        sudokuRepo.saveBoard(freshBoard, "standard_sudoku_board")

        // Fresh board without user moves should NOT trigger a save state
        assertFalse("Fresh board with 0 user moves should not create a save state", saveStateRepo.hasSaveState("sudoku"))
    }

    @Test
    fun testUndoToInitialStateClearsSaveState() {
        val initialBoard = generator.generate(seed = 12345L)
        
        // Initial board save -> has 0 user moves -> no save state
        sudokuRepo.saveBoard(initialBoard, "standard_sudoku_board")
        assertFalse("Initial board with 0 moves has no save state", saveStateRepo.hasSaveState("sudoku"))

        // User makes a move
        cellsLoop@ for (r in 0..8) {
            for (c in 0..8) {
                val cell = initialBoard.getCell(r, c)
                if (!cell.isHint) {
                    val updatedCells = initialBoard.cells.map {
                        if (it.row == r && it.col == c) it.copy(number = 1) else it
                    }
                    val boardWithMove = SudokuBoard(updatedCells)
                    sudokuRepo.saveBoard(boardWithMove, "standard_sudoku_board")
                    break@cellsLoop
                }
            }
        }
        assertTrue("Save state created after user move", saveStateRepo.hasSaveState("sudoku"))

        // User undos move back to initial board state
        sudokuRepo.saveBoard(initialBoard, "standard_sudoku_board")
        assertFalse("Undoing back to 0 user moves must clear save state", saveStateRepo.hasSaveState("sudoku"))
    }

    @Test
    fun testPuzzleModeSaveStateMetadata() {
        val initialBoard = generator.generate(seed = 12345L)
        val cells = initialBoard.cells.toMutableList()
        val emptyIndex = cells.indexOfFirst { !it.isHint }
        cells[emptyIndex] = cells[emptyIndex].copy(number = 5)

        sudokuRepo.saveBoard(SudokuBoard(cells), "puzzle_sudoku_1")
        assertTrue(saveStateRepo.hasSaveState("sudoku"))
        val saveMeta = saveStateRepo.getSaveState("sudoku")
        assertNotNull(saveMeta)
        assertEquals("puzzle", saveMeta?.mode)
        assertEquals("sudoku_1", saveMeta?.puzzleId)
    }

    @Test
    fun testDailyModeSaveStateMetadata() {
        val initialBoard = generator.generate(seed = 12345L)
        val cells = initialBoard.cells.toMutableList()
        val emptyIndex = cells.indexOfFirst { !it.isHint }
        cells[emptyIndex] = cells[emptyIndex].copy(number = 5)

        sudokuRepo.saveBoard(SudokuBoard(cells), "daily_sudoku_board")
        assertTrue(saveStateRepo.hasSaveState("sudoku"))
        val saveMeta = saveStateRepo.getSaveState("sudoku")
        assertNotNull(saveMeta)
        assertEquals("daily", saveMeta?.mode)
        assertNull(saveMeta?.puzzleId)
    }
}
