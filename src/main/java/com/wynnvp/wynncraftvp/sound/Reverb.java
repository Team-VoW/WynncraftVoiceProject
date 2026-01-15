/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

/**
 * Reverb environment types ordered from least to most reverberant.
 * Each enum has an explicit ID for stable serialization/deserialization.
 * WARNING: Do not change the ID values as this will break backward compatibility with existing data.
 */
public enum Reverb {
    OUTSIDE(0), // Open field (1.5s decay)
    FOREST(1), // Trees dampening (1.2s decay)
    FURNISHED_ROOM(2), // Small room with furniture (0.4s decay)
    EMPTY_ROOM(3), // Small hard surfaces (1.1s decay)
    HALLWAY(4), // Narrow corridor (0.8s decay)
    LARGE_HALL(5), // Throne room/banquet hall (3.5s decay)
    SMALL_CAVE(6), // Confined underground (2.1s decay)
    CAVE(7), // Medium cavern (2.9s decay)
    BIG_CAVE(8), // Large cavern (4.3s decay)
    CATHEDRAL(9); // Massive space (7.2s decay)

    private final int id;

    Reverb(int id) {
        this.id = id;
    }

    /**
     * Gets the stable integer ID for this reverb type.
     * This ID is used for serialization and must not change.
     */
    public int getId() {
        return id;
    }

    /**
     * Looks up a Reverb enum by its integer ID.
     * @param id The reverb ID (0-9)
     * @return The corresponding Reverb enum, or null if not found
     */
    public static Reverb fromId(int id) {
        for (Reverb reverb : values()) {
            if (reverb.id == id) {
                return reverb;
            }
        }
        return null;
    }
}
