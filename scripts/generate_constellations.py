import os
import json
import random
import networkx as nx

class StarBattleGenerator:
    def __init__(self, size, stars_per_row=1):
        self.size = size
        self.stars_per_row = stars_per_row
        self.grid = [[-1 for _ in range(size)] for _ in range(size)]
        self.regions = {} # region_id -> list of points

    def generate_regions(self):
        """Partition the grid into N contiguous regions using graph growth."""
        G = nx.grid_2d_graph(self.size, self.size)
        centers = random.sample(list(G.nodes), self.size)
        
        region_map = {center: i for i, center in enumerate(centers)}
        unassigned = list(set(G.nodes) - set(centers))
        random.shuffle(unassigned)
        
        # Grow regions
        queue = list(centers)
        region_cells = {i: [centers[i]] for i in range(self.size)}
        
        while unassigned:
            random.shuffle(queue)
            for parent in queue:
                neighbors = [n for n in G.neighbors(parent) if n in unassigned]
                if neighbors:
                    neighbor = random.choice(neighbors)
                    rid = region_map[parent]
                    region_map[neighbor] = rid
                    region_cells[rid].append(neighbor)
                    unassigned.remove(neighbor)
                    queue.append(neighbor)
                    break 
            else:
                # If no parent can expand, re-shuffle or find closest unassigned
                if unassigned:
                    node = unassigned.pop()
                    # Assign to nearest neighbor's region
                    for n in G.neighbors(node):
                        if n in region_map:
                            rid = region_map[n]
                            region_map[node] = rid
                            region_cells[rid].append(node)
                            break
        
        self.regions = {str(k): [list(p) for p in v] for k, v in region_cells.items()}
        return self.regions

    def solve(self, regions_dict, size, limit=2):
        """Standard backtracking solver for Star Battle."""
        solutions = []
        
        # Convert regions for fast lookup
        cell_to_region = {}
        for rid, cells in regions_dict.items():
            for r, c in cells:
                cell_to_region[(r, c)] = rid

        def is_valid(r, c, stars):
            # Row check
            if any(s[0] == r for s in stars): return False
            # Col check
            if any(s[1] == c for s in stars): return False
            # Region check
            rid = cell_to_region[(r,c)]
            if any(cell_to_region[(s[0], s[1])] == rid for s in stars): return False
            # Adjacency check (optional for Star Battle, but common: stars cannot touch)
            for sr, sc in stars:
                if abs(sr - r) <= 1 and abs(sc - c) <= 1: return False
            return True

        def backtrack(row, current_stars):
            if len(solutions) >= limit: return
            if row == size:
                solutions.append(list(current_stars))
                return

            for col in range(size):
                if is_valid(row, col, current_stars):
                    current_stars.append((row, col))
                    backtrack(row + 1, current_stars)
                    current_stars.pop()

        backtrack(0, [])
        return solutions

    def generate_puzzle(self):
        max_attempts = 100
        for _ in range(max_attempts):
            regions = self.generate_regions()
            solutions = self.solve(regions, self.size)
            if len(solutions) == 1:
                return {
                    "size": self.size,
                    "regions": regions,
                    "solution": [list(s) for s in solutions[0]]
                }
        return None

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    
    configs = {
        "Easy": {"size": 5, "count": 10},
        "Medium": {"size": 6, "count": 10},
        "Hard": {"size": 8, "count": 5}
    }
    
    for diff, config in configs.items():
        folder_name = f"Constellations_{diff}"
        folder_path = os.path.join(output_dir, folder_name)
        os.makedirs(folder_path, exist_ok=True)
        
        gen = StarBattleGenerator(config["size"])
        count = 0
        while count < config["count"]:
            puzzle = gen.generate_puzzle()
            if puzzle:
                count += 1
                filename = f"puzzle_{count:03d}.json"
                with open(os.path.join(folder_path, filename), "w") as f:
                    json.dump(puzzle, f, indent=2)
                print(f"Generated {folder_name}/{filename}")

if __name__ == "__main__":
    main()
