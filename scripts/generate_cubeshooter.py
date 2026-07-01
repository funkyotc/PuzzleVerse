import os
import json
import glob
import random

PALETTE = [0, 1, 2, 3]

DIFFICULTY_CONFIG = {
    "Easy":   {"cols": 6,  "rows": 6,  "shapes": 6,  "n_colors": 4, "min_per_color": 2},
    "Medium": {"cols": 8,  "rows": 8,  "shapes": 10, "n_colors": 4, "min_per_color": 3},
    "Hard":   {"cols": 10, "rows": 10, "shapes": 16, "n_colors": 4, "min_per_color": 4},
    "Expert": {"cols": 15, "rows": 25, "shapes": 35, "n_colors": 4, "min_per_color": 6},
    "Master": {"cols": 20, "rows": 40, "shapes": 60, "n_colors": 4, "min_per_color": 8},
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


# --- SOLVER ENGINE & REVERSE PEELING FOR SOLVABILITY ---

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


def generate_solvable_tray(grid, cols, rows):
    """
    Peels cubes from the outside in (reverse-clearing) to generate
    a list of tanks guaranteed to be solvable and correctly ordered.
    """
    grid_copy = [row[:] for row in grid]
    shot_sequence = []
    
    cubes_remaining = 0
    for r in range(rows):
        for c in range(cols):
            if grid_copy[r][c] is not None:
                cubes_remaining += 1
                
    loop_len = 2 * (cols + rows)
    attempts = 0
    max_attempts = cubes_remaining * 15
    
    while cubes_remaining > 0 and attempts < max_attempts:
        attempts += 1
        visible_cubes = []
        for step in range(loop_len):
            facing = get_facing_cell(step, cols, rows)
            if facing is not None:
                r, c, direction = facing
                tr, tc = find_first_cube(grid_copy, r, c, direction, cols, rows)
                if tr is not None and tc is not None:
                    visible_cubes.append((tr, tc))
                    
        if not visible_cubes:
            return None
            
        target_r, target_c = random.choice(visible_cubes)
        color = grid_copy[target_r][target_c]
        grid_copy[target_r][target_c] = None
        shot_sequence.append(color)
        cubes_remaining -= 1
        
    if cubes_remaining > 0:
        return None
        
    # Reverse the peel-off sequence to get the clearing sequence
    clearing_shots = list(reversed(shot_sequence))
    
    # Group consecutive same-color shots into tanks of size 5, 10, 15, 20
    tray_tanks = []
    current_color = None
    current_ammo = 0
    
    for color in clearing_shots:
        if current_color is None:
            current_color = color
            current_ammo = 1
        elif color == current_color and current_ammo < 20:
            current_ammo += 1
        else:
            tray_tanks.append((current_color, current_ammo))
            current_color = color
            current_ammo = 1
    if current_color is not None:
        tray_tanks.append((current_color, current_ammo))
        
    return tray_tanks


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

    if len(cube_cells) < n_colors * min_per_color:
        return None

    rng.shuffle(cube_cells)
    target_per_color = len(cube_cells) // n_colors
    remainder = len(cube_cells) - target_per_color * n_colors
    color_counts = [target_per_color] * n_colors
    for i in range(remainder):
        color_counts[i] += 1

    if any(c < min_per_color for c in color_counts):
        return None

    color_for_cell = {}
    idx_c = 0
    for c in range(n_colors):
        for _ in range(color_counts[c]):
            color_for_cell[cube_cells[idx_c]] = c
            idx_c += 1

    grid = [[None] * cols for _ in range(rows)]
    for (y, x), c in color_for_cell.items():
        grid[y][x] = c

    # Generate a guaranteed solvable tray using our reverse peeling logic
    tray_tanks = generate_solvable_tray(grid, cols, rows)
    if tray_tanks is None:
        return None

    # Format tray list of {"color": c, "ammo": a}
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
        while i < target_count and attempts < target_count * 100:
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
