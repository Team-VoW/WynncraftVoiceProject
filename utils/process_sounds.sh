#!/bin/sh

# This script processes the sound_info.json file and creates a sounds.json file,
# which is used by Minecraft to load and play sounds.

# Check if the sound_info.json file is provided
if [ "$#" -ne 1 ]; then
  echo "Usage: $0 path/to/sound_info.json"
  exit 1
fi

sound_info_file=$1

# Check if the provided file exists
if [ ! -f "$sound_info_file" ]; then
  echo "File not found: $sound_info_file"
  exit 1
fi

# Get the directory of the provided sound_info.json file
sound_info_dir=$(dirname "$sound_info_file")

# Define the output sounds.json file path
sounds_json_file="$sound_info_dir/sounds.json"

# Process the sound_info.json file and create the sounds.json file
# NOTE: This script can only generate the fileOverride field for the sounds.json file,
#       if the NPC name and line info are provided in the sound_info.json file.
#       If either of these data is not available, the script fails. Use a unique fileOverride value for these cases.
jq '[
  .content | to_entries | map(
    .key as $content | .value.contexts | to_entries | map(
      .key as $context | .value.dialogues | map(
        .fileOverride as $fileOverride |
        {
          "fileName": ($fileOverride // (
            ($content | gsub("[^a-zA-Z0-9]"; "") | ascii_downcase) + "-" +
            ($context | gsub("[^a-zA-Z0-9]"; "") | ascii_downcase) + "-" +
            (.lineInfo.npc | gsub("[^a-zA-Z0-9]"; "") | ascii_downcase) + "-" +
            (.lineInfo.line.current | tostring)
          ))
        } | {
          (.fileName): {
            "category": "voice",
            "sounds": [
              {
                "name": "wynnvp:\(.fileName)",
                "stream": true
              }
            ]
          }
        }
      )
    )
  ) | flatten | add
] | add | to_entries | sort_by(.key) | from_entries' "$sound_info_file" > "$sounds_json_file"

# Check if the sounds.json file was created successfully
if [ $? -eq 0 ]; then
  echo "sounds.json file has been created successfully at $sounds_json_file"
else
  echo "An error occurred while creating the sounds.json file"
  exit 1
fi
