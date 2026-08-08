"""
Generate Flow Free puzzles using organic grid partitioning and solver verification.
Every puzzle is guaranteed to:
1. Be fully solvable.
2. Require 100% grid coverage with no empty space left.
3. Have no wasted/redundant snake paths.
4. Possess a unique full-coverage solution.
"""
import json
import os
import random
from pathlib import Path

class FlowSolver:
    def __init__(self, size, dots):
        self.size = size
        self.num_colors = len(dots)
        self.dots = dots
        self.dot_map = {}
        self.dot_pairs = {}
        for d in dots:
            cid = d['colorId']
            st = (d['start']['r'], d['start']['c'])
            en = (d['end']['r'], d['end']['c'])
            self.dot_map[st] = cid
            self.dot_map[en] = cid
            self.dot_pairs[cid] = (st, en)

        self.neighbors = {}
        for r in range(size):
            for c in range(size):
                nbrs = []
                for dr, dc in [(-1,0), (1,0), (0,-1), (0,1)]:
                    nr, nc = r + dr, c + dc
                    if 0 <= nr < size and 0 <= nc < size:
                        nbrs.append((nr, nc))
                self.neighbors[(r, c)] = nbrs

        self.color_sorted_nbrs = {}
        for cid, (st, en) in self.dot_pairs.items():
            self.color_sorted_nbrs[cid] = {}
            for r in range(size):
                for c in range(size):
                    nbrs = list(self.neighbors[(r, c)])
                    nbrs.sort(key=lambda n: abs(n[0]-en[0]) + abs(n[1]-en[1]))
                    self.color_sorted_nbrs[cid][(r, c)] = nbrs

    def solve(self, max_solutions=2, max_steps=100000):
        """Find up to max_solutions full-coverage solutions."""
        grid = [[0] * self.size for _ in range(self.size)]
        solutions_found = []
        paths = {cid: [] for cid in self.dot_pairs}
        
        color_order = sorted(
            list(range(1, self.num_colors + 1)),
            key=lambda c: abs(self.dot_pairs[c][0][0] - self.dot_pairs[c][1][0]) + abs(self.dot_pairs[c][0][1] - self.dot_pairs[c][1][1])
        )

        heads = {}
        step_count = [0]

        def is_dead_end(r, c, curr_cid):
            if grid[r][c] != 0:
                return False
            is_dot = (r, c) in self.dot_map
            curr_head = heads.get(curr_cid)
            avail = sum(1 for nr, nc in self.neighbors[(r, c)] if grid[nr][nc] == 0 or (nr, nc) == curr_head)
            min_req = 1 if is_dot else 2
            return avail < min_req

        def check_local_prune(r, c, curr_cid):
            for nr, nc in self.neighbors[(r, c)]:
                if is_dead_end(nr, nc, curr_cid):
                    return True
            return False

        def backtrack(color_idx, curr_pos):
            step_count[0] += 1
            if step_count[0] > max_steps or len(solutions_found) >= max_solutions:
                return

            cid = color_order[color_idx]
            start_pt, end_pt = self.dot_pairs[cid]

            if curr_pos == end_pt:
                heads.pop(cid, None)
                if color_idx + 1 == self.num_colors:
                    total_filled = sum(len(p) for p in paths.values())
                    if total_filled == self.size * self.size:
                        solutions_found.append({c: list(p) for c, p in paths.items()})
                    return
                else:
                    next_cid = color_order[color_idx + 1]
                    n_start, n_end = self.dot_pairs[next_cid]
                    grid[n_start[0]][n_start[1]] = next_cid
                    paths[next_cid].append(n_start)
                    heads[next_cid] = n_start
                    backtrack(color_idx + 1, n_start)
                    paths[next_cid].pop()
                    grid[n_start[0]][n_start[1]] = 0
                    heads.pop(next_cid, None)
                    return

            r, c = curr_pos
            nbrs = self.color_sorted_nbrs[cid][(r, c)]

            for nr, nc in nbrs:
                if (nr, nc) == end_pt:
                    grid[nr][nc] = cid
                    paths[cid].append((nr, nc))
                    heads[cid] = (nr, nc)
                    backtrack(color_idx, (nr, nc))
                    paths[cid].pop()
                    grid[nr][nc] = 0
                    heads[cid] = curr_pos
                elif grid[nr][nc] == 0 and (nr, nc) not in self.dot_map:
                    grid[nr][nc] = cid
                    paths[cid].append((nr, nc))
                    heads[cid] = (nr, nc)
                    
                    if not check_local_prune(nr, nc, cid):
                        backtrack(color_idx, (nr, nc))
                    
                    paths[cid].pop()
                    grid[nr][nc] = 0
                    heads[cid] = curr_pos

        first_cid = color_order[0]
        f_start, f_end = self.dot_pairs[first_cid]
        grid[f_start[0]][f_start[1]] = first_cid
        paths[first_cid].append(f_start)
        heads[first_cid] = f_start
        backtrack(0, f_start)

        if step_count[0] > max_steps:
            return []  # Search timed out, candidate rejected
        return solutions_found

