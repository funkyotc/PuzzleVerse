import os
import re

directory = r'c:\Users\funky\AppDev\PuzzleVerse\app\src\main\java\com\funkyotc\puzzleverse'

game_ids = {
    'SudokuScreen.kt': 'sudoku',
    'BonzaScreen.kt': 'bonza',
    'ConstellationsScreen.kt': 'constellations',
    'ShapesScreen.kt': 'shapes',
    'WordleScreen.kt': 'wordle',
    'TfeScreen.kt': 'tfe',
    'MinesweeperScreen.kt': 'minesweeper',
    'NonogramScreen.kt': 'nonogram',
    'BlockPuzzleScreen.kt': 'blockpuzzle',
    'KakuroScreen.kt': 'kakuro',
    'FlowFreeScreen.kt': 'flowfree'
}

for root, dirs, files in os.walk(directory):
    for file in files:
        if file in game_ids:
            game_id = game_ids[file]
            path = os.path.join(root, file)
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()

            pattern = re.compile(r'(confirmButton\s*=\s*\{)(.*?Button.*?)(^\s*\})', re.MULTILINE | re.DOTALL)
            
            def repl(match):
                prefix = match.group(1)
                button_logic = match.group(2)
                suffix = match.group(3)
                
                if 'mode == \"daily\"' in button_logic:
                    return match.group(0)
                if 'navController' not in content:
                    return match.group(0)
                
                new_logic = f'''
                if (mode == \"daily\") {{
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {{
                        androidx.compose.material3.Button(onClick = {{ navController.navigate(\"home\") {{ popUpTo(0) }} }}) {{
                            androidx.compose.material3.Text(\"Main Menu\")
                        }}
                        androidx.compose.material3.Button(onClick = {{ navController.navigate(\"game/{game_id}/standard/new\") {{ popUpTo(\"home\") }} }}) {{
                            androidx.compose.material3.Text(\"Random Puzzles\")
                        }}
                    }}
                }} else {{
{button_logic}
                }}
'''
                return prefix + new_logic + suffix

            parts = content.split('AlertDialog(')
            new_parts = [parts[0]]
            
            for part in parts[1:]:
                if 'You Win' in part or 'Congratulations' in part or 'solved the puzzle' in part or 'won' in part.lower():
                    new_part = pattern.sub(repl, part, count=1)
                    new_parts.append(new_part)
                else:
                    new_parts.append(part)
                    
            new_content = 'AlertDialog('.join(new_parts)
            
            if new_content != content:
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print(f'Updated {file}')
