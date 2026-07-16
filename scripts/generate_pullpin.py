import os
import json
import random

WORLD_W = 400.0
WORLD_H = 700.0

FLOOR_Y = 680.0
FLOOR_H = 20.0

WALL_THICK = 12.0

DIFFICULTY_CONFIG = {
    "Easy":   {"n_cups": 2, "count": 12},
    "Medium": {"n_cups": 3, "count": 12},
    "Hard":   {"n_cups": 4, "count": 12},
    "Expert": {"n_cups": 5, "count": 12},
}

VALID_COLORS = list(range(1, 9))  # 1..8


def build_walls(n_cups, cup_xs):
    walls = []
    # Outer boundary
    walls.append({"x": 0.0, "y": FLOOR_Y, "w": WORLD_W, "h": FLOOR_H})          # floor
    walls.append({"x": 0.0, "y": 0.0, "w": WALL_THICK, "h": WORLD_H})           # left wall
    walls.append({"x": WORLD_W - WALL_THICK, "y": 0.0, "w": WALL_THICK, "h": WORLD_H})  # right wall
    walls.append({"x": 0.0, "y": 0.0, "w": WORLD_W, "h": WALL_THICK})           # top wall

    # Divider walls between adjacent cup columns, from y=300 up to the floor
    tops = [300.0]
    for i in range(len(cup_xs) - 1):
        mid_x = (cup_xs[i] + cup_xs[i + 1]) / 2.0
        top_y = tops[0]
        h = FLOOR_Y - top_y
        walls.append({"x": mid_x - 6.0, "y": top_y, "w": 12.0, "h": h})
    return walls


def gen_level(diff, idx, seed_val):
    rng = random.Random(seed_val)
    cfg = DIFFICULTY_CONFIG[diff]
    n_cups = cfg["n_cups"]

    # Horizontal placement of cups within the interior (between walls)
    left_bound = WALL_THICK + 24.0
    right_bound = WORLD_W - WALL_THICK - 24.0
    usable = right_bound - left_bound
    cup_xs = []
    for i in range(n_cups):
        if n_cups == 1:
            cx = (left_bound + right_bound) / 2.0
        else:
            frac = (i + 1) / (n_cups + 1)
            cx = left_bound + usable * frac
        cup_xs.append(cx)

    # Validate bounds
    for cx in cup_xs:
        if not (12.0 < cx < 388.0):
            return None

    walls = build_walls(n_cups, cup_xs)

    cups = []
    pins = []
    balls = []
    for i, cx in enumerate(cup_xs):
        color = VALID_COLORS[i % len(VALID_COLORS)]
        cup_id = f"cup_{i}"
        ball_id = f"ball_{i}"
        pin_id = f"pin_{i}"

        # Cup resting on the floor (center y so its base meets floor top)
        cup_radius = 22.0
        cup_cy = FLOOR_Y - WALL_THICK + 0.0  # sit just above floor
        cup_cy = FLOOR_Y - 4.0
        cups.append({"id": cup_id, "x": cx, "y": cup_cy, "radius": cup_radius, "color": color})

        # Ball directly above the cup
        ball_radius = 16.0
        ball_cy = 600.0
        balls.append({"id": ball_id, "x": cx, "y": ball_cy, "color": color, "radius": ball_radius})

        # Horizontal pin just below the ball, blocking it from falling
        pin_w = 80.0
        pin_h = 10.0
        pin_y = ball_cy + ball_radius + 2.0  # just under the ball
        pins.append({
            "id": pin_id,
            "x": cx - pin_w / 2.0,
            "y": pin_y,
            "w": pin_w,
            "h": pin_h,
            "pullDx": 1.0 if (i % 2 == 0) else -1.0,
            "pullDy": 0.0,
        })

    return {
        "id": f"pullpin_{diff.lower()}_{idx:03d}",
        "difficulty": diff,
        "walls": walls,
        "cups": cups,
        "pins": pins,
        "balls": balls,
    }


def verify_solvable(level):
    """Lightweight straight-down drop simulation.

    Each ball is held by exactly one pin directly below it. Removing that pin
    lets the ball fall straight down. We confirm the ball lands within its
    matching cup (centered at the same x). Since balls are placed directly
    above their cups, a straight drop lands correctly.
    """
    cups = {c["id"]: c for c in level["cups"]}
    # Map each ball to its cup by matching x center and color
    for ball in level["balls"]:
        # Find the matching-color cup whose x center is closest
        best = None
        best_dist = 1e9
        for cup in level["cups"]:
            if cup["color"] == ball["color"]:
                d = abs(cup["x"] - ball["x"])
                if d < best_dist:
                    best_dist = d
                    best = cup
        if best is None:
            return False
        # Straight drop: ball x must stay within cup x +- radius
        if best_dist > best["radius"]:
            return False
        # Ball must start above the cup and not already inside walls
        if ball["y"] >= best["y"]:
            return False

    # No ball should start overlapping a wall
    for wall in level["walls"]:
        for ball in level["balls"]:
            bx = ball["x"]
            by = ball["y"]
            r = ball["radius"]
            # circle-rect overlap (approx: check center +/- radius extremes)
            closest_x = max(wall["x"], min(bx, wall["x"] + wall["w"]))
            closest_y = max(wall["y"], min(by, wall["y"] + wall["h"]))
            dx = bx - closest_x
            dy = by - closest_y
            if dx * dx + dy * dy < r * r:
                # Ignore the floor/cup-area overlap (balls rest above floor)
                if wall["y"] < FLOOR_Y - 1.0:
                    return False
    return True


