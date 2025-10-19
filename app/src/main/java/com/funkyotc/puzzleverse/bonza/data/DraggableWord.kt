package com.funkyotc.puzzleverse.bonza.data

data class DraggableWord(
    val wordFragment: WordFragment,
    val id: String = wordFragment.text,
    var offsetX: Float = 0f,
    var offsetY: Float = 0f,
    var groupId: String = id
)
