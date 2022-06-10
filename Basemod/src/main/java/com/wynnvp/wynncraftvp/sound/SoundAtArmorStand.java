package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class SoundAtArmorStand extends MovingSound {

    private final String rawName;

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName){
        super(soundEvent, SoundCategory.VOICE);
        this.rawName = getId(rawName).replaceAll("[0-9]", "");
    }

    @Override
    public void update() {
        if (rawName.isEmpty()) return;
        if (NPCHandler.getNamesHandlers().containsKey(rawName)) {
            Vec3d vector = NPCHandler.find(rawName);
            this.xPosF = (float)vector.x;
            this.zPosF = (float)vector.z;
            this.yPosF = (float)vector.y;
            //SoundPlayer.SPEAKING = true;
        }
    }

    private String getId(String name) {
        String id = "???";
        if (name.contains("-")) {
            String[] args = name.split("-");
            id = args[1];
        } else if (name.contains("talkingmushroom")) {
            id = "talkingmushroom";
        }
        return id;
    }


}
