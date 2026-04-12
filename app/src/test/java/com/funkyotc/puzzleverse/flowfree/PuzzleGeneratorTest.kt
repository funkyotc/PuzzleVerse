package com.funkyotc.puzzleverse.flowfree

import com.funkyotc.puzzleverse.flowfree.data.ColorDot
import com.funkyotc.puzzleverse.flowfree.data.FlowDifficulty
import com.funkyotc.puzzleverse.flowfree.data.Point
import org.junit.Test
import java.io.File
import kotlin.random.Random

class PuzzleGeneratorTest {

    private val DIRECTIONS = arrayOf(
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(0, -1), intArrayOf(0, 1)
    )

    @Test
    fun generatePuzzlesOffline() {
        val outputDir = File("src/main/java/com/funkyotc/puzzleverse/flowfree/data")
        if (!outputDir.exists()) outputDir.mkdirs()

        val outputFile = File(outputDir, "FlowFreePregenerated.kt")
        
        val random = Random(42) // Fixed seed for reproducibility

        val sb = StringBuilder()
        sb.append("package com.funkyotc.puzzleverse.flowfree.data\n\n")
        sb.append("object FlowFreePregenerated {\n")
        sb.append("    val PUZZLES = mapOf(\n")

        for (difficulty in FlowDifficulty.values()) {
            println("Generating puzzles for ${difficulty.name}...")
            sb.append("        FlowDifficulty.${difficulty.name} to listOf(\n")
            
            val count = 3 // Let's generate 3 valid bases per difficulty for speed. You can increase this!
            var generated = 0
            
            while (generated < count) {
                val size = difficulty.gridSize
                val numColors = difficulty.numColors
                
                val solution = generateSolution(size, numColors, random)
                if (solution == null) continue
                
                val dots = extractDots(solution, size)
                
                if (hasUniqueSolution(dots, size)) {
                    println("Found unique puzzle $generated for ${difficulty.name}")
                    sb.append("            listOf(\n")
                    val dotStrings = dots.map { dot ->
                        "                ColorDot(${dot.colorId}, Point(${dot.start.r}, ${dot.start.c}), Point(${dot.end.r}, ${dot.end.c}))"
                    }
                    sb.append(dotStrings.joinToString(",\n"))
                    sb.append("\n            )")
                    if (generated < count - 1) sb.append(",")
                    sb.append("\n")
                    generated++
                }
            }
            
            sb.append("        )")
            if (difficulty != FlowDifficulty.values().last()) sb.append(",")
            sb.append("\n")
        }

        sb.append("    )\n")
        sb.append("}\n")

        outputFile.writeText(sb.toString())
        println("Puzzles successfully written to ${outputFile.absolutePath}")
    }

