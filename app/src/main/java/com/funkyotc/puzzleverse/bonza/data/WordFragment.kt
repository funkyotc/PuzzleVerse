package com.funkyotc.puzzleverse.bonza.data

import androidx.compose.ui.geometry.Offset

data class WordFragment(
    val id: Int,
    val text: String,
    val initialPosition: Offset,
    var currentPosition: Offset,
    val solvedPosition: Offset? = null,
    val direction: ConnectionDirection = ConnectionDirection.HORIZONTAL,
    var groupId: Int = id
)
