import java.util.Random

data class Point(val r: Int, val c: Int)
data class ColorDot(val colorId: Int, val start: Point, val end: Point)

val DIRECTIONS = arrayOf(
    intArrayOf(-1, 0), intArrayOf(1, 0),
    intArrayOf(0, -1), intArrayOf(0, 1)
)

fun generateRandomFilledGrid(size: Int, numColors: Int, random: Random): List<ColorDot>? {
    val grid = Array(size) { IntArray(size) { 0 } }
    val paths = Array(numColors + 1) { mutableListOf<Point>() }
    
    val emptyCells = mutableListOf<Point>()
    for (r in 0 until size) {
        for (c in 0 until size) {
            emptyCells.add(Point(r, c))
        }
    }
    emptyCells.shuffle(random)
    
    for (i in 1..numColors) {
        if (emptyCells.isEmpty()) return null
        val start = emptyCells.removeAt(emptyCells.size - 1)
        paths[i].add(start)
        grid[start.r][start.c] = i
    }
    
    var changed = true
    while (changed) {
        changed = false
        val colorOrder = (1..numColors).shuffled(random)
        for (i in colorOrder) {
            val path = paths[i]
            val head = path.last()
            val validDirs = DIRECTIONS.filter { dir ->
                val nr = head.r + dir[0]
                val nc = head.c + dir[1]
                nr in 0 until size && nc in 0 until size && grid[nr][nc] == 0
            }
            if (validDirs.isNotEmpty()) {
                val dir = validDirs[random.nextInt(validDirs.size)]
                val next = Point(head.r + dir[0], head.c + dir[1])
                grid[next.r][next.c] = i
                path.add(next)
                emptyCells.remove(next)
                changed = true
            }
        }
    }
    
    if (emptyCells.isNotEmpty()) return null
    
    val dots = mutableListOf<ColorDot>()
    for (i in 1..numColors) {
        val p = paths[i]
        if (p.size < 2) return null
        dots.add(ColorDot(i, p.first(), p.last()))
    }
    return dots
}

fun main() {
    val random = Random()
    var attempts = 0
    while (true) {
        attempts++
        val dots = generateRandomFilledGrid(6, 5, random)
        if (dots != null) {
            println("Success on attempt $attempts")
            break
        }
        if (attempts > 100000) {
            println("Failed after 100000 attempts")
            break
        }
    }
}
