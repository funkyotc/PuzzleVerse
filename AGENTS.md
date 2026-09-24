# AGENTS.md — PuzzleVerse

## Project Overview
Android app with 20 games listed in `ui/screens/home/HomeScreen.kt`. Eighteen have checked-in puzzle data or assets; 2048 and Minesweeper generate boards at runtime. The UI offers Standard / Daily entry points for each game, plus puzzle browsers for 17 games. Do not assume daily boards are identical across devices: Minesweeper and 2048 use unseeded randomness.

## Commands
```bash
# Build (use gradlew.bat on Windows, ./gradlew on Linux/Mac)
## IMPORTANT: Do NOT append `2>&1` — PowerShell wraps stderr in ErrorRecord objects,
## making build errors invisible and causing hangs on Gradle failures.
gradlew.bat assembleDebug
gradlew.bat assembleStaging
gradlew.bat assembleFinalRelease

# If build hangs, clean stale Gradle locks first:
#   Get-ChildItem -Recurse -Filter "*.lock" .\.gradle\ | Remove-Item -Force
# Or: gradlew.bat --stop

# Tests
gradlew.bat testDebugUnitTest
gradlew.bat connectedDebugAndroidTest

# Lint
gradlew.bat lintDebug

# Commit changes after completing a task
## Use the git-commit skill for conventional commit message generation
git add -A && git commit -m "type(scope): description"
```

## Tech Stack
- Kotlin 2.2.10, AGP 9.3.1, Compose BOM 2024.06.00, Gradle 9.5.0
- Gradle daemon toolchain JDK 21; Java source/target compatibility 11
- minSdk 24, compileSdk/targetSdk 36
- Single module: `:app`

## Architecture
- **Entry**: `MainActivity.kt` — Compose `NavHost` with transition animations
- **Navigation routes**: `game/{gameId}/{mode}`, `game/{gameId}/{mode}/new`, `{gameId}/puzzles`, `game/{gameId}/puzzle/{puzzleId}`
- **Data**: Pre-generated puzzles in `data/{Game}Pregenerated.kt` + `assets/bonza/puzzles.json`, `assets/wordle/valid_words.txt`
- **State**: ViewModel + Compose state hoisting
- **Sound**: `SoundManager` (SoundPool) loaded in MainActivity, provided via `LocalSoundManager` CompositionLocal
- **Theme**: `PuzzleVerseTheme` — 8 theme IDs (default/dark/light/ocean/forest/sunset/cyberpunk/2048); dynamic color is available on Android 12+ but disabled by default

## Build Types
| Type | Minify | Shrink Resources | App ID Suffix | Debuggable |
|------|--------|-----------------|---------------|------------|
| debug | no | no | — | yes |
| staging | no | no | `.staging` | yes |
| release | no | no | — | no |
| finalRelease | yes | yes | — | no |

`versionCode` reads `GITHUB_RUN_NUMBER` env var, defaults to 1. `finalRelease` is the only build type with minification + resource shrinking.

## Critical Gotchas
- **JDK Path**: `gradle.properties` had `org.gradle.java.home` hardcoded — now commented out. Agents on Windows with Android Studio should be fine; non-standard setups may need to set `JAVA_HOME`.
- **Pre-generated Data & Generators**: 17 games have `*Pregenerated.kt` files and Wordle has a checked-in word list. The 18 corresponding generator runners under `app/src/main/java/generators/` run on demand, outside normal Gradle build and test tasks. `tfe` and `minesweeper` use runtime generation.
- **CI**: `.github/workflows/build-debug.yml` runs unit tests and a debug build on pull requests and pushes to `master`. Its Java setup requests 11 while the Gradle daemon toolchain requests 21; verify CI before relying on it.
- **No opencode.json**: Repository lacks opencode configuration; consider creating one for custom instructions.
- **Compose Multi-Touch Gestures**: Stacking standard `detectDragGestures` and `detectTapGestures` suppresses multi-finger input (e.g. tapping secondary finger while dragging). Use `awaitPointerEventScope` to track multi-pointer state explicitly.
- **SVG Silhouette Paths**: Composite puzzle silhouettes must combine constituent SVG piece paths via geometric `Path.Op.UNION` to strip internal edges and render clean outer outlines.

## Key Files
- `app/build.gradle.kts` — Build config, dependencies, build types (namespace `com.funkyotc.puzzleverse`)
- `gradle/libs.versions.toml` — Version catalog
- `gradle.properties` — JVM args, AndroidX flags
- `app/src/main/java/com/funkyotc/puzzleverse/MainActivity.kt` — Navigation wiring, SoundManager init, theme setup
- `app/src/main/java/com/funkyotc/puzzleverse/ui/screens/home/HomeScreen.kt` — Game list definition
- `app/src/main/java/generators/` — Standalone generator runners for 18 games with checked-in puzzle data or assets
- `app/src/main/assets/bonza/puzzles.json` — Bonza puzzle themes
- `app/src/main/assets/wordle/valid_words.txt` — Wordle dictionary
