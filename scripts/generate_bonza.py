import json
import os
import random
import networkx as nx

class BonzaGenerator:
    def __init__(self, theme, words):
        self.theme = theme
        self.words = [w.upper() for w in words]
        
    def build_layout(self):
        """Build a connected crossword layout using a graph-priority approach."""
        if not self.words: return []
        
        # Sort words by length (longer words first usually make better anchors)
        sorted_words = sorted(self.words, key=len, reverse=True)
        
        grid = {} # (x, y) -> char
        word_placements = []
        
        # Place first word
        first = sorted_words[0]
        for i, c in enumerate(first):
            grid[(i, 0)] = c
        word_placements.append({"word": first, "x": 0, "y": 0, "dir": "HORIZONTAL"})
        
        remaining = sorted_words[1:]
        
        # Iteratively try to add words that intersect with the current layout
        while remaining:
            best_placement = None
            best_word_idx = -1
            
            # Shuffling remaining to introduce variety
            random.shuffle(remaining)
            
            for idx, word in enumerate(remaining):
                # Try to find a valid intersection with any placed word
                placements = self.find_all_valid_placements(word, grid, word_placements)
                if placements:
                    best_placement = random.choice(placements)
                    best_word_idx = idx
                    break
            
            if best_placement:
                word = remaining.pop(best_word_idx)
                word_placements.append(best_placement)
                # Update grid
                x, y, d = best_placement["x"], best_placement["y"], best_placement["dir"]
                for i, c in enumerate(word):
                    gx = x + (i if d == "HORIZONTAL" else 0)
                    gy = y + (i if d == "VERTICAL" else 0)
                    grid[(gx, gy)] = c
            else:
                # If no word can intersect, stop or force a secondary cluster (not ideal for Bonza)
                # For Bonza, we want one big cluster. Let's just discard remaining words.
                break
                
        return word_placements

    def find_all_valid_placements(self, word, grid, existing_placements):
        valid = []
        for ep in existing_placements:
            e_word = ep["word"]
            ex, ey = ep["x"], ep["y"]
            edir = ep["dir"]
            
            new_dir = "VERTICAL" if edir == "HORIZONTAL" else "HORIZONTAL"
            
            for ei, ec in enumerate(e_word):
                for wi, wc in enumerate(word):
                    if ec == wc:
                        # Shared char found, calculate start pos for new word
                        if edir == "HORIZONTAL":
                            nx = ex + ei
                            ny = ey - wi
                        else:
                            nx = ex - wi
                            ny = ey + ei
                            
                        if self.can_place(word, nx, ny, new_dir, grid):
                            valid.append({"word": word, "x": nx, "y": ny, "dir": new_dir})
        return valid

    def can_place(self, word, x, y, direction, grid):
        # 1. Check for basic collisions and mandatory gaps
        for i, char in enumerate(word):
            nx = x + (i if direction == "HORIZONTAL" else 0)
            ny = y + (i if direction == "VERTICAL" else 0)
            
            # Check if cell is occupied by a different letter
            if (nx, ny) in grid and grid[(nx, ny)] != char:
                return False
            
            # Check neighbors to ensure no accidental word formation (crossword rules)
            neighbors = [(nx+1, ny), (nx-1, ny), (nx, ny+1), (nx, ny-1)]
            for mx, my in neighbors:
                # If it's a neighbor not part of the current word being placed
                # and NOT the character we are intersecting with, it must be empty
                if (mx, my) in grid and grid[(mx, my)] != char:
                    # Check if this cell is actually part of the word we are intersecting
                    # (Simplified: just allow intersections but prevent parallel adjacent words)
                    pass

        # 2. Prevent parallel touching words (very strict crossword layout)
        for i in range(-1, len(word) + 1):
            for side in [-1, 1]:
                if direction == "HORIZONTAL":
                    sx, sy = x + i, y + side
                else:
                    sx, sy = x + side, y + i
                if (sx, sy) in grid: return False
                
        return True

    def chunk_words(self, placements):
        fragments = []
        fid = 0
        for p in placements:
            word = p["word"]
            x, y, d = p["x"], p["y"], p["dir"]
            
            i = 0
            while i < len(word):
                csize = random.choice([2, 3])
                if i + csize > len(word): csize = len(word) - i
                
                chunk = word[i:i+csize]
                cx = x + (i if d == "HORIZONTAL" else 0)
                cy = y + (i if d == "VERTICAL" else 0)
                
                fragments.append({
                    "id": fid,
                    "text": chunk,
                    "solvedPosition": {"x": cx, "y": cy},
                    "direction": d
                })
                fid += 1
                i += csize
        
        # Center the puzzle
        if fragments:
            mx = min(f["solvedPosition"]["x"] for f in fragments)
            my = min(f["solvedPosition"]["y"] for f in fragments)
            for f in fragments:
                f["solvedPosition"]["x"] -= mx
                f["solvedPosition"]["y"] -= my
                
        return fragments

def main():
    themes = {
        "Nature": ["Forest", "Mountain", "River", "Ocean", "Desert", "Valley", "Island", "Canyon"],
        "Technology": ["Computer", "Internet", "Software", "Hardware", "Database", "Network", "Algorithm", "Security"],
        "Fruit": ["Apple", "Banana", "Cherry", "Durian", "Elderberry", "Fig", "Grape", "Honeydew"],
        "Cities": ["London", "Paris", "Tokyo", "NewYork", "Berlin", "Sydney", "Mumbai", "Cairo"]
    }
    
    output_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), "output")
    
    for theme, words in themes.items():
        folder = os.path.join(output_dir, f"Bonza_{theme}")
        os.makedirs(folder, exist_ok=True)
        
        for i in range(5): # Generate 5 puzzles per theme
            random.shuffle(words)
            puzzle_words = words[:random.randint(4, 6)]
            gen = BonzaGenerator(theme, puzzle_words)
            layout = gen.build_layout()
            fragments = gen.chunk_words(layout)
            
            # Scramble initial layout
            for f in fragments:
                f["initialPosition"] = {"x": random.randint(0, 10), "y": random.randint(0, 10)}
            
            data = {
                "theme": theme,
                "words": [l["word"] for l in layout],
                "fragments": fragments
            }
            
            with open(os.path.join(folder, f"puzzle_{i+1:03d}.json"), "w") as f:
                json.dump(data, f, indent=2)
            print(f"Generated {theme} puzzle {i+1}")

if __name__ == "__main__":
    main()
