package com.wynnvp.wynncraftvp.sound;

public class DialogueData {
    private String dialogueLine;
    private String fileName;
    private boolean shouldPlayOnPlayer = false;
    private int customSoundFallOff = 0;
    private Vector3 customPosition = null;
    private String npcName;
    private Environment environment = Environment.OUTSIDE;

    public void setDialogueLine(String dialogueLine) {
        this.dialogueLine = dialogueLine;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setShouldPlayOnPlayer(boolean shouldPlayOnPlayer) {
        this.shouldPlayOnPlayer = shouldPlayOnPlayer;
    }

    public void setCustomSoundFallOff(int customSoundFallOff) {
        this.customSoundFallOff = customSoundFallOff;
    }

    public void setCustomPosition(Vector3 customPosition) {
        this.customPosition = customPosition;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getDialogueLine() {
        return dialogueLine;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isShouldPlayOnPlayer() {
        return shouldPlayOnPlayer;
    }

    public int getCustomSoundFallOff() {
        return customSoundFallOff;
    }

    public Vector3 getCustomPosition() {
        return customPosition;
    }

    public String getNpcName() {
        return npcName;
    }

    public Environment getEnvironment() {
        return environment;
    }
}

