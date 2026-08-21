/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

/**
 * Turns Minecraft's own Directional Audio setting on.
 *
 * <p>Exists so the nudge in {@link com.wynnvp.wynncraftvp.sound.player.OpenAlPlayer} can be a single
 * click rather than a set of directions through the options menus. It sets the vanilla option, nothing
 * more: Minecraft's own value change handler is what rebuilds the sound engine with HRTF enabled, so
 * the options screen keeps telling the truth and the mod is not left overriding a setting behind the
 * player's back.
 *
 * <p>The name is deliberately unlisted anywhere else. It is a button, not a feature.
 */
public class DirectionalAudioCommand {
    public static final String NAME = "vow-directional-audio";

    public static void register(CommandDispatcher<VowCommandSource> dispatcher) {
        LiteralArgumentBuilder<VowCommandSource> builder = VowCommands.literal(NAME)
                .executes(context -> {
                    Minecraft client = context.getSource().getClient();
                    // Deferred for the same reason as the settings screen command: the chat screen is
                    // closing around us, and setting this reloads the sound engine underneath it.
                    client.execute(() -> enable(client));
                    return 1;
                });

        dispatcher.register(builder);
    }

    private static void enable(Minecraft client) {
        Options options = client.options;
        if (options.directionalAudio().get()) {
            Utils.sendMessage("Directional Audio is already on.");
            return;
        }

        // Setting it is the whole job. Minecraft's own handler for this option calls
        // SoundManager.reload(), which rebuilds the OpenAL context with ALC_HRTF_SOFT enabled.
        options.directionalAudio().set(true);
        options.save();
        Utils.sendMessage("Directional Audio is now §aon§r. Voices will sound like they come from where "
                + "the NPC is standing. It works best with headphones; if you play on speakers and it "
                + "sounds worse, turn it back off in Options -> Music & Sounds.");
    }
}
