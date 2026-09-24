# Pull the Pin rescue: Phase 0 replacement boundary

Date: 2026-09-24

Scope: Phase 0 of the [rescue development plan](pullpin-rescue-development-plan-2026-09-24.md). Source baseline: `8c6cc2d` (`docs(pullpin): plan king rescue gameplay redesign`), initially clean working tree. This checkpoint changes documentation only. Rescue gameplay, migration code and new levels are not implemented.

## Inspected replacement boundary

Paths below are relative to `app/src/main/java/com/funkyotc/puzzleverse/` unless stated otherwise.

| Area | Current evidence | Implementation decision |
| --- | --- | --- |
| `pullpin/data/PullPinModels.kt` | 400 × 700 drawing world; walls, balls, cups, pins with rescue-count locks; solution is only an ordered pin-ID list | Keep coordinates and wall geometry. Introduce explicit stone stock, king/body/head geometry, survival objective and tick-stamped traces. Remove colour, cup, bomb and lock rules from rescue types. |
| `pullpin/physics/PullPinPhysicsEngine.kt` | dyn4j 5.0.2; drawing units divided by 100; gravity +9 m/s²; circles and rotated rectangular walls; static pins removed by ID | Retain dyn4j, scaling and geometry conventions. Add stone velocity/support evidence and fixed king collision geometry. Measure dense stock before increasing level count. No stone capture/deletion as a survival shortcut. |
| `pullpin/physics/PullPinSession.kt` | Fixed 1/60-second steps; independent pending pin map; each pull removes collision at accepted tick +14; duplicate and terminal pulls rejected | Retain independent pull ownership and accepted-action ticks. Replace capture/colour/bomb rules with local sustained burial and sustained whole-load settling; loss before win. |
| Session start/settling | Physics already steps before the first pull even though status is `IDLE`; returns early when all bodies sleep and no pull is pending | Introduce explicit ready/start ownership. Once active, advance rule timers even if physics sleeps, so settled and burial intervals can finish. Automatic danger must start only after the board is ready. |
| `pullpin/viewmodel/PullPinViewModel.kt` | Calls one fixed step then `delay(16)`; no elapsed-time accumulator; coroutine starts immediately; pause/background flags; reconstructs on retry | Replace wall-time-dependent pacing with a monotonic fixed-step driver, resetting elapsed-time ownership on pause/resume. Keep retry reconstruction. Gate stepping and input on board readiness and blocking overlays. |
| Undo | Reconstructs and replays accepted actions to the tick immediately before the last action | Prefer removing Undo from the three-level prototype. If later retained, replay must reconstruct burial exposure, settling, pending pulls and elapsed ticks exactly. Retry is always available, including a no-input loss. |
| `pullpin/ui/PullPinScreen.kt` | Aspect-fit Canvas, latest-state tap callbacks, padded targets, per-pin accessibility actions, lifecycle observer and help/hint dialogs | Keep layout/input conventions. Replace ball/cup graphics, collection sounds, lock labels, legends, hints, semantics and outcome text. Render the king from the collision/head definition. |
| Pin visual timing | UI uses a 220 ms Compose tween; collision removal uses 14 simulation ticks (~233 ms), and the tween continues while simulation is paused | Drive withdrawal progress from the same simulation ticks as collision removal. Existing animation ownership is reusable, but visual/collision alignment is not proven. |
| Pause timing | Help/hint pause is applied in `LaunchedEffect`; `removePin` does not check pause flags | Pause synchronously before displaying blocking overlays and reject gameplay actions while blocked. Keep lifecycle handling, with no wall-clock catch-up after resume. |
| `pullpin/data/PullPinPregenerated.kt` | 48 recipe-built ball levels, 12 each in Easy/Medium/Hard/Expert | Preserve old campaign during the initial isolated physics slice. Author one rescue layout in Phase 1, three in Phase 2. No expansion or 48-level requirement before user feel acceptance. |
| Routes | `MainActivity.kt` has Standard/Daily, `/new`, browser and exact-ID routes | Keep public `pullpin` route identity. Phase 3 must change browser data and gameplay together, plus handle retired IDs. The screen's `forceNewGame` parameter is unused and the Pull the Pin `/new` branch does not pass it: define and test new-game semantics explicitly. |
| Validators | Kotlin runner simulates old solutions with 300 ticks between pulls; `scripts/generate_pullpin.py` invokes `PullPinCampaignTest` | Both are already validators, not data writers. Migrate them to timed rescue traces in Phase 3; preserve that non-overwriting property. |

## Storage policy selected for implementation

The current completion store is `PullPinPuzzleCompletion`, created by `PuzzleCompletionRepository(context, "PullPin")`. Each key is a level ID such as `pullpin_easy_001`, with Boolean `true`. Standard selects the first uncompleted level; browser completion reads the same store. There is no persisted campaign index.

The current Pull the Pin ViewModel does not serialize or restore physics state. Shared app metadata can nevertheless exist in `PuzzleVerseSaveStates`: `save_pullpin` and `standard_save_pullpin` contain `SaveStateMetadata` JSON (`gameId`, `mode`, `puzzleId`, `timestamp`, nullable `jsonState`, nullable `challengeEpochDay`). `active_save_games` is a shared set. The generic `/new` route writes metadata; the detail screen uses it to construct a resume route. Treat old metadata as a possible retired route, never as a recoverable rescue simulation.

