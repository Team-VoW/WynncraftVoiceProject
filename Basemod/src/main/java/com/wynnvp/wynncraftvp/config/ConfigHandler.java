package com.wynnvp.wynncraftvp.config;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigHandler {

    private static Configuration config;

    public static boolean playAllSoundsOnPlayer;
    public static String word = "gzog6zilzq6zxlt";
    public static boolean logMissingLines;
    public static boolean createLog;
    public static boolean anonymous;
    public static boolean hasShownReportLineGui;
    public static boolean sendFunFact;
    public static int blockCutOff;
    public static int maxCoolDownLines;


    private static final String category = "Settings";

    private static void init(File file) {
        config = new Configuration(file);

        config.addCustomCategoryComment(ConfigHandler.category, "Settings for the mod");

        playAllSoundsOnPlayer = config.getBoolean("playAllSoundsOnPlayer", ConfigHandler.category, false, "If the mod should play all sounds on the player");
        logMissingLines = config.getBoolean("logMissingLines", ConfigHandler.category, false, "If the mod should send in unvoiced lines");
        logMissingLines = config.getBoolean("createLog", ConfigHandler.category, false, "If a custom log should be created for logging all lines that were sent to our server");
        anonymous = config.getBoolean("anonymous", ConfigHandler.category, false, "If line reporting should be anonymous");
        sendFunFact = config.getBoolean("sendFunFact", ConfigHandler.category, true, "If it should say a fun fact about the mod each time the player join Wynncraft");
        hasShownReportLineGui = config.getBoolean("hasShownReportLineGui", ConfigHandler.category, false, "If the Report line gui has been shown");
        blockCutOff = config.getInt("blockCutOff", ConfigHandler.category, 32, 16, 10000, "At what distance voices become unhearable");
        maxCoolDownLines = config.getInt("maxCoolDownLines", ConfigHandler.category, 2, 1, 10000, "How many different sound files can be on cooldown.");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        ModCore.config = new File(event.getModConfigurationDirectory() + "/" + ModCore.MODID);
        ModCore.config.mkdirs();
        init(new File(ModCore.config.getPath(), ModCore.MODID + ".cfg"));
    }

    public static void SetPlayAllSoundsOnPlayer(Boolean playOnPlayer) {
        ConfigHandler.playAllSoundsOnPlayer = playOnPlayer;
        config.get(category, "playAllSoundsOnPlayer", false).set(playOnPlayer);
        config.save();
    }

    public static void setLogMissingLines(Boolean logMissingLines) {
        ConfigHandler.logMissingLines = logMissingLines;
        config.get(category, "logMissingLines", false).set(logMissingLines);
        config.save();
    }

    public static void setAnonymous(Boolean anonymous) {
        ConfigHandler.anonymous = anonymous;
        config.get(category, "anonymous", false).set(anonymous);
        config.save();
    }

    public static void setHasShownReportLineGui(Boolean hasShownReportLineGui) {
        ConfigHandler.hasShownReportLineGui = hasShownReportLineGui;
        config.get(category, "hasShownReportLineGui", false).set(hasShownReportLineGui);
        config.save();
    }


    public static void setSendFunFact(Boolean sendFunFact) {
        ConfigHandler.sendFunFact = sendFunFact;
        config.get(category, "sendFunFact", false).set(sendFunFact);
        config.save();
    }

    public static void setBlockCutOff(int value) {
        ConfigHandler.blockCutOff = value;
        config.get(category, "blockCutOff", 1.5f).set(value);
        config.save();
    }

    public static void setMaxCoolDownLines(int value) {
        ConfigHandler.maxCoolDownLines = value;
        config.get(category, "maxCoolDownLines", 2).set(value);
        config.save();
    }


}
