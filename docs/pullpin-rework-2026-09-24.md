# Pull the Pin rework — 2026-09-24

## Gameplay

The 48 stable puzzle IDs now contain a progressive campaign with 12 levels per difficulty. Standard starts at the first unfinished board, and Play Again advances through the campaign. Daily selection stays tied to the UTC day; browser selection keeps its exact ID. Retry rebuilds the current board instead of choosing a different puzzle.

Chambers teach mixing grey balls by physical contact before opening an exit, then combine offset drops and angled funnels, multiple colors, staggered heights, bomb containment, rescue-count locks, and lower reservoirs that require a second mixing stage. Some pins must remain in place. Hard and Expert combine the mechanics with more chambers and larger batches. These are authored recipe variations, not 48 independent hand-drawn layouts.

The board has labeled handles, numbered ball/cup colors, visible lock counts, enlarged touch targets, and accessibility actions for pins. Undo replays to before the last accepted pull, Hint explains the next stage, and the loss dialog offers undo or retry. Help, hints and backgrounding pause the simulation.

## Rules and implementation

`PullPinSession` owns fixed-step physics and game rules for both the app and tests. Win requires every non-bomb ball to be captured. Captured balls leave the physics world; bombs touching balls or cups, uncolored/wrong-color captures, and escaped balls lose the level. Coloring uses actual contact distance with a small tolerance rather than the previous 30-unit aura. Capture matches the drawn cup rectangle.

The dyn4j world converts 100 drawing units to one metre, so collision tolerances, travel limits and penetration correction work at the intended scale. Ramps rotate identically in physics and rendering. Funnel outlet walls contain deflections and prevent stranded balls. Pull animations and removals belong to the current session, so retry cannot inherit delayed removals from a previous run. Idle settled time does not grow undo replay work.

The Kotlin runner and `scripts/generate_pullpin.py` now verify the authored campaign rather than overwriting it with the previous generator. To add content, edit `PullPinPregenerated.kt`, provide its intended pin sequence, and run the physics proof. Existing browser IDs and completion storage remain compatible.

## Validation

- Campaign proof runs all 48 intended solutions through shipped physics and rules, with both 120-frame and 300-frame waits between pulls. It checks actual `WON` state, not merely a color/geometry precondition.
- Regressions cover premature exit and reservoir release, bomb contact, locked gates, coloring through barriers, deterministic undo, pending-pull retry, campaign progress, pause, and actual Daily victory awarding a streak only once.
- `gradlew.bat testDebugUnitTest assembleDebug lintDebug`: passed. 158 tests reported, 148 passed, 10 skipped; all 17 Pull the Pin tests passed. Lint reported 0 errors and 39 warnings elsewhere, with no Pull the Pin findings.
- `python scripts/check_navigation_coverage.py`: passed for 20 Standard routes, 20 new-game routes and 17 browsers.
- Emulator `emulator-5554`, 1080 x 2400: inspected tutorial and Hard 012 layout; tutorial won with two actual pin taps. Hard 012 exercised locked-gate feedback, bomb loss, undo back to zero moves, Hint, and all three staged reservoirs. All 15 balls were saved with nine pulls and the victory dialog appeared. The tested debug APK remains installed.
- Screenshots are local build artifacts: `app/build/pullpin-ui.png`, `pullpin-win.png`, `pullpin-hard.png` and `pullpin-hard-win.png`. They are not committed.

## Remaining acceptance

The automated proof demonstrates a winning route for every board; it does not establish that every arbitrary timing or pin order wins. Physical-device feel, TalkBack interaction, landscape/small-screen inspection and a complete hands-on playthrough of all 48 boards remain separate acceptance work. No new live CI run is claimed.
