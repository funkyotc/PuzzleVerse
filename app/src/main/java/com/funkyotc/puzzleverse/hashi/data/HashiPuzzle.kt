package com.funkyotc.puzzleverse.hashi.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class Island(
    val x: Int,
    val y: Int,
    val requiredBridges: Int
)

data class HashiPuzzle(
    override val id: String,
    val width: Int,
    val height: Int,
    val islands: List<Island>,
    override val difficulty: String = "Easy",
    override val label: String = "Puzzle",
    override val subtitle: String = "${width}x${height}"
) : BrowseablePuzzle
