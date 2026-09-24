# Future features and experience backlog

Status: proposals, not confirmed defects or committed work. Correctness issues and release gates belong in [the active game audit fix plan](game-fix-plan-2026-09-24.md). Priorities below reflect likely player impact; they need product review before implementation.

## P1 — Follow-ups after audit fixes

| Candidate | Player benefit | Prerequisite |
| --- | --- | --- |
| Broader touch and gesture checks | More reliable input across all 20 games, including simultaneous touches | Define repeatable gestures and target devices |
| Hint behavior review | Hints stay useful without skipping the intended puzzle logic | Inventory existing hint actions and completion rules |
| Sound and haptics review | Consistent feedback across games | Review current audio and vibration on a device |
| Small-screen and accessibility pass | Usable boards, controls, and labels on compact phones and larger text settings | Choose target devices and text scales |
| Save and recovery UX | Clear feedback when old or damaged Daily progress is replaced | Complete dated Daily save handling |
| Visual gameplay checks | Catch clipping, confusing board states, animation faults, and win/loss dialog issues | Define device captures for representative levels and outcomes |
| Daily status and UTC rollover explanation | Players understand when a challenge resets and why progress changes | Ship consistent UTC Daily behavior |

## P2 — Broader candidate features

| Candidate | Player benefit | Prerequisite |
| --- | --- | --- |
| Chess board aids: coordinates, flip, history, undo, hints, captured pieces | Easier puzzle study and correction | Verify the current Chess UI and sound puzzle content first |
| Optional Chess standard play and local AI | A separate free-play mode | Product rules, difficulty target, and performance budget |
| Animation and visual polish for Hexa Stack and other games | Clearer outcomes and more satisfying feedback | Baseline gesture and frame-rate checks |
| Layout and audio refinement for Water Sort and other games | Better readability and feedback | Device review of current presentation |
| More varied procedural levels for Pull the Pin and Flow Free | Longer-lived challenge catalog | Solver-gated generation and all-level completion checks |

These broader candidates were inspired partly by the [historical implementation plan](implementation_plan.md). Check the current code and device behavior before opening an implementation task; some ideas may already be present.
