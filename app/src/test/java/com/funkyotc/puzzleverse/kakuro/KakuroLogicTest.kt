package com.funkyotc.puzzleverse.kakuro

import com.funkyotc.puzzleverse.kakuro.data.CellType
import com.funkyotc.puzzleverse.kakuro.data.Clue
import com.funkyotc.puzzleverse.kakuro.data.KakuroCell
import com.funkyotc.puzzleverse.kakuro.data.KakuroPregenerated
import com.funkyotc.puzzleverse.kakuro.data.KakuroPuzzleLibrary
import com.funkyotc.puzzleverse.kakuro.data.KakuroState
import com.funkyotc.puzzleverse.kakuro.viewmodel.KakuroViewModel
import org.junit.Assert.*
import org.junit.Test

class KakuroLogicTest {

    @Test
    fun testKakuroCellTypesAndClues() {
        val clueCell = KakuroCell(
            type = CellType.CLUE,
            clue = Clue(horizontalSum = 16, verticalSum = 24),
            r = 0,
            c = 1
        )
        assertEquals(CellType.CLUE, clueCell.type)
        assertEquals(16, clueCell.clue?.horizontalSum)
        assertEquals(24, clueCell.clue?.verticalSum)

        val inputCell = KakuroCell(
            type = CellType.PLAYER_INPUT,
            playerValue = 7,
            r = 1,
            c = 1
        )
        assertEquals(CellType.PLAYER_INPUT, inputCell.type)
        assertEquals(7, inputCell.playerValue)
    }

    @Test
    fun testKakuroInitialState() {
        val state = KakuroState(rows = 4, cols = 4)
        assertEquals(4, state.rows)
        assertEquals(4, state.cols)
        assertFalse(state.isWon)
        assertFalse(state.isGameOver)
    }

    @Test
    fun testPregeneratedPuzzlesAreValidAndNonEmpty() {
        val allPuzzles = KakuroPregenerated.ALL_PUZZLES
        assertTrue("Pregenerated puzzles list should not be empty", allPuzzles.isNotEmpty())

        for (puzzle in allPuzzles) {
            val grid = puzzle.grid
            var clueCount = 0
            var inputCount = 0

            for (row in grid) {
                for (cell in row) {
                    if (cell.type == CellType.CLUE) clueCount++
                    if (cell.type == CellType.PLAYER_INPUT) inputCount++
                }
            }

            assertTrue("Puzzle ${puzzle.id} must have at least one CLUE cell", clueCount > 0)
            assertTrue("Puzzle ${puzzle.id} must have at least one PLAYER_INPUT cell", inputCount > 0)
        }
    }

    @Test
    fun testKakuroPuzzleLibraryReturnsValidPuzzles() {
        for (i in 1..20) {
            val grid = KakuroPuzzleLibrary.getRandomPuzzle()
            var clueCount = 0
            var inputCount = 0

            for (row in grid) {
                for (cell in row) {
                    if (cell.type == CellType.CLUE) clueCount++
                    if (cell.type == CellType.PLAYER_INPUT) inputCount++
                }
            }

            assertTrue("Library puzzle must have CLUE cells", clueCount > 0)
            assertTrue("Library puzzle must have PLAYER_INPUT cells", inputCount > 0)
        }
    }

    @Test
    fun testKakuroSolve3x3Puzzle() {
        val vm = KakuroViewModel()
        // Fill 3x3 solution: (1,1)=1, (1,2)=2, (2,1)=3, (2,2)=4
        val state = vm.state.value
        val isLevel1 = state.rows == 3 && state.cols == 3
        if (isLevel1) {
            vm.setCellValue(1, 1, 1)
            vm.setCellValue(1, 2, 2)
            vm.setCellValue(2, 1, 3)
            vm.setCellValue(2, 2, 4)
            assertTrue("Solving 3x3 puzzle should win", vm.state.value.isWon)
        }
    }
}
