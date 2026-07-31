package com.funkyotc.puzzleverse.woodnuts.data

import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedWoodNutsLevel(
    override val id: String,
    override val difficulty: String,
    val rows: Int,
    val cols: Int,
    val bolts: List<Bolt>,
    val planks: List<Plank>,
    val boardQueue: List<ScrewColor> = emptyList()
) : BrowseablePuzzle {
    override val label: String get() = id.substringAfterLast('_')
    override val subtitle: String get() = "${rows}x${cols}"
}

object WoodNutsPregenerated {

    val ALL_LEVELS: List<PregeneratedWoodNutsLevel> by lazy { generateAllLevels() }

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedWoodNutsLevel>> by lazy {
        ALL_LEVELS.groupBy { it.difficulty }
    }

    fun getPuzzleById(id: String): PregeneratedWoodNutsLevel? = ALL_LEVELS.find { it.id == id }

    private fun generateAllLevels(): List<PregeneratedWoodNutsLevel> {
        val list = mutableListOf<PregeneratedWoodNutsLevel>()
        
        // 10 Easy levels (3x3 - 4x4)
        for (i in 1..10) {
            list.add(createLevel("woodnuts_easy_${i.toString().padStart(3, '0')}", "Easy", 3 + (i % 2), i))
        }
        
        // 10 Medium levels (4x4 - 5x5)
        for (i in 1..10) {
            list.add(createLevel("woodnuts_medium_${i.toString().padStart(3, '0')}", "Medium", 4 + (i % 2), 10 + i))
        }
        
        // 10 Hard levels (5x5)
        for (i in 1..10) {
            list.add(createLevel("woodnuts_hard_${i.toString().padStart(3, '0')}", "Hard", 5, 20 + i))
        }
        
        // 10 Expert levels (5x5 - 6x6)
        for (i in 1..10) {
            list.add(createLevel("woodnuts_expert_${i.toString().padStart(3, '0')}", "Expert", 5 + (i % 2), 30 + i))
        }
        
        // 10 Master levels (6x6)
        for (i in 1..10) {
            list.add(createLevel("woodnuts_master_${i.toString().padStart(3, '0')}", "Master", 6, 40 + i))
        }
        
        return list
    }

    private fun createLevel(id: String, difficulty: String, size: Int, seedIndex: Int): PregeneratedWoodNutsLevel {
        val random = kotlin.random.Random(seedIndex.toLong() * 1000L + 42L)
        val bolts = mutableListOf<Bolt>()
        val planks = mutableListOf<Plank>()
        
        val plankCount = when (difficulty) {
            "Easy" -> 3 + (seedIndex % 2)
            "Medium" -> 5 + (seedIndex % 2)
            "Hard" -> 7 + (seedIndex % 2)
            "Expert" -> 8 + (seedIndex % 3)
            else -> 10 + (seedIndex % 3)
        }
        
        val allCoords = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                allCoords.add(Pair(r, c))
            }
        }
        allCoords.shuffle(random)
        
        val rawTotal = (plankCount * 1.8f).toInt().coerceAtMost(size * size - 2)
        val boardCount = (rawTotal / 3).coerceAtLeast(1)
        val totalBolts = boardCount * 3
        val boltCoords = allCoords.take(totalBolts)
        
        val availableColors = ScrewColor.entries.shuffled(random)
        val boardQueue = mutableListOf<ScrewColor>()
        val boltColors = mutableListOf<ScrewColor>()
        
        for (b in 0 until boardCount) {
            val color = availableColors[b % availableColors.size]
            boardQueue.add(color)
            repeat(3) {
                boltColors.add(color)
            }
        }
        boltColors.shuffle(random)

        for ((idx, coord) in boltCoords.withIndex()) {
            bolts.add(Bolt("b${idx + 1}", coord.first, coord.second, color = boltColors[idx]))
        }
        
        var plankId = 1
        for (p in 0 until plankCount) {
            if (bolts.size < 2) break
            val b1 = bolts[random.nextInt(bolts.size)]
            var b2 = bolts[random.nextInt(bolts.size)]
            while (b2.id == b1.id) {
                b2 = bolts[random.nextInt(bolts.size)]
            }
            
            val r1 = minOf(b1.row, b2.row)
            val c1 = minOf(b1.col, b2.col)
            val r2 = maxOf(b1.row, b2.row)
            val c2 = maxOf(b1.col, b2.col)
            
            val extraBolts = bolts.filter { b ->
                b.id != b1.id && b.id != b2.id &&
                b.row in r1..r2 && b.col in c1..c2 &&
                (random.nextFloat() < 0.4f)
            }.map { it.id }
            
            val attachedBolts = (listOf(b1.id, b2.id) + extraBolts).distinct()
            
            planks.add(
                Plank(
                    id = "p${plankId++}",
                    startRow = r1,
                    startCol = c1,
                    endRow = r2,
                    endCol = c2,
                    boltIds = attachedBolts,
                    depthLayer = p % 3
                )
            )
        }
        
        return PregeneratedWoodNutsLevel(
            id = id,
            difficulty = difficulty,
            rows = size,
            cols = size,
            bolts = bolts,
            planks = planks,
            boardQueue = boardQueue
        )
    }
}
