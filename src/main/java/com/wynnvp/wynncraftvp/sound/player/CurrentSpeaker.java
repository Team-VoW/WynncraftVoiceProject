package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.npc.CachedEntity;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class CurrentSpeaker {

    private CachedEntity cachedEntity;

    private String rawName = "";

    private Vec3 lastEntityPos;


    public void setNpc(String name){
        rawName = name.toLowerCase().replace(" ", "");
        cachedEntity = null;
    }

    public Optional<Vec3> getUpdatedPosition(){
        if (this.rawName.isEmpty()) {
            return Optional.empty();
        }
        if (!NPCHandler.isCachedValid(cachedEntity))
            cachedEntity = NPCHandler.findEntity(rawName);

        //Was not able to find the entity, so just let the sound keep on playing where it is.
        if (cachedEntity == null){
            if (lastEntityPos == null){
                return Optional.empty();
            }

        } else {
            lastEntityPos = cachedEntity.child.getEyePosition();
        }

        return Optional.of(lastEntityPos);
    }
}
