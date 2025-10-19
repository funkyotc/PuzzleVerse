### **Project: PuzzleVerse - Daily & Standard Puzzles**

### **1. High-Level Summary**

The goal is to create a polished, engaging, and user-friendly native Android application named "PuzzleVerse". The app will serve as a hub for multiple classic puzzle games, each with two modes: a "Standard" mode for casual play and a "Daily Challenge" mode that is consistent for all users each day. The initial game roster will include Sudoku, Bonza, Constellations, Shapes, and Wordle.

The application will be built using modern, best-practice Android development technologies to ensure it is robust, maintainable, and has a high-quality feel. This document reflects the current state of the application, using the Sudoku game as a template for future development.

### **2. Core Features**

*   **Game Hub:** A central home screen to browse and select from the available puzzle games.
*   **Dual Game Modes:**
    *   **Standard Play:** Generate unique, random puzzles on-demand for unlimited play.
    *   **Daily Challenge:** A single, unique puzzle for each game, refreshed every 24 hours.
*   **Resume Puzzle:** For standard games, progress is automatically saved, allowing users to resume an unfinished puzzle.
*   **Daily Challenge Streak:** The app tracks the number of consecutive days a user has completed a daily challenge for each game. This streak is displayed on the home screen.
*   **Five Initial Games:**
    *   **Sudoku:** Classic 9x9 number grid puzzle.
    *   **Bonza:** A word puzzle where users connect word fragments on a grid.
    *   **Constellations:** A logic game where the goal is to place stars in every row, column, and color region.
    *   **Shapes:** A tangram-style puzzle where users fit geometric pieces into a target silhouette.
    *   **Wordle:** The popular 5-letter word guessing game.
*   **Local Progress Storage:** User progress for daily challenges, standard games, and streaks are saved on the device.
*   **Fullscreen Mode:** The app runs in an immersive fullscreen mode, hiding the system status and navigation bars.
*   **Launch Animation:** A simple and elegant launch animation using the app's adaptive icon.

### **3. Sudoku - The Template Game**

The Sudoku game has been implemented with a polished UI and a robust set of features. It should be used as a template for the design and functionality of all other games in PuzzleVerse.

**Sudoku Features:**
*   **Game Board:** A clean, easy-to-read 9x9 grid with thicker borders for the 3x3 blocks.
*   **Number Input:** A 3x3 number pad for quick and intuitive number entry.
*   **Highlighting:**
    *   The selected cell, row, column, and 3x3 block are highlighted.
    *   All other instances of the selected number are also highlighted.
*   **Pencil Mode:** Allows users to enter notes in a cell. The pencil button and number pad are visually distinct when this mode is active.
*   **Erase Button:** A dedicated button to clear the contents of a selected cell.
*   **Undo Button:** Reverts the last move.
*   **Victory Detection:** The app automatically detects when the puzzle is solved and displays a congratulatory message.
*   **New Puzzle Generation:** For standard mode, users can generate a new, random puzzle at any time via a "Shuffle" button in the title bar, with a confirmation dialog to prevent accidental resets.

### **4. Technology Stack & Architecture**

*   **Language:** **Kotlin**
*   **UI Toolkit:** **Jetpack Compose**
*   **Architecture:** **MVVM (Model-View-ViewModel)**
*   **Navigation:** **Compose Navigation**
*   **Data Persistence:** **SharedPreferences** with **Gson** for serialization.
*   **Splash Screen API:** For the launch animation.

### **5. UI/UX Design Principles**

*   **Theme:** A clean, minimalist, and modern aesthetic based on **Material Design 3**.
*   **User Flow:**
    1.  **App Launch** -> **Splash Screen** -> **Home Screen** (Displays a grid of games with streak counters).
    2.  **Select a Game** -> **Game Detail Screen** (Presents "Standard Game" (with "Resume" or "New Game" options) and "Daily Challenge" options).
    3.  **Select a Mode** -> **Game Board Screen**.
*   **Game Screen Layout:**
    *   **`Scaffold`:** Each game screen is built using a `Scaffold`.
    *   **`TopAppBar`:** A `TopAppBar` displays the game's title, a back button, and game-specific actions (e.g., "New Puzzle").
    *   **Content:** The main game content is displayed in the body of the `Scaffold`, with appropriate padding to account for the system bars.

### **6. Development Plan (Updated)**

**Phase 1: Project Foundation & Core UI (Complete)**
*   Project setup with Kotlin and Jetpack Compose.
*   Core navigation structure implemented.
*   Main UI components designed and built.

**Phase 2: First Game Implementation - Sudoku (Complete)**
*   Data models and game logic developed.
*   Interactive Sudoku board UI created.
*   "Daily Challenge" and "Standard" modes implemented.
*   Local data storage for game state and streaks integrated.
*   Advanced features like pencil mode, highlighting, and undo implemented.

**Phase 3: Implementing the Remaining Games**
*Using the Sudoku implementation as a template:*
1.  **For each game (Wordle, Bonza, Constellations, Shapes):**
    *   Implement the game's data models and logic within a `ViewModel`.
    *   Create the interactive game board UI using Jetpack Compose, following the `Scaffold` and `TopAppBar` pattern.
    *   Implement the `Standard` and `Daily Challenge` modes, using the `ViewModelFactory` to handle puzzle loading and generation.
    *   Integrate streak tracking for daily challenges.
    *   Add any game-specific actions (e.g., a "Hint" button) to the `TopAppBar` or an action row at the bottom of the screen.

**Phase 4: Polish, Settings & Refinement**
1.  Add user settings (e.g., theme selection, sound on/off).
2.  Add sound effects for user interactions.
3.  Conduct thorough testing and bug fixing across all games and devices.
