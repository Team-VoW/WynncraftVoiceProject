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
import java.util.Objects;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

/**
 * Handles Wynncraft overlay-based dialogue (used in areas like Fruma).
 * Kept entirely separate from the existing chat-based dialogue system.
 *
 * <p>This class is a thin Minecraft adapter: it extracts text from {@link Component}
 * objects and wires Minecraft-dependent side effects (sound playback, config reads,
 * logging) into the pure {@link OverlayStateMachine} via {@link OverlayDialogueListener}.
 *
 * <p>All tick-based stability logic, player-name substitution, and early play key
 * comparison live in {@link OverlayStateMachine}, which has no {@code net.minecraft.*}
 * dependencies and is directly unit-testable.
 */
public final class OverlayHandler implements OverlayDialogueListener {
    private static final String OVERLAY_BODY_FONT = "dialogue/text/wynncraft/body";
    private static final String OVERLAY_NAMEPLATE_FONT = "dialogue/text/nameplate";
    private static final String PLAYER_REPLACEMENT = "soldier";

    private final OverlayStateMachine stateMachine;

    public OverlayHandler() {
        stateMachine = new OverlayStateMachine(
                () -> Objects.requireNonNull(Utils.mc().level).getGameTime(),
                this,
                () -> ModCore.config.isEarlyPlayOverlay(),
                OverlayHandler::replacePlayerName);
    }

    public void onConnectionChange() {
        stateMachine.reset();
    }

    public void onTick() {
        stateMachine.onTick();
    }

    public void onOverlayReceived(Component content) {
        String body = extractAllBodyText(content);
        String npc = extractFontText(content, OVERLAY_NAMEPLATE_FONT);
        if (ModCore.config.isLogOverlayPackets() && Utils.mc().level != null) {
            long tick = Utils.mc().level.getGameTime();
            VowLogger.logComment("feed(" + tick + ", \"" + body + "\", \"" + npc + "\")");
        }
        stateMachine.onTextReceived(body, npc);
    }

    public boolean isVoiceDialogActive() {
        return stateMachine.isVoiceDialogActive();
    }

    // OverlayDialogueListener implementation ----------------------------------------

    @Override
    public void onDialogueFired(String combined, String formattedLine, String finalKey) {
        if (ModCore.config.isLogOverlayDialogueToChat()) {
            Utils.sendMessage("§f" + combined);
        }
        ModCore.instance.soundPlayer.playSound(LineFormatter.formatToLineData(formattedLine));
    }

    @Override
    public void onDialogueAlreadyPlayed(String combined, String formattedLine) {
        if (ModCore.config.isLogOverlayDialogueToChat()) {
            Utils.sendMessage("§f" + combined);
        }
        if (!ModCore.config.isOnlyLogNotPlayingLines() && ModCore.config.isLogDialogueLines()) {
            VowLogger.logLine(formattedLine + " [PLAYED]");
        }
    }

    @Override
    public void onWrongEarlyPlay() {
        ModCore.instance.soundPlayer.stopCurrentAudio();
    }

    @Override
    public String tryEarlyPlay(String rawCombined, String excludeKey) {
        String prefix =
                LineFormatter.formatToLineData(replacePlayerName(rawCombined)).getSoundLine();
        if (prefix.length() < ModCore.config.getEarlyPlayOverlayMinChars()) return null;

        var match = ModCore.instance.soundsHandler.findEarlyMatch(prefix);
        if (match.isEmpty()) return null;

        String key = match.get().getKey();
        if (key.equals(excludeKey)) return null;

        ModCore.instance.soundPlayer.playFromObject(match.get().getValue());
        return key;
    }

    // Text extraction (Minecraft Component API) ------------------------------------

    /**
     * Extracts and joins all body-font text segments from the component tree.
     * Delegates the codepoint-cleaning step to {@link #cleanBodyRawText(List)}.
     */
    private String extractAllBodyText(Component content) {
        List<String> parts = new ArrayList<>();
        collectFontText(content, OVERLAY_BODY_FONT, parts);
        return cleanBodyRawText(parts);
    }

    /**
     * Cleans a list of raw body-font strings by replacing high Unicode codepoints
     * (custom font separators used by Wynncraft) with spaces.
     *
     * <p>Package-private to allow direct testing without a Minecraft environment.
     */
    static String cleanBodyRawText(List<String> rawParts) {
        if (rawParts.isEmpty()) return null;

        StringBuilder cleaned = new StringBuilder();
        for (String part : rawParts) {
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

    private static String replacePlayerName(String text) {
        LocalPlayer player = Utils.player();
        if (player == null) return text;
        String name = player.getName().getString();
        if (name.isEmpty()) return text;
        text = text.replace(name, PLAYER_REPLACEMENT);
        String nickname = ModCore.config.getNicknameOverride();
        if (!nickname.isEmpty()) {
            text = text.replace(nickname, PLAYER_REPLACEMENT);
        }
        return text;
    }
}
