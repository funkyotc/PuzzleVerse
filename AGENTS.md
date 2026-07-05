# AGENTS.md — PuzzleVerse

## Project Overview
Android app with 17 puzzle games: sudoku, bonza, constellations, shapes, wordle, tfe (2048), minesweeper, nonogram, blockpuzzle, kakuro, flowfree, shikaku, cubeshooter, pullpin, watersort, woodnuts, hexasort. Each game has Standard / Daily modes with streak tracking and resume.

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
- Kotlin 2.2.10, AGP 9.3.0-rc01, Compose BOM 2024.06.00
- minSdk 24, compileSdk/targetSdk 36
- Java 11 compatibility
- Single module: `:app`

## Architecture
- **Entry**: `MainActivity.kt` — AnimatedNavHost with Accompanist navigation-animation
- **Navigation routes**: `game/{gameId}/{mode}`, `game/{gameId}/{mode}/new`, `{gameId}/puzzles`, `game/{gameId}/puzzle/{puzzleId}`
- **Data**: Pre-generated puzzles in `data/{Game}Pregenerated.kt` + `assets/bonza/puzzles.json`, `assets/wordle/valid_words.txt`
- **State**: ViewModel + Compose state hoisting
- **Sound**: `SoundManager` (SoundPool) loaded in MainActivity, provided via `LocalSoundManager` CompositionLocal
- **Theme**: `PuzzleVerseTheme` — 7 themes (default/dark/light/ocean/forest/sunset/cyberpunk/2048), dynamic color on Android 12+

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
- **Pre-generated Data**: Daily/Standard puzzles use pre-generated Kotlin objects, NOT runtime generation. Modifying puzzle logic requires updating both sources.
- **No CI/CD**: No GitHub Actions or other CI workflows exist. Builds are manual.
- **No opencode.json**: Repository lacks opencode configuration; consider creating one for custom instructions.

## Key Files
- `app/build.gradle.kts` — Build config, dependencies, build types (namespace `com.funkyotc.puzzleverse`)
- `gradle/libs.versions.toml` — Version catalog
- `gradle.properties` — JVM args, AndroidX flags
- `app/src/main/java/com/funkyotc/puzzleverse/MainActivity.kt` — Navigation wiring, SoundManager init, theme setup
- `app/src/main/java/com/funkyotc/puzzleverse/ui/screens/home/HomeScreen.kt` — Game list definition
- `app/src/main/assets/bonza/puzzles.json` — Bonza puzzle themes
- `app/src/main/assets/wordle/valid_words.txt` — Wordle dictionary (5758 words)
