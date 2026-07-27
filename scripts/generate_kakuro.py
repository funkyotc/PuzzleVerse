import os
import json
import glob
import random

class KakuroSolver:
    """
    Solves a Kakuro grid with given clues to count number of valid solutions.
    Uses MRV and Min/Max Sum Pruning for high performance.
    """
    def __init__(self, size, white_cells, h_runs, v_runs):
        self.size = size
        self.white_cells = white_cells
        self.h_runs = h_runs
        self.v_runs = v_runs

    def count_solutions(self, limit=2):
        assignment = {}
        solutions = 0

        def min_possible_sum(k, available_nums):
            if k == 0: return 0
            if len(available_nums) < k: return 999
            return sum(available_nums[:k])

        def max_possible_sum(k, available_nums):
            if k == 0: return 0
            if len(available_nums) < k: return -999
            return sum(available_nums[-k:])

        def get_candidates(cell):
            h_info = self.h_runs[cell]
            v_info = self.v_runs[cell]

            h_cells, h_target = h_info['cells'], h_info['clue']
            v_cells, v_target = v_info['cells'], v_info['clue']

            h_used = {assignment[p] for p in h_cells if p in assignment}
            v_used = {assignment[p] for p in v_cells if p in assignment}

            h_sum = sum(assignment[p] for p in h_cells if p in assignment)
            v_sum = sum(assignment[p] for p in v_cells if p in assignment)

            h_unfilled = sum(1 for p in h_cells if p not in assignment) - 1
            v_unfilled = sum(1 for p in v_cells if p not in assignment) - 1

            h_avail = [n for n in range(1, 10) if n not in h_used]
            v_avail = [n for n in range(1, 10) if n not in v_used]

            candidates = []
            for num in range(1, 10):
                if num in h_used or num in v_used:
                    continue

                rem_h = h_target - (h_sum + num)
                if rem_h < 0:
                    continue
                rem_h_avail = [n for n in h_avail if n != num]
                if rem_h < min_possible_sum(h_unfilled, rem_h_avail) or rem_h > max_possible_sum(h_unfilled, rem_h_avail):
                    continue

                rem_v = v_target - (v_sum + num)
                if rem_v < 0:
                    continue
                rem_v_avail = [n for n in v_avail if n != num]
                if rem_v < min_possible_sum(v_unfilled, rem_v_avail) or rem_v > max_possible_sum(v_unfilled, rem_v_avail):
                    continue

                candidates.append(num)
            return candidates

        def backtrack():
            nonlocal solutions
            if solutions >= limit:
                return

            unassigned = [c for c in self.white_cells if c not in assignment]
            if not unassigned:
                solutions += 1
                return

            best_cell = None
            best_candidates = None
            for cell in unassigned:
                cands = get_candidates(cell)
                if best_candidates is None or len(cands) < len(best_candidates):
                    best_cell = cell
                    best_candidates = cands
                    if len(best_candidates) == 0:
                        break

            if not best_candidates:
                return

            for num in best_candidates:
                assignment[best_cell] = num
                backtrack()
                del assignment[best_cell]
                if solutions >= limit:
                    return

        backtrack()
        return solutions


