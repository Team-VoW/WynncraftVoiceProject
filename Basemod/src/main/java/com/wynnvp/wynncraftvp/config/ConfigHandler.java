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

    public static String playerName;
    public static boolean playAllSoundsOnPlayer = false;

    private static final String category = "Settings";

    private static void init(File file){
        config = new Configuration(file);

        config.addCustomCategoryComment(ConfigHandler.category, "Settings for the mod");
        playerName = config.getString("PlayerName", ConfigHandler.category, "null", "PlayerName to look out for in text");

        playAllSoundsOnPlayer = config.getBoolean("playAllSoundsOnPlayer", ConfigHandler.category, false, "If the mod should play all sounds on the player");

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

    public static void SetName(String name) {
        ConfigHandler.playerName = name;

        config.get(category, "PlayerName", ConfigHandler.category, Minecraft.getMinecraft().player.getName()).set(name);
        config.save();
    }




    }
