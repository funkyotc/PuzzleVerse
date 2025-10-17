package com.funkyotc.puzzleverse.streak.data

import android.content.Context
import com.google.gson.Gson

class StreakRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("StreakPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getStreak(gameId: String): Streak {
        val streakJson = sharedPreferences.getString(gameId, null)
        return if (streakJson != null) {
            gson.fromJson(streakJson, Streak::class.java)
        } else {
            Streak(gameId = gameId)
        }
    }

    fun saveStreak(streak: Streak) {
        val streakJson = gson.toJson(streak)
        sharedPreferences.edit().putString(streak.gameId, streakJson).apply()
    }
}
