package generators.flowfree

import java.io.File
import kotlin.math.abs
import kotlin.random.Random

data class Point(val r: Int, val c: Int)
data class ColorDot(val colorId: Int, val start: Point, val end: Point)

data class PregeneratedPuzzle(
    val id: String,
    val size: Int,
    val difficulty: String,
    val dots: List<ColorDot>
)

class FlowSolver(val size: Int, val dots: List<ColorDot>) {
    private val numColors = dots.size
    private val dotMap = HashMap<Point, Int>()
    private val dotPairs = HashMap<Int, Pair<Point, Point>>()
    private val neighborsMap = HashMap<Point, List<Point>>()
    private val colorSortedNbrs = HashMap<Int, HashMap<Point, List<Point>>>()

    private val directions = arrayOf(
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(0, -1), intArrayOf(0, 1)
    )

    init {
        for (d in dots) {
            dotMap[d.start] = d.colorId
            dotMap[d.end] = d.colorId
            dotPairs[d.colorId] = Pair(d.start, d.end)
        }

        for (r in 0 until size) {
            for (c in 0 until size) {
                val pt = Point(r, c)
                val nbrs = mutableListOf<Point>()
                for (d in directions) {
                    val nr = r + d[0]
                    val nc = c + d[1]
                    if (nr in 0 until size && nc in 0 until size) {
                        nbrs.add(Point(nr, nc))
                    }
                }
                neighborsMap[pt] = nbrs
            }
        }

        for ((cid, pair) in dotPairs) {
            val endPt = pair.second
            val mapForColor = HashMap<Point, List<Point>>()
            for (r in 0 until size) {
                for (c in 0 until size) {
                    val pt = Point(r, c)
                    val nbrs = neighborsMap[pt]!!.sortedBy { n ->
                        abs(n.r - endPt.r) + abs(n.c - endPt.c)
                    }
                    mapForColor[pt] = nbrs
                }
            }
            colorSortedNbrs[cid] = mapForColor
        }
    }

    fun countFullCoverageSolutions(maxSolutions: Int = 2, maxSteps: Int = 2000): Int {
        val grid = Array(size) { IntArray(size) { 0 } }
        var solutionsFound = 0
        val pathLengths = IntArray(numColors + 1)
        val colorOrder = dotPairs.keys.sortedBy { cid ->
            val p = dotPairs[cid]!!
            abs(p.first.r - p.second.r) + abs(p.first.c - p.second.c)
        }

        val heads = HashMap<Int, Point>()
        var stepCount = 0

        fun isDeadEnd(r: Int, c: Int, currCid: Int): Boolean {
            if (grid[r][c] != 0) return false
            val pt = Point(r, c)
            val isDot = dotMap.containsKey(pt)
            val currHead = heads[currCid]
            var avail = 0
            val nbrs = neighborsMap[pt] ?: return false
            for (n in nbrs) {
                if (grid[n.r][n.c] == 0 || n == currHead) {
                    avail++
                }
            }
            val minReq = if (isDot) 1 else 2
            return avail < minReq
        }

        fun checkLocalPrune(r: Int, c: Int, currCid: Int): Boolean {
            val pt = Point(r, c)
            val nbrs = neighborsMap[pt] ?: return false
            for (n in nbrs) {
                if (isDeadEnd(n.r, n.c, currCid)) return true
            }
            return false
        }

        fun backtrack(colorIdx: Int, currPos: Point) {
            stepCount++
            if (stepCount > maxSteps || solutionsFound >= maxSolutions) return

            val cid = colorOrder[colorIdx]
            val (_, endPt) = dotPairs[cid]!!

            if (currPos == endPt) {
                heads.remove(cid)
                if (colorIdx + 1 == colorOrder.size) {
                    val totalFilled = pathLengths.sum()
                    if (totalFilled == size * size) {
                        solutionsFound++
                    }
                } else {
                    val nextCid = colorOrder[colorIdx + 1]
                    val (nStart, _) = dotPairs[nextCid]!!
                    grid[nStart.r][nStart.c] = nextCid
                    pathLengths[nextCid] = 1
                    heads[nextCid] = nStart
                    backtrack(colorIdx + 1, nStart)
                    grid[nStart.r][nStart.c] = 0
                    pathLengths[nextCid] = 0
                    heads.remove(nextCid)
                }
                return
            }

            val nbrs = colorSortedNbrs[cid]!![currPos] ?: return

            for (nextPt in nbrs) {
                if (nextPt == endPt) {
                    grid[nextPt.r][nextPt.c] = cid
                    pathLengths[cid]++
                    heads[cid] = nextPt
                    backtrack(colorIdx, nextPt)
                    pathLengths[cid]--
                    grid[nextPt.r][nextPt.c] = 0
                    heads[cid] = currPos
                } else if (grid[nextPt.r][nextPt.c] == 0 && !dotMap.containsKey(nextPt)) {
                    grid[nextPt.r][nextPt.c] = cid
                    pathLengths[cid]++
                    heads[cid] = nextPt

                    if (!checkLocalPrune(nextPt.r, nextPt.c, cid)) {
                        backtrack(colorIdx, nextPt)
                    }

                    pathLengths[cid]--
                    grid[nextPt.r][nextPt.c] = 0
                    heads[cid] = currPos
                }
            }
        }

        val firstCid = colorOrder[0]
        val (fStart, _) = dotPairs[firstCid]!!
        grid[fStart.r][fStart.c] = firstCid
        pathLengths[firstCid] = 1
        heads[firstCid] = fStart
        backtrack(0, fStart)

        if (stepCount > maxSteps) return 0
        return solutionsFound
    }
}

