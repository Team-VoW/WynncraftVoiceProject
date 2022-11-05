package com.wynnvp.wynncraftvp.npc;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class NPCHandler {

    public static Vec3d findPosition(String rawName) {
        CachedEntity cachedEntity = findEntity(rawName);
        return cachedEntity == null ? null : cachedEntity.child.getEyePos();
    }

    public static CachedEntity findEntity(String rawName) {
        rawName = getName(rawName);

        CachedEntity c = findNPC(rawName);
        if (!isCachedValid(c)) {
            return null;
        }

        return c;
    }

    //Standard find function
    private static CachedEntity findNPC(String rawName) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        Entity entity = getClosestEntityWithName(rawName, player);

        //If no entity was found
        if (entity == null)
            return new CachedEntity();

        Entity childEntity = getHead(entity);
        if (childEntity == null) {
            childEntity = entity;
        }

        CachedEntity cachedEntity = new CachedEntity(entity, childEntity);


        return cachedEntity;
    }

    private static Entity getHead(Entity entity) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        double closestSquaredXYDistance = 1.3;
        Entity childEntity = null;
        Vec3d npcEyePos = entity.getEyePos().add(0, -0.2, 0);


        for (Entity entityInWorld : player.clientWorld.getEntities()) { // iterate over every single entity
            // double dist = entityInWorld.getEyePos().squaredDistanceTo(npcEyePos);
           // Vec3d entityEyePos = entityInWorld.getEyePos();
            double xzBlockDistance = squaredXZDistance(entityInWorld.getEyePos(), npcEyePos);

            if (closestSquaredXYDistance < xzBlockDistance
                    || entityInWorld.getEyePos().y > npcEyePos.y + 0.4) { // find closest
                continue;
            }
            if (!entityInWorld.isInvisible()) {
                closestSquaredXYDistance = xzBlockDistance;
                childEntity = entityInWorld;
                continue;
            }

            if (isArmourStand(entityInWorld)) {
                childEntity = entityInWorld;
                break;
            }
        }

        return childEntity;
    }

    private static double squaredXZDistance(Vec3d first, Vec3d second) {
        double d = first.x - second.x;
        double f = first.z - second.z;
        return d * d + f * f;
    }


    public static boolean isArmourStand(Entity entity) {
        for (ItemStack item : entity.getItemsEquipped()) {
            if (item != null && !item.isEmpty()) {
                return true;
            }
        }
        return false;
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

            String entityName = getName(entityInWorld);

            if (entityName.contains(rawName)) {
                closestDistance = distance;
                entity = entityInWorld;
                continue;
            }

            //This code is to handle playing the sound at the location of the closest ??? character if no npc with the real name was found
            if (entityName.contains("???")) {
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
        return getName(entity.getDisplayName().getString());
    }

    private static String getName(String name) {
        return name.replaceAll("§.", "").toLowerCase().replaceAll("[^a-z?\\d]", "");
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
        assert MinecraftClient.getInstance().player != null;
        if (c.name.getEyePos().distanceTo(MinecraftClient.getInstance().player.getEyePos()) > 200) {
            return false;
        }
        // distance change?
        return !(Math.abs(c.name.distanceTo(c.child) - c.distance) > config.getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid());
    }


}
