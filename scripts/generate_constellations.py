import os
import json

# Pre-defined Constellation (Star Battle) layouts
# Each difficulty has a 'size' and a few puzzles defined by regions and solutions.
# Rules: 1 star per row, column, and region (for small grids).
PUZZLES = {
    "Easy": [
        {
            "size": 5,
            "regions": {
                "0": [[0,0], [0,1], [1,0], [2,0]],
                "1": [[0,2], [0,3], [0,4], [1,3], [1,4]],
                "2": [[1,1], [1,2], [2,1], [2,2], [2,3]],
                "3": [[3,0], [3,1], [4,0], [4,1], [4,2]],
                "4": [[3,2], [3,3], [3,4], [4,3], [4,4], [2,4]]
            },
            "solution": [[0,0], [1,4], [2,2], [3,1], [4,3]]
        },
        {
            "size": 5,
            "regions": {
                "0": [[0,0], [0,1], [0,2], [1,0], [1,1]],
                "1": [[0,3], [0,4], [1,2], [1,3], [1,4]],
                "2": [[2,0], [2,1], [2,2], [3,0], [4,0]],
                "3": [[2,3], [2,4], [3,3], [3,4], [4,4]],
                "4": [[3,1], [3,2], [4,1], [4,2], [4,3]]
            },
            "solution": [[0,1], [1,3], [2,0], [3,4], [4,2]]
        }
    ],
    "Medium": [
        {
            "size": 6,
            "regions": {
                "0": [[0,0], [0,1], [0,2], [1,0], [1,1]],
                "1": [[0,3], [0,4], [0,5], [1,3], [1,4]],
                "2": [[1,2], [2,2], [2,3], [3,3]],
                "3": [[1,5], [2,4], [2,5], [3,4], [3,5]],
                "4": [[2,0], [2,1], [3,0], [4,0], [5,0]],
                "5": [[3,1], [3,2], [4,1], [4,2], [4,3], [4,4], [4,5], [5,1], [5,2], [5,3], [5,4], [5,5]]
            },
            "solution": [[0,0], [1,4], [2,2], [3,5], [4,3], [5,1]]
        }
    ],
    "Hard": [
        {
            "size": 8,
            "regions": {
                "0": [[0,0], [0,1], [0,2], [1,0], [1,1]],
                "1": [[0,3], [0,4], [0,5], [0,6], [1,3], [1,4]],
                "2": [[0,7], [1,7], [2,7], [3,7], [4,7], [5,7], [6,7], [7,7]],
                "3": [[1,2], [2,0], [2,1], [2,2], [2,3]],
                "4": [[1,5], [1,6], [2,4], [2,5], [2,6], [3,4], [3,5], [3,6]],
                "5": [[3,0], [3,1], [4,0], [4,1], [5,0], [5,1]],
                "6": [[3,2], [3,3], [4,2], [4,3], [5,2], [5,3], [6,2], [6,3]],
                "7": [[4,4], [4,5], [4,6], [5,4], [5,5], [5,6], [6,4], [6,5], [6,6], [7,0], [7,1], [7,2], [7,3], [7,4], [7,5], [7,6]]
            },
            "solution": [[0,0], [1,6], [2,3], [3,5], [4,1], [5,7], [6,4], [7,2]]
        }
    ]
}

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    os.makedirs(output_dir, exist_ok=True)
    
    for diff, puzzles in PUZZLES.items():
        folder_name = f"Constellations_{diff}"
        folder_path = os.path.join(output_dir, folder_name)
        os.makedirs(folder_path, exist_ok=True)
        
        for idx, p in enumerate(puzzles):
            filename = f"puzzle_{idx+1:03d}.json"
            filepath = os.path.join(folder_path, filename)
            with open(filepath, "w") as f:
                json.dump(p, f, indent=2)
                
            print(f"Generated {filename} in {folder_name}")

if __name__ == "__main__":
    main()
