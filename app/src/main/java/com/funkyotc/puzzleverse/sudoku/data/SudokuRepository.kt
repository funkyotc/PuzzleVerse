package com.funkyotc.puzzleverse.sudoku.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import java.lang.Exception

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.SystemUtcDaySource
import com.funkyotc.puzzleverse.core.UtcDaySource

class SudokuRepository(
    context: Context? = null,
    sharedPreferences: SharedPreferences? = null,
    private val daySource: UtcDaySource = SystemUtcDaySource
) {
    private val sharedPreferences: SharedPreferences = sharedPreferences ?: context?.getSharedPreferences("SudokuPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()

    private val gson = Gson()
    private val saveStateRepo = com.funkyotc.puzzleverse.core.data.SaveStateRepository(context)

    fun saveBoard(board: SudokuBoard, key: String) {
        val boardJson = gson.toJson(board)
        sharedPreferences.edit {
            putString(key, boardJson)
            if (key.startsWith("daily_")) putLong("${key}_epoch_day", daySource.epochDay())
        }
        val hasMoves = board.cells.any { !it.isHint && (it.number != 0 || it.pencilMarks.isNotEmpty()) }
        if (hasMoves) {
            val mode = when {
                key.startsWith("puzzle_") -> "puzzle"
                key.contains("daily") -> "daily"
                else -> "standard"
            }
            val puzzleId = if (key.startsWith("puzzle_")) key.removePrefix("puzzle_") else null
            saveStateRepo.saveGameState("sudoku", mode = mode, puzzleId = puzzleId)
        } else {
            saveStateRepo.clearSaveState("sudoku")
        }
    }

    fun loadBoard(key: String): SudokuBoard? {
        if (key.startsWith("daily_") && sharedPreferences.getLong("${key}_epoch_day", Long.MIN_VALUE) != daySource.epochDay()) {
            sharedPreferences.edit { remove(key); remove("${key}_epoch_day") }
            return null
        }
        val boardJson = sharedPreferences.getString(key, null) ?: return null
        return try {
            val board = gson.fromJson(boardJson, SudokuBoard::class.java)
            if (board != null && board.cells.isNotEmpty()) {
                board.getCell(0, 0)
            }
            board
        } catch (e: Exception) {
            sharedPreferences.edit { remove(key) }
            null
        }
    }
}
