package com.funkyotc.puzzleverse.flowfree.data

import android.content.Context

/**
 * Tracks which pregenerated Flow Free puzzles have been completed.
 */
class FlowPuzzleCompletionRepository(context: Context) {

    private val prefs = context.getSharedPreferences("FlowPuzzleCompletion", Context.MODE_PRIVATE)

    fun isCompleted(puzzleId: String): Boolean {
        return prefs.getBoolean(puzzleId, false)
    }

    fun markCompleted(puzzleId: String) {
        prefs.edit().putBoolean(puzzleId, true).apply()
    }

    fun getCompletedIds(): Set<String> {
        return prefs.all.filter { it.value == true }.keys
    }
}
