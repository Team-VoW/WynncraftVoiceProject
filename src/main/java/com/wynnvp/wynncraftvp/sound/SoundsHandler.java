/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.joml.Vector3f;

public class SoundsHandler {
    private final HashMap<String, SoundObject> sounds = new HashMap<>();

    public SoundsHandler() {
        Sounds.register(this);
    }

    public static SoundEvent registerSound(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ModCore.MODID, name.toLowerCase());
        SoundEvent se = SoundEvent.createVariableRangeEvent(id);

        Registry.register(BuiltInRegistries.SOUND_EVENT, id, se);

        return se;
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

    public void addSound(String message, String id, boolean movingSound, Vector3f position) {
        addSound(message, id, movingSound, position, 0);
    }

    public void addSound(String message, String id, boolean movingSound, int fallOff) {
        addSound(message, id, movingSound, null, fallOff);
    }

    // If position is 0 null use default. If falloff is 0 use default
    public void addSound(String message, String id, boolean movingSound, Vector3f position, int fallOff) {
        LineData lineData = formatToLineData(message);
        sounds.put(
                lineData.getSoundLine(),
                new SoundObject(
                        lineData.getNPCName(),
                        id,
                        new CustomSoundClass(registerSound(id), movingSound),
                        position,
                        fallOff));
    }

    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }
}
