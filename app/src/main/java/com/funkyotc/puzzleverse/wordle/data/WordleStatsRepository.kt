package com.funkyotc.puzzleverse.wordle.data

import android.content.Context
import android.content.SharedPreferences

class WordleStatsRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("wordle_stats", Context.MODE_PRIVATE)

    fun getGamesPlayed(): Int = prefs.getInt("games_played", 0)
    fun getGamesWon(): Int = prefs.getInt("games_won", 0)
    fun getCurrentStreak(): Int = prefs.getInt("current_streak", 0)
    fun getMaxStreak(): Int = prefs.getInt("max_streak", 0)
    
    fun getGuessDistribution(): Map<Int, Int> {
        return (1..6).associateWith { guessNum ->
            prefs.getInt("guess_dist_$guessNum", 0)
        }
    }

    fun recordResult(won: Boolean, guesses: Int) {
        val gamesPlayed = getGamesPlayed() + 1
        val gamesWon = if (won) getGamesWon() + 1 else getGamesWon()
        
        val currentStreak = if (won) getCurrentStreak() + 1 else 0
        val maxStreak = maxOf(getMaxStreak(), currentStreak)

        val editor = prefs.edit()
            .putInt("games_played", gamesPlayed)
            .putInt("games_won", gamesWon)
            .putInt("current_streak", currentStreak)
            .putInt("max_streak", maxStreak)

        if (won && guesses in 1..6) {
            val count = prefs.getInt("guess_dist_$guesses", 0)
            editor.putInt("guess_dist_$guesses", count + 1)
        }
        
        editor.apply()
    }
}
