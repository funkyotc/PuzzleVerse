import os
import json
import glob

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    
    target = os.path.join(script_dir, "..", "app", "src", "main", "java",
                          "com", "funkyotc", "puzzleverse", "shapes", "data",
                          "ShapesPregenerated.kt")
    target = os.path.normpath(target)
    
    # Needs to create directory if not exists
    os.makedirs(os.path.dirname(target), exist_ok=True)
    
    all_puzzles = []
    
    for folder in sorted(os.listdir(output_dir)):
        if not folder.startswith("Shapes_"):
            continue
        folder_path = os.path.join(output_dir, folder)
        if not os.path.isdir(folder_path):
            continue
            
        diff_name = folder.replace("Shapes_", "")
        
        json_files = sorted(glob.glob(os.path.join(folder_path, "puzzle_*.json")))
        for jf in json_files:
            with open(jf, 'r') as f:
                data = json.load(f)
            
            puzzle_id = f"Shapes_{diff_name}_{os.path.splitext(os.path.basename(jf))[0]}"
            
            # Form target shape kotlin
            target_vertices = ", ".join([f"androidx.compose.ui.geometry.Offset({v['x']}f, {v['y']}f)" for v in data["target"]["vertices"]])
            target_kt = f"TargetShape(vertices = listOf({target_vertices}))"
            
            # Form pieces kotlin
            pieces_kt = []
            for idx, p in enumerate(data["pieces"]):
                vertices_str = ", ".join([f"androidx.compose.ui.geometry.Offset({v['x']}f, {v['y']}f)" for v in p["initialVertices"]])
                pieces_kt.append(
                    f"PuzzlePiece(id = {p['id']}, initialVertices = listOf({vertices_str}), position = androidx.compose.ui.geometry.Offset(0f, 0f), solutionPosition = androidx.compose.ui.geometry.Offset({p['solutionPosition']['x']}f, {p['solutionPosition']['y']}f), solutionRotation = {p['solutionRotation']}f)"
                )
            
            all_puzzles.append({
                "id": puzzle_id,
                "difficulty": diff_name,
                "name": data.get("name", "Shape"),
                "target_kt": target_kt,
                "pieces_kt": ",\n                    ".join(pieces_kt)
            })
            
    sb = []
    sb.append("package com.funkyotc.puzzleverse.shapes.data")
    sb.append("")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.append("import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece")
    sb.append("import com.funkyotc.puzzleverse.shapes.model.TargetShape")
    sb.append("import com.funkyotc.puzzleverse.shapes.model.ShapesPuzzle")
    sb.append("")
    sb.append("data class PregeneratedShape(")
    sb.append("    override val id: String,")
    sb.append("    override val difficulty: String,")
    sb.append("    val name: String,")
    sb.append("    val target: TargetShape,")
    sb.append("    val pieces: List<PuzzlePiece>")
    sb.append(") : BrowseablePuzzle {")
    sb.append("    override val label: String get() = name")
    sb.append("    override val subtitle: String get() = \"${pieces.size} pieces\"")
    sb.append("    ")
    sb.append("    fun toShapesPuzzle(): ShapesPuzzle {")
    sb.append("        return ShapesPuzzle(pieces, target, false)")
    sb.append("    }")
    sb.append("}")
    sb.append("")
    sb.append("object ShapesPregenerated {")
    sb.append("    val ALL_PUZZLES: List<PregeneratedShape> by lazy {")
    sb.append("        listOf(")
    
    for p in all_puzzles:
        sb.append(f'            PregeneratedShape(')
        sb.append(f'                id = "{p["id"]}",')
        sb.append(f'                difficulty = "{p["difficulty"]}",')
        sb.append(f'                name = "{p["name"]}",')
        sb.append(f'                target = {p["target_kt"]},')
        sb.append(f'                pieces = listOf(\n                    {p["pieces_kt"]}\n                )')
        sb.append(f'            ),')
        
    sb.append("        )")
    sb.append("    }")
    sb.append("")
    sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedShape>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }")
    sb.append("")
    sb.append("    fun getPuzzleById(id: String): PregeneratedShape? = ALL_PUZZLES.find { it.id == id }")
    sb.append("}")
    sb.append("")
    
    with open(target, 'w') as f:
        f.write("\n".join(sb))
        
    print(f"Baked {len(all_puzzles)} Shape puzzles into {target}")

if __name__ == '__main__':
    main()
