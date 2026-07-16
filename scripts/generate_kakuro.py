import os
import json
import random
import numpy as np
import copy

class KakuroGenerator:
    def __init__(self, size):
        self.size = size
        self.grid = np.zeros((size, size), dtype=int)
        self.values = {}

    def generate(self):
        # 1. Start with a filled valid grid
        # We fill grid[1:, 1:] with numbers 1-9 such that no row/col has duplicates.
        self.grid.fill(1)
        self.grid[0, :] = 0
        self.grid[:, 0] = 0
        
        white_cells = [(r, c) for r in range(1, self.size) for c in range(1, self.size)]
        
        # Simple backtracking to fill the grid
        self.values = {}
        def fill_latin(idx):
            if idx == len(white_cells): return True
            r, c = white_cells[idx]
            used = set()
            for i in range(1, self.size):
                if (r, i) in self.values: used.add(self.values[(r, i)])
                if (i, c) in self.values: used.add(self.values[(i, c)])
            nums = [n for n in range(1, min(10, self.size)) if n not in used]
            random.shuffle(nums)
            for n in nums:
                self.values[(r, c)] = n
                if fill_latin(idx + 1): return True
                del self.values[(r, c)]
            return False

        if not fill_latin(0):
            return None, None
            
        # 2. Strategically remove numbers (turn them into black cells)
        best_grid = copy.deepcopy(self.grid)
        
        for attempt in range(50):
            self.grid = copy.deepcopy(best_grid)
            
            cells = list(white_cells)
            random.shuffle(cells)
            
            placed_black = 0
            target_black = int(self.size * self.size * 0.35)
            
            for (r, c) in cells:
                if placed_black >= target_black:
                    break
                if self.grid[r, c] == 1:
                    r_sym, c_sym = self.size - r, self.size - c
                    self.grid[r, c] = 0
                    if 1 <= r_sym < self.size and 1 <= c_sym < self.size:
                        self.grid[r_sym, c_sym] = 0
                    placed_black += 2

            # Clean up runs of length 1
            changed = True
            while changed:
                changed = False
                for r in range(1, self.size):
                    for c in range(1, self.size):
                        if self.grid[r, c] == 1:
                            h_len = len(self.get_run(r, c, (0, 1)))
                            v_len = len(self.get_run(r, c, (1, 0)))
                            if h_len < 2 or v_len < 2:
                                self.grid[r, c] = 0
                                changed = True
            
            # 3. Calculate hints based on remaining filled cells
            clues = self.calculate_clues()
            
            # 4. Check for unique solvability
            if self.is_uniquely_solvable(clues):
                return self.values, clues
                
        return None, None

    def get_run(self, r, c, delta):
        dr, dc = delta
        run = []
        cr, cc = r, c
        while 0 <= cr - dr < self.size and 0 <= cc - dc < self.size and self.grid[cr - dr, cc - dc] == 1:
            cr -= dr
            cc -= dc
        while 0 <= cr < self.size and 0 <= cc < self.size and self.grid[cr, cc] == 1:
            run.append((cr, cc))
            cr += dr
            cc += dc
        return run

    def calculate_clues(self):
        clues = {}
        for r in range(self.size):
            for c in range(self.size):
                if self.grid[r, c] == 0:
                    h_clue = None
                    if c + 1 < self.size and self.grid[r, c+1] == 1:
                        h_run = self.get_run(r, c+1, (0, 1))
                        h_clue = sum(self.values[p] for p in h_run)
                    
                    v_clue = None
                    if r + 1 < self.size and self.grid[r+1, c] == 1:
                        v_run = self.get_run(r+1, c, (1, 0))
                        v_clue = sum(self.values[p] for p in v_run)
                    
                    if h_clue or v_clue:
                        clues[(r, c)] = {"h": h_clue, "v": v_clue}
        return clues

    def is_uniquely_solvable(self, clues):
        white_cells = [(r, c) for r in range(self.size) for c in range(self.size) if self.grid[r, c] == 1]
        
        cell_runs = {}
        for (r, c) in white_cells:
            cc = c
            while cc > 0 and self.grid[r, cc-1] == 1: cc -= 1
            h_start = cc
            cc = c
            while cc < self.size-1 and self.grid[r, cc+1] == 1: cc += 1
            h_end = cc
            
            rr = r
            while rr > 0 and self.grid[rr-1, c] == 1: rr -= 1
            v_start = rr
            rr = r
            while rr < self.size-1 and self.grid[rr+1, c] == 1: rr += 1
            v_end = rr
            
            h_c = clues.get((r, h_start-1), {}).get("h")
            v_c = clues.get((v_start-1, c), {}).get("v")
            
            cell_runs[(r, c)] = {
                "h_cells": [(r, i) for i in range(h_start, h_end+1)],
                "v_cells": [(i, c) for i in range(v_start, v_end+1)],
                "h_clue": h_c,
                "v_clue": v_c
            }
            
        solver_values = {}
        
        def solve(idx):
            if idx == len(white_cells):
                return 1
            
            r, c = white_cells[idx]
            info = cell_runs[(r, c)]
            
            used_h = set()
            sum_h = 0
            unfilled_h = 0
            for p in info["h_cells"]:
                if p in solver_values:
                    used_h.add(solver_values[p])
                    sum_h += solver_values[p]
                else:
                    unfilled_h += 1
                    
            used_v = set()
            sum_v = 0
            unfilled_v = 0
            for p in info["v_cells"]:
                if p in solver_values:
                    used_v.add(solver_values[p])
                    sum_v += solver_values[p]
                else:
                    unfilled_v += 1
                    
            h_target = info["h_clue"]
            v_target = info["v_clue"]
            
            count = 0
            for n in range(1, 10):
                if n in used_h or n in used_v: continue
                if h_target is not None:
                    if sum_h + n > h_target: continue
                    if unfilled_h == 1 and sum_h + n != h_target: continue
                if v_target is not None:
                    if sum_v + n > v_target: continue
                    if unfilled_v == 1 and sum_v + n != v_target: continue
                
                solver_values[(r, c)] = n
                count += solve(idx + 1)
                del solver_values[(r, c)]
                if count > 1: return count
            return count
            
        solutions = solve(0)
        return solutions == 1

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output", "kakuro")
    os.makedirs(output_dir, exist_ok=True)
    
    configs = [(5, "Easy", 15), (7, "Medium", 10), (9, "Hard", 10)]
    
    for size, diff, count in configs:
        for i in range(count):
            gen = KakuroGenerator(size)
            values, clues = None, None
            while values is None:
                values, clues = gen.generate()
            
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
