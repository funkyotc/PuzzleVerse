package com.funkyotc.puzzleverse.streak.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences

class StreakRepository(
    context: Context? = null,
    sharedPreferences: SharedPreferences? = null
) {
    private val sharedPreferences: SharedPreferences = sharedPreferences ?: context?.getSharedPreferences("StreakPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()


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
        sharedPreferences.edit { putString(streak.gameId, streakJson) }
    }

    fun isCompletedToday(gameId: String, today: Long = com.funkyotc.puzzleverse.core.todayEpochDay()): Boolean {
        return getStreak(gameId).isCompletedToday(today)
    }

    fun recordDailyCompletion(gameId: String, today: Long = com.funkyotc.puzzleverse.core.todayEpochDay()): Streak {
        val streak = getStreak(gameId)
        if (streak.lastCompletedEpochDay == today) {
            return streak
        }
        val newCount = if (streak.lastCompletedEpochDay == today - 1) streak.count + 1 else 1
        val updated = streak.copy(count = newCount, lastCompletedEpochDay = today)
        saveStreak(updated)
        return updated
    }
}

