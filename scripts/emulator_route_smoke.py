"""Smoke-check PuzzleVerse menu routes on an already running Android emulator.

This checks screen launches only. It does not prove puzzle completion, gestures,
replay dialogs, or UTC rollover. Run after assembleDebug and adb install -r.
"""

import json
import os
import re
import subprocess
import sys
import time
import xml.etree.ElementTree as ET
from pathlib import Path


SDK = Path(os.environ.get("ANDROID_HOME") or Path(os.environ["LOCALAPPDATA"]) / "Android" / "Sdk")
ADB = SDK / "platform-tools" / "adb.exe"
DEVICE = os.environ.get("PUZZLEVERSE_DEVICE", "emulator-5554")
PACKAGE = "com.funkyotc.puzzleverse"
GAMES = [
    ("sudoku", "Sudoku", True), ("bonza", "Bonza", True),
    ("constellations", "Constellations", True), ("wordle", "Wordle", False),
    ("tfe", "2048", False), ("minesweeper", "Minesweeper", False),
    ("nonogram", "Nonogram", True), ("kakuro", "Kakuro", True),
    ("flowfree", "Flow Free", True), ("shikaku", "Shikaku", True),
    ("cubeshooter", "Cube Shooter", True), ("pullpin", "Pull the Pin", True),
    ("watersort", "Water Sort", True), ("woodnuts", "Wood Screws", True),
    ("hexasort", "Hexa Sort", True), ("hexastack", "Hexa Stack", True),
    ("chess", "Chess", True), ("hashi", "Hashi", True),
    ("arrowescape", "Arrow Escape", True), ("tangrams", "Tangrams", True),
]


def adb(*args, check=True):
    result = subprocess.run([str(ADB), "-s", DEVICE, *args], capture_output=True, timeout=30)
    if check and result.returncode:
        raise RuntimeError(result.stderr.decode(errors="replace"))
    return result.stdout


def screen():
    for _ in range(4):
        adb("shell", "uiautomator", "dump", "/sdcard/window.xml", check=False)
        try:
            root = ET.fromstring(adb("exec-out", "cat", "/sdcard/window.xml"))
            if root.find(".//node") is not None:
                if any("isn't responding" in node.get("text", "") for node in root.iter("node")):
                    raise RuntimeError("Android ANR dialog for PuzzleVerse")
                return root
        except ET.ParseError:
            pass
        time.sleep(0.5)
    raise RuntimeError("Could not read the emulator UI hierarchy")


def labels(root):
    return [node.get("text", "") for node in root.iter("node") if node.get("text")]


