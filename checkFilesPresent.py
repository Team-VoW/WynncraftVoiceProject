import json
import os

# Path to the JSON file
json_file_path = "sounds/sounds.json"
# Path to the sounds directory
sounds_dir = "sounds"

# Load the JSON data
with open(json_file_path, "r", encoding="utf-8") as f:
    data = json.load(f)

# Collect all registered sound files
registered_files = set()
for entry in data:
    if "file" not in entry:
        continue  # Skip entries without a "file" key
    registered_files.add(entry["file"] + ".ogg")

# Check for missing files
missing_files = []
for sound_file in registered_files:
    sound_path = os.path.join(sounds_dir, sound_file)
    if not os.path.isfile(sound_path):
        missing_files.append(sound_file)

# Print missing files
if missing_files:
    print("Missing sound files:")
    for file in missing_files:
        print(file)
else:
    print("All sound files are present.")

# Check for unregistered files
all_sound_files = {f for f in os.listdir(sounds_dir) if f.endswith(".ogg")}
unregistered_files = all_sound_files - registered_files

# Print unregistered files
if unregistered_files:
    print("---------------------------------------------------------------------------------------")
    print("Unregistered sound files:")
    for file in unregistered_files:
        print(file)
else:
    print("All sound files are registered.")