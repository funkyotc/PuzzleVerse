package com.funkyotc.puzzleverse.sudoku.data

import android.content.Context

class SudokuCompletionRepository(context: Context) {
    private val prefs = context.getSharedPreferences("SudokuPuzzleCompletion", Context.MODE_PRIVATE)

    fun isCompleted(puzzleId: String): Boolean = prefs.getBoolean(puzzleId, false)

    fun markCompleted(puzzleId: String) {
        prefs.edit().putBoolean(puzzleId, true).apply()
    }

    fun getCompletedIds(): Set<String> = prefs.all.filter { it.value == true }.keys
}
