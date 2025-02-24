import os
import json
import hashlib
from pathlib import Path

def calculate_hash(file_path):
    sha256_hash = hashlib.sha256()
    with open(file_path, "rb") as f:
        for byte_block in iter(lambda: f.read(4096), b""):
            sha256_hash.update(byte_block)
    return sha256_hash.hexdigest()

def generate_manifest():
    sounds_dir = Path('src/main/resources/assets/wynnvp/sounds')
    manifest = {}
    
    if not sounds_dir.exists():
        print(f"Warning: {sounds_dir} does not exist")
        return {}
        
    for file_path in sounds_dir.rglob('*.ogg'):
        file_size = os.path.getsize(file_path)
        file_hash = calculate_hash(file_path)
        
        # Use the filename without extension as the key
        key = file_path.stem
        
        manifest[key] = {
            "size": file_size,
            "hash": file_hash
        }
        print(f"Processed: {key}")
    
    return manifest

# Generate and save manifest
manifest = generate_manifest()
with open('audio_manifest.json', 'w') as f:
    json.dump(manifest, f, indent=2, sort_keys=True)

print("Generated audio_manifest.json")