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
        if (rawName.isEmpty()) {
            EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
            this.xPosF = (float)playerSP.posX;
            this.zPosF = (float)playerSP.posZ;
            this.yPosF = (float)playerSP.posY;
            return;
        }
        if (NPCHandler.getNamesHandlers().containsKey(rawName)) {
            Vec3d vector = NPCHandler.find(rawName);
            this.xPosF = (float)vector.x;
            this.zPosF = (float)vector.z;
            this.yPosF = (float)vector.y;
            //SoundPlayer.SPEAKING = true;
        } else {
            EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
            this.xPosF = (float)playerSP.posX;
            this.zPosF = (float)playerSP.posZ;
            this.yPosF = (float)playerSP.posY;
        }
    }


}
