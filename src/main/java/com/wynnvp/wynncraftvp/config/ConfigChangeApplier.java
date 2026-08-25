/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import com.wynnvp.wynncraftvp.ModCore;

/**
 * Puts settings that are only read at startup into effect as soon as they are changed.
 * <p>
 * Most options are read wherever they are used and so need nothing here. The ones that do -- the
 * sounds.json source and the audio download -- used to be acted on once during initialization, which
 * meant switching them on did nothing at all until the game was restarted.
 * <p>
 * The state last acted on is held statically rather than snapshotted per screen, because the settings
 * screens nest: closing the debug screen returns to the main one, and both then report themselves as
 * closed. Comparing against what was last applied means the work happens once, on whichever screen
 * actually changed something.
 */
public final class ConfigChangeApplier {
    private static volatile Snapshot lastApplied;

    private ConfigChangeApplier() {}

    /** Records the state the mod started up with, so the first comparison has a baseline. */
    public static void initialize(VOWAutoConfig config) {
        lastApplied = Snapshot.of(config);
    }

    /**
     * Acts on whatever has changed since the last call. Safe to call when nothing has, which is the
     * common case: a settings screen that was only looked at closes like any other.
     */
    public static void applyChanges() {
        VOWAutoConfig config = ModCore.config;
        Snapshot previous = lastApplied;
        if (config == null || previous == null) {
            return;
        }

        Snapshot current = Snapshot.of(config);
        lastApplied = current;
        if (previous.equals(current)) {
            return;
        }

        if (!previous.downloadSounds && current.downloadSounds && ModCore.instance != null) {
            ModCore.info("Download sounds was enabled, starting the audio download check now");
            ModCore.instance.audioDownloader.checkNow();
        }

        if (soundsSourceChanged(previous, current) && ModCore.instance != null) {
            ModCore.info("The sounds.json source changed, reloading it now");
            ModCore.instance.soundsHandler.reload();
        }
    }

    /**
     * The path only matters while the custom source is switched on, so editing it with the toggle off
     * is not a reason to reload.
     */
    private static boolean soundsSourceChanged(Snapshot previous, Snapshot current) {
        if (previous.useCustomSoundsJson != current.useCustomSoundsJson) {
            return true;
        }
        return current.useCustomSoundsJson && !previous.customSoundsJsonPath.equals(current.customSoundsJsonPath);
    }

    private record Snapshot(boolean downloadSounds, boolean useCustomSoundsJson, String customSoundsJsonPath) {
        static Snapshot of(VOWAutoConfig config) {
            return new Snapshot(
                    config.downloadSounds, config.isUseCustomSoundsJson(), config.getCustomSoundsJsonPath());
        }
    }
}
