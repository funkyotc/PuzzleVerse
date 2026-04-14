import os
import json
import random
import numpy as np

class NonogramGenerator:
    def __init__(self, size):
        self.size = size
        self.grid = np.zeros((size, size), dtype=bool)

    def get_clues(self, line):
        clues = []
        count = 0
        for val in line:
            if val: count += 1
            elif count > 0:
                clues.append(count)
                count = 0
        if count > 0: clues.append(count)
        return clues

    def solve_line(self, clues, length):
        """Memoized line solver."""
        memo = {}

        def solve(clue_idx, start_pos):
            state = (clue_idx, start_pos)
            if state in memo: return memo[state]
            
            if clue_idx == len(clues):
                # No more clues, remaining must be empty
                res = [[False] * (length - start_pos)]
                memo[state] = res
                return res

            res = []
            clue = clues[clue_idx]
            # Calculate min space needed for remaining clues
            remaining_clues = clues[clue_idx+1:]
            min_needed = sum(remaining_clues) + len(remaining_clues)
            
            # Try placing current clue at all possible positions
            for p in range(start_pos, length - min_needed - clue + 1):
                # Ensure gap before if not the first clue
                if clue_idx > 0 and p == start_pos: continue
                
                prefix = [False] * (p - start_pos) + [True] * clue
                # Must follow with a gap if not last
                if clue_idx < len(clues) - 1:
                    prefix.append(False)
                    
                sub_solutions = solve(clue_idx + 1, p + clue + (1 if clue_idx < len(clues) - 1 else 0))
                for sub in sub_solutions:
                    res.append(prefix + sub)
            
            memo[state] = res
            return res

        return solve(0, 0)

    def is_unique(self, row_clues, col_clues):
        """Deductive line solver to check for strict unique solvability."""
        size = self.size
        state = np.full((size, size), -1, dtype=int) # -1: unknown, 0: empty, 1: filled
        
        row_poss = [self.solve_line(c, size) for c in row_clues]
        col_poss = [self.solve_line(c, size) for c in col_clues]
        
        changed = True
        while changed:
            changed = False
            # Check Rows
            for r in range(size):
                if not row_poss[r]: return False
                for c in range(size):
                    if state[r, c] == -1:
                        first = row_poss[r][0][c]
                        if all(p[c] == first for p in row_poss[r]):
                            state[r, c] = 1 if first else 0
                            changed = True
                            
            # Filter Columns
            for c in range(size):
                col_poss[c] = [p for p in col_poss[c] if all(state[r, c] == -1 or state[r, c] == (1 if p[r] else 0) for r in range(size))]
                
            # Check Columns
            for c in range(size):
                if not col_poss[c]: return False
                for r in range(size):
                    if state[r, c] == -1:
                        first = col_poss[c][0][r]
                        if all(p[r] == first for p in col_poss[c]):
                            state[r, c] = 1 if first else 0
                            changed = True
                            
            # Filter Rows
            for r in range(size):
                row_poss[r] = [p for p in row_poss[r] if all(state[r, c] == -1 or state[r, c] == (1 if p[c] else 0) for c in range(size))]

        return np.all(state != -1)

    def generate(self, difficulty):
        while True:
            # Generate random shape with some symmetry
            self.grid.fill(False)
            density = random.uniform(0.3, 0.6)
            self.grid = np.random.rand(self.size, self.size) < density
            
            if random.random() < 0.5: # Symmetric
                self.grid = self.grid | self.grid[:, ::-1]
                
            row_clues = [self.get_clues(self.grid[r, :]) for r in range(self.size)]
            col_clues = [self.get_clues(self.grid[:, c]) for c in range(self.size)]
            
            if self.is_unique(row_clues, col_clues):
                return {
                    "size": self.size,
                    "difficulty": difficulty,
                    "row_clues": row_clues,
                    "col_clues": col_clues,
                    "grid_str": "".join("1" if c else "0" for row in self.grid for c in row)
                }

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output", "nonogram")
    os.makedirs(output_dir, exist_ok=True)
    
    configs = [(5, "Easy", 15), (10, "Medium", 15), (15, "Hard", 10)]
    
    for size, diff, count in configs:
        gen = NonogramGenerator(size)
        for i in range(count):
            p = gen.generate(diff)
            p["id"] = f"nonogram_{diff.lower()}_{i+1}"
            with open(os.path.join(out_dir if 'out_dir' in locals() else output_dir, f"{p['id']}.json"), "w") as f:
                json.dump(p, f, indent=2)
            print(f"Generated Nonogram {diff} {i+1}")

if __name__ == "__main__":
    main()
