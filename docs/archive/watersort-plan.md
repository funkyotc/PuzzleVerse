# Water Sort — Implementation Plan

## Game Mechanics
- N bottles containing stacked colored water layers
- Tap a bottle to select it (picks up top color), tap another to pour
- Can only pour onto same color layer or into empty bottle
- Goal: sort each color into its own bottle
- Levels defined by number of colors + number of empty bottles

## Data Models (`watersort/data/WaterSortModels.kt`)

```kotlin
data class Bottle(val colors: List<Int>) // colors[0] = bottom, last = top, empty list = empty bottle

data class Level(
    val id: String,
    val difficulty: String,
    val bottles: List<Bottle>,
    val numColors: Int
)

data class WaterSortState(
    val level: Level,
    val bottles: List<Bottle>,
    val selectedIndex: Int = -1,
    val moves: Int = 0,
    val isWon: Boolean = false
)
```

Only 2 user actions:
1. `selectBottle(i)` — pick up top color from bottle i
2. `pourBottle(i)` — pour held color into bottle i

Pour rules:
- Bottle must not be full (max height = grid height like 4 or 5)
- Target top color must match held color OR bottle is empty
- After pour, selection is cleared

## ViewModel (`watersort/viewmodel/WaterSortViewModel.kt`)

Standard pattern matching all existing games:

```kotlin
class WaterSortViewModel(
    streakRepository: StreakRepository?,
    mode: String?,
    puzzleId: String?
) : ViewModel() {
    private val _state = MutableStateFlow<WaterSortState?>(null)
    val state: StateFlow<WaterSortState?> = _state.asStateFlow()

    fun selectBottle(index: Int)
    fun pourBottle(index: Int)
    fun startNewGame()
}
```

- **Daily mode**: index into pregenerated levels by `todayEpochDay() % levels.size`
- **Standard mode**: random level from difficulty bucket
- **Puzzle mode**: load by `puzzleId`
- Win → save streak (daily) + `settingsRepository.addWin()` + `completionRepo.markCompleted()` (puzzle)

## UI (`watersort/ui/WaterSortScreen.kt`)

- Tap-to-select, tap-to-pour (no drag, matches fake-ad UX)
- Bottles drawn as vertical rounded rectangles with colored segments via Canvas
- Highlight selected bottle with green border/glow
- Win/game-over dialogs match existing pattern (victory alert with Play Again / Back)
- How-to-play AlertDialog
- TopAppBar with back, info (how-to-play), restart buttons

## Pre-generated Data (`watersort/data/WaterSortPregenerated.kt`)
- Generator-based: builds levels by shuffling complete single-color stacks → every
  puzzle is guaranteed solvable (each color appears exactly `height` times).
- Deterministic seeds so levels are stable across devices/runs (important for Daily/Browse).
- Tiers (overhauled):
  | Tier | Colors | Jars (filled+empty) | Height | Variants |
  |------|--------|----------------------|--------|----------|
  | Easy | 3–4 | 4–6 | 4 | ~16 |
  | Medium | 5–6 | 6–8 | 4–5 | ~12 |
  | Hard | 7–8 | 9–11 | 5 | ~12 |
  | Expert | 9–10 | 11–13 | 5–6 | ~12 |
  | Nightmare | 11–12 | 14–16 | 6 | ~12 |
  (~64 levels total)
- Implements `BrowseablePuzzle` interface for `PuzzleBrowserScreen`
- `PUZZLES_BY_DIFFICULTY: Map<String, List<Level>>` for browser tabs

## Files to Modify

| File | Change |
|------|--------|
| `MainActivity.kt` | Add `"watersort"` case in `when(gameId)` for `game/{gameId}/{mode}`, `game/{gameId}/{mode}/new`, and puzzle routes; add `watersort/puzzles` browser route; add import for `WaterSortScreen` |
| `HomeScreen.kt` | Add `Game("watersort", "Water Sort")` to games list |
| `GameDetailScreen.kt` | Add `"watersort"` case with Random Puzzle + Browse Puzzles + Daily Challenge |

## Files to Create

```
app/src/main/java/com/funkyotc/puzzleverse/watersort/data/WaterSortModels.kt
app/src/main/java/com/funkyotc/puzzleverse/watersort/data/WaterSortPregenerated.kt
app/src/main/java/com/funkyotc/puzzleverse/watersort/viewmodel/WaterSortViewModel.kt
app/src/main/java/com/funkyotc/puzzleverse/watersort/ui/WaterSortScreen.kt
```

## Estimated Size

~400 lines total across all files. Simplest of the three.

## Daily Challenge Eligibility

Yes — Water Sort should have daily challenges (remove from the daily exclusion list in HomeScreen).
