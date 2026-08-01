# Game Development Plan — PuzzleVerse Multi-Game Enhancement & Refinements

This plan outlines the complete, robust architectural and implementation steps to resolve bugs, rework puzzle generators, improve layout scaling, refine animations, and calibrate sound leveling across 11 games and overall app audio.

---

## User Review Required

> [!IMPORTANT]
> - **Game State Persistence (2048 / Sudoku)**: Standardizes saving mechanisms across `Tfe` (2048) and `Sudoku` to ensure progress is strictly restored when pressing "Resume" from the Home or Game Detail screens.
> - **Generator Reworks (Flow Free / Pull the Pin)**: Standalone generator runners (`FlowFreeGeneratorRunner.kt` and `PullPinGeneratorRunner.kt`) will be executed to re-bake updated `*Pregenerated.kt` puzzle datasets into the codebase.
> - **Chess Rework**: Adds Rank/File board annotations, captured piece panels, board orientation flip toggling, move history/hints/undo, and standard vs AI gameplay capabilities.

---

## Proposed Changes

---

### 1. Sudoku (Save State & Resume Fix)

#### [MODIFY] [SudokuRepository.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/sudoku/data/SudokuRepository.kt)
- Fix move detection check: change `hasMoves` from `board.cells.any { it.number != 0 }` (which evaluates to true for unplayed boards with initial clues) to `board.cells.any { !it.isHint && (it.number != 0 || it.pencilMarks.isNotEmpty()) }`.
- Ensure `saveGameState` receives the correct `mode` and `puzzleId` parameter when saving puzzle or standard states.

#### [MODIFY] [SudokuViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/sudoku/viewmodel/SudokuViewModel.kt)
- Align key management (`boardKey`) so standard, daily, and browseable puzzle states load from matching storage keys upon resuming.

---

### 2. Bonza (Piece Gap Spacing)

#### [MODIFY] [BonzaViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/bonza/viewmodel/BonzaViewModel.kt)
- Update `layoutFragmentsWithoutOverlaps()` piece collision detection.
- Inflate placed piece bounding boxes by 1.0 grid unit buffer (`newRect.right + 1 <= placed.left || placed.right + 1 <= newRect.left || newRect.bottom + 1 <= placed.top || placed.bottom + 1 <= newRect.top`) to guarantee at least a 1-unit gap between all scattered pieces on initial puzzle load.

---

### 3. Constellations (Soft Tap Sound Effect)

#### [MODIFY] [SoundManager.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/core/audio/SoundManager.kt)
- Adjust individual relative volume coefficient for `SOUND_ID_GLASS_CHIME` (reduce volume parameter from 0.6f to 0.35f) to eliminate harsh high-frequency tap feedback.

#### [MODIFY] [ConstellationsScreen.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/constellations/ui/ConstellationsScreen.kt)
- Calibrate cell tap sound calls to use the softened audio channel.

---

### 4. 2048 / TFE (Save Game Functionality)

#### [NEW] [TfeRepository.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/tfe/data/TfeRepository.kt)
- Create repository class to serialize `TfeState` (grid tiles, score, highest tile) into `SharedPreferences` and interface with `SaveStateRepository`.

#### [MODIFY] [TfeViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/tfe/viewmodel/TfeViewModel.kt)
- Add `TfeRepository` instance.
- Load saved state on ViewModel initialization if present and `forceNewGame` is false; save game state on every valid tile movement (`move()`); clear saved state on game over or victory.

#### [MODIFY] [TfeScreen.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/tfe/ui/TfeScreen.kt)
- Pass repository into ViewModel factory and handle resume flow.

---

### 5. Nonograms (Solvability Audit)

#### [MODIFY] [NonogramPregenerated.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/nonogram/data/NonogramPregenerated.kt)
- Audit all pregenerated Nonogram puzzles using `NonogramSolver.isSolvableWithoutGuessing()`.
- Replace any non-uniquely-solvable grid matrices with 100% logic-solvable puzzle grids.

---

### 6. Flow Free (Puzzle Generation Rework)

#### [MODIFY] [FlowFreeGeneratorRunner.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/generators/flowfree/FlowFreeGeneratorRunner.kt)
- Replace random single-step walker with a maze-partitioning grid generator that guarantees complete 100% cell coverage, minimum path lengths (>= 3), non-crossing flows, and valid unique solution paths for Easy/Medium/Hard/Expert configurations.

#### [MODIFY] [FlowFreePregenerated.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/flowfree/data/FlowFreePregenerated.kt)
- Re-bake puzzle dataset by executing `FlowFreeGeneratorRunner.kt`.

---

### 7. Shikaku (Sound Softening)

#### [MODIFY] [SoundManager.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/core/audio/SoundManager.kt)
- Reduce gain for `SOUND_ID_LINE_CLEAR` and drag selection clicks used by Shikaku to create smooth, gentle audio feedback.

