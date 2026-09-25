# Pull the Pin: king rescue development plan

Date: 2026-09-24

Status: Phases 0–3 are implemented as recorded in the [Phase 0 boundary](pullpin-rescue-phase-0-2026-09-24.md), [Phase 1 physics record](pullpin-rescue-phase-1-2026-09-25.md), [Phase 2 prototype record](pullpin-rescue-phase-2-2026-09-25.md), and [Phase 3 campaign record](pullpin-rescue-phase-3-2026-09-25.md). The user subsequently requested phases 4 and 5; the [Phase 4–5 record](pullpin-rescue-phase-4-5-2026-09-25.md) tracks the expansion and release evidence. The physical-device and user-feel gates remain open, and automated checks alone do not close them.

This plan replaces the coloured-ball-and-cup design direction. The [previous rework record](pullpin-rework-2026-09-24.md) documents that implementation and its validation, not completion of this rescue game. The [older archived plan](archive/pullpin-plan.md) is historical. Existing game-audit results do not prove the new rules or levels.

## 1. Approved experience

The player sees a small crowned figure below a flowing, finite load of stones. Stones accumulate around his feet and rise toward his head while the player reads the passages. Tapping a pin permanently removes it, opening a route that can divert the flow away from him. The tension comes from making the right choice quickly and seeing the physical consequence immediately.

| Decision | Approved starting behaviour |
| --- | --- |
| Danger | Stones flow automatically when active gameplay begins; no first pull is required. |
| Material | A dense, heavy stream of small stones that tumbles, piles and spills like coarse sand. |
| Failure | Gradual burial, not damage from individual rock impacts. |
| Timing | Initially target 15–25 seconds before burial when the player does nothing; tune through play. |
| Input | Tap a pin once to remove it permanently. Other pins remain usable during flow and pull animations. |
| Initial rescue mechanic | Open catch basins beneath sloping streams to divert stones before they reach the king. |
| Supply | Finite and visible from the start; no hidden spawning or replenishment. |
| Initial victory | All stones settle with the king's head clear. Partial burial is acceptable. |
| Difficulty | Early levels are readable and usually solvable on the first attempt; harder levels can require a few quick retries. |
| Teaching order | Teach useful actions in the correct order before adding misleading connections. |
| Board | Entire layout, stone supply, chambers and pins visible on one screen. No scrolling or hidden traps. |
| Presentation | Simple shapes, flat colours and minimal animation consistent with PuzzleVerse. |
| Later mechanics | Drain accumulated stones and open a physical escape route, in addition to diversion. |

The game must feel like a rescue under pressure. More particles or a more elaborate solver alone do not establish that the experience works.

## 2. First playable slice

Build three handcrafted levels before a campaign or generator. Use a fixed, simply drawn king and small circular physics bodies with stone-like rendering. Do not start with a ragdoll, elaborate character art or a fluid solver.

1. **Divert:** stones travel down a slope toward the king. One floor pin opens a catch basin underneath the stream. Pulling it visibly changes the route; doing nothing eventually buries him.
2. **Prepare, then divert:** first open a connection to a larger receiving chamber, then release the stream into that route. Opening only the diversion leaves insufficient capacity and causes a visible backup toward the king. Early timing should allow recovery by opening the second pin promptly; a sustained wrong order must demonstrate its danger.
3. **Choose under pressure:** two plausible connections are visible, but one feeds stones back toward the king. The geometry must explain the mistake without a hidden rule. A correct route protects him even when some stones have already reached his chamber.

Give each level an intended timed solution, a no-input outcome and a representative wrong-choice outcome. Use the actual physics to establish these outcomes. Do not assume an empty side chamber will catch a stream: place openings, slopes and barriers so gravity demonstrably redirects it.

### Prototype acceptance

- The stream is dense and satisfying, not a handful of bouncing coloured balls.
- A rising local pile communicates the remaining danger without needing a numeric countdown.
- A pull produces an understandable physical change, and rapid consecutive pulls are accepted.
- No-input burial is approximately within the initial 15–25-second target on these levels.
- Correct play can win with the king partially buried; complete excavation is not secretly required.
- Retry restores the same layout and initial load immediately and consistently.
- The user has played the slice and accepted the rescue feel before expansion. Automated proof is supporting evidence, not a substitute for this gate.

## 3. Gameplay rules and technical defaults

These are implementation proposals supporting the approved experience. Numeric thresholds and performance budgets must be measured during the prototype, not treated as approved final values.

