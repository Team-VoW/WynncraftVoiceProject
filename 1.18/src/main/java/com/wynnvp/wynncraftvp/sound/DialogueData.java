package com.wynnvp.wynncraftvp.sound;

public class DialogueData {
    private String line;
    private String file;
    private boolean onPlayer = false;
    private int fallOff = 0;
    private Vector3 position = null;
    private String npc;
    private Reverb reverb = Reverb.OUTSIDE;

    public void setDialogueLine(String dialogueLine) {
        this.line = dialogueLine;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setOnPlayer(boolean onPlayer) {
        this.onPlayer = onPlayer;
    }

    public void setFallOff(int fallOff) {
        this.fallOff = fallOff;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setNpc(String npc) {
        this.npc = npc;
    }

    public void setReverb(Reverb reverb) {
        this.reverb = reverb;
    }

    public String getDialogueLine() {
        return line;
    }

    public String getFile() {
        return file;
    }

    public boolean isOnPlayer() {
        return onPlayer;
    }

    public int getFallOff() {
        return fallOff;
    }

    public Vector3 getPosition() {
        return position;
    }

    public String getNpc() {
        return npc;
    }

    public Reverb getReverb() {
        return reverb;
    }
}

