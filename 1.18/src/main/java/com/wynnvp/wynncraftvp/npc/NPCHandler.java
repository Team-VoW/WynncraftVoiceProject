package com.wynnvp.wynncraftvp.npc;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class NPCHandler {

    public static Vec3 findPosition(String rawName) {
        CachedEntity cachedEntity = findEntity(rawName);
        return cachedEntity == null ? null : cachedEntity.child.getEyePosition();
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
        Player player = Minecraft.getInstance().player;
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
        Player player = Minecraft.getInstance().player;
        assert player != null;
        double closestSquaredXYDistance = 1.3;
        Entity childEntity = null;
        Vec3 npcEyePos = entity.getEyePosition().add(0, -0.2, 0);


        for (Entity entityInWorld : (Minecraft.getInstance().level.entitiesForRendering())) { // iterate over every single entity
            // double dist = entityInWorld.getEyePos().squaredDistanceTo(npcEyePos);
            // Vec3 entityEyePos = entityInWorld.getEyePos();
            double xzBlockDistance = squaredXZDistance(entityInWorld.getEyePosition(), npcEyePos);

            if (closestSquaredXYDistance < xzBlockDistance
                    || entityInWorld.getEyePosition().y > npcEyePos.y + 0.4) { // find closest
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

    private static double squaredXZDistance(Vec3 first, Vec3 second) {
        double d = first.x - second.x;
        double f = first.z - second.z;
        return d * d + f * f;
    }


    public static boolean isArmourStand(Entity entity) {
        return entity instanceof net.minecraft.world.entity.decoration.ArmorStand;
    }


    private static Entity getClosestEntityWithName(String rawName, Player player) {
        Vec3 playerEyePos = player.getEyePosition();

        //Set to really high as a start value to find a closer one
        double closestDistance = 20000;
        Entity entity = null;
        for (Entity entityInWorld : Minecraft.getInstance().level.entitiesForRendering()) { // iterate over every single entity
            double distance = entityInWorld.position().distanceToSqr(playerEyePos);

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
        if (c.name.getEyePosition().distanceTo(Minecraft.getInstance().player.getEyePosition()) > 200) {
            return false;
        }
        // distance change?
        return !(Math.abs(c.name.distanceTo(c.child) - c.distance) > config.getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid());
    }


}
