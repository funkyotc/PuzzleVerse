package com.funkyotc.puzzleverse.core.data

interface BrowseablePuzzle {
    val id: String
    val difficulty: String
    val label: String
    val subtitle: String

    val shortLabel: String
        get() {
            val match = Regex("""\d+$""").find(label)
            return match?.value?.trimStart('0') ?: label
        }
}
