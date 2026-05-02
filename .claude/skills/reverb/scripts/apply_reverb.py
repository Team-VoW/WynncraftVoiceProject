#!/usr/bin/env python3
"""Apply a reverb type to a numbered range of sound entries in sounds/sounds.json.

Usage: python apply_reverb.py <prefix> <start> <end> <reverb>
Example: python apply_reverb.py anewbeginning-syndra 1 38 BIG_CAVE
"""
import sys
import os

VALID_REVERBS = {
    "OUTSIDE", "FOREST", "FURNISHED_ROOM", "EMPTY_ROOM", "HALLWAY",
    "LARGE_HALL", "SMALL_CAVE", "CAVE", "BIG_CAVE", "CATHEDRAL",
}

SOUNDS_JSON = os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", "sounds", "sounds.json")


def main():
    if len(sys.argv) != 5:
        print("Usage: apply_reverb.py <prefix> <start> <end> <reverb>")
        print("Example: apply_reverb.py anewbeginning-syndra 1 38 BIG_CAVE")
        sys.exit(1)

    prefix, start, end, reverb = sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), sys.argv[4].upper()

    if reverb not in VALID_REVERBS:
        print(f"Invalid reverb '{reverb}'. Valid types: {', '.join(sorted(VALID_REVERBS))}")
        sys.exit(1)

    path = os.path.normpath(SOUNDS_JSON)
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()

    count = 0
    for i in range(start, end + 1):
        old = f'    "file": "{prefix}-{i}"\n  }}'
        new = f'    "file": "{prefix}-{i}",\n    "reverb": "{reverb}"\n  }}'
        updated = content.replace(old, new)
        if updated != content:
            count += 1
            content = updated

    with open(path, "w", encoding="utf-8") as f:
        f.write(content)

    skipped = (end - start + 1) - count
    print(f"Applied {reverb} to {count} entries ({prefix}-{start} to {prefix}-{end})")
    if skipped:
        print(f"Skipped {skipped} (already had reverb or not found)")


if __name__ == "__main__":
    main()
