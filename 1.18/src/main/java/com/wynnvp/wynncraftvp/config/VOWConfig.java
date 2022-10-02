package com.wynnvp.wynncraftvp.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@Config(name = "wynnvp")
public class VOWConfig implements ConfigData {

    //private static Configuration config;

    public boolean playAllSoundsOnPlayer = false;

    @ConfigEntry.Gui.Excluded
    public String word = "gzog6zilzq6zxlt";


    public boolean logMissingLines = true;
    public boolean anonymous = false;

    public boolean sendFunFact = true;

    @ConfigEntry.Gui.Excluded
    public double tripleQuestionMarkInessentiel = 200;
    @ConfigEntry.Gui.Excluded
    public double tripleQuestionMarkMaxDistance = 50;


    public int blockCutOff = 32;

    @ConfigEntry.Gui.Tooltip
    public boolean highlightSpeaker = false;


    @ConfigEntry.Gui.Excluded
    public double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5; // small name?


}
