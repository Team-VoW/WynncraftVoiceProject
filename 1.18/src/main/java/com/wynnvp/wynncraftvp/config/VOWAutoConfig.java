package com.wynnvp.wynncraftvp.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "wynnvp")
public class VOWAutoConfig implements ConfigData {


    //--------------------------------------------------------------
    //Excluded stuff:
    @ConfigEntry.Gui.Excluded
    public String word = "gzog6zilzq6zxlt";

    @ConfigEntry.Gui.Excluded
    public boolean hasChosenLineReport = false;
    @ConfigEntry.Gui.Excluded
    public int tripleQuestionMarkInessentiel = 1000;

    @ConfigEntry.Gui.Excluded
    public double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5; // small name?
    //--------------------------------------------------------------

    public boolean playAllSoundsOnPlayer = false;

    //--------------------------------------------------------------
    //Line reporting settings
    public boolean reportMissingLines = true;
    public boolean anonymous = true;
    public boolean sendFunFact = true;
    //--------------------------------------------------------------

    public int blockCutOff = 32;
    public boolean removeVillagerSounds = false;
    @ConfigEntry.Gui.Tooltip
    public boolean highlightSpeaker = false;


    //--------------------------------------------------------------
    //Logging Settings:
    @ConfigEntry.Gui.Tooltip
    public boolean logDialogueLines = true;
    @ConfigEntry.Gui.Tooltip
    public boolean onlyLogNotPlayingLines = true;
    @ConfigEntry.Gui.Tooltip
    public boolean logPlayingInformation = false;

    //--------------------------------------------------------------


    public boolean isLogDialogueLines() {
        return logDialogueLines;
    }
    public boolean isOnlyLogNotPlayingLines() {
        return onlyLogNotPlayingLines;
    }
    public boolean isLogPlayingInformation(){
        return logPlayingInformation;
    }


    public boolean isRemoveVillagerSounds() {
        return removeVillagerSounds;
    }
    public void setRemoveVillagerSounds(boolean input){
        removeVillagerSounds = input;
    }


    public boolean isPlayAllSoundsOnPlayer() {
        return playAllSoundsOnPlayer;
    }

    public String getWord() {
        return word;
    }

    
    public boolean isReportMissingLines() {
        return reportMissingLines;
    }

    public void setReportMissingLines(boolean input){
        reportMissingLines = input;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    
    public void setAnonymous(boolean input){
        anonymous = input;
    }

    
    public boolean isSendFunFact() {
        return sendFunFact;
    }

    
    public int getTripleQuestionMarkInessentiel() {
        return tripleQuestionMarkInessentiel;
    }


    
    public int getBlockCutOff() {
        return blockCutOff;
    }

    
    public boolean isHighlightSpeaker() {
        return highlightSpeaker;
    }


    public double getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid() {
        return npcFinderThingMaxDistanceChangeBeforeCacheInvalid;
    }
    
    public boolean getHasShownMissingLineNotification(){ return hasChosenLineReport; }

    
    public void setHasShownMissingLineNotification(boolean input) {
        hasChosenLineReport = input;
    }


}
