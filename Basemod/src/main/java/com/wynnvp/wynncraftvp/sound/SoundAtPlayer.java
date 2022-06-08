package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.NPCNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundAtPlayer extends MovingSound {

    public SoundAtPlayer(SoundEvent soundEvent){
        super(soundEvent, SoundCategory.VOICE);
    }

    @Override
    public void update(){
        //Plays the sound at the player
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        this.xPosF = (float)playerSP.posX;
        this.zPosF = (float)playerSP.posZ;
        this.yPosF = (float)playerSP.posY;
    }

}
