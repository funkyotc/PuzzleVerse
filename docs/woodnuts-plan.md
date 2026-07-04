# Wood Nuts & Bolts — Implementation Plan

## Game Mechanics
- Wooden planks arranged on a board, held by bolts at their corners
- Tap a bolt to unscrew it (instant removal)
- When the last bolt holding a plank is removed, the plank falls off
- Chain reactions: removing one plank may expose other bolts
- Goal: remove all planks from the board

## Data Models (`woodnuts/data/WoodNutsModels.kt`)

```kotlin
data class Bolt(
    val id: String,
    val row: Int,       // grid row position
    val col: Int,       // grid col position
    val removed: Boolean = false
)

data class Plank(
    val id: String,
    val startRow: Int,
    val startCol: Int,
    val endRow: Int,
    val endCol: Int,
    val boltIds: List<String>,  // bolts holding this plank
    val removed: Boolean = false
)

data class WoodNutsLevel(
    val id: String,
    val difficulty: String,
    val rows: Int,
    val cols: Int,
    val bolts: List<Bolt>,
    val planks: List<Plank>
) : BrowseablePuzzle

data class WoodNutsState(
    val level: WoodNutsLevel,
    val bolts: List<Bolt>,
    val planks: List<Plank>,
    val moves: Int = 0,
    val isWon: Boolean = false,
    val lastRemovedPlankId: String? = null
)
```

**Support check logic:**
- A plank is supported if ANY of its `boltIds` references a non-removed bolt
- When a bolt is removed, check all planks referencing it
- Any plank now fully unsupported → remove it immediately (chain reaction)

## ViewModel (`woodnuts/viewmodel/WoodNutsViewModel.kt`)

```kotlin
class WoodNutsViewModel(
    streakRepository: StreakRepository?,
    mode: String?,
    puzzleId: String?
) : ViewModel() {
    private val _state = MutableStateFlow<WoodNutsState?>(null)
    val state: StateFlow<WoodNutsState?> = _state.asStateFlow()

    fun removeBolt(boltId: String)
    fun startNewGame()
}
```

- `removeBolt(boltId)`:
  1. Mark bolt as removed
  2. For each plank referencing this bolt: check if still supported
  3. If not supported → mark plank removed, record `lastRemovedPlankId`
  4. Repeat step 2-3 until no more planks become unsupported (chain reaction)
  5. If all planks removed → `isWon = true`

- `startNewGame` → picks level by mode (daily/standard/puzzle)

## UI (`woodnuts/ui/WoodNutsScreen.kt`)

- Grid-based rendering:
  - **Planks**: colored rounded rectangles spanning their cell range. Each plank gets a distinct color from a palette.
  - **Bolts**: circles at grid intersections within planks. Tap target.
  - Removed planks fade out with slide animation (Animatable).
  - Removed bolts disappear.
- Tap on a bolt to unscrew it
- Victory alert when all planks gone
- How-to-play dialog
- TopAppBar with back, info, restart

## Pre-generated Data (`woodnuts/data/WoodNutsPregenerated.kt`)

- 10+ levels across:
  - Easy (3×3 grid, 2-3 planks, solution in 1-2 steps)
  - Medium (4×4 grid, 4-6 planks, branching)
  - Hard (5×5 grid, 7+ planks, deep chain reactions)
- Implements `BrowseablePuzzle`
- `PUZZLES_BY_DIFFICULTY` for browser

**Level design approach:**
- Planks must not overlap (each cell owned by at most 1 plank)
- Bolts placed at intersections adjacent to planks
- At least 1 bolt per plank
- Optimal solve path = remove bolts in correct order

## Files to Modify

| File | Change |
|------|--------|
| `MainActivity.kt` | Add `"woodnuts"` case in `when(gameId)` for `game/{gameId}/{mode}`, `game/{gameId}/{mode}/new`, and puzzle routes; add `woodnuts/puzzles` browser route; add import for `WoodNutsScreen` |
| `HomeScreen.kt` | Add `Game("woodnuts", "Wood Nuts & Bolts")` to games list |
| `GameDetailScreen.kt` | Add `"woodnuts"` case with Random Puzzle + Browse Puzzles + Daily Challenge |
| `PuzzleBrowserScreen.kt` | Add `"woodnuts"` → `"🪵"` in `GameTypePreview` |

## Files to Create

```
app/src/main/java/com/funkyotc/puzzleverse/woodnuts/data/WoodNutsModels.kt
app/src/main/java/com/funkyotc/puzzleverse/woodnuts/data/WoodNutsPregenerated.kt
app/src/main/java/com/funkyotc/puzzleverse/woodnuts/viewmodel/WoodNutsViewModel.kt
app/src/main/java/com/funkyotc/puzzleverse/woodnuts/ui/WoodNutsScreen.kt
```

## Estimated Size

~500 lines total across all files.

## Key Design Decisions

1. **One-tap bolt removal** — no multi-tap strength mechanic. Keeps it simple and fast.
2. **No real physics** — planks just disappear (fade) instead of physical falling. Much simpler.
3. **Plank colors** — each plank gets a unique color from a pre-defined palette so they're visually distinct.
4. **Grid intersections** — bolts exist at intersection points, not cells. A bolt at (r,c) can hold planks that use that corner.

## Daily Challenge Eligibility

Yes — Wood Nuts & Bolts should have daily challenges.
