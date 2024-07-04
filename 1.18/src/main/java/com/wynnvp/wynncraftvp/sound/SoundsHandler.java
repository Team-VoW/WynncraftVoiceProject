package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;
import java.util.Optional;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

public class SoundsHandler {

    private final HashMap<String, SoundObject> sounds;

    public SoundsHandler() {
        sounds = new HashMap<>();

        Sounds.register(this);
    }

    public static SoundEvent registerSound(String name) {

        ResourceLocation id = new ResourceLocation(ModCore.MODID, name.toLowerCase());
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

    public void addSound(String message, String id, boolean movingSound, Vector3 position) {
        addSound(message, id, movingSound, position, 0);
    }

    public void addSound(String message, String id, boolean movingSound, int fallOff) {
        addSound(message, id, movingSound, null, fallOff);
    }


    public void addSound(String message, String id, boolean movingSound, Vector3 position, int fallOff) {

        LineData lineData = formatToLineData(message);
        message = lineData.getSoundLine();
        sounds.put(message, new SoundObject(lineData.getNPCName(), id, new CustomSoundClass(registerSound(id), movingSound), position, fallOff));
    }


    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }

}

