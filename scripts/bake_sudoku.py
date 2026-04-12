"""
Convert generated Sudoku JSON puzzles into SudokuPregenerated.kt
Splits data into multiple functions to avoid JVM method size limits.
Run: python scripts/bake_sudoku.py
"""
import os
import json
import glob


def grid_to_string(grid):
    """Flatten a 9x9 grid to 81-char string of digits."""
    return "".join(str(cell) for row in grid for cell in row)


def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")

    target = os.path.join(script_dir, "..", "app", "src", "main", "java",
                          "com", "funkyotc", "puzzleverse", "sudoku", "data",
                          "SudokuPregenerated.kt")
    target = os.path.normpath(target)

    all_puzzles = []

    for folder in sorted(os.listdir(output_dir)):
        if not folder.startswith("Sudoku_"):
            continue
        folder_path = os.path.join(output_dir, folder)
        if not os.path.isdir(folder_path):
            continue

        diff_name = folder.replace("Sudoku_", "")

        json_files = sorted(glob.glob(os.path.join(folder_path, "puzzle_*.json")))
        for jf in json_files:
            with open(jf, 'r') as f:
                data = json.load(f)
            puzzle_id = f"Sudoku_{diff_name}_{os.path.splitext(os.path.basename(jf))[0]}"
            all_puzzles.append({
                "id": puzzle_id,
                "difficulty": diff_name,
                "clues": data["clues"],
                "puzzle": grid_to_string(data["puzzle"]),
                "solution": grid_to_string(data["solution"])
            })

    sb = []
    sb.append("package com.funkyotc.puzzleverse.sudoku.data\n")
    sb.append("")
    sb.append("data class PregeneratedSudoku(")
    sb.append("    val id: String,")
    sb.append("    val difficulty: String,")
    sb.append("    val clues: Int,")
    sb.append("    val puzzleStr: String,")
    sb.append("    val solutionStr: String")
    sb.append(") {")
    sb.append("    val puzzle: List<List<Int>> get() = puzzleStr.map { it.digitToInt() }.chunked(9)")
    sb.append("    val solution: List<List<Int>> get() = solutionStr.map { it.digitToInt() }.chunked(9)")
    sb.append("}")
    sb.append("")
    sb.append("object SudokuPregenerated {")
    sb.append("")
    sb.append("    val ALL_PUZZLES: List<PregeneratedSudoku> by lazy {")
    sb.append("        listOf(")

    for p in all_puzzles:
        sb.append(f'            PregeneratedSudoku("{p["id"]}", "{p["difficulty"]}", {p["clues"]}, "{p["puzzle"]}", "{p["solution"]}"),')

    sb.append("        )")
    sb.append("    }")
    sb.append("")
    sb.append('    val PUZZLES_BY_DIFFICULTY: Map<String, List<PregeneratedSudoku>> by lazy { ALL_PUZZLES.groupBy { it.difficulty } }')
    sb.append("")
    sb.append("    fun getPuzzleById(id: String): PregeneratedSudoku? = ALL_PUZZLES.find { it.id == id }")
    sb.append("")
    sb.append("    fun getRandomPuzzle(difficulty: String): PregeneratedSudoku? {")
    sb.append("        return PUZZLES_BY_DIFFICULTY[difficulty]?.randomOrNull()")
    sb.append("    }")
    sb.append("}")
    sb.append("")

    with open(target, 'w') as f:
        f.write("\n".join(sb))

    print(f"Baked {len(all_puzzles)} Sudoku puzzles into {target}")


if __name__ == '__main__':
    main()
