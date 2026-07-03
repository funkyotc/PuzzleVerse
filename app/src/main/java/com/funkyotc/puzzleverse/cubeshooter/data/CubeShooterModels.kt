package com.funkyotc.puzzleverse.cubeshooter.data

const val COLOR_CYAN = 0
const val COLOR_MAGENTA = 1
const val COLOR_YELLOW = 2
const val COLOR_GREEN = 3
const val COLOR_ORANGE = 4
const val COLOR_PURPLE = 5
const val COLOR_RED = 6
const val COLOR_BLUE = 7
const val COLOR_TEAL = 8
const val COLOR_PINK = 9
const val COLOR_INDIGO = 10
const val COLOR_LIME = 11

val CUBE_SHOOTER_PALETTE: List<Int> = listOf(
    COLOR_CYAN, COLOR_MAGENTA, COLOR_YELLOW, COLOR_GREEN, COLOR_ORANGE,
    COLOR_PURPLE, COLOR_RED, COLOR_BLUE, COLOR_TEAL, COLOR_PINK,
    COLOR_INDIGO, COLOR_LIME
)

data class Tank(val color: Int, val ammo: Int)

data class TrackTank(val tank: Tank, val position: Float)

data class TankTransition(val id: String, val tank: Tank, val fromCol: Int? = null, val fromTraySlot: Int? = null)

data class TankReturn(val id: String, val tank: Tank)

data class Level(
    val id: String,
    val difficulty: String,
    val cols: Int,
    val rows: Int,
    val grid: List<List<Int?>>,
    val tray: List<Tank>
)

data class Projectile(
    val id: String,
    val startCol: Float,
    val startRow: Float,
    val endCol: Float,
    val endRow: Float,
    val color: Int,
    val progress: Float
)

data class FadingCube(
    val row: Int,
    val col: Int,
    val color: Int,
    val progress: Float
)

data class CubeShooterState(
    val level: Level,
    val sourceColumns: List<List<Tank>>,
    val storageTray: List<Tank>,
    val track: List<TrackTank>,
    val cubesRemaining: Int,
    val score: Int = 0,
    val isWon: Boolean = false,
    val isGameOver: Boolean = false,
    val projectiles: List<Projectile> = emptyList(),
    val fadingCubes: List<FadingCube> = emptyList(),
    val transitions: List<TankTransition> = emptyList(),
    val returns: List<TankReturn> = emptyList()
)
