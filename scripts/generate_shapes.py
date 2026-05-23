import os
import json

# Define the standard 7 Tangram pieces around their centroids
PIECES = [
    # 1. Large Pink Triangle (LT1) - area 16 in 8x8 grid
    {
        "id": 1,
        "initialVertices": [
            {"x": -0.3333, "y": -1.0},
            {"x": -0.3333, "y": 1.0},
            {"x": 0.6667, "y": 0.0}
        ]
    },
    # 2. Large Blue Triangle (LT2) - area 16
    {
        "id": 2,
        "initialVertices": [
            {"x": -1.0, "y": 0.3333},
            {"x": 1.0, "y": 0.3333},
            {"x": 0.0, "y": -0.6667}
        ]
    },
    # 3. Medium Triangle (MT) - area 8
    {
        "id": 3,
        "initialVertices": [
            {"x": -0.6667, "y": -0.3333},
            {"x": 0.3333, "y": -0.3333},
            {"x": 0.3333, "y": 0.6667}
        ]
    },
    # 4. Small Turquoise Triangle (ST1) - area 4
    {
        "id": 4,
        "initialVertices": [
            {"x": -0.5, "y": -0.1667},
            {"x": 0.5, "y": -0.1667},
            {"x": 0.0, "y": 0.3333}
        ]
    },
    # 5. Small Red Triangle (ST2) - area 4
    {
        "id": 5,
        "initialVertices": [
            {"x": 0.1667, "y": -0.5},
            {"x": 0.1667, "y": 0.5},
            {"x": -0.3333, "y": 0.0}
        ]
    },
    # 6. Green Square (SQ) - area 8
    {
        "id": 6,
        "initialVertices": [
            {"x": -0.5, "y": 0.0},
            {"x": 0.0, "y": -0.5},
            {"x": 0.5, "y": 0.0},
            {"x": 0.0, "y": 0.5}
        ]
    },
    # 7. Orange Parallelogram (PA) - area 8
    {
        "id": 7,
        "initialVertices": [
            {"x": -0.75, "y": -0.25},
            {"x": 0.25, "y": -0.25},
            {"x": 0.75, "y": 0.25},
            {"x": -0.25, "y": 0.25}
        ]
    }
]

PUZZLES = {
    "Easy": [
        {
            "name": "Square",
            "target": {
                "vertices": [
                    {"x": -1.0, "y": -1.0},
                    {"x": 1.0, "y": -1.0},
                    {"x": 1.0, "y": 1.0},
                    {"x": -1.0, "y": 1.0}
                ]
            },
            "pieces_solution": [
                {"id": 1, "pos": {"x": -0.6667, "y": 0.0}, "rot": 0},
                {"id": 2, "pos": {"x": 0.0, "y": 0.6667}, "rot": 0},
                {"id": 3, "pos": {"x": 0.6667, "y": -0.6667}, "rot": 0},
                {"id": 4, "pos": {"x": 0.0, "y": -0.3333}, "rot": 0},
                {"id": 5, "pos": {"x": 0.8333, "y": 0.5}, "rot": 0},
                {"id": 6, "pos": {"x": 0.5, "y": 0.0}, "rot": 0},
                {"id": 7, "pos": {"x": -0.25, "y": -0.75}, "rot": 0}
            ]
        }
    ],
    "Medium": [
        {
            "name": "Chevron",
            "target": {
                "vertices": [
                    {"x": -1.0, "y": -1.0},
                    {"x": 1.0, "y": -1.0},
                    {"x": 1.0, "y": 1.0},
                    {"x": 2.0, "y": 2.0},
                    {"x": 0.0, "y": 2.0},
                    {"x": 0.0, "y": 0.0}
                ]
            },
            "pieces_solution": [
                {"id": 1, "pos": {"x": 0.3333, "y": 1.0}, "rot": 0},
                {"id": 2, "pos": {"x": 1.0, "y": 1.6667}, "rot": 0},
                {"id": 3, "pos": {"x": 0.6667, "y": -0.6667}, "rot": 0},
                {"id": 4, "pos": {"x": 0.0, "y": -0.3333}, "rot": 0},
                {"id": 5, "pos": {"x": 0.8333, "y": 0.5}, "rot": 0},
                {"id": 6, "pos": {"x": 0.5, "y": 0.0}, "rot": 0},
                {"id": 7, "pos": {"x": -0.25, "y": -0.75}, "rot": 0}
            ]
        }
    ],
    "Hard": [
        {
            "name": "Parallelogram",
            "target": {
                "vertices": [
                    {"x": -1.0, "y": -1.0},
                    {"x": 1.0, "y": -1.0},
                    {"x": 3.0, "y": 1.0},
                    {"x": 1.0, "y": 1.0}
                ]
            },
            "pieces_solution": [
                {"id": 1, "pos": {"x": 1.3333, "y": 0.0}, "rot": 0},
                {"id": 2, "pos": {"x": 2.0, "y": 0.6667}, "rot": 0},
                {"id": 3, "pos": {"x": 0.6667, "y": -0.6667}, "rot": 0},
                {"id": 4, "pos": {"x": 0.0, "y": -0.3333}, "rot": 0},
                {"id": 5, "pos": {"x": 0.8333, "y": 0.5}, "rot": 0},
                {"id": 6, "pos": {"x": 0.5, "y": 0.0}, "rot": 0},
                {"id": 7, "pos": {"x": -0.25, "y": -0.75}, "rot": 0}
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
