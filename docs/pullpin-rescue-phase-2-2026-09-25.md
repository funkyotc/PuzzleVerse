# Pull the Pin rescue: Phase 2 playable prototype

Date: 2026-09-25 (Australia/Sydney)

Status: Three-level slice implemented and verified through real-session tests and an Android emulator. The Phase 2 gate is **open** until representative physical-device play and user feel acceptance. The rescue campaign has not been expanded.

## Playable entry and scope

Open **PuzzleVerse → Pull the Pin → Play Standard**. The Standard route now opens the rescue prototype. All three levels can be selected from the prototype screen, and a win advances to the next level. Retry rebuilds the same layout and starts immediately. The first start and each level selection show brief rules while the simulation is stopped. Help and app backgrounding pause the fixed-step clock; resume discards elapsed wall time.

Daily and the exact puzzle browser still use the older ball campaign. Rescue progress is in memory and uses `pullpin_rescue_prototype_001` through `_003` IDs; no old completed-ball data is interpreted as rescue progress. The campaign, Daily and save migration belong to Phase 3 after acceptance.

The screen draws every stone, chamber, fixed king, head and pin on one 400 × 700 board. A local head-coverage message and king colour show danger. Tapping a gold handle removes only that pin after the session's 14-tick withdrawal. Distinct pins accept consecutive taps while stones continue moving; removed pins cannot be tapped again. No impact damage or countdown was added.

## Authored geometry and physics traces

All outcomes below use the same `RescueSession` and dyn4j world as live play. A tick is 1/60 simulated second; all 352 initial stones remain accounted for. The test suite asserts the status, stock integrity, no-input timing and meaningful wrong-choice results.

| Level | Intended actions | No input | Intended result | Representative wrong choice |
| --- | --- | --- | --- | --- |
| 1 · Divert | Open the floor basin pin at tick 180 (3 s) | Buried at tick 1229 (20.48 s) | Rescued at tick 3263 (54.38 s) | A tick-1080 pull (18 s) buries the king; Phase 1 trace retained. |
| 2 · Prepare, then divert | Open receiving chamber at tick 90, floor basin at tick 180 | Buried at tick 1213 (20.22 s) | Rescued at tick 3129 (52.15 s) | Floor basin alone backs up and buries the king at tick 2875. Opening the chamber at tick 300 after floor tick 180 recovers and wins. |
| 3 · Choose the safe route | Open floor basin at tick 180; leave king-side opening shut | Buried at tick 1211 (20.18 s) | Rescued at tick 2902 (48.37 s) | Opening the king-side route at tick 120 and floor basin at tick 180 buries him at tick 2227. |

Level 2's added divider narrows the first receiving pocket; its lower removable passage connects to the larger chamber. Level 3's extra opening visibly connects the basin to the king's chamber. The shared finite load, slopes and king location keep each change readable. A two-pin Level 2 trace accepted consecutive actions at ticks 180 and 181 and won. Level 3's correct floor pull at tick 240 also won.

Winning still takes roughly 48–54 simulated seconds while the finite stock flows and settles. This is an open feel question for the user's playtest. The 15–25-second target applies to no-input burial, not a win timer.

## Emulator interaction evidence

On the existing **Medium_Phone** Android 37 x86_64 emulator, installed the debug APK and entered the Standard route through the home screen. Visually inspected the actual screenshots listed below:

- [Start overlay](pullpin-rescue-phase-2-evidence/01-ready.png): board and stock visible before the start button; no hidden initial flow.
- [Uninterrupted stream](pullpin-rescue-phase-2-evidence/02-flow.png) and [burial](pullpin-rescue-phase-2-evidence/03-buried.png): stones travel down the slope and surround the king's head; the loss dialog appears.
- [Level 1 diversion](pullpin-rescue-phase-2-evidence/04-diverted.png): a handle tap removed the floor pin and visibly changed the stream into the basin. The emulator later showed “King rescued.”
- [Level 2 layout](pullpin-rescue-phase-2-evidence/06-level2.png) and [rapid pulls](pullpin-rescue-phase-2-evidence/07-rapid-pulls.png): both handles are separate and visible; two consecutive touch inputs removed both pins while the flow continued. The emulator later showed “King rescued.”
- [Level 3 choice](pullpin-rescue-phase-2-evidence/08-level3-choice.png): the correct basin pin is removed while the king-side route remains visibly closed. The emulator later showed “King rescued.”

This is emulator interaction evidence, not physical-device frame-pacing or a user judgement of feel. No physical Android device was attached during this run. Android performance and memory budgets remain unmeasured on a representative device.

## Automated verification

`gradlew.bat testDebugUnitTest assembleDebug lintDebug --console=plain` passed with the existing Gradle cache. The final JVM report has **174 tests: 164 passed, 10 skipped, zero failures/errors**, including the new ViewModel clock regression. Lint reported zero errors, 36 warnings and three hints. `python scripts/check_navigation_coverage.py` passed: 20 Standard, 20 `/new` and 17 browser routes.

The Gradle unit-test task's former 60-second timeout expired during the expanded full-session suite, so its task limit is now three minutes. The rerun passed in 1m 23s; this limit change does not alter gameplay.

## Remaining Phase 2 gate

Play all three levels on a representative physical Android device, inspect frame pacing/touch reliability, and obtain the user's judgement that the stone flow, urgency, diversion and retry feel right. Revisit the 48–54-second settlement wait if it feels slow. Keep Phase 3 campaign/progression work paused until this acceptance.
