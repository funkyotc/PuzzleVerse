# Pull the Pin rescue: Phase 1 physics verification

Date: 2026-09-25 (Australia/Sydney)

Scope: Phase 1 of the [development plan](pullpin-rescue-development-plan-2026-09-24.md), following the [Phase 0 boundary](pullpin-rescue-phase-0-2026-09-24.md). One real dyn4j rescue fixture is implemented and tested. The existing app, ball campaign, routes and progress storage remain unchanged. This is a simulation checkpoint, not a playable-prototype or device-acceptance claim.

## Implemented

New production code is isolated under `app/src/main/java/com/funkyotc/puzzleverse/pullpin/rescue/`:

- `RescueModels.kt`: finite stone stock, king body/head geometry, explicit survival objective, timed pulls, per-pin runtime progress and local burial measurements.
- `RescuePhysics.kt`: dyn4j 5.0.2 at 100 drawing units/metre, static king/body/head and wall shapes, circular stone collisions and independent pin removal. All stones remain in the world; escaped stock is a fault rather than deleted progress.
- `RescueSession.kt`: explicit READY/start boundary, automatic flow after start, fixed 60 Hz steps, accepted-action ticks, independent 14-tick pin withdrawals, pause, identical-layout retry, sustained local burial and whole-stock settling. `RescueClock` retains active elapsed ticks across render cadences and discards readiness/paused wall time, including pauses without intermediate frames. No Android UI is connected to this clock yet.
- `RescuePrototype.kt`: one bounded hopper, descending stream, removable sloping floor pin, lower catch basin and fixed king chamber. ID `pullpin_rescue_prototype_001` is separate from production completion IDs. Its intended solution is a pull at tick 180 (3 seconds).

The engine uses shared wall geometry, but no colour propagation, cups, bombs, rescued-ball locks, capture/deletion or impact damage. No new solver/dependency was introduced. Undo is absent from this isolated prototype, as selected in Phase 0; the old game's Undo is unchanged.

## Prototype tuning values

These are measured starting values, not shipping guarantees:

| Parameter | Value |
| --- | --- |
| Finite visible stock | 352 stones, radius 4.5 drawing units |
| Gravity | +0.5 m/s² in screen-down coordinates |
| Stone material | Density 2.5; friction 0.45; restitution 0; linear damping 0.1; angular damping 0.8 |
| King | 18 × 34-unit body, radius 9 head, centred at x=334 with feet at y=690 |
| Burial measurement | 6 × 6 samples in each head flank, above-head strip and two supporting strips below the flanks; occupied samples come from actual slow stone circles |
| Burial thresholds | Both flanks ≥42%, above ≥30%, weaker supporting strip ≥42%; stone speed ≤18 units/s |
| Burial grace | 60 consecutive covered ticks (1 second); clearance resets exposure |
| Settling | Every stone speed ≤1.5 units/s and displacement ≤0.03 units per step for 90 consecutive ticks, with no pending pull |
| Pin removal | Accepted at tick T, collision removed before the physics step at T+14; visual progress derives from those same ticks |

Burial requires a supported pile surrounding the head, not a single high stone or a board-wide fill count. The support measure is a local occupancy heuristic, not a dyn4j contact-graph proof; Phase 2 must tune it against observed scenes. Settling uses sustained motion tolerances because sleep flags alone left a delayed rescue waiting indefinitely. Damping supplies rolling resistance for circular collision shapes. Loss is evaluated before victory; a covered head cannot win during the grace interval.

## Real-session evidence

`RescuePrototypeTest` drives the shipped rescue session and writes CSV/SVG position snapshots. These are deterministic simulated seconds, not wall-clock test durations.

| Input trace | Outcome | Terminal tick | Simulated seconds |
| --- | --- | --- | --- |
| No input | Buried | 1229 | 20.48 |
| Pull at tick 120 (2 s) | Won | 3263 | 54.38 |
| Pull at tick 180 (3 s) | Won | 3263 | 54.38 |
| Pull at tick 240 (4 s) | Won | 2950 | 49.17 |
| Pull at tick 600 (10 s) | Won, partially buried | 2885 | 48.08 |
| Pull at tick 1080 (18 s) | Buried despite diversion | 1229 | 20.48 |

