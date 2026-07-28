package com.funkyotc.puzzleverse.hexasort.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences

class HexaSortRepository(
    context: Context? = null,
    prefs: SharedPreferences? = null
) {
    private val prefs: SharedPreferences = prefs ?: context?.getSharedPreferences("HexaSortPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()

    private val gson = Gson()
    private val saveStateRepo = com.funkyotc.puzzleverse.core.data.SaveStateRepository(context)

    fun saveGrid(grid: List<List<Int?>>, key: String) {
        val json = gson.toJson(grid)
        prefs.edit { putString(key, json) }
        val hasTiles = grid.any { row -> row.any { cell -> cell != null } }
        if (hasTiles) {
            saveStateRepo.saveGameState("hexasort", mode = if (key.contains("daily")) "daily" else "standard")
        } else {
            saveStateRepo.clearSaveState("hexasort")
        }
    }

    fun loadGrid(key: String): List<List<Int?>>? {
        val json = prefs.getString(key, null) ?: return null
        return try {
            gson.fromJson(json, Array<Array<Any?>>::class.java)?.map { row ->
                row.map { cell ->
                    when (cell) {
                        is Double -> cell.toInt()
                        is Int -> cell
                        else -> null
                    }
                }
            }
        } catch (e: Exception) {
            prefs.edit { remove(key) }
            null
        }
    }

    fun saveInt(key: String, value: Int) {
        prefs.edit { putInt(key, value) }
    }

    fun loadInt(key: String, default: Int = 0): Int {
        return prefs.getInt(key, default)
    }

    fun removeKey(key: String) {
        prefs.edit { remove(key) }
        if (key.contains("grid")) {
            saveStateRepo.clearSaveState("hexasort")
        }
    }
}
