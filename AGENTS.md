# AGENTS.md — PuzzleVerse

## Project Overview
Android app with 5+ puzzle games (Sudoku, Nonogram, Kakuro, Word Search, Bonza). Each game has Standard and Daily modes with streak tracking and resume functionality.

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
```

## Tech Stack
- Kotlin 2.2.10, AGP 9.1.1, Compose BOM 2024.06.00
- minSdk 24, compileSdk/targetSdk 36
- Java 11 compatibility
- Single module: `:app`

## Architecture
- **Entry**: `MainActivity.kt` — NavHost with routes `game/{gameId}/{mode}/{puzzleId}`
- **Navigation**: Compose Navigation + Accompanist animations
- **Data**: Pre-generated puzzles in `data/{Game}Pregenerated.kt` + `assets/bonza/puzzles.json`
- **State**: ViewModel + Compose state hoisting

## Build Types
| Type | Minify | Shrink Resources | App ID Suffix |
|------|--------|-----------------|---------------|
| debug | no | no | — |
| staging | no | no | `.staging` |
| release | no | no | — |
| finalRelease | yes | yes | — |

## Critical Gotchas
- **JDK Path**: `gradle.properties` hardcodes `org.gradle.java.home=C:/Program Files/Android/Android Studio/jbr`. Will fail on non-Windows or custom JDK setups.
- **Pre-generated Data**: Daily/Standard puzzles use pre-generated Kotlin objects, NOT runtime generation. Modifying puzzle logic requires updating both sources.
- **finalRelease**: Only build type with minification + resource shrinking enabled.

## Key Files
- `app/build.gradle.kts` — Build config, dependencies, build types
- `gradle/libs.versions.toml` — Version catalog
- `gradle.properties` — JVM args, JDK path, AndroidX flags
- `app/src/main/java/com/funkyotc/puzzleverse/MainActivity.kt` — Navigation wiring
- `app/src/main/java/com/funkyotc/puzzleverse/data/` — Pre-generated puzzle data
- `app/src/main/assets/bonza/puzzles.json` — Bonza puzzle data
