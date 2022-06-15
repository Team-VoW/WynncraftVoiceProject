package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class SoundAtArmorStand extends MovingSound {

    private final Vec3d vector;

    public SoundAtArmorStand(SoundEvent soundEvent, Vec3d vector){
        super(soundEvent, SoundCategory.VOICE);
        this.vector = vector;
    }

    @Override
    public void update() {
        if (this.vector == null) {
            return;
        }
        this.xPosF = (float)this.vector.x;
        this.zPosF = (float)this.vector.z;
        this.yPosF = (float)this.vector.y;
    }


}
