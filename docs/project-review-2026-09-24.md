# Project review — 2026-09-24

Scope: repository structure, build configuration, navigation, persistence and daily-mode paths, existing documentation, and local JVM/build checks. This review did not include device playthroughs or a release build. No game code was changed.

## Findings

1. **High — Flow Free checked-in puzzle data fails its uniqueness/coverage test.** `testDebugUnitTest` ran 122 tests: 1 failed and 10 were skipped. `FlowFreeGeneratorTest.testVerifyFlowFreePuzzles` reports `Medium_6x6_puzzle_001` has **0** full-coverage solutions; the test requires exactly **1**. Inspect the checked-in puzzle and solver together, then regenerate or replace invalid data and rerun the test. Evidence: `app/src/test/java/com/funkyotc/puzzleverse/flowfree/FlowFreeGeneratorTest.kt:44` and `app/build/test-results/testDebugUnitTest/TEST-com.funkyotc.puzzleverse.flowfree.FlowFreeGeneratorTest.xml` (local build output).
2. **High — “Daily” does not always identify a shared daily board.** Minesweeper chooses mine positions with `allPositions.shuffle()` after the first tap (`MinesweeperViewModel.kt:57-75`). 2048 starts with random tiles and uses an undated `daily_tfe_board` save key (`TfeViewModel.kt:24, 45-72`). The UI still offers Daily and records streaks, but its boards are not reproducible across devices from the date alone. If shared daily puzzles are a requirement, define a date-based seed and a daily reset policy for these games, then test them across fresh installations and day boundaries.
3. **Medium — CI Java configuration conflicts with the repository toolchain.** `.github/workflows/build-debug.yml:25` installs Java 11, while `gradle/gradle-daemon-jvm.properties:13` requests JDK 21 and the wrapper is Gradle 9.5.0. The workflow was not executed in this review. Align CI's JDK setup with the project toolchain, then confirm a pull request run passes before treating CI as reliable.
4. **Medium — Existing documentation described an older project.** The previous README presented an initial four-game roadmap and claimed standard mode always generates a random puzzle. `AGENTS.md` said CI was absent, listed a nonexistent `blockpuzzle` game, and described Accompanist navigation and seven themes. The README and contributor guidance have been updated from the current code. `implementation_plan.md` is now labeled as a historical proposal so its tasks are not mistaken for verified status.

## Repository snapshot

- Single Android `:app` module, Kotlin 2.2.10, AGP 9.3.1, Gradle 9.5.0, Compose BOM 2024.06.00, API 24 minimum and API 36 target.
- Twenty games appear in `HomeScreen.games`; `MainActivity` wires game routes and browseable puzzle routes. Seventeen game packages have checked-in `*Pregenerated.kt` puzzle data; Wordle has a word-list asset. 2048 and Minesweeper generate boards at runtime.
- Local state uses `SharedPreferences` repositories for save metadata, individual game boards, streaks, and settings. Theme and sound settings are present.
- `.github/workflows/build-debug.yml` runs unit tests and assembles a debug APK on pull requests and pushes to `master`.

## Verification

| Check | Result |
| --- | --- |
| `testDebugUnitTest` | Failed: 122 tests, 1 failure, 10 skipped; Flow Free puzzle `Medium_6x6_puzzle_001` returned 0 solutions. |
| `assembleDebug` | Passed; all 36 tasks were up to date. |
| `lintDebug` | Not run in this documentation review. |
| `connectedDebugAndroidTest` | Not run; no emulator or device acceptance was performed. |

The local Gradle wrapper initially targeted an unwritable `C:\.gradle` directory. Checks ran after setting `GRADLE_USER_HOME` to the existing user cache at `C:\Users\funky\.gradle`.
