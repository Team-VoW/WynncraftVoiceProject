/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

/**
 * Handles Wynncraft overlay-based dialogue (used in areas like Fruma).
 * Kept entirely separate from the existing chat-based dialogue system.
 *
 * <p>Overlay packets arrive as a typewriter animation — the body text grows character by character.
 * We wait for it to stabilise before logging the completed line.
 */
public final class OverlayHandler {
    private static final String OVERLAY_BODY_FONT = "dialogue/text/wynncraft/body";
    private static final String OVERLAY_NAMEPLATE_FONT = "dialogue/text/nameplate";

    // Ticks the body text must remain unchanged before we consider the typewriter done.
    private static final int OVERLAY_STABILITY_TICKS = 5;

    // Minimum formatted-key length before attempting prefix lookup.
    // Avoids useless queries when only the NPC name has arrived.
    private static final int MIN_EARLY_PLAY_PREFIX_LENGTH = 10;

    private String pendingBody = null;
    private String pendingNpc = null;
    private long lastBodyChangeTick = -1;
    private long lastOverlayPacketTick = -1;
    private String lastFiredText = null;
    private boolean earlyPlayed = false;
    private String lastEarlyPlayedKey = null;

    public void onConnectionChange() {
        pendingBody = null;
        pendingNpc = null;
        lastBodyChangeTick = -1;
        lastOverlayPacketTick = -1;
        lastFiredText = null;
        earlyPlayed = false;
        lastEarlyPlayedKey = null;
    }

    public void onTick() {
        if (pendingBody == null) return;

        long currentTick = Utils.mc().level.getGameTime();

        if (lastBodyChangeTick > 0 && currentTick >= lastBodyChangeTick + OVERLAY_STABILITY_TICKS) {
            firePending();
            return;
        }

        if (lastOverlayPacketTick > 0 && currentTick >= lastOverlayPacketTick + OVERLAY_STABILITY_TICKS) {
            firePending();
        }
    }

    public void onOverlayReceived(Component content) {
        long currentTick = Utils.mc().level.getGameTime();

        String body = extractAllBodyText(content);
        String npc = extractFontText(content, OVERLAY_NAMEPLATE_FONT);

        if (body == null || body.isBlank()) return;

        if (npc == null || npc.isBlank()) {
            npc = pendingNpc;
        }

        if (pendingNpc != null && !npc.equals(pendingNpc)) {
            firePending();
        }

        if (!body.equals(pendingBody)) {
            if (pendingBody != null && body.length() < pendingBody.length() / 2) {
                firePending();
            }
            pendingBody = body;
            lastBodyChangeTick = currentTick;
        }

        pendingNpc = npc;
        lastOverlayPacketTick = currentTick;

        if (ModCore.config.isEarlyPlayOverlay()) {
            tryEarlyPlay();
        }
    }

    private void tryEarlyPlay() {
        if (pendingNpc == null || pendingBody == null) return;

        String combined = pendingNpc + ": " + pendingBody;
        String prefix =
                LineFormatter.formatToLineData(replacePlayerName(combined)).getSoundLine();
        if (prefix.length() < MIN_EARLY_PLAY_PREFIX_LENGTH) return;

        if (earlyPlayed) return;

        var match = ModCore.instance.soundsHandler.findEarlyMatch(prefix);
        if (match.isEmpty()) return;

        String key = match.get().getKey();
        if (key.equals(lastEarlyPlayedKey)) return;

        earlyPlayed = true;
        lastEarlyPlayedKey = key;
        ModCore.instance.soundPlayer.playFromObject(match.get().getValue());
    }

    private void firePending() {
        String body = pendingBody;
        String npc = pendingNpc;
        pendingBody = null;
        pendingNpc = null;
        lastBodyChangeTick = -1;
        lastOverlayPacketTick = -1;

        boolean alreadyPlayed = earlyPlayed && ModCore.config.isEarlyPlayOverlay();
        earlyPlayed = false;

        if (body == null) return;

        String combined = npc != null ? npc + ": " + body : "//" + body;
        if (combined.equals(lastFiredText)) return;

        lastFiredText = combined;

        if (ModCore.config.isLogOverlayDialogueToChat()) {
            Utils.sendMessage("§f" + combined);
        }
        VowLogger.logLine(combined);

        if (!alreadyPlayed) {
            String playbackLine = npc != null ? combined : body;
            ModCore.instance.soundPlayer.playSound(LineFormatter.formatToLineData(replacePlayerName(playbackLine)));
        }
    }

    private static String replacePlayerName(String text) {
        LocalPlayer player = Utils.player();
        if (player == null) return text;
        String name = player.getName().getString();
        if (name.isEmpty()) return text;
        return text.replace(name, "soldier");
    }

    /**
     * Extracts and joins all body-font text from the component tree.
     * High Unicode codepoints (custom font separators) are replaced with spaces.
     */
    private String extractAllBodyText(Component content) {
        List<String> parts = new ArrayList<>();
        collectFontText(content, OVERLAY_BODY_FONT, parts);
        if (parts.isEmpty()) return null;

        StringBuilder cleaned = new StringBuilder();
        for (String part : parts) {
            for (int i = 0; i < part.length(); ) {
                int cp = part.codePointAt(i);
                if (cp > 0x00FF) {
                    if (cleaned.length() > 0 && cleaned.charAt(cleaned.length() - 1) != ' ') {
                        cleaned.append(' ');
                    }
                } else {
                    cleaned.appendCodePoint(cp);
                }
                i += Character.charCount(cp);
            }
            if (cleaned.length() > 0 && cleaned.charAt(cleaned.length() - 1) != ' ') {
                cleaned.append(' ');
            }
        }
        String result = cleaned.toString().trim();
        return result.isBlank() ? null : result;
    }

    private void collectFontText(Component component, String fontSubstring, List<String> results) {
        if (component.getStyle().getFont().toString().contains(fontSubstring)) {
            List<Component> siblings = component.getSiblings();
            if (!siblings.isEmpty()) {
                results.add(siblings.get(0).getString());
            }
            // Don't recurse into siblings of a matched font component — they belong to this
            // font span and would produce duplicate fragments (e.g. the wrapped second line).
            return;
        }
        for (Component sibling : component.getSiblings()) {
            collectFontText(sibling, fontSubstring, results);
        }
    }

    private String extractFontText(Component component, String fontSubstring) {
        if (component.getStyle().getFont().toString().contains(fontSubstring)) {
            List<Component> siblings = component.getSiblings();
            if (!siblings.isEmpty()) {
                return siblings.get(0).getString();
            }
        }
        for (Component sibling : component.getSiblings()) {
            String result = extractFontText(sibling, fontSubstring);
            if (result != null) return result;
        }
        return null;
    }
}
