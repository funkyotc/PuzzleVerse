package com.funkyotc.puzzleverse.nonogram.data

import android.content.Context

class NonogramCompletionRepository(context: Context) {
    private val prefs = context.getSharedPreferences("NonogramPuzzleCompletion", Context.MODE_PRIVATE)

    fun isCompleted(puzzleId: String): Boolean = prefs.getBoolean(puzzleId, false)

    fun markCompleted(puzzleId: String) {
        prefs.edit().putBoolean(puzzleId, true).apply()
    }

    fun getCompletedIds(): Set<String> = prefs.all.filter { it.value == true }.keys
}
