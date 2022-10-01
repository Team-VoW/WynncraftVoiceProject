package com.wynnvp.wynncraftvp.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@Config(name = "wynnvp")
public class VOWConfig implements ConfigData {

    //private static Configuration config;

    public static boolean playAllSoundsOnPlayer = false;

    @ConfigEntry.Gui.Excluded
    public static String word = "gzog6zilzq6zxlt";


    public static boolean logMissingLines = false;
    public static boolean anonymous = false;

    public static boolean sendFunFact = true;

    @ConfigEntry.Gui.Excluded
    public static double tripleQuestionMarkInessentiel = 200;
    @ConfigEntry.Gui.Excluded
    public static double tripleQuestionMarkMaxDistance = 50;


    public static int blockCutOff = 32;

    @ConfigEntry.Gui.Tooltip
    public static boolean highlightSpeaker = false;


    @ConfigEntry.Gui.Excluded
    public static double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5; // small name?


}
