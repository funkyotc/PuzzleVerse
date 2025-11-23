package com.funkyotc.puzzleverse.bonza.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class BonzaRepository(private val context: Context) {

    fun getPuzzleThemes(): List<BonzaPuzzleTheme> {
        return try {
            val json = context.assets.open("bonza/puzzles.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<BonzaPuzzleTheme>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}
