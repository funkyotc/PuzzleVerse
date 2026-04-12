"""
Convert generated JSON puzzles into FlowFreePregenerated.kt
Run: python scripts/bake_puzzles.py
"""
import os
import json
import glob

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    
    # Map folder names to difficulty enum names
    difficulty_map = {
        "Easy": "EASY",
        "Medium": "MEDIUM",
        "Hard": "HARD",
        "Expert": "EXPERT"
    }
    
    # Collect all puzzles grouped by difficulty then by size
    # Structure: { "EASY": { 5: [ {dots, id} ], ... }, ... }
    all_puzzles = {}
    
    for folder in sorted(os.listdir(output_dir)):
        folder_path = os.path.join(output_dir, folder)
        if not os.path.isdir(folder_path):
            continue
            
        # Parse folder name like "Easy_5x5"
        parts = folder.split("_")
        if len(parts) != 2:
            continue
        diff_name = parts[0]
        size_str = parts[1]  # "5x5"
        
        enum_name = difficulty_map.get(diff_name)
        if not enum_name:
            continue
            
        size = int(size_str.split("x")[0])
        
        if enum_name not in all_puzzles:
            all_puzzles[enum_name] = {}
        if size not in all_puzzles[enum_name]:
            all_puzzles[enum_name][size] = []
        
        json_files = sorted(glob.glob(os.path.join(folder_path, "puzzle_*.json")))
        for jf in json_files:
            with open(jf, 'r') as f:
                data = json.load(f)
            puzzle_id = os.path.splitext(os.path.basename(jf))[0]  # "puzzle_001"
            all_puzzles[enum_name][size].append({
                "id": f"{diff_name}_{size_str}_{puzzle_id}",
                "dots": data["dots"],
                "size": size,
                "difficulty": diff_name
            })
    
    # Generate Kotlin
    target = os.path.join(script_dir, "..", "app", "src", "main", "java", 
                          "com", "funkyotc", "puzzleverse", "flowfree", "data",
                          "FlowFreePregenerated.kt")
    target = os.path.normpath(target)
    
    sb = []
    sb.append("package com.funkyotc.puzzleverse.flowfree.data\n")
    sb.append("")
    sb.append("data class PregeneratedPuzzle(")
    sb.append("    val id: String,")
    sb.append("    val size: Int,")
    sb.append("    val difficulty: String,")
    sb.append("    val dots: List<ColorDot>")
    sb.append(")")
    sb.append("")
    sb.append("object FlowFreePregenerated {")
    sb.append("")
    sb.append("    val ALL_PUZZLES: List<PregeneratedPuzzle> = listOf(")
    
    total = 0
    for enum_name in ["EASY", "MEDIUM", "HARD", "EXPERT"]:
        if enum_name not in all_puzzles:
            continue
        sizes = sorted(all_puzzles[enum_name].keys())
        for size in sizes:
            puzzles = all_puzzles[enum_name][size]
            for p in puzzles:
                dots_str = ", ".join([
                    f"ColorDot({d['colorId']}, Point({d['start']['r']}, {d['start']['c']}), Point({d['end']['r']}, {d['end']['c']}))"
                    for d in p["dots"]
                ])
                sb.append(f'        PregeneratedPuzzle("{p["id"]}", {p["size"]}, "{p["difficulty"]}", listOf({dots_str})),')
                total += 1
    
    sb.append("    )")
    sb.append("")
    sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedPuzzle>> = ALL_PUZZLES.groupBy { it.difficulty }")
    sb.append("")
    sb.append("    fun getPuzzleById(id: String): PregeneratedPuzzle? = ALL_PUZZLES.find { it.id == id }")
    sb.append("")
    sb.append("    fun getRandomPuzzle(difficulty: FlowDifficulty): PregeneratedPuzzle? {")
    sb.append("        val diffName = when(difficulty) {")
    sb.append('            FlowDifficulty.EASY -> "Easy"')
    sb.append('            FlowDifficulty.MEDIUM -> "Medium"')
    sb.append('            FlowDifficulty.HARD -> "Hard"')
    sb.append('            FlowDifficulty.EXPERT -> "Expert"')
    sb.append("        }")
    sb.append("        return PUZZLES_BY_DIFFICULTY[diffName]?.randomOrNull()")
    sb.append("    }")
    sb.append("}")
    sb.append("")
    
    with open(target, 'w') as f:
        f.write("\n".join(sb))
    
    print(f"Baked {total} puzzles into {target}")

if __name__ == '__main__':
    main()
