# PuzzleVerse game audit — 2026-09-24

> **Correction after implementation:** The Flow Free solver's default 2,000-step limit returned zero when its search was truncated. Rechecking every board with a one-million-step limit and an explicit truncation flag found exactly one full-coverage solution for all 20 boards. The original finding below records what the initial audit observed; the boards were not replaced.

## Scope and evidence

This audit covers all 20 Home games, their detail menus, Standard and Daily launch routes, puzzle browser routes, daily selection, and the available generator checks. I built the debug APK, ran the JVM suite and lint, installed the APK on the `Medium_Phone` emulator (1080 × 2400), and opened each Standard screen. I also checked Daily and puzzle-browser entry points from the detail menus. These launch checks do not prove full gameplay, touch accuracy, wins, save recovery, or a date rollover. No game code or puzzle data was changed.

`testDebugUnitTest`: 122 tests, 1 failure, 10 skipped. The failure is `FlowFreeGeneratorTest.testVerifyFlowFreePuzzles`. `assembleDebug` and `lintDebug` passed. Lint produced 36 warnings and 3 hints, with no errors. The checked-in Android instrumentation test is a template, not a 20-game playthrough.

## Findings, in repair order

1. **Critical — Flow Free has 14 unfinishable checked-in boards.** I ran the repository's `FlowSolver.countFullCoverageSolutions(maxSolutions = 2)` over all 20 `FlowFreePregenerated.ALL_PUZZLES`. The result was 6 boards with exactly one solution and 14 with zero. All five Easy boards and `Medium_6x6_puzzle_002` passed. `Medium_6x6_puzzle_001` and `_003`–`_005`, all five Hard, and all five Expert boards returned zero. The regular test stops at the first failure, masking the other 13. Since Daily cycles through this same list, 14 of each 20 selected boards are unsolvable under the game's full-coverage rule. Evidence: `flowfree/data/FlowFreePregenerated.kt`, `flowfree/data/FlowFreePuzzleLibrary.kt`, `FlowFreeGeneratorTest.kt:44`, and the temporary exhaustive solver run. Replace or regenerate the invalid boards, then keep a per-board assertion in the permanent suite.
2. **Critical — Seven Chess puzzles declare a false checkmate.** Replaying each listed solution with the project chess library left the final board **not mated** for `Chess_Easy_002`, `_003`, `_005`, `Chess_Medium_003`, `_004`, `Chess_Hard_001`, and `_003` (7 of 15). `ChessViewModel.kt:180-191` declares a win and shows `Checkmate!` when the scripted move list ends, without checking the board. The permanent `ChessSolvabilityTest.kt:16-53` only checks move legality. Correct or replace those puzzles, assert final mate for every entry, and require mate before showing the win message.
3. **Critical — Kakuro puzzle browser crashes.** Opening Browse Puzzles on the emulator terminates the app with `ClassCastException: PregeneratedKakuro cannot be cast to BrowseablePuzzle` at `MainActivity.kt:397`. `PregeneratedKakuro` does not implement the interface (`kakuro/data/KakuroPregenerated.kt:3-10`), while the route force-casts every item. The browser cannot be used until the model implements the browser contract or is mapped into a browseable adapter. This was reproduced twice and confirmed in the emulator crash log.
4. **High — Five Daily modes select an ordinary random board.** Nonogram and Kakuro ignore `mode == "daily"` in `startNewGame()` and call their random libraries. Arrow Escape's Daily branch chooses a random Medium puzzle. Minesweeper shuffles mine positions without a date seed after the first tap. 2048 also starts and spawns random tiles. Thus **five** Daily modes lack a date-defined board (Nonogram, Kakuro, Arrow Escape, Minesweeper, 2048). Evidence: `nonogram/viewmodel/NonogramViewModel.kt:27-47`, `kakuro/viewmodel/KakuroViewModel.kt:31-39`, `arrowescape/ui/ArrowEscapeViewModel.kt:65-74`, `minesweeper/viewmodel/MinesweeperViewModel.kt:57-66`, `tfe/viewmodel/TfeViewModel.kt:45-61`.
5. **High — Some saved Daily boards survive into a new UTC day.** Sudoku, Shikaku, 2048, and Hexa Sort use fixed Daily save keys and restore a nonempty saved board without checking its date. Hexa Sort accepts an old grid when its dimensions match today's selected level, even if colors or layout differ. Hexa Stack also uses a fixed key, though it checks the saved level ID against today's level; a repeated selection can still restore older progress. Evidence: `SudokuViewModel.kt:31,54-67`, `ShikakuViewModel.kt:33,59-68`, `TfeViewModel.kt:24,29-39`, `HexaSortViewModel.kt:35-75`, `HexaStackViewModel.kt:39-78`. Save the epoch day with Daily progress and reject a save from a different day.
6. **High — End-of-game replay can open a placeholder.** Daily replay in Constellations, Nonogram, Kakuro, and Flow Free navigates to `game/<id>/standard/new`; Hashi's Standard replay uses the same route. `MainActivity.kt:224-244` has no `/new` case for those IDs and falls through to `GameScreen`, which displays placeholder text. Evidence: the respective `*Screen.kt` `onPlayAgainClick` handlers and `ui/screens/game/GameScreen.kt:25-54`. Route those actions to a supported fresh game or add proper `/new` cases.
7. **Medium — Generator correctness is unevenly tested.** Nonogram's 40 checked-in grids pass its logic-solvability test, Cube Shooter tests its checked-in levels, and Hexa Stack has a greedy win test for all 70 levels. Pull the Pin's 48-level test checks counts, bounds, and nonempty lists, not a winning pin order. Kakuro tests nonempty clues and inputs, not that sums are solvable or unique. Chess tests legality of each listed move but misses the seven false mates above. Hashi's Daily win test succeeds by directly recording a streak when its greedy bridge attempt fails. Hexa Sort and Wood Screws test mechanics but lack all-level completion proof. These gaps mean a green JVM suite would not establish every generator is correct.
8. **Medium — Daily rollover and naming need a product decision.** `core/DateUtils.kt:6-8` uses UTC; in Sydney a new Daily begins around 10:00 or 11:00 local time. Date-seeded random selection in Wordle, Hexa Sort, and Hexa Stack can select the same puzzle on adjacent days by chance. The detail screen builds its title from the ID, yielding names such as `Flowfree`, `Pullpin`, and `Woodnuts` instead of the Home labels. Evidence: `WordleViewModel.kt:38-39`, `HexaSortViewModel.kt:90-94`, `HexaStackViewModel.kt:98-102`, `GameDetailScreen.kt:55`.

