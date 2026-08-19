#!/usr/bin/env python3
"""Transcribe voice assets locally and compare them with sounds/sounds.json.

Requires: pip install faster-whisper
The first run downloads the requested Whisper model; later runs use its local cache.
"""

from __future__ import annotations

import argparse
import difflib
import json
import re
import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_MANIFEST = ROOT / "sounds" / "sounds.json"
DEFAULT_SOUNDS = ROOT / "sounds"


def normalize(text: str) -> str:
    return " ".join(re.sub(r"[^a-z0-9 ]", " ", text.lower()).split())


def load_expected(manifest: Path, prefix: str) -> dict[str, str]:
    entries = json.loads(manifest.read_text(encoding="utf-8"))
    expected: dict[str, str] = {}
    for entry in entries:
        filename = entry.get("file")
        line = entry.get("line")
        if isinstance(filename, str) and isinstance(line, str) and filename.startswith(prefix):
            expected[filename] = line
    return expected


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("prefix", help="Filename prefix from sounds.json, e.g. clearingthecamps-captainkymer-")
    parser.add_argument("--model", default="small.en", help="Whisper model to use (default: small.en)")
    parser.add_argument("--manifest", type=Path, default=DEFAULT_MANIFEST)
    parser.add_argument("--sounds-dir", type=Path, default=DEFAULT_SOUNDS)
    parser.add_argument("--output", type=Path, help="Optional JSON report path")
    parser.add_argument("--threshold", type=float, default=0.75, help="Minimum similarity to pass (default: 0.75)")
    args = parser.parse_args()

    try:
        from faster_whisper import WhisperModel
    except ImportError:
        print("faster-whisper is not installed. Run: py -m pip install faster-whisper", file=sys.stderr)
        return 2

    expected = load_expected(args.manifest, args.prefix)
    if not expected:
        print(f"No dialogue entries found for prefix: {args.prefix}", file=sys.stderr)
        return 2

    model = WhisperModel(args.model, device="cpu", compute_type="int8")
    results = []
    failures = 0
    for file_id, dialogue in expected.items():
        audio = args.sounds_dir / f"{file_id}.ogg"
        if not audio.is_file():
            print(f"MISSING  {audio.name}")
            results.append({"file": file_id, "status": "missing", "expected": dialogue})
            failures += 1
            continue

        segments, _ = model.transcribe(str(audio), language="en", vad_filter=True, beam_size=5)
        transcript = " ".join(segment.text.strip() for segment in segments).strip()
        similarity = difflib.SequenceMatcher(None, normalize(dialogue), normalize(transcript)).ratio()
        status = "PASS" if similarity >= args.threshold else "CHECK"
        failures += status == "CHECK"
        print(f"{status:5} {similarity:0.0%}  {audio.name}")
        print(f"  expected:   {dialogue}")
        print(f"  transcript: {transcript}")
        results.append({
            "file": file_id,
            "status": status.lower(),
            "similarity": round(similarity, 4),
            "expected": dialogue,
            "transcript": transcript,
        })

    if args.output:
        args.output.parent.mkdir(parents=True, exist_ok=True)
        args.output.write_text(json.dumps(results, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
        print(f"Report written to {args.output}")
    return 1 if failures else 0


if __name__ == "__main__":
    raise SystemExit(main())
