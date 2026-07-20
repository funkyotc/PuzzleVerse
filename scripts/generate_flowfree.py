"""
Generate Flow Free puzzles using the improved generator
"""
import json
import os
from pathlib import Path

# This would invoke the Kotlin generator via a subprocess or use a Python implementation
# For now, let's create a simple Python-based implementation that matches the algorithm

def generate_snake_path(size):
    """Generate a simple Hamiltonian snake path"""
    path = []
    for r in range(size):
        if r % 2 == 0:
            for c in range(size):
                path.append([r, c])
            # row left-to-right
        else:
            for c in range(size - 1, -1, -1):
                path.append([r, c])
            # row right-to-left
    return path

def cut_path(path, num_colors):
    """Cut path into segments with minimum length 2"""
    n = len(path)
    if n < num_colors * 2:
        return None
    
    lengths = [2] * num_colors
    remaining = n - num_colors * 2
    
    import random
    random.seed(42)
    idx = random.randint(0, num_colors - 1)
    while remaining > 0:
        lengths[idx] += 1
        remaining -= 1
        idx = random.randint(0, num_colors - 1)
    
    cuts = [0]
    for length in lengths:
        cuts.append(cuts[-1] + length)
    
    return cuts

def create_puzzles():
    script_dir = Path(__file__).parent
    output_dir = script_dir / "output"
    
    difficulty_map = {
        "Easy": (5, 4),
        "Medium": (6, 5),
        "Hard": (7, 6),
        "Expert": (8, 7)
    }
    
    for diff_name, (size, num_colors) in difficulty_map.items():
        folder = output_dir / f"{diff_name}_{size}x{size}"
        folder.mkdir(parents=True, exist_ok=True)
        
        path = generate_snake_path(size)
        cuts = cut_path(path, num_colors)
        
        for i in range(num_colors):
            start = path[cuts[i]]
            end = path[cuts[i + 1] - 1]
            
            dots = [{"colorId": i + 1, "start": {"r": start[0], "c": start[1]}, "end": {"r": end[0], "c": end[1]}}]
            
            puzzle_data = {"dots": dots}
            puzzle_file = folder / f"puzzle_{i+1:03d}.json"
            with open(puzzle_file, 'w') as f:
                json.dump(puzzle_data, f, indent=2)
        
        print(f"Generated {num_colors} puzzles for {diff_name} ({size}x{size})")

if __name__ == "__main__":
    create_puzzles()