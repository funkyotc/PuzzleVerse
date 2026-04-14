import json
import os
import random

PUZZLE_DATA = {
    "Animals": [
        {"words": ["DOG", "CAT", "MOUSE", "HORSE"]},
        {"words": ["LION", "TIGER", "BEAR", "WOLF"]},
        {"words": ["EAGLE", "HAWK", "OWL", "FALCON"]}
    ],
    "Colors": [
        {"words": ["RED", "BLUE", "GREEN", "YELLOW"]},
        {"words": ["PURPLE", "ORANGE", "PINK", "BROWN"]},
        {"words": ["CYAN", "MAGENTA", "TEAL", "MAROON"]}
    ],
    "Space": [
        {"words": ["SUN", "MOON", "STAR", "PLANET"]},
        {"words": ["MARS", "VENUS", "EARTH", "JUPITER"]},
        {"words": ["COMET", "METEOR", "ASTEROID", "GALAXY"]}
    ]
}

def build_crossword(words):
    # A very naive crossword builder for small sets of words
    grid = {} # (x, y) -> char
    word_placements = []
    
    # Place first word horizontally
    first_word = words[0]
    for i, c in enumerate(first_word):
        grid[(i, 0)] = c
    word_placements.append({"word": first_word, "x": 0, "y": 0, "dir": "HORIZONTAL"})
    
    # Place other words
    for word in words[1:]:
        placed = False
        # Try finding intersections
        for placement in word_placements:
            if placed: break
            
            p_word = placement["word"]
            px, py = placement["x"], placement["y"]
            p_dir = placement["dir"]
            
            # Find common letters
            for p_idx, p_char in enumerate(p_word):
                if placed: break
                for w_idx, w_char in enumerate(word):
                    if p_char == w_char:
                        # Attempt to place
                        new_dir = "VERTICAL" if p_dir == "HORIZONTAL" else "HORIZONTAL"
                        if new_dir == "VERTICAL":
                            start_x = px + p_idx
                            start_y = py - w_idx
                        else:
                            start_x = px - w_idx
                            start_y = py + p_idx
                            
                        # Check collisions
                        collision = False
                        for i, cc in enumerate(word):
                            test_x = start_x + (i if new_dir == "HORIZONTAL" else 0)
                            test_y = start_y + (i if new_dir == "VERTICAL" else 0)
                            if (test_x, test_y) in grid and grid[(test_x, test_y)] != cc:
                                collision = True
                                break
                        
                        if not collision:
                            for i, cc in enumerate(word):
                                test_x = start_x + (i if new_dir == "HORIZONTAL" else 0)
                                test_y = start_y + (i if new_dir == "VERTICAL" else 0)
                                grid[(test_x, test_y)] = cc
                            word_placements.append({"word": word, "x": start_x, "y": start_y, "dir": new_dir})
                            placed = True
                            break
                        
        if not placed:
            # Fallback: Just place it somewhere below the current grid bounds
            max_y = max((y for x,y in grid.keys()), default=0) + 2
            for i, c in enumerate(word):
                grid[(i, max_y)] = c
            word_placements.append({"word": word, "x": 0, "y": max_y, "dir": "HORIZONTAL"})
            
    return word_placements

def chunk_words(word_placements):
    fragments = []
    idx = 0
    for wp in word_placements:
        word = wp["word"]
        x, y, d = wp["x"], wp["y"], wp["dir"]
        
        # Split word into chunks of 2-3 characters randomly
        i = 0
        while i < len(word):
            chunk_size = random.choice([2, 3])
            if i + chunk_size > len(word):
                chunk_size = len(word) - i
            
            chunk = word[i:i+chunk_size]
            cx = x + (i if d == "HORIZONTAL" else 0)
            cy = y + (i if d == "VERTICAL" else 0)
            
            fragments.append({
                "id": idx,
                "text": chunk,
                "solvedPosition": {"x": cx, "y": cy},
                "direction": d
            })
            idx += 1
            i += chunk_size
            
    # Normalize positions so minimum x and y is 0
    if fragments:
        min_x = min(f["solvedPosition"]["x"] for f in fragments)
        min_y = min(f["solvedPosition"]["y"] for f in fragments)
        for f in fragments:
            f["solvedPosition"]["x"] -= min_x
            f["solvedPosition"]["y"] -= min_y
            
    return fragments

def main():
    output_base = os.path.join(os.path.dirname(os.path.abspath(__file__)), "output")
    os.makedirs(output_base, exist_ok=True)
    
    print("Bonza Puzzle Generator")
    
    for theme, puzzle_list in PUZZLE_DATA.items():
        folder_name = f"Bonza_{theme}"
        folder_path = os.path.join(output_base, folder_name)
        os.makedirs(folder_path, exist_ok=True)
        
        for i, pd in enumerate(puzzle_list):
            words = pd["words"]
            placements = build_crossword(words)
            fragments = chunk_words(placements)
            
            # Scramble initial positions
            for f in fragments:
                f["initialPosition"] = {"x": random.randint(0, 10), "y": random.randint(0, 10)}
            
            data = {
                "theme": theme,
                "words": words,
                "fragments": fragments
            }
            
            filepath = os.path.join(folder_path, f"puzzle_{i+1:03d}.json")
            with open(filepath, 'w') as f:
                json.dump(data, f, indent=2)
                
            print(f"Generated {theme} puzzle {i+1}")

if __name__ == "__main__":
    main()
