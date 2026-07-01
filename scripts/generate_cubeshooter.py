import os
import json
import glob
import random

PALETTE = [0, 1, 2, 3]

DIFFICULTY_CONFIG = {
    "Easy":   {"size": 6,  "shapes": 6,  "n_colors": 4, "min_per_color": 2},
    "Medium": {"size": 8,  "shapes": 10, "n_colors": 4, "min_per_color": 3},
    "Hard":   {"size": 10, "shapes": 16, "n_colors": 4, "min_per_color": 4},
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


def gen_level(diff, idx, seed_val):
    rng = random.Random(seed_val)
    cfg = DIFFICULTY_CONFIG[diff]
    size = cfg["size"]
    target_shapes = cfg["shapes"]
    n_colors = cfg["n_colors"]
    min_per_color = cfg["min_per_color"]

    grid = [[None] * size for _ in range(size)]
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
        if w > size or h > size:
            continue
        ox = rng.randint(0, size - w)
        oy = rng.randint(0, size - h)
        cells = [(ox + dx, oy + dy) for (dx, dy) in rot_shape]
        if any(grid[y][x] is not None for (x, y) in cells):
            continue
        for (x, y) in cells:
            grid[y][x] = -1
        placed += 1

    cube_cells = []
    for y in range(size):
        for x in range(size):
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

    grid = [[None] * size for _ in range(size)]
    for (y, x), c in color_for_cell.items():
        grid[y][x] = c

    tray = []
    for c in range(n_colors):
        if color_counts[c] > 0:
            tray.append({"color": c, "ammo": color_counts[c]})

    if len(tray) > 5:
        return None

    return {
        "id": f"cubeshooter_{diff.lower()}_{idx:03d}",
        "difficulty": diff,
        "cols": size,
        "rows": size,
        "grid": grid,
        "tray": tray,
        "_color_counts": color_counts,
    }


def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    out_dir = os.path.join(script_dir, "output", "cubeshooter")
    os.makedirs(out_dir, exist_ok=True)

    counts_per_difficulty = {"Easy": 20, "Medium": 20, "Hard": 20}

    for diff, target_count in counts_per_difficulty.items():
        i = 0
        attempts = 0
        while i < target_count and attempts < target_count * 30:
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
