package com.funkyotc.puzzleverse.sudoku.data

import android.content.Context
import com.google.gson.Gson
import java.lang.Exception

class SudokuRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("SudokuPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveBoard(board: SudokuBoard, key: String) {
        val boardJson = gson.toJson(board)
        sharedPreferences.edit().putString(key, boardJson).apply()
    }

    fun loadBoard(key: String): SudokuBoard? {
        val boardJson = sharedPreferences.getString(key, null) ?: return null
        return try {
            val board = gson.fromJson(boardJson, SudokuBoard::class.java)
            // This is a check to ensure the loaded board is valid.
            // If the board data is from an old, incompatible version, this will throw an exception.
            if (board != null && board.cells.isNotEmpty()) {
                board.getCell(0, 0)
            }
            board
        } catch (e: Exception) {
            // If we catch an exception, it means the saved data is corrupt.
            // We delete the bad data and return null to force a new board to be created.
            sharedPreferences.edit().remove(key).apply()
            null
        }
    }
}
