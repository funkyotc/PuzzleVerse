# Pull the Pin — Implementation Plan

## Game Mechanics
- Grid board with colored balls, matching colored cups, walls, and removable pins
- Tapping a pin removes it
- When pins are removed, balls fall/roll into matching cups via gravity
- Goal: each colored ball lands in its matching cup
- All balls must be in cups to win

## Simplified Physics (No Physics Engine)

Instead of real-time physics, use **deterministic grid-based gravity simulation**:

- Board is an N×M grid of cell types: `EMPTY`, `WALL`, `PIN`, `BALL(color)`, `CUP(color)`
- When a PIN is removed, run a cascade: for each ball not yet in a cup, try DOWN first, then LEFT, then RIGHT, repeating until stable
- Balls stop at cups, walls, other balls, or the grid edge
- Cascade runs synchronously — no real-time game loop needed

## Data Models (`pullpin/data/PullPinModels.kt`)

```kotlin
enum class CellType { EMPTY, WALL, PIN, BALL, CUP }

data class Cell(val type: CellType, val color: Int? = null)

data class PullPinLevel(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val grid: List<List<Cell>>
) : BrowseablePuzzle

data class BallState(
    val row: Int,
    val col: Int,
    val color: Int,
    val inCup: Boolean = false
)

data class PullPinState(
    val level: PullPinLevel,
    val grid: List<List<Cell>>,
    val balls: List<BallState>,
    val removedPins: Set<String>, // "row,col" keys
    val moves: Int = 0,
    val isWon: Boolean = false
)
```

## Cascade Simulation Logic

`simulateCascade(grid, balls) -> Pair<List<List<Cell>>, List<BallState>>`

Algorithm:
1. For each ball not `inCup`:
   a. Try DOWN: if cell is EMPTY, move ball down one row
   b. If DOWN blocked, try LEFT, then RIGHT
   c. If any move succeeds, continue from new position
   d. Repeat until no move possible in a full pass
   e. If ball lands on a matching CUP cell → mark `inCup = true`
2. Repeat the outer loop until a full pass makes no changes

## ViewModel (`pullpin/viewmodel/PullPinViewModel.kt`)

```kotlin
class PullPinViewModel(
    streakRepository: StreakRepository?,
    mode: String?,
    puzzleId: String?
) : ViewModel() {
    private val _state = MutableStateFlow<PullPinState?>(null)
    val state: StateFlow<PullPinState?> = _state.asStateFlow()

    fun removePin(row: Int, col: Int)
    fun startNewGame()
}
```

- `removePin` → marks pin as removed in grid, runs cascade, checks win
- `startNewGame` → picks level by mode (daily/standard/puzzle)
- Win → streak save (daily) + `addWin()` + `markCompleted()` (puzzle)

## UI (`pullpin/ui/PullPinScreen.kt`)

- Grid rendered with Compose (scaled to fit screen width)
- Color legend:
  - BALL → colored circle with label
  - CUP → outlined rounded rect with same color
  - PIN → small circle with dark outline, tap target
  - WALL → dark filled cell
  - EMPTY → transparent
- Tap on PIN cells to remove them
- Animate pin removal with short fade-out (Animatable)
- Animate ball falling with spring (offset + scale)
- Win alert / game dialogs matching existing pattern
- How-to-play dialog describing mechanics
- TopAppBar with back, info, restart

## Pre-generated Data (`pullpin/data/PullPinPregenerated.kt`)

- 10+ levels across Easy (5×5, 1-2 balls, few pins, open layout), Medium (6×6, 2-3 balls, more walls), Hard (7×7, 3-4 balls, complex walls), Expert (8×8)
- Implements `BrowseablePuzzle`
- `PUZZLES_BY_DIFFICULTY` for browser
- Grid encoded as `List<List<Cell>>` — compact string representation in source

## Files to Modify

| File | Change |
|------|--------|
| `MainActivity.kt` | Add `"pullpin"` case in `when(gameId)` for `game/{gameId}/{mode}`, `game/{gameId}/{mode}/new`, and puzzle routes; add `pullpin/puzzles` browser route; add import for `PullPinScreen` |
| `HomeScreen.kt` | Add `Game("pullpin", "Pull the Pin")` to games list |
| `GameDetailScreen.kt` | Add `"pullpin"` case with Random Puzzle + Browse Puzzles + Daily Challenge |
| `PuzzleBrowserScreen.kt` | Add `"pullpin"` → `"📌"` in `GameTypePreview` |

## Files to Create

```
app/src/main/java/com/funkyotc/puzzleverse/pullpin/data/PullPinModels.kt
app/src/main/java/com/funkyotc/puzzleverse/pullpin/data/PullPinPregenerated.kt
app/src/main/java/com/funkyotc/puzzleverse/pullpin/viewmodel/PullPinViewModel.kt
app/src/main/java/com/funkyotc/puzzleverse/pullpin/ui/PullPinScreen.kt
```

## Estimated Size

~600 lines total. The cascade simulation and grid rendering are the bulk.

## Key Risks

- Cascade simulation must terminate (non-infinite loops) — use iteration cap
- Some levels may have unsolvable layouts — design levels carefully
- On mobile, small grid cells need good tap targets (min 44dp)

## Daily Challenge Eligibility

Yes — Pull the Pin should have daily challenges.
