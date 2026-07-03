import os
import json
import random

import math

PALETTE = list(range(12))

DIFFICULTY_CONFIG = {
    "Easy":   {"cols": 20, "rows": 40, "n_colors_min": 5,  "n_colors_max": 8,  "min_ammo": 5, "pool": [5, 10, 15, 20], "threshold": 20},
    "Medium": {"cols": 30, "rows": 50, "n_colors_min": 8,  "n_colors_max": 10, "min_ammo": 5, "pool": [5, 10, 15], "threshold": 15},
    "Hard":   {"cols": 40, "rows": 60, "n_colors_min": 10, "n_colors_max": 12, "min_ammo": 5, "pool": [5, 10, 15], "threshold": 15},
}


# --- SOLVER ENGINE FOR SOLVABILITY VERIFICATION ---

def get_facing_cell(cell_idx, cols, rows):
    if cell_idx < cols:
        return (0, cell_idx, "DOWN")
    elif cell_idx < cols + rows:
        return (cell_idx - cols, cols - 1, "LEFT")
    elif cell_idx < 2 * cols + rows:
        c = (cols - 1) - (cell_idx - (cols + rows))
        return (rows - 1, c, "UP")
    elif cell_idx < 2 * cols + 2 * rows:
        r = (rows - 1) - (cell_idx - (2 * cols + rows))
        return (r, 0, "RIGHT")
    return None


def find_first_cube(grid, start_row, start_col, direction, cols, rows):
    if direction == "DOWN":
        for r in range(rows):
            if grid[r][start_col] is not None:
                return (r, start_col)
    elif direction == "LEFT":
        for c in range(cols - 1, -1, -1):
            if grid[start_row][c] is not None:
                return (start_row, c)
    elif direction == "UP":
        for r in range(rows - 1, -1, -1):
            if grid[r][start_col] is not None:
                return (r, start_col)
    elif direction == "RIGHT":
        for c in range(cols):
            if grid[start_row][c] is not None:
                return (start_row, c)
    return (None, None)


def get_bottom_middle_track_index(cols, rows):
    middle_col = (cols + 1) // 2
    return (cols + rows) + (cols - middle_col)


def run_loop(grid, cols, rows, tank_color, tank_ammo):
    grid_copy = [row[:] for row in grid]
    ammo = tank_ammo
    start_idx = get_bottom_middle_track_index(cols, rows)
    loop_len = 2 * (cols + rows)
    
    for step in range(loop_len + 1):
        cell_idx = (start_idx + step) % loop_len
        facing = get_facing_cell(cell_idx, cols, rows)
        if facing is not None:
            r, c, direction = facing
            tr, tc = find_first_cube(grid_copy, r, c, direction, cols, rows)
            if tr is not None and tc is not None:
                if grid_copy[tr][tc] == tank_color and ammo > 0:
                    grid_copy[tr][tc] = None
                    ammo -= 1
                    
    return grid_copy, ammo


def solve_greedy(cols, rows, grid, source_cols, storage):
    """
    Extremely fast greedy solver that always makes the best local move.
    Returns True if solved.
    """
    curr_grid = [r[:] for r in grid]
    curr_sources = [list(c) for c in source_cols]
    curr_storage = list(storage)
    
    while True:
        if all(cell is None for row in curr_grid for cell in row):
            return True
            
        best_move = None
        best_cleared_count = -1
        
        # Try source columns
        for i in range(3):
            if len(curr_sources[i]) > 0:
                tank_color, tank_ammo = curr_sources[i][-1]
                new_grid, remaining_ammo = run_loop(curr_grid, cols, rows, tank_color, tank_ammo)
                cleared = tank_ammo - remaining_ammo
                if cleared > 0 or (len(curr_storage) < 5):
                    # Prioritize moves that clear more blocks
                    score = cleared * 1000 - len(curr_storage)
                    if score > best_cleared_count:
                        best_cleared_count = score
                        best_move = ("source", i, new_grid, remaining_ammo, tank_color)
                        
        # Try storage
        for i in range(len(curr_storage)):
            tank_color, tank_ammo = curr_storage[i]
            new_grid, remaining_ammo = run_loop(curr_grid, cols, rows, tank_color, tank_ammo)
            cleared = tank_ammo - remaining_ammo
            if cleared > 0:
                score = cleared * 1000 + 500  # prioritize from storage to free up space
                if score > best_cleared_count:
                    best_cleared_count = score
                    best_move = ("storage", i, new_grid, remaining_ammo, tank_color)
                    
        if best_move is None:
            return False
            
        move_type, idx, next_grid, rem_ammo, color = best_move
        curr_grid = next_grid
        if move_type == "source":
            curr_sources[idx].pop()
            if rem_ammo > 0:
                curr_storage.append((color, rem_ammo))
        else:
            curr_storage.pop(idx)
            if rem_ammo > 0:
                curr_storage.append((color, rem_ammo))
                
        if len(curr_storage) > 5:
            return False


