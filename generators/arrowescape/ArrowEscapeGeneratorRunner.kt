package generators.arrowescape

import com.funkyotc.puzzleverse.arrowescape.model.Arrow
import com.funkyotc.puzzleverse.arrowescape.model.ArrowEscapeGenerator
import com.funkyotc.puzzleverse.arrowescape.model.CandidateStats
import com.funkyotc.puzzleverse.arrowescape.model.LevelShape
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random

data class ExtremeSlotConfig(
    val slotNumber: Int,
    val puzzleId: String,
    val objectName: String,
    val width: Int,
    val height: Int,
    val shape: LevelShape
)

/**
 * Standalone Kotlin runner for generating extreme Arrow Escape puzzles.
 * Moves heavy puzzle generation out of Gradle tasks into an isolated Kotlin runner.
 */
fun main(args: Array<String>) {
    val candidatesPerSlot = args.getOrNull(0)?.toIntOrNull() ?: 5000
    val threadCount = Runtime.getRuntime().availableProcessors()

    println("==========================================================================")
    println("ARROW ESCAPE EXTREME PUZZLE GENERATOR RUNNER")
    println("Threads: $threadCount | Candidates Target Per Slot: $candidatesPerSlot")
    println("==========================================================================")

    val slots = listOf(
        ExtremeSlotConfig(1, "arrowescape_extreme_001", "ArrowEscapePuzzle121", 30, 30, LevelShape.SQUARE),
        ExtremeSlotConfig(2, "arrowescape_extreme_002", "ArrowEscapePuzzle122", 30, 30, LevelShape.CIRCLE),
        ExtremeSlotConfig(3, "arrowescape_extreme_003", "ArrowEscapePuzzle123", 30, 30, LevelShape.TRIANGLE),
        ExtremeSlotConfig(4, "arrowescape_extreme_004", "ArrowEscapePuzzle124", 30, 30, LevelShape.HEXAGON),
        ExtremeSlotConfig(5, "arrowescape_extreme_005", "ArrowEscapePuzzle125", 30, 30, LevelShape.PENTAGON),
        ExtremeSlotConfig(6, "arrowescape_extreme_006", "ArrowEscapePuzzle126", 30, 30, LevelShape.STAR),
        ExtremeSlotConfig(7, "arrowescape_extreme_007", "ArrowEscapePuzzle127", 30, 30, LevelShape.DIAMOND),
        ExtremeSlotConfig(8, "arrowescape_extreme_008", "ArrowEscapePuzzle128", 30, 30, LevelShape.CROSS),
        ExtremeSlotConfig(9, "arrowescape_extreme_009", "ArrowEscapePuzzle129", 30, 30, LevelShape.HEART),
        ExtremeSlotConfig(10, "arrowescape_extreme_010", "ArrowEscapePuzzle130", 35, 35, LevelShape.SQUARE),
        ExtremeSlotConfig(11, "arrowescape_extreme_011", "ArrowEscapePuzzle131", 35, 35, LevelShape.CIRCLE),
        ExtremeSlotConfig(12, "arrowescape_extreme_012", "ArrowEscapePuzzle132", 35, 35, LevelShape.TRIANGLE),
        ExtremeSlotConfig(13, "arrowescape_extreme_013", "ArrowEscapePuzzle133", 35, 35, LevelShape.HEXAGON),
        ExtremeSlotConfig(14, "arrowescape_extreme_014", "ArrowEscapePuzzle134", 35, 35, LevelShape.PENTAGON),
        ExtremeSlotConfig(15, "arrowescape_extreme_015", "ArrowEscapePuzzle135", 40, 40, LevelShape.SQUARE),
        ExtremeSlotConfig(16, "arrowescape_extreme_016", "ArrowEscapePuzzle136", 40, 40, LevelShape.CIRCLE),
        ExtremeSlotConfig(17, "arrowescape_extreme_017", "ArrowEscapePuzzle137", 40, 40, LevelShape.DIAMOND),
        ExtremeSlotConfig(18, "arrowescape_extreme_018", "ArrowEscapePuzzle138", 40, 40, LevelShape.CROSS),
        ExtremeSlotConfig(19, "arrowescape_extreme_019", "ArrowEscapePuzzle139", 40, 40, LevelShape.HEART),
        ExtremeSlotConfig(20, "arrowescape_extreme_020", "ArrowEscapePuzzle140", 40, 40, LevelShape.STAR)
    )

    val generatedObjects = mutableMapOf<String, Pair<ExtremeSlotConfig, Pair<List<Arrow>, CandidateStats>>>()
    val startTime = System.currentTimeMillis()

    for (slot in slots) {
        val slotStartTime = System.currentTimeMillis()
        val totalEvaluated = AtomicInteger(0)
        val bestStatsRef = AtomicReference<Pair<List<Arrow>, CandidateStats>?>(null)

        val executor = Executors.newFixedThreadPool(threadCount)
        val generator = ArrowEscapeGenerator()

        val workerTasks = (0 until threadCount).map { threadIdx ->
            Runnable {
                val seedRandom = Random(System.nanoTime() + threadIdx * 99991L)
                while (totalEvaluated.get() < candidatesPerSlot) {
                    val count = totalEvaluated.incrementAndGet()
                    if (count > candidatesPerSlot) break

                    val candidate = generator.generateExtremeCandidateLayout(slot.width, slot.height, slot.shape, seedRandom)
                    if (candidate.isNotEmpty()) {
                        val stats = generator.evaluateCandidate(candidate, slot.width, slot.height, slot.shape)
                        if (stats.isSolvable && stats.score > 0) {
                            while (true) {
                                val currentBest = bestStatsRef.get()
                                if (currentBest == null || stats.score > currentBest.second.score) {
                                    if (bestStatsRef.compareAndSet(currentBest, Pair(candidate, stats))) {
                                        break
                                    }
                                } else {
                                    break
                                }
                            }
                        }
                    }
                }
            }
        }

        val loggerActive = AtomicInteger(1)
        val loggerThread = Thread {
            var lastPrintedCount = 0
            var lastCheckTime = System.currentTimeMillis()
            while (loggerActive.get() == 1) {
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    break
                }
                val currentCount = totalEvaluated.get()
                val now = System.currentTimeMillis()
                val dt = (now - lastCheckTime) / 1000.0
                val rate = if (dt > 0) ((currentCount - lastPrintedCount) / dt).toInt() else 0
                lastPrintedCount = currentCount
                lastCheckTime = now

                val elapsedSec = (now - slotStartTime) / 1000
                val best = bestStatsRef.get()
                val pct = String.format("%.1f", (currentCount.coerceAtMost(candidatesPerSlot).toDouble() / candidatesPerSlot.toDouble()) * 100.0)
                val bestScoreStr = if (best != null) String.format("%.1f", best.second.score) else "None"
                val depthStr = best?.second?.depth?.toString() ?: "-"
                val startersStr = best?.second?.starters?.toString() ?: "-"

                System.out.printf(
                    "\r[%s %03d/020] Iter %,d/%,d (%s%%) | Best Score: %s | Depth: %s | Starters: %s | Rate: %,d c/s | Elapsed: %02d:%02d",
                    "Extreme", slot.slotNumber, currentCount.coerceAtMost(candidatesPerSlot), candidatesPerSlot,
                    pct, bestScoreStr, depthStr, startersStr, rate, elapsedSec / 60, elapsedSec % 60
                )
                System.out.flush()
            }
        }
        loggerThread.start()

        workerTasks.forEach { executor.submit(it) }
        executor.shutdown()
        while (!executor.isTerminated) {
            Thread.sleep(100)
        }

        loggerActive.set(0)
        loggerThread.interrupt()
        loggerThread.join(2000)

        var finalBest = bestStatsRef.get()
        if (finalBest == null) {
            println("\n[Extreme ${String.format("%03d", slot.slotNumber)}] Single candidate fallback...")
            val fallbackLayout = generator.generateExtreme(slot.width, slot.height, 0.85f, slot.shape)
            val fallbackStats = generator.evaluateCandidate(fallbackLayout, slot.width, slot.height, slot.shape)
            finalBest = Pair(fallbackLayout, fallbackStats)
        }

        generatedObjects[slot.objectName] = Pair(slot, finalBest)
        val slotElapsed = (System.currentTimeMillis() - slotStartTime) / 1000
        System.out.printf(
            "\n[Extreme %03d/020] -> SELECTED CANDIDATE: Score=%.1f, Depth=%d, Starters=%d, Intersections=%d, Density=%.1f%% (Time: %02d:%02d)\n",
            slot.slotNumber, finalBest.second.score, finalBest.second.depth, finalBest.second.starters,
            finalBest.second.intersections, finalBest.second.density * 100f, slotElapsed / 60, slotElapsed % 60
        )
    }

    val totalTimeSec = (System.currentTimeMillis() - startTime) / 1000
    println("\n==========================================================================")
    println("PREGENERATION COMPLETE IN %02d:%02d!".format(totalTimeSec / 60, totalTimeSec % 60))
    println("Updating ArrowEscapePregenerated.kt...")

    updatePregeneratedFile(generatedObjects)
}