def tap_node(node):
    match = re.fullmatch(r"\[(\d+),(\d+)\]\[(\d+),(\d+)\]", node.get("bounds", ""))
    if not match:
        raise RuntimeError("UI node has no bounds")
    left, top, right, bottom = map(int, match.groups())
    adb("shell", "input", "tap", str((left + right) // 2), str((top + bottom) // 2))
    time.sleep(0.35)


def tap_text(root, text, *, last=False, contains=False):
    matches = [node for node in root.iter("node") if
               (text in node.get("text", "") if contains else node.get("text") == text)]
    if not matches:
        raise RuntimeError(f"Cannot find {text!r}; visible labels: {labels(root)[:20]}")
    tap_node(matches[-1] if last else matches[0])


def verify_game(root, game_id):
    if not any(node.get("package") == PACKAGE for node in root.iter("node")):
        raise RuntimeError("App is no longer foreground")
    if any("Game Screen:" in label for label in labels(root)):
        raise RuntimeError(f"Placeholder opened for {game_id}")
    if "Daily Challenge" in labels(root):
        raise RuntimeError(f"Detail menu remained open for {game_id}")


def wait_for_game(game_id):
    for _ in range(4):
        root = screen()
        if "Daily Challenge" not in labels(root):
            verify_game(root, game_id)
            return
        time.sleep(0.5)
    raise RuntimeError(f"Detail menu remained open for {game_id}")


def wait_for_detail():
    for _ in range(4):
        root = screen()
        if "Daily Challenge" in labels(root):
            return root
        time.sleep(0.5)
    raise RuntimeError("Could not return to detail menu")


def open_detail(name):
    adb("shell", "am", "force-stop", PACKAGE)
    adb("shell", "am", "start", "-n", f"{PACKAGE}/.MainActivity")
    time.sleep(1.5)
    # Android preserves the Home list position across Activity restarts. Return
    # to its top before scanning downward, including when the previous game
    # was near the end of the catalog.
    for _ in range(4):
        adb("shell", "input", "swipe", "520", "590", "520", "1840", "180")
    for _ in range(16):
        root = screen()
        nodes = list(root.iter("node"))
        heading = next((i for i, node in enumerate(nodes) if node.get("text") == "All Puzzles"), -1)
        matching = [node for i, node in enumerate(nodes) if node.get("text") == name and
                    (name not in ("Sudoku", "Bonza", "Constellations") or heading >= 0 and i > heading)]
        if matching:
            if os.environ.get("PUZZLEVERSE_DEBUG"):
                print("Tap", name, matching[-1].get("bounds"), flush=True)
            tap_node(matching[-1])
            for _ in range(3):
                detail = screen()
                if os.environ.get("PUZZLEVERSE_DEBUG"):
                    print("After tap", labels(detail)[:12], flush=True)
                if "Daily Challenge" in labels(detail):
                    return detail
                if "Clear" in labels(detail) and any(label.startswith("Resume ") for label in labels(detail)):
                    if os.environ.get("PUZZLEVERSE_CLEAR_TEST_SAVES") != "1":
                        raise RuntimeError(f"Saved-game prompt for {name}; set PUZZLEVERSE_CLEAR_TEST_SAVES=1 on a disposable test device")
                    # Prior smoke runs can leave a Daily save. Home asks before
                    # opening detail; explicitly clear only disposable test data.
                    tap_text(detail, "Clear")
                    return wait_for_detail()
                time.sleep(0.4)
            adb("shell", "input", "keyevent", "4")
        adb("shell", "input", "swipe", "520", "1840", "520", "590", "290")
    raise RuntimeError(f"Could not open detail menu for {name}")


def check_game(game_id, name, has_browser):
    result = {"game": game_id, "detail": False, "daily": False,
              "standard": False, "browser": None}
    detail = open_detail(name)
    if name not in labels(detail):
        raise RuntimeError(f"Detail title mismatch for {game_id}: {labels(detail)[:12]}")
    result["detail"] = True
    tap_text(detail, "Daily Challenge")
    time.sleep(0.8)
    wait_for_game(game_id)
    result["daily"] = True
    adb("shell", "input", "keyevent", "4")
    detail = wait_for_detail()
    if game_id in ("minesweeper", "cubeshooter"):
        tap_text(detail, "Easy", contains=True)
    elif "Play Standard" in labels(detail):
        tap_text(detail, "Play Standard")
    elif "New Game" in labels(detail):
        tap_text(detail, "New Game")
    else:
        raise RuntimeError(f"No Standard launch on {game_id} detail menu")
    time.sleep(0.8)
    wait_for_game(game_id)
    result["standard"] = True
    adb("shell", "input", "keyevent", "4")
    detail = wait_for_detail()
    if has_browser:
        tap_text(detail, "Browse Puzzles")
        browser = screen()
        if not any(node.get("package") == PACKAGE for node in browser.iter("node")):
            raise RuntimeError(f"{game_id} browser left the app")
        if game_id == "tangrams":
            browser_open = any("Easy (" in label for label in labels(browser))
        else:
            browser_open = any("Puzzles" in label for label in labels(browser))
        if not browser_open:
            raise RuntimeError(f"{game_id} browser title missing: {labels(browser)[:20]}")
        result["browser"] = True
    return result


def main():
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    results = []
    report = Path("app/build/reports/emulator-route-smoke.json")
    report.parent.mkdir(parents=True, exist_ok=True)
    selected = set(filter(None, os.environ.get("PUZZLEVERSE_GAMES", "").split(",")))
    for game_id, name, has_browser in GAMES:
        if selected and game_id not in selected:
            continue
        try:
            outcome = check_game(game_id, name, has_browser)
            print(f"PASS {game_id}: detail, daily, standard" + (", browser" if has_browser else ""), flush=True)
        except Exception as error:
            outcome = {"game": game_id, "error": str(error)}
            print(f"FAIL {game_id}: {error}", flush=True)
        results.append(outcome)
        report.write_text(json.dumps(results, indent=2), encoding="utf-8")
    # UI hierarchy snapshots can race a Home scroll or transition. Retry a
    # failed route once from a fresh Activity before reporting it as a failure.
    for index, outcome in enumerate(results):
        if "error" not in outcome:
            continue
        game_id = outcome["game"]
        _, name, has_browser = next(game for game in GAMES if game[0] == game_id)
        try:
            results[index] = check_game(game_id, name, has_browser)
            print(f"PASS {game_id} on retry: detail, daily, standard" +
                  (", browser" if has_browser else ""), flush=True)
        except Exception as error:
            results[index] = {"game": game_id, "error": str(error)}
            print(f"FAIL {game_id} on retry: {error}", flush=True)
        report.write_text(json.dumps(results, indent=2), encoding="utf-8")
    print(f"Report: {report}")
    raise SystemExit(1 if any("error" in result for result in results) else 0)


if __name__ == "__main__":
    main()
