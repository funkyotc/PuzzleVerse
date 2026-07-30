package com.funkyotc.puzzleverse.arrowescape.model

enum class LevelShape(val displayName: String) {
    SQUARE("Square"),
    CIRCLE("Circle"),
    TRIANGLE("Triangle"),
    HEXAGON("Hexagon"),
    PENTAGON("Pentagon"),
    STAR("Star"),
    DIAMOND("Diamond"),
    CROSS("Cross"),
    HEART("Heart");

    fun isCellInside(x: Int, y: Int, width: Int, height: Int): Boolean {
        if (x !in 0 until width || y !in 0 until height) return false
        if (this == SQUARE) return true

        val cx = (width - 1) / 2.0
        val cy = (height - 1) / 2.0
        val nx = (x - cx) / (width / 2.0)   // Normalized [-1, 1]
        val ny = (y - cy) / (height / 2.0)  // Normalized [-1, 1]

        return when (this) {
            SQUARE -> true
            CIRCLE -> (nx * nx + ny * ny) <= 0.95 * 0.95
            TRIANGLE -> ny in -0.95..0.95 && Math.abs(nx) <= (1.0 - (ny + 0.95) / 1.9)
            HEXAGON -> Math.abs(nx) <= 0.95 && (Math.abs(nx) * 0.5 + Math.abs(ny) * 0.866) <= 0.92
            PENTAGON -> {
                val angle = Math.atan2(ny, nx)
                val dist = Math.sqrt(nx * nx + ny * ny)
                val sector = (angle % (2.0 * Math.PI / 5.0) + 2.0 * Math.PI / 5.0) % (2.0 * Math.PI / 5.0) - Math.PI / 5.0
                val rMax = 0.92 * Math.cos(Math.PI / 5.0) / Math.cos(sector)
                dist <= rMax
            }
            STAR -> {
                val angle = Math.atan2(ny, nx) - Math.PI / 2.0
                val dist = Math.sqrt(nx * nx + ny * ny)
                val starMod = (angle % (2.0 * Math.PI / 5.0) + 2.0 * Math.PI / 5.0) % (2.0 * Math.PI / 5.0)
                val factor = if (starMod < Math.PI / 5.0) {
                    0.35 + 0.60 * (starMod / (Math.PI / 5.0))
                } else {
                    0.95 - 0.60 * ((starMod - Math.PI / 5.0) / (Math.PI / 5.0))
                }
                dist <= factor
            }
            DIAMOND -> Math.abs(nx) + Math.abs(ny) <= 0.95
            CROSS -> Math.abs(nx) <= 0.40 || Math.abs(ny) <= 0.40
            HEART -> {
                val hx = nx * 1.15
                val hy = -ny * 1.15 + 0.25
                val a = hx * hx + hy * hy - 0.75
                a * a * a - hx * hx * hy * hy * hy <= 0.0
            }
        }
    }
}
