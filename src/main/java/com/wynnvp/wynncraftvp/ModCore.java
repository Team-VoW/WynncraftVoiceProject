/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.commands.VowCommands;
import com.wynnvp.wynncraftvp.config.ConfigFileRecovery;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import com.wynnvp.wynncraftvp.config.VowConfigHolder;
import com.wynnvp.wynncraftvp.core.Managers;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import com.wynnvp.wynncraftvp.sound.downloader.AudioDownloader;
import com.wynnvp.wynncraftvp.sound.downloader.ToastManager;
import com.wynnvp.wynncraftvp.sound.player.AudioPlayer;
import com.wynnvp.wynncraftvp.text.OverlayHandler;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModCore implements ModInitializer {
    public static final String MODID = "wynnvp";

    private String version;

    public static boolean inLiveWynnServer = false;

    public SoundsHandler soundsHandler;
    public static ModCore instance;

    public AudioPlayer audioPlayer;
    public SoundPlayer soundPlayer;
    public static OverlayHandler overlayHandler;

    public static VOWAutoConfig config;
    public static VowConfigHolder configHolder;

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public AudioDownloader audioDownloader;

    @Override
    public void onInitialize() {
        Optional<ModContainer> vowMod = FabricLoader.getInstance().getModContainer(MODID);
        if (vowMod.isEmpty()) {
            ModCore.error("Mod not found by fabric.");
            return;
        }

        version = "v" + vowMod.get().getMetadata().getVersion().getFriendlyString();

        LOGGER.info(
                "Loading Voices of Wynn {} (on Minecraft {})",
                version,
                SharedConstants.getCurrentVersion().name());

        Managers.initialize();

        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(MODID + ".toml");
        recoverCorruptedConfig(configPath);
        configHolder = new VowConfigHolder(configPath);
        config = configHolder.load();

        instance = this;
        overlayHandler = new OverlayHandler();

        soundPlayer = new SoundPlayer();
        soundsHandler = new SoundsHandler();

        VowLogger.Initialize();

        audioDownloader = new AudioDownloader(AudioPlayer.AUDIO_FOLDER);

        new ToastManager(Minecraft.getInstance());

        VowCommands.register();
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }

    public static void error(String msg, Throwable t) {
        LOGGER.error(msg, t);
    }

    public static void warn(String msg) {
        LOGGER.warn(msg);
    }

    public static void warn(String msg, Throwable t) {
        LOGGER.warn(msg, t);
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public String getVersion() {
        return version;
    }

    private static void recoverCorruptedConfig(Path configPath) {
        try {
            if (ConfigFileRecovery.quarantineIfNullByteCorrupted(configPath)) {
                LOGGER.warn(
                        "Found a corrupted Voices of Wynn config at {} and moved it aside. A new default config will be created.",
                        configPath);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to check Voices of Wynn config for corruption before loading it.", e);
        }
    }
}
