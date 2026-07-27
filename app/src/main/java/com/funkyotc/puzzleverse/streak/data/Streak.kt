package com.funkyotc.puzzleverse.streak.data

// Using Long for epochDay to be Gson-friendly
data class Streak(
    val gameId: String,
    val count: Int = 0,
    val lastCompletedEpochDay: Long? = null
) {
    fun isCompletedToday(today: Long = com.funkyotc.puzzleverse.core.todayEpochDay()): Boolean {
        return lastCompletedEpochDay == today
    }
}

