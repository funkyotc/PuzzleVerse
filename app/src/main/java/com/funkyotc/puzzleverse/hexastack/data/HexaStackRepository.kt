package com.funkyotc.puzzleverse.hexastack.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences

class HexaStackRepository(
    context: Context? = null,
    prefs: SharedPreferences? = null
) {
    private val prefs: SharedPreferences = prefs ?: context?.getSharedPreferences("HexaStackPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()

    private val gson = Gson()
    private val saveStateRepo = com.funkyotc.puzzleverse.core.data.SaveStateRepository(context)

    data class SavedCell(val q: Int, val r: Int, val tiles: List<Int>)

    data class HexaStackSave(
        val levelId: String,
        val cells: List<SavedCell>,
        val tray: List<List<Int>?>,
        val deckIndex: Int,
        val score: Int,
        val moves: Int
    )

    fun saveGame(key: String, save: HexaStackSave) {
        prefs.edit { putString(key, gson.toJson(save)) }
        if (save.cells.isNotEmpty()) {
            saveStateRepo.saveGameState("hexastack", mode = if (key.contains("daily")) "daily" else "standard")
        } else {
            saveStateRepo.clearSaveState("hexastack")
        }
    }

    fun loadGame(key: String): HexaStackSave? {
        val json = prefs.getString(key, null) ?: return null
        return try {
            gson.fromJson(json, HexaStackSave::class.java)
        } catch (e: Exception) {
            prefs.edit { remove(key) }
            null
        }
    }

    fun removeKey(key: String) {
        prefs.edit { remove(key) }
        if (key.contains("grid")) {
            saveStateRepo.clearSaveState("hexastack")
        }
    }
}
