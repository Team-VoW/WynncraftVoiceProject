package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class SoundAtArmorStand extends MovingSound {

    private final String rawName;

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName){
        super(soundEvent, SoundCategory.VOICE);
        this.rawName = rawName;
    }

    @Override
    public void update() {
        if (this.rawName.isEmpty()) {
            return;
        }
        NPCHandler.find(rawName).ifPresent(vector -> {
            this.xPosF = (float)vector.x;
            this.zPosF = (float)vector.z;
            this.yPosF = (float)vector.y;
        });
    }


}
