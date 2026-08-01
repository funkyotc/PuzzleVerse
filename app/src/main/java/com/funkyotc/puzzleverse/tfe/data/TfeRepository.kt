package com.funkyotc.puzzleverse.tfe.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.data.SaveStateRepository
import com.google.gson.Gson

class TfeRepository(
    context: Context? = null,
    sharedPreferences: SharedPreferences? = null
) {
    private val sharedPreferences: SharedPreferences = sharedPreferences
        ?: context?.getSharedPreferences("TfePrefs", Context.MODE_PRIVATE)
        ?: InMemorySharedPreferences()

    private val gson = Gson()
    private val saveStateRepo = SaveStateRepository(context)

    @Suppress("SENSELESS_COMPARISON")
    fun saveGame(key: String, state: TfeState) {
        try {
            val json = gson.toJson(state)
            val mode = if (key.contains("daily")) "daily" else "standard"
            if (!state.isGameOver && !state.isWon && state.tiles != null && (state.tiles.isNotEmpty() || state.score > 0)) {
                sharedPreferences.edit { putString("savedState_$key", json) }
                saveStateRepo.saveGameState("tfe", mode = mode)
            } else {
                clearGame(key)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    fun loadGame(key: String): TfeState? {
        return try {
            val json = sharedPreferences.getString("savedState_$key", null)
            if (json != null) {
                val state = gson.fromJson(json, TfeState::class.java)
                if (state != null && state.tiles != null && state.tiles.isNotEmpty() && !state.isGameOver && !state.isWon) {
                    state
                } else {
                    if (state != null) {
                        clearGame(key)
                    }
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            sharedPreferences.edit { remove("savedState_$key") }
            null
        }
    }

    fun clearGame(key: String) {
        sharedPreferences.edit { remove("savedState_$key") }
        saveStateRepo.clearSaveState("tfe")
    }
}
