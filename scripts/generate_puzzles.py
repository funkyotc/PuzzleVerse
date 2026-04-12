import os
import json
import random
import time
import sys
import multiprocessing
from collections import deque
from concurrent.futures import ProcessPoolExecutor, as_completed

DIFFICULTIES = [
    {"name": "Easy", "size": 5, "colors": 4, "target": 20},
    {"name": "Medium", "size": 6, "colors": 5, "target": 20},
    {"name": "Hard", "size": 7, "colors": 6, "target": 10},
    {"name": "Expert", "size": 8, "colors": 7, "target": 5}
]

def build_neighbors(size):
    """Precompute adjacency list as tuples for speed."""
    size2 = size * size
    nbs = [None] * size2
    for r in range(size):
        for c in range(size):
            i = r * size + c
            adj = []
            if r > 0: adj.append(i - size)
            if r < size - 1: adj.append(i + size)
            if c > 0: adj.append(i - 1)
            if c < size - 1: adj.append(i + 1)
            nbs[i] = tuple(adj)
    return nbs


def generate_filled_grid(size, num_colors, nbs):
    """
    Fill a grid with non-overlapping paths using Warnsdorff heuristic.
    Returns flat list grid or None on failure.
    """
    size2 = size * size
    grid = [0] * size2

    # Random seed placement
    cells = list(range(size2))
    random.shuffle(cells)
    
    # heads[color_index] = (head_cell, tail_cell)
    head = [0] * num_colors
    tail = [0] * num_colors
    path_len = [1] * num_colors
    
    for i in range(num_colors):
        seed = cells[i]
        grid[seed] = i + 1
        head[i] = seed
        tail[i] = seed

    filled = num_colors
    order = list(range(num_colors))

    while filled < size2:
        random.shuffle(order)
        grew = False

        for pi in order:
            cid = pi + 1
            # Try both ends
            for end_cell in (head[pi], tail[pi]) if random.random() > 0.5 else (tail[pi], head[pi]):
                empty_nbs = [n for n in nbs[end_cell] if grid[n] == 0]
                if not empty_nbs:
                    continue

                # Warnsdorff: pick neighbor with fewest onward empty neighbors
                if len(empty_nbs) == 1:
                    pick = empty_nbs[0]
                else:
                    best_score = 999
                    pick = empty_nbs[0]
                    for n in empty_nbs:
                        s = sum(1 for nn in nbs[n] if grid[nn] == 0)
                        if s < best_score:
                            best_score = s
                            pick = n
                    # Small randomness for variety
                    if random.random() < 0.15 and len(empty_nbs) > 1:
                        pick = random.choice(empty_nbs)

                grid[pick] = cid
                if end_cell == head[pi]:
                    head[pi] = pick
                else:
                    tail[pi] = pick
                path_len[pi] += 1
                filled += 1
                grew = True
                break
            if grew:
                break

        if not grew:
            return None

        # Quick bottleneck: any empty cell with zero empty neighbors = dead
        # Only check neighbors of the cell we just filled (incremental check)
        if grew:
            for n in nbs[pick]:
                if grid[n] == 0:
                    if not any(grid[nn] == 0 for nn in nbs[n] if nn != n):
                        # This empty cell has no other empty neighbors
                        # It could still be OK if it's the last empty cell
                        empty_count = 0
                        for nn in nbs[n]:
                            if grid[nn] == 0:
                                empty_count += 1
                        if empty_count == 0 and filled < size2 - 1:
                            return None

    # Validate: each color must form a simple path (exactly 2 endpoints)
    for ci in range(num_colors):
        cid = ci + 1
        endpoint_count = 0
        for i in range(size2):
            if grid[i] == cid:
                same_neighbors = sum(1 for n in nbs[i] if grid[n] == cid)
                if same_neighbors <= 1:
                    endpoint_count += 1
        if endpoint_count != 2:
            return None

    return grid


