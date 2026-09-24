"""Validate the authored Pull the Pin campaign using the shipped JVM physics.

The old generator could overwrite the campaign with unproved straight drops.
Edit PullPinPregenerated.kt to author levels, then run this verification gate.
"""
import os
from pathlib import Path
import subprocess

if __name__ == "__main__":
    root = Path(__file__).resolve().parents[1]
    wrapper = root / ("gradlew.bat" if os.name == "nt" else "gradlew")
    raise SystemExit(subprocess.call([str(wrapper), "testDebugUnitTest", "--tests",
        "com.funkyotc.puzzleverse.pullpin.PullPinCampaignTest", "--console=plain"], cwd=root))
