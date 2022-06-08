package com.wynnvp.wynncraftvp.npc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@AllArgsConstructor
public class NPCHandler {

    @Getter
    private static final Set<NPCHandler> namesHandlers = new HashSet<>();

    public static NPCHandler add(String name, int x, int y, int z) {
        return add(name, new Vec3d(x, y, z));
    }

    public static NPCHandler add(String name, Vec3d vector) {
        return new NPCHandler(name, vector).create();
    }

    public static void remove(NPCHandler namesHandler) {
        namesHandlers.remove(namesHandler);
    }

    public static Optional<NPCHandler> find(String rawNames) {
        return namesHandlers.stream().filter(f -> f.getName().equalsIgnoreCase(rawNames)).sorted((o1, o2) ->
                (int) (o1.getVector().length() - o2.getVector().length())).findAny();
    }

    public static Optional<NPCHandler> findAll(String rawNames) {
        return namesHandlers.stream().filter(f -> f.getName().equalsIgnoreCase(rawNames)).findAny();
    }

    public static Optional<NPCHandler> find(Vec3d vector) {
        return namesHandlers.stream().filter(f -> f.getVector().equals(vector)).findAny();
    }

    public static Optional<NPCHandler> find(NPCNames npcNames) {
        return find(npcNames.getRawName());
    }

    //OBJECT
    private final String name;

    @Setter
    private Vec3d vector;

    public NPCHandler create() {
        namesHandlers.remove(this);
        namesHandlers.add(this);
        return this;
    }

}
