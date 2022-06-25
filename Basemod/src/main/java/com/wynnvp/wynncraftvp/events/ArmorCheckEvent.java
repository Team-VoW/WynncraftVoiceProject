package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.events.custom.PacketEvent;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.QuestMarkHandler;
import com.wynnvp.wynncraftvp.utils.StringBlacklist;
import com.wynnvp.wynncraftvp.utils.reflection.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class ArmorCheckEvent {

    private static final int DISTANCE_MULTI = 2;
    private static final int BLOCKS_PER_BLOCKS = 53;

    int ticks = 1;

    //EVENT FOR REMOVE OF CHANCE IN 2*53 DISTANCE
    //EVENT IN BETA-TEST
    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent event) {
        if (!ModCore.inServer) return;
        if (NPCHandler.getNamesHandlers().isEmpty()) return;
        if (ticks % 20 == 0) {
            checkArround();
        }
        ticks++;
    }

    //EVENT FOR UPDATE ARMORSTAND VECTOR
    @SubscribeEvent
    public void onPacketTeleport(PacketEvent.Incoming<SPacketEntityTeleport> event) {
        if (!ModCore.inServer) return;

        if (Minecraft.getMinecraft().world == null) {
            System.out.println("(SPacketEntityTeleport) WORLD IS NULL!!!");
            return;
        }
        final Entity entity = Minecraft.getMinecraft().world.getEntityByID(event.getPacket().getEntityId());
        if (entity == null) return;
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand armorStand = (EntityArmorStand) entity;
            updateVector(armorStand);
        }
    }

    @SubscribeEvent
    public void onPacketVelocity(PacketEvent.Incoming<SPacketEntityVelocity> event) {
        if (!ModCore.inServer) return;

        if (Minecraft.getMinecraft().world == null) {
            System.out.println("(SPacketEntityVelocity) WORLD IS NULL!!!");
            return;
        }
        final Entity entity = Minecraft.getMinecraft().world.getEntityByID(event.getPacket().getEntityID());
        if (entity == null) return;
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand armorStand = (EntityArmorStand) entity;
            updateVector(armorStand);
        }
    }

    //EVENT FOR ADD ARMORSTAND IN CACHE
    @SubscribeEvent
    public void onPacketEntity(PacketEvent.Incoming<SPacketEntity> event) {
        if (!ModCore.inServer) return;

        if (Minecraft.getMinecraft().world == null) {
            System.out.println("(SPacketEntity) WORLD IS NULL!!!");
            return;
        }
        final Entity entity = event.getPacket().getEntity(Minecraft.getMinecraft().world);
        if (entity instanceof EntityArmorStand) {
            final boolean visible = ReflectionUtils.isNameVisibleFromMetadata(entity.getDataManager().getAll());
            if (!visible) return;
            final String name = ReflectionUtils.getNameFromMetadata(entity.getDataManager().getAll());
            final EntityArmorStand armorStand = (EntityArmorStand) entity;
            if (name == null || name.isEmpty()) return;
            setNamesHandlerProtocol(armorStand, name);
        }
    }

    @SubscribeEvent
    public void onReceivePacket(PacketEvent.Incoming<SPacketEntityMetadata> event) {
        if (!ModCore.inServer) return;

        if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            Minecraft.getMinecraft().addScheduledTask(() -> onReceivePacket(event));
            return;
        }

        if (event.getPacket().getDataManagerEntries().isEmpty()) return;
        if (Minecraft.getMinecraft().world == null) {
            System.out.println("(SPacketEntityMetadata) WORLD IS NULL!!!");
            return;
        }
        final Entity entity = Minecraft.getMinecraft().world.getEntityByID(event.getPacket().getEntityId());
        if (entity == null) return;
        if (entity instanceof EntityArmorStand) {
            final String name = ReflectionUtils.getNameFromMetadata(event.getPacket().getDataManagerEntries());
            if (name == null || name.isEmpty()) return;
            final EntityArmorStand armorStand = (EntityArmorStand) entity;
            setNamesHandlerProtocol(armorStand, name);
        }
    }

    private void setNamesHandlerProtocol(EntityArmorStand armorStand, String name) {
        String rawName = TextFormatting.getTextWithoutFormattingCodes(name.toLowerCase().trim().replace(" ", "").replace("'", ""));
        if (rawName == null) return;
        if (StringBlacklist.has(rawName)) return;

        //Get rawname if original is ???
        if (rawName.contains("???")) {
            String quest = QuestMarkHandler.getWichQuest().get(rawName);
            if (quest == null || quest.isEmpty()) return;
            //Get by quest
            rawName = ModCore.instance.soundsHandler.getNPCName(quest);
        }

        if (ModCore.instance.soundsHandler.containsName(rawName)) {
            NPCHandler.add(rawName, armorStand.getPositionVector());
        }
    }

    private void checkArround() {
        if (Minecraft.getMinecraft().player == null) return;
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        final Map<String, Vec3d> setToRemove = new HashMap<>();
        for (Map.Entry<String, List<Vec3d>> value : NPCHandler.getNamesHandlers().entrySet()) {
            for (Vec3d vec3d : value.getValue()) {
                final double distance = player.getDistance(vec3d.x, vec3d.y, vec3d.z);
                final int multiply = DISTANCE_MULTI*BLOCKS_PER_BLOCKS;
                if (distance >= multiply || distance <= -(multiply)) {
                    setToRemove.put(value.getKey(), vec3d);
                }
            }
        }
        for (Map.Entry<String, Vec3d> stringVec3dEntry : setToRemove.entrySet()) {
            NPCHandler.getNamesHandlers().get(stringVec3dEntry.getKey()).remove(stringVec3dEntry.getValue());
            if (NPCHandler.getNamesHandlers().get(stringVec3dEntry.getKey()).isEmpty())
                NPCHandler.remove(stringVec3dEntry.getKey());
        }
        setToRemove.clear();
    }

    private void updateVector(EntityArmorStand armorStand) {
        final String name = ReflectionUtils.getNameFromMetadata(armorStand.getDataManager().getAll());
        if (name == null || name.isEmpty()) return;
        String rawName = TextFormatting.getTextWithoutFormattingCodes(name.toLowerCase().trim().replace(" ", "").replace("'", ""));
        if (rawName == null) return;
        if (StringBlacklist.has(rawName)) return;

        //Get rawname if original is ???
        if (rawName.contains("???")) {
            String quest = QuestMarkHandler.getWichQuest().get(rawName);
            if (quest == null || quest.isEmpty()) return;
            //Get by quest
            rawName = ModCore.instance.soundsHandler.getNPCName(quest);
        }

        if (ModCore.instance.soundsHandler.containsName(rawName)) {
            updateVelocity(rawName, armorStand);
        }
    }

    private void updateVelocity(String rawName, EntityArmorStand armorStand) {
        if (NPCHandler.getNamesHandlers().containsKey(rawName)) {
            List<Vec3d> vec3dList = NPCHandler.getNamesHandlers().get(rawName);
            if (vec3dList.isEmpty()) {
                vec3dList.add(armorStand.getPositionVector());
            } else {
                if (vec3dList.size() == 1) {
                    vec3dList.set(0, armorStand.getPositionVector());
                } else {
                    Vec3d approxi = NPCHandler.find(rawName).orElse(null);
                    int result = 0;
                    for (int index = 0; index < vec3dList.size(); index++) {
                        Vec3d now = NPCHandler.getNamesHandlers().get(rawName).get(index);
                        if (approxi == null) continue;
                        if (now.length() == approxi.length()) {
                            result = index;
                            break;
                        }
                    }
                    System.out.println("Result: " + result);
                    vec3dList.set(result, armorStand.getPositionVector());
                }
            }
            NPCHandler.getNamesHandlers().put(rawName, vec3dList);
        } else {
            NPCHandler.add(rawName, armorStand.getPositionVector());
        }
    }

}