private fun updatePregeneratedFile(
    results: Map<String, Pair<ExtremeSlotConfig, Pair<List<Arrow>, CandidateStats>>>
) {
    val candidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt"),
        File("src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt")
    )
    val file = candidates.find { it.exists() }
    if (file == null) {
        println("Error: ArrowEscapePregenerated.kt not found at expected paths!")
        return
    }

    println("Targeting file: ${file.absolutePath}")
    var content = file.readText()

    for ((objectName, data) in results) {
        val (slot, best) = data
        val arrows = best.first
        val sb = StringBuilder()
        sb.append("object ").append(objectName).append(" {\n")
        sb.append("    val shape = LevelShape.").append(slot.shape.name).append("\n")
        sb.append("    val arrows = listOf<Arrow>(\n")

        for (arrow in arrows) {
            sb.append("        Arrow(").append(arrow.id).append(", listOf(")
            val segStrs = arrow.segments.joinToString(", ") { "Coordinate(${it.x}, ${it.y})" }
            sb.append(segStrs).append("), Direction.").append(arrow.direction.name).append(", ").append(arrow.color).append("),\n")
        }

        sb.append("    )\n")
        sb.append("}")

        val regex = Regex("object\\s+$objectName\\s*\\{[\\s\\S]*?\\n\\}")
        if (regex.containsMatchIn(content)) {
            content = regex.replace(content, sb.toString())
        } else {
            println("Warning: Could not match $objectName in ArrowEscapePregenerated.kt")
        }
    }

    file.writeText(content)
    println("Successfully wrote updated puzzles to ${file.absolutePath}!")
}
