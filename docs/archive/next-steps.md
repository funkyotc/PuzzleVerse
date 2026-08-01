# PuzzleVerse — Roadmap 2.0

## 🎯 Current Focus: Generation & Quality

### Shapes: Tangram Upgrade
- [ ] **7 Classic Pieces**: Update `scripts/generate_shapes.py` to use the standard 7 Tangram pieces.
- [ ] **Varied Puzzles**: Generate 20+ logic puzzles using all 7 pieces (Animals, People, Objects).
- [ ] **Bake & Integrate**: Update the app with the new Tangram content.

### Script Refinement & Logic
- [ ] **Optimization**: Further optimize backtracking solvers for Sudoku and Nonograms using more efficient pruning.
- [ ] **Difficulty Balancing**: Review and adjust difficulty ratings for Kakuro and Bonza based on word complexity/sum frequency.

---

## 🚀 Future Phases

### Cloud & Social
- [ ] **Cloud Sync**: Firebase integration for synchronizing progress, streaks, and Wordle stats across devices.
- [ ] **Global Leaderboards**: Daily challenge leaderboards for puzzle speed and streak maintenance.
- [ ] **Friend Challenges**: Ability to share a specific puzzle ID with friends for head-to-head solving.

### UI/UX Polish 2.0
- [ ] **Solved Animations**: High-quality particle effects and victory animations for all game types.
- [ ] **Dark Mode Refinement**: Custom themed colors for high-contrast visibility in dark mode.
- [ ] **Soundscape**: Unique ambient loops for each puzzle category.

---

## 🛠️ Infrastructure
- [ ] **Global Scheduler**: Move Daily Challenge selection to a server-side or local fixed-seed scheduler to ensure all players get the same puzzle each day.
- [ ] **Analytics**: Integration of light analytics to track which puzzle types are most popular.
