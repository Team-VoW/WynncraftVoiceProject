package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.events.ArmorCheckEvent;
import com.wynnvp.wynncraftvp.events.QuitServerEvent;
import com.wynnvp.wynncraftvp.sound.SoundController;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import com.wynnvp.wynncraftvp.utils.StringBlacklist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = ModCore.MODID,
        name = ModCore.NAME,
        version = ModCore.VERSION,
        clientSideOnly = true
)
public class ModCore {

    public static File config;

    public static final String MODID = "wynnvp";
    public static final String NAME = "Wynncraft Voice Project";
    public static final String VERSION = "0.1";
    public static boolean inServer = false;
    public static boolean inLiveWynnServer = false;
    public SoundsHandler soundsHandler;
    public static ModCore instance;
    public SoundPlayer soundPlayer;
    public SoundController controller;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ConfigHandler.registerConfig(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        soundPlayer = new SoundPlayer();
        soundsHandler = new SoundsHandler();
        controller = new SoundController();

        StringBlacklist.namesDefault();

        MinecraftForge.EVENT_BUS.register(new ArmorCheckEvent());
        MinecraftForge.EVENT_BUS.register(new QuitServerEvent());
        instance = this;
    }


}
