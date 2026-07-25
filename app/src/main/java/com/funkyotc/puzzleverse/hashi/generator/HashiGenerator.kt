package com.funkyotc.puzzleverse.hashi.generator

import com.funkyotc.puzzleverse.hashi.data.HashiPuzzle
import com.funkyotc.puzzleverse.hashi.data.Island
import kotlin.random.Random

class HashiGenerator(private val seed: Long = Random.nextLong()) {

    fun generate(difficulty: String): HashiPuzzle {
        val random = Random(seed)
        val (width, height, targetIslands) = when (difficulty.lowercase()) {
            "easy" -> Triple(7, 7, 8)
            "medium" -> Triple(9, 9, 14)
            "hard" -> Triple(11, 11, 20)
            "expert" -> Triple(13, 13, 26)
            else -> Triple(7, 7, 8)
        }

        val islandsMap = mutableMapOf<Pair<Int, Int>, Int>() // (x, y) -> bridge sum
        val islandList = mutableListOf<Island>()
        val gridOccupied = Array(height) { BooleanArray(width) }

        // Start with initial island
        val startX = random.nextInt(1, width - 1)
        val startY = random.nextInt(1, height - 1)
        islandsMap[Pair(startX, startY)] = 0
        gridOccupied[startY][startX] = true

        val directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
        var attempts = 0

        while (islandsMap.size < targetIslands && attempts < 500) {
            attempts++
            val existing = islandsMap.keys.toList().random(random)
            val dir = directions.random(random)
            val dist = random.nextInt(2, 5)

            val nx = existing.first + dir.first * dist
            val ny = existing.second + dir.second * dist

            if (nx !in 1 until width - 1 || ny !in 1 until height - 1) continue
            if (islandsMap.containsKey(Pair(nx, ny))) continue

            // Check line of sight (no existing islands between)
            var blocked = false
            for (step in 1 until dist) {
                val cx = existing.first + dir.first * step
                val cy = existing.second + dir.second * step
                if (gridOccupied[cy][cx]) {
                    blocked = true
                    break
                }
            }
            if (blocked) continue

            // Place bridge (1 or 2 bridges)
            val count = random.nextInt(1, 3)
            islandsMap[existing] = (islandsMap[existing] ?: 0) + count
            islandsMap[Pair(nx, ny)] = count

            for (step in 0..dist) {
                val cx = existing.first + dir.first * step
                val cy = existing.second + dir.second * step
                gridOccupied[cy][cx] = true
            }
        }

        for ((pos, bridgeCount) in islandsMap) {
            islandList.add(Island(x = pos.first, y = pos.second, requiredBridges = bridgeCount))
        }

        return HashiPuzzle(
            id = "hashi_${difficulty}_${seed}",
            width = width,
            height = height,
            islands = islandList
        )
    }
}
