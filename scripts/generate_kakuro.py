import os
import json
import random
import multiprocessing
import itertools
from collections import defaultdict

def generate_layout(size):
    # Generating a valid kakuro layout: no 2x2 white blocks, no single white cells.
    # We use a simple randomized scheme.
    grid = [['W'] * size for _ in range(size)]
    for r in range(size):
        grid[r][0] = 'B'
    for c in range(size):
        grid[0][c] = 'B'
    
    # Randomly place black cells to break 2x2 and reduce density
    # Density around 30% black
    for r in range(1, size):
        for c in range(1, size):
            if random.random() < 0.3:
                grid[r][c] = 'B'

    # Fix 2x2 white clumps
    for r in range(1, size - 1):
        for c in range(1, size - 1):
            if grid[r][c] == 'W' and grid[r+1][c] == 'W' and grid[r][c+1] == 'W' and grid[r+1][c+1] == 'W':
                grid[r + random.randint(0,1)][c + random.randint(0,1)] = 'B'
    
    # Fix single isolated
    for r in range(1, size):
        for c in range(1, size):
            if grid[r][c] == 'W':
                is_h_isolated = (c == 1 or grid[r][c-1] == 'B') and (c == size-1 or grid[r][c+1] == 'B')
                is_v_isolated = (r == 1 or grid[r-1][c] == 'B') and (r == size-1 or grid[r+1][c] == 'B')
                if is_h_isolated and is_v_isolated:
                    grid[r][c] = 'B'

    # Create clues
    clues = {}
    for r in range(size):
        for c in range(size):
            if grid[r][c] == 'B':
                has_h = c + 1 < size and grid[r][c+1] == 'W'
                has_v = r + 1 < size and grid[r+1][c] == 'W'
                if has_h or has_v:
                    grid[r][c] = 'C'
                    clues[(r, c)] = {'h': None, 'v': None}
    
    # We must ensure all white cells belong to at least a horizontal run >1 or vertical run >1
    # For simplicity if a run is 1, make it black.
    changed = True
    while changed:
        changed = False
        for r in range(size):
            for c in range(size):
                if grid[r][c] == 'W':
                    # check h-run
                    c_start = c
                    while c_start > 0 and grid[r][c_start-1] == 'W': c_start -= 1
                    c_end = c
                    while c_end < size - 1 and grid[r][c_end+1] == 'W': c_end += 1
                    
                    # check v-run
                    r_start = r
                    while r_start > 0 and grid[r_start-1] == 'W': r_start -= 1
                    r_end = r
                    while r_end < size - 1 and grid[r_end+1] == 'W': r_end += 1
                    
                    if (c_end - c_start == 0) and (r_end - r_start == 0):
                        grid[r][c] = 'B'
                        changed = True

    # re-mark clues
    clues.clear()
    for r in range(size):
        for c in range(size):
            if grid[r][c] in ('B', 'C'):
                grid[r][c] = 'B'
                has_h = c + 1 < size and grid[r][c+1] == 'W'
                has_v = r + 1 < size and grid[r+1][c] == 'W'
                if has_h or has_v:
                    grid[r][c] = 'C'
                    clues[(r, c)] = {'h': None, 'v': None}

    return grid, clues

def fill_grid(grid, clues, size):
    # Backtracking to fill digits
    white_cells = [(r, c) for r in range(size) for c in range(size) if grid[r][c] == 'W']
    ans = {}
    
    # Prepare runs
    # map cell -> list of cells in its h-run and v-run
    h_runs = {}
    v_runs = {}
    for r, c in white_cells:
        # h-run
        ci = c
        while ci > 0 and grid[r][ci-1] == 'W': ci -= 1
        h_run = []
        while ci < size and grid[r][ci] == 'W':
            h_run.append((r, ci))
            ci += 1
        h_runs[(r, c)] = h_run
        
        # v-run
        ri = r
        while ri > 0 and grid[ri-1][c] == 'W': ri -= 1
        v_run = []
        while ri < size and grid[ri][c] == 'W':
            v_run.append((ri, c))
            ri += 1
        v_runs[(r, c)] = v_run

    def solve(idx):
        if idx == len(white_cells):
            return True
        r, c = white_cells[idx]
        hr = h_runs[(r, c)]
        vr = v_runs[(r, c)]
        
        used = set()
        for hr_c in hr:
            if hr_c in ans: used.add(ans[hr_c])
        for vr_c in vr:
            if vr_c in ans: used.add(ans[vr_c])
            
        avail = list(set(range(1, 10)) - used)
        random.shuffle(avail)
        for val in avail:
            ans[(r, c)] = val
            if solve(idx + 1):
                return True
            del ans[(r, c)]
        return False
        
    if solve(0):
        # Calc clues
        for r, c in clues.keys():
            if c + 1 < size and grid[r][c+1] == 'W':
                clues[(r, c)]['h'] = sum(ans[rc] for rc in h_runs[(r, c+1)])
            if r + 1 < size and grid[r+1][c] == 'W':
                clues[(r, c)]['v'] = sum(ans[rc] for rc in v_runs[(r+1, c)])
        return ans
    return None

