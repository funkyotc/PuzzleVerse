"""Check Home IDs against both game dispatches and the expected browser routes."""

import re
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
home = (ROOT / "app/src/main/java/com/funkyotc/puzzleverse/ui/screens/home/HomeScreen.kt").read_text()
nav = (ROOT / "app/src/main/java/com/funkyotc/puzzleverse/MainActivity.kt").read_text()
game_ids = set(re.findall(r'Game\("([a-z]+)",', home.split("@OptIn")[0]))
dispatches = re.findall(r'when \(gameId\) \{(.*?)\n\s*else ->', nav, re.S)[:2]
if len(game_ids) != 20 or len(dispatches) != 2:
    raise SystemExit("Could not identify the Home catalog and both game dispatches")

failures = []
for label, block in zip(("game", "/new"), dispatches):
    handled = set(re.findall(r'"([a-z]+)"\s*->', block))
    missing = game_ids - handled
    if missing:
        failures.append(f"{label} missing: {', '.join(sorted(missing))}")

browser_ids = game_ids - {"wordle", "tfe", "minesweeper"}
browser_routes = set(re.findall(r'"([a-z]+)/puzzles\?difficulty=\{difficulty\}"', nav))
missing_browsers = browser_ids - browser_routes
if missing_browsers:
    failures.append(f"browser missing: {', '.join(sorted(missing_browsers))}")

if failures:
    raise SystemExit("\n".join(failures))
print(f"PASS: {len(game_ids)} standard routes, {len(game_ids)} /new routes, {len(browser_ids)} browsers")