Use these rules when integrating the rescue campaign:

1. Assign stable IDs such as `pullpin_rescue_v1_001`; keep prototype IDs distinct (`pullpin_rescue_prototype_001`) and do not award production completion for prototype runs. Do not reuse old ball IDs or automatically copy completion flags.
2. Retain the existing `PullPinPuzzleCompletion` preference file, using versioned IDs as the completion namespace. This preserves the browser's `gameName = "PullPin"` and icon mapping without changing the shared repository. Both browser and gameplay must query the new IDs only. Keep legacy flags untouched for reversibility.
3. At campaign activation, use a Pull the Pin-specific version marker (for example `campaign_version = 1` in `PullPinRescueMigration`). Before marking migration complete, call the existing `SaveStateRepository.clearSaveState("pullpin")` to retire both old metadata slots and remove only `pullpin` from the shared active set. Make this ordered and idempotent; never clear all app saves. Run this before offering Resume on the game detail screen, not just after loading gameplay.
4. Handle a retired exact puzzle ID explicitly with a short replacement notice and a route to the rescue browser. Do not silently reinterpret it as a new level or allow `selectLevel` to throw. Valid new exact IDs must retain exact selection.
5. Preserve `StreakPrefs` history for game ID `pullpin`, including existing same-day credit; upgrading must neither reset a legitimate streak nor award a second credit. Campaign completion is separately versioned. Credit new rescue Daily wins only on a real terminal victory for the current UTC day.
6. Preserve all other games' completion, saves and streak keys. Add migration regressions using mixed old/new Pull the Pin entries and unrelated-game sentinels. Repeating migration must leave new rescue metadata intact.

Daily selection currently uses `floorMod(UTC epoch day, level count)`. Keep deterministic selection over a fixed, versioned campaign order. The route-scoped streak repository is bound to the day the route opened, while the ViewModel can replace its level at midnight; Phase 3 must test the real route/repository combination so a new-day victory is not silently rejected by an old-day repository. Do not change the shared streak rules merely to bypass this guard.

## Test replacement and next implementation scope

Inspected all five Pull the Pin test classes under `app/src/test/java/com/funkyotc/puzzleverse/pullpin/`:

- Retain/adapt the physics fall, wall, pin removal and out-of-bounds checks.
- Replace cup-colour preconditions, bomb/contact-colouring/lock rules and the 48-level assertions with rescue rules as the old experience is replaced.
- Retain retry/pending-pull, progression, pause and once-only Daily credit coverage; add readiness, concurrent pulls, blocked input and UTC rollover cases. Existing pause tests actually allow a pull while paused, so that expectation must change.
- Replace fixed inter-pull delays with actual tick-stamped winning, delayed and wrong-choice traces. Add passing streams, chest-high/remote piles, sustained head coverage, pending pulls, settling jitter and loss/win precedence cases.

Phase 1 should add isolated rescue types/session/engine and one catch-basin fixture alongside the running ball campaign. Use the real dyn4j simulation for correct, delayed and no-input traces; measure active stone count and warm simulation step cost. This avoids making the shipped UI unusable while burial and settling are still being established. Phase 2 then supplies the three-level playable UI; Phase 3 removes the obsolete ball path and activates campaign migration after user acceptance. No unrelated game implementation changes belong to this work.

Do not claim dense-stream feel or Android performance from a JVM test. The Phase 1 gate needs reproducible pile behaviour and measured simulation cost; the Phase 2 gate additionally needs emulator evidence, representative-device evidence and the user's acceptance before campaign expansion.

## Fresh baseline

Executed on Windows against the unchanged source baseline:

```powershell
$env:GRADLE_USER_HOME='C:\Users\funky\.gradle'
.\gradlew.bat testDebugUnitTest --rerun assembleDebug --console=plain
python scripts/check_navigation_coverage.py
```

- Gradle: **BUILD SUCCESSFUL**, 7 seconds reported. The test task executed freshly using `--rerun`; debug assembly was up to date. JUnit XML reports **158 tests, 0 failures, 0 errors, 10 skipped** (148 executed). No failing unrelated tests observed in this run.
- Pull the Pin: 17 tests across five classes, with no failures, errors or skips. These prove only the existing ball game.
- Navigation checker: **PASS**, 20 Standard routes, 20 `/new` routes, 17 browsers. This is static coverage, not touch/navigation acceptance.
- Raw local evidence: `app/build/test-results/testDebugUnitTest/TEST-*.xml`, `app/build/reports/tests/testDebugUnitTest/index.html`, and `app/build/outputs/apk/debug/app-debug.apk`. These generated outputs remain untracked.
- The initial sandboxed Gradle attempt could not open the wrapper cache lock outside the workspace. Approved elevated execution with the existing user cache succeeded; no lock deletion or build-config change was needed.
- Lint, emulator play, physical-device performance, visual QA and live CI were not run for this documentation checkpoint. There is no new rescue gameplay to accept yet.

Phase 0 gate: **complete**. Replacement scope and versioned storage policy are documented, build/test baseline is recorded, and unrelated games are unchanged. Phase 1 is next.
