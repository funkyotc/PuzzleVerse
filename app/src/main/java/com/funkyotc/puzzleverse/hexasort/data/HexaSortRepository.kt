package com.funkyotc.puzzleverse.hexasort.data

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson

class HexaSortRepository(context: Context) {
    private val prefs = context.getSharedPreferences("HexaSortPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveGrid(grid: List<List<Int?>>, key: String) {
        val json = gson.toJson(grid)
        prefs.edit { putString(key, json) }
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
    }
}
