/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.commands.DebugCommand;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import com.wynnvp.wynncraftvp.core.Managers;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import com.wynnvp.wynncraftvp.sound.downloader.AudioDownloader;
import com.wynnvp.wynncraftvp.sound.downloader.ToastManager;
import com.wynnvp.wynncraftvp.sound.player.AudioPlayer;
import com.wynnvp.wynncraftvp.text.ChatHandler;
import java.util.Optional;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
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
    public static boolean isUsingClothApi = false;

    public SoundsHandler soundsHandler;
    public static ModCore instance;

    public AudioPlayer audioPlayer;
    public SoundPlayer soundPlayer;
    public static ChatHandler chatHandler;

    public static VOWAutoConfig config;

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
                SharedConstants.getCurrentVersion().getName());

        Managers.initialize();

        isUsingClothApi = FabricLoader.getInstance().isModLoaded("cloth-config");

        AutoConfig.register(VOWAutoConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(VOWAutoConfig.class).getConfig();

        instance = this;
        chatHandler = new ChatHandler();

        soundPlayer = new SoundPlayer();
        soundsHandler = new SoundsHandler();

        VowLogger.Initialize();

        ClientTickEvents.END_WORLD_TICK.register(cli -> {
            // Your ticking method
            chatHandler.onTick();
            if (audioPlayer != null) audioPlayer.openAlPlayer.onTick();
        });
        audioDownloader = new AudioDownloader(AudioPlayer.AUDIO_FOLDER);

        new ToastManager(Minecraft.getInstance());

        CommandRegistrationCallback.EVENT.register(DebugCommand::register);
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
}
