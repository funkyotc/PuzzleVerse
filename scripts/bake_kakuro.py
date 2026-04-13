import os
import json
import glob

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    input_dir = os.path.join(script_dir, "output", "kakuro")
    target = os.path.join(script_dir, "..", "app", "src", "main", "java",
                          "com", "funkyotc", "puzzleverse", "kakuro", "data",
                          "KakuroPregenerated.kt")
    target = os.path.normpath(target)

    all_puzzles = []

    if not os.path.exists(input_dir):
        print("No Kakuro output found.")
        return

    json_files = sorted(glob.glob(os.path.join(input_dir, "*.json")))
    for jf in json_files:
        with open(jf, 'r') as f:
            data = json.load(f)
            
        base_name = os.path.splitext(os.path.basename(jf))[0]
        puzzle_id = f"kakuro_{base_name.lower()}"
        
        diff = data["difficulty"]
        size = data["size"]
        grid = data["grid"]
        
        all_puzzles.append({
            "id": puzzle_id,
            "difficulty": diff,
            "rows": size,
            "cols": size,
            "grid": grid
        })

    sb = []
    sb.append("package com.funkyotc.puzzleverse.kakuro.data")
    sb.append("")
    sb.append("data class PregeneratedKakuro(")
    sb.append("    val id: String,")
    sb.append("    val difficulty: String,")
    sb.append("    val rows: Int,")
    sb.append("    val cols: Int,")
    sb.append("    val grid: List<List<KakuroCell>>")
    sb.append(")")
    sb.append("")
    sb.append("object KakuroPregenerated {")
    sb.append("")
    sb.append("    val ALL_PUZZLES: List<PregeneratedKakuro> by lazy {")
    sb.append("        listOf(")
    
    for p in all_puzzles:
        sb.append(f'            PregeneratedKakuro("{p["id"]}", "{p["difficulty"]}", {p["rows"]}, {p["cols"]}, listOf(')
        for r_idx, row in enumerate(p["grid"]):
            sb.append(f'                listOf(')
            cell_strs = []
            for cell in row:
                t = cell["type"]
                cr = cell["row"]
                cc = cell["col"]
                if t == "BLACK":
                    cell_strs.append(f'KakuroCell(CellType.BLACK, null, null, {cr}, {cc})')
                elif t == "CLUE":
                    cv = cell["clue"]["v"]
                    ch = cell["clue"]["h"]
                    v_str = str(cv) if cv is not None else "null"
                    h_str = str(ch) if ch is not None else "null"
                    cell_strs.append(f'KakuroCell(CellType.CLUE, Clue({h_str}, {v_str}), null, {cr}, {cc})')
                else:
                    cv = cell["val"]
                    cell_strs.append(f'KakuroCell(CellType.PLAYER_INPUT, null, null, {cr}, {cc})') # Not saving the answer directly into player val
            sb.append("                    " + ", ".join(cell_strs))
            sb.append("                )" + ("," if r_idx < len(p["grid"]) - 1 else ""))
        sb.append("            )),")
        
    sb.append("        )")
    sb.append("    }")
    sb.append("")
    sb.append('    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedKakuro>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }')
    sb.append("}")
    sb.append("")

    with open(target, 'w') as f:
        f.write("\n".join(sb))

    print(f"Baked {len(all_puzzles)} Kakuro puzzles into {target}")

if __name__ == '__main__':
    main()
