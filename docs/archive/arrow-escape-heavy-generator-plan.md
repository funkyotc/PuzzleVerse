# Arrow Escape Heavy Optimization & Pregeneration Plan

Plan for deep offline puzzle generation utilizing heavy processing power, multithreading, and candidate scoring to create the highest quality, most entangled Arrow Escape puzzles.

---

## Overview & Objective
Instead of returning the first candidate puzzle that satisfies basic constraints, the **Heavy Pregenerator** evaluates tens of thousands of candidate layouts per puzzle slot ($10,000 - 50,000$ candidates) using an **Objective Quality Scoring Function**. Only the top-scoring **0.1%** candidate layouts are selected and baked into [ArrowEscapePregenerated.kt](file:///C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt).

---

## 1. Candidate Quality Scoring Function ($Q$)

Each generated candidate layout is assigned an objective Quality Score $Q$:

$$Q = 10.0 \cdot \text{Depth} + 5.0 \cdot \text{Intersections} + 8.0 \cdot \text{MultiBlockers} + 2.0 \cdot \text{Turns} + 100.0 \cdot \text{Density}$$

- **DAG Depth ($\text{Depth}$)**: Length of the longest sequential escape chain.
- **Ray-Body Intersections ($\text{Intersections}$)**: Total count of exit rays crossing other arrow bodies across the board.
- **Multi-Blocker Count ($\text{MultiBlockers}$)**: Count of arrows obstructed by 2 or more distinct prerequisite arrows.
- **Winding Serpentine Turns ($\text{Turns}$)**: Count of 90° bends across all arrow bodies.
- **Cell Density ($\text{Density}$)**: Percentage of grid cells occupied (targeting 95%+).

---

## 2. Live Terminal Output & Progress Tracking

The script [PregenerateExtremePuzzles.kt](file:///C:/Users/funky/AppDev/PuzzleVerse/app/src/test/java/com/funkyotc/puzzleverse/arrowescape/PregenerateExtremePuzzles.kt) outputs live progress to the console during execution:

```text
=================================================================================
ARROW ESCAPE HEAVY OPTIMIZATION GENERATOR
=================================================================================
[Master 005/020] Iter 14,200/50,000 (28.4%) | Best Q: 482.5 | Depth: 42 | Starters: 1 | Elapsed: 00:15
[Master 005/020] Iter 28,900/50,000 (57.8%) | Best Q: 512.0 | Depth: 45 | Starters: 1 | Elapsed: 00:31
[Master 005/020] -> SELECTED CANDIDATE: Score=512.0, Depth=45, Starters=1, Intersections=84
=================================================================================
```

---

## 3. High-Power Parallel Generation Script

- **File**: `app/src/test/java/com/funkyotc/puzzleverse/arrowescape/PregenerateExtremePuzzles.kt`
- **Execution Command**:
  ```powershell
  .\gradlew.bat testDebugUnitTest --tests "com.funkyotc.puzzleverse.arrowescape.PregenerateExtremePuzzles"
  ```
- **Multithreading**: Utilizes `Dispatchers.Default` across all available CPU cores to evaluate up to 5,000 candidates/second.

---

## Proposed Changes

### Generator & Scoring Engine
- Add candidate evaluation and line-intersection metrics to [ArrowEscapeGenerator.kt](file:///C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/arrowescape/model/ArrowEscapeGenerator.kt).

### Multithreaded Pregeneration Task
- Create [PregenerateExtremePuzzles.kt](file:///C:/Users/funky/AppDev/PuzzleVerse/app/src/test/java/com/funkyotc/puzzleverse/arrowescape/PregenerateExtremePuzzles.kt) with live terminal output and progress reporting.

---

## Verification & Execution Plan
1. Run heavy pregeneration task via PowerShell:
   ```powershell
   .\gradlew.bat testDebugUnitTest --tests "com.funkyotc.puzzleverse.arrowescape.PregenerateExtremePuzzles"
   ```
2. Monitor live terminal output for candidate counts, scores, and depth metrics.
3. Verify updated [ArrowEscapePregenerated.kt](file:///C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt).
4. Run `.\gradlew.bat assembleDebug` to verify clean build.
