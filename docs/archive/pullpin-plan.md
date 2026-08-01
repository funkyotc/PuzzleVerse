# Pull the Pin — Implementation (Reworked: continuous physics)

## Overview
Pull the Pin was reworked away from the grid system to a **continuous 2D physics**
implementation backed by **dyn4j 5.0.2** (already a project dependency, also used by
WoodNuts). Puzzles are baked Kotlin data (no runtime grid generation).

## Coordinate system
Virtual world: `WORLD_W = 400f`, `WORLD_H = 700f` (portrait, y grows downward,
bottom = floor). The UI scales the world to fit the screen.

## Entities (`pullpin/data/PullPinModels.kt`)
- `WallSegment(x, y, w, h)` — static wall rectangle (top-left).
- `CupData(id, x, y, radius, color)` — colored cup on the floor; a matching-color ball
  resting inside is captured (x,y is center). Color 1..8.
- `PinData(id, x, y, w, h, pullDx, pullDy, removed, isPulling)` — removable blocking
  rectangle; pulling it slides it out (pullDx/pullDy) then removes it from physics.
- `BallSpawn(id, x, y, color, radius)` — initial ball (center). Color 0 = grey.
- `PullPinLevel(id, difficulty, walls, cups, pins, balls)` — implements `BrowseablePuzzle`.
- `BallRuntime(id, x, y, color, inCup, captured, outOfBounds)` — live ball state.
- `PullPinState(level, balls, pins, moves, status, lostReason)`; `GameStatus { IDLE, RUNNING, WON, LOST }`.

## Physics (`pullpin/physics/PullPinPhysicsEngine.kt`)
Wraps `org.dyn4j.world.World<Body>`:
- Static `Body` rectangles for walls and (non-removed) pins (`MassType.INFINITE`).
- Dynamic circle `Body` for each ball (`MassType.NORMAL`, gravity +900 in +y).
- `initWorld(level)`, `removePin(id)`, `step(dt)`, `getBallTransforms()`,
  `isBallOutOfBounds(id)`.
Deterministic given identical step order; no random slide (replaces old
`Random().nextBoolean()` behavior).

## ViewModel (`pullpin/viewmodel/PullPinViewModel.kt`)
- `viewModelScope` loop (~60 FPS) steps the engine, syncs ball positions, merges
  grey→colored balls on contact, detects cup capture / wrong-color / grey-in-cup /
  out-of-bounds, win and settle-to-IDLE. Mirrors `WoodNutsViewModel`.
- `removePin(id)` → slide-out animation (220ms) → `engine.removePin`.
- Daily streak (`"pullpin"`), puzzle completion (`PuzzleCompletionRepository("PullPin")`),
  mode routing (standard/daily/puzzle/easy..expert).

## UI (`pullpin/ui/PullPinScreen.kt`)
`StandardGameLayout` + `Canvas` rendering the world scaled to screen. Draws walls,
cups, balls, and animated pull-out pins. Tap a pin to remove it. Keeps how-to/win/lose
dialogs, color legend, and stats row.

## Data (`pullpin/data/PullPinPregenerated.kt`)
48 baked levels (12 Easy / 12 Medium / 12 Hard / 12 Expert) built programmatically with
a solvable layout: each cup has a matching-color ball held by a pin directly above it
(straight-down drop), dividers keep balls in their column; Hard/Expert add a grey ball
above each (colored on contact). `ALL_LEVELS` + `PUZZLES_BY_DIFFICULTY`.

## Generator (`scripts/generate_pullpin.py`)
Python generator + solver (straight-down solvability check) that emits per-level JSON
and a baked Kotlin file. Output is reproducible via seeded RNG. The baked data in
`PullPinPregenerated.kt` was produced/verified from this layout.

## Modes / Routes (unchanged)
`game/pullpin/{mode}`, `game/pullpin/{mode}/new`, `pullpin/puzzles`,
`game/pullpin/puzzle/{id}`. Streak key `"pullpin"`, completion key `"PullPin"`.
