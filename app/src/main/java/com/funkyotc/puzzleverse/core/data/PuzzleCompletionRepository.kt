package com.funkyotc.puzzleverse.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PuzzleCompletionRepository(
    context: Context? = null,
    gameName: String = "",
    prefs: SharedPreferences? = null
) {
    private val prefs: SharedPreferences = prefs ?: context?.getSharedPreferences("${gameName}PuzzleCompletion", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()


    fun isCompleted(puzzleId: String): Boolean = prefs.getBoolean(puzzleId, false)

    fun markCompleted(puzzleId: String) {
        prefs.edit { putBoolean(puzzleId, true) }
    }

    fun getCompletedIds(): Set<String> = try {
        prefs.all.filter { it.value == true }.keys
    } catch (e: Exception) {
        emptySet()
    }
}