    private fun generateSolution(size: Int, numColors: Int, random: Random): Array<IntArray>? {
        val grid = Array(size) { IntArray(size) }
        val totalCells = size * size

        val paths = mutableListOf<MutableList<Point>>()
        val allCells = mutableListOf<Point>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                allCells.add(Point(r, c))
            }
        }
        allCells.shuffle(random)

        if (allCells.size < numColors) return null

        for (i in 0 until numColors) {
            val seed = allCells[i]
            grid[seed.r][seed.c] = i + 1
            paths.add(mutableListOf(seed))
        }

        var filledCells = numColors
        var stuckCount = 0
        val maxStuck = totalCells * 10

        while (filledCells < totalCells && stuckCount < maxStuck) {
            val pathIndices = (0 until numColors).shuffled(random)
            var grew = false

            for (pi in pathIndices) {
                val path = paths[pi]
                val colorId = pi + 1

                val ends = listOf(path.first(), path.last())
                val shuffledEnds = if (random.nextBoolean()) ends else ends.reversed()

                for (end in shuffledEnds) {
                    val neighbors = getEmptyNeighbors(end, grid, size)
                    if (neighbors.isNotEmpty()) {
                        val next = neighbors[random.nextInt(neighbors.size)]
                        grid[next.r][next.c] = colorId
                        if (end == path.first()) {
                            path.add(0, next)
                        } else {
                            path.add(next)
                        }
                        filledCells++
                        grew = true
                        break
                    }
                }
                if (grew) break
            }

            if (!grew) {
                stuckCount++
                if (!tryUnstick(grid, paths, size, numColors, random)) {
                    stuckCount += 10
                }
            } else {
                stuckCount = 0
            }
        }

        return if (filledCells == totalCells) grid else null
    }

    private fun tryUnstick(grid: Array<IntArray>, paths: MutableList<MutableList<Point>>, size: Int, numColors: Int, random: Random): Boolean {
        // Dummy unstick to save complexity; just fails fast and retries from zero.
        return false
    }

    private fun getEmptyNeighbors(p: Point, grid: Array<IntArray>, size: Int): List<Point> {
        val result = mutableListOf<Point>()
        for (dir in DIRECTIONS) {
            val nr = p.r + dir[0]
            val nc = p.c + dir[1]
            if (nr in 0 until size && nc in 0 until size && grid[nr][nc] == 0) {
                result.add(Point(nr, nc))
            }
        }
        return result
    }

    private fun extractDots(grid: Array<IntArray>, size: Int): List<ColorDot> {
        val colorPaths = mutableMapOf<Int, MutableList<Point>>()

        for (r in 0 until size) {
            for (c in 0 until size) {
                val colorId = grid[r][c]
                if (colorId > 0) {
                    colorPaths.getOrPut(colorId) { mutableListOf() }.add(Point(r, c))
                }
            }
        }

        val dots = mutableListOf<ColorDot>()
        for ((colorId, cells) in colorPaths) {
            val endpoints = mutableListOf<Point>()
            for (cell in cells) {
                var sameColorNeighbors = 0
                for (dir in DIRECTIONS) {
                    val nr = cell.r + dir[0]
                    val nc = cell.c + dir[1]
                    if (nr in 0 until size && nc in 0 until size && grid[nr][nc] == colorId) {
                        sameColorNeighbors++
                    }
                }
                if (sameColorNeighbors <= 1) {
                    endpoints.add(cell)
                }
            }

            if (endpoints.size >= 2) {
                dots.add(ColorDot(colorId, endpoints.first(), endpoints.last()))
            }
        }

        return dots
    }

    private fun hasUniqueSolution(dots: List<ColorDot>, size: Int): Boolean {
        if (dots.isEmpty()) return false
        val solver = FlowFreeSolver(dots, size)
        
        // Timeout mechanism: since 8x8 can occasionally hang, run it in a thread and timeout.
        var unique = false
        val thread = Thread {
            unique = solver.countSolutions(maxCount = 2) == 1
        }
        thread.start()
        thread.join(2000) // 2 seconds max
        if (thread.isAlive) {
            thread.interrupt()
            return false
        }
        return unique
    }

    private inner class FlowFreeSolver(
        private val dots: List<ColorDot>,
        private val size: Int
    ) {
        private val grid = Array(size) { IntArray(size) }
        private var solutionCount = 0
        private var maxSolutions = 2

        fun countSolutions(maxCount: Int): Int {
            solutionCount = 0
            maxSolutions = maxCount

            for (dot in dots) {
                grid[dot.start.r][dot.start.c] = dot.colorId
                grid[dot.end.r][dot.end.c] = dot.colorId
            }

            val partialPaths = dots.map { dot ->
                dot.colorId to mutableListOf(dot.start)
            }.toMap().toMutableMap()

            solve(partialPaths)

            return solutionCount
        }

        private fun solve(partialPaths: MutableMap<Int, MutableList<Point>>) {
            if (Thread.currentThread().isInterrupted) return
            if (solutionCount >= maxSolutions) return

            var bestColor = -1
            var bestOptions = Int.MAX_VALUE

            for (dot in dots) {
                val path = partialPaths[dot.colorId] ?: continue
                val head = path.last()

                if (head == dot.end && path.size > 1) continue

                val target = dot.end
                val options = countValidMoves(head, dot.colorId, target)
                if (options == 0) return
                if (options < bestOptions) {
                    bestOptions = options
                    bestColor = dot.colorId
                }
            }

            if (bestColor == -1) {
                var filled = 0
                for (r in 0 until size) {
                    for (c in 0 until size) {
                        if (grid[r][c] != 0) filled++
                    }
                }
                if (filled == size * size) {
                    solutionCount++
                }
                return
            }

            val dot = dots.find { it.colorId == bestColor }!!
            val path = partialPaths[bestColor]!!
            val head = path.last()

            val moves = getValidMoves(head, bestColor, dot.end)
            for (move in moves) {
                grid[move.r][move.c] = bestColor
                path.add(move)

                if (!hasIsolatedEmptyCells()) {
                    solve(partialPaths)
                }

                path.removeAt(path.size - 1)
                if (move != dot.start && move != dot.end) {
                    grid[move.r][move.c] = 0
                }

                if (solutionCount >= maxSolutions) return
            }
        }

        private fun countValidMoves(from: Point, colorId: Int, target: Point): Int {
            var count = 0
            for (dir in DIRECTIONS) {
                val nr = from.r + dir[0]
                val nc = from.c + dir[1]
                if (nr in 0 until size && nc in 0 until size) {
                    val p = Point(nr, nc)
                    if (p == target && grid[nr][nc] == colorId) {
                        count++
                    } else if (grid[nr][nc] == 0) {
                        count++
                    }
                }
            }
            return count
        }

        private fun getValidMoves(from: Point, colorId: Int, target: Point): List<Point> {
            val moves = mutableListOf<Point>()
            for (dir in DIRECTIONS) {
                val nr = from.r + dir[0]
                val nc = from.c + dir[1]
                if (nr in 0 until size && nc in 0 until size) {
                    val p = Point(nr, nc)
                    if (p == target && grid[nr][nc] == colorId) {
                        moves.add(p)
                    } else if (grid[nr][nc] == 0) {
                        moves.add(p)
                    }
                }
            }
            return moves
        }

        private fun hasIsolatedEmptyCells(): Boolean {
            val emptyCells = mutableSetOf<Point>()
            for (r in 0 until size) {
                for (c in 0 until size) {
                    if (grid[r][c] == 0) {
                        emptyCells.add(Point(r, c))
                    }
                }
            }

            if (emptyCells.isEmpty()) return false

            val start = emptyCells.first()
            val visited = mutableSetOf(start)
            val queue = ArrayDeque<Point>()
            queue.add(start)

            while (queue.isNotEmpty()) {
                val curr = queue.removeFirst()
                for (dir in DIRECTIONS) {
                    val nr = curr.r + dir[0]
                    val nc = curr.c + dir[1]
                    val p = Point(nr, nc)
                    if (p in emptyCells && p !in visited) {
                        visited.add(p)
                        queue.add(p)
                    }
                }
            }

            return visited.size != emptyCells.size
        }
    }
}
