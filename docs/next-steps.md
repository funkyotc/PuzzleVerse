# PuzzleVerse — Next Steps

## ✅ Completed

### Flow Free

- [x] 48 pregenerated puzzles (Easy 5×5, Medium 6×6, Hard 7×7, Expert 8×8)
- [x] Shared puzzle browser integration
- [x] Win dialog: Back to List + Next Puzzle (sequential)
- [x] Scripts: `scripts/generate_puzzles.py`, `scripts/bake_puzzles.py`

### Sudoku

- [x] 65 pregenerated puzzles (20 Easy, 20 Medium, 15 Hard, 10 Expert)
- [x] Shared puzzle browser integration
- [x] Win dialog: Back to List + Next Puzzle (sequential by difficulty)
- [x] Scripts: `scripts/generate_sudoku.py`, `scripts/bake_sudoku.py`

### 2048

- [x] Menu simplified to just "Play" + "Daily Challenge"

### Nonogram (Phase 2)

- [x] 40 pregenerated puzzles (15 Easy, 15 Medium, 10 Hard)
- [x] Shared puzzle browser integration
- [x] Win dialog: Back to List + Next Puzzle
- [x] Scripts: `scripts/generate_nonograms.py`, `scripts/bake_nonograms.py`

### Kakuro (Phase 2)

- [x] 33 pregenerated puzzles (15 Easy, 10 Medium, 8 Hard)
- [x] Sharedป Shared puzzle browser integration
- [x] Win dialog: Back to List + Next Puzzle
- [x] Scripts: `scripts/generate_kakuro.py`, `scripts/bake_kakuro.py`

### Minesweeper (Phase 3)

- [x] Difficulty presets: Easy (9×9), Medium (16×16), Hard (16×30)
- [x] Dynamic grid/mine initialization based on selection

### Core Infrastructure (Phase 4)

- [x] Generic `PuzzleBrowserScreen` (replaces 4 game-specific screens)
- [x] Generic `PuzzleCompletionRepository` (replaces 4 game-specific repos)
- [x] Unified `BrowseablePuzzle` interface
- [x] Automated baking pipeline for all future pregenerated games

---

## 🛠️ Maintenance & Bug Fixes (The "Polish" Queue)

### UI/UX Refactor (High Priority)

- [x] **Main Menu Overhaul**: Rework layout to have Daily Challenge at the top, with puzzle lists in a second section.
- [ ] **Level Select Refinement**: Improve level select screen (numbers only in a grid, colored to mark completed levels).
- [ ] **Theme/Level Previews**: Implement theme previews using the selection buttons.
- [ ] **Progression/Unlocks**: Show locked themes/levels with unlock requirements (greyed out).
- [ ] **Information Density**: Show difficulty and level number for all pregenerated puzzles.

### Bug Fixes & Logic

- [x] **Daily Challenge Fixes**:
  - Remove Daily Challenge option from random-selection puzzles.
  - Fix broken Daily Challenge buttons.
- [x] **Flow Free Refinement**:
  - [x] Remove Easy/Med/Hard difficulty selection from Flow screen (use pregenerated list).
  - [x] Add Flow Undo and Restart buttons.
- [x] **Sudoku Tweaks**:
  - [x] Dim number buttons in Sudoku once all numbers are placed.
  - [x] Scale Sudoku buttons correctly to prevent overlapping.

### Feature Enhancements

- [ ] **Sudoku/Nonogram/etc. Scaling**: Standardize grid/button scaling across all puzzle types.

---

## Phase 5: Bonza, Constellations, Shapes

### Bonza

1. [x] Write `scripts/generate_bonza.py`
2. [x] Write `scripts/bake_bonza.py` → `BonzaPregenerated.kt`
3. [x] Browser grouped by theme instead of difficulty
4. [x] Completion tracking + Back to List / Next Puzzle

### Constellations

1. [x] Write `scripts/generate_constellations.py`
2. [x] Write `scripts/bake_constellations.py` → `ConstellationsPregenerated.kt`
3. [x] Standard browser + completion tracking

### Shapes

1. [x] Write `scripts/generate_shapes.py`
2. [x] Write `scripts/bake_shapes.py` → `ShapesPregenerated.kt`
3. [x] Standard browser + completion tracking

---

## Phase 6: Wordle Stats + Polish

1. Create `WordleStatsRepository.kt` — track games played, win rate, guess distribution
2. Add stats display screen accessible from Wordle menu
3. Optional: Add word length variants (4/5/6 letter modes)

---

## Phase 7: Extra Fixes

- [ ] **Bonza Pickup Offset**: Fix the piece pickup offset so that it doesnt change once the piece is dropped.
- [ ] **External Libraries**: Update the puzzle generation scripts to generate fun and interesting puzzles. Use whatever nessicary external libraries that are needed to do this.

---

## Pipeline Summary

...
