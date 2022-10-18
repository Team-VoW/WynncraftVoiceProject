package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

public class SoundsHandler {

    //private final List<SoundObject> sounds;
    private final HashMap<String, SoundObject> sounds;
    private final Set<String> npcNames;

    public SoundsHandler() {
        sounds = new HashMap<>();
        npcNames = new HashSet<>();

        Sounds.register(this);
    }

    public static SoundEvent registerSound(String name) {

        Identifier id = new Identifier(ModCore.MODID, name.toLowerCase());
        SoundEvent se = new SoundEvent(id);

        Registry.register(Registry.SOUND_EVENT, id, se);

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

    public void addSound(String message, String id, boolean movingSound, Vec3d position) {

        addSound(message, id, movingSound, position, 0);
    }

    //If position is 0 null use default. If falloff is 0 use default
    public void addSound(String message, String id, boolean movingSound, Vec3d position, int fallOff) {

        LineData lineData = formatToLineData(message);
        npcNames.add(lineData.getNPCName());
        message = lineData.getSoundLine();
        sounds.put(message, new SoundObject(lineData.getNPCName(), id, new CustomSoundClass(registerSound(id), movingSound), position, fallOff));
    }

    public boolean containsName(String rawName) {
        return npcNames.contains(rawName);
    }

    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }

}

