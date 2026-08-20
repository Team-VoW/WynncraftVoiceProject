# CLAUDE.md

**Voices of Wynn** — Fabric mod for Minecraft 1.21.11. Intercepts chat packets to detect NPC dialogue, matches lines to audio files via `audio_manifest.json`, and plays 3D spatial audio via OpenAL at NPC locations on the Wynncraft MMORPG server.

## Commands
```bash
gradlew build                  # Build JAR to build/libs/
gradlew build -x spotlessApply # Build without formatting
gradlew spotlessApply          # Format code (Palantir Java Format)
gradlew runClient              # Launch Minecraft with mod
gradlew build -PbetaSoundsJsonUrl=<url> -PbetaSoundsUrl=<url>  # Beta build with custom CDN URLs
```
Pre-commit hook: `git config core.hooksPath utils/git-hooks`

JUnit 5 + Mockito are on the test classpath (`gradlew test`), though the house style is hand-rolled
fakes rather than Mockito. Anything touching `Minecraft.getInstance()` is untestable headless, so
logic that needs covering goes in a class with no `net.minecraft.*` imports (see
`text/OverlayStateMachine` and `sound/physics/`) with a thin adapter around it.

## Pipeline
`Chat Packet → MixinChatListener → ChatHandler → ReceiveChatEvent → LineFormatter → SoundPlayer → SoundsHandler → AudioPlayer → OpenAlPlayer`

## File Structure
```
src/main/java/com/wynnvp/wynncraftvp/
├── ModCore.java        # Entry point
├── core/               # Manager pattern (Manager.java, Managers.java)
├── config/             # VOWAutoConfig.java (TOML via VowConfigHolder), gui/VowConfigScreen.java
├── events/             # ChatHandler, ReceiveChatEvent, Mixins
├── npc/                # NPC entity tracking / CurrentSpeaker
├── sound/
│   ├── downloader/     # AudioDownloader (CDN fetch + hash verify)
│   ├── player/         # OpenAlPlayer, AudioPlayer, OggDecoder, SoundPhysicsManager
│   ├── physics/        # Ray-traced acoustics (pure model + world adapters)
│   └── dialogue/       # DialogueData, SoundObject
├── text/               # LineFormatter, StyledText
└── utils/              # Misc utilities
```

## Sound physics
Reverb and through-wall muffling are traced from the blocks around the speaker rather than authored.
`sound/physics/` holds the model; everything in it except `AcousticMaterialTable` and
`LevelMaterialProbe` has no `net.minecraft.*` imports and is unit tested. `SoundPhysicsManager` drives
it from `ClientTickEvent.onEndWorldTick`, which is the only place raycasting is safe — the audio
executor is a worker pool and must never read the world.

Two rules to keep in mind when touching it:
- **Barriers are acoustically invisible.** Wynncraft builds invisible walls out of them, so they must
  never reflect or muffle. The transparent set is checked before any shape test in
  `AcousticMaterialTable`, because a barrier is a fully solid, fully opaque block.
- **An unloaded chunk means open air, never solid.** Otherwise speakers near the render-distance edge
  get sealed into an imaginary box.

An explicit `"reverb"` in `sounds.json` still overrides the traced result; an absent field means
"work it out". Material values live in `assets/wynnvp/sound_physics/materials.json` and can be
overridden without a rebuild by dropping `wynnvp-materials.json` in the config directory.

Audio cache: `VOW_AUDIO/` | Manifest: `audio_manifest.json` (key: `questname-npcname-linetext`)
Logging: `ModCore.info/warn/error(...)` | Debug: `/vowdebug` (dev only)
Config file: `.minecraft/config/wynnvp.toml` | Settings GUI: Mod Menu or `/vow-config`

## Commit Style
Conventional Commits: `feat:`, `fix:`, `chore:` etc.

## DON'Ts
- No wildcard imports — Spotless rejects them
- No `org.jetbrains.annotations` — use standard Java annotations
- Don't skip `spotlessApply` — license headers are auto-managed and required
- Don't log with anything other than `ModCore.LOGGER`
- No Fabric API (`net.fabricmc.fabric.api.*`) — the mod only depends on Fabric Loader. It is not on the
  compile classpath, so such an import fails the build. To hook vanilla, add a mixin in `events/mixins/`
  that calls a static event class in `events/` (see `MixinClientTickListener` → `ClientTickEvent`)
