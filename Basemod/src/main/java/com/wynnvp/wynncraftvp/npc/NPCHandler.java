package com.wynnvp.wynncraftvp.npc;

import com.google.common.collect.Comparators;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class NPCHandler {

    private static final Map<String, List<Vec3d>> namesHandlers = new HashMap<>();

    public static void add(String name, Vec3d vector) {
        if (namesHandlers.containsKey(name)) {
            List<Vec3d> list = namesHandlers.get(name);
            if (list.contains(vector)) return;
            list.add(new Vec3d((int)vector.x, (int)vector.y, (int)vector.z));
            namesHandlers.put(name, list);
        } else {
            namesHandlers.put(name, new ArrayList<>(Collections.singletonList(new Vec3d((int) vector.x, (int) vector.y, (int) vector.z))));
        }
    }

    //Get the closest armorstand
    //WARNING: System in test
    public static Optional<Vec3d> find(String rawNames) {
        Vec3d result;
        /*Vec3d empty = new Vec3d(0, 0, 0);
        if (Minecraft.getMinecraft().player != null) {
            empty = Minecraft.getMinecraft().player.getPositionVector();
        }*/

        final List<Vec3d> list = namesHandlers.get(rawNames);
        if (Minecraft.getMinecraft().player == null) {
            return list.stream().findAny();
        }
        final Vec3d playerPosition = Minecraft.getMinecraft().player.getPositionVector();
        try {
            result = list.stream()
                    .sorted(Comparator.comparingDouble(o -> o.distanceTo(playerPosition)))
                    .findAny().orElse(null);
        } catch (IllegalArgumentException exception) {
            result = list.stream().findAny().orElse(null);
        }
        return Optional.ofNullable(result);
    }

    /*public static Vec3d find(String rawNames) {
        return namesHandlers.get(rawNames).stream().findAny().orElse(new Vec3d(0, 0, 0));
    }*/

    public static void remove(String name) {
        namesHandlers.remove(name);
    }

    public static Map<String, List<Vec3d>> getNamesHandlers() {
        return namesHandlers;
    }
}
