package com.funkyotc.puzzleverse.kakuro

import com.funkyotc.puzzleverse.kakuro.data.CellType
import com.funkyotc.puzzleverse.kakuro.data.Clue
import com.funkyotc.puzzleverse.kakuro.data.KakuroCell
import com.funkyotc.puzzleverse.kakuro.data.KakuroState
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
}
