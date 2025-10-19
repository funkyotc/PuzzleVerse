package com.funkyotc.puzzleverse.bonza.data

import java.util.UUID

data class DraggableWord(
    val id: String = UUID.randomUUID().toString(),
    val bonzaWord: BonzaWord,
    var offsetX: Float = 0f,
    var offsetY: Float = 0f,
    var groupId: String = id // Initially, each word is in its own group
)
