3. Constellations
Description of Gameplay and Rules: Constellations is a strategic logic puzzle played on a grid that is divided into distinct, color-coded regions. The objective is to place a single star in each row, each column, and each colored region. There is an additional critical rule: stars cannot be placed in cells that are adjacent to each other, and this includes diagonal adjacency. Players must use logic to deduce the correct positions for the stars based on these constraints.
Win Conditions: The puzzle is solved when every row, every column, and every colored region on the grid contains exactly one star, and no two stars are touching (horizontally, vertically, or diagonally).
Development Plan:
    • Phase 1: Core Logic & Puzzle Generation
        ◦ Develop an algorithm to procedurally generate valid Constellations puzzles, including the grid layout, colored regions, and a guaranteed unique solution.
        ◦ Implement the game's rule set in code, preventing players from placing stars in invalid positions (e.g., two in one row, or adjacent to another star).
        ◦ Create a robust validation function that can instantly check the board state for the win condition.
    • Phase 2: User Interface (UI) and User Experience (UX)
        ◦ Design a clear grid interface where the colored regions are easily distinguishable.
        ◦ Implement a simple and intuitive interaction for placing and removing stars from cells (e.g., a single click or tap).
        ◦ Provide immediate visual feedback when a player attempts to make an invalid move, such as a red highlight or a shake animation.
    • Phase 3: Features and Polish
        ◦ Introduce varying grid sizes and complexities to create different difficulty levels.
        ◦ Add a hint system that reveals the correct location of one star.
        ◦ Include an "error check" button that highlights any incorrectly placed stars on the board.
        ◦ Implement a timer and a move counter for players who want to challenge themselves.
    • Phase 4: Testing and Deployment
        ◦ Rigorously test the puzzle generator to ensure it consistently produces solvable puzzles with unique solutions.
        ◦ Ensure the UI is responsive and works well on both desktop and mobile screens.
        ◦ Deploy the game as part of the PuzzleVerse suite.