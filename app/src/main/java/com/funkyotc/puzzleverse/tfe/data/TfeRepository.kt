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

    fun saveGame(key: String, state: TfeState) {
        try {
            val json = gson.toJson(state)
            sharedPreferences.edit { putString("savedState_$key", json) }
            val mode = if (key.contains("daily")) "daily" else "standard"
            if (!state.isGameOver && (state.tiles.isNotEmpty() || state.score > 0)) {
                saveStateRepo.saveGameState("tfe", mode = mode)
            } else {
                saveStateRepo.clearSaveState("tfe")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadGame(key: String): TfeState? {
        return try {
            val json = sharedPreferences.getString("savedState_$key", null)
            if (json != null) {
                gson.fromJson(json, TfeState::class.java)
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