def extract_dots(grid, size, nbs):
    """Extract endpoint pairs for each color from a filled grid."""
    size2 = size * size
    color_endpoints = {}

    for i in range(size2):
        cid = grid[i]
        if cid > 0:
            same_n = sum(1 for n in nbs[i] if grid[n] == cid)
            if same_n <= 1:
                if cid not in color_endpoints:
                    color_endpoints[cid] = []
                color_endpoints[cid].append(i)

    dots = []
    for cid, eps in sorted(color_endpoints.items()):
        if len(eps) >= 2:
            dots.append({
                'colorId': cid,
                'start': {'r': eps[0] // size, 'c': eps[0] % size},
                'end': {'r': eps[1] // size, 'c': eps[1] % size}
            })
    return dots


def verify_unique_solution(dots, size, nbs, max_nodes=50000):
    """
    Backtracking solver counting solutions. Returns 0, 1, or 2+.
    Uses MRV heuristic. Stops early at 2 solutions.
    Uses a node counter to timeout on complex puzzles.
    """
    size2 = size * size
    grid = [0] * size2

    # Build lookup structures
    color_ids = []
    targets = {}
    starts = {}

    for d in dots:
        cid = d['colorId']
        color_ids.append(cid)
        s_i = d['start']['r'] * size + d['start']['c']
        e_i = d['end']['r'] * size + d['end']['c']
        grid[s_i] = cid
        grid[e_i] = cid
        starts[cid] = s_i
        targets[cid] = e_i

    paths = {cid: [starts[cid]] for cid in color_ids}
    result = [0]  # solution count
    nodes = [0]   # node counter for timeout

    def solve():
        if result[0] >= 2:
            return
        nodes[0] += 1
        if nodes[0] > max_nodes:
            result[0] = 99  # timeout signal
            return

        # MRV: find unfinished color with fewest moves
        best_cid = -1
        best_moves = None
        best_count = 999

        for cid in color_ids:
            path = paths[cid]
            h = path[-1]
            t = targets[cid]

            if h == t and len(path) > 1:
                continue  # Already connected

            moves = []
            for n in nbs[h]:
                if n == t and grid[n] == cid:
                    moves.append(n)
                elif grid[n] == 0:
                    moves.append(n)

            if not moves:
                return  # Dead end, prune

            if len(moves) < best_count:
                best_count = len(moves)
                best_cid = cid
                best_moves = moves
                if best_count == 1:
                    break  # Can't do better

        if best_cid == -1:
            # All connected. Check grid full.
            if 0 not in grid:
                result[0] += 1
            return

        path = paths[best_cid]
        t = targets[best_cid]

        for move in best_moves:
            grid[move] = best_cid
            path.append(move)

            solve()

            path.pop()
            # Only clear if not a pre-placed endpoint
            if move != starts[best_cid] and move != t:
                grid[move] = 0

            if result[0] >= 2 or nodes[0] > max_nodes:
                return

    solve()
    return result[0]


def worker_generate(args):
    difficulty_name, size, num_colors, worker_id = args
    nbs = build_neighbors(size)
    attempts = 0
    grids_found = 0
    is_logger = (worker_id == 0)
    t0 = time.time()

    while True:
        attempts += 1

        if is_logger and attempts % 500 == 0:
            elapsed = time.time() - t0
            print(f"  [{difficulty_name} {size}x{size}] Worker-0: {attempts} attempts, "
                  f"{grids_found} grids filled, {elapsed:.1f}s elapsed", flush=True)

        grid = generate_filled_grid(size, num_colors, nbs)
        if grid is None:
            continue

        grids_found += 1
        dots = extract_dots(grid, size, nbs)
        if len(dots) != num_colors:
            continue

        # Verify unique solution with timeout
        count = verify_unique_solution(dots, size, nbs, max_nodes=100000)

        if count == 1:
            return {
                "size": size,
                "difficulty": difficulty_name,
                "dots": dots,
                "attempts": attempts,
                "grids_tested": grids_found
            }
        # count == 0, 2+, or 99 (timeout) → try again


def main():
    output_base = os.path.join(os.path.dirname(os.path.abspath(__file__)), "output")
    os.makedirs(output_base, exist_ok=True)

    print(f"Flow Free Puzzle Generator")
    print(f"Using 12 CPU cores")
    print(f"Output: {output_base}")
    print()

    for diff in DIFFICULTIES:
        folder_name = f"{diff['name']}_{diff['size']}x{diff['size']}"
        folder_path = os.path.join(output_base, folder_name)
        os.makedirs(folder_path, exist_ok=True)

        target = diff['target']
        print(f"=== {folder_name}: Generating {target} puzzles ===")
        t_start = time.time()

        # Give each task a unique worker_id so only worker 0 logs
        tasks = [(diff['name'], diff['size'], diff['colors'], i) for i in range(target)]

        results_count = 0
        with ProcessPoolExecutor(max_workers=12) as executor:
            futures = [executor.submit(worker_generate, t) for t in tasks]

            for future in as_completed(futures):
                result = future.result()
                results_count += 1

                filename = f"puzzle_{results_count:03d}.json"
                filepath = os.path.join(folder_path, filename)

                with open(filepath, 'w') as f:
                    json.dump(result, f, indent=2)

                elapsed = time.time() - t_start
                print(f"  [{folder_name}] {results_count}/{target} done "
                      f"({result['attempts']} attempts, {result['grids_tested']} grids tested, "
                      f"{elapsed:.1f}s total)", flush=True)

        elapsed = time.time() - t_start
        print(f"  Completed {folder_name} in {elapsed:.1f}s\n")

    print("All done! Puzzles saved to scripts/output/")


if __name__ == '__main__':
    main()