def generate_initial_snake_paths(size, num_colors):
    path = []
    for r in range(size):
        if r % 2 == 0:
            for c in range(size):
                path.append((r, c))
        else:
            for c in range(size - 1, -1, -1):
                path.append((r, c))
    
    total = len(path)
    base_len = total // num_colors
    remainder = total % num_colors
    
    paths = []
    idx = 0
    for i in range(num_colors):
        length = base_len + (1 if i < remainder else 0)
        paths.append(path[idx:idx + length])
        idx += length

    return paths

def mutate_paths(size, num_colors, initial_paths, iterations=40):
    min_len = 3
    paths = [list(p) for p in initial_paths]
    
    def get_neighbors(r, c):
        res = []
        for dr, dc in [(-1,0), (1,0), (0,-1), (0,1)]:
            nr, nc = r + dr, c + dc
            if 0 <= nr < size and 0 <= nc < size:
                res.append((nr, nc))
        return res

    for _ in range(iterations):
        grid = {}
        for p_idx, p in enumerate(paths):
            for pos, pt in enumerate(p):
                grid[pt] = (p_idx, pos)

        p1_idx = random.randint(0, num_colors - 1)
        p1 = paths[p1_idx]
        if len(p1) <= min_len:
            continue

        for end_pt_pos in [0, -1]:
            pt = p1[end_pt_pos]
            nbrs = get_neighbors(pt[0], pt[1])
            random.shuffle(nbrs)
            
            transferred = False
            for n in nbrs:
                p2_idx, p2_pos = grid[n]
                if p2_idx == p1_idx:
                    continue
                p2 = paths[p2_idx]
                
                if p2_pos == 0:
                    if end_pt_pos == 0:
                        p1.pop(0)
                    else:
                        p1.pop()
                    p2.insert(0, pt)
                    transferred = True
                    break
                elif p2_pos == len(p2) - 1:
                    if end_pt_pos == 0:
                        p1.pop(0)
                    else:
                        p1.pop()
                    p2.append(pt)
                    transferred = True
                    break

            if transferred:
                break

    return paths

def generate_partition_puzzle(size, num_colors):
    min_len = 3
    for attempt in range(500):
        initial = generate_initial_snake_paths(size, num_colors)
        max_it = 40 if size <= 6 else 25
        iterations = random.randint(10, max_it)
        paths = mutate_paths(size, num_colors, initial, iterations=iterations)
        
        valid = True
        dots = []
        for i, p in enumerate(paths):
            if len(p) < min_len:
                valid = False
                break
            st, en = p[0], p[-1]
            if abs(st[0]-en[0]) + abs(st[1]-en[1]) < 1:
                valid = False
                break
            dots.append({
                "colorId": i + 1,
                "start": {"r": st[0], "c": st[1]},
                "end": {"r": en[0], "c": en[1]}
            })

        if not valid:
            continue

        # Verify uniqueness with FlowSolver
        solver = FlowSolver(size, dots)
        solutions = solver.solve(max_solutions=2, max_steps=3000)
        if len(solutions) == 1:
            return dots

    return None

def create_puzzles():
    script_dir = Path(__file__).parent
    output_dir = script_dir / "output"
    
    difficulty_map = {
        "Easy": (5, 5, 5),      # size, colors, count
        "Medium": (6, 6, 5),
        "Hard": (7, 6, 5),
        "Expert": (8, 7, 5)
    }

    random.seed(42)
    
    for diff_name, (size, num_colors, count) in difficulty_map.items():
        folder = output_dir / f"{diff_name}_{size}x{size}"
        folder.mkdir(parents=True, exist_ok=True)
        
        for f in folder.glob("puzzle_*.json"):
            f.unlink()

        generated = 0
        attempts = 0
        while generated < count and attempts < 10000:
            attempts += 1
            dots = generate_partition_puzzle(size, num_colors)
            if dots:
                generated += 1
                puzzle_data = {"dots": dots}
                puzzle_file = folder / f"puzzle_{generated:03d}.json"
                with open(puzzle_file, 'w') as f:
                    json.dump(puzzle_data, f, indent=2)
                print(f"[{diff_name}] Generated puzzle {generated}/{count} (size {size}x{size}, {num_colors} colors)", flush=True)
        
        if generated < count:
            print(f"Warning: Generated {generated}/{count} puzzles for {diff_name}", flush=True)

if __name__ == "__main__":
    create_puzzles()