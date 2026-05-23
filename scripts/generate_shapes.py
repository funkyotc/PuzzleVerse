import os
import json
import math

# Define the standard 7 Tangram pieces around their centroids
PIECES = [
    # 1. Large Pink Triangle (LT1)
    {
        "id": 1,
        "initialVertices": [
            {"x": -0.3333, "y": -1.0},
            {"x": -0.3333, "y": 1.0},
            {"x": 0.6667, "y": 0.0}
        ]
    },
    # 2. Large Blue Triangle (LT2)
    {
        "id": 2,
        "initialVertices": [
            {"x": -1.0, "y": 0.3333},
            {"x": 1.0, "y": 0.3333},
            {"x": 0.0, "y": -0.6667}
        ]
    },
    # 3. Medium Triangle (MT)
    {
        "id": 3,
        "initialVertices": [
            {"x": -0.6667, "y": -0.3333},
            {"x": 0.3333, "y": -0.3333},
            {"x": 0.3333, "y": 0.6667}
        ]
    },
    # 4. Small Turquoise Triangle (ST1)
    {
        "id": 4,
        "initialVertices": [
            {"x": -0.5, "y": -0.1667},
            {"x": 0.5, "y": -0.1667},
            {"x": 0.0, "y": 0.3333}
        ]
    },
    # 5. Small Red Triangle (ST2)
    {
        "id": 5,
        "initialVertices": [
            {"x": 0.1667, "y": -0.5},
            {"x": 0.1667, "y": 0.5},
            {"x": -0.3333, "y": 0.0}
        ]
    },
    # 6. Green Square (SQ)
    {
        "id": 6,
        "initialVertices": [
            {"x": -0.5, "y": 0.0},
            {"x": 0.0, "y": -0.5},
            {"x": 0.5, "y": 0.0},
            {"x": 0.0, "y": 0.5}
        ]
    },
    # 7. Orange Parallelogram (PA)
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

BASE_PUZZLES = {
    "Easy": {
        "name": "Square",
        "target": [
            {"x": -1.0, "y": -1.0},
            {"x": 1.0, "y": -1.0},
            {"x": 1.0, "y": 1.0},
            {"x": -1.0, "y": 1.0}
        ],
        "pieces_solution": [
            {"id": 1, "pos": {"x": -0.6667, "y": 0.0}, "rot": 0},
            {"id": 2, "pos": {"x": 0.0, "y": 0.6667}, "rot": 0},
            {"id": 3, "pos": {"x": 0.6667, "y": -0.6667}, "rot": 0},
            {"id": 4, "pos": {"x": 0.0, "y": -0.3333}, "rot": 0},
            {"id": 5, "pos": {"x": 0.8333, "y": 0.5}, "rot": 0},
            {"id": 6, "pos": {"x": 0.5, "y": 0.0}, "rot": 0},
            {"id": 7, "pos": {"x": -0.25, "y": -0.75}, "rot": 0}
        ]
    },
    "Medium": {
        "name": "Chevron",
        "target": [
            {"x": -1.0, "y": -1.0},
            {"x": 1.0, "y": -1.0},
            {"x": 1.0, "y": 1.0},
            {"x": 2.0, "y": 2.0},
            {"x": 0.0, "y": 2.0},
            {"x": 0.0, "y": 0.0}
        ],
        "pieces_solution": [
            {"id": 1, "pos": {"x": 0.3333, "y": 1.0}, "rot": 0},
            {"id": 2, "pos": {"x": 1.0, "y": 1.6667}, "rot": 0},
            {"id": 3, "pos": {"x": 0.6667, "y": -0.6667}, "rot": 0},
            {"id": 4, "pos": {"x": 0.0, "y": -0.3333}, "rot": 0},
            {"id": 5, "pos": {"x": 0.8333, "y": 0.5}, "rot": 0},
            {"id": 6, "pos": {"x": 0.5, "y": 0.0}, "rot": 0},
            {"id": 7, "pos": {"x": -0.25, "y": -0.75}, "rot": 0}
        ]
    },
    "Hard": {
        "name": "Parallelogram",
        "target": [
            {"x": -1.0, "y": -1.0},
            {"x": 1.0, "y": -1.0},
            {"x": 3.0, "y": 1.0},
            {"x": 1.0, "y": 1.0}
        ],
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
}

TRANSFORMS = [
    {"tx": 0.0, "ty": 0.0, "rot": 0},
    {"tx": 0.5, "ty": 0.0, "rot": 0},
    {"tx": 0.0, "ty": 0.5, "rot": 0},
    {"tx": -0.5, "ty": 0.0, "rot": 0},
    {"tx": 0.0, "ty": -0.5, "rot": 0},
    {"tx": 0.0, "ty": 0.0, "rot": 45},
    {"tx": 0.0, "ty": 0.0, "rot": 90},
    {"tx": 0.0, "ty": 0.0, "rot": 180},
    {"tx": 0.0, "ty": 0.0, "rot": 270},
    {"tx": 0.2, "ty": -0.2, "rot": 45}
]

def rotate_point(pt, angle_degrees):
    rad = math.radians(angle_degrees)
    cos_a = math.cos(rad)
    sin_a = math.sin(rad)
    return {
        "x": round(pt["x"] * cos_a - pt["y"] * sin_a, 4),
        "y": round(pt["x"] * sin_a + pt["y"] * cos_a, 4)
    }

def translate_point(pt, tx, ty):
    return {
        "x": round(pt["x"] + tx, 4),
        "y": round(pt["y"] + ty, 4)
    }

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    os.makedirs(output_dir, exist_ok=True)
    
    for diff, base in BASE_PUZZLES.items():
        folder_name = f"Shapes_{diff}"
        folder_path = os.path.join(output_dir, folder_name)
        os.makedirs(folder_path, exist_ok=True)
        
        for idx, trans in enumerate(TRANSFORMS):
            tx, ty, rot = trans["tx"], trans["ty"], trans["rot"]
            
            # Transform target
            new_target_vertices = [translate_point(rotate_point(v, rot), tx, ty) for v in base["target"]]
            
            # Transform pieces
            pieces_data = []
            for solution_info in base["pieces_solution"]:
                piece_def = next(pdef for pdef in PIECES if pdef["id"] == solution_info["id"])
                
                # Transform piece solution coordinates
                new_pos = translate_point(rotate_point(solution_info["pos"], rot), tx, ty)
                new_rot = (solution_info["rot"] + rot) % 360
                
                pieces_data.append({
                    "id": piece_def["id"],
                    "initialVertices": piece_def["initialVertices"],
                    "solutionPosition": new_pos,
                    "solutionRotation": float(new_rot)
                })
                
            data = {
                "name": f"{base['name']} #{idx+1}",
                "target": {"vertices": new_target_vertices},
                "pieces": pieces_data
            }
            
            filename = f"puzzle_{idx+1:03d}.json"
            filepath = os.path.join(folder_path, filename)
            with open(filepath, "w") as f:
                json.dump(data, f, indent=2)
                
            print(f"Generated {filename} in {folder_name}")

if __name__ == "__main__":
    main()
