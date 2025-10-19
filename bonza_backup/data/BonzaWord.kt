package com.funkyotc.puzzleverse.bonza.data

import android.graphics.Rect

data class BonzaWord(
    val word: String,
    val letters: List<Char>,
    val x: Int,
    val y: Int,
    val isHorizontal: Boolean
) {
    fun getRect(): Rect {
        return if (isHorizontal) {
            Rect(x, y, x + letters.size, y + 1)
        } else {
            Rect(x, y, x + 1, y + letters.size)
        }
    }
}
