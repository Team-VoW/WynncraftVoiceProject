/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.npc.CachedEntity;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import java.util.Optional;
import net.minecraft.world.phys.Vec3;

public class CurrentSpeaker {
    private CachedEntity cachedEntity;
    public String rawName = "";
    private Optional<Vec3> lastEntityPos;

    private Optional<Vec3> setPosition;

    public CurrentSpeaker() {
        lastEntityPos = Optional.empty();
        setPosition = Optional.empty();
    }

    public CurrentSpeaker(String name, Optional<Vec3> pos) {
        lastEntityPos = Optional.empty();
        setPosition = Optional.empty();
        setNpc(name, pos);
    }

    public void setNpc(String name, Optional<Vec3> pos) {
        pos.ifPresentOrElse(
                p -> {
                    if (pos.get().x == 0 && pos.get().y == 0 && pos.get().z == 0) {
                        setPosition = Optional.empty();
                    } else {
                        setPosition = pos;
                    }
                },
                () -> {
                    setPosition = Optional.empty();
                });

        setPosition = pos;
        rawName = name.toLowerCase().replace(" ", "");
        cachedEntity = null;
    }

    public Optional<Vec3> getUpdatedPosition() {
        if (setPosition.isPresent()) {
            return setPosition;
        }

        if (this.rawName.isEmpty()) {
            return Optional.empty();
        }
        if (!NPCHandler.isCachedValid(cachedEntity)) cachedEntity = NPCHandler.findEntity(rawName);

        // Was not able to find the entity, so just let the sound keep on playing where it is.
        if (cachedEntity == null) {
            if (lastEntityPos.isEmpty()) {
                return Optional.empty();
            }

        } else {
            lastEntityPos = Optional.of(cachedEntity.child.getEyePosition());
        }

        return lastEntityPos;
    }
}
