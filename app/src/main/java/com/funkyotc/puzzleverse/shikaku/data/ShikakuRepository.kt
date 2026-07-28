package com.funkyotc.puzzleverse.shikaku.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences

class ShikakuRepository(
    context: Context? = null,
    sharedPreferences: SharedPreferences? = null
) {
    private val sharedPreferences: SharedPreferences = sharedPreferences ?: context?.getSharedPreferences("ShikakuPrefs", Context.MODE_PRIVATE) ?: InMemorySharedPreferences()

    private val gson = Gson()
    private val saveStateRepo = com.funkyotc.puzzleverse.core.data.SaveStateRepository(context, sharedPreferences)

    fun saveBoard(board: ShikakuBoard, key: String = board.puzzleId) {
        try {
            val json = gson.toJson(board)
            sharedPreferences.edit { putString("savedBoard_$key", json) }
            val hasMoves = board.playerRectangles.isNotEmpty() || board.cells.any { it.rectangleId != null }
            if (hasMoves) {
                val mode = if (board.isDaily) "daily" else "standard"
                saveStateRepo.saveGameState("shikaku", mode = mode, puzzleId = board.puzzleId)
            } else {
                saveStateRepo.clearSaveState("shikaku")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadBoard(puzzleId: String): ShikakuBoard? {
        return try {
            val json = sharedPreferences.getString("savedBoard_$puzzleId", null)
            if (json != null) {
                gson.fromJson(json, ShikakuBoard::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            sharedPreferences.edit { remove("savedBoard_$puzzleId") }
            null
        }
    }

    fun clearBoard(puzzleId: String) {
        sharedPreferences.edit { remove("savedBoard_$puzzleId") }
        saveStateRepo.clearSaveState("shikaku")
    }
}