### Simulation and input

- Keep a fixed simulation step and record accepted actions by simulation tick. Render independently of the step rate; slow rendering must not change the puzzle rules.
- Start the flow once the board is visible, input is attached and any initial instructions are dismissed. Do not consume the rescue window behind navigation transitions or help overlays.
- Model the initial finite stock explicitly. Every stone starts in a visible location; stones remaining in upstream chambers count toward the level state.
- Use irreversible pins and per-pin pull state. Reject duplicate taps on a removed/pulling pin, but never impose a global input lock while another pin animates.
- Define and consistently apply the tick when each pin stops blocking stones. Keep the visual withdrawal and collision change aligned.
- Pause simulation for backgrounding, help, hints and other blocking overlays. Resume without accumulating wall-clock catch-up steps.

### Burial

- Define a visible head region and measure sustained local pile coverage around it using the actual stone positions. A stone briefly passing over the head is not burial.
- Prototype a local occupancy/support test plus a short sustained-coverage grace period. Tune it against recorded scenes of passing streams, chest-high piles, one-sided piles and fully covered heads.
- Avoid a board-wide fill percentage or a single highest stone as the failure test; remote piles and bouncing stones must not kill the king.
- Keep rendering and collision geometry consistent so the silhouette never appears safe after a burial failure. No separate impact-health mechanic in the initial rules.
- Apply loss before win if both predicates become true on the same tick. Terminal outcomes occur once and stop gameplay input.

### Survival victory and settling

- Win only when the finite load has settled over a sustained interval, no pin removal is pending, and the king's head remains clear. Include stones in every chamber, not only those near the king.
- Stones safely retained behind unpulled pins may remain there. Do not require every pin to be removed or every stone to pass through an outlet.
- Prevent transient low velocity at the top of a bounce from counting as settled. Tune motion tolerances to suppress tiny solver jitter without awarding premature wins.
- Bound all physical areas or define visible drain outlets explicitly. Do not silently delete stones that escape through a collision bug and count that as rescue.
- Do not award a win merely because a timer expires. The initial timing target describes no-input danger, not the victory condition.

### Retry, hints, undo and progress

- Retry reconstructs the current level with its identical load and resets every timer, pending pull and terminal flag. Keep retries quick in both success and failure flows.
- Preserve existing help/pause conventions. Rewrite hints around rescue actions rather than colour mixing and cups; help and hints pause before covering the board.
- Assess existing undo against continuous danger. If retained, restore the exact pre-action simulation state, including elapsed time and burial exposure; removing an action without rewinding physics is invalid. Undo policy is not a reason to delay the three-level prototype.
- Keep Standard, Daily and exact browser selection. Verify UTC rollover and streak credit under the new win condition.
- Give the rescue campaign versioned level IDs/completion storage. Old completed ball puzzles must not mark new rescue levels complete. Preserve unrelated game progress and handle old Pull the Pin saves explicitly.

## 4. Later levels and interview alternatives

The following separates the requested later mechanics from optional variations. Alternatives discussed during the interview are not all requirements; they must not quietly replace the approved core.

