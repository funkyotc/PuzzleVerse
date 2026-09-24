package com.funkyotc.puzzleverse.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.gson.Gson
import com.funkyotc.puzzleverse.core.SystemUtcDaySource
import com.funkyotc.puzzleverse.core.UtcDaySource

data class SaveStateMetadata(
    val gameId: String,
    val mode: String = "standard",
    val puzzleId: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val jsonState: String? = null,
    val challengeEpochDay: Long? = null
)

val LocalSaveStateRepository = staticCompositionLocalOf<SaveStateRepository> {
    error("No SaveStateRepository provided")
}

class SaveStateRepository(
    context: Context? = null,
    private val daySource: UtcDaySource = SystemUtcDaySource,
    sharedPreferences: SharedPreferences? = null
) {
    private val prefs: SharedPreferences = sharedPreferences ?: context?.getSharedPreferences("PuzzleVerseSaveStates", Context.MODE_PRIVATE)
        ?: InMemorySharedPreferences()
    private val gson = Gson()

    private val _savedGameIds = MutableStateFlow(loadSavedGameIds())
    val savedGameIds: StateFlow<Set<String>> = _savedGameIds.asStateFlow()

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "active_save_games" || key?.startsWith("save_") == true) {
            _savedGameIds.value = loadSavedGameIds()
        }
    }

    init {
        expireDailySaveStates()
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    fun expireDailySaveStates() {
        for (gameId in loadSavedGameIds()) {
            val raw = prefs.getString("save_$gameId", null) ?: continue
            val saved = runCatching { gson.fromJson(raw, SaveStateMetadata::class.java) }.getOrNull()
            if (saved?.mode == "daily" && saved.challengeEpochDay != daySource.epochDay()) {
                discardDailyMetadata(gameId)
            }
        }
    }

    private fun loadSavedGameIds(): Set<String> {
        return try {
            prefs.getStringSet("active_save_games", emptySet()) ?: emptySet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    fun hasSaveState(gameId: String): Boolean {
        return _savedGameIds.value.contains(gameId) || prefs.contains("save_$gameId")
    }

    fun getSaveState(gameId: String): SaveStateMetadata? {
        if (!hasSaveState(gameId)) return null
        val rawJson = prefs.getString("save_$gameId", null) ?: return null
        return try {
            val state = gson.fromJson(rawJson, SaveStateMetadata::class.java)
            if (state?.mode == "daily" && state.challengeEpochDay != daySource.epochDay()) {
                discardDailyMetadata(gameId)
            } else state
        } catch (e: Exception) {
            null
        }
    }

    private fun discardDailyMetadata(gameId: String): SaveStateMetadata? {
        val backup = prefs.getString("standard_save_$gameId", null)
        val standard = runCatching { gson.fromJson(backup, SaveStateMetadata::class.java) }.getOrNull()
        if (backup != null && standard?.mode == "standard") {
            prefs.edit {
                putString("save_$gameId", backup)
                remove("standard_save_$gameId")
            }
            return standard
        }
        clearSaveState(gameId)
        return null
    }

    fun saveGameState(gameId: String, mode: String = "standard", puzzleId: String? = null, jsonState: String? = null) {
        val previous = getSaveState(gameId)
        val standardBackup = if (mode == "daily" && previous?.mode == "standard") {
            prefs.getString("save_$gameId", null)
        } else null
        val metadata = SaveStateMetadata(
            gameId = gameId,
            mode = mode,
            puzzleId = puzzleId,
            timestamp = System.currentTimeMillis(),
            jsonState = jsonState,
            challengeEpochDay = if (mode == "daily") daySource.epochDay() else null
        )
        val jsonStr = gson.toJson(metadata)

        val currentSet = loadSavedGameIds().toMutableSet()
        currentSet.add(gameId)

        prefs.edit {
            if (standardBackup != null) putString("standard_save_$gameId", standardBackup)
            if (mode != "daily") remove("standard_save_$gameId")
            putString("save_$gameId", jsonStr)
            putStringSet("active_save_games", currentSet)
        }
        _savedGameIds.value = currentSet
    }

    fun clearSaveState(gameId: String, onlyMode: String? = null) {
        if (onlyMode == "daily") {
            val raw = prefs.getString("save_$gameId", null)
            val current = runCatching { gson.fromJson(raw, SaveStateMetadata::class.java) }.getOrNull()
            if (current?.mode == "daily") discardDailyMetadata(gameId)
            return
        }
        val currentSet = loadSavedGameIds().toMutableSet()
        currentSet.remove(gameId)

        prefs.edit {
            remove("save_$gameId")
            remove("standard_save_$gameId")
            putStringSet("active_save_games", currentSet)
        }
        _savedGameIds.value = currentSet
    }

    fun clearAllSaveStates() {
        val currentSet = loadSavedGameIds()
        prefs.edit {
            for (gameId in currentSet) {
                remove("save_$gameId")
                remove("standard_save_$gameId")
            }
            remove("active_save_games")
        }
        _savedGameIds.value = emptySet()
    }
}
