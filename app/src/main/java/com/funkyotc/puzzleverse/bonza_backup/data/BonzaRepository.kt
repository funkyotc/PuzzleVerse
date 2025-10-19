package com.funkyotc.puzzleverse.bonza.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BonzaRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("BonzaPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveWords(words: List<DraggableWord>?, key: String) {
        val wordsJson = gson.toJson(words)
        sharedPreferences.edit().putString(key, wordsJson).apply()
    }

    fun loadWords(key: String): List<DraggableWord>? {
        val wordsJson = sharedPreferences.getString(key, null)
        val type = object : TypeToken<List<DraggableWord>>() {}.type
        return gson.fromJson(wordsJson, type)
    }

    fun saveClue(clue: String?, key: String) {
        sharedPreferences.edit().putString(key, clue).apply()
    }

    fun loadClue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}
