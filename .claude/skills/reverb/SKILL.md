---
name: reverb
description: Apply reverb settings to NPC dialogue entries in sounds.json. Use when the user asks about adding reverb to a quest, location, or NPC, or asks which reverb type fits a given environment.
---

# Reverb Skill

Adds `"reverb"` fields to entries in `sounds/sounds.json`. Reverb is applied via OpenAL EFX when a line plays in-game.

**Omitting the field now means "work it out from the world".** The mod ray-traces the blocks around the speaker and
derives the reverb from what it finds, so most lines need no tag at all. Setting one is an override: it forces that
preset wherever the line plays, ignoring the geometry. Reach for it only where the tracer gets it wrong — a scripted
scene whose acoustics should not match the room, or a speaker with no resolvable position.

Muffling through walls is separate and always applies, tagged or not.

## Reverb Types

| Name | Decay | Use For |
|------|-------|---------|
| `OUTSIDE` | 1.5s | Open fields, default |
| `FOREST` | 1.2s | Outdoors with trees/foliage |
| `FURNISHED_ROOM` | 0.4s | Small rooms with furniture |
| `EMPTY_ROOM` | 1.1s | Small rooms, hard surfaces |
| `HALLWAY` | 0.8s | Narrow corridors |
| `LARGE_HALL` | 3.5s | Throne rooms, banquet halls |
| `SMALL_CAVE` | 2.1s | Confined underground spaces |
| `CAVE` | 2.9s | Medium caverns |
| `BIG_CAVE` | 4.3s | Large caverns, caves with vegetation |
| `CATHEDRAL` | 7.2s | Massive enclosed spaces |

## JSON Format

```json
{
  "line": "NPC: dialogue text",
  "file": "questname-npcname-1",
  "reverb": "CAVE"
}
```

## Applying Reverb in Bulk

Use `scripts/apply_reverb.py` to apply reverb to a numbered range of sound entries:

```bash
python .claude/skills/reverb/scripts/apply_reverb.py anewbeginning-syndra 1 38 BIG_CAVE
```

Args: `<file-prefix> <start> <end> <reverb-type>`

Run from the project root. Modifies `sounds/sounds.json` in place. Only affects entries that don't already have a reverb field.
