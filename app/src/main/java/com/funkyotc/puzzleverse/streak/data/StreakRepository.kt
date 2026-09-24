package com.funkyotc.puzzleverse.streak.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import com.funkyotc.puzzleverse.core.SystemUtcDaySource
import com.funkyotc.puzzleverse.core.UtcDaySource

class StreakRepository(
    context: Context? = null,
    sharedPreferences: SharedPreferences? = null,
    private val daySource: UtcDaySource = SystemUtcDaySource,
    private val runEpochDay: Long? = null
) {
    private val sharedPreferences: SharedPreferences = sharedPreferences ?: context?.getSharedPreferences("StreakPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()


    private val gson = Gson()

    /** Keeps an old screen's delayed completion tied to the day it opened. */
    fun forDailyRun(epochDay: Long): StreakRepository = StreakRepository(
        sharedPreferences = sharedPreferences, daySource = daySource, runEpochDay = epochDay
    )

    fun markActiveDailyRun(gameId: String, epochDay: Long) {
        sharedPreferences.edit { putLong("active_daily_day_$gameId", epochDay) }
    }

    fun getStreak(gameId: String): Streak {
        val streakJson = sharedPreferences.getString(gameId, null)
        return if (streakJson != null) {
            gson.fromJson(streakJson, Streak::class.java)
        } else {
            Streak(gameId = gameId)
        }
    }

    fun saveStreak(streak: Streak) {
        if (runEpochDay != null &&
            (runEpochDay != daySource.epochDay() || streak.lastCompletedEpochDay != runEpochDay)) return
        val activeDayKey = "active_daily_day_${streak.gameId}"
        if (sharedPreferences.contains(activeDayKey) &&
            sharedPreferences.getLong(activeDayKey, Long.MIN_VALUE) != daySource.epochDay()) return
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
        return getStreak(gameId)
    }
}

