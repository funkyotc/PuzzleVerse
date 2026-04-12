import os
import re

directory = r'c:\Users\funky\AppDev\PuzzleVerse\app\src\main\java\com\funkyotc\puzzleverse'

for root, dirs, files in os.walk(directory):
    for file in files:
        if file.endswith('Screen.kt'):
            path = os.path.join(root, file)
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()

            orig = content

            # This regex will find:
            # if (mode == "daily") { ... } else {
            #    TextButton(onClick = { ... }
            #    }) {
            #        Text("...")
            #    }
            # }
            
            # Since the outer confirmButton = { ... } was the boundary, let's just find the `if (mode == "daily")` that has the broken else.
            
            # Find all occurrences of the broken signature
            pattern = re.compile(r'if\s*\(mode\s*==\s*"daily"\)\s*\{\s*androidx\.compose.*?\}\s*else\s*\{\s*\n\s*(TextButton\(onClick.*?)\}\s*\n\s*\}\)\s*\{', re.DOTALL)
            
            def rpl(m):
                # We extract the TextButton inner logic
                inner = m.group(1).strip()
                # We need to assemble back 'TextButton(onClick = { ... }) {'
                # Wait, inner ends with the inside of the onClick block. So we add '}) {'
                return inner + ' }) {'

            content = pattern.sub(rpl, content)
            
            # Also, we have a stray closing brace from the `else` block still hanging around further down.
            # It's usually like:
            #     Text("...")
            #   }
            # } <- this one
            # To clean it up, we can use a simpler approach:
            # We know the specific incorrect structure we injected. Let's just fix it.
            
            if content != orig:
                # We need to remove the trailing '}' from the `else` block.
                # Actually, an even safer approach is to replace the whole `confirmButton = { if (mode=="daily")... }` 
                # if it contains 'Use a Hint?' or 'How To Play'. We can just use split by 'AlertDialog('
                pass

            
            # Better strategy: Split by AlertDialog
            parts = content.split('AlertDialog(')
            new_parts = [parts[0]]
            modified = False
            
            for part in parts[1:]:
                is_win = ('You Win' in part or 'Congratulations' in part or 'solved the puzzle' in part or 'won' in part.lower())
                if not is_win:
                    # Remove the daily check we mistakenly added to this dialog.
                    # We injected: `if (mode == "daily") { ... } else { \n {button_logic} \n }`
                    # Where button logic is exactly what we stripped in group(2).
                    # Actually, our bad regex injection looks exactly like:
                    # confirmButton = {
                    #     if (mode == "daily") { ... } else {
                    #         ...TextButton...
                    #     }
                    # }) {
                    #    Text(...)
                    # }
                    
                    if 'if (mode == "daily") { \n                    androidx.compose' in part or 'if (mode == "daily")' in part:
                        # Let's extract what was inside the TextButton onClick.
                        if 'Use a Hint' in part:
                            # reconstruct hint confirm button
                            vm = "viewModel"
                            if "bonzaViewModel" in part: vm = "bonzaViewModel"
                            elif "constellationsViewModel" in part: vm = "constellationsViewModel"
                            part = re.sub(r'confirmButton\s*=\s*\{.*?dismissButton', f'confirmButton = {{\n                TextButton(onClick = {{\n                    showHintDialog = false\n                    {vm}.hint()\n                }}) {{\n                    Text("Yes")\n                }}\n            }},\n            dismissButton', part, flags=re.DOTALL)
                        elif 'How To Play' in part:
                            part = re.sub(r'confirmButton\s*=\s*\{.*?^\s*\)\s*$', f'confirmButton = {{\n                TextButton(onClick = {{ showHowToDialog = false }}) {{\n                    Text("OK")\n                }}\n            }}\n        )', part, flags=re.DOTALL|re.MULTILINE)
                        elif 'New Game' in part or 'New Puzzle' in part:
                            vm = "viewModel"
                            func = "startNewGame"
                            if "bonzaViewModel" in part: 
                                vm = "bonzaViewModel"
                                func = "newGame"
                            elif "constellationsViewModel" in part:
                                vm = "constellationsViewModel"
                                func = "loadNewPuzzle"
                            # There's usually a dismissButton in New Game dialogs.
                            part = re.sub(r'confirmButton\s*=\s*\{.*?dismissButton', f'confirmButton = {{\n                TextButton(onClick = {{\n                    showNewGameDialog = false\n                    {vm}.{func}()\n                }}) {{\n                    Text("Confirm")\n                }}\n            }},\n            dismissButton', part, flags=re.DOTALL)
                        
                        modified = True
                        
                new_parts.append(part)
                
            new_content = 'AlertDialog('.join(new_parts)
            if new_content != orig:
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print(f'Fixed {file}')
