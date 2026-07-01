package com.funkyotc.puzzleverse.cubeshooter.data

const val COLOR_CYAN = 0
const val COLOR_MAGENTA = 1
const val COLOR_YELLOW = 2
const val COLOR_GREEN = 3

val CUBE_SHOOTER_PALETTE: List<Int> = listOf(COLOR_CYAN, COLOR_MAGENTA, COLOR_YELLOW, COLOR_GREEN)

data class Tank(val color: Int, val ammo: Int)

data class TrackTank(val tank: Tank, val position: Float)

data class Level(
    val id: String,
    val difficulty: String,
    val cols: Int,
    val rows: Int,
    val grid: List<List<Int?>>,
    val tray: List<Tank>
)

data class CubeShooterState(
    val level: Level,
    val tray: List<Tank>,
    val track: List<TrackTank>,
    val cubesRemaining: Int,
    val score: Int = 0,
    val isWon: Boolean = false,
    val isGameOver: Boolean = false
)