def can_solve(cols, rows, grid, source_cols, storage, memo):
    if all(cell is None for row in grid for cell in row):
        return True
        
    state_key = (
        tuple(tuple(r) for r in grid),
        tuple(tuple(c) for c in source_cols),
        tuple(storage)
    )
    if state_key in memo:
        return memo[state_key]

    valid_moves = []

    # 1. Dispatch from source columns
    for i in range(3):
        if len(source_cols[i]) > 0:
            tank = source_cols[i][-1]
            tank_color, tank_ammo = tank
            new_grid, remaining_ammo = run_loop(grid, cols, rows, tank_color, tank_ammo)
            
            new_source_cols = list(list(c) for c in source_cols)
            new_source_cols[i].pop()
            new_source_cols = tuple(tuple(c) for c in new_source_cols)
            
            new_storage = list(storage)
            if remaining_ammo > 0:
                new_storage.append((tank_color, remaining_ammo))
                
            if len(new_storage) <= 5:
                cleared = remaining_ammo < tank_ammo
                valid_moves.append((cleared, "source", i, new_grid, new_source_cols, tuple(new_storage)))

    # 2. Dispatch from storage tray
    for i in range(len(storage)):
        tank_color, tank_ammo = storage[i]
        new_grid, remaining_ammo = run_loop(grid, cols, rows, tank_color, tank_ammo)
        
        new_storage = list(storage)
        new_storage.pop(i)
        if remaining_ammo > 0:
            new_storage.append((tank_color, remaining_ammo))
            
        if len(new_storage) <= 5:
            cleared = remaining_ammo < tank_ammo
            valid_moves.append((cleared, "storage", i, new_grid, source_cols, tuple(new_storage)))

    # Sort moves to prioritize those that clear blocks!
    valid_moves.sort(key=lambda x: x[0], reverse=True)

    for cleared, move_type, idx, new_grid, new_source, new_store in valid_moves:
        if can_solve(cols, rows, new_grid, new_source, new_store, memo):
            memo[state_key] = True
            return True

    memo[state_key] = False
    return False


def split_ammo_multiples_of_5(total_ammo, rng, cfg):
    parts = []
    remaining = total_ammo
    pool = cfg["pool"]
    threshold = cfg["threshold"]
    min_ammo = cfg["min_ammo"]
    while remaining > threshold:
        valid = [s for s in pool if s <= remaining - min_ammo]
        if not valid:
            valid = [min_ammo]
        chunk = rng.choice(valid)
        parts.append(chunk)
        remaining -= chunk
    if remaining > 0:
        parts.append(remaining)
    return parts


# --- LEVEL GENERATION ---

