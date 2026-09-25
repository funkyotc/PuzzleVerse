package com.funkyotc.puzzleverse.pullpin.data

const val WORLD_W = 400f
const val WORLD_H = 700f

/** Shared portrait-board geometry for rescue rendering and physics. */
data class WallSegment(val x: Float, val y: Float, val w: Float, val h: Float, val angle: Float = 0f)
