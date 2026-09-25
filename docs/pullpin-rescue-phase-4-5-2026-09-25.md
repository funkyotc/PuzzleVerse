# Pull the Pin rescue: Phase 4 expansion and Phase 5 verification

Date: 2026-09-25

Status: Phase 4 mechanics and three introductory levels are implemented. Phase 5 automated checks and disposable-emulator play are complete. The release gate remains open for physical-device performance, user feel, accessibility acceptance and a live CI run for this change.

## Phase 4 mechanics

- The campaign now has six versioned rescue IDs. Levels 1–3 keep their existing IDs and completion; levels 4–6 append drainage, full-body clearance and physical escape. The three-level prototype remains separate.
- Survival, full-clear and escape are explicit objectives. The goal appears in the preplay dialog and on the board. Full-clear requires the body area to be free of stones after settling. Escape requires the king's actual supported arrival in the visible blue exit with his body clear; opening the gate alone cannot win. Burial and escaped-stone losses retain precedence.
- Level 4 places a removable floor at the measured pile location, above a bounded lower chamber. The king remains supported on the adjoining floor. Level 5 reuses this readable layout to teach full clearance before any mixed objective. Level 6 uses a dyn4j king body with fixed rotation and a horizontal motor; stone and closed-gate contacts block movement. No position teleport awards an escape.
- The campaign validator now includes the three new levels. No combined diversion, drainage and escape level or optional challenge variation was added before separate play acceptance.

## Authored physics proof

All tests use the production `RescueSession`, 60 Hz steps, 352 persistent stone IDs and timed pin pulls. Results below are representative deterministic traces, not device timing.

| Level | Intended actions | No input / wrong action | Winning outcome |
| --- | --- | --- | --- |
| 4 · Drain the pile | `divert` 180, `drain` 450 | No input buries near tick 1031; drain alone delays but does not prevent burial | Survival near tick 2788, with stones in the lower chamber |
| 5 · Free the king | `divert` 180, `drain` 450 | No input buries; diversion alone leaves the body obstructed and cannot meet full-clear | Body clear and settled near tick 2788 |
| 6 · Reach the exit | `divert` 180, `exit` 360 | Diversion alone leaves the gate blocking arrival; exit alone buries the king; no input remains playable at the closed gate | Supported, body-clear arrival near tick 429 |

Rule tests also cover drainage clearing head exposure during the burial grace interval, full-clear rejecting a stone beside the body, escape rejecting short or unsupported arrival, and loss precedence. Retry reconstructs the stock and pin state.

## Phase 5 evidence

- `gradlew.bat testDebugUnitTest assembleDebug lintDebug --console=plain` passed with the existing user Gradle cache: 166 tests, 156 passed, 10 skipped, zero failures/errors; lint reported zero errors, 36 warnings and three hints.
- `python scripts/check_navigation_coverage.py` passed: 20 Standard, 20 `/new` and 17 browser routes. `python scripts/generate_pullpin.py` passed its original and expanded campaign suites.
- On a disposable Pixel 5 API 30 emulator, the route smoke passed for the other 19 games. Pull the Pin initially failed its Back return because the READY help dialog consumed Back. After the dialog fix and rebuilt APK, the targeted Pull the Pin smoke passed detail, Daily, Standard and browser routes without an ANR.
- Exact browser levels 4, 5 and 6 showed their objective before play and won through actual screen taps. Level 4 retry from the win dialog restored all 352 stones, and Back returned to the browser. The level 6 result showed the king inside the marked exit.
- The normal 1080×2340 emulator screenshot showed the full board and its handles. Changing Android's display size while the app was open caused the app surface to go black despite an intact accessibility hierarchy. After a clean AVD boot configured at 720×1280, the final APK rendered the full board and level 6 won through taps on both smaller handles. The emulator route script now scales scroll gestures to the reported viewport; Pull the Pin detail, Daily, Standard and browser routes passed at both sizes. A Dark-theme screenshot at 720×1280 showed legible text, stones, king, handles and exit.
- `dumpsys gfxinfo` reported 4140 cumulative frames and 90.89% janky frames in this headless software-rendered emulator session. This is a warning to measure on representative hardware, not an Android-device shipping benchmark.

## Open release gate

- Complete actual physical-device play and measure simulation cost, frame pacing, memory and a suitable stone cap. Check pause/background return, replay interactions, accessibility actions and remaining themes there.
- Obtain user acceptance of rescue feel, including drainage and escape. The earlier Phase 2 physical-device/user-feel gate remains unverified.
- Run a live CI build for the eventual published commit. JVM, build, lint and emulator route checks alone do not establish release readiness.
