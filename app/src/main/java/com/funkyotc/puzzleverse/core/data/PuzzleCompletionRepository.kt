package com.funkyotc.puzzleverse.core.data

import android.content.Context

class PuzzleCompletionRepository(context: Context, gameName: String) {
    private val prefs = context.getSharedPreferences("${gameName}PuzzleCompletion", Context.MODE_PRIVATE)

    fun isCompleted(puzzleId: String): Boolean = prefs.getBoolean(puzzleId, false)

    fun markCompleted(puzzleId: String) {
        prefs.edit().putBoolean(puzzleId, true).apply()
    }

    fun getCompletedIds(): Set<String> = try {
        prefs.all.filter { it.value == true }.keys
    } catch (e: Exception) {
        emptySet()
    }
}
