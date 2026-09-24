# Game audit implementation verification — 2026-09-24

This is the evidence record for the [active fix plan](game-fix-plan-2026-09-24.md). A screen launch, model win, and hands-on playthrough are different checks.

## Automated checks

| Area | Evidence | Limit |
| --- | --- | --- |
| Flow Free | All 20 checked-in boards have exactly one full-coverage solution with a one-million-step search cap; none truncated | No touch playthrough |
| Chess | All 15 scripts end in actual mate; ViewModel requires mate before a win | No touch playthrough |
| Kakuro | All 24 checked-in clue sets plus fixed digits have exactly one winning assignment; browser model contract checked | No full touch solve |
| Hashi | All 40 regenerated boards are solved by legal bridge actions through the real ViewModel win check | No touch playthrough |
| Water Sort | All 64 generated levels replay a legal winning pour sequence through the real ViewModel | No touch playthrough |
| Shikaku | All 30 checked-in rectangle layouts reach the real ViewModel win state through rectangle actions | Uniqueness and touch playthrough remain unproven |
| Bonza | All 90 catalog puzzles reach the ViewModel win rule through repeated legal hint actions | Drag and snap touch playthrough remains unproven |
| Wood Screws | All 50 levels clear their color-board queues through real bolt-removal actions without tray overflow | Plank-fall physics and final win remain unproven |
| Pull the Pin | Reworked campaign: all 48 intended pin orders win through the shipped physics and capture rules; see [rework record](pullpin-rework-2026-09-24.md) | Full 48-board touch playthrough and physical-device feel remain unverified |
| Hexa Sort | All 24 levels have no singleton color and at least one opening pop. The bounded no-shuffle probe found candidate solutions for 7 levels | Full ViewModel wins, two-shuffle search, and the other 17 levels remain unproven |
| Daily | Fake-clock tests cover same-day cross-instance selection, adjacent-day changes, dated and legacy saves, Home-list cleanup on restart and at midnight, same-game Standard metadata preservation across Daily replay and rollover, 2048 resume sequence, Minesweeper date plus first tap and all 81 safe opening cells, active-run replacement route, and stale-run streak rejection after rollover | The periodic Compose timer and actual on-device midnight transition have not been instrumented |
| Other audit entries | Existing generator/mechanics checks remain for Tangrams and the other games in the audit table | All-level playthrough completion remains unproven where the original audit marked it unproven |

The no-shuffle Hexa Sort probe leaves `Easy_001`, `_004`, `_008`, `Medium_001`, `_002`, `_004`, `_006`–`_008`, and all eight Hard IDs unproven. It excludes the game's two shuffle actions, so these are proof gaps, not established impossible boards. Pull the Pin's reworked 48 levels now have physics-based final-win proof; Wood Screws' 50 levels still need that proof.

The new Hashi solver exposed 36 boards with no connected, noncrossing bridge assignment. The generator had allowed an island to land on an occupied bridge cell. Preventing that placement and regenerating from the existing seeds kept IDs stable; all 40 boards now pass through the ViewModel win rule.

Latest full `testDebugUnitTest`: **148 tests, 0 failures, 10 skipped**. `assembleDebug` and `lintDebug` passed on Windows with JDK 21. CI now requests JDK 21, but no live CI run has been used as an acceptance gate.

## Emulator navigation

Device: Android `Medium_Phone` AVD (`emulator-5554`), debug APK. The route script is [scripts/emulator_route_smoke.py](../scripts/emulator_route_smoke.py); raw and combined results are in `app/build/reports/emulator-route-smoke-full.json` and `emulator-route-smoke-combined.json`. It checks detail, Daily, Standard, and browser entry where available. The first full sweep passed 19 of 20 games; Arrow Escape passed an isolated rerun. A second full sweep passed 18 of 20, while 2048 and Arrow Escape failed menu discovery during an Android “PuzzleVerse isn't responding” prompt. Logcat recorded an ANR with “Application does not have a focused window,” a 56-second Activity launch, and high kernel/sensor-service CPU use. After rebooting the disposable AVD and installing the final debug APK, isolated runs passed 2048 and Arrow Escape. Combined evidence covers all 20 detail, Daily, and Standard entries and 17 browsers, but a single clean 20-game sweep and the cause of the emulator ANR remain unproven. The script handles saved-game prompts only when `PUZZLEVERSE_CLEAR_TEST_SAVES=1` is explicitly set on a disposable test device; it also waits for screen transitions and recognizes Tangrams' custom browser heading. Kakuro browser and a selected Kakuro puzzle opened without the former cast crash.

The [navigation coverage check](../scripts/check_navigation_coverage.py) passed for all 20 main routes, all 20 `/new` dispatches, and all 17 expected browsers. Detail-menu New Game was reached where shown during the route sweep. The five audited end-of-game replay dialogs have not been driven to a win on the emulator, so their tap-to-route behavior remains an interaction gate.

## Gameplay and release limits

A real emulator swipe in Daily 2048 changed the board from two `2` tiles and score 0 to `4` plus `2` and score 4. This checks one game interaction, not a completed game. No complete touch playthrough of all 20 games has been performed. Automated wins for Hashi and Water Sort do not establish gesture accuracy, hint behavior, sound, small-screen layout, save recovery, or visual outcome quality on a device. Hexa Sort and Wood Screws retain the level-specific proof gaps above. Pull the Pin now has a winning-physics proof for its reworked campaign, documented in the linked rework record. A release acceptance pass still needs those proofs or explicit product acceptance of the unproven levels, ANR reproduction on a healthy device, an emulator midnight rollover check, the five replay dialogs, hands-on interactions, and a live JDK-21 CI run.