def gen_level(diff, idx, seed_val):
    rng = random.Random(seed_val)
    cfg = DIFFICULTY_CONFIG[diff]
    cols = cfg["cols"]
    rows = cfg["rows"]
    n_colors = rng.randint(cfg["n_colors_min"], cfg["n_colors_max"])

    total_cells = cols * rows
    # Make sure total_cubes is a multiple of 5
    total_cubes = (total_cells // 5) * 5
    
    # Generate all positions in the grid
    all_positions = [(r, c) for r in range(rows) for c in range(cols)]
    
    # Select which cells will have cubes (minimizing empty spaces by keeping it as full as possible)
    cube_positions = rng.sample(all_positions, total_cubes)
    cube_set = set(cube_positions)
    
    # Initialize empty grid
    grid = [[None] * cols for _ in range(rows)]
    
    # Distribute total_cubes into colors, ensuring each color has a multiple of 5 cubes (min 10)
    color_counts = [10] * n_colors
    remaining_cubes = total_cubes - (n_colors * 10)
    
    while remaining_cubes > 0:
        c_idx = rng.randint(0, n_colors - 1)
        color_counts[c_idx] += 5
        remaining_cubes -= 5
        
    # Difficulty-weighted pattern selection
    easy_patterns = ["horizontal_layers", "vertical_columns", "checkerboard", "corner_radiance"]
    medium_patterns = ["concentric_rectangles", "forward_diagonals", "backward_diagonals", "diamond_star", "circular_waves", "horizontal_sine"]
    hard_patterns = ["spiral", "quadrant_rotation", "interference", "random_regions", "snake_columns", "stripes_alternating", "radial_sectors", "fractal_blocks"]

    diff_weights = {
        "Easy":   [0.70, 0.30, 0.00],
        "Medium": [0.20, 0.50, 0.30],
        "Hard":   [0.00, 0.20, 0.80],
    }
    w_easy, w_med, w_hard = diff_weights[diff]
    pattern_type = rng.choices(
        [rng.choice(easy_patterns), rng.choice(medium_patterns), rng.choice(hard_patterns)],
        weights=[w_easy, w_med, w_hard]
    )[0]
    
    r_mid = rows / 2.0
    c_mid = cols / 2.0
    
    def get_val(r, c):
        if pattern_type == "horizontal_layers":
            return r
        elif pattern_type == "vertical_columns":
            return c
        elif pattern_type == "concentric_rectangles":
            return max(abs(r - r_mid), abs(c - c_mid))
        elif pattern_type == "forward_diagonals":
            return r + c
        elif pattern_type == "backward_diagonals":
            return r - c
        elif pattern_type == "diamond_star":
            return abs(r - r_mid) + abs(c - c_mid)
        elif pattern_type == "circular_waves":
            return (r - r_mid)**2 + (c - c_mid)**2
        elif pattern_type == "checkerboard":
            return ((int(r // 4) + int(c // 4)) % 2) + rng.uniform(0, 0.1)
        elif pattern_type == "horizontal_sine":
            return r + math.sin(c * 0.5) * 3.0
        elif pattern_type == "corner_radiance":
            return r**2 + c**2
        elif pattern_type == "spiral":
            dr = r - r_mid
            dc = c - c_mid
            return math.atan2(dr, dc) + 0.5 * math.hypot(dr, dc)
        elif pattern_type == "quadrant_rotation":
            q = (1 if r < r_mid else 3) + (1 if c < c_mid else 0)
            return (r + c + q * 100) % 200
        elif pattern_type == "interference":
            return math.sin(r * 0.3 + c * 0.2) + math.sin(r * 0.2 - c * 0.3)
        elif pattern_type == "random_regions":
            seed = (int(r // 8) * 100003 + int(c // 8) * 50021) & 0xFFFF
            return ((seed * 1103515245 + 12345) & 0x7FFFFFFF) % 1000
        elif pattern_type == "snake_columns":
            return (c if r % 2 == 0 else cols - 1 - c) + r * cols
        elif pattern_type == "stripes_alternating":
            return (r + c * 2) // 5
        elif pattern_type == "radial_sectors":
            dr = r - r_mid
            dc = c - c_mid
            return (int(math.atan2(dr, dc) * 4 / math.pi + 4) % 8) * 100 + int(math.hypot(dr, dc))
        elif pattern_type == "fractal_blocks":
            size = max(cols, rows)
            val = 0
            sr, sc = r, c
            step = 1
            while size > 4:
                quadrant = (0 if sr < size / 2 else 2) + (0 if sc < size / 2 else 1)
                val += quadrant * step
                sr = int(sr % (size / 2))
                sc = int(sc % (size / 2))
                size = int(size / 2)
                step *= 4
            return val
        return 0

    # Add difficulty-scaled color jitter at zone boundaries
    jitter_amount = {"Easy": 0.0, "Medium": 0.15, "Hard": 0.35}[diff]
    values = [get_val(r, c) for (r, c) in cube_positions]
    vmin, vmax = min(values), max(values)
    vrange = max(vmax - vmin, 1)

    def sort_key(pos):
        return (get_val(pos[0], pos[1]) - vmin) / vrange + rng.uniform(0, jitter_amount)

    sorted_cubes = sorted(cube_positions, key=sort_key)
    
    color_assignment = {}
    current_index = 0
    for c in range(n_colors):
        for _ in range(color_counts[c]):
            color_assignment[sorted_cubes[current_index]] = c
            current_index += 1
            
    # Fill grid
    for r in range(rows):
        for c in range(cols):
            if (r, c) in cube_set:
                grid[r][c] = color_assignment[(r, c)]
                
    # Create multiple tanks per color in multiples of 5, minimum 10
    tray_tanks = []
    for c in range(n_colors):
        parts = split_ammo_multiples_of_5(color_counts[c], rng, cfg)
        for ammo in parts:
            tray_tanks.append((c, ammo))

    # For large grids, shuffle once and accept (solver is impractical as a gatekeeper)
    # For small grids, verify solvability with greedy+backtrack
    if cols > 10 or rows > 10:
        rng.shuffle(tray_tanks)
        json_tray = [{"color": t[0], "ammo": t[1]} for t in tray_tanks]
        return {
            "id": f"cubeshooter_{diff.lower()}_{idx:03d}",
            "difficulty": diff,
            "cols": cols,
            "rows": rows,
            "grid": grid,
            "tray": json_tray,
            "_color_counts": color_counts,
        }

    # Solvability check for small grids
    solvability_attempts = 35
    for _ in range(solvability_attempts):
        rng.shuffle(tray_tanks)
        
        json_tray = [{"color": t[0], "ammo": t[1]} for t in tray_tanks]
        
        source_cols = [[] for _ in range(3)]
        for i, t in enumerate(tray_tanks):
            source_cols[i % 3].append(t)
            
        source_cols_tuple = tuple(tuple(col) for col in source_cols)
        
        if solve_greedy(cols, rows, grid, source_cols_tuple, ()):
            return {
                "id": f"cubeshooter_{diff.lower()}_{idx:03d}",
                "difficulty": diff,
                "cols": cols,
                "rows": rows,
                "grid": grid,
                "tray": json_tray,
                "_color_counts": color_counts,
            }
            
        if cols <= 10 and rows <= 10:
            memo = {}
            if can_solve(cols, rows, grid, source_cols_tuple, (), memo):
                return {
                    "id": f"cubeshooter_{diff.lower()}_{idx:03d}",
                    "difficulty": diff,
                    "cols": cols,
                    "rows": rows,
                    "grid": grid,
                    "tray": json_tray,
                    "_color_counts": color_counts,
                }

    return None


def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    out_dir = os.path.join(script_dir, "output", "cubeshooter")
    os.makedirs(out_dir, exist_ok=True)

    counts_per_difficulty = {
        "Easy": 20,
        "Medium": 20,
        "Hard": 20,
    }

    for diff, target_count in counts_per_difficulty.items():
        i = 0
        attempts = 0
        while i < target_count and attempts < target_count * 150:
            attempts += 1
            seed_val = (hash(("cubeshooter", diff, attempts)) & 0xFFFFFFFF)
            lvl = gen_level(diff, idx=i + 1, seed_val=seed_val)
            if lvl is None:
                continue
            fname = f"cubeshooter_{diff.lower()}_{i + 1:03d}.json"
            with open(os.path.join(out_dir, fname), "w") as f:
                json.dump(lvl, f, indent=2)
            i += 1
        print(f"Generated {i} {diff} levels")


if __name__ == "__main__":
    main()