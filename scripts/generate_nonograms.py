import os
import json
import random

def solve_line(clues, length):
    # Returns all possible valid line configurations for given clues and length
    if not clues:
        return [[False] * length]
    
    first = clues[0]
    rest = clues[1:]
    min_rest = sum(rest) + len(rest)
    
    valid = []
    # Maximum start position for the first block
    max_start = length - min_rest - first
    for start in range(max_start + 1):
        prefix = [False] * start + [True] * first
        
        if not rest:
            suffix = [False] * (length - len(prefix))
            valid.append(prefix + suffix)
        else:
            prefix.append(False)
            sub_sols = solve_line(rest, length - len(prefix))
            for sub in sub_sols:
                valid.append(prefix + sub)
    return valid

def check_unique(grid):
    size = len(grid)
    
    # Generate clues from grid
    row_clues = []
    col_clues = []
    for r in range(size):
        r_clue = []
        count = 0
        for c in range(size):
            if grid[r][c]: count += 1
            elif count > 0:
                r_clue.append(count)
                count = 0
        if count > 0: r_clue.append(count)
        row_clues.append(r_clue)
        
    for c in range(size):
        c_clue = []
        count = 0
        for r in range(size):
            if grid[r][c]: count += 1
            elif count > 0:
                c_clue.append(count)
                count = 0
        if count > 0: c_clue.append(count)
        col_clues.append(c_clue)
        
    # Line solving algorithm
    # Iterate until no more changes. If completed, unique. Otherwise not strictly line-solvable.
    state = [[None for _ in range(size)] for _ in range(size)]
    row_poss = [solve_line(row_clues[r], size) for r in range(size)]
    col_poss = [solve_line(col_clues[c], size) for c in range(size)]
    
    changed = True
    while changed:
        changed = False
        
        # update state from rows
        for r in range(size):
            if not row_poss[r]: return False # Contradiction
            for c in range(size):
                if state[r][c] is None:
                    first_val = row_poss[r][0][c]
                    if all(p[c] == first_val for p in row_poss[r]):
                        state[r][c] = first_val
                        changed = True
                        
        # filter cols by state
        for c in range(size):
            new_poss = []
            for p in col_poss[c]:
                valid = True
                for r in range(size):
                    if state[r][c] is not None and state[r][c] != p[r]:
                        valid = False
                        break
                if valid: new_poss.append(p)
            if len(new_poss) < len(col_poss[c]):
                col_poss[c] = new_poss
                changed = True

        # update state from cols
        for c in range(size):
            if not col_poss[c]: return False
            for r in range(size):
                if state[r][c] is None:
                    first_val = col_poss[c][0][r]
                    if all(p[r] == first_val for p in col_poss[c]):
                        state[r][c] = first_val
                        changed = True
                        
        # filter rows by state
        for r in range(size):
            new_poss = []
            for p in row_poss[r]:
                valid = True
                for c in range(size):
                    if state[r][c] is not None and state[r][c] != p[c]:
                        valid = False
                        break
                if valid: new_poss.append(p)
            if len(new_poss) < len(row_poss[r]):
                row_poss[r] = new_poss
                changed = True

    for r in range(size):
        for c in range(size):
            if state[r][c] is None:
                return False
    return True

def generate_shape(size):
    # random shapes
    grid = [[False]*size for _ in range(size)]
    
    cx, cy = size//2, size//2
    # Place some blocks near center to ensure connectivity
    for _ in range(size * size // 2):
        r, c = random.randint(0, size-1), random.randint(0, size-1)
        grid[r][c] = True
            
    # symmetry optionally
    if random.random() < 0.5:
        # vertical sym
        for r in range(size):
            for c in range(size//2):
                grid[r][size-1-c] = grid[r][c]
                
    # ensure not completely empty/full
    density = sum(sum(r) for r in grid) / (size*size)
    if density < 0.2 or density > 0.8:
        return None
        
    return grid

def generate_puzzles(size, diff, count):
    puzzles = []
    attempts = 0
    while len(puzzles) < count:
        attempts += 1
        g = generate_shape(size)
        if g and check_unique(g):
            puzzles.append(g)
            print(f"[{diff}] Generated {len(puzzles)}/{count}")
    return puzzles

def main():
    targets = [
        (5, 'Easy', 15),
        (10, 'Medium', 15),
        (15, 'Hard', 10)
    ]
    
    out_dir = os.path.join(os.path.dirname(__file__), 'output', 'nonogram')
    os.makedirs(out_dir, exist_ok=True)
    
    results = {}
    for s, d, t in targets:
        rs = generate_puzzles(s, d, t)
        results[d] = []
        for i, grid in enumerate(rs):
            # calculate clues
            row_clues = []
            col_clues = []
            for r in range(s):
                cc = []
                count = 0
                for c in range(s):
                    if grid[r][c]: count += 1
                    elif count > 0:
                        cc.append(count)
                        count = 0
                if count > 0: cc.append(count)
                row_clues.append(cc)
                
            for c in range(s):
                cc = []
                count = 0
                for r in range(s):
                    if grid[r][c]: count += 1
                    elif count > 0:
                        cc.append(count)
                        count = 0
                if count > 0: cc.append(count)
                col_clues.append(cc)
                
            grid_str = "".join("1" if c else "0" for row in grid for c in row)
            p = {
                "id": f"nonogram_{d.lower()}_{i+1}",
                "difficulty": d,
                "size": s,
                "row_clues": row_clues,
                "col_clues": col_clues,
                "grid_str": grid_str
            }
            results[d].append(p)
            out_path = os.path.join(out_dir, f"{p['id']}.json")
            with open(out_path, 'w') as f:
                json.dump(p, f, indent=2)

if __name__ == '__main__':
    main()
