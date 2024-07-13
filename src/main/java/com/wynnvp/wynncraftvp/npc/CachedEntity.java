/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.npc;

import net.minecraft.world.entity.Entity;

public class CachedEntity {
    public Entity name;
    public Entity child;
    public double distance;
    public boolean isArmourStand = false;

    public CachedEntity(Entity nameEntity, Entity childEntity) {
        name = nameEntity;
        child = childEntity;
        isArmourStand = NPCHandler.isArmourStand(childEntity);
        distance = nameEntity.distanceTo(child);
    }

    public CachedEntity() {}
}
