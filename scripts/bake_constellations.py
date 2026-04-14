import os
import json
import glob

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    
    target = os.path.join(script_dir, "..", "app", "src", "main", "java",
                          "com", "funkyotc", "puzzleverse", "constellations", "data",
                          "ConstellationsPregenerated.kt")
    target = os.path.normpath(target)
    
    all_puzzles = []
    
    for folder in sorted(os.listdir(output_dir)):
        if not folder.startswith("Constellations_"):
            continue
        folder_path = os.path.join(output_dir, folder)
        if not os.path.isdir(folder_path):
            continue
            
        diff_name = folder.replace("Constellations_", "")
        
        json_files = sorted(glob.glob(os.path.join(folder_path, "puzzle_*.json")))
        for jf in json_files:
            with open(jf, 'r') as f:
                data = json.load(f)
            
            puzzle_id = f"Constellations_{diff_name}_{os.path.splitext(os.path.basename(jf))[0]}"
            
            # format regions
            regions_kt = []
            for region_id, coords in data["regions"].items():
                pairs = ", ".join([f"{r} to {c}" for r, c in coords])
                regions_kt.append(f"{region_id} to listOf({pairs})")
            
            regions_str = "mapOf(\n                    " + ",\n                    ".join(regions_kt) + "\n                )"
            
            solution_pairs = ", ".join([f"{r} to {c}" for r, c in data["solution"]])
            solution_str = f"listOf({solution_pairs})"
            
            all_puzzles.append({
                "id": puzzle_id,
                "difficulty": diff_name,
                "size": data["size"],
                "regions_str": regions_str,
                "solution_str": solution_str
            })
            
    sb = []
    sb.append("package com.funkyotc.puzzleverse.constellations.data")
    sb.append("")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.append("")
    sb.append("data class PregeneratedConstellation(")
    sb.append("    override val id: String,")
    sb.append("    override val difficulty: String,")
    sb.append("    val size: Int,")
    sb.append("    val regions: Map<Int, List<Pair<Int, Int>>>,")
    sb.append("    val solution: List<Pair<Int, Int>>")
    sb.append(") : BrowseablePuzzle {")
    sb.append('    override val label: String get() = "Level ${id.takeLast(3)}"')
    sb.append('    override val subtitle: String get() = "${size}x${size}"')
    sb.append("    ")
    sb.append("    fun toConstellationsPuzzle(): ConstellationsPuzzle {")
    sb.append("        val grid = List(size) { r -> List(size) { c ->")
    sb.append("            // Find which region contains this (r,c)")
    sb.append("            val regionId = regions.entries.find { it.value.contains(r to c) }?.key ?: 0")
    sb.append("            Cell(row = r, col = c, regionId = regionId)")
    sb.append("        }}")
    sb.append("        return ConstellationsPuzzle(size, grid, regions, solution)")
    sb.append("    }")
    sb.append("}")
    sb.append("")
    sb.append("object ConstellationsPregenerated {")
    sb.append("    val ALL_PUZZLES: List<PregeneratedConstellation> by lazy {")
    sb.append("        listOf(")
    
    for p in all_puzzles:
        sb.append(f'            PregeneratedConstellation(')
        sb.append(f'                id = "{p["id"]}",')
        sb.append(f'                difficulty = "{p["difficulty"]}",')
        sb.append(f'                size = {p["size"]},')
        sb.append(f'                regions = {p["regions_str"]},')
        sb.append(f'                solution = {p["solution_str"]}')
        sb.append(f'            ),')
        
    sb.append("        )")
    sb.append("    }")
    sb.append("")
    sb.append("    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedConstellation>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }")
    sb.append("")
    sb.append("    fun getPuzzleById(id: String): PregeneratedConstellation? = ALL_PUZZLES.find { it.id == id }")
    sb.append("}")
    sb.append("")
    
    with open(target, 'w') as f:
        f.write("\n".join(sb))
        
    print(f"Baked {len(all_puzzles)} Constellations puzzles into {target}")

if __name__ == '__main__':
    main()
