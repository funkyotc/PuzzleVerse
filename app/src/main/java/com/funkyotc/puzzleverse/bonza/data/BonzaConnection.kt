package com.funkyotc.puzzleverse.bonza.data

/**
 * Represents a connection between two word fragments.
 */
data class BonzaConnection(
    val fragment1Id: Int,
    val fragment2Id: Int,
    val direction: ConnectionDirection
)
