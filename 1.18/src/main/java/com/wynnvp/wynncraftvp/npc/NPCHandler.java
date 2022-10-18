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
        Entity entity = getClosestEntityWithName(rawName, player);

        //If no entity was found
        if (entity == null)
            return new CachedEntity();

        double closestDistance = 64;
        Entity childEntity = null;
        boolean isArmourStand = false;
        Vec3d npcEyePos = entity.getEyePos().add(0, -0.2, 0);
        for (Entity entityInWorld : player.clientWorld.getEntities()) { // iterate over every single entity
            double dist = entityInWorld.getEyePos().squaredDistanceTo(npcEyePos);

            if (closestDistance < dist || entityInWorld.getEyePos().y > npcEyePos.y + 0.4) { // find closest
                continue;
            }
            if (!entityInWorld.isInvisible()) {
                closestDistance = dist;
                childEntity = entityInWorld;
            } else {
                for (ItemStack item : entityInWorld.getItemsEquipped()) {
                    if (item != null && !item.isEmpty()) {
                        closestDistance = dist;
                        childEntity = entityInWorld;
                        isArmourStand = true;
                        break;
                    }
                }
            }
        }


        CachedEntity cachedEntity = new CachedEntity();
        if (childEntity == null) {
            childEntity = entity;
        }

        cachedEntity.name = entity;
        cachedEntity.child = childEntity;
        cachedEntity.isArmourStand = isArmourStand;
        cachedEntity.distance = entity.distanceTo(cachedEntity.child);


        return cachedEntity;
    }

    private static Entity getClosestEntityWithName(String rawName, ClientPlayerEntity player) {


        Vec3d playerEyePos = player.getEyePos();

        //Set to really high as a start value to find a closer one
        double closestDistance = 20000;
        Entity entity = null;
        for (Entity entityInWorld : player.clientWorld.getEntities()) { // iterate over every single entity
            double distance = entityInWorld.getPos().squaredDistanceTo(playerEyePos);

            //This entity is further away then the closest one
            if (closestDistance < distance)
                continue;

            String name = getName(entityInWorld);

            if (name.contains(rawName)) {
                closestDistance = distance;
                entity = entityInWorld;
                continue;
            }

            //This code is to handle playing the sound at the location of the closest ??? character if no npc with the real name was found
            if (name.contains("???")) {
                //Adds a big settable number so that it is only played at this ??? character if there really is no other with the real name
                distance += config.getTripleQuestionMarkInessentiel();
                //If there is a closer entity with the real name or with ??? name then continue
                if (closestDistance < distance)
                    continue;
                closestDistance = distance;
                entity = entityInWorld;
            }
        }
        return entity;
    }

    private static String getName(Entity entity) {
        return entity.getDisplayName().getString().replaceAll("§.", "").toLowerCase().replaceAll("[^a-z?\\d]", "");
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