The 3-second trace is repeated with exact state equality. Early wins send more than 300 stones into the basin; for the 3-second trace, 346 finish there and six remain in the king chamber. All upstream stock empties on these early wins. The delayed win leaves more than 50 stones in the king chamber: head-flank coverage is 44.4%/83.3%, but above-head coverage is only 19.4%, so complete excavation is demonstrably unnecessary. Every terminal state retains all 352 stone IDs.

Final pairwise stone penetration stays below the 1.5-unit regression ceiling: approximately 0.49–0.50 units on wins and 1.10–1.35 on burial failures. This measures final stone-to-stone penetration, not every intermediate contact or device stability. Boundary escape is separately a loss condition.

Inspected a four-panel position plot covering no input at 10 seconds, burial, early diversion and delayed rescue. It shows the continuous stream, rising local pile and actual basin diversion. These debug diagrams depict collision geometry, not finished king art or gameplay screenshots. Phase 2 still needs clear visual burial feedback so the solid head shape does not appear safe when surrounded.

The measured time to settle after an early solution is about 49–54 seconds. That is an open feel/tuning issue for Phase 2; do not replace settle-to-win with a timeout to shorten it.

## Simulation cost

Each trace records full session-step cost (physics, snapshots and rule evaluation) after excluding the first 120 ticks. In the full verification run, with lint running concurrently, mean step cost was **1.46–1.94 ms** and per-trace p95 was **1.80–2.94 ms** for 352 stones on this Windows host. A preceding focused run measured means around 1.03–1.20 ms and p95 around 1.18–1.95 ms. These are diagnostic JVM measurements, not Android frame pacing or an approved shipping particle cap.

No emulator/device memory, rendering cost or frame pacing has been measured. Keep dyn4j for Phase 2; select the shipping cap only from representative Android measurements.

## Verification

```powershell
$env:GRADLE_USER_HOME='C:\Users\funky\.gradle'
.\gradlew.bat testDebugUnitTest assembleDebug lintDebug --console=plain
python scripts/check_navigation_coverage.py
```

The rescue suite contains 12 new tests: three real-session scenario tests and nine rule/input/clock tests. Coverage includes supported versus passing/chest-high/one-sided/remote piles, grace-period clearance, loss precedence, distant moving stock, bounce apexes, pending pulls, escaped-stock retention, concurrent/duplicate pulls, exact collision timing, retry, retained-stock wins, terminal input rejection, render cadence and paused/readiness time. Clearance during grace is a rule-level synthetic scene test; a physical drainage level belongs to Phase 4.

Full project verification: **BUILD SUCCESSFUL** in 1m 46s. JUnit reports **170 tests: 160 passed, 10 skipped, zero failures/errors**. All 12 rescue tests and the existing 17 ball-game tests pass. Debug APK assembly executed successfully. Lint completed with zero errors, 36 warnings and three hints; none reference the new rescue code/tests. Navigation coverage passes for 20 Standard routes, 20 `/new` routes and 17 browsers. `git diff --check` passes. No live CI, emulator or physical-device run was performed.

Generated local evidence (not committed):

- `app/build/test-results/testDebugUnitTest/TEST-com.funkyotc.puzzleverse.pullpin.RescuePrototypeTest.xml` and `RescueRulesTest` XML; the prototype report includes measured costs and terminal metrics.
- `app/build/reports/pullpin-rescue/pull-*-tick-*.svg`, `pull-*-final.svg` and matching CSV files, regenerated by the prototype tests.
- `app/build/reports/pullpin-rescue/comparison.png`: locally rendered QA montage of the generated SVG scenes.
- `app/build/reports/tests/testDebugUnitTest/index.html` and `app/build/reports/lint-results-debug.html`.

Phase 1 technical gate is complete: one reproducible catch-basin rescue, tested terminal rules, inspected pile/diversion geometry and measured host simulation cost. Phase 2 is next: connect the ready/pause/clock contract to the UI, implement the remaining two layouts, tune play duration, then obtain separate emulator, physical-device and user-feel acceptance. Do not expand the campaign before that acceptance.