| Mechanic or variation | Placement | Design and constraints |
| --- | --- | --- |
| Correct-order diversion | Early campaign | Prepare a receiving route before releasing stones. Start with forgiving recovery time, then narrow the margin. |
| Dangerous connections | Medium onward | Tempting pins connect safe-looking chambers back toward the king. Make the full route visible and physically consistent. |
| Sideways release above a slope | After floor catch basins | Use gravity and geometry to feed a side passage before stones reach the king. An opening alone must not magically attract stones. |
| Drain the king's chamber | Required later expansion | A lower or side pin lets accumulated stones spill away. First introduce this as recovery while survival remains the objective. Ensure the king is supported and cannot fall through the drain. |
| Free the king from the pile | Later explicit objective | Require the body/exit area to be clear, rather than merely the head. Label this objective before play and use a dedicated win predicate. |
| Physical escape route | Required later expansion | Open a path and have the king move to a visible safe area while stones still fall. Define movement, blockage and arrival rules; opening an exit alone is not victory. Introduce movement simply before combining it with burial pressure. |
| Diversion + drainage + escape | Hard/Expert | Combine familiar mechanics in stages; do not introduce every new rule in one level. A diversion buys time, drainage frees the path, then the king exits. |
| Chambers filling and backing up | Medium/Hard | A diversion provides temporary capacity. The player must open another irreversible route before the backup reaches the king. Retain a finite visible supply and clear capacity cues. |
| 5–10-second rescue windows | Optional late challenge | Introduce only after mechanics are learned. Keep few decisive pulls, fast retry and visibly fair timing. Do not make this the default campaign pace. |
| A few learning failures | Hard/Expert | Harder spatial reasoning and timing may need retries. Same layout and same inputs produce repeatable lessons; avoid random deaths or hidden connections. |
| Chunkier rocks mixed with fine stones | Optional later experiment | Could introduce visible blockage/bridging puzzles. First prove stability and readability; keep the dense small-stone stream as the main material. No impact damage implied. |
| First pull starts flow / inspection mode | Optional practice or accessibility variant | Can support learning without changing Standard's automatic flow. Clearly distinguish it from the core timed challenge and decide progression policy before shipping. |
| Reusable open/close gates | Separate future variant only | Conflicts with approved irreversible pins. Requires an explicit design revisit; do not add as an ordinary difficulty increase. |
| Endless source that must be stopped | Separate future variant only | Conflicts with finite visible stock and settle-to-win. Would need clearly different source and victory rules. |
| Weighted crushing platform | Separate future hazard only | Changes gradual burial into a mechanism puzzle; requires explicit future approval and separate failure teaching. |
| Damage from falling rocks | Excluded from current plan | Changes the approved burial rule and would make safe-looking partial burial harder to understand. |
| Elaborate comic panic or dramatic story scenes | Excluded from current plan | Keep the app's simple presentation; small readable reactions are sufficient. |

Recommended progression: simple diversion → correct order → misleading connections → capacity/backup → drainage → escape → combined challenges. Adjust timing after spatial difficulty is proven, rather than making every new mechanic faster immediately.

## 5. Implementation phases

### Phase 0 — Establish the replacement boundary

- Inspect the current Pull the Pin models, session, engine, ViewModel, Canvas UI, authored data, tests and generator runners.
- Keep useful infrastructure: fixed-step session, correctly scaled dyn4j world, pin animation ownership, route integration, theme/layout conventions and accessibility actions.
- Replace colour propagation, coloured cup capture, bombs, rescue-count pin locks and ball-specific win/loss text in the new experience. These are not prerequisites for rescue gameplay.
- Record the old save/completion format and choose a versioned migration strategy before changing campaign data.
- Record a build/test baseline and identify failures unrelated to this work.

Gate: concrete implementation scope and storage policy documented; no unrelated game changes.

### Phase 1 — Physics and rescue rules

- Introduce rescue-specific data for finite stone stock, king/head geometry, pin passages, objectives and timed solution traces. Prefer explicit types over encoding stones as grey balls and the king as a cup.
- Extend or replace the existing engine/session in small steps, retaining dyn4j initially. Measure particle count, collision stability and step cost before choosing any different simulation approach.
- Implement automatic flow, concurrent pin pulls, gradual burial, stable settling and survival victory.
- Build the first catch-basin layout and test correct pull, delayed pull and no input through the real session.

Gate: one reproducible rescue with convincing pile behaviour, correct terminal rules and measured simulation cost. If dense stones perform poorly, resolve this before authoring more levels.

### Phase 2 — Three-level playable prototype

- Implement the three levels in section 2 with minimal king/stone/chamber rendering.
- Add concise rules, clear available pin handles, immediate retry and simple head-danger feedback.
- Integrate lifecycle pause and ensure several rapid taps work during animation and flow.
- Tune initial stock, slopes, basin capacity and timing to the target experience.
- Play on an emulator and a representative physical Android device; collect a short recording or screenshots plus actual interaction observations.

Gate: every prototype criterion passes, and the user accepts the feel. Do not expand to 48 levels merely to match the former campaign count.

### Phase 3 — Integrate campaign and progression

- Replace old campaign content and help/hint language, and apply versioned progress/save handling.
- Keep Standard progression, exact puzzle browsing, Daily selection, retry and new-game routes consistent.
- Add authored diversion levels that introduce one spatial concept at a time. Establish the number of levels after the prototype gate.
- Record timed winning traces and representative wrong actions for each level. A list of pin IDs without pull timing is insufficient for this game.
- Update generator runners to validate the rescue campaign, preventing old generators from overwriting new content.

Gate: all initial campaign levels have actual simulated victory proof and route/progress regression coverage; old completion data cannot skip the new campaign.

### Phase 4 — Drainage and escape

