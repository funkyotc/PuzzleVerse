import os
import json
import random
import numpy as np

class KakuroGenerator:
    def __init__(self, size):
        self.size = size
        self.grid = np.zeros((size, size), dtype=int) # 0: Black/Clue, 1: White

    def generate_layout(self):
        """Procedural layout generation for Kakuro."""
        self.grid.fill(0)
        # Margin of black cells
        self.grid[1:, 1:] = 1 # Potential white cells
        
        # Randomly block cells to create "runs"
        for _ in range(int(self.size * self.size * 0.35)):
            r, c = random.randint(1, self.size-1), random.randint(1, self.size-1)
            self.grid[r, c] = 0
            
        # Clean up: no single white cells, no 2x2 white blocks
        for _ in range(3): # Iterative cleanup
            for r in range(1, self.size):
                for c in range(1, self.size):
                    if self.grid[r, c] == 1:
                        # Horizontal run length
                        h_run = self.get_run(r, c, (0, 1))
                        v_run = self.get_run(r, c, (1, 0))
                        if len(h_run) < 2 and len(v_run) < 2:
                            self.grid[r, c] = 0
                            
            # Break 2x2 blocks
            for r in range(1, self.size-1):
                for c in range(1, self.size-1):
                    if np.all(self.grid[r:r+2, c:c+2] == 1):
                        self.grid[r + random.randint(0,1), c + random.randint(0,1)] = 0
                        
        return self.grid

    def get_run(self, r, c, delta):
        dr, dc = delta
        run = []
        # Go back to start
        cr, cc = r, c
        while 0 <= cr - dr < self.size and 0 <= cc - dc < self.size and self.grid[cr - dr, cc - dc] == 1:
            cr -= dr
            cc -= dc
        # Collect run
        while 0 <= cr < self.size and 0 <= cc < self.size and self.grid[cr, cc] == 1:
            run.append((cr, cc))
            cr += dr
            cc += dc
        return run

    def fill_and_clue(self):
        white_cells = [(r, c) for r in range(self.size) for c in range(self.size) if self.grid[r, c] == 1]
        values = {}
        
        def solve(idx):
            if idx == len(white_cells): return True
            r, c = white_cells[idx]
            
            h_run = self.get_run(r, c, (0, 1))
            v_run = self.get_run(r, c, (1, 0))
            
            used = {values[p] for p in h_run if p in values} | {values[p] for p in v_run if p in values}
            nums = [n for n in range(1, 10) if n not in used]
            random.shuffle(nums)
            
            for n in nums:
                values[(r, c)] = n
                if solve(idx + 1): return True
                del values[(r, c)]
            return False

        if solve(0):
            # Calculate clues
            clues = {}
            for r in range(self.size):
                for c in range(self.size):
                    if self.grid[r, c] == 0:
                        h_clue = None
                        if c + 1 < self.size and self.grid[r, c+1] == 1:
                            h_clue = sum(values[p] for p in self.get_run(r, c+1, (0, 1)))
                        
                        v_clue = None
                        if r + 1 < self.size and self.grid[r+1, c] == 1:
                            v_clue = sum(values[p] for p in self.get_run(r+1, c, (1, 0)))
                        
                        if h_clue or v_clue:
                            clues[(r, c)] = {"h": h_clue, "v": v_clue}
            return values, clues
        return None

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output", "kakuro")
    os.makedirs(output_dir, exist_ok=True)
    
    configs = [(4, "Easy", 15), (6, "Medium", 10), (8, "Hard", 10)]
    
    for size, diff, count in configs:
        gen = KakuroGenerator(size)
        for i in range(count):
            while True:
                gen.generate_layout()
                res = gen.fill_and_clue()
                if res:
                    values, clues = res
                    break
            
            grid_data = []
            for r in range(size):
                row = []
                for c in range(size):
                    if gen.grid[r, c] == 0:
                        if (r, c) in clues:
                            row.append({"type": "CLUE", "clue": {"horizontalSum": clues[(r, c)]["h"], "verticalSum": clues[(r, c)]["v"]}, "row": r, "col": c})
                        else:
                            row.append({"type": "BLACK", "row": r, "col": c})
                    else:
                        row.append({"type": "PLAYER_INPUT", "val": values[(r, c)], "row": r, "col": c})
                grid_data.append(row)
                
            p = {"difficulty": diff, "size": size, "grid": grid_data}
            with open(os.path.join(output_dir, f"{diff}_{i+1}.json"), "w") as f:
                json.dump(p, f, indent=2)
            print(f"Generated Kakuro {diff} {i+1}")

if __name__ == "__main__":
    main()
