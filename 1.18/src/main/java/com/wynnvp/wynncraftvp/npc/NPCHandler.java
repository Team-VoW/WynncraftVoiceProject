package com.wynnvp.wynncraftvp.npc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class NPCHandler {

    private static class CachedEntity {
        public Entity name;
        public Entity child;
        public double distance;

        public boolean isArmourStand = false;
    }

    private static final Map<String, CachedEntity> cache = new HashMap<>();

    public static void clearCache() {
        cache.clear();
    }

    // stand find function
    public static CachedEntity findNPC(String rawName) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        Vec3d playerEyePos = player.getEyePos();

        //Set to really high as a start value to find a closer one
        double closestDistance = 20000;
        Entity entity = null;
        for (Entity entityInWorld : player.clientWorld.getEntities()) { // iterate over every single entity
            double distance = entityInWorld.getPos().distanceTo(playerEyePos);
            String name = entityInWorld.getDisplayName().getString().replaceAll("§.", "").toLowerCase().replaceAll("[^a-z?\\d]", "");
            if (name.contains("???")) {
                if (distance > config.getTripleQuestionMarkInessentiel()) {
                    continue;
                }
                distance += 1;
                distance *= config.getTripleQuestionMarkInessentiel();
            }
            if ((closestDistance > distance) && name.contains(rawName)) {
                closestDistance = distance;
                entity = entityInWorld;
            }
        }

        closestDistance = 8;
        Entity child = null;
        boolean isArmourStand = false;
        if (entity != null) {
            Vec3d pos = entity.getEyePos().add(0, -0.2, 0);
            for (Entity e : player.clientWorld.getEntities()) { // iterate over every single entity
                double dist = e.getEyePos().distanceTo(pos);
                if ((closestDistance > dist) && e.getEyePos().y < entity.getEyePos().y + 0.2) { // find closest
                    if (!e.isInvisible()) {
                        closestDistance = dist;
                        child = e;
                    } else {
                        for (ItemStack item : e.getItemsEquipped()) {
                            if (item != null && !item.isEmpty()) {
                                closestDistance = dist;
                                child = e;
                                isArmourStand = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        CachedEntity cachedEntity = new CachedEntity();
        if (entity != null) {
            if (child == null) {
                child = entity;
            }

            cachedEntity.name = entity;
            cachedEntity.child = child;
            cachedEntity.isArmourStand = isArmourStand;
            cachedEntity.distance = entity.distanceTo(cachedEntity.child);
        }

        return cachedEntity;
    }

    public static boolean isCachedValid(CachedEntity c) {
        if (c == null || c.child == null || c.name == null) {
            return false;
        }

        if (!c.name.isAlive()) { // is alive
            return false;
        }
        if (!c.child.isAlive()) { // is alive
            return false;
        }
        if (c.child.isInvisible() && !c.isArmourStand) {
            return false;
        }
        // is closer than 200 meters/blocks/generic measurement unit
        if (c.name.getEyePos().distanceTo(MinecraftClient.getInstance().player.getEyePos()) > 200) {
            return false;
        }
        // distance change?
        return !(Math.abs(c.name.distanceTo(c.child) - c.distance) > config.getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid());
    }

    // This works or not idk
    public static Vec3d find(String rawName) {
        rawName = rawName.replaceAll("§.", "").toLowerCase().replaceAll("[^a-z?\\d]", "");

        CachedEntity c = cache.get(rawName);

        if (isCachedValid(c)) {
            return c.child.getEyePos();
        } else {
            c = findNPC(rawName);
            if (!isCachedValid(c)) {
                return null;
            }
            cache.put(rawName, c);
        }

        return c.child.getEyePos();
    }
}
