package com.funkyotc.puzzleverse.chess.data

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson

class ChessRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("ChessPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun savePuzzleState(state: ChessState, key: String) {
        val json = gson.toJson(state)
        sharedPreferences.edit { putString(key, json) }
    }

    fun loadPuzzleState(key: String): ChessState? {
        val json = sharedPreferences.getString(key, null) ?: return null
        return try {
            gson.fromJson(json, ChessState::class.java)
        } catch (e: Exception) {
            sharedPreferences.edit { remove(key) }
            null
        }
    }

    fun clearPuzzleState(key: String) {
        sharedPreferences.edit { remove(key) }
    }
}
