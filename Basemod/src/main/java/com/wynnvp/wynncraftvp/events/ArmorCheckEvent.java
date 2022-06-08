package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.events.custom.PacketEvent;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.NPCNames;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.utils.StringBlacklist;
import com.wynnvp.wynncraftvp.utils.reflection.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArmorCheckEvent {

    private static final int DISTANCE_MULTI = 2;
    private static final int BLOCKS_PER_BLOCKS = 53;

    int ticks = 1;

    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent event) {
        if (!ModCore.inServer) return;
        if (NPCHandler.getNamesHandlers().isEmpty()) return;
        if (ticks % 20 == 0) {
            checkArround();
        }
        ticks++;
    }

    @SubscribeEvent
    public void onPacketTeleport(PacketEvent.Incoming<SPacketEntityTeleport> event) {
        if (!ModCore.inServer) return;

        final Entity entity = Minecraft.getMinecraft().world.getEntityByID(event.getPacket().getEntityId());
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand armorStand = (EntityArmorStand) entity;
            final String rawName = getRawName(armorStand);
            if (rawName == null) return;
            if (NPCNames.contains(rawName)) {
                if (NPCHandler.find(rawName).isPresent()) {
                    NPCHandler namesHandler = NPCHandler.find(rawName).get();
                    namesHandler.setVector(armorStand.getPositionVector());
                } else {
                    NPCHandler.add(rawName, armorStand.getPositionVector());
                }
            }
        }
    }

    @SubscribeEvent
    public void onPacketEntity(PacketEvent.Incoming<SPacketEntity> event) {
        if (!ModCore.inServer) return;

        final Entity entity = event.getPacket().getEntity(Minecraft.getMinecraft().world);
        setNamesHandlerProtocol(entity, entity.getDataManager().getAll());
    }

    @SubscribeEvent
    public void onReceivePacket(PacketEvent.Incoming<SPacketEntityMetadata> event) {
        if (!ModCore.inServer) return;

        if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            Minecraft.getMinecraft().addScheduledTask(() -> onReceivePacket(event));
            return;
        }

        if (event.getPacket().getDataManagerEntries().isEmpty()) return;
        final Entity entity = Minecraft.getMinecraft().world.getEntityByID(event.getPacket().getEntityId());
        setNamesHandlerProtocol(entity, event.getPacket().getDataManagerEntries());
    }

    private void setNamesHandlerProtocol(Entity entity, List<EntityDataManager.DataEntry<?>> dataManagerEntries) {
        if (entity == null) return;
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand armorStand = (EntityArmorStand) entity;

            final boolean visible = ReflectionUtils.isNameVisibleFromMetadata(dataManagerEntries);
            if (!visible) return;
            final String name = ReflectionUtils.getNameFromMetadata(dataManagerEntries);
            if (name == null || name.isEmpty()) return;
            if (StringBlacklist.has(name)) return;
            final String rawName = TextFormatting.getTextWithoutFormattingCodes(name.toLowerCase().trim());
            if (rawName == null) return;
            if (NPCNames.contains(rawName)) {
                if (SoundPlayer.SPEAKING) {
                    if (NPCHandler.find(rawName).isPresent()) {
                        NPCHandler namesHandler = NPCHandler.find(rawName).get();
                        namesHandler.setVector(armorStand.getPositionVector());
                    } else {
                        NPCHandler.add(rawName, armorStand.getPositionVector());
                    }
                } else {
                    if (!NPCHandler.find(rawName).isPresent()) {
                        NPCHandler.add(rawName, armorStand.getPositionVector());
                    } else if (!NPCHandler.find(armorStand.getPositionVector()).isPresent()) {
                        NPCHandler.add(rawName, armorStand.getPositionVector());
                    }
                }
            }/* else if (!StringBlacklist.has(rawName)) {
                if (name.contains(TextFormatting.DARK_GREEN.toString()) || name.contains("Citizen")) {
                    if (!NamesHandler.find(rawName).isPresent()) {
                        NamesHandler.add(rawName, armorStand.getPositionVector());
                        if (McIf.player() != null) {
                            McIf.player().sendMessage(new TextComponentString("Found name: " + name));
                        }
                    } else if (!NamesHandler.find(armorStand.getPositionVector()).isPresent()) {
                        NamesHandler.add(rawName, armorStand.getPositionVector());
                        if (McIf.player() != null) {
                            McIf.player().sendMessage(new TextComponentString("Found name: " + name));
                        }
                    }
                }
            }*/
        }
    }

    private void checkArround() {
        if (Minecraft.getMinecraft().player == null) return;
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        final Set<NPCHandler> setToRemove = new HashSet<>();
        NPCHandler.getNamesHandlers().stream().filter(namesHandler -> {
            final double distance = player.getPositionVector().distanceTo(namesHandler.getVector());
            final int multiply = DISTANCE_MULTI*BLOCKS_PER_BLOCKS;
            return distance >= multiply || distance <= -(multiply);
        }).forEach(setToRemove::add);
        NPCHandler.getNamesHandlers().removeAll(setToRemove);
        setToRemove.clear();
    }

    private String getRawName(Entity entity) {
        final boolean visible = ReflectionUtils.isNameVisibleFromMetadata(entity.getDataManager().getAll());
        if (!visible) return null;
        final String name = ReflectionUtils.getNameFromMetadata(entity.getDataManager().getAll());
        if (name == null || name.isEmpty()) return null;
        return TextFormatting.getTextWithoutFormattingCodes(name.toLowerCase().trim());
    }

}