- Add drainage as a way to reverse rising burial before adding full-clear objectives.
- Add explicit objective types and display the objective before active play.
- Implement simple physical king movement for escape levels, with visible destination, clear support and blockage rules, and win only on safe arrival.
- Combine diversion, drainage and escape only after each works independently.
- Introduce capacity backups, misleading connections and tighter timing gradually. Keep optional variants from section 4 separate until their value is established.

Gate: each new objective has dedicated rule tests, an introductory level and hands-on acceptance; mixed levels remain readable on one screen.

### Phase 5 — Validation and release readiness

- Run unit tests, debug build, lint and navigation coverage after implementation. Use the project Windows Gradle instructions; never append `2>&1`. Select a writable Gradle user-home cache if the environment default is inaccessible.
- Verify actual touch play, frame pacing, pauses, retry, accessibility actions, themes and small-screen layout. Keep emulator and physical-device evidence distinct.
- Update the gameplay documentation and verification record with exact evidence and unresolved limitations. Verify a live CI run before claiming CI as a release gate.

Gate: no known invalid authored level, reliable lifecycle/progress behaviour and accepted device gameplay. A passing JVM suite alone is not gameplay acceptance.

## 6. Verification matrix

| Area | Required evidence |
| --- | --- |
| Determinism | Same initial state and timed actions yield matching outcomes under the fixed-step session; frame-rate variation does not alter simulation time or outcomes. |
| Burial | Passing rocks and chest-high piles survive; sustained head coverage loses; draining during the grace period can rescue; remote piles do not affect the king. |
| Settling | No win during a temporary pause in motion or pending pull; retained safe stock can settle; physics jitter does not stall completion indefinitely. |
| Input | Multiple distinct pulls accepted on nearby ticks; duplicate pulls ignored; terminal state blocks later actions. |
| Retry/pause | Retry during a pull clears delayed removals; help/backgrounding freezes danger; resume does not fast-forward. |
| Level proof | Every authored level has a winning timed trace, no-input outcome and meaningful wrong-choice regression. Test a reasonable timing window around early-level solutions rather than a single frame-perfect trace. |
| Later objectives | Drainage reduces measured coverage; free-body goals require clearance; escape goals require actual arrival and cannot award a survival-only win. |
| Integration | Standard advancement, exact browser IDs, new game, same-level retry, old-save migration, Daily reproducibility and UTC rollover/streak credit. |
| Presentation | Stones and king agree with collision geometry; every pin and chamber is readable and tappable; all stock is on screen across supported layouts/themes. |
| Performance | Measure physics step cost, frame pacing, active stone count and memory on a representative Android device. Set the shipping stone cap from these results, not desktop performance. |
| Feel | User play confirms that urgency, stream diversion and rising piles deliver the intended rescue experience. |

## 7. Primary code touchpoints

- `app/src/main/java/com/funkyotc/puzzleverse/pullpin/data/PullPinModels.kt`: rescue entities, objective definitions and runtime state.
- `app/src/main/java/com/funkyotc/puzzleverse/pullpin/data/PullPinPregenerated.kt`: handcrafted rescue layouts and solution metadata.
- `app/src/main/java/com/funkyotc/puzzleverse/pullpin/physics/PullPinPhysicsEngine.kt`: finite stone simulation, geometry and later drains/king movement.
- `app/src/main/java/com/funkyotc/puzzleverse/pullpin/physics/PullPinSession.kt`: simulation time, input, burial, settling and objective evaluation.
- `app/src/main/java/com/funkyotc/puzzleverse/pullpin/viewmodel/PullPinViewModel.kt`: lifecycle, replay/retry, campaign and Daily integration.
- `app/src/main/java/com/funkyotc/puzzleverse/pullpin/ui/PullPinScreen.kt`: simple rendering, input, hints and objective feedback.
- `app/src/test/java/com/funkyotc/puzzleverse/pullpin/`: replace obsolete ball-rule assertions with real rescue-rule and campaign proofs while retaining applicable integration regressions.
- `app/src/main/java/generators/pullpin/PullPinGeneratorRunner.kt` and `scripts/generate_pullpin.py`: migrate validation to the rescue format.

## 8. Decisions to resolve through the prototype

Exact stone count/size, friction/restitution, burial coverage threshold and grace period, settling duration, final timing bands, king collision shape and performance budget remain tuning decisions. Later escape movement and optional challenge variants need their own small design pass when that phase starts. None of these justify silently changing the approved finite-stock, irreversible-pin, gradual-burial core.