def create_puzzle_from_mask(mask):
    size = len(mask)
    white_cells = []
    for r in range(size):
        for c in range(size):
            if mask[r][c] == 'W':
                white_cells.append((r, c))

    h_runs_by_cell = {}
    for r in range(size):
        c = 0
        while c < size:
            if mask[r][c] == 'W':
                start_c = c
                while c < size and mask[r][c] == 'W':
                    c += 1
                run_cells = [(r, i) for i in range(start_c, c)]
                clue_cell = (r, start_c - 1)
                for cell in run_cells:
                    h_runs_by_cell[cell] = {'clue_pos': clue_cell, 'cells': run_cells}
            else:
                c += 1

    v_runs_by_cell = {}
    for c in range(size):
        r = 0
        while r < size:
            if mask[r][c] == 'W':
                start_r = r
                while r < size and mask[r][c] == 'W':
                    r += 1
                run_cells = [(i, c) for i in range(start_r, r)]
                clue_cell = (start_r - 1, c)
                for cell in run_cells:
                    v_runs_by_cell[cell] = {'clue_pos': clue_cell, 'cells': run_cells}
            else:
                r += 1

    for cell in white_cells:
        if cell not in h_runs_by_cell or cell not in v_runs_by_cell:
            return None
        if not (2 <= len(h_runs_by_cell[cell]['cells']) <= 9):
            return None
        if not (2 <= len(v_runs_by_cell[cell]['cells']) <= 9):
            return None

    assignment = {}

    def get_fill_candidates(cell):
        h_cells = h_runs_by_cell[cell]['cells']
        v_cells = v_runs_by_cell[cell]['cells']

        h_used = {assignment[p] for p in h_cells if p in assignment}
        v_used = {assignment[p] for p in v_cells if p in assignment}

        return [n for n in range(1, 10) if n not in h_used and n not in v_used]

    def fill_numbers():
        unassigned = [c for c in white_cells if c not in assignment]
        if not unassigned:
            return True

        best_cell = None
        best_candidates = None
        for cell in unassigned:
            cands = get_fill_candidates(cell)
            if best_candidates is None or len(cands) < len(best_candidates):
                best_cell = cell
                best_candidates = cands
                if len(best_candidates) == 0:
                    break

        if not best_candidates:
            return False

        random.shuffle(best_candidates)
        for n in best_candidates:
            assignment[best_cell] = n
            if fill_numbers():
                return True
            del assignment[best_cell]

        return False

    for attempt in range(200):
        assignment.clear()
        if not fill_numbers():
            continue

        h_run_clues = {}
        for cell, info in h_runs_by_cell.items():
            clue_pos = info['clue_pos']
            if clue_pos not in h_run_clues:
                h_run_clues[clue_pos] = sum(assignment[p] for p in info['cells'])

        v_run_clues = {}
        for cell, info in v_runs_by_cell.items():
            clue_pos = info['clue_pos']
            if clue_pos not in v_run_clues:
                v_run_clues[clue_pos] = sum(assignment[p] for p in info['cells'])

        solver_h_runs = {}
        for cell, info in h_runs_by_cell.items():
            solver_h_runs[cell] = {
                'cells': info['cells'],
                'clue': h_run_clues[info['clue_pos']]
            }

        solver_v_runs = {}
        for cell, info in v_runs_by_cell.items():
            solver_v_runs[cell] = {
                'cells': info['cells'],
                'clue': v_run_clues[info['clue_pos']]
            }

        solver = KakuroSolver(size, white_cells, solver_h_runs, solver_v_runs)
        s_count = solver.count_solutions(limit=2)

        if s_count == 1:
            grid_json = []
            for r in range(size):
                row_data = []
                for c in range(size):
                    cell_pos = (r, c)
                    if mask[r][c] == 'W':
                        row_data.append({
                            "type": "PLAYER_INPUT",
                            "val": assignment[cell_pos],
                            "row": r,
                            "col": c
                        })
                    else:
                        h_clue = h_run_clues.get(cell_pos)
                        v_clue = v_run_clues.get(cell_pos)
                        if h_clue is not None or v_clue is not None:
                            row_data.append({
                                "type": "CLUE",
                                "clue": {
                                    "horizontalSum": h_clue,
                                    "verticalSum": v_clue
                                },
                                "row": r,
                                "col": c
                            })
                        else:
                            row_data.append({
                                "type": "BLACK",
                                "row": r,
                                "col": c
                            })
                grid_json.append(row_data)

            return grid_json

    return None


MASKS_EASY_5x5 = [
    [
        ". . C C .",
        ". C W W .",
        "C W W W .",
        "C W W W .",
        ". C W W ."
    ],
    [
        ". C C . .",
        "C W W C .",
        "C W W W C",
        ". C W W W",
        ". . C W W"
    ]
]

MASKS_MEDIUM_6x6 = [
    [
        ". . C C . .",
        ". C W W C .",
        "C W W W W C",
        "C W W W W C",
        ". C W W C .",
        ". . C C . ."
    ],
    [
        ". C C . C .",
        "C W W C W W",
        "C W W W W C",
        ". C W W W C",
        "C W W C W W",
        ". C C . C ."
    ]
]

MASKS_HARD_7x7 = [
    [
        ". . C C C . .",
        ". C W W W C .",
        "C W W W W W C",
        "C W W . C W W",
        "C W W W W W C",
        ". C W W W C .",
        ". . C C C . ."
    ],
    [
        ". C C . C C .",
        "C W W C W W .",
        "C W W W W W C",
        ". C W W W C .",
        "C W W W W W C",
        ". C W W C W W",
        ". C C . C C ."
    ]
]

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output", "kakuro")
    os.makedirs(output_dir, exist_ok=True)

    for old_file in glob.glob(os.path.join(output_dir, "*.json")):
        try:
            os.remove(old_file)
        except Exception:
            pass

    configs = [
        ("Easy", MASKS_EASY_5x5, 15),
        ("Medium", MASKS_MEDIUM_6x6, 10),
        ("Hard", MASKS_HARD_7x7, 10)
    ]

    for diff, masks, count in configs:
        generated = 0
        attempts = 0
        while generated < count:
            attempts += 1
            mask_raw = random.choice(masks)
            mask = [row.split() for row in mask_raw]
            grid_data = create_puzzle_from_mask(mask)
            if grid_data is not None:
                generated += 1
                p = {"difficulty": diff, "size": len(mask), "grid": grid_data}
                filepath = os.path.join(output_dir, f"{diff.lower()}_{generated}.json")
                with open(filepath, "w") as f:
                    json.dump(p, f, indent=2)
                print(f"Generated Kakuro {diff} {generated}/{count} (Attempt {attempts})")

if __name__ == "__main__":
    main()
