package com.funkyotc.puzzleverse.nonogram.data

object NonogramSolver {

    /**
     * Checks if the given Nonogram grid is solvable by pure line deduction logic
     * without needing to make any guesses (line-solvable).
     */
    fun isSolvableWithoutGuessing(grid: List<List<Boolean>>): Boolean {
        val rows = grid.size
        val cols = if (rows > 0) grid[0].size else 0
        if (rows == 0 || cols == 0) return true
        
        // Compute row and column clues
        val rowClues = grid.map { calculateClues(it) }
        val colClues = (0 until cols).map { c -> calculateClues(grid.map { it[c] }) }
        
        // 0 = UNKNOWN, 1 = FILLED, -1 = CROSSED
        val solvedGrid = Array(rows) { IntArray(cols) { 0 } }
        
        var progress = true
        var iterations = 0
        val maxIterations = rows * cols // Safety bound
        
        while (progress && iterations < maxIterations) {
            progress = false
            iterations++
            
            // 1. Solve Rows
            for (r in 0 until rows) {
                val lineState = solvedGrid[r]
                val configs = getValidLineConfigsPruned(rowClues[r], lineState)
                if (configs.isEmpty()) return false // Contradiction: no consistent layout exists
                
                for (c in 0 until cols) {
                    if (lineState[c] == 0) { // Only check unknown cells
                        var allTrue = true
                        var allFalse = true
                        for (config in configs) {
                            if (config[c]) {
                                allFalse = false
                            } else {
                                allTrue = false
                            }
                        }
                        if (allTrue) {
                            solvedGrid[r][c] = 1
                            progress = true
                        } else if (allFalse) {
                            solvedGrid[r][c] = -1
                            progress = true
                        }
                    }
                }
            }
            
            // 2. Solve Columns
            for (c in 0 until cols) {
                val lineState = IntArray(rows) { r -> solvedGrid[r][c] }
                val configs = getValidLineConfigsPruned(colClues[c], lineState)
                if (configs.isEmpty()) return false // Contradiction
                
                for (r in 0 until rows) {
                    if (solvedGrid[r][c] == 0) {
                        var allTrue = true
                        var allFalse = true
                        for (config in configs) {
                            if (config[r]) {
                                allFalse = false
                            } else {
                                allTrue = false
                            }
                        }
                        if (allTrue) {
                            solvedGrid[r][c] = 1
                            progress = true
                        } else if (allFalse) {
                            solvedGrid[r][c] = -1
                            progress = true
                        }
                    }
                }
            }
        }
        
        // If there are still UNKNOWN (0) cells, the puzzle requires guessing
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (solvedGrid[r][c] == 0) return false
            }
        }
        return true
    }
    
    private fun calculateClues(line: List<Boolean>): List<Int> {
        val clues = mutableListOf<Int>()
        var count = 0
        for (v in line) {
            if (v) {
                count++
            } else if (count > 0) {
                clues.add(count)
                count = 0
            }
        }
        if (count > 0) clues.add(count)
        if (clues.isEmpty()) clues.add(0)
        return clues
    }
    
    /**
     * Recursively generates all candidate Boolean configurations of placing clues in a line,
     * pruning branches immediately if they contradict the known lineState.
     */
    private fun getValidLineConfigsPruned(
        clues: List<Int>,
        lineState: IntArray
    ): List<BooleanArray> {
        val result = mutableListOf<BooleanArray>()
        val L = lineState.size
        
        fun generate(clueIndex: Int, currentIndex: Int, currentConfig: BooleanArray) {
            if (clueIndex == clues.size) {
                // Ensure no remaining cells are known to be FILLED (1)
                for (i in currentIndex until L) {
                    if (lineState[i] == 1) return
                }
                result.add(currentConfig.clone())
                return
            }
            
            val currentClue = clues[clueIndex]
            if (currentClue == 0) {
                // Empty line clue
                for (i in 0 until L) {
                    if (lineState[i] == 1) return
                }
                result.add(BooleanArray(L))
                return
            }
            
            var minSpaceRemaining = 0
            for (i in clueIndex + 1 until clues.size) {
                minSpaceRemaining += clues[i] + 1
            }
            
            val maxStartIndex = L - currentClue - minSpaceRemaining
            for (start in currentIndex..maxStartIndex) {
                var possible = true
                
                // 1. No cells before this clue placement can be FILLED (1)
                for (i in currentIndex until start) {
                    if (lineState[i] == 1) {
                        possible = false
                        break
                    }
                }
                if (!possible) continue
                
                // 2. No cells within the clue placement range can be CROSSED (-1)
                for (i in 0 until currentClue) {
                    if (lineState[start + i] == -1) {
                        possible = false
                        break
                    }
                }
                if (!possible) continue
                
                // 3. The spacer cell immediately following the clue must not be FILLED (1)
                val endOfClue = start + currentClue
                if (clueIndex < clues.size - 1) {
                    if (endOfClue < L && lineState[endOfClue] == 1) {
                        possible = false
                    }
                }
                if (!possible) continue
                
                val newConfig = currentConfig.clone()
                for (i in 0 until currentClue) {
                    newConfig[start + i] = true
                }
                val nextIndex = endOfClue + if (clueIndex < clues.size - 1) 1 else 0
                generate(clueIndex + 1, nextIndex, newConfig)
            }
        }
        
        generate(0, 0, BooleanArray(L))
        return result
    }
}
