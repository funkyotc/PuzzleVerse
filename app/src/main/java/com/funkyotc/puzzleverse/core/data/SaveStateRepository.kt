package com.funkyotc.puzzleverse.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject

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
    context: Context? = null,
    prefs: SharedPreferences? = null
) {
    private val prefs: SharedPreferences = prefs
        ?: context?.getSharedPreferences("PuzzleVerseSaveStates", Context.MODE_PRIVATE)
        ?: InMemorySharedPreferences()

    private val _savedGameIds = MutableStateFlow(loadSavedGameIds())
    val savedGameIds: StateFlow<Set<String>> = _savedGameIds.asStateFlow()

    private fun loadSavedGameIds(): Set<String> {
        return try {
            prefs.getStringSet("active_save_games", emptySet()) ?: emptySet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    fun hasSaveState(gameId: String): Boolean {
        return _savedGameIds.value.contains(gameId)
    }

    fun getSaveState(gameId: String): SaveStateMetadata? {
        if (!hasSaveState(gameId)) return null
        val rawJson = prefs.getString("save_$gameId", null) ?: return null
        return try {
            val obj = JSONObject(rawJson)
            SaveStateMetadata(
                gameId = obj.getString("gameId"),
                mode = obj.optString("mode", "standard"),
                puzzleId = if (obj.has("puzzleId") && !obj.isNull("puzzleId")) obj.getString("puzzleId") else null,
                timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                jsonState = if (obj.has("jsonState") && !obj.isNull("jsonState")) obj.getString("jsonState") else null
            )
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
        val obj = JSONObject().apply {
            put("gameId", metadata.gameId)
            put("mode", metadata.mode)
            put("puzzleId", metadata.puzzleId)
            put("timestamp", metadata.timestamp)
            put("jsonState", metadata.jsonState)
        }

        val currentSet = loadSavedGameIds().toMutableSet()
        currentSet.add(gameId)

        prefs.edit {
            putString("save_$gameId", obj.toString())
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
