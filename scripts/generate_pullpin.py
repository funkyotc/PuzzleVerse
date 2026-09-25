"""Validate the checked-in rescue campaign's timed traces without writing level data."""
import os
from pathlib import Path
import subprocess

if __name__ == "__main__":
    root = Path(__file__).resolve().parents[1]
    wrapper = root / ("gradlew.bat" if os.name == "nt" else "gradlew")
    raise SystemExit(subprocess.call([str(wrapper), "testDebugUnitTest", "--tests",
        "com.funkyotc.puzzleverse.pullpin.RescueCampaignTest", "--tests",
        "com.funkyotc.puzzleverse.pullpin.RescueExpansionTest", "--console=plain"], cwd=root))
