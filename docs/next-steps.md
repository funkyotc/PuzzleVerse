# PuzzleVerse — Next Steps

## ✅ Completed

### Flow Free
- 48 pregenerated puzzles (Easy 5×5, Medium 6×6, Hard 7×7, Expert 8×8)
- Puzzle browser with difficulty tabs, size subheadings, completion checkmarks
- Win dialog: Back to List + Next Puzzle (sequential)
- Scripts: `scripts/generate_puzzles.py`, `scripts/bake_puzzles.py`

### Sudoku
- 65 pregenerated puzzles (20 Easy, 20 Medium, 15 Hard, 10 Expert)
- Puzzle browser with difficulty tabs, completion tracking
- Win dialog: Back to List + Next Puzzle (sequential by difficulty)
- Scripts: `scripts/generate_sudoku.py`, `scripts/bake_sudoku.py`

### 2048
- Menu simplified to just "Play" + "Daily Challenge"

---

## Phase 2: Nonogram + Kakuro

### Nonogram
**Goal:** Generate browsable nonogram puzzles at multiple sizes.

**Steps:**
1. Write `scripts/generate_nonograms.py`
   - Generate pixel-art patterns using template shapes (hearts, stars, arrows, animals)
   - Sizes: 5×5 (Easy), 10×10 (Medium), 15×15 (Hard)
   - Verify each puzzle has a unique solution via line-solving logic
   - Target: 15 Easy, 15 Medium, 10 Hard
2. Write `scripts/bake_nonograms.py` → `NonogramPregenerated.kt`
   - Store grids as compact binary strings ("10110..." for filled/empty)
3. Create `NonogramCompletionRepository.kt`
4. Create `NonogramPuzzleBrowserScreen.kt`
5. Update `NonogramViewModel.kt` — accept puzzleId, load specific puzzle
6. Update `GameDetailScreen.kt` — add "Browse Puzzles" for nonogram
7. Update `MainActivity.kt` — add `nonogram/puzzles` and `game/nonogram/puzzle/{id}` routes
8. Add win dialog with Back to List + Next Puzzle

### Kakuro
**Goal:** Generate browsable kakuro puzzles at multiple grid sizes.

**Steps:**
[x] 1. Write `scripts/generate_kakuro.py`
   - Use constraint satisfaction to build valid grids
   - Sizes: 4×4 (Easy), 6×6 (Medium), 8×8 (Hard)
   - Verify unique solutions
   - Target: 15 Easy, 10 Medium, 8 Hard
   - Use multiprocessing (12 cores) — Hard grids may take minutes
[x] 2. Write `scripts/bake_kakuro.py` → `KakuroPregenerated.kt`
[x] 3. Create `KakuroCompletionRepository.kt`
[x] 4. Create `KakuroPuzzleBrowserScreen.kt`
[x] 5. Update `KakuroViewModel.kt` — accept puzzleId
[x] 6. Update `GameDetailScreen.kt` — add "Browse Puzzles" for kakuro
[x] 7. Update `MainActivity.kt` — add routes
[x] 8. Add win dialog with Back to List + Next Puzzle

---

## Phase 3: Minesweeper Difficulty

**Goal:** Add difficulty presets (no pregeneration needed — procedural is fine).

**Steps:**
1. Create `MinesweeperDifficulty.kt` enum:
   - `Easy`: 9×9, 10 mines
   - `Medium`: 16×16, 40 mines (current default)
   - `Hard`: 16×30, 99 mines
2. Update `MinesweeperViewModel.kt` — accept difficulty, use its grid/mine params
3. Update `GameDetailScreen.kt` — show difficulty selector cards (Easy/Medium/Hard)
4. No puzzle browser needed — Minesweeper is inherently random

---

## Phase 4: Shared Infrastructure

**Goal:** Reduce code duplication across all puzzle browsers.

**Steps:**
1. Create generic `PuzzleCompletionRepository(context, gameId)` replacing per-game repos
2. Create generic `PuzzleBrowserScreen` composable accepting:
   - `puzzles: List<BrowsablePuzzle>` (id, difficulty, size/label)
   - `completedIds: Set<String>`
   - `difficultyLabels: List<String>`
   - `onPuzzleSelected: (String) -> Unit`
3. Migrate Flow Free, Sudoku, Nonogram, Kakuro browsers to use shared component
4. Delete per-game `*CompletionRepository.kt` files
5. Delete per-game `*PuzzleBrowserScreen.kt` files

---

## Phase 5: Bonza, Constellations, Shapes

### Bonza
1. Write `scripts/generate_bonza.py`
   - Generate word cluster puzzles from themed word lists
   - Categories: Animals, Food, Science, Sports, Geography, etc.
   - Target: 10 puzzles per theme, 6 themes = 60 puzzles
2. Write `scripts/bake_bonza.py` → `BonzaPregenerated.kt`
3. Browser grouped by theme instead of difficulty
4. Completion tracking + Back to List / Next Puzzle

### Constellations
1. Write `scripts/generate_constellations.py`
   - Generate star patterns with known solutions
   - Difficulty by star count: Easy (5), Medium (8), Hard (12)
   - Target: 10 per difficulty = 30 puzzles
2. Write `scripts/bake_constellations.py` → `ConstellationsPregenerated.kt`
3. Standard browser + completion tracking

### Shapes
1. Write `scripts/generate_shapes.py`
   - Generate shape-fitting puzzles at varying piece counts
   - Difficulty by piece count and grid size
   - Target: 10 per difficulty = 30 puzzles
2. Write `scripts/bake_shapes.py` → `ShapesPregenerated.kt`
3. Standard browser + completion tracking

---

## Phase 6: Wordle Stats + Polish

1. Create `WordleStatsRepository.kt` — track games played, win rate, guess distribution
2. Add stats display screen accessible from Wordle menu
3. Optional: Add word length variants (4/5/6 letter modes)

---

## Pipeline Summary

For any game needing pregenerated puzzles:
```
python scripts/generate_<game>.py    # Generate JSON puzzles
python scripts/bake_<game>.py        # Convert to Kotlin source
./gradlew assembleDebug              # Build app
```

All generator scripts output to `scripts/output/` (gitignored).
All bake scripts write directly to the appropriate `data/` package.
