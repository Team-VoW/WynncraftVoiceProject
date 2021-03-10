package com.wynnvp.wynncraftvp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class WynnVPMovingSound extends MovingSound {

    public WynnVPMovingSound(SoundEvent soundEvent){
        super(soundEvent, SoundCategory.VOICE);
    }
    @Override
    public void update(){
        //Plays the sound at the player
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        this.xPosF = (float)playerSP.posX;
        this.zPosF = (float)playerSP.posZ;
        this.yPosF = (float)playerSP.posY;
        //TODO Change this to the NPCS location
    }

}
