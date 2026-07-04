# Hexa Sort — Implementation Plan

## Game Mechanics
- Honeycomb-style hexagonal grid filled with colored hexagons
- Tap any group of **2+ connected same-color** hexes to remove them
- Above hexes fall down to fill gaps
- New hexes drop in from the top to fill remaining empty cells
- Goal: clear all hexes from the board to win

## Data Models (`hexasort/data/HexaSortModels.kt`)

```kotlin
data class HexCell(
    val color: Int,        // color ID (0..N), -1 for empty
    val removed: Boolean = false  // during animation
)

data class HexaSortLevel(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<Int?>>  // null = empty, Int = color ID
) : BrowseablePuzzle

data class HexaSortState(
    val level: HexaSortLevel,
    val grid: List<List<Int?>>,   // current live grid
    val score: Int = 0,
    val moves: Int = 0,
    val isWon: Boolean = false,
    val isGameOver: Boolean = false,
    val flashingCells: Set<Pair<Int, Int>> = emptySet() // for tap animation
)
```

**Grid representation:**
- Stored as a rectangular grid with `rows` and `cols`
- Hexagons in odd rows are offset-right by half a cell (staggered)
- `null` = empty cell
- 6 neighbors computed from (row, col) using offset coordinate system

**Neighbor lookup for offset hex grid (odd-r):**
```
Even row neighbors: (r-1,c-1), (r-1,c), (r,c-1), (r,c+1), (r+1,c-1), (r+1,c)
Odd row neighbors:  (r-1,c), (r-1,c+1), (r,c-1), (r,c+1), (r+1,c), (r+1,c+1)
```

## ViewModel (`hexasort/viewmodel/HexaSortViewModel.kt`)

```kotlin
class HexaSortViewModel(
    streakRepository: StreakRepository?,
    mode: String?,
    puzzleId: String?
) : ViewModel() {
    private val _state = MutableStateFlow<HexaSortState?>(null)
    val state: StateFlow<HexaSortState?> = _state.asStateFlow()

    fun tapCell(row: Int, col: Int)
    fun startNewGame()
}
```

**`tapCell(row, col)` logic:**
1. If cell is null → ignore
2. Run flood-fill from (row, col) to find all connected same-color cells
3. If group size < 2 → ignore (must be 2+)
4. Remove all cells in the group → set to null
5. **Gravity pass**: for each column, shift non-null cells downward to fill gaps
6. **Fill pass**: for each empty cell at top of column (after gravity), refill with random color from the level's palette
7. If all cells are null → `isWon = true`
8. If no valid moves remain → `isGameOver = true`

**`startNewGame()`:**
- Selects level by mode (daily/standard/puzzle)
- Initializes grid from level data

**Win check (daily):**
```kotlin
if (isWon && mode == "daily") {
    streakRepository?.saveStreak(...)
}
settingsRepository.addWin()
if (mode == "puzzle") completionRepo.markCompleted(puzzleId)
```

## UI (`hexasort/ui/HexaSortScreen.kt`)

**Hexagon rendering:**
- Use Compose `Canvas` to draw hexagonal tiles
- Each hexagon is drawn as a regular hexagon path (6-point polygon)
- Odd rows offset by half-hex-width horizontally
- Color filled with palette color, subtle border

**Interaction:**
- Tap on any hexagon → hit-test against hex positions → call `tapCell`
- On successful remove, flash the group briefly (Animatable alpha pulse)
- Animate gravity with translate transition (simple snap, no smooth fall)

**Layout:**
```
+---------------------------+
| TopAppBar (back, info,    |
|   restart)                |
+---------------------------+
|                           |
|   Hex grid centered       |
|   (fills available        |
|    width, maintains       |
|    aspect ratio)          |
|                           |
+---------------------------+
| Score: N    Moves: M     |
+---------------------------+
```

**Dialogs:**
- Victory: "All hexes cleared!" with Play Again / Back buttons
- Game Over: "No more moves!" with Try Again
- How-to-play: "Tap groups of 2+ same-colored hexes to clear them"

**Hit-testing:**
- On touch, compute which hexagonal cell contains the tap point:
  - Divide tap (x,y) by hex dimensions
  - Determine approximate row and column
  - Check if the tap falls within the hexagon's inscribed circle/area
  - Offset odd rows by half-hex-width

## Pre-generated Data (`hexasort/data/HexaSortPregenerated.kt`)

- 10+ levels across:
  - **Easy** (5×5 grid, 3 colors, generous groups guaranteed)
  - **Medium** (6×6, 4 colors, some isolated singles)
  - **Hard** (7×7, 5 colors, tight groups)
- Each level stores the initial grid as `List<List<Int?>>` where `Int` = color ID
- Implements `BrowseablePuzzle` interface
- `PUZZLES_BY_DIFFICULTY` for PuzzleBrowserScreen
  
**Level design:**
- Levels are generated offline using a script or hand-crafted
- Each level is solvable (verified by a solver that simulates optimal play)
- Color palette: 4-6 distinct colors per level
- Grid dimensions chosen so the hexagons are comfortably tappable on mobile

## Files to Modify

| File | Change |
|------|--------|
| `MainActivity.kt` | Add `"hexasort"` case in `when(gameId)` for `game/{gameId}/{mode}`, `game/{gameId}/{mode}/new`, and `game/{gameId}/puzzle/{puzzleId}` routes; add `hexasort/puzzles` browser route; add import for `HexaSortScreen` |
| `HomeScreen.kt` | Add `Game("hexasort", "Hexa Sort")` to games list |
| `GameDetailScreen.kt` | Add `"hexasort"` case with Random Puzzle + Browse Puzzles + Daily Challenge |

## Files to Create

```
app/src/main/java/com/funkyotc/puzzleverse/hexasort/data/HexaSortModels.kt
app/src/main/java/com/funkyotc/puzzleverse/hexasort/data/HexaSortPregenerated.kt
app/src/main/java/com/funkyotc/puzzleverse/hexasort/viewmodel/HexaSortViewModel.kt
app/src/main/java/com/funkyotc/puzzleverse/hexasort/ui/HexaSortScreen.kt
```

## Estimated Size

~500 lines total across all files.

## Key Design Decisions

1. **Square-grid backing store** — use a rectangular matrix with offset rendering rather than a true hexagonal coordinate system. Simpler to reason about and store as JSON-like data.

2. **Color palette from level** — each level defines its own set of colors so we can vary them. Colors are Int IDs mapped to Compose Colors.

3. **Gravity snap** — cells snap to their new position instantly (no smooth animation for falling). Keeps code simple. Flash animation on removal is adequate feedback.

4. **Random refill** — after removing a group, empty top cells get filled with random colors. This makes the endgame unpredictable and challenging.

5. **Group check on interact** — every tap flood-fills to verify group size >= 2. This is fast enough on small grids (max 7×7 = 49 cells).

## Daily Challenge Eligibility

Yes — Hexa Sort should have daily challenges.

## Why Hexa Sort Fits the "Fake Ad" Vibe

Hexa Sort (or "Hexa Match") is one of the most prevalent fake ad games. The ads show a satisfying honeycomb board where you tap large groups of same-colored hexagons and they pop with a pleasant animation. The ad usually implies it's a brain-training puzzle, while the actual gameplay is straightforward match-clearing. The hexagonal grid makes it visually distinct from every other game in PuzzleVerse.