## Per-game assessment

“Opens” means the emulator displayed the game screen from its menu. “Date selected” means the code chooses a board from the UTC day, but it does not guarantee a different board from yesterday or a valid level. “Unproven” means the present tests do not establish every shipped level is completable.

| Game | Standard menu/screen | Daily selection | Level data and validation |
| --- | --- | --- | --- |
| Sudoku | Opens; browser present | Seeded by UTC day; stale saved board risk | Generator and save tests, but no full device solve |
| Bonza | Opens; browser present | Date-seeded generator | Fragment layout and interaction unit tests; full solve unproven |
| Constellations | Opens; browser present | Date-seeded generator | Generator tests pass; four UI/ViewModel tests skipped; Daily replay broken |
| Wordle | Opens; no browser | Date-seeded word-list pick; adjacent repeats possible | Guess evaluation tests; word-list selection not a full daily rollover test |
| 2048 | Opens; no browser | Unseeded tiles; stale Daily save | Mechanics and save tests; no shared board |
| Minesweeper | Opens at Easy/Medium/Hard; no browser | Unseeded mine shuffle after first tap | Basic logic tests; no reproducible Daily board |
| Nonogram | Opens; browser present | Random puzzle on each launch | All 40 pregenerated grids pass logic solver; Daily replay broken |
| Kakuro | Opens; browser crashes | Random puzzle on each launch | Checked-in puzzles only checked for nonempty clue/input counts; Daily replay broken |
| Flow Free | Opens; browser present | UTC index into 20 boards | 14 of 20 boards fail full-coverage solver; Daily replay broken |
| Shikaku | Opens; browser present | Date-seeded generator; stale save risk | Generator shape tests; all-board uniqueness unproven |
| Cube Shooter | Opens at Easy/Medium/Hard; browser present | UTC index into checked-in levels | All-level solvability test passes |
| Pull the Pin | Opens; browser present | UTC index into 48 levels | Bounds and physics tests pass; winning order for every layout unproven |
| Water Sort | Opens; browser present | UTC index into checked-in levels | Bottle mechanics tested; all-level solve unproven |
| Wood Screws | Opens; browser present | UTC index into checked-in levels | Generated layouts and physics exist; all-level solve unproven |
| Hexa Sort | Opens; browser present | Date-seeded library pick; stale grid risk | Pop/gravity mechanics tested; all-level solve unproven |
| Hexa Stack | Opens; browser present | Date-seeded library pick; repeated ID can restore old save | Greedy test wins all 70 checked-in levels |
| Chess | Opens; browser present | UTC index into 15 puzzles | Seven scripted solutions do not checkmate; false win message |
| Hashi | Opens; browser present | UTC index into Medium levels | Bridge rules tested; all-level solve unproven; Standard replay broken |
| Arrow Escape | Opens; browser present | Random Medium puzzle | Generator tests pass; Daily board changes within same day |
| Tangrams | Opens; browser present | UTC index into checked-in silhouettes | Validator/unit tests; full visual placement solve unproven |

## Menu and UI consistency

All 20 games use `StandardGameLayout` for the game top bar. The shared detail screen offers a Daily Challenge for all 20, Browse Puzzles for 17, and a Standard entry point. Wordle, 2048, and Minesweeper intentionally have no browser. Minesweeper and Cube Shooter present difficulty cards in place of a single Standard card. All 20 Standard and all 20 Daily game screens opened on the emulator. Sixteen of 17 puzzle browsers opened; the Kakuro browser crashes. The run did not systematically exercise hints, gestures, win/loss dialogs, sound, or small-screen layout.

## Verification limits and next gate

After repairing Flow Free data and Daily selection/save behavior, add deterministic tests for same-day replay and the UTC day boundary, plus all-level solve checks for the flagged generators. Then run scripted emulator paths and actual interaction playthroughs for all 20 games. The current checks establish screen launches and selected model invariants, not that every level is playable end to end.
