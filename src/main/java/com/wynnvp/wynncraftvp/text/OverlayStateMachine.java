/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import com.wynnvp.wynncraftvp.utils.LineFormatter;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.LongSupplier;

/**
 * Pure state machine for Wynncraft overlay dialogue detection.
 *
 * <p>Tracks the typewriter animation: body text grows character by character, so
 * we wait for it to be stable for {@value #OVERLAY_STABILITY_TICKS} ticks before
 * considering a line complete.
 *
 * <p>All Minecraft API access is injected:
 * <ul>
 *   <li>{@code tickClock} — current game tick
 *   <li>{@code playerNameReplacer} — substitutes the player's name with the placeholder
 *   <li>{@code listener} — receives audio/logging events
 * </ul>
 *
 * <p>This class has no {@code net.minecraft.*} dependencies and is directly unit-testable.
 * The key comparison logic (early play correct / wrong) lives here so tests can assert
 * on {@link OverlayDialogueListener#onWrongEarlyPlay()} and
 * {@link OverlayDialogueListener#onDialogueAlreadyPlayed} without a Minecraft environment.
 */
public final class OverlayStateMachine {
    // Ticks the body text must remain unchanged before we consider the typewriter done.
    static final int OVERLAY_STABILITY_TICKS = 5;

    // How many times the same body text must be received before we consider the typewriter done.
    // Real data shows partial texts repeat at most 3x during animation; completed lines repeat 100+x.
    static final int OVERLAY_STABILITY_REPEATS = 7;

    // Ticks without an overlay packet before voiceDialogActive is force-cleared (10 seconds).
    static final int VOICE_DIALOG_TIMEOUT_TICKS = 200;

    private final LongSupplier tickClock;
    private final OverlayDialogueListener listener;
    private final BooleanSupplier earlyPlayEnabled;
    private final Function<String, String> playerNameReplacer;

    private String pendingBody = null;
    private String pendingNpc = null;
    private int pendingBodyRepeatCount = 0;
    private long lastBodyChangeTick = -1;
    private long lastOverlayPacketTick = -1;
    private long lastReceivedOverlayTick = -1;
    private String lastFiredText = null;
    private boolean earlyPlayed = false;
    private String lastEarlyPlayedKey = null;
    private boolean voiceDialogActive = false;

    public OverlayStateMachine(
            LongSupplier tickClock,
            OverlayDialogueListener listener,
            BooleanSupplier earlyPlayEnabled,
            Function<String, String> playerNameReplacer) {
        this.tickClock = tickClock;
        this.listener = listener;
        this.earlyPlayEnabled = earlyPlayEnabled;
        this.playerNameReplacer = playerNameReplacer;
    }

    public void onTick() {
        long currentTick = tickClock.getAsLong();

        if (voiceDialogActive
                && lastReceivedOverlayTick != -1
                && currentTick >= lastReceivedOverlayTick + VOICE_DIALOG_TIMEOUT_TICKS) {
            voiceDialogActive = false;
        }

        if (pendingBody == null) return;

        if (lastBodyChangeTick != -1
                && currentTick >= lastBodyChangeTick + OVERLAY_STABILITY_TICKS
                && pendingBodyRepeatCount >= OVERLAY_STABILITY_REPEATS) {
            firePending();
            return;
        }

        if (lastOverlayPacketTick != -1
                && currentTick >= lastOverlayPacketTick + OVERLAY_STABILITY_TICKS
                && pendingBodyRepeatCount >= OVERLAY_STABILITY_REPEATS) {
            firePending();
        }
    }

    /**
     * Feed the text extracted from an overlay packet into the state machine.
     *
     * @param body the cleaned body text (null or blank means the overlay was cleared)
     * @param npc  the NPC nameplate text (null/blank falls back to the last known NPC)
     */
    public void onTextReceived(String body, String npc) {
        long currentTick = tickClock.getAsLong();
        lastReceivedOverlayTick = currentTick;

        if (body == null || body.isBlank()) {
            voiceDialogActive = false;
            pendingBody = null;
            pendingNpc = null;
            pendingBodyRepeatCount = 0;
            lastBodyChangeTick = -1;
            lastOverlayPacketTick = -1;
            earlyPlayed = false;
            lastEarlyPlayedKey = null;
            return;
        }

        voiceDialogActive = true;

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
            pendingBodyRepeatCount = 1;
            lastBodyChangeTick = currentTick;
        } else {
            pendingBodyRepeatCount++;
        }

        pendingNpc = npc;
        lastOverlayPacketTick = currentTick;

        if (earlyPlayEnabled.getAsBoolean()) {
            tryEarlyPlay();
        }
    }

    public boolean isVoiceDialogActive() {
        return voiceDialogActive;
    }

    /** Resets all state — call on server disconnect / reconnect. */
    public void reset() {
        pendingBody = null;
        pendingNpc = null;
        pendingBodyRepeatCount = 0;
        lastBodyChangeTick = -1;
        lastOverlayPacketTick = -1;
        lastReceivedOverlayTick = -1;
        lastFiredText = null;
        earlyPlayed = false;
        lastEarlyPlayedKey = null;
        voiceDialogActive = false;
    }

    private void tryEarlyPlay() {
        if (pendingNpc == null || pendingBody == null) return;
        if (earlyPlayed) return;

        String rawCombined = pendingNpc + ": " + pendingBody;
        String matchedKey = listener.tryEarlyPlay(rawCombined, lastEarlyPlayedKey);
        if (matchedKey == null) return;

        earlyPlayed = true;
        lastEarlyPlayedKey = matchedKey;
    }

    private void firePending() {
        String body = pendingBody;
        String npc = pendingNpc;
        pendingBody = null;
        pendingNpc = null;
        pendingBodyRepeatCount = 0;
        lastBodyChangeTick = -1;
        lastOverlayPacketTick = -1;

        String firedEarlyKey = earlyPlayed ? lastEarlyPlayedKey : null;
        earlyPlayed = false;

        if (body == null) return;

        String combined = npc != null ? npc + ": " + body : "//" + body;
        if (combined.equals(lastFiredText)) return;
        lastFiredText = combined;

        // Compute the final manifest key so we can compare against what was early-played.
        String playbackLine = npc != null ? combined : body;
        String formattedLine = playerNameReplacer.apply(playbackLine);
        String finalKey = LineFormatter.formatToLineData(formattedLine).getSoundLine();

        boolean wasEarlyPlayed = firedEarlyKey != null && earlyPlayEnabled.getAsBoolean();
        boolean alreadyPlayed = wasEarlyPlayed && finalKey.equals(firedEarlyKey);
        boolean wrongKeyPlayed = wasEarlyPlayed && !finalKey.equals(firedEarlyKey);

        if (wrongKeyPlayed) {
            listener.onWrongEarlyPlay();
        }

        if (alreadyPlayed) {
            listener.onDialogueAlreadyPlayed(combined, formattedLine);
        } else {
            listener.onDialogueFired(combined, formattedLine, finalKey);
        }
    }
}
