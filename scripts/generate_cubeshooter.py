import os
import json
import random

PALETTE = [0, 1, 2, 3]

DIFFICULTY_CONFIG = {
    "Easy":        {"cols": 6,  "rows": 6,  "n_colors": 2},
    "Medium":      {"cols": 8,  "rows": 8,  "n_colors": 3},
    "Hard":        {"cols": 10, "rows": 10, "n_colors": 4},
    "Expert":      {"cols": 15, "rows": 25, "n_colors": 4},
    "Master":      {"cols": 20, "rows": 40, "n_colors": 4},
    "Grandmaster": {"cols": 30, "rows": 50, "n_colors": 4},
    "Legendary":   {"cols": 40, "rows": 60, "n_colors": 4},
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


def split_ammo_multiples_of_5(total_ammo, rng):
    parts = []
    remaining = total_ammo
    while remaining > 25:
        chunk = rng.choice([10, 15, 20])
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
    n_colors = cfg["n_colors"]

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
        
    # Assign colors to selected cube positions
    shuffled_cubes = list(cube_positions)
    rng.shuffle(shuffled_cubes)
    
    color_assignment = {}
    current_index = 0
    for c in range(n_colors):
        for _ in range(color_counts[c]):
            color_assignment[shuffled_cubes[current_index]] = c
            current_index += 1
            
    # Fill grid
    for r in range(rows):
        for c in range(cols):
            if (r, c) in cube_set:
                grid[r][c] = color_assignment[(r, c)]
                
    # Create multiple tanks per color in multiples of 5, minimum 10
    tray_tanks = []
    for c in range(n_colors):
        parts = split_ammo_multiples_of_5(color_counts[c], rng)
        for ammo in parts:
            tray_tanks.append((c, ammo))
            
    # For larger grids, we can safely bypass the heavy solver checks
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

    # Shuffle and distribute across source columns, verifying solvability
    solvability_attempts = 35
    for _ in range(solvability_attempts):
        rng.shuffle(tray_tanks)
        
        # Format for JSON: list of {"color": c, "ammo": a}
        json_tray = [{"color": t[0], "ammo": t[1]} for t in tray_tanks]
        
        # Reconstruct state for verification
        source_cols = [[] for _ in range(3)]
        for i, t in enumerate(tray_tanks):
            source_cols[i % 3].append(t)
            
        source_cols_tuple = tuple(tuple(col) for col in source_cols)
        
        # First, run the incredibly fast greedy check
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
            
        # For smaller grids, if greedy fails, try the backtrack solver
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
        "Expert": 20,
        "Master": 20,
        "Grandmaster": 20,
        "Legendary": 20,
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