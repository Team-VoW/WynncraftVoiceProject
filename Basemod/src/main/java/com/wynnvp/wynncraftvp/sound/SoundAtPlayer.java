package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.NPCNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SoundAtPlayer extends MovingSound {

    public SoundAtPlayer(SoundEvent soundEvent){
        super(soundEvent, SoundCategory.VOICE);
    }

    @Override
    public void update() {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        this.xPosF = (float)playerSP.posX;
        this.zPosF = (float)playerSP.posZ;
        this.yPosF = (float)playerSP.posY;
    }


}
