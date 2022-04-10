package com.wynnvp.wynncraftvp.config;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigHandler {


    private static Configuration config;

    public static boolean playAllSoundsOnPlayer;
    public static String apiKey = "e3l06vpl32wgr8b";
    public static boolean logMissingLines;


    private static final String category = "Settings";

    private static void init(File file){
        config = new Configuration(file);

        config.addCustomCategoryComment(ConfigHandler.category, "Settings for the mod");

        playAllSoundsOnPlayer = config.getBoolean("playAllSoundsOnPlayer", ConfigHandler.category, false, "If the mod should play all sounds on the player");
        logMissingLines = config.getBoolean("logMissingLines", ConfigHandler.category, false, "If the mod should send in unvoiced lines");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event){
        ModCore.config = new File(event.getModConfigurationDirectory() + "/" + ModCore.MODID);
        ModCore.config.mkdirs();
        init(new File(ModCore.config.getPath(), ModCore.MODID + ".cfg"));

    }

    public static void SetPlayAllSoundsOnPlayer(Boolean playOnPlayer){
        ConfigHandler.playAllSoundsOnPlayer = playOnPlayer;
        config.get(category, "playAllSoundsOnPlayer", false).set(playOnPlayer);
        config.save();
    }
    public static void setLogMissingLines(Boolean logMissingLines){
        ConfigHandler.logMissingLines = logMissingLines;
        config.get(category, "logMissingLines", false).set(logMissingLines);
        config.save();
    }







    }
