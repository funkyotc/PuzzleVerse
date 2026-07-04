package com.funkyotc.puzzleverse.core

import java.util.Calendar
import java.util.TimeZone

fun todayEpochDay(): Long {
    return Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 86400000L
}
