import os
import json
import glob


def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    input_dir = os.path.join(script_dir, "output", "cubeshooter")
    target = os.path.join(
        script_dir,
        "..",
        "app",
        "src",
        "main",
        "java",
        "com",
        "funkyotc",
        "puzzleverse",
        "cubeshooter",
        "data",
        "CubeShooterPregenerated.kt",
    )
    target = os.path.normpath(target)

    all_levels = []

    if not os.path.exists(input_dir):
        print("No Cube Shooter output found.")
        return

    json_files = sorted(glob.glob(os.path.join(input_dir, "*.json")))
    for jf in json_files:
        with open(jf, "r") as f:
            data = json.load(f)
        all_levels.append(data)

    sb = []
    sb.append("package com.funkyotc.puzzleverse.cubeshooter.data")
    sb.append("")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.append("")
    sb.append("data class PregeneratedLevel(")
    sb.append("    override val id: String,")
    sb.append("    override val difficulty: String,")
    sb.append("    val cols: Int,")
    sb.append("    val rows: Int,")
    sb.append("    val grid: List<List<Int?>>,")
    sb.append("    val tray: List<Tank>")
    sb.append(") : BrowseablePuzzle {")
    sb.append("    override val label: String get() = id.substringAfterLast('_')")
    sb.append("    override val subtitle: String get() = \"${cols}x${rows}\"")
    sb.append("}")
    sb.append("")
    sb.append("object CubeShooterPregenerated {")
    sb.append("")

    # Chunk size of 1 level to safely avoid JVM MethodTooLargeException
    chunk_size = 1
    chunks_count = (len(all_levels) + chunk_size - 1) // chunk_size

    for chunk_idx in range(chunks_count):
        start = chunk_idx * chunk_size
        end = min(start + chunk_size, len(all_levels))
        chunk_levels = all_levels[start:end]

        sb.append(f"    private fun getChunk{chunk_idx}(): List<PregeneratedLevel> {{")
        sb.append("        return listOf(")

        for p in chunk_levels:
            sb.append(
                f'            PregeneratedLevel("{p["id"]}", "{p["difficulty"]}", '
                f'{p["cols"]}, {p["rows"]}, listOf('
            )
            rows = p["grid"]
            for r_idx, row in enumerate(rows):
                sb.append("                listOf(")
                cells = []
                for v in row:
                    cells.append("null" if v is None else str(v))
                sb.append("                    " + ", ".join(cells))
                sb.append(
                    "                )" + ("," if r_idx < len(rows) - 1 else "")
                )
            sb.append("            ), listOf(")
            tray = p["tray"]
            for t_idx, t in enumerate(tray):
                sb.append(
                    f'                Tank({t["color"]}, {t["ammo"]})'
                    + ("," if t_idx < len(tray) - 1 else "")
                )
            sb.append("            )),")

        sb.append("        )")
        sb.append("    }")
        sb.append("")

    sb.append("    val ALL_LEVELS: List<PregeneratedLevel> by lazy {")
    sb.append("        " + " + ".join(f"getChunk{i}()" for i in range(chunks_count)))
    sb.append("    }")
    sb.append("")
    sb.append(
        '    val LEVELS_BY_DIFFICULTY: Map<String, List<PregeneratedLevel>> by lazy { ALL_LEVELS.groupBy { it.difficulty } }'
    )
    sb.append("}")
    sb.append("")

    with open(target, "w", encoding="utf-8") as f:
        f.write("\n".join(sb))

    print(f"Baked {len(all_levels)} Cube Shooter levels in chunked methods into {target}")


if __name__ == "__main__":
    main()
