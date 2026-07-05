import os
import json
import math

PIECES = [
    {
        "id": 1,
        "initialVertices": [
            {"x": -0.3333, "y": -1.0},
            {"x": -0.3333, "y": 1.0},
            {"x": 0.6667, "y": 0.0}
        ]
    },
    {
        "id": 2,
        "initialVertices": [
            {"x": -1.0, "y": 0.3333},
            {"x": 1.0, "y": 0.3333},
            {"x": 0.0, "y": -0.6667}
        ]
    },
    {
        "id": 3,
        "initialVertices": [
            {"x": -0.6667, "y": -0.3333},
            {"x": 0.3333, "y": -0.3333},
            {"x": 0.3333, "y": 0.6667}
        ]
    },
    {
        "id": 4,
        "initialVertices": [
            {"x": -0.5, "y": -0.1667},
            {"x": 0.5, "y": -0.1667},
            {"x": 0.0, "y": 0.3333}
        ]
    },
    {
        "id": 5,
        "initialVertices": [
            {"x": 0.1667, "y": -0.5},
            {"x": 0.1667, "y": 0.5},
            {"x": -0.3333, "y": 0.0}
        ]
    },
    {
        "id": 6,
        "initialVertices": [
            {"x": -0.5, "y": 0.0},
            {"x": 0.0, "y": -0.5},
            {"x": 0.5, "y": 0.0},
            {"x": 0.0, "y": 0.5}
        ]
    },
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

def transform_vertices(vertices, pos, rot):
    """Rotate then translate vertices."""
    rad = math.radians(rot)
    cos_a = math.cos(rad)
    sin_a = math.sin(rad)
    result = []
    for v in vertices:
        rx = v["x"] * cos_a - v["y"] * sin_a
        ry = v["x"] * sin_a + v["y"] * cos_a
        result.append({"x": round(rx + pos["x"], 4), "y": round(ry + pos["y"], 4)})
    return result

def compute_silhouette(piece_solutions):
    """Compute the outer silhouette of all pieces combined.
    Uses convex hull since tangram puzzles tile to convex shapes."""
    all_verts = []
    for sol in piece_solutions:
        piece_def = next(pdef for pdef in PIECES if pdef["id"] == sol["id"])
        world_verts = transform_vertices(piece_def["initialVertices"], sol["pos"], sol["rot"])
        all_verts.extend(world_verts)
    return convex_hull(all_verts)

def cross(o, a, b):
    return (a["x"] - o["x"]) * (b["y"] - o["y"]) - (a["y"] - o["y"]) * (b["x"] - o["x"])

def convex_hull(points):
    unique = []
    seen = set()
    for p in points:
        key = (p["x"], p["y"])
        if key not in seen:
            seen.add(key)
            unique.append(p)
    if len(unique) <= 3:
        return unique
    pts = sorted(unique, key=lambda p: (p["x"], p["y"]))

    lower = []
    for p in pts:
        while len(lower) >= 2 and cross(lower[-2], lower[-1], p) <= 0:
            lower.pop()
        lower.append(p)

    upper = []
    for p in reversed(pts):
        while len(upper) >= 2 and cross(upper[-2], upper[-1], p) <= 0:
            upper.pop()
        upper.append(p)

    return lower[:-1] + upper[:-1]

BASE_PUZZLES = {
    "Easy": {
        "name": "Tangram Square",
        "type": "Square",
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
        "type": "Chevron",
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
        "type": "Parallelogram",
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

# Compute targets for base puzzles
for diff in BASE_PUZZLES:
    base = BASE_PUZZLES[diff]
    base["target"] = compute_silhouette(base["pieces_solution"])

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
    {"tx": 0.3, "ty": -0.2, "rot": 45},
    {"tx": -0.5, "ty": 0.5, "rot": 0},
    {"tx": 0.6, "ty": -0.5, "rot": 0},
    {"tx": -0.3, "ty": -0.4, "rot": 0},
    {"tx": 0.4, "ty": 0.4, "rot": 0},
    {"tx": 0.6, "ty": 0.0, "rot": 90},
    {"tx": 0.0, "ty": -0.6, "rot": 90},
    {"tx": -0.6, "ty": 0.0, "rot": 180},
    {"tx": 0.0, "ty": 0.6, "rot": 180},
    {"tx": 0.5, "ty": 0.6, "rot": 270},
    {"tx": -0.6, "ty": -0.6, "rot": 270},
    {"tx": 0.3, "ty": 0.0, "rot": 15},
    {"tx": -0.3, "ty": 0.2, "rot": 60},
    {"tx": 0.0, "ty": -0.3, "rot": 120},
    {"tx": 0.7, "ty": 0.3, "rot": 90},
    {"tx": -0.7, "ty": -0.3, "rot": 90},
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
            
            new_target_vertices = [translate_point(rotate_point(v, rot), tx, ty) for v in base["target"]]
            
            pieces_data = []
            for solution_info in base["pieces_solution"]:
                piece_def = next(pdef for pdef in PIECES if pdef["id"] == solution_info["id"])
                new_pos = translate_point(rotate_point(solution_info["pos"], rot), tx, ty)
                new_rot = (solution_info["rot"] + rot) % 360
                pieces_data.append({
                    "id": piece_def["id"],
                    "initialVertices": piece_def["initialVertices"],
                    "solutionPosition": new_pos,
                    "solutionRotation": float(new_rot)
                })
                
            data = {
                "name": f"{base['type']} #{idx+1}",
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
