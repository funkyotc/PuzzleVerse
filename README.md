# PuzzleVerse

PuzzleVerse is a native Android puzzle hub built with Kotlin and Jetpack Compose. The home screen lists 20 games, with Standard and Daily entry points, local progress, and daily streaks. Some games also offer a puzzle browser or difficulty choices.

## Games

Sudoku, Bonza, Constellations, Wordle, 2048, Minesweeper, Nonogram, Kakuro, Flow Free, Shikaku, Cube Shooter, Pull the Pin, Water Sort, Wood Screws, Hexa Sort, Hexa Stack, Chess, Hashi, Arrow Escape, and Tangrams.

The game catalog lives in [`HomeScreen.kt`](app/src/main/java/com/funkyotc/puzzleverse/ui/screens/home/HomeScreen.kt). Route wiring and puzzle browsers live in [`MainActivity.kt`](app/src/main/java/com/funkyotc/puzzleverse/MainActivity.kt). Daily mode is available from the UI for all 20 games, but do not assume every daily board is the same across devices: Minesweeper places mines with an unseeded shuffle after the first tap, and 2048 adds random tiles.

## Build and test

Use Android Studio with the Android SDK for API 36 and a JDK 21 installation. The Gradle wrapper is 9.5.0, and the daemon toolchain requests JDK 21. Java source and target compatibility are set to 11. The app supports Android 7.0 (API 24) and later.

From PowerShell:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest
.\gradlew.bat lintDebug
```

On Linux or macOS, use `./gradlew` instead. Instrumented tests require a connected device or emulator: `connectedDebugAndroidTest`. Build variants include `debug`, `staging` (application ID suffix `.staging`), `release`, and `finalRelease` (minification and resource shrinking). The debug APK is written to `app/build/outputs/apk/debug/`.

If the wrapper chooses an unwritable Gradle cache, set `GRADLE_USER_HOME` to a writable directory before running it. Do not append `2>&1` to the PowerShell build commands; it can obscure Gradle error output.

GitHub Actions runs unit tests and a debug build on pull requests and pushes to `master` in [`.github/workflows/build-debug.yml`](.github/workflows/build-debug.yml). The workflow's Java setup currently specifies 11 while the Gradle daemon requests 21; see the [project review](docs/project-review-2026-09-24.md) before relying on CI status.

## Project layout

- `app/src/main/java/com/funkyotc/puzzleverse/`: game UI, ViewModels, data, navigation, settings, sound, streaks, and shared save metadata.
- `app/src/main/java/generators/`: standalone generator runners used on demand to update checked-in puzzle data. They are separate from normal Gradle build and test tasks.
- `app/src/main/assets/`: Bonza puzzle JSON, the Wordle dictionary, and Tangrams SVGs.
- `app/src/test/`: JVM tests for game logic, generators, save state, audio, and streaks.
- `app/src/androidTest/`: Android instrumented test source.
- `docs/archive/`: older game notes and plans. [`docs/implementation_plan.md`](docs/implementation_plan.md) is a historical proposal, not a verified completion record.

State is stored locally using `SharedPreferences` repositories. [`SaveStateRepository.kt`](app/src/main/java/com/funkyotc/puzzleverse/core/data/SaveStateRepository.kt) tracks resume metadata; several games have their own board repositories. Settings include sound effects and unlockable themes. `MainActivity` supplies the sound and save repositories through Compose locals.

## Current review

The [September 2026 project review](docs/project-review-2026-09-24.md) records the code and documentation findings plus build and test evidence. It is a snapshot, not a release certification.
