package com.wynnvp.wynncraftvp.npc;

import com.wynnvp.wynncraftvp.config.VOWConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class NPCHandler {

    private static class Cached {
        public Entity name;
        public Entity child;
        public double distance;
    }

    private static Map<String, Cached> cache = new HashMap<>();

    // stand find function
    public static Cached findNPC(String rawName) {
        ClientPlayerEntity c = MinecraftClient.getInstance().player;

        double d = 200;
        Entity entity = null;
        assert c != null;
        for (Entity e : c.clientWorld.getEntities()) { // iterate over every single entity
            double dist = e.getPos().distanceTo(c.getEyePos());
            String name = e.getDisplayName().getString().replaceAll("§.", "").replaceAll("§", "").toLowerCase().replaceAll("[^a-z\\d]", "");
            if ((d > dist) && name.equals(rawName)) { // test for it to be what is needed and close
                d = dist;
                entity = e;
            }
        }

        d = 8;
        Entity child = null;
        if (entity != null) {
            Vec3d pos = entity.getEyePos().add(0, -0.2, 0);
            for (Entity e : c.clientWorld.getEntities()) { // iterate over every single entity
                double dist = e.getEyePos().distanceTo(pos);
                if ((d > dist) && !e.isInvisible() && e.getEyePos().y < entity.getEyePos().y + 0.2) { // find closest
                    d = dist;
                    child = e;
                }
            }
        }
        Cached cached = new Cached();
        cached.name = entity;
        cached.child = child;
        cached.distance = cached.name.distanceTo(cached.child);

        return cached;
    }

    public static boolean isCachedValid (Cached c) {
        if (c == null || c.child == null || c.name == null) {
            return false;
        }

        if (!c.name.isAlive()) { // is alive
            return false;
        }
        if (!c.child.isAlive()) { // is alive
            return false;
        }
        if (c.child.isInvisible()) {
            return false;
        }
        // is closer than 200 meters/blocks/generic measurement unit
        if (c.name.getEyePos().distanceTo(MinecraftClient.getInstance().player.getEyePos()) > 200) {
            return false;
        }
        // distance change?
        if (Math.abs(c.name.distanceTo(c.child) - c.distance) > VOWConfig.npcFinderThingMaxDistanceChangeBeforeCacheInvalid) {
            return false;
        }

        return true;
    }

    // This works or not idk
    public static Vec3d find(String rawName) {
        rawName = rawName.replaceAll("§.", "").replaceAll("§", "").toLowerCase().replaceAll("[^a-z\\d]", "");

        Cached c = cache.get(rawName);

        if (isCachedValid(c)) {
            return c.child.getEyePos();
        } else {
            c = findNPC(rawName);
            cache.put(rawName, c);
        }

        return c.child.getEyePos();
    }
}
