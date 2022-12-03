package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import com.wynnvp.wynncraftvp.config.VOWConfig;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModCore implements ModInitializer {

    public static final String MODID = "wynnvp";
    public static final String NAME = "Wynncraft Voice Project";
    public static final String VERSION = "1.1.2";
    public static boolean inLiveWynnServer = false;
    public static boolean isUsingClothApi = false;
    public SoundsHandler soundsHandler;
    public static ModCore instance;
    public SoundPlayer soundPlayer;
    public static final Logger LOGGER = LoggerFactory.getLogger("wynnvp");

    public static VOWConfig config;

    @Override
    public void onInitialize() {
        instance = this;

        soundPlayer = new SoundPlayer();
        soundsHandler = new SoundsHandler();

        isUsingClothApi = FabricLoader.getInstance().isModLoaded("cloth-config");
        if (isUsingClothApi) {
            LOGGER.debug("Found cloth api");

            AutoConfig.register(VOWAutoConfig.class, Toml4jConfigSerializer::new);

            config = AutoConfig.getConfigHolder(VOWAutoConfig.class).getConfig();
        } else {
            config = new VOWConfig();
        }
    }
}
