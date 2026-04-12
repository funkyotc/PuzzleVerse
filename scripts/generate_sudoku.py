"""
Offline Sudoku puzzle generator.
Generates puzzles at 4 difficulty levels and saves as JSON.
Run: python scripts/generate_sudoku.py
"""
import os
import json
import random
import time

DIFFICULTIES = [
    {"name": "Easy", "clues_range": (36, 45), "target": 20},
    {"name": "Medium", "clues_range": (28, 35), "target": 20},
    {"name": "Hard", "clues_range": (22, 27), "target": 15},
    {"name": "Expert", "clues_range": (17, 21), "target": 10}
]


def solve(grid, find_all=False, max_solutions=2):
    """Backtracking solver. Returns solution count (up to max_solutions)."""
    empty = find_empty(grid)
    if not empty:
        return 1

    r, c = empty
    count = 0
    for num in range(1, 10):
        if is_valid(grid, r, c, num):
            grid[r][c] = num
            count += solve(grid, find_all, max_solutions)
            grid[r][c] = 0
            if count >= max_solutions:
                return count
    return count


def find_empty(grid):
    for r in range(9):
        for c in range(9):
            if grid[r][c] == 0:
                return (r, c)
    return None


def is_valid(grid, row, col, num):
    # Row
    if num in grid[row]:
        return False
    # Column
    for r in range(9):
        if grid[r][col] == num:
            return False
    # Box
    br, bc = 3 * (row // 3), 3 * (col // 3)
    for r in range(br, br + 3):
        for c in range(bc, bc + 3):
            if grid[r][c] == num:
                return False
    return True


def generate_solved_grid():
    """Generate a fully solved 9x9 Sudoku grid."""
    grid = [[0] * 9 for _ in range(9)]
    fill_grid(grid)
    return grid


def fill_grid(grid):
    empty = find_empty(grid)
    if not empty:
        return True
    r, c = empty
    nums = list(range(1, 10))
    random.shuffle(nums)
    for num in nums:
        if is_valid(grid, r, c, num):
            grid[r][c] = num
            if fill_grid(grid):
                return True
            grid[r][c] = 0
    return False


def rate_difficulty(grid):
    """
    Count solver backtracks as difficulty metric.
    More backtracks = harder puzzle.
    """
    counter = [0]
    
    def solve_count(g):
        empty = find_empty(g)
        if not empty:
            return True
        r, c = empty
        for num in range(1, 10):
            if is_valid(g, r, c, num):
                g[r][c] = num
                counter[0] += 1
                if solve_count(g):
                    return True
                g[r][c] = 0
        return False
    
    test = [row[:] for row in grid]
    solve_count(test)
    return counter[0]


def create_puzzle(clues_min, clues_max):
    """
    Generate a valid Sudoku puzzle with unique solution.
    Returns (puzzle_grid, solution_grid, num_clues, backtrack_count).
    """
    solution = generate_solved_grid()
    puzzle = [row[:] for row in solution]
    
    # Number of clues to keep
    target_clues = random.randint(clues_min, clues_max)
    cells_to_remove = 81 - target_clues
    
    # Randomize removal order
    positions = [(r, c) for r in range(9) for c in range(9)]
    random.shuffle(positions)
    
    removed = 0
    for r, c in positions:
        if removed >= cells_to_remove:
            break
        
        backup = puzzle[r][c]
        puzzle[r][c] = 0
        
        # Verify unique solution
        test = [row[:] for row in puzzle]
        if solve(test, find_all=True, max_solutions=2) != 1:
            puzzle[r][c] = backup  # Restore — removing this breaks uniqueness
        else:
            removed += 1
    
    actual_clues = sum(1 for r in range(9) for c in range(9) if puzzle[r][c] != 0)
    backtracks = rate_difficulty(puzzle)
    
    return puzzle, solution, actual_clues, backtracks


def main():
    output_base = os.path.join(os.path.dirname(os.path.abspath(__file__)), "output")
    os.makedirs(output_base, exist_ok=True)
    
    print("Sudoku Puzzle Generator")
    print()
    
    for diff in DIFFICULTIES:
        folder_name = f"Sudoku_{diff['name']}"
        folder_path = os.path.join(output_base, folder_name)
        os.makedirs(folder_path, exist_ok=True)
        
        target = diff['target']
        clues_min, clues_max = diff['clues_range']
        print(f"=== {folder_name}: Generating {target} puzzles ({clues_min}-{clues_max} clues) ===")
        t_start = time.time()
        
        for i in range(target):
            puzzle, solution, clues, backtracks = create_puzzle(clues_min, clues_max)
            
            data = {
                "difficulty": diff['name'],
                "clues": clues,
                "backtracks": backtracks,
                "puzzle": puzzle,
                "solution": solution
            }
            
            filename = f"puzzle_{i+1:03d}.json"
            filepath = os.path.join(folder_path, filename)
            with open(filepath, 'w') as f:
                json.dump(data, f, indent=2)
            
            elapsed = time.time() - t_start
            print(f"  [{folder_name}] {i+1}/{target} done "
                  f"({clues} clues, {backtracks} backtracks, {elapsed:.1f}s)", flush=True)
        
        elapsed = time.time() - t_start
        print(f"  Completed {folder_name} in {elapsed:.1f}s\n")
    
    print("All done! Sudoku puzzles saved to scripts/output/")


if __name__ == '__main__':
    main()
