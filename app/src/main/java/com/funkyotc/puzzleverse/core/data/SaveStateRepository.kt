package com.funkyotc.puzzleverse.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.gson.Gson

data class SaveStateMetadata(
    val gameId: String,
    val mode: String = "standard",
    val puzzleId: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val jsonState: String? = null
)

val LocalSaveStateRepository = staticCompositionLocalOf<SaveStateRepository> {
    error("No SaveStateRepository provided")
}

class SaveStateRepository(
    context: Context? = null
) {
    private val prefs: SharedPreferences = context?.getSharedPreferences("PuzzleVerseSaveStates", Context.MODE_PRIVATE)
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
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
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
            gson.fromJson(rawJson, SaveStateMetadata::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun saveGameState(gameId: String, mode: String = "standard", puzzleId: String? = null, jsonState: String? = null) {
        val metadata = SaveStateMetadata(
            gameId = gameId,
            mode = mode,
            puzzleId = puzzleId,
            timestamp = System.currentTimeMillis(),
            jsonState = jsonState
        )
        val jsonStr = gson.toJson(metadata)

        val currentSet = loadSavedGameIds().toMutableSet()
        currentSet.add(gameId)

        prefs.edit {
            putString("save_$gameId", jsonStr)
            putStringSet("active_save_games", currentSet)
        }
        _savedGameIds.value = currentSet
    }

    fun clearSaveState(gameId: String) {
        val currentSet = loadSavedGameIds().toMutableSet()
        currentSet.remove(gameId)

        prefs.edit {
            remove("save_$gameId")
            putStringSet("active_save_games", currentSet)
        }
        _savedGameIds.value = currentSet
    }

    fun clearAllSaveStates() {
        val currentSet = loadSavedGameIds()
        prefs.edit {
            for (gameId in currentSet) {
                remove("save_$gameId")
            }
            remove("active_save_games")
        }
        _savedGameIds.value = emptySet()
    }
}
