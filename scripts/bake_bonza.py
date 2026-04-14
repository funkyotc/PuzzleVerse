import os
import json
import glob

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, "output")
    
    target = os.path.join(script_dir, "..", "app", "src", "main", "java",
                          "com", "funkyotc", "puzzleverse", "bonza", "data",
                          "BonzaPregenerated.kt")
    target = os.path.normpath(target)
    
    all_puzzles = []
    
    for folder in sorted(os.listdir(output_dir)):
        if not folder.startswith("Bonza_"):
            continue
        folder_path = os.path.join(output_dir, folder)
        if not os.path.isdir(folder_path):
            continue
            
        theme_name = folder.replace("Bonza_", "")
        
        json_files = sorted(glob.glob(os.path.join(folder_path, "puzzle_*.json")))
        for jf in json_files:
            with open(jf, 'r') as f:
                data = json.load(f)
            
            puzzle_id = f"Bonza_{theme_name}_{os.path.splitext(os.path.basename(jf))[0]}"
            
            # Pack fragment data into a string format or just standard list representation
            # For simplicity in Kotlin we can instantiate it dynamically or represent it as data
            
            fragments_kt = []
            for frag in data["fragments"]:
                direction = "ConnectionDirection." + ("VERTICAL" if frag["direction"] == "VERTICAL" else "HORIZONTAL")
                fragments_kt.append(
                    f'WordFragment(id={frag["id"]}, text="{frag["text"]}", initialPosition=androidx.compose.ui.geometry.Offset({frag["initialPosition"]["x"]}f, {frag["initialPosition"]["y"]}f), currentPosition=androidx.compose.ui.geometry.Offset({frag["initialPosition"]["x"]}f, {frag["initialPosition"]["y"]}f), solvedPosition=androidx.compose.ui.geometry.Offset({frag["solvedPosition"]["x"]}f, {frag["solvedPosition"]["y"]}f), direction={direction})'
                )
            
            all_puzzles.append({
                "id": puzzle_id,
                "theme": theme_name,
                "words": data["words"],
                "fragments": fragments_kt
            })
            
    sb = []
    sb.append("package com.funkyotc.puzzleverse.bonza.data")
    sb.append("")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle")
    sb.append("")
    sb.append("data class PregeneratedBonza(")
    sb.append("    override val id: String,")
    sb.append("    val theme: String,")
    sb.append("    val words: List<String>,")
    sb.append("    val fragments: List<WordFragment>")
    sb.append(") : BrowseablePuzzle {")
    sb.append("    override val difficulty: String get() = theme")
    sb.append("    override val label: String get() = theme")
    sb.append('    override val subtitle: String get() = "${words.size} words"')
    sb.append("    ")
    sb.append("    fun toBonzaPuzzle() = BonzaPuzzle(")
    sb.append("        theme = theme,")
    sb.append("        words = words,")
    sb.append("        fragments = fragments,")
    sb.append("        connections = emptyList(),")
    sb.append("        solvedFragments = emptyList()")
    sb.append("    )")
    sb.append("}")
    sb.append("")
    sb.append("object BonzaPregenerated {")
    sb.append("    val ALL_PUZZLES: List<PregeneratedBonza> by lazy {")
    sb.append("        listOf(")
    
    for p in all_puzzles:
        sb.append(f'            PregeneratedBonza(')
        sb.append(f'                id = "{p["id"]}",')
        sb.append(f'                theme = "{p["theme"]}",')
        sb.append(f'                words = listOf({", ".join([f"{chr(34)}{w}{chr(34)}" for w in p["words"]])}),')
        sb.append(f'                fragments = listOf(')
        for frag in p["fragments"]:
            sb.append(f'                    {frag},')
        sb.append(f'                )')
        sb.append(f'            ),')
        
    sb.append("        )")
    sb.append("    }")
    sb.append("")
    sb.append("    val PUZZLES_BY_THEME: Map<String, List<PregeneratedBonza>> by lazy { ALL_PUZZLES.groupBy { it.theme } }")
    sb.append("")
    sb.append("    fun getPuzzleById(id: String): PregeneratedBonza? = ALL_PUZZLES.find { it.id == id }")
    sb.append("}")
    sb.append("")
    
    with open(target, 'w') as f:
        f.write("\n".join(sb))
        
    print(f"Baked {len(all_puzzles)} Bonza puzzles into {target}")

if __name__ == '__main__':
    main()