fun main(args: Array<String> = emptyArray()) {
    val random = Random(999)

    fun generateSpiralPath(size: Int): List<Point> {
        val gridVisited = Array(size) { BooleanArray(size) { false } }
        val path = mutableListOf<Point>()
        var r = 0
        var c = 0
        var dr = 0
        var dc = 1
        for (i in 0 until (size * size)) {
            path.add(Point(r, c))
            gridVisited[r][c] = true
            val nr = r + dr
            val nc = c + dc
            if (nr in 0 until size && nc in 0 until size && !gridVisited[nr][nc]) {
                r = nr
                c = nc
            } else {
                val tmp = dr
                dr = dc
                dc = -tmp
                r += dr
                c += dc
            }
        }
        return path
    }

    fun generateSerpentinePath(size: Int, numColors: Int, rnd: Random): List<ColorDot>? {
        val path = if (rnd.nextBoolean()) {
            generateSpiralPath(size)
        } else {
            val p = mutableListOf<Point>()
            for (r in 0 until size) {
                if (r % 2 == 0) {
                    for (c in 0 until size) p.add(Point(r, c))
                } else {
                    for (c in (size - 1) downTo 0) p.add(Point(r, c))
                }
            }
            p
        }

        val total = path.size
        val minLen = 3

        val turns = mutableListOf<Int>()
        for (i in 1 until (total - 1)) {
            val dr1 = path[i].r - path[i - 1].r
            val dc1 = path[i].c - path[i - 1].c
            val dr2 = path[i + 1].r - path[i].r
            val dc2 = path[i + 1].c - path[i].c
            if (dr1 != dr2 || dc1 != dc2) {
                turns.add(i)
            }
        }

        val validTurns = turns.filter { it in minLen..(total - minLen) }
        val pool = if (validTurns.size >= numColors - 1) validTurns else (minLen..(total - minLen)).toList()

        for (attempt in 1..200) {
            val cuts = pool.shuffled(rnd).take(numColors - 1).sorted()
            val lengths = mutableListOf<Int>()
            var prev = 0
            var valid = true
            for (c in cuts) {
                val l = c - prev
                if (l < minLen) { valid = false; break }
                lengths.add(l)
                prev = c
            }
            lengths.add(total - prev)
            if (lengths.any { it < minLen }) valid = false

            if (valid) {
                var idx = 0
                val dots = mutableListOf<ColorDot>()
                for ((i, l) in lengths.withIndex()) {
                    val sub = path.subList(idx, idx + l)
                    idx += l
                    val st = sub.first()
                    val en = sub.last()
                    if (abs(st.r - en.r) + abs(st.c - en.c) < 2) {
                        valid = false
                        break
                    }
                    dots.add(ColorDot(i + 1, st, en))
                }
                if (valid) return dots
            }
        }
        return null
    }

    fun generateUniquePuzzle(size: Int, numColors: Int, rnd: Random): List<ColorDot>? {
        for (attempt in 1..2000) {
            val dots = generateSerpentinePath(size, numColors, rnd) ?: continue
            val solver = FlowSolver(size, dots)
            val count = solver.countFullCoverageSolutions(maxSolutions = 2, maxSteps = 2000)
            if (count == 1) {
                return dots
            }
        }
        return null
    }

    val configs = listOf(
        Triple("Easy", 5, 5),
        Triple("Medium", 6, 6),
        Triple("Hard", 7, 6),
        Triple("Expert", 8, 7)
    )

    val puzzles = mutableListOf<PregeneratedPuzzle>()

    for ((diff, size, numColors) in configs) {
        var added = 0
        var attempts = 0
        while (added < 5 && attempts < 500) {
            attempts++
            val dots = generateUniquePuzzle(size, numColors, random)
            if (dots != null) {
                added++
                val id = "${diff}_${size}x${size}_puzzle_${String.format("%03d", added)}"
                puzzles.add(PregeneratedPuzzle(id, size, diff, dots))
                println("Generated unique puzzle $added/5 for $diff (${size}x${size}).")
            }
        }
        if (added < 5) {
            println("Warning: Only generated $added/5 puzzles for $diff (${size}x${size}).")
        }
    }

    // Write FlowFreePregenerated.kt
    val sb = StringBuilder()
    sb.appendLine("package com.funkyotc.puzzleverse.flowfree.data")
    sb.appendLine()
    sb.appendLine("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.appendLine()
    sb.appendLine("data class PregeneratedPuzzle(")
    sb.appendLine("    override val id: String,")
    sb.appendLine("    val size: Int,")
    sb.appendLine("    override val difficulty: String,")
    sb.appendLine("    val dots: List<ColorDot>")
    sb.appendLine(") : BrowseablePuzzle {")
    sb.appendLine("    override val label: String get() = \"Puzzle\"")
    sb.appendLine("    override val subtitle: String get() = \"\${size}x\${size}\"")
    sb.appendLine("}")
    sb.appendLine()
    sb.appendLine("object FlowFreePregenerated {")
    sb.appendLine()
    sb.appendLine("    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(")

    for (p in puzzles) {
        val dotsStr = p.dots.joinToString(", ") { d ->
            "ColorDot(${d.colorId}, Point(${d.start.r}, ${d.start.c}), Point(${d.end.r}, ${d.end.c}))"
        }
        sb.appendLine("        PregeneratedPuzzle(\"${p.id}\", ${p.size}, \"${p.difficulty}\", listOf($dotsStr)),")
    }

    sb.appendLine("    )")
    sb.appendLine()
    sb.appendLine("    val PUZZLES_BY_DIFFICULTY: Map<String, List<BrowseablePuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }")
    sb.appendLine()
    sb.appendLine("    fun getPuzzleById(id: String): PregeneratedPuzzle? = ALL_PUZZLES.find { it.id == id }")
    sb.appendLine()
    sb.appendLine("    fun getRandomPuzzle(difficulty: FlowDifficulty): PregeneratedPuzzle? {")
    sb.appendLine("        val diffName = when(difficulty) {")
    sb.appendLine("            FlowDifficulty.EASY -> \"Easy\"")
    sb.appendLine("            FlowDifficulty.MEDIUM -> \"Medium\"")
    sb.appendLine("            FlowDifficulty.HARD -> \"Hard\"")
    sb.appendLine("            FlowDifficulty.EXPERT -> \"Expert\"")
    sb.appendLine("        }")
    sb.appendLine("        return ALL_PUZZLES.filter { it.difficulty == diffName }.randomOrNull()")
    sb.appendLine("    }")
    sb.appendLine("}")

    val targetFile = if (File("app").isDirectory) {
        File("app/src/main/java/com/funkyotc/puzzleverse/flowfree/data/FlowFreePregenerated.kt")
    } else {
        File("src/main/java/com/funkyotc/puzzleverse/flowfree/data/FlowFreePregenerated.kt")
    }
    targetFile.parentFile?.mkdirs()
    targetFile.writeText(sb.toString())

    println("Successfully generated FlowFreePregenerated.kt with ${puzzles.size} unique puzzles.")
}
