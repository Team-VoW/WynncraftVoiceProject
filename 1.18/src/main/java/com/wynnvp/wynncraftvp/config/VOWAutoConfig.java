package com.wynnvp.wynncraftvp.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "wynnvp")
public class VOWAutoConfig extends VOWConfig implements ConfigData {

    //private static Configuration config;

    public boolean playAllSoundsOnPlayer = false;

    @ConfigEntry.Gui.Excluded
    public String word = "gzog6zilzq6zxlt";


    public boolean logMissingLines = true;
    public boolean anonymous = false;

    public boolean sendFunFact = true;

    @ConfigEntry.Gui.Excluded
    public boolean hasChosenLineReport = false;

    @ConfigEntry.Gui.Excluded
    public int tripleQuestionMarkInessentiel = 1000;
    @ConfigEntry.Gui.Excluded
    public int tripleQuestionMarkMaxDistance = 2500;


    public int blockCutOff = 32;

    @ConfigEntry.Gui.Tooltip
    public boolean highlightSpeaker = false;


    @ConfigEntry.Gui.Excluded
    public double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5; // small name?


    @Override
    public boolean isPlayAllSoundsOnPlayer() {
        return playAllSoundsOnPlayer;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public boolean isLogMissingLines() {
        return logMissingLines;
    }

    @Override
    public void setLogMissingLines(boolean input){
        logMissingLines = input;
    }

    @Override
    public boolean isAnonymous() {
        return anonymous;
    }

    @Override
    public void setAnonymous(boolean input){
        anonymous = input;
    }

    @Override
    public boolean isSendFunFact() {
        return sendFunFact;
    }

    @Override
    public int getTripleQuestionMarkInessentiel() {
        return tripleQuestionMarkInessentiel;
    }

    @Override
    public int getSquaredTripleQuestionMarkMaxDistance() {
        return tripleQuestionMarkMaxDistance;
    }

    @Override
    public int getBlockCutOff() {
        return blockCutOff;
    }

    @Override
    public boolean isHighlightSpeaker() {
        return highlightSpeaker;
    }

    @Override
    public double getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid() {
        return npcFinderThingMaxDistanceChangeBeforeCacheInvalid;
    }
    @Override
    public boolean isHasChosenLineReport(){ return hasChosenLineReport; }

    @Override
    public void setHasChosenLineReport(boolean input) {
        hasChosenLineReport = input;
    }


}
