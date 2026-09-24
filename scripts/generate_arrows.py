import random
import os

class Direction:
    UP = (0, -1)
    DOWN = (0, 1)
    LEFT = (-1, 0)
    RIGHT = (1, 0)

def opposite(d):
    if d == Direction.UP: return Direction.DOWN
    if d == Direction.DOWN: return Direction.UP
    if d == Direction.LEFT: return Direction.RIGHT
    if d == Direction.RIGHT: return Direction.LEFT

def to_string(d):
    if d == Direction.UP: return "UP"
    if d == Direction.DOWN: return "DOWN"
    if d == Direction.LEFT: return "LEFT"
    if d == Direction.RIGHT: return "RIGHT"

def generate(width, height, density, seed):
    rng = random.Random(seed)
    grid = [[0 for _ in range(width)] for _ in range(height)]
    arrows = []
    next_id = 1
    
    target_cells = int(width * height * density)
    filled_cells = 0
    colors = [1, 2, 3, 4, 5, 6, 7]

    attempts = 0
    max_attempts = target_cells * 20

    while filled_cells < target_cells and attempts < max_attempts:
        attempts += 1
        
        edge = rng.randint(0, 3)
        if edge == 0:
            spawn_dir = Direction.UP
            head_x = rng.randint(0, width - 1)
            head_y = height - 1
        elif edge == 1:
            spawn_dir = Direction.DOWN
            head_x = rng.randint(0, width - 1)
            head_y = 0
        elif edge == 2:
            spawn_dir = Direction.LEFT
            head_x = width - 1
            head_y = rng.randint(0, height - 1)
        else:
            spawn_dir = Direction.RIGHT
            head_x = 0
            head_y = rng.randint(0, height - 1)

        if grid[head_y][head_x] != 0:
            continue

        length = rng.randint(1, 5)
        segments = []
        current_x = head_x
        current_y = head_y
        current_dir = opposite(spawn_dir)
        
        segments.append((current_x, current_y))

        for i in range(1, length):
            if rng.random() < 0.3:
                turn_options = [d for d in [Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT] if d != current_dir and d != opposite(current_dir)]
                current_dir = rng.choice(turn_options)

            next_x = current_x + current_dir[0]
            next_y = current_y + current_dir[1]

            if next_x < 0 or next_x >= width or next_y < 0 or next_y >= height:
                break
            if grid[next_y][next_x] != 0:
                break
            if (next_x, next_y) in segments:
                break

            current_x = next_x
            current_y = next_y
            segments.append((current_x, current_y))

        arrow_id = next_id
        next_id += 1
        for x, y in segments:
            grid[y][x] = arrow_id
            filled_cells += 1
        
        arrows.append({
            "id": arrow_id,
            "segments": segments,
            "direction": spawn_dir,
            "color": rng.choice(colors)
        })

    return arrows

def main():
    sb = []
    sb.append("package com.funkyotc.puzzleverse.arrowescape.data\n")
    sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Arrow\n")
    sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Coordinate\n")
    sb.append("import com.funkyotc.puzzleverse.arrowescape.model.Direction\n\n")
    sb.append("object ArrowEscapePregenerated {\n")
    
    sizes = [10, 20, 30]
    difficulties = ["Easy", "Medium", "Hard"]
    
    sb.append("    val PUZZLES_BY_DIFFICULTY = mapOf(\n")
    
    for i in range(len(sizes)):
        size = sizes[i]
        diff = difficulties[i]
        
        sb.append(f'        "{diff}" to listOf(\n')
        for j in range(5):
            arrows = generate(size, size, 0.4, 42 + i*10 + j)
            sb.append("            listOf(\n")
            for arrow in arrows:
                seg_str = ", ".join([f"Coordinate({x}, {y})" for x, y in arrow["segments"]])
                dir_str = to_string(arrow["direction"])
                sb.append(f'                Arrow({arrow["id"]}, listOf({seg_str}), Direction.{dir_str}, {arrow["color"]}),\n')
            sb.append("            ),\n")
        sb.append("        ),\n")
    
    sb.append("    )\n")
    sb.append("}\n")
    
    os.makedirs("app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data", exist_ok=True)
    with open("app/src/main/java/com/funkyotc/puzzleverse/arrowescape/data/ArrowEscapePregenerated.kt", "w") as f:
        f.write("".join(sb))
        
    print("Generated puzzles.")

if __name__ == "__main__":
    main()
