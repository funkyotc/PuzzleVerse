package com.funkyotc.puzzleverse.shapes.data

import android.content.Context
import androidx.core.content.edit
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle
import com.google.gson.Gson

data class PieceSaveState(
    val id: Int,
    val posX: Float,
    val posY: Float,
    val rotation: Float,
    val isFlipped: Boolean,
    val isLocked: Boolean
)

data class ShapesSaveState(
    val pieces: List<PieceSaveState>,
    val currentLevel: Int,
    val mode: String?,
    val puzzleId: String?
)

class ShapesRepository(context: Context) {

    private val prefs = context.getSharedPreferences("ShapesPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun save(puzzle: ShapesPuzzle, currentLevel: Int, mode: String?, puzzleId: String?) {
        val pieceStates = puzzle.pieces.map { p ->
            PieceSaveState(
                id = p.id,
                posX = p.position.x,
                posY = p.position.y,
                rotation = p.rotation,
                isFlipped = p.isFlipped,
                isLocked = p.isLocked
            )
        }
        val state = ShapesSaveState(pieceStates, currentLevel, mode, puzzleId)
        val key = saveKey(mode, puzzleId)
        prefs.edit { putString(key, gson.toJson(state)) }
    }

    fun loadPieces(mode: String?, puzzleId: String?): ShapesSaveState? {
        val key = saveKey(mode, puzzleId)
        val json = prefs.getString(key, null) ?: return null
        return try {
            gson.fromJson(json, ShapesSaveState::class.java)
        } catch (e: Exception) {
            prefs.edit { remove(key) }
            null
        }
    }

    fun clear(mode: String?, puzzleId: String?) {
        prefs.edit { remove(saveKey(mode, puzzleId)) }
    }

    private fun saveKey(mode: String?, puzzleId: String?): String {
        return "shapes_${mode ?: "standard"}_${puzzleId ?: "random"}"
    }
}
