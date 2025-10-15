package com.funkyotc.puzzleverse.bonza.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.funkyotc.puzzleverse.bonza.data.BonzaWord
import java.util.UUID

data class DraggableWord(
    val bonzaWord: BonzaWord,
    val id: String = UUID.randomUUID().toString(),
    var offsetX: Dp = 0.dp,
    var offsetY: Dp = 0.dp,
    var groupId: String = id
)
