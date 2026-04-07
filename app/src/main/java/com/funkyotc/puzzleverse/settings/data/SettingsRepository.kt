package com.funkyotc.puzzleverse.settings.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)

    private val _activeTheme = MutableStateFlow(sharedPreferences.getString("active_theme", "default") ?: "default")
    val activeTheme: Flow<String> = _activeTheme

    private val _unlockedThemes = MutableStateFlow(sharedPreferences.getStringSet("unlocked_themes", setOf("default", "dark", "light")) ?: setOf("default", "dark", "light"))
    val unlockedThemes: Flow<Set<String>> = _unlockedThemes

    private val _totalWins = MutableStateFlow(sharedPreferences.getInt("total_wins", 0))
    val totalWins: Flow<Int> = _totalWins

    fun setActiveTheme(theme: String) {
        if (_unlockedThemes.value.contains(theme)) {
            sharedPreferences.edit().putString("active_theme", theme).apply()
            _activeTheme.value = theme
        }
    }

    fun addWin() {
        val newWins = _totalWins.value + 1
        sharedPreferences.edit().putInt("total_wins", newWins).apply()
        _totalWins.value = newWins

        // Unlock logic
        val newUnlocks = _unlockedThemes.value.toMutableSet()
        if (newWins >= 5) newUnlocks.add("ocean")
        if (newWins >= 10) newUnlocks.add("forest")
        if (newWins >= 20) newUnlocks.add("sunset")
        if (newWins >= 50) newUnlocks.add("cyberpunk")

        if (newUnlocks.size > _unlockedThemes.value.size) {
            sharedPreferences.edit().putStringSet("unlocked_themes", newUnlocks).apply()
            _unlockedThemes.value = newUnlocks
        }
    }
}