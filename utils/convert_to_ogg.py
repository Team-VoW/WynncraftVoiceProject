#!/usr/bin/env python3
"""Convert voice line WAV files to Ogg Vorbis, matching the format used in sounds/.

Output: mono, 44100 Hz, libvorbis ~q4 (matches existing sounds/*.ogg encoding).

Usage:
    py utils/convert_to_ogg.py <input_dir_or_files...> [--out sounds] [--ffmpeg path/to/ffmpeg.exe]

Examples:
    py utils/convert_to_ogg.py "E:\\esst9\\OneDrive\\voice project\\lines\\z_Secret Discoveries\\Light_Forest\\Dern Beast" --out sounds
    py utils/convert_to_ogg.py file1.wav file2.wav --ffmpeg "C:\\ffmpeg\\bin\\ffmpeg.exe"
"""

from __future__ import annotations

import argparse
import shutil
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUT = ROOT / "sounds"


def find_ffmpeg(explicit: str | None) -> str:
    if explicit:
        return explicit
    found = shutil.which("ffmpeg")
    if found:
        return found
    print("ffmpeg not found on PATH; pass --ffmpeg <path to ffmpeg.exe>", file=sys.stderr)
    sys.exit(2)


def gather_wavs(inputs: list[str]) -> list[Path]:
    wavs: list[Path] = []
    for item in inputs:
        path = Path(item)
        if path.is_dir():
            wavs.extend(sorted(path.glob("*.wav")))
        elif path.is_file() and path.suffix.lower() == ".wav":
            wavs.append(path)
        else:
            print(f"Skipping non-wav input: {item}", file=sys.stderr)
    return wavs


def convert(ffmpeg: str, src: Path, dest: Path, overwrite: bool) -> None:
    if dest.exists() and not overwrite:
        print(f"SKIP (exists) {dest.name}")
        return
    cmd = [
        ffmpeg,
        "-y",
        "-i",
        str(src),
        "-ac",
        "1",
        "-ar",
        "44100",
        "-c:a",
        "libvorbis",
        "-q:a",
        "4",
        "-map_metadata",
        "-1",
        str(dest),
    ]
    result = subprocess.run(cmd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"FAILED {src.name}\n{result.stderr}", file=sys.stderr)
        sys.exit(1)
    print(f"OK   {src.name} -> {dest.name}")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("inputs", nargs="+", help="WAV files or a directory containing WAV files")
    parser.add_argument("--out", type=Path, default=DEFAULT_OUT, help="Output directory (default: sounds/)")
    parser.add_argument("--ffmpeg", help="Path to ffmpeg.exe (default: ffmpeg on PATH)")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite existing .ogg files")
    args = parser.parse_args()

    ffmpeg = find_ffmpeg(args.ffmpeg)
    wavs = gather_wavs(args.inputs)
    if not wavs:
        print("No .wav files found", file=sys.stderr)
        return 2

    args.out.mkdir(parents=True, exist_ok=True)
    for wav in wavs:
        dest = args.out / f"{wav.stem}.ogg"
        convert(ffmpeg, wav, dest, args.overwrite)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
