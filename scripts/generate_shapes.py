import os
import json

# Define the classic 7 Tangram pieces around their local origin (0,0) approx.
# We will just construct 3 basic shapes for demonstration in the pipeline.

PIECES = [
    # Small Triangle 1
    {"id": 1, "initialVertices": [{"x": -0.5, "y": 0.5}, {"x": 0.5, "y": 0.5}, {"x": 0.0, "y": 0.0}]},
    # Small Triangle 2
    {"id": 2, "initialVertices": [{"x": -0.5, "y": 0.5}, {"x": 0.5, "y": 0.5}, {"x": 0.0, "y": 0.0}]},
    # Square
    {"id": 3, "initialVertices": [{"x": -0.5, "y": -0.5}, {"x": 0.5, "y": -0.5}, {"x": 0.5, "y": 0.5}, {"x": -0.5, "y": 0.5}]}
]

PUZZLES = {
    "Easy": [
        {
            "name": "House",
            "target": {
                "vertices": [{"x": 0.0, "y": 0.0}, {"x": 1.0, "y": 0.0}, {"x": 1.0, "y": 1.0}, {"x": -1.0, "y": 1.0}, {"x": -1.0, "y": 0.0}, {"x": -0.5, "y": -0.5}]
            },
            "pieces_solution": [
                {"id": 1, "pos": {"x": -0.5, "y": 0.5}, "rot": 0},
                {"id": 2, "pos": {"x": 0.5, "y": 0.5}, "rot": 0},
                {"id": 3, "pos": {"x": 0.0, "y": 1.0}, "rot": 0}
            ]
        },
        {
            "name": "Diamond",
            "target": {
                "vertices": [{"x": 0.0, "y": -1.0}, {"x": 1.0, "y": 0.0}, {"x": 0.0, "y": 1.0}, {"x": -1.0, "y": 0.0}]
            },
            "pieces_solution": [
                {"id": 1, "pos": {"x": -0.5, "y": 0.0}, "rot": 90},
                {"id": 2, "pos": {"x": 0.5, "y": 0.0}, "rot": -90},
                {"id": 3, "pos": {"x": 0.0, "y": 0.0}, "rot": 45}
            ]
        }
    ]
}

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    os.makedirs(output_dir, exist_ok=True)
    
    for diff, puzzles in PUZZLES.items():
        folder_name = f"Shapes_{diff}"
        folder_path = os.path.join(output_dir, folder_name)
        os.makedirs(folder_path, exist_ok=True)
        
        for idx, p in enumerate(puzzles):
            
            pieces_data = []
            for solution_info in p["pieces_solution"]:
                piece_def = next(pdef for pdef in PIECES if pdef["id"] == solution_info["id"])
                pieces_data.append({
                    "id": piece_def["id"],
                    "initialVertices": piece_def["initialVertices"],
                    "solutionPosition": solution_info["pos"],
                    "solutionRotation": solution_info["rot"]
                })
                
            data = {
                "name": p["name"],
                "target": p["target"],
                "pieces": pieces_data
            }
            
            filename = f"puzzle_{idx+1:03d}.json"
            filepath = os.path.join(folder_path, filename)
            with open(filepath, "w") as f:
                json.dump(data, f, indent=2)
                
            print(f"Generated {filename} in {folder_name}")

if __name__ == "__main__":
    main()
