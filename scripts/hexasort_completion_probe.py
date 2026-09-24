"""Bounded exact pop/gravity search for shipped Hexa Sort boards (no shuffles)."""

import re
from collections import Counter
from pathlib import Path


source = Path("app/src/main/java/com/funkyotc/puzzleverse/hexasort/data/HexaSortPregenerated.kt").read_text()
pattern = re.compile(r'PregeneratedHexaSort\("([^"]+)", "[^"]+", (\d+), (\d+), listOf\((.*?)\n        \)\)', re.S)


def neighbors(row, col, rows, cols):
    candidates = [(row, col - 1), (row, col + 1)]
    if row % 2:
        candidates += [(row - 1, col), (row - 1, col + 1), (row + 1, col), (row + 1, col + 1)]
    else:
        candidates += [(row - 1, col - 1), (row - 1, col), (row + 1, col - 1), (row + 1, col)]
    return [(r, c) for r, c in candidates if 0 <= r < rows and 0 <= c < cols]


def moves(grid):
    rows, cols = len(grid), len(grid[0])
    seen = set()
    result = []
    for row in range(rows):
        for col in range(cols):
            if (row, col) in seen or grid[row][col] < 0:
                continue
            color = grid[row][col]
            group = {(row, col)}
            queue = [(row, col)]
            for r, c in queue:
                for nr, nc in neighbors(r, c, rows, cols):
                    if (nr, nc) not in group and grid[nr][nc] == color:
                        group.add((nr, nc))
                        queue.append((nr, nc))
            seen.update(group)
            if len(group) < 2:
                continue
            new = [list(r) for r in grid]
            for r, c in group:
                new[r][c] = -1
            for c in range(cols):
                values = [new[r][c] for r in range(rows) if new[r][c] >= 0]
                for r in range(rows):
                    new[r][c] = -1 if r < rows - len(values) else values[r - (rows - len(values))]
            result.append((len(group), (row, col), tuple(tuple(r) for r in new)))
    return sorted(result, reverse=True)


for match in pattern.finditer(source):
    game_id, rows, cols, raw = match.groups()
    grid = tuple(tuple(map(int, row.split(","))) for row in re.findall(r"listOf\(([^()]*)\)", raw))
    assert len(grid) == int(rows) and all(len(row) == int(cols) for row in grid)
    singleton_colors = [color for color, count in Counter(cell for row in grid for cell in row).items() if count == 1]
    if singleton_colors:
        print(game_id, "IMPOSSIBLE SINGLETON COLORS", singleton_colors, flush=True)
        continue
    visited = set()
    budget = 200_000 if int(rows) <= 6 else 20_000

    def solve(board):
        if all(cell < 0 for row in board for cell in row):
            return []
        if len(visited) >= budget or board in visited:
            return None
        visited.add(board)
        for _, tap, next_board in moves(board):
            continuation = solve(next_board)
            if continuation is not None:
                return [tap] + continuation
        return None

    solution = solve(grid)
    print(game_id, "SOLVED" if solution is not None else "UNPROVEN", len(visited),
          "moves=" + str(len(solution)) if solution is not None else "", flush=True)
