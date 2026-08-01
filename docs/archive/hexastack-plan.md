# Hexa Stack Game Design & Implementation Plan (`hexastack`)

## Overview
**Hexa Stack** is a 3D isometric/layered hex-grid puzzle game for PuzzleVerse. The player receives 3-slot reserve trays containing pre-stacked colored hexagonal tiles to place onto empty cells of an axial hex grid. Adjacent cells with matching top tile colors merge and stack together. When a stack accumulates **10 or more contiguous tiles of the same color**, those tiles match, trigger a visual pop animation, and clear from the board.

---

## Game Rules & Core Mechanics

### 1. Grid Geometry
- Hexagonal grid using axial coordinates `(q, r)` with 6 directions:
  `(+1, 0), (+1, -1), (0, -1), (-1, 0), (-1, +1), (0, +1)`
- Grid sizes vary by level (e.g. 19-cell radius-2 grid, or 37-cell radius-3 grid).
- Grid cells hold a `HexStack`, represented by an ordered list of integer color IDs (where the last item is the top tile).

### 2. Player Hand / Reserve Tray
- Player has 3 reserve slots containing 3 pre-stacked tile groups (each 1–5 tiles deep).
- Player drags or taps a reserve stack onto an **empty grid cell**.
- Spawns follow a **100% deterministic sequence** pre-defined per level.
- When all 3 slots are placed, 3 new stacks from the deterministic level spawn deck are dealt to the tray.

### 3. Stack Stacking & Merging Rules
- **Primary Merge Priority**: When a stack is placed onto an empty hex cell, adjacent cells with matching top colors transfer their matching contiguous top tiles onto the **newly placed stack**.
- **Secondary / Cascade Priority**: If transferring tiles changes the top color of a stack, or during secondary cascading merges across adjacent stacks, matching top tiles merge onto the **highest stack** among the matching neighbors.
- **Match & Pop Rule**: Whenever any stack reaches **10 or more tiles of the same color**, those 10 (or all contiguous top tiles of that color) match and pop off the board, earning points and freeing stack depth.
- **Cascading Engine**: Popping tiles or transferring tiles can reveal lower tiles in a stack. If the newly exposed top color matches adjacent cells, another merge cascade step is evaluated automatically.

### 4. Win / Loss Conditions
- **Win Condition**: Clearing **all tiles** off the board and out of hand.
- **Loss Condition**: Grid becomes completely full with no empty cells available to place any of the remaining reserve stacks.

### 5. Level Generation & Solvability
- Level solver (`HexaStackSolver`) uses move simulation / search to verify that every pre-generated level in `HexaStackPregenerated.kt` is 100% solvable.
- Levels are organized across difficulties: `Easy`, `Medium`, `Hard`, and `Expert`.

---

## Technical Architecture & File Structure

```
com.funkyotc.puzzleverse.hexastack/
├── data/
│   ├── HexaStackModels.kt        # Grid coordinates, stack model, level, state
│   ├── HexaStackSolver.kt        # Solvability verification algorithm
│   ├── HexaStackPregenerated.kt  # Pre-generated solvable level definitions
│   └── HexaStackRepository.kt    # Save state persistence
├── viewmodel/
│   └── HexaStackViewModel.kt     # Game state engine, merge cascade logic, hand dealing
└── ui/
    └── HexaStackScreen.kt        # Jetpack Compose UI, 3D stacked hex tiles, drag gestures, pop animations
```

---

## Verification Strategy

1. **Automated Tests**:
   - `HexaStackSolverTest.kt` verifying merge rules, 10+ matching pops, cascade resolution, and solvability of all pregenerated levels.
   - Build validation with `gradlew.bat assembleDebug`.

2. **Manual Testing**:
   - Play testing standard mode, daily challenge, and level browser.
   - Verifying gesture fluidity, sound effects, save state restore, and streak tracking.
