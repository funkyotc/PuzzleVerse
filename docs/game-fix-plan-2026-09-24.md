# Game audit fix plan — 2026-09-24

Status: active implementation plan; implementation evidence and remaining proof gaps are tracked in the [verification record](game-fix-verification-2026-09-24.md). Source: [game audit](game-audit-2026-09-24.md). The [older implementation plan](implementation_plan.md) is a historical proposal, not a completion report.

Phases 1–3 are implemented with automated checks. Phase 4 remains open for Pull the Pin pin-order wins, Hexa Sort two-shuffle wins, Wood Screws plank-fall wins, complete touch playthroughs, a device midnight check, replay-dialog interactions, a clean emulator sweep without an ANR, and a live CI run. The verification record separates what passed from what is still unproven.

## Acceptance contract

- Every listed Flow Free board has exactly one fully searched, full-coverage solution under the shipped rules. Every scripted Chess solution ends in actual checkmate. Kakuro browsing opens and launches its selected puzzle.
- All 20 Daily entries use the UTC epoch day. A challenge is reproducible across fresh installations. Minesweeper additionally uses the first-tap coordinate so any first tap remains safe; equal date and coordinate yield equal mines. For checked-in selections with at least two choices, consecutive UTC days select different puzzles.
- Daily saves carry their challenge day. An undated or older Daily save is discarded without changing Standard progress. An active Daily resets at 00:00 UTC and the previous board cannot award today's streak.
- Every reachable `/new` route launches a fresh playable game. Game detail titles match Home labels. No valid game route reaches placeholder UI.
- Verification distinguishes model tests, emulator navigation, and actual gameplay. An unproved level stays explicitly marked unproven.

## Phase 1 — Restore playable content (critical)

1. **Flow Free:** The audit's 14 zero-solution results were caused by the solver's 2,000-step search limit. A one-million-step search verified exactly one solution for all 20 checked-in boards without truncation. Keep the boards, report truncation separately from zero solutions, and collect all failing IDs in one test result. Reject generated output that does not pass the same solver gate.
2. **Chess:** Replay every move from each FEN using chesslib and collect every illegal move or non-mated ending. Correct or replace the seven reported false mates while retaining stable puzzle IDs. Require `board.isMated` after the final move before setting `isWon`, displaying “Checkmate!”, or recording a streak. Test the content and the ViewModel win transition.
3. **Kakuro browser:** Have `PregeneratedKakuro` implement `BrowseablePuzzle` with stable label and subtitle, remove the force cast in `MainActivity`, and test model conformance plus browser-to-game navigation.

Gate: the three regressions pass, and the debug app opens those games and Kakuro's browser on the emulator.

## Phase 2 — Daily selection and persistence (high)

1. Add an injectable UTC epoch-day source used by Daily selection, saves, streak credit, and rollover. Keep Standard and browser modes independent of the Daily seed. Pick Nonogram, Kakuro, and Arrow Escape from their checked-in libraries using the day; fail visibly on missing puzzle IDs rather than substituting a random board.
2. For 2048, seed both initial tiles and subsequent spawns from the day and persist the generator state with the board. Identical day plus identical moves must produce identical tile positions and values, including after restore. For Minesweeper, seed placement from the day and first-tap coordinate; preserve the safe first-tap neighborhood.
3. Replace random date-seeded *selection* in Wordle, Hexa Sort, and Hexa Stack with a stable selection rule that excludes yesterday's item when the pool contains at least two. Keep selected IDs stable across installations and app restarts.
4. Save the challenge day with Daily progress in Sudoku, Shikaku, 2048, Hexa Sort, and Hexa Stack, plus any other Daily mode that stores progress. Treat existing undated Daily saves as stale. On foreground entry and while a Daily screen is active, detect a UTC-day change, clear only that Daily state, load the new challenge, and prevent a late result from updating the prior day's streak.

Gate: clock-controlled tests cover same-day relaunch, two fresh installations, same input sequence, midnight while active, stale and legacy saves, and preservation of Standard saves.

## Phase 3 — Navigation and naming (high to medium)

1. Make the `/new` dispatch exhaustive for all games that can navigate to it, especially Constellations, Nonogram, Kakuro, Flow Free, and Hashi. The four Daily replay actions begin a fresh Standard game; Hashi Standard replay does likewise. Check detail-menu New Game and dialog actions for all 20 games. A valid game ID must never fall through to `GameScreen` placeholder text.
2. Use the Home game catalog as the source for game-detail display names, avoiding route-ID capitalization such as “Flowfree,” “Pullpin,” and “Woodnuts.”

Gate: automated route coverage where feasible and emulator checks for each of the 20 Standard, Daily, and available browser routes, plus the five affected replay actions.

## Phase 4 — Generator proof and release evidence (medium)

1. Test Pull the Pin by finding an actual winning pin order under the shipped physics and win condition; test Kakuro clues with a solver for existence and uniqueness; test Hashi by constructing legal bridges through the real win rule without directly recording a streak. Add all-level completion proof for Hexa Sort, Wood Screws, and Water Sort. Strengthen Shikaku, Bonza, Tangrams, and other “unproven” entries only with checks that model real player victory. Keep slow exhaustive checks bounded and identify any unsolved or unproved IDs rather than silently skipping them.
2. Run `gradlew.bat testDebugUnitTest`, `assembleDebug`, and `lintDebug`. Align CI's Java setup with the required JDK 21, then check a real CI run. Use an emulator to navigate every Standard, Daily, and browser entry; perform hands-on gameplay, win/loss, gesture, hint, save/resume, and date-rollover playthroughs. Record counts, device, date, and the exact remaining unproved IDs in a verification report.

Gate: no known invalid shipped puzzle, no runtime navigation crash, and a clear evidence table separating automated proof from emulator and manual acceptance. A green JVM suite alone is not a 20-game playthrough.
