/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.core.Manager;
import com.wynnvp.wynncraftvp.core.Managers;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueHolder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundManager extends Manager {
    // The path of the file that contains the sound information, currently as a bundled resource
    private static final String SOUND_INFO_FILE = "/assets/wynnvp/sound_info.json";

    private DialogueHolder dialogueHolder = DialogueHolder.EMPTY;

    public SoundManager() {
        super(List.of());

        // Load the sound info from the json file
        loadSounds();

        // Register the sounds as sound events
        registerSounds();
    }

    private void loadSounds() {
        try (InputStream resourceAsStream = SoundManager.class.getResourceAsStream(SOUND_INFO_FILE)) {
            if (resourceAsStream == null) {
                ModCore.error("Could not find bundled sound info file, sounds will not be loaded.");
                return;
            }

            DialogueHolder dialogueHolder =
                    Managers.Json.GSON.fromJson(new InputStreamReader(resourceAsStream), DialogueHolder.class);

            if (dialogueHolder == null) {
                ModCore.error("Could not load sound info file, sounds will not be loaded.");
                return;
            }

            this.dialogueHolder = dialogueHolder;
            ModCore.info("Loaded %d dialogue sounds."
                    .formatted(dialogueHolder.dialogues().count()));
        } catch (Exception e) {
            ModCore.error("Error loading sound info file: " + e.getMessage());
        }
    }

    private void registerSounds() {
        dialogueHolder.dialogues().forEach(dialogueData -> {
            ResourceLocation resourceLocation =
                    ResourceLocation.fromNamespaceAndPath(ModCore.MODID, dialogueData.dialogueId());
            SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(resourceLocation);
            Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, soundEvent);
        });
    }
}