def count_solutions(grid, clues, size, limit=2):
    white_cells = [(r, c) for r in range(size) for c in range(size) if grid[r][c] == 'W']
    ans = {}
    
    h_runs = {}
    v_runs = {}
    for r, c in white_cells:
        ci = c
        while ci > 0 and grid[r][ci-1] == 'W': ci -= 1
        h_run = []
        clue_pos = None
        if ci > 0 and grid[r][ci-1] == 'C': clue_pos = (r, ci-1)
        while ci < size and grid[r][ci] == 'W':
            h_run.append((r, ci))
            ci += 1
        h_runs[(r, c)] = (h_run, clue_pos)
        
        ri = r
        while ri > 0 and grid[ri-1][c] == 'W': ri -= 1
        v_run = []
        clue_pos_v = None
        if ri > 0 and grid[ri-1][c] == 'C': clue_pos_v = (ri-1, c)
        while ri < size and grid[ri][c] == 'W':
            v_run.append((ri, c))
            ri += 1
        v_runs[(r, c)] = (v_run, clue_pos_v)

    sols = 0
    def solve(idx):
        nonlocal sols
        if sols >= limit:
            return
        if idx == len(white_cells):
            sols += 1
            return
        
        r, c = white_cells[idx]
        hr, h_clue = h_runs[(r, c)]
        vr, v_clue = v_runs[(r, c)]
        
        used = set()
        h_sum = 0
        h_unfilled = 0
        for hr_c in hr:
            if hr_c in ans: 
                used.add(ans[hr_c])
                h_sum += ans[hr_c]
            else:
                h_unfilled += 1
                
        v_sum = 0
        v_unfilled = 0
        for vr_c in vr:
            if vr_c in ans: 
                used.add(ans[vr_c])
                v_sum += ans[vr_c]
            else:
                v_unfilled += 1
                
        h_target = clues[h_clue]['h'] if h_clue and clues[h_clue]['h'] else 999
        v_target = clues[v_clue]['v'] if v_clue and clues[v_clue]['v'] else 999
        
        # Heuristic pruning could go here
        
        for val in range(1, 10):
            if val in used: continue
            
            # Prune
            if h_unfilled == 1 and h_sum + val != h_target: continue
            if h_unfilled > 1 and h_sum + val >= h_target: continue
            
            if v_unfilled == 1 and v_sum + val != v_target: continue
            if v_unfilled > 1 and v_sum + val >= v_target: continue
            
            ans[(r, c)] = val
            solve(idx + 1)
            del ans[(r, c)]
            
    solve(0)
    return sols

def try_generate(params):
    size, difficulty = params
    # Attempt to generate a unique layout
    for _ in range(500):
        grid, clues = generate_layout(size)
        ans = fill_grid(grid, clues, size)
        if ans:
            cnt = count_solutions(grid, clues, size, limit=2)
            if cnt == 1:
                return grid, clues, size, difficulty, ans
    return None

def main():
    targets = [
        (4, 'Easy', 15),
        (6, 'Medium', 10),
        (8, 'Hard', 8)
    ]
    
    tasks = []
    for s, d, t in targets:
        for _ in range(t * 3): # Extra tasks in case of failure
            tasks.append((s, d))
            
    results = {
        'Easy': [],
        'Medium': [],
        'Hard': []
    }
    
    print(f"Generating Kakuro puzzles on {multiprocessing.cpu_count()} cores...")
    
    with multiprocessing.Pool(processes=12) as pool:
        for res in pool.imap_unordered(try_generate, tasks):
            if res:
                grid, clues, size, diff, ans = res
                if len(results[diff]) < next(t for s, d, t in targets if d == diff):
                    results[diff].append(res)
                    print(f"Generated {diff} ({len(results[diff])})")

    out_dir = os.path.join(os.path.dirname(__file__), 'output', 'kakuro')
    os.makedirs(out_dir, exist_ok=True)
    
    for diff, items in results.items():
        for i, (grid, clues, size, _, ans) in enumerate(items):
            out_path = os.path.join(out_dir, f"{diff}_{i+1}.json")
            
            # Serialize
            export_grid = []
            for r in range(size):
                row_data = []
                for c in range(size):
                    t = grid[r][c]
                    if t == 'B':
                        row_data.append({"type": "BLACK", "row": r, "col": c})
                    elif t == 'C':
                        row_data.append({"type": "CLUE", "clue": clues[(r, c)], "row": r, "col": c})
                    else:
                        row_data.append({"type": "PLAYER_INPUT", "val": ans[(r, c)], "row": r, "col": c})
                export_grid.append(row_data)
                
            with open(out_path, 'w') as f:
                json.dump({"difficulty": diff, "size": size, "grid": export_grid}, f, indent=2)
                
    print("Generation complete.")

if __name__ == '__main__':
    main()
