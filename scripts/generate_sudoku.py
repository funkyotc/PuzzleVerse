import os
import json
import random
import time
import numpy as np

class SudokuGenerator:
    def __init__(self):
        self.grid = np.zeros((9, 9), dtype=int)

    def is_valid(self, row, col, num):
        # Row check
        if num in self.grid[row, :]: return False
        # Column check
        if num in self.grid[:, col]: return False
        # Box check
        br, bc = 3 * (row // 3), 3 * (col // 3)
        if num in self.grid[br:br+3, bc:bc+3]: return False
        return True

    def solve(self, grid=None):
        """Standard backtracking solver using numpy for speed."""
        if grid is None: grid = self.grid
        
        empty = np.argwhere(grid == 0)
        if len(empty) == 0:
            return 1
            
        r, c = empty[0]
        count = 0
        
        # Optimization: use bitmask to find available numbers
        used = set(grid[r, :]) | set(grid[:, c]) | set(grid[3*(r//3):3*(r//3)+3, 3*(c//3):3*(c//3)+3].flatten())
        available = [n for n in range(1, 10) if n not in used]
        
        for num in available:
            grid[r, c] = num
            count += self.solve(grid)
            grid[r, c] = 0
            if count >= 2: break # We only need to know if it's unique
        return count

    def fill_grid(self):
        """Fill grid using randomized backtracking."""
        empty = np.argwhere(self.grid == 0)
        if len(empty) == 0:
            return True
            
        r, c = empty[0]
        nums = list(range(1, 10))
        random.shuffle(nums)
        
        for num in nums:
            if self.is_valid(r, c, num):
                self.grid[r, c] = num
                if self.fill_grid():
                    return True
                self.grid[r, c] = 0
        return False

    def generate_puzzle(self, target_clues):
        self.grid.fill(0)
        self.fill_grid()
        solution = self.grid.copy()
        
        puzzle = solution.copy()
        positions = [(r, c) for r in range(9) for c in range(9)]
        random.shuffle(positions)
        
        removed = 0
        for r, c in positions:
            if 81 - removed <= target_clues: break
            
            val = puzzle[r, c]
            puzzle[r, c] = 0
            
            # Check uniqueness
            test_grid = puzzle.copy()
            if self.solve(test_grid) != 1:
                puzzle[r, c] = val # Restore
            else:
                removed += 1
                
        return puzzle, solution

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    
    configs = [
        {"name": "Easy", "range": (36, 45), "count": 20},
        {"name": "Medium", "range": (28, 35), "count": 20},
        {"name": "Hard", "range": (22, 27), "count": 15},
        {"name": "Expert", "range": (17, 22), "count": 10}
    ]
    
    gen = SudokuGenerator()
    
    for cfg in configs:
        folder = os.path.join(output_dir, f"Sudoku_{cfg['name']}")
        os.makedirs(folder, exist_ok=True)
        
        for i in range(cfg["count"]):
            clues = random.randint(*cfg["range"])
            p, s = gen.generate_puzzle(clues)
            
            data = {
                "difficulty": cfg["name"],
                "clues": int(np.count_nonzero(p)),
                "puzzle": p.tolist(),
                "solution": s.tolist()
            }
            
            with open(os.path.join(folder, f"puzzle_{i+1:03d}.json"), "w") as f:
                json.dump(data, f, indent=2)
            print(f"Generated Sudoku {cfg['name']} {i+1}")

if __name__ == "__main__":
    main()
