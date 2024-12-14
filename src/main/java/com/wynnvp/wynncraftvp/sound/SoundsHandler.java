/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

import com.wynnvp.wynncraftvp.sound.line.LineData;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.world.phys.Vec3;

public class SoundsHandler {
    private final HashMap<String, SoundObject> sounds = new HashMap<>();

    public SoundsHandler() {
        Sounds.register(this);
    }

    /**
     * Method to add the sounds to the system
     *
     * @param message     Identification message
     * @param id          Identification sound
     * @param movingSound false if the sound moves with the player,
     *                    otherwise it will move with the ArmorStand
     */
    public void addSound(String message, String id, boolean movingSound) {
        addSound(message, id, movingSound, null);
    }

    public void addSound(String message, String id, boolean movingSound, Vec3 position) {
        addSound(message, id, movingSound, position, 0);
    }

    public void addSound(String message, String id, boolean movingSound, int fallOff) {
        addSound(message, id, movingSound, null, fallOff);
    }

    // If position is 0 null use default. If falloff is 0 use default
    public void addSound(String message, String id, boolean movingSound, Vec3 position, int fallOff) {
        LineData lineData = formatToLineData(message);
        sounds.put(lineData.getSoundLine(), new SoundObject(lineData.getNPCName(), id, movingSound, position, fallOff));
    }

    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }
}
