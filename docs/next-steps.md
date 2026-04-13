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
- [x] Shared puzzle browser integration
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
