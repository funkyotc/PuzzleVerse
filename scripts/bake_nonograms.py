import os
import json
import glob

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    input_dir = os.path.join(script_dir, "output", "nonogram")
    target = os.path.join(script_dir, "..", "app", "src", "main", "java",
                          "com", "funkyotc", "puzzleverse", "nonogram", "data",
                          "NonogramPregenerated.kt")
    target = os.path.normpath(target)

    all_puzzles = []

    if not os.path.exists(input_dir):
        print("No Nonogram output found.")
        return

    json_files = sorted(glob.glob(os.path.join(input_dir, "*.json")))
    for jf in json_files:
        with open(jf, 'r') as f:
            data = json.load(f)
        all_puzzles.append(data)

    sb = []
    sb.append("package com.funkyotc.puzzleverse.nonogram.data")
    sb.append("")
    sb.append("data class PregeneratedNonogram(")
    sb.append("    val id: String,")
    sb.append("    val difficulty: String,")
    sb.append("    val size: Int,")
    sb.append("    val rowClues: List<List<Int>>,")
    sb.append("    val colClues: List<List<Int>>,")
    sb.append("    val gridStr: String")
    sb.append(") {")
    sb.append("    val grid: List<List<Boolean>> get() = gridStr.map { it == '1' }.chunked(size)")
    sb.append("}")
    sb.append("")
    sb.append("object NonogramPregenerated {")
    sb.append("")
    sb.append("    val ALL_PUZZLES: List<PregeneratedNonogram> by lazy {")
    sb.append("        listOf(")
    
    for p in all_puzzles:
        id_str = p["id"]
        diff = p["difficulty"]
        sz = p["size"]
        gs = p["grid_str"]
        
        r_clues_str = "listOf(" + ", ".join("listOf(" + ", ".join(str(c) for c in rc) + ")" for rc in p["row_clues"]) + ")"
        c_clues_str = "listOf(" + ", ".join("listOf(" + ", ".join(str(c) for c in cc) + ")" for cc in p["col_clues"]) + ")"
        
        sb.append(f'            PregeneratedNonogram("{id_str}", "{diff}", {sz}, {r_clues_str}, {c_clues_str}, "{gs}"),')
        
    sb.append("        )")
    sb.append("    }")
    sb.append("")
    sb.append('    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedNonogram>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }')
    sb.append("}")
    sb.append("")

    with open(target, 'w') as f:
        f.write("\n".join(sb))

    print(f"Baked {len(all_puzzles)} Nonogram puzzles into {target}")

if __name__ == '__main__':
    main()
