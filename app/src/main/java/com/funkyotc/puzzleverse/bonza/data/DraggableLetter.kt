package com.funkyotc.puzzleverse.bonza.data

import java.util.UUID

data class DraggableLetter(
    val id: String = UUID.randomUUID().toString(),
    val letter: Char,
    val wordId: String,
    val correctX: Int,
    val correctY: Int,
    var currentX: Float,
    var currentY: Float,
    var isPlaced: Boolean = false
)
