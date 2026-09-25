# Pull the Pin rescue: Phase 3 campaign integration

Date: 2026-09-25

The three handcrafted rescue boards form the initial production campaign. This keeps the campaign at the proven prototype size while the Phase 2 physical-device and user-feel gate remains open. The user's request to proceed with Phase 3 authorizes integration; it does not supply evidence that the earlier feel gate passed. No drainage, escape or 48-level expansion is included.

## Integrated routes and progress

- Standard, Daily, `/new` and exact browser routes now use the rescue session and screen. The browser lists only the versioned rescue levels. Old ball gameplay, generated ball levels and their obsolete tests were removed.
- Production IDs are `pullpin_rescue_v1_001` through `003`; prototype and old ball IDs remain separate. Standard resumes at the first unfinished production ID. The `/new` route starts at Level 1. Retry reconstructs the same level and stock. Exact browser selection opens exactly the selected ID.
- The existing `PullPinPuzzleCompletion` preference file remains in place. Legacy Boolean completion keys are untouched and cannot match a production rescue ID. Daily selects from the fixed campaign order by UTC epoch day and credits the existing Pull the Pin streak only on a current-day terminal win. A stale Daily route stops stepping until navigation reloads it.
- `PullPinRescueMigration` records `campaign_version = 1`. On first startup it clears only the retired Pull the Pin save metadata, before the detail screen offers Resume. Repeating the migration preserves new rescue metadata. Other games' saves and all completion and streak history remain intact. Opening a retired exact puzzle ID shows a replacement notice and browser link.
- Help and hints describe diversion, stone flow and head safety. Undo, colour mixing, cups, bombs and rescue-count locks are no longer in the playable route.

## Authored level proof

All traces use `RescueSession` with a 60 Hz fixed step and the 352-stone dyn4j world. `RescueCampaignTest` replays each production level's timed solution and no-input run, checks all original stone IDs remain present, and covers representative wrong choices. The Kotlin and Python validator entry points now target the rescue campaign without writing puzzle data.

| Production ID | Intended pull ticks | No-input | Winning trace | Wrong choice |
| --- | --- | --- | --- | --- |
| `pullpin_rescue_v1_001` | `divert` 180 | Buried near tick 1229 | Rescued near tick 3263 | `divert` at 1080 buries the king |
| `pullpin_rescue_v1_002` | `prepare` 90, `divert` 180 | Buried near tick 1213 | Rescued near tick 3129 | `divert` alone backs up and buries the king; adding `prepare` at 300 recovers |
| `pullpin_rescue_v1_003` | `divert` 180 | Buried near tick 1211 | Rescued near tick 2902 | `spill` at 120 followed by `divert` at 180 buries the king |

The tick values are the earlier Phase 2 observations of the same layouts. The Phase 3 tests replay the production copies and assert terminal outcomes rather than fixing exact terminal ticks. Additional Phase 2 timing checks cover a later successful pull on Level 3 and rapid consecutive Level 2 pulls.

## Verification and open acceptance

The full `gradlew.bat testDebugUnitTest assembleDebug --console=plain` run using the existing user Gradle cache passed: **161 tests, 151 passed, 10 skipped, zero failures/errors**, and a successful debug APK build. The updated `python scripts/generate_pullpin.py` validator passed its rescue campaign tests. `lintDebug` reported zero errors, 36 warnings and three hints. `python scripts/check_navigation_coverage.py` passed for 20 Standard routes, 20 `/new` routes and 17 browser routes. There was no connected Android device during Phase 3, so actual touch navigation, physical-device performance and user acceptance remain open. The [Phase 2 record](pullpin-rescue-phase-2-2026-09-25.md) remains the last emulator interaction evidence.
