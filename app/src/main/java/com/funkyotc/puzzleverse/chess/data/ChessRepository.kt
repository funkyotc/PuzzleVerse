package com.funkyotc.puzzleverse.chess.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences

class ChessRepository(
    context: Context? = null,
    sharedPreferences: SharedPreferences? = null
) {
    private val sharedPreferences: SharedPreferences = sharedPreferences ?: context?.getSharedPreferences("ChessPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()

    private val gson = Gson()
    private val saveStateRepo = com.funkyotc.puzzleverse.core.data.SaveStateRepository(context, sharedPreferences)

    fun savePuzzleState(state: ChessState, key: String) {
        val json = gson.toJson(state)
        sharedPreferences.edit { putString(key, json) }
        if (!state.isWon && !state.isGameOver && state.moveAttempts > 0) {
            saveStateRepo.saveGameState("chess", mode = if (key.contains("daily")) "daily" else "standard", puzzleId = state.puzzleId)
        } else {
            saveStateRepo.clearSaveState("chess")
        }
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
        saveStateRepo.clearSaveState("chess")
    }
}
