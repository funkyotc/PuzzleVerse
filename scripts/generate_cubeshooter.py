import os
import json
import glob
import random

PALETTE = [0, 1, 2, 3]

DIFFICULTY_CONFIG = {
    "Easy":   {"cols": 6,  "rows": 6,  "shapes": 10, "n_colors": 2, "min_per_color": 10},
    "Medium": {"cols": 8,  "rows": 8,  "shapes": 16, "n_colors": 3, "min_per_color": 10},
    "Hard":   {"cols": 10, "rows": 10, "shapes": 22, "n_colors": 4, "min_per_color": 10},
    "Expert": {"cols": 15, "rows": 25, "shapes": 35, "n_colors": 4, "min_per_color": 10},
    "Master": {"cols": 20, "rows": 40, "shapes": 60, "n_colors": 4, "min_per_color": 10},
}

SHAPES = [
    [(0, 0), (0, 1), (0, 2)],
    [(0, 0), (1, 0), (2, 0)],
    [(0, 0), (0, 1), (1, 0), (1, 1)],
    [(0, 0), (1, 0), (2, 0), (2, 1)],
    [(0, 0), (0, 1), (0, 2), (1, 0)],
    [(0, 0), (0, 1), (1, 1), (2, 1)],
    [(0, 2), (1, 0), (1, 1), (1, 2)],
    [(0, 0), (0, 1), (0, 2), (1, 1)],
    [(0, 0), (1, 0), (1, 1), (2, 0)],
    [(1, 0), (1, 1), (1, 2), (0, 1)],
    [(0, 1), (1, 0), (1, 1), (2, 1)],
    [(0, 1), (0, 2), (1, 0), (1, 1)],
    [(0, 0), (0, 1), (1, 1), (1, 2)],
    [(0, 1), (1, 0), (1, 1), (1, 2), (2, 1)],
    [(0, 0), (0, 1), (1, 1), (1, 0), (2, 0)],
    [(0, 0), (1, 0), (1, 1), (2, 1)],
    [(0, 0), (1, 0), (1, 1), (1, 2), (2, 2)],
    [(0, 0), (0, 1), (1, 0), (1, 1), (1, 2)],
]


def rotated(shape, rot):
    out = []
    for x, y in shape:
        if rot == 0:
            nx, ny = x, y
        elif rot == 1:
            nx, ny = y, -x
        elif rot == 2:
            nx, ny = -x, -y
        else:
            nx, ny = -y, x
        out.append((nx, ny))
    return out


def normalize(shape):
    if not shape:
        return []
    minx = min(p[0] for p in shape)
    miny = min(p[1] for p in shape)
    return [(x - minx, y - miny) for (x, y) in shape]


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
    """
    Splits total_ammo (which is a multiple of 5, >= 10) into varied parts >= 10
    that are also multiples of 5.
    """
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
    target_shapes = cfg["shapes"]
    n_colors = cfg["n_colors"]
    min_per_color = cfg["min_per_color"]

    grid = [[None] * cols for _ in range(rows)]
    placed = 0
    attempts = 0
    max_attempts = target_shapes * 40
    while placed < target_shapes and attempts < max_attempts:
        attempts += 1
        shape = rng.choice(SHAPES)
        rot = rng.randint(0, 3)
        rot_shape = normalize(rotated(shape, rot))
        if not rot_shape:
            continue
        w = max(p[0] for p in rot_shape) + 1
        h = max(p[1] for p in rot_shape) + 1
        if w > cols or h > rows:
            continue
        ox = rng.randint(0, cols - w)
        oy = rng.randint(0, rows - h)
        cells = [(ox + dx, oy + dy) for (dx, dy) in rot_shape]
        if any(grid[y][x] is not None for (x, y) in cells):
            continue
        for (x, y) in cells:
            grid[y][x] = -1
        placed += 1

    cube_cells = []
    for y in range(rows):
        for x in range(cols):
            if grid[y][x] == -1:
                grid[y][x] = None
                cube_cells.append((y, x))

    min_required = n_colors * 10
    if len(cube_cells) < min_required:
        return None

    while len(cube_cells) % 5 != 0:
        cube_cells.pop()

    rng.shuffle(cube_cells)

    # Distribute in multiples of 5, minimum 10
    color_counts = [10] * n_colors
    remaining = len(cube_cells) - min_required
    while remaining > 0:
        c_idx = rng.randint(0, n_colors - 1)
        color_counts[c_idx] += 5
        remaining -= 5

    color_for_cell = {}
    idx_c = 0
    for c in range(n_colors):
        for _ in range(color_counts[c]):
            color_for_cell[cube_cells[idx_c]] = c
            idx_c += 1

    grid = [[None] * cols for _ in range(rows)]
    for (y, x), c in color_for_cell.items():
        grid[y][x] = c

    # Create multiple tanks per color in multiples of 5, minimum 10
    tray_tanks = []
    for c in range(n_colors):
        parts = split_ammo_multiples_of_5(color_counts[c], rng)
        for ammo in parts:
            tray_tanks.append((c, ammo))

    # Shuffle and distribute across source columns, verifying solvability
    solvability_attempts = 45
    for _ in range(solvability_attempts):
        rng.shuffle(tray_tanks)
        
        # Format for JSON: list of {"color": c, "ammo": a}
        json_tray = [{"color": t[0], "ammo": t[1]} for t in tray_tanks]
        
        # Reconstruct state for verification
        source_cols = [[] for _ in range(3)]
        for i, t in enumerate(tray_tanks):
            source_cols[i % 3].append(t)
            
        source_cols_tuple = tuple(tuple(col) for col in source_cols)
        
        # Verify using our backtrack solver
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
        "Easy": 100,
        "Medium": 100,
        "Hard": 100,
        "Expert": 100,
        "Master": 100,
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
