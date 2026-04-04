# CLAUDE.md

**Voices of Wynn** — Fabric mod for Minecraft 1.21.4. Intercepts chat packets to detect NPC dialogue, matches lines to audio files via `audio_manifest.json`, and plays 3D spatial audio via OpenAL at NPC locations on the Wynncraft MMORPG server.

## Commands
```bash
gradlew build                  # Build JAR to build/libs/
gradlew build -x spotlessApply # Build without formatting
gradlew spotlessApply          # Format code (Palantir Java Format)
gradlew runClient              # Launch Minecraft with mod
```
Pre-commit hook: `git config core.hooksPath utils/git-hooks`

No unit tests — all testing is manual in-game.

## Pipeline
`Chat Packet → MixinChatListener → ChatHandler → ReceiveChatEvent → LineFormatter → SoundPlayer → SoundsHandler → AudioPlayer → OpenAlPlayer`

## File Structure
```
src/main/java/com/wynnvp/wynncraftvp/
├── ModCore.java        # Entry point
├── core/               # Manager pattern (Manager.java, Managers.java)
├── config/             # VOWAutoConfig.java (TOML, Cloth Config GUI)
├── events/             # ChatHandler, ReceiveChatEvent, Mixins
├── npc/                # NPC entity tracking / CurrentSpeaker
├── sound/
│   ├── downloader/     # AudioDownloader (CDN fetch + hash verify)
│   ├── player/         # OpenAlPlayer, AudioPlayer, OggDecoder
│   └── dialogue/       # DialogueData, SoundObject
├── text/               # LineFormatter, StyledText
└── utils/              # Misc utilities
```

Audio cache: `VOW_AUDIO/` | Manifest: `audio_manifest.json` (key: `questname-npcname-linetext`)
Logging: `ModCore.info/warn/error(...)` | Debug: `/vowdebug` (dev only)
Config file: `.minecraft/config/wynnvp.toml`

## Commit Style
Conventional Commits: `feat:`, `fix:`, `chore:` etc.

## DON'Ts
- No wildcard imports — Spotless rejects them
- No `org.jetbrains.annotations` — use standard Java annotations
- Don't modify dialogue detection logic carelessly — packet ordering is complex and can break multi-line / confirmationless dialogues
- Don't skip `spotlessApply` — license headers are auto-managed and required
- Don't log with anything other than `ModCore.LOGGER`
- Don't add unit tests scaffolding — this project tests manually in-game
