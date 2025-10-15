package com.funkyotc.puzzleverse.constellations.data

data class Star(val id: Int, val x: Float, val y: Float)

data class Connection(val from: Int, val to: Int)

data class ConstellationsPuzzle(
    val stars: List<Star>,
    val connections: List<Connection>
)
