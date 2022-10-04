package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.config.VOWConfig;
import com.wynnvp.wynncraftvp.sound.SoundController;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import com.wynnvp.wynncraftvp.utils.StringBlacklist;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModCore implements ModInitializer {

    public static final String MODID = "wynnvp";
    public static final String NAME = "Wynncraft Voice Project";
    public static final String VERSION = "0.0.1";
    public static boolean inLiveWynnServer = false;
    public SoundsHandler soundsHandler;
    public static ModCore instance;
    public SoundPlayer soundPlayer;
    public SoundController controller;

    public static final Logger LOGGER = LoggerFactory.getLogger("wynnvp");

    public static VOWConfig config;

    @Override
    public void onInitialize() {
        instance = this;

        soundPlayer = new SoundPlayer();
        soundsHandler = new SoundsHandler();
        controller = new SoundController();

        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            LOGGER.debug("Found cloth api");
            AutoConfig.register(VOWConfig.class, Toml4jConfigSerializer::new);

            config = AutoConfig.getConfigHolder(VOWConfig.class).getConfig();
        } else {
            config = new VOWConfig();
            config.logMissingLines = false;
        }

        StringBlacklist.namesDefault();
    }
}
