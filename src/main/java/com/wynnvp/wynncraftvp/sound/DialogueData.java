/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import net.minecraft.world.phys.Vec3;

public class DialogueData {
    private String line;
    private String file;
    private boolean onPlayer = false;
    private int fallOff = 0;
    private Vec3 pos = null;
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

    public void setPos(Vec3 pos) {
        this.pos = pos;
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

    public Vec3 getPos() {
        return pos;
    }

    public String getNpc() {
        return npc;
    }

    public Reverb getReverb() {
        return reverb;
    }
}
