package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import com.wynnvp.wynncraftvp.text.ChatHandler3;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModCore implements ModInitializer {

    public static final String MODID = "wynnvp";
    public static final String NAME = "Wynncraft Voice Project";
    public static final String VERSION = "1.6";
    public static boolean inLiveWynnServer = false;
    public static boolean isUsingClothApi = false;
    public SoundsHandler soundsHandler;
    public static ModCore instance;
    public SoundPlayer soundPlayer;
    public static final Logger LOGGER = LoggerFactory.getLogger("wynnvp");

    public static VOWAutoConfig config;

    public static ChatHandler3 chatHandler;

    @Override
    public void onInitialize() {

        instance = this;
        chatHandler = new ChatHandler3();


        soundPlayer = new SoundPlayer();
        soundsHandler = new SoundsHandler();

        isUsingClothApi = FabricLoader.getInstance().isModLoaded("cloth-config");
        //    if (isUsingClothApi) {
        LOGGER.debug("Found cloth api");

        AutoConfig.register(VOWAutoConfig.class, Toml4jConfigSerializer::new);

        config = AutoConfig.getConfigHolder(VOWAutoConfig.class).getConfig();
      /*  } else {
            config = new VOWAutoConfig();
        }*/

        VowLogger.Initialize();

        ClientTickEvents.END_WORLD_TICK.register(cli -> {
            // Your ticking method
            chatHandler.onTick();
        });
    }
}
