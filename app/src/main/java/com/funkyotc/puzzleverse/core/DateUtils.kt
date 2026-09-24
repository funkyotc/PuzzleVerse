package com.funkyotc.puzzleverse.core

import java.util.Calendar
import java.util.TimeZone

fun todayEpochDay(): Long {
    return Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 86400000L
}

fun interface UtcDaySource {
    fun epochDay(): Long
}

val SystemUtcDaySource = UtcDaySource { todayEpochDay() }

/** Reports one replacement route when the UTC day changes during an open Daily run. */
class DailyRolloverTracker(private val daySource: UtcDaySource) {
    private var observedDay = daySource.epochDay()

    fun routeToReload(gameId: String?, mode: String?): String? {
        val currentDay = daySource.epochDay()
        if (currentDay == observedDay) return null
        observedDay = currentDay
        return if (mode == "daily" && gameId != null) "game/$gameId/daily" else null
    }
}

/** A stable rotation that cannot repeat on adjacent days when the pool has at least two items. */
fun dailyIndex(epochDay: Long, count: Int): Int {
    require(count > 0) { "Daily puzzle pool must not be empty" }
    if (count == 1) return 0
    fun gcd(a: Int, b: Int): Int {
        var x = a
        var y = b
        while (y != 0) {
            val remainder = x % y
            x = y
            y = remainder
        }
        return x
    }
    var step = kotlin.math.sqrt(count.toDouble()).toInt().coerceAtLeast(1)
    while (gcd(step, count) != 1) step++
    val day = Math.floorMod(epochDay, count.toLong()).toInt()
    return ((day.toLong() * step + count / 3) % count).toInt()
}