---

### 8. Pull the Pin (Puzzle Generation Rework)

#### [MODIFY] [PullPinGeneratorRunner.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/generators/pullpin/PullPinGeneratorRunner.kt)
- Replace basic linear cup layout builder with a multi-template procedural generator featuring angled chutes, funnel chambers, color-mixing zones, bomb/hazard pins, and pin-blocking dependencies.

#### [MODIFY] [PullPinPregenerated.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/pullpin/data/PullPinPregenerated.kt)
- Re-generate layout definitions by executing `PullPinGeneratorRunner.kt`.

---

### 9. Water Sort (Board Scaling & Water Audio)

#### [MODIFY] [WaterSortScreen.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/watersort/ui/WaterSortScreen.kt)
- **Board Scaling**: Replace hardcoded 4-column threshold with a balanced multi-row layout calculation (`cols = (bottles.size + 1) / 2` for 2-row layouts; e.g. 5 jars -> 3 top / 2 bottom, 7 jars -> 4 top / 3 bottom). Center each row's items individually to maximize bottle height and eliminate awkward single-bottle bottom rows.
- **Audio**: Connect pouring action to proper fluid pour sound effect.

#### [MODIFY] [liquid_pour.wav](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/res/raw/liquid_pour.wav)
- Replace placeholder audio file with a high-quality realistic liquid pouring water sound effect.

---

### 10. Hexa Stack (Animation Refinement & Placing Bug Fix)

#### [MODIFY] [HexaStackScreen.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/hexastack/ui/HexaStackScreen.kt)
- **Placing Bug Fix**: Fix Jetpack Compose stale state closure bug in `pointerInput` by keying `pointerInput(state)` and reading updated state references during drag/drop hit testing.
- **Animation Refinement**: Fine-tune 3D parabolic arc elevation scaling, tile flip rotation, shadow rendering, and pop particle timing for smooth tile merging.

#### [MODIFY] [HexaStackViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/hexastack/viewmodel/HexaStackViewModel.kt)
- Ensure `_state.value` consistently updates tray slots when new tiles are dealt from `spawnDeck`.

---

### 11. Chess (Overall Refinement & Game Rework)

#### [MODIFY] [ChessScreen.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/chess/ui/ChessScreen.kt)
- Add Rank (1-8) and File (a-h) algebraic labels along the outer board borders.
- Add captured piece tray displaying captured white and black pieces.
- Add board flip orientation toggle (allow viewing from Black perspective).
- Add visual indicators for check, checkmate, and hint highlights.

#### [MODIFY] [ChessViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/chess/viewmodel/ChessViewModel.kt)
- Add move history tracking, move undo functionality, and hint suggestion system.
- Integrate standard Chess mode option vs local min-max AI engine using `chesslib`.

---

### 12. Overall Audio Leveling

#### [MODIFY] [SoundManager.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/core/audio/SoundManager.kt)
- Implement a comprehensive sound volume normalization map `SOUND_VOLUMES: Map<Int, Float>` covering all 21 sound IDs.
- Normalize peak and RMS gains across clicks, drops, slides, pops, chimes, and victories so no sound effect is jarringly loud or quiet.

---

## Verification Plan

### Automated Tests
- Run Gradle unit tests to ensure all game models, solvers, logic engines, and generators build and pass cleanly:
  ```powershell
  gradlew.bat testDebugUnitTest
  ```

### Manual Verification
- Verify app compilation and build debug APK:
  ```powershell
  gradlew.bat assembleDebug
  ```
- Test each modified game in emulator/device:
  - **Sudoku**: Make moves, press back, press Resume -> confirm board resumes exactly.
  - **Bonza**: Start puzzle -> confirm all scattered pieces have >= 1 unit gap between them.
  - **Constellations**: Tap stars -> verify soft, pleasant tap sound.
  - **2048**: Play moves, exit to menu, press Resume -> verify board & score resume.
  - **Nonograms**: Solve puzzles -> verify all pregenerated nonograms are logic solvable without guessing.
  - **Flow Free**: Browse & play generated puzzles -> verify complete grid coverage and valid non-crossing flows.
  - **Shikaku**: Clear region -> verify smooth, soft line clear sound.
  - **Pull the Pin**: Play levels -> verify rich, varied funnel & hazard pin layouts.
  - **Water Sort**: 5-jar level -> verify 3 top / 2 bottom layout with large bottles & realistic watery pour sound.
  - **Hexa Stack**: Place > 3 stacks -> verify tray continues to spawn new stacks without freezing; test 3D merge flip animation.
  - **Chess**: Test rank/file labels, flip board, hint button, undo, and captured piece tray.
  - **Overall Sound**: Play various games -> verify consistent, balanced relative audio volume across all sound effects.