def kotlin_ball(b):
    return (f'BallSpawn("{b["id"]}", {b["x"]:.1f}f, {b["y"]:.1f}f, '
            f'{int(b["color"])}, {b["radius"]:.1f}f)')


def kotlin_cup(c):
    return (f'CupData("{c["id"]}", {c["x"]:.1f}f, {c["y"]:.1f}f, '
            f'{c["radius"]:.1f}f, {int(c["color"])})')


def kotlin_wall(w):
    return (f'WallSegment({w["x"]:.1f}f, {w["y"]:.1f}f, '
            f'{w["w"]:.1f}f, {w["h"]:.1f}f)')


def kotlin_pin(p):
    return (f'PinData("{p["id"]}", {p["x"]:.1f}f, {p["y"]:.1f}f, '
            f'{p["w"]:.1f}f, {p["h"]:.1f}f, '
            f'{p["pullDx"]:.1f}f, {p["pullDy"]:.1f}f, false, false)')


def level_to_kotlin(level):
    walls = ",\n            ".join(kotlin_wall(w) for w in level["walls"])
    cups = ",\n            ".join(kotlin_cup(c) for c in level["cups"])
    pins = ",\n            ".join(kotlin_pin(p) for p in level["pins"])
    balls = ",\n            ".join(kotlin_ball(b) for b in level["balls"])
    return (
        f'    PullPinLevel(\n'
        f'        id = "{level["id"]}",\n'
        f'        difficulty = "{level["difficulty"]}",\n'
        f'        walls = listOf(\n            {walls}\n        ),\n'
        f'        cups = listOf(\n            {cups}\n        ),\n'
        f'        pins = listOf(\n            {pins}\n        ),\n'
        f'        balls = listOf(\n            {balls}\n        )\n'
        f'    )'
    )


def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    out_dir = os.path.join(script_dir, "output", "pullpin")
    os.makedirs(out_dir, exist_ok=True)

    all_levels = []
    for diff, cfg in DIFFICULTY_CONFIG.items():
        target = cfg["count"]
        i = 0
        attempts = 0
        while i < target and attempts < target * 200:
            attempts += 1
            seed_val = (hash(("pullpin", diff, attempts)) & 0xFFFFFFFF)
            lvl = gen_level(diff, idx=i + 1, seed_val=seed_val)
            if lvl is None:
                continue
            if not verify_solvable(lvl):
                continue
            fname = f"pullpin_{diff.lower()}_{i + 1:03d}.json"
            with open(os.path.join(out_dir, fname), "w") as f:
                json.dump(lvl, f, indent=2)
            all_levels.append(lvl)
            i += 1
        print(f"Generated {i} {diff} levels")

    # Build baked Kotlin file (self-contained with data classes)
    level_calls = ",\n".join(level_to_kotlin(l) for l in all_levels)

    kotlin_src = f"""package com.funkyotc.puzzleverse.pullpin.data

data class WallSegment(val x: Float, val y: Float, val w: Float, val h: Float)
data class CupData(val id: String, val x: Float, val y: Float, val radius: Float, val color: Int)
data class PinData(
    val id: String,
    val x: Float,
    val y: Float,
    val w: Float,
    val h: Float,
    val pullDx: Float,
    val pullDy: Float,
    val removed: Boolean,
    val isPulling: Boolean
)
data class BallSpawn(val id: String, val x: Float, val y: Float, val color: Int, val radius: Float)
data class PullPinLevel(
    val id: String,
    val difficulty: String,
    val walls: List<WallSegment>,
    val cups: List<CupData>,
    val pins: List<PinData>,
    val balls: List<BallSpawn>
) {{
    val label: String get() = id.substringAfterLast('_')
}}

object PullPinPregenerated {{
    val ALL_LEVELS: List<PullPinLevel> = listOf(
{level_calls}
    )

    val PUZZLES_BY_DIFFICULTY: Map<String, List<PullPinLevel>> = ALL_LEVELS.groupBy {{ it.difficulty }}
}}
"""
    kotlin_path = os.path.join(out_dir, "PullPinPregenerated.kt")
    with open(kotlin_path, "w") as f:
        f.write(kotlin_src)
    print(f"Wrote Kotlin file: {kotlin_path}")
    print(f"Total levels: {len(all_levels)}")


if __name__ == "__main__":
    main()
