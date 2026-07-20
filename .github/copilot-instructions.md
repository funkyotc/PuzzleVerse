# PuzzleVerse Copilot Instructions

## Build, Test, and Lint Commands
- Build debug APK: `gradlew.bat assembleDebug`
- Build staging APK: `gradlew.bat assembleStaging`
- Build release APK: `gradlew.bat assembleFinalRelease`
- Run unit tests: `gradlew.bat testDebugUnitTest`
- Run instrumented tests: `gradlew.bat connectedDebugAndroidTest`
- Lint code: `gradlew.bat lintDebug`
- Clean build: Delete `.gradle` lock files if Gradle hangs

## High-Level Architecture
- **Entry Point**: `MainActivity.kt` initializes navigation, sound, and theme
- **Navigation Structure**: Routes follow patterns:
  - `game/{gameId}/{mode}` - Standard/Daily game modes
  - `game/{gameId}/{mode}/new` - New puzzle generation
  - `{gameId}/puzzles` - Puzzle browsing
  - `game/{gameId}/puzzle/{puzzleId}` - Specific puzzle views
- **Data Organization**: 
  - Pre-generated puzzles stored in `data/{Game}Pregenerated.kt` files
  - Game assets (JSON, text) in `assets/{game}/` directories
  - Sound manager initialized in MainActivity and exposed via CompositionLocal
- **State Management**: ViewModel pattern with Compose state hoisting
- **Theming**: 7 themes via `PuzzleVerseTheme` with dynamic color support on Android 12+

## Key Conventions
- **Pre-generated Data**: All puzzles are pre-generated Kotlin objects, not runtime-generated
- **Mode Implementation**: Standard and Daily modes are implemented separately
- **Theme System**: 7 predefined themes with dynamic color support
- **Navigation**: Compose Navigation with Accompanist animations
- **Sound Management**: Centralized SoundManager using SoundPool
- **Theme Customization**: Dynamic color support on Android 12+

## Critical Gotchas
- JDK Path: `gradle.properties` previously had hardcoded `org.gradle.java.home` - ensure JAVA_HOME is set if issues arise
- Puzzle Logic: Modifying puzzle generation requires updating both source code and assets
- No CI/CD: Manual builds required - Gradle wrapper is the primary build tool
- Build Types: Only `finalRelease` uses minification and resource shrinking